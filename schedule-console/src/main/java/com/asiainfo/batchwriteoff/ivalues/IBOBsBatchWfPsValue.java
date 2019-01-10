package com.asiainfo.batchwriteoff.ivalues;
import com.ai.appframe2.common.DataStructInterface;

public interface IBOBsBatchWfPsValue extends DataStructInterface {

  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_Port = "PORT";
  public final static  String S_ShellCatalog = "SHELL_CATALOG";
  public final static  String S_StartShell = "START_SHELL";
  public final static  String S_UserName = "USER_NAME";
  public final static  String S_ThreadNum = "THREAD_NUM";
  public final static  String S_WfProcessName = "WF_PROCESS_NAME";
  public final static  String S_StopShell = "STOP_SHELL";
  public final static  String S_MonitorShell = "MONITOR_SHELL";
  public final static  String S_Password = "PASSWORD";
  public final static  String S_Paras = "PARAS";
  public final static  String S_HostIp = "HOST_IP";
  public final static  String S_WfProcessId = "WF_PROCESS_ID";
  public final static  String S_WfRedisId = "WF_REDIS_ID";
  public final static  String S_WfFlowId = "WF_FLOW_ID";


public String getRegionId();

public int getPort();

public String getShellCatalog();

public String getStartShell();

public String getUserName();

public int getThreadNum();

public String getWfProcessName();

public String getStopShell();

public String getMonitorShell();

public String getPassword();

public String getParas();

public String getHostIp();

public String getWfProcessId();

public String getWfRedisId();

public long getWfFlowId();


public  void setRegionId(String value);

public  void setPort(int value);

public  void setShellCatalog(String value);

public  void setStartShell(String value);

public  void setUserName(String value);

public  void setThreadNum(int value);

public  void setWfProcessName(String value);

public  void setStopShell(String value);

public  void setMonitorShell(String value);

public  void setPassword(String value);

public  void setParas(String value);

public  void setHostIp(String value);

public  void setWfProcessId(String value);

public  void setWfRedisId(String value);

public  void setWfFlowId(long value);
}
