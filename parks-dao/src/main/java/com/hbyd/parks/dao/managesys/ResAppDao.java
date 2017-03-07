package com.hbyd.parks.dao.managesys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.managesys.ResApp;

/**
 * Created by allbutone on 14-7-15.
 */
public interface ResAppDao extends BaseDao<ResApp, String> {
    ResApp getByName(String name);
}
