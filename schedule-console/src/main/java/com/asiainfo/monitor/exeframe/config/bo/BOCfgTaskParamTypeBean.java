package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgTaskParamTypeValue;

public class BOCfgTaskParamTypeBean extends DataContainer implements DataContainerInterface,IBOCfgTaskParamTypeValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgTaskParamType";



  public final static  String S_ParamValueType = "PARAM_VALUE_TYPE";
  public final static  String S_ParamName = "PARAM_NAME";
  public final static  String S_ParamId = "PARAM_ID";
  public final static  String S_DataType = "DATA_TYPE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgTaskParamTypeBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initParamValueType(String value){
     this.initProperty(S_ParamValueType,value);
  }
  public  void setParamValueType(String value){
     this.set(S_ParamValueType,value);
  }
  public  void setParamValueTypeNull(){
     this.set(S_ParamValueType,null);
  }

  public String getParamValueType(){
       return DataType.getAsString(this.get(S_ParamValueType));
  
  }
  public String getParamValueTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ParamValueType));
      }

  public void initParamName(String value){
     this.initProperty(S_ParamName,value);
  }
  public  void setParamName(String value){
     this.set(S_ParamName,value);
  }
  public  void setParamNameNull(){
     this.set(S_ParamName,null);
  }

  public String getParamName(){
       return DataType.getAsString(this.get(S_ParamName));
  
  }
  public String getParamNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ParamName));
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

  public void initDataType(String value){
     this.initProperty(S_DataType,value);
  }
  public  void setDataType(String value){
     this.set(S_DataType,value);
  }
  public  void setDataTypeNull(){
     this.set(S_DataType,null);
  }

  public String getDataType(){
       return DataType.getAsString(this.get(S_DataType));
  
  }
  public String getDataTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DataType));
      }


 
 }

