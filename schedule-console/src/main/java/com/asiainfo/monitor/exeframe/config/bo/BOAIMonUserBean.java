package com.asiainfo.monitor.exeframe.config.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserValue;

public class BOAIMonUserBean extends DataContainer implements DataContainerInterface,IBOAIMonUserValue{

    private static String m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonUser";



  public final static  String S_State = "STATE";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_Notes = "NOTES";
  public final static  String S_TryTimes = "TRY_TIMES";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_AllowChgPass = "ALLOW_CHG_PASS";
  public final static  String S_UserCode = "USER_CODE";
  public final static  String S_ExpireDate = "EXPIRE_DATE";
  public final static  String S_OpId = "OP_ID";
  public final static  String S_LockFlag = "LOCK_FLAG";
  public final static  String S_MultiLoginFlag = "MULTI_LOGIN_FLAG";
  public final static  String S_UserId = "USER_ID";
  public final static  String S_EffectDate = "EFFECT_DATE";
  public final static  String S_UserPass = "USER_PASS";
  public final static  String S_UserName = "USER_NAME";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonUserBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
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

  public void initTryTimes(int value){
     this.initProperty(S_TryTimes,new Integer(value));
  }
  public  void setTryTimes(int value){
     this.set(S_TryTimes,new Integer(value));
  }
  public  void setTryTimesNull(){
     this.set(S_TryTimes,null);
  }

  public int getTryTimes(){
        return DataType.getAsInt(this.get(S_TryTimes));
  
  }
  public int getTryTimesInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_TryTimes));
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

  public void initAllowChgPass(int value){
     this.initProperty(S_AllowChgPass,new Integer(value));
  }
  public  void setAllowChgPass(int value){
     this.set(S_AllowChgPass,new Integer(value));
  }
  public  void setAllowChgPassNull(){
     this.set(S_AllowChgPass,null);
  }

  public int getAllowChgPass(){
        return DataType.getAsInt(this.get(S_AllowChgPass));
  
  }
  public int getAllowChgPassInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_AllowChgPass));
      }

  public void initUserCode(String value){
     this.initProperty(S_UserCode,value);
  }
  public  void setUserCode(String value){
     this.set(S_UserCode,value);
  }
  public  void setUserCodeNull(){
     this.set(S_UserCode,null);
  }

  public String getUserCode(){
       return DataType.getAsString(this.get(S_UserCode));
  
  }
  public String getUserCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_UserCode));
      }

  public void initExpireDate(Timestamp value){
     this.initProperty(S_ExpireDate,value);
  }
  public  void setExpireDate(Timestamp value){
     this.set(S_ExpireDate,value);
  }
  public  void setExpireDateNull(){
     this.set(S_ExpireDate,null);
  }

  public Timestamp getExpireDate(){
        return DataType.getAsDateTime(this.get(S_ExpireDate));
  
  }
  public Timestamp getExpireDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_ExpireDate));
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

  public void initLockFlag(int value){
     this.initProperty(S_LockFlag,new Integer(value));
  }
  public  void setLockFlag(int value){
     this.set(S_LockFlag,new Integer(value));
  }
  public  void setLockFlagNull(){
     this.set(S_LockFlag,null);
  }

  public int getLockFlag(){
        return DataType.getAsInt(this.get(S_LockFlag));
  
  }
  public int getLockFlagInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_LockFlag));
      }

  public void initMultiLoginFlag(int value){
     this.initProperty(S_MultiLoginFlag,new Integer(value));
  }
  public  void setMultiLoginFlag(int value){
     this.set(S_MultiLoginFlag,new Integer(value));
  }
  public  void setMultiLoginFlagNull(){
     this.set(S_MultiLoginFlag,null);
  }

  public int getMultiLoginFlag(){
        return DataType.getAsInt(this.get(S_MultiLoginFlag));
  
  }
  public int getMultiLoginFlagInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_MultiLoginFlag));
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

  public void initEffectDate(Timestamp value){
     this.initProperty(S_EffectDate,value);
  }
  public  void setEffectDate(Timestamp value){
     this.set(S_EffectDate,value);
  }
  public  void setEffectDateNull(){
     this.set(S_EffectDate,null);
  }

  public Timestamp getEffectDate(){
        return DataType.getAsDateTime(this.get(S_EffectDate));
  
  }
  public Timestamp getEffectDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_EffectDate));
      }

  public void initUserPass(String value){
     this.initProperty(S_UserPass,value);
  }
  public  void setUserPass(String value){
     this.set(S_UserPass,value);
  }
  public  void setUserPassNull(){
     this.set(S_UserPass,null);
  }

  public String getUserPass(){
       return DataType.getAsString(this.get(S_UserPass));
  
  }
  public String getUserPassInitialValue(){
        return DataType.getAsString(this.getOldObj(S_UserPass));
      }

  public void initUserName(String value){
     this.initProperty(S_UserName,value);
  }
  public  void setUserName(String value){
     this.set(S_UserName,value);
  }
  public  void setUserNameNull(){
     this.set(S_UserName,null);
  }

  public String getUserName(){
       return DataType.getAsString(this.get(S_UserName));
  
  }
  public String getUserNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_UserName));
      }


 
 }

