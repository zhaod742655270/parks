package com.hbyd.parks.attendancesys.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by allbutone on 2014/9/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/attendancesys/applicationContext_Task.xml"})
public class TestTaskExecutor {

    @Resource
    private TaskExecutorExample taskExecutorExample;

    @Test
    public void testExecutor(){
        taskExecutorExample.printMessage();
    }

    @Test
    public void testScheduler() throws Exception {


    }
}
