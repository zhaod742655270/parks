package com.hbyd.parks.doorsys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.domain.doorsys.AccessEventInfo;
import com.hbyd.parks.dto.doorsys.AccessEventInfoDTO;
import com.hbyd.parks.dto.doorsys.AccessEventStatusDTO;
import com.hbyd.parks.ws.doorsys.AccessEventInfoWS;

/**
 * 服务实现：刷卡记录
 */
public class AccessEventInfoWSImpl extends BaseWSImpl<AccessEventInfoDTO, AccessEventInfo> implements AccessEventInfoWS {
    @Override
    public AccessEventStatusDTO getStatus(){
//      personOrCarFlag: 0-人， 1-车
//      personType: 0-员工，1-访客

//      园区内员工数
        String sql_1 = "select count(*) from door_access_event_info_latest where personOrCarFlag = 0 and personType = 0";
//      园区内访客数
        String sql_2 = "select count(*) from door_access_event_info_latest where personOrCarFlag = 0 and personType = 1";
//      园区内员工车辆数
        String sql_3 = "select count(*) from door_access_event_info_latest where personOrCarFlag = 1 and personType = 0";
//      园区内访客车辆数
        String sql_4 = "select count(*) from door_access_event_info_latest where personOrCarFlag = 1 and personType = 1";

        AccessEventStatusDTO dto = new AccessEventStatusDTO();
        dto.setEmpCnt(baseDao.getRowCount(sql_1));
        dto.setVisCnt(baseDao.getRowCount(sql_2));
        dto.setEmpCarCnt(baseDao.getRowCount(sql_3));
        dto.setVisCarCnt(baseDao.getRowCount(sql_4));

        return dto;
    }
}
