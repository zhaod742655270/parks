package com.hbyd.parks.dto.supportsys;

/**
 * 电子围栏
 */
public class DeviceElecfenceDTO extends DeviceDTO {
    private String beginInfo;
    private String endInfo;
    private int sn;

    public String getBeginInfo() {
        return beginInfo;
    }

    public void setBeginInfo(String beginInfo) {
        this.beginInfo = beginInfo;
    }

    public String getEndInfo() {
        return endInfo;
    }

    public void setEndInfo(String endInfo) {
        this.endInfo = endInfo;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }
}
