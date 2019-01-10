package com.asiainfo.schedule.core.client.tf.template.defaults.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.complex.self.po.TableSplitMapping;
import com.ai.appframe2.complex.tab.split.SplitTableFactory;
import com.ai.appframe2.complex.tab.split.TableVars;
import com.ai.appframe2.complex.tab.split.function.IFunction;
import com.asiainfo.schedule.core.client.tf.config.table.AbstractTable;
import com.asiainfo.schedule.core.data.DataManagerFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DefaultTable extends AbstractTable {
	private transient static Logger log = LoggerFactory.getLogger(DefaultTable.class);

	public static final String HIS_TF_DATE = "TF_DATE";

	public static final String SRC = "SRC";

	public static final String HIS = "HIS";

	public static final String DEST = "DEST";

	// 历史表分表
	private boolean isHisSplit = false;
	private TableSplitMapping[] objTableSplitMapping = null;
	private IFunction[] objIFunction = null;
	// 历史表分表

	// 源表按帐期分表过渡期
	private String srcTpTableName;
	private int srcTpHour;
	private String srcTpColumnName;
	// 源表按帐期分表

	private String querySql = null;

	private String finishSql = null;

	private String processingSql = null;

	private String processErrorSql = null;

	private String countSql = null;

	private String type = null;

	/**
	 * 构造函数
	 */
	public DefaultTable() {
	}

	public String getFinishSql() {
		return finishSql;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public void setFinishSql(String finishSql) {
		this.finishSql = finishSql;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setHistSplit(boolean isHisSplit) {
		this.isHisSplit = isHisSplit;
	}

	public boolean isHisSplit() {
		return this.isHisSplit;
	}

	public void setTableSplitMapping(TableSplitMapping[] objTableSplitMapping) {
		this.objTableSplitMapping = objTableSplitMapping;
	}

	public TableSplitMapping[] getTableSplitMapping() {
		return this.objTableSplitMapping;
	}

	public void setIFunction(IFunction[] objIFunction) {
		this.objIFunction = objIFunction;
	}

	public IFunction[] getIFunction() {
		return this.objIFunction;
	}

	public String getProcessingSql() {
		return processingSql;
	}

	public void setProcessingSql(String processingSql) {
		this.processingSql = processingSql;
	}

	public String getProcessErrorSql() {
		return processErrorSql;
	}

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public int getSrcTpHour() {
		return srcTpHour;
	}

	public String getSrcTpColumnName() {
		return srcTpColumnName;
	}

	public String getSrcTpTableName() {
		return srcTpTableName;
	}

	public void setProcessErrorSql(String processErrorSql) {
		this.processErrorSql = processErrorSql;
	}

	public void setSrcTpColumnName(String srcTpColumnName) {
		this.srcTpColumnName = srcTpColumnName;
	}

	public void setSrcTpHour(int srcTpHour) {
		this.srcTpHour = srcTpHour;
	}

	public void setSrcTpTableName(String srcTpTableName) {
		this.srcTpTableName = srcTpTableName;
	}

	/**
	 * 查询
	 * 
	 * @throws Exception
	 * @return HashMap[]
	 */
	public List query() throws Exception {
		String sql = this.querySql;
		List rtn = new ArrayList();

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			conn = ServiceManager.getSession().getConnection(this.getDbAcctCode());

			// 判断是否源表分表
			if (!StringUtils.isBlank(this.srcTpTableName)) {
				Calendar rightNow = Calendar.getInstance();
				java.util.Date curDate = new java.util.Date();
				rightNow.setTime(curDate);
				int day = rightNow.get(Calendar.DAY_OF_MONTH);
				int hour = rightNow.get(Calendar.HOUR_OF_DAY);
				if (day == 1 && hour <= this.srcTpHour) {
					// 如果是月份的第一天，并且处于过渡期之内，union all这个月和上个月的表
					DateFormat dateformat = new SimpleDateFormat("yyyyMM");
					String orginialTableName = com.ai.appframe2.util.StringUtils.getParamFromString(this.srcTpTableName, "{", "}")[0];

					// 上个月
					Calendar lastMonth = Calendar.getInstance();
					lastMonth.setTime(curDate);
					lastMonth.set(Calendar.MONTH, rightNow.get(Calendar.MONTH) - 1);

					String lastyyyymm = dateformat.format(lastMonth.getTime());
					String last = sql;
					last = StringUtils.replaceOnce(last, this.getTableName(), orginialTableName + lastyyyymm);

					// 当月
					String cur = sql;
					String curyyyymm = dateformat.format(curDate);
					cur = StringUtils.replaceOnce(cur, this.getTableName(), orginialTableName + curyyyymm);

					sql = "(" + last + ") union all (" + cur + ")";
				} else {
					// 不处于过渡期之内，所以不执行union all
					String orginialTableName = com.ai.appframe2.util.StringUtils.getParamFromString(this.srcTpTableName, "{", "}")[0];
					DateFormat dateformat = new SimpleDateFormat("yyyyMM");

					String yyyymm = dateformat.format(curDate);
					sql = StringUtils.replaceOnce(sql, this.getTableName(), orginialTableName + yyyymm);
				}
			}
			// 判断是否源表分表结束

			if (log.isDebugEnabled()) {
				log.debug("执行query的SQL:" + sql);
			}

			ptmt = conn.prepareStatement(sql);

			int fetchSize = 0;
			String strFetchSize = DataManagerFactory.getDataManager().getScheduleConfig().getTfSrcQuerySize();
			if (!StringUtils.isBlank(strFetchSize) && StringUtils.isNumeric(strFetchSize)) {
				fetchSize = Integer.parseInt(strFetchSize);
			}

			if (fetchSize > 0) {
				ptmt.setFetchSize(fetchSize);
			}

			rs = ptmt.executeQuery();
			while (rs.next()) {
				HashMap map = new HashMap();
				for (int i = 0; i < columns.length; i++) {
					map.put(columns[i].getName(), rs.getObject(columns[i].getName()));
				}

				rtn.add(map);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ptmt != null) {
				ptmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return rtn;
	}

	public long count() throws Exception {
		String sql = this.countSql;

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		long result = 0;
		try {
			conn = ServiceManager.getSession().getConnection(this.getDbAcctCode());

			// 判断是否源表分表
			if (!StringUtils.isBlank(this.srcTpTableName)) {
				Calendar rightNow = Calendar.getInstance();
				java.util.Date curDate = new java.util.Date();
				rightNow.setTime(curDate);
				int day = rightNow.get(Calendar.DAY_OF_MONTH);
				int hour = rightNow.get(Calendar.HOUR_OF_DAY);
				if (day == 1 && hour <= this.srcTpHour) {
					// 如果是月份的第一天，并且处于过渡期之内，union all这个月和上个月的表
					DateFormat dateformat = new SimpleDateFormat("yyyyMM");
					String orginialTableName = com.ai.appframe2.util.StringUtils.getParamFromString(this.srcTpTableName, "{", "}")[0];

					// 上个月
					Calendar lastMonth = Calendar.getInstance();
					lastMonth.setTime(curDate);
					lastMonth.set(Calendar.MONTH, rightNow.get(Calendar.MONTH) - 1);

					String lastyyyymm = dateformat.format(lastMonth.getTime());
					String last = sql;
					last = StringUtils.replaceOnce(last, this.getTableName(), orginialTableName + lastyyyymm);

					// 当月
					String cur = sql;
					String curyyyymm = dateformat.format(curDate);
					cur = StringUtils.replaceOnce(cur, this.getTableName(), orginialTableName + curyyyymm);

					sql = "SELECT sum(CNT) AS CNT FROM ((" + last + ") union all (" + cur + "))";
				} else {
					// 不处于过渡期之内，所以不执行union all
					String orginialTableName = com.ai.appframe2.util.StringUtils.getParamFromString(this.srcTpTableName, "{", "}")[0];
					DateFormat dateformat = new SimpleDateFormat("yyyyMM");

					String yyyymm = dateformat.format(curDate);
					sql = StringUtils.replaceOnce(sql, this.getTableName(), orginialTableName + yyyymm);
				}
			}
			// 判断是否源表分表结束

			if (log.isDebugEnabled()) {
				log.debug("执行query的SQL:" + sql);
			}

			ptmt = conn.prepareStatement(sql);

			rs = ptmt.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
				break;
			}
			return result;

		} catch (Exception ex) {
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ptmt != null) {
				ptmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * 完成
	 * 
	 * @param maps
	 *          HashMap[]
	 * @throws Exception
	 */
	public void finish(HashMap[] maps) throws Exception {
		if (type.equals(SRC) && !StringUtils.isBlank(this.srcTpTableName)) {
			// 判断是否源表分表
			// 拆分map数组成多个月
			DateFormat dateformat = new SimpleDateFormat("yyyyMM");
			Map tmp = new HashMap();
			for (int i = 0; i < maps.length; i++) {
				String key = dateformat.format(((java.util.Date) maps[i].get(this.srcTpColumnName)));
				if (tmp.containsKey(key)) {
					List list = (List) tmp.get(key);
					list.add(maps[i]);
				} else {
					List list = new ArrayList();
					list.add(maps[i]);
					tmp.put(key, list);
				}
			}

			// 按照月进行多次finish
			Set set = tmp.keySet();
			for (Iterator iter = set.iterator(); iter.hasNext();) {
				String item = (String) iter.next();
				List list = (List) tmp.get(item);
				_finish((HashMap[]) list.toArray(new HashMap[0]), true);
			}
		} else {
			// 正常完成
			_finish(maps, false);
		}
	}

	/**
	 * 
	 * @param maps
	 *          HashMap[]
	 * @param isSrcSplit
	 *          boolean
	 * @throws Exception
	 */
	private void _finish(HashMap[] maps, boolean isSrcSplit) throws Exception {
		if (StringUtils.isBlank(this.finishSql)) {
			return;
		}

		Connection conn = null;
		PreparedStatement ptmt = null;
		try {
			conn = ServiceManager.getSession().getConnection(this.getDbAcctCode());

			String _fsql = this.finishSql;

			// 判断是否源表分表
			if (isSrcSplit) {
				String orginialTableName = com.ai.appframe2.util.StringUtils.getParamFromString(this.srcTpTableName, "{", "}")[0];
				DateFormat dateformat = new SimpleDateFormat("yyyyMM");
				// 已经按月分割好，所以仅仅取第一条数据
				String yyyymm = dateformat.format((java.util.Date) maps[0].get(this.srcTpColumnName));
				_fsql = StringUtils.replace(_fsql, this.getTableName(), orginialTableName + yyyymm);
			}
			// 判断是否源表分表结束

			// 这个选项的目的，是为了支持按天进行分区的表，查询条件带上分区字段
			// 绑定变量的顺序,一定是主键在前，宏定义变量在后
			// 例如:delete from {TableName} where work_id = ? and create_date =
			// {C_CREATE_DATE}
			String[] macro = null;
			if (StringUtils.contains(_fsql, "{C_")) {
				// 含有宏定义
				String[] tmp = com.ai.appframe2.util.StringUtils.getParamFromString(_fsql, "{", "}");
				List l = new ArrayList();
				for (int i = 0; i < tmp.length; i++) {
					if (tmp[i] != null && tmp[i].length() >= 2 && StringUtils.substring(tmp[i], 0, 2).equals("C_")) {
						_fsql = StringUtils.replace(_fsql, "{" + tmp[i] + "}", "?");
						l.add(StringUtils.substring(tmp[i], 2));
					}
				}
				macro = (String[]) l.toArray(new String[0]);
			}

			if (log.isDebugEnabled()) {
				log.debug("执行finish的SQL:" + _fsql);
			}

			// 考虑历史表分表的情况
			HashMap distinct = null;
			if (type.equals(HIS)) {
				if (isHisSplit && objTableSplitMapping != null && objIFunction != null
						&& objTableSplitMapping.length == objIFunction.length) {
					// 根据分表规则和分表函数计算出每一个sql，然后采用sql的剔重的规则
					distinct = new HashMap();

					for (int i = 0; i < maps.length; i++) {

						TableVars objTableVars = new TableVars();
						String tableName = this.objTableSplitMapping[0].getTableName();
						for (int j = 0; j < objTableSplitMapping.length; j++) {
							objTableVars.add(tableName, objTableSplitMapping[j].getColumnName(),
									objIFunction[j].convert(maps[i].get(objTableSplitMapping[j].getColumnName())));
						}

						String sql = SplitTableFactory.createQuerySQL(_fsql, objTableVars);
						if (distinct.containsKey(sql)) {
							List l = (List) distinct.get(sql);
							l.add(maps[i]);
							distinct.put(sql, l);
						} else {
							List l = new ArrayList();
							l.add(maps[i]);
							distinct.put(sql, l);
						}
					}
				} else {
					ptmt = conn.prepareStatement(_fsql);
				}
			} else {
				ptmt = conn.prepareStatement(_fsql);
			}
			// 结束考虑历史表分表的情况

			for (int i = 0; i < maps.length; i++) {
				if (type.equals(SRC)) {
					int j = 0;
					for (; j < this.pk.getColumns().length; j++) {
						ptmt.setObject(j + 1, maps[i].get(this.getPk().getColumns()[j].getName()));
					}

					if (macro != null && macro.length != 0) {
						for (int k = 0; k < macro.length; k++) {
							ptmt.setObject(j + k + 1, maps[i].get(macro[k]));
						}
					}

					ptmt.addBatch();
				} else if (type.equals(HIS)) {

					// 历史表考虑分表，一般来说历史表都是按照时间进行分表的
					if (this.isHisSplit && distinct != null) {
						Set key = distinct.keySet();
						for (Iterator iter = key.iterator(); iter.hasNext();) {
							String sql = (String) iter.next();
							List l = (List) distinct.get(sql);

							long start = 0;
							if (log.isDebugEnabled()) {
								start = System.currentTimeMillis();
							}

							// 批量提交历史
							PreparedStatement hisPtmt = null;
							try {
								hisPtmt = conn.prepareStatement(sql);
								for (Iterator iter2 = l.iterator(); iter2.hasNext();) {
									HashMap item = (HashMap) iter2.next();
									int pos = 1;
									for (int j = 0; j < this.columns.length; j++) {
										if (this.columns[j].getName().equals(DefaultTable.HIS_TF_DATE)) {
											continue;
										}
										hisPtmt.setObject(pos, item.get(this.columns[j].getSrcCloumnName()));
										pos++;
									}
									hisPtmt.addBatch();
								}

								hisPtmt.executeBatch();
							} catch (Exception ex) {
								throw ex;
							} finally {
								if (hisPtmt != null) {
									hisPtmt.close();
								}
							}

							if (log.isDebugEnabled()) {
								log.debug("历史表分表,批量提交耗时:" + (System.currentTimeMillis() - start) + ":ms");
							}

							// 批量提交历史
						}

						// 批量提交历史，已经批量处理过了
						break;
					} else {
						int pos = 1;
						for (int j = 0; j < this.columns.length; j++) {
							if (this.columns[j].getName().equals(DefaultTable.HIS_TF_DATE)) {
								continue;
							}
							ptmt.setObject(pos, maps[i].get(this.columns[j].getSrcCloumnName()));
							pos++;
						}
						ptmt.addBatch();
					}
				} else if (type.equals(DEST)) {
					for (int j = 0; j < this.columns.length; j++) {
						ptmt.setObject(j + 1, maps[i].get(this.columns[j].getSrcCloumnName()));
					}
					ptmt.addBatch();
				} else {
					throw new Exception("无法识别的类型:" + type);
				}
			}

			if (ptmt != null) {
				ptmt.executeBatch();
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (ptmt != null) {
				ptmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * 处理中
	 * 
	 * @param maps
	 *          HashMap[]
	 * @throws Exception
	 */
	public void processing(HashMap[] maps) throws Exception {
		if (StringUtils.isBlank(this.processingSql)) {
			return;
		}

		Connection conn = null;
		PreparedStatement ptmt = null;
		try {
			conn = ServiceManager.getSession().getConnection(this.getDbAcctCode());

			// 绑定变量的顺序,一定是主键在前，宏定义变量在后
			// 例如:update {TableName} set state='P' where work_id = ? and create_date =
			// {C_CREATE_DATE}
			String _fsql = this.processingSql;
			String[] macro = null;
			if (StringUtils.contains(_fsql, "{C_")) {
				// 含有宏定义
				String[] tmp = com.ai.appframe2.util.StringUtils.getParamFromString(_fsql, "{", "}");
				List l = new ArrayList();
				for (int i = 0; i < tmp.length; i++) {
					if (tmp[i] != null && tmp[i].length() >= 2 && StringUtils.substring(tmp[i], 0, 2).equals("C_")) {
						_fsql = StringUtils.replace(_fsql, "{" + tmp[i] + "}", "?");
						l.add(StringUtils.substring(tmp[i], 2));
					}
				}
				macro = (String[]) l.toArray(new String[0]);
			}

			if (log.isDebugEnabled()) {
				log.debug("执行processing的SQL:" + _fsql);
			}

			ptmt = conn.prepareStatement(_fsql);
			for (int i = 0; i < maps.length; i++) {
				int j = 0;
				for (; j < this.pk.getColumns().length; j++) {
					ptmt.setObject(j + 1, maps[i].get(this.getPk().getColumns()[j].getName()));
				}

				if (macro != null && macro.length != 0) {
					for (int k = 0; k < macro.length; k++) {
						ptmt.setObject(j + k + 1, maps[i].get(macro[k]));
					}
				}

				ptmt.addBatch();
			}

			ptmt.executeBatch();
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (ptmt != null) {
				ptmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * 处理失败
	 * 
	 * @param maps
	 *          HashMap[]
	 * @param ex
	 *          Throwable
	 * @throws Exception
	 */
	public void processError(HashMap[] maps, Throwable ex) throws Exception {
		if (maps == null || maps.length == 0 || StringUtils.isBlank(this.processErrorSql)) {
			return;
		}

		String[] sql = StringUtils.split(this.processErrorSql, ";");
		for (int i = 0; i < sql.length; i++) {
			String curSql = sql[i];
			// 判断一下是否含有err表分表的情况
			if (StringUtils.contains(curSql, "[YYYYMM]")) {
				DateFormat dateformat = new SimpleDateFormat("yyyyMM");
				String yyyymm = dateformat.format(new java.util.Date());
				curSql = StringUtils.replace(curSql, "[YYYYMM]", yyyymm);
			} else if (StringUtils.contains(curSql, "[YYMM]")) {
				DateFormat dateformat = new SimpleDateFormat("yyMM");
				String yymm = dateformat.format(new java.util.Date());
				curSql = StringUtils.replace(curSql, "[YYMM]", yymm);
			} else if (StringUtils.contains(curSql, "[YYYY]")) {
				DateFormat dateformat = new SimpleDateFormat("yyyy");
				String yyyy = dateformat.format(new java.util.Date());
				curSql = StringUtils.replace(curSql, "[YYYY]", yyyy);
			} else if (StringUtils.contains(curSql, "[YY]")) {
				DateFormat dateformat = new SimpleDateFormat("yy");
				String yy = dateformat.format(new java.util.Date());
				curSql = StringUtils.replace(curSql, "[YY]", yy);
			}

			processErrorBySingleSql(maps, curSql, ex);
		}
	}

	/**
	 * 
	 * @param maps
	 *          HashMap[]
	 * @param sql
	 *          String
	 * @param _ex
	 *          Throwable
	 * @throws Exception
	 */
	private void processErrorBySingleSql(HashMap[] maps, String sql, Throwable _ex) throws Exception {
		if (StringUtils.isBlank(sql)) {
			return;
		}

		Connection conn = null;
		PreparedStatement ptmt = null;
		try {
			conn = ServiceManager.getSession().getConnection(this.getDbAcctCode());

			// 绑定变量的顺序,一定是主键在前，宏定义变量在后
			// 例如:update {TableName} set state='P' ,remarks={D_EXCEPTION_4000} where
			// work_id = {C_WORK_ID} and create_date = {C_CREATE_DATE}
			String _fsql = sql;

			String[] tmp = com.ai.appframe2.util.StringUtils.getParamFromString(_fsql, "{", "}");
			ArrayList[] parameter = new ArrayList[maps.length];
			for (int i = 0; i < parameter.length; i++) {
				parameter[i] = new ArrayList();
				for (int j = 0; j < tmp.length; j++) {
					if (tmp[j].indexOf("D_EXCEPTION") != -1) {
						// 异常
						int len = 1900;
						String l = StringUtils.substringAfter(tmp[j], "D_EXCEPTION_");
						if (!StringUtils.isBlank(l) && StringUtils.isNumeric(l)) {
							len = Integer.parseInt(l);
						}
						parameter[i].add(getExceptionText(len, _ex));
					} else {
						String name = StringUtils.substringAfter(tmp[j], "C_");
						parameter[i].add(maps[i].get(name));
					}
				}
			}

			for (int i = 0; i < tmp.length; i++) {
				_fsql = StringUtils.replace(_fsql, "{" + tmp[i] + "}", "?");
			}

			if (log.isDebugEnabled()) {
				log.debug("执行processError的SQL:" + _fsql);
				for (int i = 0; i < parameter.length; i++) {
					log.debug("第" + i + "条数据:" + parameter[i]);
				}
			}

			ptmt = conn.prepareStatement(_fsql);

			for (int i = 0; i < maps.length; i++) {
				int j = 1;
				for (Iterator iter = parameter[i].iterator(); iter.hasNext();) {
					Object value = (Object) iter.next();
					ptmt.setObject(j, value);
					j++;
				}
				ptmt.addBatch();
			}
			ptmt.executeBatch();
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (ptmt != null) {
				ptmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * 获得异常的文本信息
	 * 
	 * @param length
	 *          int
	 * @param ex
	 *          Throwable
	 * @return String
	 */
	private static String getExceptionText(int length, Throwable ex) {
		return splitByLength(ExceptionUtils.getFullStackTrace(ExceptionUtils.getRootCause(new Exception(ex))), length)[0];
	}

	/**
	 * 按照长度分割
	 * 
	 * @param xml
	 *          String
	 * @param length
	 *          int
	 * @return String[]
	 */
	private static String[] splitByLength(String xml, int length) {
		String[] rtn = null;

		int count = (xml.length() / length + 1);
		rtn = new String[count];
		for (int i = 0; i < count; i++) {
			rtn[i] = StringUtils.substring(xml, length * i, length * (i + 1));
		}
		return rtn;
	}

	/**
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("数据库连接编码:" + this.getDbAcctCode() + ",表:" + this.getTableName() + ",类型:" + this.getType() + "\n");
		for (int i = 0; i < this.pk.getColumns().length; i++) {
			sb.append("主键对应的列:" + this.pk.getColumns()[i].getName() + ",类型:" + this.pk.getColumns()[i].getType() + "\n");
		}

		for (int i = 0; i < this.columns.length; i++) {
			sb.append("列:" + this.columns[i].getName() + ",类型:" + this.columns[i].getType() + "\n");
		}

		if (this.type.equals(SRC)) {
			sb.append("查询SQL:" + this.querySql + "\n");
			sb.append("完成SQL:" + this.finishSql + "\n");
			sb.append("统计SQL:" + this.countSql + "\n");
			if (!StringUtils.isBlank(this.processingSql)) {
				sb.append("处理中SQL:" + this.processingSql + "\n");
			}

			if (!StringUtils.isBlank(this.processErrorSql)) {
				String[] tmp = StringUtils.split(this.processErrorSql, ";");
				for (int i = 0; i < tmp.length; i++) {
					sb.append("异常的SQL:" + tmp[i] + "\n");
				}
			}

		} else {
			sb.append("完成SQL:" + this.finishSql + "\n");
		}

		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();

		for (int p = 0; p < 10000; p++) {

			String _fsql = "delete from {TableName} where work_id = ? and  create_date = {C_CREATE_DATE}";
			String[] macro = null;
			if (StringUtils.contains(_fsql, "{C_")) {
				// 含有宏定义
				String[] tmp = com.ai.appframe2.util.StringUtils.getParamFromString(_fsql, "{", "}");
				List l = new ArrayList();
				for (int i = 0; i < tmp.length; i++) {
					if (tmp[i] != null && tmp[i].length() >= 2 && StringUtils.substring(tmp[i], 0, 2).equals("C_")) {
						_fsql = StringUtils.replace(_fsql, "{" + tmp[i] + "}", "?");
						l.add(StringUtils.substring(tmp[i], 2));
					}
				}
				macro = (String[]) l.toArray(new String[0]);
			}

			// System.out.println(_fsql);
			for (int i = 0; i < macro.length; i++) {
				// System.out.println(macro[i]);
			}
		}

		System.out.println("耗时:" + (System.currentTimeMillis() - start) + ":ms");

	}
}
