package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.BaseEntity;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

/**
 * Created by Len on 2016/3/2.
 */
@Entity
@Table(name = "oa_contract_gathering_postil")
@Audited
public class ContractGatheringPostil extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "gatheringFK")
    @NotAudited
    private ContractGathering contractGathering;

    private int projectSn;

    private String contractNoYDPostil;

    private String contractNoPostil;

    private String contractNamePostil;

    private String companyFirstPostil;

    private String companySecondPostil;

    private String signDatePostil;

    private String amountPostil;

    private String receivedPostil;

    private String receiveNoPostil;

    private String oncreditPostil;

    private String remainPostil;

    private String projectManagerPostil;

    private String projectDirectorPostil;

    private String notePostil;

    private String receptionDatePostil;

    private String partnerPostil;

    private String grossPostil;

    public ContractGathering getContractGathering() {
        return contractGathering;
    }

    public void setContractGathering(ContractGathering contractGathering) {
        this.contractGathering = contractGathering;
    }

    public int getProjectSn() {
        return projectSn;
    }

    public void setProjectSn(int projectSn) {
        this.projectSn = projectSn;
    }

    public String getContractNoYDPostil() {
        return contractNoYDPostil;
    }

    public void setContractNoYDPostil(String contractNoYDPostil) {
        this.contractNoYDPostil = contractNoYDPostil;
    }

    public String getContractNoPostil() {
        return contractNoPostil;
    }

    public void setContractNoPostil(String contractNoPostil) {
        this.contractNoPostil = contractNoPostil;
    }

    public String getContractNamePostil() {
        return contractNamePostil;
    }

    public void setContractNamePostil(String contractNamePostil) {
        this.contractNamePostil = contractNamePostil;
    }

    public String getCompanyFirstPostil() {
        return companyFirstPostil;
    }

    public void setCompanyFirstPostil(String companyFirstPostil) {
        this.companyFirstPostil = companyFirstPostil;
    }

    public String getCompanySecondPostil() {
        return companySecondPostil;
    }

    public void setCompanySecondPostil(String companySecondPostil) {
        this.companySecondPostil = companySecondPostil;
    }

    public String getSignDatePostil() {
        return signDatePostil;
    }

    public void setSignDatePostil(String signDatePostil) {
        this.signDatePostil = signDatePostil;
    }

    public String getAmountPostil() {
        return amountPostil;
    }

    public void setAmountPostil(String amountPostil) {
        this.amountPostil = amountPostil;
    }

    public String getReceivedPostil() {
        return receivedPostil;
    }

    public void setReceivedPostil(String receivedPostil) {
        this.receivedPostil = receivedPostil;
    }

    public String getReceiveNoPostil() {
        return receiveNoPostil;
    }

    public void setReceiveNoPostil(String receiveNoPostil) {
        this.receiveNoPostil = receiveNoPostil;
    }

    public String getOncreditPostil() {
        return oncreditPostil;
    }

    public void setOncreditPostil(String oncreditPostil) {
        this.oncreditPostil = oncreditPostil;
    }

    public String getRemainPostil() {
        return remainPostil;
    }

    public void setRemainPostil(String remainPostil) {
        this.remainPostil = remainPostil;
    }

    public String getProjectManagerPostil() {
        return projectManagerPostil;
    }

    public void setProjectManagerPostil(String projectManagerPostil) {
        this.projectManagerPostil = projectManagerPostil;
    }

    public String getProjectDirectorPostil() {
        return projectDirectorPostil;
    }

    public void setProjectDirectorPostil(String projectDirectorPostil) {
        this.projectDirectorPostil = projectDirectorPostil;
    }

    public String getNotePostil() {
        return notePostil;
    }

    public void setNotePostil(String notePostil) {
        this.notePostil = notePostil;
    }

    public String getReceptionDatePostil() {
        return receptionDatePostil;
    }

    public void setReceptionDatePostil(String receptionDatePostil) {
        this.receptionDatePostil = receptionDatePostil;
    }

    public String getPartnerPostil() {
        return partnerPostil;
    }

    public void setPartnerPostil(String partnerPostil) {
        this.partnerPostil = partnerPostil;
    }

    public String getGrossPostil() {
        return grossPostil;
    }

    public void setGrossPostil(String grossPostil) {
        this.grossPostil = grossPostil;
    }
}
