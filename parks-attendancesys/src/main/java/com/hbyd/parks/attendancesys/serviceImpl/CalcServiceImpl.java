package com.hbyd.parks.attendancesys.serviceImpl;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.hbyd.parks.attendancesys.service.CalcService;
import com.hbyd.parks.common.constant.Direction;
import com.hbyd.parks.common.enums.ShiftType;
import com.hbyd.parks.common.util.DateUtil;
import com.hbyd.parks.dao.attendancesys.*;
import com.hbyd.parks.dao.doorsys.AccessEventDao;
import com.hbyd.parks.dao.supportsys.CardDao;
import com.hbyd.parks.dao.supportsys.EmployeeDao;
import com.hbyd.parks.domain.attendancesys.*;
import com.hbyd.parks.domain.doorsys.AccessEvent;
import com.hbyd.parks.domain.supportsys.Card;
import com.hbyd.parks.domain.supportsys.Employee;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hbyd.parks.common.enums.ShiftType.REST;
import static com.hbyd.parks.common.util.DateUtil.*;
import static org.hibernate.criterion.DetachedCriteria.forClass;
import static org.hibernate.criterion.Property.forName;
import static org.hibernate.criterion.Restrictions.and;

/**
 * 考勤计算服务的实现
 */
@Service
public class CalcServiceImpl implements CalcService {

    /**
     * Is log4j thread-safe?
     * Yes, log4j is thread-safe. Log4j components are designed to be used in heavily multi-threaded systems.
     */
    private org.apache.log4j.Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private HolidayDao holidayDao;

    @Resource
    private LeaveRecordDao leaveRecordDao;

    @Resource
    private EmployeeDao employeeDao;

    @Resource
    private CardDao cardDao;

    @Resource
    private IntervalDao intervalDao;

    @Resource
    private AtteInfoDao atteInfoDao;

    @Resource
    private AtteDetailDao atteDetailDao;

    @Resource
    private AccessEventDao accessEventDao;

    @Resource
    private GlobalDefDao globalDefDao;

    @Resource
    private ShiftAssignDao shiftAssignDao;

    @Resource
    private ShiftDao shiftDao;

    @Override
    public boolean isHoliday(Date date) {
        DetachedCriteria criteria = forClass(Holiday.class);
        criteria.add(forName("startDate").le(date))//JPA 之比较日期
                .add(forName("endDate").ge(date));
        return holidayDao.getCnt(criteria) > 0;
    }

    //TODO 删除 Shift 后，AtteInfo 所使用的 Shift 尚未处理
    @Override
    public void recalc(String empId, Date beginDate, Date endDate){

        if(new DateTime(beginDate).isAfter(new DateTime(endDate))){
            throw new IllegalArgumentException("起始日期不应晚于结束日期!");
        }

        Employee employee = employeeDao.getById(empId);

        if(!employee.getIsInvolve()){
            logger.info("人员不参与考勤：" + empId + ", 无需重新计算考勤");
            return;//跳过不参与考勤的人员
        }

        int days = DateUtil.getDaysBetween(beginDate, endDate);

        if(days == 0){//确保如果只有一天，也能够迭代
            days = 1;
        }

        for (int i = 0; i < days; i++) {
            DateTime day = new DateTime(beginDate).plusDays(i);

//          节假日不计算考勤和旷工
            if(isThatDayRest(day)){
                continue;
            }

//          查询当天绑定的班次
            Shift shift = getShift(empId, day);

//          休息班次不计算
            if(REST.equals(shift.getShiftType())){
                continue;
            }

            ShiftResult shiftResult = getShiftResult(shift, day);

//          查询当天的考勤记录
            AtteInfo atteInfo = atteInfoDao.getDayInfo(empId, day.toDate());

            if(atteInfo == null){//当天没有考勤记录
//              新建考勤记录
                atteInfo = newAtteInfo(shiftResult, employee);
                atteInfoDao.save(atteInfo);
            }else{
                resetCalcResult(atteInfo);
            }

//          重新计算考勤
            Date sLeftBound = shiftResult.getShiftSignInBegin().toDate();//班次左边界
            Date sRightBound = shiftResult.getShiftSignOutEnd().toDate();//班次右边界

//          查询员工在班次范围内的有效打卡
            List<AccessEvent> events = accessEventDao.getEvents(empId, sLeftBound, sRightBound);

//          如果班次内存在有效打卡记录，重新计算
            if(events != null && events.size() > 0){
                for (AccessEvent event : events) {
                    event.setIsCalced(false);//先修改标志位，否则不会计算
                    calc(event);//此时计算的刷卡记录均为班次范围内的刷卡记录，直接忽略界外的打卡事件
                }
            }

//          执行至此，如果系统日期时间超过班次右边界，重新计算旷工
            if(DateTime.now().isAfter(shiftResult.getShiftSignOutEnd())){
//              由于 atteInfo 没有参与 calc(event), 不能体现最新状态, 因此需要重新查询
                atteInfo = atteInfoDao.getDayInfo(empId, day.toDate());
                calcAbsent(atteInfo);
            }
        }
    }

    /**<pre>
     * 重置考勤班次的计算结果：
     * 1. 刷卡计算结果（迟到/早退、请假）
     * 2. 旷工计算结果（旷工）
     * </pre>
     * @param atteInfo 考勤班次
     */
    private void resetCalcResult(AtteInfo atteInfo) {
        atteInfo.setArriveLateCount(0);//迟到
        atteInfo.setLeaveEarlyCount(0);//早退

        atteInfo.setLeaveCount(0);//请假
        atteInfo.setAbsentCount(0);//旷工

        List<AtteDetail> details = atteInfo.getDetails();
        if(details.size() > 0){
            for (AtteDetail detail : details) {
                detail.setLeaveNum(0);
                detail.setLeaveType(null);
                detail.setArriveLateNum(0);
                detail.setLeaveEarlyNum(0);

                detail.setAbsentNum(0);

//              置空签到和签退时间，重新计算刷卡事件时会再次填充上
                detail.setSignInTime(null);
                detail.setSignOutTime(null);

                atteDetailDao.update(detail);
            }
        }
        atteInfoDao.update(atteInfo);
    }

    @Override
    public void calcAbsent(DateTime day, Employee employee) {
        logger.info(String.format("当前计算旷工的人员：%s, 日期：%s", employee.getId(), day));
        AtteInfo atteInfo = atteInfoDao.getDayInfo(employee.getId(), day.toDate());

//      节假日不计算旷工，为节假日创建考勤记录是没有意义的
        if(isThatDayRest(day)){
            return;
        }

        if(atteInfo == null){
            atteInfo = atteInfoDao.save(newAtteInfo(day, employee));//某人某天（某班次）的考勤班次
        }
//      计算考勤班次的旷工情况
        calcAbsent(atteInfo);
    }

    /**判断某天是否休息(节假日和周末)
     * @param day 日期
     * @return 如果休息，返回 true, 否则 false
     */
    private boolean isThatDayRest(DateTime day) {
//        int i = day.dayOfWeek().get();
//        不能这么判断：有时为了凑假，会将周六日的和节假日拼接，可能导致周日需要上班，这样周末就不是休息日
//        return (i == SATURDAY) || (i == SUNDAY) || isHoliday(day.toDate());
//        因此，这里之判断是否休息日，具体是否双休，则由排班结果决定（是休息班次则休，不是就不休）
        return isHoliday(day.toDate());
    }

    /**<pre>
     * 为人员新建考勤班次(考勤记录)
     * 如果已经得到可计算的班次，推荐调用此方法，好处：无需再次查询哪天哪个班次
     *
     * 人员表
     * </pre>
     * @param shiftResult 班次（可计算的班次信息）
     * @param employee 人员
     */
    private AtteInfo newAtteInfo(ShiftResult shiftResult, Employee employee) {
        AtteInfo atteInfo = new AtteInfo();

        atteInfo.setEmpFK(employee.getId());
        atteInfo.setEmpName(employee.getEmpName());

        atteInfo.setDeptFK(employee.getDepartment().getId());
        atteInfo.setDeptName(employee.getDepartment().getDeptName());

        atteInfo.setDay(shiftResult.getDay().toDate());
        atteInfo.setShift(shiftResult.getShift());

        return atteInfo;
    }

    /**为某人某天新建考勤班次(考勤记录)
     * @param day 哪天的考勤班次，即：考勤班次所对应的班次属于哪天
     * @param employee 人员
     */
    private AtteInfo newAtteInfo(DateTime day, Employee employee){
        Shift shift = getShift(employee.getId(), day);
        ShiftResult shiftResult = getShiftResult(shift, day);
        return newAtteInfo(shiftResult, employee);
    }

    /**新建考勤时段
     * @param atteInfo 所属的考勤班次
     * @param interval 对应的时段
     * @return 考勤时段
     */
    private AtteDetail newAtteDetail(AtteInfo atteInfo, Interval interval){
        AtteDetail atteDetail = new AtteDetail();
        atteDetail.setAtteInfo(atteInfo);
        atteDetail.setInterval(interval);
        return atteDetail;
    }

    @Override
    public void calcAbsent(AtteInfo ai) {

//      第一种情形：无论考勤记录何时创建，只要是休息班次，就不计算旷工
        if(ShiftType.REST.equals(ai.getShift().getShiftType())){
            return;
        }

        ShiftResult sr = getShiftResult(ai.getShift(), new DateTime(ai.getDay()));

//      获取考勤时段对应的时段列表
        List<AtteDetail> details = ai.getDetails();
        if (details == null){
            ai.setDetails(new ArrayList<AtteDetail>());
        }
        FluentIterable<Interval> ins_exist = FluentIterable.from(ai.getDetails()).transform(new Function<AtteDetail, Interval>() {
            @Nullable
            @Override
            public Interval apply(AtteDetail input) {
                return input.getInterval();
            }
        });

//      获取班次对应的时段列表
        List<Interval> ins_all = sr.getIntervals();

//      最终参与旷工计算的考勤时段
        List<AtteDetail> ads = new ArrayList<>();

//      补充未打卡导致的考勤时段空缺
        if(ins_exist.size() < ins_all.size()){
            for (Interval i : ins_all) {
                if(!ins_exist.contains(i)){
                    AtteDetail ad = newAtteDetail(ai, i);
                    atteDetailDao.save(ad);
                    ads.add(ad);
                }
            }
        }

//      合并已有考勤时段
        ads.addAll(ai.getDetails());

        for (AtteDetail ad : ads) {//考勤时段中存储的都是日期时间
            Interval i = ad.getInterval();//时段
            IntervalResult ir = getIntervalResult(new DateTime(ai.getDay()), i);//可计算的时段信息

//          判断时段是否请假
            List<LeaveRecord> lrs = getLeaveRecords(ir.getWorkTimeBegin(), ir.getWorkTimeEnd(), ai.getEmpFK());

            if(lrs.size() > 0){//时段内请假
                int interLeaveMins = getSumLeaveMins(ir, lrs).intValue();
                ad.setLeaveNum(interLeaveMins);
                atteDetailDao.update(ad);

                ai.setLeaveCount(ai.getLeaveCount() + ad.getLeaveNum());
            }else{//时段内未请假
                int interLen = getMinsJoda(ir.getWorkTimeBegin(), ir.getWorkTimeEnd());//时段长度：分钟数

//              要求签到、签退时，如果没有签到和签退，算作旷工
                if ((ad.getSignInTime() == null && i.getNeedSignIn())||(ad.getSignOutTime() == null && i.getNeedSignOut())){
                    ad.setAbsentNum(interLen);
                    atteDetailDao.update(ad);

                    ai.setAbsentCount(ai.getAbsentCount() + ad.getAbsentNum());
                }
            }
        }

        atteInfoDao.update(ai);
    }

    /**获取某人某天的班次
     * @param empId 人员ID
     * @param day 某天
     * @return 班次
     */
    private Shift getShift(String empId, DateTime day) {
        Shift shift;
        String shiftId = shiftAssignDao.getShift(empId, day.toDate());
        if(shiftId == null){
            shift = globalDefDao.getLatest().getShift();
        }else{
            shift = shiftDao.getById(shiftId);
        }
        return shift;
    }

    @Override
    public ShiftResult matchShiftResult(String empId, DateTime eventTime) {
//      1. 刷卡当天班次
        DateTime dayCurr = eventTime;
        String shiftIdCurr = shiftAssignDao.getShift(empId, dayCurr.toDate());

//      2. 刷卡前一天班次
        DateTime dayPre = eventTime.minusDays(1);
        String shiftIdPre = shiftAssignDao.getShift(empId, dayPre.toDate());

//      全局班次
        Shift g_shift = globalDefDao.getLatest().getShift();
//      当天班次
        Shift shiftCurr = (shiftIdCurr == null) ? g_shift : shiftDao.getById(shiftIdCurr);
//      昨天班次
        Shift shiftPre = (shiftIdPre == null) ? g_shift : shiftDao.getById(shiftIdPre);

//      班次只有三种类型：NORMAL/GLOBAL/REST
        if (REST.equals(shiftCurr.getShiftType())) {//当天：休息班次
//          判断是否属于上个班次
            if (REST.equals(shiftPre.getShiftType())) {//昨天：休息班次
                return null;
            } else {//昨天：全局/正常班次
                ShiftResult srPre = getShiftResult(shiftPre, dayPre);
                if (matchShift(eventTime, srPre)) {
                    return srPre;
                } else {
                    return null;
                }
            }
        } else {//当天：全局/正常班次
            ShiftResult srCurr = getShiftResult(shiftCurr, dayCurr);

            if (matchShift(eventTime, srCurr)) {//当天匹配
                return srCurr;
            } else {//当天未匹配
//              匹配前一天
                if (REST.equals(shiftPre.getShiftType())) {
                    return null;
                } else {
                    ShiftResult srPre = getShiftResult(shiftPre, dayPre);
                    if (matchShift(eventTime, srPre)) {
                        return srPre;
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    @Override
    public void calc(AccessEvent e) {
        String threadName = Thread.currentThread().getName() + ": ";
        logger.info(threadName + "开始计算考勤...");

        if(e.getIsCalced()){//刷卡记录已算过，跳过
            return;
        }

        String ioType = e.getIoType().trim();//出入类型
        DateTime eventTime = new DateTime(e.getEventTime());//刷卡时间

        if (!Direction.IN.equals(ioType) && !Direction.OUT.equals(ioType)){//脏数据异常
            throw new RuntimeException("access direction must be 'in' or 'out', can't be " + ioType);
        }

//      刷卡当天是否放假
        if(!isHoliday(eventTime.toDate())){

//          查找卡信息
            Card card = cardDao.getByCardNo(e.getCardNo());//卡

//          如果刷卡记录的卡号没有对应的卡，不计算考勤，但要标明已计算，如：
//          1. 没有读到卡信息，会产生一条卡号为 0 的刷卡记录
//          2. 通过无障碍通道时，无需刷卡，但也会产生刷卡记录，其卡号为 0
            if(card == null){
                logger.error(threadName + "无效卡号： " + e.getCardNo());
                doneCalc(e);
                return;
            }

//          查询持卡人
            String ownId = card.getOwnId();
            if(ownId == null){//如未指定持卡人(卡未和人员关联)，不计算考勤，但要标明已计算.
                logger.error(threadName + "卡无归属人，卡号：" + e.getCardNo());
                doneCalc(e);
                return;
            }

            Employee emp = employeeDao.getById(ownId);

            if(emp == null){//卡所关联的人员不存在，不计算考勤，但要标明已计算
                logger.error(threadName + "卡的归属人应该存在，但查询不存在，卡号：" + e.getCardNo());
                doneCalc(e);
                return;
            }

//          匹配班次结果: 不可能返回休息班次
            ShiftResult shiftResult = matchShiftResult(emp.getId(), eventTime);
            if(shiftResult == null){//可能是：1.处于休息班次内；2. 处于正常班次之间
                doneCalc(e);
                return;
            }

//          是否参与考勤
            if(emp.getIsInvolve()){
//              获取刷卡时段
                IntervalResult accessInterval = getAccessInterval(shiftResult, eventTime);

//              不计考勤（不对考勤记录新增/更新）的情形 - 1
                if(accessInterval == null){//刷卡时间不处于任何一个时段
                    doneCalc(e);
                    return;
                } else {//刷卡时间处于某个时段，但所在区间无效
                    boolean validIn = datetimeBetween(eventTime, accessInterval.getSignInTimeBegin(), accessInterval.getLateDeadLine());
                    boolean validOut = datetimeBetween(eventTime, accessInterval.getEarlyDeadLine(), accessInterval.getSignOutTimeEnd());

                    if((!validIn) && (!validOut)){//无效区间：既不处于签到区间，又不属于签退区间
                        doneCalc(e);
                        return;
                    }
                }

//              不计考勤（不对考勤记录新增/更新）的情形 - 2
                if(Direction.IN.equals(ioType)){
                    if(datetimeBetween(eventTime, accessInterval.getEarlyDeadLine(), accessInterval.getSignOutTimeEnd())){
//                      在签退区间内签到
                        doneCalc(e);
                        return;
                    }
                }else if (Direction.OUT.equals(ioType)){
                    if(datetimeBetween(eventTime, accessInterval.getSignInTimeBegin(), accessInterval.getLateDeadLine())){
//                      在签到区间内签退
                        doneCalc(e);
                        return;
                    }
                }

//              查询班次绑定日期（并非刷卡当天）的考勤记录
                Date shiftDay = shiftResult.getDay().toDate();
                AtteInfo atteInfo = findAtteInfo(emp, shiftDay);
                if(atteInfo == null){
                    atteInfo = atteInfoDao.save(newAtteInfo(shiftResult, emp));//某人某天某班次
                }

//              查询考勤时段
                AtteDetail atteDetail = findDetail(atteInfo, accessInterval.getInterval());

                if(atteDetail == null){//考勤时段不存在
                    atteDetail = atteDetailDao.save(newAtteDetail(atteInfo, accessInterval.getInterval()));
                }

//              记录打卡时间
//                  无论请假与否都得记录，因此不能只在未请假时记录。要单独提出来。
                if(Direction.IN.equals(ioType)) {//进
                    Date preSignInTS = atteDetail.getSignInTime();//已有签到时间
                    if (preSignInTS == null) {
                        atteDetail.setSignInTime(eventTime.toDate());
                    } else {
                        if (dateTimeCompare(eventTime.toDate(), preSignInTS) < 0) {//刷卡时间早于已有"签到时间"
                            atteDetail.setSignInTime(eventTime.toDate());
                        }
                    }
                } else if(Direction.OUT.equals(ioType)) {//出
                    Date preSignOutTS = atteDetail.getSignOutTime();//已有签退时间

                    if (preSignOutTS == null) {//尚未签退
                        atteDetail.setSignOutTime(eventTime.toDate());
                    } else {//已有签退
                        if (dateTimeCompare(eventTime.toDate(), preSignOutTS) > 0) {//刷卡时间晚于已有"签退时间"
                            atteDetail.setSignOutTime(eventTime.toDate());
                        }
                    }
                }

//              查询和刷卡时段重叠的请假记录
                List<LeaveRecord> lrs = getLeaveRecords(accessInterval.getWorkTimeBegin(), accessInterval.getWorkTimeEnd(), emp.getId());

//              计算考勤
                if(lrs.size() == 0){ //如未请假
                    if(Direction.IN.equals(ioType)){//进
                        Integer oldLateMins = atteDetail.getArriveLateNum();

//                      签到区间：正常区间 + 迟到区间
//                      迟到区间（上班开始时间 - 迟到临界时间）
                        if(datetimeBetween(eventTime, accessInterval.getWorkTimeBegin(), accessInterval.getLateDeadLine())){
                            Long actualSignInMillis = atteDetail.getSignInTime().getTime();//实际签到时间
                            Long standardSignInMillis = accessInterval.getWorkTimeBegin().getMillis();//标准签到时间

                            Integer newLateMins = getMins(actualSignInMillis - standardSignInMillis).intValue();//肯定大于0

                            if(newLateMins > accessInterval.getInterval().getLateDelay()){//迟到，且超出允许的范围
                                if(oldLateMins == 0){//之前尚未计算
                                    atteDetail.setArriveLateNum(newLateMins);
                                    atteInfo.setArriveLateCount(atteInfo.getArriveLateCount() + newLateMins);
                                }else if(oldLateMins > 0){//之前计算过
                                    if(newLateMins < oldLateMins){//修正数据延迟导致的误判：迟到了，但迟到的没那么多，需要修正为最少的迟到分钟数
                                        atteDetail.setArriveLateNum(newLateMins);
                                        atteInfo.setArriveLateCount(atteInfo.getArriveLateCount() - oldLateMins + newLateMins);
                                    }
                                }
                            }else{//迟到，但在允许的范围内
                                atteDetail.setArriveLateNum(0);
                                revokePreLateAssert(atteInfo, oldLateMins);
                            }
                        } else if (datetimeBetween(eventTime, accessInterval.getSignInTimeBegin(), accessInterval.getWorkTimeBegin())){
//                          正常区间（签到临界时间 - 上班开始时间）
                            atteDetail.setArriveLateNum(0);
                            revokePreLateAssert(atteInfo, oldLateMins);
                        }
                    }else if(Direction.OUT.equals(ioType)) {//出
                        Integer oldEarlyMins = atteDetail.getLeaveEarlyNum();

//                      签退区间：正常区间 + 早退区间
//                      早退区间（早退临界时间 - 下班结束时间）
                        if(datetimeBetween(eventTime, accessInterval.getEarlyDeadLine(), accessInterval.getWorkTimeEnd())){
                            long actualSignOutMillis = atteDetail.getSignOutTime().getTime();//实际签退时间
                            long standardSignOutMillis = accessInterval.getWorkTimeEnd().getMillis();//标准签退时间

                            Integer newEarlyMins = getMins(standardSignOutMillis - actualSignOutMillis).intValue();//肯定大于0

                            if(newEarlyMins > accessInterval.getInterval().getEarlyDelay()){//早退，且超出允许范围
                                if(oldEarlyMins == 0){//之前尚未计算
                                    atteDetail.setLeaveEarlyNum(newEarlyMins);
                                    atteInfo.setLeaveEarlyCount(atteInfo.getLeaveEarlyCount() + newEarlyMins);
                                }else if(oldEarlyMins > 0){//之前计算过
                                    if(newEarlyMins < oldEarlyMins){//早退了，但早退的没那么多，需要修正为最少的早退分钟数
                                        atteDetail.setLeaveEarlyNum(newEarlyMins);
                                        atteInfo.setLeaveEarlyCount(atteInfo.getLeaveEarlyCount() - oldEarlyMins + newEarlyMins);
                                    }
                                }
                            }else{//早退，但在允许范围内
                                atteDetail.setLeaveEarlyNum(0);
                                revokePreEarlyAssert(atteInfo, oldEarlyMins);//撤销之前数据延迟导致的误判
                            }
                        } else if (datetimeBetween(eventTime, accessInterval.getWorkTimeEnd(), accessInterval.getSignOutTimeEnd())){
//                          正常区间（下班结束时间 - 签退临界时间）
                            atteDetail.setLeaveEarlyNum(0);
                            revokePreEarlyAssert(atteInfo, oldEarlyMins);//撤销之前数据延迟导致的误判
                        }
                    }
                }else{ //当前时段已请假，此时请假记录和时段一定有重叠，计算请假时长并记录
                    Integer oldLeaveNum = atteDetail.getLeaveNum();
                    Integer newLeaveNum = getSumLeaveMins(accessInterval, lrs).intValue();

                    atteDetail.setLeaveType(lrs.get(0).getLeaveType());//请假类型，以第一个请假记录的类型为准
                    atteDetail.setLeaveNum(newLeaveNum);//请假分钟数

//                  请假分钟数始终以最新计算结果为准
                    atteInfo.setLeaveCount(atteInfo.getLeaveCount() - oldLeaveNum + newLeaveNum);
                }
            }
        }

        doneCalc(e);
        logger.info(threadName + "结束计算考勤，刷卡记录标志位已改变");

//      每次计算完成后都要 flush(), 而不是每一批，因为下一次计算需要以上一次的计算为参照
//      否则：
//          第一次计算时查询考勤记录和考勤时段，发现没有，就创建新的，但并没有同步到数据库
//          第二次计算时查询考勤记录和考勤时段，发现没有，也创建新的，同样没有同步到数据库
//          ...
//          这样，本应创建一次的考勤记录和考勤时段，却创建(new)了多次，而 Hibernate 为新创建的实体分配的 ID 各不相同
//          导致批量同步时：本该插入一条的 "考勤记录/考勤时段" 重复插入了多条
        accessEventDao.flush();//将当前线程上绑定的 Session 同步到数据库，同时清理一级缓存(一级缓存内有当前线程查询的 AtteInfo, AtteDetail, AccessInterval 和传入的 AccessEvent)
    }

    /**获取刷卡时间所在的班次
     * @param eventTime 刷卡时间
     * @param empId 人员 ID
     * @return 班次，如未匹配到班次，返回 NULL
     */
    private ShiftResult getAssociatedShift(DateTime eventTime, String empId) {
        ShiftResult shiftResult;//匹配结果

//      1. 刷卡当天班次
        String shiftIdCurr = shiftAssignDao.getShift(empId, eventTime.toDate());
//      2. 刷卡前一天班次
        DateTime dayPre = eventTime.minusDays(1);
        String shiftIdPre = shiftAssignDao.getShift(empId, dayPre.toDate());

        if(shiftIdCurr != null){//当天有班次
            Shift shiftCurr = shiftDao.getById(shiftIdCurr);

            if(REST.equals(shiftCurr.getShiftType())){


            }else{//ShiftType.NORMAL
                ShiftResult sr = getShiftResult(shiftCurr, eventTime);
                if(matchShift(eventTime, sr)){
                    shiftResult = sr;
                    shiftResult.setShiftMatched(true);
                    shiftResult.setShiftOnAccessDayExist(true);
                }
            }
        }

        if (shiftIdPre != null) {//前一天有班次

        }

//      如果未能匹配到班次就使用全局班次，如下情形就不合理了：
//          刷卡当天和前一天均未匹配到班次，但刷卡当天存在班次
//      因此，使用全局班次的条件是：
//          刷卡当天和前一天均未匹配到班次，且刷卡当天未分配班次

//      执行到这里，说明无论是刷卡当天还是前一天，都未能匹配到班次，shiftResult 仍然为 NULL
        shiftResult = new ShiftResult();
        shiftResult.setShiftMatched(false);
        shiftResult.setShiftOnAccessDayExist(shiftIdCurr != null);//刷卡当天是否有班次

        return shiftResult;
    }

    /**获取可计算的班次结果
     * @param shift 班次
     * @param day 班次绑定的日期
     * @return 班次结果
     */
    private ShiftResult getShiftResult(Shift shift, DateTime day) {
        ShiftResult result = new ShiftResult();

        result.setDay(day);
        result.setShift(shift);

//      排班：1. 单独指定某天的班次；2.使用规律班次排班
//      前提：如果班次下没有时段，不能排班，这样才能保证下述代码的安全性。
        List<Interval> intervals = intervalDao.findIntervals(shift.getId());

//      计算班次边界
        String boundLeft = intervals.get(0).getSignInTimeBegin();
        String boundRight = intervals.get(0).getSignOutTimeEnd();

//      时段存储的时间均为字符串格式的时间，范围：00:00:00 - 72:00:00
        for (int i = 1; i < intervals.size(); i++) {
            Interval interval = intervals.get(i);
            if(boundLeft.compareTo(interval.getSignInTimeBegin()) == 1){
                boundLeft = interval.getSignInTimeBegin();
            }
            if(boundRight.compareTo(interval.getSignOutTimeEnd()) == -1){
                boundRight = interval.getSignOutTimeEnd();
            }
        }

        result.setIntervals(intervals);
        result.setShiftSignInBegin(resetTime(day, boundLeft));
        result.setShiftSignOutEnd(resetTime(day, boundRight));

        List<IntervalResult> irs = new ArrayList<>(intervals.size());
        for (Interval i : intervals) {
            irs.add(getIntervalResult(day, i));
        }
        result.setIntervalResults(irs);

        return result;
    }

    /**获取可计算的时段
     * @param day 时段所属班次绑定的日期
     * @param i 时段
     */
    private IntervalResult getIntervalResult(DateTime day, Interval i) {
        IntervalResult ir = new IntervalResult();

        ir.setInterval(i);//绑定关联的时段

        ir.setSignInTimeBegin(resetTime(day, i.getSignInTimeBegin()));
        ir.setWorkTimeBegin(resetTime(day, i.getWorkTimeBegin()));
        ir.setLateDeadLine(resetTime(day, i.getLateDeadLine()));
        ir.setEarlyDeadLine(resetTime(day, i.getEarlyDeadLine()));
        ir.setWorkTimeEnd(resetTime(day, i.getWorkTimeEnd()));
        ir.setSignOutTimeEnd(resetTime(day, i.getSignOutTimeEnd()));

        return ir;
    }

    /**根据班次边界（"开始时段的 signInTimeBegin" 到 "结束时段的 signOutTimeEnd"）判断刷卡时间是否发生在指定班次
     * @param eventTime 刷卡时间
     * @param shiftResult 可计算的班次结果
     * @return 如果匹配，返回 true, 否则 false
     */
    private boolean matchShift(DateTime eventTime, ShiftResult shiftResult) {
        if(eventTime.isAfter(shiftResult.getShiftSignInBegin()) && eventTime.isBefore(shiftResult.getShiftSignOutEnd())){
            return true;
        }

        return false;
    }

    /**刷卡记录计算完成后，将其标志位修改为 true
     * @param e 刷卡记录
     */
    private void doneCalc(AccessEvent e) {
        e.setIsCalced(true);
        accessEventDao.update(e);//将刷卡记录添加到 Session 中，方便 flush 和 clear
    }

    /**撤销之前的早退认定，这个认定往往是由数据延时导致的
     * @param atteInfo 考勤记录
     * @param preEarlyMins    之前误判的早退分钟数
     */
    public void revokePreEarlyAssert(AtteInfo atteInfo, Integer preEarlyMins) {
        if(preEarlyMins != 0){//如果有数据延迟导致的误判
            atteInfo.setLeaveEarlyCount(atteInfo.getLeaveEarlyCount() - preEarlyMins);
        }
    }

    /**撤销之前的迟到认定，这个认定往往是由数据延时导致的
     * @param atteInfo 考勤记录
     * @param preLateMins    之前误判的迟到分钟数
     */
    public void revokePreLateAssert(AtteInfo atteInfo, Integer preLateMins) {
        if(preLateMins != 0){//如果有数据延迟导致的误判
            atteInfo.setArriveLateCount(atteInfo.getArriveLateCount() - preLateMins);
        }
    }

    public Long getSumLeaveMins(IntervalResult currInterResult, List<LeaveRecord> leaveRecords) {

        long sum = 0;//时段多次请假重叠

        long inBegin = currInterResult.getWorkTimeBegin().getMillis();//时段开始
        long inEnd = currInterResult.getWorkTimeEnd().getMillis();//时段结束

        long iLen = inEnd - inBegin;//时段时长

        for (LeaveRecord leaveRecord : leaveRecords) {

            long lrBegin = leaveRecord.getFromDate().getTime();//请假记录开始
            long lrEnd = leaveRecord.getToDate().getTime();//请假记录结束

            long leaveMillis;//时段单次请假重叠

            if(lrBegin < inBegin && (inBegin < lrEnd && lrEnd < inEnd)){//前重叠
                leaveMillis = lrEnd - inBegin;
            } else if((inBegin < lrBegin) && (lrEnd < inEnd)){//被包含
                leaveMillis = lrEnd - lrBegin;
            } else if((inBegin < lrBegin && lrBegin < inEnd) && inEnd < lrEnd){//后重叠
                leaveMillis = inEnd - lrBegin;
            } else {//由于请假记录和时段必然重叠，因此，这里就是：请假记录跨越整个时段
                leaveMillis = iLen;
            }

            sum += leaveMillis;
        }

        if(sum > iLen){//如果同一时段不同请假记录互相重叠，如：时段（8点到12点）内请假：（7点到11点+9点到13点）
            return getMins(iLen);
        }

        return getMins(sum);
    }

    @Override
    public List<LeaveRecord> getLeaveRecords(DateTime begin, DateTime end, String empId) {
        DetachedCriteria criteria = forClass(LeaveRecord.class)
                .add(forName("employee.id").eq(empId))
                .add(and(
                    forName("toDate").gt(begin.toDate()),
                    forName("fromDate").lt(end.toDate())
                ));

        return leaveRecordDao.findListByCriteria(criteria);
    }


    @Override
    public IntervalResult getAccessInterval(ShiftResult shiftResult, DateTime eventTime) {
        for (IntervalResult result : shiftResult.getIntervalResults()) {
            if(datetimeBetween(eventTime, result.getSignInTimeBegin(), result.getSignOutTimeEnd())){
                return result;
            }
        }
        return null;
    }

    @Override
    public AtteInfo findAtteInfo(Employee employee, Date day) {
        DetachedCriteria criteria = forClass(AtteInfo.class);
        criteria.add(forName("empFK").eq(employee.getId()));
        criteria.add(forName("day").eq(day));//JPA 限定只比较日期

        return atteInfoDao.findOneByCriteria(criteria);
    }

    @Override
    public AtteDetail findDetail(AtteInfo info, Interval accessInterval) {
        DetachedCriteria criteria = forClass(AtteDetail.class);
        criteria.add(forName("interval.id").eq(accessInterval.getId()))
                .add(forName("atteInfo.id").eq(info.getId()));

        return atteDetailDao.findOneByCriteria(criteria);
    }
}
