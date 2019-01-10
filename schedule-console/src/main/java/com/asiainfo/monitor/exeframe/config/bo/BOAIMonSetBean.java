package com.asiainfo.monitor.exeframe.config.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSetValue;

public class BOAIMonSetBean extends DataContainer implements DataContainerInterface,IBOAIMonSetValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonSet";



  public final static  String S_Operater = "OPERATER";
  public final static  String S_State = "STATE";
  public final static  String S_ServerId = "SERVER_ID";
  public final static  String S_SetVcode = "SET_VCODE";
  public final static  String S_AppName = "APP_NAME";
  public final static  String S_SetDesc = "SET_DESC";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_HostId = "HOST_ID";
  public final static  String S_SetId = "SET_ID";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_SetValue = "SET_VALUE";
  public final static  String S_SetCode = "SET_CODE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonSetBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initOperater(String value){
     this.initProperty(S_Operater,value);
  }
  public  void setOperater(String value){
     this.set(S_Operater,value);
  }
  public  void setOperaterNull(){
     this.set(S_Operater,null);
  }

  public String getOperater(){
       return DataType.getAsString(this.get(S_Operater));
  
  }
  public String getOperaterInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Operater));
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

  public void initServerId(long value){
     this.initProperty(S_ServerId,new Long(value));
  }
  public  void setServerId(long value){
     this.set(S_ServerId,new Long(value));
  }
  public  void setServerIdNull(){
     this.set(S_ServerId,null);
  }

  public long getServerId(){
        return DataType.getAsLong(this.get(S_ServerId));
  
  }
  public long getServerIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ServerId));
      }

  public void initSetVcode(String value){
     this.initProperty(S_SetVcode,value);
  }
  public  void setSetVcode(String value){
     this.set(S_SetVcode,value);
  }
  public  void setSetVcodeNull(){
     this.set(S_SetVcode,null);
  }

  public String getSetVcode(){
       return DataType.getAsString(this.get(S_SetVcode));
  
  }
  public String getSetVcodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SetVcode));
      }

  public void initAppName(String value){
     this.initProperty(S_AppName,value);
  }
  public  void setAppName(String value){
     this.set(S_AppName,value);
  }
  public  void setAppNameNull(){
     this.set(S_AppName,null);
  }

  public String getAppName(){
       return DataType.getAsString(this.get(S_AppName));
  
  }
  public String getAppNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_AppName));
      }

  public void initSetDesc(String value){
     this.initProperty(S_SetDesc,value);
  }
  public  void setSetDesc(String value){
     this.set(S_SetDesc,value);
  }
  public  void setSetDescNull(){
     this.set(S_SetDesc,null);
  }

  public String getSetDesc(){
       return DataType.getAsString(this.get(S_SetDesc));
  
  }
  public String getSetDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SetDesc));
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

  public void initHostId(long value){
     this.initProperty(S_HostId,new Long(value));
  }
  public  void setHostId(long value){
     this.set(S_HostId,new Long(value));
  }
  public  void setHostIdNull(){
     this.set(S_HostId,null);
  }

  public long getHostId(){
        return DataType.getAsLong(this.get(S_HostId));
  
  }
  public long getHostIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_HostId));
      }

  public void initSetId(long value){
     this.initProperty(S_SetId,new Long(value));
  }
  public  void setSetId(long value){
     this.set(S_SetId,new Long(value));
  }
  public  void setSetIdNull(){
     this.set(S_SetId,null);
  }

  public long getSetId(){
        return DataType.getAsLong(this.get(S_SetId));
  
  }
  public long getSetIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_SetId));
      }

  public void initCreateDate(Timestamp value){
     this.initProperty(S_CreateDate,value);
  }
  public  void setCreateDate(Timestamp value){
     this.set(S_CreateDate,value);
  }
  public  void setCreateDateNull(){
     this.set(S_CreateDate,null);
  }

  public Timestamp getCreateDate(){
        return DataType.getAsDateTime(this.get(S_CreateDate));
  
  }
  public Timestamp getCreateDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_CreateDate));
      }

  public void initSetValue(String value){
     this.initProperty(S_SetValue,value);
  }
  public  void setSetValue(String value){
     this.set(S_SetValue,value);
  }
  public  void setSetValueNull(){
     this.set(S_SetValue,null);
  }

  public String getSetValue(){
       return DataType.getAsString(this.get(S_SetValue));
  
  }
  public String getSetValueInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SetValue));
      }

  public void initSetCode(String value){
     this.initProperty(S_SetCode,value);
  }
  public  void setSetCode(String value){
     this.set(S_SetCode,value);
  }
  public  void setSetCodeNull(){
     this.set(S_SetCode,null);
  }

  public String getSetCode(){
       return DataType.getAsString(this.get(S_SetCode));
  
  }
  public String getSetCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SetCode));
      }


 
 }

