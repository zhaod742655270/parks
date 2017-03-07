package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;

import javax.persistence.*;

/**
 * Created by Len on 2016/3/2.
 */

@Entity
@Table(name = "oa_contract_gathering_log")
public class ContractGatheringLog extends RecoverableEntity {

    private String operateType;


    @ManyToOne
    @JoinColumn(name = "gatheringFK", referencedColumnName = "id")
    private ContractGathering contractGathering;

    private String userName;

    private String amount;

    private String amountChange;

    private String received;

    private String receivedChange;

    private String receiveNo;

    private String receiveNoChange;

    private String oncreditChange;

    private String oncredit;

    private String remainChange;

    private String remain;

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


    public ContractGathering getContractGathering() {
        return contractGathering;
    }

    public void setContractGathering(ContractGathering contractGathering) {
        this.contractGathering = contractGathering;
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

    public String getAmountChange() {
        return amountChange;
    }

    public void setAmountChange(String amountChange) {
        this.amountChange = amountChange;
    }

    public String getReceivedChange() {
        return receivedChange;
    }

    public void setReceivedChange(String receivedChange) {
        this.receivedChange = receivedChange;
    }

    public String getReceiveNoChange() {
        return receiveNoChange;
    }

    public void setReceiveNoChange(String receiveNoChange) {
        this.receiveNoChange = receiveNoChange;
    }

    public String getOncreditChange() {
        return oncreditChange;
    }

    public void setOncreditChange(String oncreditChange) {
        this.oncreditChange = oncreditChange;
    }

    public String getRemainChange() {
        return remainChange;
    }

    public void setRemainChange(String remainChange) {
        this.remainChange = remainChange;
    }

}
