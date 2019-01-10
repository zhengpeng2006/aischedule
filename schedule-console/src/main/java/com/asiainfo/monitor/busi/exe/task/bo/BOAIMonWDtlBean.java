package com.asiainfo.monitor.busi.exe.task.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWDtlValue;

public class BOAIMonWDtlBean extends DataContainer implements DataContainerInterface,IBOAIMonWDtlValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWDtl";



  public final static  String S_DtlId = "DTL_ID";
  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_PersonId = "PERSON_ID";
  public final static  String S_WarnLevel = "WARN_LEVEL";
  public final static  String S_InfoId = "INFO_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonWDtlBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initDtlId(long value){
     this.initProperty(S_DtlId,new Long(value));
  }
  public  void setDtlId(long value){
     this.set(S_DtlId,new Long(value));
  }
  public  void setDtlIdNull(){
     this.set(S_DtlId,null);
  }

  public long getDtlId(){
        return DataType.getAsLong(this.get(S_DtlId));
  
  }
  public long getDtlIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_DtlId));
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

  public void initPersonId(long value){
     this.initProperty(S_PersonId,new Long(value));
  }
  public  void setPersonId(long value){
     this.set(S_PersonId,new Long(value));
  }
  public  void setPersonIdNull(){
     this.set(S_PersonId,null);
  }

  public long getPersonId(){
        return DataType.getAsLong(this.get(S_PersonId));
  
  }
  public long getPersonIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_PersonId));
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


 
 }

