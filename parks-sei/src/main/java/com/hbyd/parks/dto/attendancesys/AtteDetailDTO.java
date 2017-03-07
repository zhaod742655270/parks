package com.hbyd.parks.dto.attendancesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * 考勤时段
 */
public class AtteDetailDTO extends BaseDTO{
    /**
     * 签到时间
     */
    private String signInTime;

    /**
     * 签退时间
     */
    private String signOutTime;

    /**
     * 迟到分钟数
     */
    private int arriveLateNum;
    /**
     * 早退分钟数
     */
    private int leaveEarlyNum;
    /**
     * 旷工分钟数
     */
    private int absentNum;
    /**
     * 请假分钟数
     */
    private int leaveNum;

    /**
     * 请假类型
     */
    private String leaveType;

    /**
     * 时段类型，即：时段标志位
     */
    private String type;

    public String getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(String signInTime) {
        this.signInTime = signInTime;
    }

    public String getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(String signOutTime) {
        this.signOutTime = signOutTime;
    }

    public int getArriveLateNum() {
        return arriveLateNum;
    }

    public void setArriveLateNum(int arriveLateNum) {
        this.arriveLateNum = arriveLateNum;
    }

    public int getLeaveEarlyNum() {
        return leaveEarlyNum;
    }

    public void setLeaveEarlyNum(int leaveEarlyNum) {
        this.leaveEarlyNum = leaveEarlyNum;
    }

    public int getAbsentNum() {
        return absentNum;
    }

    public void setAbsentNum(int absentNum) {
        this.absentNum = absentNum;
    }

    public int getLeaveNum() {
        return leaveNum;
    }

    public void setLeaveNum(int leaveNum) {
        this.leaveNum = leaveNum;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
