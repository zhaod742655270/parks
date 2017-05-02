package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Zhao_d on 2016/12/27.
 */
public class WarehouseInputProDTO extends BaseDTO {
    private String parentIdFK;          //入库单ID
    private String productId;           //货品ID
    private String productName;         //货品名称
    private String productModelNumber;   //货品型号
    private String productSpecifications;   //货品封装
    private String productUnit;          //货品单位
    private Double quantity;             //数量
    private Double price;                //单价
    private Double valence;              //合价
    private String note;                //备注

    private WarehouseApplicationProDTO warehouseApplicationPro;  //申请单对应货品
    private WarehouseDTO warehouse;        //对应库存货品

    public String getParentIdFK() {
        return parentIdFK;
    }

    public void setParentIdFK(String parentIdFK) {
        this.parentIdFK = parentIdFK;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductModelNumber() {
        return productModelNumber;
    }

    public void setProductModelNumber(String productModelNumber) {
        this.productModelNumber = productModelNumber;
    }

    public String getProductSpecifications() {
        return productSpecifications;
    }

    public void setProductSpecifications(String productSpecifications) {
        this.productSpecifications = productSpecifications;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public WarehouseApplicationProDTO getWarehouseApplicationPro() {
        return warehouseApplicationPro;
    }

    public void setWarehouseApplicationPro(WarehouseApplicationProDTO warehouseApplicationPro) {
        this.warehouseApplicationPro = warehouseApplicationPro;
    }

    public WarehouseDTO getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseDTO warehouse) {
        this.warehouse = warehouse;
    }
}
