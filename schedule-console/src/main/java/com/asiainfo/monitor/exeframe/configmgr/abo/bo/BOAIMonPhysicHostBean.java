package com.asiainfo.monitor.exeframe.configmgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.*;

public class BOAIMonPhysicHostBean extends DataContainer implements DataContainerInterface,IBOAIMonPhysicHostValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHost";



  public final static  String S_HostId = "HOST_ID";
  public final static  String S_HostCode = "HOST_CODE";
  public final static  String S_HostName = "HOST_NAME";
  public final static  String S_HostIp = "HOST_IP";
  public final static  String S_HostDesc = "HOST_DESC";
  public final static  String S_State = "STATE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_Remarks = "REMARKS";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonPhysicHostBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initHostId(long value){
     this.initProperty(S_HostId,new Long(value));
  }
  public  void setHostId(long value){
     this.set(S_HostId,new Long(value));
  }
  public  void setHostIdNull(){
     this.set(S_HostId,null);
  }

  public long getHostId(){
        return DataType.getAsLong(this.get(S_HostId));
  
  }
  public long getHostIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_HostId));
      }

  public void initHostCode(String value){
     this.initProperty(S_HostCode,value);
  }
  public  void setHostCode(String value){
     this.set(S_HostCode,value);
  }
  public  void setHostCodeNull(){
     this.set(S_HostCode,null);
  }

  public String getHostCode(){
       return DataType.getAsString(this.get(S_HostCode));
  
  }
  public String getHostCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_HostCode));
      }

  public void initHostName(String value){
     this.initProperty(S_HostName,value);
  }
  public  void setHostName(String value){
     this.set(S_HostName,value);
  }
  public  void setHostNameNull(){
     this.set(S_HostName,null);
  }

  public String getHostName(){
       return DataType.getAsString(this.get(S_HostName));
  
  }
  public String getHostNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_HostName));
      }

  public void initHostIp(String value){
     this.initProperty(S_HostIp,value);
  }
  public  void setHostIp(String value){
     this.set(S_HostIp,value);
  }
  public  void setHostIpNull(){
     this.set(S_HostIp,null);
  }

  public String getHostIp(){
       return DataType.getAsString(this.get(S_HostIp));
  
  }
  public String getHostIpInitialValue(){
        return DataType.getAsString(this.getOldObj(S_HostIp));
      }

  public void initHostDesc(String value){
     this.initProperty(S_HostDesc,value);
  }
  public  void setHostDesc(String value){
     this.set(S_HostDesc,value);
  }
  public  void setHostDescNull(){
     this.set(S_HostDesc,null);
  }

  public String getHostDesc(){
       return DataType.getAsString(this.get(S_HostDesc));
  
  }
  public String getHostDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_HostDesc));
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


 
 }

