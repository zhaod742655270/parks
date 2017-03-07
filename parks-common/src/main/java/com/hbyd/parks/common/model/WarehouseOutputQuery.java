package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2017/2/20.
 */
public class WarehouseOutputQuery extends QueryBeanEasyUI {
    private String numberQuery;
    private String outputTypeQuery;
    private String outputDateBegQuery;
    private String outputDateEndQuery;

    public String getNumberQuery() {
        return numberQuery;
    }

    public void setNumberQuery(String numberQuery) {
        this.numberQuery = numberQuery;
    }

    public String getOutputTypeQuery() {
        return outputTypeQuery;
    }

    public void setOutputTypeQuery(String outputTypeQuery) {
        this.outputTypeQuery = outputTypeQuery;
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
}
