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
 * Created by Zhao_d on 2017/3/3.
 */
@Entity
@Table(name="oa_warehouse_Borrow")
@Audited
public class WarehouseBorrow extends RecoverableEntity{
    private String number;          //借用编号
    private String borrowDate;      //借用日期
    @ManyToOne
    @JoinColumn(name="borrowPersonFK")
    @NotAudited
    private User borrowPerson;      //借用人
    private String state;           //状态(借用、已归还)
    private String backDate;        //归还日期

    @ManyToOne
    @JoinColumn(name="productFK")
    @NotAudited
    private WarehouseProduct warehouseProduct;      //借用物品

    private Double quantity;             //数量
    private String note;                //备注

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public User getBorrowPerson() {
        return borrowPerson;
    }

    public void setBorrowPerson(User borrowPerson) {
        this.borrowPerson = borrowPerson;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public WarehouseProduct getWarehouseProduct() {
        return warehouseProduct;
    }

    public void setWarehouseProduct(WarehouseProduct warehouseProduct) {
        this.warehouseProduct = warehouseProduct;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
