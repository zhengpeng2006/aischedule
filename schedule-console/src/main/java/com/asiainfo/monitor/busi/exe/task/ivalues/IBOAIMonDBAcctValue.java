package com.asiainfo.monitor.busi.exe.task.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonDBAcctValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_DbAcctCode = "DB_ACCT_CODE";
  public final static  String S_Sid = "SID";
  public final static  String S_Username = "USERNAME";
  public final static  String S_Password = "PASSWORD";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ConnMax = "CONN_MAX";
  public final static  String S_ConnMin = "CONN_MIN";
  public final static  String S_Host = "HOST";
  public final static  String S_Port = "PORT";


public String getState();

public String getDbAcctCode();

public String getSid();

public String getUsername();

public String getPassword();

public String getRemarks();

public int getConnMax();

public int getConnMin();

public String getHost();

public int getPort();


public  void setState(String value);

public  void setDbAcctCode(String value);

public  void setSid(String value);

public  void setUsername(String value);

public  void setPassword(String value);

public  void setRemarks(String value);

public  void setConnMax(int value);

public  void setConnMin(int value);

public  void setHost(String value);

public  void setPort(int value);
}
