package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/4/25
 */
@Entity
@Table(name = "oa_marketing_projectRecord")
@Audited
public class ProjectRecord extends RecoverableEntity {

    private String recordSN;

    private String sheetName;

    private String name;

    private String contents;

    private String place;

    private Double budget;

    private String employer;

    private String connection;

    private String manager;

    private String recordDate;

    private String biddingDate;

    private Integer bidding;

    private Integer winBidding;

    private Double amount;

    private String bidSubject;

    private String bidBond;

    private String note;

    private String type;

    public String getRecordSN() {
        return recordSN;
    }

    public void setRecordSN(String recordSN) {
        this.recordSN = recordSN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getBiddingDate() {
        return biddingDate;
    }

    public void setBiddingDate(String biddingDate) {
        this.biddingDate = biddingDate;
    }

    public Integer getBidding() {
        return bidding;
    }

    public void setBidding(Integer bidding) {
        this.bidding = bidding;
    }

    public Integer getWinBidding() {
        return winBidding;
    }

    public void setWinBidding(Integer winBidding) {
        this.winBidding = winBidding;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBidSubject() {
        return bidSubject;
    }

    public void setBidSubject(String bidSubject) {
        this.bidSubject = bidSubject;
    }

    public String getBidBond() {
        return bidBond;
    }

    public void setBidBond(String bidBond) {
        this.bidBond = bidBond;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
