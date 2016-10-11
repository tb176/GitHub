/**
 * File: DateHelper.java
 * Description: 日期的辅助类, 定义日期的输出格式, 日期的运算等内容
 * Copyright 2010 mo9. All rights reserved
 *  
 */
package com.mo9.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * 提供字符串到日期,
 * 日期到字符串的处理
 * @author jacky
 *
 */
public class DateHelper {

	private static Logger logger = Logger.getLogger(DateHelper.class);
	
	/**国际标准时间*/
	public static  final String TIME_ZONE_GMT="GMT";
	/**美国东部时间夏令时.(西四区)*/
	public static final String TIME_ZONE_EDT="America/New_York";
	/**北京时间.(东八区)*/
	public static final String TIME_ZONE_CST="Asia/Shanghai";
	
	/**
	 * 日期的显示格式是年-月-日
	 * 如2010-12-31
	 */
	public static final String YEAR_MONTH_DAY ="yyyy-MM-dd";
	

		
	
	
	/**
	 * 日期的显示格式是 年-月-日  小时:分钟:秒
	 * 如 2010-12-31  13:24:34
	 * 小时是24小时制的
	 */
	public static final String YEAR_MONTH_DAY_H24_MIN_SEC  = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 日期的显示格式是 年-月-日  小时:分钟:秒
	 * 如 2010-12-31  13:24:34
	 * 小时是24小时制的
	 */
	public static final String TIMESTAMP_STYLE  = "yyyyMMddHHmmss";
	
	
	/**
	 * 
	 */
	public static final String YEAR_MONTH_DAY_H24_MIN = "yyyy-MM-dd HH:mm";
	
	
	
	public static final String YEAR_MONTH_DAY_H24_MIN_DE_HH="HH:mm";
	
	public static final String YEAR_MONTH_DAY_H24_MIN_DE_YY="dd-MM-yyyy";
	
	/**
	 * 日期的显示格式是 年-月-日  小时:分钟:秒 AM/PM
	 * 如 2010-12-31  3:24:34 下午
	 * 小时是12小时制的
	 * a 表示AM/PM
	 */
	public static final String YEAR_MONTH_DAY_H12_MIN_SEC_AP  = "yyyy-MM-dd hh:mm:ss a";
	
	/**
	 *  日期显示的格式是年-月-日 小时:分钟:秒 星期几
	 *  如2010-12-31 13:24:32 星期五 
	 */
	public static final String YEAR_MONTH_DAY_H24_MIN_SEC_E = "yyyy-MM-dd HH:mm:ss E";
	
	/**
	 * 显示小时:分钟:秒
	 * 如13:24:32
	 * 小时是24小时制的
	 */
	public static final String H24_MIN_SEC = "HH:mm:ss";
	
	/**
	 * 显示小时:分钟:秒  星期几
	 * 如13:24:32 星期五
	 * 小时是24小时制
	 */
	public static final String H24_MIN_SEC_E = "HH:mm:ss E";
	
	
	/**
	 * 显示小时:分钟:秒 上午/下午
	 * 如3:25:35 下午
	 * 小时是12小时制的
	 */
	public static final String H12_MIN_SEC_AP ="hh:mm:ss a";
	
	/**
	 * 显示小时:分钟:秒 上午/下午  星期几
	 * 如3:25:35 下午 星期五
	 * 小时是12小时制
	 */
	public static final String H12_MIN_SEC_AP_E = "hh:mm:ss a E";
	
	public static final String H24 = "HH";
	
	/**
	 * 显示小时:分钟
	 * 如13:24
	 * 小时是24小时制的
	 */
	public static final String H24_MIN = "HH:mm";
	
	/**
	 * 显示小时:分钟  星期几
	 * 如13:24 星期五
	 * 小时是24小时制
	 */
	public static final String H24_MIN_E = "HH:mm E";
	
	
	/**
	 * 显示小时:分钟 上午/下午
	 * 如3:25 下午
	 * 小时是12小时制的
	 */
	public static final String H12_MIN_AP ="hh:mm a";
	
	/**
	 * 显示小时:分钟 上午/下午  星期几
	 * 如3:25 下午 星期五
	 * 小时是12小时制
	 */
	public static final String H12_MIN_AP_E = "hh:mm a E";
	
	private DateHelper(){		
	}	
	
	/**
	 * 将日期按照指定的显示风格, 转换成字符串
	 * @param date 传入的日期
	 * @param style 输出的风格 
	 * @return 指定风格的字符串
	 */
	public static String convertToString(Date date, String style){
		
		SimpleDateFormat df = new SimpleDateFormat(style);			
		return df.format(date);
	}	
	
	/**
	 * 将日期按照指定的显示风格,指定的时区， 转换成字符串
	 * @param date 传入的日期
	 * @param style 输出的风格 
	 * @return 指定风格的字符串
	 */
	public static String convertToString(Date date, String style,TimeZone tz){
		SimpleDateFormat df = new SimpleDateFormat(style);			
		df.setTimeZone(tz);
		return df.format(date);
	}
	

	/**
	 * 将字符串按照定制的风格, 转换成日期
	 * @param strdate 传入的字符串
	 * @param style   指定的日期风格
	 * @return        被转换后的日期
	 */
	public static Date convertToDate(String strdate, String style){
		
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat(style);	
		
		try{

			date = df.parse(strdate);

		}catch(ParseException pe){
			logger.info("字符串的传入参数不能被转换成指定的格式", pe);
			throw new RuntimeException(pe);
		}
		return date;
	}
	
	/**
	 * 将字符串按照定制的风格, 转换成日期
	 * @param strdate 传入的字符串
	 * @param style   指定的日期风格
	 * @return        被转换后的日期
	 */
	public static Date convertToDate(String strdate, String style,TimeZone tz){
		
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat(style);
		df.setTimeZone(tz);
		
		try{

			date = df.parse(strdate);

		}catch(ParseException pe){
			throw new RuntimeException(pe);
		}
		return date;
	}
	public static int getYear(String startTime){
		int year=0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		try{
			year=sdf.parse(startTime).getYear()+1900;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return year;
	}
	public static int getMonth(String startTime){
		int year=0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		try{
			year=sdf.parse(startTime).getMonth()+1;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return year;
	}
	
	/**
	 * 获取两个时间点之间的时差
	 * @Title: getTimeLag
	 * @Description: TODO
	 * @param start
	 * @param end
	 * @param type
	 * @return 返回指定时间单位的start-end.
	 * @return: int
	 */
	public static int getTimeLagAsMinute(Date start,Date end)
	{
		return Math.abs((int)((start.getTime()-end.getTime())/(1000*60)));
	}
	
	/**
	 * 返回以周为单位的时间差.
	 * TODO
	 * @param start
	 * @param end
	 * @return
	 * @return: int
	 */
	public static int getTimeLagAsWeek(Date start,Date end)
	{
		return Math.abs((int)((start.getTime()-end.getTime())/(1000*60*60*24*7)));
	}
	
	/**
     * 返回以天单位的时间差.
     * TODO
     * @param start
     * @param end
     * @return
     * @return: int
     */
    public static int getTimeLagAsDay(Date start,Date end)
    {
        return Math.abs((int)((start.getTime()-end.getTime())/(1000*60*60*24)));
    }
	
	
	/**将日期延迟到当天23:59:59秒.*/
	public static final Date delay2DayEnding(Date time)
	{
	   return convertToDate(convertToString(time, YEAR_MONTH_DAY)+" 23:59:59", YEAR_MONTH_DAY_H24_MIN_SEC);
	}
	
	/**将日期迁移到当天开始.*/
    public static final Date pre2DayStarting(Date time)
    {
       return convertToDate(convertToString(time, YEAR_MONTH_DAY), YEAR_MONTH_DAY);
    }
	
    /**加天数*/
	public static final Date addDay(Date time,int day)
	{
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(time);
	    calendar.add(Calendar.DAY_OF_YEAR, day);
	    return calendar.getTime();
	}
	
	
	public static final int getWeekDay(Date time)
	{
	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        return dayOfWeek;
	}
	
	
	public static void main(String[] args){
		Date d1 = DateHelper.convertToDate("2013-12-01", DateHelper.YEAR_MONTH_DAY);
		Date d2 = DateHelper.convertToDate("2013-12-29", DateHelper.YEAR_MONTH_DAY);
		System.out.print(DateHelper.getTimeLagAsDay(d1, d2));
		
	}
	
}
