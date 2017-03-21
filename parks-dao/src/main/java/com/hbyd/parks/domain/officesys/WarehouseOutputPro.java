package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

/**
 * Created by Zhao_d on 2017/2/20.
 */
@Entity
@Table(name="oa_warehouse_output_product")
@Audited
public class WarehouseOutputPro extends RecoverableEntity {
    @ManyToOne
    @JoinColumn(name = "parentIdFK", referencedColumnName = "id")
    @NotAudited
    private WarehouseOutput warehouseOutput;          //出库单

    @ManyToOne
    @JoinColumn(name = "productIdFK", referencedColumnName = "id")
    @NotAudited
    private WarehouseProduct warehouseProduct;      //货品

    private Double quantity;             //数量
    private String note;                //备注

    @ManyToOne
    @JoinColumn(name = "applyProIdFK", referencedColumnName = "id")
    @NotAudited
    private WarehouseApplicationPro warehouseApplicationPro;        //申请单对应货品

    @ManyToOne
    @JoinColumn(name = "warehouseFK",referencedColumnName = "id")
    @NotAudited
    private Warehouse warehouse;        //对应库存货品

    public WarehouseOutput getWarehouseOutput() {
        return warehouseOutput;
    }

    public void setWarehouseOutput(WarehouseOutput warehouseOutput) {
        this.warehouseOutput = warehouseOutput;
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

    public WarehouseApplicationPro getWarehouseApplicationPro() {
        return warehouseApplicationPro;
    }

    public void setWarehouseApplicationPro(WarehouseApplicationPro warehouseApplicationPro) {
        this.warehouseApplicationPro = warehouseApplicationPro;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
