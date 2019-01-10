package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonNodeValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_HostId = "HOST_ID";
  public final static  String S_IsMonitorNode = "IS_MONITOR_NODE";
  public final static  String S_Remark = "REMARK";
  public final static  String S_NodeId = "NODE_ID";
  public final static  String S_NodeName = "NODE_NAME";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_NodeCode = "NODE_CODE";


public String getState();

public long getHostId();

public String getIsMonitorNode();

public String getRemark();

public long getNodeId();

public String getNodeName();

public Timestamp getCreateDate();

public String getNodeCode();


public  void setState(String value);

public  void setHostId(long value);

public  void setIsMonitorNode(String value);

public  void setRemark(String value);

public  void setNodeId(long value);

public  void setNodeName(String value);

public  void setCreateDate(Timestamp value);

public  void setNodeCode(String value);
}
