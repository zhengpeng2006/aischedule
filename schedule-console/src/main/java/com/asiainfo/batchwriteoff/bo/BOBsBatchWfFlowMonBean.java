package com.asiainfo.batchwriteoff.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.*;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfFlowMonValue;

import java.sql.Timestamp;

public class BOBsBatchWfFlowMonBean extends DataContainer implements DataContainerInterface,IBOBsBatchWfFlowMonValue {

  private static String  m_boName = "com.asiainfo.crm.common.bo.BOBsBatchWfFlowMon";



  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_StateDate = "STATE_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_BillingCycleId = "BILLING_CYCLE_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_StartDate = "START_DATE";
  public final static  String S_BillDay = "BILL_DAY";
  public final static  String S_FinishDate = "FINISH_DATE";
  public final static  String S_WfFlowId = "WF_FLOW_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOBsBatchWfFlowMonBean() throws AIException {
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

  public void initBillingCycleId(int value){
     this.initProperty(S_BillingCycleId,new Integer(value));
  }
  public  void setBillingCycleId(int value){
     this.set(S_BillingCycleId,new Integer(value));
  }
  public  void setBillingCycleIdNull(){
     this.set(S_BillingCycleId,null);
  }

  public int getBillingCycleId(){
        return DataType.getAsInt(this.get(S_BillingCycleId));
  
  }
  public int getBillingCycleIdInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_BillingCycleId));
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

  public void initStartDate(Timestamp value){
     this.initProperty(S_StartDate,value);
  }
  public  void setStartDate(Timestamp value){
     this.set(S_StartDate,value);
  }
  public  void setStartDateNull(){
     this.set(S_StartDate,null);
  }

  public Timestamp getStartDate(){
        return DataType.getAsDateTime(this.get(S_StartDate));
  
  }
  public Timestamp getStartDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_StartDate));
      }

  public void initBillDay(int value){
     this.initProperty(S_BillDay,new Integer(value));
  }
  public  void setBillDay(int value){
     this.set(S_BillDay,new Integer(value));
  }
  public  void setBillDayNull(){
     this.set(S_BillDay,null);
  }

  public int getBillDay(){
        return DataType.getAsInt(this.get(S_BillDay));
  
  }
  public int getBillDayInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_BillDay));
      }

  public void initFinishDate(Timestamp value){
     this.initProperty(S_FinishDate,value);
  }
  public  void setFinishDate(Timestamp value){
     this.set(S_FinishDate,value);
  }
  public  void setFinishDateNull(){
     this.set(S_FinishDate,null);
  }

  public Timestamp getFinishDate(){
        return DataType.getAsDateTime(this.get(S_FinishDate));
  
  }
  public Timestamp getFinishDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_FinishDate));
      }

  public void initWfFlowId(long value){
     this.initProperty(S_WfFlowId,new Long(value));
  }
  public  void setWfFlowId(long value){
     this.set(S_WfFlowId,new Long(value));
  }
  public  void setWfFlowIdNull(){
     this.set(S_WfFlowId,null);
  }

  public long getWfFlowId(){
        return DataType.getAsLong(this.get(S_WfFlowId));
  
  }
  public long getWfFlowIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_WfFlowId));
      }


 
 }

