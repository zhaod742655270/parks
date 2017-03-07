package com.hbyd.parks.daoImpl.hibernate.attendancesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.attendancesys.AtteDetailDao;
import com.hbyd.parks.domain.attendancesys.AtteDetail;
import org.springframework.stereotype.Repository;

/**
 * 考勤时段
 */
@Repository
public class AtteDetailDaoImpl extends BaseDaoImpl<AtteDetail, String> implements AtteDetailDao {
}
