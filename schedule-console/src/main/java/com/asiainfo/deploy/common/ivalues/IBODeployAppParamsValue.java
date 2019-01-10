package com.asiainfo.deploy.common.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBODeployAppParamsValue extends DataStructInterface{

  public final static  String S_ParamType = "PARAM_TYPE";
  public final static  String S_Value = "VALUE";
  public final static  String S_ApplicationId = "APPLICATION_ID";
  public final static  String S_Key = "KEY";
  public final static  String S_ApplicationParamId = "APPLICATION_PARAM_ID";


public String getParamType();

public String getValue();

public long getApplicationId();

public String getKey();

public long getApplicationParamId();


public  void setParamType(String value);

public  void setValue(String value);

public  void setApplicationId(long value);

public  void setKey(String value);

public  void setApplicationParamId(long value);
}
