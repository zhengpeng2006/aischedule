package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleValue;

public class BOAIMonRuleBean extends DataContainer implements DataContainerInterface,IBOAIMonRuleValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonRule";



  public final static  String S_State = "STATE";
  public final static  String S_RuleKey = "RULE_KEY";
  public final static  String S_RuleKind = "RULE_KIND";
  public final static  String S_RuleId = "RULE_ID";
  public final static  String S_RuleCode = "RULE_CODE";
  public final static  String S_RuleType = "RULE_TYPE";
  public final static  String S_RuleValue = "RULE_VALUE";
  public final static  String S_Remark = "REMARK";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonRuleBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initState(String value){
     this.initProperty(S_State,value);
  }
  public  void setState(String value){
     this.set(S_State,value);
  }
  public  void setStateNull(){
     this.set(S_State,null);
  }

  public String getState(){
       return DataType.getAsString(this.get(S_State));
  
  }
  public String getStateInitialValue(){
        return DataType.getAsString(this.getOldObj(S_State));
      }

  public void initRuleKey(String value){
     this.initProperty(S_RuleKey,value);
  }
  public  void setRuleKey(String value){
     this.set(S_RuleKey,value);
  }
  public  void setRuleKeyNull(){
     this.set(S_RuleKey,null);
  }

  public String getRuleKey(){
       return DataType.getAsString(this.get(S_RuleKey));
  
  }
  public String getRuleKeyInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RuleKey));
      }

  public void initRuleKind(String value){
     this.initProperty(S_RuleKind,value);
  }
  public  void setRuleKind(String value){
     this.set(S_RuleKind,value);
  }
  public  void setRuleKindNull(){
     this.set(S_RuleKind,null);
  }

  public String getRuleKind(){
       return DataType.getAsString(this.get(S_RuleKind));
  
  }
  public String getRuleKindInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RuleKind));
      }

  public void initRuleId(long value){
     this.initProperty(S_RuleId,new Long(value));
  }
  public  void setRuleId(long value){
     this.set(S_RuleId,new Long(value));
  }
  public  void setRuleIdNull(){
     this.set(S_RuleId,null);
  }

  public long getRuleId(){
        return DataType.getAsLong(this.get(S_RuleId));
  
  }
  public long getRuleIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RuleId));
      }

  public void initRuleCode(String value){
     this.initProperty(S_RuleCode,value);
  }
  public  void setRuleCode(String value){
     this.set(S_RuleCode,value);
  }
  public  void setRuleCodeNull(){
     this.set(S_RuleCode,null);
  }

  public String getRuleCode(){
       return DataType.getAsString(this.get(S_RuleCode));
  
  }
  public String getRuleCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RuleCode));
      }

  public void initRuleType(String value){
     this.initProperty(S_RuleType,value);
  }
  public  void setRuleType(String value){
     this.set(S_RuleType,value);
  }
  public  void setRuleTypeNull(){
     this.set(S_RuleType,null);
  }

  public String getRuleType(){
       return DataType.getAsString(this.get(S_RuleType));
  
  }
  public String getRuleTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RuleType));
      }

  public void initRuleValue(String value){
     this.initProperty(S_RuleValue,value);
  }
  public  void setRuleValue(String value){
     this.set(S_RuleValue,value);
  }
  public  void setRuleValueNull(){
     this.set(S_RuleValue,null);
  }

  public String getRuleValue(){
       return DataType.getAsString(this.get(S_RuleValue));
  
  }
  public String getRuleValueInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RuleValue));
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

