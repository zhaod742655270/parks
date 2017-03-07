package com.hbyd.parks.daoImpl.hibernate.attendancesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.attendancesys.AtteInfoDao;
import com.hbyd.parks.domain.attendancesys.AtteInfo;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import java.util.Date;

import static org.hibernate.criterion.Restrictions.*;
/**
 * 考勤班次
 */
@Repository
public class AtteInfoDaoImpl extends BaseDaoImpl<AtteInfo, String> implements AtteInfoDao {
    @Override
    public AtteInfo getDayInfo(String empId, Date day){
        DetachedCriteria criteria = DetachedCriteria.forClass(AtteInfo.class)
                .add(eq("day", day))
                .add(eq("empFK", empId));

        return findOneByCriteria(criteria);
    }
}
