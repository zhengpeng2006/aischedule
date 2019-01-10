package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonCustomizePanelValue extends DataStructInterface{

  public final static  String S_Layer = "LAYER";
  public final static  String S_State = "STATE";
  public final static  String S_TempType = "TEMP_TYPE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CpanelId = "CPANEL_ID";
  public final static  String S_CustType = "CUST_TYPE";
  public final static  String S_CpanelName = "CPANEL_NAME";
  public final static  String S_CpanelDesc = "CPANEL_DESC";


public String getLayer();

public String getState();

public String getTempType();

public String getRemarks();

public long getCpanelId();

public String getCustType();

public String getCpanelName();

public String getCpanelDesc();


public  void setLayer(String value);

public  void setState(String value);

public  void setTempType(String value);

public  void setRemarks(String value);

public  void setCpanelId(long value);

public  void setCustType(String value);

public  void setCpanelName(String value);

public  void setCpanelDesc(String value);
}
