package com.hbyd.parks.daoImpl.hibernate.managesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.managesys.ResMenuDao;
import com.hbyd.parks.domain.managesys.ResMenu;
import org.springframework.stereotype.Repository;

/**
 * Created by allbutone on 14-7-18.
 */
@Repository
public class ResMenuDaoImpl extends BaseDaoImpl<ResMenu, String> implements ResMenuDao {
    @Override
    public ResMenu getByName(String name) {
        return getByName("menuName", name);
    }
}
