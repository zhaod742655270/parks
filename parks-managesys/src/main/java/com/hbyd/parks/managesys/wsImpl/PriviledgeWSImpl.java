package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.constant.TypeConstant;
import com.hbyd.parks.dao.managesys.*;
import com.hbyd.parks.domain.managesys.*;
import com.hbyd.parks.dto.managesys.PriviledgeDTO;
import com.hbyd.parks.dto.managesys.ResAppDTO;
import com.hbyd.parks.dto.managesys.ResBtnDTO;
import com.hbyd.parks.dto.managesys.ResMenuDTO;
import com.hbyd.parks.ws.managesys.PriviledgeWS;
import com.hbyd.parks.common.util.ValHelper;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import javax.annotation.Resource;
import java.util.*;

/**
 * 实现权限服务
 * Created by allbutone on 14-7-14.
 */
public class PriviledgeWSImpl extends BaseWSImpl<PriviledgeDTO, Priviledge> implements PriviledgeWS {

    @Resource
    private UserDao userDao;

    @Resource
    private PriviledgeDao priviledgeDao;

    @Resource
    private ResAppDao resAppDao;

    @Resource
    private ResMenuDao resMenuDao;

    @Resource
    private ResBtnDao resBtnDao;

    @Override
    public Collection<ResAppDTO> getApps(String userId) {
        String hql = "select a from Priviledge p, ResApp a where p.priResId=a.id AND p.priOwnerId in (:ids) order by appOrder asc ";

        List<ResApp> apps = resAppDao.getCurrSession().createQuery(hql)
                .setParameterList("ids", getPriOwnerIDs(userId))
                .list();

        //如果角色和用户同时拥有相同子系统权限，需要去重
        Map<String, ResAppDTO> appSet = new HashMap<>();

        if (apps != null) {//如果拥有权限
            for (ResApp app : apps) {
                ResAppDTO appd = dozerMapper.map(app, ResAppDTO.class);
                appSet.put(appd.getId(), appd);
            }
        }

//      如果没有权限，返回的就是长度为 0 的集合
        return sortResList(appSet);
    }

    public Collection sortResList(Map appSet) {
        Collection<ResAppDTO> coll = appSet.values();
        ArrayList list = new ArrayList(coll);
        Collections.sort(list);
        return list;
    }


    /**
     * 获取属主ID 集合，由 userID 和其关联的 roleID 构成
     *
     * @param userId 用户 ID
     * @return ID 集合
     */
    private Set<String> getPriOwnerIDs(String userId) {
        //id 使用的都是 uuid, 不可能重复
        Set<String> pri_owner_ids = new HashSet<>();//not null

        User user = userDao.getById(userId);

        if (user == null) {
            throw new RuntimeException("User doesn't exist!");
        }

        pri_owner_ids.add(userId);//size > 0

        Set<Role> roles = user.getRoles();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                pri_owner_ids.add(role.getId());
            }
        }
        return pri_owner_ids;
    }


    @Override
    public Collection<ResMenuDTO> getMenus(String userId, String appId) {

        String hql = "select m from Priviledge p, ResMenu m where p.priResId=m.id and " +
                "m.resApp.id=:appId and " +
                "p.priOwnerId in (:ids) " +
                "order by menuOrder ";

        Session session = resMenuDao.getCurrSession();
        List<ResMenu> menus = session.createQuery(hql).setParameter("appId", appId)
                .setParameterList("ids", getPriOwnerIDs(userId))
                .list();

        //如果角色和用户同时拥有相同菜单权限，需要去重
        Map<String, ResMenuDTO> dmenus = new HashMap<>();
        if (menus != null) {//如果拥有权限
            for (ResMenu o : menus) {
                ResMenuDTO d = dozerMapper.map(o, ResMenuDTO.class);
                dmenus.put(d.getId(), d);
            }
        }
        return sortResList(dmenus);
    }

    @Override
    public Collection<ResBtnDTO> getButtons(String userId, String menuId) {

        String hql = "select b from Priviledge p, ResBtn b where p.priResId=b.id and " +
                "b.resMenu.id=:menuId and " +
                "p.priOwnerId in (:ids) " +
                "order by btnOrder ";

        List<ResBtn> buttons = resBtnDao.getCurrSession().createQuery(hql).setParameter("menuId", menuId).setParameterList("ids", getPriOwnerIDs(userId)).list();

        //如果角色和用户同时拥有相同的按钮权限，需要去重
        Map<String, ResBtnDTO> dbtns = new HashMap<>();
        if (buttons != null) {//如果拥有权限
            for (ResBtn resBtn : buttons) {
                ResBtnDTO d = dozerMapper.map(resBtn, ResBtnDTO.class);
                dbtns.put(d.getId(), d);
            }
        }
        return sortResList(dbtns);
    }

    @Override
    public boolean checkUser(String userName, String pwd) {

        DetachedCriteria dc = DetachedCriteria.forClass(User.class);
        dc.add(Property.forName("userName").eq(userName))
                .add(Property.forName("password").eq(pwd));
        List list = dc.getExecutableCriteria(userDao.getCurrSession()).list();

        return (list != null && list.size() > 0) ? true : false;
    }

    @Override
    public PriviledgeDTO authorize(String priOwnerId, String priOwnerType, String priResId, String priResType,String url) {
        Priviledge pri = new Priviledge();
        pri.setPriOwnerId(priOwnerId);
        pri.setPriOwnerType(priOwnerType);
        pri.setPriResId(priResId);
        pri.setPriResType(priResType);
        pri.setUrl(url);

        priviledgeDao.save(pri);
        return dozerMapper.map(pri, PriviledgeDTO.class);
    }

    @Override
    public boolean checkExist(String id, String name) {
        return false;//默认实现，没意义
    }

    @Override
    public void delByID(String id) {
//      权限没有外界关联，可以直接删除
        priviledgeDao.delete(id);
    }

    @Override
    public void update(PriviledgeDTO dto) {
        ValHelper.notNullOrEmpty(dto.getId());
        Priviledge pri = dozerMapper.map(dto, Priviledge.class);
        priviledgeDao.update(pri);
    }

    @Override
    public PriviledgeDTO save(PriviledgeDTO dto) {
//      确切地说，这里是添加权限，添加前无需删除任何权限
        checkPriviledge(dto);

        Priviledge pri = dozerMapper.map(dto, Priviledge.class);
        priviledgeDao.save(pri);
        return dozerMapper.map(pri, PriviledgeDTO.class);
    }

    /**
     * 检查权限是否合法
     *
     * @param dto
     */
    private void checkPriviledge(PriviledgeDTO dto) {
        if (null == dto.getPriOwnerType() || "".equals(dto.getPriOwnerType())) {
            throw new RuntimeException("主体类型不能为空");
        }
        if (null == dto.getPriOwnerId() || "".equals(dto.getPriOwnerId())) {
            throw new RuntimeException("主体ID不能为空");
        }
        if (null == dto.getPriResId() || "".equals(dto.getPriResId())) {
            throw new RuntimeException("资源ID不能为空");
        }
        if (null == dto.getPriResType() || "".equals(dto.getPriResType())) {
            throw new RuntimeException("资源类型不能为空");
        }
    }

    @Override//TODO 需要将批处理封装到 DAO 中，同时分批执行提高效率
    public void updateBatch(String roleId, String appId, List<PriviledgeDTO> pris) {

        ValHelper.notNullOrEmpty(new Object[]{roleId, appId}, "roleId", "appId");

        //1. 删除角色在子系统下的原有权限
        for (Priviledge pri : getResPris(roleId, appId)) {
            priviledgeDao.delete(pri);
        }

        //2. 添加新的权限
        if (pris != null) {
            for (PriviledgeDTO dto : pris) {
                Priviledge pri = dozerMapper.map(dto, Priviledge.class);
                priviledgeDao.save(pri);
            }
        }
    }

    @Override
    public List<PriviledgeDTO> getResPriDTOs(String roleId, String appId) {
        List<Priviledge> priList = getResPris(roleId, appId);
        List<PriviledgeDTO> dList = new ArrayList<>(priList.size());

        for (Priviledge pri : priList) {
            dList.add(dozerMapper.map(pri, PriviledgeDTO.class));
        }
        return dList;
    }

    /**
     * 获取角色在某个子系统下的权限
     *
     * @param roleId 角色ID
     * @param appId  子系统ID
     * @return 权限列表
     */
    protected List<Priviledge> getResPris(String roleId, String appId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Priviledge.class);

        criteria
                .add(Property.forName("priOwnerType").eq(TypeConstant.ROLE))
                .add(Property.forName("priOwnerId").eq(roleId))
                .add(Property.forName("priResId").in(getResIDs(appId)));

        return criteria.getExecutableCriteria(userDao.getCurrSession()).list();
    }

    /**
     * 获取子系统资源ID列表：子系统ID + 菜单ID + 按钮ID
     *
     * @param appId 子系统ID
     * @return 资源ID 列表
     */
    private List<String> getResIDs(String appId) {
        List<String> priResIDs = new ArrayList<>();
        priResIDs.add(appId);
        for (ResMenu menu : resAppDao.getById(appId).getResMenus()) {
            priResIDs.add(menu.getId());
            for (ResBtn btn : menu.getResBtns()) {
                priResIDs.add(btn.getId());
            }
        }
        return priResIDs;
    }


    @Override
    public boolean validatePriviledge(String userId, String url) {
        String sql = "select url from base_priviledge where priOwnerId in" +
                " (select roleFK from base_user_role where userFK='" + userId + "')";

        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sql).list().contains(url);
    }
}
