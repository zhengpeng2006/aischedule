package com.asiainfo.monitor.busi.exe.task.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonLRecordValue extends DataStructInterface{

  public final static  String S_ExpireDate = "EXPIRE_DATE";
  public final static  String S_OpId = "OP_ID";
  public final static  String S_Layer = "LAYER";
  public final static  String S_GroupId = "GROUP_ID";
  public final static  String S_InfoCode = "INFO_CODE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_IsTriggerWarn = "IS_TRIGGER_WARN";
  public final static  String S_Ip = "IP";
  public final static  String S_HostName = "HOST_NAME";
  public final static  String S_OrgId = "ORG_ID";
  public final static  String S_InfoId = "INFO_ID";
  public final static  String S_RecordId = "RECORD_ID";
  public final static  String S_BatchId = "BATCH_ID";
  public final static  String S_ValidDate = "VALID_DATE";
  public final static  String S_MonType = "MON_TYPE";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_InfoName = "INFO_NAME";
  public final static  String S_InfoValue = "INFO_VALUE";
  public final static  String S_FromType = "FROM_TYPE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_DoneCode = "DONE_CODE";
  public final static  String S_WarnLevel = "WARN_LEVEL";


public Timestamp getExpireDate();

public long getOpId();

public String getLayer();

public String getGroupId();

public String getInfoCode();

public String getRemarks();

public String getIsTriggerWarn();

public String getIp();

public String getHostName();

public long getOrgId();

public long getInfoId();

public long getRecordId();

public long getBatchId();

public Timestamp getValidDate();

public String getMonType();

public Timestamp getDoneDate();

public String getInfoName();

public String getInfoValue();

public String getFromType();

public Timestamp getCreateDate();

public long getDoneCode();

public String getWarnLevel();


public  void setExpireDate(Timestamp value);

public  void setOpId(long value);

public  void setLayer(String value);

public  void setGroupId(String value);

public  void setInfoCode(String value);

public  void setRemarks(String value);

public  void setIsTriggerWarn(String value);

public  void setIp(String value);

public  void setHostName(String value);

public  void setOrgId(long value);

public  void setInfoId(long value);

public  void setRecordId(long value);

public  void setBatchId(long value);

public  void setValidDate(Timestamp value);

public  void setMonType(String value);

public  void setDoneDate(Timestamp value);

public  void setInfoName(String value);

public  void setInfoValue(String value);

public  void setFromType(String value);

public  void setCreateDate(Timestamp value);

public  void setDoneCode(long value);

public  void setWarnLevel(String value);
}
