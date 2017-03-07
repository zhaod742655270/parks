package com.hbyd.parks.dto.supportsys;

/**
 * 消防探头
 */
public class DeviceFireprobeDTO extends DeviceDTO {
    private String loopAddr;
    private String commAddr;
    private String groupId;
    private String groupName;

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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
