package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgTfValue;

public class BOCfgTfBean extends DataContainer implements DataContainerInterface,IBOCfgTfValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgTf";



  public final static  String S_DataCount = "DATA_COUNT";
  public final static  String S_State = "STATE";
  public final static  String S_ScanRownum = "SCAN_ROWNUM";
  public final static  String S_WhenIdleSleepTime = "WHEN_IDLE_SLEEP_TIME";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_FinishSql = "FINISH_SQL";
  public final static  String S_Template = "TEMPLATE";
  public final static  String S_TransformClassname = "TRANSFORM_CLASSNAME";
  public final static  String S_ProcessingSql = "PROCESSING_SQL";
  public final static  String S_StartTime = "START_TIME";
  public final static  String S_ExecuteMethod = "EXECUTE_METHOD";
  public final static  String S_MethodId = "METHOD_ID";
  public final static  String S_ErrorSql = "ERROR_SQL";
  public final static  String S_SrcTableName = "SRC_TABLE_NAME";
  public final static  String S_PkColumns = "PK_COLUMNS";
  public final static  String S_QuerySql = "QUERY_SQL";
  public final static  String S_ScanIntervalTime = "SCAN_INTERVAL_TIME";
  public final static  String S_CfgTfCode = "CFG_TF_CODE";
  public final static  String S_SrcDbAcctCode = "SRC_DB_ACCT_CODE";
  public final static  String S_Duration = "DURATION";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgTfBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initDataCount(long value){
     this.initProperty(S_DataCount,new Long(value));
  }
  public  void setDataCount(long value){
     this.set(S_DataCount,new Long(value));
  }
  public  void setDataCountNull(){
     this.set(S_DataCount,null);
  }

  public long getDataCount(){
        return DataType.getAsLong(this.get(S_DataCount));
  
  }
  public long getDataCountInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_DataCount));
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

  public void initScanRownum(long value){
     this.initProperty(S_ScanRownum,new Long(value));
  }
  public  void setScanRownum(long value){
     this.set(S_ScanRownum,new Long(value));
  }
  public  void setScanRownumNull(){
     this.set(S_ScanRownum,null);
  }

  public long getScanRownum(){
        return DataType.getAsLong(this.get(S_ScanRownum));
  
  }
  public long getScanRownumInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ScanRownum));
      }

  public void initWhenIdleSleepTime(int value){
     this.initProperty(S_WhenIdleSleepTime,new Integer(value));
  }
  public  void setWhenIdleSleepTime(int value){
     this.set(S_WhenIdleSleepTime,new Integer(value));
  }
  public  void setWhenIdleSleepTimeNull(){
     this.set(S_WhenIdleSleepTime,null);
  }

  public int getWhenIdleSleepTime(){
        return DataType.getAsInt(this.get(S_WhenIdleSleepTime));
  
  }
  public int getWhenIdleSleepTimeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_WhenIdleSleepTime));
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

  public void initFinishSql(String value){
     this.initProperty(S_FinishSql,value);
  }
  public  void setFinishSql(String value){
     this.set(S_FinishSql,value);
  }
  public  void setFinishSqlNull(){
     this.set(S_FinishSql,null);
  }

  public String getFinishSql(){
       return DataType.getAsString(this.get(S_FinishSql));
  
  }
  public String getFinishSqlInitialValue(){
        return DataType.getAsString(this.getOldObj(S_FinishSql));
      }

  public void initTemplate(String value){
     this.initProperty(S_Template,value);
  }
  public  void setTemplate(String value){
     this.set(S_Template,value);
  }
  public  void setTemplateNull(){
     this.set(S_Template,null);
  }

  public String getTemplate(){
       return DataType.getAsString(this.get(S_Template));
  
  }
  public String getTemplateInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Template));
      }

  public void initTransformClassname(String value){
     this.initProperty(S_TransformClassname,value);
  }
  public  void setTransformClassname(String value){
     this.set(S_TransformClassname,value);
  }
  public  void setTransformClassnameNull(){
     this.set(S_TransformClassname,null);
  }

  public String getTransformClassname(){
       return DataType.getAsString(this.get(S_TransformClassname));
  
  }
  public String getTransformClassnameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TransformClassname));
      }

  public void initProcessingSql(String value){
     this.initProperty(S_ProcessingSql,value);
  }
  public  void setProcessingSql(String value){
     this.set(S_ProcessingSql,value);
  }
  public  void setProcessingSqlNull(){
     this.set(S_ProcessingSql,null);
  }

  public String getProcessingSql(){
       return DataType.getAsString(this.get(S_ProcessingSql));
  
  }
  public String getProcessingSqlInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ProcessingSql));
      }

  public void initStartTime(String value){
     this.initProperty(S_StartTime,value);
  }
  public  void setStartTime(String value){
     this.set(S_StartTime,value);
  }
  public  void setStartTimeNull(){
     this.set(S_StartTime,null);
  }

  public String getStartTime(){
       return DataType.getAsString(this.get(S_StartTime));
  
  }
  public String getStartTimeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_StartTime));
      }

  public void initExecuteMethod(String value){
     this.initProperty(S_ExecuteMethod,value);
  }
  public  void setExecuteMethod(String value){
     this.set(S_ExecuteMethod,value);
  }
  public  void setExecuteMethodNull(){
     this.set(S_ExecuteMethod,null);
  }

  public String getExecuteMethod(){
       return DataType.getAsString(this.get(S_ExecuteMethod));
  
  }
  public String getExecuteMethodInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ExecuteMethod));
      }

  public void initMethodId(long value){
     this.initProperty(S_MethodId,new Long(value));
  }
  public  void setMethodId(long value){
     this.set(S_MethodId,new Long(value));
  }
  public  void setMethodIdNull(){
     this.set(S_MethodId,null);
  }

  public long getMethodId(){
        return DataType.getAsLong(this.get(S_MethodId));
  
  }
  public long getMethodIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_MethodId));
      }

  public void initErrorSql(String value){
     this.initProperty(S_ErrorSql,value);
  }
  public  void setErrorSql(String value){
     this.set(S_ErrorSql,value);
  }
  public  void setErrorSqlNull(){
     this.set(S_ErrorSql,null);
  }

  public String getErrorSql(){
       return DataType.getAsString(this.get(S_ErrorSql));
  
  }
  public String getErrorSqlInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ErrorSql));
      }

  public void initSrcTableName(String value){
     this.initProperty(S_SrcTableName,value);
  }
  public  void setSrcTableName(String value){
     this.set(S_SrcTableName,value);
  }
  public  void setSrcTableNameNull(){
     this.set(S_SrcTableName,null);
  }

  public String getSrcTableName(){
       return DataType.getAsString(this.get(S_SrcTableName));
  
  }
  public String getSrcTableNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SrcTableName));
      }

  public void initPkColumns(String value){
     this.initProperty(S_PkColumns,value);
  }
  public  void setPkColumns(String value){
     this.set(S_PkColumns,value);
  }
  public  void setPkColumnsNull(){
     this.set(S_PkColumns,null);
  }

  public String getPkColumns(){
       return DataType.getAsString(this.get(S_PkColumns));
  
  }
  public String getPkColumnsInitialValue(){
        return DataType.getAsString(this.getOldObj(S_PkColumns));
      }

  public void initQuerySql(String value){
     this.initProperty(S_QuerySql,value);
  }
  public  void setQuerySql(String value){
     this.set(S_QuerySql,value);
  }
  public  void setQuerySqlNull(){
     this.set(S_QuerySql,null);
  }

  public String getQuerySql(){
       return DataType.getAsString(this.get(S_QuerySql));
  
  }
  public String getQuerySqlInitialValue(){
        return DataType.getAsString(this.getOldObj(S_QuerySql));
      }

  public void initScanIntervalTime(long value){
     this.initProperty(S_ScanIntervalTime,new Long(value));
  }
  public  void setScanIntervalTime(long value){
     this.set(S_ScanIntervalTime,new Long(value));
  }
  public  void setScanIntervalTimeNull(){
     this.set(S_ScanIntervalTime,null);
  }

  public long getScanIntervalTime(){
        return DataType.getAsLong(this.get(S_ScanIntervalTime));
  
  }
  public long getScanIntervalTimeInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ScanIntervalTime));
      }

  public void initCfgTfCode(String value){
     this.initProperty(S_CfgTfCode,value);
  }
  public  void setCfgTfCode(String value){
     this.set(S_CfgTfCode,value);
  }
  public  void setCfgTfCodeNull(){
     this.set(S_CfgTfCode,null);
  }

  public String getCfgTfCode(){
       return DataType.getAsString(this.get(S_CfgTfCode));
  
  }
  public String getCfgTfCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CfgTfCode));
      }

  public void initSrcDbAcctCode(String value){
     this.initProperty(S_SrcDbAcctCode,value);
  }
  public  void setSrcDbAcctCode(String value){
     this.set(S_SrcDbAcctCode,value);
  }
  public  void setSrcDbAcctCodeNull(){
     this.set(S_SrcDbAcctCode,null);
  }

  public String getSrcDbAcctCode(){
       return DataType.getAsString(this.get(S_SrcDbAcctCode));
  
  }
  public String getSrcDbAcctCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SrcDbAcctCode));
      }

  public void initDuration(int value){
     this.initProperty(S_Duration,new Integer(value));
  }
  public  void setDuration(int value){
     this.set(S_Duration,new Integer(value));
  }
  public  void setDurationNull(){
     this.set(S_Duration,null);
  }

  public int getDuration(){
        return DataType.getAsInt(this.get(S_Duration));
  
  }
  public int getDurationInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_Duration));
      }


 
 }

