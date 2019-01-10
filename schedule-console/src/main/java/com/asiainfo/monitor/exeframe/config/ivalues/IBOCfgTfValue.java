package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgTfValue extends DataStructInterface{

  public final static  String S_DataCount = "DATA_COUNT";
  public final static  String S_State = "STATE";
  public final static  String S_ScanRownum = "SCAN_ROWNUM";
  public final static  String S_WhenIdleSleepTime = "WHEN_IDLE_SLEEP_TIME";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_FinishSql = "FINISH_SQL";
  public final static  String S_Template = "TEMPLATE";
  public final static  String S_TransformClassname = "TRANSFORM_CLASSNAME";
  public final static  String S_ProcessingSql = "PROCESSING_SQL";
  public final static  String S_StartTime = "START_TIME";
  public final static  String S_ExecuteMethod = "EXECUTE_METHOD";
  public final static  String S_MethodId = "METHOD_ID";
  public final static  String S_ErrorSql = "ERROR_SQL";
  public final static  String S_SrcTableName = "SRC_TABLE_NAME";
  public final static  String S_PkColumns = "PK_COLUMNS";
  public final static  String S_QuerySql = "QUERY_SQL";
  public final static  String S_ScanIntervalTime = "SCAN_INTERVAL_TIME";
  public final static  String S_CfgTfCode = "CFG_TF_CODE";
  public final static  String S_SrcDbAcctCode = "SRC_DB_ACCT_CODE";
  public final static  String S_Duration = "DURATION";


public long getDataCount();

public String getState();

public long getScanRownum();

public int getWhenIdleSleepTime();

public String getRemarks();

public String getFinishSql();

public String getTemplate();

public String getTransformClassname();

public String getProcessingSql();

public String getStartTime();

public String getExecuteMethod();

public long getMethodId();

public String getErrorSql();

public String getSrcTableName();

public String getPkColumns();

public String getQuerySql();

public long getScanIntervalTime();

public String getCfgTfCode();

public String getSrcDbAcctCode();

public int getDuration();


public  void setDataCount(long value);

public  void setState(String value);

public  void setScanRownum(long value);

public  void setWhenIdleSleepTime(int value);

public  void setRemarks(String value);

public  void setFinishSql(String value);

public  void setTemplate(String value);

public  void setTransformClassname(String value);

public  void setProcessingSql(String value);

public  void setStartTime(String value);

public  void setExecuteMethod(String value);

public  void setMethodId(long value);

public  void setErrorSql(String value);

public  void setSrcTableName(String value);

public  void setPkColumns(String value);

public  void setQuerySql(String value);

public  void setScanIntervalTime(long value);

public  void setCfgTfCode(String value);

public  void setSrcDbAcctCode(String value);

public  void setDuration(int value);
}
