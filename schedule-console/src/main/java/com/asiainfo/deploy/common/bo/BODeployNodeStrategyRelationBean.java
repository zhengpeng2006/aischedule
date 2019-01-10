package com.asiainfo.deploy.common.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.deploy.common.ivalues.*;

public class BODeployNodeStrategyRelationBean extends DataContainer implements DataContainerInterface,IBODeployNodeStrategyRelationValue{

  private static String  m_boName = "com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelation";



  public final static  String S_DeployStrategyId = "DEPLOY_STRATEGY_ID";
  public final static  String S_CreateTime = "CREATE_TIME";
  public final static  String S_NodeId = "NODE_ID";
  public final static  String S_ModifyTime = "MODIFY_TIME";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BODeployNodeStrategyRelationBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //��������������������ҵ���������
   throw new AIException("Cannot reset ObjectType");
 }


  public void initDeployStrategyId(long value){
     this.initProperty(S_DeployStrategyId,new Long(value));
  }
  public  void setDeployStrategyId(long value){
     this.set(S_DeployStrategyId,new Long(value));
  }
  public  void setDeployStrategyIdNull(){
     this.set(S_DeployStrategyId,null);
  }

  public long getDeployStrategyId(){
        return DataType.getAsLong(this.get(S_DeployStrategyId));
  
  }
  public long getDeployStrategyIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_DeployStrategyId));
      }

  public void initCreateTime(Timestamp value){
     this.initProperty(S_CreateTime,value);
  }
  public  void setCreateTime(Timestamp value){
     this.set(S_CreateTime,value);
  }
  public  void setCreateTimeNull(){
     this.set(S_CreateTime,null);
  }

  public Timestamp getCreateTime(){
        return DataType.getAsDateTime(this.get(S_CreateTime));
  
  }
  public Timestamp getCreateTimeInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_CreateTime));
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

  public void initModifyTime(Timestamp value){
     this.initProperty(S_ModifyTime,value);
  }
  public  void setModifyTime(Timestamp value){
     this.set(S_ModifyTime,value);
  }
  public  void setModifyTimeNull(){
     this.set(S_ModifyTime,null);
  }

  public Timestamp getModifyTime(){
        return DataType.getAsDateTime(this.get(S_ModifyTime));
  
  }
  public Timestamp getModifyTimeInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_ModifyTime));
      }


 
 }

