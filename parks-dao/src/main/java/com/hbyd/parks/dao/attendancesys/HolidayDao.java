package com.hbyd.parks.dao.attendancesys;

import com.hbyd.parks.domain.attendancesys.Holiday;
import com.hbyd.parks.common.base.BaseDao;

/**
 * Created by allbutone on 14-8-8.
 */
public interface HolidayDao extends BaseDao<Holiday, String> {
    Holiday getByName(String name);
}
