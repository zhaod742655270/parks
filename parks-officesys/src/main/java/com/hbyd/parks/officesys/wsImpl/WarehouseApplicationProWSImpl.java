package com.hbyd.parks.officesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseApplicationQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.WarehouseApplicationProDao;
import com.hbyd.parks.domain.officesys.WarehouseApplicationPro;
import com.hbyd.parks.dto.officesys.WarehouseApplicationProDTO;
import com.hbyd.parks.ws.officesys.WarehouseApplicationProWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * Created by Zhao_d on 2017/1/23.
 */
public class WarehouseApplicationProWSImpl extends BaseWSImpl<WarehouseApplicationProDTO,WarehouseApplicationPro> implements WarehouseApplicationProWS{

    @Resource
    private WarehouseApplicationProDao warehouseApplicationProDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseApplicationQuery query, String parentId){
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseApplicationPro.class);
        criteria.add(eq("isValid",true));

        PageBeanEasyUI pageBeanEasyUI = warehouseApplicationProDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public WarehouseApplicationProDTO save(WarehouseApplicationProDTO dto){
        ValHelper.idNotExist(dto.getId());
        WarehouseApplicationPro target = dozerMapper.map(dto,WarehouseApplicationPro.class);
        warehouseApplicationProDao.save(target);
        return dozerMapper.map(target, WarehouseApplicationProDTO.class);
    }
}
