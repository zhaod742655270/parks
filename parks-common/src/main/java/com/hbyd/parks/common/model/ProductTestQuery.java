package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2016/12/28.
 */
public class ProductTestQuery extends QueryBeanEasyUI{
    private String idQuery;
    private String productNameQuery;        //设备名称编号
    private String registerPersonQuery;     //登记人
    private String testPersonQuery;         //测试人
    private String numberQuery;             //编号
    private String regDateBegQuery;         //登记日期起
    private String regDateEndQuery;         //登记日期起

    public String getIdQuery() {
        return idQuery;
    }

    public void setIdQuery(String idQuery) {
        this.idQuery = idQuery;
    }

    public String getProductNameQuery() {
        return productNameQuery;
    }

    public void setProductNameQuery(String productNameQuery) {
        this.productNameQuery = productNameQuery;
    }

    public String getRegisterPersonQuery() {
        return registerPersonQuery;
    }

    public void setRegisterPersonQuery(String registerPersonQuery) {
        this.registerPersonQuery = registerPersonQuery;
    }

    public String getTestPersonQuery() {
        return testPersonQuery;
    }

    public void setTestPersonQuery(String testPersonQuery) {
        this.testPersonQuery = testPersonQuery;
    }

    public String getNumberQuery() {
        return numberQuery;
    }

    public void setNumberQuery(String numberQuery) {
        this.numberQuery = numberQuery;
    }

    public String getRegDateBegQuery() {
        return regDateBegQuery;
    }

    public void setRegDateBegQuery(String regDateBegQuery) {
        this.regDateBegQuery = regDateBegQuery;
    }

    public String getRegDateEndQuery() {
        return regDateEndQuery;
    }

    public void setRegDateEndQuery(String regDateEndQuery) {
        this.regDateEndQuery = regDateEndQuery;
    }
}
