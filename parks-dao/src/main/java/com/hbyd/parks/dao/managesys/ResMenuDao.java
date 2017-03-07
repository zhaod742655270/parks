package com.hbyd.parks.dao.managesys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.managesys.ResMenu;

/**
 * Created by allbutone on 14-7-18.
 */
public interface ResMenuDao extends BaseDao<ResMenu, String> {
    ResMenu getByName(String name);
}
