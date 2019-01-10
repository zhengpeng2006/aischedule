package com.asiainfo.monitor.exeframe.config.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleValue;

public class BOAIMonUserRoleBean extends DataContainer implements DataContainerInterface,IBOAIMonUserRoleValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserRole";



  public final static  String S_UserRoleId = "USER_ROLE_ID";
  public final static  String S_RoleName = "ROLE_NAME";
  public final static  String S_State = "STATE";
  public final static  String S_DomainId = "DOMAIN_ID";
  public final static  String S_RoldCode = "ROLD_CODE";
  public final static  String S_OpId = "OP_ID";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_Notes = "NOTES";
  public final static  String S_CreateDate = "CREATE_DATE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonUserRoleBean() throws AIException{
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

  public void initRoleName(String value){
     this.initProperty(S_RoleName,value);
  }
  public  void setRoleName(String value){
     this.set(S_RoleName,value);
  }
  public  void setRoleNameNull(){
     this.set(S_RoleName,null);
  }

  public String getRoleName(){
       return DataType.getAsString(this.get(S_RoleName));
  
  }
  public String getRoleNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RoleName));
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

  public void initDomainId(long value){
     this.initProperty(S_DomainId,new Long(value));
  }
  public  void setDomainId(long value){
     this.set(S_DomainId,new Long(value));
  }
  public  void setDomainIdNull(){
     this.set(S_DomainId,null);
  }

  public long getDomainId(){
        return DataType.getAsLong(this.get(S_DomainId));
  
  }
  public long getDomainIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_DomainId));
      }

  public void initRoldCode(String value){
     this.initProperty(S_RoldCode,value);
  }
  public  void setRoldCode(String value){
     this.set(S_RoldCode,value);
  }
  public  void setRoldCodeNull(){
     this.set(S_RoldCode,null);
  }

  public String getRoldCode(){
       return DataType.getAsString(this.get(S_RoldCode));
  
  }
  public String getRoldCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RoldCode));
      }

  public void initOpId(long value){
     this.initProperty(S_OpId,new Long(value));
  }
  public  void setOpId(long value){
     this.set(S_OpId,new Long(value));
  }
  public  void setOpIdNull(){
     this.set(S_OpId,null);
  }

  public long getOpId(){
        return DataType.getAsLong(this.get(S_OpId));
  
  }
  public long getOpIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_OpId));
      }

  public void initDoneDate(Timestamp value){
     this.initProperty(S_DoneDate,value);
  }
  public  void setDoneDate(Timestamp value){
     this.set(S_DoneDate,value);
  }
  public  void setDoneDateNull(){
     this.set(S_DoneDate,null);
  }

  public Timestamp getDoneDate(){
        return DataType.getAsDateTime(this.get(S_DoneDate));
  
  }
  public Timestamp getDoneDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_DoneDate));
      }

  public void initNotes(String value){
     this.initProperty(S_Notes,value);
  }
  public  void setNotes(String value){
     this.set(S_Notes,value);
  }
  public  void setNotesNull(){
     this.set(S_Notes,null);
  }

  public String getNotes(){
       return DataType.getAsString(this.get(S_Notes));
  
  }
  public String getNotesInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Notes));
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

