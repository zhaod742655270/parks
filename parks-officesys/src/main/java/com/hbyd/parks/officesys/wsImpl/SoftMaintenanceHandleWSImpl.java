package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.SoftMaintenanceQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.SoftMaintenanceHandleDao;
import com.hbyd.parks.domain.officesys.SoftMaintenanceHandle;
import com.hbyd.parks.dto.officesys.SoftMaintenanceHandleDTO;
import com.hbyd.parks.ws.officesys.SoftMaintenanceHandleWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Created by Zhao_d on 2016/12/21.
 */
public class SoftMaintenanceHandleWSImpl extends BaseWSImpl<SoftMaintenanceHandleDTO,SoftMaintenanceHandle> implements SoftMaintenanceHandleWS {

    @Resource
    SoftMaintenanceHandleDao softMaintenanceHandleDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(SoftMaintenanceQuery query, String parentIdFK){
            DetachedCriteria criteria = DetachedCriteria.forClass(SoftMaintenanceHandle.class);
            criteria.add(eq("isValid", true));
            criteria.add(eq("softMaintenance.id",parentIdFK));
            if (!Strings.isNullOrEmpty(query.getHandlePersonQuery())) {
                criteria.add(like("handlePerson", "%" + query.getHandlePersonQuery() + "%"));
            }
            if (!Strings.isNullOrEmpty(query.getHandleBegTimeQuery())) {
                criteria.add(eq("handleBegTime", query.getHandleBegTimeQuery()));
            }
            if (!Strings.isNullOrEmpty(query.getHandleEndTimeQuery())) {
                criteria.add(eq("handleEndTime", query.getHandleEndTimeQuery()));
            }
            if (!Strings.isNullOrEmpty(query.getHandleResultQuery())) {
                criteria.add(like("handleResult", "%" + query.getHandleResultQuery() + "%"));
            }
            PageBeanEasyUI pageBeanEasyUI = null;
            pageBeanEasyUI = softMaintenanceHandleDao.getPageBean(query, criteria);
            List list = getDTOList(pageBeanEasyUI.getRows());
            pageBeanEasyUI.setRows(list);
            return pageBeanEasyUI;
    }

    @Override
    public SoftMaintenanceHandleDTO save(SoftMaintenanceHandleDTO dto){
        ValHelper.idNotExist(dto.getId());
        SoftMaintenanceHandle target = dozerMapper.map(dto,SoftMaintenanceHandle.class);
        softMaintenanceHandleDao.save(target);
        return dozerMapper.map(target, SoftMaintenanceHandleDTO.class);
    }

    @Override
    public void update(SoftMaintenanceHandleDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        SoftMaintenanceHandle target = softMaintenanceHandleDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        //首先将所有关联的表置空，否则会以为要级联更新关联表的主键而报错
        target.setHandlePerson(null);
        dozerMapper.map(dto, target);
        softMaintenanceHandleDao.update(target);
    }
}
