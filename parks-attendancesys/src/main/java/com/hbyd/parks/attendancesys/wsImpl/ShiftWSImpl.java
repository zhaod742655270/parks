package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.enums.ShiftType;
import com.hbyd.parks.dao.attendancesys.IntervalDao;
import com.hbyd.parks.dao.attendancesys.ShiftAssignDao;
import com.hbyd.parks.dao.attendancesys.ShiftBindingDao;
import com.hbyd.parks.dao.attendancesys.ShiftDao;
import com.hbyd.parks.domain.attendancesys.Interval;
import com.hbyd.parks.domain.attendancesys.Shift;
import com.hbyd.parks.dto.attendancesys.ShiftDTO;
import com.hbyd.parks.ws.attendancesys.ShiftWS;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static com.hbyd.parks.common.util.ValHelper.*;
import static org.hibernate.criterion.DetachedCriteria.forClass;
import static org.hibernate.criterion.Restrictions.eq;

/**
 * 班次服务
 */
public class ShiftWSImpl extends BaseWSImpl<ShiftDTO, Shift> implements ShiftWS {
    @Resource
    private ShiftDao shiftDao;

    @Resource
    private ShiftAssignDao shiftAssignDao;

    @Resource
    private ShiftBindingDao shiftBindingDao;

    @Resource
    private IntervalDao intervalDao;

    @Override
    public boolean checkExist(String id, String name) {
        Shift found = shiftDao.getByName(name);

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

    /*
        禁止删除班次：
        1. 班次在规律班次中引用
        2. 班次在排班结果中引用
        除此之外，均可删除
     */
    @Override
    public void delByID(String id) {
        notNullOrEmpty(id);

//      先查询 shift_binding 再查询 shift_assign 效率更高，因为前者
//      - 查询语句更简单
//      - 数据量比较小
//      - 班次绑定使用的班次，排班表也很有可能使用了
        if (shiftBindingDao.usedWithinShiftBinding(id) || shiftAssignDao.usedWithinShiftAssign(id)) {
//            throw new UnsupportedOperationException();
            throw new RuntimeException("班次已被使用，不能删除!");
        } else {
            List<Interval> intervals = intervalDao.findIntervals(id);
            for (Interval interval : intervals) {
                intervalDao.delete(interval);
            }

            shiftDao.delete(id);
        }
    }

    /**
     * 更新班次
     *
     * @param src DTO 实体，如果只更新自身信息，将关联的时段集合设置为 null 即可
     */
    @Override
    public void update(ShiftDTO src) {
        notNull(src, "更新的目标实体不能为NULL");

        String shiftID = src.getId();
        notNullOrEmpty(shiftID);//更新目标必须有ID

//        1. 从数据库获取要更新的实体
        Shift dest = shiftDao.getById(shiftID);
        notNull(dest, "更新的目标实体不存在");

//        2. 填充更新的信息
        dozerMapper.map(src, dest);//非空拷贝

//        3. 执行更新操作
        shiftDao.update(dest);//shift 没有关联，只更新自身
    }

    /**
     * 保存班次
     *
     * @param src DTO 实体，如果只保存自身信息，将关联的时段集合设置为 null 即可
     * @return 班次传输实体
     */
    @Override
    public ShiftDTO save(ShiftDTO src) {
        notNull(src, "保存的目标实体不能为NULL");
        idNotExist(src.getId());

//        1. 转换为要保存的目标实体
        Shift shift = dozerMapper.map(src, Shift.class);//非空拷贝

//        2. 执行保存
        Shift saved = shiftDao.save(shift);//shift 没有关联，只保存自身

//        3. 返回保存结果
        return dozerMapper.map(saved, ShiftDTO.class);
    }

    @Override
    public ShiftDTO getByName(String name) {
        Shift byName = shiftDao.getByName(name);
        if (byName == null) {
            return null;
        } else {
            return dozerMapper.map(byName, ShiftDTO.class);
        }
    }

    @Override
    public List<ShiftDTO> findValidShift() {
        String sql = "SELECT distinct s.* from attend_shift s INNER JOIN attend_interval i on s.id = i.shiftFK\n" +
                "UNION \n" +
                "SELECT * from attend_shift where shiftType = 'REST'";

        SQLQuery sqlQuery = shiftDao.getCurrSession().createSQLQuery(sql).addEntity(Shift.class);

        return getDTOList(sqlQuery.list());
    }

    @Override
    public List<ShiftDTO> findValidNormal() {
        String sql = "SELECT distinct s.* from attend_shift s INNER JOIN attend_interval i on s.id = i.shiftFK\n";

        SQLQuery sqlQuery = shiftDao.getCurrSession().createSQLQuery(sql).addEntity(Shift.class);

        return getDTOList(sqlQuery.list());
    }

    @Override
    public List<ShiftDTO> findAllNormal() {
        DetachedCriteria criteria = forClass(Shift.class).add(eq("shiftType", ShiftType.NORMAL));
        List<Shift> eList = shiftDao.findListByCriteria(criteria);
        return getDTOList(eList);
    }
}
