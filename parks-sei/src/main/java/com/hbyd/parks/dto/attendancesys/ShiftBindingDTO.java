package com.hbyd.parks.dto.attendancesys;

import com.hbyd.parks.common.base.BaseDTO;
import com.hbyd.parks.common.enums.ShiftType;

/**
 * 传输对象：班次绑定
 */
public class ShiftBindingDTO extends BaseDTO {
    /**
     * 要绑定的天的名称，如果按周绑定，就是：星期X；按日绑定，就是：1,2..
     */
    private int dayToBound;

    /**
     * 绑定的班次主键
     */
    private String shiftFK;

    /**
     * 绑定的班次类型
     */
    private ShiftType shiftType;

    /**
     * 绑定的班次名称
     */
    private String shiftName;

    /**
     * 所属的规律班次
     */
    private String regularShiftFK;

    /**
     * <pre>
     * 纵向索引：
     * 1. 如果按周排列，就是：所属的周索引
     * 2. 如果按日排列，就是：空字符串，因为日的绑定名称就是索引，无需在界面显示纵向索引
     * </pre>
     */
    private int idx;

    public ShiftType getShiftType() {
        return shiftType;
    }

    public void setShiftType(ShiftType shiftType) {
        this.shiftType = shiftType;
    }

    public int getDayToBound() {
        return dayToBound;
    }

    public void setDayToBound(int dayToBound) {
        this.dayToBound = dayToBound;
    }

    public String getShiftFK() {
        return shiftFK;
    }

    public void setShiftFK(String shiftFK) {
        this.shiftFK = shiftFK;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getRegularShiftFK() {
        return regularShiftFK;
    }

    public void setRegularShiftFK(String regularShiftFK) {
        this.regularShiftFK = regularShiftFK;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
