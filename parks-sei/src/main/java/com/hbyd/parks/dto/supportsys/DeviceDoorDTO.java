package com.hbyd.parks.dto.supportsys;

/**
 * 门
 */
public class DeviceDoorDTO extends DeviceDTO {
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

    public String getDoorSn() {
        return doorSn;
    }

    public void setDoorSn(String doorSn) {
        this.doorSn = doorSn;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public String getOpenTimeId() {
        return openTimeId;
    }

    public void setOpenTimeId(String openTimeId) {
        this.openTimeId = openTimeId;
    }

    public String getCloseTimeId() {
        return closeTimeId;
    }

    public void setCloseTimeId(String closeTimeId) {
        this.closeTimeId = closeTimeId;
    }

    public String getSpecialTimeId() {
        return specialTimeId;
    }

    public void setSpecialTimeId(String specialTimeId) {
        this.specialTimeId = specialTimeId;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(String warningTime) {
        this.warningTime = warningTime;
    }

    public String getAlarmPwd() {
        return alarmPwd;
    }

    public void setAlarmPwd(String alarmPwd) {
        this.alarmPwd = alarmPwd;
    }

    public Integer getAlarmPwdUsed() {
        return alarmPwdUsed;
    }

    public void setAlarmPwdUsed(Integer alarmPwdUsed) {
        this.alarmPwdUsed = alarmPwdUsed;
    }

    public String getPublicPwd() {
        return publicPwd;
    }

    public void setPublicPwd(String publicPwd) {
        this.publicPwd = publicPwd;
    }

    public Integer getPublicPwdUsed() {
        return publicPwdUsed;
    }

    public void setPublicPwdUsed(Integer publicPwdUsed) {
        this.publicPwdUsed = publicPwdUsed;
    }

    public String getIsDown() {
        return isDown;
    }

    public void setIsDown(String isDown) {
        this.isDown = isDown;
    }
}
