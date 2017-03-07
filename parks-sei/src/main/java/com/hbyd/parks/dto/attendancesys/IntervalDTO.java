package com.hbyd.parks.dto.attendancesys;


import com.hbyd.parks.common.base.BaseDTO;

/**
 * 时段
 */
public class IntervalDTO extends BaseDTO {

    /**
     * 时段名称
     */
    private String name;

    /**
     * 时段类型
     */
    private String type;

    /**
     * 上班时间标志  0当天，1后一天
     */
    private int workTimeBeginFlag;
    /**
     * 上班时间
     */
    private String workTimeBegin;
    /**
     * 上班时间  列表中显示的格式
     */
    private String workTimeBeginFormat;

    /**
     * 下班时间标志 0当天，1后一天
     */
    private int workTimeEndFlag;
    /**
     * 下班时间
     */
    private String workTimeEnd;
    /**
     * 下班时间  列表中显示的格式
     */
    private String workTimeEndFormat;
    /**
     * 签到起始时间标志 0当天，1后一天
     */
    private int signInTimeBeginFlag;
    /**
     * 签到起始时间
     */
    private String signInTimeBegin;
    /**
     * 签到起始时间 列表中显示的格式
     */
    private String signInTimeBeginFormat;


    /**
     * 签退结束时间标志 0当天，1后一天
     */
    private int signOutTimeEndFlag;
    /**
     * 签退结束时间
     */
    private String signOutTimeEnd;
    /**
     * 签退结束时间 列表中显示的格式
     */
    private String signOutTimeEndFormat;

//1. 迟到/早退规则 - LateEarlyRule
    /**
     * 迟到结束时间标志 0当天，1后一天
     */
    private int lateDeadLineFlag;
    /**
     * 迟到结束时间
     */
    private String lateDeadLine;
    /**
     * 早退开始时间标志 0当天，1后一天
     */
    private int earlyDeadLineFlag;
    /**
     * 早退开始时间
     */
    private String earlyDeadLine;

    /**
     * 迟到延时：上班后多少分钟内不计迟到
     */
    private int lateDelay;

    /**
     * 早退延时：下班前多少分钟内不计早退
     */
    private int earlyDelay;

//2. 签到规则 - SignRule
//  2.1 签到方式
    /**
     * 必须签到
     */
    private boolean needSignIn;

    /**
     * 必须签退
     */
    private boolean needSignOut;

//  2.2 未签处理
    /**
     * 上班无有效签到记录的认定: 迟到、旷工
     */
    private String invalidSignInAssert;

    /**
     * 上班无有效签到记录时，罚时：旷工天数/迟到分钟数
     */
    private float invalidSignInPunish;

    /**
     * 下班无有效签退记录的认定：早退、旷工
     */
    private String invalidSignOutAssert;

    /**
     * 下班无有效签退记录时，罚时：旷工天数/迟到分钟数
     */
    private float invalidSignOutPunish;

//3. 考勤规则
    /**
     * 考勤标准
     * 考勤结果按照什么计算：班次设定时长、实际工作时长
     */
    private String attendanceStandard;

    /**
     * 考勤结果类型：出勤、加班、其他
     */
    private String attendanceType;

    /**
     * 班次
     */
    private String shiftID;


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

    public int getLateDelay() {
        return lateDelay;
    }

    public void setLateDelay(int lateDelay) {
        this.lateDelay = lateDelay;
    }

    public int getEarlyDelay() {
        return earlyDelay;
    }

    public void setEarlyDelay(int earlyDelay) {
        this.earlyDelay = earlyDelay;
    }

    public boolean isNeedSignIn() {
        return needSignIn;
    }

    public void setNeedSignIn(boolean needSignIn) {
        this.needSignIn = needSignIn;
    }

    public boolean isNeedSignOut() {
        return needSignOut;
    }

    public void setNeedSignOut(boolean needSignOut) {
        this.needSignOut = needSignOut;
    }

    public String getInvalidSignInAssert() {
        return invalidSignInAssert;
    }

    public void setInvalidSignInAssert(String invalidSignInAssert) {
        this.invalidSignInAssert = invalidSignInAssert;
    }

    public float getInvalidSignInPunish() {
        return invalidSignInPunish;
    }

    public void setInvalidSignInPunish(float invalidSignInPunish) {
        this.invalidSignInPunish = invalidSignInPunish;
    }

    public String getInvalidSignOutAssert() {
        return invalidSignOutAssert;
    }

    public void setInvalidSignOutAssert(String invalidSignOutAssert) {
        this.invalidSignOutAssert = invalidSignOutAssert;
    }

    public float getInvalidSignOutPunish() {
        return invalidSignOutPunish;
    }

    public void setInvalidSignOutPunish(float invalidSignOutPunish) {
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

    public String getShiftID() {
        return shiftID;
    }

    public void setShiftID(String shiftID) {
        this.shiftID = shiftID;
    }


    public int getWorkTimeBeginFlag() {
        return workTimeBeginFlag;
    }

    public void setWorkTimeBeginFlag(int workTimeBeginFlag) {
        this.workTimeBeginFlag = workTimeBeginFlag;
    }

    public int getWorkTimeEndFlag() {
        return workTimeEndFlag;
    }

    public void setWorkTimeEndFlag(int workTimeEndFlag) {
        this.workTimeEndFlag = workTimeEndFlag;
    }

    public int getSignInTimeBeginFlag() {
        return signInTimeBeginFlag;
    }

    public void setSignInTimeBeginFlag(int signInTimeBeginFlag) {
        this.signInTimeBeginFlag = signInTimeBeginFlag;
    }

    public int getSignOutTimeEndFlag() {
        return signOutTimeEndFlag;
    }

    public void setSignOutTimeEndFlag(int signOutTimeEndFlag) {
        this.signOutTimeEndFlag = signOutTimeEndFlag;
    }

    public int getLateDeadLineFlag() {
        return lateDeadLineFlag;
    }

    public void setLateDeadLineFlag(int lateDeadLineFlag) {
        this.lateDeadLineFlag = lateDeadLineFlag;
    }

    public int getEarlyDeadLineFlag() {
        return earlyDeadLineFlag;
    }

    public void setEarlyDeadLineFlag(int earlyDeadLineFlag) {
        this.earlyDeadLineFlag = earlyDeadLineFlag;
    }

    public String getWorkTimeBeginFormat() {
        return workTimeBeginFormat;
    }

    public void setWorkTimeBeginFormat(String workTimeBeginFormat) {
        this.workTimeBeginFormat = workTimeBeginFormat;
    }

    public String getWorkTimeEndFormat() {
        return workTimeEndFormat;
    }

    public void setWorkTimeEndFormat(String workTimeEndFormat) {
        this.workTimeEndFormat = workTimeEndFormat;
    }

    public String getSignInTimeBeginFormat() {
        return signInTimeBeginFormat;
    }

    public void setSignInTimeBeginFormat(String signInTimeBeginFormat) {
        this.signInTimeBeginFormat = signInTimeBeginFormat;
    }

    public String getSignOutTimeEndFormat() {
        return signOutTimeEndFormat;
    }

    public void setSignOutTimeEndFormat(String signOutTimeEndFormat) {
        this.signOutTimeEndFormat = signOutTimeEndFormat;
    }
}