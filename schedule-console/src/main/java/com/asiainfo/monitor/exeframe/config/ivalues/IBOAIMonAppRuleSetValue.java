package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonAppRuleSetValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_AppRuleType = "APP_RULE_TYPE";
  public final static  String S_AppId = "APP_ID";
  public final static  String S_StategyImpl = "STATEGY_IMPL";
  public final static  String S_RulesetIds = "RULESET_IDS";
  public final static  String S_Remark = "REMARK";


public String getState();

public String getAppRuleType();

public long getAppId();

public String getStategyImpl();

public String getRulesetIds();

public String getRemark();


public  void setState(String value);

public  void setAppRuleType(String value);

public  void setAppId(long value);

public  void setStategyImpl(String value);

public  void setRulesetIds(String value);

public  void setRemark(String value);
}
