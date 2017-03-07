package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import com.hbyd.parks.domain.managesys.User;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Zhao_d on 2016/12/21.
 */
@Entity
@Table(name = "oa_maintenance_software_handle")
@Audited
public class SoftMaintenanceHandle extends RecoverableEntity {
    @ManyToOne
    @JoinColumn(name = "parentIdFK", referencedColumnName = "id")
    @NotAudited
    private SoftMaintenance softMaintenance;
    @ManyToOne
    @JoinColumn(name = "handlePersonFK")
    @NotAudited
    private User handlePerson;       //承担人
    private String handleBegTime;        //开始时间
    private String handleDesc;         //处理过程
    private String handleEndTime;        //结束时间
    private String handleResult;           //处理结果

    public SoftMaintenance getSoftMaintenance() {
        return softMaintenance;
    }

    public void setSoftMaintenance(SoftMaintenance softMaintenance) {
        this.softMaintenance = softMaintenance;
    }

    public User getHandlePerson() {
        return handlePerson;
    }

    public void setHandlePerson(User handlePerson) {
        this.handlePerson = handlePerson;
    }

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
}
