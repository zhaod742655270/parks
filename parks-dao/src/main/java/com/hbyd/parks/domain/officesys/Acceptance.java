package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import com.hbyd.parks.domain.managesys.User;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/4/25
 */
@Entity
@Table(name = "oa_procuremen_acceptance")
@Audited
public class Acceptance extends RecoverableEntity {

    @ManyToOne
    @JoinColumn(name = "purchaserFK")
    @NotAudited
    private User purchaser;

    @ManyToOne
    @JoinColumn(name = "projectManagerFK")
    @NotAudited
    private User projectManager;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "projectFK")
    @NotAudited
    private ContractGathering contractGathering;

    private Float SN;

    private String equipmentName;

    private String brand;

    private String specification;

    private String technicalParameter;

    private String unit;

    private Float quantity;

    private Double price;

    private Double valence;

    private String requiredArrivalTime;

    private String inventory;

    private String testNote;

    private String budgetNote;

    private String supplier;

    private Float purchaseQuantity;

    private String arrivalTime;

    private Float arrivalQuantity;

    private Float remainQuantity;

    private String direction;

    private boolean packages;

    private boolean appearance;

    private boolean datasheet;

    @NotAudited
    private String productID;

    private boolean POST;

    private String operation;

    private String arrivalNote;

    private String runnedTime;     //设备运行时间

    @OneToOne(mappedBy = "acceptance" ,cascade = CascadeType.DETACH)
    private AcceptancePostil acceptancePostil;


    //是否定制部分
    private boolean custom;         //是否定制

    private String insulationLeather;   //绝缘皮标志

    private String diameter;        //线径

    private String allLength;       //整长

    private String thick;           //壁厚

    private String outsideDiameter;     //外径

    private String size;            //尺寸

    private String color;           //颜色

    private String material;        //材质

    private String boreDistance;        //孔距

    //审核功能
    private Boolean examine;   //审核

    private Boolean purchaseOperate;   //采购数量是否已更新




    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getTechnicalParameter() {
        return technicalParameter;
    }

    public void setTechnicalParameter(String technicalParameter) {
        this.technicalParameter = technicalParameter;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(Float remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public Float getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Float purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Float getSN() {
        return SN;
    }

    public void setSN(Float SN) {
        this.SN = SN;
    }

    public String getBudgetNote() {
        return budgetNote;
    }

    public void setBudgetNote(String budgetNote) {
        this.budgetNote = budgetNote;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getRequiredArrivalTime() {
        return requiredArrivalTime;
    }

    public void setRequiredArrivalTime(String requiredArrivalTime) {
        this.requiredArrivalTime = requiredArrivalTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Float getArrivalQuantity() {
        return arrivalQuantity;
    }

    public void setArrivalQuantity(Float arrivalQuantity) {
        this.arrivalQuantity = arrivalQuantity;
    }

    public boolean isPackages() {
        return packages;
    }

    public void setPackages(boolean packages) {
        this.packages = packages;
    }

    public boolean isAppearance() {
        return appearance;
    }

    public void setAppearance(boolean appearance) {
        this.appearance = appearance;
    }

    public boolean isDatasheet() {
        return datasheet;
    }

    public void setDatasheet(boolean datasheet) {
        this.datasheet = datasheet;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public boolean isPOST() {
        return POST;
    }

    public void setPOST(boolean POST) {
        this.POST = POST;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getArrivalNote() {
        return arrivalNote;
    }

    public void setArrivalNote(String arrivalNote) {
        this.arrivalNote = arrivalNote;
    }

    public User getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(User purchaser) {
        this.purchaser = purchaser;
    }

    public User getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public ContractGathering getContractGathering() {
        return contractGathering;
    }

    public void setContractGathering(ContractGathering contractGathering) {
        this.contractGathering = contractGathering;
    }

    public AcceptancePostil getAcceptancePostil() {
        return acceptancePostil;
    }

    public void setAcceptancePostil(AcceptancePostil acceptancePostil) {
        this.acceptancePostil = acceptancePostil;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public String getInsulationLeather() {
        return insulationLeather;
    }

    public void setInsulationLeather(String insulationLeather) {
        this.insulationLeather = insulationLeather;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getAllLength() {
        return allLength;
    }

    public void setAllLength(String allLength) {
        this.allLength = allLength;
    }

    public String getThick() {
        return thick;
    }

    public void setThick(String thick) {
        this.thick = thick;
    }

    public String getOutsideDiameter() {
        return outsideDiameter;
    }

    public void setOutsideDiameter(String outsideDiameter) {
        this.outsideDiameter = outsideDiameter;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getBoreDistance() {
        return boreDistance;
    }

    public void setBoreDistance(String boreDistance) {
        this.boreDistance = boreDistance;
    }

    public Boolean getExamine() {
        return examine;
    }

    public void setExamine(Boolean examine) {
        if(examine == true){
            this.examine = true;
        }else{
            this.examine = false;
        }
    }

    public Boolean getPurchaseOperate() {
        return purchaseOperate;
    }

    public void setPurchaseOperate(Boolean purchaseOperate) {
        if(purchaseOperate == true){
            this.purchaseOperate = true;
        }else{
            this.purchaseOperate = false;
        }
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getValence() {
        return valence;
    }

    public void setValence(Double valence) {
        this.valence = valence;
    }

    public String getTestNote() {
        return testNote;
    }

    public void setTestNote(String testNote) {
        this.testNote = testNote;
    }

    public String getRunnedTime() {
        return runnedTime;
    }

    public void setRunnedTime(String runnedTime) {
        this.runnedTime = runnedTime;
    }
}
