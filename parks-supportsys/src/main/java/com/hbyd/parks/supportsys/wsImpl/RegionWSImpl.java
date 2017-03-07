package com.hbyd.parks.supportsys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.dao.supportsys.RegionDao;
import com.hbyd.parks.domain.supportsys.Region;
import com.hbyd.parks.dto.supportsys.RegionDTO;
import com.hbyd.parks.ws.supportsys.RegionWS;
import org.dozer.DozerBeanMapper;

import javax.annotation.Resource;

import static com.hbyd.parks.common.util.ValHelper.idNotExist;
import static com.hbyd.parks.common.util.ValHelper.notNull;

/**
 * 区域服务
 */
public class RegionWSImpl extends BaseWSImpl<RegionDTO, Region> implements RegionWS {

    @Resource
    private DozerBeanMapper dozerMapper;

    @Resource
    private RegionDao regionDao;

    @Override
    public void update(RegionDTO dto) {
        notNull(dto.getId(), "更新实体必须有 ID");

//      防止拷贝关联属性
        String newParentFK = dto.getParentFK();
        dto.setParentFK(null);

//      获取目标实体
        Region target = regionDao.getById(dto.getId());

//      更新普通属性
        dozerMapper.map(dto, target);

//      更新关联属性
        if(null == newParentFK){
            target.setParent(null);
        }else{
            Region parent = regionDao.getById(newParentFK);
            target.setParent(parent);
        }

//      执行更新
        regionDao.update(target);
    }

    @Override
    public RegionDTO save(RegionDTO dto) {
        idNotExist(dto.getId());

//      1. 防止拷贝关联属性
        String parentFK = dto.getParentFK();
        dto.setParentFK(null);

//      2. 拷贝普通属性
        Region target = dozerMapper.map(dto, Region.class);

//      3. 填充关联属性
        if(null == parentFK){
            target.setParent(null);
        }else {
            Region parent = regionDao.getById(parentFK);
            target.setParent(parent);
        }

//      4. 保存目标实体
        Region saved = regionDao.save(target);

        return dozerMapper.map(saved, RegionDTO.class);
    }
}
