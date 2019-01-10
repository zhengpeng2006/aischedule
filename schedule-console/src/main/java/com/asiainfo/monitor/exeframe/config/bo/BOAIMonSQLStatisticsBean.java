package com.asiainfo.monitor.exeframe.config.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSQLStatisticsValue;

public class BOAIMonSQLStatisticsBean extends DataContainer implements DataContainerInterface,IBOAIMonSQLStatisticsValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonSQLStatistics";



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

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonSQLStatisticsBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initSql(String value){
     this.initProperty(S_Sql,value);
  }
  public  void setSql(String value){
     this.set(S_Sql,value);
  }
  public  void setSqlNull(){
     this.set(S_Sql,null);
  }

  public String getSql(){
       return DataType.getAsString(this.get(S_Sql));
  
  }
  public String getSqlInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Sql));
      }

  public void initAgeUseTime(String value){
     this.initProperty(S_AgeUseTime,value);
  }
  public  void setAgeUseTime(String value){
     this.set(S_AgeUseTime,value);
  }
  public  void setAgeUseTimeNull(){
     this.set(S_AgeUseTime,null);
  }

  public String getAgeUseTime(){
       return DataType.getAsString(this.get(S_AgeUseTime));
  
  }
  public String getAgeUseTimeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_AgeUseTime));
      }

  public void initRulesetDesc(String value){
     this.initProperty(S_RulesetDesc,value);
  }
  public  void setRulesetDesc(String value){
     this.set(S_RulesetDesc,value);
  }
  public  void setRulesetDescNull(){
     this.set(S_RulesetDesc,null);
  }

  public String getRulesetDesc(){
       return DataType.getAsString(this.get(S_RulesetDesc));
  
  }
  public String getRulesetDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RulesetDesc));
      }

  public void initUseCount(long value){
     this.initProperty(S_UseCount,new Long(value));
  }
  public  void setUseCount(long value){
     this.set(S_UseCount,new Long(value));
  }
  public  void setUseCountNull(){
     this.set(S_UseCount,null);
  }

  public long getUseCount(){
        return DataType.getAsLong(this.get(S_UseCount));
  
  }
  public long getUseCountInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_UseCount));
      }

  public void initCreateTime(Timestamp value){
     this.initProperty(S_CreateTime,value);
  }
  public  void setCreateTime(Timestamp value){
     this.set(S_CreateTime,value);
  }
  public  void setCreateTimeNull(){
     this.set(S_CreateTime,null);
  }

  public Timestamp getCreateTime(){
        return DataType.getAsDateTime(this.get(S_CreateTime));
  
  }
  public Timestamp getCreateTimeInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_CreateTime));
      }

  public void initSqlTag(String value){
     this.initProperty(S_SqlTag,value);
  }
  public  void setSqlTag(String value){
     this.set(S_SqlTag,value);
  }
  public  void setSqlTagNull(){
     this.set(S_SqlTag,null);
  }

  public String getSqlTag(){
       return DataType.getAsString(this.get(S_SqlTag));
  
  }
  public String getSqlTagInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SqlTag));
      }

  public void initMinUseTime(String value){
     this.initProperty(S_MinUseTime,value);
  }
  public  void setMinUseTime(String value){
     this.set(S_MinUseTime,value);
  }
  public  void setMinUseTimeNull(){
     this.set(S_MinUseTime,null);
  }

  public String getMinUseTime(){
       return DataType.getAsString(this.get(S_MinUseTime));
  
  }
  public String getMinUseTimeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MinUseTime));
      }

  public void initAppId(long value){
     this.initProperty(S_AppId,new Long(value));
  }
  public  void setAppId(long value){
     this.set(S_AppId,new Long(value));
  }
  public  void setAppIdNull(){
     this.set(S_AppId,null);
  }

  public long getAppId(){
        return DataType.getAsLong(this.get(S_AppId));
  
  }
  public long getAppIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_AppId));
      }

  public void initSuccCount(long value){
     this.initProperty(S_SuccCount,new Long(value));
  }
  public  void setSuccCount(long value){
     this.set(S_SuccCount,new Long(value));
  }
  public  void setSuccCountNull(){
     this.set(S_SuccCount,null);
  }

  public long getSuccCount(){
        return DataType.getAsLong(this.get(S_SuccCount));
  
  }
  public long getSuccCountInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_SuccCount));
      }

  public void initRulesetId(long value){
     this.initProperty(S_RulesetId,new Long(value));
  }
  public  void setRulesetId(long value){
     this.set(S_RulesetId,new Long(value));
  }
  public  void setRulesetIdNull(){
     this.set(S_RulesetId,null);
  }

  public long getRulesetId(){
        return DataType.getAsLong(this.get(S_RulesetId));
  
  }
  public long getRulesetIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RulesetId));
      }

  public void initMaxUseTime(String value){
     this.initProperty(S_MaxUseTime,value);
  }
  public  void setMaxUseTime(String value){
     this.set(S_MaxUseTime,value);
  }
  public  void setMaxUseTimeNull(){
     this.set(S_MaxUseTime,null);
  }

  public String getMaxUseTime(){
       return DataType.getAsString(this.get(S_MaxUseTime));
  
  }
  public String getMaxUseTimeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MaxUseTime));
      }

  public void initRowId(long value){
     this.initProperty(S_RowId,new Long(value));
  }
  public  void setRowId(long value){
     this.set(S_RowId,new Long(value));
  }
  public  void setRowIdNull(){
     this.set(S_RowId,null);
  }

  public long getRowId(){
        return DataType.getAsLong(this.get(S_RowId));
  
  }
  public long getRowIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RowId));
      }

  public void initRuleTime(String value){
     this.initProperty(S_RuleTime,value);
  }
  public  void setRuleTime(String value){
     this.set(S_RuleTime,value);
  }
  public  void setRuleTimeNull(){
     this.set(S_RuleTime,null);
  }

  public String getRuleTime(){
       return DataType.getAsString(this.get(S_RuleTime));
  
  }
  public String getRuleTimeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RuleTime));
      }

  public void initRemark(String value){
     this.initProperty(S_Remark,value);
  }
  public  void setRemark(String value){
     this.set(S_Remark,value);
  }
  public  void setRemarkNull(){
     this.set(S_Remark,null);
  }

  public String getRemark(){
       return DataType.getAsString(this.get(S_Remark));
  
  }
  public String getRemarkInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Remark));
      }


 
 }

