package com.ara.common.util;

import com.ara.common.bean.TimeBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by XieXin on 2018/12/10.
 * 时间工具类
 */
public class DateUtils {
    /*** yyyy-MM-dd HH:mm:ss*/
    public static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    /*** yyyy/MM/dd HH:mm:ss*/
    public static final DateFormat SLASH_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
    /*** yyyy-MM-dd*/
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    /*** yyyy年MM月dd日*/
    public static final DateFormat YMD_FORMAT = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    /*** MM月dd日*/
    public static final DateFormat MD_FORMAT = new SimpleDateFormat("MM月dd日", Locale.getDefault());
    /*** yyyy年MM月dd日 HH:mm:ss*/
    public static final DateFormat YMD_TIME_FORMAT = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.getDefault());
    /*** yyyy年MM月dd日 HH:mm*/
    public static final DateFormat YMD_HM_FORMAT = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());
    /*** HH:mm:ss*/
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    /*** HH时mm分ss秒*/
    public static final DateFormat HMS_FORMAT = new SimpleDateFormat("HH时mm分ss秒", Locale.getDefault());
    /*** yyyy-MM-dd-HH-mm-ss*/
    private static final DateFormat PRIVATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
    /*** yyyy/MM/dd HH:mm*/
    public static final DateFormat YMD_HM_FORMAT_2 = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
    /*** mm分ss秒*/
    public static final DateFormat TIME_FORMAT2 = new SimpleDateFormat("mm分ss秒", Locale.getDefault());

    /*** yyyy/MM*/
    public static final DateFormat YMD_FORMAT2 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

    private DateUtils() {
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param date Date类型时间
     * @return 时间字符串
     */
    public static String date2String(final Date date) {
        return date2String(date, DEFAULT_FORMAT);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为format</p>
     *
     * @param date   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(final Date date, final DateFormat format) {
        return format.format(date);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, DEFAULT_FORMAT);
    }

    /**
     * 将时间戳转为TimeBean
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static TimeBean millis2Time(final long millis) {
        String[] strings = millis2String(millis, PRIVATE_FORMAT).split("-");
        return TimeBean.init(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5]);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为format</p>
     *
     * @param millis 毫秒时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String millis2String(final long millis, final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, DEFAULT_FORMAT);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time, final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            LoggerUtils.e(e.getMessage());
        }
        return -1;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(final String time) {
        return string2Date(time, DEFAULT_FORMAT);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            LoggerUtils.e(e.getMessage());
        }
        return null;
    }

    /**
     * 将时间字符串转为另一种格式时间字符串类型
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return String
     */
    public static String string2String(final String time, final DateFormat format) {
        return date2String(string2Date(time), format);
    }

    /**
     * 获取当前时间戳
     *
     * @return long
     */
    public static long currentTime() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        return date.getTime();
    }

    /**
     * 将时间转化为（00天 00时00分00秒）
     *
     * @param date 要计算的时间 毫秒
     * @return
     */
    public static String formatLongToTimeStr(Long date) {
        long day = date / (1000 * 60 * 60 * 24);
        if (day < 0) day = 0;
        long hour = (date / (1000 * 60 * 60)) - (day * 24);
        if (hour < 0) hour = 0;
        long min = (date / (1000 * 60)) - (day * 24 * 60) - (hour * 60);
        if (min < 0) min = 0;
        long s = (date / 1000) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (min * 60);
        if (s < 0) s = 0;
        return zeroPadding(day) + "天 " + zeroPadding(hour) + "小时" + zeroPadding(min) + "分" + zeroPadding(s) + "秒";
    }

    /**
     * 将时间转化为 TimeBean
     *
     * @param date 要计算的时间 毫秒
     * @return
     */
    public static TimeBean formatLongToTime(Long date) {
        long day = date / (1000 * 60 * 60 * 24);
        if (day < 0) day = 0;
        long hour = (date / (1000 * 60 * 60)) - (day * 24);
        if (hour < 0) hour = 0;
        long min = (date / (1000 * 60)) - (day * 24 * 60) - (hour * 60);
        if (min < 0) min = 0;
        long s = (date / 1000) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (min * 60);
        if (s < 0) s = 0;
        long milliseconds = date - (day * 24 * 60 * 60 * 1000) - (hour * 60 * 60 * 1000) - (min * 60 * 1000) - (s * 1000);
        if (milliseconds < 0) milliseconds = 0;
        return TimeBean.init("00", "00", zeroPadding(day), zeroPadding(hour), zeroPadding(min), zeroPadding(s), zeroPadding(milliseconds));
    }

    /**
     * 补零
     */
    public static String zeroPadding(long number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return number + "";
        }
    }
}
