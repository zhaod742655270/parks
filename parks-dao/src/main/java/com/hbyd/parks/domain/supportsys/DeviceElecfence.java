package com.hbyd.parks.domain.supportsys;

import javax.persistence.*;

/**
 * 电子围栏
 */
@Entity
@Table(name = "base_device_elecfence", schema = "dbo", catalog = "parks")
//@DiscriminatorValue(DiscValue.电子围栏)
@PrimaryKeyJoinColumn(
        name = "id", referencedColumnName = "id"
)
@Access(AccessType.PROPERTY)
public class DeviceElecfence extends Device {
    private String beginInfo;
    private String endInfo;
    private int sn;

    @Basic
    @Column(name = "beginInfo")
    public String getBeginInfo() {
        return beginInfo;
    }

    public void setBeginInfo(String beginInfo) {
        this.beginInfo = beginInfo;
    }

    @Basic
    @Column(name = "endInfo")
    public String getEndInfo() {
        return endInfo;
    }

    public void setEndInfo(String endInfo) {
        this.endInfo = endInfo;
    }

    @Basic
    @Column(name = "sn")
    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }
}
