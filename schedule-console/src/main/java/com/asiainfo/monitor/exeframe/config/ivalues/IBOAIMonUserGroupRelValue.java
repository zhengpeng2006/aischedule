package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonUserGroupRelValue extends DataStructInterface{

  public final static  String S_UserGroupId = "USER_GROUP_ID";
  public final static  String S_RelateId = "RELATE_ID";
  public final static  String S_UserId = "USER_ID";


public long getUserGroupId();

public long getRelateId();

public long getUserId();


public  void setUserGroupId(long value);

public  void setRelateId(long value);

public  void setUserId(long value);
}
