package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseOutputQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.WarehouseOutputDao;
import com.hbyd.parks.domain.officesys.WarehouseOutput;
import com.hbyd.parks.dto.officesys.WarehouseOutputDTO;
import com.hbyd.parks.ws.officesys.WarehouseOutputWS;
import org.hibernate.criterion.DetachedCriteria;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by Zhao_d on 2017/2/20.
 */
public class WarehouseOutputWSImpl extends BaseWSImpl<WarehouseOutputDTO,WarehouseOutput> implements WarehouseOutputWS {

    @Resource
    private WarehouseOutputDao warehouseOutputDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseOutputQuery query){
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseOutput.class);
        criteria.add(eq("isValid",true));
        if(!Strings.isNullOrEmpty(query.getNumberQuery())){
            criteria.add(like("number","%" + query.getNumberQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getOutputTypeQuery())){
            criteria.add(eq("outputType",query.getOutputTypeQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getOutputDateBegQuery())){
            criteria.add(ge("outputDate",query.getOutputDateBegQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getOutputDateEndQuery())){
            criteria.add(le("outputDate",query.getOutputDateEndQuery()));
        }

        PageBeanEasyUI pageBeanEasyUI = warehouseOutputDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public WarehouseOutputDTO save(WarehouseOutputDTO dto){
        ValHelper.idNotExist(dto.getId());
        WarehouseOutput target = dozerMapper.map(dto,WarehouseOutput.class);
        warehouseOutputDao.save(target);
        return dozerMapper.map(target, WarehouseOutputDTO.class);
    }

    @Override
    public void update(WarehouseOutputDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        WarehouseOutput target = warehouseOutputDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        //首先将所有关联的表置空，否则会以为要级联更新关联表的主键而报错
        if(!Strings.isNullOrEmpty(dto.getApplicationID())) {
            target.setWarehouseApplication(null);
        }
        if(!Strings.isNullOrEmpty(dto.getWarehouseID())) {
            target.setWarehouse(null);
        }
        if(!Strings.isNullOrEmpty(dto.getRecordPersonID())) {
            target.setRecordPerson(null);
        }
        if(!Strings.isNullOrEmpty(dto.getExaminePersonID())) {
            target.setExaminePerson(null);
        }
        if(!Strings.isNullOrEmpty(dto.getCompanyId())) {
            target.setCompany(null);
        }
        dozerMapper.map(dto,target);
        warehouseOutputDao.update(target);
    }

    /**
     * 获得入库单号
     */
    public String getNewNumber(WarehouseOutputQuery query){
        String number = "";
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseOutput.class);
        String dateNow = DateTime.now().toString("yyyyMMdd");
        criteria.add(eq("isValid",true));
        criteria.add(like("number","%" + dateNow + "%"));
        PageBeanEasyUI pageBeanEasyUI = warehouseOutputDao.getPageBean(query,criteria);
        String count = String.valueOf(pageBeanEasyUI.getTotal() + 1);
        if(count.length() == 1){
            number = dateNow + "0" + count;
        }else{
            number = dateNow + count;
        }
        return number;
    }
}
