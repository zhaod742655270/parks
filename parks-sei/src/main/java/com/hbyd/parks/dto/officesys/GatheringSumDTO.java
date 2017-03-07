package com.hbyd.parks.dto.officesys;

/**
 * Created by Len on 2016/3/2.
 */


public class GatheringSumDTO {
    //总条数
    private Long count;

    private Double amountSum;

    private Double receivedSum;

    private Double receiveNoSum;

    private Double oncreditSum;

    private Double remainSum;

    private Double grossSum;

    public Double getAmountSum() {
        return amountSum;
    }

    public void setAmountSum(Double amountSum) {
        this.amountSum = amountSum;
    }

    public Double getReceivedSum() {
        return receivedSum;
    }

    public void setReceivedSum(Double receivedSum) {
        this.receivedSum = receivedSum;
    }

    public Double getReceiveNoSum() {
        return receiveNoSum;
    }

    public void setReceiveNoSum(Double receiveNoSum) {
        this.receiveNoSum = receiveNoSum;
    }

    public Double getOncreditSum() {
        return oncreditSum;
    }

    public void setOncreditSum(Double oncreditSum) {
        this.oncreditSum = oncreditSum;
    }

    public Double getRemainSum() {
        return remainSum;
    }

    public void setRemainSum(Double remainSum) {
        this.remainSum = remainSum;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getGrossSum() {
        return grossSum;
    }

    public void setGrossSum(Double grossSum) {
        this.grossSum = grossSum;
    }
}
