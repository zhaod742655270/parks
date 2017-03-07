package com.hbyd.parks.daoImpl.hibernate.attendancesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.attendancesys.LeaveTypeDao;
import com.hbyd.parks.domain.attendancesys.LeaveType;
import org.springframework.stereotype.Repository;

/**
 * Created by allbutone on 14-8-11.
 */
@Repository
public class LeaveTypeDaoImpl extends BaseDaoImpl<LeaveType,String> implements LeaveTypeDao {
    @Override
    public LeaveType getByName(String name) {
        return getByName("name", name);
    }
}
