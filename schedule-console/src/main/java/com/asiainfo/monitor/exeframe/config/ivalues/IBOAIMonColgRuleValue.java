package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonColgRuleValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_RuleId = "RULE_ID";
  public final static  String S_Expr5 = "EXPR5";
  public final static  String S_Expr1 = "EXPR1";
  public final static  String S_Expr7 = "EXPR7";
  public final static  String S_HostId = "HOST_ID";
  public final static  String S_Expr2 = "EXPR2";
  public final static  String S_Expr6 = "EXPR6";
  public final static  String S_AppId = "APP_ID";
  public final static  String S_RuleName = "RULE_NAME";
  public final static  String S_RuleKind = "RULE_KIND";
  public final static  String S_Expr3 = "EXPR3";
  public final static  String S_RuleType = "RULE_TYPE";
  public final static  String S_Expr9 = "EXPR9";
  public final static  String S_Expr4 = "EXPR4";
  public final static  String S_Expr8 = "EXPR8";
  public final static  String S_Expr0 = "EXPR0";
  public final static  String S_Remark = "REMARK";


public String getState();

public long getRuleId();

public String getExpr5();

public String getExpr1();

public String getExpr7();

public long getHostId();

public String getExpr2();

public String getExpr6();

public long getAppId();

public String getRuleName();

public String getRuleKind();

public String getExpr3();

public String getRuleType();

public String getExpr9();

public String getExpr4();

public String getExpr8();

public String getExpr0();

public String getRemark();


public  void setState(String value);

public  void setRuleId(long value);

public  void setExpr5(String value);

public  void setExpr1(String value);

public  void setExpr7(String value);

public  void setHostId(long value);

public  void setExpr2(String value);

public  void setExpr6(String value);

public  void setAppId(long value);

public  void setRuleName(String value);

public  void setRuleKind(String value);

public  void setExpr3(String value);

public  void setRuleType(String value);

public  void setExpr9(String value);

public  void setExpr4(String value);

public  void setExpr8(String value);

public  void setExpr0(String value);

public  void setRemark(String value);
}
