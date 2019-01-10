package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRelValue;

public class BOAIMonUserGroupRelBean extends DataContainer implements DataContainerInterface,IBOAIMonUserGroupRelValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRel";



  public final static  String S_UserGroupId = "USER_GROUP_ID";
  public final static  String S_RelateId = "RELATE_ID";
  public final static  String S_UserId = "USER_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonUserGroupRelBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initUserGroupId(long value){
     this.initProperty(S_UserGroupId,new Long(value));
  }
  public  void setUserGroupId(long value){
     this.set(S_UserGroupId,new Long(value));
  }
  public  void setUserGroupIdNull(){
     this.set(S_UserGroupId,null);
  }

  public long getUserGroupId(){
        return DataType.getAsLong(this.get(S_UserGroupId));
  
  }
  public long getUserGroupIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_UserGroupId));
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


 
 }

