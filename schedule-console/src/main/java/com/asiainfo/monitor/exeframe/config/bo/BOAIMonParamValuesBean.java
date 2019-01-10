package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;

public class BOAIMonParamValuesBean extends DataContainer implements DataContainerInterface,IBOAIMonParamValuesValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamValues";



  public final static  String S_SortId = "SORT_ID";
  public final static  String S_ParamValue = "PARAM_VALUE";
  public final static  String S_RegisteId = "REGISTE_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ParamDesc = "PARAM_DESC";
  public final static  String S_RegisteType = "REGISTE_TYPE";
  public final static  String S_ParamId = "PARAM_ID";
  public final static  String S_ParamCode = "PARAM_CODE";
  public final static  String S_VId = "V_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonParamValuesBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initSortId(int value){
     this.initProperty(S_SortId,new Integer(value));
  }
  public  void setSortId(int value){
     this.set(S_SortId,new Integer(value));
  }
  public  void setSortIdNull(){
     this.set(S_SortId,null);
  }

  public int getSortId(){
        return DataType.getAsInt(this.get(S_SortId));
  
  }
  public int getSortIdInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_SortId));
      }

  public void initParamValue(String value){
     this.initProperty(S_ParamValue,value);
  }
  public  void setParamValue(String value){
     this.set(S_ParamValue,value);
  }
  public  void setParamValueNull(){
     this.set(S_ParamValue,null);
  }

  public String getParamValue(){
       return DataType.getAsString(this.get(S_ParamValue));
  
  }
  public String getParamValueInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ParamValue));
      }

  public void initRegisteId(long value){
     this.initProperty(S_RegisteId,new Long(value));
  }
  public  void setRegisteId(long value){
     this.set(S_RegisteId,new Long(value));
  }
  public  void setRegisteIdNull(){
     this.set(S_RegisteId,null);
  }

  public long getRegisteId(){
        return DataType.getAsLong(this.get(S_RegisteId));
  
  }
  public long getRegisteIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RegisteId));
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

  public void initParamDesc(String value){
     this.initProperty(S_ParamDesc,value);
  }
  public  void setParamDesc(String value){
     this.set(S_ParamDesc,value);
  }
  public  void setParamDescNull(){
     this.set(S_ParamDesc,null);
  }

  public String getParamDesc(){
       return DataType.getAsString(this.get(S_ParamDesc));
  
  }
  public String getParamDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ParamDesc));
      }

  public void initRegisteType(int value){
     this.initProperty(S_RegisteType,new Integer(value));
  }
  public  void setRegisteType(int value){
     this.set(S_RegisteType,new Integer(value));
  }
  public  void setRegisteTypeNull(){
     this.set(S_RegisteType,null);
  }

  public int getRegisteType(){
        return DataType.getAsInt(this.get(S_RegisteType));
  
  }
  public int getRegisteTypeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_RegisteType));
      }

  public void initParamId(long value){
     this.initProperty(S_ParamId,new Long(value));
  }
  public  void setParamId(long value){
     this.set(S_ParamId,new Long(value));
  }
  public  void setParamIdNull(){
     this.set(S_ParamId,null);
  }

  public long getParamId(){
        return DataType.getAsLong(this.get(S_ParamId));
  
  }
  public long getParamIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ParamId));
      }

  public void initParamCode(String value){
     this.initProperty(S_ParamCode,value);
  }
  public  void setParamCode(String value){
     this.set(S_ParamCode,value);
  }
  public  void setParamCodeNull(){
     this.set(S_ParamCode,null);
  }

  public String getParamCode(){
       return DataType.getAsString(this.get(S_ParamCode));
  
  }
  public String getParamCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ParamCode));
      }

  public void initVId(long value){
     this.initProperty(S_VId,new Long(value));
  }
  public  void setVId(long value){
     this.set(S_VId,new Long(value));
  }
  public  void setVIdNull(){
     this.set(S_VId,null);
  }

  public long getVId(){
        return DataType.getAsLong(this.get(S_VId));
  
  }
  public long getVIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_VId));
      }


 
 }

