package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.WarehouseDao;
import com.hbyd.parks.domain.officesys.Warehouse;
import com.hbyd.parks.domain.officesys.WarehouseBorrow;
import com.hbyd.parks.domain.officesys.WarehouseInputPro;
import com.hbyd.parks.domain.officesys.WarehouseOutputPro;
import com.hbyd.parks.dto.officesys.WarehouseDTO;
import com.hbyd.parks.ws.officesys.WarehouseWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Created by Zhao_d on 2017/2/9.
 */
public class WarehouseWSImpl extends BaseWSImpl<WarehouseDTO,Warehouse> implements WarehouseWS {

    @Resource
    private WarehouseDao warehouseDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseQuery query){
        DetachedCriteria criteria = DetachedCriteria.forClass(Warehouse.class);
        criteria.add(eq("isValid",true));

        if(!Strings.isNullOrEmpty(query.getProductIdQuery())){
            criteria.createAlias("warehouseProduct","warehouseProduct")
                    .add(eq("warehouseProduct.id",query.getProductIdQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getNameQuery())){
            criteria.createAlias("warehouseProduct","warehouseProduct")
                    .add(like("warehouseProduct.name","%" + query.getNameQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getTypeQuery())){
            criteria.createAlias("warehouseProduct","warehouseProduct")
                    .add(eq("warehouseProduct.productType",query.getTypeQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getBrandQuery())){
            criteria.createAlias("warehouseProduct","warehouseProduct")
                    .add(eq("warehouseProduct.brand",query.getBrandQuery()));
        }

        PageBeanEasyUI pageBeanEasyUI = warehouseDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public WarehouseDTO save(WarehouseDTO dto){
        ValHelper.idNotExist(dto.getId());
        Warehouse target = dozerMapper.map(dto,Warehouse.class);
        warehouseDao.save(target);
        return dozerMapper.map(target, WarehouseDTO.class);
    }

    @Override
    public void update(WarehouseDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        Warehouse target = warehouseDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        dozerMapper.map(dto,target);
        warehouseDao.update(target);
    }

    //新增货品时在库存中添加信息
    public void addProduct(String productId){
        WarehouseDTO dto = new WarehouseDTO();
        dto.setProductId(productId);
        dto.setQuantity(0d);
        dto.setQuantityBorrow(0d);
        dto.setQuantityUse(0d);
        save(dto);
    }

    //统计出入库数据
    public Double getStatisticsForInputOutput(String warehouseId){
        Warehouse warehouse = warehouseDao.getById(warehouseId);
        Double quantity = 0d;
        Set<WarehouseInputPro> warehouseInputPros = warehouse.getWarehouseInputPros();
        Set<WarehouseOutputPro> warehouseOutputPros = warehouse.getWarehouseOutputPros();
        if(warehouseInputPros != null) {
            for (WarehouseInputPro warehouseInputPro : warehouseInputPros){
                if(warehouseInputPro.isValid() && warehouseInputPro.getWarehouseInput().isValid()) {
                    quantity += warehouseInputPro.getQuantity();
                }
            }
        }
        if(warehouseOutputPros != null) {
            for (WarehouseOutputPro warehouseOutputPro : warehouseOutputPros){
                if(warehouseOutputPro.isValid() && warehouseOutputPro.getWarehouseOutput().isValid()) {
                    quantity -= warehouseOutputPro.getQuantity();
                }
            }
        }
        return quantity;
    }

    //统计借用数据
    public Double getStatisticsForBorrow(String warehouseId) {
        Warehouse warehouse = warehouseDao.getById(warehouseId);
        Double borrow = 0d;
        Set<WarehouseBorrow> warehouseBorrows = warehouse.getWarehouseBorrows();
        if(warehouseBorrows != null) {
            for (WarehouseBorrow warehouseBorrow : warehouseBorrows){
                if(warehouseBorrow.isValid() && warehouseBorrow.getState().equals("未归还")) {
                    borrow += warehouseBorrow.getQuantity();
                }
            }
        }
        return borrow;
    }

    //修改库存数量
    public void changeQuantity(String productId,Double quantity,Double newCost){
        //获得原库存数据
        WarehouseQuery query = new WarehouseQuery();
        query.setSort("id");
        query.setOrder("asc");
        query.setRows(1);
        query.setProductIdQuery(productId);
        PageBeanEasyUI page = getPageBeanByQueryBean(query);
        WarehouseDTO dto = (WarehouseDTO) page.getRows().get(0);

        //更新数据
        if(quantity != null) {
            dto.setQuantity(dto.getQuantity() + quantity);
            dto.setQuantityUse(dto.getQuantityUse() + quantity);
        }
        if(newCost != null) {
            dto.setNewCost(newCost);
        }
            update(dto);
    }

    public void changeQuantityByBorrow(String productId,Double quantity){
        //获得原库存数据
        WarehouseQuery query = new WarehouseQuery();
        query.setSort("id");
        query.setOrder("asc");
        query.setRows(1);
        query.setProductIdQuery(productId);
        PageBeanEasyUI page = getPageBeanByQueryBean(query);
        WarehouseDTO dto = (WarehouseDTO) page.getRows().get(0);

        //更新数据
        if(quantity != null) {
            dto.setQuantityBorrow(dto.getQuantityBorrow() + quantity);
            dto.setQuantityUse(dto.getQuantityUse() - quantity);
        }
        update(dto);
    }
}
