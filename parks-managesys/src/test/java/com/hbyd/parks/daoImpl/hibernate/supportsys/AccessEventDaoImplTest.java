package com.hbyd.parks.daoImpl.hibernate.supportsys;

import com.hbyd.parks.dao.doorsys.AccessEventDao;
import com.hbyd.parks.daoImpl.hibernate.AppConfig;
import com.hbyd.parks.domain.doorsys.AccessEvent;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@TransactionConfiguration
public class AccessEventDaoImplTest {

    @Resource
    private AccessEventDao accessEventDao;

    @Test
    @Transactional
    public void testGetEvents() throws Exception {
//        String empId = "212647";
        String empId = "不存在的人员";

//        2014-11-1 20:00:00
        MutableDateTime beginDT = new DateTime().toMutableDateTime();
        beginDT.setYear(2014);
        beginDT.setMonthOfYear(11);
        beginDT.setDayOfMonth(1);
        beginDT.setHourOfDay(20);
        beginDT.setMinuteOfHour(0);
        beginDT.setSecondOfMinute(0);

//        2014-11-2 06:00:00
        MutableDateTime endDT = new DateTime().toMutableDateTime();
        endDT.setYear(2014);
        endDT.setMonthOfYear(11);
        endDT.setDayOfMonth(2);
        endDT.setHourOfDay(6);
        endDT.setMinuteOfHour(0);
        endDT.setSecondOfMinute(0);

        List<AccessEvent> events = accessEventDao.getEvents(empId, beginDT.toDate(), endDT.toDate());

        if(events == null || events.size() == 0){
            System.out.println(events);
        }else {
            for (AccessEvent event : events) {
                System.out.println(event.getCardNo() + "\t" + event.getEventTime());
            }
        }
    }
}