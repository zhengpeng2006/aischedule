package com.asiainfo.schedule.core.client.tf.config.table;

/**
 * 抽象表
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: AI(NanJing)
 * </p>
 * 
 * @author Yang Hua
 * @author LiuQQ
 * @version 2.0
 */
public abstract class AbstractTable {

	protected String dbAcctCode = null;
	protected String tableName = null;
	protected Column[] columns = null;
	protected PK pk = null;

	public AbstractTable() {
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Column[] getColumns() {
		return columns;
	}

	public void setColumns(Column[] columns) {
		this.columns = columns;
	}

	public PK getPk() {
		return pk;
	}

	public void setPk(PK pk) {
		this.pk = pk;
	}

	public String getDbAcctCode() {
		return dbAcctCode;
	}

	public void setDbAcctCode(String dbAcctCode) {
		this.dbAcctCode = dbAcctCode;
	}

}
