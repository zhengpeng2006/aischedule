package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgRemoteValue;

public class BOCfgRemoteBean extends DataContainer implements DataContainerInterface,IBOCfgRemoteValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgRemote";



  public final static  String S_State = "STATE";
  public final static  String S_RemoteGrp = "REMOTE_GRP";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_RemoteLocator = "REMOTE_LOCATOR";
  public final static  String S_CfgRemoteCode = "CFG_REMOTE_CODE";
  public final static  String S_RemoteDesc = "REMOTE_DESC";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgRemoteBean() throws AIException{
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

  public void initRemoteGrp(String value){
     this.initProperty(S_RemoteGrp,value);
  }
  public  void setRemoteGrp(String value){
     this.set(S_RemoteGrp,value);
  }
  public  void setRemoteGrpNull(){
     this.set(S_RemoteGrp,null);
  }

  public String getRemoteGrp(){
       return DataType.getAsString(this.get(S_RemoteGrp));
  
  }
  public String getRemoteGrpInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RemoteGrp));
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

  public void initRemoteLocator(String value){
     this.initProperty(S_RemoteLocator,value);
  }
  public  void setRemoteLocator(String value){
     this.set(S_RemoteLocator,value);
  }
  public  void setRemoteLocatorNull(){
     this.set(S_RemoteLocator,null);
  }

  public String getRemoteLocator(){
       return DataType.getAsString(this.get(S_RemoteLocator));
  
  }
  public String getRemoteLocatorInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RemoteLocator));
      }

  public void initCfgRemoteCode(String value){
     this.initProperty(S_CfgRemoteCode,value);
  }
  public  void setCfgRemoteCode(String value){
     this.set(S_CfgRemoteCode,value);
  }
  public  void setCfgRemoteCodeNull(){
     this.set(S_CfgRemoteCode,null);
  }

  public String getCfgRemoteCode(){
       return DataType.getAsString(this.get(S_CfgRemoteCode));
  
  }
  public String getCfgRemoteCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CfgRemoteCode));
      }

  public void initRemoteDesc(String value){
     this.initProperty(S_RemoteDesc,value);
  }
  public  void setRemoteDesc(String value){
     this.set(S_RemoteDesc,value);
  }
  public  void setRemoteDescNull(){
     this.set(S_RemoteDesc,null);
  }

  public String getRemoteDesc(){
       return DataType.getAsString(this.get(S_RemoteDesc));
  
  }
  public String getRemoteDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RemoteDesc));
      }


 
 }

