package com.asiainfo.monitor.busi.exe.task.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonPTimeValue extends DataStructInterface{

  public final static  String S_TType = "T_TYPE";
  public final static  String S_State = "STATE";
  public final static  String S_TimeId = "TIME_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Expr = "EXPR";


public String getTType();

public String getState();

public long getTimeId();

public String getRemarks();

public String getExpr();


public  void setTType(String value);

public  void setState(String value);

public  void setTimeId(long value);

public  void setRemarks(String value);

public  void setExpr(String value);
}
