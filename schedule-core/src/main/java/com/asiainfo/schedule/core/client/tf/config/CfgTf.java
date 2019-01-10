package com.asiainfo.schedule.core.client.tf.config;

public class CfgTf {
	private CfgTfDtl[] objCfgTfDtl = null;
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

	public CfgTf() {
	}

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public String getFinishSql() {
		return finishSql;
	}

	public String getQuerySql() {
		return querySql;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getSrcDbAcctCode() {
		return srcDbAcctCode;
	}

	public String getSrcTableName() {
		return srcTableName;
	}

	public String getCfgTfCode() {
		return cfgTfCode;
	}

	public void setFinishSql(String finishSql) {
		this.finishSql = finishSql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setSrcDbAcctCode(String srcDbAcctCode) {
		this.srcDbAcctCode = srcDbAcctCode;
	}

	public void setSrcTableName(String srcTableName) {
		this.srcTableName = srcTableName;
	}

	public void setCfgTfCode(String CfgTfCode) {
		this.cfgTfCode = CfgTfCode;
	}

	public CfgTfDtl[] getObjCfgTfDtl() {
		return objCfgTfDtl;
	}

	public void setObjCfgTfDtl(CfgTfDtl[] objCfgTfDtl) {
		this.objCfgTfDtl = objCfgTfDtl;
	}

	public String getProcessingSql() {
		return processingSql;
	}

	public void setProcessingSql(String processingSql) {
		this.processingSql = processingSql;
	}

	public void setErrorSql(String errorSql) {
		this.errorSql = errorSql;
	}

	public String getErrorSql() {
		return errorSql;
	}

	public String getPkColumns() {
		return pkColumns;
	}

	public void setPkColumns(String pkColumns) {
		this.pkColumns = pkColumns;
	}
}
