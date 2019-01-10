package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgTfThreadValue;

public class BOCfgTfThreadBean extends DataContainer implements DataContainerInterface,IBOCfgTfThreadValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgTfThread";



  public final static  String S_DataCount = "DATA_COUNT";
  public final static  String S_State = "STATE";
  public final static  String S_InitThreads = "INIT_THREADS";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CfgTfThreadId = "CFG_TF_THREAD_ID";
  public final static  String S_MaxThreads = "MAX_THREADS";
  public final static  String S_BufferSize = "BUFFER_SIZE";
  public final static  String S_OverloadProtectPercent = "OVERLOAD_PROTECT_PERCENT";
  public final static  String S_MinThreads = "MIN_THREADS";
  public final static  String S_KeepAliveTime = "KEEP_ALIVE_TIME";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgTfThreadBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initDataCount(int value){
     this.initProperty(S_DataCount,new Integer(value));
  }
  public  void setDataCount(int value){
     this.set(S_DataCount,new Integer(value));
  }
  public  void setDataCountNull(){
     this.set(S_DataCount,null);
  }

  public int getDataCount(){
        return DataType.getAsInt(this.get(S_DataCount));
  
  }
  public int getDataCountInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_DataCount));
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

  public void initInitThreads(int value){
     this.initProperty(S_InitThreads,new Integer(value));
  }
  public  void setInitThreads(int value){
     this.set(S_InitThreads,new Integer(value));
  }
  public  void setInitThreadsNull(){
     this.set(S_InitThreads,null);
  }

  public int getInitThreads(){
        return DataType.getAsInt(this.get(S_InitThreads));
  
  }
  public int getInitThreadsInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_InitThreads));
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

  public void initCfgTfThreadId(long value){
     this.initProperty(S_CfgTfThreadId,new Long(value));
  }
  public  void setCfgTfThreadId(long value){
     this.set(S_CfgTfThreadId,new Long(value));
  }
  public  void setCfgTfThreadIdNull(){
     this.set(S_CfgTfThreadId,null);
  }

  public long getCfgTfThreadId(){
        return DataType.getAsLong(this.get(S_CfgTfThreadId));
  
  }
  public long getCfgTfThreadIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_CfgTfThreadId));
      }

  public void initMaxThreads(int value){
     this.initProperty(S_MaxThreads,new Integer(value));
  }
  public  void setMaxThreads(int value){
     this.set(S_MaxThreads,new Integer(value));
  }
  public  void setMaxThreadsNull(){
     this.set(S_MaxThreads,null);
  }

  public int getMaxThreads(){
        return DataType.getAsInt(this.get(S_MaxThreads));
  
  }
  public int getMaxThreadsInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_MaxThreads));
      }

  public void initBufferSize(int value){
     this.initProperty(S_BufferSize,new Integer(value));
  }
  public  void setBufferSize(int value){
     this.set(S_BufferSize,new Integer(value));
  }
  public  void setBufferSizeNull(){
     this.set(S_BufferSize,null);
  }

  public int getBufferSize(){
        return DataType.getAsInt(this.get(S_BufferSize));
  
  }
  public int getBufferSizeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_BufferSize));
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

  public void initMinThreads(int value){
     this.initProperty(S_MinThreads,new Integer(value));
  }
  public  void setMinThreads(int value){
     this.set(S_MinThreads,new Integer(value));
  }
  public  void setMinThreadsNull(){
     this.set(S_MinThreads,null);
  }

  public int getMinThreads(){
        return DataType.getAsInt(this.get(S_MinThreads));
  
  }
  public int getMinThreadsInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_MinThreads));
      }

  public void initKeepAliveTime(int value){
     this.initProperty(S_KeepAliveTime,new Integer(value));
  }
  public  void setKeepAliveTime(int value){
     this.set(S_KeepAliveTime,new Integer(value));
  }
  public  void setKeepAliveTimeNull(){
     this.set(S_KeepAliveTime,null);
  }

  public int getKeepAliveTime(){
        return DataType.getAsInt(this.get(S_KeepAliveTime));
  
  }
  public int getKeepAliveTimeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_KeepAliveTime));
      }


 
 }

