package com.hbyd.parks.dao.attendancesys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.attendancesys.ShiftAssign;

import java.util.Date;

/**
 * Created by allbutone on 2014/10/22.
 */
public interface ShiftAssignDao extends BaseDao<ShiftAssign, String> {
    /**查询人员某年某月的排班记录
     * @param empId 人员ID
     * @param year 年份
     * @param month 月份
     * @return 排班记录
     */
    ShiftAssign getByEmpId(String empId, int year, int month);

    /**查询某人某天的班次
     * @param empId 人员ID
     * @param date 日期，时间部分无效
     * @return 班次ID, 如果不存在，返回 null;
     */
    String getShift(String empId, Date date);

    /**按照年月查询月排班记录
     * @param yearAndMonth 年月字符串，格式为：yyyy-MM
     * @param empId        人员ID
     * @return 当月排班记录
     */
    ShiftAssign getShiftAssign(String yearAndMonth, String empId);

    /**
     * 班次是否被排班结果引用
     *
     * @param shiftId 班次ID
     * @return 如果被引用, 返回 true, 否则 false;
     */
    boolean usedWithinShiftAssign(String shiftId);
}
