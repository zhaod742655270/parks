package com.hbyd.parks.supportsys.wsImpl;


import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.supportsys.DeviceDescFiregroupDao;
import com.hbyd.parks.domain.supportsys.DeviceDescFiregroup;
import com.hbyd.parks.dto.supportsys.DeviceDescFiregroupDTO;
import com.hbyd.parks.ws.supportsys.DeviceDescFiregroupWS;

import javax.annotation.Resource;

/**
 * WS 实现：DeviceDescFiregroup
 */
public class DeviceDescFiregroupWSImpl extends BaseWSImpl<DeviceDescFiregroupDTO, DeviceDescFiregroup> implements DeviceDescFiregroupWS {
    @Resource
    private DeviceDescFiregroupDao deviceDescFiregroupDao;

    @Override
    public void update(DeviceDescFiregroupDTO dto) {
        String id = dto.getId();
        ValHelper.notNull(id, "更新实体必须有 ID");

//      提取关联属性

//      置空关联属性

//      获取目标
        DeviceDescFiregroup target = deviceDescFiregroupDao.getById(id);

//      填充普通属性
        dozerMapper.map(dto, target);

//      填充关联属性

//      执行更新操作
        deviceDescFiregroupDao.update(target);
    }

    @Override
    public DeviceDescFiregroupDTO save(DeviceDescFiregroupDTO dto) {
        String id = dto.getId();
        ValHelper.idNotExist(id);

//      提取关联属性

//      置空关联属性

//      获取目标，并填充普通属性
        DeviceDescFiregroup target = dozerMapper.map(dto, DeviceDescFiregroup.class);

//      填充关联属性

//      执行更新操作
        return dozerMapper.map(deviceDescFiregroupDao.save(target), DeviceDescFiregroupDTO.class);
    }
}
