package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonColgAnalyseDefineValue extends DataStructInterface{

  public final static  String S_DefineDesc = "DEFINE_DESC";
  public final static  String S_State = "STATE";
  public final static  String S_AnalyseMethod = "ANALYSE_METHOD";
  public final static  String S_AnalyseType = "ANALYSE_TYPE";
  public final static  String S_DefineId = "DEFINE_ID";
  public final static  String S_AnalyseId = "ANALYSE_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_AnalyseClass = "ANALYSE_CLASS";
  public final static  String S_AnalyseCode = "ANALYSE_CODE";
  public final static  String S_DefineName = "DEFINE_NAME";
  public final static  String S_DefineCode = "DEFINE_CODE";


public String getDefineDesc();

public String getState();

public String getAnalyseMethod();

public int getAnalyseType();

public long getDefineId();

public long getAnalyseId();

public String getRemarks();

public String getAnalyseClass();

public String getAnalyseCode();

public String getDefineName();

public String getDefineCode();


public  void setDefineDesc(String value);

public  void setState(String value);

public  void setAnalyseMethod(String value);

public  void setAnalyseType(int value);

public  void setDefineId(long value);

public  void setAnalyseId(long value);

public  void setRemarks(String value);

public  void setAnalyseClass(String value);

public  void setAnalyseCode(String value);

public  void setDefineName(String value);

public  void setDefineCode(String value);
}
