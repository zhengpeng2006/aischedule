package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgTaskParamTypeValue extends DataStructInterface{

  public final static  String S_ParamValueType = "PARAM_VALUE_TYPE";
  public final static  String S_ParamName = "PARAM_NAME";
  public final static  String S_ParamId = "PARAM_ID";
  public final static  String S_DataType = "DATA_TYPE";


public String getParamValueType();

public String getParamName();

public long getParamId();

public String getDataType();


public  void setParamValueType(String value);

public  void setParamName(String value);

public  void setParamId(long value);

public  void setDataType(String value);
}
