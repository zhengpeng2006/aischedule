package com.asiainfo.monitor.exeframe.config.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonACTStatisticsValue extends DataStructInterface{

  public final static  String S_ClassName = "CLASS_NAME";
  public final static  String S_AgeUseTime = "AGE_USE_TIME";
  public final static  String S_RulesetDesc = "RULESET_DESC";
  public final static  String S_UseCount = "USE_COUNT";
  public final static  String S_CreateTime = "CREATE_TIME";
  public final static  String S_MinUseTime = "MIN_USE_TIME";
  public final static  String S_SuccCount = "SUCC_COUNT";
  public final static  String S_AppId = "APP_ID";
  public final static  String S_MethodName = "METHOD_NAME";
  public final static  String S_RulesetId = "RULESET_ID";
  public final static  String S_AppName = "APP_NAME";
  public final static  String S_MaxUseTime = "MAX_USE_TIME";
  public final static  String S_RowId = "ROW_ID";
  public final static  String S_RuleTime = "RULE_TIME";
  public final static  String S_Remark = "REMARK";


public String getClassName();

public String getAgeUseTime();

public String getRulesetDesc();

public long getUseCount();

public Timestamp getCreateTime();

public String getMinUseTime();

public long getSuccCount();

public long getAppId();

public String getMethodName();

public long getRulesetId();

public String getAppName();

public String getMaxUseTime();

public long getRowId();

public String getRuleTime();

public String getRemark();


public  void setClassName(String value);

public  void setAgeUseTime(String value);

public  void setRulesetDesc(String value);

public  void setUseCount(long value);

public  void setCreateTime(Timestamp value);

public  void setMinUseTime(String value);

public  void setSuccCount(long value);

public  void setAppId(long value);

public  void setMethodName(String value);

public  void setRulesetId(long value);

public  void setAppName(String value);

public  void setMaxUseTime(String value);

public  void setRowId(long value);

public  void setRuleTime(String value);

public  void setRemark(String value);
}
