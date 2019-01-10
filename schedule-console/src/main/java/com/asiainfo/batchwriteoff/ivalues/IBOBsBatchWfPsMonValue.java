package com.asiainfo.batchwriteoff.ivalues;

import com.ai.appframe2.common.DataStructInterface;

import java.sql.Timestamp;

public interface IBOBsBatchWfPsMonValue extends DataStructInterface {

  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_TotalHisAmount = "TOTAL_HIS_AMOUNT";
  public final static  String S_TotalBillAmount = "TOTAL_BILL_AMOUNT";
  public final static  String S_StateDate = "STATE_DATE";
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


public String getRegionId();

public long getTotalHisAmount();

public long getTotalBillAmount();

public Timestamp getStateDate();

public String getState();

public String getRemarks();

public Timestamp getStartDate();

public long getTotalCount();

public long getTotalCurAmount();

public String getWfProcessId();

public long getDealCount();

public long getTotalBalanceAmount();

public int getBillingCycleId();

public int getBillDay();

public Timestamp getFinishDate();

public long getWfFlowId();


public  void setRegionId(String value);

public  void setTotalHisAmount(long value);

public  void setTotalBillAmount(long value);

public  void setStateDate(Timestamp value);

public  void setState(String value);

public  void setRemarks(String value);

public  void setStartDate(Timestamp value);

public  void setTotalCount(long value);

public  void setTotalCurAmount(long value);

public  void setWfProcessId(String value);

public  void setDealCount(long value);

public  void setTotalBalanceAmount(long value);

public  void setBillingCycleId(int value);

public  void setBillDay(int value);

public  void setFinishDate(Timestamp value);

public  void setWfFlowId(long value);
}
