package com.asiainfo.batchwriteoff.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.*;
import com.asiainfo.batchwriteoff.ivalues.IBOAmBatchWfThreadMonValue;

import java.sql.Timestamp;

public class BOAmBatchWfThreadMonBean extends DataContainer implements DataContainerInterface,IBOAmBatchWfThreadMonValue {

  private static String  m_boName = "com.asiainfo.crm.common.bo.BOAmBatchWfThreadMon";



  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_TotalHisAmount = "TOTAL_HIS_AMOUNT";
  public final static  String S_WfThreadId = "WF_THREAD_ID";
  public final static  String S_TotalBillAmount = "TOTAL_BILL_AMOUNT";
  public final static  String S_StateDate = "STATE_DATE";
  public final static  String S_AcctMode = "ACCT_MODE";
  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_StartDate = "START_DATE";
  public final static  String S_TotalCount = "TOTAL_COUNT";
  public final static  String S_TotalCurAmount = "TOTAL_CUR_AMOUNT";
  public final static  String S_WfProcessId = "WF_PROCESS_ID";
  public final static  String S_DealCount = "DEAL_COUNT";
  public final static  String S_TotalBalanceAmount = "TOTAL_BALANCE_AMOUNT";
  public final static  String S_BillingCycleId = "BILLING_CYCLE_ID";
  public final static  String S_BillDay = "BILL_DAY";
  public final static  String S_FinishDate = "FINISH_DATE";
  public final static  String S_WfFlowId = "WF_FLOW_ID";
  public final static  String S_ModeValue = "MODE_VALUE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAmBatchWfThreadMonBean() throws AIException {
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

  public void initTotalHisAmount(long value){
     this.initProperty(S_TotalHisAmount,new Long(value));
  }
  public  void setTotalHisAmount(long value){
     this.set(S_TotalHisAmount,new Long(value));
  }
  public  void setTotalHisAmountNull(){
     this.set(S_TotalHisAmount,null);
  }

  public long getTotalHisAmount(){
        return DataType.getAsLong(this.get(S_TotalHisAmount));
  
  }
  public long getTotalHisAmountInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TotalHisAmount));
      }

  public void initWfThreadId(String value){
     this.initProperty(S_WfThreadId,value);
  }
  public  void setWfThreadId(String value){
     this.set(S_WfThreadId,value);
  }
  public  void setWfThreadIdNull(){
     this.set(S_WfThreadId,null);
  }

  public String getWfThreadId(){
       return DataType.getAsString(this.get(S_WfThreadId));
  
  }
  public String getWfThreadIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_WfThreadId));
      }

  public void initTotalBillAmount(long value){
     this.initProperty(S_TotalBillAmount,new Long(value));
  }
  public  void setTotalBillAmount(long value){
     this.set(S_TotalBillAmount,new Long(value));
  }
  public  void setTotalBillAmountNull(){
     this.set(S_TotalBillAmount,null);
  }

  public long getTotalBillAmount(){
        return DataType.getAsLong(this.get(S_TotalBillAmount));
  
  }
  public long getTotalBillAmountInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TotalBillAmount));
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

  public void initAcctMode(int value){
     this.initProperty(S_AcctMode,new Integer(value));
  }
  public  void setAcctMode(int value){
     this.set(S_AcctMode,new Integer(value));
  }
  public  void setAcctModeNull(){
     this.set(S_AcctMode,null);
  }

  public int getAcctMode(){
        return DataType.getAsInt(this.get(S_AcctMode));
  
  }
  public int getAcctModeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_AcctMode));
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

  public void initTotalCount(long value){
     this.initProperty(S_TotalCount,new Long(value));
  }
  public  void setTotalCount(long value){
     this.set(S_TotalCount,new Long(value));
  }
  public  void setTotalCountNull(){
     this.set(S_TotalCount,null);
  }

  public long getTotalCount(){
        return DataType.getAsLong(this.get(S_TotalCount));
  
  }
  public long getTotalCountInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TotalCount));
      }

  public void initTotalCurAmount(long value){
     this.initProperty(S_TotalCurAmount,new Long(value));
  }
  public  void setTotalCurAmount(long value){
     this.set(S_TotalCurAmount,new Long(value));
  }
  public  void setTotalCurAmountNull(){
     this.set(S_TotalCurAmount,null);
  }

  public long getTotalCurAmount(){
        return DataType.getAsLong(this.get(S_TotalCurAmount));
  
  }
  public long getTotalCurAmountInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TotalCurAmount));
      }

  public void initWfProcessId(String value){
     this.initProperty(S_WfProcessId,value);
  }
  public  void setWfProcessId(String value){
     this.set(S_WfProcessId,value);
  }
  public  void setWfProcessIdNull(){
     this.set(S_WfProcessId,null);
  }

  public String getWfProcessId(){
       return DataType.getAsString(this.get(S_WfProcessId));
  
  }
  public String getWfProcessIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_WfProcessId));
      }

  public void initDealCount(long value){
     this.initProperty(S_DealCount,new Long(value));
  }
  public  void setDealCount(long value){
     this.set(S_DealCount,new Long(value));
  }
  public  void setDealCountNull(){
     this.set(S_DealCount,null);
  }

  public long getDealCount(){
        return DataType.getAsLong(this.get(S_DealCount));
  
  }
  public long getDealCountInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_DealCount));
      }

  public void initTotalBalanceAmount(long value){
     this.initProperty(S_TotalBalanceAmount,new Long(value));
  }
  public  void setTotalBalanceAmount(long value){
     this.set(S_TotalBalanceAmount,new Long(value));
  }
  public  void setTotalBalanceAmountNull(){
     this.set(S_TotalBalanceAmount,null);
  }

  public long getTotalBalanceAmount(){
        return DataType.getAsLong(this.get(S_TotalBalanceAmount));
  
  }
  public long getTotalBalanceAmountInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TotalBalanceAmount));
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

  public void initModeValue(int value){
     this.initProperty(S_ModeValue,new Integer(value));
  }
  public  void setModeValue(int value){
     this.set(S_ModeValue,new Integer(value));
  }
  public  void setModeValueNull(){
     this.set(S_ModeValue,null);
  }

  public int getModeValue(){
        return DataType.getAsInt(this.get(S_ModeValue));
  
  }
  public int getModeValueInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_ModeValue));
      }


 
 }

