package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2017/1/22.
 */
public class WarehouseApplicationQuery extends QueryBeanEasyUI {

    private String nameQuery;
    private String recordDateBegQuery;
    private String recordDateEndQuery;
    private String typeQuery;

    public String getNameQuery() {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery) {
        this.nameQuery = nameQuery;
    }

    public String getRecordDateBegQuery() {
        return recordDateBegQuery;
    }

    public void setRecordDateBegQuery(String recordDateBegQuery) {
        this.recordDateBegQuery = recordDateBegQuery;
    }

    public String getRecordDateEndQuery() {
        return recordDateEndQuery;
    }

    public void setRecordDateEndQuery(String recordDateEndQuery) {
        this.recordDateEndQuery = recordDateEndQuery;
    }

    public String getTypeQuery() {
        return typeQuery;
    }

    public void setTypeQuery(String typeQuery) {
        this.typeQuery = typeQuery;
    }
}
