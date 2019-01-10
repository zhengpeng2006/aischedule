package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonGroupHostRelValue extends DataStructInterface{

  public final static  String S_HostId = "HOST_ID";
  public final static  String S_GroupId = "GROUP_ID";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_RelationId = "RELATION_ID";


public long getHostId();

public long getGroupId();

public Timestamp getCreateDate();

public long getRelationId();


public  void setHostId(long value);

public  void setGroupId(long value);

public  void setCreateDate(Timestamp value);

public  void setRelationId(long value);
}
