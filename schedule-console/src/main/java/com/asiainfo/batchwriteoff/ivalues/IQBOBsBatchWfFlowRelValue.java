package com.asiainfo.batchwriteoff.ivalues;

import com.ai.appframe2.common.DataStructInterface;

import java.sql.Timestamp;

public interface IQBOBsBatchWfFlowRelValue extends DataStructInterface {

  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_RelRegionId = "REL_REGION_ID";
  public final static  String S_WfFlowDesc = "WF_FLOW_DESC";
  public final static  String S_IsDtl = "IS_DTL";
  public final static  String S_StateDate = "STATE_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_PreWfFlowId = "PRE_WF_FLOW_ID";
  public final static  String S_WfFlowName = "WF_FLOW_NAME";
  public final static  String S_DtlUrl = "DTL_URL";
  public final static  String S_TimeGroupId = "TIME_GROUP_ID";
  public final static  String S_PreWfFlowState = "PRE_WF_FLOW_STATE";
  public final static  String S_BillDay = "BILL_DAY";
  public final static  String S_IsDiscontinue = "IS_DISCONTINUE";
  public final static  String S_RunMode = "RUN_MODE";
  public final static  String S_BusinessClass = "BUSINESS_CLASS";
  public final static  String S_WfFlowId = "WF_FLOW_ID";


public String getRegionId();

public String getRelRegionId();

public String getWfFlowDesc();

public String getIsDtl();

public Timestamp getStateDate();

public String getState();

public long getPreWfFlowId();

public String getWfFlowName();

public String getDtlUrl();

public long getTimeGroupId();

public String getPreWfFlowState();

public long getBillDay();

public String getIsDiscontinue();

public String getRunMode();

public String getBusinessClass();

public long getWfFlowId();


public  void setRegionId(String value);

public  void setRelRegionId(String value);

public  void setWfFlowDesc(String value);

public  void setIsDtl(String value);

public  void setStateDate(Timestamp value);

public  void setState(String value);

public  void setPreWfFlowId(long value);

public  void setWfFlowName(String value);

public  void setDtlUrl(String value);

public  void setTimeGroupId(long value);

public  void setPreWfFlowState(String value);

public  void setBillDay(long value);

public  void setIsDiscontinue(String value);

public  void setRunMode(String value);

public  void setBusinessClass(String value);

public  void setWfFlowId(long value);
}
