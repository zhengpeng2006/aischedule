package com.asiainfo.batchwriteoff.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.*;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfParaValue;

import java.sql.Timestamp;

public class BOBsBatchWfParaBean extends DataContainer implements DataContainerInterface,IBOBsBatchWfParaValue {

  private static String  m_boName = "com.asiainfo.crm.common.bo.BOBsBatchWfPara";



  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_StateDate = "STATE_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ParaValue = "PARA_VALUE";
  public final static  String S_ParaCode = "PARA_CODE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOBsBatchWfParaBean() throws AIException {
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException {
   return S_TYPE;
 }

 public void setObjectType(ObjectType value) throws AIException {
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initRegionId(String value){
     this.initProperty(S_RegionId,value);
  }
  public  void setRegionId(String value){
     this.set(S_RegionId,value);
  }
  public  void setRegionIdNull(){
     this.set(S_RegionId,null);
  }

  public String getRegionId(){
       return DataType.getAsString(this.get(S_RegionId));
  
  }
  public String getRegionIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RegionId));
      }

  public void initStateDate(Timestamp value){
     this.initProperty(S_StateDate,value);
  }
  public  void setStateDate(Timestamp value){
     this.set(S_StateDate,value);
  }
  public  void setStateDateNull(){
     this.set(S_StateDate,null);
  }

  public Timestamp getStateDate(){
        return DataType.getAsDateTime(this.get(S_StateDate));
  
  }
  public Timestamp getStateDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_StateDate));
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

  public void initParaValue(String value){
     this.initProperty(S_ParaValue,value);
  }
  public  void setParaValue(String value){
     this.set(S_ParaValue,value);
  }
  public  void setParaValueNull(){
     this.set(S_ParaValue,null);
  }

  public String getParaValue(){
       return DataType.getAsString(this.get(S_ParaValue));
  
  }
  public String getParaValueInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ParaValue));
      }

  public void initParaCode(String value){
     this.initProperty(S_ParaCode,value);
  }
  public  void setParaCode(String value){
     this.set(S_ParaCode,value);
  }
  public  void setParaCodeNull(){
     this.set(S_ParaCode,null);
  }

  public String getParaCode(){
       return DataType.getAsString(this.get(S_ParaCode));
  
  }
  public String getParaCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ParaCode));
      }


 
 }

