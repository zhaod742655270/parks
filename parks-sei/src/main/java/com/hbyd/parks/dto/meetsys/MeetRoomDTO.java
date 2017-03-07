package com.hbyd.parks.dto.meetsys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * 会议室
 */
public class MeetRoomDTO extends BaseDTO {
    private String name;
    private String descInfo;
    private String capacity;

//  关联信息
    private String regionId;
    private String regionName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
