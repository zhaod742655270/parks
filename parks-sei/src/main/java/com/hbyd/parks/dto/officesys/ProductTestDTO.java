package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Zhao_d on 2016/12/28.
 */
public class ProductTestDTO extends BaseDTO {
    private String productName;         //产品型号名称
    private String number;              //编号
    private String extractPosition;     //产品提取位置
    private String registerPersonID;      //登记人
    private String registerPersonName;
    private String registerDate;        //登记日期
    private String hopeEndDate;         //要求完成日期
    private String approvePersonID;       //批准人
    private String approvePersonName;
    private String approveDate;         //批准日期
    private String approveNote;         //审批日期
    private Double quantity;            //数量
    private String testBasis;            //测试依据
    private String testType;            //测试类别
    private String testDesc;            //测试功能项描述

    private String testPersonID;          //测试人
    private String testPersonName;
    private String planBegDate;         //计划开始时间
    private String planEndDate;         //计划结束时间
    private String begDate;             //实际开始时间
    private String endDate;             //实际结束时间
    private String finishedBug;         //已解决BUG编号
    private String unfinishedBug;       //未解决BUG编号
    private String finallyName;         //最终型号名称
    private String finallyPosition;     //最终提取位置
    private String summany;             //总结
    private String note;                //备注

    private String assignPersonId;      //指派处理人
    private String assignPersonName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExtractPosition() {
        return extractPosition;
    }

    public void setExtractPosition(String extractPosition) {
        this.extractPosition = extractPosition;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getHopeEndDate() {
        return hopeEndDate;
    }

    public void setHopeEndDate(String hopeEndDate) {
        this.hopeEndDate = hopeEndDate;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getTestBasis() {
        return testBasis;
    }

    public void setTestBasis(String testBasis) {
        this.testBasis = testBasis;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getTestDesc() {
        return testDesc;
    }

    public void setTestDesc(String testDesc) {
        this.testDesc = testDesc;
    }

    public String getPlanBegDate() {
        return planBegDate;
    }

    public void setPlanBegDate(String planBegDate) {
        this.planBegDate = planBegDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getBegDate() {
        return begDate;
    }

    public void setBegDate(String begDate) {
        this.begDate = begDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFinishedBug() {
        return finishedBug;
    }

    public void setFinishedBug(String finishedBug) {
        this.finishedBug = finishedBug;
    }

    public String getUnfinishedBug() {
        return unfinishedBug;
    }

    public void setUnfinishedBug(String unfinishedBug) {
        this.unfinishedBug = unfinishedBug;
    }

    public String getFinallyName() {
        return finallyName;
    }

    public void setFinallyName(String finallyName) {
        this.finallyName = finallyName;
    }

    public String getFinallyPosition() {
        return finallyPosition;
    }

    public void setFinallyPosition(String finallyPosition) {
        this.finallyPosition = finallyPosition;
    }

    public String getSummany() {
        return summany;
    }

    public void setSummany(String summany) {
        this.summany = summany;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRegisterPersonID() {
        return registerPersonID;
    }

    public void setRegisterPersonID(String registerPersonID) {
        this.registerPersonID = registerPersonID;
    }

    public String getRegisterPersonName() {
        return registerPersonName;
    }

    public void setRegisterPersonName(String registerPersonName) {
        this.registerPersonName = registerPersonName;
    }

    public String getApprovePersonID() {
        return approvePersonID;
    }

    public void setApprovePersonID(String approvePersonID) {
        this.approvePersonID = approvePersonID;
    }

    public String getApprovePersonName() {
        return approvePersonName;
    }

    public void setApprovePersonName(String approvePersonName) {
        this.approvePersonName = approvePersonName;
    }

    public String getTestPersonID() {
        return testPersonID;
    }

    public void setTestPersonID(String testPersonID) {
        this.testPersonID = testPersonID;
    }

    public String getTestPersonName() {
        return testPersonName;
    }

    public void setTestPersonName(String testPersonName) {
        this.testPersonName = testPersonName;
    }

    public String getAssignPersonId() {
        return assignPersonId;
    }

    public void setAssignPersonId(String assignPersonId) {
        this.assignPersonId = assignPersonId;
    }

    public String getAssignPersonName() {
        return assignPersonName;
    }

    public void setAssignPersonName(String assignPersonName) {
        this.assignPersonName = assignPersonName;
    }

    public String getApproveNote() {
        return approveNote;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }
}
