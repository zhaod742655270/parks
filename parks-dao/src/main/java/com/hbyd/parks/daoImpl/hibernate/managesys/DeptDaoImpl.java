package com.hbyd.parks.daoImpl.hibernate.managesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.managesys.DeptDao;
import com.hbyd.parks.domain.managesys.Department;
import org.springframework.stereotype.Repository;

@Repository
public class DeptDaoImpl extends BaseDaoImpl<Department, String> implements DeptDao {
    @Override
    public Department getByName(String name) {
        return getByName("deptName", name);
    }
}
