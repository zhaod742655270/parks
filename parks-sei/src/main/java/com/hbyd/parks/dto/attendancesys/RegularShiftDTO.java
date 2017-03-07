package com.hbyd.parks.dto.attendancesys;

import com.hbyd.parks.common.base.BaseDTO;
import com.hbyd.parks.common.constant.Unit;

import java.util.List;

/**
 * 规律班次
 */
public class RegularShiftDTO extends BaseDTO{
    /**
     * 规律班次的名称
     */
    private String name;

    /**
     * 单位数，之前叫做"周期数"
     */
    private int unitCnt;

    /**
     * 单位
     */
    private Unit unit;

    /**
     * 班次绑定列表
     */
    private List<ShiftBindingDTO> bindings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitCnt() {
        return unitCnt;
    }

    public void setUnitCnt(int unitCnt) {
        this.unitCnt = unitCnt;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public List<ShiftBindingDTO> getBindings() {
        return bindings;
    }

    public void setBindings(List<ShiftBindingDTO> bindings) {
        this.bindings = bindings;
    }
}
