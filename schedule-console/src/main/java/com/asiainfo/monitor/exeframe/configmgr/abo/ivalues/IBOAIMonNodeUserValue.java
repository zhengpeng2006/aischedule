package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonNodeUserValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_UserId = "USER_ID";
  public final static  String S_NodeUserId = "NODE_USER_ID";
  public final static  String S_Remark = "REMARK";
  public final static  String S_NodeId = "NODE_ID";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_ModifyDate = "MODIFY_DATE";


public String getState();

public long getUserId();

public long getNodeUserId();

public String getRemark();

public long getNodeId();

public Timestamp getCreateDate();

public Timestamp getModifyDate();


public  void setState(String value);

public  void setUserId(long value);

public  void setNodeUserId(long value);

public  void setRemark(String value);

public  void setNodeId(long value);

public  void setCreateDate(Timestamp value);

public  void setModifyDate(Timestamp value);
}
