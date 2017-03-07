package com.hbyd.parks.domain.attendancesys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 班次绑定，即：规律班次的内容
 */
@Entity
@Table(name = "attend_shift_binding")
public class ShiftBinding extends BaseEntity {
    /**
     * 要绑定的天的名称
     * 1. 按周绑定，使用 JodaTime 提供的 DateTimeConstants 提供的常量，如：DateTimeConstants.MONDAY
     * 2. 按日绑定，直接指定数字即可，如：每个月的第一天，可以指定为 1，依此类推
     */
    private int dayToBound;

    /**
     * 绑定的班次
     */
    @ManyToOne
    @JoinColumn(name = "shiftFK", referencedColumnName = "id")
    private Shift shift;

    /**
     * 所属的规律班次
     */
    @ManyToOne
    @JoinColumn(name = "regularShiftFK", referencedColumnName = "id")
    private RegularShift regularShift;

    /**
     * <pre>
     * 纵向索引：
     * 1. 如果按周排列，就是：所属的周索引
     * 2. 如果按日排列，就是：空字符串，因为日的绑定名称就是索引，无需在界面显示纵向索引
     * </pre>
     */
    private int idx;

    public int getDayToBound() {
        return dayToBound;
    }

    public void setDayToBound(int dayToBound) {
        this.dayToBound = dayToBound;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public RegularShift getRegularShift() {
        return regularShift;
    }

    public void setRegularShift(RegularShift regularShift) {
        this.regularShift = regularShift;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int index) {
        this.idx = index;
    }
}
