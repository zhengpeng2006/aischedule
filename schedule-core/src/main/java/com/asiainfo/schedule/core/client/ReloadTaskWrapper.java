package com.asiainfo.schedule.core.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.appframe2.bo.dialect.DialectFactory;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.complex.center.CenterInfo;
import com.ai.appframe2.complex.center.interfaces.ICenter;
import com.ai.appframe2.complex.datasource.DataSourceTemplate;
import com.asiainfo.schedule.core.client.tf.config.CfgTf;
import com.asiainfo.schedule.core.client.tf.config.CfgTfDtl;
import com.asiainfo.schedule.core.client.tf.config.table.Column;
import com.asiainfo.schedule.core.client.tf.config.table.PK;
import com.asiainfo.schedule.core.client.tf.template.reload.table.ReloadTable;
import com.asiainfo.schedule.core.client.tf.template.reload.table.ReloadTableQuery;
import com.asiainfo.schedule.core.client.tf.util.QueryUtil;
import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.utils.CommonUtils;

public class ReloadTaskWrapper extends TaskProcessor {
	private static final Logger log = LoggerFactory.getLogger(ReloadTaskWrapper.class);
	private CfgTf objCfgTf = null;

	private ReloadTable srcTableConfig = null;
	private ReloadTable[] destTableConfig = null;

	// reload的dest连接指定使用回滚段
	private static String DEST_ROLLBACK_SEGMENT = null;
	static {
		String destRollbackSegment = System.getProperty("appframe.tf.reload.dest_rollback_segment");
		if (!StringUtils.isBlank(destRollbackSegment)) {
			DEST_ROLLBACK_SEGMENT = destRollbackSegment;

			log.error("reload的dest连接使用指定回滚段:" + DEST_ROLLBACK_SEGMENT);
		}
	}

	ReloadTaskWrapper(TaskBean taskBean, String taskItem, String jobId, String itemJobId, Map<String, String> _param,
			String serverId) throws Exception {
		super(taskBean, taskItem, jobId, itemJobId, _param, serverId);
	}

	/**
	 * 工作
	 */
	@Override
	protected void excute() throws Exception {

		try {
			this.objCfgTf = DataManagerFactory.getDataManager().getCfgTfByCode(param.getTaskCode());
			if (objCfgTf == null || CommonUtils.isBlank(objCfgTf.getSrcDbAcctCode()) || CommonUtils.isBlank(objCfgTf.getSrcTableName())) {
				throw new Exception("任务tf的相关配置关键属性不能为空！" + param.getTaskCode());
			}
			String tabName = this.objCfgTf.getSrcTableName();
			if (!CommonUtils.isBlank(tabName) && tabName.indexOf("{REGION_CODE}") > -1) {
				if (!param.isSplitRegion() || CommonUtils.isBlank(param.getRegionCode())) {
					throw new Exception("任务未按地市分片，源表按地市分表无法执行！任务编码：" + param.getTaskCode() + ",源表名：" + tabName);
				}
				this.objCfgTf.setSrcTableName(tabName.replace("{REGION_CODE}", param.getRegionCode()));
			}
			// 处理数据源配置,按中心分
			String srcAcctCode = this.objCfgTf.getSrcDbAcctCode();
			if (!CommonUtils.isBlank(srcAcctCode) && srcAcctCode.indexOf(DataSourceTemplate.CENTER_FLAG) > -1) {
				if (!param.isSplitRegion() || CommonUtils.isBlank(param.getRegionCode())) {
					throw new Exception("任务配置的数据源带有分中心标识，但未按地市拆分！任务编码：" + param.getTaskCode() + ",数据源：" + srcAcctCode);
				}

				ICenter centerClass = DataManagerFactory.getDataManager().getScheduleConfig().getCenterClass();
				if (centerClass == null) {
					throw new Exception("当前任务[" + param.getTaskCode() + "]数据源设置了分中心，必须配置中心的实现类！");
				}
				CenterInfo centerInfo = centerClass.getCenterByValue(param.getRegionCode());
				if (centerInfo == null) {
					throw new Exception("根据分片地市编码找不到中心！" + param.getRegionCode());
				}
				// 设置实际数据源
				this.objCfgTf.setSrcDbAcctCode(srcAcctCode.replace(DataSourceTemplate.CENTER_FLAG, centerInfo.getCenter()));
				// 处理历史、目的表

				CfgTfDtl[] dtls = this.objCfgTf.getObjCfgTfDtl();
				if (dtls != null && dtls.length > 0) {
					for (CfgTfDtl dtl : dtls) {
						String dbAcctCode = dtl.getDbAcctCode();
						if (!CommonUtils.isBlank(dbAcctCode) && dbAcctCode.indexOf(DataSourceTemplate.CENTER_FLAG) > -1) {
							dtl.setDbAcctCode(dbAcctCode.replace(DataSourceTemplate.CENTER_FLAG, centerInfo.getCenter()));
						}
					}
				}
			}
			// 处理数据源配置,按地市分
			srcAcctCode = this.objCfgTf.getSrcTableName();
			if (!CommonUtils.isBlank(srcAcctCode) && srcAcctCode.indexOf("{REGION_CODE}") > -1) {
				if (!param.isSplitRegion() || CommonUtils.isBlank(param.getRegionCode())) {
					throw new Exception("任务未按地市分片，源表数据源配置有误：" + param.getTaskCode() + "," + srcAcctCode);
				}
				// 设置实际数据源
				this.objCfgTf.setSrcDbAcctCode(srcAcctCode.replace("{REGION_CODE}", param.getRegionCode()));
			}
			if (log.isDebugEnabled()) {
				log.debug("源表名：" + objCfgTf.getSrcTableName());
				log.debug("源数据源名：" + objCfgTf.getSrcDbAcctCode());
				CfgTfDtl[] dtls = this.objCfgTf.getObjCfgTfDtl();
				if (dtls != null && dtls.length > 0) {
					for (CfgTfDtl dtl : dtls) {
						log.debug(dtl.getTfType() + " 数据源:" + dtl.getDbAcctCode());
					}
				}
			}

			ReloadTableQuery objTableQuery = new ReloadTableQuery(param, this.objCfgTf);
			srcTableConfig = objTableQuery.getSrcTable();
			destTableConfig = objTableQuery.getDestTable();
		} catch (Exception ex) {
			// 记录失败到日志表
			CfgTfDtl[] objCfgTfDtl = this.objCfgTf.getObjCfgTfDtl();
			for (int i = 0; i < objCfgTfDtl.length; i++) {
				logOnError(objCfgTf.getSrcDbAcctCode(), objCfgTf.getSrcTableName(), objCfgTfDtl[i].getDbAcctCode(),
						objCfgTfDtl[i].getTableName());
			}

			throw ex;
		}
		for (int i = 0; i < this.destTableConfig.length; i++) {
			try {
				long start = 0;
				if (log.isInfoEnabled()) {
					log.info("开始源表:" + this.srcTableConfig.getTableName() + ",reload表:" + this.destTableConfig[i].getTableName() + "的对应");
					start = System.currentTimeMillis();
				}

				if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)
						&& isPKEqual(this.srcTableConfig, this.destTableConfig[i])) {
					// oracle,如果有相同的PK的列
					this.reloadByTableWithPK(this.srcTableConfig, this.destTableConfig[i]);
				} else {
					this.reloadByTable(this.srcTableConfig, this.destTableConfig[i]);
				}

				if (log.isInfoEnabled()) {
					log.info("完成源表:" + this.srcTableConfig.getTableName() + ",reload表:" + this.destTableConfig[i].getTableName()
							+ "的对应,耗时:" + (System.currentTimeMillis() - start));
				}

			} catch (Exception ex) {
				// 记录失败到日志表
				logOnError(this.srcTableConfig.getDbAcctCode(), this.srcTableConfig.getTableName(),
						this.destTableConfig[i].getDbAcctCode(), this.destTableConfig[i].getTableName());
				log.error("执行源表:" + this.srcTableConfig.getTableName() + ",reload表:" + this.destTableConfig[i].getTableName()
						+ ",reload分析失败");
				throw ex;
			}
		}

	}

	/**
	 * reload 不采用dblink和存储过程 纯insert/delete方式实现s
	 * 
	 * @param srcTable
	 *          ReloadTable
	 * @param destTable
	 *          ReloadTable
	 * @throws Exception
	 */
	private void reloadByTable(ReloadTable srcTable, ReloadTable destTable) throws Exception {
		Connection src = null;
		PreparedStatement srcPtmt = null;
		ResultSet srcRs = null;

		Connection dest = null;
		PreparedStatement destPtmt = null;
		ResultSet destRs = null;
		try {
			// 源表
			src = ServiceManager.getSession().getConnection(srcTable.getDbAcctCode());

			srcPtmt = src.prepareStatement("select * from " + srcTable.getTableName());
			srcPtmt.setFetchSize(1000);
			srcRs = srcPtmt.executeQuery();

			// 目的表
			Column[] destColumns = destTable.getColumns();
			// 可写连接
			dest = ServiceManager.getSession().getNewConnection(destTable.getDbAcctCode());

			// 执行回滚段方法
			if (!StringUtils.isBlank(DEST_ROLLBACK_SEGMENT)) {
				PreparedStatement tmp = dest.prepareStatement("set transaction use rollback segment " + DEST_ROLLBACK_SEGMENT);
				tmp.execute();
				tmp.close();
			}

			// 查询目的表的记录数
			long oldCount = -1;
			String selectSQL = "select count(*) from " + destTable.getTableName();
			destPtmt = dest.prepareStatement(selectSQL);
			destRs = destPtmt.executeQuery();
			while (destRs.next()) {
				oldCount = destRs.getLong(1);
				break;
			}

			if (oldCount < 0) {
				throw new Exception("无法获得目的表的记录数");
			}

			// 删除目的表的所有记录
			String deleteSQL = null;
			if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
				deleteSQL = "delete from " + destTable.getTableName() + " nologging";
			} else {
				deleteSQL = "delete from " + destTable.getTableName();
			}
			destPtmt = dest.prepareStatement(deleteSQL);
			destPtmt.execute();

			// 再插入所有源表的记录到目的表
			String insertSQL = null;
			if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
				insertSQL = "insert /*+ append */ into " + destTable.getTableName() + " (" + col2String(destColumns) + ") values ("
						+ col2Mark(destColumns) + ")";
			} else {
				insertSQL = "insert into " + destTable.getTableName() + " (" + col2String(destColumns) + ") values ("
						+ col2Mark(destColumns) + ")";
			}
			destPtmt = dest.prepareStatement(insertSQL);
			int newCount = 0;
			while (srcRs.next()) {
				newCount++;
				for (int i = 0; i < destColumns.length; i++) {
					destPtmt.setObject(i + 1, srcRs.getObject(destColumns[i].getSrcCloumnName()));
				}

				destPtmt.addBatch();
				// 1000条提交一次
				if (newCount % 1000 == 0) {
					destPtmt.executeBatch();
				}
			}
			destPtmt.executeBatch();

			// 记录日志信息
			logOnSuccess(dest, srcTable.getDbAcctCode(), srcTable.getTableName(), destTable.getDbAcctCode(), destTable.getTableName(),
					oldCount, newCount);

			dest.commit();
		} catch (Exception ex) {
			dest.rollback();
			throw ex;
		} finally {
			if (srcRs != null) {
				try {
					srcRs.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
			if (srcPtmt != null) {
				try {
					srcPtmt.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
			if (src != null) {
				try {
					src.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
			if (destRs != null) {
				try {
					destRs.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
			if (destPtmt != null) {
				try {
					destPtmt.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
			if (dest != null) {
				try {
					dest.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
		}
	}

	/**
	 * reload 不采用dblink和存储过程 纯insert/delete方式实现s
	 * 
	 * @param srcTable
	 *          ReloadTable
	 * @param destTable
	 *          ReloadTable
	 * @throws Exception
	 */
	private void reloadByTableWithPK(ReloadTable srcTable, ReloadTable destTable) throws Exception {
		Connection src = null;
		PreparedStatement srcPtmt = null;
		ResultSet srcRs = null;

		Connection dest = null;
		PreparedStatement destPtmt = null;
		ResultSet destRs = null;
		PreparedStatement destPtmt2 = null;
		try {
			// 源表
			src = ServiceManager.getSession().getConnection(srcTable.getDbAcctCode());

			srcPtmt = src.prepareStatement("select * from " + srcTable.getTableName());
			srcPtmt.setFetchSize(1000);
			srcRs = srcPtmt.executeQuery();

			// 目的表
			// create table destTable_tmp as select * from destTable where 1!=1;
			// truncate table destTable_tmp
			// 判断临时表的主键是否存在,不存在建立主键
			// insert into destTable_tmp by srcRs

			// 先目的表和源表比较,如果有不一致的，删除目的表中存在的数据
			// select * from destTable minus select * from destTable_tmp
			// delete from destTable where pk =?

			// 再源表和目的表比较，如果有不一致的，插入来自于源表中的数据
			// select * from destTable_tmp minus select * from destTable
			// insert into destTable select * from destTable_tmp where pk =?

			// ===============
			Column[] destColumns = destTable.getColumns();
			String tmpTableName = destTable.getTableName() + "_tmp";
			// 可写连接
			dest = ServiceManager.getSession().getNewConnection(destTable.getDbAcctCode());

			// 执行回滚段方法
			if (!StringUtils.isBlank(DEST_ROLLBACK_SEGMENT)) {
				PreparedStatement tmp = dest.prepareStatement("set transaction use rollback segment " + DEST_ROLLBACK_SEGMENT);
				tmp.execute();
				tmp.close();
			}

			boolean isExist = QueryUtil.isExistTable(destTable.getDbAcctCode(), tmpTableName);
			// 临时表如果存在，先把表删除，防止表结构有改动
			if (isExist) {
				destPtmt = dest.prepareStatement("drop table " + tmpTableName + " cascade constraints ");
				destPtmt.execute();
			}
			destPtmt = dest.prepareStatement("create table " + tmpTableName + " as select * from " + destTable.getTableName()
					+ " where 1!=1");
			destPtmt.execute();

			// 读取临时表的PK
			PK tmpPK = QueryUtil.readTablePK(destTable.getDbAcctCode(), tmpTableName, destColumns);
			if (tmpPK == null || tmpPK.getColumns() == null || tmpPK.getColumns().length == 0) {
				Column[] cols = destTable.getPk().getColumns();
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < cols.length; i++) {
					list.add(cols[i].getName());
				}
				String pkStr = StringUtils.join(list.iterator(), ",");

				// 可能表名过长，造成主键超长无法建立
				String pkName = "pk_" + System.currentTimeMillis();// pk_" + tmpTableName + "
				destPtmt = dest.prepareStatement("alter table " + tmpTableName + " add constraint " + pkName + " primary key (" + pkStr
						+ ") using index");
				destPtmt.execute();
			}

			// 再插入所有源表的记录到目的表
			String insertTmpSQL = null;
			if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
				insertTmpSQL = "insert /*+ append */ into " + tmpTableName + " (" + col2String(destColumns) + ") values ("
						+ col2Mark(destColumns) + ")";
			} else {
				insertTmpSQL = "insert into " + tmpTableName + " (" + col2String(destColumns) + ") values (" + col2Mark(destColumns)
						+ ")";
			}
			destPtmt = dest.prepareStatement(insertTmpSQL);
			int newCount = 0;
			while (srcRs.next()) {
				newCount++;
				for (int i = 0; i < destColumns.length; i++) {
					destPtmt.setObject(i + 1, srcRs.getObject(destColumns[i].getSrcCloumnName()));
				}

				destPtmt.addBatch();
				// 1000条提交一次
				if (newCount % 1000 == 0) {
					destPtmt.executeBatch();
				}
			}
			destPtmt.executeBatch();

			// minus1
			Column[] destPKCols = destTable.getPk().getColumns();
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < destPKCols.length; i++) {
				list.add(destPKCols[i].getName() + " = ? ");
			}
			String destPKStr = StringUtils.join(list.iterator(), " and ");
			String minus1 = "select * from  " + destTable.getTableName() + " minus select * from " + tmpTableName;
			String deleteDestSQL = null;
			if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
				deleteDestSQL = "delete from  " + destTable.getTableName() + " nologging where " + destPKStr;
			} else {
				deleteDestSQL = "delete from  " + destTable.getTableName() + " where " + destPKStr;
			}

			destPtmt = dest.prepareStatement(minus1);
			destRs = destPtmt.executeQuery();
			destPtmt2 = dest.prepareStatement(deleteDestSQL);
			int deleteCount = 0;
			while (destRs.next()) {
				deleteCount++;
				for (int i = 0; i < destPKCols.length; i++) {
					destPtmt2.setObject(i + 1, destRs.getObject(destPKCols[i].getName()));
				}

				destPtmt2.addBatch();
				// 1000条提交一次
				if (deleteCount % 1000 == 0) {
					destPtmt2.executeBatch();
				}
			}
			destPtmt2.executeBatch();

			// minus2
			String minus2 = "select * from  " + tmpTableName + " minus select * from " + destTable.getTableName();
			String insertDestSQL = "insert into  " + destTable.getTableName() + " select * from  " + tmpTableName + " where "
					+ destPKStr;

			destPtmt = dest.prepareStatement(minus2);
			destRs = destPtmt.executeQuery();
			destPtmt2 = dest.prepareStatement(insertDestSQL);
			int insertCount = 0;
			while (destRs.next()) {
				insertCount++;
				for (int i = 0; i < destPKCols.length; i++) {
					destPtmt2.setObject(i + 1, destRs.getObject(destPKCols[i].getName()));
				}

				destPtmt2.addBatch();
				// 1000条提交一次
				if (insertCount % 1000 == 0) {
					destPtmt2.executeBatch();
				}
			}
			destPtmt2.executeBatch();

			// 记录日志信息
			logOnSuccess(dest, srcTable.getDbAcctCode(), srcTable.getTableName(), destTable.getDbAcctCode(), destTable.getTableName(),
					deleteCount, insertCount);

			dest.commit();
		} catch (Exception ex) {
			dest.rollback();
			throw ex;
		} finally {
			if (srcRs != null) {
				try {
					srcRs.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
			if (srcPtmt != null) {
				try {
					srcPtmt.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
			if (src != null) {
				try {
					src.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
			if (destRs != null) {
				try {
					destRs.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
			if (destPtmt != null) {
				try {
					destPtmt.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
			if (destPtmt2 != null) {
				try {
					destPtmt.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
			if (dest != null) {
				try {
					dest.close();
				} catch (SQLException ex1) {
					throw new Exception(ex1);
				}
			}
		}
	}

	/**
	 * 
	 * @param dest
	 *          Connection
	 * @param srcDbAcctCode
	 *          String
	 * @param srcTable
	 *          String
	 * @param destDbAcctCode
	 *          String
	 * @param destTable
	 *          String
	 * @param oldCount
	 *          long
	 * @param newCount
	 *          long
	 * @throws Exception
	 */
	private void logOnSuccess(Connection dest, String srcDbAcctCode, String srcTable, String destDbAcctCode, String destTable,
			long oldCount, long newCount) throws Exception {

		PreparedStatement ptmt = null;
		try {

			long id = DialectFactory.getDialect().getNewId(dest, "RELOAD_LOG$SEQ");
			if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
				ptmt = dest
						.prepareStatement("insert into reload_log(log_id,src_tab,src_instance,dest_tab,mod_count,del_count,done_date) values (?,?,'',?,?,?,sysdate)");
			} else if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.MYSQL)) {
				ptmt = dest
						.prepareStatement("insert into reload_log(log_id,src_tab,src_instance,dest_tab,mod_count,del_count,done_date) values (?,?,'',?,?,?,sysdate())");
			} else {
				throw new Exception("仅仅支持oracle和mysql数据库");
			}
			ptmt.setLong(1, id);
			ptmt.setString(2, srcDbAcctCode + "." + srcTable);
			ptmt.setString(3, destDbAcctCode + "." + destTable);
			ptmt.setLong(4, newCount);
			ptmt.setLong(5, oldCount);
			ptmt.execute();
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (ptmt != null) {
				try {
					ptmt.close();
				} catch (SQLException ex1) {
					ex1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param srcDbAcctCode
	 *          String
	 * @param srcTable
	 *          String
	 * @param destDbAcctCode
	 *          String
	 * @param destTable
	 *          String
	 */
	private void logOnError(String srcDbAcctCode, String srcTable, String destDbAcctCode, String destTable) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		try {
			conn = ServiceManager.getSession().getNewConnection(destDbAcctCode);
			long id = DialectFactory.getDialect().getNewId(conn, "RELOAD_LOG$SEQ");
			if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
				ptmt = conn
						.prepareStatement("insert into reload_log(log_id,src_tab,src_instance,dest_tab,mod_count,del_count,done_date) values (?,?,'',?,-999,-999,sysdate)");
			} else if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.MYSQL)) {
				ptmt = conn
						.prepareStatement("insert into reload_log(log_id,src_tab,src_instance,dest_tab,mod_count,del_count,done_date) values (?,?,'',?,-999,-999,sysdate())");
			} else {
				throw new Exception("仅仅支持oracle和mysql数据库");
			}
			ptmt.setLong(1, id);
			ptmt.setString(2, srcDbAcctCode + "." + srcTable);
			ptmt.setString(3, destDbAcctCode + "." + destTable);
			ptmt.execute();
			conn.commit();
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (Throwable ex3) {
				log.error("回滚失败", ex3);
			}
			log.error("记录错误信息出错", ex);
		} finally {
			if (ptmt != null) {
				try {
					ptmt.close();
				} catch (SQLException ex1) {
					ex1.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex2) {
					ex2.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将列的转换成字符串
	 * 
	 * @param columns
	 *          Column[]
	 * @return String
	 */
	private String col2String(Column[] columns) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < columns.length; i++) {
			list.add(columns[i].getName());
		}
		return StringUtils.join(list.iterator(), ",");
	}

	/**
	 * 将列的转换成问号
	 * 
	 * @param columns
	 *          Column[]
	 * @return String
	 */
	private String col2Mark(Column[] columns) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < columns.length; i++) {
			list.add("?");
		}
		return StringUtils.join(list.iterator(), ",");
	}

	/**
	 * 是否PK相等
	 * 
	 * @param src
	 *          ReloadTable
	 * @param dest
	 *          ReloadTable
	 * @return boolean
	 */
	private boolean isPKEqual(ReloadTable src, ReloadTable dest) {
		boolean rtn = true;
		if (src.getPk() != null && src.getPk().getColumns() != null && src.getPk().getColumns().length > 0 && dest.getPk() != null
				&& dest.getPk().getColumns() != null && dest.getPk().getColumns().length > 0) {
			if (src.getPk().getColumns().length == dest.getPk().getColumns().length) {
				Column[] srcCol = src.getPk().getColumns();
				Column[] destCol = dest.getPk().getColumns();
				for (int i = 0; i < srcCol.length; i++) {
					boolean isFound = false;
					for (int j = 0; j < destCol.length; j++) {
						if (srcCol[i].getName().equalsIgnoreCase(destCol[j].getName())) {
							isFound = true;
							break;
						}
					}

					if (!isFound) {
						rtn = false;
						break;
					}
				}
			} else {
				rtn = false;
			}
		} else {
			rtn = false;
		}
		return rtn;
	}

	@Override
	public String getDealDescription() {
		return "processing";
	}
}
