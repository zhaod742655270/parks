package com.hbyd.parks.attendancesys.util;

import com.hbyd.parks.common.util.DateUtil;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static com.hbyd.parks.common.util.DateUtil.*;

public class DateUtilTest {
    @Test
    public void bar(){
        int row = 3;
        int col = 9;
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++) {//每行分几次输出
                System.out.print(j + 1 + "\t");
            }

            System.out.println();
        }
    }

    @Test
    public void foo() {
        int n = 5;
        for (int i = 0; i < n; i++) {
            for (int a = 0; a < (n - (i + 1)); a++) {
                System.out.print(" ");
            }
            System.out.print("*");
            for (int b = 0; b < (i * 2); b++) {
                System.out.print("-");
            }
            System.out.print("*");
            System.out.println();
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int a = 0; a < (n - (i + 1)); a++) {
                System.out.print(" ");
            }
            System.out.print("*");
            for (int b = 0; b < (i * 2); b++) {
                System.out.print("-");
            }
            System.out.print("*");
            System.out.println();
        }
    }

    @Test
    public void testJodaWeekend() {
        DateTime.Property dayOfWeek = DateTime.parse("2015-01-04").dayOfWeek();
        System.out.println(dayOfWeek.getAsText());
        System.out.println(dayOfWeek.getAsShortText());
        Assert.assertTrue(dayOfWeek.get() == DateTimeConstants.SUNDAY);
    }

    @Test
    public void testGetMins() {
        int mins = DateUtil.getMins(new Date(), DateTime.now().plusHours(1).toDate());
        Assert.assertEquals(60, mins);
    }

    /**
     * 测试：Timestamps before Epoch (1970 Jan 1st) are represented by negative numbers.
     *
     * @throws ParseException
     */
    @Test
    public void testGetTime() throws ParseException {
//        Date.getTime() 的起算时间为：January 1, 1970, 00:00:00 GMT
        System.out.println(new Date(0).toLocaleString());//1970-1-1 8:00:00, 不一致，因为不是 GMT 时区

        Date someDate = DateUtil.parseDateTime("1900-1-1 12:10:00");
        Date someDate2 = DateUtil.parseDateTime("2000-1-1 12:10:00");
        Date someDate3 = DateUtil.parseDateTime("2013-1-1 12:10:00");

        System.out.println(DateUtil.getTimePart(someDate));//-71757000, 时间部分竟然成了负数
        System.out.println(DateUtil.getTimePart(someDate2));//15000000
        System.out.println(DateUtil.getTimePart(someDate3));//15000000

//        如果需要和 1970-1-1 8:00:00 前的 Date 做比较，如 1900-01-01 11:40:00, 需先将日期部分修改为 1970-1-1 以后才能正常比较

        System.out.println(someDate.toLocaleString());//1900-1-1 12:10:00
        System.out.println(someDate2.toLocaleString());//2000-1-1 12:10:00
        System.out.println(someDate3.toLocaleString());//2013-1-1 12:10:00

        System.out.println(someDate.getTime());//-2208974157000
        System.out.println(someDate2.getTime());//946699800000
        System.out.println(someDate3.getTime());//1357013400000
    }

    @Test
    public void testBar() throws ParseException {
        System.out.println(new Date().toString());//Thu Sep 11 14:00:16 CST 2014

        Calendar c = new GregorianCalendar(1900, 10, 11, 0, 0, 0);
        Date d = DateUtil.parseDateTime("1900-10-11 00:00:00");
        System.out.println(c.getTimeInMillis());//-2181888357000
        System.out.println(d.getTime());//-2184566757000

        System.out.println(c.get(Calendar.DAY_OF_MONTH) + " "
                + c.get(Calendar.MONTH) + ", " + c.get(Calendar.YEAR) + " "
                + c.get(Calendar.ERA));//11 10, 1900 1

        System.out.println(c.getTime());//Sun Nov 11 00:00:00 CST 1900
    }

    /**
     * 测试：java.util.Date 可表示的范围，按当前时区（而非中央时区）输出
     */
    @Test
    public void testDateRange() {
        DateFormat df = new SimpleDateFormat("d MMM yyyy G, HH:mm:ss.S Z");

        System.out.println(df.format(new Date(Long.MIN_VALUE)));//3 十二月 292269055 公元前, 00:47:04.192 +0800

        System.out.println(df.format(new Date(0)));//1 一月 1970 公元, 08:00:00.0 +0800

        System.out.println(df.format(new Date(Long.MAX_VALUE)));//17 八月 292278994 公元, 15:12:55.807 +0800
    }

    /**
     * 测试：java.sql.Date 和 java.sql.Time, 参考 javadoc, seriously!
     */
    @Test
    public void testJDBCDateAndTime() {
        Time time = new Time(12, 1, 1);
        System.out.println(time.toString());//12:01:01

//        year - the year minus 1900; must be 0 to 8099. (Note that 8099 is 9999 minus 1900.)
//        month - 0 to 11
//        day - 1 to 31
        java.sql.Date date = new java.sql.Date(100, 0, 1);
        System.out.println(date.toString());//2000-01-01
    }

//    @Test
//    public void testFoo() throws ParseException {
//        long time = DateUtil.parseDateTime("2013-01-20 08:09:09").getTime();
//        System.out.println(time);
//        long time1 = DateUtil.parseDateTime("2013-02-10 08:09:09").getTime();
//        System.out.println(time1);
//        long time2 = DateUtil.parseDateTime("2014-02-10 14:09:09").getTime();
//        System.out.println(time2);
//        long time3 = DateUtil.parseDateTime("2014-06-20 08:19:09").getTime();
//        System.out.println(time3);
//
//        Date date = null; // your date
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        // etc.
//    }

    @Test
    public void testSqlTime() {
        Time time = new Time(12, 0, 0);
        long millis = time.getTime();
        System.out.println(millis);//14400000 ms since 1970-1-1 08:00:00

        Assert.assertEquals(millis, DateUtil.getTimePart(time));
    }

    /**
     * jdk 的 date.getTime() 和 joda-time 的 new Datetime(date).getMillis() 是等价的
     */
    @Test
    public void testMillis() {
        Date date = new Date();

        Assert.assertEquals(date.getTime(), new DateTime(date).getMillis());
    }

    @Test
    public void testGetDaysBetween() throws ParseException {
        Date begin = DateUtil.parseDate("2014-10-1");
        Date end = DateUtil.parseDate("2014-10-7");
        int daysBetween = DateUtil.getDaysBetween(begin, end);
        System.out.println(daysBetween);//6
    }

    @Test
    public void testGetMonthsBetween() throws ParseException {
        Date begin = DateUtil.parseDate("2014-1-1");
        Date end = DateUtil.parseDate("2014-10-7");

        System.out.println(new DateTime(begin).getMonthOfYear());//1
        System.out.println(new DateTime(end).getMonthOfYear());//10

        int monthsBetween = DateUtil.getMonthsBetween(begin, end);
        System.out.println(monthsBetween);//9
    }

    @Test
    public void testDayOfWeek() throws ParseException {
//      parse() 出来的 Date 都是本地时区的 Date
//      如果数据库字段存储的只有日期部分，Hibernate 将其映射为 Date 时，时间部分为本地时区格式，如：2014-10-20T00:00:00.000+08:00
        Date d1 = DateUtil.parseDate("2014-10-20");
        DateTime dt1 = new DateTime(d1);
        System.out.println(dt1);//东八区：2014-10-20T00:00:00.000+08:00
        Assert.assertEquals(DateTimeConstants.MONDAY, dt1.getDayOfWeek());

        Date d2 = DateUtil.parseDate("2014-10-22");
        DateTime dt2 = new DateTime(d2);
        System.out.println(dt2);//东八区：2014-10-22T00:00:00.000+08:00
        Assert.assertEquals(DateTimeConstants.WEDNESDAY, dt2.getDayOfWeek());
    }

    @Test
    public void testTimeBetween() throws ParseException {

        Date d1 = parseDateTime("2014-12-31 12:09:00");
        Date d2 = parseDateTime("2014-12-31 12:10:00");
        Date d3 = parseDateTime("2014-12-31 12:12:00");

        System.out.println(timeBetween(d2, d1, d3));//true
    }

    @Test
    public void testDatetimeBetween() {
        DateTime before = DateTime.parse("2013-10-31 12:09:00", DateTimeFormat.forPattern(DateUtil.DATETIME));
        DateTime mid = DateTime.parse("2013-12-31 12:10:00", DateTimeFormat.forPattern(DateUtil.DATETIME));
        DateTime after = DateTime.parse("2014-12-31 12:12:00", DateTimeFormat.forPattern(DateUtil.DATETIME));

        junit.framework.Assert.assertTrue(datetimeBetween(mid.toDate(), before.toDate(), after.toDate()));
    }

    @Test
    public void testJoda() throws ParseException {
//        Date date3 = parseDateTime("1970-1-1 07:50:10");
//        printTime(date3);//可以提取 8 点之前的时间

        Date date = parseDateTime("2000-10-31 14:22:09");
//        printTime(date);

        Date date2 = parseDateTime("2014-12-30 13:30:09");
//        printTime(date2);


//        compare(lhsObj, rhsObj):
//        negative value if lhsObj < rhsObj
//        negative value if left-hand-side Obj < right-hand-side Obj
        int r1 = DateTimeComparator.getDateOnlyInstance().compare(date, date2);//date < date2 ? -1 : 1
        System.out.println(r1);

        int r2 = DateTimeComparator.getTimeOnlyInstance().compare(date, date2);//date < date2 ? -1 : 1
        System.out.println(r2);
    }

    @Test
    public void test_1() throws ParseException {

//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+0:00"));//使用格林威治时间

        Date date = parseDateTime("1970-1-1 08:00:00");
        printTime(date);
//        System.out.println(getTimePart(date));//0
//        System.out.println(getDatePart(date));//0

        Date date1 = parseDateTime("1970-1-1 08:10:00");
        printTime(date1);
//        System.out.println(getTimePart(date1));//600000
//        System.out.println(getDatePart(date1));//0

        Date date2 = parseDateTime("1970-1-1 07:50:00");
        printTime(date2);
//        System.out.println(getTimePart(date2));//-600000
//        System.out.println(getDatePart(date2));//0

        Date date3 = parseDateTime("1970-1-2 07:50:00");
        printTime(date3);
//        System.out.println(getTimePart(date3));//not -600000, but 85800000. which is 24*60*60*1000 - 10 * 60 * 1000
//        System.out.println(getDatePart(date3));//0, 相对 1970-1-1 08:00:00 而言，不足一天

    }

    public void printTime(Date date) {
//      API: get + 目标单位 + of + 上一级单位
        System.out.println("-------");
        DateTime d = new DateTime(date);

        System.out.println("YearOfCentury: " + d.getYearOfCentury());//14
        System.out.println("Year: " + d.getYear());//2014
        System.out.println("MonthOfYear: " + d.getMonthOfYear());
        System.out.println("DayOfMonth: " + d.getDayOfMonth());

        System.out.println("HourOfDay: " + d.getHourOfDay());
        System.out.println("MinuteOfHour: " + d.getMinuteOfHour());
        System.out.println("SecondOfMinute: " + d.getSecondOfMinute());
        System.out.println("--------");
    }

    @Test
    public void testGetDaysWithinMonth() throws ParseException {
        Date d1 = DateUtil.parseDate("2014-2-2");
        Date d2 = DateUtil.parseDate("2014-10-1");//星期三
        Date d3 = DateUtil.parseDate("2014-4-2");
        Date d4 = DateUtil.parseDate("2013-2-2");

        System.out.println(DateUtil.getDaysWithinMonth(d1));//28
        System.out.println(DateUtil.getDaysWithinMonth(d2));//31
        System.out.println(DateUtil.getDaysWithinMonth(d3));//30
        System.out.println(DateUtil.getDaysWithinMonth(d4));//28

        System.out.println(new DateTime(d2).getDayOfMonth());//1
        System.out.println(new DateTime(d2).getDayOfWeek());//3
    }

    @Test
    public void testTimeZone() {
        System.out.println("本地默认时区：");
        System.out.println(TimeZone.getDefault().getID());//Asia/Shanghai

        System.out.println("所有时区：");

        for (String s : TimeZone.getAvailableIDs()) {
            System.out.println(s);
        }

        System.out.println("东 8 区：");

        for (String string : TimeZone.getAvailableIDs(TimeZone.getTimeZone(
                "GMT+08:00").getRawOffset())) {
            System.out.println(string);
        }
    }

    @Test
    public void testParse() {
        try {
            Date date = DateUtil.parse("2014-10-1", DateUtil.DATE);
            DateTime dateTime = new DateTime(date);

            System.out.println(dateTime.getYear());//2014
            System.out.println(dateTime.getMonthOfYear());//10
            System.out.println(dateTime.getDayOfMonth());//1

            System.out.println(dateTime.getHourOfDay());//0
            System.out.println(dateTime.getMinuteOfHour());//0
            System.out.println(dateTime.getSecondOfMinute());//0

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testResetTime() {
        String timeStr = "30:10:20";
        DateTime now = DateTime.now();
        DateTime resultDate = DateUtil.resetTime(now, timeStr);

        Assert.assertEquals(1, resultDate.getDayOfMonth() - now.getDayOfMonth());
        Assert.assertEquals((30 - 24), resultDate.getHourOfDay());
    }

    @Test
    public void testResetTime2() throws InterruptedException {
        DateTime now = DateTime.now();
        Thread.sleep(1000);
        DateTime now2 = DateTime.now();

        int hours = now2.getHourOfDay();
        int mins = now2.getMinuteOfHour();
        int secs = now2.getSecondOfMinute();

        DateTime datetime = resetTime(now, now2);

        Assert.assertEquals(hours, datetime.getHourOfDay());
        Assert.assertEquals(mins, datetime.getMinuteOfHour());
        Assert.assertEquals(secs, datetime.getSecondOfMinute());
    }

    @Test
    public void testResetTime3() {
        DateTime dateAfter = resetTime(DateTime.now(), 10, 20, 8);

//        断言
        Assert.assertEquals(10, dateAfter.getHourOfDay());
        Assert.assertEquals(20, dateAfter.getMinuteOfHour());
        Assert.assertEquals(8, dateAfter.getSecondOfMinute());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResetTimeException() {
        String timeStr = "30:10:20a";
        DateUtil.resetTime(DateTime.now(), timeStr);
    }

    @Test
    public void compareTimeInStringFormat() {
        String time_1 = "12:23:00";
        String time_2 = "13:11:40";
        String time_3 = "12:23:00";

        Assert.assertEquals(-1, time_1.compareTo(time_2));//Compares two strings lexicographically
        Assert.assertEquals(1, time_2.compareTo(time_1));
        Assert.assertEquals(0, time_1.compareTo(time_3));
    }

    /**
     * JodaTime 并没有提供 minus() 操作，但 add() 操作可以负数作为参数
     */
    @Test
    public void testJodaMinus() {
        DateTime dt = new DateTime(new Date());
        MutableDateTime mdt = dt.toMutableDateTime();
        mdt.addDays(-1);

        Days days = Days.daysBetween(mdt, dt);
        Assert.assertEquals(1, days.getDays());
    }

    @Test
    public void testAddHoursByDayOffset() {
//        String time = DateUtil.addHoursByDayOffset(0, "90:10:23");
        String time = DateUtil.addHoursByDayOffset(0, "09:10:23");
        System.out.println(time);
        time = DateUtil.addHoursByDayOffset(1, "09:10:23");
        System.out.println(time);
        time = DateUtil.addHoursByDayOffset(2, "09:10:23");
        System.out.println(time);
//-----------------------------------------------
        time = DateUtil.addHoursByDayOffset(0, "10:10:23");
        System.out.println(time);
        time = DateUtil.addHoursByDayOffset(1, "10:10:23");
        System.out.println(time);
        time = DateUtil.addHoursByDayOffset(2, "10:10:23");
        System.out.println(time);
    }

    @Test
    public void testResolveTimeString() {
        String[] result1 = DateUtil.resolveTimeString("17:00:00");
        System.out.println(result1[0]);
        System.out.println(result1[1]);

        String[] result2 = DateUtil.resolveTimeString("41:00:00");
        System.out.println(result2[0]);
        System.out.println(result2[1]);

        String[] result3 = DateUtil.resolveTimeString("65:00:00");
        System.out.println(result3[0]);
        System.out.println(result3[1]);
    }
}