package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Len on 2016/3/2.
 */

public class AcceptancePostilDTO extends BaseDTO {

    private String parentID;

    private String brandPostil;

    private String specificationPostil;

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getBrandPostil() {
        return brandPostil;
    }

    public void setBrandPostil(String brandPostil) {
        this.brandPostil = brandPostil;
    }

    public String getSpecificationPostil() {
        return specificationPostil;
    }

    public void setSpecificationPostil(String specificationPostil) {
        this.specificationPostil = specificationPostil;
    }
}
