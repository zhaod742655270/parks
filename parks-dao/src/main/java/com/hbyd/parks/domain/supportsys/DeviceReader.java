package com.hbyd.parks.domain.supportsys;

import javax.persistence.*;

/**
 * 读头
 */
@Entity
@Table(name = "base_device_reader", schema = "dbo", catalog = "parks")
//@DiscriminatorValue(DiscValue.读头)
@PrimaryKeyJoinColumn(
        name = "id", referencedColumnName = "id"
)
@Access(AccessType.PROPERTY)
public class DeviceReader extends Device {
    private String readerSn;
    private String isIn;
    private String workMode;
    private String zoneId;
    private String keyA;
    private String keyB;
    private String curProtocol;
    private String isDown;

    @Basic
    @Column(name = "readerSn")
    public String getReaderSn() {
        return readerSn;
    }

    public void setReaderSn(String readerSn) {
        this.readerSn = readerSn;
    }

    @Basic
    @Column(name = "isIn")
    public String getIsIn() {
        return isIn;
    }

    public void setIsIn(String isIn) {
        this.isIn = isIn;
    }

    @Basic
    @Column(name = "workMode")
    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    @Basic
    @Column(name = "zoneId")
    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    @Basic
    @Column(name = "keyA")
    public String getKeyA() {
        return keyA;
    }

    public void setKeyA(String keyA) {
        this.keyA = keyA;
    }

    @Basic
    @Column(name = "keyB")
    public String getKeyB() {
        return keyB;
    }

    public void setKeyB(String keyB) {
        this.keyB = keyB;
    }

    @Basic
    @Column(name = "curProtocol")
    public String getCurProtocol() {
        return curProtocol;
    }

    public void setCurProtocol(String curProtocol) {
        this.curProtocol = curProtocol;
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
