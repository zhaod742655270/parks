package com.hbyd.parks.attendancesys.task;

import com.hbyd.parks.attendancesys.service.CalcService;
import com.hbyd.parks.common.util.DateUtil;
import com.hbyd.parks.domain.supportsys.Employee;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * 线程不是由 Spring 实例化的，因此不能注入
 */
class AbsentTask implements Runnable{

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 计算旷工的服务，因为是线程，不能通过 Spring 注入
     */
    private CalcService calcService;

    /**
     * 需要计算旷工的一批人员
     */
    private List<Employee> employees;

    /**
     * 计算哪天的旷工
     */
    private DateTime day;

    public void setDay(DateTime day) {
        this.day = day;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setCalcService(CalcService calcService) {
        this.calcService = calcService;
    }

    @Override
    public void run() {
        long begin = new Date().getTime();
        for (Employee emp : employees) {
            try {
                calcService.calcAbsent(day, emp);
            } catch (Exception e) {
                //确保单次计算不影响整体的计算
                logger.info(String.format("当前线程 %s，旷工计算失败，人员：%s，日期：%s", Thread.currentThread().getName(), emp.getId(), day), e);
                continue;
            }
        }
        long end = new Date().getTime();
        logger.info(String.format("当前线程：%s，处理人员数：%s，总共用时： %s 分钟", Thread.currentThread().getName(), employees.size(), DateUtil.getMins(end - begin)));
    }
}