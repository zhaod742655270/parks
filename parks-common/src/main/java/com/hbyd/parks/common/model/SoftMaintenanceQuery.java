package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2016/12/8.
 */
public class SoftMaintenanceQuery extends QueryBeanEasyUI {
    private String idQuery;      //ID
    
    private String projectNameQuery;        //项目名称

    private String productNameQuery;        //产品名称

    private String regPersonQuery;          //登记人

    private String numberQuery;             //编号

    private String regDateBegQuery;         //登记日期起

    private String regDateEndQuery;         //登记日期起

    private String handlePersonQuery;            //处理承担人

    private String handleBegTimeQuery;          //处理开始时间

    private String handleEndTimeQuery;          //处理结束时间

    private String handleResultQuery;           //处理结果

    private String assignPersonQuery;           //指派处理人

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

    public String getRegPersonQuery() {
        return regPersonQuery;
    }

    public void setRegPersonQuery(String regPersonQuery) {
        this.regPersonQuery = regPersonQuery;
    }

    public String getIdQuery() {
        return idQuery;
    }

    public void setIdQuery(String idQuery) {
        this.idQuery = idQuery;
    }

    public String getHandlePersonQuery() {
        return handlePersonQuery;
    }

    public void setHandlePersonQuery(String handlePersonQuery) {
        this.handlePersonQuery = handlePersonQuery;
    }

    public String getHandleBegTimeQuery() {
        return handleBegTimeQuery;
    }

    public void setHandleBegTimeQuery(String handleBegTimeQuery) {
        this.handleBegTimeQuery = handleBegTimeQuery;
    }

    public String getHandleEndTimeQuery() {
        return handleEndTimeQuery;
    }

    public void setHandleEndTimeQuery(String handleEndTimeQuery) {
        this.handleEndTimeQuery = handleEndTimeQuery;
    }

    public String getHandleResultQuery() {
        return handleResultQuery;
    }

    public void setHandleResultQuery(String handleResultQuery) {
        this.handleResultQuery = handleResultQuery;
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
}
