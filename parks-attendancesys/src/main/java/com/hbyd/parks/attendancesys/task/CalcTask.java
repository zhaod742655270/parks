package com.hbyd.parks.attendancesys.task;

import com.hbyd.parks.domain.doorsys.AccessEvent;
import com.hbyd.parks.attendancesys.service.CalcService;

/**
 * 计算考勤的任务线程, 仅适用于单线程顺序计算<br/>
 */
public class CalcTask implements Runnable{

    private AccessEvent accessEvent;
    private CalcService calcService;

    public CalcTask(CalcService calcService) {
        this.calcService = calcService;
    }

    @Override
    public void run() {
        if(accessEvent == null){
            throw new RuntimeException("accessEvent inside CalcTask cannot be null!");
        }
        calcService.calc(accessEvent);

//        清理一级缓存

//        清理二级缓存

    }

    public void setAccessEvent(AccessEvent accessEvent) {
        this.accessEvent = accessEvent;
    }
}
