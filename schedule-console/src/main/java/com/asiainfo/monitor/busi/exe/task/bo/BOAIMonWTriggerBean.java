package com.asiainfo.monitor.busi.exe.task.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWTriggerValue;

public class BOAIMonWTriggerBean extends DataContainer implements DataContainerInterface,IBOAIMonWTriggerValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWTrigger";



  public final static  String S_State = "STATE";
  public final static  String S_InfoName = "INFO_NAME";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_ExpiryDate = "EXPIRY_DATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_Ip = "IP";
  public final static  String S_TriggerId = "TRIGGER_ID";
  public final static  String S_InfoId = "INFO_ID";
  public final static  String S_Layer = "LAYER";
  public final static  String S_WarnLevel = "WARN_LEVEL";
  public final static  String S_Content = "CONTENT";
  public final static  String S_RecordId = "RECORD_ID";
  public final static  String S_Phonenum = "PHONENUM";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonWTriggerBean() throws AIException{
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

  public void initInfoName(String value){
     this.initProperty(S_InfoName,value);
  }
  public  void setInfoName(String value){
     this.set(S_InfoName,value);
  }
  public  void setInfoNameNull(){
     this.set(S_InfoName,null);
  }

  public String getInfoName(){
       return DataType.getAsString(this.get(S_InfoName));
  
  }
  public String getInfoNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_InfoName));
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

  public void initExpiryDate(Timestamp value){
     this.initProperty(S_ExpiryDate,value);
  }
  public  void setExpiryDate(Timestamp value){
     this.set(S_ExpiryDate,value);
  }
  public  void setExpiryDateNull(){
     this.set(S_ExpiryDate,null);
  }

  public Timestamp getExpiryDate(){
        return DataType.getAsDateTime(this.get(S_ExpiryDate));
  
  }
  public Timestamp getExpiryDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_ExpiryDate));
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

  public void initIp(String value){
     this.initProperty(S_Ip,value);
  }
  public  void setIp(String value){
     this.set(S_Ip,value);
  }
  public  void setIpNull(){
     this.set(S_Ip,null);
  }

  public String getIp(){
       return DataType.getAsString(this.get(S_Ip));
  
  }
  public String getIpInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Ip));
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

  public void initLayer(String value){
     this.initProperty(S_Layer,value);
  }
  public  void setLayer(String value){
     this.set(S_Layer,value);
  }
  public  void setLayerNull(){
     this.set(S_Layer,null);
  }

  public String getLayer(){
       return DataType.getAsString(this.get(S_Layer));
  
  }
  public String getLayerInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Layer));
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

  public void initContent(String value){
     this.initProperty(S_Content,value);
  }
  public  void setContent(String value){
     this.set(S_Content,value);
  }
  public  void setContentNull(){
     this.set(S_Content,null);
  }

  public String getContent(){
       return DataType.getAsString(this.get(S_Content));
  
  }
  public String getContentInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Content));
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

  public void initPhonenum(String value){
     this.initProperty(S_Phonenum,value);
  }
  public  void setPhonenum(String value){
     this.set(S_Phonenum,value);
  }
  public  void setPhonenumNull(){
     this.set(S_Phonenum,null);
  }

  public String getPhonenum(){
       return DataType.getAsString(this.get(S_Phonenum));
  
  }
  public String getPhonenumInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Phonenum));
      }


 
 }

