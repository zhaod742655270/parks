package com.hbyd.parks.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2015/11/26
 */
public class AcceptanceTreeGrid {
    private String id;

    private String purchaserID;

    private String purchaserName;

    private String projectManagerID;

    private String projectManagerName;

    private String contractGatheringID;

    private String contractGatheringName;

    private Float SN;

    private String equipmentName;

    private String brand;

    private String specification;

    private String technicalParameter;

    private String unit;

    private Float quantity;

    private String requiredArrivalTime;

    private String inventory;

    private String budgetNote;

    private String supplier;

    private Float purchaseQuantity;

    private String arrivalTime;

    private Float arrivalQuantity;

    private boolean packages;

    private boolean appearance;

    private boolean datasheet;

    private String productID;

    private boolean POST;

    private String operation;

    private String arrivalNote;
    private List<AcceptanceTreeGrid> children;

    public void addChild(AcceptanceTreeGrid child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getProjectManagerID() {
        return projectManagerID;
    }

    public void setProjectManagerID(String projectManagerID) {
        this.projectManagerID = projectManagerID;
    }

    public String getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    public String getContractGatheringID() {
        return contractGatheringID;
    }

    public void setContractGatheringID(String contractGatheringID) {
        this.contractGatheringID = contractGatheringID;
    }

    public String getContractGatheringName() {
        return contractGatheringName;
    }

    public void setContractGatheringName(String contractGatheringName) {
        this.contractGatheringName = contractGatheringName;
    }

    public Float getSN() {
        return SN;
    }

    public void setSN(Float SN) {
        this.SN = SN;
    }

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

    public String getRequiredArrivalTime() {
        return requiredArrivalTime;
    }

    public void setRequiredArrivalTime(String requiredArrivalTime) {
        this.requiredArrivalTime = requiredArrivalTime;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
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

    public Float getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Float purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
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

    public List<AcceptanceTreeGrid> getChildren() {
        return children;
    }

    public void setChildren(List<AcceptanceTreeGrid> children) {
        this.children = children;
    }
}
