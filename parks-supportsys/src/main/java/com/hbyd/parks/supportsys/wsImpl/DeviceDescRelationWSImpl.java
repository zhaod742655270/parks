package com.hbyd.parks.supportsys.wsImpl;


import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.supportsys.DeviceDescRelationDao;
import com.hbyd.parks.domain.supportsys.DeviceDescRelation;
import com.hbyd.parks.dto.supportsys.DeviceDescRelationDTO;
import com.hbyd.parks.ws.supportsys.DeviceDescRelationWS;

import javax.annotation.Resource;

/**
 * WS 实现：DeviceDescRelation
 */
public class DeviceDescRelationWSImpl extends BaseWSImpl<DeviceDescRelationDTO, DeviceDescRelation> implements DeviceDescRelationWS {
    @Resource
    private DeviceDescRelationDao deviceDescRelationDao;

    @Override
    public void update(DeviceDescRelationDTO dto) {
        String id = dto.getId();
        ValHelper.notNull(id, "更新实体必须有 ID");

//      提取关联属性

//      置空关联属性

//      获取更新目标
        DeviceDescRelation target = deviceDescRelationDao.getById(id);

//      填充普通属性
        dozerMapper.map(dto, target);

//      填充关联属性

//      执行更新操作
        deviceDescRelationDao.update(target);
    }

    @Override
    public DeviceDescRelationDTO save(DeviceDescRelationDTO dto) {
//        String id = dto.getId();
//        ValHelper.idNotExist(id);//设备关系的 ID 为设备ID, 保存时需要手动填充

//      提取关联属性

//      置空关联属性

//      获取更新目标，并填充普通属性
        DeviceDescRelation target = dozerMapper.map(dto, DeviceDescRelation.class);

//      填充关联属性

//      执行更新操作
        return dozerMapper.map(deviceDescRelationDao.save(target), DeviceDescRelationDTO.class);
    }
}
