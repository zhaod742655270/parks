package com.hbyd.parks.daoImpl.hibernate.supportsys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.supportsys.RegionDao;
import com.hbyd.parks.domain.supportsys.Region;
import org.springframework.stereotype.Repository;

/**
 * 区域 DAO 实现
 */
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region, String> implements RegionDao {

}
