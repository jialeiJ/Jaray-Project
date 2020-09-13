package com.vienna.jaray.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author Administrator
 * @date 2020年09月12日 13:58
 * @description: 日期时间工具类
 */
public class DateTimeUtil {
	public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_COMPACT = "yyyyMMdd";
    public static final String DATE_FORMAT_COMPACTFULL = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_FULL_MSEL = "yyyyMMddHHmmssSSSS";
    public static final String DATE_YEAR_MONTH = "yyyyMM";
    public static final String DATE_FORMAT_FULL_MSE = "yyyyMMddHHmmssSSS";

    /**
     * 获取系统当前日期
     * @return 系统当前日期
     */
    public static Date getCurrentDate() {
        return new Date();
    }
    
    /**
     * 获取系统当前日期
     *
     * @return 系统当前日期
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }
    
    /**
     * 根据时间格式返回对应的String类型的时间
     *
     * @param format 时间格式
     * @return String类型的时间
     */
    public static String getCurDateTime(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return now.format(dateTimeFormatter);
    }
    
    /**
     * 得到当前日期
     *
     * @return String 当前日期 yyyy-MM-dd HH:mm:ss格式
     */
    public static String getCurDateTimeFull() {
        return getCurDateTime(DATE_FORMAT_FULL);
    }
    
    /**
     * 得到当前日期
     *
     * @return String 当前日期 yyyyMMddHHmmss格式
     */
    public static String getCurDateTime1() {
        return getCurDateTime(DATE_FORMAT_FULL);
    }
    
    /**
     * 得到当前日期YYYYMM格式
     *
     * @return String 当前日期 yyyyMM格式
     */
    public static String getCurDateYyyyMm() {
        return getCurDateTime(DATE_YEAR_MONTH);
    }
    
    /**
     * 得到当前日期
     *
     * @return String 当前日期 yyyyMMdd格式
     */
    public static String getCurDateYyyyMmDd() {
        return getCurDateTime(DATE_FORMAT_COMPACT);
    }
    
    /**
     * 判断是否是今天
     *
     * @param strDate String类型时间
     * @return 是否是今天
     */
    public static boolean isCurrentDay(String strDate) {
        boolean bRet = false;
        LocalDate strLocalDate = LocalDate.parse(strDate);
        if (LocalDate.now().getYear() == strLocalDate.getYear()) {
            MonthDay monthDay = MonthDay.from(strLocalDate);
            MonthDay today = MonthDay.from(LocalDate.now());
            return monthDay.equals(today);
        }
        return bRet;
    }
    
    /**
     * 获取几小时后的时间
     *
     * @param hour 小时
     * @param format 时间格式
     * @return 几小时后的时间
     */
    public static String getAfterDateTime(int hour, String format) {
        LocalTime localTime = LocalTime.now().plusHours(hour);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localTime.format(dateTimeFormatter);
    }
    
    /**
     * 获取当前日期时间戳(yyyyMMddHHmmssSSSS)
     *
     * @return 当前日期时间戳
     */
    public static String getTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_FULL_MSEL);
        return now.format(dateTimeFormatter);
    }

    /**
     * 日期转字符串
     *
     * @param thedate Date类型时间
     * @param format 时间格式
     * @return String类型时间
     */
    public static String parseDateToString(Date thedate, String format) {
        if (thedate != null) {
            Instant instant = thedate.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            return localDateTime.format(dateTimeFormatter);
        }
        return null;
    }
    
    /**
     * parseDateToString(Date thedate, String format)的重载方法
     *
     * @param thedate Date类型时间
     * @return String类型时间
     */
    public static String parseDateToString(Date thedate) {
        return parseDateToString(thedate, DATE_FORMAT_FULL);
    }

    /**
     * 字符串转日期
     *
     * @param thedate Date类型时间
     * @param format 时间格式
     * @return Date类型时间
     */
    public static Date parseStringToDate(String thedate, String format) {
        DateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
        	date = sdf.parse(thedate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * String类型日期转成format形式String
     *
     * @param srcFormat 源时间格式
     * @param tarFormat 目标时间格式
     * @param strDate String类型时间
     * @return String类型时间
     */
    public static String changeFormatDateString(String srcFormat, String tarFormat, String strDate) {
        if (strDate == null) {
            return "";
        }
        if (strDate.length() >= srcFormat.length() && srcFormat.length() >= tarFormat.length()) {
            return parseDateToString(parseStringToDate(strDate, srcFormat), tarFormat);
        }
        return strDate;
    }
    
    /**
     * 得到当前日期的前N天时间 yyyymmdd
     *
     * @param format 时间格式
     * @param day 天数
     * @return 当前日期的前N天时间 yyyymmdd
     */
    public static String beforeNdaysDate(String format, int day) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        if (day > 0) {
            return LocalDateTime.now().minusDays(day).format(dateTimeFormatter);
        }
        return null;
    }

    /**
     * 得到时间的N分钟后的时间
     * @param theDate String类型时间
     * @param nMinNum 分钟数
     * @param format 时间格式
     * @return N分钟后的时间
     */
    public static String beforeNminsDate(String theDate, Integer nMinNum, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(theDate,dateTimeFormatter).minusMinutes(nMinNum).format(dateTimeFormatter);
    }

    /**
     * 获得N个月后的日期
     *
     * @param theDate String类型时间
     * @param month 月数
     * @param format 时间格式
     * @return N个月后的日期
     */
    public static String afterNmonthDate(String theDate, int month, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(theDate,dateTimeFormatter)
                .plusMonths(month)
                .format(dateTimeFormatter);
    }

    /**
     *
     * @param theDate String类型时间,格式 yyyy-MM-dd
     * @param nDayNum 天数
     * @param format 时间格式
     * @return N天后的日期
     */
    public static String afterNdaysDate(String theDate, Integer nDayNum, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(theDate,dateTimeFormatter)
                .plusDays(nDayNum)
                .format(dateTimeFormatter);
    }
    
    /**
     * 得到N小时后的日期
     *
     * @param theDate String类型时间
     * @param nHourNum 小时数
     * @param format 时间格式
     * @return N小时后的日期
     */
    public static String afterNhoursDate(String theDate, Integer nHourNum, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(theDate,dateTimeFormatter)
                .plusHours(nHourNum)
                .format(dateTimeFormatter);
    }
    
    /**
     * 得到N分钟后的日期
     *
     * @param theDate String类型时间
     * @param nMinNum 分钟数
     * @param format 时间格式
     * @return N分钟后的日期
     */
    public static String afterNminsDate(String theDate, Integer nMinNum, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(theDate,dateTimeFormatter)
                .plusMinutes(nMinNum)
                .format(dateTimeFormatter);
    }
    
    /**
     * 得到N秒后的日期
     * 
     * @param theDate String类型时间
     * @param nSecNum 秒数
     * @param format 时间格式
     * @return N秒后的日期
     */
    public static String afterNsecondsDate(String theDate, Integer nSecNum, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(theDate,dateTimeFormatter)
                .plusSeconds(nSecNum)
                .format(dateTimeFormatter);
    }
    
    /**
     * 比较两个字符串格式日期大小,带格式的日期
     * 
     * @param srcDate 源时间
     * @param tarDate 目标时间
     * @param format 时间格式
     * @return 两个字符串格式日期大小
     */
    public static boolean isBefore(String srcDate, String tarDate, String format) {
        try {
            Date dat1 = parseStringToDate(srcDate, format);
            Date dat2 = parseStringToDate(tarDate, format);
            return dat1.before(dat2);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 比较两个字符串格式日期大小,带格式的日期,返回long
     * 
     * @param srcDate 源时间
     * @param tarDate 目标时间
     * @param format 时间格式
     * @return 两个字符串格式日期大小
     */
    public static long isBeforeInt(String srcDate, String tarDate, String format) {
        long result = 0;
        try {
            Date dat1 = parseStringToDate(srcDate, format);
            Date dat2 = parseStringToDate(tarDate, format);
            return dat2.getTime() - dat1.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 得到上一个月或者下一个月的日期
     * 
     * @param theDate String类型时间
     * @param month 月份
     * @param formatStr 时间格式
     * @return 上一个月或者下一个月的日期
     */
    public static String getDayafterMonth(String theDate, int month, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
        return LocalDateTime.parse(theDate).plusMonths(month).format(dateTimeFormatter);
    }
    
    /**
     * 将秒转换为小时分秒等
     *
     * @param sec 秒数
     * @return 小时分秒
     */
    public String changeTime(int sec) {
        String temp = "";
        int second = 60;
        int hour = 3600;
        if (sec < second) {
            temp = "" + sec + "秒";
        } else if (sec < hour) {
            temp = "" + sec / second + "分" + sec % second + "秒";
        } else {
            temp = "" + sec / hour + "小时" + (sec % hour) / second + "分" + sec % second + "秒";
        }
        return temp;
    }
    
    /**
     * 计算两个日期相差天数
     *
     * @param end   结束日期
     * @param start 开始日期
     */
    public static int getSubDays(String end, String start) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        long between = ChronoUnit.DAYS.between(startDate, endDate);
        return (int) between;
    }
    
    public static String getTimeDiff(Date endTime, Date startTime) {
        long l = endTime.getTime() - startTime.getTime();
        String returnStr = "";
        long day = l / (24 * 60 * 60 * 1000);
        if (day > 0) {
            returnStr += (day + "天");
        }
        long hour = (l / (60 * 60 * 1000) - day * 24);
        if (hour > 0) {
            returnStr += (hour + "小时");
        }
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        if (min > 0) {
            returnStr += (min + "分");
        }
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (s > 0) {
            returnStr += (s + "秒");
        }
        return returnStr;
    }
}
