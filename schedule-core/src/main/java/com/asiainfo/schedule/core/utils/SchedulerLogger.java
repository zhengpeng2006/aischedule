package com.asiainfo.schedule.core.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.appframe2.bo.dialect.DialectFactory;
import com.ai.appframe2.complex.util.RuntimeServerUtil;
import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.logger.db.LoggerDBUtils;
import com.asiainfo.logger.utils.LoggerUtils;
import com.asiainfo.schedule.core.data.DataManagerFactory;

public class SchedulerLogger {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerLogger.class);

	private static final String AI_SCHED_TASK_LOG = "AI.ZJ.AI_SCHED_TASK_LOG";

	private static final String UPDATE_SQL = " update ABG_AI_SCHED_TASK_LOG set finish_time = to_date(?,'yyyy-mm-dd hh24:mi:ss') , state ='F' where task_code = ? and job_id = ? ";

	private static final String INSERT_SQL = " insert into abg_ai_sched_task_log (SYSTEM_DOMAIN,SUBSYSTEM_DOMAIN,APP_SERVER_NAME,	"
			+ "TASK_CODE,TASK_NAME,TASK_VERSION,JOB_ID,START_TIME,STATE,CREATE_DATE,LOG_DATE)	values	"
			+ "('CRM','AMS',?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,	to_date(?,'yyyy-mm-dd hh24:mi:ss'), sysdate) ";
	
	private static final String MYSQL_INSERT_SQL = " insert into abg_ai_sched_task_log (SYSTEM_DOMAIN,SUBSYSTEM_DOMAIN,APP_SERVER_NAME,	"
			+ "TASK_CODE,TASK_NAME,TASK_VERSION,JOB_ID,START_TIME,STATE,CREATE_DATE,LOG_DATE)	values	"
			+ "('CRM','AMS',?,?,?,?,?,DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s'),?,	DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s'), sysdate()) ";

	static String splitChar = LoggerUtils.getColumnSeperator();

	public static void createTaskLog(String taskCode, String taskName, String taskVersion, String jobId, String startTime, String state) {

		if (logger.isInfoEnabled()) {
			logger.info("生成任务新建日志:" + taskCode + ",jobId:" + jobId);
		}
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (StringUtils.isBlank(state)) {
				state = "C";
			}
			conn = LoggerDBUtils.getDBConnection();
			if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
				ps = conn.prepareStatement(INSERT_SQL);
			} else {
				ps = conn.prepareStatement(MYSQL_INSERT_SQL);
			}
			ps.setString(1, RuntimeServerUtil.getServerName());
			ps.setString(2, taskCode);
			ps.setString(3, taskName);
			ps.setString(4, taskVersion);
			ps.setString(5, jobId);
			ps.setString(6, startTime);
			ps.setString(7, state);
			ps.setString(8, DateUtils.formatYYYYMMddHHmmss(DateUtils.getCurrentDate()));
			ps.execute();

			conn.commit();
		} catch (Exception ex) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException e) {
			}
			logger.error("任务新建日志出现异常！！" + jobId, ex);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	public static void finshTaskLog(String taskCode, String jobId, Date finishTime) {
		if (CommonUtils.isBlank(taskCode) || CommonUtils.isBlank(jobId)) {
			logger.error("taskCode || jobId is blank!!!");
			return;
		}

		if (logger.isInfoEnabled()) {
			logger.info("结束任务流水:" + taskCode + ",jobId:" + jobId);
		}

		if (finishTime == null) {
			finishTime = DateUtils.getCurrentDate();
		}
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = LoggerDBUtils.getDBConnection();
			ps = conn.prepareStatement(UPDATE_SQL);
			ps.setString(1, DateUtils.formatYYYYMMddHHmmss(finishTime));
			ps.setString(2, taskCode);
			ps.setString(3, jobId);
			ps.execute();

			conn.commit();
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {
			}
			logger.error("任务更新结束状态出现异常！！" + jobId, ex);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	public static void addTaskLogDtl(String taskCode, String taskVersion, String jobId, String loggerType, String taskItem, String operator, String opInfo,
			String exMsg) {
		StringBuilder msg = new StringBuilder("");
		// 任务编码
		msg.append(taskCode).append(splitChar);
		msg.append(taskVersion).append(splitChar);
		msg.append(jobId).append(splitChar);
		msg.append(loggerType).append(splitChar);
		msg.append(taskItem).append(splitChar);
		msg.append(exMsg == null ? "" : exMsg).append(splitChar);
		msg.append(operator).append(splitChar);
		msg.append(opInfo).append(splitChar);
		msg.append(DateUtils.formatYYYYMMddHHmmss(DateUtils.getCurrentDate()));
		if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
			LoggerUtils.addLog(AI_SCHED_TASK_LOG, msg.toString());
		} else {
			LoggerUtils.addLog("MYSQL.AI.ZJ.AI_SCHED_TASK_LOG", msg.toString());
		}
	}
	
	private final static String ERROR_SQL = "insert into sched_task_err_log_dtl_yyyyMM"
			+ "(SYSTEM_DOMAIN,SUBSYSTEM_DOMAIN,APP_SERVER_NAME,TASK_CODE,"
			+ "TASK_VERSION,JOB_ID,LOG_TYPE,TASK_ITEM,EX_MSG,ERR_LEVEL,OPERATOR,OP_INFO,LOG_DATE,CREATE_DATE) values "
			+ "('CRM','AMS',?,?,?,?,?,?,?,?,?,?,sysdate,to_date(?,'yyyy-mm-dd hh24:mi:ss'))";
	
	private final static String MYSQL_ERROR_SQL = "insert into sched_task_err_log_dtl_yyyyMM"
			+ "(SYSTEM_DOMAIN,SUBSYSTEM_DOMAIN,APP_SERVER_NAME,TASK_CODE,"
			+ "TASK_VERSION,JOB_ID,LOG_TYPE,TASK_ITEM,EX_MSG,ERR_LEVEL,OPERATOR,OP_INFO,LOG_DATE,CREATE_DATE) values "
			+ "('CRM','AMS',?,?,?,?,?,?,?,?,?,?,sysdate(),DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s'))";
	
	public static void addTaskErrorLogDtl(String taskCode, String taskVersion, String jobId, String loggerType, String taskItem, String operator, String opInfo,
			String errorLevel, String exMsg) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if ("false".equals(DataManagerFactory.getDataManager().getScheduleConfig().getTaskAlarmFlag())) {
				return;
			}
			conn = LoggerDBUtils.getDBConnection();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
			String dateSuffix = sf.format(new Date());
			String sql = "";
			if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
				sql = ERROR_SQL.replaceAll("yyyyMM", dateSuffix);
			} else {
				sql = MYSQL_ERROR_SQL.replaceAll("yyyyMM", dateSuffix);
			}
			ps = conn.prepareStatement(sql);
			ps.setString(1, operator);
			ps.setString(2, taskCode);
			ps.setString(3, taskVersion);
			ps.setString(4, jobId);
			ps.setString(5, loggerType);
			ps.setString(6, taskItem);
			ps.setString(7, exMsg);
			ps.setString(8, errorLevel);
			ps.setString(9, operator);
			ps.setString(10, opInfo);
			ps.setString(11, DateUtils.formatYYYYMMddHHmmss(DateUtils.getCurrentDate()));
			ps.execute();

			conn.commit();
		} catch (Exception ex) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException e) {
			}
			logger.error("！！", ex);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	}

}
