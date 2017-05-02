package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2017/2/20.
 */
public class WarehouseOutputQuery extends QueryBeanEasyUI {
    private String numberQuery;
    private String outputDateBegQuery;
    private String outputDateEndQuery;

    private String nameQuery;
    private String modelNumberQuery;
    private String specificationsQuery;

    public String getNumberQuery() {
        return numberQuery;
    }

    public void setNumberQuery(String numberQuery) {
        this.numberQuery = numberQuery;
    }

    public String getOutputDateBegQuery() {
        return outputDateBegQuery;
    }

    public void setOutputDateBegQuery(String outputDateBegQuery) {
        this.outputDateBegQuery = outputDateBegQuery;
    }

    public String getOutputDateEndQuery() {
        return outputDateEndQuery;
    }

    public void setOutputDateEndQuery(String outputDateEndQuery) {
        this.outputDateEndQuery = outputDateEndQuery;
    }

    public String getNameQuery() {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery) {
        this.nameQuery = nameQuery;
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
