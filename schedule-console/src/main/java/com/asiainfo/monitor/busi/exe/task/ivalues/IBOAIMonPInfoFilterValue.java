package com.asiainfo.monitor.busi.exe.task.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonPInfoFilterValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_FilterId = "FILTER_ID";
  public final static  String S_Expr4 = "EXPR4";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Expr3 = "EXPR3";
  public final static  String S_Expr2 = "EXPR2";
  public final static  String S_Expr1 = "EXPR1";
  public final static  String S_FilterName = "FILTER_NAME";
  public final static  String S_FilterDesc = "FILTER_DESC";
  public final static  String S_Expr5 = "EXPR5";


public String getState();

public long getFilterId();

public String getExpr4();

public String getRemarks();

public String getExpr3();

public String getExpr2();

public String getExpr1();

public String getFilterName();

public String getFilterDesc();

public String getExpr5();


public  void setState(String value);

public  void setFilterId(long value);

public  void setExpr4(String value);

public  void setRemarks(String value);

public  void setExpr3(String value);

public  void setExpr2(String value);

public  void setExpr1(String value);

public  void setFilterName(String value);

public  void setFilterDesc(String value);

public  void setExpr5(String value);
}
