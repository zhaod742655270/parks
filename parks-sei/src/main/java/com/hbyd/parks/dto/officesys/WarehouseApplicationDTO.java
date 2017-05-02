package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Zhao_d on 2017/1/22.
 */
public class WarehouseApplicationDTO extends BaseDTO {
    private String number;          //申请单号
    private String name;            //名称
    private String type;            //类型(入库/出库)
    private String recordPersonID;        //录入人
    private String recordPersonName;
    private String recordDate;          //录入日期
    private String productNum;          //生产任务单号
    private String note;                //备注

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecordPersonID() {
        return recordPersonID;
    }

    public void setRecordPersonID(String recordPersonID) {
        this.recordPersonID = recordPersonID;
    }

    public String getRecordPersonName() {
        return recordPersonName;
    }

    public void setRecordPersonName(String recordPersonName) {
        this.recordPersonName = recordPersonName;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }
}
