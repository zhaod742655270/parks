package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import com.hbyd.parks.domain.managesys.User;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.List;

/**
 * 入库管理
 * Created by Zhao_d on 2016/12/26.
 */
@Entity
@Table(name = "oa_warehouse_input")
@Audited
public class WarehouseInput extends RecoverableEntity {
    private String number;              //入库单号
    private String inputDate;           //入库日期
    private String inputType;           //入库类型

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
    private WarehouseCompanyIn company;        //供应商
    private String note;                //备注

    @OneToMany(mappedBy = "warehouseInput")
    @NotAudited
    private List<WarehouseInputPro> warehouseInputPro;        //入库货品

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getExamineDate() {
        return examineDate;
    }

    public void setExamineDate(String examineDate) {
        this.examineDate = examineDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<WarehouseInputPro> getWarehouseInputPro() {
        return warehouseInputPro;
    }

    public void setWarehouseInputPro(List<WarehouseInputPro> warehouseInputPro) {
        this.warehouseInputPro = warehouseInputPro;
    }

    public User getRecordPerson() {
        return recordPerson;
    }

    public void setRecordPerson(User recordPerson) {
        this.recordPerson = recordPerson;
    }

    public User getExaminePerson() {
        return examinePerson;
    }

    public void setExaminePerson(User examinePerson) {
        this.examinePerson = examinePerson;
    }

    public WarehouseCompanyIn getCompany() {
        return company;
    }

    public void setCompany(WarehouseCompanyIn company) {
        this.company = company;
    }

    public WarehouseInfo getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseInfo warehouse) {
        this.warehouse = warehouse;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public WarehouseApplication getWarehouseApplication() {
        return warehouseApplication;
    }

    public void setWarehouseApplication(WarehouseApplication warehouseApplication) {
        this.warehouseApplication = warehouseApplication;
    }
}
