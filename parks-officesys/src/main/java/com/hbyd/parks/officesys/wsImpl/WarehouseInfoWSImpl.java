package com.hbyd.parks.officesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dao.officesys.WarehouseInfoDao;
import com.hbyd.parks.domain.officesys.WarehouseInfo;
import com.hbyd.parks.dto.officesys.WarehouseInfoDTO;
import com.hbyd.parks.ws.officesys.WarehouseInfoWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * Created by Zhao_d on 2017/1/23.
 */
public class WarehouseInfoWSImpl extends BaseWSImpl<WarehouseInfoDTO,WarehouseInfo> implements WarehouseInfoWS {

    @Resource
    private WarehouseInfoDao warehouseInfoDao;

    /**
     * 获得所有仓库类型
     */
    public PageBeanEasyUI getWarehouseType(){
        QueryBeanEasyUI query = new QueryBeanEasyUI();
        query.setRows(1000);
        query.setSort("id");
        query.setOrder("asc");

        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseInfo.class);
        criteria.add(eq("isValid",true));

        PageBeanEasyUI pageBeanEasyUI = warehouseInfoDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }
}
