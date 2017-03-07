package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import com.hbyd.parks.domain.managesys.User;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.List;

/**
 * 出库管理
 * Created by Zhao_d on 2016/12/26.
 */
@Entity
@Table(name = "oa_warehouse_output")
@Audited
public class WarehouseOutput extends RecoverableEntity {
    private String number;              //出库单号
    private String outputDate;           //出库日期
    private String outputType;           //出库类型

    @ManyToOne
    @JoinColumn(name="warehouseApplyFK")
    @NotAudited
    private WarehouseApplication warehouseApplication;    //申请单

    @ManyToOne
    @JoinColumn(name="warehouseTypeFK")
    @NotAudited
    private WarehouseInfo warehouse;           //仓库

    @ManyToOne
    @JoinColumn(name="recordPersonFK")
    @NotAudited
    private User recordPerson;        //录入人

    private String recordDate;          //录入日期

    @ManyToOne
    @JoinColumn(name="examinePersonFK")
    @NotAudited
    private User examinePerson;       //审核人

    private String examineDate;         //审核日期
    @ManyToOne
    @JoinColumn(name="companyIdFK")
    @NotAudited
    private WarehouseCompanyOut company;        //供应商

    private String note;                //备注

    @OneToMany(mappedBy = "warehouseOutput")
    @NotAudited
    private List<WarehouseOutputPro> warehouseOutputPro;        //出库货品

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(String outputDate) {
        this.outputDate = outputDate;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public WarehouseInfo getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseInfo warehouse) {
        this.warehouse = warehouse;
    }

    public User getRecordPerson() {
        return recordPerson;
    }

    public void setRecordPerson(User recordPerson) {
        this.recordPerson = recordPerson;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public User getExaminePerson() {
        return examinePerson;
    }

    public void setExaminePerson(User examinePerson) {
        this.examinePerson = examinePerson;
    }

    public String getExamineDate() {
        return examineDate;
    }

    public void setExamineDate(String examineDate) {
        this.examineDate = examineDate;
    }

    public WarehouseCompanyOut getCompany() {
        return company;
    }

    public void setCompany(WarehouseCompanyOut company) {
        this.company = company;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public WarehouseApplication getWarehouseApplication() {
        return warehouseApplication;
    }

    public void setWarehouseApplication(WarehouseApplication warehouseApplication) {
        this.warehouseApplication = warehouseApplication;
    }

    public List<WarehouseOutputPro> getWarehouseOutputPro() {
        return warehouseOutputPro;
    }

    public void setWarehouseOutputPro(List<WarehouseOutputPro> warehouseOutputPro) {
        this.warehouseOutputPro = warehouseOutputPro;
    }
}
