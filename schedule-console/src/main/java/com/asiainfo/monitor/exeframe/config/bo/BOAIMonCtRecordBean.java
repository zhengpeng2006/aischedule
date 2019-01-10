package com.asiainfo.monitor.exeframe.config.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCtRecordValue;

public class BOAIMonCtRecordBean extends DataContainer implements DataContainerInterface,IBOAIMonCtRecordValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonCtRecord";



  public final static  String S_CtType = "CT_TYPE";
  public final static  String S_InvalidDuti = "INVALID_DUTI";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CtObjtype = "CT_OBJTYPE";
  public final static  String S_CtObjname = "CT_OBJNAME";
  public final static  String S_InvalidDate = "INVALID_DATE";
  public final static  String S_CtDate = "CT_DATE";
  public final static  String S_CtActor = "CT_ACTOR";
  public final static  String S_EffectDate = "EFFECT_DATE";
  public final static  String S_CtFlag = "CT_FLAG";
  public final static  String S_CtId = "CT_ID";
  public final static  String S_CtObjid = "CT_OBJID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonCtRecordBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initCtType(int value){
     this.initProperty(S_CtType,new Integer(value));
  }
  public  void setCtType(int value){
     this.set(S_CtType,new Integer(value));
  }
  public  void setCtTypeNull(){
     this.set(S_CtType,null);
  }

  public int getCtType(){
        return DataType.getAsInt(this.get(S_CtType));
  
  }
  public int getCtTypeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_CtType));
      }

  public void initInvalidDuti(long value){
     this.initProperty(S_InvalidDuti,new Long(value));
  }
  public  void setInvalidDuti(long value){
     this.set(S_InvalidDuti,new Long(value));
  }
  public  void setInvalidDutiNull(){
     this.set(S_InvalidDuti,null);
  }

  public long getInvalidDuti(){
        return DataType.getAsLong(this.get(S_InvalidDuti));
  
  }
  public long getInvalidDutiInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_InvalidDuti));
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

  public void initCtObjtype(int value){
     this.initProperty(S_CtObjtype,new Integer(value));
  }
  public  void setCtObjtype(int value){
     this.set(S_CtObjtype,new Integer(value));
  }
  public  void setCtObjtypeNull(){
     this.set(S_CtObjtype,null);
  }

  public int getCtObjtype(){
        return DataType.getAsInt(this.get(S_CtObjtype));
  
  }
  public int getCtObjtypeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_CtObjtype));
      }

  public void initCtObjname(String value){
     this.initProperty(S_CtObjname,value);
  }
  public  void setCtObjname(String value){
     this.set(S_CtObjname,value);
  }
  public  void setCtObjnameNull(){
     this.set(S_CtObjname,null);
  }

  public String getCtObjname(){
       return DataType.getAsString(this.get(S_CtObjname));
  
  }
  public String getCtObjnameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CtObjname));
      }

  public void initInvalidDate(Timestamp value){
     this.initProperty(S_InvalidDate,value);
  }
  public  void setInvalidDate(Timestamp value){
     this.set(S_InvalidDate,value);
  }
  public  void setInvalidDateNull(){
     this.set(S_InvalidDate,null);
  }

  public Timestamp getInvalidDate(){
        return DataType.getAsDateTime(this.get(S_InvalidDate));
  
  }
  public Timestamp getInvalidDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_InvalidDate));
      }

  public void initCtDate(Timestamp value){
     this.initProperty(S_CtDate,value);
  }
  public  void setCtDate(Timestamp value){
     this.set(S_CtDate,value);
  }
  public  void setCtDateNull(){
     this.set(S_CtDate,null);
  }

  public Timestamp getCtDate(){
        return DataType.getAsDateTime(this.get(S_CtDate));
  
  }
  public Timestamp getCtDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_CtDate));
      }

  public void initCtActor(String value){
     this.initProperty(S_CtActor,value);
  }
  public  void setCtActor(String value){
     this.set(S_CtActor,value);
  }
  public  void setCtActorNull(){
     this.set(S_CtActor,null);
  }

  public String getCtActor(){
       return DataType.getAsString(this.get(S_CtActor));
  
  }
  public String getCtActorInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CtActor));
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

  public void initCtFlag(int value){
     this.initProperty(S_CtFlag,new Integer(value));
  }
  public  void setCtFlag(int value){
     this.set(S_CtFlag,new Integer(value));
  }
  public  void setCtFlagNull(){
     this.set(S_CtFlag,null);
  }

  public int getCtFlag(){
        return DataType.getAsInt(this.get(S_CtFlag));
  
  }
  public int getCtFlagInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_CtFlag));
      }

  public void initCtId(long value){
     this.initProperty(S_CtId,new Long(value));
  }
  public  void setCtId(long value){
     this.set(S_CtId,new Long(value));
  }
  public  void setCtIdNull(){
     this.set(S_CtId,null);
  }

  public long getCtId(){
        return DataType.getAsLong(this.get(S_CtId));
  
  }
  public long getCtIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_CtId));
      }

  public void initCtObjid(long value){
     this.initProperty(S_CtObjid,new Long(value));
  }
  public  void setCtObjid(long value){
     this.set(S_CtObjid,new Long(value));
  }
  public  void setCtObjidNull(){
     this.set(S_CtObjid,null);
  }

  public long getCtObjid(){
        return DataType.getAsLong(this.get(S_CtObjid));
  
  }
  public long getCtObjidInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_CtObjid));
      }


 
 }

