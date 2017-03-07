package com.hbyd.parks.dto.attendancesys;

import com.hbyd.parks.common.base.BaseDTO;

import java.util.List;

/**
 * 考勤记录
 */
public class AtteInfoDTO extends BaseDTO{

    /**
     * 员工 ID
     */
    private String empFK;
    /**
     * 员工名称
     */
    private String empName;

    /**
     * 部门ID
     */
    private String deptFK;
    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 班次ID
     */
    private String shiftFK;

    /**
     * 暂时不用
     */
    private Boolean eventFlag;

    /**
     * 迟到
     */
    private int arriveLateCount;
    /**
     * 早退
     */
    private int leaveEarlyCount;
    /**
     * 旷工
     */
    private int absentCount;
    /**
     * 请假
     */
    private int leaveCount;

    /**
     * 日期
     */
    private String day;


    /**
     * 考勤时段
     */
    private List<AtteDetailDTO> details;

    public String getEmpFK() {
        return empFK;
    }

    public void setEmpFK(String empFK) {
        this.empFK = empFK;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDeptFK() {
        return deptFK;
    }

    public void setDeptFK(String deptFK) {
        this.deptFK = deptFK;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getShiftFK() {
        return shiftFK;
    }

    public void setShiftFK(String shiftFK) {
        this.shiftFK = shiftFK;
    }

    public Boolean getEventFlag() {
        return eventFlag;
    }

    public void setEventFlag(Boolean eventFlag) {
        this.eventFlag = eventFlag;
    }

    public int getArriveLateCount() {
        return arriveLateCount;
    }

    public void setArriveLateCount(int arriveLateCount) {
        this.arriveLateCount = arriveLateCount;
    }

    public int getLeaveEarlyCount() {
        return leaveEarlyCount;
    }

    public void setLeaveEarlyCount(int leaveEarlyCount) {
        this.leaveEarlyCount = leaveEarlyCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(int absentCount) {
        this.absentCount = absentCount;
    }

    public int getLeaveCount() {
        return leaveCount;
    }

    public void setLeaveCount(int leaveCount) {
        this.leaveCount = leaveCount;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<AtteDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<AtteDetailDTO> details) {
        this.details = details;
    }
}
