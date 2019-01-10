package com.asiainfo.monitor.busi.exe.task.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonWPersonValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_PersonId = "PERSON_ID";
  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Name = "NAME";
  public final static  String S_Region = "REGION";
  public final static  String S_Phonenum = "PHONENUM";


public String getState();

public long getPersonId();

public long getRegionId();

public String getRemarks();

public String getName();

public String getRegion();

public String getPhonenum();


public  void setState(String value);

public  void setPersonId(long value);

public  void setRegionId(long value);

public  void setRemarks(String value);

public  void setName(String value);

public  void setRegion(String value);

public  void setPhonenum(String value);
}
