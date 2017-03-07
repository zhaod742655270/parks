package com.hbyd.parks.client.queryBean;

import com.hbyd.parks.common.model.QueryBeanEasyUI;

/**
 * 刷卡事件的查询 Bean, 用于封装查询参数, 为了方便分页，该 Bean 继承自 QueryBeanEasyUI
 */
public class DeviceQueryBean extends QueryBeanEasyUI{

    private String descInfo;
    private String roomId;


    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
