package com.asiainfo.monitor.exeframe.config.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonUserGroupValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_UserGroupId = "USER_GROUP_ID";
  public final static  String S_OpId = "OP_ID";
  public final static  String S_GroupName = "GROUP_NAME";
  public final static  String S_GroupCode = "GROUP_CODE";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_Notes = "NOTES";
  public final static  String S_CreateDate = "CREATE_DATE";


public String getState();

public long getUserGroupId();

public long getOpId();

public String getGroupName();

public String getGroupCode();

public Timestamp getDoneDate();

public String getNotes();

public Timestamp getCreateDate();


public  void setState(String value);

public  void setUserGroupId(long value);

public  void setOpId(long value);

public  void setGroupName(String value);

public  void setGroupCode(String value);

public  void setDoneDate(Timestamp value);

public  void setNotes(String value);

public  void setCreateDate(Timestamp value);
}
