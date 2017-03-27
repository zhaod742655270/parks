package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2016/12/14.
 */
public class HandMaintenanceQuery extends QueryBeanEasyUI {
    private String idQuery;
    private String projectNameQuery;        //项目名称
    private String productNameQuery;        //设备名称编号
    private String registerPersonQuery;     //登记人
    private String numberQuery;             //编号
    private String regDateBegQuery;         //登记日期起
    private String regDateEndQuery;         //登记日期起
    private String assignPersonQuery;       //指派处理人员

    private String checkPersonQuery;        //查询人，用于查询时的权限限制


    public String getIdQuery() {
        return idQuery;
    }

    public void setIdQuery(String idQuery) {
        this.idQuery = idQuery;
    }

    public String getProjectNameQuery() {
        return projectNameQuery;
    }

    public void setProjectNameQuery(String projectNameQuery) {
        this.projectNameQuery = projectNameQuery;
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

    public String getAssignPersonQuery() {
        return assignPersonQuery;
    }

    public void setAssignPersonQuery(String assignPersonQuery) {
        this.assignPersonQuery = assignPersonQuery;
    }

    public String getCheckPersonQuery() {
        return checkPersonQuery;
    }

    public void setCheckPersonQuery(String checkPersonQuery) {
        this.checkPersonQuery = checkPersonQuery;
    }
}
