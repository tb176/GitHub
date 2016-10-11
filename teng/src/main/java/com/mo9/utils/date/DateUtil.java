package com.mo9.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
/**
 * <p>The Super Java Framework .</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * @author Steven
 * @version 1.0
 */

public class DateUtil{
	
  public static final String ymd ="yyyy-MM-dd";	
	
	
 private static final int[] dayArray = new int[]
  { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
 private static SimpleDateFormat sdf = new SimpleDateFormat();

 public static synchronized Calendar getCalendar()
 {
  return GregorianCalendar.getInstance();
 }

 /**
  @return String
  */
 public static synchronized String getDateMilliFormat()
 {
  Calendar cal = Calendar.getInstance();
  return getDateMilliFormat( cal );
 }

 /**
  @param cal
  @return String
  */
 public static synchronized String getDateMilliFormat( java.util.Calendar cal )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
  return getDateFormat( cal, pattern );
 }
 
 /**
  * <li>功能描述：时间相减得到天数
  * @param beginDateStr
  * @param endDateStr
  * @return
  * long
  * @author Administrator
  */
 public static long getDaySub(String beginDateStr,String endDateStr)
 {
     long day=0;
     java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");   
     java.util.Date beginDate;
     java.util.Date endDate;
     try
     {
         beginDate = format.parse(beginDateStr);
         endDate= format.parse(endDateStr);   
         day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);   
         //System.out.println("相隔的天数="+day);  
     } catch (ParseException e)
     {
         e.printStackTrace();
         throw new RuntimeException(e);
     }  
     return day;
 }

 /**
  @param date
  @return String
  */
 public static synchronized String getDateMilliFormat( java.util.Date date )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
  return getDateFormat( date, pattern );
 }

 /**
  @param date
  @return 格式如 Fri Aug 05 2016 23:21:14
  */
 public static synchronized String getDateUsLocaleFormat( java.util.Date date )
 {
  String pattern = "EEE MMM dd yyyy HH:mm:ss";
  DateFormat df = new SimpleDateFormat(pattern, Locale.US);
  return toString( date, df );
 }

 /**
  @param date
  @return String
  */
 public static synchronized String getDateMonthChineseFormat( java.util.Date date )
 {
  String pattern = "yyyy年MM月";
  return getDateFormat( date, pattern );
 }

 /**
 @param date
 @return String
 */
	public static synchronized String getDateMonthNormalFormat( java.util.Date date )
	{
	 String pattern = "yyyyMM";
	 return getDateFormat( date, pattern );
	}

 /**
  @param date
  @return String 
  */
 public static synchronized java.util.Date parseDateChinese( String date )
 {
  String pattern = "yyyy年MM月dd日";
  return parseDateFormat(date, pattern );
 }
 
 /**
 @param date
 @return String 
 */
public static synchronized java.util.Date parseDateNormal( String date )
{
 String pattern = "yyyyMMdd";
 return parseDateFormat(date, pattern );
}

 /**
 @param date
 @return String 
 */
 public static synchronized java.util.Date parseDateChineseFormat( String date )
 {
  String pattern = "yyyy年MM月dd日HH时mm分ss秒";
  return parseDateFormat(date, pattern );
 }
 
 /**
  @param strDate
  @return java.util.Calendar
  */
 public static synchronized Calendar parseCalendarMilliFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
  return parseCalendarFormat( strDate, pattern );
 }

 /**
  @param strDate
  @return java.util.Date
  */
 public static synchronized Date parseDateMilliFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
  return parseDateFormat( strDate, pattern );
 }

 /**
  @return String
  */
 public static synchronized String getDateSecondFormat()
 {
  Calendar cal = Calendar.getInstance();
  return getDateSecondFormat( cal );
 }

 /**
  @param cal
  @return String
  */
 public static synchronized String getDateSecondFormat( java.util.Calendar cal )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss";
  return getDateFormat( cal, pattern );
 }

 /**
  @param date
  @return String
  */
 public static synchronized String getDateSecondFormat( java.util.Date date )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss";
  return getDateFormat( date, pattern );
 }

 /**
  @param strDate
  @return java.util.Calendar
  */
 public static synchronized Calendar parseCalendarSecondFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss";
  return parseCalendarFormat( strDate, pattern );
 }

 /**
  @param strDate
  @return java.util.Date
  */
 public static synchronized Date parseDateSecondFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss";
  return parseDateFormat( strDate, pattern );
 }

 /**
  @return String
  */
 public static synchronized String getDateMinuteFormat()
 {
  Calendar cal = Calendar.getInstance();
  return getDateMinuteFormat( cal );
 }

 /**
  @param cal
  @return String
  */
 public static synchronized String getDateMinuteFormat( java.util.Calendar cal )
 {
  String pattern = "yyyy-MM-dd HH:mm";
  return getDateFormat( cal, pattern );
 }

 /**
  @param date
  @return String
  */
 public static synchronized String getDateMinuteFormat( java.util.Date date )
 {
  String pattern = "yyyy-MM-dd HH:mm";
  return getDateFormat( date, pattern );
 }

 /**
  @param strDate
  @return java.util.Calendar
  */
 public static synchronized Calendar parseCalendarMinuteFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd HH:mm";
  return parseCalendarFormat( strDate, pattern );
 }

 /**
  @param strDate
  @return java.util.Date
  */
 public static synchronized Date parseDateMinuteFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd HH:mm";
  return parseDateFormat( strDate, pattern );
 }
 /**
 @param patten
 @return java.lang.String
 */
 public static String getDate(String patten) {
     SimpleDateFormat sf = new SimpleDateFormat(patten,java.util.Locale.US);
     return sf.format(new Date());
 }
 /**
  @return String
  */
 public static synchronized String getDateDayFormat()
 {
  Calendar cal = Calendar.getInstance();
  return getDateDayFormat( cal );
 }

 /**
  @param cal
  @return String
  */
 public static synchronized String getDateDayFormat( java.util.Calendar cal )
 {
  String pattern = "yyyy-MM-dd";
  return getDateFormat( cal, pattern );
 }

 /**
  @param date
  @return String
  */
 public static synchronized String getDateDayFormat( java.util.Date date )
 {
  String pattern = "yyyy-MM-dd";
  return getDateFormat( date, pattern );
 }
 
 /**
 @param date
 @return String
 */
public static synchronized String getDateDayNormalFormat( java.util.Date date )
{
 String pattern = "yyyyMMdd";
 return getDateFormat( date, pattern );
}

 /**
  @param strDate
  @return java.util.Calendar
  */
 public static synchronized Calendar parseCalendarDayFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd";
  return parseCalendarFormat( strDate, pattern );
 }

 /**
  @param strDate
  @return java.util.Date
  */
 public static synchronized Date parseDateDayFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd";
  return parseDateFormat( strDate, pattern );
 }

 /**
  @return String
  */
 public static synchronized String getDateFileFormat()
 {
  Calendar cal = Calendar.getInstance();
  return getDateFileFormat( cal );
 }

 /**
  @param cal
  @return String
  */
 public static synchronized String getDateFileFormat( java.util.Calendar cal )
 {
  String pattern = "yyyy-MM-dd_HH-mm-ss";
  return getDateFormat( cal, pattern );
 }

 /**
  @param date
  @return String
  */
 public static synchronized String getDateFileFormat( java.util.Date date )
 {
  String pattern = "yyyy-MM-dd_HH-mm-ss";
  return getDateFormat( date, pattern );
 }

 /**
  @param strDate
  @return java.util.Calendar
  */
 public static synchronized Calendar parseCalendarFileFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd_HH-mm-ss";
  return parseCalendarFormat( strDate, pattern );
 }

 /**
  @param strDate
  @return java.util.Date
  */
 public static synchronized Date parseDateFileFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd_HH-mm-ss";
  return parseDateFormat( strDate, pattern );
 }

 /**
  @return String
  */
 public static synchronized String getDateW3CFormat()
 {
  Calendar cal = Calendar.getInstance();
  return getDateW3CFormat( cal );
 }

 /**
  @param cal
  @return String
  */
 public static synchronized String getDateW3CFormat( java.util.Calendar cal )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss";
  return getDateFormat( cal, pattern );
 }

 /**
  @param date
  @return String
  */
 public static synchronized String getDateW3CFormat( java.util.Date date )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss";
  return getDateFormat( date, pattern );
 }

 /**
  @param strDate
  @return java.util.Calendar
  */
 public static synchronized Calendar parseCalendarW3CFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss";
  return parseCalendarFormat( strDate, pattern );
 }

 /**
  @param strDate
  @return java.util.Date
  */
 public static synchronized Date parseDateW3CFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss";
  return parseDateFormat( strDate, pattern );
 }

 /**
  @param cal
  @return String
  */
 public static synchronized String getDateFormat( java.util.Calendar cal )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss";
  return getDateFormat( cal, pattern );
 }

 /**
  @param date
  @return String
  */
 public static synchronized String getDateFormat( java.util.Date date )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss";
  return getDateFormat( date, pattern );
 }

 /**
  @param strDate
  @return java.util.Calendar
  */
 public static synchronized Calendar parseCalendarFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss";
  return parseCalendarFormat( strDate, pattern );
 }

 /**
  @param strDate
  @return java.util.Date
  */
 public static synchronized Date parseDateFormat( String strDate )
 {
  String pattern = "yyyy-MM-dd HH:mm:ss";
  return parseDateFormat( strDate, pattern );
 }

 /**
  @param cal
  @param pattern
  @return String
  */
 public static synchronized String getDateFormat( java.util.Calendar cal, String pattern )
 {
  return getDateFormat( cal.getTime(), pattern );
 }

 /**
  @param date
  @param pattern
  @return String
  */
 public static synchronized String getDateFormat( java.util.Date date, String pattern )
 {
	if(date==null){
		return null;
	}
  synchronized ( sdf )
  {
   String str = null;
   sdf.applyPattern( pattern );
   str = sdf.format( date );
   return str;
  }
 }

 /**
  @param strDate
  @param pattern
  @return java.util.Calendar
  */
 public static synchronized Calendar parseCalendarFormat( String strDate, String pattern )
 {
  synchronized ( sdf )
  {
   Calendar cal = null;
   sdf.applyPattern( pattern );
   try
   {
    sdf.parse( strDate );
    cal = sdf.getCalendar();
   }
   catch ( Exception e )
   {
   }
   return cal;
  }
 }

 /**
  @param strDate
  @param pattern
  @return java.util.Date
  */
 public static synchronized Date parseDateFormat( String strDate, String pattern )
 {
  synchronized ( sdf )
  {
   Date date = null;
   sdf.applyPattern( pattern );
   try
   {
    date = sdf.parse( strDate );
   }
   catch ( Exception e )
   {
   }
   return date;
  }
 }

 public static synchronized int getLastDayOfMonth( int month )
 {
  if ( month < 1 || month > 12 )
  {
   return -1;
  }
  int retn = 0;
  if ( month == 2 )
  {
   if ( isLeapYear() )
   {
    retn = 29;
   }
   else
   {
    retn = dayArray[month - 1];
   }
  }
  else
  {
   retn = dayArray[month - 1];
  }
  return retn;
 }

 public static synchronized int getLastDayOfMonth( int year, int month )
 {
  if ( month < 1 || month > 12 )
  {
   return -1;
  }
  int retn = 0;
  if ( month == 2 )
  {
   if ( isLeapYear( year ) )
   {
    retn = 29;
   }
   else
   {
    retn = dayArray[month - 1];
   }
  }
  else
  {
   retn = dayArray[month - 1];
  }
  return retn;
 }

 public static synchronized boolean isLeapYear()
 {
  Calendar cal = Calendar.getInstance();
  int year = cal.get( Calendar.YEAR );
  return isLeapYear( year );
 }

 public static synchronized boolean isLeapYear( int year )
 {
  /**
   * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
   * 3.能被4整除同时能被100整除则不是闰年
   */
  if ( ( year % 400 ) == 0 )
   return true;
  else if ( ( year % 4 ) == 0 )
  {
   if ( ( year % 100 ) == 0 )
    return false;
   else return true;
  }
  else return false;
 }

 /**
  * 判断指定日期的年份是否是闰年
  *
  * @param date
  *            指定日期。
  * @return 是否闰年
  */
 public static synchronized boolean isLeapYear( java.util.Date date )
 {
  /**
   * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
   * 3.能被4整除同时能被100整除则不是闰年
   */
//  int year = date.getYear();
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  int year = gc.get( Calendar.YEAR );
  return isLeapYear( year );
 }

 public static synchronized boolean isLeapYear( java.util.Calendar gc )
 {
  /**
   * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
   * 3.能被4整除同时能被100整除则不是闰年
   */
  int year = gc.get( Calendar.YEAR );
  return isLeapYear( year );
 }

 /**
  * 得到指定日期的前一个工作日
  *
  * @param date
  *            指定日期。
  * @return 指定日期的前一个工作日
  */
 public static synchronized java.util.Date getPreviousWeekDay(
  java.util.Date date )
 {
  {
   /**
    * 详细设计：
    * 1.如果date是星期日，则减3天
    * 2.如果date是星期六，则减2天
    * 3.否则减1天
    */
   GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
   gc.setTime( date );
   return getPreviousWeekDay( gc );
//   switch ( gc.get( Calendar.DAY_OF_WEEK ) )
//   {
//    case ( Calendar.MONDAY    ):
//     gc.add( Calendar.DATE, -3 );
//     break;
//    case ( Calendar.SUNDAY    ):
//     gc.add( Calendar.DATE, -2 );
//     break;
//    default:
//     gc.add( Calendar.DATE, -1 );
//     break;
//   }
//   return gc.getTime();
  }
 }

 public static synchronized java.util.Date getPreviousWeekDay(
  java.util.Calendar gc )
 {
  {
   /**
    * 详细设计：
    * 1.如果date是星期日，则减3天
    * 2.如果date是星期六，则减2天
    * 3.否则减1天
    */
   switch ( gc.get( Calendar.DAY_OF_WEEK ) )
   {
    case ( Calendar.MONDAY    ):
     gc.add( Calendar.DATE, -3 );
     break;
    case ( Calendar.SUNDAY    ):
     gc.add( Calendar.DATE, -2 );
     break;
    default:
     gc.add( Calendar.DATE, -1 );
     break;
   }
   return gc.getTime();
  }
 }

 /**
  * 得到指定日期的后一个工作日
  *
  * @param date
  *            指定日期。
  * @return 指定日期的后一个工作日
  */
 public static synchronized java.util.Date getNextWeekDay(
  java.util.Date date )
 {
  /**
   * 详细设计：
   * 1.如果date是星期五，则加3天
   * 2.如果date是星期六，则加2天
   * 3.否则加1天
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  switch ( gc.get( Calendar.DAY_OF_WEEK ) )
  {
   case ( Calendar.FRIDAY    ):
    gc.add( Calendar.DATE, 3 );
    break;
   case ( Calendar.SATURDAY    ):
    gc.add( Calendar.DATE, 2 );
    break;
   default:
    gc.add( Calendar.DATE, 1 );
    break;
  }
  return gc.getTime();
 }

 public static synchronized java.util.Calendar getNextWeekDay(
  java.util.Calendar gc )
 {
  /**
   * 详细设计：
   * 1.如果date是星期五，则加3天
   * 2.如果date是星期六，则加2天
   * 3.否则加1天
   */
  switch ( gc.get( Calendar.DAY_OF_WEEK ) )
  {
   case ( Calendar.FRIDAY    ):
    gc.add( Calendar.DATE, 3 );
    break;
   case ( Calendar.SATURDAY    ):
    gc.add( Calendar.DATE, 2 );
    break;
   default:
    gc.add( Calendar.DATE, 1 );
    break;
  }
  return gc;
 }

 /**
  * 取得指定日期的下一个月的最后一天
  *
  * @param date
  *            指定日期。
  * @return 指定日期的下一个月的最后一天
  */
 public static synchronized java.util.Date getLastDayOfNextMonth(
  java.util.Date date )
 {
  /**
   * 详细设计：
   * 1.调用getNextMonth设置当前时间
   * 2.以1为基础，调用getLastDayOfMonth
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  gc.setTime( DateUtil.getNextMonth( gc.getTime() ) );
  gc.setTime( DateUtil.getLastDayOfMonth( gc.getTime() ) );
  return gc.getTime();
 }

 /**
  * 取得指定日期的下一个星期的最后一天
  *
  * @param date
  *            指定日期。
  * @return 指定日期的下一个星期的最后一天
  */
 public static synchronized java.util.Date getLastDayOfNextWeek(
  java.util.Date date )
 {
  /**
   * 详细设计：
   * 1.调用getNextWeek设置当前时间
   * 2.以1为基础，调用getLastDayOfWeek
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  gc.setTime( DateUtil.getNextWeek( gc.getTime() ) );
  gc.setTime( DateUtil.getLastDayOfWeek( gc.getTime() ) );
  return gc.getTime();
 }

 /**
  * 取得指定日期的下一个月的第一天
  *
  * @param date
  *            指定日期。
  * @return 指定日期的下一个月的第一天
  */
 public static synchronized java.util.Date getFirstDayOfNextMonth(
  java.util.Date date )
 {
  /**
   * 详细设计：
   * 1.调用getNextMonth设置当前时间
   * 2.以1为基础，调用getFirstDayOfMonth
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  gc.setTime( DateUtil.getNextMonth( gc.getTime() ) );
  gc.setTime( DateUtil.getFirstDayOfMonth( gc.getTime() ) );
  return gc.getTime();
 }

 public static synchronized java.util.Calendar getFirstDayOfNextMonth(
  java.util.Calendar gc )
 {
  /**
   * 详细设计：
   * 1.调用getNextMonth设置当前时间
   * 2.以1为基础，调用getFirstDayOfMonth
   */
  gc.setTime( DateUtil.getNextMonth( gc.getTime() ) );
  gc.setTime( DateUtil.getFirstDayOfMonth( gc.getTime() ) );
  return gc;
 }

 /**
  * 取得指定日期的下一个星期的第一天
  *
  * @param date
  *            指定日期。
  * @return 指定日期的下一个星期的第一天
  */
 public static synchronized java.util.Date getFirstDayOfNextWeek(
  java.util.Date date )
 {
  /**
   * 详细设计：
   * 1.调用getNextWeek设置当前时间
   * 2.以1为基础，调用getFirstDayOfWeek
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  gc.setTime( DateUtil.getNextWeek( gc.getTime() ) );
  gc.setTime( DateUtil.getFirstDayOfWeek( gc.getTime() ) );
  return gc.getTime();
 }

 public static synchronized java.util.Calendar getFirstDayOfNextWeek(
  java.util.Calendar gc )
 {
  /**
   * 详细设计：
   * 1.调用getNextWeek设置当前时间
   * 2.以1为基础，调用getFirstDayOfWeek
   */
  gc.setTime( DateUtil.getNextWeek( gc.getTime() ) );
  gc.setTime( DateUtil.getFirstDayOfWeek( gc.getTime() ) );
  return gc;
 }

 /**
  * 取得指定日期的下一个月
  *
  * @param date
  *            指定日期。
  * @return 指定日期的下一个月
  */
 public static synchronized java.util.Date getNextMonth( java.util.Date date )
 {
  /**
   * 详细设计：
   * 1.指定日期的月份加1
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  gc.add( Calendar.MONTH, 1 );
  return gc.getTime();
 }

 /**
  * 取得指定日期的前N个月
  *
  * @param date
  *            指定日期。
  * @return 指定日期的下一个月
  */
 public static synchronized java.util.Date getPreviousMonth( java.util.Date date, int nmonth )
 {
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  gc.add( Calendar.MONTH, (0 - nmonth) );
  return gc.getTime();
 }

 public static synchronized java.util.Calendar getNextMonth( java.util.Calendar gc )
 {
  /**
   * 详细设计：
   * 1.指定日期的月份加1
   */
  gc.add( Calendar.MONTH, 1 );
  return gc;
 }

 /**
  * 取得指定日期的下一天
  *
  * @param date
  *            指定日期。
  * @return 指定日期的下一天
  */
 public static synchronized java.util.Date getNextDay( java.util.Date date )
 {
  /**
   * 详细设计： 1.指定日期加1天
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  gc.add( Calendar.DATE, 1 );
  return gc.getTime();
 }
 
 /**
  * 取得指定日期的前N天
  *
  * @param date
  *            指定日期。
  * @param n 
  * @return 指定日期的前N天
  */
 public static synchronized java.util.Date getPreviousNDay( java.util.Date date, int n )
 {
  /**
   * 详细设计： 1.指定日期减N天
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  gc.add( Calendar.DATE, (0 - n) );
  return gc.getTime();
 }
 
 /**
  * 取得指定日期的后N天
  *
  * @param date
  *            指定日期。
  * @param n 
  * @return 指定日期的前N天
  */
 public static synchronized java.util.Date getNextNDay( java.util.Date date, int n )
 {
  /**
   * 详细设计： 1.指定日期减N天
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  gc.add( Calendar.DATE, (0 + n) );
  return gc.getTime();
 }

 public static synchronized java.util.Calendar getNextDay( java.util.Calendar gc )
 {
  /**
   * 详细设计： 1.指定日期加1天
   */
  gc.add( Calendar.DATE, 1 );
  return gc;
 }

 /**
  * 取得指定日期的下一个星期
  *
  * @param date
  *            指定日期。
  * @return 指定日期的下一个星期
  */
 public static synchronized java.util.Date getNextWeek( java.util.Date date )
 {
  /**
   * 详细设计：
   * 1.指定日期加7天
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  gc.add( Calendar.DATE, 7 );
  return gc.getTime();
 }

 public static synchronized java.util.Calendar getNextWeek( java.util.Calendar gc )
 {
  /**
   * 详细设计：
   * 1.指定日期加7天
   */
  gc.add( Calendar.DATE, 7 );
  return gc;
 }

 /**
  * 取得指定日期的所处星期的最后一天
  *
  * @param date
  *            指定日期。
  * @return 指定日期的所处星期的最后一天
  */
 public static synchronized java.util.Date getLastDayOfWeek(
  java.util.Date date )
 {
  /**
   * 详细设计：
   * 1.如果date是星期日，则加6天
   * 2.如果date是星期一，则加5天
   * 3.如果date是星期二，则加4天
   * 4.如果date是星期三，则加3天
   * 5.如果date是星期四，则加2天
   * 6.如果date是星期五，则加1天
   * 7.如果date是星期六，则加0天
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  switch ( gc.get( Calendar.DAY_OF_WEEK ) )
  {
   case ( Calendar.SUNDAY  ):
    gc.add( Calendar.DATE, 6 );
    break;
   case ( Calendar.MONDAY  ):
    gc.add( Calendar.DATE, 5 );
    break;
   case ( Calendar.TUESDAY  ):
    gc.add( Calendar.DATE, 4 );
    break;
   case ( Calendar.WEDNESDAY  ):
    gc.add( Calendar.DATE, 3 );
    break;
   case ( Calendar.THURSDAY  ):
    gc.add( Calendar.DATE, 2 );
    break;
   case ( Calendar.FRIDAY  ):
    gc.add( Calendar.DATE, 1 );
    break;
   case ( Calendar.SATURDAY  ):
    gc.add( Calendar.DATE, 0 );
    break;
  }
  return gc.getTime();
 }

 /**
  * 取得指定日期的所处星期的第一天
  *
  * @param date
  *            指定日期。
  * @return 指定日期的所处星期的第一天
  */
 public static synchronized java.util.Date getFirstDayOfWeek(
  java.util.Date date )
 {
  /**
   * 详细设计：
   * 1.如果date是星期日，则减0天
   * 2.如果date是星期一，则减1天
   * 3.如果date是星期二，则减2天
   * 4.如果date是星期三，则减3天
   * 5.如果date是星期四，则减4天
   * 6.如果date是星期五，则减5天
   * 7.如果date是星期六，则减6天
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  switch ( gc.get( Calendar.DAY_OF_WEEK ) )
  {
   case ( Calendar.SUNDAY  ):
    gc.add( Calendar.DATE, 0 );
    break;
   case ( Calendar.MONDAY  ):
    gc.add( Calendar.DATE, -1 );
    break;
   case ( Calendar.TUESDAY  ):
    gc.add( Calendar.DATE, -2 );
    break;
   case ( Calendar.WEDNESDAY  ):
    gc.add( Calendar.DATE, -3 );
    break;
   case ( Calendar.THURSDAY  ):
    gc.add( Calendar.DATE, -4 );
    break;
   case ( Calendar.FRIDAY  ):
    gc.add( Calendar.DATE, -5 );
    break;
   case ( Calendar.SATURDAY  ):
    gc.add( Calendar.DATE, -6 );
    break;
  }
  return gc.getTime();
 }

 public static synchronized java.util.Calendar getFirstDayOfWeek(
  java.util.Calendar gc )
 {
  /**
   * 详细设计：
   * 1.如果date是星期日，则减0天
   * 2.如果date是星期一，则减1天
   * 3.如果date是星期二，则减2天
   * 4.如果date是星期三，则减3天
   * 5.如果date是星期四，则减4天
   * 6.如果date是星期五，则减5天
   * 7.如果date是星期六，则减6天
   */
  switch ( gc.get( Calendar.DAY_OF_WEEK ) )
  {
   case ( Calendar.SUNDAY  ):
    gc.add( Calendar.DATE, 0 );
    break;
   case ( Calendar.MONDAY  ):
    gc.add( Calendar.DATE, -1 );
    break;
   case ( Calendar.TUESDAY  ):
    gc.add( Calendar.DATE, -2 );
    break;
   case ( Calendar.WEDNESDAY  ):
    gc.add( Calendar.DATE, -3 );
    break;
   case ( Calendar.THURSDAY  ):
    gc.add( Calendar.DATE, -4 );
    break;
   case ( Calendar.FRIDAY  ):
    gc.add( Calendar.DATE, -5 );
    break;
   case ( Calendar.SATURDAY  ):
    gc.add( Calendar.DATE, -6 );
    break;
  }
  return gc;
 }

 /**
  * 取得指定日期的所处月份的最后一天
  *
  * @param date
  *            指定日期。
  * @return 指定日期的所处月份的最后一天
  */
 public static synchronized java.util.Date getLastDayOfMonth(
  java.util.Date date )
 {
  /**
   * 详细设计：
   * 1.如果date在1月，则为31日
   * 2.如果date在2月，则为28日
   * 3.如果date在3月，则为31日
   * 4.如果date在4月，则为30日
   * 5.如果date在5月，则为31日
   * 6.如果date在6月，则为30日
   * 7.如果date在7月，则为31日
   * 8.如果date在8月，则为31日
   * 9.如果date在9月，则为30日
   * 10.如果date在10月，则为31日
   * 11.如果date在11月，则为30日
   * 12.如果date在12月，则为31日
   * 1.如果date在闰年的2月，则为29日
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  switch ( gc.get( Calendar.MONTH ) )
  {
   case 0:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
   case 1:
    gc.set( Calendar.DAY_OF_MONTH, 28 );
    break;
   case 2:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
   case 3:
    gc.set( Calendar.DAY_OF_MONTH, 30 );
    break;
   case 4:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
   case 5:
    gc.set( Calendar.DAY_OF_MONTH, 30 );
    break;
   case 6:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
   case 7:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
   case 8:
    gc.set( Calendar.DAY_OF_MONTH, 30 );
    break;
   case 9:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
   case 10:
    gc.set( Calendar.DAY_OF_MONTH, 30 );
    break;
   case 11:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
  }
  //检查闰年
  if ( ( gc.get( Calendar.MONTH ) == Calendar.FEBRUARY )
   && ( isLeapYear( gc.get( Calendar.YEAR ) ) ) )
  {
   gc.set( Calendar.DAY_OF_MONTH, 29 );
  }
  return gc.getTime();
 }

 public static synchronized java.util.Calendar getLastDayOfMonth(
  java.util.Calendar gc )
 {
  /**
   * 详细设计：
   * 1.如果date在1月，则为31日
   * 2.如果date在2月，则为28日
   * 3.如果date在3月，则为31日
   * 4.如果date在4月，则为30日
   * 5.如果date在5月，则为31日
   * 6.如果date在6月，则为30日
   * 7.如果date在7月，则为31日
   * 8.如果date在8月，则为31日
   * 9.如果date在9月，则为30日
   * 10.如果date在10月，则为31日
   * 11.如果date在11月，则为30日
   * 12.如果date在12月，则为31日
   * 1.如果date在闰年的2月，则为29日
   */
  switch ( gc.get( Calendar.MONTH ) )
  {
   case 0:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
   case 1:
    gc.set( Calendar.DAY_OF_MONTH, 28 );
    break;
   case 2:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
   case 3:
    gc.set( Calendar.DAY_OF_MONTH, 30 );
    break;
   case 4:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
   case 5:
    gc.set( Calendar.DAY_OF_MONTH, 30 );
    break;
   case 6:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
   case 7:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
   case 8:
    gc.set( Calendar.DAY_OF_MONTH, 30 );
    break;
   case 9:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
   case 10:
    gc.set( Calendar.DAY_OF_MONTH, 30 );
    break;
   case 11:
    gc.set( Calendar.DAY_OF_MONTH, 31 );
    break;
  }
  //检查闰年
  if ( ( gc.get( Calendar.MONTH ) == Calendar.FEBRUARY )
   && ( isLeapYear( gc.get( Calendar.YEAR ) ) ) )
  {
   gc.set( Calendar.DAY_OF_MONTH, 29 );
  }
  return gc;
 }

 /**
  * 取得指定日期的所处月份的第一天
  *
  * @param date
  *            指定日期。
  * @return 指定日期的所处月份的第一天
  */
 public static synchronized java.util.Date getFirstDayOfMonth( java.util.Date date )
 {
  /**
   * 详细设计： 1.设置为1号
   */
  GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
  gc.setTime( date );
  gc.set( Calendar.DAY_OF_MONTH, 1 );
  return gc.getTime();
 }

 public static synchronized java.util.Calendar getFirstDayOfMonth( java.util.Calendar gc )
 {
  /**
   * 详细设计： 1.设置为1号
   */
  gc.set( Calendar.DAY_OF_MONTH, 1 );
  return gc;
 }

 /**
  * 将日期对象转换成为指定ORA日期、时间格式的字符串形式。如果日期对象为空，返回 一个空字符串对象，而不是一个空对象。
  *
  * @param theDate
  *            将要转换为字符串的日期对象。
  * @param hasTime
  *            如果返回的字符串带时间则为true
  * @return 转换的结果
  */
 public static synchronized String toOraString( Date theDate, boolean hasTime )
 {
  /**
   * 详细设计：
   * 1.如果有时间，则设置格式为getOraDateTimeFormat()的返回值
   * 2.否则设置格式为getOraDateFormat()的返回值
   * 3.调用toString(Date theDate, DateFormat
   * theDateFormat)
   */
  DateFormat theFormat;
  if ( hasTime )
  {
   theFormat = getOraDateTimeFormat();
  }
  else
  {
   theFormat = getOraDateFormat();
  }
  return toString( theDate, theFormat );
 }

 /**
  * 将日期对象转换成为指定日期、时间格式的字符串形式。如果日期对象为空，返回 一个空字符串对象，而不是一个空对象。
  *
  * @param theDate
  *            将要转换为字符串的日期对象。
  * @param hasTime
  *            如果返回的字符串带时间则为true
  * @return 转换的结果
  */
 public static synchronized String toString( Date theDate, boolean hasTime )
 {
  /**
   * 详细设计：
   * 1.如果有时间，则设置格式为getDateTimeFormat的返回值
   * 2.否则设置格式为getDateFormat的返回值
   * 3.调用toString(Date theDate, DateFormat theDateFormat)
   */
  DateFormat theFormat;
  if ( hasTime )
  {
   theFormat = getDateTimeFormat();
  }
  else
  {
   theFormat = getDateFormat();
  }
  return toString( theDate, theFormat );
 }

 /**
  * 标准日期格式
  */
 private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
  "MM/dd/yyyy" );
 /**
  * 标准时间格式
  */
 private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
  "MM/dd/yyyy HH:mm" );
 /**
  * 带时分秒的标准时间格式
  */
 private static final SimpleDateFormat DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat(
  "MM/dd/yyyy HH:mm:ss" );
 /**
  * ORA标准日期格式
  */
 private static final SimpleDateFormat ORA_DATE_FORMAT = new SimpleDateFormat(
  "yyyyMMdd" );
 /**
  * ORA标准时间格式
  */
 private static final SimpleDateFormat ORA_DATE_TIME_FORMAT = new SimpleDateFormat(
  "yyyyMMddHHmm" );
 /**
  * 带时分秒的ORA标准时间格式
  */
 private static final SimpleDateFormat ORA_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat(
  "yyyyMMddHHmmss" );

 /**
  * 创建一个标准日期格式的克隆
  *
  * @return 标准日期格式的克隆
  */
 public static synchronized DateFormat getDateFormat()
 {
  /**
   * 详细设计： 1.返回DATE_FORMAT
   */
  SimpleDateFormat theDateFormat = ( SimpleDateFormat )
   DATE_FORMAT.clone();
  theDateFormat.setLenient( false );
  return theDateFormat;
 }

 /**
  * 创建一个标准时间格式的克隆
  *
  * @return 标准时间格式的克隆
  */
 public static synchronized DateFormat getDateTimeFormat()
 {
  /**
   * 详细设计： 1.返回DATE_TIME_FORMAT
   */
  SimpleDateFormat theDateTimeFormat = ( SimpleDateFormat ) DATE_TIME_FORMAT
   .clone();
  theDateTimeFormat.setLenient( false );
  return theDateTimeFormat;
 }

 /**
  * 创建一个标准ORA日期格式的克隆
  *
  * @return 标准ORA日期格式的克隆
  */
 public static synchronized DateFormat getOraDateFormat()
 {
  /**
   * 详细设计： 1.返回ORA_DATE_FORMAT
   */
  SimpleDateFormat theDateFormat = ( SimpleDateFormat ) ORA_DATE_FORMAT
   .clone();
  theDateFormat.setLenient( false );
  return theDateFormat;
 }

 /**
  * 创建一个标准ORA时间格式的克隆
  *
  * @return 标准ORA时间格式的克隆
  */
 public static synchronized DateFormat getOraDateTimeFormat()
 {
  /**
   * 详细设计： 1.返回ORA_DATE_TIME_FORMAT
   */
  SimpleDateFormat theDateTimeFormat = ( SimpleDateFormat )
   ORA_DATE_TIME_FORMAT.clone();
  theDateTimeFormat.setLenient( false );
  return theDateTimeFormat;
 }

 /**
  * 将一个日期对象转换成为指定日期、时间格式的字符串。 如果日期对象为空，返回一个空字符串，而不是一个空对象。
  *
  * @param theDate
  *            要转换的日期对象
  * @param theDateFormat
  *            返回的日期字符串的格式
  * @return 转换结果
  */
 public static synchronized String toString( Date theDate,
  DateFormat theDateFormat )
 {
  /**
   * 详细设计：
   * 1.theDate为空，则返回""
   * 2.否则使用theDateFormat格式化
   */
  if ( theDate == null )
   return "";
  return theDateFormat.format( theDate );
 }
 
 public static String getDate(Date date, String patten) {
		SimpleDateFormat sf = new SimpleDateFormat(patten);
		return sf.format(date);
	}

public static Date getDate(String dateStr, String patten)
	throws ParseException {
		if (dateStr == null)
			return null;
		SimpleDateFormat sf = new SimpleDateFormat(patten);
		return sf.parse(dateStr);
 }	
 
	/**
	 * 得到一个6位随机数
	 * 
	 * **/
	public static String getRomNum(){
		double ranNum = Math.random();
		int i = (int) (ranNum * 1000000);
		return String.valueOf(i);
	}
	
	
	public static String getDateStr(String dateStr,String old_pattern ,String new_pattern){
		String sDate="";
		try {
			 sDate=getDate(getDate(dateStr, old_pattern), new_pattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  sDate;
	}
 
	
	/**    
	 * 生成一个随机数,生成的随机数【有重复】的数字     
	 * @param several 得到多少位的随机数  
	 * @return    
	 */
	public static String getRomNum(int several) {
	Random random = new Random();
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < several; i++) {
	sb.append(random.nextInt(9));
	}
	return sb.toString();
	} 

	/**
	 * 生成一个随机数,生成的随机数【没有重复】的数字,生成的长度最多是10位的随机数
	 * @param several 得到多少位的随机数,不能够大于10
	 * @return
	 */
	public static String randomNoRepeat(int several) {
	int[] array = {0,1,2,3,4,5,6,7,8,9};
	int length = array.length;
	if (several > length) {
	several = length;
	}
	    Random rand = new Random();
	    for (int i = length; i > 1; i--) {
	        int index = rand.nextInt(i);
	        int tmp = array[index];
	        array[index] = array[i - 1];
	        array[i - 1] = tmp;
	    }
	    StringBuffer sb = new StringBuffer();
	    for(int i = 0; i < several; i++) {
	     sb.append(array[i]);
	    }
	    return sb.toString();
	} 
	
	/**
	 * 返回date2 - date1的天数差
	 * @param date2
	 * @param date1
	 * @return date2 - date1的天数差
	 */
	public static int getDaysDiff(Date date2, Date date1){
		String ds2 = DateUtil.getDateDayFormat(date2);
		String ds1 = DateUtil.getDateDayFormat(date1);
		Date d2 = DateUtil.parseDateW3CFormat(ds2 + " 00:00:01");
		Date d1 = DateUtil.parseDateW3CFormat(ds1 + " 00:00:01");		
		
		// fast way
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		long endInstant = d2.getTime();
		int presumedDays = (int) ((endInstant - d1.getTime()) / MILLIS_IN_DAY);
		Calendar cursor = GregorianCalendar.getInstance();
		cursor.setTime(d1);
		cursor.add(Calendar.DAY_OF_YEAR, presumedDays);
		long instant = cursor.getTimeInMillis();
		if (instant == endInstant){
			return presumedDays;
		}
		
		// slower way
		final int step = instant < endInstant ? 1 : -1;
		String ds = "";
		do {
			cursor.add(Calendar.DAY_OF_MONTH, step);
			presumedDays += step;
			Date d = cursor.getTime();
			ds = DateUtil.getDateDayFormat(d);			
		} while (!ds.equals(ds2));
		return presumedDays;
	}
	/**
	 * 返回date2 - date1的时间差
	 * @param date2
	 * @param date1
	 * @return
	 */
	public static String getDaysDiffTimes(Date date2, Date date1){
		long diff = date2.getTime()-date1.getTime();
		long diffHours = diff / (60 * 60 * 1000);
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffSeconds = diff / 1000 % 60;
		StringBuffer totDiff = new StringBuffer("");
		if(diffHours<10&&diffHours>0){
			totDiff.append("0");
		}
		totDiff.append(diffHours).append(":");
		if(diffMinutes<10&&diffMinutes>0){
			totDiff.append("0");
		}
		totDiff.append(diffMinutes).append(":");
		if(diffSeconds<10&&diffSeconds>0){
			totDiff.append("0");
		}
		totDiff.append(diffSeconds);
		
		return totDiff.toString();
	}
	
	/**
	 * 格式为 xx:xx:xx
	 * @param hms hh:mm:ss
	 * @return
	 */
	public static long parseToSeconds(String hms){
		long ret = 0;
		String[] hmstmp = hms.split(":");
		for(int i = 0;i < hmstmp.length;i++){
			String t = hmstmp[i];
			switch(i){
			case 0:
				ret += 3600L * Integer.parseInt(t.startsWith("0")? t.substring(1) : t);
				break;
			case 1:
				ret += 60L * Integer.parseInt(t.startsWith("0")? t.substring(1) : t);				
				break;
			case 2:
				ret += 1L * Integer.parseInt(t.startsWith("0")? t.substring(1) : t);							
				break;
			default:
				break;
			}
		}
		return ret;
	}
	

	
	/**
	 * 格式为 xx时xx分xx秒 或者 xx小时xx分xx秒
	 * @param durationStr
	 * @return
	 */
	public static long getDurationFromRaw(String durationStr){
		long duration = 0;
		String durationColonStr = "";
		String residueStr = durationStr;
		if(durationStr.contains("时") || durationStr.contains("小时")){
			if(durationStr.contains("小时")){
			   String[] tmp = residueStr.split("小时");
			   durationColonStr += (tmp[0].length() > 1? tmp[0] : "0" + tmp[0]);
			   residueStr = tmp[1];
			}else{
			   String[] tmp = residueStr.split("时");
			   durationColonStr += (tmp[0].length() > 1? tmp[0] : "0" + tmp[0]);
			   residueStr = tmp[1];				
			}
		}else{
			durationColonStr += "00";
		}
		
		if(durationStr.contains("分")){
			String[] tmp = residueStr.split("分");
			durationColonStr += ":" + (tmp[0].length() > 1? tmp[0] : "0" + tmp[0]);
			residueStr = tmp[1];
		}else{
			durationColonStr += ":00";
		}
		
		if(durationStr.contains("秒")){
			String[] tmp = residueStr.split("秒");
			durationColonStr += ":" + (tmp[0].length() > 1? tmp[0] : "0" + tmp[0]);
		}
		
		duration = DateUtil.parseToSeconds(durationColonStr);
		return duration;
	}
	
	/**
	 * 某些情况需要根据【月日】来推断日期，比如'01-16 20:13:18',常用于解析外部系统返回的日期数据
	 * @param vl
	 * @return 
	 */
	public static Date parseWithoutYear(String vl){
		Date ret = null;
		String sourceTimeStr = vl.substring(1, vl.length() - 1);
		if (sourceTimeStr.length() > 14) {
			ret = DateUtil.parseDateW3CFormat(sourceTimeStr);
		} else {
			Calendar thisYearCal = Calendar.getInstance();
			thisYearCal.setTime(new Date());
			int thisYear = thisYearCal.get(Calendar.YEAR);
			Date tryd = DateUtil.parseDateW3CFormat(thisYear + "-" + sourceTimeStr);
			if (tryd.compareTo(thisYearCal.getTime()) >= 0) {
				tryd = DateUtil.parseDateW3CFormat((thisYear - 1) + "-" + sourceTimeStr);
			}
			ret = tryd;
		}
		return ret;
	}

	public static void main(String[] args) {
		String timeString="2016年05月11日10时40分15秒";
		//System.out.println(DateUtil.getDateSecondFormat(DateUtil.parseDateFormat(timeString,"yyyyMMddHHmmss")));
		
		System.out.println(DateUtil.parseDateChineseFormat(timeString));
		
		System.out.println(DateUtil.getDaysDiff(DateUtil.parseDateDayFormat("2016-08-16"),DateUtil.parseDateDayFormat("2016-05-11")));
	}
}


