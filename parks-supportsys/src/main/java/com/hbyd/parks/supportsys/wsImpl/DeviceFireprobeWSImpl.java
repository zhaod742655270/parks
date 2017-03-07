package com.hbyd.parks.supportsys.wsImpl;


import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.supportsys.*;
import com.hbyd.parks.domain.supportsys.DeviceFireprobe;
import com.hbyd.parks.dto.supportsys.DeviceFireprobeDTO;
import com.hbyd.parks.ws.supportsys.DeviceFireprobeWS;

import javax.annotation.Resource;

/**
 * WS 实现：DeviceFireprobe
 */
public class DeviceFireprobeWSImpl extends BaseWSImpl<DeviceFireprobeDTO, DeviceFireprobe> implements DeviceFireprobeWS {
    @Resource
    private DeviceDao deviceDao;

    @Resource
    private DeviceFireprobeDao deviceFireprobeDao;

    @Resource
    private DeviceDescTypeDao deviceDescTypeDao;

    @Resource
    private RegionDao regionDao;

    @Resource
    private DeviceDescFiregroupDao groupDao;

    @Override
    public void update(DeviceFireprobeDTO dto) {
        String id = dto.getId();
        ValHelper.notNull(id, "更新实体必须有 ID");

//      提取关联属性
        String parentId = dto.getParentId();
        String typeId = dto.getTypeId();
        String regionId = dto.getRegionId();

//      提取自身关联属性
        String groupId = dto.getGroupId();

//      置空关联属性
        dto.setParentId(null);
        dto.setTypeId(null);
        dto.setRegionId(null);

        dto.setGroupId(null);

//      获取目标
        DeviceFireprobe target = deviceFireprobeDao.getById(id);

//      填充普通属性
        dozerMapper.map(dto, target);

//      填充关联属性
        target.setParent(deviceDao.getById(parentId));
        target.setDeviceType(deviceDescTypeDao.getById(typeId));
        target.setRegion(regionDao.getById(regionId));

//      填充自身关联属性
        target.setGroup(groupDao.getById(groupId));

//      执行更新操作
        deviceFireprobeDao.update(target);
    }

    @Override
    public DeviceFireprobeDTO save(DeviceFireprobeDTO dto) {
        String id = dto.getId();
        ValHelper.idNotExist(id);

//      提取关联属性
        String parentId = dto.getParentId();
        String typeId = dto.getTypeId();
        String regionId = dto.getRegionId();

//      提取自身关联属性
        String groupId = dto.getGroupId();

//      置空关联属性
        dto.setParentId(null);
        dto.setTypeId(null);
        dto.setRegionId(null);

//      获取目标，并填充普通属性
        DeviceFireprobe target = dozerMapper.map(dto, DeviceFireprobe.class);

//      填充关联属性
        target.setParent(deviceDao.getById(parentId));
        target.setDeviceType(deviceDescTypeDao.getById(typeId));
        target.setRegion(regionDao.getById(regionId));

//      填充自身关联属性
        target.setGroup(groupDao.getById(groupId));

//      执行更新操作
        return dozerMapper.map(deviceFireprobeDao.save(target), DeviceFireprobeDTO.class);
    }
}
