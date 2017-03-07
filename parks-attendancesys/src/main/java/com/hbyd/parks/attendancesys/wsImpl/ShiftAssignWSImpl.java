package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.constant.Unit;
import com.hbyd.parks.common.util.DateUtil;
import com.hbyd.parks.dao.attendancesys.RegularShiftDao;
import com.hbyd.parks.dao.attendancesys.ShiftAssignDao;
import com.hbyd.parks.dao.attendancesys.ShiftBindingDao;
import com.hbyd.parks.dao.attendancesys.ShiftDao;
import com.hbyd.parks.dao.supportsys.EmployeeDao;
import com.hbyd.parks.domain.attendancesys.Shift;
import com.hbyd.parks.domain.attendancesys.ShiftAssign;
import com.hbyd.parks.domain.attendancesys.ShiftBinding;
import com.hbyd.parks.domain.supportsys.Employee;
import com.hbyd.parks.dto.attendancesys.ShiftAssignDTO;
import com.hbyd.parks.dto.attendancesys.ShiftDTO;
import com.hbyd.parks.ws.attendancesys.ShiftAssignWS;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排班服务实现
 */
public class ShiftAssignWSImpl extends BaseWSImpl<ShiftAssignDTO, ShiftAssign> implements ShiftAssignWS {

    @Resource
    private ShiftDao shiftDao;

    @Resource
    private ShiftBindingDao shiftBindingDao;

    @Resource
    private EmployeeDao employeeDao;

    @Resource
    private RegularShiftDao regularShiftDao;

    @Resource
    private ShiftAssignDao shiftAssignDao;

//  更新某人某天的班次

/*
下面两个方法是针对之前的 attend_shift_assign 编写的排班方法
    if OBJECT_ID('attend_shift_assign', 'U') is not NULL
    drop table attend_shift_assign

    create table attend_shift_assign(
        id varchar(50) primary key,
        employeeFK varchar(50) not null,
        shiftFK varchar(50) not null,
        day datetime not null
    );
存在如下问题，而重新设计：
    1. 如果部门下的人员太多，这个操作可能很耗时，因为插入的是同一张表，因此，即使采用多线程，速度提升也不大。
    2. 每人每天都要对应一条排班记录，如果安排一个月，排班记录数仅一个部门而言就很多
*/

/*
    @Override
    public void assignShiftForDept(Date begin, Date end, String deptId, String regularShiftId) {
        List<Employee> emps = employeeDao.getEmpsWithinDept(deptId);

        for (Employee emp : emps) {
            assignShiftForEmp(begin, end, emp.getId(), regularShiftId);
        }
    }

    @Override
    public void assignShiftForEmp(Date begin, Date end, String empId, String regularShiftId) {
//      获取规律班次的班次绑定信息: select * from attend_shift_binding ORDER BY idx, dayToBound;
        List<ShiftBinding> sbs = shiftBindingDao.getShiftBindings(regularShiftId);
        Employee emp = employeeDao.getById(empId);
        RegularShift regularShift = regularShiftDao.getById(regularShiftId);

        Unit unit = regularShift.getUnit();//排班的单位，如：日、周
        int daysBetween = DateUtil.getDaysBetween(begin, end);

        if(Unit.日.equals(unit)){//按日排班：依次排开即可
            for (int i = 0; i < daysBetween + 1; i++) {//首尾都包含，所以需要 + 1
                ShiftAssign sa = new ShiftAssign();
                sa.setEmployee(emp);
    //          使用规律班次中的班次绑定循环填充指定时段
    //          daysBetween: 31 (index: 0 - 30)
    //          sbs.size(): 7 (index: 0 - 6)
    //          循环规律如下：
    //          0 1 2 3 4 5 6 0 1 2 3 4 5 6 0 1 2 3 4 5 6 0 1 2 3 4 5 6 0 1 2
                sa.setDay(new DateTime(begin).plusDays(i).toDate());
                sa.setShift(sbs.get(i % sbs.size()).getShift());
                shiftAssignDao.save(sa);
            }
        }else {//按周排班
            int dayOfWeek = new DateTime(begin).getDayOfWeek();//Monday-Sunday : 1-7
            int beginIdx = dayOfWeek - 1;//规律班次绑定的起始索引

            for (int i = 0; i < daysBetween + 1; i++) {
                ShiftAssign sa = new ShiftAssign();
                sa.setEmployee(emp);
                sa.setDay(new DateTime(begin).plusDays(i).toDate());
                sa.setShift(sbs.get((i + beginIdx) % sbs.size()).getShift());
                shiftAssignDao.save(sa);
            }
        }
    }
*/

//    @Override//已测
//    public List<ShiftAssignDTO> getShiftAssignResult(int beginYear, int beginMonth, int endYear, int endMonth, String id, boolean idType) {
//        if((beginYear * 12 + beginMonth) > (endYear * 12 + endMonth)){//折算为月，方便比较早晚
//            throw new RuntimeException("查询的起始日期不能晚于结束日期");
//        }
//
//        int yearGap = endYear - beginYear;
//        DetachedCriteria criteria = DetachedCriteria.forClass(ShiftAssign.class);
//
//        if(idType){//查询部门的排班
//            criteria.createAlias("employee.department", "dept")
//                    .add(eq("dept.id", id));
//        }else{//查询个人的排班
//            criteria.add(eq("employee.id", id));
//        }
//
//        if(yearGap == 0){//同年
//            criteria.add(eq("year", beginYear))
//                    .add(ge("month", beginMonth))
//                    .add(le("month", endMonth));
//
//        } else {//不同年
//            Junction qHead = conjunction()
//                    .add(eq("year", beginYear))
//                    .add(ge("month", beginMonth))
//                    .add(le("month", 12));
//
//            Junction qTail = conjunction()
//                    .add(eq("year", endYear))
//                    .add(ge("month", 1))
//                    .add(le("month", endMonth));
//
//            if(yearGap == 1){//跨一年
//                criteria.add(or(qHead, qTail));
//            } else if(yearGap > 1){//跨多年
//                LogicalExpression qMiddle = and(
//                        ge("year", beginYear + 1),
//                        le("year", endYear - 1)
//                );
//                criteria.add(disjunction()
//                        .add(qHead)
//                        .add(qMiddle)
//                        .add(qTail));
//            }
//        }
//        List eList = baseDao.findListByCriteria(criteria);
//        return getDTOList(eList);
//    }

    @Override
    public void assignShiftForDept(Date begin, Date end, String deptId, String regularShiftId) {
        List<Employee> emps = employeeDao.getEmpsWithinDept(deptId);
        for (Employee emp : emps) {
            assignShiftForEmp(begin, end, emp.getId(), regularShiftId);
        }
    }

    @Override
    public void assignShiftForEmps(Date begin, Date end, String[] empIds, String regularShiftId){
        if(empIds!=null && empIds.length>0){
            for (String empId : empIds) {
                assignShiftForEmp(begin, end, empId, regularShiftId);
            }
        }
    }

    //  JodaTime 中的继承关系：
//      AbstractDateTime
//          |--BaseDateTime
//                |--DateTime
//      AbstractDateTime 重写了 hashCode() 和 equals() 方法，因此 JodaTime 的 DateTime 是可以作为 HashMap 中的 key 的
    @Override
    public void assignShiftForEmp(Date begin, Date end, String empId, String regularShiftId) {
        if(DateUtil.dateTimeCompare(begin, end) > 0){
            throw new RuntimeException("起始日期不能晚于结束日期");
        }

        DateTime beginDate = new DateTime(begin);

        Employee emp = employeeDao.getById(empId);
        List<ShiftBinding> shiftBindings = shiftBindingDao.getShiftBindings(regularShiftId);//order by idx, dayToBound
        Unit unit = regularShiftDao.getById(regularShiftId).getUnit();

        int preMonth = beginDate.getMonthOfYear();
        int preYear = beginDate.getYear();//getYearOfCentury() 返回的只是所在世纪的年数，只有后两位，如：2014 返回的是 14

        Map<Integer, Shift> storeMap = new HashMap<>(31);//存储每个月内 day 和 shiftFK 的映射

//      按日排班：时间段和规律班次没有对应关系，将规律班次依次排开即可，规律班次的起始索引为 0
//          假定选定的时段为 2014-10-1 到 2014-10-31， 那么规律班次依次排开即可。
//      按周排班：在按日排班的基础上，规律班次的起始位置发生了偏移（规律班次的起始索引不为 0），然后才能依次排开
//          假定选定的时段为 2014-10-1 到 2014-10-31， 因为 2014-10-1 是星期三，在规律班次中的索引为 2，因此规律班次的起始偏移为 2
        int offset = 0;

        if(Unit.周.equals(unit)){
            offset = beginDate.getDayOfWeek() - 1;//1-7 的对应索引为 0-6
        }


//      因为班次要依次排开，所以只能按照 daysBetween 顺序迭代
//      时段跨多月
        for (int i = 0; i < DateUtil.getDaysBetween(begin, end) + 1; i++) {
            DateTime currDate = beginDate.plusDays(i);
            int currYear = currDate.getYear();
            int currMonth = currDate.getMonthOfYear();
            int currDay = currDate.getDayOfMonth();

            //存储上个月的排班记录
            if(currYear == preYear && currMonth > preMonth){//同年下月
                saveOrUpdateShiftAssign(emp, currYear, preMonth, storeMap);
                storeMap.clear();//清空，这样才能存储下个月的映射
                preMonth = currMonth;
            }else if (currYear > preYear){//次年下月
                saveOrUpdateShiftAssign(emp, preYear, preMonth, storeMap);
                storeMap.clear();
                preMonth = currMonth;
                preYear = currYear;
            }

            Shift shift = shiftBindings.get((i + offset) % shiftBindings.size()).getShift();
            storeMap.put(currDay, shift);
        }

//      处理未满的月，包括两种情形：
//      1. 仅指定一个月，且"映射数 <= 该月最大天数"
//      2. 指定多个月，但最后一月"映射数 <= 该月最大天数"
        if(storeMap.size() > 0){
            //存储最后一个月的排班记录
            saveOrUpdateShiftAssign(emp, preYear, preMonth, storeMap);
        }
    }

    /**存储某月对应的排班记录
     * @param emp 人员
     * @param year 年
     * @param month 月
     * @param storeMap 月内每天和班次的映射，即：某天分配了什么班次
     */
    private void saveOrUpdateShiftAssign(Employee emp, int year, int month, Map<Integer, Shift> storeMap) {
        String yearAndMonth = DateUtil.getYearAndMonth(year, month);

        ShiftAssign sa = shiftAssignDao.getShiftAssign(yearAndMonth, emp.getId());
        if(sa != null){//当月已有排班记录
            emptyDayShift(sa);
        }else{//当月尚无排班记录
            sa = new ShiftAssign();
            sa.setEmployee(emp);
            sa.setYearAndMonth(yearAndMonth);
        }

//      添加每天的班次
        for (Map.Entry<Integer, Shift> entry : storeMap.entrySet()) {
            Integer day = entry.getKey();
            Shift shift = entry.getValue();

            updateDayShift(sa, day, shift);
        }

//      保存排班记录
        shiftAssignDao.saveOrUpdate(sa);
    }

    /**更新某天的班次，如果要删除当天班次，shiftId 传 Null 即可
     * @param sa 排班记录
     * @param day 天
     * @param shift 班次
     */
    private void updateDayShift(ShiftAssign sa, int day, Shift shift) {
        String mutatorName_1 = "setS" + day;
        String mutatorName_2 = "setsName" + day;

        Class<? extends ShiftAssign> clazz = sa.getClass();
//        设置班次 ID
        try {
            Method mutator_1 = clazz.getMethod(mutatorName_1, String.class);
            if(shift == null){
                mutator_1.invoke(sa, new Object[]{null});//删除班次ID
            }else{
                mutator_1.invoke(sa, shift.getId());
            }
        } catch (Exception e) {
            logger.error("反射执行异常，方法名称：" + mutatorName_1, e);
            throw new RuntimeException(e);
        }

//        设置班次名称
        try {
            Method mutator_2 = clazz.getMethod(mutatorName_2, String.class);
            if(shift == null){
                mutator_2.invoke(sa, new Object[]{null});//删除班次名称
            }else{
                mutator_2.invoke(sa, shift.getName());
            }
        } catch (Exception e) {
            logger.error("反射执行异常，方法名称：" + mutatorName_2, e);
            throw new RuntimeException(e);
        }
    }

    /**清空每天的班次
     * @param sa 某月排班记录
     */
    private void emptyDayShift(ShiftAssign sa){
        for (int i = 1; i <= 31; i++) {
            String setterName = "setS" + i;
            try {
                Method setter = sa.getClass().getMethod(setterName, String.class);
//                setter.invoke(sa, null);//这样不对，可变参数数组在迭代时会 NullPointerException
                setter.invoke(sa, new Object[]{null});
            } catch (Exception e) {
                logger.error("反射执行异常, setterName: " + setterName, e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateDayShiftForEmp(String shiftAssignId, int day, String shiftId) {
        ShiftAssign sa = shiftAssignDao.getById(shiftAssignId);
        Shift shift = shiftDao.getById(shiftId);

        if(sa == null){
            throw new RuntimeException("排班记录不存在!");
        }

        updateDayShift(sa, day, shift);
        shiftAssignDao.update(sa);
    }

    @Override
    public ShiftDTO getDayShift(Date date, String empId){
        String shiftId = shiftAssignDao.getShift(empId, date);

        if(shiftId == null){
            return null;
        }else{
            Shift shift = shiftDao.getById(shiftId);
            if(shift == null){
                throw new RuntimeException("排班表引用的班次ID 无效，可能存在脏数据，shiftId: " + shiftId);
            }
            return dozerMapper.map(shift, ShiftDTO.class);
        }
    }
}
