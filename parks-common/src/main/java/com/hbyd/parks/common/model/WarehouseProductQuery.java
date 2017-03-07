package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2017/1/20.
 */
public class WarehouseProductQuery extends QueryBeanEasyUI {
    private String nameQuery;
    private String productTypeQuery;
    private String brandQuery;

    private String modelNumberQuery;     //型号
    private String specificationsQuery;  //封装

    public String getNameQuery() {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery) {
        this.nameQuery = nameQuery;
    }

    public String getProductTypeQuery() {
        return productTypeQuery;
    }

    public void setProductTypeQuery(String productTypeQuery) {
        this.productTypeQuery = productTypeQuery;
    }

    public String getBrandQuery() {
        return brandQuery;
    }

    public void setBrandQuery(String brandQuery) {
        this.brandQuery = brandQuery;
    }

    public String getModelNumberQuery() {
        return modelNumberQuery;
    }

    public void setModelNumberQuery(String modelNumberQuery) {
        this.modelNumberQuery = modelNumberQuery;
    }

    public String getSpecificationsQuery() {
        return specificationsQuery;
    }

    public void setSpecificationsQuery(String specificationsQuery) {
        this.specificationsQuery = specificationsQuery;
    }
}
