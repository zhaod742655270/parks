package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2016/11/4.
 */
public class QuotationQuery extends QueryBeanEasyUI{
    //年代
    private String sheetNameQuery;
    //项目名称
    private String projectNameQuery;
    //项目类型
    private String projectTypeQuery;
    //设备名称
    private String nameQuery;
    //规格型号
    private String specificationQuery;
    //品牌名称
    private String brandQuery;

    public String getNameQuery(){ return nameQuery; }

    public void setNameQuery(String nameQuery){ this.nameQuery = nameQuery; }

    public String getSpecificationQuery() {
        return specificationQuery;
    }

    public void setSpecificationQuery(String specificationQuery) {
        this.specificationQuery = specificationQuery;
    }

    public String getBrandQuery() {
        return brandQuery;
    }

    public void setBrandQuery(String brandQuery) {
        this.brandQuery = brandQuery;
    }

    public String getSheetNameQuery() {
        return sheetNameQuery;
    }

    public void setSheetNameQuery(String sheetNameQuery) {
        this.sheetNameQuery = sheetNameQuery;
    }

    public String getProjectNameQuery() {
        return projectNameQuery;
    }

    public void setProjectNameQuery(String projectNameQuery) {
        this.projectNameQuery = projectNameQuery;
    }

    public String getProjectTypeQuery() {
        return projectTypeQuery;
    }

    public void setProjectTypeQuery(String projectTypeQuery) {
        this.projectTypeQuery = projectTypeQuery;
    }
}
