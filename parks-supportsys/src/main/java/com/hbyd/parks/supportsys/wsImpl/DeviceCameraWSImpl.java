package com.hbyd.parks.supportsys.wsImpl;


import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.supportsys.DeviceCameraDao;
import com.hbyd.parks.dao.supportsys.DeviceDao;
import com.hbyd.parks.dao.supportsys.DeviceDescTypeDao;
import com.hbyd.parks.dao.supportsys.RegionDao;
import com.hbyd.parks.domain.supportsys.DeviceCamera;
import com.hbyd.parks.dto.supportsys.DeviceCameraDTO;
import com.hbyd.parks.ws.supportsys.DeviceCameraWS;

import javax.annotation.Resource;

/**
 * WS 实现：DeviceCamera
 */
public class DeviceCameraWSImpl extends BaseWSImpl<DeviceCameraDTO, DeviceCamera> implements DeviceCameraWS {
    @Resource
    private DeviceDao deviceDao;

    @Resource
    private DeviceCameraDao deviceCameraDao;

    @Resource
    private DeviceDescTypeDao deviceDescTypeDao;

    @Resource
    private RegionDao regionDao;

    @Override
    public void update(DeviceCameraDTO dto) {
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
        DeviceCamera target = deviceCameraDao.getById(id);

//      填充普通属性
        dozerMapper.map(dto, target);

//      填充关联属性
        target.setParent(deviceCameraDao.getById(parentId));
        target.setDeviceType(deviceDescTypeDao.getById(typeId));
        target.setRegion(regionDao.getById(regionId));

//      执行更新操作
        deviceCameraDao.update(target);
    }

    @Override
    public DeviceCameraDTO save(DeviceCameraDTO dto) {
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
        DeviceCamera target = dozerMapper.map(dto, DeviceCamera.class);

//      填充关联属性
        target.setParent(deviceCameraDao.getById(parentId));
        target.setDeviceType(deviceDescTypeDao.getById(typeId));
        target.setRegion(regionDao.getById(regionId));

//      执行更新操作
        return dozerMapper.map(deviceCameraDao.save(target), DeviceCameraDTO.class);
    }
}
