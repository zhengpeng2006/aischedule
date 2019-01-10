package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonUserRoleEntityRelValue extends DataStructInterface{

  public final static  String S_UserRoleId = "USER_ROLE_ID";
  public final static  String S_EntityId = "ENTITY_ID";
  public final static  String S_RelateId = "RELATE_ID";


public long getUserRoleId();

public long getEntityId();

public long getRelateId();


public  void setUserRoleId(long value);

public  void setEntityId(long value);

public  void setRelateId(long value);
}
