package org.gxfj.iknow.util;

import java.util.Date;

public class Time {
    final static private int QUESTION_STATE_UNSOLVE = 1;
    final static private int QUESTION_SCENARIO_STUDENT = 1;
    final static private int MILLIS_PER_YEAR = 366*24*60*60;
    final static private int MILLIS_PER_MONTH = 30*24*60*60;
    final static private int MILLIS_PER_DAY = 24*60*60;
    final static private int MILLIS_PER_HOUR = 60*60;
    final static private int MILLIS_PER_MINUTE = 60;

    public static String getTime(Date m){
        long ms = m.getTime();
        long second,minutes, hours, days,month,years;
        long timeNow = System.currentTimeMillis();
        long d = (timeNow - ms)/1000;
        years = Math.round(d / MILLIS_PER_YEAR);
        month = Math.round(d / MILLIS_PER_MONTH);
        days = Math.round(d / MILLIS_PER_DAY);
        hours = Math.round(d / MILLIS_PER_HOUR);
        minutes = Math.round(d / MILLIS_PER_MINUTE);
        second = Math.round(d);
        if (years > 0) {
            return years + "年前";
        } else if (month > 0 && years == 0) {
            return days + "月前";
        } else if (days > 0 && month == 0) {
            return days + "天前";
        } else if (hours > 0 && days == 0) {
            return hours + "小时前";
        } else if (minutes > 0 && hours == 0) {
            return minutes + "分钟前";
        } else if (second >= 0 && minutes == 0) {
            return "刚刚";
        } else {
            return ("数据库时间超过了当前时间！！");
        }

    }
}
