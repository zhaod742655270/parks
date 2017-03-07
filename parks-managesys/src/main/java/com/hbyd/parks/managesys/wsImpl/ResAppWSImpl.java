package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.managesys.ResAppDao;
import com.hbyd.parks.dao.managesys.ResBtnDao;
import com.hbyd.parks.dao.managesys.ResMenuDao;
import com.hbyd.parks.domain.managesys.ResApp;
import com.hbyd.parks.domain.managesys.ResBtn;
import com.hbyd.parks.domain.managesys.ResMenu;
import com.hbyd.parks.dto.managesys.ResAppDTO;
import com.hbyd.parks.ws.managesys.ResAppWS;

import javax.annotation.Resource;

/**
 * 子系统 DAO 实现
 */
public class ResAppWSImpl extends BaseWSImpl<ResAppDTO, ResApp> implements ResAppWS {
    @Resource
    private ResAppDao resAppDao;

    @Resource
    private ResMenuDao resMenuDao;

    @Resource
    private ResBtnDao resBtnDao;

    @Override
    public boolean checkExist(String id, String name) {
        ResApp app = resAppDao.getByName(name);

        if (app != null && !id.equals(app.getId())) {//如果已经存在且修改的不是当前实体
            return true;
        }
        return false;
    }

    /**
     * 删除子系统，级联删除其下的菜单以及菜单的按钮
     *
     * @param id 子系统 ID
     */
    @Override
    public void delByID(String id) {
        ResApp app = resAppDao.getById(id);
        for (ResMenu menu : app.getResMenus()) {
            for (ResBtn btn : menu.getResBtns()) {
                resBtnDao.delete(btn.getId());//删除按钮
            }
            resMenuDao.delete(menu.getId());//删除菜单
        }
        resAppDao.delete(app.getId());//删除子系统
    }

    @Override
    public void update(ResAppDTO dto) {
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");

        ResApp target = resAppDao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");

//      更新只涉及普通属性
        dto.setResMenus(null);

        dozerMapper.map(dto, target);
        resAppDao.update(target);
    }

    @Override
    public ResAppDTO save(ResAppDTO dto) {
        ValHelper.idNotExist(dto.getId());

//      保存只涉及普通属性
        dto.setResMenus(null);

        ResApp target = dozerMapper.map(dto, ResApp.class);
        resAppDao.save(target);
        return dozerMapper.map(target, ResAppDTO.class);
    }

    @Override
    public ResAppDTO getByName(String name) {
        ResApp target = resAppDao.getByName(name);
        if (target == null) {
            return null;
        } else {
            return dozerMapper.map(target, ResAppDTO.class);
        }
    }
}
