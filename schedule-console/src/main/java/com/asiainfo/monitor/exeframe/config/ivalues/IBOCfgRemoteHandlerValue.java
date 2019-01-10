package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgRemoteHandlerValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ImplClassName = "IMPL_CLASS_NAME";
  public final static  String S_HandlerId = "HANDLER_ID";
  public final static  String S_CfgRemoteCode = "CFG_REMOTE_CODE";
  public final static  String S_InterfaceClassName = "INTERFACE_CLASS_NAME";


public String getState();

public String getRemarks();

public String getImplClassName();

public long getHandlerId();

public String getCfgRemoteCode();

public String getInterfaceClassName();


public  void setState(String value);

public  void setRemarks(String value);

public  void setImplClassName(String value);

public  void setHandlerId(long value);

public  void setCfgRemoteCode(String value);

public  void setInterfaceClassName(String value);
}
