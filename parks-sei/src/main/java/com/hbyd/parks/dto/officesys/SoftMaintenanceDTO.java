package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Zhao_d on 2016/12/8.
 */
public class SoftMaintenanceDTO extends BaseDTO {
    private String projectName;         //项目名称
    private String number;          //编号
    private String productName;         //产品名称
    private String regPersonID;         //登记人
    private String regPersonName;
    private String regDate;           //登记日期
    private String hopeEndDate;      //要求完成日期
    private String contractsID;         //项目联系人
    private String contractsName;
    private String phoneNo;             //联系方式
    private String faultDesc;           //故障现象

    private String result;              //最终结论
    private String resultPersonID;        //最终结论填写人
    private String resultPersonName;
    private String resultDate;          //最终结论填写日期


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getHopeEndDate() {
        return hopeEndDate;
    }

    public void setHopeEndDate(String hopeEndDate) {
        this.hopeEndDate = hopeEndDate;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    public String getRegPersonID() {
        return regPersonID;
    }

    public void setRegPersonID(String regPersonID) {
        this.regPersonID = regPersonID;
    }

    public String getRegPersonName() {
        return regPersonName;
    }

    public void setRegPersonName(String regPersonName) {
        this.regPersonName = regPersonName;
    }

    public String getContractsID() {
        return contractsID;
    }

    public void setContractsID(String contractsID) {
        this.contractsID = contractsID;
    }

    public String getContractsName() {
        return contractsName;
    }

    public void setContractsName(String contractsName) {
        this.contractsName = contractsName;
    }

    public String getResultPersonID() {
        return resultPersonID;
    }

    public void setResultPersonID(String resultPersonID) {
        this.resultPersonID = resultPersonID;
    }

    public String getResultPersonName() {
        return resultPersonName;
    }

    public void setResultPersonName(String resultPersonName) {
        this.resultPersonName = resultPersonName;
    }
}
