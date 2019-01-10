package com.asiainfo.schedule.core.client.tf.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.appframe2.bo.dialect.DialectFactory;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.schedule.core.client.tf.config.table.Column;
import com.asiainfo.schedule.core.client.tf.config.table.PK;

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class QueryUtil {
	private transient static Logger log = LoggerFactory.getLogger(QueryUtil.class);

	private QueryUtil() {
	}

	/**
	 * 读表的主键
	 * 
	 * @param dbAcctCode
	 *          String
	 * @param tableName
	 *          String
	 * @param columns
	 *          Column[]
	 * @throws Exception
	 * @return PK
	 */
	public static PK readTablePK(String dbAcctCode, String tableName, Column[] columns) throws Exception {
		if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
			return readOracleTablePK(dbAcctCode, tableName, columns);
		} else if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.MYSQL)) {
			return readMySQLTablePK(dbAcctCode, tableName, columns);
		} else {
			throw new Exception("仅仅支持oracle和mysql数据库");
		}
	}

	/**
	 * 
	 * @param dbAcctCode
	 *          String
	 * @param tableName
	 *          String
	 * @param columns
	 *          Column[]
	 * @return PK
	 * @throws Exception
	 */

	private static PK readOracleTablePK(String dbAcctCode, String tableName, Column[] columns) throws Exception {
		PK pk = new PK();

		List cols = new ArrayList();
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rsColumn = null;
		try {
			conn = ServiceManager.getSession().getConnection(dbAcctCode);

			HashMap parameter1 = new HashMap();
			parameter1.put("owner", conn.getMetaData().getUserName().toUpperCase());
			parameter1.put("object_name", tableName.toUpperCase());

			HashMap[] type = query(
					conn,
					"select object_type from all_objects a where a.owner = :owner and a.object_name= :object_name and a.object_type in( 'TABLE', 'SYNONYM') ",
					parameter1, new String[] { "OBJECT_TYPE" });

			if (type.length == 0) {
				log.error("没有获得主键");
				return null;
			}

			if (type.length != 1) {
				throw new Exception("获得主键出错,查询出多种类型出来");
			}

			String owner = null;
			String ownerTableName = null;

			String tableType = (String) type[0].get("OBJECT_TYPE");
			if (tableType.equalsIgnoreCase("SYNONYM")) {
				HashMap parameter2 = new HashMap();
				parameter2.put("synonym_name", tableName.toUpperCase());

				HashMap[] syMap = query(conn, "select * from user_synonyms where SYNONYM_name = :synonym_name ", parameter2,
						new String[] { "TABLE_OWNER", "TABLE_NAME" });

				if (syMap.length != 1) {
					throw new Exception("查询出多个同义词出来");
				}

				owner = (String) syMap[0].get("TABLE_OWNER");
				ownerTableName = (String) syMap[0].get("TABLE_NAME");
			} else if (tableType.equalsIgnoreCase("TABLE")) {
				owner = conn.getMetaData().getUserName().toUpperCase();
				ownerTableName = tableName.toUpperCase();
			} else {
				throw new Exception("不能识别的类型:" + tableType);
			}

			ptmt = conn
					.prepareStatement("select u2.*  from all_constraints u1, all_cons_columns u2 where u1.owner = ? and u2.owner = ? and u1.table_name = ? and u1.constraint_type = 'P' and u1.constraint_name = u2.constraint_name");
			ptmt.setString(1, owner);
			ptmt.setString(2, owner);
			ptmt.setString(3, ownerTableName);

			rsColumn = ptmt.executeQuery();
			// 产生列
			while (rsColumn.next()) {
				for (int j = 0; j < columns.length; j++) {
					if (rsColumn.getString(4).equals(columns[j].getName())) {
						Column obj = columns[j].copy();
						cols.add(obj);
						break;
					}
				}
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (rsColumn != null) {
				rsColumn.close();
			}
			if (ptmt != null) {
				ptmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		pk.setColumns((Column[]) cols.toArray(new Column[0]));
		return pk;
	}

	/**
	 * 
	 * @param dbAcctCode
	 *          String
	 * @param tableName
	 *          String
	 * @param columns
	 *          Column[]
	 * @return PK
	 * @throws Exception
	 */
	private static PK readMySQLTablePK(String dbAcctCode, String tableName, Column[] columns) throws Exception {
		PK pk = new PK();

		List cols = new ArrayList();
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ServiceManager.getSession().getConnection(dbAcctCode);
			rs = conn.getMetaData().getPrimaryKeys(null, conn.getMetaData().getUserName().toUpperCase(), tableName.toUpperCase());
			// 产生列
			while (rs.next()) {
				for (int j = 0; j < columns.length; j++) {
					if (rs.getString("COLUMN_NAME").equals(columns[j].getName())) {
						Column obj = columns[j].copy();
						cols.add(obj);
						break;
					}
				}
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		pk.setColumns((Column[]) cols.toArray(new Column[0]));
		return pk;
	}

	/**
	 * 
	 * @param conn
	 *          Connection
	 * @param sql
	 *          String
	 * @param parameters
	 *          HashMap
	 * @param columns
	 *          String[]
	 * @throws Exception
	 * @return HashMap[]
	 */
	private static HashMap[] query(Connection conn, String sql, HashMap parameters, String[] columns) throws Exception {
		List list = new ArrayList();
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {

			String[] parameterNames = com.ai.appframe2.util.StringUtils.getParamFromString(sql, ":", " ");
			sql = com.ai.appframe2.util.StringUtils.replaceParamString(sql, parameterNames, "? ", ":", " ");

			ptmt = conn.prepareStatement(sql);

			for (int i = 0; i < parameterNames.length; i++) {
				ptmt.setObject(i + 1, parameters.get(parameterNames[i]));
			}

			rs = ptmt.executeQuery();
			// 产生列
			while (rs.next()) {
				HashMap data = new HashMap();

				for (int j = 0; j < columns.length; j++) {
					data.put(columns[j], rs.getObject(columns[j]));
				}
				list.add(data);
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
		}

		return (HashMap[]) list.toArray(new HashMap[0]);
	}

	/**
	 * 读表的列
	 * 
	 * @param dbAcctCode
	 *          String
	 * @param tableName
	 *          String
	 * @throws Exception
	 * @return Column[]
	 */
	public static Column[] readTableColumns(String dbAcctCode, String tableName) throws Exception {
		if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
			return readOracleTableColumns(dbAcctCode, tableName);
		} else if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.MYSQL)) {
			return readMySQLTableColumns(dbAcctCode, tableName);
		} else {
			throw new Exception("仅仅支持oracle和mysql数据库");
		}
	}

	/**
	 * 
	 * @param dbAcctCode
	 *          String
	 * @param tableName
	 *          String
	 * @return Column[]
	 * @throws Exception
	 */
	private static Column[] readOracleTableColumns(String dbAcctCode, String tableName) throws Exception {
		List list = new ArrayList();

		Connection conn = null;
		ResultSet rsColumn = null;
		try {

			conn = ServiceManager.getSession().getConnection(dbAcctCode);

			HashMap parameters1 = new HashMap();
			parameters1.put("owner", conn.getMetaData().getUserName().toUpperCase());
			parameters1.put("object_name", tableName.toUpperCase());
			HashMap[] type = query(
					conn,
					"select object_type from all_objects a where a.owner = :owner and a.object_name= :object_name  and a.object_type in( 'TABLE','SYNONYM','VIEW') ",
					parameters1, new String[] { "OBJECT_TYPE" });

			if (type.length == 0) {
				throw new Exception("仅仅支持TABLE,SYNONYM和VIEW类型");
			}

			if (type.length != 1) {
				throw new Exception("查询出多种类型出来");
			}

			String owner = null;
			String ownerTableName = null;

			String tableType = (String) type[0].get("OBJECT_TYPE");
			if (tableType.equalsIgnoreCase("SYNONYM")) {
				HashMap parameters2 = new HashMap();
				parameters2.put("synonym_name", tableName.toUpperCase());

				HashMap[] syMap = query(conn, "select * from user_synonyms where SYNONYM_name = :synonym_name ", parameters2,
						new String[] { "TABLE_OWNER", "TABLE_NAME" });

				if (syMap.length != 1) {
					throw new Exception("查询出多个同义词出来");
				}

				owner = (String) syMap[0].get("TABLE_OWNER");
				ownerTableName = (String) syMap[0].get("TABLE_NAME");
			} else if (tableType.equalsIgnoreCase("TABLE")) {
				owner = conn.getMetaData().getUserName().toUpperCase();
				ownerTableName = tableName.toUpperCase();
			} else if (tableType.equalsIgnoreCase("VIEW")) {
				owner = conn.getMetaData().getUserName().toUpperCase();
				ownerTableName = tableName.toUpperCase();
			} else {
				throw new Exception("不能识别的类型:" + tableType);
			}

			HashMap parameters3 = new HashMap();
			parameters3.put("table_name", ownerTableName.toUpperCase());
			parameters3.put("owner", owner);

			HashMap[] data = query(
					conn,
					"select COLUMN_NAME,DATA_TYPE,DATA_PRECISION,DATA_SCALE from all_tab_cols where table_name = :table_name  and owner = :owner ",
					parameters3, new String[] { "COLUMN_NAME", "DATA_TYPE", "DATA_PRECISION", "DATA_SCALE" });

			for (int i = 0; i < data.length; i++) {
				String colName = (String) data[i].get("COLUMN_NAME");
				String colType = (String) data[i].get("DATA_TYPE");

				Column column = new Column();
				column.setName(colName.toUpperCase());

				if (colType.equalsIgnoreCase("VARCHAR2")) {
					column.setType(String.class);
				} else if (colType.equalsIgnoreCase("CHAR")) {
					column.setType(String.class);
				} else if (colType.equalsIgnoreCase("NUMBER")) {
					if (data[i].get("DATA_PRECISION") != null) {
						BigDecimal colLength = (BigDecimal) data[i].get("DATA_PRECISION");
						BigDecimal colScale = (BigDecimal) data[i].get("DATA_SCALE");
						if (colScale.longValue() > 0) {
							if (colLength.longValue() > 10) {
								column.setType(Double.TYPE);
							} else {
								column.setType(Float.TYPE);
							}
						} else {
							column.setType(Long.TYPE);
						}
					} else {
						column.setType(Long.TYPE);
					}
				} else if (colType.equalsIgnoreCase("DATE") || colType.equalsIgnoreCase("TIMESTAMP")) {
					column.setType(Timestamp.class);
				} else {
					System.out.println("不支持的类型:" + colType);
				}

				list.add(column);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (rsColumn != null) {
				rsColumn.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return (Column[]) list.toArray(new Column[0]);
	}

	/**
	 * 
	 * @param dbAcctCode
	 *          String
	 * @param tableName
	 *          String
	 * @return Column[]
	 * @throws Exception
	 */
	private static Column[] readMySQLTableColumns(String dbAcctCode, String tableName) throws Exception {
		List list = new ArrayList();

		Connection conn = null;
		ResultSet rs = null;
		try {

			conn = ServiceManager.getSession().getConnection(dbAcctCode);
			rs = conn.getMetaData().getColumns(null, conn.getMetaData().getUserName().toUpperCase(), tableName.toUpperCase(), null);

			while (rs.next()) {
				String colName = (String) rs.getString("COLUMN_NAME");
				String colType = (String) rs.getString("TYPE_NAME");

				Column column = new Column();
				column.setName(colName.toUpperCase());

				if (colType.equalsIgnoreCase("VARCHAR") || colType.equalsIgnoreCase("VARCHAR2")) {
					column.setType(String.class);
				} else if (colType.equalsIgnoreCase("CHAR")) {
					column.setType(String.class);
				} else if (colType.equalsIgnoreCase("NUMBER") || colType.equalsIgnoreCase("BIGINT")) {
					if (rs.getString("COLUMN_SIZE") != null) {
						BigDecimal colLength = (BigDecimal) rs.getBigDecimal("COLUMN_SIZE");
						BigDecimal colScale = (BigDecimal) rs.getBigDecimal("DECIMAL_DIGITS");
						if (colScale.longValue() > 0) {
							if (colLength.longValue() > 10) {
								column.setType(Double.TYPE);
							} else {
								column.setType(Float.TYPE);
							}
						} else {
							column.setType(Long.TYPE);
						}
					} else {
						column.setType(Long.TYPE);
					}
				} else if (colType.equalsIgnoreCase("DATE") || colType.equalsIgnoreCase("TIMESTAMP")
						|| colType.equalsIgnoreCase("DATETIME")) {
					column.setType(Timestamp.class);
				} else {
					System.out.println("不支持的类型:" + colType);
				}

				list.add(column);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return (Column[]) list.toArray(new Column[0]);
	}

	/**
	 * 表是否存在
	 * 
	 * @param dbAcctCode
	 *          String
	 * @param tableName
	 *          String
	 * @throws Exception
	 * @return boolean
	 */
	public static boolean isExistTable(String dbAcctCode, String tableName) throws Exception {
		if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
			return isOracleExistTable(dbAcctCode, tableName);
		} else if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.MYSQL)) {
			return isMySQLExistTable(dbAcctCode, tableName);
		} else {
			throw new Exception("仅仅支持oracle和mysql数据库");
		}
	}

	/**
	 * 
	 * @param dbAcctCode
	 *          String
	 * @param tableName
	 *          String
	 * @return boolean
	 * @throws Exception
	 */
	private static boolean isOracleExistTable(String dbAcctCode, String tableName) throws Exception {
		boolean rtn = false;

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			conn = ServiceManager.getSession().getConnection(dbAcctCode);

			// 采用直接查询表的方式
			// 有可能没有查询tabs的权限
			if (!rtn) {
				try {
					ptmt = conn.prepareStatement("select 1 as RTN__  from " + tableName + " where rownum < 2");

					rs = ptmt.executeQuery();
				} catch (SQLException ex) {
					// 表不存在
					return false;
				}
				rtn = true;
			}

			if (!rtn) {
				// 采用查询tabs的方法
				ptmt.clearBatch();
				ptmt.close();
				ptmt = null;

				rs.close();
				rs = null;

				ptmt = conn.prepareStatement("select * from tabs where TABLE_NAME = ?");
				ptmt.setString(1, tableName);

				rs = ptmt.executeQuery();
				while (rs.next()) {
					if (rs.getString("TABLE_NAME").equals(tableName)) {
						rtn = true;
					}
					break;
				}
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

	/**
	 * 
	 * @param dbAcctCode
	 *          String
	 * @param tableName
	 *          String
	 * @return boolean
	 * @throws Exception
	 */
	private static boolean isMySQLExistTable(String dbAcctCode, String tableName) throws Exception {
		boolean rtn = false;

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			conn = ServiceManager.getSession().getConnection(dbAcctCode);

			// 有可能没有查询权限
			if (!rtn) {
				try {
					ptmt = conn.prepareStatement("select 1  from " + tableName + " where 1!=1");
					rs = ptmt.executeQuery();
				} catch (SQLException ex) {
					// 表不存在
					return false;
				}
				rtn = true;
			}

			if (!rtn) {
				rs = conn.getMetaData().getTables(null, conn.getMetaData().getUserName().toUpperCase(), tableName.toUpperCase(), null);
				while (rs.next()) {
					rtn = true;
					break;
				}
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
}
