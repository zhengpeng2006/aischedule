package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonUsersValue extends DataStructInterface{

  public final static  String S_UserPwd = "USER_PWD";
  public final static  String S_State = "STATE";
  public final static  String S_UserId = "USER_ID";
  public final static  String S_UserName = "USER_NAME";
  public final static  String S_UserCode = "USER_CODE";
  public final static  String S_Remark = "REMARK";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_ModifyDate = "MODIFY_DATE";


public String getUserPwd();

public String getState();

public long getUserId();

public String getUserName();

public String getUserCode();

public String getRemark();

public Timestamp getCreateDate();

public Timestamp getModifyDate();


public  void setUserPwd(String value);

public  void setState(String value);

public  void setUserId(long value);

public  void setUserName(String value);

public  void setUserCode(String value);

public  void setRemark(String value);

public  void setCreateDate(Timestamp value);

public  void setModifyDate(Timestamp value);
}
