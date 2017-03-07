package com.hbyd.parks.daoImpl.hibernate.managesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.managesys.UserDao;
import com.hbyd.parks.domain.managesys.User;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao {
    @Override
    public User getByName(String name) {
        return getByName("userName", name);
    }

    @Override
    public List<User> getUsersWithinDept(String deptId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq("department.id", deptId));

        return findListByCriteria(criteria);
    }
}
