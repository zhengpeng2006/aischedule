package com.asiainfo.monitor.busi.exe.task.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWSmsValue;

public class BOAIMonWSmsBean extends DataContainer implements DataContainerInterface,IBOAIMonWSmsValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWSms";



  public final static  String S_SmsContent = "SMS_CONTENT";
  public final static  String S_SendDate = "SEND_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_PhoneNum = "PHONE_NUM";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_TriggerId = "TRIGGER_ID";
  public final static  String S_InfoId = "INFO_ID";
  public final static  String S_Exceptions = "EXCEPTIONS";
  public final static  String S_RecordId = "RECORD_ID";
  public final static  String S_SmsId = "SMS_ID";
  public final static  String S_SendCount = "SEND_COUNT";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_WarnLevel = "WARN_LEVEL";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonWSmsBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initSmsContent(String value){
     this.initProperty(S_SmsContent,value);
  }
  public  void setSmsContent(String value){
     this.set(S_SmsContent,value);
  }
  public  void setSmsContentNull(){
     this.set(S_SmsContent,null);
  }

  public String getSmsContent(){
       return DataType.getAsString(this.get(S_SmsContent));
  
  }
  public String getSmsContentInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SmsContent));
      }

  public void initSendDate(Timestamp value){
     this.initProperty(S_SendDate,value);
  }
  public  void setSendDate(Timestamp value){
     this.set(S_SendDate,value);
  }
  public  void setSendDateNull(){
     this.set(S_SendDate,null);
  }

  public Timestamp getSendDate(){
        return DataType.getAsDateTime(this.get(S_SendDate));
  
  }
  public Timestamp getSendDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_SendDate));
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

  public void initPhoneNum(String value){
     this.initProperty(S_PhoneNum,value);
  }
  public  void setPhoneNum(String value){
     this.set(S_PhoneNum,value);
  }
  public  void setPhoneNumNull(){
     this.set(S_PhoneNum,null);
  }

  public String getPhoneNum(){
       return DataType.getAsString(this.get(S_PhoneNum));
  
  }
  public String getPhoneNumInitialValue(){
        return DataType.getAsString(this.getOldObj(S_PhoneNum));
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

  public void initTriggerId(long value){
     this.initProperty(S_TriggerId,new Long(value));
  }
  public  void setTriggerId(long value){
     this.set(S_TriggerId,new Long(value));
  }
  public  void setTriggerIdNull(){
     this.set(S_TriggerId,null);
  }

  public long getTriggerId(){
        return DataType.getAsLong(this.get(S_TriggerId));
  
  }
  public long getTriggerIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TriggerId));
      }

  public void initInfoId(long value){
     this.initProperty(S_InfoId,new Long(value));
  }
  public  void setInfoId(long value){
     this.set(S_InfoId,new Long(value));
  }
  public  void setInfoIdNull(){
     this.set(S_InfoId,null);
  }

  public long getInfoId(){
        return DataType.getAsLong(this.get(S_InfoId));
  
  }
  public long getInfoIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_InfoId));
      }

  public void initExceptions(String value){
     this.initProperty(S_Exceptions,value);
  }
  public  void setExceptions(String value){
     this.set(S_Exceptions,value);
  }
  public  void setExceptionsNull(){
     this.set(S_Exceptions,null);
  }

  public String getExceptions(){
       return DataType.getAsString(this.get(S_Exceptions));
  
  }
  public String getExceptionsInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Exceptions));
      }

  public void initRecordId(long value){
     this.initProperty(S_RecordId,new Long(value));
  }
  public  void setRecordId(long value){
     this.set(S_RecordId,new Long(value));
  }
  public  void setRecordIdNull(){
     this.set(S_RecordId,null);
  }

  public long getRecordId(){
        return DataType.getAsLong(this.get(S_RecordId));
  
  }
  public long getRecordIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RecordId));
      }

  public void initSmsId(long value){
     this.initProperty(S_SmsId,new Long(value));
  }
  public  void setSmsId(long value){
     this.set(S_SmsId,new Long(value));
  }
  public  void setSmsIdNull(){
     this.set(S_SmsId,null);
  }

  public long getSmsId(){
        return DataType.getAsLong(this.get(S_SmsId));
  
  }
  public long getSmsIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_SmsId));
      }

  public void initSendCount(int value){
     this.initProperty(S_SendCount,new Integer(value));
  }
  public  void setSendCount(int value){
     this.set(S_SendCount,new Integer(value));
  }
  public  void setSendCountNull(){
     this.set(S_SendCount,null);
  }

  public int getSendCount(){
        return DataType.getAsInt(this.get(S_SendCount));
  
  }
  public int getSendCountInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_SendCount));
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

  public void initWarnLevel(String value){
     this.initProperty(S_WarnLevel,value);
  }
  public  void setWarnLevel(String value){
     this.set(S_WarnLevel,value);
  }
  public  void setWarnLevelNull(){
     this.set(S_WarnLevel,null);
  }

  public String getWarnLevel(){
       return DataType.getAsString(this.get(S_WarnLevel));
  
  }
  public String getWarnLevelInitialValue(){
        return DataType.getAsString(this.getOldObj(S_WarnLevel));
      }


 
 }

