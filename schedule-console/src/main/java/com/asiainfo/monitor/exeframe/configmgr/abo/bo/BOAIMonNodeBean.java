package com.asiainfo.monitor.exeframe.configmgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.*;

public class BOAIMonNodeBean extends DataContainer implements DataContainerInterface,IBOAIMonNodeValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonNode";



  public final static  String S_NodeId = "NODE_ID";
  public final static  String S_HostId = "HOST_ID";
  public final static  String S_NodeCode = "NODE_CODE";
  public final static  String S_NodeName = "NODE_NAME";
  public final static  String S_State = "STATE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_Remark = "REMARK";
  public final static  String S_IsMonitorNode = "IS_MONITOR_NODE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonNodeBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
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

  public void initNodeCode(String value){
     this.initProperty(S_NodeCode,value);
  }
  public  void setNodeCode(String value){
     this.set(S_NodeCode,value);
  }
  public  void setNodeCodeNull(){
     this.set(S_NodeCode,null);
  }

  public String getNodeCode(){
       return DataType.getAsString(this.get(S_NodeCode));
  
  }
  public String getNodeCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_NodeCode));
      }

  public void initNodeName(String value){
     this.initProperty(S_NodeName,value);
  }
  public  void setNodeName(String value){
     this.set(S_NodeName,value);
  }
  public  void setNodeNameNull(){
     this.set(S_NodeName,null);
  }

  public String getNodeName(){
       return DataType.getAsString(this.get(S_NodeName));
  
  }
  public String getNodeNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_NodeName));
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

  public void initIsMonitorNode(String value){
     this.initProperty(S_IsMonitorNode,value);
  }
  public  void setIsMonitorNode(String value){
     this.set(S_IsMonitorNode,value);
  }
  public  void setIsMonitorNodeNull(){
     this.set(S_IsMonitorNode,null);
  }

  public String getIsMonitorNode(){
       return DataType.getAsString(this.get(S_IsMonitorNode));
  
  }
  public String getIsMonitorNodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IsMonitorNode));
      }


 
 }

