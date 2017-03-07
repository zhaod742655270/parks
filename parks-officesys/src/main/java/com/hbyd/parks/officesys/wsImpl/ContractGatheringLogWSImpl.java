package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.ConLogQueryBean;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.ContractGatheringLogDao;
import com.hbyd.parks.domain.officesys.ContractGathering;
import com.hbyd.parks.domain.officesys.ContractGatheringLog;
import com.hbyd.parks.dto.officesys.ContractGatheringDTO;
import com.hbyd.parks.dto.officesys.ContractGatheringLogDTO;
import com.hbyd.parks.ws.officesys.ContractGatheringLogWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by Len on 2016/3/3.
 */
public class ContractGatheringLogWSImpl extends BaseWSImpl<ContractGatheringLogDTO, ContractGatheringLog> implements ContractGatheringLogWS {
    @Resource
    private ContractGatheringLogDao contractGatheringLogDao;

    @Override
    public void delByID(String id) {
        if (!Strings.isNullOrEmpty(id))
            contractGatheringLogDao.delete(id);//删除
    }

    @Override
    public ContractGatheringLogDTO save(ContractGatheringLogDTO dto) {
        ValHelper.idNotExist(dto.getId());
        ContractGatheringDTO contractGatheringDTO=new ContractGatheringDTO();
        contractGatheringDTO.setId(dto.getGatheringFK());
        contractGatheringDTO.setContractNo(dto.getContractNo());
        ContractGatheringLog target = dozerMapper.map(dto, ContractGatheringLog.class);
        ContractGathering contractGathering=dozerMapper.map(contractGatheringDTO,ContractGathering.class);
        target.setContractGathering(contractGathering);
        contractGatheringLogDao.save(target);
        return dozerMapper.map(target, ContractGatheringLogDTO.class);
    }


    @Override
    public PageBeanEasyUI getPageBeanByLogQueryBean(ConLogQueryBean queryBean) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ContractGatheringLog.class)
                           .createAlias("contractGathering", "c" );
        criteria.add(eq("c.id", queryBean.getGatheringID()));

        PageBeanEasyUI pageBeanEasyUI = contractGatheringLogDao.getPageBean(queryBean, criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public PageBeanEasyUI getPageBeanByDate(ConLogQueryBean queryBean) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ContractGatheringLog.class)
                                                     .createAlias("contractGathering", "c");

        if (!Strings.isNullOrEmpty(queryBean.getContractNo()))
        criteria.add(like("c.contractNo", "%" + queryBean.getContractNo() + "%"));

        criteria.add(ge("date", queryBean.getBeginDate()));
        criteria.add(le("date", queryBean.getEndDate()));

        PageBeanEasyUI pageBeanEasyUI = contractGatheringLogDao.getPageBean(queryBean, criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }
}
