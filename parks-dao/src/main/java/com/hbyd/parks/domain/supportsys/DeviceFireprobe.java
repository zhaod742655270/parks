package com.hbyd.parks.domain.supportsys;

import javax.persistence.*;

/**
 * 消防探头
 */
@Entity
@Table(name = "base_device_fireprobe", schema = "dbo", catalog = "parks")
//@DiscriminatorValue(DiscValue.探头)
@PrimaryKeyJoinColumn(
        name = "id", referencedColumnName = "id"
)
@Access(AccessType.PROPERTY)
public class DeviceFireprobe extends Device {
    private String loopAddr;
    private String commAddr;

    private DeviceDescFiregroup group;

    @ManyToOne
    @JoinColumn(name = "groupFk", referencedColumnName = "id")
    public DeviceDescFiregroup getGroup() {
        return group;
    }

    public void setGroup(DeviceDescFiregroup group) {
        this.group = group;
    }

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
