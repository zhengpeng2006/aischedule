package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgTaskParamDefineValue;

public class BOCfgTaskParamDefineBean extends DataContainer implements DataContainerInterface,IBOCfgTaskParamDefineValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgTaskParamDefine";



  public final static  String S_State = "STATE";
  public final static  String S_Id = "ID";
  public final static  String S_CfgTaskTypeId = "CFG_TASK_TYPE_ID";
  public final static  String S_ParamId = "PARAM_ID";
  public final static  String S_IsMust = "IS_MUST";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgTaskParamDefineBean() throws AIException{
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

  public void initId(long value){
     this.initProperty(S_Id,new Long(value));
  }
  public  void setId(long value){
     this.set(S_Id,new Long(value));
  }
  public  void setIdNull(){
     this.set(S_Id,null);
  }

  public long getId(){
        return DataType.getAsLong(this.get(S_Id));
  
  }
  public long getIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Id));
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

  public void initParamId(long value){
     this.initProperty(S_ParamId,new Long(value));
  }
  public  void setParamId(long value){
     this.set(S_ParamId,new Long(value));
  }
  public  void setParamIdNull(){
     this.set(S_ParamId,null);
  }

  public long getParamId(){
        return DataType.getAsLong(this.get(S_ParamId));
  
  }
  public long getParamIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ParamId));
      }

  public void initIsMust(String value){
     this.initProperty(S_IsMust,value);
  }
  public  void setIsMust(String value){
     this.set(S_IsMust,value);
  }
  public  void setIsMustNull(){
     this.set(S_IsMust,null);
  }

  public String getIsMust(){
       return DataType.getAsString(this.get(S_IsMust));
  
  }
  public String getIsMustInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IsMust));
      }


 
 }

