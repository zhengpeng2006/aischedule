package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgSocketAsynValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_WorkThreadMin = "WORK_THREAD_MIN";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CfgSocketCode = "CFG_SOCKET_CODE";
  public final static  String S_WorkKeepAliveTime = "WORK_KEEP_ALIVE_TIME";
  public final static  String S_SendBufferSize = "SEND_BUFFER_SIZE";
  public final static  String S_SendKeepAliveTime = "SEND_KEEP_ALIVE_TIME";
  public final static  String S_IsAsyn = "IS_ASYN";
  public final static  String S_OverloadProtectPercent = "OVERLOAD_PROTECT_PERCENT";
  public final static  String S_SendThreadMin = "SEND_THREAD_MIN";
  public final static  String S_SendThreadMax = "SEND_THREAD_MAX";
  public final static  String S_WorkBufferSize = "WORK_BUFFER_SIZE";
  public final static  String S_WorkThreadMax = "WORK_THREAD_MAX";


public String getState();

public int getWorkThreadMin();

public String getRemarks();

public String getCfgSocketCode();

public int getWorkKeepAliveTime();

public int getSendBufferSize();

public int getSendKeepAliveTime();

public String getIsAsyn();

public int getOverloadProtectPercent();

public int getSendThreadMin();

public int getSendThreadMax();

public int getWorkBufferSize();

public int getWorkThreadMax();


public  void setState(String value);

public  void setWorkThreadMin(int value);

public  void setRemarks(String value);

public  void setCfgSocketCode(String value);

public  void setWorkKeepAliveTime(int value);

public  void setSendBufferSize(int value);

public  void setSendKeepAliveTime(int value);

public  void setIsAsyn(String value);

public  void setOverloadProtectPercent(int value);

public  void setSendThreadMin(int value);

public  void setSendThreadMax(int value);

public  void setWorkBufferSize(int value);

public  void setWorkThreadMax(int value);
}
