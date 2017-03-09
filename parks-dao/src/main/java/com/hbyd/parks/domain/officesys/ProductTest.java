package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import com.hbyd.parks.domain.managesys.User;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Zhao_d on 2016/12/28.
 */
@Entity
@Table(name = "oa_product_test")
@Audited
public class ProductTest  extends RecoverableEntity{
    private String productName;         //产品型号名称
    private String number;              //编号
    private String extractPosition;     //产品提取位置
    @ManyToOne
    @JoinColumn(name="registerPersonFK")
    @NotAudited
    private User registerPerson;      //登记人
    private String registerDate;        //登记日期
    private String hopeEndDate;         //要求完成日期
    @ManyToOne
    @JoinColumn(name="approvePersonFK")
    @NotAudited
    private User approvePerson;       //批准人
    private String approveDate;         //批准日期
    private Double quantity;            //数量
    private String testBasis;            //测试依据
    private String testType;            //测试类别
    private String testDesc;            //测试功能项描述
    @ManyToOne
    @JoinColumn(name="testPersonFK")
    @NotAudited
    private User testPerson;          //测试人
    private String planBegDate;         //计划开始时间
    private String planEndDate;         //计划结束时间
    private String begDate;             //实际开始时间
    private String endDate;             //实际结束时间
    private String finishedBug;         //已解决BUG编号
    private String unfinishedBug;       //未解决BUG编号
    private String finallyName;         //最终型号名称
    private String finallyPosition;     //最终提取位置
    private String summany;             //总结
    private String note;                //备注

    @ManyToOne
    @JoinColumn(name="assignPersonFK")
    @NotAudited
    private User assignPerson;      //指派处理人

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExtractPosition() {
        return extractPosition;
    }

    public void setExtractPosition(String extractPosition) {
        this.extractPosition = extractPosition;
    }

    public User getRegisterPerson() {
        return registerPerson;
    }

    public void setRegisterPerson(User registerPerson) {
        this.registerPerson = registerPerson;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getHopeEndDate() {
        return hopeEndDate;
    }

    public void setHopeEndDate(String hopeEndDate) {
        this.hopeEndDate = hopeEndDate;
    }

    public User getApprovePerson() {
        return approvePerson;
    }

    public void setApprovePerson(User approvePerson) {
        this.approvePerson = approvePerson;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getTestBasis() {
        return testBasis;
    }

    public void setTestBasis(String testBasis) {
        this.testBasis = testBasis;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getTestDesc() {
        return testDesc;
    }

    public void setTestDesc(String testDesc) {
        this.testDesc = testDesc;
    }

    public User getTestPerson() {
        return testPerson;
    }

    public void setTestPerson(User testPerson) {
        this.testPerson = testPerson;
    }

    public String getPlanBegDate() {
        return planBegDate;
    }

    public void setPlanBegDate(String planBegDate) {
        this.planBegDate = planBegDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getBegDate() {
        return begDate;
    }

    public void setBegDate(String begDate) {
        this.begDate = begDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFinishedBug() {
        return finishedBug;
    }

    public void setFinishedBug(String finishedBug) {
        this.finishedBug = finishedBug;
    }

    public String getUnfinishedBug() {
        return unfinishedBug;
    }

    public void setUnfinishedBug(String unfinishedBug) {
        this.unfinishedBug = unfinishedBug;
    }

    public String getFinallyName() {
        return finallyName;
    }

    public void setFinallyName(String finallyName) {
        this.finallyName = finallyName;
    }

    public String getFinallyPosition() {
        return finallyPosition;
    }

    public void setFinallyPosition(String finallyPosition) {
        this.finallyPosition = finallyPosition;
    }

    public String getSummany() {
        return summany;
    }

    public void setSummany(String summany) {
        this.summany = summany;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getAssignPerson() {
        return assignPerson;
    }

    public void setAssignPerson(User assignPerson) {
        this.assignPerson = assignPerson;
    }
}
