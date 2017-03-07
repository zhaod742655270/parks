package com.hbyd.parks.domain.supportsys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.*;

/**
 * 消防探头组
 */
@Entity
@Table(name = "base_device_desc_firegroup", schema = "dbo", catalog = "parks")
public class DeviceDescFiregroup extends BaseEntity {
    private String name;

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
