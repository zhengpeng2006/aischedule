package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgRemoteValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_RemoteGrp = "REMOTE_GRP";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_RemoteLocator = "REMOTE_LOCATOR";
  public final static  String S_CfgRemoteCode = "CFG_REMOTE_CODE";
  public final static  String S_RemoteDesc = "REMOTE_DESC";


public String getState();

public String getRemoteGrp();

public String getRemarks();

public String getRemoteLocator();

public String getCfgRemoteCode();

public String getRemoteDesc();


public  void setState(String value);

public  void setRemoteGrp(String value);

public  void setRemarks(String value);

public  void setRemoteLocator(String value);

public  void setCfgRemoteCode(String value);

public  void setRemoteDesc(String value);
}
