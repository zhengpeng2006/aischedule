package com.asiainfo.monitor.exeframe.config.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgTaskValue;

public class BOCfgTaskBean extends DataContainer implements DataContainerInterface,IBOCfgTaskValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgTask";



  public final static  String S_State = "STATE";
  public final static  String S_BusinessClass = "BUSINESS_CLASS";
  public final static  String S_StaffId = "STAFF_ID";
  public final static  String S_TaskExpr = "TASK_EXPR";
  public final static  String S_SortId = "SORT_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_TaskName = "TASK_NAME";
  public final static  String S_CfgTaskId = "CFG_TASK_ID";
  public final static  String S_StateDate = "STATE_DATE";
  public final static  String S_CfgTaskTypeCode = "CFG_TASK_TYPE_CODE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_TaskMethod = "TASK_METHOD";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgTaskBean() throws AIException{
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

  public void initBusinessClass(String value){
     this.initProperty(S_BusinessClass,value);
  }
  public  void setBusinessClass(String value){
     this.set(S_BusinessClass,value);
  }
  public  void setBusinessClassNull(){
     this.set(S_BusinessClass,null);
  }

  public String getBusinessClass(){
       return DataType.getAsString(this.get(S_BusinessClass));
  
  }
  public String getBusinessClassInitialValue(){
        return DataType.getAsString(this.getOldObj(S_BusinessClass));
      }

  public void initStaffId(long value){
     this.initProperty(S_StaffId,new Long(value));
  }
  public  void setStaffId(long value){
     this.set(S_StaffId,new Long(value));
  }
  public  void setStaffIdNull(){
     this.set(S_StaffId,null);
  }

  public long getStaffId(){
        return DataType.getAsLong(this.get(S_StaffId));
  
  }
  public long getStaffIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_StaffId));
      }

  public void initTaskExpr(String value){
     this.initProperty(S_TaskExpr,value);
  }
  public  void setTaskExpr(String value){
     this.set(S_TaskExpr,value);
  }
  public  void setTaskExprNull(){
     this.set(S_TaskExpr,null);
  }

  public String getTaskExpr(){
       return DataType.getAsString(this.get(S_TaskExpr));
  
  }
  public String getTaskExprInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskExpr));
      }

  public void initSortId(long value){
     this.initProperty(S_SortId,new Long(value));
  }
  public  void setSortId(long value){
     this.set(S_SortId,new Long(value));
  }
  public  void setSortIdNull(){
     this.set(S_SortId,null);
  }

  public long getSortId(){
        return DataType.getAsLong(this.get(S_SortId));
  
  }
  public long getSortIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_SortId));
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

  public void initStateDate(Timestamp value){
     this.initProperty(S_StateDate,value);
  }
  public  void setStateDate(Timestamp value){
     this.set(S_StateDate,value);
  }
  public  void setStateDateNull(){
     this.set(S_StateDate,null);
  }

  public Timestamp getStateDate(){
        return DataType.getAsDateTime(this.get(S_StateDate));
  
  }
  public Timestamp getStateDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_StateDate));
      }

  public void initCfgTaskTypeCode(String value){
     this.initProperty(S_CfgTaskTypeCode,value);
  }
  public  void setCfgTaskTypeCode(String value){
     this.set(S_CfgTaskTypeCode,value);
  }
  public  void setCfgTaskTypeCodeNull(){
     this.set(S_CfgTaskTypeCode,null);
  }

  public String getCfgTaskTypeCode(){
       return DataType.getAsString(this.get(S_CfgTaskTypeCode));
  
  }
  public String getCfgTaskTypeCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CfgTaskTypeCode));
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

  public void initTaskMethod(String value){
     this.initProperty(S_TaskMethod,value);
  }
  public  void setTaskMethod(String value){
     this.set(S_TaskMethod,value);
  }
  public  void setTaskMethodNull(){
     this.set(S_TaskMethod,null);
  }

  public String getTaskMethod(){
       return DataType.getAsString(this.get(S_TaskMethod));
  
  }
  public String getTaskMethodInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskMethod));
      }


 
 }

