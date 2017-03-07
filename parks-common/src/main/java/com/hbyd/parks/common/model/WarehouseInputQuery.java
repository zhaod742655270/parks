package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2016/12/26.
 */
public class WarehouseInputQuery extends QueryBeanEasyUI {

    private String numberQuery;
    private String inputTypeQuery;
    private String inputDateBegQuery;
    private String inputDateEndQuery;

    public String getNumberQuery() {
        return numberQuery;
    }

    public void setNumberQuery(String numberQuery) {
        this.numberQuery = numberQuery;
    }

    public String getInputTypeQuery() {
        return inputTypeQuery;
    }

    public void setInputTypeQuery(String inputTypeQuery) {
        this.inputTypeQuery = inputTypeQuery;
    }

    public String getInputDateBegQuery() {
        return inputDateBegQuery;
    }

    public void setInputDateBegQuery(String inputDateBegQuery) {
        this.inputDateBegQuery = inputDateBegQuery;
    }

    public String getInputDateEndQuery() {
        return inputDateEndQuery;
    }

    public void setInputDateEndQuery(String inputDateEndQuery) {
        this.inputDateEndQuery = inputDateEndQuery;
    }
}
