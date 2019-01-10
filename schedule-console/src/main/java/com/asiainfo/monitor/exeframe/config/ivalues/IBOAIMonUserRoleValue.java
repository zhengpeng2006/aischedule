package com.asiainfo.monitor.exeframe.config.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonUserRoleValue extends DataStructInterface{

  public final static  String S_UserRoleId = "USER_ROLE_ID";
  public final static  String S_RoleName = "ROLE_NAME";
  public final static  String S_State = "STATE";
  public final static  String S_DomainId = "DOMAIN_ID";
  public final static  String S_RoldCode = "ROLD_CODE";
  public final static  String S_OpId = "OP_ID";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_Notes = "NOTES";
  public final static  String S_CreateDate = "CREATE_DATE";


public long getUserRoleId();

public String getRoleName();

public String getState();

public long getDomainId();

public String getRoldCode();

public long getOpId();

public Timestamp getDoneDate();

public String getNotes();

public Timestamp getCreateDate();


public  void setUserRoleId(long value);

public  void setRoleName(String value);

public  void setState(String value);

public  void setDomainId(long value);

public  void setRoldCode(String value);

public  void setOpId(long value);

public  void setDoneDate(Timestamp value);

public  void setNotes(String value);

public  void setCreateDate(Timestamp value);
}
