package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.SoftMaintenanceQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.managesys.UserDao;
import com.hbyd.parks.dao.officesys.SoftMaintenanceDao;
import com.hbyd.parks.domain.officesys.SoftMaintenance;
import com.hbyd.parks.dto.officesys.SoftMaintenanceDTO;
import com.hbyd.parks.ws.officesys.SoftMaintenanceWS;
import org.hibernate.criterion.DetachedCriteria;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by Zhao_d on 2016/12/9.
 */
public class SoftMaintenanceWSImpl extends BaseWSImpl<SoftMaintenanceDTO,SoftMaintenance> implements SoftMaintenanceWS {

    @Resource
    SoftMaintenanceDao softMaintenanceDao;

    @Resource
    UserDao userDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(SoftMaintenanceQuery query){
        DetachedCriteria criteria = DetachedCriteria.forClass(SoftMaintenance.class);
        criteria.add(eq("isValid",true));
        if(!Strings.isNullOrEmpty(query.getProjectNameQuery())){
            criteria.add(like("projectName","%" + query.getProjectNameQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getProductNameQuery())){
            criteria.add(like("productName","%" + query.getProductNameQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getRegPersonQuery())){
            criteria.add(like("regPerson","%" + query.getRegPersonQuery() + "%"));
        }
        if(!Strings.isNullOrEmpty(query.getNumberQuery())){
            criteria.add(like("number","%" + query.getNumberQuery() + "%"));
        }
        if (!Strings.isNullOrEmpty(query.getRegDateBegQuery())) {
            criteria.add(ge("regDate", query.getRegDateBegQuery()));
        }
        if (!Strings.isNullOrEmpty(query.getRegDateEndQuery())) {
            criteria.add(le("regDate", query.getRegDateEndQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getIdQuery())){
            criteria.add(eq("id",query.getIdQuery()));
        }
        PageBeanEasyUI pageBeanEasyUI = softMaintenanceDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public SoftMaintenanceDTO save(SoftMaintenanceDTO dto){
        ValHelper.idNotExist(dto.getId());
        SoftMaintenance target = dozerMapper.map(dto,SoftMaintenance.class);
        softMaintenanceDao.save(target);
        return dozerMapper.map(target, SoftMaintenanceDTO.class);
    }

    @Override
    public void update(SoftMaintenanceDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        SoftMaintenance target = softMaintenanceDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        //首先将所有关联的表置空，否则会以为要级联更新关联表的主键而报错
        target.setRegPerson(null);
        target.setContracts(null);
        target.setResultPerson(null);
        dozerMapper.map(dto, target);
        softMaintenanceDao.update(target);
    }

    public String getNewNumber(SoftMaintenanceQuery query){
        String number = "";
        DetachedCriteria criteria = DetachedCriteria.forClass(SoftMaintenance.class);
        String dateNow = DateTime.now().toString("yyyyMMdd");
        criteria.add(eq("isValid",true));
        criteria.add(like("number","%" + dateNow + "%"));
        PageBeanEasyUI pageBeanEasyUI = softMaintenanceDao.getPageBean(query,criteria);
        String count = String.valueOf(pageBeanEasyUI.getTotal() + 1);
        if(count.length() == 1){
            number = dateNow + "0" + count;
        }else{
            number = dateNow + count;
        }
        return number;
    }
}
