package com.asiainfo.monitor.busi.exe.task.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonWDtlValue extends DataStructInterface{

  public final static  String S_DtlId = "DTL_ID";
  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_PersonId = "PERSON_ID";
  public final static  String S_WarnLevel = "WARN_LEVEL";
  public final static  String S_InfoId = "INFO_ID";


public long getDtlId();

public String getState();

public String getRemarks();

public long getPersonId();

public String getWarnLevel();

public long getInfoId();


public  void setDtlId(long value);

public  void setState(String value);

public  void setRemarks(String value);

public  void setPersonId(long value);

public  void setWarnLevel(String value);

public  void setInfoId(long value);
}
