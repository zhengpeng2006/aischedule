package com.asiainfo.schedule.core.client.tf.config;


public class CfgTfDtl {
  private CfgTfMapping[] objCfgTfMapping = null;
  private String cfgTfDtlId;
  private String cfgTfCode;
  private String tableName;
  private String dbAcctCode;
  private String tfType;
  private String finishSql;
  private String remarks;

  public CfgTfDtl() {
  }
  public String getDbAcctCode() {
    return dbAcctCode;
  }
  public String getFinishSql() {
    return finishSql;
  }
  public String getRemarks() {
    return remarks;
  }
  public String getTableName() {
    return tableName;
  }

  public String getTfType() {
    return tfType;
  }
  public void setTfType(String tfType) {
    this.tfType = tfType;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
  public void setFinishSql(String finishSql) {
    this.finishSql = finishSql;
  }
  public void setDbAcctCode(String dbAcctCode) {
    this.dbAcctCode = dbAcctCode;
  }
  public CfgTfMapping[] getObjCfgTfMapping() {
    return objCfgTfMapping;
  }
  public void setObjCfgTfMapping(CfgTfMapping[] objCfgTfMapping) {
    this.objCfgTfMapping = objCfgTfMapping;
  }
  public String getCfgTfDtlId() {
    return cfgTfDtlId;
  }
  public String getCfgTfCode() {
    return cfgTfCode;
  }
  public void setCfgTfDtlId(String cfgTfDtlId) {
    this.cfgTfDtlId = cfgTfDtlId;
  }
  public void setCfgTfCode(String cfgTfCode) {
    this.cfgTfCode = cfgTfCode;
  }

}
