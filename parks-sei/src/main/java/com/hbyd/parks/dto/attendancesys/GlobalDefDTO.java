package com.hbyd.parks.dto.attendancesys;

import com.hbyd.parks.common.base.BaseDTO;
import com.hbyd.parks.common.enums.AssertType;
import com.hbyd.parks.common.enums.RestDay;

import java.util.List;

/**
 * Created by allbutone on 2014/8/27.
 */
public class GlobalDefDTO extends BaseDTO {

    /**
     * 班次 ID
     */
    private String shiftID;

    /**
     * 班次名称
     */
    private String shiftName;

    /**
     * 固定休息日，如：星期一，星期四； 实际存储格式为
     */
    private List<RestDay> restDays;

    /**
     * 无效考勤认定，如：休息，旷工
     */
    private AssertType invalidAssert;


    public String getShiftID() {
        return shiftID;
    }

    public void setShiftID(String shiftID) {
        this.shiftID = shiftID;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public List<RestDay> getRestDays() {
        return restDays;
    }

    public void setRestDays(List<RestDay> restDays) {
        this.restDays = restDays;
    }

    public AssertType getInvalidAssert() {
        return invalidAssert;
    }

    public void setInvalidAssert(AssertType invalidAssert) {
        this.invalidAssert = invalidAssert;
    }
}
