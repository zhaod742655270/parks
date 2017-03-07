package com.hbyd.parks.domain.doorsys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 用于刷卡数据检索的实体
 */
@Entity
@javax.persistence.Table(name = "door_access_event_info", schema = "dbo", catalog = "parks")
@Access(AccessType.PROPERTY)
@SecondaryTable(name = "door_capture_photo", schema = "dbo", catalog = "parks", pkJoinColumns = @PrimaryKeyJoinColumn(
        name = "id",//door_capture_photo.id
        referencedColumnName = "id"//door_access_event_info.id
))
public class AccessEventInfo extends BaseEntity {
//  下面是 AccessEventInfo 的信息：
    private String eId;

    @Basic
    @javax.persistence.Column(name = "eId")
    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
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

    private String devType;

    @Basic
    @javax.persistence.Column(name = "devType")
    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    private String devUUID;

    @Basic
    @javax.persistence.Column(name = "devUUID")
    public String getDevUUID(){return  devUUID;}

    public void setDevUUID(String devUUID){this.devUUID=devUUID;}

    private String devId;

    @Basic
    @javax.persistence.Column(name = "devId")
    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    private String devDesc;

    @Basic
    @javax.persistence.Column(name = "devDesc")
    public String getDevDesc() {
        return devDesc;
    }

    public void setDevDesc(String devDesc) {
        this.devDesc = devDesc;
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

    private String doorName;

    @Basic
    @javax.persistence.Column(name = "doorName")
    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
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

    private String cardProp;

    @Basic
    @javax.persistence.Column(name = "cardProp")
    public String getCardProp() {
        return cardProp;
    }

    public void setCardProp(String cardProp) {
        this.cardProp = cardProp;
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

    private String deptId;

    @Basic
    @javax.persistence.Column(name = "deptId")
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    private String deptName;

    @Basic
    @javax.persistence.Column(name = "deptName")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    private String personType;

    @Basic
    @javax.persistence.Column(name = "personType")
    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    private String personId;

    @Basic
    @javax.persistence.Column(name = "personId")
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    private String personName;

    @Basic
    @javax.persistence.Column(name = "personName")
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    private String visitDeptName;

    @Basic
    @javax.persistence.Column(name = "visitDeptName")
    public String getVisitDeptName() {
        return visitDeptName;
    }

    public void setVisitDeptName(String visitDeptName) {
        this.visitDeptName = visitDeptName;
    }

    private String gender;

    @Basic
    @javax.persistence.Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String idNo;

    @Basic
    @javax.persistence.Column(name = "idNo")
    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    private String phone;

    @Basic
    @javax.persistence.Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String empCode;

    @Basic
    @javax.persistence.Column(name = "empCode")
    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    private String dutyCode;

    @Basic
    @javax.persistence.Column(name = "dutyCode")
    public String getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
    }

    private String dutyName;

    @Basic
    @javax.persistence.Column(name = "dutyName")
    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    private String carType;

    @Basic
    @javax.persistence.Column(name = "carType")
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    private String carNo;

    @Basic
    @javax.persistence.Column(name = "carNo")
    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    private String regPlateNo;

    @Basic
    @javax.persistence.Column(name = "regPlateNo")
    public String getRegPlateNo() {
        return regPlateNo;
    }

    public void setRegPlateNo(String regPlateNo) {
        this.regPlateNo = regPlateNo;
    }

    private String upPlateNo;

    @Basic
    @javax.persistence.Column(name = "upPlateNo")
    public String getUpPlateNo() {
        return upPlateNo;
    }

    public void setUpPlateNo(String upPlateNo) {
        this.upPlateNo = upPlateNo;
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

//  ------------------------------------------
//  下面是 CapturePhoto 的信息
    private String firstPath;
    private String secondPath;
    private String bigPhotoName;
    private String smallPhotoName;

    @Basic
    @Column(name = "firstPath", table = "door_capture_photo")
    public String getFirstPath() {
        return firstPath;
    }

    public void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
    }

    @Basic
    @Column(name = "secondPath", table = "door_capture_photo")
    public String getSecondPath() {
        return secondPath;
    }

    public void setSecondPath(String secondPath) {
        this.secondPath = secondPath;
    }

    @Basic
    @Column(name = "bigPhotoName", table = "door_capture_photo")
    public String getBigPhotoName() {
        return bigPhotoName;
    }

    public void setBigPhotoName(String bigPhotoName) {
        this.bigPhotoName = bigPhotoName;
    }

    @Basic
    @Column(name = "smallPhotoName", table = "door_capture_photo")
    public String getSmallPhotoName() {
        return smallPhotoName;
    }

    public void setSmallPhotoName(String smallPhotoName) {
        this.smallPhotoName = smallPhotoName;
    }

}
