package com.xingchen.boot.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName DateUtils
 * @Description TODO(为了统一时间处理，同时防止由于使用不合理的类库造成并发问题)
 * @Author xingchen
 * @Date 2019-08-08 16:17
 * @Version 1.0.0
 */
public class DateUtils {

    /**
     * 设置默认时区为亚洲上海
     */
//    public static final String DETAULR_TIME_ZONE = "Asia/Shanghai";
    public static final String DETAULR_TIME_ZONE = "+08:00";

    public static final String PATTERN_YYYYMMDD = "yyyyMMdd";
    public static final String PATTERN_YYYYMMDD_LINE = "yyyy-MM-dd";
    public static final String PATTERN_YYYYMMDDhhmmss_LINE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据指定的pattern格式化输出给定的时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String parse(Date date, String pattern) {
        if (date == null) return null;
        return getDateTime(date).toString(pattern);
    }

    /**
     * 使用默认的pattern格式化指定的时间
     *
     * @param date
     * @return
     */
    public static String parseDefault(Date date) {
        return getDateTime(date).toString(PATTERN_YYYYMMDD);
    }

    /**
     * 使用默认的pattern格式化指定的时间
     *
     * @param date
     * @return
     */
    public static String parseDateLine(Date date) {
        return getDateTime(date).toString(PATTERN_YYYYMMDD_LINE);
    }


    /**
     * 使用当前时间并使用指定的pattern格式化数据
     *
     * @return
     */
    public static String parseNowWithDefault() {
        return getDateTime().toString(PATTERN_YYYYMMDD);
    }

    /**
     * 根据指定的pattern格式化输出给定的时间
     *
     * @param pattern
     * @param pattern
     * @return
     */
    public static String parseNowWithPattern(String pattern) {
        return getDateTime().toString(pattern);
    }

    /**
     * 获取时间操作句柄
     *
     * @return
     */
    public static DateTime getDateTime() {
        return new DateTime(DateTimeZone.forID(DETAULR_TIME_ZONE));
    }

    /**
     * 根据给定的时间 获取时间操作句柄
     *
     * @return
     */
    public static DateTime getDateTime(Date date) {
        return new DateTime(date, DateTimeZone.forID(DETAULR_TIME_ZONE));
    }

    /**
     * 根据给定的时间字符串 获取时间操作句柄
     *
     * @return
     */
    public static DateTime getDateTime(String dateString) {
        return new DateTime(dateString, DateTimeZone.forID(DETAULR_TIME_ZONE));
    }

    /**
     * 功能描述: 格式化字符串时间
     *
     * @param: [dateString, pattern]
     * @return: org.joda.time.DateTime
     * @auther: pengw
     * @date: 2018/12/8 17:23
     */
    public static DateTime getDateTime(String dateString, String pattern) {
        if (Strings.isNullOrEmpty(pattern)) {
            pattern = PATTERN_YYYYMMDD_LINE;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern).withZone(DateTimeZone.forID(DETAULR_TIME_ZONE));
        return dateTimeFormatter.parseDateTime(dateString);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getNow() {
        return getDateTime().toDate();
    }

    /**
     * 格式化当前时间
     *
     * @param pattern
     * @return
     */
    public static DateTime getNowWithPattern(String pattern) {
        String dateStr = getDateTime().toString(pattern);
        return getDateTime(dateStr);
    }

    /**
     * 将指定的时间格式化后返回
     *
     * @param date
     * @param pattern
     * @return
     */
    public static DateTime getDate(Date date, String pattern) {
        String dateStr = getDateTime(date).toString(pattern);
        return getDateTime(dateStr);
    }

    /**
     * 在当前时间向前或者向后推移offset天
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date offsetDateByDay(Date date, int offset) {
        return getDateTime(date).plusDays(offset).toDate();
    }

    /**
     * 在当前时间向前或者向后推移offset分钟
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date offsetDateByMillis(Date date, int offset) {
        return getDateTime(date).plusMillis(offset).toDate();
    }

    /**
     * 获取两个时间之间有多少天，决对天数
     *
     * @param start
     * @param end
     * @return
     */
    public static int getStepBetweenDays(Date start, Date end) {
        start = getDate(start, PATTERN_YYYYMMDD_LINE).toDate();
        end = getDate(end, PATTERN_YYYYMMDD_LINE).toDate();
        Period duration = new Period(getDateTime(start), getDateTime(end), PeriodType.days());
        return Math.abs(duration.getDays());
    }

    /**
     * 获取两个时间相差多少秒
     *
     * @param start
     * @param end
     * @return
     */
    public static int getStepBetweenSeconds(Date start, Date end) {
        Period duration = new Period(getDateTime(start), getDateTime(end), PeriodType.days());
        return Math.abs(duration.getSeconds());
    }

    /**
     * 返回一个时间区间，开始时间为startTime结束时间为endTime,offset为时间跨度，
     * 如果传入起始时间为null，则默认为当前时间为起始时间
     *
     * @param startTime
     * @param offset
     * @return
     */
    public static Map<String, Object> rangeTime(Date startTime, int offset) {
        Map<String, Object> range = Maps.newHashMap();
        if (startTime == null) {
            startTime = getNow();
        }
        if (offset >= 0) {
            range.put("startTime", startTime);
            range.put("endTime", offsetDateByDay(startTime, offset));
        } else {
            range.put("startTime", offsetDateByDay(startTime, offset));
            range.put("endTime", startTime);
        }

        return range;
    }
}
