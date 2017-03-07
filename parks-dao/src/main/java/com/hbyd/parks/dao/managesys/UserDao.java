package com.hbyd.parks.dao.managesys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.managesys.User;

import java.util.List;

public interface UserDao extends BaseDao<User, String> {
    User getByName(String name);

    /**获取某部门下的用户
     * @param deptId 部门ID
     * @return 用户列表
     */
    List<User> getUsersWithinDept(String deptId);
}
