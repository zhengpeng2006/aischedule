package com.asiainfo.index.base.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.index.base.ivalues.*;

public class BOBsMonitorBean extends DataContainer implements DataContainerInterface,IBOBsMonitorValue{

  private static String  m_boName = "com.asiainfo.index.base.bo.BOBsMonitor";



  public final static  String S_MonitorName = "MONITOR_NAME";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_MonitorDesc = "MONITOR_DESC";
  public final static  String S_MonitorId = "MONITOR_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOBsMonitorBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initMonitorName(String value){
     this.initProperty(S_MonitorName,value);
  }
  public  void setMonitorName(String value){
     this.set(S_MonitorName,value);
  }
  public  void setMonitorNameNull(){
     this.set(S_MonitorName,null);
  }

  public String getMonitorName(){
       return DataType.getAsString(this.get(S_MonitorName));
  
  }
  public String getMonitorNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MonitorName));
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

  public void initDoneDate(Timestamp value){
     this.initProperty(S_DoneDate,value);
  }
  public  void setDoneDate(Timestamp value){
     this.set(S_DoneDate,value);
  }
  public  void setDoneDateNull(){
     this.set(S_DoneDate,null);
  }

  public Timestamp getDoneDate(){
        return DataType.getAsDateTime(this.get(S_DoneDate));
  
  }
  public Timestamp getDoneDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_DoneDate));
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

  public void initMonitorDesc(String value){
     this.initProperty(S_MonitorDesc,value);
  }
  public  void setMonitorDesc(String value){
     this.set(S_MonitorDesc,value);
  }
  public  void setMonitorDescNull(){
     this.set(S_MonitorDesc,null);
  }

  public String getMonitorDesc(){
       return DataType.getAsString(this.get(S_MonitorDesc));
  
  }
  public String getMonitorDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MonitorDesc));
      }

  public void initMonitorId(int value){
     this.initProperty(S_MonitorId,new Integer(value));
  }
  public  void setMonitorId(int value){
     this.set(S_MonitorId,new Integer(value));
  }
  public  void setMonitorIdNull(){
     this.set(S_MonitorId,null);
  }

  public int getMonitorId(){
        return DataType.getAsInt(this.get(S_MonitorId));
  
  }
  public int getMonitorIdInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_MonitorId));
      }


 
 }

