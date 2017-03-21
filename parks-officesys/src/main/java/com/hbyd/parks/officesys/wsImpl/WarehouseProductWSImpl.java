package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.google.gson.internal.LinkedTreeMap;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseProductQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.WarehouseProductDao;
import com.hbyd.parks.domain.officesys.WarehouseProduct;
import com.hbyd.parks.dto.officesys.WarehouseProductDTO;
import com.hbyd.parks.ws.officesys.WarehouseProductWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Created by Zhao_d on 2016/12/28.
 */
public class WarehouseProductWSImpl extends BaseWSImpl<WarehouseProductDTO,WarehouseProduct> implements WarehouseProductWS {

    @Resource
    private WarehouseProductDao warehouseProductDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseProductQuery query){
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseProduct.class);
        criteria.add(eq("isValid",true));
        if(!Strings.isNullOrEmpty(query.getNameQuery())){
            criteria.add(like("name","%" + query.getNameQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getProductTypeQuery())){
            criteria.add(eq("productType",query.getProductTypeQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getBrandQuery())){
            criteria.add(eq("brand",query.getBrandQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getModelNumberQuery())){
            criteria.add(eq("modelNumber",query.getModelNumberQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getSpecificationsQuery())){
            criteria.add(eq("specifications",query.getSpecificationsQuery()));
        }

        PageBeanEasyUI pageBeanEasyUI = warehouseProductDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public WarehouseProductDTO save(WarehouseProductDTO dto){
        ValHelper.idNotExist(dto.getId());
        WarehouseProduct target = dozerMapper.map(dto,WarehouseProduct.class);
        warehouseProductDao.save(target);
        return dozerMapper.map(target, WarehouseProductDTO.class);
    }

    @Override
    public void update(WarehouseProductDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        WarehouseProduct target = warehouseProductDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        dozerMapper.map(dto,target);
        warehouseProductDao.update(target);
    }

    //判断货品是否已经存在(通过名称、型号、封装、版本),如果不存在则新增该货品,返回货品ID
    public String isProductExist(LinkedTreeMap map){
        WarehouseProductQuery productQuery = new WarehouseProductQuery();
        productQuery.setRows(1);
        productQuery.setSort("id");
        productQuery.setOrder("asc");
        productQuery.setNameQuery(map.get("productName").toString());
        productQuery.setModelNumberQuery(map.get("productModelNumber").toString());
        productQuery.setSpecificationsQuery(map.get("productSpecifications").toString());
        productQuery.setBrandQuery(map.get("productBrand").toString());
        PageBeanEasyUI list = getPageBeanByQueryBean(productQuery);

        if(list.getRows() == null){
            WarehouseProductDTO productDTO = new WarehouseProductDTO();
            productDTO.setName(map.get("productName").toString());
            productDTO.setProductType(map.get("productType").toString());
            productDTO.setModelNumber(map.get("productModelNumber").toString());
            productDTO.setSpecifications(map.get("productSpecifications").toString());
            productDTO.setBrand(map.get("productBrand").toString());
            productDTO.setUnit(map.get("productUnit").toString());
            productDTO.setProductDesc("自动添加");
            productDTO = save(productDTO);
            return productDTO.getId();
        }else{
            WarehouseProductDTO productDTO = (WarehouseProductDTO)(list.getRows().get(0));
            return productDTO.getId();
        }
    }
}
