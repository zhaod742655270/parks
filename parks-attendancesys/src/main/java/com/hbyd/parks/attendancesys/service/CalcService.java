package com.hbyd.parks.attendancesys.service;

import com.hbyd.parks.attendancesys.serviceImpl.IntervalResult;
import com.hbyd.parks.attendancesys.serviceImpl.ShiftResult;
import com.hbyd.parks.domain.attendancesys.AtteDetail;
import com.hbyd.parks.domain.attendancesys.AtteInfo;
import com.hbyd.parks.domain.attendancesys.Interval;
import com.hbyd.parks.domain.attendancesys.LeaveRecord;
import com.hbyd.parks.domain.doorsys.AccessEvent;
import com.hbyd.parks.domain.supportsys.Employee;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * 计算考勤的服务
 */
public interface CalcService {
    /**
     * 判断某一天是否假日
     *
     * @param date 需要判断的日期
     * @return 如果是假日，返回 true, 否则 false
     */
    boolean isHoliday(Date date);

    /**<pre>
     * 事后计算，即：重新计算某段时间内，每天对应班次的考勤记录
     * 如果起始日期和结束日期相差不超过一天，表示计算起始日期那天的考勤记录
     * </pre>
     * @param empId        人员ID(必须是参与考勤的人员，不是也没关系，会直接跳过，但会影响计算速度)
     * @param beginDate    起始日期
     * @param endDate      结束日期
     */
    //TODO 删除 Shift 后，AtteInfo 所使用的 Shift 尚未处理
    void recalc(String empId, Date beginDate, Date endDate);

    /**计算某人某天的旷工情况
     * @param employee 人员
     * @param day 某天
     */
    void calcAbsent(DateTime day, Employee employee);

    /**计算考勤班次的旷工情况
     * @param ai  考勤班次
     *
     */
    void calcAbsent(AtteInfo ai);

    /**
     * 匹配可计算的班次结果
     *
     * @param empId     人员ID
     * @param eventTime 刷卡时间
     * @return 可计算的班次结果，如未匹配到，直接返回 NULL
     */
    ShiftResult matchShiftResult(String empId, DateTime eventTime);

    /**
     * 计算考勤的入口方法
     */
    void calc(AccessEvent e);

    /**
     * 计算时段和所有请假记录的重叠时长(分钟数)
     *
     * @param currInterResult    当前时段，为可计算的时段
     * @param leaveRecords 和时段重叠的所有请假记录
     * @return 时段内累计请假分钟数
     */
    Long getSumLeaveMins(IntervalResult currInterResult, List<LeaveRecord> leaveRecords);

    /**
     * 查询刷卡当天和刷卡时段重叠的请假记录
     *
     * @param begin
     * @param end
     * @param empId
     * @return 请假记录列表，如果没有匹配的请假记录，返回空列表，注意：一个时段可以重叠多个请假记录
     */
    List<LeaveRecord> getLeaveRecords(DateTime begin, DateTime end, String empId);

    /**
     * 查询刷卡时间发生在班次的哪个时段
     *
     * @param shiftResult 班次结果
     * @param eventTime   刷卡时间
     * @return 刷卡时间所在的时段，如果不属于任何时段，返回 null
     */
    IntervalResult getAccessInterval(ShiftResult shiftResult, DateTime eventTime);

    /**
     * 查询人员某天的考勤记录
     *
     * @param employee 人员
     * @param date     日期
     * @return 考勤记录
     */
    AtteInfo findAtteInfo(Employee employee, Date date);

    /**
     * 查询目标时段对应的考勤时段
     *
     * @param info           考勤记录
     * @param accessInterval 刷卡时间所在时段
     * @return 考勤时段
     */
    AtteDetail findDetail(AtteInfo info, Interval accessInterval);
}
