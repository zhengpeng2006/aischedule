package com.asiainfo.deploy.common.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.deploy.common.ivalues.IBODeployAppParamsValue;

public class BODeployAppParamsBean extends DataContainer implements DataContainerInterface,IBODeployAppParamsValue{

  private static String  m_boName = "com.asiainfo.deploy.common.bo.BODeployAppParams";



  public final static  String S_ApplicationParamId = "APPLICATION_PARAM_ID";
  public final static  String S_Key = "KEY";
  public final static  String S_ApplicationId = "APPLICATION_ID";
  public final static  String S_Value = "VALUE";
  public final static  String S_ParamType = "PARAM_TYPE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BODeployAppParamsBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 @Override
public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initApplicationParamId(long value){
     this.initProperty(S_ApplicationParamId,new Long(value));
  }
  @Override
public  void setApplicationParamId(long value){
     this.set(S_ApplicationParamId,new Long(value));
  }
  public  void setApplicationParamIdNull(){
     this.set(S_ApplicationParamId,null);
  }

  @Override
public long getApplicationParamId(){
        return DataType.getAsLong(this.get(S_ApplicationParamId));
  
  }
  public long getApplicationParamIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ApplicationParamId));
      }

  public void initKey(String value){
     this.initProperty(S_Key,value);
  }
  @Override
public  void setKey(String value){
     this.set(S_Key,value);
  }
  public  void setKeyNull(){
     this.set(S_Key,null);
  }

  @Override
public String getKey(){
       return DataType.getAsString(this.get(S_Key));
  
  }
  public String getKeyInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Key));
      }

  public void initApplicationId(long value){
     this.initProperty(S_ApplicationId,new Long(value));
  }
  @Override
public  void setApplicationId(long value){
     this.set(S_ApplicationId,new Long(value));
  }
  public  void setApplicationIdNull(){
     this.set(S_ApplicationId,null);
  }

  @Override
public long getApplicationId(){
        return DataType.getAsLong(this.get(S_ApplicationId));
  
  }
  public long getApplicationIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ApplicationId));
      }

  public void initValue(String value){
     this.initProperty(S_Value,value);
  }
  @Override
public  void setValue(String value){
     this.set(S_Value,value);
  }
  public  void setValueNull(){
     this.set(S_Value,null);
  }

  @Override
public String getValue(){
       return DataType.getAsString(this.get(S_Value));
  
  }
  public String getValueInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Value));
      }

  public void initParamType(String value){
     this.initProperty(S_ParamType,value);
  }
  @Override
public  void setParamType(String value){
     this.set(S_ParamType,value);
  }
  public  void setParamTypeNull(){
     this.set(S_ParamType,null);
  }

  @Override
public String getParamType(){
       return DataType.getAsString(this.get(S_ParamType));
  
  }
  public String getParamTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ParamType));
      }


 
 }

