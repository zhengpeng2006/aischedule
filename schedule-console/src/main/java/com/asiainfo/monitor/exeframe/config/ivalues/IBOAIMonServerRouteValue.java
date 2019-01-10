package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonServerRouteValue extends DataStructInterface{

  public final static  String S_RouteId = "ROUTE_ID";
  public final static  String S_DNodeCode = "D_NODE_CODE";
  public final static  String S_SServId = "S_SERV_ID";
  public final static  String S_State = "STATE";
  public final static  String S_DNodeId = "D_NODE_ID";
  public final static  String S_SNodeId = "S_NODE_ID";
  public final static  String S_SNodeCode = "S_NODE_CODE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_DServCode = "D_SERV_CODE";
  public final static  String S_SServCode = "S_SERV_CODE";
  public final static  String S_DServId = "D_SERV_ID";


public long getRouteId();

public String getDNodeCode();

public long getSServId();

public String getState();

public long getDNodeId();

public long getSNodeId();

public String getSNodeCode();

public String getRemarks();

public String getDServCode();

public String getSServCode();

public long getDServId();


public  void setRouteId(long value);

public  void setDNodeCode(String value);

public  void setSServId(long value);

public  void setState(String value);

public  void setDNodeId(long value);

public  void setSNodeId(long value);

public  void setSNodeCode(String value);

public  void setRemarks(String value);

public  void setDServCode(String value);

public  void setSServCode(String value);

public  void setDServId(long value);
}
