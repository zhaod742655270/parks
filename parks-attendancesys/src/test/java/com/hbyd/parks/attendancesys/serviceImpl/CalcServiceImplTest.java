package com.hbyd.parks.attendancesys.serviceImpl;


import com.hbyd.parks.dao.attendancesys.AtteInfoDao;
import com.hbyd.parks.dao.attendancesys.GlobalDefDao;
import com.hbyd.parks.dao.attendancesys.HolidayDao;
import com.hbyd.parks.dao.attendancesys.IntervalDao;
import com.hbyd.parks.dao.doorsys.AccessEventDao;
import com.hbyd.parks.dao.supportsys.EmployeeDao;
import com.hbyd.parks.domain.attendancesys.Holiday;
import com.hbyd.parks.domain.attendancesys.Interval;
import com.hbyd.parks.domain.doorsys.AccessEvent;
import com.hbyd.parks.attendancesys.service.CalcService;
import com.hbyd.parks.common.util.DateUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "/spring/applicationContext.xml",
        "/spring/applicationContext_Hibernate.xml",
        "/spring/attendancesys/applicationContext_Task.xml"
})
public class CalcServiceImplTest {

//    用于准备测试数据的 DAO
    @Resource
    private HolidayDao holidayDao;

    @Resource
    private AtteInfoDao atteInfoDao;

    @Resource
    private EmployeeDao employeeDao;

    @Resource
    private AccessEventDao accessEventDao;

    @Resource
    private IntervalDao intervalDao;

    @Resource
    private GlobalDefDao globalDefDao;

//    测试的目标服务
    @Resource
    private CalcService calcService;

    @Test
    public void testFindOne() throws ParseException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Interval.class);
        Date accessTime = DateUtil.parseTime("14:04:24");

        String shiftID = "24c6869c489b060501489bfac7a30001";
        criteria.add(Property.forName("shift.id").eq(shiftID))
                .add(Property.forName("workTimeBegin").lt(accessTime))//JPA 限定只比较时间
                .add(Property.forName("workTimeEnd").gt(accessTime));

        Interval oneByCriteria = intervalDao.findOneByCriteria(criteria);
        System.out.println(oneByCriteria.getWorkTimeBegin());
        System.out.println(oneByCriteria.getWorkTimeEnd());
    }

    @Test
    public void testIsHoliday() throws ParseException {

//        插入测试假日记录：中秋节
        Holiday holiday = new Holiday();
        holiday.setName("测试节假日_1");
        holiday.setStartDate(new java.sql.Date(DateUtil.parseDate("2014-07-23").getTime()));
        holiday.setEndDate(new java.sql.Date(DateUtil.parseDate("2014-07-30").getTime()));
        holidayDao.save(holiday);

//        测试临界值
        assertTrue(calcService.isHoliday(DateUtil.parseDate("2014-07-23")));
        assertTrue(calcService.isHoliday(DateUtil.parseDate("2014-07-30")));

//        测试中间值
        assertTrue(calcService.isHoliday(DateUtil.parseDate("2014-07-25")));

//        测试界外值
        assertFalse(calcService.isHoliday(DateUtil.parseDate("2014-09-10")));
        assertFalse(calcService.isHoliday(DateUtil.parseDate("2014-06-20")));

//        测试今天是否假日
        System.out.println(calcService.isHoliday(new Date()));
    }

    @Test
    public void testCalc(){
        List<AccessEvent> events = accessEventDao.findAll();
        System.out.println("多少个刷卡记录：" + events.size());
        for (AccessEvent e : events) {
            calcService.calc(e);
        }

    }
}