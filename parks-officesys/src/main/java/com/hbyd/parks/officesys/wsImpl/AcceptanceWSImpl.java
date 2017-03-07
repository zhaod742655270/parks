package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.AcceptanceQuery;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.AcceptanceDao;
import com.hbyd.parks.dao.officesys.AcceptancePostilDao;
import com.hbyd.parks.dao.officesys.ContractGatheringDao;
import com.hbyd.parks.domain.managesys.User;
import com.hbyd.parks.domain.officesys.Acceptance;
import com.hbyd.parks.domain.officesys.ContractGathering;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.dto.officesys.AcceptanceDTO;
import com.hbyd.parks.dto.officesys.ContractGatheringDTO;
import com.hbyd.parks.ws.officesys.AcceptanceWS;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.in;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/4/25
 */
public class AcceptanceWSImpl extends BaseWSImpl<AcceptanceDTO, Acceptance> implements AcceptanceWS {

    @Resource
    private AcceptanceDao acceptanceDao;

    @Resource
    private ContractGatheringDao contractGatheringDao;

    @Resource
    private AcceptancePostilDao acceptancePostilDao;

    @Override
    public List<ContractGatheringDTO> getProjectByYear(String year) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ContractGathering.class)
                .add(eq("sheetName", year))
                .add(in("projectType",new String[]{"弱电项目","零星项目"}));
        List<ContractGathering> list = criteria.getExecutableCriteria(baseDao.getCurrSession()).list();
        List dList = new ArrayList(list.size());
        for (ContractGathering contractGathering : list) {
            dList.add(dozerMapper.map(contractGathering, ContractGatheringDTO.class));
        }
        return dList;

    }

    @Override
    public List<UserDTO> getPurchaser() {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
                .createAlias("department", "d")
                .add(eq("d.deptName", "采购部"));
        List<User> list = criteria.getExecutableCriteria(baseDao.getCurrSession()).list();
        List dList = new ArrayList(list.size());
        for (User user : list) {
            dList.add(dozerMapper.map(user, UserDTO.class));
        }
        return dList;
    }

    @Override
    public List<UserDTO> getProjectManager() {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
                .createAlias("department", "d")
                .add(eq("d.deptName", "工程部"));
        List<User> list = criteria.getExecutableCriteria(baseDao.getCurrSession()).list();
        List dList = new ArrayList(list.size());
        for (User user : list) {
            dList.add(dozerMapper.map(user, UserDTO.class));
        }
        return dList;
    }

    @Override
    public PageBeanEasyUI getPageBeanByContractID(String id,AcceptanceQuery page) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Acceptance.class)
                .createAlias("contractGathering", "c");
        criteria.add(eq("isValid", true));
        if (!Strings.isNullOrEmpty(id)) {
            criteria.add(eq("c.id", id));
        }

        if(!Strings.isNullOrEmpty(page.getEquipmentName())){
            criteria.add(like("equipmentName", "%"+page.getEquipmentName()+"%"));
        }

        if(!Strings.isNullOrEmpty(page.getSpecification())){
            criteria.add(like("specification","%"+page.getSpecification()+ "%"));
        }
        PageBeanEasyUI pageBeanEasyUI = acceptanceDao.getPageBean(page, criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }


    @Override
    public AcceptanceDTO save(AcceptanceDTO dto) {
        ValHelper.idNotExist(dto.getId());

////      保存只涉及普通属性
//        dto.setContractGatheringPostil(null);

        Acceptance target = dozerMapper.map(dto, Acceptance.class);
        acceptanceDao.save(target);
        return dozerMapper.map(target, AcceptanceDTO.class);
    }


    @Override
    public void update(AcceptanceDTO dto) {
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        Acceptance target = acceptanceDao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");
        //首先将所有关联的表置空，否则会以为要级联更新关联表的主键而报错
        target.setPurchaser(null);
        target.setProjectManager(null);
        target.setContractGathering(null);
        dozerMapper.map(dto, target);
        acceptanceDao.update(target);

    }

    @Override
    public void delByID(String id) {
        Acceptance acceptance = acceptanceDao.getById(id);
        if (acceptance.getAcceptancePostil() != null) {
            String acceptaceID = acceptance.getAcceptancePostil().getId();
            acceptance.getAcceptancePostil().setAcceptance(null);
            acceptancePostilDao.delete(acceptaceID);
        }

        acceptanceDao.update(acceptance);
    }


    @Override
    public String getUserFK(String userName) {
        String sql="select id from base_user u where u.userName='"+userName+"'" ;
        Session session = sessionFactory.getCurrentSession();
        ArrayList list = (ArrayList) session.createSQLQuery(sql).list();
        if(list.size()>0) {
            return (String)list.get(0);
        }else{
            return null;
        }

    }

    @Override
    public String getContractFK(String contractName,String sheetName,String contractNo) {
        String sql="select id from oa_contract_gathering_new o where (o.contractName='"+contractName+"' and o.sheetName='"+sheetName+"'and o.contractNo='"+contractNo+"')" ;
        Session session = sessionFactory.getCurrentSession();
        ArrayList list = (ArrayList) session.createSQLQuery(sql).list();
        if(list.size()>0) {
            return (String)list.get(0);
        }else{
            return null;
        }
    }
}
