package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonSVStatisticsTmpValue extends DataStructInterface{

  public final static  String S_Issuccess = "ISSUCCESS";
  public final static  String S_Classname = "CLASSNAME";
  public final static  String S_Flag = "FLAG";
  public final static  String S_Methodname = "METHODNAME";
  public final static  String S_Logdate = "LOGDATE";
  public final static  String S_TmpId = "TMP_ID";
  public final static  String S_Usetime = "USETIME";
  public final static  String S_Usercode = "USERCODE";
  public final static  String S_Cputime = "CPUTIME";


public int getIssuccess();

public String getClassname();

public int getFlag();

public String getMethodname();

public long getLogdate();

public long getTmpId();

public long getUsetime();

public long getUsercode();

public long getCputime();


public  void setIssuccess(int value);

public  void setClassname(String value);

public  void setFlag(int value);

public  void setMethodname(String value);

public  void setLogdate(long value);

public  void setTmpId(long value);

public  void setUsetime(long value);

public  void setUsercode(long value);

public  void setCputime(long value);
}
