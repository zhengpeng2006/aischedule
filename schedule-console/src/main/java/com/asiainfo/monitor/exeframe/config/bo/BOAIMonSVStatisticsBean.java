package com.asiainfo.monitor.exeframe.config.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSVStatisticsValue;

public class BOAIMonSVStatisticsBean extends DataContainer implements DataContainerInterface,IBOAIMonSVStatisticsValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonSVStatistics";



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

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonSVStatisticsBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initClassName(String value){
     this.initProperty(S_ClassName,value);
  }
  public  void setClassName(String value){
     this.set(S_ClassName,value);
  }
  public  void setClassNameNull(){
     this.set(S_ClassName,null);
  }

  public String getClassName(){
       return DataType.getAsString(this.get(S_ClassName));
  
  }
  public String getClassNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ClassName));
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

  public void initTotalUseTime(String value){
     this.initProperty(S_TotalUseTime,value);
  }
  public  void setTotalUseTime(String value){
     this.set(S_TotalUseTime,value);
  }
  public  void setTotalUseTimeNull(){
     this.set(S_TotalUseTime,null);
  }

  public String getTotalUseTime(){
       return DataType.getAsString(this.get(S_TotalUseTime));
  
  }
  public String getTotalUseTimeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TotalUseTime));
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

  public void initTotalCpuTime(String value){
     this.initProperty(S_TotalCpuTime,value);
  }
  public  void setTotalCpuTime(String value){
     this.set(S_TotalCpuTime,value);
  }
  public  void setTotalCpuTimeNull(){
     this.set(S_TotalCpuTime,null);
  }

  public String getTotalCpuTime(){
       return DataType.getAsString(this.get(S_TotalCpuTime));
  
  }
  public String getTotalCpuTimeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TotalCpuTime));
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

  public void initLastCpuTime(String value){
     this.initProperty(S_LastCpuTime,value);
  }
  public  void setLastCpuTime(String value){
     this.set(S_LastCpuTime,value);
  }
  public  void setLastCpuTimeNull(){
     this.set(S_LastCpuTime,null);
  }

  public String getLastCpuTime(){
       return DataType.getAsString(this.get(S_LastCpuTime));
  
  }
  public String getLastCpuTimeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_LastCpuTime));
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

  public void initMinCpuTime(String value){
     this.initProperty(S_MinCpuTime,value);
  }
  public  void setMinCpuTime(String value){
     this.set(S_MinCpuTime,value);
  }
  public  void setMinCpuTimeNull(){
     this.set(S_MinCpuTime,null);
  }

  public String getMinCpuTime(){
       return DataType.getAsString(this.get(S_MinCpuTime));
  
  }
  public String getMinCpuTimeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MinCpuTime));
      }

  public void initMaxCpuTime(String value){
     this.initProperty(S_MaxCpuTime,value);
  }
  public  void setMaxCpuTime(String value){
     this.set(S_MaxCpuTime,value);
  }
  public  void setMaxCpuTimeNull(){
     this.set(S_MaxCpuTime,null);
  }

  public String getMaxCpuTime(){
       return DataType.getAsString(this.get(S_MaxCpuTime));
  
  }
  public String getMaxCpuTimeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MaxCpuTime));
      }

  public void initMethodName(String value){
     this.initProperty(S_MethodName,value);
  }
  public  void setMethodName(String value){
     this.set(S_MethodName,value);
  }
  public  void setMethodNameNull(){
     this.set(S_MethodName,null);
  }

  public String getMethodName(){
       return DataType.getAsString(this.get(S_MethodName));
  
  }
  public String getMethodNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MethodName));
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

  public void initAgeCpuTime(String value){
     this.initProperty(S_AgeCpuTime,value);
  }
  public  void setAgeCpuTime(String value){
     this.set(S_AgeCpuTime,value);
  }
  public  void setAgeCpuTimeNull(){
     this.set(S_AgeCpuTime,null);
  }

  public String getAgeCpuTime(){
       return DataType.getAsString(this.get(S_AgeCpuTime));
  
  }
  public String getAgeCpuTimeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_AgeCpuTime));
      }

  public void initLastUseTime(String value){
     this.initProperty(S_LastUseTime,value);
  }
  public  void setLastUseTime(String value){
     this.set(S_LastUseTime,value);
  }
  public  void setLastUseTimeNull(){
     this.set(S_LastUseTime,null);
  }

  public String getLastUseTime(){
       return DataType.getAsString(this.get(S_LastUseTime));
  
  }
  public String getLastUseTimeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_LastUseTime));
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

