package com.hbyd.parks.common.model;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/3/15
 */
public class AcceptanceQuery extends QueryBeanEasyUI {

    private String equipmentName;

    private String specification;

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
