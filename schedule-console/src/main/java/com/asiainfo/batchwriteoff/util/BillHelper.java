package com.asiainfo.batchwriteoff.util;

import com.ai.common.i18n.CrmLocaleFactory;
import org.apache.commons.lang.time.DateUtils;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BillHelper {

	/**
	 * 格式化为金额0.00格式
	 * 
	 * @param amount
	 *            金额单位：分
	 * @return
	 * @author jiajj
	 */
	public static String fmMoney(long amount) {
		return new BigDecimal(amount).divide(new BigDecimal(100), 2,
				BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 格式化为金额0.00格式
	 * 
	 * @param amount
	 *            金额单位：厘
	 */
	public static String fmMoneyLi(long amount) {
		return new BigDecimal(amount).divide(new BigDecimal(1000), 2,
				BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 格式化为金额0.00格式
	 * 
	 * @param amount
	 *            金额单位：分
	 */
	public static String fmMoney(String amount) {
		return new BigDecimal(amount).divide(new BigDecimal(100), 2,
				BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 格式化为金额0.00格式
	 * 
	 * @param amount
	 *            金额单位：厘
	 * @return
	 * @author jiajj
	 */
	public static String fmMoneyLi(String amount) {
		return new BigDecimal(amount).divide(new BigDecimal(1000), 2,
				BigDecimal.ROUND_HALF_UP).toString();
	}
	/**
	 * 讲金额由分 转换为 厘
	 * 
	 * @param amount		金额单位：分
	 * 
	 * @return				单位： 厘
	 * @author yanrg
	 */
	public static long fmMoneyFromCent2Li(long amount)throws Exception{
		return amount * 10;
	}
	
	/**
	 * 将金额由 元 转换为 分
	 * 
	 * @param amount
	 *            金额单位：元
	 * @return	单位： 分
	 * @author jiajj
	 */
	public static long fmMoneyFromYuan2Cent(String amount) {
		return new BigDecimal(amount).multiply(new BigDecimal(100)).longValue();
	}
	
	/**
	 * 讲金额由 厘 转换为 分
	 * 
	 * @param amount		金额单位：厘
	 * 
	 * @return				单位： 分
	 * @author yanrg
	 */
	public static long fmMoneyFromLi2Cent(long amount)throws Exception{
		return new BigDecimal(amount).divide(new BigDecimal(10), 0,
				BigDecimal.ROUND_HALF_UP).longValue();
	}

	/**
	 * 格式化为百分比
	 * 
	 * @param dividend
	 * @param divisor
	 * @return
	 * @author jiajj
	 */
	public static String fmPercent(long dividend, long divisor) {
		return new BigDecimal(dividend).divide(new BigDecimal(divisor), 2,
				BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))
				.toString()
				+ "%";
	}

	/**
	 * 流量KB转为MB
	 * 
	 * @param flux
	 * @return
	 * @author xiaolei
	 */
	public static String fmFlux(long flux) {
		return new BigDecimal(flux).divide(new BigDecimal(1024), 2,
				BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 格式化日期格式为：YYYY年MM月DD日
	 * 
	 * @param date
	 *            YYYYMMDD
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 * @author jiajj
	 */
	public static String fmDate(String date) throws Exception, RemoteException {
		StringBuffer rtn = new StringBuffer(date.substring(0, 4));
		rtn.append(CrmLocaleFactory.getResource("AMS0700291")).append(date.substring(4, 6)).
		append(CrmLocaleFactory.getResource("AMS0700292")).append(date.substring(6, 8)).
		append(CrmLocaleFactory.getResource("AMS0700293"));
		return rtn.toString();
	}

	/**
	 * 格式化日期格式为：YYYY年MM月DD日 HH24:MI:SS
	 *
	 * @param dateTime
	 *            YYYYMMDDHH24MISS
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 * @author jiajj
	 */
	public static String fmDateTime(String dateTime) throws Exception,
			RemoteException {
		if (dateTime == null || dateTime.length() < 14) {
			return dateTime;
		}
		StringBuffer rtn = new StringBuffer(dateTime.substring(0, 4));
		rtn.append(CrmLocaleFactory.getResource("AMS0700291")).append(dateTime.substring(4, 6)).
		append(CrmLocaleFactory.getResource("AMS0700292")).append(
				dateTime.substring(6, 8)).append(CrmLocaleFactory.getResource("AMS0700293")).append(
				dateTime.substring(8, 10)).append(":").append(
				dateTime.substring(10, 12)).append(":").append(
				dateTime.substring(12, 14));
		return rtn.toString();
	}

	/**
	 * 格式化日期格式为：YYYY年MM月DD日
	 *
	 * @param date
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 * @author jiajj
	 */
	public static String fmDate(Date date) throws Exception, RemoteException {
		StringBuffer sb = new StringBuffer();
		sb.append("yyyy").append(CrmLocaleFactory.getResource("AMS0700291"))
		  .append("MM").append(CrmLocaleFactory.getResource("AMS0700292"))
		  .append("dd").append(CrmLocaleFactory.getResource("AMS0700293"));
		SimpleDateFormat dateFormat = new SimpleDateFormat(sb.toString());
		return dateFormat.format(date);
	}

	/**
	 * 格式化日期格式为：YYYYMMDD
	 *
	 * @param date
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 * @author jiajj
	 */
	public static String fmDateYYMMDD(Date date) throws Exception,
			RemoteException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(date);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(fmMoneyFromLi2Cent(123));
		
		//System.out.println(new BigDecimal("12.34").multiply(new BigDecimal("23.345")));
	}
	
	public static Date parseYYYYDate(String date)throws Exception{
    	/**
    	 * 确保pattern中，yyyy-MM在yyyyMMdd最前面，yyyyMMdd也能够匹配2009-10,且匹配出的结果是不正确的时间
    	 */
    	String[] pattern = new String[]{"yyyy-MM","yyyyMM","MM/yyyy",
    			"yyyyMMdd","yyyy-MM-dd","MM/dd/yyyyy",
    			"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss","MM/dd/yyyy HH:mm:ss"};
    	return DateUtils.parseDate(date, pattern);
    }
}
