package com.asiainfo.monitor.exeframe.config.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgTaskValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_BusinessClass = "BUSINESS_CLASS";
  public final static  String S_StaffId = "STAFF_ID";
  public final static  String S_TaskExpr = "TASK_EXPR";
  public final static  String S_SortId = "SORT_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_TaskName = "TASK_NAME";
  public final static  String S_CfgTaskId = "CFG_TASK_ID";
  public final static  String S_StateDate = "STATE_DATE";
  public final static  String S_CfgTaskTypeCode = "CFG_TASK_TYPE_CODE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_TaskMethod = "TASK_METHOD";


public String getState();

public String getBusinessClass();

public long getStaffId();

public String getTaskExpr();

public long getSortId();

public String getRemarks();

public String getTaskName();

public long getCfgTaskId();

public Timestamp getStateDate();

public String getCfgTaskTypeCode();

public Timestamp getCreateDate();

public String getTaskMethod();


public  void setState(String value);

public  void setBusinessClass(String value);

public  void setStaffId(long value);

public  void setTaskExpr(String value);

public  void setSortId(long value);

public  void setRemarks(String value);

public  void setTaskName(String value);

public  void setCfgTaskId(long value);

public  void setStateDate(Timestamp value);

public  void setCfgTaskTypeCode(String value);

public  void setCreateDate(Timestamp value);

public  void setTaskMethod(String value);
}
