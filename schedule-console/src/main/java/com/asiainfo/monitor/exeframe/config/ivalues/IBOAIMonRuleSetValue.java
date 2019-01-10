package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonRuleSetValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_RulesetKind = "RULESET_KIND";
  public final static  String S_RulesetName = "RULESET_NAME";
  public final static  String S_RulesetId = "RULESET_ID";
  public final static  String S_RulesetCode = "RULESET_CODE";
  public final static  String S_RulesetType = "RULESET_TYPE";
  public final static  String S_Remark = "REMARK";


public String getState();

public String getRulesetKind();

public String getRulesetName();

public long getRulesetId();

public String getRulesetCode();

public String getRulesetType();

public String getRemark();


public  void setState(String value);

public  void setRulesetKind(String value);

public  void setRulesetName(String value);

public  void setRulesetId(long value);

public  void setRulesetCode(String value);

public  void setRulesetType(String value);

public  void setRemark(String value);
}
