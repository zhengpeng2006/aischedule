package com.asiainfo.monitor.busi.exe.task.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTableValue;

public class BOAIMonPTableBean extends DataContainer implements DataContainerInterface,IBOAIMonPTableValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPTable";



  public final static  String S_Sql = "SQL";
  public final static  String S_State = "STATE";
  public final static  String S_DbAcctCode = "DB_ACCT_CODE";
  public final static  String S_DbUrlName = "DB_URL_NAME";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Name = "NAME";
  public final static  String S_TableId = "TABLE_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonPTableBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initSql(String value){
     this.initProperty(S_Sql,value);
  }
  public  void setSql(String value){
     this.set(S_Sql,value);
  }
  public  void setSqlNull(){
     this.set(S_Sql,null);
  }

  public String getSql(){
       return DataType.getAsString(this.get(S_Sql));
  
  }
  public String getSqlInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Sql));
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

  public void initDbUrlName(String value){
     this.initProperty(S_DbUrlName,value);
  }
  public  void setDbUrlName(String value){
     this.set(S_DbUrlName,value);
  }
  public  void setDbUrlNameNull(){
     this.set(S_DbUrlName,null);
  }

  public String getDbUrlName(){
       return DataType.getAsString(this.get(S_DbUrlName));
  
  }
  public String getDbUrlNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DbUrlName));
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

  public void initName(String value){
     this.initProperty(S_Name,value);
  }
  public  void setName(String value){
     this.set(S_Name,value);
  }
  public  void setNameNull(){
     this.set(S_Name,null);
  }

  public String getName(){
       return DataType.getAsString(this.get(S_Name));
  
  }
  public String getNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Name));
      }

  public void initTableId(long value){
     this.initProperty(S_TableId,new Long(value));
  }
  public  void setTableId(long value){
     this.set(S_TableId,new Long(value));
  }
  public  void setTableIdNull(){
     this.set(S_TableId,null);
  }

  public long getTableId(){
        return DataType.getAsLong(this.get(S_TableId));
  
  }
  public long getTableIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TableId));
      }


 
 }

