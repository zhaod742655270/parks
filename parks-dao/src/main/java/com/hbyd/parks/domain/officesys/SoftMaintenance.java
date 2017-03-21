package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import com.hbyd.parks.domain.managesys.User;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Zhao_d on 2016/12/8.
 */
@Entity
@Table(name = "oa_maintenance_software")
@Audited
public class SoftMaintenance extends RecoverableEntity {
    private String projectName;         //项目名称
    private String number;          //编号
    private String productName;         //产品名称
    @ManyToOne
    @JoinColumn(name = "regPersonFK")
    @NotAudited
    private User regPerson;           //登记人
    private String regDate;           //登记日期
    private String hopeEndDate;      //要求完成日期
    @ManyToOne
    @JoinColumn(name="approvePersonFK")
    @NotAudited
    private User approvePerson;       //批准人
    private String approveDate;         //批准日期
    private String approveNote;         //审批备注
    @ManyToOne
    @JoinColumn(name = "contractsFK")
    @NotAudited
    private User contracts;        //项目联系人
    private String phoneNo;             //联系方式
    private String faultDesc;           //故障现象

    private String result;              //最终结论
    @ManyToOne
    @JoinColumn(name = "resultPersonFK",referencedColumnName = "id")
    @NotAudited
    private User resultPerson;        //最终结论填写人
    private String resultDate;          //最终结论填写日期

    @OneToMany(mappedBy = "softMaintenance")
    @NotAudited
    private List<SoftMaintenanceHandle> softMaintenanceHandle;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getHopeEndDate() {
        return hopeEndDate;
    }

    public void setHopeEndDate(String hopeEndDate) {
        this.hopeEndDate = hopeEndDate;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public List<SoftMaintenanceHandle> getSoftMaintenanceHandle() {
        return softMaintenanceHandle;
    }

    public void setSoftMaintenanceHandle(List<SoftMaintenanceHandle> softMaintenanceHandle) {
        this.softMaintenanceHandle = softMaintenanceHandle;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public User getResultPerson() {
        return resultPerson;
    }

    public void setResultPerson(User resultPerson) {
        this.resultPerson = resultPerson;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    public User getRegPerson() {
        return regPerson;
    }

    public void setRegPerson(User regPerson) {
        this.regPerson = regPerson;
    }

    public User getContracts() {
        return contracts;
    }

    public void setContracts(User contracts) {
        this.contracts = contracts;
    }

    public User getAssignPerson() {
        return assignPerson;
    }

    public void setAssignPerson(User assignPerson) {
        this.assignPerson = assignPerson;
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

    public String getApproveNote() {
        return approveNote;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }
}
