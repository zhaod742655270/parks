package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.DateUtil;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.attendancesys.HolidayDao;
import com.hbyd.parks.domain.attendancesys.Holiday;
import com.hbyd.parks.dto.attendancesys.HolidayDTO;
import com.hbyd.parks.ws.attendancesys.HolidayWS;
import org.joda.time.DateTime;

import javax.annotation.Resource;


/**
 * 节假日服务实现，没有关联，很简单的 crud
 * 所有方法的参数都经过了"切面参数 NOT NULL 验证"
 */
public class HolidayWSImpl extends BaseWSImpl<HolidayDTO, Holiday> implements HolidayWS {

    @Resource
    private HolidayDao holidayDao;

    @Override
    public void delByID(String id) {
        holidayDao.delete(id);//底层实现：load(id), 如果记录不存在，报错，这里无需抛异常
    }

    @Override
    public void update(HolidayDTO dto) {
        String id = dto.getId();

//        0. 更新的目标实体必须有 ID
        ValHelper.notNull(id);

//        1. 从数据库获取要更新的实体
        Holiday holiday = holidayDao.getById(id);
        ValHelper.notNull(holiday, "更新的目标实体不存在");

//        2. 填充更新的信息
        dozerMapper.map(dto, holiday);

//        3. 修正起止日期边界
        reviseHolidayBoundary(holiday);

//        4. 执行更新操作
        holidayDao.update(holiday);
    }

    @Override
    public HolidayDTO save(HolidayDTO dto) {
        ValHelper.idNotExist(dto.getId());

//        1. 转换为要保存的目标实体
        Holiday holiday = dozerMapper.map(dto, Holiday.class);

//        2. 执行保存
        Holiday saved = holidayDao.save(holiday);

//        3. 修正起止日期边界
        reviseHolidayBoundary(saved);

//        3. 返回保存结果
        return dozerMapper.map(saved, HolidayDTO.class);
    }

    /**<pre>
     * 修正请假起止日期边界
     * 操作界面只有日期，因此如果前台传来的是：
     * 2014-11-01  到  2014-11-02
     * 数据库实际存储为：
     * 2014-11-01 00:00:00 到  2014-11-02 00:00:00 跨度只有 24 小时
     * 而实际生活中，2014-11-01  到  2014-11-02 意味着休息两天，即：48 小时
     * 那么需要在存储前将日期边界修正为：
     * 2014-11-01 00:00:00 到  2014-11-02 23:59:59
     *</pre>
     * @param holiday
     */
    private void reviseHolidayBoundary(Holiday holiday) {
        DateTime newStartDate = DateUtil.resetTime(new DateTime(holiday.getStartDate()), 0, 0, 0);
        DateTime newEndDate = DateUtil.resetTime(new DateTime(holiday.getEndDate()), 23, 59, 59);

        holiday.setStartDate(newStartDate.toDate());
        holiday.setEndDate(newEndDate.toDate());
    }

    @Override
    public HolidayDTO getByName(String name) {
        Holiday byName = holidayDao.getByName(name);
        if (byName == null) {
            return null;
        } else {
            return dozerMapper.map(byName, HolidayDTO.class);
        }
    }

    @Override
    public boolean checkExist(String id, String name) {
        Holiday found = holidayDao.getByName(name);

        if (found == null) {//没有同名的
            return false;
        } else {
            if (id.equals(found.getId())) {//使用原有名称
                return false;
            } else {
                return true;
            }
        }
    }
}
