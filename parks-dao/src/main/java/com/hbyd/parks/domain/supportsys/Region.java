package com.hbyd.parks.domain.supportsys;

import com.hbyd.parks.common.base.RecoverableEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 区域
 */
@Entity
@Table(name = "base_region", schema = "dbo", catalog = "parks")
public class Region extends RecoverableEntity {
    private String regionName;
    private String regionDesc;

    @ManyToOne
    @JoinColumn(name = "parentFK", referencedColumnName = "id")
    private Region parent;

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

    public Region getParent() {
        return parent;
    }

    public void setParent(Region parent) {
        this.parent = parent;
    }
}
