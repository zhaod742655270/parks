package com.hbyd.parks.domain.supportsys;

import javax.persistence.*;

/**
 * 门
 */
@Entity
@Table(name = "base_device_door", schema = "dbo", catalog = "parks")
//@DiscriminatorValue(DiscValue.门)
@PrimaryKeyJoinColumn(
        name = "id", referencedColumnName = "id"
)
@Access(AccessType.PROPERTY)
public class DeviceDoor extends Device {
    private String doorSn;
    private String workMode;
    private String openTimeId;
    private String closeTimeId;
    private String specialTimeId;
    private String delayTime;
    private String openTime;
    private String warningTime;
    private String alarmPwd;
    private Integer alarmPwdUsed;
    private String publicPwd;
    private Integer publicPwdUsed;
    private String isDown;

    @Basic
    @Column(name = "doorSn")
    public String getDoorSn() {
        return doorSn;
    }

    public void setDoorSn(String doorSn) {
        this.doorSn = doorSn;
    }

    @Basic
    @Column(name = "workMode")
    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    @Basic
    @Column(name = "openTimeId")
    public String getOpenTimeId() {
        return openTimeId;
    }

    public void setOpenTimeId(String openTimeId) {
        this.openTimeId = openTimeId;
    }

    @Basic
    @Column(name = "closeTimeId")
    public String getCloseTimeId() {
        return closeTimeId;
    }

    public void setCloseTimeId(String closeTimeId) {
        this.closeTimeId = closeTimeId;
    }

    @Basic
    @Column(name = "specialTimeId")
    public String getSpecialTimeId() {
        return specialTimeId;
    }

    public void setSpecialTimeId(String specialTimeId) {
        this.specialTimeId = specialTimeId;
    }

    @Basic
    @Column(name = "delayTime")
    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }

    @Basic
    @Column(name = "openTime")
    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    @Basic
    @Column(name = "warningTime")
    public String getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(String warningTime) {
        this.warningTime = warningTime;
    }

    @Basic
    @Column(name = "alarmPwd")
    public String getAlarmPwd() {
        return alarmPwd;
    }

    public void setAlarmPwd(String alarmPwd) {
        this.alarmPwd = alarmPwd;
    }

    @Basic
    @Column(name = "alarmPwdUsed")
    public Integer getAlarmPwdUsed() {
        return alarmPwdUsed;
    }

    public void setAlarmPwdUsed(Integer alarmPwdUsed) {
        this.alarmPwdUsed = alarmPwdUsed;
    }

    @Basic
    @Column(name = "publicPwd")
    public String getPublicPwd() {
        return publicPwd;
    }

    public void setPublicPwd(String publicPwd) {
        this.publicPwd = publicPwd;
    }

    @Basic
    @Column(name = "publicPwdUsed")
    public Integer getPublicPwdUsed() {
        return publicPwdUsed;
    }

    public void setPublicPwdUsed(Integer publicPwdUsed) {
        this.publicPwdUsed = publicPwdUsed;
    }

    @Basic
    @Column(name = "isDown")
    public String getIsDown() {
        return isDown;
    }

    public void setIsDown(String isDown) {
        this.isDown = isDown;
    }
}
