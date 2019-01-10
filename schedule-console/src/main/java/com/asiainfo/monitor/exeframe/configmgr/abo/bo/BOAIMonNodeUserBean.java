package com.asiainfo.monitor.exeframe.configmgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.*;

public class BOAIMonNodeUserBean extends DataContainer implements DataContainerInterface,IBOAIMonNodeUserValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonNodeUser";



  public final static  String S_NodeUserId = "NODE_USER_ID";
  public final static  String S_NodeId = "NODE_ID";
  public final static  String S_UserId = "USER_ID";
  public final static  String S_State = "STATE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_ModifyDate = "MODIFY_DATE";
  public final static  String S_Remark = "REMARK";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonNodeUserBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initNodeUserId(long value){
     this.initProperty(S_NodeUserId,new Long(value));
  }
  public  void setNodeUserId(long value){
     this.set(S_NodeUserId,new Long(value));
  }
  public  void setNodeUserIdNull(){
     this.set(S_NodeUserId,null);
  }

  public long getNodeUserId(){
        return DataType.getAsLong(this.get(S_NodeUserId));
  
  }
  public long getNodeUserIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_NodeUserId));
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

  public void initUserId(long value){
     this.initProperty(S_UserId,new Long(value));
  }
  public  void setUserId(long value){
     this.set(S_UserId,new Long(value));
  }
  public  void setUserIdNull(){
     this.set(S_UserId,null);
  }

  public long getUserId(){
        return DataType.getAsLong(this.get(S_UserId));
  
  }
  public long getUserIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_UserId));
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

  public void initModifyDate(Timestamp value){
     this.initProperty(S_ModifyDate,value);
  }
  public  void setModifyDate(Timestamp value){
     this.set(S_ModifyDate,value);
  }
  public  void setModifyDateNull(){
     this.set(S_ModifyDate,null);
  }

  public Timestamp getModifyDate(){
        return DataType.getAsDateTime(this.get(S_ModifyDate));
  
  }
  public Timestamp getModifyDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_ModifyDate));
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

