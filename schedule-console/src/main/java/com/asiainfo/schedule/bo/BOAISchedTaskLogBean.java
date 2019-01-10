package com.asiainfo.schedule.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.schedule.ivalues.*;

public class BOAISchedTaskLogBean extends DataContainer implements DataContainerInterface,IBOAISchedTaskLogValue{

  private static String  m_boName = "com.asiainfo.schedule.bo.BOAISchedTaskLog";



  public final static  String S_LogDate = "LOG_DATE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_JobId = "JOB_ID";
  public final static  String S_StartTime = "START_TIME";
  public final static  String S_FinishTime = "FINISH_TIME";
  public final static  String S_TaskName = "TASK_NAME";
  public final static  String S_AppServerName = "APP_SERVER_NAME";
  public final static  String S_TaskVersion = "TASK_VERSION";
  public final static  String S_SubsystemDomain = "SUBSYSTEM_DOMAIN";
  public final static  String S_State = "STATE";
  public final static  String S_SystemDomain = "SYSTEM_DOMAIN";
  public final static  String S_TaskCode = "TASK_CODE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAISchedTaskLogBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initLogDate(Timestamp value){
     this.initProperty(S_LogDate,value);
  }
  public  void setLogDate(Timestamp value){
     this.set(S_LogDate,value);
  }
  public  void setLogDateNull(){
     this.set(S_LogDate,null);
  }

  public Timestamp getLogDate(){
        return DataType.getAsDateTime(this.get(S_LogDate));
  
  }
  public Timestamp getLogDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_LogDate));
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

  public void initJobId(String value){
     this.initProperty(S_JobId,value);
  }
  public  void setJobId(String value){
     this.set(S_JobId,value);
  }
  public  void setJobIdNull(){
     this.set(S_JobId,null);
  }

  public String getJobId(){
       return DataType.getAsString(this.get(S_JobId));
  
  }
  public String getJobIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_JobId));
      }

  public void initStartTime(Timestamp value){
     this.initProperty(S_StartTime,value);
  }
  public  void setStartTime(Timestamp value){
     this.set(S_StartTime,value);
  }
  public  void setStartTimeNull(){
     this.set(S_StartTime,null);
  }

  public Timestamp getStartTime(){
        return DataType.getAsDateTime(this.get(S_StartTime));
  
  }
  public Timestamp getStartTimeInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_StartTime));
      }

  public void initFinishTime(Timestamp value){
     this.initProperty(S_FinishTime,value);
  }
  public  void setFinishTime(Timestamp value){
     this.set(S_FinishTime,value);
  }
  public  void setFinishTimeNull(){
     this.set(S_FinishTime,null);
  }

  public Timestamp getFinishTime(){
        return DataType.getAsDateTime(this.get(S_FinishTime));
  
  }
  public Timestamp getFinishTimeInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_FinishTime));
      }

  public void initTaskName(String value){
     this.initProperty(S_TaskName,value);
  }
  public  void setTaskName(String value){
     this.set(S_TaskName,value);
  }
  public  void setTaskNameNull(){
     this.set(S_TaskName,null);
  }

  public String getTaskName(){
       return DataType.getAsString(this.get(S_TaskName));
  
  }
  public String getTaskNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskName));
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

  public void initTaskVersion(String value){
     this.initProperty(S_TaskVersion,value);
  }
  public  void setTaskVersion(String value){
     this.set(S_TaskVersion,value);
  }
  public  void setTaskVersionNull(){
     this.set(S_TaskVersion,null);
  }

  public String getTaskVersion(){
       return DataType.getAsString(this.get(S_TaskVersion));
  
  }
  public String getTaskVersionInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskVersion));
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

  public void initTaskCode(String value){
     this.initProperty(S_TaskCode,value);
  }
  public  void setTaskCode(String value){
     this.set(S_TaskCode,value);
  }
  public  void setTaskCodeNull(){
     this.set(S_TaskCode,null);
  }

  public String getTaskCode(){
       return DataType.getAsString(this.get(S_TaskCode));
  
  }
  public String getTaskCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskCode));
      }


 
 }

