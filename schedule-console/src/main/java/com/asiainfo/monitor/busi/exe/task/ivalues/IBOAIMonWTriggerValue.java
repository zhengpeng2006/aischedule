package com.asiainfo.monitor.busi.exe.task.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonWTriggerValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_InfoName = "INFO_NAME";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_ExpiryDate = "EXPIRY_DATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_Ip = "IP";
  public final static  String S_TriggerId = "TRIGGER_ID";
  public final static  String S_InfoId = "INFO_ID";
  public final static  String S_Layer = "LAYER";
  public final static  String S_WarnLevel = "WARN_LEVEL";
  public final static  String S_Content = "CONTENT";
  public final static  String S_RecordId = "RECORD_ID";
  public final static  String S_Phonenum = "PHONENUM";


public String getState();

public String getInfoName();

public Timestamp getDoneDate();

public Timestamp getExpiryDate();

public String getRemarks();

public Timestamp getCreateDate();

public String getIp();

public long getTriggerId();

public long getInfoId();

public String getLayer();

public String getWarnLevel();

public String getContent();

public long getRecordId();

public String getPhonenum();


public  void setState(String value);

public  void setInfoName(String value);

public  void setDoneDate(Timestamp value);

public  void setExpiryDate(Timestamp value);

public  void setRemarks(String value);

public  void setCreateDate(Timestamp value);

public  void setIp(String value);

public  void setTriggerId(long value);

public  void setInfoId(long value);

public  void setLayer(String value);

public  void setWarnLevel(String value);

public  void setContent(String value);

public  void setRecordId(long value);

public  void setPhonenum(String value);
}
