package com.hbyd.parks.supportsys.wsImpl;


import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.supportsys.DeviceControllerDao;
import com.hbyd.parks.dao.supportsys.DeviceDao;
import com.hbyd.parks.dao.supportsys.DeviceDescTypeDao;
import com.hbyd.parks.dao.supportsys.RegionDao;
import com.hbyd.parks.domain.supportsys.DeviceController;
import com.hbyd.parks.dto.supportsys.DeviceControllerDTO;
import com.hbyd.parks.ws.supportsys.DeviceControllerWS;

import javax.annotation.Resource;

/**
 * WS 实现：DeviceController
 */
public class DeviceControllerWSImpl extends BaseWSImpl<DeviceControllerDTO, DeviceController> implements DeviceControllerWS {
    @Resource
    private DeviceDao deviceDao;

    @Resource
    private DeviceControllerDao deviceControllerDao;

    @Resource
    private DeviceDescTypeDao deviceDescTypeDao;

    @Resource
    private RegionDao regionDao;

    @Override
    public void update(DeviceControllerDTO dto) {
        String id = dto.getId();
        ValHelper.notNull(id, "更新实体必须有 ID");

//      提取关联属性
        String parentId = dto.getParentId();
        String typeId = dto.getTypeId();
        String regionId = dto.getRegionId();

//      置空关联属性
        dto.setParentId(null);
        dto.setTypeId(null);
        dto.setRegionId(null);

//      获取目标
        DeviceController target = deviceControllerDao.getById(id);

//      填充普通属性
        dozerMapper.map(dto, target);

//      填充关联属性
        target.setParent(deviceDao.getById(parentId));
        target.setDeviceType(deviceDescTypeDao.getById(typeId));
        target.setRegion(regionDao.getById(regionId));

//      执行更新操作
        deviceControllerDao.update(target);
    }

    @Override
    public DeviceControllerDTO save(DeviceControllerDTO dto) {
        String id = dto.getId();
        ValHelper.idNotExist(id);

//      提取关联属性
        String parentId = dto.getParentId();
        String typeId = dto.getTypeId();
        String regionId = dto.getRegionId();

//      置空关联属性
        dto.setParentId(null);
        dto.setTypeId(null);
        dto.setRegionId(null);

//      获取目标，并填充普通属性
        DeviceController target = dozerMapper.map(dto, DeviceController.class);

//      填充关联属性
        target.setParent(deviceDao.getById(parentId));
        target.setDeviceType(deviceDescTypeDao.getById(typeId));
        target.setRegion(regionDao.getById(regionId));

//      执行更新操作
        return dozerMapper.map(deviceControllerDao.save(target), DeviceControllerDTO.class);
    }
}
