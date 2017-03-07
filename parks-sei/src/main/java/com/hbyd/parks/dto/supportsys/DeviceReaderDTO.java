package com.hbyd.parks.dto.supportsys;

/**
 * 读头
 */
public class DeviceReaderDTO extends DeviceDTO {
    private String readerSn;
    private String isIn;
    private String workMode;
    private String zoneId;
    private String keyA;
    private String keyB;
    private String curProtocol;
    private String isDown;

    public String getReaderSn() {
        return readerSn;
    }

    public void setReaderSn(String readerSn) {
        this.readerSn = readerSn;
    }

    public String getIsIn() {
        return isIn;
    }

    public void setIsIn(String isIn) {
        this.isIn = isIn;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getKeyA() {
        return keyA;
    }

    public void setKeyA(String keyA) {
        this.keyA = keyA;
    }

    public String getKeyB() {
        return keyB;
    }

    public void setKeyB(String keyB) {
        this.keyB = keyB;
    }

    public String getCurProtocol() {
        return curProtocol;
    }

    public void setCurProtocol(String curProtocol) {
        this.curProtocol = curProtocol;
    }

    public String getIsDown() {
        return isDown;
    }

    public void setIsDown(String isDown) {
        this.isDown = isDown;
    }
}
