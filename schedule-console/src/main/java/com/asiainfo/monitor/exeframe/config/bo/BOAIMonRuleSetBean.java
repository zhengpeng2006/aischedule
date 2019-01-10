package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleSetValue;

public class BOAIMonRuleSetBean extends DataContainer implements DataContainerInterface,IBOAIMonRuleSetValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonRuleSet";



  public final static  String S_State = "STATE";
  public final static  String S_RulesetKind = "RULESET_KIND";
  public final static  String S_RulesetName = "RULESET_NAME";
  public final static  String S_RulesetId = "RULESET_ID";
  public final static  String S_RulesetCode = "RULESET_CODE";
  public final static  String S_RulesetType = "RULESET_TYPE";
  public final static  String S_Remark = "REMARK";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonRuleSetBean() throws AIException{
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

  public void initRulesetKind(String value){
     this.initProperty(S_RulesetKind,value);
  }
  public  void setRulesetKind(String value){
     this.set(S_RulesetKind,value);
  }
  public  void setRulesetKindNull(){
     this.set(S_RulesetKind,null);
  }

  public String getRulesetKind(){
       return DataType.getAsString(this.get(S_RulesetKind));
  
  }
  public String getRulesetKindInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RulesetKind));
      }

  public void initRulesetName(String value){
     this.initProperty(S_RulesetName,value);
  }
  public  void setRulesetName(String value){
     this.set(S_RulesetName,value);
  }
  public  void setRulesetNameNull(){
     this.set(S_RulesetName,null);
  }

  public String getRulesetName(){
       return DataType.getAsString(this.get(S_RulesetName));
  
  }
  public String getRulesetNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RulesetName));
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

  public void initRulesetCode(String value){
     this.initProperty(S_RulesetCode,value);
  }
  public  void setRulesetCode(String value){
     this.set(S_RulesetCode,value);
  }
  public  void setRulesetCodeNull(){
     this.set(S_RulesetCode,null);
  }

  public String getRulesetCode(){
       return DataType.getAsString(this.get(S_RulesetCode));
  
  }
  public String getRulesetCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RulesetCode));
      }

  public void initRulesetType(String value){
     this.initProperty(S_RulesetType,value);
  }
  public  void setRulesetType(String value){
     this.set(S_RulesetType,value);
  }
  public  void setRulesetTypeNull(){
     this.set(S_RulesetType,null);
  }

  public String getRulesetType(){
       return DataType.getAsString(this.get(S_RulesetType));
  
  }
  public String getRulesetTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RulesetType));
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

