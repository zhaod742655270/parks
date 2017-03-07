package com.hbyd.parks.dao.managesys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.managesys.Role;

public interface RoleDao extends BaseDao<Role, String> {
    Role getByName(String name);
}
