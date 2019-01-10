package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonUserGroupRoleRelValue extends DataStructInterface{

  public final static  String S_UserRoleId = "USER_ROLE_ID";
  public final static  String S_UserGroupId = "USER_GROUP_ID";
  public final static  String S_RelatId = "RELAT_ID";


public long getUserRoleId();

public long getUserGroupId();

public long getRelatId();


public  void setUserRoleId(long value);

public  void setUserGroupId(long value);

public  void setRelatId(long value);
}
