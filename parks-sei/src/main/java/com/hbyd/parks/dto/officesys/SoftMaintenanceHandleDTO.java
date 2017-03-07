package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Zhao_d on 2016/12/21.
 */
public class SoftMaintenanceHandleDTO extends BaseDTO {
    private String parentIdFK;      //所属维护记录
    private String handlePersonID;       //承担人
    private String handlePersonName;
    private String handleBegTime;        //开始时间
    private String handleDesc;         //处理过程
    private String handleEndTime;        //结束时间
    private String handleResult;           //处理结果


    public String getHandleBegTime() {
        return handleBegTime;
    }

    public void setHandleBegTime(String handleBegTime) {
        this.handleBegTime = handleBegTime;
    }

    public String getHandleDesc() {
        return handleDesc;
    }

    public void setHandleDesc(String handleDesc) {
        this.handleDesc = handleDesc;
    }

    public String getHandleEndTime() {
        return handleEndTime;
    }

    public void setHandleEndTime(String handleEndTime) {
        this.handleEndTime = handleEndTime;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public String getParentIdFK() {
        return parentIdFK;
    }

    public void setParentIdFK(String parentIdFK) {
        this.parentIdFK = parentIdFK;
    }

    public String getHandlePersonID() {
        return handlePersonID;
    }

    public void setHandlePersonID(String handlePersonID) {
        this.handlePersonID = handlePersonID;
    }

    public String getHandlePersonName() {
        return handlePersonName;
    }

    public void setHandlePersonName(String handlePersonName) {
        this.handlePersonName = handlePersonName;
    }
}
