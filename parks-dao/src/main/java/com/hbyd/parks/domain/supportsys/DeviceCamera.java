package com.hbyd.parks.domain.supportsys;

import javax.persistence.*;

/**
 * 摄像机
 */
@Entity
@Table(name = "base_device_camera", schema = "dbo", catalog = "parks")
//@DiscriminatorValue(DiscValue.摄像机)
@PrimaryKeyJoinColumn(
        name = "id", referencedColumnName = "id"
)
@Access(AccessType.PROPERTY)
public class DeviceCamera extends Device {
    private String channelNum;
    private String haveHolder;

    @Basic
    @Column(name = "channelNum")
    public String getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    @Basic
    @Column(name = "haveHolder")
    public String getHaveHolder() {
        return haveHolder;
    }

    public void setHaveHolder(String haveHolder) {
        this.haveHolder = haveHolder;
    }
}
