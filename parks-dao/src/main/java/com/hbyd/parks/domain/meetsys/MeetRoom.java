package com.hbyd.parks.domain.meetsys;

import com.hbyd.parks.common.base.BaseEntity;
import com.hbyd.parks.domain.supportsys.Region;

import javax.persistence.*;

/**
 * 会议室
 */
@Entity
@Table(name = "meet_room", schema = "dbo", catalog = "parks")
@Access(AccessType.PROPERTY)
public class MeetRoom extends BaseEntity {
    private String name;
    private String descInfo;
    private String capacity;

    private Region region;

    @ManyToOne
    @JoinColumn(name = "regionFk", referencedColumnName = "id")
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "descInfo")
    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    @Basic
    @Column(name = "capacity")
    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
