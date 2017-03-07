package com.hbyd.parks.dto.supportsys;

/**
 * 控制器
 */
public class DeviceControllerDTO extends DeviceDTO {
    private Integer doorCount;

    public Integer getDoorCount() {
        return doorCount;
    }

    public void setDoorCount(Integer doorCount) {
        this.doorCount = doorCount;
    }
}
