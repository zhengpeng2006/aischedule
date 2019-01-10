package com.asiainfo.monitor.busi.exe.task.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonWSmsValue extends DataStructInterface{

  public final static  String S_SmsContent = "SMS_CONTENT";
  public final static  String S_SendDate = "SEND_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_PhoneNum = "PHONE_NUM";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_TriggerId = "TRIGGER_ID";
  public final static  String S_InfoId = "INFO_ID";
  public final static  String S_Exceptions = "EXCEPTIONS";
  public final static  String S_RecordId = "RECORD_ID";
  public final static  String S_SmsId = "SMS_ID";
  public final static  String S_SendCount = "SEND_COUNT";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_WarnLevel = "WARN_LEVEL";


public String getSmsContent();

public Timestamp getSendDate();

public String getState();

public String getPhoneNum();

public String getRemarks();

public long getTriggerId();

public long getInfoId();

public String getExceptions();

public long getRecordId();

public long getSmsId();

public int getSendCount();

public Timestamp getCreateDate();

public String getWarnLevel();


public  void setSmsContent(String value);

public  void setSendDate(Timestamp value);

public  void setState(String value);

public  void setPhoneNum(String value);

public  void setRemarks(String value);

public  void setTriggerId(long value);

public  void setInfoId(long value);

public  void setExceptions(String value);

public  void setRecordId(long value);

public  void setSmsId(long value);

public  void setSendCount(int value);

public  void setCreateDate(Timestamp value);

public  void setWarnLevel(String value);
}
