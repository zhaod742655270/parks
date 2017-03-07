package com.hbyd.parks.supportsys.wsImpl;


import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.supportsys.DeviceDao;
import com.hbyd.parks.dao.supportsys.DeviceDescTypeDao;
import com.hbyd.parks.dao.supportsys.DeviceDoorDao;
import com.hbyd.parks.dao.supportsys.RegionDao;
import com.hbyd.parks.domain.supportsys.DeviceDoor;
import com.hbyd.parks.dto.supportsys.DeviceDoorDTO;
import com.hbyd.parks.ws.supportsys.DeviceDoorWS;

import javax.annotation.Resource;

/**
 * WS 实现：DeviceDoor
 */
public class DeviceDoorWSImpl extends BaseWSImpl<DeviceDoorDTO, DeviceDoor> implements DeviceDoorWS {
    @Resource
    private DeviceDao deviceDao;

    @Resource
    private DeviceDoorDao deviceDoorDao;

    @Resource
    private DeviceDescTypeDao deviceDescTypeDao;

    @Resource
    private RegionDao regionDao;

    @Override
    public void update(DeviceDoorDTO dto) {
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
        DeviceDoor target = deviceDoorDao.getById(id);

//      填充普通属性
        dozerMapper.map(dto, target);

//      填充关联属性
        target.setParent(deviceDao.getById(parentId));
        target.setDeviceType(deviceDescTypeDao.getById(typeId));
        target.setRegion(regionDao.getById(regionId));

//      执行更新操作
        deviceDoorDao.update(target);
    }

    @Override
    public DeviceDoorDTO save(DeviceDoorDTO dto) {
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
        DeviceDoor target = dozerMapper.map(dto, DeviceDoor.class);

//      填充关联属性
        target.setParent(deviceDao.getById(parentId));
        target.setDeviceType(deviceDescTypeDao.getById(typeId));
        target.setRegion(regionDao.getById(regionId));

//      执行更新操作
        return dozerMapper.map(deviceDoorDao.save(target), DeviceDoorDTO.class);
    }
}
