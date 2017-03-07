package com.hbyd.parks.attendancesys.task;

import com.hbyd.parks.attendancesys.service.CalcService;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.Resource;

/**
 * A Factory Bean producing {@link CalcTask}
 */
public class CalcTaskFactoryBean implements FactoryBean<CalcTask> {

    @Resource
    private CalcService calcService;

    @Override
    public CalcTask getObject() throws Exception {
        return new CalcTask(calcService);
    }

    @Override
    public Class<?> getObjectType() {
        return CalcTask.class;
    }

    @Override
    public boolean isSingleton() {
        return false;//Product is a Thread, must not be singleton;
    }
}
