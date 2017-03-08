package com.hbyd.parks.daoImpl.hibernate.managesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.managesys.UserDao;
import com.hbyd.parks.domain.managesys.User;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao {
    @Override
    public User getByName(String name) {
        User user = getByName("userName", name);
        if(user.isValid()){
            return user;
        }else{
            return null;
        }
    }

    @Override
    public List<User> getUsersWithinDept(String deptId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(eq("department.id", deptId))
                .add(eq("isValid",true));

        return findListByCriteria(criteria);
    }
}
