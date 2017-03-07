package com.hbyd.parks.domain.attendancesys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.*;

/**
 * 班次中的时间段，时间不涉及日期，按分钟数存储
 */
@Entity
@Table(name = "attend_interval")
//@SecondaryTables({
//        @SecondaryTable(name = "t_ruleLateEarly"),//存储：迟到、早退规则
//        @SecondaryTable(name = "t_ruleSign"),//存储：签到、签退规则
//        @SecondaryTable(name = "t_ruleAttendance")//存储：考勤规则
//})
public class Interval extends BaseEntity {

    /**
     * 时段名称，如：上午时段、下午时段
     */
    private String name;

    /**
     * 时段类型，如：上午，下午等
     */
    private String type;

    /**
     * 时段开始
     */
    @Column(name = "workTimeBegin")
    private String workTimeBegin;

    /**
     * 时段结束
     */
    @Column(name = "workTimeEnd")
    private String workTimeEnd;

    /**
     * 签到开始
     */
    @Column(name = "signInTimeBegin")
    private String signInTimeBegin;

    /**
     * 签退结束
     */
    @Column(name = "signOutTimeEnd")
    private String signOutTimeEnd;

//1. 迟到/早退规则 - LateEarlyRule
    /**
     * 迟到临界时间
     */
//    @Column(name = "lateDeadLine", table = "t_ruleLateEarly")
    @Column(name = "lateDeadLine")
    private String lateDeadLine;

    /**
     * 早退临界时间
     */
//    @Column(name = "earlyDeadLine", table = "t_ruleLateEarly")
    @Column(name = "earlyDeadLine")
    private String earlyDeadLine;

    /**
     * 迟到延时：上班后多少分钟内不计迟到
     */
//    @Column(name = "lateDelay", table = "t_ruleLateEarly")
    @Column(name = "lateDelay")
    private Integer lateDelay;

    /**
     * 早退延时：下班前多少分钟内不计早退
     */
//    @Column(name = "earlyDelay", table = "t_ruleLateEarly")
    @Column(name = "earlyDelay")
    private Integer earlyDelay;

//2. 签到规则 - SignRule
//  2.1 签到方式
    /**
     * 必须签到
     */
//    @Column(name = "needSignIn", table = "t_ruleSign")
    @Column(name = "needSignIn")
    private Boolean needSignIn;

    /**
     * 必须签退
     */
//    @Column(name = "needSignOut", table = "t_ruleSign")
    @Column(name = "needSignOut")
    private Boolean needSignOut;

//  2.2 未签处理
    /**
     * 上班无有效签到记录的认定: 迟到、旷工
     */
//    @Column(name = "invalidSignInAssert", table = "t_ruleSign")
    @Column(name = "invalidSignInAssert")
    private String invalidSignInAssert;

    /**
     * 上班无有效签到记录时，罚时：旷工天数/迟到分钟数
     */
//    @Column(name = "invalidSignInPunish", table = "t_ruleSign")
    @Column(name = "invalidSignInPunish")
    private Float invalidSignInPunish;

    /**
     * 下班无有效签退记录的认定：早退、旷工
     */
//    @Column(name = "invalidSignOutAssert", table = "t_ruleSign")
    @Column(name = "invalidSignOutAssert")
    private String invalidSignOutAssert;

    /**
     * 下班无有效签退记录时，罚时：旷工天数/迟到分钟数
     */
//    @Column(name = "invalidSignOutPunish", table = "t_ruleSign")
    @Column(name = "invalidSignOutPunish")
    private Float invalidSignOutPunish;

//3. 考勤规则
    /**
     * 考勤标准
     * 考勤结果按照什么计算：班次设定时长、实际工作时长
     */
//    @Column(name = "attendanceStandard", table = "t_ruleAttendance")
    @Column(name = "attendanceStandard")
    private String attendanceStandard;

    /**
     * 考勤结果类型：出勤、加班、其他
     */
//    @Column(name = "attendanceType", table = "t_ruleAttendance")
    @Column(name = "attendanceType")
//    if hibernate @Cascade not defined, it defaults to cascade = none
    private String attendanceType;


//  关联的班次

    /**
     * 班次
     */
    @ManyToOne//JPA: By default no operations are cascaded.
    @JoinColumn(name = "shiftFK", referencedColumnName = "id")
    private Shift shift;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWorkTimeBegin() {
        return workTimeBegin;
    }

    public void setWorkTimeBegin(String workTimeBegin) {
        this.workTimeBegin = workTimeBegin;
    }

    public String getWorkTimeEnd() {
        return workTimeEnd;
    }

    public void setWorkTimeEnd(String workTimeEnd) {
        this.workTimeEnd = workTimeEnd;
    }

    public String getSignInTimeBegin() {
        return signInTimeBegin;
    }

    public void setSignInTimeBegin(String signInTimeBegin) {
        this.signInTimeBegin = signInTimeBegin;
    }

    public String getSignOutTimeEnd() {
        return signOutTimeEnd;
    }

    public void setSignOutTimeEnd(String signOutTimeEnd) {
        this.signOutTimeEnd = signOutTimeEnd;
    }

    public String getLateDeadLine() {
        return lateDeadLine;
    }

    public void setLateDeadLine(String lateDeadLine) {
        this.lateDeadLine = lateDeadLine;
    }

    public String getEarlyDeadLine() {
        return earlyDeadLine;
    }

    public void setEarlyDeadLine(String earlyDeadLine) {
        this.earlyDeadLine = earlyDeadLine;
    }

    public Integer getLateDelay() {
        return lateDelay;
    }

    public void setLateDelay(Integer lateDelay) {
        this.lateDelay = lateDelay;
    }

    public Integer getEarlyDelay() {
        return earlyDelay;
    }

    public void setEarlyDelay(Integer earlyDelay) {
        this.earlyDelay = earlyDelay;
    }

    public Boolean getNeedSignIn() {
        return needSignIn;
    }

    public void setNeedSignIn(Boolean needSignIn) {
        this.needSignIn = needSignIn;
    }

    public Boolean getNeedSignOut() {
        return needSignOut;
    }

    public void setNeedSignOut(Boolean needSignOut) {
        this.needSignOut = needSignOut;
    }

    public String getInvalidSignInAssert() {
        return invalidSignInAssert;
    }

    public void setInvalidSignInAssert(String invalidSignInAssert) {
        this.invalidSignInAssert = invalidSignInAssert;
    }

    public Float getInvalidSignInPunish() {
        return invalidSignInPunish;
    }

    public void setInvalidSignInPunish(Float invalidSignInPunish) {
        this.invalidSignInPunish = invalidSignInPunish;
    }

    public String getInvalidSignOutAssert() {
        return invalidSignOutAssert;
    }

    public void setInvalidSignOutAssert(String invalidSignOutAssert) {
        this.invalidSignOutAssert = invalidSignOutAssert;
    }

    public Float getInvalidSignOutPunish() {
        return invalidSignOutPunish;
    }

    public void setInvalidSignOutPunish(Float invalidSignOutPunish) {
        this.invalidSignOutPunish = invalidSignOutPunish;
    }

    public String getAttendanceStandard() {
        return attendanceStandard;
    }

    public void setAttendanceStandard(String attendanceStandard) {
        this.attendanceStandard = attendanceStandard;
    }

    public String getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(String attendanceType) {
        this.attendanceType = attendanceType;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
