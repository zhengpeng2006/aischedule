package com.asiainfo.monitor.exeframe.configmgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.*;

public class BOAIMonServerBean extends DataContainer implements DataContainerInterface,IBOAIMonServerValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonServer";



  public final static  String S_ServerId = "SERVER_ID";
  public final static  String S_ServerCode = "SERVER_CODE";
  public final static  String S_NodeId = "NODE_ID";
  public final static  String S_ServerName = "SERVER_NAME";
  public final static  String S_BusinessType = "BUSINESS_TYPE";
  public final static  String S_LocatorType = "LOCATOR_TYPE";
  public final static  String S_Locator = "LOCATOR";
  public final static  String S_ServerIp = "SERVER_IP";
  public final static  String S_ServerPort = "SERVER_PORT";
  public final static  String S_Sversion = "SVERSION";
  public final static  String S_CheckUrl = "CHECK_URL";
  public final static  String S_MidwareType = "MIDWARE_TYPE";
  public final static  String S_TempType = "TEMP_TYPE";
  public final static  String S_StartCmdId = "START_CMD_ID";
  public final static  String S_StopCmdId = "STOP_CMD_ID";
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
  public BOAIMonServerBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initServerId(long value){
     this.initProperty(S_ServerId,new Long(value));
  }
  public  void setServerId(long value){
     this.set(S_ServerId,new Long(value));
  }
  public  void setServerIdNull(){
     this.set(S_ServerId,null);
  }

  public long getServerId(){
        return DataType.getAsLong(this.get(S_ServerId));
  
  }
  public long getServerIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ServerId));
      }

  public void initServerCode(String value){
     this.initProperty(S_ServerCode,value);
  }
  public  void setServerCode(String value){
     this.set(S_ServerCode,value);
  }
  public  void setServerCodeNull(){
     this.set(S_ServerCode,null);
  }

  public String getServerCode(){
       return DataType.getAsString(this.get(S_ServerCode));
  
  }
  public String getServerCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ServerCode));
      }

  public void initNodeId(long value){
     this.initProperty(S_NodeId,new Long(value));
  }
  public  void setNodeId(long value){
     this.set(S_NodeId,new Long(value));
  }
  public  void setNodeIdNull(){
     this.set(S_NodeId,null);
  }

  public long getNodeId(){
        return DataType.getAsLong(this.get(S_NodeId));
  
  }
  public long getNodeIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_NodeId));
      }

  public void initServerName(String value){
     this.initProperty(S_ServerName,value);
  }
  public  void setServerName(String value){
     this.set(S_ServerName,value);
  }
  public  void setServerNameNull(){
     this.set(S_ServerName,null);
  }

  public String getServerName(){
       return DataType.getAsString(this.get(S_ServerName));
  
  }
  public String getServerNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ServerName));
      }

  public void initBusinessType(String value){
     this.initProperty(S_BusinessType,value);
  }
  public  void setBusinessType(String value){
     this.set(S_BusinessType,value);
  }
  public  void setBusinessTypeNull(){
     this.set(S_BusinessType,null);
  }

  public String getBusinessType(){
       return DataType.getAsString(this.get(S_BusinessType));
  
  }
  public String getBusinessTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_BusinessType));
      }

  public void initLocatorType(String value){
     this.initProperty(S_LocatorType,value);
  }
  public  void setLocatorType(String value){
     this.set(S_LocatorType,value);
  }
  public  void setLocatorTypeNull(){
     this.set(S_LocatorType,null);
  }

  public String getLocatorType(){
       return DataType.getAsString(this.get(S_LocatorType));
  
  }
  public String getLocatorTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_LocatorType));
      }

  public void initLocator(String value){
     this.initProperty(S_Locator,value);
  }
  public  void setLocator(String value){
     this.set(S_Locator,value);
  }
  public  void setLocatorNull(){
     this.set(S_Locator,null);
  }

  public String getLocator(){
       return DataType.getAsString(this.get(S_Locator));
  
  }
  public String getLocatorInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Locator));
      }

  public void initServerIp(String value){
     this.initProperty(S_ServerIp,value);
  }
  public  void setServerIp(String value){
     this.set(S_ServerIp,value);
  }
  public  void setServerIpNull(){
     this.set(S_ServerIp,null);
  }

  public String getServerIp(){
       return DataType.getAsString(this.get(S_ServerIp));
  
  }
  public String getServerIpInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ServerIp));
      }

  public void initServerPort(short value){
     this.initProperty(S_ServerPort,new Short(value));
  }
  public  void setServerPort(short value){
     this.set(S_ServerPort,new Short(value));
  }
  public  void setServerPortNull(){
     this.set(S_ServerPort,null);
  }

  public short getServerPort(){
        return DataType.getAsShort(this.get(S_ServerPort));
  
  }
  public short getServerPortInitialValue(){
        return DataType.getAsShort(this.getOldObj(S_ServerPort));
      }

  public void initSversion(String value){
     this.initProperty(S_Sversion,value);
  }
  public  void setSversion(String value){
     this.set(S_Sversion,value);
  }
  public  void setSversionNull(){
     this.set(S_Sversion,null);
  }

  public String getSversion(){
       return DataType.getAsString(this.get(S_Sversion));
  
  }
  public String getSversionInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Sversion));
      }

  public void initCheckUrl(String value){
     this.initProperty(S_CheckUrl,value);
  }
  public  void setCheckUrl(String value){
     this.set(S_CheckUrl,value);
  }
  public  void setCheckUrlNull(){
     this.set(S_CheckUrl,null);
  }

  public String getCheckUrl(){
       return DataType.getAsString(this.get(S_CheckUrl));
  
  }
  public String getCheckUrlInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CheckUrl));
      }

  public void initMidwareType(String value){
     this.initProperty(S_MidwareType,value);
  }
  public  void setMidwareType(String value){
     this.set(S_MidwareType,value);
  }
  public  void setMidwareTypeNull(){
     this.set(S_MidwareType,null);
  }

  public String getMidwareType(){
       return DataType.getAsString(this.get(S_MidwareType));
  
  }
  public String getMidwareTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MidwareType));
      }

  public void initTempType(String value){
     this.initProperty(S_TempType,value);
  }
  public  void setTempType(String value){
     this.set(S_TempType,value);
  }
  public  void setTempTypeNull(){
     this.set(S_TempType,null);
  }

  public String getTempType(){
       return DataType.getAsString(this.get(S_TempType));
  
  }
  public String getTempTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TempType));
      }

  public void initStartCmdId(short value){
     this.initProperty(S_StartCmdId,new Short(value));
  }
  public  void setStartCmdId(short value){
     this.set(S_StartCmdId,new Short(value));
  }
  public  void setStartCmdIdNull(){
     this.set(S_StartCmdId,null);
  }

  public short getStartCmdId(){
        return DataType.getAsShort(this.get(S_StartCmdId));
  
  }
  public short getStartCmdIdInitialValue(){
        return DataType.getAsShort(this.getOldObj(S_StartCmdId));
      }

  public void initStopCmdId(short value){
     this.initProperty(S_StopCmdId,new Short(value));
  }
  public  void setStopCmdId(short value){
     this.set(S_StopCmdId,new Short(value));
  }
  public  void setStopCmdIdNull(){
     this.set(S_StopCmdId,null);
  }

  public short getStopCmdId(){
        return DataType.getAsShort(this.get(S_StopCmdId));
  
  }
  public short getStopCmdIdInitialValue(){
        return DataType.getAsShort(this.getOldObj(S_StopCmdId));
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

