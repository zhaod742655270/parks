package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Zhao_d on 2017/1/22.
 */
public class WarehouseApplicationProDTO extends BaseDTO {
    private String parentIdFK;          //申请单ID
    private String productName;         //货品名称
    private String productModelNumber;   //货品型号
    private String productSpecifications;   //货品封装
    private String productBrand;           //品牌
    private String productUnit;          //货品单位
    private String productNum;          //生产任务单号
    private Double quantity;             //数量
    private Double quantityInput;       //入库、出库数量
    private String note;                //备注

    public String getParentIdFK() {
        return parentIdFK;
    }

    public void setParentIdFK(String parentIdFK) {
        this.parentIdFK = parentIdFK;
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

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
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

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public Double getQuantityInput() {
        return quantityInput;
    }

    public void setQuantityInput(Double quantityInput) {
        this.quantityInput = quantityInput;
    }
}
