package com.asiainfo.monitor.exeframe.config.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonUserPriEntityValue extends DataStructInterface{

  public final static  String S_EntityId = "ENTITY_ID";
  public final static  String S_State = "STATE";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_Notes = "NOTES";
  public final static  String S_EntityCode = "ENTITY_CODE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_DeployType = "DEPLOY_TYPE";
  public final static  String S_EntitySeq = "ENTITY_SEQ";
  public final static  String S_ParentId = "PARENT_ID";
  public final static  String S_EntityAttr = "ENTITY_ATTR";
  public final static  String S_OpId = "OP_ID";
  public final static  String S_EntityName = "ENTITY_NAME";
  public final static  String S_LoadType = "LOAD_TYPE";
  public final static  String S_EntityType = "ENTITY_TYPE";
  public final static  String S_EntityStyle = "ENTITY_STYLE";
  public final static  String S_SelfType = "SELF_TYPE";


public long getEntityId();

public String getState();

public Timestamp getDoneDate();

public String getNotes();

public String getEntityCode();

public Timestamp getCreateDate();

public String getDeployType();

public int getEntitySeq();

public long getParentId();

public String getEntityAttr();

public long getOpId();

public String getEntityName();

public String getLoadType();

public int getEntityType();

public String getEntityStyle();

public int getSelfType();


public  void setEntityId(long value);

public  void setState(String value);

public  void setDoneDate(Timestamp value);

public  void setNotes(String value);

public  void setEntityCode(String value);

public  void setCreateDate(Timestamp value);

public  void setDeployType(String value);

public  void setEntitySeq(int value);

public  void setParentId(long value);

public  void setEntityAttr(String value);

public  void setOpId(long value);

public  void setEntityName(String value);

public  void setLoadType(String value);

public  void setEntityType(int value);

public  void setEntityStyle(String value);

public  void setSelfType(int value);
}
