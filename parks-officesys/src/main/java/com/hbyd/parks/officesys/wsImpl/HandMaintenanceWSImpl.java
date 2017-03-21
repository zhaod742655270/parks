package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.HandMaintenanceQuery;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.HandMaintenanceDao;
import com.hbyd.parks.domain.officesys.HandMaintenance;
import com.hbyd.parks.dto.officesys.HandMaintenanceDTO;
import com.hbyd.parks.ws.officesys.HandMaintenanceWS;
import org.hibernate.criterion.DetachedCriteria;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by Zhao_d on 2016/12/14.
 */
public class HandMaintenanceWSImpl extends BaseWSImpl<HandMaintenanceDTO,HandMaintenance> implements HandMaintenanceWS {

    @Resource
    HandMaintenanceDao handMaintenanceDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(HandMaintenanceQuery query){
        DetachedCriteria criteria = DetachedCriteria.forClass(HandMaintenance.class);
        //添加查询条件
        criteria.add(eq("isValid",true));
        if(!Strings.isNullOrEmpty(query.getIdQuery())){
            criteria.add(eq("id",query.getIdQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getProjectNameQuery())){
            criteria.add(like("projectName",query.getProjectNameQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getProductNameQuery())){
            criteria.add(like("productName",query.getProductNameQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getRegisterPersonQuery())){
            criteria.createAlias("registerPerson","registerPerson")
                    .add(eq("registerPerson.id",query.getRegisterPersonQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getNumberQuery())){
            criteria.add(like("number",query.getNumberQuery()));
        }
        if (!Strings.isNullOrEmpty(query.getRegDateBegQuery())) {
            criteria.add(ge("registerDate", query.getRegDateBegQuery()));
        }
        if (!Strings.isNullOrEmpty(query.getRegDateEndQuery())) {
            criteria.add(le("registerDate", query.getRegDateEndQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getAssignPersonQuery())){
            criteria.createAlias("assignPerson","assignPerson")
                    .add(eq("assignPerson.id",query.getAssignPersonQuery()));
        }
        PageBeanEasyUI pageBeanEasyUI = handMaintenanceDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public HandMaintenanceDTO save(HandMaintenanceDTO dto){
        ValHelper.idNotExist(dto.getId());
        HandMaintenance target = dozerMapper.map(dto,HandMaintenance.class);
        handMaintenanceDao.save(target);
        return dozerMapper.map(target, HandMaintenanceDTO.class);
    }

    @Override
    public void update(HandMaintenanceDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        HandMaintenance target = handMaintenanceDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        //将ID为空的人员类字段置为NULL，防止出现脏数据
        if(Strings.isNullOrEmpty(dto.getVerifyPersonID())){
            dto.setVerifyPersonID(null);
        }
        if(Strings.isNullOrEmpty(dto.getAnalyPersonID())){
            dto.setAnalyPersonID(null);
        }
        if(Strings.isNullOrEmpty(dto.getRepairPersonID())){
            dto.setRepairPersonID(null);
        }
        if(Strings.isNullOrEmpty(dto.getTestPersonID())){
            dto.setTestPersonID(null);
        }
        //首先将所有关联的表置空，否则会以为要级联更新关联表的主键而报错
        if (!Strings.isNullOrEmpty(dto.getRegisterPersonID())) {
            target.setRegisterPerson(null);
        }
        if (!Strings.isNullOrEmpty(dto.getApprovePersonID())) {
            target.setApprovePerson(null);
        }
        if (!Strings.isNullOrEmpty(dto.getReportPersonID())) {
            target.setReportPerson(null);
        }
        if (!Strings.isNullOrEmpty(dto.getVerifyPersonID())) {
            target.setVerifyPerson(null);
        }
        if (!Strings.isNullOrEmpty(dto.getAnalyPersonID())) {
            target.setAnalyPerson(null);
        }
        if (!Strings.isNullOrEmpty(dto.getRepairPersonID())) {
            target.setRepairPerson(null);
        }
        if (!Strings.isNullOrEmpty(dto.getTestPersonID())) {
            target.setTestPerson(null);
        }
        if(!Strings.isNullOrEmpty(dto.getAssignPersonId())) {
            target.setAssignPerson(null);
        }
        dozerMapper.map(dto, target);
        handMaintenanceDao.update(target);
    }

    public String getNewNumber(HandMaintenanceQuery query){
        String number = "";
        DetachedCriteria criteria = DetachedCriteria.forClass(HandMaintenance.class);
        String dateNow = DateTime.now().toString("yyyyMMdd");
        criteria.add(eq("isValid",true));
        criteria.add(like("number","%" + dateNow + "%"));
        PageBeanEasyUI pageBeanEasyUI = handMaintenanceDao.getPageBean(query,criteria);
        String count = String.valueOf(pageBeanEasyUI.getTotal() + 1);
        if(count.length() == 1){
            number = dateNow + "0" + count;
        }else{
            number = dateNow + count;
        }
        return number;
    }
}
