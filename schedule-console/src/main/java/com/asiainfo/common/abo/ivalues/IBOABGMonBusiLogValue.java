package com.asiainfo.common.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOABGMonBusiLogValue extends DataStructInterface{

  public final static  String S_MonFlag = "MON_FLAG";
  public final static  String S_TaskSplitId = "TASK_SPLIT_ID";
  public final static  String S_ErrCnt = "ERR_CNT";
  public final static  String S_PerErrCnt = "PER_ERR_CNT";
  public final static  String S_RegionCode = "REGION_CODE";
  public final static  String S_SerialNo = "SERIAL_NO";
  public final static  String S_HandleCnt = "HANDLE_CNT";
  public final static  String S_SubsystemDomain = "SUBSYSTEM_DOMAIN";
  public final static  String S_SystemDomain = "SYSTEM_DOMAIN";
  public final static  String S_TotalCnt = "TOTAL_CNT";
  public final static  String S_PerHandleCnt = "PER_HANDLE_CNT";
  public final static  String S_Ext1 = "EXT1";
  public final static  String S_Ext2 = "EXT2";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_ThroughputId = "THROUGHPUT_ID";
  public final static  String S_AppServerName = "APP_SERVER_NAME";
  public final static  String S_ConsumeTime = "CONSUME_TIME";
  public final static  String S_Seq = "SEQ";
  public final static  String S_ServerCode = "SERVER_CODE";
  public final static  String S_TaskId = "TASK_ID";


public String getMonFlag();

public String getTaskSplitId();

public long getErrCnt();

public long getPerErrCnt();

public String getRegionCode();

public long getSerialNo();

public long getHandleCnt();

public String getSubsystemDomain();

public String getSystemDomain();

public long getTotalCnt();

public long getPerHandleCnt();

public String getExt1();

public String getExt2();

public Timestamp getCreateDate();

public long getThroughputId();

public String getAppServerName();

public long getConsumeTime();

public long getSeq();

public String getServerCode();

public String getTaskId();


public  void setMonFlag(String value);

public  void setTaskSplitId(String value);

public  void setErrCnt(long value);

public  void setPerErrCnt(long value);

public  void setRegionCode(String value);

public  void setSerialNo(long value);

public  void setHandleCnt(long value);

public  void setSubsystemDomain(String value);

public  void setSystemDomain(String value);

public  void setTotalCnt(long value);

public  void setPerHandleCnt(long value);

public  void setExt1(String value);

public  void setExt2(String value);

public  void setCreateDate(Timestamp value);

public  void setThroughputId(long value);

public  void setAppServerName(String value);

public  void setConsumeTime(long value);

public  void setSeq(long value);

public  void setServerCode(String value);

public  void setTaskId(String value);
}
