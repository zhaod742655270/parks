package com.hbyd.parks.dto.attendancesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by allbutone on 14-8-11.
 */
public class LeaveTypeDTO extends BaseDTO {
    /**
     * 名称
     */
    private String name;

    /**
     * 符号
     */
    private String symbol;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}