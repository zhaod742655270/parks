package com.hbyd.parks.attendancesys.serviceImpl;

import com.hbyd.parks.domain.attendancesys.Interval;
import org.joda.time.DateTime;

/**
 * 可计算的时段，某些字段定义为 JodaTime 提供的 DateTime 格式，便于比较
 */
public class IntervalResult {
    /**
     * 关联的时段
     */
    private Interval interval;
    /**
     * 时段开始
     */
    private DateTime workTimeBegin;

    /**
     * 时段结束
     */
    private DateTime workTimeEnd;

    /**
     * 签到开始
     */
    private DateTime signInTimeBegin;

    /**
     * 签退结束
     */
    private DateTime signOutTimeEnd;

    /**
     * 迟到临界时间
     */
    private DateTime lateDeadLine;

    /**
     * 早退临界时间
     */
    private DateTime earlyDeadLine;

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public DateTime getWorkTimeBegin() {
        return workTimeBegin;
    }

    public void setWorkTimeBegin(DateTime workTimeBegin) {
        this.workTimeBegin = workTimeBegin;
    }

    public DateTime getWorkTimeEnd() {
        return workTimeEnd;
    }

    public void setWorkTimeEnd(DateTime workTimeEnd) {
        this.workTimeEnd = workTimeEnd;
    }

    public DateTime getSignInTimeBegin() {
        return signInTimeBegin;
    }

    public void setSignInTimeBegin(DateTime signInTimeBegin) {
        this.signInTimeBegin = signInTimeBegin;
    }

    public DateTime getSignOutTimeEnd() {
        return signOutTimeEnd;
    }

    public void setSignOutTimeEnd(DateTime signOutTimeEnd) {
        this.signOutTimeEnd = signOutTimeEnd;
    }

    public DateTime getLateDeadLine() {
        return lateDeadLine;
    }

    public void setLateDeadLine(DateTime lateDeadLine) {
        this.lateDeadLine = lateDeadLine;
    }

    public DateTime getEarlyDeadLine() {
        return earlyDeadLine;
    }

    public void setEarlyDeadLine(DateTime earlyDeadLine) {
        this.earlyDeadLine = earlyDeadLine;
    }
}
