package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonServerValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_ServerPort = "SERVER_PORT";
  public final static  String S_TempType = "TEMP_TYPE";
  public final static  String S_ServerCode = "SERVER_CODE";
  public final static  String S_Locator = "LOCATOR";
  public final static  String S_Remark = "REMARK";
  public final static  String S_StopCmdId = "STOP_CMD_ID";
  public final static  String S_Sversion = "SVERSION";
  public final static  String S_StartCmdId = "START_CMD_ID";
  public final static  String S_ServerName = "SERVER_NAME";
  public final static  String S_ServerIp = "SERVER_IP";
  public final static  String S_CheckUrl = "CHECK_URL";
  public final static  String S_ServerId = "SERVER_ID";
  public final static  String S_NodeId = "NODE_ID";
  public final static  String S_BusinessType = "BUSINESS_TYPE";
  public final static  String S_MidwareType = "MIDWARE_TYPE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_LocatorType = "LOCATOR_TYPE";


public String getState();

public short getServerPort();

public String getTempType();

public String getServerCode();

public String getLocator();

public String getRemark();

public short getStopCmdId();

public String getSversion();

public short getStartCmdId();

public String getServerName();

public String getServerIp();

public String getCheckUrl();

public long getServerId();

public long getNodeId();

public String getBusinessType();

public String getMidwareType();

public Timestamp getCreateDate();

public String getLocatorType();


public  void setState(String value);

public  void setServerPort(short value);

public  void setTempType(String value);

public  void setServerCode(String value);

public  void setLocator(String value);

public  void setRemark(String value);

public  void setStopCmdId(short value);

public  void setSversion(String value);

public  void setStartCmdId(short value);

public  void setServerName(String value);

public  void setServerIp(String value);

public  void setCheckUrl(String value);

public  void setServerId(long value);

public  void setNodeId(long value);

public  void setBusinessType(String value);

public  void setMidwareType(String value);

public  void setCreateDate(Timestamp value);

public  void setLocatorType(String value);
}
