package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Len on 2016/3/2.
 */


public class PaymentDTO extends BaseDTO {

    private int projectSn;

    private String contractNO;

    private String sheetName;

    private int contractSn;

    private String contractName;
    
    private String contractType;

    private String companyFirst;

    private String companySecond;

    private String purchaseContent;

    private String signDate;

    private String purchasePerson;

    private Double contractSum;

    private Double payment;

    private Double paymentNo;

    private String condition;

    private String note;

    private Set<ContractGatheringDTO> contractGatherings = new HashSet<>();

    private PaymentPostilDTO paymentPostil;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getProjectSn() {
        return projectSn;
    }

    public void setProjectSn(int projectSn) {
        this.projectSn = projectSn;
    }

    public String getContractNO() {
        return contractNO;
    }

    public void setContractNO(String contractNO) {
        this.contractNO = contractNO;
    }

    public int getContractSn() {
        return contractSn;
    }

    public void setContractSn(int contractSn) {
        this.contractSn = contractSn;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getCompanyFirst() {
        return companyFirst;
    }

    public void setCompanyFirst(String companyFirst) {
        this.companyFirst = companyFirst;
    }

    public String getCompanySecond() {
        return companySecond;
    }

    public void setCompanySecond(String companySecond) {
        this.companySecond = companySecond;
    }

    public String getPurchaseContent() {
        return purchaseContent;
    }

    public void setPurchaseContent(String purchaseContent) {
        this.purchaseContent = purchaseContent;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getPurchasePerson() {
        return purchasePerson;
    }

    public void setPurchasePerson(String purchasePerson) {
        this.purchasePerson = purchasePerson;
    }

    public Double getContractSum() {
        return contractSum;
    }

    public void setContractSum(Double contractSum) {
        this.contractSum = contractSum;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PaymentPostilDTO getPaymentPostil() {
        return paymentPostil;
    }

    public void setPaymentPostil(PaymentPostilDTO paymentPostil) {
        this.paymentPostil = paymentPostil;
    }

    /**
     * 付款合同拥有的收款合同（可能多个，可能没有）
     */
    public Set<ContractGatheringDTO> getContractGatherings() {
        return contractGatherings;
    }

    public void setContractGatherings(Set<ContractGatheringDTO> contractGatherings) {
        this.contractGatherings = contractGatherings;
    }
}
