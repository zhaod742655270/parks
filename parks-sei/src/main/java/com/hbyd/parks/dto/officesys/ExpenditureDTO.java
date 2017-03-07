package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Zhao_d on 2017/2/23.
 */
public class ExpenditureDTO extends BaseDTO {
    private String recordPersonId;          //申请人
    private String recordPersonName;
    private String recordDepartment;    //申请部门(默认市场部)
    private String projectRecordId;    //项目名称
    private String projectRecordName;
    private String expendContent;       //项目支出内容(默认差旅)
    private Double entertainCost;       //招待费
    private Double trafficCost;         //交通费
    private Double travelCost;          //差旅费
    private Double giftCost;            //礼品费
    private Double oilCost;             //油料费
    private Double officeCost;          //办公费
    private Double teleCost;            //通讯费
    private Double travelSubsidy;       //差旅补助费
    private Double otherCost;           //其他费用
    private Double totalCost;           //费用合计
    private String examinePerson;         //审核人
    private String examineDate;         //审核日期
    private String note;                //备注

    public String getRecordPersonId() {
        return recordPersonId;
    }

    public void setRecordPersonId(String recordPersonId) {
        this.recordPersonId = recordPersonId;
    }

    public String getRecordPersonName() {
        return recordPersonName;
    }

    public void setRecordPersonName(String recordPersonName) {
        this.recordPersonName = recordPersonName;
    }

    public String getRecordDepartment() {
        return recordDepartment;
    }

    public void setRecordDepartment(String recordDepartment) {
        this.recordDepartment = recordDepartment;
    }

    public String getProjectRecordId() {
        return projectRecordId;
    }

    public void setProjectRecordId(String projectRecordId) {
        this.projectRecordId = projectRecordId;
    }

    public String getProjectRecordName() {
        return projectRecordName;
    }

    public void setProjectRecordName(String projectRecordName) {
        this.projectRecordName = projectRecordName;
    }

    public String getExpendContent() {
        return expendContent;
    }

    public void setExpendContent(String expendContent) {
        this.expendContent = expendContent;
    }

    public Double getEntertainCost() {
        return entertainCost;
    }

    public void setEntertainCost(Double entertainCost) {
        this.entertainCost = entertainCost;
    }

    public Double getTrafficCost() {
        return trafficCost;
    }

    public void setTrafficCost(Double trafficCost) {
        this.trafficCost = trafficCost;
    }

    public Double getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(Double travelCost) {
        this.travelCost = travelCost;
    }

    public Double getGiftCost() {
        return giftCost;
    }

    public void setGiftCost(Double giftCost) {
        this.giftCost = giftCost;
    }

    public Double getOilCost() {
        return oilCost;
    }

    public void setOilCost(Double oilCost) {
        this.oilCost = oilCost;
    }

    public Double getOfficeCost() {
        return officeCost;
    }

    public void setOfficeCost(Double officeCost) {
        this.officeCost = officeCost;
    }

    public Double getTeleCost() {
        return teleCost;
    }

    public void setTeleCost(Double teleCost) {
        this.teleCost = teleCost;
    }

    public Double getTravelSubsidy() {
        return travelSubsidy;
    }

    public void setTravelSubsidy(Double travelSubsidy) {
        this.travelSubsidy = travelSubsidy;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getExamineDate() {
        return examineDate;
    }

    public void setExamineDate(String examineDate) {
        this.examineDate = examineDate;
    }

    public String getExaminePerson() {
        return examinePerson;
    }

    public void setExaminePerson(String examinePerson) {
        this.examinePerson = examinePerson;
    }
}
