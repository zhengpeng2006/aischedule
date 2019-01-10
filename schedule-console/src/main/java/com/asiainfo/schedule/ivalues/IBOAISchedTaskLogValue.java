package com.asiainfo.schedule.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAISchedTaskLogValue extends DataStructInterface{

  public final static  String S_LogDate = "LOG_DATE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_JobId = "JOB_ID";
  public final static  String S_StartTime = "START_TIME";
  public final static  String S_FinishTime = "FINISH_TIME";
  public final static  String S_TaskName = "TASK_NAME";
  public final static  String S_AppServerName = "APP_SERVER_NAME";
  public final static  String S_TaskVersion = "TASK_VERSION";
  public final static  String S_SubsystemDomain = "SUBSYSTEM_DOMAIN";
  public final static  String S_State = "STATE";
  public final static  String S_SystemDomain = "SYSTEM_DOMAIN";
  public final static  String S_TaskCode = "TASK_CODE";


public Timestamp getLogDate();

public Timestamp getCreateDate();

public String getJobId();

public Timestamp getStartTime();

public Timestamp getFinishTime();

public String getTaskName();

public String getAppServerName();

public String getTaskVersion();

public String getSubsystemDomain();

public String getState();

public String getSystemDomain();

public String getTaskCode();


public  void setLogDate(Timestamp value);

public  void setCreateDate(Timestamp value);

public  void setJobId(String value);

public  void setStartTime(Timestamp value);

public  void setFinishTime(Timestamp value);

public  void setTaskName(String value);

public  void setAppServerName(String value);

public  void setTaskVersion(String value);

public  void setSubsystemDomain(String value);

public  void setState(String value);

public  void setSystemDomain(String value);

public  void setTaskCode(String value);
}
