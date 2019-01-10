package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgTaskParamValueValue;

public class BOCfgTaskParamValueBean extends DataContainer implements DataContainerInterface,IBOCfgTaskParamValueValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgTaskParamValue";



  public final static  String S_Id = "ID";
  public final static  String S_CfgTaskId = "CFG_TASK_ID";
  public final static  String S_ParamValue = "PARAM_VALUE";
  public final static  String S_ParamId = "PARAM_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgTaskParamValueBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
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

  public void initCfgTaskId(long value){
     this.initProperty(S_CfgTaskId,new Long(value));
  }
  public  void setCfgTaskId(long value){
     this.set(S_CfgTaskId,new Long(value));
  }
  public  void setCfgTaskIdNull(){
     this.set(S_CfgTaskId,null);
  }

  public long getCfgTaskId(){
        return DataType.getAsLong(this.get(S_CfgTaskId));
  
  }
  public long getCfgTaskIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_CfgTaskId));
      }

  public void initParamValue(String value){
     this.initProperty(S_ParamValue,value);
  }
  public  void setParamValue(String value){
     this.set(S_ParamValue,value);
  }
  public  void setParamValueNull(){
     this.set(S_ParamValue,null);
  }

  public String getParamValue(){
       return DataType.getAsString(this.get(S_ParamValue));
  
  }
  public String getParamValueInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ParamValue));
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


 
 }

