package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QuotationQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.QuotationProjectDao;
import com.hbyd.parks.domain.officesys.QuotationProject;
import com.hbyd.parks.dto.officesys.QuotationProjectDTO;
import com.hbyd.parks.ws.officesys.QuotationProjectWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Created by Administrator on 2016/10/31.
 */
public class QuotationProjectWSImpl extends BaseWSImpl<QuotationProjectDTO, QuotationProject> implements QuotationProjectWS {
    @Resource
    private QuotationProjectDao quotationProjectDao;

    @Override
    public PageBeanEasyUI getProjectPageBeanByQueryBean(QuotationQuery queryBean) {
        DetachedCriteria criteria = DetachedCriteria.forClass(QuotationProject.class);

        criteria = getCriteria(criteria, queryBean);
        PageBeanEasyUI pageBeanEasyUI = quotationProjectDao.getPageBean(queryBean, criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    private DetachedCriteria getCriteria(DetachedCriteria criteria, QuotationQuery queryBean) {

        criteria.add(eq("isValid", true));

        if (!Strings.isNullOrEmpty(queryBean.getSheetNameQuery()))
            criteria.add(eq("sheetName",  queryBean.getSheetNameQuery()));

        if (!Strings.isNullOrEmpty(queryBean.getProjectNameQuery()))
            criteria.add(like("projectName", "%" + queryBean.getProjectNameQuery() + "%"));

        if (!Strings.isNullOrEmpty(queryBean.getProjectTypeQuery()))
            criteria.add(like("projectType", "%" + queryBean.getProjectTypeQuery() + "%"));

        return criteria;
    }

    @Override
    public QuotationProjectDTO save(QuotationProjectDTO dto){
        //检验ID不重复
        ValHelper.idNotExist(dto.getId());
        //保存
        QuotationProject target = dozerMapper.map(dto, QuotationProject.class);
        quotationProjectDao.save(target);
        return dozerMapper.map(target, QuotationProjectDTO.class);
    }

    @Override
    public void update(QuotationProjectDTO dto){
        //检验修改在Id不为空
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        //检验修改在目标数据不为空
        QuotationProject target = quotationProjectDao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");
        //更新
        dozerMapper.map(dto,target);
        quotationProjectDao.update(target);
    }
}
