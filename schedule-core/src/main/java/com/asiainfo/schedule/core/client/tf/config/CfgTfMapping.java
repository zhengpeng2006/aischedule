package com.asiainfo.schedule.core.client.tf.config;

public class CfgTfMapping {
  private String mappingId;
  private String CfgTfDtlId;
  private String srcColumnName;
  private String tfColumnName;
  private String remarks;

  public CfgTfMapping() {
  }

  public String getRemarks() {
    return remarks;
  }
  public String getCfgTfDtlId() {
    return CfgTfDtlId;
  }
  public String getTfColumnName() {
    return tfColumnName;
  }
  public void setTfColumnName(String tfColumnName) {
    this.tfColumnName = tfColumnName;
  }
  public void setCfgTfDtlId(String CfgTfDtlId) {
    this.CfgTfDtlId = CfgTfDtlId;
  }
  public void setSrcColumnName(String srcColumnName) {
    this.srcColumnName = srcColumnName;
  }
  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getSrcColumnName() {
    return srcColumnName;
  }
  public String getMappingId() {
    return mappingId;
  }
  public void setMappingId(String mappingId) {
    this.mappingId = mappingId;
  }
}
