package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgTfDtlValue;

public class BOCfgTfDtlBean extends DataContainer implements DataContainerInterface,IBOCfgTfDtlValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgTfDtl";



  public final static  String S_TableName = "TABLE_NAME";
  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_FinishSql = "FINISH_SQL";
  public final static  String S_TfType = "TF_TYPE";
  public final static  String S_CfgTfDtlId = "CFG_TF_DTL_ID";
  public final static  String S_CfgTfCode = "CFG_TF_CODE";
  public final static  String S_DbAcctCode = "DB_ACCT_CODE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgTfDtlBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initTableName(String value){
     this.initProperty(S_TableName,value);
  }
  public  void setTableName(String value){
     this.set(S_TableName,value);
  }
  public  void setTableNameNull(){
     this.set(S_TableName,null);
  }

  public String getTableName(){
       return DataType.getAsString(this.get(S_TableName));
  
  }
  public String getTableNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TableName));
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

  public void initTfType(String value){
     this.initProperty(S_TfType,value);
  }
  public  void setTfType(String value){
     this.set(S_TfType,value);
  }
  public  void setTfTypeNull(){
     this.set(S_TfType,null);
  }

  public String getTfType(){
       return DataType.getAsString(this.get(S_TfType));
  
  }
  public String getTfTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TfType));
      }

  public void initCfgTfDtlId(long value){
     this.initProperty(S_CfgTfDtlId,new Long(value));
  }
  public  void setCfgTfDtlId(long value){
     this.set(S_CfgTfDtlId,new Long(value));
  }
  public  void setCfgTfDtlIdNull(){
     this.set(S_CfgTfDtlId,null);
  }

  public long getCfgTfDtlId(){
        return DataType.getAsLong(this.get(S_CfgTfDtlId));
  
  }
  public long getCfgTfDtlIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_CfgTfDtlId));
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

  public void initDbAcctCode(String value){
     this.initProperty(S_DbAcctCode,value);
  }
  public  void setDbAcctCode(String value){
     this.set(S_DbAcctCode,value);
  }
  public  void setDbAcctCodeNull(){
     this.set(S_DbAcctCode,null);
  }

  public String getDbAcctCode(){
       return DataType.getAsString(this.get(S_DbAcctCode));
  
  }
  public String getDbAcctCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DbAcctCode));
      }


 
 }

