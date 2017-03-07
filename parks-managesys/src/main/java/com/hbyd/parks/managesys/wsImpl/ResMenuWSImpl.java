package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.managesys.ResAppDao;
import com.hbyd.parks.dao.managesys.ResBtnDao;
import com.hbyd.parks.dao.managesys.ResMenuDao;
import com.hbyd.parks.domain.managesys.ResApp;
import com.hbyd.parks.domain.managesys.ResBtn;
import com.hbyd.parks.domain.managesys.ResMenu;
import com.hbyd.parks.dto.managesys.ResMenuDTO;
import com.hbyd.parks.ws.managesys.ResMenuWS;

import javax.annotation.Resource;

/**
 * 菜单 DAO 实现
 */
public class ResMenuWSImpl extends BaseWSImpl<ResMenuDTO, ResMenu> implements ResMenuWS {

    @Resource
    private ResMenuDao resMenuDao;

    @Resource
    private ResBtnDao resBtnDao;

    @Resource
    private ResAppDao resAppDao;

    @Override
    public boolean checkExist(String id, String name) {
        return false;//允许重名
    }

//  菜单只有两级
    @Override
    public void delByID(String id) {
        ResMenu menu = resMenuDao.getById(id);

        if (menu.getChildMenus() != null && menu.getChildMenus().size() > 0) {//如果是父菜单
            for (ResMenu subMenu : menu.getChildMenus()) {
                for (ResBtn btn : subMenu.getResBtns()) {
//                    删除子菜单下的按钮
                    resBtnDao.delete(btn.getId());
                }
//                删除子菜单
                resMenuDao.delete(subMenu.getId());
            }
        } else {// 如果是子菜单
            for (ResBtn btn : menu.getResBtns()) {
                resBtnDao.delete(btn.getId());
            }
        }
        resMenuDao.delete(id);
    }

    @Override
    public void update(ResMenuDTO dto) {

        ValHelper.notNull(dto.getId(), "更新操作的 ID 不能为 NULL!");

        ResMenu target = resMenuDao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");

//      所属子系统
        String appID = dto.getAppId();
//      上级菜单
        String parentID = dto.getParentId();

//      菜单属性比较特殊，不使用 Dozer 进行映射，手动更新界面需要的属性即可

//      1. 父菜单: 可有可无
        if (null == parentID || "".equals(parentID.trim())) {//未指定父菜单
            target.setParent(null);
        } else {//界面需要选择父菜单，才能添加子菜单，因此父菜单一定是存在的
            ResMenu parentMenu = resMenuDao.getById(parentID);
            target.setParent(parentMenu);
        }
//      2. 子系统：必须有
//        菜单必须属于某个子系统
        if (appID == null || "".equals(appID.trim())) {
            throw new IllegalArgumentException("菜单未指定子系统!");
        }
//        界面需要先选择子系统才能操作其内的菜单，因此，子系统一定是存在的
        ResApp app = resAppDao.getById(appID);
        target.setResApp(app);

//      3. 填充普通属性
        target.setIsVisible(dto.getIsVisible());
        target.setMenuIconUrl(dto.getMenuIconUrl());
        target.setMenuName(dto.getMenuName());
        target.setMenuOrder(dto.getMenuOrder());
        target.setMenuPageUrl(dto.getMenuPageUrl());

//      执行更新
        resMenuDao.update(target);
    }

    @Override
    public ResMenuDTO save(ResMenuDTO dto) {
        ValHelper.idNotExist(dto.getId());

//      所属子系统
        String appID = dto.getAppId();
//      上级菜单
        String parentID = dto.getParentId();

//      菜单属性比较特殊，不使用 Dozer 进行映射，手动更新界面需要的属性即可

        ResMenu target = new ResMenu();

//      1. 父菜单: 可有可无
        if (null == parentID || "".equals(parentID.trim())) {//未指定父菜单
            target.setParent(null);
        } else {//界面需要选择父菜单，才能添加子菜单，因此父菜单一定是存在的
            ResMenu parentMenu = resMenuDao.getById(parentID);
            target.setParent(parentMenu);
        }
//      2. 子系统：必须有
//        菜单必须属于某个子系统
        if (appID == null || "".equals(appID.trim())) {
            throw new IllegalArgumentException("菜单未指定子系统!");
        }
//        界面需要先选择子系统才能操作其内的菜单，因此，子系统一定是存在的
        ResApp app = resAppDao.getById(appID);
        target.setResApp(app);

//      3. 填充普通属性
        target.setIsVisible(dto.getIsVisible());
        target.setMenuIconUrl(dto.getMenuIconUrl());
        target.setMenuName(dto.getMenuName());
        target.setMenuOrder(dto.getMenuOrder());
        target.setMenuPageUrl(dto.getMenuPageUrl());

//      执行保存
        ResMenu saved = resMenuDao.save(target);
        return dozerMapper.map(saved, ResMenuDTO.class);
    }

    @Override
    public ResMenuDTO getByName(String name) {
        ResMenu menu = resMenuDao.getByName(name);
        if (menu == null) {
            return null;
        } else {
            return dozerMapper.map(menu, ResMenuDTO.class);
        }
    }
}
