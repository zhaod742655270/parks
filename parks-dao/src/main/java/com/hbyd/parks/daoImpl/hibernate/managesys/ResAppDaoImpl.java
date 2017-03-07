package com.hbyd.parks.daoImpl.hibernate.managesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.managesys.ResAppDao;
import com.hbyd.parks.domain.managesys.ResApp;
import org.springframework.stereotype.Repository;

/**
 * Created by allbutone on 14-7-15.
 */
@Repository
public class ResAppDaoImpl extends BaseDaoImpl<ResApp, String> implements ResAppDao {
    @Override
    public ResApp getByName(String name) {
        return getByName("appName", name);
    }
}
