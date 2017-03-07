package com.hbyd.parks.dto.doorsys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * 刷卡事件查询
 */
public class AccessEventInfoDTO extends BaseDTO {

//  下面是 CapturePhoto 需要展示的字段
    private String firstPath;
    private String secondPath;
    private String bigPhotoName;
    private String smallPhotoName;
    private String bigPhotoPath;
    private String smallPhotoPath;
    private String existsBigPhoto;
    private String existsSmallPhoto;
    public String getFirstPath() {
        return firstPath;
    }

    public void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
    }

    public String getSecondPath() {
        return secondPath;
    }

    public void setSecondPath(String secondPath) {
        this.secondPath = secondPath;
    }

    public String getBigPhotoName() {
        return bigPhotoName;
    }
    public void setBigPhotoName(String bigPhotoName) {
        this.bigPhotoName = bigPhotoName;
    }

    public String getSmallPhotoName(){
        return smallPhotoName;
    }
    public void setSmallPhotoName(String smallPhotoName) {
        this.smallPhotoName = smallPhotoName;
    }

    public String getBigPhotoPath() {
        String url="";
        if(firstPath!=null && secondPath!=null &&bigPhotoName!=null)
        {
            url=firstPath+"/"+secondPath+"/"+bigPhotoName;
        }

        return url;
    }
    public void setBigPhotoPath(String bigPhotoPath) {
        this.bigPhotoPath = bigPhotoPath;
    }

    public String getSmallPhotoPath() {
        String url="";
        if(firstPath!=null && secondPath!=null &&smallPhotoName!=null)
        {
            url=firstPath+"/"+secondPath+"/"+smallPhotoName;
        }

        return url;
    }

    public void setSmallPhotoPath(String smallPhotoPath) {
        this.smallPhotoPath = smallPhotoPath;
    }


    public String getExistsBigPhoto() {

        return getBigPhotoPath()==""?"否":"是";
    }
    public void setExistsBigPhoto(String existsBigPhoto) {
        this.existsBigPhoto = existsBigPhoto;
    }

    public String getExistsSmallPhoto() {

        return getSmallPhotoPath()==""?"否":"是";
    }
    public void setExistsSmallPhoto(String existsBigPhoto) {
        this.existsSmallPhoto = existsBigPhoto;
    }
    //  下面是 AccessEventInfo 需要展示的字段
    private String eId;
    private String passId;
    private String passSn;
    private String ioType;
    private String eventTime;
    private String upTime;
    private String capPhotoSn;
    private String devType;
    private String devUUID;
    private String devId;
    private String devDesc;
    private String doorSn;
    private String doorName;
    private String devWorkMode;
    private String devIdentifyMode;
    private String operMode;
    private String identifyResult;
    private Boolean isValidPass;
    private String isValidPassDes;
    private Boolean isTriggerPass;
    private String isTriggerPassDes;
    private String cardProp;
    private String cardNo;
    private String deptId;
    private String deptName;
    private String personType;
    private String personId;
    private String personName;
    private String visitDeptName;
    private String gender;
    private String idNo;
    private String phone;
    private String empCode;
    private String dutyCode;
    private String dutyName;
    private String carType;
    private String carNo;
    private String regPlateNo;
    private String upPlateNo;
    private String carTime;
    private String carId;
    private String plateNo;
    private String cardPropDes;

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
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

        String ioTypeDescription = "";
        switch (ioType) {
            case "0":
                ioTypeDescription = "进门";
                break;

            case "1":
                ioTypeDescription = "出门";
                break;

        }
        return ioTypeDescription;
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

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getDevDesc() {
        return devDesc;
    }

    public void setDevDesc(String devDesc) {
        this.devDesc = devDesc;
    }

    public String getDoorSn() {
        return doorSn;
    }

    public void setDoorSn(String doorSn) {
        this.doorSn = doorSn;
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
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
        String eResultDescription = "";
        switch (identifyResult) {
            case "0":
                eResultDescription = "操作有效";
                break;
            case "1":
                eResultDescription = getCarType() == null ? "卡无效" : "人卡过期";
                break;
            case "2":
                eResultDescription = getCarType() == null ? "密码无效" : "车牌过期";
                break;
            case "3":
                eResultDescription = getCarType() == null ? "卡与密码无效" : "车卡过期";
                break;
            case "4":
                eResultDescription = getCarType() == null ? "有效期无效" : "无此人卡";
                break;
            case "5":
                eResultDescription = getCarType() == null ? "权限无效" : "无此车卡";
                break;
            case "6":
                eResultDescription = getCarType() == null ? "时间表无效" : "无此车牌";
                break;
            case "7":
                eResultDescription = "时区表无效";
                break;
            case "8":
                eResultDescription = "常关有效";
                break;
            case "9":
                eResultDescription = "常开有效";
                break;
            case "10":
                eResultDescription = "远程开有效";
                break;
            case "11":
                eResultDescription = "模式不匹配";
                break;
            case "12":
                eResultDescription = "用户ID无效";
                break;
            case "255":
                eResultDescription = getCarType() == null ? "未定义" : "事件非法";
                break;
        }
        return eResultDescription;
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

    public String getCardProp() {
        return cardProp;
    }

    public void setCardProp(String cardProp) {
        this.cardProp = cardProp;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getVisitDeptName() {
        return visitDeptName;
    }

    public void setVisitDeptName(String visitDeptName) {
        this.visitDeptName = visitDeptName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getRegPlateNo() {
        return regPlateNo;
    }

    public void setRegPlateNo(String regPlateNo) {
        this.regPlateNo = regPlateNo;
    }

    public String getUpPlateNo() {
        return upPlateNo;
    }

    public void setUpPlateNo(String upPlateNo) {
        this.upPlateNo = upPlateNo;
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

    public String getPlateNo() {
        String result = "";
        if (getUpPlateNo() != null) result = getUpPlateNo();
        else if (getRegPlateNo() != null) result = getRegPlateNo();
        return result;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getIsValidPassDes() {
        String des = isValidPass ? "是" : "否";
        return des;
    }

    public void setIsValidPassDes(String isValidPassDes) {
        this.isValidPassDes = isValidPassDes;
    }

    public String getIsTriggerPassDes() {
        String des = isTriggerPass ? "是" : "否";
        return des;
    }

    public void setIsTriggerPassDes(String isTriggerPassDes) {
        this.isTriggerPassDes = isTriggerPassDes;
    }

    public String getCardPropDes() {
        String des="";
        switch (cardProp){
            case "0":
                des="人卡";
                break;
            case "1":
                des="车卡";
                break;
            case "3":
                des="车牌";
                break;
        }

        return des;
    }

    public void setCardPropDes(String cardPropDes) {
        this.cardPropDes = cardPropDes;
    }


    public String getDevUUID() {
        return devUUID;
    }

    public void setDevUUID(String devUUID) {
        this.devUUID = devUUID;
    }
}
