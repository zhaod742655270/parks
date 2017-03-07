package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Len on 2016/3/2.
 */

@Entity
@Table(name = "oa_contract_paying")
@Audited
public class Payment extends RecoverableEntity {

    @Column(insertable = false, updatable = false)
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


    @OneToOne(mappedBy = "payment" ,cascade = CascadeType.DETACH)
    @NotAudited
    private PaymentPostil paymentPostil;

    @OneToMany(mappedBy = "contractPayment" ,cascade = CascadeType.DETACH)
    @NotAudited
    private List<PaymentLog> paymentLogs;



    /**
     * 收款合同拥有的付款合同
     */
    @ManyToMany
    @JoinTable(name = "oa_contract_gathering_paying", joinColumns = {
            @JoinColumn(name = "payingFK", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "gatheringFK", referencedColumnName = "id")
    })
    @NotAudited
    private Set<ContractGathering> contractGatherings = new HashSet<ContractGathering>();

    /*protected boolean isValid = true;//默认为有效数据,保存/更新服务均不涉及此字段，只有 delFake 会修改该字段

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }*/

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

    public PaymentPostil getPaymentPostil() {
        return paymentPostil;
    }

    public void setPaymentPostil(PaymentPostil paymentPostil) {
        this.paymentPostil = paymentPostil;
    }

    public List<PaymentLog> getPaymentLogs() {
        return paymentLogs;
    }

    public void setPaymentLogs(List<PaymentLog> paymentLogs) {
        this.paymentLogs = paymentLogs;
    }

    public Set<ContractGathering> getContractGatherings() {
        return contractGatherings;
    }

    public void setContractGatherings(Set<ContractGathering> contractGatherings) {
        this.contractGatherings = contractGatherings;
    }

}
