package com.hbyd.parks.supportsys.wsImpl;


import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.supportsys.DeviceDao;
import com.hbyd.parks.dao.supportsys.DeviceDescTypeDao;
import com.hbyd.parks.dao.supportsys.DeviceElecfenceDao;
import com.hbyd.parks.dao.supportsys.RegionDao;
import com.hbyd.parks.domain.supportsys.DeviceElecfence;
import com.hbyd.parks.dto.supportsys.DeviceElecfenceDTO;
import com.hbyd.parks.ws.supportsys.DeviceElecfenceWS;

import javax.annotation.Resource;

/**
 * WS 实现：DeviceElecfence
 */
public class DeviceElecfenceWSImpl extends BaseWSImpl<DeviceElecfenceDTO, DeviceElecfence> implements DeviceElecfenceWS {
    @Resource
    private DeviceDao deviceDao;

    @Resource
    private DeviceElecfenceDao deviceElecfenceDao;

    @Resource
    private DeviceDescTypeDao deviceDescTypeDao;

    @Resource
    private RegionDao regionDao;

    @Override
    public void update(DeviceElecfenceDTO dto) {
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
        DeviceElecfence target = deviceElecfenceDao.getById(id);

//      填充普通属性
        dozerMapper.map(dto, target);

//      填充关联属性
        target.setParent(deviceDao.getById(parentId));
        target.setDeviceType(deviceDescTypeDao.getById(typeId));
        target.setRegion(regionDao.getById(regionId));

//      执行更新操作
        deviceElecfenceDao.update(target);
    }

    @Override
    public DeviceElecfenceDTO save(DeviceElecfenceDTO dto) {
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
        DeviceElecfence target = dozerMapper.map(dto, DeviceElecfence.class);

//      填充关联属性
        target.setParent(deviceDao.getById(parentId));
        target.setDeviceType(deviceDescTypeDao.getById(typeId));
        target.setRegion(regionDao.getById(regionId));

//      执行更新操作
        return dozerMapper.map(deviceElecfenceDao.save(target), DeviceElecfenceDTO.class);
    }
}
