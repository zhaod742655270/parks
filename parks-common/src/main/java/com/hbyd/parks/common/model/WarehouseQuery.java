package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2017/2/9.
 */
public class WarehouseQuery extends QueryBeanEasyUI {
    private String productIdQuery;
    private String nameQuery;
    private String typeQuery;
    private String brandQuery;


    public String getNameQuery() {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery) {
        this.nameQuery = nameQuery;
    }

    public String getTypeQuery() {
        return typeQuery;
    }

    public void setTypeQuery(String typeQuery) {
        this.typeQuery = typeQuery;
    }

    public String getBrandQuery() {
        return brandQuery;
    }

    public void setBrandQuery(String brandQuery) {
        this.brandQuery = brandQuery;
    }

    public String getProductIdQuery() {
        return productIdQuery;
    }

    public void setProductIdQuery(String productIdQuery) {
        this.productIdQuery = productIdQuery;
    }
}
