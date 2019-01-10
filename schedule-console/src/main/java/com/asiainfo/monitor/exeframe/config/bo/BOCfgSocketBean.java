package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgSocketValue;

public class BOCfgSocketBean extends DataContainer implements DataContainerInterface,IBOCfgSocketValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgSocket";



  public final static  String S_SocketBusinessClass = "SOCKET_BUSINESS_CLASS";
  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CfgSocketCode = "CFG_SOCKET_CODE";
  public final static  String S_SocketDesc = "SOCKET_DESC";
  public final static  String S_SocketGrp = "SOCKET_GRP";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgSocketBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initSocketBusinessClass(String value){
     this.initProperty(S_SocketBusinessClass,value);
  }
  public  void setSocketBusinessClass(String value){
     this.set(S_SocketBusinessClass,value);
  }
  public  void setSocketBusinessClassNull(){
     this.set(S_SocketBusinessClass,null);
  }

  public String getSocketBusinessClass(){
       return DataType.getAsString(this.get(S_SocketBusinessClass));
  
  }
  public String getSocketBusinessClassInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SocketBusinessClass));
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

  public void initCfgSocketCode(String value){
     this.initProperty(S_CfgSocketCode,value);
  }
  public  void setCfgSocketCode(String value){
     this.set(S_CfgSocketCode,value);
  }
  public  void setCfgSocketCodeNull(){
     this.set(S_CfgSocketCode,null);
  }

  public String getCfgSocketCode(){
       return DataType.getAsString(this.get(S_CfgSocketCode));
  
  }
  public String getCfgSocketCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CfgSocketCode));
      }

  public void initSocketDesc(String value){
     this.initProperty(S_SocketDesc,value);
  }
  public  void setSocketDesc(String value){
     this.set(S_SocketDesc,value);
  }
  public  void setSocketDescNull(){
     this.set(S_SocketDesc,null);
  }

  public String getSocketDesc(){
       return DataType.getAsString(this.get(S_SocketDesc));
  
  }
  public String getSocketDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SocketDesc));
      }

  public void initSocketGrp(String value){
     this.initProperty(S_SocketGrp,value);
  }
  public  void setSocketGrp(String value){
     this.set(S_SocketGrp,value);
  }
  public  void setSocketGrpNull(){
     this.set(S_SocketGrp,null);
  }

  public String getSocketGrp(){
       return DataType.getAsString(this.get(S_SocketGrp));
  
  }
  public String getSocketGrpInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SocketGrp));
      }


 
 }

