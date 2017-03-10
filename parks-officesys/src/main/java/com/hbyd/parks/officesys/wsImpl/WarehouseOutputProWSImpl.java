package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseOutputQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.WarehouseOutputProDao;
import com.hbyd.parks.domain.officesys.WarehouseOutputPro;
import com.hbyd.parks.dto.officesys.WarehouseOutputProDTO;
import com.hbyd.parks.ws.officesys.WarehouseOutputProWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Created by Zhao_d on 2017/2/20.
 */
public class WarehouseOutputProWSImpl extends BaseWSImpl<WarehouseOutputProDTO,WarehouseOutputPro> implements WarehouseOutputProWS {
    @Resource
    private WarehouseOutputProDao warehouseOutputProDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseOutputQuery query, String parentId){
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseOutputPro.class);
        criteria.add(eq("isValid",true));
        criteria.add(eq("warehouseOutput.id",parentId));

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

        PageBeanEasyUI pageBeanEasyUI = warehouseOutputProDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public WarehouseOutputProDTO save(WarehouseOutputProDTO dto){
        ValHelper.idNotExist(dto.getId());
        WarehouseOutputPro target = dozerMapper.map(dto,WarehouseOutputPro.class);
        warehouseOutputProDao.save(target);
        return dozerMapper.map(target, WarehouseOutputProDTO.class);
    }

    @Override
    public void update(WarehouseOutputProDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        WarehouseOutputPro target = warehouseOutputProDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        dozerMapper.map(dto,target);
        warehouseOutputProDao.update(target);
    }

    public Double getQuantityById(String id){
        WarehouseOutputPro target = warehouseOutputProDao.getById(id);
        return target.getQuantity();
    }
}
