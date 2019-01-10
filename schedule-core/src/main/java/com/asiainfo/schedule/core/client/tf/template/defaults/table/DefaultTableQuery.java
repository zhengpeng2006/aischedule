package com.asiainfo.schedule.core.client.tf.template.defaults.table;

import com.ai.appframe2.bo.dialect.DialectFactory;
import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.complex.cache.impl.TableSplitCacheImpl;
import com.ai.appframe2.complex.cache.impl.TableSplitFunctionCacheImpl;
import com.ai.appframe2.complex.cache.impl.TableSplitMappingCacheImpl;
import com.ai.appframe2.complex.self.po.TableSplit;
import com.ai.appframe2.complex.self.po.TableSplitMapping;
import com.ai.appframe2.complex.tab.split.function.IFunction;
import com.asiainfo.schedule.core.client.TaskDealParam;
import com.asiainfo.schedule.core.client.tf.config.CfgTf;
import com.asiainfo.schedule.core.client.tf.config.CfgTfDtl;
import com.asiainfo.schedule.core.client.tf.config.CfgTfMapping;
import com.asiainfo.schedule.core.client.tf.config.table.Column;
import com.asiainfo.schedule.core.client.tf.config.table.PK;
import com.asiainfo.schedule.core.client.tf.util.QueryUtil;
import com.asiainfo.schedule.core.data.DataManagerFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultTableQuery {
	private transient static Logger log = LoggerFactory.getLogger(DefaultTableQuery.class);

	private CfgTf objCfgTf = null;

	private DefaultTable srcTable = null;
	private DefaultTable[] hisTable = null;
	private DefaultTable[] destTable = null;

	/**
	 * 构造函数
	 * 
	 * @param objInitParameter
	 *          InitParameter
	 * @param objCfgTf
	 *          CfgTf
	 * @throws Exception
	 */
	public DefaultTableQuery(TaskDealParam objInitParameter, CfgTf objCfgTf) throws Exception {
		this.objCfgTf = objCfgTf;
		int mod = objInitParameter.getTaskItemCount();
		int modVal = Integer.parseInt(objInitParameter.getTaskItem());
		// 源表分表数据
		boolean isSrcSplit = false;
		String srcTpTableName = null;
		String srcTpColumnName = null;
		int srcTpHour = 0;
		if (StringUtils.contains(objCfgTf.getSrcTableName(), "{") && StringUtils.contains(objCfgTf.getSrcTableName(), "}")) {
			// 判断源表是否包含分表规则
			String[] tmp = com.ai.appframe2.util.StringUtils.getParamFromString(objCfgTf.getSrcTableName(), "{", "}");
			if (tmp == null) {
				throw new Exception("源表配置了分表方式,必须含有分表规则");
			}

			if (tmp.length != 1) {
				throw new Exception("源表配置了分表方式,但是分表规则不唯一");
			}

			String[] split = StringUtils.split(tmp[0], "|");
			if (split == null) {
				throw new Exception("源表配置了分表方式,必须含有分表规则");
			}

			if (split.length != 3) {
				throw new Exception("源表配置了分表方式,但是分表规则不正确.比如配置成如下样式TEST_{YYYYMMM|CREATE_DATE|2}");
			}

			if (!split[0].equalsIgnoreCase("YYYYMM")) {
				throw new Exception("源表配置了分表方式,分表规则目前仅仅支持按月分表");
			}

			srcTpColumnName = split[1].trim().toUpperCase();

			if (!StringUtils.isNumeric(split[2].trim())) {
				throw new Exception("源表配置了分表方式,但是过度时间不为数字");
			}
			srcTpHour = Integer.parseInt(split[2].trim());

			String tmpSrcTableName = objCfgTf.getSrcTableName();
			// 时间
			DateFormat dateformat = new SimpleDateFormat("yyyyMM");
			String date = dateformat.format(new Date());
			String tmpTableName = StringUtils.substringBefore(tmpSrcTableName, "{") + date;

			// 将源表修改成实际表名
			objCfgTf.setSrcTableName(tmpTableName);
			srcTpTableName = "{" + StringUtils.substringBefore(tmpSrcTableName, "{") + "}";
			isSrcSplit = true;
		}

		// 源表
		// 主键
		this.srcTable = this.readTableInfo(objCfgTf.getSrcDbAcctCode(), objCfgTf.getSrcTableName(), DefaultTable.SRC);

		// 如果源表分表
		if (isSrcSplit) {
			this.srcTable.setSrcTpTableName(srcTpTableName);
			this.srcTable.setSrcTpColumnName(srcTpColumnName);
			this.srcTable.setSrcTpHour(srcTpHour);

			boolean isFoundColumn = false;
			Column[] cols = this.srcTable.getColumns();
			for (int i = 0; i < cols.length; i++) {
				if (cols[i].getName().equals(srcTpColumnName)) {
					isFoundColumn = true;
					break;
				}
			}

			if (!isFoundColumn) {
				throw new Exception("源表配置了分表方式,但是在源表中未找到列:" + srcTpColumnName);
			}
		}

		// 首先采用自定义主键
		if (!StringUtils.isBlank(objCfgTf.getPkColumns())) {
			// 自定义主键
			String[] pkStr = StringUtils.split(objCfgTf.getPkColumns(), ":");

			Column[] cols = new Column[pkStr.length];
			for (int i = 0; i < pkStr.length; i++) {
				String[] pk = StringUtils.split(pkStr[i], ",");
				if (pk == null || pk.length != 2) {
					throw new Exception("数据库连接编码:" + this.srcTable.getDbAcctCode() + ",表:" + this.srcTable.getTableName()
							+ "自定义主键不符合格式,格式如下(A,VARCHAR2:B,NUMBER)");
				}

				cols[i] = new Column();
				cols[i].setName(pk[0].trim().toUpperCase());
				cols[i].setSrcCloumnName(cols[i].getName().toUpperCase());
				if (pk[1].trim().equalsIgnoreCase("NUMBER")) {
					cols[i].setType(Long.TYPE);
				} else if (pk[1].trim().equalsIgnoreCase("BIGINT")) {
					cols[i].setType(Long.TYPE);
				} else if (pk[1].trim().equalsIgnoreCase("VARCHAR2")) {
					cols[i].setType(String.class);
				} else if (pk[1].trim().equalsIgnoreCase("VARCHAR")) {
					cols[i].setType(String.class);
				} else if (pk[1].trim().equalsIgnoreCase("CHAR")) {
					cols[i].setType(String.class);
				} else if (pk[1].trim().equalsIgnoreCase("DATE") || pk[1].trim().equalsIgnoreCase("TIME")
						|| pk[1].trim().equalsIgnoreCase("TIMESTAMP") || pk[1].trim().equalsIgnoreCase("DATETIME")) {
					cols[i].setType(Timestamp.class);
				} else {
					throw new Exception("自定义的列的类型" + pk[1].trim() + "不支持");
				}

				// rowid为系统的列
				if (cols[i].getName().equalsIgnoreCase(Column.ROW_ID_COLUMN_NAME)) {
					if (!DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
						throw new Exception("只有oracle数据库才支持rowid");
					}

					cols[i].setName(Column.ROW_ID_COLUMN_NAME);
					cols[i].setSrcCloumnName(Column.ROW_ID_COLUMN_NAME);
					if (!cols[i].getType().equals(String.class)) {
						throw new Exception("采用rowid为主键,那么类型必须为VARCHAR2或者VARCHAR");
					}
					if (pkStr.length != 1) {
						throw new Exception("采用rowid为主键,主键字段只能有rowid");
					}
					cols[i].setRowId(true);

					// 设置新的列
					Column[] srcCol = this.srcTable.getColumns();
					Column[] newCol = new Column[srcCol.length + 1];
					newCol[0] = cols[i];
					for (int j = 1; j <= srcCol.length; j++) {
						newCol[j] = srcCol[j - 1];
					}
					this.srcTable.setColumns(newCol);
				} else {
					Column[] srcColumn = this.srcTable.getColumns();
					for (int j = 0; j < srcColumn.length; j++) {
						if (cols[i].getName().equalsIgnoreCase(srcColumn[j].getName())) {
							if (!cols[i].getType().equals(srcColumn[j].getType())) {
								throw new Exception("采用自定义主键,主键字段:" + cols[i].getName() + ",类型:" + cols[i].getType() + "和数据库的类型:"
										+ srcColumn[j].getType() + "不匹配");
							}
						}
					}
				}
			}

			PK objPK = new PK();
			objPK.setColumns(cols);
			this.srcTable.setPk(objPK);

			if (log.isDebugEnabled()) {
				StringBuffer sb = new StringBuffer();
				Column[] objColumn = this.srcTable.getPk().getColumns();
				for (int i = 0; i < objColumn.length; i++) {
					sb.append("列:" + objColumn[i].getSrcCloumnName() + ",类型:" + objColumn[i].getType() + "\t");
				}
				log.debug("数据库连接编码:" + this.srcTable.getDbAcctCode() + ",表:" + this.srcTable.getTableName() + "自定义主键:" + sb.toString());
			}

		} else if (this.srcTable.getPk().getColumns() != null && this.srcTable.getPk().getColumns().length != 0) {
			// 其次采用表中的真实主键
			if (log.isDebugEnabled()) {
				StringBuffer sb = new StringBuffer();
				Column[] cols = this.srcTable.getPk().getColumns();
				for (int i = 0; i < cols.length; i++) {
					sb.append("列:" + cols[i].getSrcCloumnName() + ",类型:" + cols[i].getType() + "\t");
				}
				log.debug("数据库连接编码:" + this.srcTable.getDbAcctCode() + ",表:" + this.srcTable.getTableName() + "主键:" + sb.toString());
			}
		} else {
			// 表没有主键也没有自定义主键
			throw new Exception("数据库连接编码:" + this.srcTable.getDbAcctCode() + ",表:" + this.srcTable.getTableName() + "必须含有主键或者自定义主键");
		}
		// 主键完毕

		// 线程和processing_sql的校验
		// if (objCfgTf.getExecuteMethod().equalsIgnoreCase("THREAD") &&
		// StringUtils.isBlank(objCfgTf.getProcessingSql())) {
		// throw new Exception("编码:" + objCfgTf.getCfgTfCode() +
		// ",采用线程方式,必须配置processing_sql");
		// }

		if (this.srcTable.getPk().getColumns().length == 1) {
			// 一个列的主键
			// 查询SQL
			Column pk = this.srcTable.getPk().getColumns()[0];
			String condition = null;

			// 采用rowid为主键
			if (pk.isRowId()) {
				condition = " mod(dbms_utility.get_hash_value(rowid,1,10000)," + mod + ")=" + modVal
						+ appendRownum(objInitParameter.getEachFetchDataNum()) + " ";

				if (StringUtils.isBlank(this.objCfgTf.getQuerySql())) {
					this.srcTable.setQuerySql("select " + getSqlColumnsByColumn(this.srcTable.getColumns()) + "  from "
							+ this.srcTable.getTableName() + " where " + condition);
				} else {
					String sql = this.objCfgTf.getQuerySql();
					if (StringUtils.contains(sql, "{RowId}")) {
						sql = StringUtils.replace(sql, "{RowId}", pk.getSrcCloumnName());
						sql = StringUtils.replace(sql, "{TableName}", this.srcTable.getTableName());
						if (StringUtils.contains(sql, "{Condition}")) {
							sql = StringUtils.replace(sql, "{Condition}", condition);
						} else {
							boolean mode = StringUtils.contains(sql, "{MOD_MODE}");
							boolean value = StringUtils.contains(sql, "{MOD_VALUE}");
							if (mode && value) {
								sql = StringUtils.replace(sql, "{MOD_MODE}", String.valueOf(mod));
								sql = StringUtils.replace(sql, "{MOD_VALUE}", String.valueOf(modVal));
								sql = sql + appendRownum(objInitParameter.getEachFetchDataNum()) + " ";
							} else {
								throw new Exception("{MOD_MODE}和{MOD_VALUE}必须匹配");
							}
						}
						this.srcTable.setQuerySql(sql);
					} else {
						throw new Exception(
								"采用rowid为主键,查询sql必须包含{RowId},例如:select {RowId},aaa,bbb,ccc from {TableName} where {Condition} and state='U' ");
					}
				}
				// 完成SQL
				if (StringUtils.isBlank(this.objCfgTf.getFinishSql())) {
					this.srcTable.setFinishSql("delete from " + this.srcTable.getTableName() + " where " + pk.getName() + " = ? ");
				} else {
					String sql = this.objCfgTf.getFinishSql();

					sql = StringUtils.replace(sql, "{TableName}", this.srcTable.getTableName());
					this.srcTable.setFinishSql(sql);
				}

				// 处理中SQL
				// if (!StringUtils.isBlank(objCfgTf.getExecuteMethod()) &&
				// objCfgTf.getExecuteMethod().trim().equalsIgnoreCase("THREAD")
				// && !StringUtils.isBlank(this.objCfgTf.getProcessingSql())) {
				// String sql = this.objCfgTf.getProcessingSql();
				// sql = StringUtils.replace(sql, "{TableName}",
				// this.srcTable.getTableName());
				// this.srcTable.setProcessingSql(sql);
				// }
				if (!StringUtils.isBlank(this.objCfgTf.getProcessingSql())) {
					String sql = this.objCfgTf.getProcessingSql();
					sql = StringUtils.replace(sql, "{TableName}", this.srcTable.getTableName());
					this.srcTable.setProcessingSql(sql);
				}

				// 处理异常SQL
				this.processErrorSQL(new Column[] { pk });
			} else {
				if (pk.getType().equals(Long.TYPE) || pk.getType().equals(Integer.TYPE) || pk.getType().equals(Short.TYPE)
						|| pk.getType().equals(Double.TYPE) || pk.getType().equals(Float.TYPE)) {
					condition = " mod(" + pk.getName() + "," + mod + ")=" + modVal
							+ this.appendRownum(objInitParameter.getEachFetchDataNum()) + " ";
				} else {
					if (!DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
						throw new Exception("只有oracle数据库才支持非数字取mod");
					}
					condition = " mod(dbms_utility.get_hash_value(rowid,1,10000)," + mod + ")=" + modVal
							+ this.appendRownum(objInitParameter.getEachFetchDataNum()) + " ";
				}

				if (StringUtils.isBlank(this.objCfgTf.getQuerySql())) {
					this.srcTable.setQuerySql("select * from " + this.srcTable.getTableName() + " where " + condition);
				} else {
					String sql = this.objCfgTf.getQuerySql();
					sql = StringUtils.replace(sql, "{TableName}", this.srcTable.getTableName());

					if (StringUtils.contains(sql, "{Condition}")) {
						sql = StringUtils.replace(sql, "{Condition}", condition);
					} else {
						boolean mode = StringUtils.contains(sql, "{MOD_MODE}");
						boolean value = StringUtils.contains(sql, "{MOD_VALUE}");
						if (mode && value) {
							sql = StringUtils.replace(sql, "{MOD_MODE}", String.valueOf(mod));
							sql = StringUtils.replace(sql, "{MOD_VALUE}", String.valueOf(modVal));
							sql = sql + this.appendRownum(objInitParameter.getEachFetchDataNum()) + " ";
						} else {
							throw new Exception("{MOD_MODE}和{MOD_VALUE}必须匹配");
						}
					}

					this.srcTable.setQuerySql(sql);
				}

				// 完成SQL
				if (StringUtils.isBlank(this.objCfgTf.getFinishSql())) {
					this.srcTable.setFinishSql("delete from " + this.srcTable.getTableName() + " where " + pk.getName() + " = ? ");
				} else {
					String sql = this.objCfgTf.getFinishSql();
					sql = StringUtils.replace(sql, "{TableName}", this.srcTable.getTableName());
					this.srcTable.setFinishSql(sql);
				}

				// 处理中SQL
				// if (!StringUtils.isBlank(objCfgTf.getExecuteMethod()) &&
				// objCfgTf.getExecuteMethod().trim().equalsIgnoreCase("THREAD")
				// && !StringUtils.isBlank(this.objCfgTf.getProcessingSql())) {
				// String sql = this.objCfgTf.getProcessingSql();
				// sql = StringUtils.replace(sql, "{TableName}",
				// this.srcTable.getTableName());
				// this.srcTable.setProcessingSql(sql);
				// }
				if (!StringUtils.isBlank(this.objCfgTf.getProcessingSql())) {
					String sql = this.objCfgTf.getProcessingSql();
					sql = StringUtils.replace(sql, "{TableName}", this.srcTable.getTableName());
					this.srcTable.setProcessingSql(sql);
				}
				// 处理异常SQL
				this.processErrorSQL(new Column[] { pk });
			}
		} else {
			// 多列主键
			// 查询SQL
			Column[] pk = this.srcTable.getPk().getColumns();
			String condition = " mod(dbms_utility.get_hash_value(rowid,1,10000)," + mod + ")=" + modVal
					+ this.appendRownum(objInitParameter.getEachFetchDataNum()) + " ";
			if (StringUtils.isBlank(this.objCfgTf.getQuerySql())) {
				this.srcTable.setQuerySql("select * from " + this.srcTable.getTableName() + " where " + condition);
			} else {
				String sql = this.objCfgTf.getQuerySql();
				sql = StringUtils.replace(sql, "{TableName}", this.srcTable.getTableName());

				if (StringUtils.contains(sql, "{Condition}")) {
					sql = StringUtils.replace(sql, "{Condition}", condition);
				} else {
					boolean mode = StringUtils.contains(sql, "{MOD_MODE}");
					boolean value = StringUtils.contains(sql, "{MOD_VALUE}");
					if (mode && value) {
						sql = StringUtils.replace(sql, "{MOD_MODE}", String.valueOf(mod));
						sql = StringUtils.replace(sql, "{MOD_VALUE}", String.valueOf(modVal));
						sql = sql + this.appendRownum(objInitParameter.getEachFetchDataNum()) + " ";
					} else {
						throw new Exception("{MOD_MODE}和{MOD_VALUE}必须匹配");
					}
				}

				this.srcTable.setQuerySql(sql);
			}

			// 完成SQL
			if (StringUtils.isBlank(this.objCfgTf.getFinishSql())) {
				StringBuffer con = new StringBuffer();
				for (int i = 0; i < pk.length; i++) {
					con.append(pk[i].getName() + " = ? ");
					if (i != pk.length - 1) {
						con.append(" and ");
					}
				}

				this.srcTable.setFinishSql("delete from " + this.srcTable.getTableName() + " where " + con.toString());
			} else {
				String sql = this.objCfgTf.getFinishSql();
				sql = StringUtils.replace(sql, "{TableName}", this.srcTable.getTableName());
				this.srcTable.setFinishSql(sql);
			}

			// 处理中SQL
			// if (!StringUtils.isBlank(objCfgTf.getExecuteMethod()) &&
			// objCfgTf.getExecuteMethod().trim().equalsIgnoreCase("THREAD")
			// && !StringUtils.isBlank(this.objCfgTf.getProcessingSql())) {
			// String sql = this.objCfgTf.getProcessingSql();
			// sql = StringUtils.replace(sql, "{TableName}",
			// this.srcTable.getTableName());
			// this.srcTable.setProcessingSql(sql);
			// }

			if (!StringUtils.isBlank(this.objCfgTf.getProcessingSql())) {
				String sql = this.objCfgTf.getProcessingSql();
				sql = StringUtils.replace(sql, "{TableName}", this.srcTable.getTableName());
				this.srcTable.setProcessingSql(sql);
			}

			// 处理异常SQL
			/** @todo */
			this.processErrorSQL(pk);
		}

		// 处理统计sql
		if (StringUtils.isNotBlank(this.objCfgTf.getCountSql())) {
			String cntCondition = "";
			Column[] pk = this.srcTable.getPk().getColumns();
			if ((pk.length == 1 && pk[0].isRowId()) || pk.length > 1) {

				cntCondition = " mod(dbms_utility.get_hash_value(rowid,1,10000)," + mod + ")=" + modVal;
			} else {
				if (pk[0].getType().equals(Long.TYPE) || pk[0].getType().equals(Integer.TYPE) || pk[0].getType().equals(Short.TYPE)
						|| pk[0].getType().equals(Double.TYPE) || pk[0].getType().equals(Float.TYPE)) {
					cntCondition = " mod(" + pk[0].getName() + "," + mod + ")=" + modVal + " ";
				} else {
					cntCondition = " mod(dbms_utility.get_hash_value(rowid,1,10000)," + mod + ")=" + modVal + " ";
				}
			}

			String sql = this.objCfgTf.getCountSql();
			if (sql.equalsIgnoreCase("auto")) {
				this.srcTable.setCountSql("select count(1) from " + this.srcTable.getTableName() + " where " + cntCondition);
			} else {
				sql = StringUtils.replace(sql, "{TableName}", this.srcTable.getTableName());
				if (StringUtils.contains(sql, "{Condition}")) {
					sql = StringUtils.replace(sql, "{Condition}", cntCondition);
				} else {
					boolean mode = StringUtils.contains(sql, "{MOD_MODE}");
					boolean value = StringUtils.contains(sql, "{MOD_VALUE}");
					if (mode && value) {
						sql = StringUtils.replace(sql, "{MOD_MODE}", String.valueOf(mod));
						sql = StringUtils.replace(sql, "{MOD_VALUE}", String.valueOf(modVal)) + " ";
					} else {
						throw new Exception("{MOD_MODE}和{MOD_VALUE}必须匹配");
					}
				}
				this.srcTable.setCountSql(sql);
			}
		}
		// 统计sql end

		if (log.isInfoEnabled()) {
			log.info("源表对应的信息\n" + this.srcTable.toString());
		}

		// 历史和目的表
		CfgTfDtl[] objCfgTfDtl = objCfgTf.getObjCfgTfDtl();
		if (objCfgTfDtl == null || objCfgTfDtl.length == 0) {
			log.info("转移编码:" + objCfgTf.getCfgTfCode() + ",没有配置转移明细表的记录,需要仔细核查!");
			objCfgTfDtl = new CfgTfDtl[0];
		}

		List<DefaultTable> his = new ArrayList<DefaultTable>();
		List<DefaultTable> dest = new ArrayList<DefaultTable>();
		for (int i = 0; i < objCfgTfDtl.length; i++) {
			if (objCfgTfDtl[i].getTfType().equals(DefaultTable.HIS)) {
				DefaultTable obj = readTableInfo(objCfgTfDtl[i].getDbAcctCode(), objCfgTfDtl[i].getTableName(), DefaultTable.HIS);
				if (!checkHisTab(obj)) {
					throw new Exception("数据库连接编码:" + objCfgTf.getCfgTfCode() + ",历史表:" + objCfgTf.getSrcTableName() + "必须含有列"
							+ DefaultTable.HIS_TF_DATE + ",并且类型为Date");
				}
				obj.setFinishSql(createHisFinishSQL(obj));
				if (log.isInfoEnabled()) {
					log.info("历史表" + (his.size() + 1) + "对应的信息\n" + obj.toString());
				}

				his.add(obj);
			} else if (objCfgTfDtl[i].getTfType().equals(DefaultTable.DEST)) {
				DefaultTable obj = readTableInfo(objCfgTfDtl[i].getDbAcctCode(), objCfgTfDtl[i].getTableName(), DefaultTable.DEST);
				obj.setFinishSql(createDestFinishSQL(obj));
				if (log.isInfoEnabled()) {
					log.info("目的表" + (dest.size() + 1) + "对应的信息\n" + obj.toString());
				}
				dest.add(obj);
			} else {
				throw new Exception("无法识别的表类型:" + objCfgTfDtl[i].getTfType());
			}
		}

		this.hisTable = (DefaultTable[]) his.toArray(new DefaultTable[0]);
		this.destTable = (DefaultTable[]) dest.toArray(new DefaultTable[0]);

		// 检查表结构
		Column[] srcColumn = this.srcTable.getColumns();

		// 检查历史表
		for (int i = 0; i < this.hisTable.length; i++) {
			Column[] hisColumn = this.hisTable[i].getColumns();

			for (int k = 0; k < hisColumn.length; k++) {
				boolean isExist = false;
				for (int j = 0; j < srcColumn.length; j++) {
					if (hisColumn[k].getSrcCloumnName().equals(srcColumn[j].getName())) {
						isExist = true;
						break;
					}
				}
				if (!isExist && !hisColumn[k].getName().equalsIgnoreCase(DefaultTable.HIS_TF_DATE)) {
					throw new Exception("历史表:" + this.hisTable[i].getTableName() + ",列:" + hisColumn[k].getName() + ",无法找到对应的源表中的列");
				}
			}
		}

		// 检查目的表
		for (int i = 0; i < this.destTable.length; i++) {
			Column[] destColumn = this.destTable[i].getColumns();

			for (int k = 0; k < destColumn.length; k++) {
				boolean isExist = false;
				for (int j = 0; j < srcColumn.length; j++) {
					if (destColumn[k].getSrcCloumnName().equals(srcColumn[j].getName())) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					throw new Exception("目的表:" + this.destTable[i].getTableName() + ",列:" + destColumn[k].getName() + ",无法找到对应的源表中的列");
				}
			}
		}

	}

	/**
	 * 读表的信息
	 * 
	 * @param dbAcctCode
	 *          String
	 * @param tableName
	 *          String
	 * @param type
	 *          String
	 * @throws Exception
	 * @return Table
	 */
	private DefaultTable readTableInfo(String dbAcctCode, String tableName, String type) throws Exception {
		DefaultTable rtn = new DefaultTable();

		String oldTableName = new String(tableName.toUpperCase());
		boolean isHisSplit = false;
		// 历史表规则
		if (type.equals(DefaultTable.HIS)) {
			String[] tmpTableName = com.ai.appframe2.util.StringUtils.getParamFromString(tableName, "{", "}");
			if (tmpTableName == null || tmpTableName.length == 0) {
				// 历史表不分表
				// tableName = tableName;
			} else if (tmpTableName.length != 1) {
				throw new Exception("传入的表名不符合分表的表规则:" + tableName);
			} else {
				isHisSplit = true;
				tableName = tmpTableName[0].toUpperCase();
				log.error("历史表采用了分表,现在使用表名:" + tableName + ",获得表的信息,请确保此表存在!!!");
			}
		}
		// 结束历史表规则

		if (!QueryUtil.isExistTable(dbAcctCode, tableName)) {
			throw new Exception("数据库连接编码:" + dbAcctCode + ",表:" + tableName + "不存在");
		}

		Column[] columns = QueryUtil.readTableColumns(dbAcctCode, tableName);
		PK pk = QueryUtil.readTablePK(dbAcctCode, tableName, columns);

		//
		// 1、源表列的映射
		//
		if (type.equals(DefaultTable.SRC)) {
			for (int i = 0; i < columns.length; i++) {
				columns[i].setSrcCloumnName(columns[i].getName());
			}
		}
		//
		// 源表列的映射
		//

		//
		// 2、开始历史和目的列的映射
		//
		if (type.equals(DefaultTable.HIS) || type.equals(DefaultTable.DEST)) {
			// 映射列
			CfgTfDtl[] tmpCfgTfDtl = this.objCfgTf.getObjCfgTfDtl();

			CfgTfDtl objCfgTfDtl = null;
			for (int i = 0; i < tmpCfgTfDtl.length; i++) {
				if (isHisSplit) {
					if (tmpCfgTfDtl[i].getDbAcctCode().equals(dbAcctCode) && tmpCfgTfDtl[i].getTableName().equals(oldTableName)) {
						objCfgTfDtl = tmpCfgTfDtl[i];
						break;
					}
				} else {
					if (tmpCfgTfDtl[i].getDbAcctCode().equals(dbAcctCode) && tmpCfgTfDtl[i].getTableName().equals(tableName)) {
						objCfgTfDtl = tmpCfgTfDtl[i];
						break;
					}
				}
			}

			CfgTfMapping[] objCfgTfMapping = objCfgTfDtl.getObjCfgTfMapping();
			if (objCfgTfMapping == null || objCfgTfMapping.length == 0) {
				// 无需映射
				for (int i = 0; i < columns.length; i++) {
					columns[i].setSrcCloumnName(columns[i].getName());
				}
			} else {
				// 需要映射
				for (int i = 0; i < objCfgTfMapping.length; i++) {
					for (int j = 0; j < columns.length; j++) {
						if (objCfgTfMapping[i].getTfColumnName().equals(columns[j].getName())) {
							columns[j].setSrcCloumnName(objCfgTfMapping[i].getSrcColumnName());
							break;
						}
					}
				}
			}

		}
		//
		// 历史和目的列的映射结束
		//

		//
		// 3、裁减历史和目的列
		//
		if (type.equals(DefaultTable.HIS) || type.equals(DefaultTable.DEST)) {
			// 映射列
			CfgTfDtl[] tmpCfgTfDtl = this.objCfgTf.getObjCfgTfDtl();

			CfgTfDtl objCfgTfDtl = null;
			for (int i = 0; i < tmpCfgTfDtl.length; i++) {
				if (isHisSplit) {
					if (tmpCfgTfDtl[i].getDbAcctCode().equals(dbAcctCode) && tmpCfgTfDtl[i].getTableName().equals(oldTableName)) {
						objCfgTfDtl = tmpCfgTfDtl[i];
						break;
					}
				} else {
					if (tmpCfgTfDtl[i].getDbAcctCode().equals(dbAcctCode) && tmpCfgTfDtl[i].getTableName().equals(tableName)) {
						objCfgTfDtl = tmpCfgTfDtl[i];
						break;
					}
				}
			}

			List<Column> leftCols = new ArrayList<Column>();
			CfgTfMapping[] objCfgTfMapping = objCfgTfDtl.getObjCfgTfMapping();
			if (objCfgTfMapping != null && objCfgTfMapping.length != 0) {
				// 需要裁减
				for (int i = 0; i < columns.length; i++) {
					for (int j = 0; j < objCfgTfMapping.length; j++) {
						if (columns[i].getName().equals(objCfgTfMapping[j].getTfColumnName())) {
							leftCols.add(columns[i]);
						}
					}
				}
				columns = (Column[]) leftCols.toArray(new Column[0]);
			}
		}
		//
		// 裁减历史和目的列结束
		//

		if (isHisSplit) {
			rtn.setTableName(oldTableName);
			rtn.setHistSplit(true);

			TableSplit objTableSplit = (TableSplit) CacheFactory.get(TableSplitCacheImpl.class, tableName);
			if (objTableSplit == null) {
				throw new Exception("表:" + tableName + ",未找到分表配置");
			}

			TableSplitMapping[] objTableSplitMapping = (TableSplitMapping[]) CacheFactory.get(TableSplitMappingCacheImpl.class,
					tableName);
			if (objTableSplitMapping == null || objTableSplitMapping.length == 0) {
				throw new Exception("表:" + tableName + ",未找到分表映射配置");
			}

			rtn.setTableSplitMapping(objTableSplitMapping);

			IFunction[] objIFunction = new IFunction[objTableSplitMapping.length];
			for (int i = 0; i < objIFunction.length; i++) {
				objIFunction[i] = (IFunction) CacheFactory.get(TableSplitFunctionCacheImpl.class,
						objTableSplitMapping[i].getColumnConvertClass());
				if (objIFunction[i] == null) {
					throw new Exception("表:" + tableName + ",未找到分表函数");
				}
			}
			rtn.setIFunction(objIFunction);
		} else {
			rtn.setTableName(tableName);
		}
		rtn.setType(type);
		rtn.setDbAcctCode(dbAcctCode);
		rtn.setColumns(columns);
		rtn.setPk(pk);
		if (rtn.getColumns() == null || rtn.getColumns().length == 0) {
			throw new Exception("数据库连接编码:" + dbAcctCode + ",表:" + tableName + "的列为空");
		}

		return rtn;
	}

	/**
	 * 校验历史表
	 * 
	 * @param hisTable
	 *          Table
	 * @return boolean
	 */
	private boolean checkHisTab(DefaultTable hisTable) {
		boolean rtn = false;
		Column[] columns = hisTable.getColumns();
		for (int i = 0; i < columns.length; i++) {
			if (columns[i].getName().equals(DefaultTable.HIS_TF_DATE) && columns[i].getType().equals(Timestamp.class)) {
				rtn = true;
				break;
			}
		}
		return rtn;
	}

	/**
	 * 创建目的表的完成sql
	 * 
	 * @param obj
	 *          Table
	 * @return String
	 */
	private String createDestFinishSQL(DefaultTable obj) {
		List<String> names = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		Column[] cols = obj.getColumns();
		for (int i = 0; i < cols.length; i++) {
			names.add(cols[i].getName());
			values.add("?");
		}
		String strName = StringUtils.join(names.iterator(), ",");
		String strValue = StringUtils.join(values.iterator(), ",");
		String sql = "insert into " + obj.getTableName() + " (" + strName + ") values(" + strValue + ")";
		return sql;
	}

	/**
	 * 创建历史表的完成sql
	 * 
	 * @param obj
	 *          Table
	 * @return String
	 */
	private String createHisFinishSQL(DefaultTable obj) {
		List<String> names = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		Column[] cols = obj.getColumns();
		for (int i = 0; i < cols.length; i++) {
			names.add(cols[i].getName());
			if (cols[i].getName().equals(DefaultTable.HIS_TF_DATE)) {
				if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
					values.add("sysdate");
				} else if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.MYSQL)) {
					values.add("sysdate()");
				} else {
					throw new RuntimeException("仅仅支持oracle和mysql数据库");
				}
			} else {
				values.add("?");
			}
		}
		String strName = StringUtils.join(names.iterator(), ",");
		String strValue = StringUtils.join(values.iterator(), ",");

		String sql = null;
		try {
			if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)
					&& DataManagerFactory.getDataManager().getScheduleConfig().getTfHisInsertAppend().equalsIgnoreCase("true")) {
				sql = "insert into /*+append*/ " + obj.getTableName() + " (" + strName + ") values(" + strValue + ")";
			} else {
				sql = "insert into " + obj.getTableName() + " (" + strName + ") values(" + strValue + ")";
			}
		} catch (Exception ex) {
			throw new RuntimeException();
		}
		return sql;
	}

	/**
	 * 根据列获得列的sql
	 * 
	 * @param objColumn
	 *          Column[]
	 * @return String
	 */
	private String getSqlColumnsByColumn(Column[] objColumn) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objColumn.length; i++) {
			sb.append(objColumn[i].getSrcCloumnName());
			if (i != (objColumn.length - 1)) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * append信息
	 * 
	 * @param rownum
	 *          long
	 * @return String
	 * @throws Exception
	 */
	private String appendRownum(long rownum) throws Exception {
		if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
			return " and rownum <= " + rownum + " ";
		} else if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.MYSQL)) {
			return " limit " + rownum + " ";
		} else {
			throw new Exception("仅仅支持oracle和mysql数据库");
		}
	}

	/**
	 * 
	 * @param pk
	 *          Column
	 * @throws Exception
	 */
	private void processErrorSQL(Column[] pk) throws Exception {
		// 异常SQL
		if (!StringUtils.isBlank(this.objCfgTf.getErrorSql())) {
			if (this.objCfgTf.getErrorSql().equalsIgnoreCase("insert_err_delete_src")
					|| this.objCfgTf.getErrorSql().equalsIgnoreCase("insert_err_[YYYYMM]_delete_src")
					|| this.objCfgTf.getErrorSql().equalsIgnoreCase("insert_err_[YYMM]_delete_src")
					|| this.objCfgTf.getErrorSql().equalsIgnoreCase("insert_err_[YYYY]_delete_src")
					|| this.objCfgTf.getErrorSql().equalsIgnoreCase("insert_err_[YY]_delete_src")) {
				// insert_err_delete_src方式
				// 是否错误表存在
				// 错误表中是否存在err_msg字段

				// 拼装err表名
				String errTableName = null;
				String tmpErrTableName = null;
				if (StringUtils.contains(this.objCfgTf.getErrorSql(), "[YYYYMM]")) {
					DateFormat dateformat = new SimpleDateFormat("yyyyMM");
					String yyyymm = dateformat.format(new Date());
					errTableName = this.srcTable.getTableName() + "_err_[YYYYMM]";
					tmpErrTableName = this.srcTable.getTableName() + "_err_" + yyyymm;
				} else if (StringUtils.contains(this.objCfgTf.getErrorSql(), "[YYMM]")) {
					DateFormat dateformat = new SimpleDateFormat("yyMM");
					String yymm = dateformat.format(new Date());
					errTableName = this.srcTable.getTableName() + "_err_[YYMM]";
					tmpErrTableName = this.srcTable.getTableName() + "_err_" + yymm;
				} else if (StringUtils.contains(this.objCfgTf.getErrorSql(), "[YYYY]")) {
					DateFormat dateformat = new SimpleDateFormat("yyyy");
					String yyyy = dateformat.format(new Date());
					errTableName = this.srcTable.getTableName() + "_err_[YYYY]";
					tmpErrTableName = this.srcTable.getTableName() + "_err_" + yyyy;
				} else if (StringUtils.contains(this.objCfgTf.getErrorSql(), "[YY]")) {
					DateFormat dateformat = new SimpleDateFormat("yy");
					String yy = dateformat.format(new Date());
					errTableName = this.srcTable.getTableName() + "_err_[YY]";
					tmpErrTableName = this.srcTable.getTableName() + "_err_" + yy;
				} else {
					errTableName = this.srcTable.getTableName() + "_err";
					tmpErrTableName = this.srcTable.getTableName() + "_err";
				}

				// 检查表是否存在
				boolean isExist = QueryUtil.isExistTable(this.srcTable.getDbAcctCode(), tmpErrTableName);
				if (!isExist) {
					throw new Exception("配置了" + this.objCfgTf.getErrorSql() + "方式,但是错误表:" + tmpErrTableName + ",不存在");
				}

				boolean isHasErrMsgColumn = false;
				boolean isHasErrDate = false;
				Column[] errCols = QueryUtil.readTableColumns(this.srcTable.getDbAcctCode(), tmpErrTableName);
				for (int i = 0; i < errCols.length; i++) {
					if (errCols[i].getName().equalsIgnoreCase("err_msg") && errCols[i].getType().equals(String.class)) {
						isHasErrMsgColumn = true;
					} else if (errCols[i].getName().equalsIgnoreCase("err_date") && errCols[i].getType().equals(Timestamp.class)) {
						isHasErrDate = true;
					}
				}

				StringBuffer errorSQL = new StringBuffer();
				Column[] srcCols = this.srcTable.getColumns();
				List<String> list1 = new ArrayList<String>();
				for (int i = 0; i < srcCols.length; i++) {
					if (!srcCols[i].getName().equalsIgnoreCase(Column.ROW_ID_COLUMN_NAME)) {
						list1.add(srcCols[i].getName());
					}
				}
				List<String> list2 = new ArrayList<String>();
				for (int i = 0; i < srcCols.length; i++) {
					if (!srcCols[i].getName().equalsIgnoreCase(Column.ROW_ID_COLUMN_NAME)) {
						list2.add("{C_" + srcCols[i].getName().toUpperCase() + "}");
					}
				}

				if (isHasErrMsgColumn) {
					list1.add("err_msg");
					list2.add("{D_EXCEPTION_2000}");
				}
				if (isHasErrDate) {
					list1.add("err_date");

					if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
						list2.add("sysdate");
					} else if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.MYSQL)) {
						list2.add("sysdate()");
					} else {
						throw new Exception("仅仅支持oracle和mysql数据库");
					}
				}

				errorSQL.append("insert into " + errTableName + " (" + StringUtils.join(list1.iterator(), ",") + ")values("
						+ StringUtils.join(list2.iterator(), ",") + ")");
				errorSQL.append(";");

				if (pk.length == 1) {
					errorSQL.append("delete from " + this.srcTable.getTableName() + " where " + pk[0].getName() + " = {C_"
							+ pk[0].getName().toUpperCase() + "}");
				} else {
					List<String> list3 = new ArrayList<String>();
					for (int i = 0; i < pk.length; i++) {
						list3.add(pk[i].getName() + " = {C_" + pk[i].getName().toUpperCase() + "}");
					}
					errorSQL
							.append("delete from " + this.srcTable.getTableName() + " where " + StringUtils.join(list3.iterator(), " and "));
				}
				errorSQL.append(";");

				this.srcTable.setProcessErrorSql(errorSQL.toString());
			} else {
				String sql = this.objCfgTf.getErrorSql();
				sql = StringUtils.replace(sql, "{TableName}", this.srcTable.getTableName());
				this.srcTable.setProcessErrorSql(sql);
			}
		}
	}

	public DefaultTable getSrcTable() {
		return this.srcTable;
	}

	public DefaultTable[] getHisTable() {
		return this.hisTable;
	}

	public DefaultTable[] getDestTable() {
		return this.destTable;
	}


	public static void main(String[] args) throws Exception{
		Column[] columns = QueryUtil.readTableColumns("base", "ABG_MON_L_RECORD");
		System.out.println(columns.length);
	}
}
