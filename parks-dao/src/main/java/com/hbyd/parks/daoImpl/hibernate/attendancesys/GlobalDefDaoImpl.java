package com.hbyd.parks.daoImpl.hibernate.attendancesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.attendancesys.GlobalDefDao;
import com.hbyd.parks.domain.attendancesys.GlobalDef;
import org.springframework.stereotype.Repository;

/**
 * 全局定义
 */
@Repository
public class GlobalDefDaoImpl extends BaseDaoImpl<GlobalDef,String> implements GlobalDefDao {

    @Override
    public GlobalDef getLatest() {
        return findAll().get(0);
    }
}
