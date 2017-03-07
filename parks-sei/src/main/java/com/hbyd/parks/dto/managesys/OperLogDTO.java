package com.hbyd.parks.dto.managesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by allbutone on 2016/1/11.
 */
public class OperLogDTO extends BaseDTO {
    /**
     * 操作者名称
     */
    private String operUser;
    /**
     * 操作类型(Action 方法对应的中文含义)
     */
    private String operType;
    /**
     * 操作时间
     */
    private String operTime;

    public String getOperUser() {
        return operUser;
    }

    public void setOperUser(String operUser) {
        this.operUser = operUser;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime;
    }
}
