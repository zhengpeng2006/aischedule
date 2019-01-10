package com.asiainfo.batchwriteoff.ivalues;

import com.ai.appframe2.common.DataStructInterface;

import java.sql.Timestamp;

public interface IBOBsBillingCycleValue extends DataStructInterface {

  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_CtrlFlag = "CTRL_FLAG";
  public final static  String S_CtrlDate = "CTRL_DATE";
  public final static  String S_StateDate = "STATE_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_BillingCycleId = "BILLING_CYCLE_ID";
  public final static  String S_BillingCycleTypeId = "BILLING_CYCLE_TYPE_ID";


public String getRegionId();

public String getCtrlFlag();

public Timestamp getCtrlDate();

public Timestamp getStateDate();

public String getState();

public long getBillingCycleId();

public int getBillingCycleTypeId();


public  void setRegionId(String value);

public  void setCtrlFlag(String value);

public  void setCtrlDate(Timestamp value);

public  void setStateDate(Timestamp value);

public  void setState(String value);

public  void setBillingCycleId(long value);

public  void setBillingCycleTypeId(int value);
}
