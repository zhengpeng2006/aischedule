package com.asiainfo.common.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.common.abo.ivalues.*;

public class BOABGMonBusiErrorLogBean extends DataContainer implements DataContainerInterface,IBOABGMonBusiErrorLogValue{

  private static String  m_boName = "com.asiainfo.common.abo.bo.BOABGMonBusiErrorLog";



  public final static  String S_ErrOrderId = "ERR_ORDER_ID";
  public final static  String S_ServerCode = "SERVER_CODE";
  public final static  String S_SerialNo = "SERIAL_NO";
  public final static  String S_TaskId = "TASK_ID";
  public final static  String S_TaskSplitId = "TASK_SPLIT_ID";
  public final static  String S_RegionCode = "REGION_CODE";
  public final static  String S_ErrCnt = "ERR_CNT";
  public final static  String S_HandleTime = "HANDLE_TIME";
  public final static  String S_SystemDomain = "SYSTEM_DOMAIN";
  public final static  String S_SubsystemDomain = "SUBSYSTEM_DOMAIN";
  public final static  String S_AppServerName = "APP_SERVER_NAME";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_AcqDate = "ACQ_DATE";
  public final static  String S_MonFlag = "MON_FLAG";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOABGMonBusiErrorLogBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initErrOrderId(long value){
     this.initProperty(S_ErrOrderId,new Long(value));
  }
  public  void setErrOrderId(long value){
     this.set(S_ErrOrderId,new Long(value));
  }
  public  void setErrOrderIdNull(){
     this.set(S_ErrOrderId,null);
  }

  public long getErrOrderId(){
        return DataType.getAsLong(this.get(S_ErrOrderId));
  
  }
  public long getErrOrderIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ErrOrderId));
      }

  public void initServerCode(String value){
     this.initProperty(S_ServerCode,value);
  }
  public  void setServerCode(String value){
     this.set(S_ServerCode,value);
  }
  public  void setServerCodeNull(){
     this.set(S_ServerCode,null);
  }

  public String getServerCode(){
       return DataType.getAsString(this.get(S_ServerCode));
  
  }
  public String getServerCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ServerCode));
      }

  public void initSerialNo(long value){
     this.initProperty(S_SerialNo,new Long(value));
  }
  public  void setSerialNo(long value){
     this.set(S_SerialNo,new Long(value));
  }
  public  void setSerialNoNull(){
     this.set(S_SerialNo,null);
  }

  public long getSerialNo(){
        return DataType.getAsLong(this.get(S_SerialNo));
  
  }
  public long getSerialNoInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_SerialNo));
      }

  public void initTaskId(String value){
     this.initProperty(S_TaskId,value);
  }
  public  void setTaskId(String value){
     this.set(S_TaskId,value);
  }
  public  void setTaskIdNull(){
     this.set(S_TaskId,null);
  }

  public String getTaskId(){
       return DataType.getAsString(this.get(S_TaskId));
  
  }
  public String getTaskIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskId));
      }

  public void initTaskSplitId(String value){
     this.initProperty(S_TaskSplitId,value);
  }
  public  void setTaskSplitId(String value){
     this.set(S_TaskSplitId,value);
  }
  public  void setTaskSplitIdNull(){
     this.set(S_TaskSplitId,null);
  }

  public String getTaskSplitId(){
       return DataType.getAsString(this.get(S_TaskSplitId));
  
  }
  public String getTaskSplitIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskSplitId));
      }

  public void initRegionCode(String value){
     this.initProperty(S_RegionCode,value);
  }
  public  void setRegionCode(String value){
     this.set(S_RegionCode,value);
  }
  public  void setRegionCodeNull(){
     this.set(S_RegionCode,null);
  }

  public String getRegionCode(){
       return DataType.getAsString(this.get(S_RegionCode));
  
  }
  public String getRegionCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RegionCode));
      }

  public void initErrCnt(long value){
     this.initProperty(S_ErrCnt,new Long(value));
  }
  public  void setErrCnt(long value){
     this.set(S_ErrCnt,new Long(value));
  }
  public  void setErrCntNull(){
     this.set(S_ErrCnt,null);
  }

  public long getErrCnt(){
        return DataType.getAsLong(this.get(S_ErrCnt));
  
  }
  public long getErrCntInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ErrCnt));
      }

  public void initHandleTime(long value){
     this.initProperty(S_HandleTime,new Long(value));
  }
  public  void setHandleTime(long value){
     this.set(S_HandleTime,new Long(value));
  }
  public  void setHandleTimeNull(){
     this.set(S_HandleTime,null);
  }

  public long getHandleTime(){
        return DataType.getAsLong(this.get(S_HandleTime));
  
  }
  public long getHandleTimeInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_HandleTime));
      }

  public void initSystemDomain(String value){
     this.initProperty(S_SystemDomain,value);
  }
  public  void setSystemDomain(String value){
     this.set(S_SystemDomain,value);
  }
  public  void setSystemDomainNull(){
     this.set(S_SystemDomain,null);
  }

  public String getSystemDomain(){
       return DataType.getAsString(this.get(S_SystemDomain));
  
  }
  public String getSystemDomainInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SystemDomain));
      }

  public void initSubsystemDomain(String value){
     this.initProperty(S_SubsystemDomain,value);
  }
  public  void setSubsystemDomain(String value){
     this.set(S_SubsystemDomain,value);
  }
  public  void setSubsystemDomainNull(){
     this.set(S_SubsystemDomain,null);
  }

  public String getSubsystemDomain(){
       return DataType.getAsString(this.get(S_SubsystemDomain));
  
  }
  public String getSubsystemDomainInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SubsystemDomain));
      }

  public void initAppServerName(String value){
     this.initProperty(S_AppServerName,value);
  }
  public  void setAppServerName(String value){
     this.set(S_AppServerName,value);
  }
  public  void setAppServerNameNull(){
     this.set(S_AppServerName,null);
  }

  public String getAppServerName(){
       return DataType.getAsString(this.get(S_AppServerName));
  
  }
  public String getAppServerNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_AppServerName));
      }

  public void initCreateDate(String value){
     this.initProperty(S_CreateDate,value);
  }
  public  void setCreateDate(String value){
     this.set(S_CreateDate,value);
  }
  public  void setCreateDateNull(){
     this.set(S_CreateDate,null);
  }

  public String getCreateDate(){
       return DataType.getAsString(this.get(S_CreateDate));
  
  }
  public String getCreateDateInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CreateDate));
      }

  public void initAcqDate(Timestamp value){
     this.initProperty(S_AcqDate,value);
  }
  public  void setAcqDate(Timestamp value){
     this.set(S_AcqDate,value);
  }
  public  void setAcqDateNull(){
     this.set(S_AcqDate,null);
  }

  public Timestamp getAcqDate(){
        return DataType.getAsDateTime(this.get(S_AcqDate));
  
  }
  public Timestamp getAcqDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_AcqDate));
      }

  public void initMonFlag(String value){
     this.initProperty(S_MonFlag,value);
  }
  public  void setMonFlag(String value){
     this.set(S_MonFlag,value);
  }
  public  void setMonFlagNull(){
     this.set(S_MonFlag,null);
  }

  public String getMonFlag(){
       return DataType.getAsString(this.get(S_MonFlag));
  
  }
  public String getMonFlagInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MonFlag));
      }


 
 }

