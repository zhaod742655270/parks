package com.hbyd.parks.domain.supportsys;

import javax.persistence.*;

/**
 * 设备和会议室、部门之间的关系，只是关系描述，因此：
 * 1. 设备外键充当主键，因为表示的是设备和其他实体的关联
 * 2. 不采用对象形式表示关联
 */
@Entity
@Table(name = "base_device_desc_relation", schema = "dbo", catalog = "parks")
@Access(AccessType.FIELD)
//@AttributeOverride(name = "id", column = @Column(name = "deviceFK"))//会覆写映射，但主键生成策略没变
public class DeviceDescRelation {
    @Id
    private String id;//这个 ID 需要手动设定为设备外键

    @Column(name = "roomFK")
    private String roomFK;

    @Column(name = "deptFK")
    private String deptFK;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
