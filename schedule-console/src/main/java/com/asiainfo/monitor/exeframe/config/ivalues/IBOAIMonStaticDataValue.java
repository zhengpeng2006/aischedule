package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonStaticDataValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_SortId = "SORT_ID";
  public final static  String S_CodeName = "CODE_NAME";
  public final static  String S_ExternCodeType = "EXTERN_CODE_TYPE";
  public final static  String S_CodeValue = "CODE_VALUE";
  public final static  String S_CodeTypeAlias = "CODE_TYPE_ALIAS";
  public final static  String S_CodeDesc = "CODE_DESC";
  public final static  String S_CodeType = "CODE_TYPE";


public String getState();

public int getSortId();

public String getCodeName();

public String getExternCodeType();

public String getCodeValue();

public String getCodeTypeAlias();

public String getCodeDesc();

public String getCodeType();


public  void setState(String value);

public  void setSortId(int value);

public  void setCodeName(String value);

public  void setExternCodeType(String value);

public  void setCodeValue(String value);

public  void setCodeTypeAlias(String value);

public  void setCodeDesc(String value);

public  void setCodeType(String value);
}
