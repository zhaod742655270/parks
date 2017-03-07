package com.hbyd.parks.domain.supportsys;

import javax.persistence.*;

/**
 * IO 设备
 */
@Entity
@Table(name = "base_device_io", schema = "dbo", catalog = "parks")
//@DiscriminatorValue(DiscValue.IO)
@PrimaryKeyJoinColumn(
        name = "id", referencedColumnName = "id"
)
@Access(AccessType.PROPERTY)
public class DeviceIo extends Device {
    private String port;
    private String signalType;
    private String inOut;
    private String status;
    private String timeId;
    private String isDown;

    @Basic
    @Column(name = "port")
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Basic
    @Column(name = "signalType")
    public String getSignalType() {
        return signalType;
    }

    public void setSignalType(String signalType) {
        this.signalType = signalType;
    }

    @Basic
    @Column(name = "inOut")
    public String getInOut() {
        return inOut;
    }

    public void setInOut(String inOut) {
        this.inOut = inOut;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "timeId")
    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    @Basic
    @Column(name = "isDown")
    public String getIsDown() {
        return isDown;
    }

    public void setIsDown(String isDown) {
        this.isDown = isDown;
    }

}
