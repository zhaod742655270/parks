package com.hbyd.parks.daoImpl.hibernate.managesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.managesys.RoleDao;
import com.hbyd.parks.domain.managesys.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role, String> implements RoleDao {
    @Override
    public Role getByName(String name) {
        return getByName("roleName", name);
    }
}
