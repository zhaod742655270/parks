package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.RecordQueryBean;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.ProjectRecordDao;
import com.hbyd.parks.domain.officesys.ProjectRecord;
import com.hbyd.parks.dto.officesys.ProjectRecordDTO;
import com.hbyd.parks.ws.officesys.ProjectRecordWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/4/25
 */
public class ProjectRecordWSImpl extends BaseWSImpl<ProjectRecordDTO, ProjectRecord> implements ProjectRecordWS {

    @Resource
    private ProjectRecordDao projectRecordDao;


    @Override
    public PageBeanEasyUI getPageBeanByRecordBean(RecordQueryBean queryBean) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ProjectRecord.class);

        criteria = getCriteria(criteria, queryBean);
        PageBeanEasyUI pageBeanEasyUI = projectRecordDao.getPageBean(queryBean, criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;

    }
    private DetachedCriteria getCriteria(DetachedCriteria criteria, RecordQueryBean queryBean) {

        criteria.add(eq("isValid", true));

        if (!Strings.isNullOrEmpty(queryBean.getSheetNameQuery()))
            criteria.add(eq("sheetName",  queryBean.getSheetNameQuery()));

        if (!Strings.isNullOrEmpty(queryBean.getTypeQuery()))
            criteria.add(eq("type", queryBean.getTypeQuery()));

        if (!Strings.isNullOrEmpty(queryBean.getNameQuery()))
            criteria.add(like("name", "%" + queryBean.getNameQuery() + "%"));

        return criteria;
    }


    @Override
    public ProjectRecordDTO save(ProjectRecordDTO dto) {
        ValHelper.idNotExist(dto.getId());

        ProjectRecord target = dozerMapper.map(dto, ProjectRecord.class);
        projectRecordDao.save(target);
        return dozerMapper.map(target, ProjectRecordDTO.class);
    }


    @Override
    public void update(ProjectRecordDTO dto) {
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        ProjectRecord target =projectRecordDao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");

        dozerMapper.map(dto, target);
        projectRecordDao.update(target);

    }

}
