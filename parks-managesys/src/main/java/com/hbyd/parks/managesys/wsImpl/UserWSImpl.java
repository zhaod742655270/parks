package com.hbyd.parks.managesys.wsImpl;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.managesys.DeptDao;
import com.hbyd.parks.dao.managesys.PriviledgeDao;
import com.hbyd.parks.dao.managesys.RoleDao;
import com.hbyd.parks.dao.managesys.UserDao;
import com.hbyd.parks.domain.managesys.Role;
import com.hbyd.parks.domain.managesys.User;
import com.hbyd.parks.dto.managesys.DepartmentDTO;
import com.hbyd.parks.dto.managesys.RoleDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.ws.managesys.UserWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.hbyd.parks.common.constant.TypeConstant.USER;
import static org.hibernate.criterion.Restrictions.eq;

/**
 * Created by allbutone on 14-7-15.
 */
public class UserWSImpl extends BaseWSImpl<UserDTO, User> implements UserWS {

    @Resource
    private UserDao userDao;

    @Resource
    private DeptDao deptDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private PriviledgeDao priviledgeDao;

    @Override
    public boolean checkExist(String id, String name) {
        User user = userDao.getByName(name);

        if (user != null && !id.equals(user.getId())) {//如果已经存在且修改的不是当前用户
            return true;
        }

        return false;
    }

/*
    @Override
    public void delByID(String id) {
        User target = userDao.getById(id);
//        解除和用户的关联，User.roles 代表 many2many 关系，因此删除 User 时，自动删除关联的 user_role
//        target.setRoles(null);
//        userDao.update(target);
//        删除相关权限
        priviledgeDao.delPris(id, USER);
//        删除用户本身
        userDao.delete(id);
    }
*/

    @Override
    protected void disconnect(String id) {
        priviledgeDao.delPris(id, USER);
    }

    @Override
    protected void disconnectFake(String id) {
        disconnect(id);
    }

    @Override
    public void update(UserDTO user) {
        ValHelper.notNull(user.getId(), "更新操作接收的 ID 不能为 NULL");

        Set<RoleDTO> rds = user.getRoles();
        DepartmentDTO dept = user.getDepartment();

//      置空关联属性
        user.setDepartment(null);
        user.setRoles(null);

//      获取更新目标
        User target = userDao.getById(user.getId());
        ValHelper.notNull(target, "更新目标不存在!");

//      拷贝普通属性
        dozerMapper.map(user, target);

//      拷贝关联属性
//        所属部门
        String deptId = dept.getId();
        if(deptId == null || "".equals(deptId)){//未指定部门
            target.setDepartment(null);
        }else{//界面指定的部门一定是存在的
            target.setDepartment(deptDao.getById(deptId));
        }
//        拥有的角色
        if(rds == null || rds.size()==0){//未分配角色
            target.setRoles(null);
        }else{//界面分配的角色一定是存在的
            Iterable<Role> roles = Iterables.transform(rds, new Function<RoleDTO, Role>() {
                @Nullable
                @Override
                public Role apply(RoleDTO input) {
                    return roleDao.loadById(input.getId());
                }
            });
            target.setRoles(Sets.newHashSet(roles));//User.roles owns many2many relationship
        }

        userDao.update(target);
    }

    //添加用户时需要勾选部门和角色，因此用户所属部门和角色都是存在的
    @Override
    public UserDTO save(UserDTO user) {
        ValHelper.idNotExist(user.getId());

        Set<RoleDTO> rds = user.getRoles();
        DepartmentDTO dept = user.getDepartment();

//      置空关联属性
        user.setDepartment(null);
        user.setRoles(null);

//      拷贝普通属性
        User target = dozerMapper.map(user, User.class);

//      拷贝关联属性
//        所属部门
        String deptId = dept.getId();
        if(deptId == null || "".equals(deptId)){//未指定部门
            target.setDepartment(null);
        }else{//界面指定的部门一定是存在的
            target.setDepartment(deptDao.getById(deptId));
        }
//        拥有的角色
        if(rds == null || rds.size()==0){//未分配角色
            target.setRoles(null);
        }else{//界面分配的角色一定是存在的
            Iterable<Role> roles = Iterables.transform(rds, new Function<RoleDTO, Role>() {
                @Nullable
                @Override
                public Role apply(RoleDTO input) {
                    return roleDao.loadById(input.getId());
                }
            });
            target.setRoles(Sets.newHashSet(roles));
        }

        userDao.save(target);

        return dozerMapper.map(target, UserDTO.class);
    }

    @Override
    public UserDTO getByName(String name) {
        return dozerMapper.map(userDao.getByName(name), UserDTO.class);
    }

    @Override
    public void updatePwd(String userID, String newPwd) {
        User user = userDao.getById(userID);
        user.setPassword(newPwd);
        userDao.update(user);
    }

    @Override
    public List<UserDTO> getUserByDeptName(String deptName) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
                .createAlias("department", "d")
                .add(eq("d.deptName", deptName))
                .add(eq("isValid",true));
        List<User> list = criteria.getExecutableCriteria(baseDao.getCurrSession()).list();
        List dList = new ArrayList(list.size());
        for (User user : list) {
            dList.add(dozerMapper.map(user, UserDTO.class));
        }
        return dList;
    }
}
