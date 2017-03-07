package com.hbyd.parks.domain.attendancesys;


import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 请假类型
 */
@Entity
@Table(name = "attend_leave_type")
public class LeaveType extends BaseEntity {

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
