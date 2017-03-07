package com.hbyd.parks.dto.attendancesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * 节假日
 */
public class HolidayDTO extends BaseDTO {

    /**
     * 节假日名称
     */
    private String name;
    /**
     * 起始日期
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
