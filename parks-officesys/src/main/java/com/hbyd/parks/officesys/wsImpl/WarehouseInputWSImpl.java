package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseInputQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.WarehouseInputDao;
import com.hbyd.parks.domain.officesys.WarehouseInput;
import com.hbyd.parks.dto.officesys.WarehouseInputDTO;
import com.hbyd.parks.ws.officesys.WarehouseInputWS;
import org.hibernate.criterion.DetachedCriteria;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by Zhao_d on 2016/12/26.
 */
public class WarehouseInputWSImpl extends BaseWSImpl<WarehouseInputDTO,WarehouseInput> implements WarehouseInputWS {

    @Resource
    private WarehouseInputDao warehouseInputDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseInputQuery query){
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseInput.class);
        criteria.add(eq("isValid",true));
        if(!Strings.isNullOrEmpty(query.getNumberQuery())){
            criteria.add(like("number","%" + query.getNumberQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getInputDateBegQuery())){
            criteria.add(ge("inputDate",query.getInputDateBegQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getInputDateEndQuery())){
            criteria.add(le("inputDate",query.getInputDateEndQuery()));
        }

        PageBeanEasyUI pageBeanEasyUI = warehouseInputDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public WarehouseInputDTO save(WarehouseInputDTO dto){
        ValHelper.idNotExist(dto.getId());
        WarehouseInput target = dozerMapper.map(dto,WarehouseInput.class);
        warehouseInputDao.save(target);
        return dozerMapper.map(target, WarehouseInputDTO.class);
    }

    @Override
    public void update(WarehouseInputDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        WarehouseInput target = warehouseInputDao.getById(dto.getId());
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
        warehouseInputDao.update(target);
    }

    /**
     * 获得入库单号
     */
    public String getNewNumber(WarehouseInputQuery query){
        String number = "";
        DetachedCriteria criteria = DetachedCriteria.forClass(WarehouseInput.class);
        String dateNow = DateTime.now().toString("yyyyMMdd");
        criteria.add(eq("isValid",true));
        criteria.add(like("number","%" + dateNow + "%"));
        PageBeanEasyUI pageBeanEasyUI = warehouseInputDao.getPageBean(query,criteria);
        String count = String.valueOf(pageBeanEasyUI.getTotal() + 1);
        if(count.length() == 1){
            number = dateNow + "0" + count;
        }else{
            number = dateNow + count;
        }
        return number;
    }
}
