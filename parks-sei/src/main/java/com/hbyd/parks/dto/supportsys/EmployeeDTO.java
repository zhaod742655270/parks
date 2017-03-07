package com.hbyd.parks.dto.supportsys;

import com.hbyd.parks.common.base.RecoverableDTO;

/**
 * Created by allbutone on 2014/8/22.
 */
public class EmployeeDTO extends RecoverableDTO {
    /**
     * 部门ID
     */
    private String deptId;
    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 照片名称
     */
    private String photoName;

    private String empName;
    private String empCode;
    private String empPayNo;
    private String empCaption;
    private String empSex;
    private String empAge;
    private String empIDNo;
    private String empPhone;
    private String empHome;
    private String empNote;

//  员工类型
    private String empTypeId;
    private String empTypeName;
//  职务
    private String empDutyId;
    private String empDutyName;
//  职称
    private String empTitleId;
    private String empTitleName;

    private String empNation;
    private String empState;
    private String userNo;

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    /**
     * 入职时间，格式为：2014-08-04 13:25:31
     */
    private String beginTime;
    /**
     * 离职时间，格式为：2014-08-04 13:25:31
     */
    private String endTime;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpPayNo() {
        return empPayNo;
    }

    public void setEmpPayNo(String empPayNo) {
        this.empPayNo = empPayNo;
    }

    public String getEmpCaption() {
        return empCaption;
    }

    public void setEmpCaption(String empCaption) {
        this.empCaption = empCaption;
    }

    public String getEmpSex() {
        return empSex;
    }

    public void setEmpSex(String empSex) {
        this.empSex = empSex;
    }

    public String getEmpAge() {
        return empAge;
    }

    public void setEmpAge(String empAge) {
        this.empAge = empAge;
    }

    public String getEmpIDNo() {
        return empIDNo;
    }

    public void setEmpIDNo(String empIDNo) {
        this.empIDNo = empIDNo;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getEmpHome() {
        return empHome;
    }

    public void setEmpHome(String empHome) {
        this.empHome = empHome;
    }

    public String getEmpNote() {
        return empNote;
    }

    public void setEmpNote(String empNote) {
        this.empNote = empNote;
    }

    public String getEmpTypeId() {
        return empTypeId;
    }

    public void setEmpTypeId(String empTypeId) {
        this.empTypeId = empTypeId;
    }

    public String getEmpTypeName() {
        return empTypeName;
    }

    public void setEmpTypeName(String empTypeName) {
        this.empTypeName = empTypeName;
    }

    public String getEmpDutyId() {
        return empDutyId;
    }

    public void setEmpDutyId(String empDutyId) {
        this.empDutyId = empDutyId;
    }

    public String getEmpDutyName() {
        return empDutyName;
    }

    public void setEmpDutyName(String empDutyName) {
        this.empDutyName = empDutyName;
    }

    public String getEmpTitleId() {
        return empTitleId;
    }

    public void setEmpTitleId(String empTitleId) {
        this.empTitleId = empTitleId;
    }

    public String getEmpTitleName() {
        return empTitleName;
    }

    public void setEmpTitleName(String empTitleName) {
        this.empTitleName = empTitleName;
    }

    public String getEmpNation() {
        return empNation;
    }

    public void setEmpNation(String empNation) {
        this.empNation = empNation;
    }

    public String getEmpState() {
        return empState;
    }

    public void setEmpState(String empState) {
        this.empState = empState;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
