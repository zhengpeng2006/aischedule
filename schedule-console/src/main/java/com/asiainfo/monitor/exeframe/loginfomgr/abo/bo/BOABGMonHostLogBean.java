package com.asiainfo.monitor.exeframe.loginfomgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.loginfomgr.abo.ivalues.*;

public class BOABGMonHostLogBean extends DataContainer implements DataContainerInterface,IBOABGMonHostLogValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.loginfomgr.abo.bo.BOABGMonHostLog";



  public final static  String S_AcqLogId = "ACQ_LOG_ID";
  public final static  String S_HostIp = "HOST_IP";
  public final static  String S_KpiCpu = "KPI_CPU";
  public final static  String S_KpiMem = "KPI_MEM";
  public final static  String S_KpiFs = "KPI_FS";
  public final static  String S_ExtKpi1 = "EXT_KPI_1";
  public final static  String S_ExtKpi2 = "EXT_KPI_2";
  public final static  String S_ExtKpi3 = "EXT_KPI_3";
  public final static  String S_ExtKpi4 = "EXT_KPI_4";
  public final static  String S_SystemDomain = "SYSTEM_DOMAIN";
  public final static  String S_SubsystemDomain = "SUBSYSTEM_DOMAIN";
  public final static  String S_AppServerName = "APP_SERVER_NAME";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_MonFlag = "MON_FLAG";
  public final static  String S_AcqDate = "ACQ_DATE";
  public final static  String S_State = "STATE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOABGMonHostLogBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initAcqLogId(long value){
     this.initProperty(S_AcqLogId,new Long(value));
  }
  public  void setAcqLogId(long value){
     this.set(S_AcqLogId,new Long(value));
  }
  public  void setAcqLogIdNull(){
     this.set(S_AcqLogId,null);
  }

  public long getAcqLogId(){
        return DataType.getAsLong(this.get(S_AcqLogId));
  
  }
  public long getAcqLogIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_AcqLogId));
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

  public void initKpiCpu(String value){
     this.initProperty(S_KpiCpu,value);
  }
  public  void setKpiCpu(String value){
     this.set(S_KpiCpu,value);
  }
  public  void setKpiCpuNull(){
     this.set(S_KpiCpu,null);
  }

  public String getKpiCpu(){
       return DataType.getAsString(this.get(S_KpiCpu));
  
  }
  public String getKpiCpuInitialValue(){
        return DataType.getAsString(this.getOldObj(S_KpiCpu));
      }

  public void initKpiMem(String value){
     this.initProperty(S_KpiMem,value);
  }
  public  void setKpiMem(String value){
     this.set(S_KpiMem,value);
  }
  public  void setKpiMemNull(){
     this.set(S_KpiMem,null);
  }

  public String getKpiMem(){
       return DataType.getAsString(this.get(S_KpiMem));
  
  }
  public String getKpiMemInitialValue(){
        return DataType.getAsString(this.getOldObj(S_KpiMem));
      }

  public void initKpiFs(String value){
     this.initProperty(S_KpiFs,value);
  }
  public  void setKpiFs(String value){
     this.set(S_KpiFs,value);
  }
  public  void setKpiFsNull(){
     this.set(S_KpiFs,null);
  }

  public String getKpiFs(){
       return DataType.getAsString(this.get(S_KpiFs));
  
  }
  public String getKpiFsInitialValue(){
        return DataType.getAsString(this.getOldObj(S_KpiFs));
      }

  public void initExtKpi1(String value){
     this.initProperty(S_ExtKpi1,value);
  }
  public  void setExtKpi1(String value){
     this.set(S_ExtKpi1,value);
  }
  public  void setExtKpi1Null(){
     this.set(S_ExtKpi1,null);
  }

  public String getExtKpi1(){
       return DataType.getAsString(this.get(S_ExtKpi1));
  
  }
  public String getExtKpi1InitialValue(){
        return DataType.getAsString(this.getOldObj(S_ExtKpi1));
      }

  public void initExtKpi2(String value){
     this.initProperty(S_ExtKpi2,value);
  }
  public  void setExtKpi2(String value){
     this.set(S_ExtKpi2,value);
  }
  public  void setExtKpi2Null(){
     this.set(S_ExtKpi2,null);
  }

  public String getExtKpi2(){
       return DataType.getAsString(this.get(S_ExtKpi2));
  
  }
  public String getExtKpi2InitialValue(){
        return DataType.getAsString(this.getOldObj(S_ExtKpi2));
      }

  public void initExtKpi3(String value){
     this.initProperty(S_ExtKpi3,value);
  }
  public  void setExtKpi3(String value){
     this.set(S_ExtKpi3,value);
  }
  public  void setExtKpi3Null(){
     this.set(S_ExtKpi3,null);
  }

  public String getExtKpi3(){
       return DataType.getAsString(this.get(S_ExtKpi3));
  
  }
  public String getExtKpi3InitialValue(){
        return DataType.getAsString(this.getOldObj(S_ExtKpi3));
      }

  public void initExtKpi4(String value){
     this.initProperty(S_ExtKpi4,value);
  }
  public  void setExtKpi4(String value){
     this.set(S_ExtKpi4,value);
  }
  public  void setExtKpi4Null(){
     this.set(S_ExtKpi4,null);
  }

  public String getExtKpi4(){
       return DataType.getAsString(this.get(S_ExtKpi4));
  
  }
  public String getExtKpi4InitialValue(){
        return DataType.getAsString(this.getOldObj(S_ExtKpi4));
      }

  public void initSystemDomain(String value){
     this.initProperty(S_SystemDomain,value);
  }
  public  void setSystemDomain(String value){
     this.set(S_SystemDomain,value);
  }
  public  void setSystemDomainNull(){
     this.set(S_SystemDomain,null);
  }

  public String getSystemDomain(){
       return DataType.getAsString(this.get(S_SystemDomain));
  
  }
  public String getSystemDomainInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SystemDomain));
      }

  public void initSubsystemDomain(String value){
     this.initProperty(S_SubsystemDomain,value);
  }
  public  void setSubsystemDomain(String value){
     this.set(S_SubsystemDomain,value);
  }
  public  void setSubsystemDomainNull(){
     this.set(S_SubsystemDomain,null);
  }

  public String getSubsystemDomain(){
       return DataType.getAsString(this.get(S_SubsystemDomain));
  
  }
  public String getSubsystemDomainInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SubsystemDomain));
      }

  public void initAppServerName(String value){
     this.initProperty(S_AppServerName,value);
  }
  public  void setAppServerName(String value){
     this.set(S_AppServerName,value);
  }
  public  void setAppServerNameNull(){
     this.set(S_AppServerName,null);
  }

  public String getAppServerName(){
       return DataType.getAsString(this.get(S_AppServerName));
  
  }
  public String getAppServerNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_AppServerName));
      }

  public void initCreateDate(String value){
     this.initProperty(S_CreateDate,value);
  }
  public  void setCreateDate(String value){
     this.set(S_CreateDate,value);
  }
  public  void setCreateDateNull(){
     this.set(S_CreateDate,null);
  }

  public String getCreateDate(){
       return DataType.getAsString(this.get(S_CreateDate));
  
  }
  public String getCreateDateInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CreateDate));
      }

  public void initMonFlag(String value){
     this.initProperty(S_MonFlag,value);
  }
  public  void setMonFlag(String value){
     this.set(S_MonFlag,value);
  }
  public  void setMonFlagNull(){
     this.set(S_MonFlag,null);
  }

  public String getMonFlag(){
       return DataType.getAsString(this.get(S_MonFlag));
  
  }
  public String getMonFlagInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MonFlag));
      }

  public void initAcqDate(Timestamp value){
     this.initProperty(S_AcqDate,value);
  }
  public  void setAcqDate(Timestamp value){
     this.set(S_AcqDate,value);
  }
  public  void setAcqDateNull(){
     this.set(S_AcqDate,null);
  }

  public Timestamp getAcqDate(){
        return DataType.getAsDateTime(this.get(S_AcqDate));
  
  }
  public Timestamp getAcqDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_AcqDate));
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


 
 }

