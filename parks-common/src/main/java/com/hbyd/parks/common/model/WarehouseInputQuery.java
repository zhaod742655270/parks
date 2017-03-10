package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2016/12/26.
 */
public class WarehouseInputQuery extends QueryBeanEasyUI {

    private String numberQuery;
    private String inputTypeQuery;
    private String inputDateBegQuery;
    private String inputDateEndQuery;

    private String nameQuery;
    private String modelNumberQuery;
    private String specificationsQuery;

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
