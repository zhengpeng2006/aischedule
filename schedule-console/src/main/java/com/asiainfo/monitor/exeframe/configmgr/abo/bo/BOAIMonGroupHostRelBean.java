package com.asiainfo.monitor.exeframe.configmgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.*;

public class BOAIMonGroupHostRelBean extends DataContainer implements DataContainerInterface,IBOAIMonGroupHostRelValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupHostRel";



  public final static  String S_RelationId = "RELATION_ID";
  public final static  String S_GroupId = "GROUP_ID";
  public final static  String S_HostId = "HOST_ID";
  public final static  String S_CreateDate = "CREATE_DATE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonGroupHostRelBean() throws AIException{
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

  public void initGroupId(long value){
     this.initProperty(S_GroupId,new Long(value));
  }
  public  void setGroupId(long value){
     this.set(S_GroupId,new Long(value));
  }
  public  void setGroupIdNull(){
     this.set(S_GroupId,null);
  }

  public long getGroupId(){
        return DataType.getAsLong(this.get(S_GroupId));
  
  }
  public long getGroupIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_GroupId));
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


 
 }

