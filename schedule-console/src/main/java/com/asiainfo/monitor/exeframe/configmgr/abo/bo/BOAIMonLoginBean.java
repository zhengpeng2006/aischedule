package com.asiainfo.monitor.exeframe.configmgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.*;

public class BOAIMonLoginBean extends DataContainer implements DataContainerInterface,IBOAIMonLoginValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonLogin";



  public final static  String S_Ip = "IP";
  public final static  String S_LoginName = "LOGIN_NAME";
  public final static  String S_LastFailTime = "LAST_FAIL_TIME";
  public final static  String S_FailCount = "FAIL_COUNT";
  public final static  String S_Ext01 = "EXT_01";
  public final static  String S_Ext02 = "EXT_02";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonLoginBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initIp(String value){
     this.initProperty(S_Ip,value);
  }
  public  void setIp(String value){
     this.set(S_Ip,value);
  }
  public  void setIpNull(){
     this.set(S_Ip,null);
  }

  public String getIp(){
       return DataType.getAsString(this.get(S_Ip));
  
  }
  public String getIpInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Ip));
      }

  public void initLoginName(String value){
     this.initProperty(S_LoginName,value);
  }
  public  void setLoginName(String value){
     this.set(S_LoginName,value);
  }
  public  void setLoginNameNull(){
     this.set(S_LoginName,null);
  }

  public String getLoginName(){
       return DataType.getAsString(this.get(S_LoginName));
  
  }
  public String getLoginNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_LoginName));
      }

  public void initLastFailTime(Timestamp value){
     this.initProperty(S_LastFailTime,value);
  }
  public  void setLastFailTime(Timestamp value){
     this.set(S_LastFailTime,value);
  }
  public  void setLastFailTimeNull(){
     this.set(S_LastFailTime,null);
  }

  public Timestamp getLastFailTime(){
        return DataType.getAsDateTime(this.get(S_LastFailTime));
  
  }
  public Timestamp getLastFailTimeInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_LastFailTime));
      }

  public void initFailCount(long value){
     this.initProperty(S_FailCount,new Long(value));
  }
  public  void setFailCount(long value){
     this.set(S_FailCount,new Long(value));
  }
  public  void setFailCountNull(){
     this.set(S_FailCount,null);
  }

  public long getFailCount(){
        return DataType.getAsLong(this.get(S_FailCount));
  
  }
  public long getFailCountInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_FailCount));
      }

  public void initExt01(String value){
     this.initProperty(S_Ext01,value);
  }
  public  void setExt01(String value){
     this.set(S_Ext01,value);
  }
  public  void setExt01Null(){
     this.set(S_Ext01,null);
  }

  public String getExt01(){
       return DataType.getAsString(this.get(S_Ext01));
  
  }
  public String getExt01InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Ext01));
      }

  public void initExt02(String value){
     this.initProperty(S_Ext02,value);
  }
  public  void setExt02(String value){
     this.set(S_Ext02,value);
  }
  public  void setExt02Null(){
     this.set(S_Ext02,null);
  }

  public String getExt02(){
       return DataType.getAsString(this.get(S_Ext02));
  
  }
  public String getExt02InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Ext02));
      }


 
 }

