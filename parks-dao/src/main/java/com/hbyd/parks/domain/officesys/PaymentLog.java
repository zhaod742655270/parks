package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Len on 2016/3/2.
 */

@Entity
@Table(name = "oa_contract_paying_log")
public class PaymentLog extends BaseEntity {

    private String operateType;

    @ManyToOne
    @JoinColumn(name = "parentFK", referencedColumnName = "id")
    private Payment contractPayment;

    private String userName;

    private String date;

    private Double contractSum;

    private Double contractSumChange;

    private Double payment;

    private Double paymentChange;

    private Double paymentNo;

    private Double paymentNoChange;

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Payment getContractPayment() {
        return contractPayment;
    }

    public void setContractPayment(Payment contractPayment) {
        this.contractPayment = contractPayment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getContractSum() {
        return contractSum;
    }

    public void setContractSum(Double contractSum) {
        this.contractSum = contractSum;
    }

    public Double getContractSumChange() {
        return contractSumChange;
    }

    public void setContractSumChange(Double contractSumChange) {
        this.contractSumChange = contractSumChange;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Double getPaymentChange() {
        return paymentChange;
    }

    public void setPaymentChange(Double paymentChange) {
        this.paymentChange = paymentChange;
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
