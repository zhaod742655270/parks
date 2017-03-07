package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.AcceptanceExamineDao;
import com.hbyd.parks.domain.officesys.AcceptanceExamine;
import com.hbyd.parks.dto.officesys.AcceptanceExamineDTO;
import com.hbyd.parks.ws.officesys.AcceptanceExamineWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * Created by Zhao_d on 2017/2/14.
 */
public class AcceptanceExamineWSImpl extends BaseWSImpl<AcceptanceExamineDTO,AcceptanceExamine> implements AcceptanceExamineWS {

    @Resource
    private AcceptanceExamineDao acceptanceExamineDao;

    @Override
    public PageBeanEasyUI getPageBeanByAcceptanceId(String acceptanceId){
        DetachedCriteria criteria = DetachedCriteria.forClass(AcceptanceExamine.class);
        criteria.add(eq("isValid",true));
        if (!Strings.isNullOrEmpty(acceptanceId)) {
            criteria.add(eq("acceptance.id", acceptanceId));
        }
        QueryBeanEasyUI query = new QueryBeanEasyUI();
        query.setSort("examineDate");
        query.setOrder("desc");
        query.setRows(1000);
        PageBeanEasyUI page = acceptanceExamineDao.getPageBean(query,criteria);
        List list = getDTOList(page.getRows());
        page.setRows(list);
        return page;
    }

    @Override
    public AcceptanceExamineDTO save(AcceptanceExamineDTO dto){
        ValHelper.idNotExist(dto.getId());
        AcceptanceExamine target = dozerMapper.map(dto,AcceptanceExamine.class);
        acceptanceExamineDao.save(target);
        return dozerMapper.map(target, AcceptanceExamineDTO.class);
    }
}
