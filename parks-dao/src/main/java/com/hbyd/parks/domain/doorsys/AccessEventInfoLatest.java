package com.hbyd.parks.domain.doorsys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 反映人车最新状态的实体
 */
@Entity
@javax.persistence.Table(name = "door_access_event_info_latest", schema = "dbo", catalog = "parks")
@Access(AccessType.PROPERTY)
public class AccessEventInfoLatest extends BaseEntity {

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

    private Boolean personOrCarFlag;

    @Basic
    @javax.persistence.Column(name = "personOrCarFlag")
    public Boolean getPersonOrCarFlag() {
        return personOrCarFlag;
    }

    public void setPersonOrCarFlag(Boolean personOrCarFlag) {
        this.personOrCarFlag = personOrCarFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessEventInfoLatest that = (AccessEventInfoLatest) o;

        if (capPhotoSn != null ? !capPhotoSn.equals(that.capPhotoSn) : that.capPhotoSn != null) return false;
        if (carId != null ? !carId.equals(that.carId) : that.carId != null) return false;
        if (carNo != null ? !carNo.equals(that.carNo) : that.carNo != null) return false;
        if (carTime != null ? !carTime.equals(that.carTime) : that.carTime != null) return false;
        if (carType != null ? !carType.equals(that.carType) : that.carType != null) return false;
        if (cardNo != null ? !cardNo.equals(that.cardNo) : that.cardNo != null) return false;
        if (cardProp != null ? !cardProp.equals(that.cardProp) : that.cardProp != null) return false;
        if (deptId != null ? !deptId.equals(that.deptId) : that.deptId != null) return false;
        if (deptName != null ? !deptName.equals(that.deptName) : that.deptName != null) return false;
        if (devDesc != null ? !devDesc.equals(that.devDesc) : that.devDesc != null) return false;
        if (devId != null ? !devId.equals(that.devId) : that.devId != null) return false;
        if (devIdentifyMode != null ? !devIdentifyMode.equals(that.devIdentifyMode) : that.devIdentifyMode != null)
            return false;
        if (devType != null ? !devType.equals(that.devType) : that.devType != null) return false;
        if (devWorkMode != null ? !devWorkMode.equals(that.devWorkMode) : that.devWorkMode != null) return false;
        if (doorName != null ? !doorName.equals(that.doorName) : that.doorName != null) return false;
        if (doorSn != null ? !doorSn.equals(that.doorSn) : that.doorSn != null) return false;
        if (dutyCode != null ? !dutyCode.equals(that.dutyCode) : that.dutyCode != null) return false;
        if (dutyName != null ? !dutyName.equals(that.dutyName) : that.dutyName != null) return false;
        if (eId != null ? !eId.equals(that.eId) : that.eId != null) return false;
        if (empCode != null ? !empCode.equals(that.empCode) : that.empCode != null) return false;
        if (eventTime != null ? !eventTime.equals(that.eventTime) : that.eventTime != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (idNo != null ? !idNo.equals(that.idNo) : that.idNo != null) return false;
        if (identifyResult != null ? !identifyResult.equals(that.identifyResult) : that.identifyResult != null)
            return false;
        if (ioType != null ? !ioType.equals(that.ioType) : that.ioType != null) return false;
        if (isTriggerPass != null ? !isTriggerPass.equals(that.isTriggerPass) : that.isTriggerPass != null)
            return false;
        if (isValidPass != null ? !isValidPass.equals(that.isValidPass) : that.isValidPass != null) return false;
        if (operMode != null ? !operMode.equals(that.operMode) : that.operMode != null) return false;
        if (passId != null ? !passId.equals(that.passId) : that.passId != null) return false;
        if (passSn != null ? !passSn.equals(that.passSn) : that.passSn != null) return false;
        if (personId != null ? !personId.equals(that.personId) : that.personId != null) return false;
        if (personName != null ? !personName.equals(that.personName) : that.personName != null) return false;
        if (personOrCarFlag != null ? !personOrCarFlag.equals(that.personOrCarFlag) : that.personOrCarFlag != null)
            return false;
        if (personType != null ? !personType.equals(that.personType) : that.personType != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (regPlateNo != null ? !regPlateNo.equals(that.regPlateNo) : that.regPlateNo != null) return false;
        if (upPlateNo != null ? !upPlateNo.equals(that.upPlateNo) : that.upPlateNo != null) return false;
        if (upTime != null ? !upTime.equals(that.upTime) : that.upTime != null) return false;
        if (visitDeptName != null ? !visitDeptName.equals(that.visitDeptName) : that.visitDeptName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (eId != null ? eId.hashCode() : 0);
        result = 31 * result + (passId != null ? passId.hashCode() : 0);
        result = 31 * result + (passSn != null ? passSn.hashCode() : 0);
        result = 31 * result + (ioType != null ? ioType.hashCode() : 0);
        result = 31 * result + (eventTime != null ? eventTime.hashCode() : 0);
        result = 31 * result + (upTime != null ? upTime.hashCode() : 0);
        result = 31 * result + (capPhotoSn != null ? capPhotoSn.hashCode() : 0);
        result = 31 * result + (devType != null ? devType.hashCode() : 0);
        result = 31 * result + (devId != null ? devId.hashCode() : 0);
        result = 31 * result + (devDesc != null ? devDesc.hashCode() : 0);
        result = 31 * result + (doorSn != null ? doorSn.hashCode() : 0);
        result = 31 * result + (doorName != null ? doorName.hashCode() : 0);
        result = 31 * result + (devWorkMode != null ? devWorkMode.hashCode() : 0);
        result = 31 * result + (devIdentifyMode != null ? devIdentifyMode.hashCode() : 0);
        result = 31 * result + (operMode != null ? operMode.hashCode() : 0);
        result = 31 * result + (identifyResult != null ? identifyResult.hashCode() : 0);
        result = 31 * result + (isValidPass != null ? isValidPass.hashCode() : 0);
        result = 31 * result + (isTriggerPass != null ? isTriggerPass.hashCode() : 0);
        result = 31 * result + (cardProp != null ? cardProp.hashCode() : 0);
        result = 31 * result + (cardNo != null ? cardNo.hashCode() : 0);
        result = 31 * result + (deptId != null ? deptId.hashCode() : 0);
        result = 31 * result + (deptName != null ? deptName.hashCode() : 0);
        result = 31 * result + (personType != null ? personType.hashCode() : 0);
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        result = 31 * result + (personName != null ? personName.hashCode() : 0);
        result = 31 * result + (visitDeptName != null ? visitDeptName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (idNo != null ? idNo.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (empCode != null ? empCode.hashCode() : 0);
        result = 31 * result + (dutyCode != null ? dutyCode.hashCode() : 0);
        result = 31 * result + (dutyName != null ? dutyName.hashCode() : 0);
        result = 31 * result + (carType != null ? carType.hashCode() : 0);
        result = 31 * result + (carNo != null ? carNo.hashCode() : 0);
        result = 31 * result + (regPlateNo != null ? regPlateNo.hashCode() : 0);
        result = 31 * result + (upPlateNo != null ? upPlateNo.hashCode() : 0);
        result = 31 * result + (carTime != null ? carTime.hashCode() : 0);
        result = 31 * result + (carId != null ? carId.hashCode() : 0);
        result = 31 * result + (personOrCarFlag != null ? personOrCarFlag.hashCode() : 0);
        return result;
    }
}
