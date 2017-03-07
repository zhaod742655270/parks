package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.managesys.ResBtnDao;
import com.hbyd.parks.dao.managesys.ResMenuDao;
import com.hbyd.parks.domain.managesys.ResBtn;
import com.hbyd.parks.domain.managesys.ResMenu;
import com.hbyd.parks.dto.managesys.ResBtnDTO;
import com.hbyd.parks.ws.managesys.ResBtnWS;

import javax.annotation.Resource;

/**
 * 按钮 DAO 实现
 */
public class ResBtnWSImpl extends BaseWSImpl<ResBtnDTO, ResBtn> implements ResBtnWS {
    @Resource
    private ResMenuDao resMenuDao;

    @Resource
    private ResBtnDao resBtnDao;

    @Override
    public boolean checkExist(String id, String name) {
        return false;//可以重名
    }

//  按钮直接删除即可
    @Override
    public void delByID(String id) {
        resBtnDao.delete(id);
    }

    @Override
    public void update(ResBtnDTO dto) {
        ValHelper.notNull(dto.getId(), "更新操作的 ID 不能为 NULL!");

        ResBtn target = resBtnDao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");

//      提取关联属性：所属菜单
        String menuId = dto.getMenuId();

//      置空无关属性：防止拷贝
        dto.setMenuName(null);
        dto.setMenuId(null);

//      更新关联属性：所属菜单
        ResMenu menu = resMenuDao.getById(menuId);
        ValHelper.notNull(menu, "按钮未指定所属菜单!");
        target.setResMenu(menu);

//      更新普通属性
        dozerMapper.map(dto, target);

        resBtnDao.update(target);
    }

    @Override
    public ResBtnDTO save(ResBtnDTO dto) {
        ValHelper.idNotExist(dto.getId());

        ResBtn target = new ResBtn();

//      提取关联属性：所属菜单
        String menuId = dto.getMenuId();

//      置空无关属性：防止拷贝
        dto.setMenuName(null);
        dto.setMenuId(null);

//      填充关联属性：所属菜单
        ResMenu menu = resMenuDao.getById(menuId);
        ValHelper.notNull(menu, "按钮未指定所属菜单!");
        target.setResMenu(menu);

//      填充普通属性
        dozerMapper.map(dto, target);

        ResBtn saved = resBtnDao.save(target);
        return dozerMapper.map(saved, ResBtnDTO.class);
    }
}
