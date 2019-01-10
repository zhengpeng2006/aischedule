package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgTaskParamDefineValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_Id = "ID";
  public final static  String S_CfgTaskTypeId = "CFG_TASK_TYPE_ID";
  public final static  String S_ParamId = "PARAM_ID";
  public final static  String S_IsMust = "IS_MUST";


public String getState();

public long getId();

public long getCfgTaskTypeId();

public long getParamId();

public String getIsMust();


public  void setState(String value);

public  void setId(long value);

public  void setCfgTaskTypeId(long value);

public  void setParamId(long value);

public  void setIsMust(String value);
}
