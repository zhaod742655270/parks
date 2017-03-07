package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Zhao_d on 2017/2/9.
 */
public class WarehouseDTO extends BaseDTO {

    private String productId;           //货品ID
    private String name;         //货品名称
    private String modelNumber;   //货品型号
    private String specifications;   //货品封装
    private String brand;           //货品品牌
    private String unit;            //货品单位
    private Double newCost;         //最新单价
    private Double quantityUse;     //可用数量
    private Double quantityBorrow;  //借用数量
    private Double quantity;        //库存总量

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
