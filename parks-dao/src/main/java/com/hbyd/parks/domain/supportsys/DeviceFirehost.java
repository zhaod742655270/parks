package com.hbyd.parks.domain.supportsys;

import javax.persistence.*;

/**
 * 主机
 */
@Entity
@Table(name = "base_device_firehost", schema = "dbo", catalog = "parks")
//@DiscriminatorValue(DiscValue.主机)
@PrimaryKeyJoinColumn(
        name = "id", referencedColumnName = "id"
)
@Access(AccessType.PROPERTY)
public class DeviceFirehost extends Device {
    private String commAddr;

    @Basic
    @Column(name = "commAddr")
    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }
}
