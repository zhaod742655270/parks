package com.hbyd.parks.domain.supportsys;

import com.hbyd.parks.common.base.RecoverableEntity;
import com.hbyd.parks.domain.managesys.Department;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 人员实体
 */
@Entity
@Table(name = "base_employee")//HQL 中的表名为：base_employee
@Access(AccessType.FIELD)
//@Table(name = "base_employee", schema = "dbo", catalog = "parks")//HQL 中的表名为：parks.dbo.base_employee
public class Employee extends RecoverableEntity{

    /**
     * 所属部门
     */
    @ManyToOne
    @JoinColumn(name = "departmentFK", referencedColumnName = "id")
    private Department department;

//  Employee 无需关联 ShiftAssign, 毕竟 ShiftAssign 太多，可能会引发性能问题
//  就算启用懒加载，也挺麻烦。

    /**
     * 考勤开关
     */
    private Boolean isInvolve;

    /**
     * 照片名称
     */
    private String photoName;

    /**
     * 姓名
     */
    private String empName;
    /**
     * 编号
     */
    private String empCode;
    /**
     * 工资号
     */
    private String empPayNo;
    /**
     * 拼音缩写
     */
    private String empCaption;
    /**
     * 性别
     */
    private String empSex;
    /**
     * 年龄
     */
    private String empAge;
    /**
     * 身份证号
     */
    private String empIDNo;
    /**
     * 电话
     */
    private String empPhone;
    /**
     * 家庭住址
     */
    private String empHome;
    /**
     * 备注
     */
    private String empNote;
    /**
     * 类型
     */
    @ManyToOne
    @JoinColumn(name = "empType", referencedColumnName = "id")
    private ObjectType empType;
    /**
     * 职务
     */
    @ManyToOne
    @JoinColumn(name = "empDuty", referencedColumnName = "id")
    private ObjectType empDuty;
    /**
     * 职称，头衔
     */
    @ManyToOne
    @JoinColumn(name = "empTitle", referencedColumnName = "id")
    private ObjectType empTitle;
    /**
     * 民族
     */
    private String empNation;
    /**
     * 状态：0 离职，1 在职
     */
    private Boolean empState;

    /**
     * 用户ID
     */
    private String userNo;
    /**
     * 有效开始日期
     */
    private Timestamp beginTime;
    /**
     * 有效结束日期
     */
    private Timestamp endTime;

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Boolean getIsInvolve() {
        return isInvolve;
    }

    public void setIsInvolve(Boolean isInvolve) {
        this.isInvolve = isInvolve;
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

    public ObjectType getEmpType() {
        return empType;
    }

    public void setEmpType(ObjectType empType) {
        this.empType = empType;
    }

    public ObjectType getEmpDuty() {
        return empDuty;
    }

    public void setEmpDuty(ObjectType empDuty) {
        this.empDuty = empDuty;
    }

    public ObjectType getEmpTitle() {
        return empTitle;
    }

    public void setEmpTitle(ObjectType empTitle) {
        this.empTitle = empTitle;
    }

    public String getEmpNation() {
        return empNation;
    }

    public void setEmpNation(String empNation) {
        this.empNation = empNation;
    }

    public Boolean getEmpState() {
        return empState;
    }

    public void setEmpState(Boolean empState) {
        this.empState = empState;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
