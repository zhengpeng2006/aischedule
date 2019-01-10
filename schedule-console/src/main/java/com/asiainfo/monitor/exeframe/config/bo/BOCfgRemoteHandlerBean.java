package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgRemoteHandlerValue;

public class BOCfgRemoteHandlerBean extends DataContainer implements DataContainerInterface,IBOCfgRemoteHandlerValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgRemoteHandler";



  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ImplClassName = "IMPL_CLASS_NAME";
  public final static  String S_HandlerId = "HANDLER_ID";
  public final static  String S_CfgRemoteCode = "CFG_REMOTE_CODE";
  public final static  String S_InterfaceClassName = "INTERFACE_CLASS_NAME";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgRemoteHandlerBean() throws AIException{
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

  public void initImplClassName(String value){
     this.initProperty(S_ImplClassName,value);
  }
  public  void setImplClassName(String value){
     this.set(S_ImplClassName,value);
  }
  public  void setImplClassNameNull(){
     this.set(S_ImplClassName,null);
  }

  public String getImplClassName(){
       return DataType.getAsString(this.get(S_ImplClassName));
  
  }
  public String getImplClassNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ImplClassName));
      }

  public void initHandlerId(long value){
     this.initProperty(S_HandlerId,new Long(value));
  }
  public  void setHandlerId(long value){
     this.set(S_HandlerId,new Long(value));
  }
  public  void setHandlerIdNull(){
     this.set(S_HandlerId,null);
  }

  public long getHandlerId(){
        return DataType.getAsLong(this.get(S_HandlerId));
  
  }
  public long getHandlerIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_HandlerId));
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

  public void initInterfaceClassName(String value){
     this.initProperty(S_InterfaceClassName,value);
  }
  public  void setInterfaceClassName(String value){
     this.set(S_InterfaceClassName,value);
  }
  public  void setInterfaceClassNameNull(){
     this.set(S_InterfaceClassName,null);
  }

  public String getInterfaceClassName(){
       return DataType.getAsString(this.get(S_InterfaceClassName));
  
  }
  public String getInterfaceClassNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_InterfaceClassName));
      }


 
 }

