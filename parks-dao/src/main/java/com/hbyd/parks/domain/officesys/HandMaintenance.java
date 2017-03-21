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
 * Created by Zhao_d on 2016/12/14.
 */
@Entity
@Table(name = "oa_maintenance_hand")
@Audited
public class HandMaintenance extends RecoverableEntity {
    private String projectName;     //项目名称
    private String number;          //编号
    private String productName;     //产品名称型号
    private Double quantity;            //数量
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
    private String approveNote;         //审批备注
    private String faultContent;        //故障上报现象\测试内容
    @ManyToOne
    @JoinColumn(name="reportPersonFK")
    @NotAudited
    private User reportPerson;        //上报人
    private String productNo;           //产品编号
    private String firmwareVersion;          //固件版本号
    private String handwareVersion;         //硬件版本号
    private String repairBasis;            //维修\测试依据
    private String repairType;             //维修\测试类别
    private String faultVerify;         //故障核实情况
    @ManyToOne
    @JoinColumn(name="verifyPersonFK")
    @NotAudited
    private User verifyPerson;        //核实人
    private String techAnalysis;        //技术分析
    @ManyToOne
    @JoinColumn(name="analyPersonFK")
    @NotAudited
    private User analyPerson;         //分析人
    private String repairContent;       //维修内容
    @ManyToOne
    @JoinColumn(name="repairPersonFK")
    @NotAudited
    private User repairPerson;        //维修人
    private String repairResult;        //维修结果
    @ManyToOne
    @JoinColumn(name="testPersonFK")
    @NotAudited
    private User testPerson;          //测试人
    private String checkCost;            //检测费
    private String manhourCost;          //工时费
    private String materialsCost;        //材料费
    private String repairCost;           //维修成本(合计)

    @ManyToOne
    @JoinColumn(name="assignPersonFK")
    @NotAudited
    private User assignPerson;      //指派处理人

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
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

    public String getFaultContent() {
        return faultContent;
    }

    public void setFaultContent(String faultContent) {
        this.faultContent = faultContent;
    }

    public User getReportPerson() {
        return reportPerson;
    }

    public void setReportPerson(User reportPerson) {
        this.reportPerson = reportPerson;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getHandwareVersion() {
        return handwareVersion;
    }

    public void setHandwareVersion(String handwareVersion) {
        this.handwareVersion = handwareVersion;
    }

    public String getRepairBasis() {
        return repairBasis;
    }

    public void setRepairBasis(String repairBasis) {
        this.repairBasis = repairBasis;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public String getFaultVerify() {
        return faultVerify;
    }

    public void setFaultVerify(String faultVerify) {
        this.faultVerify = faultVerify;
    }

    public User getVerifyPerson() {
        return verifyPerson;
    }

    public void setVerifyPerson(User verifyPerson) {
        this.verifyPerson = verifyPerson;
    }

    public String getTechAnalysis() {
        return techAnalysis;
    }

    public void setTechAnalysis(String techAnalysis) {
        this.techAnalysis = techAnalysis;
    }

    public User getAnalyPerson() {
        return analyPerson;
    }

    public void setAnalyPerson(User analyPerson) {
        this.analyPerson = analyPerson;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    public User getRepairPerson() {
        return repairPerson;
    }

    public void setRepairPerson(User repairPerson) {
        this.repairPerson = repairPerson;
    }

    public String getRepairResult() {
        return repairResult;
    }

    public void setRepairResult(String repairResult) {
        this.repairResult = repairResult;
    }

    public User getTestPerson() {
        return testPerson;
    }

    public void setTestPerson(User testPerson) {
        this.testPerson = testPerson;
    }

    public String getCheckCost() {
        return checkCost;
    }

    public void setCheckCost(String checkCost) {
        this.checkCost = checkCost;
    }

    public String getManhourCost() {
        return manhourCost;
    }

    public void setManhourCost(String manhourCost) {
        this.manhourCost = manhourCost;
    }

    public String getMaterialsCost() {
        return materialsCost;
    }

    public void setMaterialsCost(String materialsCost) {
        this.materialsCost = materialsCost;
    }

    public String getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(String repairCost) {
        this.repairCost = repairCost;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getAssignPerson() {
        return assignPerson;
    }

    public void setAssignPerson(User assignPerson) {
        this.assignPerson = assignPerson;
    }

    public String getApproveNote() {
        return approveNote;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }
}
