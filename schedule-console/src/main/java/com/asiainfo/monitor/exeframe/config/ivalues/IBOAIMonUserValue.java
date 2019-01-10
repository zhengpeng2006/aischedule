package com.asiainfo.monitor.exeframe.config.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonUserValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_Notes = "NOTES";
  public final static  String S_TryTimes = "TRY_TIMES";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_AllowChgPass = "ALLOW_CHG_PASS";
  public final static  String S_UserCode = "USER_CODE";
  public final static  String S_ExpireDate = "EXPIRE_DATE";
  public final static  String S_OpId = "OP_ID";
  public final static  String S_LockFlag = "LOCK_FLAG";
  public final static  String S_MultiLoginFlag = "MULTI_LOGIN_FLAG";
  public final static  String S_UserId = "USER_ID";
  public final static  String S_EffectDate = "EFFECT_DATE";
  public final static  String S_UserPass = "USER_PASS";
  public final static  String S_UserName = "USER_NAME";


public String getState();

public Timestamp getDoneDate();

public String getNotes();

public int getTryTimes();

public Timestamp getCreateDate();

public int getAllowChgPass();

public String getUserCode();

public Timestamp getExpireDate();

public long getOpId();

public int getLockFlag();

public int getMultiLoginFlag();

public long getUserId();

public Timestamp getEffectDate();

public String getUserPass();

public String getUserName();


public  void setState(String value);

public  void setDoneDate(Timestamp value);

public  void setNotes(String value);

public  void setTryTimes(int value);

public  void setCreateDate(Timestamp value);

public  void setAllowChgPass(int value);

public  void setUserCode(String value);

public  void setExpireDate(Timestamp value);

public  void setOpId(long value);

public  void setLockFlag(int value);

public  void setMultiLoginFlag(int value);

public  void setUserId(long value);

public  void setEffectDate(Timestamp value);

public  void setUserPass(String value);

public  void setUserName(String value);
}
