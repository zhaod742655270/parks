package com.hbyd.parks.domain.attendancesys;


import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 节假日
 */
@Entity
@Table(name = "attend_holiday")
public class Holiday extends BaseEntity {
    /**
     * 节假日名称
     */
    private String name;

    /**
     * 起始日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
//    private java.sql.Date startDate;
    /**
     * 结束日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
//    private java.sql.Date endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
