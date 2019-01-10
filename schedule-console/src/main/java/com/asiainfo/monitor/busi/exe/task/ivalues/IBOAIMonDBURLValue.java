package com.asiainfo.monitor.busi.exe.task.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonDBURLValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_Url = "URL";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Name = "NAME";


public String getState();

public String getUrl();

public String getRemarks();

public String getName();


public  void setState(String value);

public  void setUrl(String value);

public  void setRemarks(String value);

public  void setName(String value);
}
