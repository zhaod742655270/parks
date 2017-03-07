package com.hbyd.parks.dao.attendancesys;

import com.hbyd.parks.domain.attendancesys.LeaveType;
import com.hbyd.parks.common.base.BaseDao;

/**
 * Created by allbutone on 14-8-11.
 */
public interface LeaveTypeDao extends BaseDao<LeaveType, String> {
    LeaveType getByName(String name);
}