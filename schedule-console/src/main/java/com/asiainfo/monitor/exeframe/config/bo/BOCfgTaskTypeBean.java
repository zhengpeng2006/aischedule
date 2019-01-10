package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgTaskTypeValue;

public class BOCfgTaskTypeBean extends DataContainer implements DataContainerInterface,IBOCfgTaskTypeValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgTaskType";



  public final static  String S_State = "STATE";
  public final static  String S_BusinessClass = "BUSINESS_CLASS";
  public final static  String S_CfgTaskTypeId = "CFG_TASK_TYPE_ID";
  public final static  String S_CfgTaskTypeCode = "CFG_TASK_TYPE_CODE";
  public final static  String S_CfgTaskTypeName = "CFG_TASK_TYPE_NAME";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgTaskTypeBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
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

  public void initBusinessClass(String value){
     this.initProperty(S_BusinessClass,value);
  }
  public  void setBusinessClass(String value){
     this.set(S_BusinessClass,value);
  }
  public  void setBusinessClassNull(){
     this.set(S_BusinessClass,null);
  }

  public String getBusinessClass(){
       return DataType.getAsString(this.get(S_BusinessClass));
  
  }
  public String getBusinessClassInitialValue(){
        return DataType.getAsString(this.getOldObj(S_BusinessClass));
      }

  public void initCfgTaskTypeId(long value){
     this.initProperty(S_CfgTaskTypeId,new Long(value));
  }
  public  void setCfgTaskTypeId(long value){
     this.set(S_CfgTaskTypeId,new Long(value));
  }
  public  void setCfgTaskTypeIdNull(){
     this.set(S_CfgTaskTypeId,null);
  }

  public long getCfgTaskTypeId(){
        return DataType.getAsLong(this.get(S_CfgTaskTypeId));
  
  }
  public long getCfgTaskTypeIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_CfgTaskTypeId));
      }

  public void initCfgTaskTypeCode(String value){
     this.initProperty(S_CfgTaskTypeCode,value);
  }
  public  void setCfgTaskTypeCode(String value){
     this.set(S_CfgTaskTypeCode,value);
  }
  public  void setCfgTaskTypeCodeNull(){
     this.set(S_CfgTaskTypeCode,null);
  }

  public String getCfgTaskTypeCode(){
       return DataType.getAsString(this.get(S_CfgTaskTypeCode));
  
  }
  public String getCfgTaskTypeCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CfgTaskTypeCode));
      }

  public void initCfgTaskTypeName(String value){
     this.initProperty(S_CfgTaskTypeName,value);
  }
  public  void setCfgTaskTypeName(String value){
     this.set(S_CfgTaskTypeName,value);
  }
  public  void setCfgTaskTypeNameNull(){
     this.set(S_CfgTaskTypeName,null);
  }

  public String getCfgTaskTypeName(){
       return DataType.getAsString(this.get(S_CfgTaskTypeName));
  
  }
  public String getCfgTaskTypeNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CfgTaskTypeName));
      }


 
 }

