package com.asiainfo.batchwriteoff.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.*;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfPsValue;

public class BOBsBatchWfPsBean extends DataContainer implements DataContainerInterface,IBOBsBatchWfPsValue {

  private static String  m_boName = "com.asiainfo.crm.common.bo.BOBsBatchWfPs";



  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_Port = "PORT";
  public final static  String S_ShellCatalog = "SHELL_CATALOG";
  public final static  String S_StartShell = "START_SHELL";
  public final static  String S_UserName = "USER_NAME";
  public final static  String S_ThreadNum = "THREAD_NUM";
  public final static  String S_WfProcessName = "WF_PROCESS_NAME";
  public final static  String S_StopShell = "STOP_SHELL";
  public final static  String S_MonitorShell = "MONITOR_SHELL";
  public final static  String S_Password = "PASSWORD";
  public final static  String S_Paras = "PARAS";
  public final static  String S_HostIp = "HOST_IP";
  public final static  String S_WfProcessId = "WF_PROCESS_ID";
  public final static  String S_WfRedisId = "WF_REDIS_ID";
  public final static  String S_WfFlowId = "WF_FLOW_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOBsBatchWfPsBean() throws AIException {
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException {
   return S_TYPE;
 }

 public void setObjectType(ObjectType value) throws AIException {
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initRegionId(String value){
     this.initProperty(S_RegionId,value);
  }
  public  void setRegionId(String value){
     this.set(S_RegionId,value);
  }
  public  void setRegionIdNull(){
     this.set(S_RegionId,null);
  }

  public String getRegionId(){
       return DataType.getAsString(this.get(S_RegionId));
  
  }
  public String getRegionIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RegionId));
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

  public void initShellCatalog(String value){
     this.initProperty(S_ShellCatalog,value);
  }
  public  void setShellCatalog(String value){
     this.set(S_ShellCatalog,value);
  }
  public  void setShellCatalogNull(){
     this.set(S_ShellCatalog,null);
  }

  public String getShellCatalog(){
       return DataType.getAsString(this.get(S_ShellCatalog));
  
  }
  public String getShellCatalogInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ShellCatalog));
      }

  public void initStartShell(String value){
     this.initProperty(S_StartShell,value);
  }
  public  void setStartShell(String value){
     this.set(S_StartShell,value);
  }
  public  void setStartShellNull(){
     this.set(S_StartShell,null);
  }

  public String getStartShell(){
       return DataType.getAsString(this.get(S_StartShell));
  
  }
  public String getStartShellInitialValue(){
        return DataType.getAsString(this.getOldObj(S_StartShell));
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

  public void initThreadNum(int value){
     this.initProperty(S_ThreadNum,new Integer(value));
  }
  public  void setThreadNum(int value){
     this.set(S_ThreadNum,new Integer(value));
  }
  public  void setThreadNumNull(){
     this.set(S_ThreadNum,null);
  }

  public int getThreadNum(){
        return DataType.getAsInt(this.get(S_ThreadNum));
  
  }
  public int getThreadNumInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_ThreadNum));
      }

  public void initWfProcessName(String value){
     this.initProperty(S_WfProcessName,value);
  }
  public  void setWfProcessName(String value){
     this.set(S_WfProcessName,value);
  }
  public  void setWfProcessNameNull(){
     this.set(S_WfProcessName,null);
  }

  public String getWfProcessName(){
       return DataType.getAsString(this.get(S_WfProcessName));
  
  }
  public String getWfProcessNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_WfProcessName));
      }

  public void initStopShell(String value){
     this.initProperty(S_StopShell,value);
  }
  public  void setStopShell(String value){
     this.set(S_StopShell,value);
  }
  public  void setStopShellNull(){
     this.set(S_StopShell,null);
  }

  public String getStopShell(){
       return DataType.getAsString(this.get(S_StopShell));
  
  }
  public String getStopShellInitialValue(){
        return DataType.getAsString(this.getOldObj(S_StopShell));
      }

  public void initMonitorShell(String value){
     this.initProperty(S_MonitorShell,value);
  }
  public  void setMonitorShell(String value){
     this.set(S_MonitorShell,value);
  }
  public  void setMonitorShellNull(){
     this.set(S_MonitorShell,null);
  }

  public String getMonitorShell(){
       return DataType.getAsString(this.get(S_MonitorShell));
  
  }
  public String getMonitorShellInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MonitorShell));
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

  public void initParas(String value){
     this.initProperty(S_Paras,value);
  }
  public  void setParas(String value){
     this.set(S_Paras,value);
  }
  public  void setParasNull(){
     this.set(S_Paras,null);
  }

  public String getParas(){
       return DataType.getAsString(this.get(S_Paras));
  
  }
  public String getParasInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Paras));
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

  public void initWfProcessId(String value){
     this.initProperty(S_WfProcessId,value);
  }
  public  void setWfProcessId(String value){
     this.set(S_WfProcessId,value);
  }
  public  void setWfProcessIdNull(){
     this.set(S_WfProcessId,null);
  }

  public String getWfProcessId(){
       return DataType.getAsString(this.get(S_WfProcessId));
  
  }
  public String getWfProcessIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_WfProcessId));
      }

  public void initWfRedisId(String value){
     this.initProperty(S_WfRedisId,value);
  }
  public  void setWfRedisId(String value){
     this.set(S_WfRedisId,value);
  }
  public  void setWfRedisIdNull(){
     this.set(S_WfRedisId,null);
  }

  public String getWfRedisId(){
       return DataType.getAsString(this.get(S_WfRedisId));
  
  }
  public String getWfRedisIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_WfRedisId));
      }

  public void initWfFlowId(long value){
     this.initProperty(S_WfFlowId,new Long(value));
  }
  public  void setWfFlowId(long value){
     this.set(S_WfFlowId,new Long(value));
  }
  public  void setWfFlowIdNull(){
     this.set(S_WfFlowId,null);
  }

  public long getWfFlowId(){
        return DataType.getAsLong(this.get(S_WfFlowId));
  
  }
  public long getWfFlowIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_WfFlowId));
      }


 
 }

