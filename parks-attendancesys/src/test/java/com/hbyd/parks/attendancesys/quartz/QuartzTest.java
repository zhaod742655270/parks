package com.hbyd.parks.attendancesys.quartz;


import org.quartz.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * HelloJob 是一个 Job, 只是简单地通过日志输出当前时间，因此，在测试该案例时，需要将日志级别调整为 INFO
 *
 * the Scheduler will not actually act on any triggers (execute jobs) until it has been started with the start() method
 */
public class QuartzTest {
    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler sched = schedFact.getScheduler();

//      定时器只有执行了 start() 操作才会作用于其内的 Trigger
        sched.start();

        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(HelloJob.class)
                .withIdentity("myJob", "group1")
                .usingJobData("one", "first")//Job 绑定的 JobData
                .usingJobData("two", "second")
                .build();

//      Notice that we give the scheduler a JobDetail instance, and that it knows the type
//      of job to be executed by simply providing the job's class as we build the JobDetail.
//      Each (and every) time the scheduler executes the job, it creates a new instance of
//      the class before calling its execute(..) method. When the execution is complete, references
//      to the job class instance are dropped, and the instance is then garbage collected.

//        withIdentity 定义的 JobKey
        System.out.println(job.getKey().getName());
        System.out.println(job.getKey().getGroup());

        // Trigger the job to run now, and then every 40 seconds
        Trigger tr_00 = newTrigger()
                .withIdentity("myTrigger", "group1")
                .usingJobData("tdk_00", "tdv_00")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();

//        withIdentity 定义的 TriggerKey
        System.out.println(tr_00.getKey().getName());
        System.out.println(tr_00.getKey().getGroup());

        // Tell quartz to schedule the job using our trigger
        sched.scheduleJob(job, tr_00);

    }
}
