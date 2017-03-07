package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseApplicationQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.WarehouseApplicationDao;
import com.hbyd.parks.domain.officesys.WarehouseApplication;
import com.hbyd.parks.dto.officesys.WarehouseApplicationDTO;
import com.hbyd.parks.ws.officesys.WarehouseApplicationWS;
import org.hibernate.criterion.DetachedCriteria;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by Zhao_d on 2017/1/22.
 */
public class WarehouseApplicationWSImpl extends BaseWSImpl<WarehouseApplicationDTO,WarehouseApplication> implements WarehouseApplicationWS{

    @Resource
    private WarehouseApplicationDao warehouseApplicationDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseApplicationQuery query){
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseApplication.class);
        criteria.add(eq("isValid",true));
        if(!Strings.isNullOrEmpty(query.getNameQuery())){
            criteria.add(like("name","%" + query.getNameQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getTypeQuery())){
            criteria.add(like("type","%" + query.getTypeQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getRecordDateBegQuery())){
            criteria.add(ge("recordDate",query.getRecordDateBegQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getRecordDateEndQuery())){
            criteria.add(le("recordDate",query.getRecordDateEndQuery()));
        }

        PageBeanEasyUI pageBeanEasyUI = warehouseApplicationDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public WarehouseApplicationDTO save(WarehouseApplicationDTO dto){
        ValHelper.idNotExist(dto.getId());
        WarehouseApplication target = dozerMapper.map(dto,WarehouseApplication.class);
        warehouseApplicationDao.save(target);
        return dozerMapper.map(target, WarehouseApplicationDTO.class);
    }

    @Override
    public void update(WarehouseApplicationDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        WarehouseApplication target = warehouseApplicationDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        //首先将所有关联的表置空，否则会以为要级联更新关联表的主键而报错
        target.setRecordPerson(null);
        dozerMapper.map(dto,target);
        warehouseApplicationDao.update(target);
    }

    /**
     * 获得申请单号
     */
    public String getNewNumber(){
        WarehouseApplicationQuery query = new WarehouseApplicationQuery();
        query.setSort("id");
        query.setOrder("asc");
        query.setRows(1000);
        String number = "";
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseApplication.class);
        String dateNow = DateTime.now().toString("yyyyMMdd");
        criteria.add(eq("isValid",true));
        criteria.add(like("number","%" + dateNow + "%"));
        PageBeanEasyUI pageBeanEasyUI = warehouseApplicationDao.getPageBean(query,criteria);
        String count = String.valueOf(pageBeanEasyUI.getTotal() + 1);
        if(count.length() == 1){
            number = dateNow + "0" + count;
        }else{
            number = dateNow + count;
        }
        return number;
    }
}
