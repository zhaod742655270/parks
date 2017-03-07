package com.hbyd.parks.dao.attendancesys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.attendancesys.ShiftBinding;

import java.util.List;

/**
 * Created by allbutone on 2014/10/20.
 */
public interface ShiftBindingDao extends BaseDao<ShiftBinding, String> {
    /**获取规律班次中的班次绑定
     * @param id 规律班次的 ID
     * @return 班次绑定列表，按照 (idx asc, dayToBound asc) 升序排列
     */
    List<ShiftBinding> getShiftBindings(String id);

    /**班次是否被班次绑定引用
     * @param shiftId 班次ID
     * @return 如果班次被引用，返回 true, 否则 false;
     */
    boolean usedWithinShiftBinding(String shiftId);
}
