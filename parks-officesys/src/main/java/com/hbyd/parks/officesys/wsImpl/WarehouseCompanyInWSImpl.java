package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseCompanyQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.WarehouseCompanyInDao;
import com.hbyd.parks.domain.officesys.WarehouseCompanyIn;
import com.hbyd.parks.dto.officesys.WarehouseCompanyInDTO;
import com.hbyd.parks.ws.officesys.WarehouseCompanyInWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Created by Zhao_d on 2017/1/20.
 */
public class WarehouseCompanyInWSImpl extends BaseWSImpl<WarehouseCompanyInDTO,WarehouseCompanyIn> implements WarehouseCompanyInWS {

    @Resource
    private WarehouseCompanyInDao warehouseCompanyInDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseCompanyQuery query){
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseCompanyIn.class);
        criteria.add(eq("isValid",true));
        if(!Strings.isNullOrEmpty(query.getNameQuery())){
            criteria.add(like("name","%" + query.getNameQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getTypeQuery())){
            criteria.add(eq("type",query.getTypeQuery()));
        }

        PageBeanEasyUI pageBeanEasyUI = warehouseCompanyInDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public WarehouseCompanyInDTO save(WarehouseCompanyInDTO dto){
        ValHelper.idNotExist(dto.getId());
        WarehouseCompanyIn target = dozerMapper.map(dto,WarehouseCompanyIn.class);
        warehouseCompanyInDao.save(target);
        return dozerMapper.map(target, WarehouseCompanyInDTO.class);
    }

    @Override
    public void update(WarehouseCompanyInDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        WarehouseCompanyIn target = warehouseCompanyInDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        dozerMapper.map(dto,target);
        warehouseCompanyInDao.update(target);
    }
}
