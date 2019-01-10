package com.asiainfo.index.base.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOBsMonitorCfgValue extends DataStructInterface{

  public final static  String S_IndexKind = "INDEX_KIND";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_IndexId = "INDEX_ID";
  public final static  String S_State = "STATE";
  public final static  String S_IndexCodeDisplay = "INDEX_CODE_DISPLAY";
  public final static  String S_IndexCodeMapping = "INDEX_CODE_MAPPING";
  public final static  String S_IndexNameDisplay = "INDEX_NAME_DISPLAY";
  public final static  String S_MonitorId = "MONITOR_ID";
  public final static  String S_MonitorCfgId = "MONITOR_CFG_ID";


public String getIndexKind();

public Timestamp getCreateDate();

public Timestamp getDoneDate();

public int getIndexId();

public String getState();

public String getIndexCodeDisplay();

public String getIndexCodeMapping();

public String getIndexNameDisplay();

public int getMonitorId();

public int getMonitorCfgId();


public  void setIndexKind(String value);

public  void setCreateDate(Timestamp value);

public  void setDoneDate(Timestamp value);

public  void setIndexId(int value);

public  void setState(String value);

public  void setIndexCodeDisplay(String value);

public  void setIndexCodeMapping(String value);

public  void setIndexNameDisplay(String value);

public  void setMonitorId(int value);

public  void setMonitorCfgId(int value);
}
