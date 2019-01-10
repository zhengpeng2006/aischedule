package com.asiainfo.index.base.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOBsMonitorValue extends DataStructInterface{

  public final static  String S_MonitorName = "MONITOR_NAME";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_MonitorDesc = "MONITOR_DESC";
  public final static  String S_MonitorId = "MONITOR_ID";


public String getMonitorName();

public Timestamp getCreateDate();

public Timestamp getDoneDate();

public String getState();

public String getMonitorDesc();

public int getMonitorId();


public  void setMonitorName(String value);

public  void setCreateDate(Timestamp value);

public  void setDoneDate(Timestamp value);

public  void setState(String value);

public  void setMonitorDesc(String value);

public  void setMonitorId(int value);
}
