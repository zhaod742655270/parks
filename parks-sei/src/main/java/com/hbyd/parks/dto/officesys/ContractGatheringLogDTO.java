package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Len on 2016/3/2.
 */


public class ContractGatheringLogDTO extends BaseDTO {


    private String gatheringFK;

    private String contractNo;

    private String userName;

    private String operateType;

    private String amount;

    private Double amountChange;

    private Double receivedChange;

    private String received;

    private String receiveNo;

    private Double receiveNoChange;

    private String oncredit;

    private Double oncreditChange;

    private String remain;

    private Double remainChange;

    private String date;

    private String gross;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getReceiveNo() {
        return receiveNo;
    }

    public void setReceiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
    }

    public String getOncredit() {
        return oncredit;
    }

    public void setOncredit(String oncredit) {
        this.oncredit = oncredit;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }


    public String getGatheringFK() {
        return gatheringFK;
    }

    public void setGatheringFK(String gatheringFK) {
        this.gatheringFK = gatheringFK;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGross() {
        return gross;
    }

    public void setGross(String gross) {
        this.gross = gross;
    }

    public Double getAmountChange() {
        return amountChange;
    }

    public void setAmountChange(Double amountChange) {
        this.amountChange = amountChange;
    }

    public Double getReceivedChange() {
        return receivedChange;
    }

    public void setReceivedChange(Double receivedChange) {
        this.receivedChange = receivedChange;
    }

    public Double getReceiveNoChange() {
        return receiveNoChange;
    }

    public void setReceiveNoChange(Double receiveNoChange) {
        this.receiveNoChange = receiveNoChange;
    }

    public Double getOncreditChange() {
        return oncreditChange;
    }

    public void setOncreditChange(Double oncreditChange) {
        this.oncreditChange = oncreditChange;
    }

    public Double getRemainChange() {
        return remainChange;
    }

    public void setRemainChange(Double remainChange) {
        this.remainChange = remainChange;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
}