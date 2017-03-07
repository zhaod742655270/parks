package com.hbyd.parks.supportsys.wsImpl;


import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.managesys.ResAppDao;
import com.hbyd.parks.dao.supportsys.DeviceDescTypeDao;
import com.hbyd.parks.domain.supportsys.DeviceDescType;
import com.hbyd.parks.dto.supportsys.DeviceDescTypeDTO;
import com.hbyd.parks.ws.supportsys.DeviceDescTypeWS;

import javax.annotation.Resource;

/**
 * WS 实现：DeviceDescType
 */
public class DeviceDescTypeWSImpl extends BaseWSImpl<DeviceDescTypeDTO, DeviceDescType> implements DeviceDescTypeWS {
    @Resource
    private DeviceDescTypeDao deviceDescTypeDao;

    @Resource
    private ResAppDao resAppDao;

    @Override
    public void update(DeviceDescTypeDTO dto) {
        String id = dto.getId();
        ValHelper.notNull(id, "更新实体必须有 ID");

//      提取关联属性
        String appId = dto.getResAppFk();

//      置空关联属性
        dto.setResAppFk(null);

//      获取目标
        DeviceDescType target = deviceDescTypeDao.getById(id);

//      填充普通属性
        dozerMapper.map(dto, target);

//      填充关联属性
        target.setResApp(resAppDao.getById(appId));

//      执行更新操作
        deviceDescTypeDao.update(target);
    }

    @Override
    public DeviceDescTypeDTO save(DeviceDescTypeDTO dto) {
        String id = dto.getId();
        ValHelper.idNotExist(id);

//      提取关联属性
        String appId = dto.getResAppFk();

//      置空关联属性
        dto.setResAppFk(null);

//      获取目标，并填充普通属性
        DeviceDescType target = dozerMapper.map(dto, DeviceDescType.class);

//      填充关联属性
        target.setResApp(resAppDao.getById(appId));

//      执行更新操作
        return dozerMapper.map(deviceDescTypeDao.save(target), DeviceDescTypeDTO.class);
    }
}
