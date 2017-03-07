package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2017/3/3.
 */
public class WarehouseBorrowQuery extends QueryBeanEasyUI{
    private String borrowPersonQuery;
    private String stateQuery;
    private String borrowDateBegQuery;
    private String borrowDateEndQuery;

    public String getBorrowPersonQuery() {
        return borrowPersonQuery;
    }

    public void setBorrowPersonQuery(String borrowPersonQuery) {
        this.borrowPersonQuery = borrowPersonQuery;
    }

    public String getStateQuery() {
        return stateQuery;
    }

    public void setStateQuery(String stateQuery) {
        this.stateQuery = stateQuery;
    }

    public String getBorrowDateBegQuery() {
        return borrowDateBegQuery;
    }

    public void setBorrowDateBegQuery(String borrowDateBegQuery) {
        this.borrowDateBegQuery = borrowDateBegQuery;
    }

    public String getBorrowDateEndQuery() {
        return borrowDateEndQuery;
    }

    public void setBorrowDateEndQuery(String borrowDateEndQuery) {
        this.borrowDateEndQuery = borrowDateEndQuery;
    }
}
