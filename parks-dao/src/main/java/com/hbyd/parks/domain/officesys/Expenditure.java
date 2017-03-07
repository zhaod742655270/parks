package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import com.hbyd.parks.domain.managesys.User;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Zhao_d on 2017/2/23.
 */
@Entity
@Table(name="oa_marketing_expenditure")
@Audited
public class Expenditure extends RecoverableEntity {

    @ManyToOne
    @JoinColumn(name="recordPersonFK")
    @NotAudited
    private User recordPerson;          //申请人
    private String recordDepartment;    //申请部门(默认市场部)
    @ManyToOne
    @JoinColumn(name="projectRecordFK")
    @NotAudited
    private ProjectRecord projectRecord;    //所属项目
    private String expendContent;       //项目支出内容(默认差旅)
    private Double entertainCost;       //招待费
    private Double trafficCost;         //交通费
    private Double travelCost;          //差旅费
    private Double giftCost;            //礼品费
    private Double oilCost;             //油料费
    private Double officeCost;          //办公费
    private Double teleCost;            //通讯费
    private Double travelSubsidy;       //差旅补助费
    private Double otherCost;           //其他费用
    private Double totalCost;           //费用合计
    private String examinePerson;         //批准人
    private String examineDate;         //批准日期
    private String note;                //备注

    public User getRecordPerson() {
        return recordPerson;
    }

    public void setRecordPerson(User recordPerson) {
        this.recordPerson = recordPerson;
    }

    public String getRecordDepartment() {
        return recordDepartment;
    }

    public void setRecordDepartment(String recordDepartment) {
        this.recordDepartment = recordDepartment;
    }

    public ProjectRecord getProjectRecord() {
        return projectRecord;
    }

    public void setProjectRecord(ProjectRecord projectRecord) {
        this.projectRecord = projectRecord;
    }

    public String getExpendContent() {
        return expendContent;
    }

    public void setExpendContent(String expendContent) {
        this.expendContent = expendContent;
    }

    public Double getEntertainCost() {
        return entertainCost;
    }

    public void setEntertainCost(Double entertainCost) {
        this.entertainCost = entertainCost;
    }

    public Double getTrafficCost() {
        return trafficCost;
    }

    public void setTrafficCost(Double trafficCost) {
        this.trafficCost = trafficCost;
    }

    public Double getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(Double travelCost) {
        this.travelCost = travelCost;
    }

    public Double getGiftCost() {
        return giftCost;
    }

    public void setGiftCost(Double giftCost) {
        this.giftCost = giftCost;
    }

    public Double getOilCost() {
        return oilCost;
    }

    public void setOilCost(Double oilCost) {
        this.oilCost = oilCost;
    }

    public Double getOfficeCost() {
        return officeCost;
    }

    public void setOfficeCost(Double officeCost) {
        this.officeCost = officeCost;
    }

    public Double getTeleCost() {
        return teleCost;
    }

    public void setTeleCost(Double teleCost) {
        this.teleCost = teleCost;
    }

    public Double getTravelSubsidy() {
        return travelSubsidy;
    }

    public void setTravelSubsidy(Double travelSubsidy) {
        this.travelSubsidy = travelSubsidy;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getExaminePerson() {
        return examinePerson;
    }

    public void setExaminePerson(String examinePerson) {
        this.examinePerson = examinePerson;
    }

    public String getExamineDate() {
        return examineDate;
    }

    public void setExamineDate(String examineDate) {
        this.examineDate = examineDate;
    }
}
