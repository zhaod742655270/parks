package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * 出库管理
 * Created by Zhao_d on 2017/01/18.
 */
public class WarehouseOutputDTO extends BaseDTO{
    private String number;              //出库单号
    private String outputDate;           //出库日期
    private String outputType;           //入库类型
    private String applicationID;       //申请单
    private String applicationName;
    private String warehouseID;           //仓库
    private String warehouseName;
    private String recordPersonID;        //录出人
    private String recordPersonName;
    private String recordDate;          //录出日期
    private String examinePersonID;       //审核人
    private String examinePersonName;
    private String examineDate;         //审核日期
    private String companyId;        //客户
    private String companyName;
    private String note;                //备注


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(String outputDate) {
        this.outputDate = outputDate;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getRecordPersonID() {
        return recordPersonID;
    }

    public void setRecordPersonID(String recordPersonID) {
        this.recordPersonID = recordPersonID;
    }

    public String getRecordPersonName() {
        return recordPersonName;
    }

    public void setRecordPersonName(String recordPersonName) {
        this.recordPersonName = recordPersonName;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getExaminePersonID() {
        return examinePersonID;
    }

    public void setExaminePersonID(String examinePersonID) {
        this.examinePersonID = examinePersonID;
    }

    public String getExaminePersonName() {
        return examinePersonName;
    }

    public void setExaminePersonName(String examinePersonName) {
        this.examinePersonName = examinePersonName;
    }

    public String getExamineDate() {
        return examineDate;
    }

    public void setExamineDate(String examineDate) {
        this.examineDate = examineDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
