package com.hbyd.parks.domain.attendancesys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 考勤时段
 */
@Entity
@Table(name = "attend_atte_detail")
public class AtteDetail extends BaseEntity{

    /**
     * 签到时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date signInTime;

    /**
     * 签退时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date signOutTime;

//    to avoid NullPointerException from Wrapper class, initialize them as fellow:
    /**
     * 迟到分钟数
     */
    private Integer arriveLateNum = new Integer(0);
    /**
     * 早退分钟数
     */
    private Integer leaveEarlyNum = new Integer(0);
    /**
     * 旷工分钟数
     */
    private Integer absentNum = new Integer(0);
    /**
     * 请假分钟数
     */
    private Integer leaveNum = new Integer(0);

    /**
     * 所属的考勤记录
     */
    @ManyToOne
    @JoinColumn(name = "atteInfoFK", referencedColumnName = "id")
    private AtteInfo atteInfo;

    /**
     * 请假类型，如果没有请假，值为 NULL
     */
    @ManyToOne
    @JoinColumn(name = "leaveTypeFK", referencedColumnName = "id")
    private LeaveType leaveType;

    /**
     * 考勤时段对应的时段，多对一，不是一对一!
     */
    @ManyToOne
    @JoinColumn(name = "intervalFK", referencedColumnName = "id")
    private Interval interval;

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public Date getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }

    public Integer getArriveLateNum() {
        return arriveLateNum;
    }

    public void setArriveLateNum(Integer arriveLateNum) {
        this.arriveLateNum = arriveLateNum;
    }

    public Integer getLeaveEarlyNum() {
        return leaveEarlyNum;
    }

    public void setLeaveEarlyNum(Integer leaveEarlyNum) {
        this.leaveEarlyNum = leaveEarlyNum;
    }

    public Integer getAbsentNum() {
        return absentNum;
    }

    public void setAbsentNum(Integer absentNum) {
        this.absentNum = absentNum;
    }

    public Integer getLeaveNum() {
        return leaveNum;
    }

    public void setLeaveNum(Integer leaveNum) {
        this.leaveNum = leaveNum;
    }

    public AtteInfo getAtteInfo() {
        return atteInfo;
    }

    public void setAtteInfo(AtteInfo atteInfo) {
        this.atteInfo = atteInfo;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }
}
