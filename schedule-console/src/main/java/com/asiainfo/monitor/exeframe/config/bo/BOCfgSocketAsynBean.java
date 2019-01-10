package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgSocketAsynValue;

public class BOCfgSocketAsynBean extends DataContainer implements DataContainerInterface,IBOCfgSocketAsynValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgSocketAsyn";



  public final static  String S_State = "STATE";
  public final static  String S_WorkThreadMin = "WORK_THREAD_MIN";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CfgSocketCode = "CFG_SOCKET_CODE";
  public final static  String S_WorkKeepAliveTime = "WORK_KEEP_ALIVE_TIME";
  public final static  String S_SendBufferSize = "SEND_BUFFER_SIZE";
  public final static  String S_SendKeepAliveTime = "SEND_KEEP_ALIVE_TIME";
  public final static  String S_IsAsyn = "IS_ASYN";
  public final static  String S_OverloadProtectPercent = "OVERLOAD_PROTECT_PERCENT";
  public final static  String S_SendThreadMin = "SEND_THREAD_MIN";
  public final static  String S_SendThreadMax = "SEND_THREAD_MAX";
  public final static  String S_WorkBufferSize = "WORK_BUFFER_SIZE";
  public final static  String S_WorkThreadMax = "WORK_THREAD_MAX";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgSocketAsynBean() throws AIException{
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

  public void initWorkThreadMin(int value){
     this.initProperty(S_WorkThreadMin,new Integer(value));
  }
  public  void setWorkThreadMin(int value){
     this.set(S_WorkThreadMin,new Integer(value));
  }
  public  void setWorkThreadMinNull(){
     this.set(S_WorkThreadMin,null);
  }

  public int getWorkThreadMin(){
        return DataType.getAsInt(this.get(S_WorkThreadMin));
  
  }
  public int getWorkThreadMinInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_WorkThreadMin));
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

  public void initWorkKeepAliveTime(int value){
     this.initProperty(S_WorkKeepAliveTime,new Integer(value));
  }
  public  void setWorkKeepAliveTime(int value){
     this.set(S_WorkKeepAliveTime,new Integer(value));
  }
  public  void setWorkKeepAliveTimeNull(){
     this.set(S_WorkKeepAliveTime,null);
  }

  public int getWorkKeepAliveTime(){
        return DataType.getAsInt(this.get(S_WorkKeepAliveTime));
  
  }
  public int getWorkKeepAliveTimeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_WorkKeepAliveTime));
      }

  public void initSendBufferSize(int value){
     this.initProperty(S_SendBufferSize,new Integer(value));
  }
  public  void setSendBufferSize(int value){
     this.set(S_SendBufferSize,new Integer(value));
  }
  public  void setSendBufferSizeNull(){
     this.set(S_SendBufferSize,null);
  }

  public int getSendBufferSize(){
        return DataType.getAsInt(this.get(S_SendBufferSize));
  
  }
  public int getSendBufferSizeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_SendBufferSize));
      }

  public void initSendKeepAliveTime(int value){
     this.initProperty(S_SendKeepAliveTime,new Integer(value));
  }
  public  void setSendKeepAliveTime(int value){
     this.set(S_SendKeepAliveTime,new Integer(value));
  }
  public  void setSendKeepAliveTimeNull(){
     this.set(S_SendKeepAliveTime,null);
  }

  public int getSendKeepAliveTime(){
        return DataType.getAsInt(this.get(S_SendKeepAliveTime));
  
  }
  public int getSendKeepAliveTimeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_SendKeepAliveTime));
      }

  public void initIsAsyn(String value){
     this.initProperty(S_IsAsyn,value);
  }
  public  void setIsAsyn(String value){
     this.set(S_IsAsyn,value);
  }
  public  void setIsAsynNull(){
     this.set(S_IsAsyn,null);
  }

  public String getIsAsyn(){
       return DataType.getAsString(this.get(S_IsAsyn));
  
  }
  public String getIsAsynInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IsAsyn));
      }

  public void initOverloadProtectPercent(int value){
     this.initProperty(S_OverloadProtectPercent,new Integer(value));
  }
  public  void setOverloadProtectPercent(int value){
     this.set(S_OverloadProtectPercent,new Integer(value));
  }
  public  void setOverloadProtectPercentNull(){
     this.set(S_OverloadProtectPercent,null);
  }

  public int getOverloadProtectPercent(){
        return DataType.getAsInt(this.get(S_OverloadProtectPercent));
  
  }
  public int getOverloadProtectPercentInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_OverloadProtectPercent));
      }

  public void initSendThreadMin(int value){
     this.initProperty(S_SendThreadMin,new Integer(value));
  }
  public  void setSendThreadMin(int value){
     this.set(S_SendThreadMin,new Integer(value));
  }
  public  void setSendThreadMinNull(){
     this.set(S_SendThreadMin,null);
  }

  public int getSendThreadMin(){
        return DataType.getAsInt(this.get(S_SendThreadMin));
  
  }
  public int getSendThreadMinInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_SendThreadMin));
      }

  public void initSendThreadMax(int value){
     this.initProperty(S_SendThreadMax,new Integer(value));
  }
  public  void setSendThreadMax(int value){
     this.set(S_SendThreadMax,new Integer(value));
  }
  public  void setSendThreadMaxNull(){
     this.set(S_SendThreadMax,null);
  }

  public int getSendThreadMax(){
        return DataType.getAsInt(this.get(S_SendThreadMax));
  
  }
  public int getSendThreadMaxInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_SendThreadMax));
      }

  public void initWorkBufferSize(int value){
     this.initProperty(S_WorkBufferSize,new Integer(value));
  }
  public  void setWorkBufferSize(int value){
     this.set(S_WorkBufferSize,new Integer(value));
  }
  public  void setWorkBufferSizeNull(){
     this.set(S_WorkBufferSize,null);
  }

  public int getWorkBufferSize(){
        return DataType.getAsInt(this.get(S_WorkBufferSize));
  
  }
  public int getWorkBufferSizeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_WorkBufferSize));
      }

  public void initWorkThreadMax(int value){
     this.initProperty(S_WorkThreadMax,new Integer(value));
  }
  public  void setWorkThreadMax(int value){
     this.set(S_WorkThreadMax,new Integer(value));
  }
  public  void setWorkThreadMaxNull(){
     this.set(S_WorkThreadMax,null);
  }

  public int getWorkThreadMax(){
        return DataType.getAsInt(this.get(S_WorkThreadMax));
  
  }
  public int getWorkThreadMaxInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_WorkThreadMax));
      }


 
 }

