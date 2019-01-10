package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAppRuleSetValue;

public class BOAIMonAppRuleSetBean extends DataContainer implements DataContainerInterface,IBOAIMonAppRuleSetValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonAppRuleSet";



  public final static  String S_State = "STATE";
  public final static  String S_AppRuleType = "APP_RULE_TYPE";
  public final static  String S_AppId = "APP_ID";
  public final static  String S_StategyImpl = "STATEGY_IMPL";
  public final static  String S_RulesetIds = "RULESET_IDS";
  public final static  String S_Remark = "REMARK";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonAppRuleSetBean() throws AIException{
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

  public void initAppRuleType(String value){
     this.initProperty(S_AppRuleType,value);
  }
  public  void setAppRuleType(String value){
     this.set(S_AppRuleType,value);
  }
  public  void setAppRuleTypeNull(){
     this.set(S_AppRuleType,null);
  }

  public String getAppRuleType(){
       return DataType.getAsString(this.get(S_AppRuleType));
  
  }
  public String getAppRuleTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_AppRuleType));
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

  public void initStategyImpl(String value){
     this.initProperty(S_StategyImpl,value);
  }
  public  void setStategyImpl(String value){
     this.set(S_StategyImpl,value);
  }
  public  void setStategyImplNull(){
     this.set(S_StategyImpl,null);
  }

  public String getStategyImpl(){
       return DataType.getAsString(this.get(S_StategyImpl));
  
  }
  public String getStategyImplInitialValue(){
        return DataType.getAsString(this.getOldObj(S_StategyImpl));
      }

  public void initRulesetIds(String value){
     this.initProperty(S_RulesetIds,value);
  }
  public  void setRulesetIds(String value){
     this.set(S_RulesetIds,value);
  }
  public  void setRulesetIdsNull(){
     this.set(S_RulesetIds,null);
  }

  public String getRulesetIds(){
       return DataType.getAsString(this.get(S_RulesetIds));
  
  }
  public String getRulesetIdsInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RulesetIds));
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

