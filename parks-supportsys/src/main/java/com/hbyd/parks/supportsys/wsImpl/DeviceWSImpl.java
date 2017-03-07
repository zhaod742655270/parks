package com.hbyd.parks.supportsys.wsImpl;


import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.supportsys.DeviceDao;
import com.hbyd.parks.dao.supportsys.DeviceDescTypeDao;
import com.hbyd.parks.dao.supportsys.RegionDao;
import com.hbyd.parks.domain.supportsys.Device;
import com.hbyd.parks.dto.supportsys.DeviceDTO;
import com.hbyd.parks.ws.supportsys.DeviceWS;

import javax.annotation.Resource;

/**
 * 服务实现:设备
 */
public class DeviceWSImpl extends BaseWSImpl<DeviceDTO, Device> implements DeviceWS {
    @Resource
    private DeviceDao deviceDao;

    @Resource
    private DeviceDescTypeDao deviceDescTypeDao;

    @Resource
    private RegionDao regionDao;

    @Override
    public void update(DeviceDTO dto) {
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

//      获取更新目标
        Device target = deviceDao.getById(id);

//      填充普通属性
        dozerMapper.map(dto, target);

//      填充关联属性
        target.setParent(deviceDao.getById(parentId));
        target.setDeviceType(deviceDescTypeDao.getById(typeId));
        target.setRegion(regionDao.getById(regionId));

//      执行更新操作
        deviceDao.update(target);//target extends BaseEntity
    }

    @Override
    public DeviceDTO save(DeviceDTO dto) {
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

//      获取更新目标，并填充普通属性
        Device target = dozerMapper.map(dto, Device.class);

//      填充关联属性
        target.setParent(deviceDao.getById(parentId));
        target.setDeviceType(deviceDescTypeDao.getById(typeId));
        target.setRegion(regionDao.getById(regionId));

//      执行更新操作
        return dozerMapper.map(deviceDao.save(target), DeviceDTO.class);
    }

//  继承策略如果使用 JOINED, HQL 查询 FROM Device 会连续 LEFT OUTER JOIN 所有子表，目前无法解决，只能使用 SQL 查询
/*
    @Override
    public DeviceDTO getByID(String id) {
        return (DeviceDTO) baseDao.getCurrSession().createSQLQuery(
                "select c.id as id, c.oneType as oneType, c.deviceId as deviceId, c.name as name, c.descInfo as descInfo, c.position as pos, c.maker as maker, c.state as state,\n" +
                        "p.id as parentId, p.name as parentName, p.deviceId as parentDeviceId,\n" +
                        "r.id as regionId, r.regionName as regionName, r.regionDesc as regionDesc,\n" +
                        "t.id as typeId, t.typeDesc as typeDesc from base_device c \n" +
                        "LEFT JOIN base_device p ON c.parentFK = p.id\n" +
                        "LEFT JOIN base_region r ON c.regionFK = r.id\n" +
                        "LEFT JOIN base_device_desc_type t on c.devTypeFK = t.id where c.id = ?")
                .setParameter(0, id)
                .setResultTransformer(Transformers.aliasToBean(DeviceDTO.class))
                .uniqueResult();
    }*/
}
