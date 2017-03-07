package com.hbyd.parks.dto.supportsys;

import com.hbyd.parks.common.base.RecoverableDTO;

/**
 * 区域实体
 */
public class RegionDTO extends RecoverableDTO {
    private String regionName;
    private String regionDesc;
    private String parentFK;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionDesc() {
        return regionDesc;
    }

    public void setRegionDesc(String regionDesc) {
        this.regionDesc = regionDesc;
    }

    public String getParentFK() {
        return parentFK;
    }

    public void setParentFK(String parentFK) {
        this.parentFK = parentFK;
    }
}
