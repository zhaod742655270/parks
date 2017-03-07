package com.hbyd.parks.daoImpl.hibernate.attendancesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.attendancesys.HolidayDao;
import com.hbyd.parks.domain.attendancesys.Holiday;
import org.springframework.stereotype.Repository;

/**
 * Created by allbutone on 14-8-8.
 */
@Repository
public class HolidayDaoImpl extends BaseDaoImpl<Holiday,String> implements HolidayDao {
    @Override
    public Holiday getByName(String name) {
        return getByName("name", name);
    }
}
