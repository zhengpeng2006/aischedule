package com.asiainfo.batchwriteoff.ivalues;

import com.ai.appframe2.common.DataStructInterface;

import java.sql.Timestamp;

public interface IBOBsBatchWfParaValue extends DataStructInterface {

  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_StateDate = "STATE_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ParaValue = "PARA_VALUE";
  public final static  String S_ParaCode = "PARA_CODE";


public String getRegionId();

public Timestamp getStateDate();

public String getState();

public String getRemarks();

public String getParaValue();

public String getParaCode();


public  void setRegionId(String value);

public  void setStateDate(Timestamp value);

public  void setState(String value);

public  void setRemarks(String value);

public  void setParaValue(String value);

public  void setParaCode(String value);
}
