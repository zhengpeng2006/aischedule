package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonPhysicHostValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_HostId = "HOST_ID";
  public final static  String S_HostDesc = "HOST_DESC";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_HostCode = "HOST_CODE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_HostName = "HOST_NAME";
  public final static  String S_HostIp = "HOST_IP";


public String getState();

public long getHostId();

public String getHostDesc();

public String getRemarks();

public String getHostCode();

public Timestamp getCreateDate();

public String getHostName();

public String getHostIp();


public  void setState(String value);

public  void setHostId(long value);

public  void setHostDesc(String value);

public  void setRemarks(String value);

public  void setHostCode(String value);

public  void setCreateDate(Timestamp value);

public  void setHostName(String value);

public  void setHostIp(String value);
}
