package com.hbyd.parks.dto.supportsys;

/**
 * 扩展模块
 */
public class DeviceExtmodDTO extends DeviceDTO {
    private String loopAddr;
    private String commAddr;

    public String getLoopAddr() {
        return loopAddr;
    }

    public void setLoopAddr(String loopAddr) {
        this.loopAddr = loopAddr;
    }

    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }
}
