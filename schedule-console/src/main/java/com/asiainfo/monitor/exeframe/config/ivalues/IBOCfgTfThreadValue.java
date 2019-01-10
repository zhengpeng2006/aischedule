package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgTfThreadValue extends DataStructInterface{

  public final static  String S_DataCount = "DATA_COUNT";
  public final static  String S_State = "STATE";
  public final static  String S_InitThreads = "INIT_THREADS";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CfgTfThreadId = "CFG_TF_THREAD_ID";
  public final static  String S_MaxThreads = "MAX_THREADS";
  public final static  String S_BufferSize = "BUFFER_SIZE";
  public final static  String S_OverloadProtectPercent = "OVERLOAD_PROTECT_PERCENT";
  public final static  String S_MinThreads = "MIN_THREADS";
  public final static  String S_KeepAliveTime = "KEEP_ALIVE_TIME";


public int getDataCount();

public String getState();

public int getInitThreads();

public String getRemarks();

public long getCfgTfThreadId();

public int getMaxThreads();

public int getBufferSize();

public int getOverloadProtectPercent();

public int getMinThreads();

public int getKeepAliveTime();


public  void setDataCount(int value);

public  void setState(String value);

public  void setInitThreads(int value);

public  void setRemarks(String value);

public  void setCfgTfThreadId(long value);

public  void setMaxThreads(int value);

public  void setBufferSize(int value);

public  void setOverloadProtectPercent(int value);

public  void setMinThreads(int value);

public  void setKeepAliveTime(int value);
}
