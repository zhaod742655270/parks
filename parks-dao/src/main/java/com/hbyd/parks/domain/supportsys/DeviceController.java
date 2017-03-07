package com.hbyd.parks.domain.supportsys;

import javax.persistence.*;

/**
 * 控制器
 */
@Entity
@Table(name = "base_device_controller", schema = "dbo", catalog = "parks")
//@DiscriminatorValue(DiscValue.控制器)
@PrimaryKeyJoinColumn(
        name = "id", referencedColumnName = "id"
)
@Access(AccessType.PROPERTY)
public class DeviceController extends Device {
    private Integer doorCount;

    @Basic
    @Column(name = "doorCount")
    public Integer getDoorCount() {
        return doorCount;
    }

    public void setDoorCount(Integer doorCount) {
        this.doorCount = doorCount;
    }
}
