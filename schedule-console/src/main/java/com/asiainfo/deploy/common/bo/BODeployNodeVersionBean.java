package com.asiainfo.deploy.common.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.deploy.common.ivalues.*;

public class BODeployNodeVersionBean extends DataContainer implements DataContainerInterface,IBODeployNodeVersionValue{

  private static String  m_boName = "com.asiainfo.deploy.common.bo.BODeployNodeVersion";



  public final static  String S_VersionId = "VERSION_ID";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_OperatorId = "OPERATOR_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_NodeId = "NODE_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BODeployNodeVersionBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initVersionId(long value){
     this.initProperty(S_VersionId,new Long(value));
  }
  public  void setVersionId(long value){
     this.set(S_VersionId,new Long(value));
  }
  public  void setVersionIdNull(){
     this.set(S_VersionId,null);
  }

  public long getVersionId(){
        return DataType.getAsLong(this.get(S_VersionId));
  
  }
  public long getVersionIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_VersionId));
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

  public void initOperatorId(long value){
     this.initProperty(S_OperatorId,new Long(value));
  }
  public  void setOperatorId(long value){
     this.set(S_OperatorId,new Long(value));
  }
  public  void setOperatorIdNull(){
     this.set(S_OperatorId,null);
  }

  public long getOperatorId(){
        return DataType.getAsLong(this.get(S_OperatorId));
  
  }
  public long getOperatorIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_OperatorId));
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


 
 }

