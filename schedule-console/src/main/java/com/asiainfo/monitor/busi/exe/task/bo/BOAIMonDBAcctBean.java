package com.asiainfo.monitor.busi.exe.task.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBAcctValue;

public class BOAIMonDBAcctBean extends DataContainer implements DataContainerInterface,IBOAIMonDBAcctValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonDBAcct";



  public final static  String S_State = "STATE";
  public final static  String S_DbAcctCode = "DB_ACCT_CODE";
  public final static  String S_Sid = "SID";
  public final static  String S_Username = "USERNAME";
  public final static  String S_Password = "PASSWORD";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ConnMax = "CONN_MAX";
  public final static  String S_ConnMin = "CONN_MIN";
  public final static  String S_Host = "HOST";
  public final static  String S_Port = "PORT";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonDBAcctBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
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

  public void initSid(String value){
     this.initProperty(S_Sid,value);
  }
  public  void setSid(String value){
     this.set(S_Sid,value);
  }
  public  void setSidNull(){
     this.set(S_Sid,null);
  }

  public String getSid(){
       return DataType.getAsString(this.get(S_Sid));
  
  }
  public String getSidInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Sid));
      }

  public void initUsername(String value){
     this.initProperty(S_Username,value);
  }
  public  void setUsername(String value){
     this.set(S_Username,value);
  }
  public  void setUsernameNull(){
     this.set(S_Username,null);
  }

  public String getUsername(){
       return DataType.getAsString(this.get(S_Username));
  
  }
  public String getUsernameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Username));
      }

  public void initPassword(String value){
     this.initProperty(S_Password,value);
  }
  public  void setPassword(String value){
     this.set(S_Password,value);
  }
  public  void setPasswordNull(){
     this.set(S_Password,null);
  }

  public String getPassword(){
       return DataType.getAsString(this.get(S_Password));
  
  }
  public String getPasswordInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Password));
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

  public void initConnMax(int value){
     this.initProperty(S_ConnMax,new Integer(value));
  }
  public  void setConnMax(int value){
     this.set(S_ConnMax,new Integer(value));
  }
  public  void setConnMaxNull(){
     this.set(S_ConnMax,null);
  }

  public int getConnMax(){
        return DataType.getAsInt(this.get(S_ConnMax));
  
  }
  public int getConnMaxInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_ConnMax));
      }

  public void initConnMin(int value){
     this.initProperty(S_ConnMin,new Integer(value));
  }
  public  void setConnMin(int value){
     this.set(S_ConnMin,new Integer(value));
  }
  public  void setConnMinNull(){
     this.set(S_ConnMin,null);
  }

  public int getConnMin(){
        return DataType.getAsInt(this.get(S_ConnMin));
  
  }
  public int getConnMinInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_ConnMin));
      }

  public void initHost(String value){
     this.initProperty(S_Host,value);
  }
  public  void setHost(String value){
     this.set(S_Host,value);
  }
  public  void setHostNull(){
     this.set(S_Host,null);
  }

  public String getHost(){
       return DataType.getAsString(this.get(S_Host));
  
  }
  public String getHostInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Host));
      }

  public void initPort(int value){
     this.initProperty(S_Port,new Integer(value));
  }
  public  void setPort(int value){
     this.set(S_Port,new Integer(value));
  }
  public  void setPortNull(){
     this.set(S_Port,null);
  }

  public int getPort(){
        return DataType.getAsInt(this.get(S_Port));
  
  }
  public int getPortInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_Port));
      }


 
 }

