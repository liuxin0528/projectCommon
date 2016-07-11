/*
 * @(#)DateUtil.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *	日期		:	2016年1月11日<br>
 *	作者		:	liuxin<br>
 *	项目		:	zehinCommon<br>
 *	功能		:	日期工具类<br>
 */
public class DateUtil {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 
	 * Description : 获取系统 Date
	 * @return Date
	 */
	public static Date getSysDate() {
		return new Date();
	}
	
	/**
	 * 
	 * Description : 获取系统 Calendar
	 * @return Calendar
	 */
	public static Calendar getSysCalendar() {
		Calendar currCalendar = Calendar.getInstance();
		return currCalendar;
	}
	
	/**
	 * 
	 * Description : 系统当前 Date -- yyyy-MM-dd
	 * @return
	 */
	public static String getCurrDate() {
		return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
	}
	
	/**
	 *  
	 * Description : 获取前一天日期
	 * @param format：格式
	 * @return
	 */
	public static String getYestoday(String format){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return format(calendar, format);
	}
	
	/**
	 * 
	 * Description : 系统当前 Date -- yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrTime() {
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
	}
	
	/**
	 * 
	 * Description : String2Date
	 * @param s
	 * @return
	 */
	public static Date parseDate(String s) {
		Date d = null;
		try {
			if (s.length() <= 10) {
				d = sdf.parse(s);
			} else {
				d = sdf2.parse(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * 
	 * Description : String2Calendar
	 * @param s
	 * @return
	 */
	public static Calendar parseCalendar(String s) {
		Calendar c = null;
		Date d = parseDate(s);
		if (d != null) {
			c = Calendar.getInstance();
			c.setTime(d);
		}
		return c;
	}
	
	/**
	 * 
	 * Description : Date2String
	 * @param d
	 * @return
	 */
	public static String toString(Date d) {
		String s = null;
		try {
			s = sdf.format(d);
		} catch (Exception e) {
		}
		return s;
	}
	
	/**
	 * 
	 * Description : Date 格式化  yyyy-MM-dd 
	 * @param d
	 * @return
	 */
	public static String format(Date d) {
		return format(d, "yyyy-MM-dd");
	}
	
	/**
	 * 
	 * Description : Calendar 格式化 yyyy-MM-dd
	 * @param c
	 * @return
	 */
	public static String format(Calendar c) {
		return format(c == null ? (Date) null : c.getTime());
	}
	
	/**
	 * 
	 * Description : Date 格式化 yyyy-MM-dd HH:mm:ss
	 * @param c
	 * @return
	 */
	public static String formatTime(Date d) {
		return format(d == null ? (Date) null : d, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 
	 * Description : Calendar 格式化 yyyy-MM-dd HH:mm:ss
	 * @param c
	 * @return
	 */
	public static String formatTime(Calendar c) {
		return format(c == null ? (Date) null : c.getTime(), "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 
	 * Description : Date 格式化 自定义格式
	 * @param d
	 * @param format yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String format(Date d, String format) {
		if (d == null) {
			return null;
		}
		SimpleDateFormat sdformat = new SimpleDateFormat(format);
		return sdformat.format(d);
	}
	
	/**
	 * 
	 * Description : 转换日期和时间为指定格式的字符串 自定义格式
	 * @param c
	 * @param format
	 * @return
	 */
	public static String format(Calendar c, String format) {
		return format(c == null ? (Date) null : c.getTime(), format);
	}
	
	/**
	 * 
	 * Description : 日期date上加count天，count为负表示减
	 * @param date
	 * @param count
	 * @return
	 */
	public static Date addDay(Date date, int count) {
		return new Date(date.getTime() + 86400000L * count);
	}
	
	/**
	 * 
	 * Description : 日期calendar上加count天，count为负表示减
	 * @param calendar
	 * @param count
	 * @return
	 */
	public static Calendar addDay(Calendar calendar,int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(calendar.getTime());
		c.add(Calendar.DAY_OF_MONTH, count);
		return c;
	}
	
	/**
	 * 
	 * Description : 日期calendar上加count月，count为负表示减
	 * @param calendar
	 * @param count
	 * @return
	 */
	public static Calendar addMonth(Calendar calendar,int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(calendar.getTime());
		c.add(Calendar.MONDAY, count);
		return c;
	}

	/**
	 * 
	 * Description : 获取当天的 00：00：00
	 * @param c
	 * @return
	 */
	public static Calendar getCalendarStartTime(Calendar c){
		return parseCalendar(format(c, "yyyy-MM-dd 00:00:00"));
	}
	
	/**
	 * 
	 * Description : 获取当天的 23：59：59
	 * @param c
	 * @return
	 */
	public static Calendar getCalendarEndTime(Calendar c){
		return parseCalendar(format(c, "yyyy-MM-dd 23:59:59"));
	}
	
	/**
	 * 
	 * Description : 获取Date开始时间
	 * @param date
	 * @return
	 */
	public static Calendar getDateStartTime(Date date){
		return parseCalendar(format(date, "yyyy-MM-dd 00:00:00"));
	}
	
	/**
	 * 
	 * Description : 获取Date结束时间
	 * @param date
	 * @return
	 */
	public static Calendar getDateEndTime(Date date){
		return parseCalendar(format(date, "yyyy-MM-dd 23:59:59"));
	}
	
	 /**
	  * 
	  * Description : 获取当前周的第一天
	  * @return
	  */
    public static String getCurrentWeekFirstDay() {
        Calendar c = Calendar.getInstance();
        String now = null;
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            now = format(c,"yyyy-MM-dd 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    
    /**
     * 
     * Description : 获取当前月的第一天
     * @return
     */
    public static String getCurrentMonthFirstDay() {
    	Calendar cal=Calendar.getInstance();//获取当前日期 
		cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		cal.add(Calendar.MONTH,0);
		return format(cal,"yyyy-MM-dd 00:00:00");
    }
    
    /**
     * 
     * Description : 当前季度的第一天，即2012-01-1 00:00:00
     * @return
     */
    public static String getCurrentQuarterFirstDay() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            
            now = format(c,"yyyy-MM-dd 00:00:00");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    
   /**
    * 
    * Description : 获取当前年的第一天
    * @return
    */
    public static String getCurrentYearFirstDay() {
    	Calendar cal=Calendar.getInstance();//获取当前日期 
		cal.set(Calendar.DAY_OF_YEAR,1);//设置为1号,当前日期既为本年第一天 
		cal.add(Calendar.YEAR,0);
		return format(cal,"yyyy-MM-dd 00:00:00");
    }
    
    /**
     * 
     * Description : 获取当前日期 为当年的第几周
     * @param currentDate
     * @return
     */
    public static int getCurrentDateWeekInYear(String currentDate){
    	  Calendar calendar = Calendar.getInstance();
    	  calendar.setFirstDayOfWeek(Calendar.MONDAY);
    	  Date date = parseDate(currentDate);
    	  calendar.setTime(date);
    	  return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 
     * Description : 获取星期
     * @param c
     * @return
     */
    public static String getDayOfWeek(final Calendar c) {
    	String str = "";
		if (c == null)
			return str;
		int day = c.get(Calendar.DAY_OF_WEEK);
		switch (day) {
		case Calendar.SUNDAY:
			str = "日";
			break;
		case Calendar.MONDAY:
			str = "一";
			break;
		case Calendar.TUESDAY:
			str = "二";
			break;
		case Calendar.WEDNESDAY:
			str = "三";
			break;
		case Calendar.THURSDAY:
			str = "四";
			break;
		case Calendar.FRIDAY:
			str = "五";
			break;
		case Calendar.SATURDAY:
			str = "六";
			break;
		default:
			str = "";
		}
		return str;
	}
    
    /**
     * 
     * Description : 检查字符串是否为指定的日期类型
     * @param value
     * @param fromatReg 格式正则表达式
     * @return
     */
    public static boolean isDateFormat(Object value, String fromatReg){
        Pattern pattern = Pattern.compile(fromatReg);
        if (ValidationUtil.isNULL(value) || !pattern.matcher(value.toString()).matches()) {
        	return false;
        }else{
        	return true;
        }
    }
    
    /**
     * 
     * Description : 检查字符串是否为日期
     * @param value
     * @return
     */
    public static boolean isDate(Object value){
        return isDateFormat(value, "^\\d{4}-\\d{1,2}-\\d{1,2}$");
    }
    
    /**
     * 
     * Description : 检查字符串是否为日期时间类型
     * @param value
     * @return
     */
    public static boolean isDateTime(Object value){
        return isDateFormat(value, "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");
    }
    
    /**
     * 
     * Description : Long转为日期，结果为String
     * @param value
     * @param format yyyy-MM-dd
     * @return
     */
    public static String longToDate(Long value, String format){
    	 try{
    		 DateFormat df =  new SimpleDateFormat(format);;
    		 Date dt = new Date(value);  
             return df.format(dt);
         }catch (Exception e){
        	 e.printStackTrace();
         }
         return null;
    }
}
