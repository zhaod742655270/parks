package com.hbyd.parks.common.util;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.joda.time.*;
import org.joda.time.chrono.GregorianChronology;

import javax.annotation.Nullable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * DateTimeComparator is thread-safe and immutable.
 */
public class DateUtil {

    /**
     * 日期比较器
     */
    public static DateTimeComparator dateComparator = DateTimeComparator.getDateOnlyInstance();

    /**
     * 时间比较器
     */
    public static DateTimeComparator timeComparator = DateTimeComparator.getTimeOnlyInstance();

    /**
     * 日期时间比较器
     */
    public static DateTimeComparator dateTimeComparator = DateTimeComparator.getInstance();

    /**
     * 前一天
     */
    public static final int DAY_BEFORE = 0;
    /**
     * 当天
     */
    public static final int DAY_MIDDLE = 1;
    /**
     * 后一天
     */
    public static final int DAY_AFTER = 2;


    /**
     * datetime pattern
     */
    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * date pattern
     */
    public static final String DATE = "yyyy-MM-dd";

    /**
     * time pattern
     */
    public static final String TIME = "HH:mm:ss";

    /**
     * 一天对应的毫秒数
     */
    public static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

    /**
     * 一小时对应的毫秒数
     */
    public static final long MILLIS_PER_HOUR = 60 * 60 * 1000;

    /**
     * 一分钟对应的毫秒数
     */
    public static final long MILLIS_PER_MIN = 60 * 1000;

    /**
     * 一秒钟对应的毫秒数
     */
    public static final long MILLIS_PER_SEC = 1000;

    private static Function<String, Integer> toIntEquivalent = new Function<String, Integer>() {
        @Nullable
        @Override
        public Integer apply(String input) {
            if (input.startsWith("0")) {
                return Integer.parseInt(input.substring(1));
            } else {
                return Integer.parseInt(input);
            }
        }
    };
    ;


    /**
     * 解析时间字符串，使用默认的解析格式：yyyy-MM-dd HH:mm:ss
     *
     * @param str 日期时间字符串
     * @return 日期对象
     * @throws ParseException 格式异常
     */

    public static Date parseDateTime(String str) throws ParseException {
        return parse(str, DATETIME);
    }

    public static Date parseDate(String str) throws ParseException {
        return parse(str, DATE);
    }

    public static Date parseTime(String str) throws ParseException {
        return parse(str, TIME);
    }

    /**
     * 获取标准的年月字符串
     *
     * @param year  年份
     * @param month 月份
     * @return yyyy-MM 格式的年月字符串
     */
    public static String getYearAndMonth(int year, int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("月份只能是 1-12 月，但传入的是：" + month);
        }
        String m = Strings.padStart(month + "", 2, '0');
        String y = year + "";
        return y + '-' + m;
    }

    /**
     * 解析时间字符串
     *
     * @param datetimeStr 日期时间字符串
     * @param pattern     解析的格式，如：yyyy-MM-dd HH:mm:ss
     * @return 日期对象
     * @throws ParseException 格式异常
     */
    public static Date parse(String datetimeStr, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        format.setTimeZone(TimeZone.getDefault());
        return format.parse(datetimeStr);
    }

    /**
     * 获取时间部分的毫秒数
     *
     * @param date
     * @return 毫秒数
     */
    @Deprecated
    public static long getTimePart(Date date) {
        long millis = date.getTime();
        return millis % MILLIS_PER_DAY;
    }

    /**
     * 获取毫秒对应的分钟数
     *
     * @param millis 秒数
     * @return 分钟数
     */
    public static Long getMins(long millis) {
        return millis / MILLIS_PER_MIN;
    }

    /**获取时段间的分钟数
     * @param begin 起始日期时间
     * @param end 结束日期时间
     * @return 分钟数
     */
    public static int getMins(Date begin, Date end){
        return getMinsJoda(new DateTime(begin), new DateTime(end));
    }

    /**获取时段间的分钟数
     * @param begin 起始日期时间
     * @param end 结束日期时间
     * @return 分钟数
     */
    public static int getMinsJoda(DateTime begin, DateTime end){
        return Minutes.minutesBetween(begin, end).getMinutes();
    }

    /**
     * 时间是否处于某个区间
     *
     * @param targetDate 要判断的时间
     * @param beginDate  起始时间
     * @param endDate    结束时间
     * @param comparator 比较器
     * @return 如果在区间内, 返回 true, 否则 false.
     */
    private static boolean compare(Comparable targetDate, Comparable beginDate, Comparable endDate, DateTimeComparator comparator) {
        int r1 = comparator.compare(beginDate, targetDate);
        int r2 = comparator.compare(targetDate, endDate);

        return r1 < 0 && r2 < 0;
    }

    /**
     * {@link DateUtil#compare(java.lang.Comparable, java.lang.Comparable, java.lang.Comparable, org.joda.time.DateTimeComparator)}
     */
    public static boolean timeBetween(Date targetDate, Date beginDate, Date endDate) {
        return compare(targetDate, beginDate, endDate, timeComparator);
    }

    /**
     * {@link DateUtil#compare(java.lang.Comparable, java.lang.Comparable, java.lang.Comparable, org.joda.time.DateTimeComparator)}
     */
    public static boolean timeBetween(DateTime targetDate, DateTime beginDate, DateTime endDate) {
        return compare(targetDate, beginDate, endDate, timeComparator);
    }

    /**
     * {@link DateUtil#compare(java.lang.Comparable, java.lang.Comparable, java.lang.Comparable, org.joda.time.DateTimeComparator)}
     */
    public static boolean datetimeBetween(Date targetDate, Date beginDate, Date endDate) {
        return compare(targetDate, beginDate, endDate, dateTimeComparator);
    }

    /**
     * {@link DateUtil#compare(java.lang.Comparable, java.lang.Comparable, java.lang.Comparable, org.joda.time.DateTimeComparator)}
     */
    public static boolean datetimeBetween(DateTime targetDate, DateTime beginDate, DateTime endDate) {
        return compare(targetDate, beginDate, endDate, dateTimeComparator);
    }

    /**
     * 判断第一个 Date 是否比第二个 Date 大
     * lhs > rhs: dateTimeCompare(lhs, rhs) > 0
     * lhs < rhs: dateTimeCompare(lhs, rhs) < 0
     * 使用规则：lhs - rhs >=< 0
     *
     * @param lhs left hand side Date
     * @param rhs right hand side Date
     * @return dateTimeCompare(lhsObj, rhsObj): negative value if lhsObj < rhsObj
     */
    public static int dateTimeCompare(Date lhs, Date rhs) {
        return dateTimeComparator.compare(lhs, rhs);
    }

    /**
     * 获取时间部分对应的毫秒数
     *
     * @param date
     * @return
     */
    public static long getTimeMillis(Date date) {
        DateTime d = new DateTime(date);
        return d.getHourOfDay() * MILLIS_PER_HOUR +
                d.getMinuteOfHour() * MILLIS_PER_MIN +
                d.getSecondOfMinute() * MILLIS_PER_SEC;
    }

//    /**
//     * 判断某个时间是否处于某个时间段
//     * @param targetDate 要判断的时间
//     * @param beginDate 起始时间
//     * @param endDate   起始时间
//     * @return 如果介于时间段内，返回 true, 否则 false;
//     */
//    @Deprecated
//    public static boolean isBetween(Date targetDate, Date beginDate, Date endDate){
//        如果 java.util.Date (及其子类)是:
//        1. 1970-1-1 8:00:00 (GMT8 时区) 之后的时间, 毫秒数为正数，getTimePart 始终为正数
//        2. 1970-1-1 8:00:00 (GMT8 时区) 之前的时间, 毫秒数为负数，getTimePart 始终为负数
//        3. 数据库字段如果仅存储时间，需要使用 datetime 字段，其日期部分始终为 1900-1-1
//        Hibernate 从数据库取出数据后封装到 java.sql.Time，其日期部分默认为 1970-1-1
//
//        下面这么做不可取，因为 target 很可能无论如何也不处于 begin 和 end 之间
//        long target = getTimePart(targetDate);//如果是 1970-1-1 8:00:00 之后，始终为正数
//        long begin = getTimePart(beginDate);//如果 beginDate 为数据库时间字段，日期默认为 1970-1-1，getTimePart 始终返回负数
//        long end = getTimePart(endDate);//如果 endDate 为数据库时间字段，日期默认为 1970-1-1，getTimePart 始终返回负数
//
//        long target = getTimePart(convert(targetDate));
//        long begin = getTimePart(convert(beginDate));
//        long end = getTimePart(convert(endDate));
//
//        if(begin >= end){
//            throw new RuntimeException("beginDate of an interval must le than endDate!");
//        }
//
//        return ((begin <= target) && (target <= end));
//    }

    /**
     * 将 java.util.Date 转化为 java.sql.Time
     *
     * @param date java.util.Date
     * @return java.sql.Time
     */
    @Deprecated
    public static Time convert(Date date) {
//        return new Time(date.getTime());//不可行
        return Time.valueOf(new Time(date.getTime()).toString());
    }

    /**
     * 获取两个日期间的天数
     *
     * @param begin 起始日期
     * @param end   结束日期
     * @return 中间的天数
     */
    public static int getDaysBetween(Date begin, Date end) {
        DateTime jBegin = new DateTime(begin);
        DateTime jEnd = new DateTime(end);

        if(jBegin.isAfter(jEnd)){
            throw new IllegalArgumentException("起始日期必须晚于结束日期");
        }

        return Days.daysBetween(jBegin, jEnd).getDays();
    }

    /**
     * 获取两个日期间的月数
     *
     * @param begin 起始日期
     * @param end   结束日期
     * @return 中间的月数
     */
    public static int getMonthsBetween(Date begin, Date end) {
        return Months.monthsBetween(new DateTime(begin), new DateTime(end)).getMonths();
    }

    /**
     * 获取某月内的天数
     *
     * @param date 日期
     * @return 月内的天数
     */
    public static int getDaysWithinMonth(Date date) {
        return GregorianChronology.getInstance().dayOfMonth().getMaximumValue(date.getTime());
    }

    /**
     * 重置日期时间的时间部分
     *
     * @param datetime 日期时间
     * @param timeStr  时间字符串，格式为 hh:mm:ss
     * @return 重置后的日期时间
     */
    public static DateTime resetTime(DateTime datetime, String timeStr) {
        valTimeStrFormat(timeStr);
        Integer[] ints = FluentIterable.from(Splitter.on(":").split(timeStr)).transform(toIntEquivalent).toArray(Integer.class);

        int hours = ints[0];
        int mins = ints[1];
        int secs = ints[2];

//        时分秒不做范围校验，因为小时数是可以大于 24 的，分钟数和秒数也都可以大于 60

        return resetTime(datetime, hours, mins, secs);
    }

    /**
     * 重置日期时间的时间部分
     *
     * @param datetime 日期时间
     * @param time     期望的时间部分
     * @return 重置后的日期时间
     */
    public static DateTime resetTime(DateTime datetime, DateTime time) {
        return resetTime(datetime,
                time.getHourOfDay(),
                time.getMinuteOfHour(),
                time.getSecondOfMinute());
    }

    /**
     * 重置日期时间的时间部分
     *
     * @param datetime 日期时间
     * @param hours    小时数
     * @param mins     分钟数
     * @param secs     秒数
     * @return 重置后的日期时间(新的 Date 对象)
     */
    public static DateTime resetTime(DateTime datetime, int hours, int mins, int secs) {
        MutableDateTime dt = datetime.toMutableDateTime();
        dt.setHourOfDay(0);
        dt.setMinuteOfHour(0);
        dt.setSecondOfMinute(0);

        dt.addHours(hours);
        dt.addMinutes(mins);
        dt.addSeconds(secs);

        return dt.toDateTime();
    }

    /**
     * add hours by day offset, hour = hour + (24 * dayOffset).
     * 0-72 小时内，前一天为 0-24，今天为 24-48，后一天为 48-72
     *
     * @param dayOffset 0/1/2 分别表示前一天/当天/后一天
     * @param timeStr   hh:MM:ss 格式的时间字符串，不足两位的用 0 补足
     */
    public static String addHoursByDayOffset(int dayOffset, String timeStr) {
//        验证参数
        if (!Lists.newArrayList(DAY_BEFORE, DAY_MIDDLE, DAY_AFTER).contains(dayOffset)) {
            throw new IllegalArgumentException("dayOffset 只能是 0/1/2!");
        }

        valTimeStrFormat(timeStr);

        Integer[] ints = FluentIterable.from(Splitter.on(":").split(timeStr)).transform(toIntEquivalent).toArray(Integer.class);

        Integer hour = ints[0];
        Integer min = ints[1];
        Integer sec = ints[2];

        valTime(hour, min, sec);

        hour += (dayOffset * 24);

        return Joiner.on(":").join(
                Strings.padStart(hour + "", 2, '0'),
                Strings.padStart(min + "", 2, '0'),
                Strings.padStart(sec + "", 2, '0')
        );
    }

    /**
     * 解析时间字符串 , 30:30:30 --> 1; 6:30:30
     * 0-72 小时内，前一天为 0-24，今天为 24-48，后一天为 48-72
     * 返回值为String数组 第一个是 dayOffset，第二个是24小时制时间字符串
     *
     * @param为72小时制时间字符串
     */
    public static String[] resolveTimeString(String timeStr) {

        valTimeStrFormat(timeStr);
        String[] result = new String[2];
        Integer[] ints = FluentIterable.from(Splitter.on(":").split(timeStr)).transform(toIntEquivalent).toArray(Integer.class);
        int hour = ints[0];
        int min = ints[1];
        int sec = ints[2];

        int realHour = hour % 24;
        int dayOffset = hour / 24;

        valTime(realHour, min, sec);

        String time = Joiner.on(":").join(
                Strings.padStart(realHour + "", 2, '0'),
                Strings.padStart(min + "", 2, '0'),
                Strings.padStart(sec + "", 2, '0'));

        result[0] = String.valueOf(dayOffset);
        result[1] = time;
        return result;
    }

    /**
     * 校验时间范围
     *
     * @param hour 时
     * @param min  分
     * @param sec  秒
     */
    public static void valTime(int hour, int min, int sec) {
        if (hour > 24 || min > 60 || sec > 60) {
            throw new IllegalArgumentException("时间非法");
        }
    }

    /**
     * 校验时间格式
     *
     * @param timeStr hh:MM:ss 格式的时间字符串
     */
    public static void valTimeStrFormat(String timeStr) {
        if (!timeStr.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
            throw new IllegalArgumentException("时间格式非法: " + timeStr);
        }
    }
}
