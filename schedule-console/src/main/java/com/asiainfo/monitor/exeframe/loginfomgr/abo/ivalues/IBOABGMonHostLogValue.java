package com.asiainfo.monitor.exeframe.loginfomgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOABGMonHostLogValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_MonFlag = "MON_FLAG";
  public final static  String S_ExtKpi1 = "EXT_KPI_1";
  public final static  String S_ExtKpi2 = "EXT_KPI_2";
  public final static  String S_AcqDate = "ACQ_DATE";
  public final static  String S_KpiMem = "KPI_MEM";
  public final static  String S_ExtKpi3 = "EXT_KPI_3";
  public final static  String S_ExtKpi4 = "EXT_KPI_4";
  public final static  String S_SubsystemDomain = "SUBSYSTEM_DOMAIN";
  public final static  String S_SystemDomain = "SYSTEM_DOMAIN";
  public final static  String S_KpiCpu = "KPI_CPU";
  public final static  String S_KpiFs = "KPI_FS";
  public final static  String S_AcqLogId = "ACQ_LOG_ID";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_AppServerName = "APP_SERVER_NAME";
  public final static  String S_HostIp = "HOST_IP";


public String getState();

public String getMonFlag();

public String getExtKpi1();

public String getExtKpi2();

public Timestamp getAcqDate();

public String getKpiMem();

public String getExtKpi3();

public String getExtKpi4();

public String getSubsystemDomain();

public String getSystemDomain();

public String getKpiCpu();

public String getKpiFs();

public long getAcqLogId();

public String getCreateDate();

public String getAppServerName();

public String getHostIp();


public  void setState(String value);

public  void setMonFlag(String value);

public  void setExtKpi1(String value);

public  void setExtKpi2(String value);

public  void setAcqDate(Timestamp value);

public  void setKpiMem(String value);

public  void setExtKpi3(String value);

public  void setExtKpi4(String value);

public  void setSubsystemDomain(String value);

public  void setSystemDomain(String value);

public  void setKpiCpu(String value);

public  void setKpiFs(String value);

public  void setAcqLogId(long value);

public  void setCreateDate(String value);

public  void setAppServerName(String value);

public  void setHostIp(String value);
}
