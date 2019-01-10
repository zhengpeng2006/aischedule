package com.asiainfo.schedule.core.po;

public class CfgTfDtlBean {
	private String cfgTfDtlId;
	private String cfgTfCode;
	private String tableName;
	private String dbAcctCode;
	private String tfType;
	private String finishSql;
	private String remarks;

	public String getCfgTfDtlId() {
		return cfgTfDtlId;
	}

	public void setCfgTfDtlId(String cfgTfDtlId) {
		this.cfgTfDtlId = cfgTfDtlId;
	}

	public String getCfgTfCode() {
		return cfgTfCode;
	}

	public void setCfgTfCode(String cfgTfCode) {
		this.cfgTfCode = cfgTfCode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDbAcctCode() {
		return dbAcctCode;
	}

	public void setDbAcctCode(String dbAcctCode) {
		this.dbAcctCode = dbAcctCode;
	}

	public String getTfType() {
		return tfType;
	}

	public void setTfType(String tfType) {
		this.tfType = tfType;
	}

	public String getFinishSql() {
		return finishSql;
	}

	public void setFinishSql(String finishSql) {
		this.finishSql = finishSql;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
