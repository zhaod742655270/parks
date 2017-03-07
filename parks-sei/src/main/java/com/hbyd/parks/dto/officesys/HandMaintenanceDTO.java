package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Zhao_d on 2016/12/14.
 */
public class HandMaintenanceDTO extends BaseDTO {
    private String projectName;     //项目名称
    private String number;          //编号
    private String productName;     //产品名称型号
    private Double quantity;            //数量
    private String registerPersonID;      //登记人
    private String registerPersonName;
    private String registerDate;        //登记日期
    private String hopeEndDate;         //要求完成日期
    private String approvePersonID;       //批准人
    private String approvePersonName;
    private String approveDate;         //批准日期
    private String faultContent;        //故障上报现象\测试内容
    private String reportPersonID;      //上报人
    private String reportPersonName;
    private String productNo;           //产品编号
    private String firmwareVersion;          //固件版本号
    private String handwareVersion;         //硬件版本号
    private String repairBasis;            //维修\测试依据
    private String repairType;             //维修\测试类别
    private String faultVerify;         //故障核实情况
    private String verifyPersonID;        //核实人
    private String verifyPersonName;
    private String techAnalysis;        //技术分析
    private String analyPersonID;         //分析人
    private String analyPersonName;
    private String repairContent;       //维修内容
    private String repairPersonID;        //维修人
    private String repairPersonName;
    private String repairResult;        //维修结果
    private String testPersonID;          //测试人
    private String testPersonName;
    private String checkCost;            //检测费
    private String manhourCost;          //工时费
    private String materialsCost;        //材料费
    private String repairCost;           //维修成本(合计)

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

    public String getTechAnalysis() {
        return techAnalysis;
    }

    public void setTechAnalysis(String techAnalysis) {
        this.techAnalysis = techAnalysis;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    public String getRepairResult() {
        return repairResult;
    }

    public void setRepairResult(String repairResult) {
        this.repairResult = repairResult;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRegisterPersonID() {
        return registerPersonID;
    }

    public void setRegisterPersonID(String registerPersonID) {
        this.registerPersonID = registerPersonID;
    }

    public String getRegisterPersonName() {
        return registerPersonName;
    }

    public void setRegisterPersonName(String registerPersonName) {
        this.registerPersonName = registerPersonName;
    }

    public String getApprovePersonID() {
        return approvePersonID;
    }

    public void setApprovePersonID(String approvePersonID) {
        this.approvePersonID = approvePersonID;
    }

    public String getApprovePersonName() {
        return approvePersonName;
    }

    public void setApprovePersonName(String approvePersonName) {
        this.approvePersonName = approvePersonName;
    }

    public String getReportPersonID() {
        return reportPersonID;
    }

    public void setReportPersonID(String reportPersonID) {
        this.reportPersonID = reportPersonID;
    }

    public String getReportPersonName() {
        return reportPersonName;
    }

    public void setReportPersonName(String reportPersonName) {
        this.reportPersonName = reportPersonName;
    }

    public String getVerifyPersonID() {
        return verifyPersonID;
    }

    public void setVerifyPersonID(String verifyPersonID) {
        this.verifyPersonID = verifyPersonID;
    }

    public String getVerifyPersonName() {
        return verifyPersonName;
    }

    public void setVerifyPersonName(String verifyPersonName) {
        this.verifyPersonName = verifyPersonName;
    }

    public String getAnalyPersonID() {
        return analyPersonID;
    }

    public void setAnalyPersonID(String analyPersonID) {
        this.analyPersonID = analyPersonID;
    }

    public String getAnalyPersonName() {
        return analyPersonName;
    }

    public void setAnalyPersonName(String analyPersonName) {
        this.analyPersonName = analyPersonName;
    }

    public String getRepairPersonID() {
        return repairPersonID;
    }

    public void setRepairPersonID(String repairPersonID) {
        this.repairPersonID = repairPersonID;
    }

    public String getRepairPersonName() {
        return repairPersonName;
    }

    public void setRepairPersonName(String repairPersonName) {
        this.repairPersonName = repairPersonName;
    }

    public String getTestPersonID() {
        return testPersonID;
    }

    public void setTestPersonID(String testPersonID) {
        this.testPersonID = testPersonID;
    }

    public String getTestPersonName() {
        return testPersonName;
    }

    public void setTestPersonName(String testPersonName) {
        this.testPersonName = testPersonName;
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
}
