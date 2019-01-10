package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRoleRelValue;

public class BOAIMonUserGroupRoleRelBean extends DataContainer implements DataContainerInterface,IBOAIMonUserGroupRoleRelValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRoleRel";



  public final static  String S_UserRoleId = "USER_ROLE_ID";
  public final static  String S_UserGroupId = "USER_GROUP_ID";
  public final static  String S_RelatId = "RELAT_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonUserGroupRoleRelBean() throws AIException{
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

  public void initRelatId(long value){
     this.initProperty(S_RelatId,new Long(value));
  }
  public  void setRelatId(long value){
     this.set(S_RelatId,new Long(value));
  }
  public  void setRelatIdNull(){
     this.set(S_RelatId,null);
  }

  public long getRelatId(){
        return DataType.getAsLong(this.get(S_RelatId));
  
  }
  public long getRelatIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RelatId));
      }


 
 }

