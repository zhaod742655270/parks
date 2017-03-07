package com.hbyd.parks.officesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.ContractGatheringDao;
import com.hbyd.parks.dao.officesys.ContractGatheringPostilDao;
import com.hbyd.parks.domain.officesys.ContractGathering;
import com.hbyd.parks.domain.officesys.ContractGatheringPostil;
import com.hbyd.parks.dto.officesys.ContractGatheringPostilDTO;
import com.hbyd.parks.ws.officesys.ContractGatheringPostilWS;

import javax.annotation.Resource;

/**
 * Created by Len on 2016/3/3.
 */
public class ContractGatheringPostilWSImpl extends BaseWSImpl<ContractGatheringPostilDTO, ContractGatheringPostil> implements ContractGatheringPostilWS {


    @Resource
    private ContractGatheringPostilDao contractGatheringPostilDao;

    @Resource
    private ContractGatheringDao contractGatheringDao;

    @Override
    public void update(ContractGatheringPostilDTO dto) {
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");

        ContractGatheringPostil target = contractGatheringPostilDao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");
        ValHelper.notNull(dto.getGatheringID(),"收款合同的ID不能为null");//关联的合同id不能为空
        //找出所属合同
        ContractGathering contractGathering = contractGatheringDao.getById(dto.getGatheringID());
        ValHelper.notNull(contractGathering, "关联的收款合同不存在!");


        dozerMapper.map(dto, target);
        target.setContractGathering(contractGathering);
        contractGatheringPostilDao.update(target);
    }

    @Override
    public ContractGatheringPostilDTO save(ContractGatheringPostilDTO dto) {
        ValHelper.idNotExist(dto.getId());//id不能存在
        ValHelper.notNull(dto.getGatheringID(),"收款合同的ID不能为null");//关联的合同id不能为空
        //找出所属合同
        ContractGathering contractGathering = contractGatheringDao.getById(dto.getGatheringID());
        ValHelper.notNull(contractGathering, "关联的收款合同不存在!");
        //对象转化
        ContractGatheringPostil target = dozerMapper.map(dto, ContractGatheringPostil.class);
        //将合同值添加上
        target.setContractGathering(contractGathering);
        contractGatheringPostilDao.save(target);
        return dozerMapper.map(target, ContractGatheringPostilDTO.class);
    }

}
