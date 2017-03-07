package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.ExpenditureQuery;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.ExpenditureDao;
import com.hbyd.parks.domain.officesys.Expenditure;
import com.hbyd.parks.dto.officesys.ExpenditureDTO;
import com.hbyd.parks.ws.officesys.ExpenditureWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by Zhao_d on 2017/2/23.
 */
public class ExpenditureWSImpl extends BaseWSImpl<ExpenditureDTO,Expenditure> implements ExpenditureWS {

    @Resource
    private ExpenditureDao expenditureDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(ExpenditureQuery query){
        DetachedCriteria criteria = DetachedCriteria.forClass(Expenditure.class);
        //查询条件
        criteria.add(eq("isValid",true));
        if(!Strings.isNullOrEmpty(query.getRecordPersonQuery())){
            criteria.add(eq("recordPerson.id",query.getRecordPersonQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getProjectQuery())){
            criteria.add(eq("projectRecord.id",query.getProjectQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getExamineDateBeg())){
            criteria.add(ge("examineDate",query.getExamineDateBeg()));
        }
        if(!Strings.isNullOrEmpty(query.getExamineDateEnd())){
            criteria.add(le("examineDate",query.getExamineDateEnd()));
        }
        PageBeanEasyUI pageBeanEasyUI = expenditureDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public ExpenditureDTO save(ExpenditureDTO dto){
        ValHelper.idNotExist(dto.getId());
        Expenditure target = dozerMapper.map(dto,Expenditure.class);
        expenditureDao.save(target);
        return dozerMapper.map(target, ExpenditureDTO.class);
    }

    @Override
    public void update(ExpenditureDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        Expenditure target = expenditureDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        //首先将所有关联的表置空，否则会以为要级联更新关联表的主键而报错
        target.setRecordPerson(null);
        target.setProjectRecord(null);

        dozerMapper.map(dto,target);
        expenditureDao.update(target);
    }
}
