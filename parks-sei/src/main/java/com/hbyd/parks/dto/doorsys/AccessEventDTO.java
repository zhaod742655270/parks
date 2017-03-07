package com.hbyd.parks.dto.doorsys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * 刷卡记录
 */
public class AccessEventDTO extends BaseDTO {

    private String eid;
    private String passId;
    private String passSn;
    private String ioType;
    private String eventTime;
    private String upTime;
    private String capPhotoSn;
    private String deviceType;
    private String deviceId;
    private String doorSn;
    private String devWorkMode;
    private String devIdentifyMode;
    private String operMode;
    private String identifyResult;
    private Boolean isValidPass;
    private Boolean isTriggerPass;
    private String cardSCode;
    private String cardNo;
    private String userNo;
    private String carTime;
    private String carId;
    private Boolean isCalced;

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public String getPassSn() {
        return passSn;
    }

    public void setPassSn(String passSn) {
        this.passSn = passSn;
    }

    public String getIoType() {
        return ioType;
    }

    public void setIoType(String ioType) {
        this.ioType = ioType;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getCapPhotoSn() {
        return capPhotoSn;
    }

    public void setCapPhotoSn(String capPhotoSn) {
        this.capPhotoSn = capPhotoSn;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDoorSn() {
        return doorSn;
    }

    public void setDoorSn(String doorSn) {
        this.doorSn = doorSn;
    }

    public String getDevWorkMode() {
        return devWorkMode;
    }

    public void setDevWorkMode(String devWorkMode) {
        this.devWorkMode = devWorkMode;
    }

    public String getDevIdentifyMode() {
        return devIdentifyMode;
    }

    public void setDevIdentifyMode(String devIdentifyMode) {
        this.devIdentifyMode = devIdentifyMode;
    }

    public String getOperMode() {
        return operMode;
    }

    public void setOperMode(String operMode) {
        this.operMode = operMode;
    }

    public String getIdentifyResult() {
        return identifyResult;
    }

    public void setIdentifyResult(String identifyResult) {
        this.identifyResult = identifyResult;
    }

    public Boolean getIsValidPass() {
        return isValidPass;
    }

    public void setIsValidPass(Boolean isValidPass) {
        this.isValidPass = isValidPass;
    }

    public Boolean getIsTriggerPass() {
        return isTriggerPass;
    }

    public void setIsTriggerPass(Boolean isTriggerPass) {
        this.isTriggerPass = isTriggerPass;
    }

    public String getCardSCode() {
        return cardSCode;
    }

    public void setCardSCode(String cardSCode) {
        this.cardSCode = cardSCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getCarTime() {
        return carTime;
    }

    public void setCarTime(String carTime) {
        this.carTime = carTime;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public Boolean getIsCalced() {
        return isCalced;
    }

    public void setIsCalced(Boolean isCalced) {
        this.isCalced = isCalced;
    }
}
