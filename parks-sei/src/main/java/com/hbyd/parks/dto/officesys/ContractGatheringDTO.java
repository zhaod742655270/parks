package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

import java.util.List;

/**
 * Created by Len on 2016/3/2.
 */


public class ContractGatheringDTO extends BaseDTO {

    private int projectSn;

    private String sheetName;

    private String projectType;

    private String contractNoYD;

    private String contractNo;

    private String contractName;

    private String companyFirst;

    private String companySecond;

    private String signDate;

    private String acceptanceDate;

    private Double amount;

    private Double received;

    private Double receiveNo;

    private Double oncredit;

    private Double remain;

    private Double gross;

    private String projectManager;

    private String projectDirector;

    private String note;

    private Integer stamp;

    private Integer isCompleted;

    private ContractGatheringPostilDTO contractGatheringPostil;

    private List<PaymentDTO> paymentDTOList;

    public int getProjectSn() {
        return projectSn;
    }

    public void setProjectSn(int projectSn) {
        this.projectSn = projectSn;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getContractNoYD() {
        return contractNoYD;
    }

    public void setContractNoYD(String contractNoYD) {
        this.contractNoYD = contractNoYD;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
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

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public Double getGross() {
        return gross;
    }

    public void setGross(Double gross) {
        this.gross = gross;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProjectDirector() {
        return projectDirector;
    }

    public void setProjectDirector(String projectDirector) {
        this.projectDirector = projectDirector;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public ContractGatheringPostilDTO getContractGatheringPostil() {
        return contractGatheringPostil;
    }

    public void setContractGatheringPostil(ContractGatheringPostilDTO contractGatheringPostil) {
        this.contractGatheringPostil = contractGatheringPostil;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getReceived() {
        return received;
    }

    public void setReceived(Double received) {
        this.received = received;
    }

    public Double getReceiveNo() {
        return receiveNo;
    }

    public void setReceiveNo(Double receiveNo) {
        this.receiveNo = receiveNo;
    }

    public Double getOncredit() {
        return oncredit;
    }

    public void setOncredit(Double oncredit) {
        this.oncredit = oncredit;
    }

    public Double getRemain() {
        return remain;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }

    public Integer getStamp() {
        return stamp;
    }

    public void setStamp(Integer stamp) {
        this.stamp = stamp;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public List<PaymentDTO> getPaymentDTOList() {
        return paymentDTOList;
    }

    public void setPaymentDTOList(List<PaymentDTO> paymentDTOList) {
        this.paymentDTOList = paymentDTOList;
    }

    public String getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(String acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }
}
