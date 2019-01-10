package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonMasterSlaveRelValue extends DataStructInterface{

  public final static  String S_SlaveHostId = "SLAVE_HOST_ID";
  public final static  String S_State = "STATE";
  public final static  String S_Remark = "REMARK";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_MasterHostId = "MASTER_HOST_ID";
  public final static  String S_RelationId = "RELATION_ID";


public long getSlaveHostId();

public String getState();

public String getRemark();

public Timestamp getCreateDate();

public long getMasterHostId();

public long getRelationId();


public  void setSlaveHostId(long value);

public  void setState(String value);

public  void setRemark(String value);

public  void setCreateDate(Timestamp value);

public  void setMasterHostId(long value);

public  void setRelationId(long value);
}
