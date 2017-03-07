package com.hbyd.parks.common.model;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/3/15
 */
public class RecordQueryBean extends QueryBeanEasyUI {

    private String sheetNameQuery;

    private String typeQuery;

    private String nameQuery;

    public String getSheetNameQuery() {
        return sheetNameQuery;
    }

    public void setSheetNameQuery(String sheetNameQuery) {
        this.sheetNameQuery = sheetNameQuery;
    }

    public String getTypeQuery() {
        return typeQuery;
    }

    public void setTypeQuery(String typeQuery) {
        this.typeQuery = typeQuery;
    }

    public String getNameQuery() {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery) {
        this.nameQuery = nameQuery;
    }
}
