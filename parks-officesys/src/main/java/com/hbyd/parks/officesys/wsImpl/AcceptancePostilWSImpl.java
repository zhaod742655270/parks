package com.hbyd.parks.officesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.AcceptanceDao;
import com.hbyd.parks.dao.officesys.AcceptancePostilDao;
import com.hbyd.parks.domain.officesys.Acceptance;
import com.hbyd.parks.domain.officesys.AcceptancePostil;
import com.hbyd.parks.dto.officesys.AcceptancePostilDTO;
import com.hbyd.parks.ws.officesys.AcceptancePostilWS;

import javax.annotation.Resource;

/**
 * Created by Len on 2016/3/3.
 */
public class AcceptancePostilWSImpl extends BaseWSImpl<AcceptancePostilDTO, AcceptancePostil> implements AcceptancePostilWS {


    @Resource
    private AcceptancePostilDao acceptancePostilDao;

    @Resource
    private AcceptanceDao acceptanceDao;

    @Override
    public void update(AcceptancePostilDTO dto) {
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");

        AcceptancePostil target = acceptancePostilDao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");
        ValHelper.notNull(dto.getParentID(),"验收清单的ID不能为null");//关联的验收清单id不能为空
        //找出所属的验收清单
        Acceptance acceptance = acceptanceDao.getById(dto.getParentID());
        ValHelper.notNull(acceptance, "关联的验收清单不存在!");


        dozerMapper.map(dto, target);
        target.setAcceptance(acceptance);
        acceptancePostilDao.update(target);
    }

    @Override
    public AcceptancePostilDTO save(AcceptancePostilDTO dto) {
        ValHelper.idNotExist(dto.getId());//id不能存在
        ValHelper.notNull(dto.getParentID(),"验收清单的ID不能为null");//关联的验收清单id不能为空
        //找出所属验收清单
        Acceptance acceptance = acceptanceDao.getById(dto.getParentID());
        ValHelper.notNull(acceptance, "关联的验收清单不存在!");
        //对象转化
        AcceptancePostil target = dozerMapper.map(dto, AcceptancePostil.class);
        //将合同值添加上
        target.setAcceptance(acceptance);
        acceptancePostilDao.save(target);
        return dozerMapper.map(target, AcceptancePostilDTO.class);
    }

}
