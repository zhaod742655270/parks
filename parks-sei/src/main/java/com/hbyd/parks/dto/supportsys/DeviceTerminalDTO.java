package com.hbyd.parks.dto.supportsys;

/**
 * 终端
 */
public class DeviceTerminalDTO extends DeviceDTO {
    private Integer inPortCount;
    private Integer outPortCount;

    public Integer getInPortCount() {
        return inPortCount;
    }

    public void setInPortCount(Integer inPortCount) {
        this.inPortCount = inPortCount;
    }

    public Integer getOutPortCount() {
        return outPortCount;
    }

    public void setOutPortCount(Integer outPortCount) {
        this.outPortCount = outPortCount;
    }
}
