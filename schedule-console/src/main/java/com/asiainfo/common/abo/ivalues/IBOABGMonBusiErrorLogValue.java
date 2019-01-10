package com.asiainfo.common.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOABGMonBusiErrorLogValue extends DataStructInterface{

  public final static  String S_MonFlag = "MON_FLAG";
  public final static  String S_ServerCode = "SERVER_CODE";
  public final static  String S_TaskSplitId = "TASK_SPLIT_ID";
  public final static  String S_AcqDate = "ACQ_DATE";
  public final static  String S_TaskId = "TASK_ID";
  public final static  String S_HandleTime = "HANDLE_TIME";
  public final static  String S_ErrOrderId = "ERR_ORDER_ID";
  public final static  String S_SubsystemDomain = "SUBSYSTEM_DOMAIN";
  public final static  String S_SystemDomain = "SYSTEM_DOMAIN";
  public final static  String S_SerialNo = "SERIAL_NO";
  public final static  String S_ErrCnt = "ERR_CNT";
  public final static  String S_RegionCode = "REGION_CODE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_AppServerName = "APP_SERVER_NAME";


public String getMonFlag();

public String getServerCode();

public String getTaskSplitId();

public Timestamp getAcqDate();

public String getTaskId();

public long getHandleTime();

public long getErrOrderId();

public String getSubsystemDomain();

public String getSystemDomain();

public long getSerialNo();

public long getErrCnt();

public String getRegionCode();

public String getCreateDate();

public String getAppServerName();


public  void setMonFlag(String value);

public  void setServerCode(String value);

public  void setTaskSplitId(String value);

public  void setAcqDate(Timestamp value);

public  void setTaskId(String value);

public  void setHandleTime(long value);

public  void setErrOrderId(long value);

public  void setSubsystemDomain(String value);

public  void setSystemDomain(String value);

public  void setSerialNo(long value);

public  void setErrCnt(long value);

public  void setRegionCode(String value);

public  void setCreateDate(String value);

public  void setAppServerName(String value);
}
