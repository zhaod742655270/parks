package com.hbyd.parks.meetsys.wsImpl;


import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.meetsys.MeetRoomDao;
import com.hbyd.parks.dao.supportsys.RegionDao;
import com.hbyd.parks.domain.meetsys.MeetRoom;
import com.hbyd.parks.dto.meetsys.MeetRoomDTO;
import com.hbyd.parks.ws.meetsys.MeetRoomWS;

import javax.annotation.Resource;

/**
 * WS 实现：MeetRoom
 */
public class MeetRoomWSImpl extends BaseWSImpl<MeetRoomDTO, MeetRoom> implements MeetRoomWS {
    @Resource
    private MeetRoomDao meetRoomDao;

    @Resource
    private RegionDao regionDao;

    @Override
    public void update(MeetRoomDTO dto) {
        String id = dto.getId();
        ValHelper.notNull(id, "更新实体必须有 ID");

//      提取关联属性
        String regionId = dto.getRegionId();

//      置空关联属性
        dto.setRegionId(null);

//      获取更新目标
        MeetRoom target = meetRoomDao.getById(id);

//      填充普通属性
        dozerMapper.map(dto, target);

//      填充关联属性
        target.setRegion(regionDao.getById(regionId));

//      执行更新操作
        meetRoomDao.update(target);
    }

    @Override
    public MeetRoomDTO save(MeetRoomDTO dto) {
        String id = dto.getId();
        ValHelper.idNotExist(id);

//      提取关联属性
        String regionId = dto.getRegionId();

//      置空关联属性
        dto.setRegionId(regionId);

//      获取更新目标，并填充普通属性
        MeetRoom target = dozerMapper.map(dto, MeetRoom.class);

//      填充关联属性
        target.setRegion(regionDao.getById(regionId));

//      执行更新操作
        return dozerMapper.map(meetRoomDao.save(target), MeetRoomDTO.class);
    }
}
