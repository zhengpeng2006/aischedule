package com.asiainfo.monitor.busi.exe.task.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonThresholdValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_Expr5 = "EXPR5";
  public final static  String S_Expr1 = "EXPR1";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ThresholdName = "THRESHOLD_NAME";
  public final static  String S_Expr2 = "EXPR2";
  public final static  String S_ExpiryDays = "EXPIRY_DAYS";
  public final static  String S_Expr3 = "EXPR3";
  public final static  String S_Expr4 = "EXPR4";
  public final static  String S_ThresholdId = "THRESHOLD_ID";


public String getState();

public String getExpr5();

public String getExpr1();

public String getRemarks();

public String getThresholdName();

public String getExpr2();

public int getExpiryDays();

public String getExpr3();

public String getExpr4();

public long getThresholdId();


public  void setState(String value);

public  void setExpr5(String value);

public  void setExpr1(String value);

public  void setRemarks(String value);

public  void setThresholdName(String value);

public  void setExpr2(String value);

public  void setExpiryDays(int value);

public  void setExpr3(String value);

public  void setExpr4(String value);

public  void setThresholdId(long value);
}
