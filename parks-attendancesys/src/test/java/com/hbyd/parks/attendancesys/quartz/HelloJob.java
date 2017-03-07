package com.hbyd.parks.attendancesys.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 自定义作业
 */
public class HelloJob implements Job {
    protected Logger logger = Logger.getLogger(this.getClass());

    private String one;
    private String two;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("当前时间：" + new Date().toLocaleString());

//      从 JobDetail 中获取数据
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

//      从 JobDataMap 中获取数据
//        System.out.println(jobDataMap.get("one"));
//        System.out.println(jobDataMap.get("two"));

//      从 Job 直接获取数据
//        System.out.println(one);
//        System.out.println(two);

//      获取 Trigger 对于的 JobDataMap
        JobDataMap map = context.getTrigger().getJobDataMap();
        System.out.println(map.get("tdk_00"));

//      Job 对应的 JobDataMap 和 Trigger 对应的 JobDataMap 合并为 context.getMergedJobDataMap();
        JobDataMap mergedMap = context.getMergedJobDataMap();
        System.out.println(mergedMap.get("tdk_00"));
    }


//  如果定义和 JobDataMap 中 key 值相同的 setter, Quartz 的默认 JobFactory 实现会自动调用 setter
//  并将 JobDataMap 中的数据做对应填充
    public void setOne(String one) {
        this.one = one;
    }

    public void setTwo(String two) {
        this.two = two;
    }
}
