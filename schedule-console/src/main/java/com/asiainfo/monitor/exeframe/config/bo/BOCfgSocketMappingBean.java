package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgSocketMappingValue;

public class BOCfgSocketMappingBean extends DataContainer implements DataContainerInterface,IBOCfgSocketMappingValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgSocketMapping";



  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_MappingId = "MAPPING_ID";
  public final static  String S_CfgSocketCode = "CFG_SOCKET_CODE";
  public final static  String S_MappingValue = "MAPPING_VALUE";
  public final static  String S_MappingName = "MAPPING_NAME";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgSocketMappingBean() throws AIException{
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

  public void initRemarks(String value){
     this.initProperty(S_Remarks,value);
  }
  public  void setRemarks(String value){
     this.set(S_Remarks,value);
  }
  public  void setRemarksNull(){
     this.set(S_Remarks,null);
  }

  public String getRemarks(){
       return DataType.getAsString(this.get(S_Remarks));
  
  }
  public String getRemarksInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Remarks));
      }

  public void initMappingId(long value){
     this.initProperty(S_MappingId,new Long(value));
  }
  public  void setMappingId(long value){
     this.set(S_MappingId,new Long(value));
  }
  public  void setMappingIdNull(){
     this.set(S_MappingId,null);
  }

  public long getMappingId(){
        return DataType.getAsLong(this.get(S_MappingId));
  
  }
  public long getMappingIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_MappingId));
      }

  public void initCfgSocketCode(String value){
     this.initProperty(S_CfgSocketCode,value);
  }
  public  void setCfgSocketCode(String value){
     this.set(S_CfgSocketCode,value);
  }
  public  void setCfgSocketCodeNull(){
     this.set(S_CfgSocketCode,null);
  }

  public String getCfgSocketCode(){
       return DataType.getAsString(this.get(S_CfgSocketCode));
  
  }
  public String getCfgSocketCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CfgSocketCode));
      }

  public void initMappingValue(String value){
     this.initProperty(S_MappingValue,value);
  }
  public  void setMappingValue(String value){
     this.set(S_MappingValue,value);
  }
  public  void setMappingValueNull(){
     this.set(S_MappingValue,null);
  }

  public String getMappingValue(){
       return DataType.getAsString(this.get(S_MappingValue));
  
  }
  public String getMappingValueInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MappingValue));
      }

  public void initMappingName(String value){
     this.initProperty(S_MappingName,value);
  }
  public  void setMappingName(String value){
     this.set(S_MappingName,value);
  }
  public  void setMappingNameNull(){
     this.set(S_MappingName,null);
  }

  public String getMappingName(){
       return DataType.getAsString(this.get(S_MappingName));
  
  }
  public String getMappingNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MappingName));
      }


 
 }

