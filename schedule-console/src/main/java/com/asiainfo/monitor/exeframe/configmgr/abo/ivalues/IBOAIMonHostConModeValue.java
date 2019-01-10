package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonHostConModeValue extends DataStructInterface{

  public final static  String S_HostId = "HOST_ID";
  public final static  String S_ConId = "CON_ID";
  public final static  String S_RelationId = "RELATION_ID";


public long getHostId();

public long getConId();

public long getRelationId();


public  void setHostId(long value);

public  void setConId(long value);

public  void setRelationId(long value);
}
