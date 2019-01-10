package com.asiainfo.monitor.exeframe.configmgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.*;

public class BOAIMonHostUserBean extends DataContainer implements DataContainerInterface,IBOAIMonHostUserValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostUser";



  public final static  String S_HostUserId = "HOST_USER_ID";
  public final static  String S_HostId = "HOST_ID";
  public final static  String S_UserName = "USER_NAME";
  public final static  String S_UserPwd = "USER_PWD";
  public final static  String S_UserType = "USER_TYPE";
  public final static  String S_State = "STATE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_Remark = "REMARK";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonHostUserBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initHostUserId(long value){
     this.initProperty(S_HostUserId,new Long(value));
  }
  public  void setHostUserId(long value){
     this.set(S_HostUserId,new Long(value));
  }
  public  void setHostUserIdNull(){
     this.set(S_HostUserId,null);
  }

  public long getHostUserId(){
        return DataType.getAsLong(this.get(S_HostUserId));
  
  }
  public long getHostUserIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_HostUserId));
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

  public void initUserName(String value){
     this.initProperty(S_UserName,value);
  }
  public  void setUserName(String value){
     this.set(S_UserName,value);
  }
  public  void setUserNameNull(){
     this.set(S_UserName,null);
  }

  public String getUserName(){
       return DataType.getAsString(this.get(S_UserName));
  
  }
  public String getUserNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_UserName));
      }

  public void initUserPwd(String value){
     this.initProperty(S_UserPwd,value);
  }
  public  void setUserPwd(String value){
     this.set(S_UserPwd,value);
  }
  public  void setUserPwdNull(){
     this.set(S_UserPwd,null);
  }

  public String getUserPwd(){
       return DataType.getAsString(this.get(S_UserPwd));
  
  }
  public String getUserPwdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_UserPwd));
      }

  public void initUserType(String value){
     this.initProperty(S_UserType,value);
  }
  public  void setUserType(String value){
     this.set(S_UserType,value);
  }
  public  void setUserTypeNull(){
     this.set(S_UserType,null);
  }

  public String getUserType(){
       return DataType.getAsString(this.get(S_UserType));
  
  }
  public String getUserTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_UserType));
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

  public void initRemark(String value){
     this.initProperty(S_Remark,value);
  }
  public  void setRemark(String value){
     this.set(S_Remark,value);
  }
  public  void setRemarkNull(){
     this.set(S_Remark,null);
  }

  public String getRemark(){
       return DataType.getAsString(this.get(S_Remark));
  
  }
  public String getRemarkInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Remark));
      }


 
 }

