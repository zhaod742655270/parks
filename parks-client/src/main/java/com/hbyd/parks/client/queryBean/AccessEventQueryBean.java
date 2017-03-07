package com.hbyd.parks.client.queryBean;

import com.hbyd.parks.common.model.QueryBeanEasyUI;

/**
 * 刷卡事件的查询 Bean, 用于封装查询参数, 为了方便分页，该 Bean 继承自 QueryBeanEasyUI
 */
public class AccessEventQueryBean extends QueryBeanEasyUI{
    private boolean deptNameCheck;
    private String deptName;

    private boolean doorNameCheck;
    private String doorName;
    private boolean personNameCheck;
    private String personName;
    private boolean cardNoCheck;
    private String cardNo;

    private String recordType;
    private String deviceType;
    private boolean plateNoCheck;
    private String plateNo;
    private boolean deptIdCheck;
    private String deptId;

    private String beginTime;
    private String endTime;

    private String passId;
    private String roomId;

    public boolean isDeptNameCheck() {
        return deptNameCheck;
    }

    public void setDeptNameCheck(boolean deptNameCheck) {
        this.deptNameCheck = deptNameCheck;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public boolean isDoorNameCheck() {
        return doorNameCheck;
    }

    public void setDoorNameCheck(boolean doorNameCheck) {
        this.doorNameCheck = doorNameCheck;
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public boolean isPersonNameCheck() {
        return personNameCheck;
    }

    public void setPersonNameCheck(boolean personNameCheck) {
        this.personNameCheck = personNameCheck;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public boolean isCardNoCheck() {
        return cardNoCheck;
    }

    public void setCardNoCheck(boolean cardNoCheck) {
        this.cardNoCheck = cardNoCheck;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public boolean isPlateNoCheck() {
        return plateNoCheck;
    }

    public void setPlateNoCheck(boolean plateNoCheck) {
        this.plateNoCheck = plateNoCheck;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public boolean isDeptIdCheck() {
        return deptIdCheck;
    }

    public void setDeptIdCheck(boolean deptIdCheck) {
        this.deptIdCheck = deptIdCheck;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
