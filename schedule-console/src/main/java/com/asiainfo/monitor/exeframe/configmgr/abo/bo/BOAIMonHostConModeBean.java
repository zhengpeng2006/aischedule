package com.asiainfo.monitor.exeframe.configmgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.*;

public class BOAIMonHostConModeBean extends DataContainer implements DataContainerInterface,IBOAIMonHostConModeValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostConMode";



  public final static  String S_RelationId = "RELATION_ID";
  public final static  String S_ConId = "CON_ID";
  public final static  String S_HostId = "HOST_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonHostConModeBean() throws AIException{
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

  public void initConId(long value){
     this.initProperty(S_ConId,new Long(value));
  }
  public  void setConId(long value){
     this.set(S_ConId,new Long(value));
  }
  public  void setConIdNull(){
     this.set(S_ConId,null);
  }

  public long getConId(){
        return DataType.getAsLong(this.get(S_ConId));
  
  }
  public long getConIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ConId));
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


 
 }

