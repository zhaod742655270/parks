package com.hbyd.parks.daoImpl.hibernate.attendancesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.common.enums.ShiftType;
import com.hbyd.parks.dao.attendancesys.ShiftDao;
import com.hbyd.parks.domain.attendancesys.Shift;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShiftDaoImpl extends BaseDaoImpl<Shift, String> implements ShiftDao {
    @Override
    public Shift getByName(String name) {
        return getByName("name", name);
    }

    @Override
    public List<Shift> getByType(ShiftType shiftType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Shift.class);
        criteria.add(Restrictions.eq("shiftType", shiftType));
        return findListByCriteria(criteria);
    }
}
