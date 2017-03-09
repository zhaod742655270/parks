package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseBorrowQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.WarehouseBorrowDao;
import com.hbyd.parks.domain.officesys.WarehouseBorrow;
import com.hbyd.parks.dto.officesys.WarehouseBorrowDTO;
import com.hbyd.parks.ws.officesys.WarehouseBorrowWS;
import org.hibernate.criterion.DetachedCriteria;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by Zhao_d on 2017/3/3.
 */
public class WarehouseBorrowWSImpl extends BaseWSImpl<WarehouseBorrowDTO,WarehouseBorrow> implements WarehouseBorrowWS{

    @Resource
    private WarehouseBorrowDao warehouseBorrowDao;

    @Override
    public PageBeanEasyUI getPageBeanByQuery(WarehouseBorrowQuery query){
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseBorrow.class);
        criteria.add(eq("isValid",true));

        if(!Strings.isNullOrEmpty(query.getBorrowPersonQuery())){
            criteria.createAlias("borrowPerson","borrowPerson")
                    .add(eq("borrowPerson.id",query.getBorrowPersonQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getStateQuery())){
            criteria.add(eq("state",query.getStateQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getBorrowDateBegQuery())){
            criteria.add(ge("borrowDate",query.getBorrowDateBegQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getBorrowDateEndQuery())){
            criteria.add(le("borrowDate",query.getBorrowDateEndQuery()));
        }

        PageBeanEasyUI pageBeanEasyUI = warehouseBorrowDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public WarehouseBorrowDTO save(WarehouseBorrowDTO dto){
        ValHelper.idNotExist(dto.getId());
        WarehouseBorrow target = dozerMapper.map(dto,WarehouseBorrow.class);
        warehouseBorrowDao.save(target);
        return dozerMapper.map(target, WarehouseBorrowDTO.class);
    }

    @Override
    public void update(WarehouseBorrowDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        WarehouseBorrow target = warehouseBorrowDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        //首先将所有关联的表置空，否则会以为要级联更新关联表的主键而报错
        if(!Strings.isNullOrEmpty(dto.getBorrowPersonId())) {
            target.setBorrowPerson(null);
        }
        if(!Strings.isNullOrEmpty(dto.getProductId())) {
            target.setWarehouseProduct(null);
        }
        dozerMapper.map(dto,target);
        warehouseBorrowDao.update(target);
    }

    /**
     * 获得借用遍号
     */
    public String getNewNumber(WarehouseBorrowQuery query){
        String number = "";
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseBorrow.class);
        String dateNow = DateTime.now().toString("yyyyMMdd");
        criteria.add(eq("isValid",true));
        criteria.add(like("number","%" + dateNow + "%"));
        PageBeanEasyUI pageBeanEasyUI = warehouseBorrowDao.getPageBean(query,criteria);
        String count = String.valueOf(pageBeanEasyUI.getTotal() + 1);
        if(count.length() == 1){
            number = dateNow + "0" + count;
        }else{
            number = dateNow + count;
        }
        return number;
    }
}
