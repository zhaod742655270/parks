package com.hbyd.parks.domain.attendancesys;

import com.hbyd.parks.common.base.BaseEntity;
import com.hbyd.parks.domain.supportsys.Employee;

import javax.persistence.*;
import java.util.Date;

/**
 * 请假记录
 */
@Entity
@Table(name = "attend_leave_record")
public class LeaveRecord extends BaseEntity {

    /**
     * 起始，yyyy-MM-dd HH:mm:SS
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;

    /**
     * 结束，yyyy-MM-dd HH:mm:SS
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDate;

    /**
     * 请假类型
     */
    @ManyToOne
    @JoinColumn(name = "leaveTypeFK", referencedColumnName = "id")
    private LeaveType leaveType;

    /**
     * 请假事由
     */
    private String cause;

    /**
     * 请假人员（员工）
     */
    @ManyToOne//单向关联
    @JoinColumn(name = "employeeFK", referencedColumnName = "id")
    private Employee employee;

//    Getters and Setters

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
