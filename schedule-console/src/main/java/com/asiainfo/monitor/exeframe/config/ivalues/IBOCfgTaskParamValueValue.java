package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgTaskParamValueValue extends DataStructInterface{

  public final static  String S_Id = "ID";
  public final static  String S_CfgTaskId = "CFG_TASK_ID";
  public final static  String S_ParamValue = "PARAM_VALUE";
  public final static  String S_ParamId = "PARAM_ID";


public long getId();

public long getCfgTaskId();

public String getParamValue();

public long getParamId();


public  void setId(long value);

public  void setCfgTaskId(long value);

public  void setParamValue(String value);

public  void setParamId(long value);
}
