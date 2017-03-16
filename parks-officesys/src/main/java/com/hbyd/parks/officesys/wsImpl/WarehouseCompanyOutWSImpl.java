package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseCompanyQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.WarehouseCompanyOutDao;
import com.hbyd.parks.domain.officesys.WarehouseCompanyOut;
import com.hbyd.parks.dto.officesys.WarehouseCompanyOutDTO;
import com.hbyd.parks.ws.officesys.WarehouseCompanyOutWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Created by Zhao_d on 2017/1/20.
 */
public class WarehouseCompanyOutWSImpl extends BaseWSImpl<WarehouseCompanyOutDTO,WarehouseCompanyOut> implements WarehouseCompanyOutWS {

    @Resource
    private WarehouseCompanyOutDao warehouseCompanyOutDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseCompanyQuery query){
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseCompanyOut.class);
        criteria.add(eq("isValid",true));
        if(!Strings.isNullOrEmpty(query.getNameQuery())){
            criteria.add(like("name","%" + query.getNameQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getTypeQuery())){
            criteria.add(eq("type",query.getTypeQuery()));
        }

        PageBeanEasyUI pageBeanEasyUI = warehouseCompanyOutDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public WarehouseCompanyOutDTO save(WarehouseCompanyOutDTO dto){
        ValHelper.idNotExist(dto.getId());
        WarehouseCompanyOut target = dozerMapper.map(dto,WarehouseCompanyOut.class);
        warehouseCompanyOutDao.save(target);
        return dozerMapper.map(target, WarehouseCompanyOutDTO.class);
    }

    @Override
    public void update(WarehouseCompanyOutDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        WarehouseCompanyOut target = warehouseCompanyOutDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        dozerMapper.map(dto,target);
        warehouseCompanyOutDao.update(target);
    }
}
