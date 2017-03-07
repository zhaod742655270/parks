package com.hbyd.parks.dao.officesys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.officesys.ProjectRecord;

import java.util.List;

/**
 * DAO 接口：Acceptance
 */
public interface ProjectRecordDao extends BaseDao<ProjectRecord, String> {

    List<ProjectRecord> getProjectRecordList();
}
