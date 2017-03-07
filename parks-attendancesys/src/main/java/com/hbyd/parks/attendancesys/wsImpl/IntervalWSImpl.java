package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.dao.attendancesys.IntervalDao;
import com.hbyd.parks.dao.attendancesys.ShiftDao;
import com.hbyd.parks.domain.attendancesys.Interval;
import com.hbyd.parks.domain.attendancesys.Shift;
import com.hbyd.parks.dto.attendancesys.IntervalDTO;
import com.hbyd.parks.ws.attendancesys.IntervalWS;

import javax.annotation.Resource;
import java.util.List;

import static com.hbyd.parks.common.util.ValHelper.*;

public class IntervalWSImpl extends BaseWSImpl<IntervalDTO, Interval> implements IntervalWS {

    @Resource
    private IntervalDao intervalDao;

    @Resource
    private ShiftDao shiftDao;

    @Override
    public void update(IntervalDTO intervalDTO) {
        notNull(intervalDTO, "更新的目标实体不能为NULL");
        notNullOrEmpty(intervalDTO.getId());//更新实体必须有ID

//        --------
        String shiftID = intervalDTO.getShiftID();
        notNull(shiftID, "必须指定时段所属班次");
        Shift newShift = shiftDao.getById(shiftID);
        notNull(newShift, "时段所属班次不存在");
//        --------

//        1. 从数据库获取要更新的实体
        Interval interval = intervalDao.getById(intervalDTO.getId());

//        2. 更新普通字段
        intervalDTO.setShiftID(null);//不进行 DTO.shiftID -> domain.shift.id 的映射，防止 Hibernate 认为 Shift 的主键发生变化而报异常
        dozerMapper.map(intervalDTO, interval);//非空拷贝

//        3. 更新关联字段
        interval.setShift(newShift);

//        4. 执行更新
        intervalDao.update(interval);
    }

    @Override
    public IntervalDTO save(IntervalDTO intervalDTO) {
        notNull(intervalDTO, "保存的目标实体不能为NULL");
        idNotExist(intervalDTO.getId());//保存时不应该存在 ID

//        --------
        String shiftID = intervalDTO.getShiftID();
        notNull(shiftID, "必须指定时段所属班次");
        Shift shift = shiftDao.getById(shiftID);
        notNull(shift, "时段所属班次不存在");
//        --------

//        1. 转换为要保存的实体
        intervalDTO.setShiftID(null);
        Interval dest = dozerMapper.map(intervalDTO, Interval.class);

//        2. 设置外键关联
        dest.setShift(shift);

//        3. 执行保存
        Interval saved = intervalDao.save(dest);

//        4. 返回保存结果
        return dozerMapper.map(saved, IntervalDTO.class);
    }

    @Override
    public List<IntervalDTO> findByShiftID(String shiftID) {
        List<Interval> list = intervalDao.findIntervals(shiftID);
        return getDTOList(list);
    }
}
