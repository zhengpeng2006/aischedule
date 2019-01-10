package com.asiainfo.monitor.exeframe.configmgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.*;

public class BOAIMonMasterSlaveRelBean extends DataContainer implements DataContainerInterface,IBOAIMonMasterSlaveRelValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonMasterSlaveRel";



  public final static  String S_RelationId = "RELATION_ID";
  public final static  String S_MasterHostId = "MASTER_HOST_ID";
  public final static  String S_SlaveHostId = "SLAVE_HOST_ID";
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
  public BOAIMonMasterSlaveRelBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initRelationId(long value){
     this.initProperty(S_RelationId,new Long(value));
  }
  public  void setRelationId(long value){
     this.set(S_RelationId,new Long(value));
  }
  public  void setRelationIdNull(){
     this.set(S_RelationId,null);
  }

  public long getRelationId(){
        return DataType.getAsLong(this.get(S_RelationId));
  
  }
  public long getRelationIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RelationId));
      }

  public void initMasterHostId(long value){
     this.initProperty(S_MasterHostId,new Long(value));
  }
  public  void setMasterHostId(long value){
     this.set(S_MasterHostId,new Long(value));
  }
  public  void setMasterHostIdNull(){
     this.set(S_MasterHostId,null);
  }

  public long getMasterHostId(){
        return DataType.getAsLong(this.get(S_MasterHostId));
  
  }
  public long getMasterHostIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_MasterHostId));
      }

  public void initSlaveHostId(long value){
     this.initProperty(S_SlaveHostId,new Long(value));
  }
  public  void setSlaveHostId(long value){
     this.set(S_SlaveHostId,new Long(value));
  }
  public  void setSlaveHostIdNull(){
     this.set(S_SlaveHostId,null);
  }

  public long getSlaveHostId(){
        return DataType.getAsLong(this.get(S_SlaveHostId));
  
  }
  public long getSlaveHostIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_SlaveHostId));
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

