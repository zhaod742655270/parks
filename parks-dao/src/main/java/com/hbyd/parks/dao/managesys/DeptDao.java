package com.hbyd.parks.dao.managesys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.managesys.Department;

public interface DeptDao extends BaseDao<Department, String> {
    Department getByName(String name);
}
