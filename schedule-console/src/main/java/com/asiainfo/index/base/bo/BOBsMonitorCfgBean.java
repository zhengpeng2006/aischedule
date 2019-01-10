package com.asiainfo.index.base.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.index.base.ivalues.*;

public class BOBsMonitorCfgBean extends DataContainer implements DataContainerInterface,IBOBsMonitorCfgValue{

  private static String  m_boName = "com.asiainfo.index.base.bo.BOBsMonitorCfg";



  public final static  String S_IndexKind = "INDEX_KIND";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_IndexId = "INDEX_ID";
  public final static  String S_State = "STATE";
  public final static  String S_IndexCodeDisplay = "INDEX_CODE_DISPLAY";
  public final static  String S_IndexCodeMapping = "INDEX_CODE_MAPPING";
  public final static  String S_IndexNameDisplay = "INDEX_NAME_DISPLAY";
  public final static  String S_MonitorId = "MONITOR_ID";
  public final static  String S_MonitorCfgId = "MONITOR_CFG_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOBsMonitorCfgBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initIndexKind(String value){
     this.initProperty(S_IndexKind,value);
  }
  public  void setIndexKind(String value){
     this.set(S_IndexKind,value);
  }
  public  void setIndexKindNull(){
     this.set(S_IndexKind,null);
  }

  public String getIndexKind(){
       return DataType.getAsString(this.get(S_IndexKind));
  
  }
  public String getIndexKindInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IndexKind));
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

  public void initIndexId(int value){
     this.initProperty(S_IndexId,new Integer(value));
  }
  public  void setIndexId(int value){
     this.set(S_IndexId,new Integer(value));
  }
  public  void setIndexIdNull(){
     this.set(S_IndexId,null);
  }

  public int getIndexId(){
        return DataType.getAsInt(this.get(S_IndexId));
  
  }
  public int getIndexIdInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_IndexId));
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

  public void initIndexCodeDisplay(String value){
     this.initProperty(S_IndexCodeDisplay,value);
  }
  public  void setIndexCodeDisplay(String value){
     this.set(S_IndexCodeDisplay,value);
  }
  public  void setIndexCodeDisplayNull(){
     this.set(S_IndexCodeDisplay,null);
  }

  public String getIndexCodeDisplay(){
       return DataType.getAsString(this.get(S_IndexCodeDisplay));
  
  }
  public String getIndexCodeDisplayInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IndexCodeDisplay));
      }

  public void initIndexCodeMapping(String value){
     this.initProperty(S_IndexCodeMapping,value);
  }
  public  void setIndexCodeMapping(String value){
     this.set(S_IndexCodeMapping,value);
  }
  public  void setIndexCodeMappingNull(){
     this.set(S_IndexCodeMapping,null);
  }

  public String getIndexCodeMapping(){
       return DataType.getAsString(this.get(S_IndexCodeMapping));
  
  }
  public String getIndexCodeMappingInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IndexCodeMapping));
      }

  public void initIndexNameDisplay(String value){
     this.initProperty(S_IndexNameDisplay,value);
  }
  public  void setIndexNameDisplay(String value){
     this.set(S_IndexNameDisplay,value);
  }
  public  void setIndexNameDisplayNull(){
     this.set(S_IndexNameDisplay,null);
  }

  public String getIndexNameDisplay(){
       return DataType.getAsString(this.get(S_IndexNameDisplay));
  
  }
  public String getIndexNameDisplayInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IndexNameDisplay));
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

  public void initMonitorCfgId(int value){
     this.initProperty(S_MonitorCfgId,new Integer(value));
  }
  public  void setMonitorCfgId(int value){
     this.set(S_MonitorCfgId,new Integer(value));
  }
  public  void setMonitorCfgIdNull(){
     this.set(S_MonitorCfgId,null);
  }

  public int getMonitorCfgId(){
        return DataType.getAsInt(this.get(S_MonitorCfgId));
  
  }
  public int getMonitorCfgIdInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_MonitorCfgId));
      }


 
 }

