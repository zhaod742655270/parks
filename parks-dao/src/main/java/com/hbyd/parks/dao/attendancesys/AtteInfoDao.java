package com.hbyd.parks.dao.attendancesys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.attendancesys.AtteInfo;

import java.util.Date;

/**
 * Created by allbutone on 2014/9/9.
 */
public interface AtteInfoDao extends BaseDao<AtteInfo, String> {
    /**获取人员当天的考勤记录
     * @param empId 人员ID
     * @param day   日期
     * @return  考勤记录
     */
    AtteInfo getDayInfo(String empId, Date day);
}
