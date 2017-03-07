package com.hbyd.parks.domain.doorsys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * 刷卡事件，用于考勤计算
 */
@Entity
@javax.persistence.Table(name = "door_access_event", schema = "dbo", catalog = "parks")
@Access(AccessType.PROPERTY)
public class AccessEvent extends BaseEntity {

    private String eid;

    @Basic
    @javax.persistence.Column(name = "eid")
    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    private String passId;

    @Basic
    @javax.persistence.Column(name = "passId")
    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    private String passSn;

    @Basic
    @javax.persistence.Column(name = "passSn")
    public String getPassSn() {
        return passSn;
    }

    public void setPassSn(String passSn) {
        this.passSn = passSn;
    }

    private String ioType;

    @Basic
    @javax.persistence.Column(name = "ioType")
    public String getIoType() {
        return ioType;
    }

    public void setIoType(String ioType) {
        this.ioType = ioType;
    }

    private Timestamp eventTime;

    @Basic
    @javax.persistence.Column(name = "eventTime")
    public Timestamp getEventTime() {
        return eventTime;
    }

    public void setEventTime(Timestamp eventTime) {
        this.eventTime = eventTime;
    }

    private Timestamp upTime;

    @Basic
    @javax.persistence.Column(name = "upTime")
    public Timestamp getUpTime() {
        return upTime;
    }

    public void setUpTime(Timestamp upTime) {
        this.upTime = upTime;
    }

    private String capPhotoSn;

    @Basic
    @javax.persistence.Column(name = "capPhotoSn")
    public String getCapPhotoSn() {
        return capPhotoSn;
    }

    public void setCapPhotoSn(String capPhotoSn) {
        this.capPhotoSn = capPhotoSn;
    }

    private String deviceType;

    @Basic
    @javax.persistence.Column(name = "deviceType")
    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    private String deviceId;

    @Basic
    @javax.persistence.Column(name = "deviceId")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    private String doorSn;

    @Basic
    @javax.persistence.Column(name = "doorSn")
    public String getDoorSn() {
        return doorSn;
    }

    public void setDoorSn(String doorSn) {
        this.doorSn = doorSn;
    }

    private String devWorkMode;

    @Basic
    @javax.persistence.Column(name = "devWorkMode")
    public String getDevWorkMode() {
        return devWorkMode;
    }

    public void setDevWorkMode(String devWorkMode) {
        this.devWorkMode = devWorkMode;
    }

    private String devIdentifyMode;

    @Basic
    @javax.persistence.Column(name = "devIdentifyMode")
    public String getDevIdentifyMode() {
        return devIdentifyMode;
    }

    public void setDevIdentifyMode(String devIdentifyMode) {
        this.devIdentifyMode = devIdentifyMode;
    }

    private String operMode;

    @Basic
    @javax.persistence.Column(name = "operMode")
    public String getOperMode() {
        return operMode;
    }

    public void setOperMode(String operMode) {
        this.operMode = operMode;
    }

    private String identifyResult;

    @Basic
    @javax.persistence.Column(name = "identifyResult")
    public String getIdentifyResult() {
        return identifyResult;
    }

    public void setIdentifyResult(String identifyResult) {
        this.identifyResult = identifyResult;
    }

    private Boolean isValidPass;

    @Basic
    @javax.persistence.Column(name = "isValidPass")
    public Boolean getIsValidPass() {
        return isValidPass;
    }

    public void setIsValidPass(Boolean isValidPass) {
        this.isValidPass = isValidPass;
    }

    private Boolean isTriggerPass;

    @Basic
    @javax.persistence.Column(name = "isTriggerPass")
    public Boolean getIsTriggerPass() {
        return isTriggerPass;
    }

    public void setIsTriggerPass(Boolean isTriggerPass) {
        this.isTriggerPass = isTriggerPass;
    }

    private String cardSCode;

    @Basic
    @javax.persistence.Column(name = "cardSCode")
    public String getCardSCode() {
        return cardSCode;
    }

    public void setCardSCode(String cardSCode) {
        this.cardSCode = cardSCode;
    }

    private String cardNo;

    @Basic
    @javax.persistence.Column(name = "cardNo")
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String userNo;

    @Basic
    @javax.persistence.Column(name = "userNo")
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    private String carTime;

    @Basic
    @javax.persistence.Column(name = "carTime")
    public String getCarTime() {
        return carTime;
    }

    public void setCarTime(String carTime) {
        this.carTime = carTime;
    }

    private String carId;

    @Basic
    @javax.persistence.Column(name = "carId")
    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    private Boolean isCalced;

    @Basic
    @javax.persistence.Column(name = "isCalced")
    public Boolean getIsCalced() {
        return isCalced;
    }

    public void setIsCalced(Boolean isCalced) {
        this.isCalced = isCalced;
    }
}
