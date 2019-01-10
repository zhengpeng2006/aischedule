package com.asiainfo.monitor.exeframe.config.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonSVStatisticsValue extends DataStructInterface{

  public final static  String S_ClassName = "CLASS_NAME";
  public final static  String S_AgeUseTime = "AGE_USE_TIME";
  public final static  String S_TotalUseTime = "TOTAL_USE_TIME";
  public final static  String S_RulesetDesc = "RULESET_DESC";
  public final static  String S_UseCount = "USE_COUNT";
  public final static  String S_CreateTime = "CREATE_TIME";
  public final static  String S_TotalCpuTime = "TOTAL_CPU_TIME";
  public final static  String S_MinUseTime = "MIN_USE_TIME";
  public final static  String S_LastCpuTime = "LAST_CPU_TIME";
  public final static  String S_SuccCount = "SUCC_COUNT";
  public final static  String S_AppId = "APP_ID";
  public final static  String S_MinCpuTime = "MIN_CPU_TIME";
  public final static  String S_MaxCpuTime = "MAX_CPU_TIME";
  public final static  String S_MethodName = "METHOD_NAME";
  public final static  String S_RulesetId = "RULESET_ID";
  public final static  String S_MaxUseTime = "MAX_USE_TIME";
  public final static  String S_AgeCpuTime = "AGE_CPU_TIME";
  public final static  String S_LastUseTime = "LAST_USE_TIME";
  public final static  String S_RowId = "ROW_ID";
  public final static  String S_RuleTime = "RULE_TIME";
  public final static  String S_Remark = "REMARK";


public String getClassName();

public String getAgeUseTime();

public String getTotalUseTime();

public String getRulesetDesc();

public long getUseCount();

public Timestamp getCreateTime();

public String getTotalCpuTime();

public String getMinUseTime();

public String getLastCpuTime();

public long getSuccCount();

public long getAppId();

public String getMinCpuTime();

public String getMaxCpuTime();

public String getMethodName();

public long getRulesetId();

public String getMaxUseTime();

public String getAgeCpuTime();

public String getLastUseTime();

public long getRowId();

public String getRuleTime();

public String getRemark();


public  void setClassName(String value);

public  void setAgeUseTime(String value);

public  void setTotalUseTime(String value);

public  void setRulesetDesc(String value);

public  void setUseCount(long value);

public  void setCreateTime(Timestamp value);

public  void setTotalCpuTime(String value);

public  void setMinUseTime(String value);

public  void setLastCpuTime(String value);

public  void setSuccCount(long value);

public  void setAppId(long value);

public  void setMinCpuTime(String value);

public  void setMaxCpuTime(String value);

public  void setMethodName(String value);

public  void setRulesetId(long value);

public  void setMaxUseTime(String value);

public  void setAgeCpuTime(String value);

public  void setLastUseTime(String value);

public  void setRowId(long value);

public  void setRuleTime(String value);

public  void setRemark(String value);
}
