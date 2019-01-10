package com.asiainfo.index.base.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOBsStaticDataValue extends DataStructInterface{

  public final static  String S_CodeType = "CODE_TYPE";
  public final static  String S_CodeTypeAlias = "CODE_TYPE_ALIAS";
  public final static  String S_SortId = "SORT_ID";
  public final static  String S_CodeValue = "CODE_VALUE";
  public final static  String S_CodeDesc = "CODE_DESC";
  public final static  String S_ExternCodeType = "EXTERN_CODE_TYPE";
  public final static  String S_CodeName = "CODE_NAME";
  public final static  String S_State = "STATE";


public String getCodeType();

public String getCodeTypeAlias();

public int getSortId();

public String getCodeValue();

public String getCodeDesc();

public String getExternCodeType();

public String getCodeName();

public String getState();


public  void setCodeType(String value);

public  void setCodeTypeAlias(String value);

public  void setSortId(int value);

public  void setCodeValue(String value);

public  void setCodeDesc(String value);

public  void setExternCodeType(String value);

public  void setCodeName(String value);

public  void setState(String value);
}
