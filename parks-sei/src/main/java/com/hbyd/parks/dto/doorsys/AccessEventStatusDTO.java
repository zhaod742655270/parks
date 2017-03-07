package com.hbyd.parks.dto.doorsys;

import java.io.Serializable;

/**
 * 后续补充的实时状态信息
 */
public class AccessEventStatusDTO implements Serializable{
    /**
     * 园区内员工数
     */
    private int empCnt;
    /**
     * 园区内访客数
     */
    private int visCnt;
    /**
     * 园区内员工车辆数
     */
    private int empCarCnt;
    /**
     * 园区内访客车辆数
     */
    private int visCarCnt;

    public int getEmpCnt() {
        return empCnt;
    }

    public void setEmpCnt(int empCnt) {
        this.empCnt = empCnt;
    }

    public int getVisCnt() {
        return visCnt;
    }

    public void setVisCnt(int visCnt) {
        this.visCnt = visCnt;
    }

    public int getEmpCarCnt() {
        return empCarCnt;
    }

    public void setEmpCarCnt(int empCarCnt) {
        this.empCarCnt = empCarCnt;
    }

    public int getVisCarCnt() {
        return visCarCnt;
    }

    public void setVisCarCnt(int visCarCnt) {
        this.visCarCnt = visCarCnt;
    }
}
