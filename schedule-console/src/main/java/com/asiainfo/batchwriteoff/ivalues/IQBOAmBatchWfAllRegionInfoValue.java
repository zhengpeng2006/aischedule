package com.asiainfo.batchwriteoff.ivalues;
import com.ai.appframe2.common.DataStructInterface;

public interface IQBOAmBatchWfAllRegionInfoValue extends DataStructInterface {

  public final static  String S_InsertData = "INSERT_DATA";
  public final static  String S_SetCtrlflagBegin = "SET_CTRLFLAG_BEGIN";
  public final static  String S_KillRealWf = "KILL_REAL_WF";
  public final static  String S_RenameTable = "RENAME_TABLE";
  public final static  String S_SetCtrlflagFinish = "SET_CTRLFLAG_FINISH";
  public final static  String S_Audit = "AUDIT";
  public final static  String S_BillingCycleId = "BILLING_CYCLE_ID";
  public final static  String S_BatchWriteoffFinish = "BATCH_WRITEOFF_FINISH";
  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_Start = "START";
  public final static  String S_RunRealWf = "RUN_REAL_WF";
  public final static  String S_End = "END";
  public final static  String S_TableAnalyse = "TABLE_ANALYSE";
  public final static  String S_Init = "INIT";
  public final static  String S_BatchWriteoff = "BATCH_WRITEOFF";
  public final static  String S_SqlloadData = "SQLLOAD_DATA";


public String getInsertData();

public String getSetCtrlflagBegin();

public String getKillRealWf();

public String getRenameTable();

public String getSetCtrlflagFinish();

public String getAudit();

public long getBillingCycleId();

public String getBatchWriteoffFinish();

public String getRegionId();

public String getStart();

public String getRunRealWf();

public String getEnd();

public String getTableAnalyse();

public String getInit();

public String getBatchWriteoff();

public String getSqlloadData();


public  void setInsertData(String value);

public  void setSetCtrlflagBegin(String value);

public  void setKillRealWf(String value);

public  void setRenameTable(String value);

public  void setSetCtrlflagFinish(String value);

public  void setAudit(String value);

public  void setBillingCycleId(long value);

public  void setBatchWriteoffFinish(String value);

public  void setRegionId(String value);

public  void setStart(String value);

public  void setRunRealWf(String value);

public  void setEnd(String value);

public  void setTableAnalyse(String value);

public  void setInit(String value);

public  void setBatchWriteoff(String value);

public  void setSqlloadData(String value);
}
