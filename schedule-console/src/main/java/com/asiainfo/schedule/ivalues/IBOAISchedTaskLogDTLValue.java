package com.asiainfo.schedule.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAISchedTaskLogDTLValue extends DataStructInterface{

  public final static  String S_LogDate = "LOG_DATE";
  public final static  String S_OpInfo = "OP_INFO";
  public final static  String S_JobId = "JOB_ID";
  public final static  String S_SubsystemDomain = "SUBSYSTEM_DOMAIN";
  public final static  String S_SystemDomain = "SYSTEM_DOMAIN";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_TaskItem = "TASK_ITEM";
  public final static  String S_Operator = "OPERATOR";
  public final static  String S_AppServerName = "APP_SERVER_NAME";
  public final static  String S_TaskVersion = "TASK_VERSION";
  public final static  String S_ExMsg = "EX_MSG";
  public final static  String S_LogType = "LOG_TYPE";
  public final static  String S_TaskCode = "TASK_CODE";


public Timestamp getLogDate();

public String getOpInfo();

public String getJobId();

public String getSubsystemDomain();

public String getSystemDomain();

public Timestamp getCreateDate();

public String getTaskItem();

public String getOperator();

public String getAppServerName();

public String getTaskVersion();

public String getExMsg();

public String getLogType();

public String getTaskCode();


public  void setLogDate(Timestamp value);

public  void setOpInfo(String value);

public  void setJobId(String value);

public  void setSubsystemDomain(String value);

public  void setSystemDomain(String value);

public  void setCreateDate(Timestamp value);

public  void setTaskItem(String value);

public  void setOperator(String value);

public  void setAppServerName(String value);

public  void setTaskVersion(String value);

public  void setExMsg(String value);

public  void setLogType(String value);

public  void setTaskCode(String value);
}
