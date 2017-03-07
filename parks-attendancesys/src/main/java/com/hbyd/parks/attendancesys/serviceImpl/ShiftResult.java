package com.hbyd.parks.attendancesys.serviceImpl;

import com.hbyd.parks.domain.attendancesys.Interval;
import com.hbyd.parks.domain.attendancesys.Shift;
import org.joda.time.DateTime;

import java.util.List;

/**
 * 可计算的班次，某些字段定义为 JodaTime 提供的 DateTime 格式，便于比较
 */
public class ShiftResult {

    /**<pre>
     * 刷卡当天是否有班次，默认为 false，排班规则：一天一班次
     * </pre>
     */
    private boolean shiftOnAccessDayExist;

    /**
     * 是否匹配到班次，默认为 false
     */
    private boolean shiftMatched;

    /**
     * 班次绑定的日期
     */
    private DateTime day;

    /**
     * 班次
     */
    private Shift shift;

    /**
     * 班次的开始边界：最早的 signInTimeBegin
     */
    private DateTime shiftSignInBegin;

    /**
     * 班次的结束边界：最晚的 signOutTimeEnd
     */
    private DateTime shiftSignOutEnd;

    /**
     * 可计算的时段列表
     */
    private List<IntervalResult> intervalResults;
    /**
     * 时段列表
     */
    private List<Interval> intervals;

    public boolean isShiftOnAccessDayExist() {
        return shiftOnAccessDayExist;
    }

    public void setShiftOnAccessDayExist(boolean shiftOnAccessDayExist) {
        this.shiftOnAccessDayExist = shiftOnAccessDayExist;
    }

    public boolean isShiftMatched() {
        return shiftMatched;
    }

    public void setShiftMatched(boolean shiftMatched) {
        this.shiftMatched = shiftMatched;
    }

    public DateTime getDay() {
        return day;
    }

    public void setDay(DateTime day) {
        this.day = day;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public DateTime getShiftSignInBegin() {
        return shiftSignInBegin;
    }

    public void setShiftSignInBegin(DateTime shiftSignInBegin) {
        this.shiftSignInBegin = shiftSignInBegin;
    }

    public DateTime getShiftSignOutEnd() {
        return shiftSignOutEnd;
    }

    public void setShiftSignOutEnd(DateTime shiftSignOutEnd) {
        this.shiftSignOutEnd = shiftSignOutEnd;
    }

    public List<IntervalResult> getIntervalResults() {
        return intervalResults;
    }

    public void setIntervalResults(List<IntervalResult> intervalResults) {
        this.intervalResults = intervalResults;
    }

    public void setIntervals(List<Interval> intervals) {
        this.intervals = intervals;
    }

    public List<Interval> getIntervals() {
        return intervals;
    }
}
