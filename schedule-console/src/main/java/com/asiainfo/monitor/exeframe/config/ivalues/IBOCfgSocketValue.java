package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgSocketValue extends DataStructInterface{

  public final static  String S_SocketBusinessClass = "SOCKET_BUSINESS_CLASS";
  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CfgSocketCode = "CFG_SOCKET_CODE";
  public final static  String S_SocketDesc = "SOCKET_DESC";
  public final static  String S_SocketGrp = "SOCKET_GRP";


public String getSocketBusinessClass();

public String getState();

public String getRemarks();

public String getCfgSocketCode();

public String getSocketDesc();

public String getSocketGrp();


public  void setSocketBusinessClass(String value);

public  void setState(String value);

public  void setRemarks(String value);

public  void setCfgSocketCode(String value);

public  void setSocketDesc(String value);

public  void setSocketGrp(String value);
}
