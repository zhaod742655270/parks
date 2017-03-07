package com.hbyd.parks.domain.attendancesys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 考勤记录
 */
@Entity
@Table(name = "attend_atte_info")
public class AtteInfo extends BaseEntity {
    private String empFK;
    private String empName;
    private String deptFK;
    private String deptName;
    private Boolean eventFlag;
//    to avoid NullPointerException from Wrapper Class, initialize them as fellow:
    /**
     * 迟到分钟数
     */
    private Integer arriveLateCount = new Integer(0);
    /**
     * 早退分钟数
     */
    private Integer leaveEarlyCount = new Integer(0);
    /**
     * 旷工分钟数
     */
    private Integer absentCount = new Integer(0);
    /**
     * 请假分钟数
     */
    private Integer leaveCount = new Integer(0);
    /**
     * 哪一天的考勤记录
     */
    @Temporal(TemporalType.DATE)
    private Date day;

    @OneToMany(mappedBy = "atteInfo")
    private List<AtteDetail> details;

    /**
     * 考勤记录对应的班次，多对一，不是一对一!
     */
    @ManyToOne
    @JoinColumn(name = "shiftFK", referencedColumnName = "id")
    private Shift shift;

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public String getEmpFK() {
        return empFK;
    }

    public void setEmpFK(String empId) {
        this.empFK = empId;
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

    public void setDeptFK(String deptId) {
        this.deptFK = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Boolean getEventFlag() {
        return eventFlag;
    }

    public void setEventFlag(Boolean eventFlag) {
        this.eventFlag = eventFlag;
    }

    public Integer getArriveLateCount() {
        return arriveLateCount;
    }

    public void setArriveLateCount(Integer arriveLateCount) {
        this.arriveLateCount = arriveLateCount;
    }

    public Integer getLeaveEarlyCount() {
        return leaveEarlyCount;
    }

    public void setLeaveEarlyCount(Integer leaveEarlyCount) {
        this.leaveEarlyCount = leaveEarlyCount;
    }

    public Integer getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(Integer absentCount) {
        this.absentCount = absentCount;
    }

    public Integer getLeaveCount() {
        return leaveCount;
    }

    public void setLeaveCount(Integer leaveCount) {
        this.leaveCount = leaveCount;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public List<AtteDetail> getDetails() {
        return details;
    }

    public void setDetails(List<AtteDetail> details) {
        this.details = details;
    }
}
