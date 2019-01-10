package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgTfMappingValue;

public class BOCfgTfMappingBean extends DataContainer implements DataContainerInterface,IBOCfgTfMappingValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgTfMapping";



  public final static  String S_State = "STATE";
  public final static  String S_TfColumnName = "TF_COLUMN_NAME";
  public final static  String S_SrcColumnName = "SRC_COLUMN_NAME";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_MappingId = "MAPPING_ID";
  public final static  String S_CfgTfDtlId = "CFG_TF_DTL_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgTfMappingBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
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

  public void initTfColumnName(String value){
     this.initProperty(S_TfColumnName,value);
  }
  public  void setTfColumnName(String value){
     this.set(S_TfColumnName,value);
  }
  public  void setTfColumnNameNull(){
     this.set(S_TfColumnName,null);
  }

  public String getTfColumnName(){
       return DataType.getAsString(this.get(S_TfColumnName));
  
  }
  public String getTfColumnNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TfColumnName));
      }

  public void initSrcColumnName(String value){
     this.initProperty(S_SrcColumnName,value);
  }
  public  void setSrcColumnName(String value){
     this.set(S_SrcColumnName,value);
  }
  public  void setSrcColumnNameNull(){
     this.set(S_SrcColumnName,null);
  }

  public String getSrcColumnName(){
       return DataType.getAsString(this.get(S_SrcColumnName));
  
  }
  public String getSrcColumnNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SrcColumnName));
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

  public void initMappingId(long value){
     this.initProperty(S_MappingId,new Long(value));
  }
  public  void setMappingId(long value){
     this.set(S_MappingId,new Long(value));
  }
  public  void setMappingIdNull(){
     this.set(S_MappingId,null);
  }

  public long getMappingId(){
        return DataType.getAsLong(this.get(S_MappingId));
  
  }
  public long getMappingIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_MappingId));
      }

  public void initCfgTfDtlId(long value){
     this.initProperty(S_CfgTfDtlId,new Long(value));
  }
  public  void setCfgTfDtlId(long value){
     this.set(S_CfgTfDtlId,new Long(value));
  }
  public  void setCfgTfDtlIdNull(){
     this.set(S_CfgTfDtlId,null);
  }

  public long getCfgTfDtlId(){
        return DataType.getAsLong(this.get(S_CfgTfDtlId));
  
  }
  public long getCfgTfDtlIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_CfgTfDtlId));
      }


 
 }

