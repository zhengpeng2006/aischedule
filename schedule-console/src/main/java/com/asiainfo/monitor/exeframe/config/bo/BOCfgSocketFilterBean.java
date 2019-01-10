package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgSocketFilterValue;

public class BOCfgSocketFilterBean extends DataContainer implements DataContainerInterface,IBOCfgSocketFilterValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOCfgSocketFilter";



  public final static  String S_State = "STATE";
  public final static  String S_FilterId = "FILTER_ID";
  public final static  String S_FilterClass = "FILTER_CLASS";
  public final static  String S_SortId = "SORT_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CfgSocketCode = "CFG_SOCKET_CODE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOCfgSocketFilterBean() throws AIException{
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

  public void initFilterId(long value){
     this.initProperty(S_FilterId,new Long(value));
  }
  public  void setFilterId(long value){
     this.set(S_FilterId,new Long(value));
  }
  public  void setFilterIdNull(){
     this.set(S_FilterId,null);
  }

  public long getFilterId(){
        return DataType.getAsLong(this.get(S_FilterId));
  
  }
  public long getFilterIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_FilterId));
      }

  public void initFilterClass(String value){
     this.initProperty(S_FilterClass,value);
  }
  public  void setFilterClass(String value){
     this.set(S_FilterClass,value);
  }
  public  void setFilterClassNull(){
     this.set(S_FilterClass,null);
  }

  public String getFilterClass(){
       return DataType.getAsString(this.get(S_FilterClass));
  
  }
  public String getFilterClassInitialValue(){
        return DataType.getAsString(this.getOldObj(S_FilterClass));
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

  public void initCfgSocketCode(String value){
     this.initProperty(S_CfgSocketCode,value);
  }
  public  void setCfgSocketCode(String value){
     this.set(S_CfgSocketCode,value);
  }
  public  void setCfgSocketCodeNull(){
     this.set(S_CfgSocketCode,null);
  }

  public String getCfgSocketCode(){
       return DataType.getAsString(this.get(S_CfgSocketCode));
  
  }
  public String getCfgSocketCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CfgSocketCode));
      }


 
 }

