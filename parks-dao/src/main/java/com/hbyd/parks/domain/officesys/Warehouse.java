package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by Zhao_d on 2017/2/9.
 */
@Entity
@Table(name="oa_warehouse")
@Audited
public class Warehouse extends RecoverableEntity {

    @OneToOne
    @JoinColumn(name = "productIdFK", referencedColumnName = "id")
    @NotAudited
    private WarehouseProduct warehouseProduct;      //货品

    private Double newCost;         //最新单价
    private Double quantityUse;     //可用数量
    private Double quantityBorrow;  //借用数量
    private Double quantity;        //库存总量

    public WarehouseProduct getWarehouseProduct() {
        return warehouseProduct;
    }

    public void setWarehouseProduct(WarehouseProduct warehouseProduct) {
        this.warehouseProduct = warehouseProduct;
    }

    public Double getQuantityUse() {
        return quantityUse;
    }

    public void setQuantityUse(Double quantityUse) {
        this.quantityUse = quantityUse;
    }

    public Double getQuantityBorrow() {
        return quantityBorrow;
    }

    public void setQuantityBorrow(Double quantityBorrow) {
        this.quantityBorrow = quantityBorrow;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getNewCost() {
        return newCost;
    }

    public void setNewCost(Double newCost) {
        this.newCost = newCost;
    }
}
