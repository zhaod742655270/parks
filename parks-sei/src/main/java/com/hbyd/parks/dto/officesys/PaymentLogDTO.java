package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Len on 2016/3/2.
 */


public class PaymentLogDTO extends BaseDTO {

    private String parentFK;

    private String contractNo;

    private String contractName;

    private String contractSn;

    private String userName;

    private String operateType;

    private Double contractSum;

    private Double contractSumChange;

    private Double paymentChange;

    private Double payment;

    private Double paymentNo;

    private Double paymentNoChange;

    private String date;

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getParentFK() {
        return parentFK;
    }

    public void setParentFK(String parentFK) {
        this.parentFK = parentFK;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Double getContractSumChange() {
        return contractSumChange;
    }

    public void setContractSumChange(Double contractSumChange) {
        this.contractSumChange = contractSumChange;
    }

    public String getContractSn() {
        return contractSn;
    }

    public void setContractSn(String contractSn) {
        this.contractSn = contractSn;
    }

    public Double getContractSum() {
        return contractSum;
    }

    public void setContractSum(Double contractSum) {
        this.contractSum = contractSum;
    }

    public Double getPaymentChange() {
        return paymentChange;
    }

    public void setPaymentChange(Double paymentChange) {
        this.paymentChange = paymentChange;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Double getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(Double paymentNo) {
        this.paymentNo = paymentNo;
    }

    public Double getPaymentNoChange() {
        return paymentNoChange;
    }

    public void setPaymentNoChange(Double paymentNoChange) {
        this.paymentNoChange = paymentNoChange;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}