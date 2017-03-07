package com.hbyd.parks.dao.attendancesys;

import com.hbyd.parks.domain.attendancesys.Interval;
import com.hbyd.parks.common.base.BaseDao;

import java.util.List;

/**
 * Created by allbutone on 14-8-7.
 */
public interface IntervalDao extends BaseDao<Interval,String> {
    /**获取某个班次下的所有时段
     * @param shiftID 班次ID
     * @return 时段列表
     */
    List<Interval> findIntervals(String shiftID);
}
