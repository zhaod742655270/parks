package com.hbyd.parks.dto.supportsys;

/**
 * 摄像机
 */
public class DeviceCameraDTO extends DeviceDTO {
    private String channelNum;
    private String haveHolder;

    public String getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    public String getHaveHolder() {
        return haveHolder;
    }

    public void setHaveHolder(String haveHolder) {
        this.haveHolder = haveHolder;
    }
}
