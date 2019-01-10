package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonParamValuesValue extends DataStructInterface{

  public final static  String S_SortId = "SORT_ID";
  public final static  String S_ParamValue = "PARAM_VALUE";
  public final static  String S_RegisteId = "REGISTE_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ParamDesc = "PARAM_DESC";
  public final static  String S_RegisteType = "REGISTE_TYPE";
  public final static  String S_ParamId = "PARAM_ID";
  public final static  String S_ParamCode = "PARAM_CODE";
  public final static  String S_VId = "V_ID";


public int getSortId();

public String getParamValue();

public long getRegisteId();

public String getRemarks();

public String getParamDesc();

public int getRegisteType();

public long getParamId();

public String getParamCode();

public long getVId();


public  void setSortId(int value);

public  void setParamValue(String value);

public  void setRegisteId(long value);

public  void setRemarks(String value);

public  void setParamDesc(String value);

public  void setRegisteType(int value);

public  void setParamId(long value);

public  void setParamCode(String value);

public  void setVId(long value);
}
