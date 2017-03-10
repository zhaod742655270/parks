package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseInputQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.WarehouseInputProDao;
import com.hbyd.parks.domain.officesys.WarehouseInputPro;
import com.hbyd.parks.dto.officesys.WarehouseInputProDTO;
import com.hbyd.parks.ws.officesys.WarehouseInputProWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Created by Zhao_d on 2016/12/27.
 */
public class WarehouseInputProWSImpl extends BaseWSImpl<WarehouseInputProDTO,WarehouseInputPro> implements WarehouseInputProWS{

    @Resource
    private WarehouseInputProDao warehouseInputProDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseInputQuery query,String parentId){
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseInputPro.class);
        criteria.add(eq("isValid",true));
        criteria.add(eq("warehouseInput.id",parentId));

        if(!Strings.isNullOrEmpty(query.getNameQuery())){
            criteria.createAlias("warehouseProduct","product")
                    .add(like("product.name","%" + query.getNameQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getModelNumberQuery())){
            criteria.createAlias("warehouseProduct","product")
                    .add(like("product.modelNumber","%" + query.getModelNumberQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getSpecificationsQuery())){
            criteria.createAlias("warehouseProduct","product")
                    .add(like("product.specifications","%" + query.getSpecificationsQuery() + "%"));
        }

        PageBeanEasyUI pageBeanEasyUI = warehouseInputProDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public WarehouseInputProDTO save(WarehouseInputProDTO dto){
        ValHelper.idNotExist(dto.getId());
        WarehouseInputPro target = dozerMapper.map(dto,WarehouseInputPro.class);
        warehouseInputProDao.save(target);
        return dozerMapper.map(target, WarehouseInputProDTO.class);
    }

    @Override
    public void update(WarehouseInputProDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        WarehouseInputPro target = warehouseInputProDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        dozerMapper.map(dto,target);
        warehouseInputProDao.update(target);
    }

    public Double getQuantityById(String id){
        WarehouseInputPro target = warehouseInputProDao.getById(id);
        return target.getQuantity();
    }
}
