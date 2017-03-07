package com.hbyd.parks.domain.managesys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 基本操作日志
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "oa_operation_log")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class OperLog extends BaseEntity {
    private String operUser;
    private String operType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date operTime;

    public OperLog(String operUser, String operType, Date operTime) {
        this.operUser = operUser;
        this.operType = operType;
        this.operTime = operTime;
    }

    public OperLog() {
    }

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

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }
}
