package com.asiainfo.batchwriteoff.ivalues;

import com.ai.appframe2.common.DataStructInterface;

import java.sql.Timestamp;

public interface IBOBsBatchWfFlowMonValue extends DataStructInterface {

  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_StateDate = "STATE_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_BillingCycleId = "BILLING_CYCLE_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_StartDate = "START_DATE";
  public final static  String S_BillDay = "BILL_DAY";
  public final static  String S_FinishDate = "FINISH_DATE";
  public final static  String S_WfFlowId = "WF_FLOW_ID";


public String getRegionId();

public Timestamp getStateDate();

public String getState();

public int getBillingCycleId();

public String getRemarks();

public Timestamp getStartDate();

public int getBillDay();

public Timestamp getFinishDate();

public long getWfFlowId();


public  void setRegionId(String value);

public  void setStateDate(Timestamp value);

public  void setState(String value);

public  void setBillingCycleId(int value);

public  void setRemarks(String value);

public  void setStartDate(Timestamp value);

public  void setBillDay(int value);

public  void setFinishDate(Timestamp value);

public  void setWfFlowId(long value);
}
