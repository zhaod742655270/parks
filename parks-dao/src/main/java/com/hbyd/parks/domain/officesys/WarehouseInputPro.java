package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

/**
 * Created by Zhao_d on 2016/12/27.
 */
@Entity
@Table(name = "oa_warehouse_input_product")
@Audited
public class WarehouseInputPro extends RecoverableEntity {
    @ManyToOne
    @JoinColumn(name = "parentIdFK", referencedColumnName = "id")
    @NotAudited
    private WarehouseInput warehouseInput;          //入库单

    @ManyToOne
    @JoinColumn(name = "productIdFK", referencedColumnName = "id")
    @NotAudited
    private WarehouseProduct warehouseProduct;      //货品

    private String productNum;          //生产任务单号
    private Double quantity;             //数量
    private Double price;                //单价
    private Double valence;              //合价
    private String note;                //备注

    public WarehouseInput getWarehouseInput() {
        return warehouseInput;
    }

    public void setWarehouseInput(WarehouseInput warehouseInput) {
        this.warehouseInput = warehouseInput;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getValence() {
        return valence;
    }

    public void setValence(Double valence) {
        this.valence = valence;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public WarehouseProduct getWarehouseProduct() {
        return warehouseProduct;
    }

    public void setWarehouseProduct(WarehouseProduct warehouseProduct) {
        this.warehouseProduct = warehouseProduct;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }
}
