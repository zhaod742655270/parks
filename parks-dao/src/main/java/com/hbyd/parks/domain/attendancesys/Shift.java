package com.hbyd.parks.domain.attendancesys;

import com.hbyd.parks.common.base.BaseEntity;
import com.hbyd.parks.common.enums.ShiftType;

import javax.persistence.*;
import java.util.List;

/**
 * 班次
 */

@Entity
@Table(name = "attend_shift")
public class Shift extends BaseEntity {

    /**
     * 班次名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 班次简述
     */
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
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
