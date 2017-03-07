package com.hbyd.parks.dto.supportsys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * 设备和会议室、部门之间的关系描述
 */
public class DeviceDescRelationDTO extends BaseDTO {
    private String roomFK;
    private String deptFK;

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
