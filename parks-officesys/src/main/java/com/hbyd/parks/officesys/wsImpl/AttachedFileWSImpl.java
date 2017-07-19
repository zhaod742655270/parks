package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.AttachedFileDao;
import com.hbyd.parks.domain.officesys.AttachedFile;
import com.hbyd.parks.dto.officesys.AttachedFileDTO;
import com.hbyd.parks.ws.officesys.AttachedFileWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * Created by Zhao_d on 2017/7/10.
 */
public class AttachedFileWSImpl extends BaseWSImpl<AttachedFileDTO, AttachedFile> implements AttachedFileWS{

    @Resource
    private AttachedFileDao dao;

    @Override
    public PageBeanEasyUI getPageBeanByAcceptanceID(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AttachedFile.class)
                .createAlias("acceptance", "a");
        criteria.add(eq("isValid", true));
        if (!Strings.isNullOrEmpty(id)) {
            criteria.add(eq("a.id", id));
        }
        QueryBeanEasyUI query = new QueryBeanEasyUI();
        query.setSort("uploadDate");
        query.setOrder("desc");
        query.setRows(1000);
        PageBeanEasyUI page = dao.getPageBean(query,criteria);
        List list = getDTOList(page.getRows());
        page.setRows(list);
        return page;
    }

    @Override
    public AttachedFileDTO save(AttachedFileDTO dto) {
        ValHelper.idNotExist(dto.getId());

        AttachedFile target = dozerMapper.map(dto, AttachedFile.class);
        dao.save(target);
        return dozerMapper.map(target, AttachedFileDTO.class);
    }

    @Override
    public void update(AttachedFileDTO dto) {
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        AttachedFile target = dao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");

        dozerMapper.map(dto, target);
        dao.update(target);

    }
}
