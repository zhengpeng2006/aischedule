package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgTfDtlValue extends DataStructInterface{

  public final static  String S_TableName = "TABLE_NAME";
  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_FinishSql = "FINISH_SQL";
  public final static  String S_TfType = "TF_TYPE";
  public final static  String S_CfgTfDtlId = "CFG_TF_DTL_ID";
  public final static  String S_CfgTfCode = "CFG_TF_CODE";
  public final static  String S_DbAcctCode = "DB_ACCT_CODE";


public String getTableName();

public String getState();

public String getRemarks();

public String getFinishSql();

public String getTfType();

public long getCfgTfDtlId();

public String getCfgTfCode();

public String getDbAcctCode();


public  void setTableName(String value);

public  void setState(String value);

public  void setRemarks(String value);

public  void setFinishSql(String value);

public  void setTfType(String value);

public  void setCfgTfDtlId(long value);

public  void setCfgTfCode(String value);

public  void setDbAcctCode(String value);
}
