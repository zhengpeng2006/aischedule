package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonHostUserValue extends DataStructInterface{

  public final static  String S_UserPwd = "USER_PWD";
  public final static  String S_State = "STATE";
  public final static  String S_HostId = "HOST_ID";
  public final static  String S_UserName = "USER_NAME";
  public final static  String S_Remark = "REMARK";
  public final static  String S_HostUserId = "HOST_USER_ID";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_UserType = "USER_TYPE";


public String getUserPwd();

public String getState();

public long getHostId();

public String getUserName();

public String getRemark();

public long getHostUserId();

public Timestamp getCreateDate();

public String getUserType();


public  void setUserPwd(String value);

public  void setState(String value);

public  void setHostId(long value);

public  void setUserName(String value);

public  void setRemark(String value);

public  void setHostUserId(long value);

public  void setCreateDate(Timestamp value);

public  void setUserType(String value);
}
