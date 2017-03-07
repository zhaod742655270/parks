package com.hbyd.parks.domain.supportsys;

import javax.persistence.*;

/**
 * 终端
 */
@Entity
@Table(name = "base_device_terminal", schema = "dbo", catalog = "parks")
//@DiscriminatorValue(DiscValue.终端)
@PrimaryKeyJoinColumn(
        name = "id", referencedColumnName = "id"
)
@Access(AccessType.PROPERTY)
public class DeviceTerminal extends Device {
    private Integer inPortCount;
    private Integer outPortCount;

    @Basic
    @Column(name = "inPortCount")
    public Integer getInPortCount() {
        return inPortCount;
    }

    public void setInPortCount(Integer inPortCount) {
        this.inPortCount = inPortCount;
    }

    @Basic
    @Column(name = "outPortCount")
    public Integer getOutPortCount() {
        return outPortCount;
    }

    public void setOutPortCount(Integer outPortCount) {
        this.outPortCount = outPortCount;
    }
}
