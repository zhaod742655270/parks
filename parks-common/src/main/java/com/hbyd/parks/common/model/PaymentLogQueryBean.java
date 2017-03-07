package com.hbyd.parks.common.model;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/3/2
 */
public class PaymentLogQueryBean extends QueryBeanEasyUI {

    private String paymentID;

    private String beginDate;

    private String endDate;

    private String contractName;

    private Integer contractSn;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Integer getContractSn() {
        return contractSn;
    }

    public void setContractSn(Integer contractSn) {
        this.contractSn = contractSn;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }
}
