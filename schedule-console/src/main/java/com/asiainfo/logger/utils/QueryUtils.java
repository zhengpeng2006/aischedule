package com.asiainfo.logger.utils;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.common.abo.bo.BOABGMonBusiErrorLogBean;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogBean;
import com.asiainfo.logger.common.LoggerDateUtils;
import com.asiainfo.logger.db.LoggerDBUtils;
import com.asiainfo.logger.utils.QueryConstants.QueryKey;

/**
 * 查询日志中心的数据（直接查询日志中心数据库的表）
 * 
 * @author 孙德东(24204)
 */
public class QueryUtils {
	private static final Log LOG = LogFactory.getLog(QueryUtils.class);

	// 读/写锁
	private static ReadWriteLock busiLogLock = new ReentrantReadWriteLock();
	private static ReadWriteLock busiErrLogLock = new ReentrantReadWriteLock();

	private QueryUtils() {
	}

	// DateFormat不是线程安全的
		private static ThreadLocal<DateFormat> fomatter = new ThreadLocal<DateFormat>() {
			@Override
			protected DateFormat initialValue() {
				return new SimpleDateFormat("yyyyMM");
			}
		};
	/**
	 * 查询业务日志
	 * 
	 * @param appCode
	 *            应用编码
	 * @param readFlag
	 *            是否
	 * @return
	 * @throws Exception
	 */
	public static BOABGMonBusiLogBean[] qryBusinessLogByCondition(Map<String, Object> conditions) throws Exception {
		DataContainerInterface[] res = qryBusinessByCondition(conditions, LogBeanType.LOG);
		if (res == null || res.length == 0) {
			return new BOABGMonBusiLogBean[0];
		}

		if (!(res instanceof BOABGMonBusiLogBean[])) {
			BOABGMonBusiLogBean[] convertRes = new BOABGMonBusiLogBean[res.length];
			for (int i = 0; i < res.length; i++) {
				convertRes[i] = (BOABGMonBusiLogBean) res[i];
			}
			return convertRes;
		}

		return (BOABGMonBusiLogBean[]) res;
	}

	/**
	 * 查询业务错单日志
	 * 
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public static BOABGMonBusiErrorLogBean[] qryBusinessErrLogByCondition(Map<String, Object> conditions)
			throws Exception {
		DataContainerInterface[] res = qryBusinessByCondition(conditions, LogBeanType.ERR_LOG);
		if (res == null || res.length == 0) {
			return new BOABGMonBusiErrorLogBean[0];
		}

		if (!(res instanceof BOABGMonBusiErrorLogBean[])) {
			BOABGMonBusiErrorLogBean[] convertRes = new BOABGMonBusiErrorLogBean[res.length];
			for (int i = 0; i < res.length; i++) {
				convertRes[i] = (BOABGMonBusiErrorLogBean) res[i];
			}
			return convertRes;
		}
		return (BOABGMonBusiErrorLogBean[]) res;
	}

	private static boolean validParams(Map<String, Object> conditions) {
		Object obj = null;

		// 必须有appcode
		if (!isNotEmptyString(conditions.get(QueryKey.APP_CODE))) {
			return false;
		}

		if ((obj = conditions.get(QueryKey.TASK_ID)) != null && (!(obj instanceof String))) {
			return false;
		}
		if ((obj = conditions.get(QueryKey.TASK_SPLIT_ID)) != null && (!(obj instanceof String))) {
			return false;
		}

		// 当按照时间段读取数据的时候，begin end必须不为空
		QueryFlag flag = QueryFlag.getQueryFlag(conditions.get(QueryKey.FLAG));
		if (flag == QueryFlag.TIME_INTERNAL) {
			java.util.Date begin = change2ValidDate(conditions.get(QueryKey.BEGIN_DATE));
			if (begin == null) {
				return false;
			}

			java.util.Date end = change2ValidDate(conditions.get(QueryKey.END_DATE));
			if (end == null) {
				return false;
			}

			// begin必须比end小
			if (begin.after(end)) {
				return false;
			}
		}

		return true;
	}

	private static enum QueryFlag {
		UNREAD, // 读取未读的
		TIME_INTERNAL; // 按照时间间隔读取

		private QueryFlag() {
		}

		public static QueryFlag getQueryFlag(Object obj) {
			if (obj != null && obj instanceof String && StringUtils.equalsIgnoreCase("T", (String) obj)) {
				return UNREAD;
			} else {
				return TIME_INTERNAL; // 默认方式
			}
		}
	}

	/**
	 * 转化为java.sql.Date
	 * 
	 * @param obj
	 * @return
	 */
	private static java.util.Date change2ValidDate(Object obj) {
		if (obj == null)
			return null;

		if (obj instanceof java.util.Date) {
			return (java.util.Date) obj;
		} else if (obj instanceof java.sql.Date) {
			return new java.util.Date(((java.sql.Date) obj).getTime());
		} else {
			return null;
		}
	}

	/**
	 * 该对象是String，并且不为空
	 * 
	 * @param obj
	 * @return
	 */
	private static boolean isNotEmptyString(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof String))
			return false;

		return StringUtils.isNotEmpty((String) obj);
	}

	private static DataContainerInterface[] qryBusinessByCondition(Map<String, Object> conditions, LogBeanType beantype)
			throws Exception {

		if (!validParams(conditions)) {
			throw new Exception("unvalid params.");
		}

		StringBuilder sb = new StringBuilder();
		Map parameters = new HashMap();

		// appcode
		sb.append(BOABGMonBusiLogBean.S_ServerCode).append(" = :appcode");
		// sb.append(BOABGMonBusiLogBean.S_ServerCode).append(" = :appcode");
		parameters.put("appcode", conditions.get(QueryKey.APP_CODE));

		// taskid
		if (isNotEmptyString(conditions.get(QueryKey.TASK_ID))) {
			sb.append(" and ").append(BOABGMonBusiLogBean.S_TaskId).append(" = :taskId");
			parameters.put("taskId", conditions.get(QueryKey.TASK_ID));
		}

		if (isNotEmptyString(conditions.get(QueryKey.TASK_SPLIT_ID))) {
			sb.append(" and ").append(BOABGMonBusiLogBean.S_TaskSplitId).append(" = :taskSplitId");
			parameters.put("taskSplitId", conditions.get(QueryKey.TASK_SPLIT_ID));
		}

		if (isNotEmptyString(conditions.get(QueryKey.REGION_CODE))) {
			sb.append(" and ").append(BOABGMonBusiLogBean.S_RegionCode).append(" = :regionCode");
			parameters.put("regionCode", conditions.get(QueryKey.REGION_CODE));
		}
		
		Connection conn = LoggerDBUtils.getDBConnection();
		QueryFlag flag = QueryFlag.getQueryFlag(conditions.get(QueryKey.FLAG));
		switch (flag) {
		case UNREAD:
			sb.append(" and ").append(BOABGMonBusiLogBean.S_MonFlag).append(" = 'N'");
			sb.append(" and ").append(BOABGMonBusiLogBean.S_CreateDate).append(" > :begin ");
			sb.append(orderby());
			java.util.Date current = LoggerDateUtils.getCurrentDate();
			parameters.put("begin", new Timestamp(LoggerDateUtils.offsetMinutes(current, QueryConstants.Query.INTERNAL).getTime()));
//			parameters.put("end", LoggerUtils.formatCreateDate(current));

			// 查询当前时间所在的表
			parameters.put(BOABGMonBusiLogBean.S_CreateDate, fomatter.get().format(current));

			DataContainerInterface[] r1 = queryOnce(conn, flag, sb.toString(), parameters, beantype);
			LoggerDBUtils.closeDBConnection(conn);
			return r1;
		case TIME_INTERNAL:
		default:
			// 参数已经校验过
			java.util.Date begin = change2ValidDate(conditions.get(QueryKey.BEGIN_DATE));
			java.util.Date end = change2ValidDate(conditions.get(QueryKey.END_DATE));

			java.util.Date[] tables = LoggerDateUtils.dividedByMonth(begin, end);
			if (tables.length == 1) // 起止时间在一个月内，也就是在一个表内
			{
				sb.append(" and ").append(BOABGMonBusiErrorLogBean.S_CreateDate).append(" between :begin and :end");
				sb.append(orderby());
				parameters.put("begin", new Timestamp(begin.getTime()));
				parameters.put("end", new Timestamp(end.getTime()));
				// 查询当前时间所在的表
				parameters.put(BOABGMonBusiErrorLogBean.S_CreateDate, fomatter.get().format(begin));

				DataContainerInterface[] r2 = queryOnce(conn, flag, sb.toString(), parameters, beantype);
				LoggerDBUtils.closeDBConnection(conn);
				return r2;
			} else { // 需要查询多张表
				List<DataContainerInterface> resList = new ArrayList<DataContainerInterface>();
				String commonSql = sb.toString();
				for (int i = 0; i < tables.length; i++) {
					String perSql = commonSql;
					if (i == 0) { // 第一张表，查询>begintime
						perSql += " and " + BOABGMonBusiErrorLogBean.S_CreateDate + " >= :begin";
						parameters.put("begin", new Timestamp(begin.getTime()));
						// 查询当前时间所在的表
						parameters.put(BOABGMonBusiErrorLogBean.S_CreateDate, fomatter.get().format(begin));
					} else if (i == tables.length - 1) { // 最后一张表，查询<endTime
						perSql += " and " + BOABGMonBusiErrorLogBean.S_CreateDate + " <= :end";
						parameters.put("end", new Timestamp(end.getTime()));
						// 查询当前时间所在的表
						parameters.put(BOABGMonBusiErrorLogBean.S_CreateDate, fomatter.get().format(end));
					} else { // 中间表，查询表中全部
						parameters.put(BOABGMonBusiErrorLogBean.S_CreateDate, fomatter.get().format(tables[i]));
					}

					// 排序
					perSql += orderby();
					DataContainerInterface[] res = queryOnce(conn, flag, perSql, parameters, beantype);
					if (res != null && res.length > 0) {
						resList.addAll(Arrays.asList(res));
					}

				}
				LoggerDBUtils.closeDBConnection(conn);
				return resList.toArray(new DataContainerInterface[0]);
			}
		}
	}

	private static String orderby() {
		return " order by " + BOABGMonBusiErrorLogBean.S_CreateDate;
	}

	private static enum LogBeanType {
		LOG, ERR_LOG;
	}

	private static DataContainerInterface[] queryOnce(Connection conn, QueryFlag flag, String conditions,
			Map parameters, LogBeanType beanType) {
		switch (beanType) {
		case LOG:
			return queryLogOnce(conn, flag, conditions, parameters);
		case ERR_LOG:
		default:
			return queryErrLogOnce(conn, flag, conditions, parameters);
		}

	}

	private static BOABGMonBusiLogBean[] queryLogOnce(Connection conn, QueryFlag flag, String conditions, Map parameters) {
		BOABGMonBusiLogBean[] results = null;

		Lock readLock = busiLogLock.readLock();
		readLock.lock();
		try {
			results = (BOABGMonBusiLogBean[]) ServiceManager.getDataStore()
					.retrieve(conn, BOABGMonBusiLogBean.class, BOABGMonBusiLogBean.getObjectTypeStatic(), null,
							conditions, parameters, -1, -1, false, false, null);
		} catch (Exception e) {
			LOG.error("query error.", e);
		} finally {
			readLock.unlock();
		}

		// 将标记设置为已读
		List<BOABGMonBusiLogBean> unreadList = new ArrayList<BOABGMonBusiLogBean>();
		if (results != null && results.length > 0) {
			for (BOABGMonBusiLogBean r : results) {
				if (StringUtils.equalsIgnoreCase(r.getMonFlag(), "N")) {
					r.setMonFlag("Y");
					unreadList.add(r);
				}
			}

			// 把未读的记录设置为已读
			if (!unreadList.isEmpty()) {
				Lock writeLock = busiLogLock.writeLock();
				writeLock.lock();
				try {
					ServiceManager.getDataStore().saveBatch(conn, unreadList.toArray(new BOABGMonBusiLogBean[0]));
					conn.commit();
				} catch (Exception e) {
					LOG.error("change flag error.", e);
				} finally {
					writeLock.unlock();
				}
			}
		}
		return results;
	}

	private static synchronized BOABGMonBusiErrorLogBean[] queryErrLogOnce(Connection conn, QueryFlag flag,
			String conditions, Map parameters) {
		BOABGMonBusiErrorLogBean[] results = null;
		
		Lock readLock = busiErrLogLock.readLock();
		readLock.lock();
		try {
			results = (BOABGMonBusiErrorLogBean[]) ServiceManager.getDataStore().retrieve(conn,
					BOABGMonBusiErrorLogBean.class, BOABGMonBusiErrorLogBean.getObjectTypeStatic(), null, conditions,
					parameters, -1, -1, false, false, null);
		} catch (Exception e) {
			LOG.error("query error.", e);
		} finally {
			readLock.unlock();
		}
		// 将标记设置为已读
		List<BOABGMonBusiErrorLogBean> unreadList = new ArrayList<BOABGMonBusiErrorLogBean>();
		if (results != null && results.length > 0) {
			for (BOABGMonBusiErrorLogBean r : results) {
				if (StringUtils.equalsIgnoreCase(r.getMonFlag(), "N")) {
					r.setMonFlag("Y");
					unreadList.add(r);
				}
			}

			if (!unreadList.isEmpty()) {
				Lock writeLock = busiErrLogLock.writeLock();
				writeLock.lock();
				try {
					ServiceManager.getDataStore().saveBatch(conn, unreadList.toArray(new BOABGMonBusiErrorLogBean[0]));
					conn.commit();
				} catch (Exception e) {
					LOG.error("change flag error.", e);
				} finally {
					writeLock.unlock();
				}
			}
		}
		return results;
	}

	/**
	 * 为了应对比较繁忙的情况，对获取连接在失败后重试，以防止日志丢失
	 * 
	 * @return
	 */
	/*private static Connection getDBConnection() {
		Connection conn = null;

		// (1) 获取日志中心数据库连接失败后重试3次，防止造成日志数据丢失
		// (2) 这里不加间隔时间，因为获取连接本身就有超时时间
		for (int i = 0; i < LoggerConstants.GET_CONNECTION_RETRY_TIME; i++) {
			try {
				conn = ServiceManager.getSession().getNewConnection(LoggerConstants.LOG_CENTER_DATA_SOURCE);
			} catch (SQLException e) {
				LOG.error("get connection from datasource:" + LoggerConstants.LOG_CENTER_DATA_SOURCE + " error.", e);
				closeDBConnection(conn);
			}

			if (conn != null)
				break;
		}

		return conn;
	}

	*//**
	 * 将连接放回连接池
	 *//*
	private static void closeDBConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				LOG.error("close connection of datasource:" + LoggerConstants.LOG_CENTER_DATA_SOURCE + " error.", e);
			}
		}
	}

	public static void main(String[] args) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put(QueryKey.APP_CODE, "MON-EXE");
		// conditions.put(QueryKey.FLAG, "T");

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date end = LoggerDateUtils.getCurrentDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(end);
		cal.add(Calendar.MONTH, -2);

		java.util.Date begin = cal.getTime();

		System.out.println("begin:" + format.format(begin));
		System.out.println("end:" + format.format(end));

		conditions.put(QueryKey.BEGIN_DATE, begin);
		conditions.put(QueryKey.END_DATE, end);

		try {
			BOABGMonBusiLogBean[] res = qryBusinessLogByCondition(conditions);
			System.out.println("grucee2014:" + res.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		 * Object[] t; List<String> os= new ArrayList<String>(); os.add("abc");
		 * 
		 * t = os.toArray(new Object[0]); for (Object p : t) { System.out.println(p); }
		 
	}*/
}
