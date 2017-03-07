package com.hbyd.parks.domain.supportsys;

import javax.persistence.*;

/**
 * 扩展模块
 */
@Entity
@Table(name = "base_device_extmod", schema = "dbo", catalog = "parks")
//@DiscriminatorValue(DiscValue.扩展模块)
@PrimaryKeyJoinColumn(
        name = "id",
        referencedColumnName = "id"
)
@Access(AccessType.PROPERTY)
public class DeviceExtmod extends Device {
    private String loopAddr;
    private String commAddr;

    @Basic
    @Column(name = "loopAddr")
    public String getLoopAddr() {
        return loopAddr;
    }

    public void setLoopAddr(String loopAddr) {
        this.loopAddr = loopAddr;
    }

    @Basic
    @Column(name = "commAddr")
    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }
}
