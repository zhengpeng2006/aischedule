package com.asiainfo.monitor.busi.exe.task.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonLRecordValue;

public class BOAIMonLRecordBean extends DataContainer implements DataContainerInterface,IBOAIMonLRecordValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonLRecord";



  public final static  String S_ExpireDate = "EXPIRE_DATE";
  public final static  String S_OpId = "OP_ID";
  public final static  String S_Layer = "LAYER";
  public final static  String S_GroupId = "GROUP_ID";
  public final static  String S_InfoCode = "INFO_CODE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_IsTriggerWarn = "IS_TRIGGER_WARN";
  public final static  String S_Ip = "IP";
  public final static  String S_HostName = "HOST_NAME";
  public final static  String S_OrgId = "ORG_ID";
  public final static  String S_InfoId = "INFO_ID";
  public final static  String S_RecordId = "RECORD_ID";
  public final static  String S_BatchId = "BATCH_ID";
  public final static  String S_ValidDate = "VALID_DATE";
  public final static  String S_MonType = "MON_TYPE";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_InfoName = "INFO_NAME";
  public final static  String S_InfoValue = "INFO_VALUE";
  public final static  String S_FromType = "FROM_TYPE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_DoneCode = "DONE_CODE";
  public final static  String S_WarnLevel = "WARN_LEVEL";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonLRecordBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
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

  public void initGroupId(String value){
     this.initProperty(S_GroupId,value);
  }
  public  void setGroupId(String value){
     this.set(S_GroupId,value);
  }
  public  void setGroupIdNull(){
     this.set(S_GroupId,null);
  }

  public String getGroupId(){
       return DataType.getAsString(this.get(S_GroupId));
  
  }
  public String getGroupIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_GroupId));
      }

  public void initInfoCode(String value){
     this.initProperty(S_InfoCode,value);
  }
  public  void setInfoCode(String value){
     this.set(S_InfoCode,value);
  }
  public  void setInfoCodeNull(){
     this.set(S_InfoCode,null);
  }

  public String getInfoCode(){
       return DataType.getAsString(this.get(S_InfoCode));
  
  }
  public String getInfoCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_InfoCode));
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

  public void initIsTriggerWarn(String value){
     this.initProperty(S_IsTriggerWarn,value);
  }
  public  void setIsTriggerWarn(String value){
     this.set(S_IsTriggerWarn,value);
  }
  public  void setIsTriggerWarnNull(){
     this.set(S_IsTriggerWarn,null);
  }

  public String getIsTriggerWarn(){
       return DataType.getAsString(this.get(S_IsTriggerWarn));
  
  }
  public String getIsTriggerWarnInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IsTriggerWarn));
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

  public void initHostName(String value){
     this.initProperty(S_HostName,value);
  }
  public  void setHostName(String value){
     this.set(S_HostName,value);
  }
  public  void setHostNameNull(){
     this.set(S_HostName,null);
  }

  public String getHostName(){
       return DataType.getAsString(this.get(S_HostName));
  
  }
  public String getHostNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_HostName));
      }

  public void initOrgId(long value){
     this.initProperty(S_OrgId,new Long(value));
  }
  public  void setOrgId(long value){
     this.set(S_OrgId,new Long(value));
  }
  public  void setOrgIdNull(){
     this.set(S_OrgId,null);
  }

  public long getOrgId(){
        return DataType.getAsLong(this.get(S_OrgId));
  
  }
  public long getOrgIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_OrgId));
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

  public void initBatchId(long value){
     this.initProperty(S_BatchId,new Long(value));
  }
  public  void setBatchId(long value){
     this.set(S_BatchId,new Long(value));
  }
  public  void setBatchIdNull(){
     this.set(S_BatchId,null);
  }

  public long getBatchId(){
        return DataType.getAsLong(this.get(S_BatchId));
  
  }
  public long getBatchIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_BatchId));
      }

  public void initValidDate(Timestamp value){
     this.initProperty(S_ValidDate,value);
  }
  public  void setValidDate(Timestamp value){
     this.set(S_ValidDate,value);
  }
  public  void setValidDateNull(){
     this.set(S_ValidDate,null);
  }

  public Timestamp getValidDate(){
        return DataType.getAsDateTime(this.get(S_ValidDate));
  
  }
  public Timestamp getValidDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_ValidDate));
      }

  public void initMonType(String value){
     this.initProperty(S_MonType,value);
  }
  public  void setMonType(String value){
     this.set(S_MonType,value);
  }
  public  void setMonTypeNull(){
     this.set(S_MonType,null);
  }

  public String getMonType(){
       return DataType.getAsString(this.get(S_MonType));
  
  }
  public String getMonTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MonType));
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

  public void initInfoValue(String value){
     this.initProperty(S_InfoValue,value);
  }
  public  void setInfoValue(String value){
     this.set(S_InfoValue,value);
  }
  public  void setInfoValueNull(){
     this.set(S_InfoValue,null);
  }

  public String getInfoValue(){
       return DataType.getAsString(this.get(S_InfoValue));
  
  }
  public String getInfoValueInitialValue(){
        return DataType.getAsString(this.getOldObj(S_InfoValue));
      }

  public void initFromType(String value){
     this.initProperty(S_FromType,value);
  }
  public  void setFromType(String value){
     this.set(S_FromType,value);
  }
  public  void setFromTypeNull(){
     this.set(S_FromType,null);
  }

  public String getFromType(){
       return DataType.getAsString(this.get(S_FromType));
  
  }
  public String getFromTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_FromType));
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

  public void initDoneCode(long value){
     this.initProperty(S_DoneCode,new Long(value));
  }
  public  void setDoneCode(long value){
     this.set(S_DoneCode,new Long(value));
  }
  public  void setDoneCodeNull(){
     this.set(S_DoneCode,null);
  }

  public long getDoneCode(){
        return DataType.getAsLong(this.get(S_DoneCode));
  
  }
  public long getDoneCodeInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_DoneCode));
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

