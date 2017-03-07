package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QuotationQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.QuotationDao;
import com.hbyd.parks.domain.officesys.Quotation;
import com.hbyd.parks.dto.officesys.QuotationDTO;
import com.hbyd.parks.ws.officesys.QuotationWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/4/25
 */
public class QuotationWSImpl extends BaseWSImpl<QuotationDTO, Quotation> implements QuotationWS{

    @Resource
    private QuotationDao quotationDao;

    @Override
    public PageBeanEasyUI getQuotationPageBeanByQueryBean(String projectId, QuotationQuery queryBean) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Quotation.class);
        criteria.add(eq("isValid", true));
        if (!Strings.isNullOrEmpty(projectId)) {
            criteria.add(eq("projectIdFK", projectId));
        }

        if(!Strings.isNullOrEmpty(queryBean.getNameQuery())){
            criteria.add(like("name", "%"+queryBean.getNameQuery()+"%"));
        }

        if(!Strings.isNullOrEmpty(queryBean.getSpecificationQuery())){
            criteria.add(like("specification", "%"+queryBean.getSpecificationQuery()+"%"));
        }

        if(!Strings.isNullOrEmpty(queryBean.getBrandQuery())){
            criteria.add(like("brand","%"+queryBean.getBrandQuery()+ "%"));
        }
        PageBeanEasyUI pageBeanEasyUI = quotationDao.getPageBean(queryBean, criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public QuotationDTO save(QuotationDTO dto) {
        ValHelper.idNotExist(dto.getId());

        Quotation target = dozerMapper.map(dto, Quotation.class);
        quotationDao.save(target);
        return dozerMapper.map(target, QuotationDTO.class);
    }

    @Override
    public void update(QuotationDTO dto) {
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        Quotation target = quotationDao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");

        dozerMapper.map(dto, target);
        quotationDao.update(target);

    }


}
