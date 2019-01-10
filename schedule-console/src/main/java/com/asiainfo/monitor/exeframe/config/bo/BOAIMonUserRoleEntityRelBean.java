package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleEntityRelValue;

public class BOAIMonUserRoleEntityRelBean extends DataContainer implements DataContainerInterface,IBOAIMonUserRoleEntityRelValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserRoleEntityRel";



  public final static  String S_UserRoleId = "USER_ROLE_ID";
  public final static  String S_EntityId = "ENTITY_ID";
  public final static  String S_RelateId = "RELATE_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonUserRoleEntityRelBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initUserRoleId(long value){
     this.initProperty(S_UserRoleId,new Long(value));
  }
  public  void setUserRoleId(long value){
     this.set(S_UserRoleId,new Long(value));
  }
  public  void setUserRoleIdNull(){
     this.set(S_UserRoleId,null);
  }

  public long getUserRoleId(){
        return DataType.getAsLong(this.get(S_UserRoleId));
  
  }
  public long getUserRoleIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_UserRoleId));
      }

  public void initEntityId(long value){
     this.initProperty(S_EntityId,new Long(value));
  }
  public  void setEntityId(long value){
     this.set(S_EntityId,new Long(value));
  }
  public  void setEntityIdNull(){
     this.set(S_EntityId,null);
  }

  public long getEntityId(){
        return DataType.getAsLong(this.get(S_EntityId));
  
  }
  public long getEntityIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_EntityId));
      }

  public void initRelateId(long value){
     this.initProperty(S_RelateId,new Long(value));
  }
  public  void setRelateId(long value){
     this.set(S_RelateId,new Long(value));
  }
  public  void setRelateIdNull(){
     this.set(S_RelateId,null);
  }

  public long getRelateId(){
        return DataType.getAsLong(this.get(S_RelateId));
  
  }
  public long getRelateIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RelateId));
      }


 
 }

