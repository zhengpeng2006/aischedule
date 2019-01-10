package com.asiainfo.monitor.tools.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.LocalDate;
import org.joda.time.Months;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: AI(NanJing)</p>
 *
 * @author Yang Hua
 * @version 1.0
 */
public final class TimeUtil {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMM");

	private static final SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final SimpleDateFormat DATE_FORMAT3 = new SimpleDateFormat("yyyy-MM-dd HH");

	private static final SimpleDateFormat DATE_FORMAT4 = new SimpleDateFormat("HH");

	private static final SimpleDateFormat DATE_FORMAT5 = new SimpleDateFormat("dd");

	private static final SimpleDateFormat DATE_FORMAT6 = new SimpleDateFormat("MM");

	private static final SimpleDateFormat DATE_FORMAT7 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private static final SimpleDateFormat DATE_FORMAT8 = new SimpleDateFormat("yy");
	
	private static final SimpleDateFormat DATE_FORMAT9 = new SimpleDateFormat("yyyyMMddHHmmss");

	private static final SimpleDateFormat DATE_FORMAT10 = new SimpleDateFormat("mm");
	
	private static final SimpleDateFormat DATE_FORMAT11 = new SimpleDateFormat("ss");
	
	private TimeUtil() {
	}

	public static String format8(Date date) {
		return DATE_FORMAT8.format(date);
	}
	
	public static String format9(Date date) {
		return DATE_FORMAT9.format(date);
	}

	/**
	 *
	 * @param date Date
	 * @return String
	 */
	public static String format(Date date) {
		return DATE_FORMAT2.format(date);
	}
	
	public static String format1(Date date){
		return DATE_FORMAT1.format(date);
	}

	public static String format7(Date date){
	    return DATE_FORMAT7.format(date);
	}
	/**
	 * 在一个时间上加上或减去小时
	 * @param ti long
	 * @param i int
	 * @return Date
	 */
	public static Date addOrMinusHours(long ti, int i) {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(GregorianCalendar.HOUR, i);
		rtn = cal.getTime();
		return rtn;
	}

	/**
	 *
	 * @param start Date
	 * @param end Date
	 * @return int
	 */
	public static int monthsBetween(Date start, Date end) {
		return Months.monthsBetween(LocalDate.fromDateFields(start),
				LocalDate.fromDateFields(end)).getMonths();
	}

	/**
	 *
	 * @param ti long
	 * @param i int
	 * @return Date
	 * @throws Exception
	 */
	public static Date addOrMinusMonth(long ti, int i) throws Exception {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(GregorianCalendar.MONTH, i);
		rtn = cal.getTime();
		return rtn;
	}

	/**
	 *
	 * @param start Date
	 * @param end Date
	 * @return int[]
	 * @throws Exception
	 */
	public static int[] computeYYYYMM(Date start, Date end) throws Exception {
		int diff = monthsBetween(start, end);
		int[] rtn = new int[diff + 1];
		for (int i = 0; i < rtn.length; i++) {
			rtn[i] = Integer.parseInt(DATE_FORMAT.format(addOrMinusMonth(start
					.getTime(), i)));
		}
		return rtn;
	}

	/**
	 *
	 * @param date Date
	 * @return int
	 */
	public static int getYYYYMM(Date date) {
		return Integer.parseInt(DATE_FORMAT.format(date));
	}

	/**
	 *
	 * @param date Date
	 * @return int
	 */
	public static String getYYYYMMDDHH(Date date) {
		return DATE_FORMAT3.format(date);
	}

	/**
	 *
	 * @param date Date
	 * @return String
	 */
	public static String getHH(Date date) {
		return DATE_FORMAT4.format(date);
	}

	public static String getDD(Date date) {
		return DATE_FORMAT5.format(date);
	}

	public static String getMM(Date date) {
		return DATE_FORMAT6.format(date);
	}
	
	/**
	 * 转换成分钟
	 * @param date
	 * @return
	 */
	public static String getMi(Date date){
		return DATE_FORMAT10.format(date);
	}
	
	/**
	 * 转换成秒
	 * @param date
	 * @return
	 */
	public static String getSs(Date date){
		return DATE_FORMAT11.format(date);
	}
	/**
	 * 本周周一
	 * @return
	 */
	public static Calendar getMondayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 1);
		return c;
	}

	/**
	 * 本周周日
	 * @return
	 */
	public static Calendar getSundayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 7);
		return c;
	}
	
	/**
	 * 本月最后一天
	 * @return
	 */
	public static Calendar getLastDateOfMonth(){
		 Date dt = new Date(System.currentTimeMillis());
		 if (dt == null)
			 return null;
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(dt);
		 int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		 cal.set(Calendar.DAY_OF_MONTH, days);
		 return cal;
	 }
	
	/**
	 * 本月第一天
	 * @return
	 */
	public static Calendar getFristDateOfMonth(){
		Date dt = new Date(System.currentTimeMillis());
		
		if (dt == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int days = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, days);
		return cal;
	 }
	/**
	 * 返回数组1.相差年数，2相差月数，3，相差天数
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	 static  int[]  getDateLength(String  fromDate, String  toDate)  {  
	      Calendar  c1  =  getCal(fromDate);  
	      Calendar  c2  =  getCal(toDate);  
	      int[]  p1  =  {  c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)  };  
	      int[]  p2  =  {  c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH)  };  
	      return  new  int[]  {  p2[0]  -  p1[0], p2[0]  *  12  +  p2[1]  -  p1[0]  *  12  -  p1[1], (int)  ((c2.getTimeInMillis()  -  c1.getTimeInMillis())  /  (24  *  3600  *  1000))  };  
	   }  

	   static  Calendar  getCal(String  date)  {  
	      Calendar  cal  =  Calendar.getInstance();  
	      cal.clear();  
	      cal.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6))  -  1, Integer.parseInt(date.substring(6, 8)));  
	      return  cal;  
	   }  
	 
	   /**
		 *
		 * @param start Date
		 * @param end Date
		 * @return int[]
		 * @throws Exception
		 */
		public static int[] createYYYYMM(Date start, Date end) throws Exception {
			int diff[] = getDateLength(format9(start), format9(end));
			int[] rtn = new int[diff[1] + 1];
			for (int i = 0; i < rtn.length; i++) {
				rtn[i] = Integer.parseInt(DATE_FORMAT.format(addOrMinusMonth(start
						.getTime(), i)));
			}
			return rtn;
		}

	public static void main(String[] args) throws Exception {
		int[] rtn = computeYYYYMM(new Date(), addOrMinusMonth((new Date()
				.getTime()), 1));
		for (int i = 0; i < rtn.length; i++) {
			System.out.println(rtn[i]);
		}
	}
}
