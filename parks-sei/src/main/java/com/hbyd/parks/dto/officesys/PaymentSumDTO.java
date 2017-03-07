package com.hbyd.parks.dto.officesys;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/3/17
 */
public class PaymentSumDTO {

    private Long count;

    private Double amountSum;

    private Double paymentSum;

    private Double paymentNoSum;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getAmountSum() {
        return amountSum;
    }

    public void setAmountSum(Double amountSum) {
        this.amountSum = amountSum;
    }

    public Double getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(Double paymentSum) {
        this.paymentSum = paymentSum;
    }

    public Double getPaymentNoSum() {
        return paymentNoSum;
    }

    public void setPaymentNoSum(Double paymentNoSum) {
        this.paymentNoSum = paymentNoSum;
    }
}
