package com.hbyd.parks.dto.supportsys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * 设备
 */
public class DeviceDTO extends BaseDTO {
    private String oneType;
    private String deviceId;
    private String name;
    private String position;
    private String descInfo;
    private String maker;
    private String state;

//  上级设备
    private String parentId;
    private String parentName;
    private String parentDeviceId;

//  区域信息
    private String regionId;
    private String regionName;
    private String regionDesc;

//  设备类型
    private String typeId;
    private String typeDesc;

//  其他外键：描述设备的归属关系
    private String roomFK;
    private String deptFK;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getOneType() {
        return oneType;
    }

    public void setOneType(String oneType) {
        this.oneType = oneType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

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

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentDeviceId() {
        return parentDeviceId;
    }

    public void setParentDeviceId(String parentDeviceId) {
        this.parentDeviceId = parentDeviceId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionDesc() {
        return regionDesc;
    }

    public void setRegionDesc(String regionDesc) {
        this.regionDesc = regionDesc;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
    public String getRoomFK() {
        return roomFK;
    }

    public void setRoomFK(String roomFK) {
        this.roomFK = roomFK;
    }

    public String getDeptFK() {
        return deptFK;
    }

    public void setDeptFK(String deptFK) {
        this.deptFK = deptFK;
    }
}
