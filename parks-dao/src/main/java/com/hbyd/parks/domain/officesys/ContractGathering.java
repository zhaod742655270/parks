package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Len on 2016/3/2.
 */

@Entity
@Table(name = "oa_contract_gathering_new")
@Audited
public class ContractGathering extends RecoverableEntity {

    @Column(insertable = false, updatable = false)
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

    @OneToOne(mappedBy = "contractGathering" ,cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @NotAudited
    private ContractGatheringPostil contractGatheringPostil;

    @OneToMany(mappedBy = "contractGathering" ,cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @NotAudited
    private List<ContractGatheringLog> contractGatheringLog;

    @OneToMany(mappedBy = "contractGathering",fetch = FetchType.LAZY)
    @NotAudited
    private Set<Acceptance> acceptances;

    @ManyToMany(mappedBy = "contractGatherings",fetch = FetchType.LAZY)
    @NotAudited
    private List<Payment> payments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="contractFK")
    @NotAudited
    private ContractGathering contractGathering;

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

    public ContractGatheringPostil getContractGatheringPostil() {
        return contractGatheringPostil;
    }

    public void setContractGatheringPostil(ContractGatheringPostil contractGatheringPostil) {
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

    public Double getGross() {
        return gross;
    }

    public void setGross(Double gross) {
        this.gross = gross;
    }

    public List<ContractGatheringLog> getContractGatheringLog() {
        return contractGatheringLog;
    }

    public void setContractGatheringLog(List<ContractGatheringLog> contractGatheringLog) {
        this.contractGatheringLog = contractGatheringLog;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Set<Acceptance> getAcceptances() {
        return acceptances;
    }

    public void setAcceptances(Set<Acceptance> acceptances) {
        this.acceptances = acceptances;
    }

    public String getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(String acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public ContractGathering getContractGathering() {
        return contractGathering;
    }

    public void setContractGathering(ContractGathering contractGathering) {
        this.contractGathering = contractGathering;
    }
}
