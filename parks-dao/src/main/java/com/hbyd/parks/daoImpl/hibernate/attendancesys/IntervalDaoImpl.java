package com.hbyd.parks.daoImpl.hibernate.attendancesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.attendancesys.IntervalDao;
import com.hbyd.parks.domain.attendancesys.Interval;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by allbutone on 14-8-7.
 */
@Repository
public class IntervalDaoImpl extends BaseDaoImpl<Interval, String> implements IntervalDao {
    @Override
    public List<Interval> findIntervals(String shiftID) {
        String hql = "from Interval i where i.shift.id = ?";
        return getCurrSession().createQuery(hql).setParameter(0, shiftID).list();
    }
}
