package com.hbyd.parks.attendancesys.task;

import com.hbyd.parks.attendancesys.service.CalcService;
import com.hbyd.parks.dao.doorsys.AccessEventDao;
import com.hbyd.parks.domain.attendancesys.*;
import com.hbyd.parks.domain.doorsys.AccessEvent;
import com.hbyd.parks.domain.managesys.Department;
import com.hbyd.parks.domain.supportsys.Card;
import com.hbyd.parks.domain.supportsys.Employee;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**定时器："实时" 计算刷卡事件
 * <pre>
 * "实时" 计算服务：每隔几秒做如下操作：
 * 1. 查询未计算过的刷卡事件
 * 2. 计算刷卡事件对应的考勤记录
 *
 * 使用线程池执行任务的问题：
 * 1) 线程池的问题：
 *     如果 executer 的 coreSize 为 5，打卡记录有 6 条，那么有 5 个线程同时在计算考勤
 *     假设线程的名称为：Thread-1, Thread-2...Thread-5
 *     那么 T1 - T5 都会同时执行 calcService.calc()，就出现了如下问题：
 *         1. T1 - T5 很可能同时判断并认定：人员当天没有考勤记录。导致一个人员当天插了多条考勤记录
 *         2. 下次计算会以上次计算的考勤记录为基准并累加，如果多个线程同时计算一个人的考勤记录
 *             某次计算很可能不以上次计算结果（考勤记录）为准，如：某次计算不等上次计算完成就开始计算。
 *             这就导致计算结果的丢失，
 *
 *     综上所述，如果需要多线程计算刷卡记录，也应该是一个人当天的所有刷卡记录由一个线程顺序计算
 *
 * 2) 定时器的问题
 *     1. 如果定时器采用 fixed-rate, 第一次定时任务读取的刷卡记录尚未计算完毕，第二次定时任务又去读取刷卡记录
 *         很可能读到第一次尚未计算完成的刷卡记录，造成了重复计算。
 *     2. 即便定时器采用 fixed-delay, 定时任务也只是派发任务给线程池，派发完毕后就算本次定时任务完成
 *         但线程池可能还没有处理完其派发的任务，如果此时下次定时任务触发，仍有可能读到上次未来得及计算的刷卡记录
 *         造成重复计算。
 *
 *     综上所述，计算任务不能交给线程池去执行，而应交给定时任务所在线程来执行，这样，只有当本次定时任务计算
 *     完所有刷卡记录，才会延时执行下次定时任务
 *
 * 3) 缓存的问题
 *     如果刷卡记录特别多，需要：
 *     1. 滚动读取刷卡记录，用一条读一条。
 *     2. hibernate 会缓存读取的实体(一级和二级), 因此, 每处理完一个实体后, 需要先将 Session 内的变动
 *         同步到数据库，再清理缓存(一级和二级)
 *
 *     如何清理缓存？
 *         刷卡记录计算完毕后，才能清理缓存（刷卡记录），定时任务如果将处理刷卡记录的任务交给线程（池）来执行
 *         就无法得知该任务是否执行完毕，自然无法清理缓存。
 *
 *         因此，最好是由定时任务自己来读取、计算、清理刷卡记录。
 *
 * 4) 工厂产品的注入问题
 *     不能将产品直接使用 @Resource 等注解直接注入，否则每次使用的都是同一份产品，正确的做法是：
 *         "retrieve product from applicationContext programmatically when needed"
 *     这可以通过实现 ApplicationContextAware 注入 ApplicationContext 来实现
 * </pre>
 *
 * @author ren_xt
 * @see {@literal 5.8.3 Customizing instantiation logic with a FactoryBean}
 */
@Component//当前类必须注册为 Bean, 这样当 Spring 扫描容器内 Bean 的时候就能够为 @Scheduled 标注的方法建立任务
public class RealTimeCalcTaskScheduler implements CalcTaskScheduler {

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 每批处理的记录数
     */
    @Value("${batchSizeEvent}")//使用占位符从 Properties 文件获取
    private int batchSize;

    /**
     * 计算考勤的服务
     */
    @Resource
    private CalcService calcService;

    /**
     * 刷卡记录 DAO
     */
    @Resource
    private AccessEventDao accessEventDao;

    /**
     * 计数用的 Criteria
     */
    private DetachedCriteria criteria_cnt = DetachedCriteria
            .forClass(AccessEvent.class)
            .add(Restrictions.eq("isCalced", false));//尚未计算过的刷卡记录


    /**
     * 查询的 Criteria
     */
    private DetachedCriteria criteria_rows = DetachedCriteria
            .forClass(AccessEvent.class)
            .add(Restrictions.eq("isCalced", false));//尚未计算过的刷卡记录

    @Override
    @Scheduled(fixedDelay = 3000, initialDelay = 1000)
    public void calc() {//分派任务，不开启事务，否则长时间占用表，无法查询
        long begin = new Date().getTime();
        dispatchCalcTask();
        long end = new Date().getTime();

        logger.info("开始执行时间：" + begin);
        logger.info("结束执行时间：" + end);
        logger.info("批次执行时间： " + (end - begin) / 1000);
    }

    private void dispatchCalcTask() {
        Long totalCnt = accessEventDao.getCnt(criteria_cnt);

        logger.info("There's " + totalCnt + " Access Events in total");

        if(totalCnt > 0){
//分批处理
            List<AccessEvent> eList = accessEventDao.getBatch(criteria_rows, 0, batchSize);
            for (int i = 0; i < eList.size(); i++) {
                AccessEvent event = eList.get(i);

                try {
                    calcService.calc(event);
                } catch (Exception e) {
//                  异常不做处理，只记录日志
                    logger.error("计算失败, 当前刷卡记录 ID ：" + event.getId(), e);
                    continue;//确保单次计算不影响整体的计算
                }
            }
            clear2ndCache();//二级缓存似乎没必要开启，因为这里在不断清理，还不如关了。

//滚动处理，无需分批
//存在问题：java.sql.SQLException: Invalid state, the ResultSet object is closed.
//如果想要 ResultSet 不关闭，只能在 dispatchCalcTask 上加事务，但这样就无法实时查询，因此不推荐
//            ScrollableResults results = accessEventDao.getScrollableResults(criteria_rows);
//            int count = 0;
//            while(results.next()) {
//                AccessEvent event = (AccessEvent) results.get()[0];
//                calcService.calc(event);//calc() 在执行完毕后会清理 Session 缓存
//                if(++count % 100 == 0){
//                    clear2ndCache();
//                }
//            }
//            clear2ndCache();
        }
    }

    /**
     * 清理二级缓存(计算考勤时，用了哪个实体的 DAO, 就清理这个实体的二级缓存)，在一个批次计算完毕后进行
     */
//    TODO 高频度的计算导致清理二级缓存的操作增多，如果内存不受控制，可以先禁用二级缓存
    private void clear2ndCache() {
        accessEventDao.clearSecLevelCache(
                AccessEvent.class,
                Employee.class,
                Department.class,
                Card.class,
                Shift.class,
                Interval.class,
                AtteInfo.class,
                AtteDetail.class,
                Holiday.class,
                LeaveRecord.class,
                GlobalDef.class);
    }

/*
    @Deprecated
    public void dispatchTask_rev1(){
        Long totalCnt = accessEventDao.getCnt(criteria_cnt);//只读事务

        logger.info("There's " + totalCnt + " Access Events in total");

        if(totalCnt > 0){
//          存在数据库连接长时间不释放的问题, 因为事务频繁开关
            ScrollableResults results = accessEventDao.getScrollableResults(criteria_rows);

            int counter = 0;
            while(results.next()){
                AccessEvent event = (AccessEvent) results.get()[0];

//              1. 使用线程池执行
//                calcWithThreadPool(event);//不推荐这么做。问题很多，参考 javadoc

//              2. 使用当前线程执行
                calc(event);
//                accessEventDao.clearFirLevelCache(event);
                accessEventDao.clearSecLevelCache(AccessEvent.class);


                if(++counter > 0 && counter % 5 == 0){
                    logger.info("Fetched " + counter + " Entities!");
                }
            }

            int mod = counter % 5;
            if(mod > 0){
                logger.info("Fetched last " + mod + " Entities!");
            }
        }
    }

    @Deprecated
    public void dispatchTask_rev2() {
        Long totalCnt = accessEventDao.getCnt(criteria_cnt);

        logger.info("There's " + totalCnt + " Access Events in total");

        if(totalCnt > 0){
            //不可行，ScrollableResults 作为结果返回后无法取值
            ScrollableResults rollableResultSet = accessEventDao.getRollableResultSet(criteria_rows);

            while(rollableResultSet.next()){
                AccessEvent event = (AccessEvent) rollableResultSet.get()[0];
                calc(event);

//              清理二级缓存，不可行，每计算一次都要清理一次，太麻烦。
                accessEventDao.clearSecLevelCache(AccessEvent.class);
                accessEventDao.clearSecLevelCache(AtteInfo.class);
                accessEventDao.clearSecLevelCache(AtteDetail.class);
            }
        }
    }


    @Deprecated
    public void calcWithThreadPool(AccessEvent event) {
//        指定任务线程需要处理的内容
        CalcTask calcTask = applicationContext.getBean("calcTask", CalcTask.class);
        calcTask.setAccessEvent(event);

//        交给 ThreadPool 执行任务
        myExecutor.execute(calcTask);
    }
*/
}
