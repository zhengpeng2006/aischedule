package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgTaskTypeValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_BusinessClass = "BUSINESS_CLASS";
  public final static  String S_CfgTaskTypeId = "CFG_TASK_TYPE_ID";
  public final static  String S_CfgTaskTypeCode = "CFG_TASK_TYPE_CODE";
  public final static  String S_CfgTaskTypeName = "CFG_TASK_TYPE_NAME";


public String getState();

public String getBusinessClass();

public long getCfgTaskTypeId();

public String getCfgTaskTypeCode();

public String getCfgTaskTypeName();


public  void setState(String value);

public  void setBusinessClass(String value);

public  void setCfgTaskTypeId(long value);

public  void setCfgTaskTypeCode(String value);

public  void setCfgTaskTypeName(String value);
}
