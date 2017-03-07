package com.hbyd.parks.dao.doorsys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.doorsys.AccessEvent;

import java.util.Date;
import java.util.List;

/**
 * 刷卡事件DAO
 */
public interface AccessEventDao extends BaseDao<AccessEvent, String>{

    /**查询某段期间内的刷卡记录
     * @param empId    卡号
     * @param beginDT   起始日期时间
     * @param endDT     结束日期时间
     * @return  刷卡事件列表
     */
    List<AccessEvent> getEvents(String empId, Date beginDT, Date endDT);
}
