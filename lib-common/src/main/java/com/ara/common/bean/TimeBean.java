package com.ara.common.bean;

/**
 * Created by XieXin on 2019/1/25.
 * 时间 实体类
 */
public class TimeBean {
    /*** 年 */
    private String year;
    /*** 月 */
    private String month;
    /*** 日 */
    private String day;
    /*** 时 */
    private String hour;
    /*** 分 */
    private String minute;
    /*** 秒 */
    private String second;
    /*** 毫秒 */
    private String milliseconds;

    public TimeBean(String year, String month, String day, String hour, String minute, String second, String milliseconds) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.milliseconds = milliseconds;
    }

    public static TimeBean init(String year, String month, String day, String hour, String minute, String second) {
        return new TimeBean(year, month, day, hour, minute, second, "0");
    }

    public static TimeBean init(String year, String month, String day, String hour, String minute, String second, String milliseconds) {
        return new TimeBean(year, month, day, hour, minute, second, milliseconds);
    }

    public String getYear() {
        return year == null ? "" : year;
    }

    public String getMonth() {
        return month == null ? "" : month;
    }

    public String getDay() {
        return day == null ? "" : day;
    }

    public String getHour() {
        return hour == null ? "" : hour;
    }

    public String getMinute() {
        return minute == null ? "" : minute;
    }

    public String getSecond() {
        return second == null ? "" : second;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getMilliseconds() {
        if (milliseconds == null) setMilliseconds("");
        return milliseconds;
    }

    public void setMilliseconds(String milliseconds) {
        this.milliseconds = milliseconds;
    }
}
