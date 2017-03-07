package com.hbyd.parks.client.queryBean;

import com.hbyd.parks.common.model.QueryBeanEasyUI;

/**
 * Created by Administrator on 2016/10/31.
 */
public class QuotationQueryBean extends QueryBeanEasyUI{

    //所属项目ID
    private String parentProjectID;

    //设备名
    private String name;

    //规格型号
    private String specification;

    //技术参数
    private String technicalParameter;

    //品牌
    private String brand;

    //数量
    private Float quantity;

    //单位
    private String unit;

    //单价
    private Float price;

    //合价
    private Float valence;

    //系数
    private Float ratio;

    //成本单价
    private Float costPrice;

    //成本合价
    private Float costValence;

    //运费
    private Boolean freight;

    //发票
    private String invoice;

    //发票备注
    private String invoiceNote;

    //质保期
    private String warranty;

    //质保期备注
    private String warrantyNote;

    //厂家是否负责调试
    private Boolean debugging;

    //报价有效期
    private String validityPeriod;

    //报价有效期备注
    private String validityPeriodNote;

    //供货周期
    private String supplyCycle;

    //付款方式
    private String payment;

    //备注
    private String note;

    public String getParentProjectID() {
        return parentProjectID;
    }

    public void setParentProjectID(String parentProjectID) {
        this.parentProjectID = parentProjectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getValence() {
        return valence;
    }

    public void setValence(Float valence) {
        this.valence = valence;
    }

    public Float getRatio() {
        return ratio;
    }

    public void setRatio(Float ratio) {
        this.ratio = ratio;
    }

    public Float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Float costPrice) {
        this.costPrice = costPrice;
    }

    public Float getCostValence() {
        return costValence;
    }

    public void setCostValence(Float costValence) {
        this.costValence = costValence;
    }

    public Boolean getFreight() {
        return freight;
    }

    public void setFreight(Boolean freight) {
        this.freight = freight;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getInvoiceNote() {
        return invoiceNote;
    }

    public void setInvoiceNote(String invoiceNote) {
        this.invoiceNote = invoiceNote;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getWarrantyNote() {
        return warrantyNote;
    }

    public void setWarrantyNote(String warrantyNote) {
        this.warrantyNote = warrantyNote;
    }

    public Boolean getDebugging() {
        return debugging;
    }

    public void setDebugging(Boolean debugging) {
        this.debugging = debugging;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getValidityPeriodNote() {
        return validityPeriodNote;
    }

    public void setValidityPeriodNote(String validityPeriodNote) {
        this.validityPeriodNote = validityPeriodNote;
    }

    public String getSupplyCycle() {
        return supplyCycle;
    }

    public void setSupplyCycle(String supplyCycle) {
        this.supplyCycle = supplyCycle;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
