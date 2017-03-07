package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import com.hbyd.parks.domain.managesys.User;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.List;

/**
 * 申请单记录表
 * Created by Zhao_d on 2017/1/22.
 */
@Entity
@Table(name="oa_warehouse_application")
@Audited
public class WarehouseApplication extends RecoverableEntity {
    private String number;          //申请单号
    private String name;            //名称
    private String type;            //类型(入库/出库)

    @ManyToOne
    @JoinColumn(name="recordPersonFK")
    @NotAudited
    private User recordPerson;        //录入人

    private String recordDate;          //录入日期
    private String note;                //备注

    @OneToMany(mappedBy = "warehouseApplication")
    @NotAudited
    private List<WarehouseApplicationPro> warehouseApplicationPro;        //货品列表

    @OneToMany(mappedBy = "warehouseApplication")
    @NotAudited
    private List<WarehouseInput> warehouseInput;        //入库单

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<WarehouseApplicationPro> getWarehouseApplicationPro() {
        return warehouseApplicationPro;
    }

    public void setWarehouseApplicationPro(List<WarehouseApplicationPro> warehouseApplicationPro) {
        this.warehouseApplicationPro = warehouseApplicationPro;
    }

    public List<WarehouseInput> getWarehouseInput() {
        return warehouseInput;
    }

    public void setWarehouseInput(List<WarehouseInput> warehouseInput) {
        this.warehouseInput = warehouseInput;
    }
}
