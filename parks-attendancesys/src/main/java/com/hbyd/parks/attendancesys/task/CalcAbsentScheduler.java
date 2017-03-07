package com.hbyd.parks.attendancesys.task;

import com.hbyd.parks.attendancesys.service.CalcService;
import com.hbyd.parks.common.util.DateUtil;
import com.hbyd.parks.dao.supportsys.EmployeeDao;
import com.hbyd.parks.domain.supportsys.Employee;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * 定时器：计算旷工
 * @author ren_xt
 */
@Component
public class CalcAbsentScheduler implements CalcTaskScheduler {

    private Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private CalcService calcService;

    @Resource
    private EmployeeDao employeeDao;

    @Resource
    private Executor myExecutor;

    /**
     * 分批查询人员的条件，默认按照人员的 ID 排序
     */
    private DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class)
            .add(eq("isInvolve", true));

    @Value("${batchSizeAbsent}")
    private int batchSize;

/*
    @Override
    @Scheduled(fixedDelay = 3000, initialDelay = 1000)
    public void calc() {
        System.out.println("#################################");
        try {
            Thread.sleep(1000*10);//测试 scheduler 的 pool-size 为 1 时是否影响其他定时任务的执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Scheduled(cron = "0 * 11 26 NOV ?")//在11月26日11点每分0秒执行
    @Scheduled(cron = "0 * * 1 DEC ?")//12月，每分执行一次（第0秒）
    public void calc() {
        System.out.println("当前时间：" + new Date().toLocaleString());
    }

    @Override
    @Scheduled(cron = "00 41 09 * * ?")//每晚10点执行旷工计算
    public void calc() {
        DateTime day = DateTime.now().minusDays(1);//定时任务触发的前一天
        ScrollableResults scrollableResults = employeeDao.getScrollableResults(criteria);

        long begin = new Date().getTime();
        int count = 0;
        while (scrollableResults.next()) {
            if (++count > 0 && count % 100 == 0) {
                logger.info(String.format("已为 %s 位员工计算了旷工", count));
            }
            Employee employee = (Employee) scrollableResults.get()[0];

            dispatchTask(day, employee);
        }
        long end = new Date().getTime();

        logger.info("开始执行时间：" + begin);
        logger.info("结束执行时间：" + end);
        logger.info(String.format("总共处理 %s 条考勤班次", count));
        logger.info("总共用时： " + DateUtil.getMins(end - begin) + " 分钟");
    }
*/

    /**
     * <pre>
     * 1. 确定计算日期
     * 1.1 每晚计算前一天考勤记录的旷工情况
     * 1.2 如果每晚计算当天考勤记录的旷工情况，就不合理，因为当天可能有上夜班的，要第二天才下班
     * 2. 参与考勤的人员 -》 通过人员和日期查询考勤记录 -》 计算考勤记录的旷工情况
     * 异常的情形：
     * 1. 人员没有部门
     * 2. 人员的部门不存在
     * 查看部门不存在的人员
     * SELECT emp.empName, emp.departmentFK, dept.id, dept.deptName from base_employee emp LEFT JOIN base_department dept on emp.departmentFK = dept.id where dept.id is NULL
     * 删除部门不存在的人员
     * DELETE from base_employee where departmentFK in (SELECT DISTINCT emp.departmentFK from base_employee emp LEFT JOIN base_department dept on emp.departmentFK = dept.id where dept.id is NULL)
     * 不参与考勤的人员，例如：ID 为 118 的部门 - 老科协
     * SELECT id, empName, isInvolve from base_employee where isInvolve = 0;
     *
     *
     * TODO 人员必须有部门，为了避免脏数据导致的异常，在查询时就应该确保一人一部门
     *
     * 使用 ScrollableResults 存在的问题：
     * 1. ScrollableResults 需要使用事务，会长时间占用数据库连接，如果旷工计算的时间超过了
     *  连接池设定：maximumActiveTime = 1200000 (20 分钟), 连接会被数据库强制断开
     * 2. ScrollableResults 底层使用的貌似是数据库的游标，使用 ScrollableResults 迭代
     * Employee 计算旷工时，竟然会锁死 AtteInfo 和 AtteDetail 两表，不知道为什么
     * 3. ScrollableResults 的速度特别慢，处理一万个人员大概 25 分钟
     * </pre>
     */
    @Override
    @Scheduled(cron = "0 1 1 * * ?")
    public void calc(){
        long begin = new Date().getTime();

        DateTime day = DateTime.now().minusDays(1);

        int round = 1;
        int total = 0;//处理的人员总数
        List<Employee> emps;//存储每一批处理的人员

        while (true) {
            emps = employeeDao.getBatch(criteria, (round - 1) * batchSize, batchSize);
            if (emps.size() > 0){
                total += emps.size();
//                calcWithMultiThread(day, emps);
                calcWithSingleThread(day, emps);
            }else {
                break;
            }
            round++;
        }

        long end = new Date().getTime();

        logger.info(String.format("主线程：%s，总共处理 %s 个人员，用时 %s 分钟", Thread.currentThread().getName(), total, DateUtil.getMins(end - begin)));
    }

    /**单线程计算旷工
     * @param day 日期
     * @param emps 人员
     */
    private void calcWithSingleThread(DateTime day, List<Employee> emps) {
        for (Employee emp : emps) {
            try {
                calcService.calcAbsent(day, emp);
            } catch (Exception e) {
                //确保单次计算不影响整体的计算
                logger.info(String.format("当前线程 %s，旷工计算失败，人员：%s，日期：%s", Thread.currentThread().getName(), emp.getId(), day), e);
                continue;
            }
        }
    }

    /**使用线程池计算旷工，实测发现：该多线程版本很容易形成死锁
     * @param day 日期
     * @param emps 人员
     */
    @Deprecated
    private void calcWithMultiThread(DateTime day, List<Employee> emps) {
        AbsentTask absentTask = new AbsentTask();
        absentTask.setDay(day);
        absentTask.setEmployees(emps);
        absentTask.setCalcService(calcService);
        myExecutor.execute(absentTask);
    }
}
