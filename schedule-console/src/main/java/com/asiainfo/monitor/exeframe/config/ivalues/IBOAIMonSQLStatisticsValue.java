package com.asiainfo.monitor.exeframe.config.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonSQLStatisticsValue extends DataStructInterface{

  public final static  String S_Sql = "SQL";
  public final static  String S_AgeUseTime = "AGE_USE_TIME";
  public final static  String S_RulesetDesc = "RULESET_DESC";
  public final static  String S_UseCount = "USE_COUNT";
  public final static  String S_CreateTime = "CREATE_TIME";
  public final static  String S_SqlTag = "SQL_TAG";
  public final static  String S_MinUseTime = "MIN_USE_TIME";
  public final static  String S_AppId = "APP_ID";
  public final static  String S_SuccCount = "SUCC_COUNT";
  public final static  String S_RulesetId = "RULESET_ID";
  public final static  String S_MaxUseTime = "MAX_USE_TIME";
  public final static  String S_RowId = "ROW_ID";
  public final static  String S_RuleTime = "RULE_TIME";
  public final static  String S_Remark = "REMARK";


public String getSql();

public String getAgeUseTime();

public String getRulesetDesc();

public long getUseCount();

public Timestamp getCreateTime();

public String getSqlTag();

public String getMinUseTime();

public long getAppId();

public long getSuccCount();

public long getRulesetId();

public String getMaxUseTime();

public long getRowId();

public String getRuleTime();

public String getRemark();


public  void setSql(String value);

public  void setAgeUseTime(String value);

public  void setRulesetDesc(String value);

public  void setUseCount(long value);

public  void setCreateTime(Timestamp value);

public  void setSqlTag(String value);

public  void setMinUseTime(String value);

public  void setAppId(long value);

public  void setSuccCount(long value);

public  void setRulesetId(long value);

public  void setMaxUseTime(String value);

public  void setRowId(long value);

public  void setRuleTime(String value);

public  void setRemark(String value);
}
