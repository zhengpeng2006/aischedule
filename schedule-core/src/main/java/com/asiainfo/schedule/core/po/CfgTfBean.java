package com.asiainfo.schedule.core.po;

public class CfgTfBean {

	private String cfgTfCode;
	private String srcDbAcctCode;
	private String srcTableName;
	private String querySql;
	private String finishSql;
	private String remarks;
	private String processingSql;
	private String errorSql;
	private String countSql;
	private String pkColumns;

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}
	
	public String getCfgTfCode() {
		return cfgTfCode;
	}

	public void setCfgTfCode(String cfgTfCode) {
		this.cfgTfCode = cfgTfCode;
	}

	public String getSrcDbAcctCode() {
		return srcDbAcctCode;
	}

	public void setSrcDbAcctCode(String srcDbAcctCode) {
		this.srcDbAcctCode = srcDbAcctCode;
	}

	public String getSrcTableName() {
		return srcTableName;
	}

	public void setSrcTableName(String srcTableName) {
		this.srcTableName = srcTableName;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
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

	public String getProcessingSql() {
		return processingSql;
	}

	public void setProcessingSql(String processingSql) {
		this.processingSql = processingSql;
	}

	public String getErrorSql() {
		return errorSql;
	}

	public void setErrorSql(String errorSql) {
		this.errorSql = errorSql;
	}

	public String getPkColumns() {
		return pkColumns;
	}

	public void setPkColumns(String pkColumns) {
		this.pkColumns = pkColumns;
	}

}
