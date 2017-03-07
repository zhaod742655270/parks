package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.attendancesys.LeaveTypeDao;
import com.hbyd.parks.domain.attendancesys.LeaveType;
import com.hbyd.parks.dto.attendancesys.LeaveTypeDTO;
import com.hbyd.parks.ws.attendancesys.LeaveTypeWS;

import javax.annotation.Resource;

/**
 * 请假类型服务，没有任何关联，只是 crud 操作
 */
public class LeaveTypeWSImpl extends BaseWSImpl<LeaveTypeDTO, LeaveType> implements LeaveTypeWS {

    @Resource
    private LeaveTypeDao leaveTypeDao;

    @Override
    public void delByID(String id) {
        leaveTypeDao.delete(id);
    }

    @Override
    public void update(LeaveTypeDTO dto) {
        String id = dto.getId();

//        0. 更新的目标实体必须有 ID
        ValHelper.notNull(id);

//        1. 从数据库获取要更新的实体
        LeaveType leaveType = leaveTypeDao.getById(id);
        ValHelper.notNull(leaveType, "更新的目标实体不存在");

//        2. 填充更新的信息
        dozerMapper.map(dto, leaveType);

//        3. 执行更新操作
        leaveTypeDao.update(leaveType);
    }

    @Override
    public LeaveTypeDTO save(LeaveTypeDTO dto) {
        ValHelper.idNotExist(dto.getId());

//        1. 转换为要保存的目标实体
        LeaveType holiday = dozerMapper.map(dto, LeaveType.class);

//        2. 执行保存
        LeaveType saved = leaveTypeDao.save(holiday);

//        3. 返回保存结果
        return dozerMapper.map(saved, LeaveTypeDTO.class);
    }

    @Override
    public boolean checkExist(String id, String name) {
        LeaveType found = leaveTypeDao.getByName(name);

        if(found == null){//没有同名的
            return false;
        }else{
            if(id.equals(found.getId())){//使用原有名称
                return false;
            }else{
                return true;
            }
        }
    }
}
