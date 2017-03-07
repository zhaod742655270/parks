package com.hbyd.parks.dto.attendancesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by allbutone on 2014/8/18.
 */
public class LeaveRecordDTO extends BaseDTO {

    /**
     * 起始日期，yyyy-MM-dd HH:mm:SS
     */
    private String fromDate;

    /**
     * 结束日期，yyyy-MM-dd HH:mm:SS
     */
    private String toDate;

    /**
     * 类型ID
     */
    private String typeID;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型符号
     */
    private String typeSymbol;

    /**
     * 请假事由
     */
    private String cause;

    /**
     * 员工ID
     */
    private String empId;

    /**
     * 员工名称
     */
    private String empName;

    /**
     * 部门名称
     */
    private String deptName;


//    Getters and Setters

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeSymbol() {
        return typeSymbol;
    }

    public void setTypeSymbol(String typeSymbol) {
        this.typeSymbol = typeSymbol;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
