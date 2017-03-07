package com.hbyd.parks.dto.attendancesys;

import com.hbyd.parks.common.base.BaseDTO;
import com.hbyd.parks.common.enums.ShiftType;

/**
 * 班次
 */
public class ShiftDTO extends BaseDTO {
    /**
     * 班次名称
     */
    private String name;

    /**
     * 班次简述
     */
    private String description;

    /**
     * 班次类型
     */
    private ShiftType shiftType;

    public ShiftType getShiftType() {
        return shiftType;
    }

    public void setShiftType(ShiftType shiftType) {
        this.shiftType = shiftType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
