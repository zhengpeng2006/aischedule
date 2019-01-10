package com.asiainfo.schedule.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.schedule.ivalues.*;

public class BOAISchedTaskLogDTLBean extends DataContainer implements DataContainerInterface,IBOAISchedTaskLogDTLValue{

  private static String  m_boName = "com.asiainfo.schedule.bo.BOAISchedTaskLogDTL";



  public final static  String S_LogDate = "LOG_DATE";
  public final static  String S_OpInfo = "OP_INFO";
  public final static  String S_JobId = "JOB_ID";
  public final static  String S_SubsystemDomain = "SUBSYSTEM_DOMAIN";
  public final static  String S_SystemDomain = "SYSTEM_DOMAIN";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_TaskItem = "TASK_ITEM";
  public final static  String S_Operator = "OPERATOR";
  public final static  String S_AppServerName = "APP_SERVER_NAME";
  public final static  String S_TaskVersion = "TASK_VERSION";
  public final static  String S_ExMsg = "EX_MSG";
  public final static  String S_LogType = "LOG_TYPE";
  public final static  String S_TaskCode = "TASK_CODE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAISchedTaskLogDTLBean() throws AIException{
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

  public void initOpInfo(String value){
     this.initProperty(S_OpInfo,value);
  }
  public  void setOpInfo(String value){
     this.set(S_OpInfo,value);
  }
  public  void setOpInfoNull(){
     this.set(S_OpInfo,null);
  }

  public String getOpInfo(){
       return DataType.getAsString(this.get(S_OpInfo));
  
  }
  public String getOpInfoInitialValue(){
        return DataType.getAsString(this.getOldObj(S_OpInfo));
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

  public void initTaskItem(String value){
     this.initProperty(S_TaskItem,value);
  }
  public  void setTaskItem(String value){
     this.set(S_TaskItem,value);
  }
  public  void setTaskItemNull(){
     this.set(S_TaskItem,null);
  }

  public String getTaskItem(){
       return DataType.getAsString(this.get(S_TaskItem));
  
  }
  public String getTaskItemInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskItem));
      }

  public void initOperator(String value){
     this.initProperty(S_Operator,value);
  }
  public  void setOperator(String value){
     this.set(S_Operator,value);
  }
  public  void setOperatorNull(){
     this.set(S_Operator,null);
  }

  public String getOperator(){
       return DataType.getAsString(this.get(S_Operator));
  
  }
  public String getOperatorInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Operator));
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

  public void initExMsg(String value){
     this.initProperty(S_ExMsg,value);
  }
  public  void setExMsg(String value){
     this.set(S_ExMsg,value);
  }
  public  void setExMsgNull(){
     this.set(S_ExMsg,null);
  }

  public String getExMsg(){
       return DataType.getAsString(this.get(S_ExMsg));
  
  }
  public String getExMsgInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ExMsg));
      }

  public void initLogType(String value){
     this.initProperty(S_LogType,value);
  }
  public  void setLogType(String value){
     this.set(S_LogType,value);
  }
  public  void setLogTypeNull(){
     this.set(S_LogType,null);
  }

  public String getLogType(){
       return DataType.getAsString(this.get(S_LogType));
  
  }
  public String getLogTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_LogType));
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

