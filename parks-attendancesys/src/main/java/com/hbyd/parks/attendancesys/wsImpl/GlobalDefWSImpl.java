package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.enums.RestDay;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.attendancesys.GlobalDefDao;
import com.hbyd.parks.dao.attendancesys.ShiftDao;
import com.hbyd.parks.dao.supportsys.EmployeeDao;
import com.hbyd.parks.domain.attendancesys.GlobalDef;
import com.hbyd.parks.domain.attendancesys.Shift;
import com.hbyd.parks.dto.attendancesys.GlobalDefDTO;
import com.hbyd.parks.ws.attendancesys.GlobalDefWS;

import javax.annotation.Resource;
import java.util.List;

/**
 * 全局定义服务实现
 */
public class GlobalDefWSImpl extends BaseWSImpl<GlobalDefDTO, GlobalDef> implements GlobalDefWS {

    @Resource
    private GlobalDefDao defDao;

    @Resource
    private ShiftDao shiftDao;

    @Resource
    private EmployeeDao employeeDao;

    @Override
    public void delByID(String id) {
        defDao.delete(id);
    }

//    update() 触发的 SQL 语句
//    Hibernate: update t_default_definition set OPTLOCK=?, invalidAssert=?, shiftFK=? where id=? and OPTLOCK=?
//    Hibernate: delete from t_default_definition_restDays where DefaultDefinition_id=?
//    Hibernate: insert into t_default_definition_restDays (DefaultDefinition_id, restDays) values (?, ?)
//    Hibernate: insert into t_default_definition_restDays (DefaultDefinition_id, restDays) values (?, ?)
//    ...
    @Override
    public void update(GlobalDefDTO dto) {
        String id = dto.getId();
        String shiftIdNew = dto.getShiftID();

//        1. 验证参数
        ValHelper.notNull(id, "更新实体必须有 ID");
        ValHelper.notNull(shiftIdNew, "班次 ID 不能为 NULL");

//        2. 获取目标实体
        GlobalDef def = defDao.getById(id);

//        3. 获取关联属性
        Shift shiftNew = shiftDao.getById(shiftIdNew);
        ValHelper.notNull(shiftNew, "班次不存在");

//        4.1 置 NULL 关联字段
        dto.setShiftID(null);

//        4.2 更新普通字段

//        1) 防止拷贝集合属性 restDays
        List<RestDay> newRestDays = dto.getRestDays();//先取出来
        dto.setRestDays(null);//置 NULL 防止拷贝
//        2) 此时只拷贝普通属性
        dozerMapper.map(dto, def);
//        3) 手动拷贝 restDays 属性
//          如果这里不手动拷贝，dozerMapper.map(dto, def) 会叠加拷贝： def.restDays = def.restDays + dto.restDays
        def.setRestDays(newRestDays);

//        4.3 更新关联字段
        def.setShift(shiftNew);

//        5. 执行更新
        defDao.update(def);
    }

    @Override
    public GlobalDefDTO save(GlobalDefDTO dto) {

        String id = dto.getId();
        String shiftID = dto.getShiftID();

//        0. 验证参数
        ValHelper.idNotExist(id);
        ValHelper.notNull(shiftID, "班次 ID 不能为 NULL");
//        1. 获取保存目标
        GlobalDef def = dozerMapper.map(dto, GlobalDef.class);
//        2. 保存关联属性
        Shift shift = shiftDao.getById(shiftID);
        ValHelper.notNull(shift, "班次不存在");
        def.setShift(shift);
//        3. 保存
        GlobalDef saved = defDao.save(def);

        return dozerMapper.map(saved, GlobalDefDTO.class);
    }
}
