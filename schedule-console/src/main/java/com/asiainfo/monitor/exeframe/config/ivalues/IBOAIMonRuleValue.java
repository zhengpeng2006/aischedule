package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonRuleValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_RuleKey = "RULE_KEY";
  public final static  String S_RuleKind = "RULE_KIND";
  public final static  String S_RuleId = "RULE_ID";
  public final static  String S_RuleCode = "RULE_CODE";
  public final static  String S_RuleType = "RULE_TYPE";
  public final static  String S_RuleValue = "RULE_VALUE";
  public final static  String S_Remark = "REMARK";


public String getState();

public String getRuleKey();

public String getRuleKind();

public long getRuleId();

public String getRuleCode();

public String getRuleType();

public String getRuleValue();

public String getRemark();


public  void setState(String value);

public  void setRuleKey(String value);

public  void setRuleKind(String value);

public  void setRuleId(long value);

public  void setRuleCode(String value);

public  void setRuleType(String value);

public  void setRuleValue(String value);

public  void setRemark(String value);
}
