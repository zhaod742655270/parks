package com.hbyd.parks.supportsys.wsImpl;


import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.supportsys.DeviceDescCommDao;
import com.hbyd.parks.domain.supportsys.DeviceDescComm;
import com.hbyd.parks.dto.supportsys.DeviceDescCommDTO;
import com.hbyd.parks.ws.supportsys.DeviceDescCommWS;

import javax.annotation.Resource;

/**
 * WS 实现：DeviceDescComm
 */
public class DeviceDescCommWSImpl extends BaseWSImpl<DeviceDescCommDTO, DeviceDescComm> implements DeviceDescCommWS {
    @Resource
    private DeviceDescCommDao deviceDescCommDao;

    @Override
    public void update(DeviceDescCommDTO dto) {
        String id = dto.getId();
        ValHelper.notNull(id, "更新实体必须有 ID");

//      提取关联属性

//      置空关联属性

//      获取目标
        DeviceDescComm target = deviceDescCommDao.getById(id);

//      填充普通属性
        dozerMapper.map(dto, target);

//      填充关联属性

//      执行更新操作
        deviceDescCommDao.update(target);
    }

    @Override
    public DeviceDescCommDTO save(DeviceDescCommDTO dto) {
        String id = dto.getId();
        ValHelper.idNotExist(id);

//      提取关联属性

//      置空关联属性

//      获取目标，并填充普通属性
        DeviceDescComm target = dozerMapper.map(dto, DeviceDescComm.class);

//      填充关联属性

//      执行更新操作
        return dozerMapper.map(deviceDescCommDao.save(target), DeviceDescCommDTO.class);
    }
}
