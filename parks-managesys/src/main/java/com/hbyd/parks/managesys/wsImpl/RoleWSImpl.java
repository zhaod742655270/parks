package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.constant.TypeConstant;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.managesys.PriviledgeDao;
import com.hbyd.parks.dao.managesys.RoleDao;
import com.hbyd.parks.dao.managesys.UserDao;
import com.hbyd.parks.domain.managesys.Role;
import com.hbyd.parks.domain.managesys.User;
import com.hbyd.parks.dto.managesys.RoleDTO;
import com.hbyd.parks.ws.managesys.RoleWS;

import javax.annotation.Resource;

/**
 * 角色服务实现：暂不支持角色的继承
 *
 * @author ren_xt
 */
public class RoleWSImpl extends BaseWSImpl<RoleDTO, Role> implements RoleWS {

    @Resource
    private RoleDao roleDao;

    @Resource
    private UserDao userDao;

    @Resource
    private PriviledgeDao priviledgeDao;

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        String id = roleDTO.getId();
        ValHelper.idNotExist(id);

//      角色目前不支持继承，因此只有普通属性
        Role target = dozerMapper.map(roleDTO, Role.class);
        roleDao.save(target);

        return dozerMapper.map(target, RoleDTO.class);
    }

    @Override
    public void delByID(String roleID) {
        ValHelper.notNull(roleID, "删除操作接收的ID 不能为 NULL");
        Role target = roleDao.getById(roleID);
        ValHelper.notNull(target, "删除的目标不存在!");

//      1. 删除角色关联的权限
        priviledgeDao.delPris(roleID, TypeConstant.ROLE);

//      2. 解除和用户的关联
        for (User user : target.getUsers()) {
            user.getRoles().remove(target);//The field that owns the relationship: User.roles
            userDao.update(user);
        }

//      3. 删除角色本身
        roleDao.delete(roleID);
    }

    @Override
    public void update(RoleDTO roleDTO) {
        String id = roleDTO.getId();
        ValHelper.notNull(id, "更新操作接收的 ID 不能为 NULL");

        Role target = roleDao.getById(id);
        ValHelper.notNull(target, "更新的目标对象不存在!");

//      更新普通属性
        dozerMapper.map(roleDTO, target);

//      执行更新
        roleDao.update(target);
    }

    @Override
    public boolean checkExist(String id, String name) {
        Role role = roleDao.getByName(name);

        if (role != null && !id.equals(role.getId())) {//如果已经存在且修改的不是当前角色
            return true;//"已存在"
        }

        return false;//不存在
    }
}
