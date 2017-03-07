package com.hbyd.parks.dao.attendancesys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.common.enums.ShiftType;
import com.hbyd.parks.domain.attendancesys.Shift;

import java.util.List;

/**
 * 该接口提供基本的增删改查和分页操作
 */
public interface ShiftDao extends BaseDao<Shift, String>{
    /**根据名称查询班次
     * @param name 班次名称
     * @return 班次实体
     */
    Shift getByName(String name);

    /**根据类型查询班次
     * @param shiftType 班次类型
     * @return 班次实体列表
     */
    List<Shift> getByType(ShiftType shiftType);
}
