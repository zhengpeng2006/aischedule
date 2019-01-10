package com.asiainfo.monitor.exeframe.config.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonSetValue extends DataStructInterface{

  public final static  String S_Operater = "OPERATER";
  public final static  String S_State = "STATE";
  public final static  String S_ServerId = "SERVER_ID";
  public final static  String S_SetVcode = "SET_VCODE";
  public final static  String S_AppName = "APP_NAME";
  public final static  String S_SetDesc = "SET_DESC";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_HostId = "HOST_ID";
  public final static  String S_SetId = "SET_ID";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_SetValue = "SET_VALUE";
  public final static  String S_SetCode = "SET_CODE";


public String getOperater();

public String getState();

public long getServerId();

public String getSetVcode();

public String getAppName();

public String getSetDesc();

public String getRemarks();

public long getHostId();

public long getSetId();

public Timestamp getCreateDate();

public String getSetValue();

public String getSetCode();


public  void setOperater(String value);

public  void setState(String value);

public  void setServerId(long value);

public  void setSetVcode(String value);

public  void setAppName(String value);

public  void setSetDesc(String value);

public  void setRemarks(String value);

public  void setHostId(long value);

public  void setSetId(long value);

public  void setCreateDate(Timestamp value);

public  void setSetValue(String value);

public  void setSetCode(String value);
}
