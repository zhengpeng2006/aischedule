package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonRuleSetRelatValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_RuleId = "RULE_ID";
  public final static  String S_RulesetId = "RULESET_ID";
  public final static  String S_Remark = "REMARK";


public String getState();

public long getRuleId();

public long getRulesetId();

public String getRemark();


public  void setState(String value);

public  void setRuleId(long value);

public  void setRulesetId(long value);

public  void setRemark(String value);
}
