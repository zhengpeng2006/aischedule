package com.asiainfo.common.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOABGMonLRecordValue extends DataStructInterface{

  public final static  String S_ExpireDate = "expire_date";
  public final static  String S_DoneDate = "done_date";
  public final static  String S_Remarks = "remarks";
  public final static  String S_HostName = "HOST_NAME";
  public final static  String S_InfoId = "INFO_ID";
  public final static  String S_IsTriggerWarn = "is_trigger_warn";
  public final static  String S_OpId = "op_id";
  public final static  String S_CreateDate = "create_date";
  public final static  String S_RecordId = "RECORD_ID";
  public final static  String S_ValidDate = "VALID_DATE";
  public final static  String S_SubsystemDomain = "subsystem_domain";
  public final static  String S_MonType = "mon_type";
  public final static  String S_WarnLevel = "warn_level";
  public final static  String S_GroupId = "GROUP_ID";
  public final static  String S_InfoCode = "INFO_CODE";
  public final static  String S_Layer = "layer";
  public final static  String S_DoneCode = "done_code";
  public final static  String S_InfoName = "info_name";
  public final static  String S_InfoValue = "info_value";
  public final static  String S_Ip = "ip";
  public final static  String S_AppServerName = "app_server_name";
  public final static  String S_BatchId = "batch_id";
  public final static  String S_OrgId = "org_id";
  public final static  String S_CreateTime = "create_time";
  public final static  String S_FromType = "from_type";
  public final static  String S_SystemDomain = "system_domain";


public Timestamp getExpireDate();

public Timestamp getDoneDate();

public String getRemarks();

public String getHostName();

public long getInfoId();

public String getIsTriggerWarn();

public long getOpId();

public String getCreateDate();

public long getRecordId();

public Timestamp getValidDate();

public String getSubsystemDomain();

public String getMonType();

public String getWarnLevel();

public String getGroupId();

public String getInfoCode();

public String getLayer();

public long getDoneCode();

public String getInfoName();

public String getInfoValue();

public String getIp();

public String getAppServerName();

public long getBatchId();

public long getOrgId();

public Timestamp getCreateTime();

public String getFromType();

public String getSystemDomain();


public  void setExpireDate(Timestamp value);

public  void setDoneDate(Timestamp value);

public  void setRemarks(String value);

public  void setHostName(String value);

public  void setInfoId(long value);

public  void setIsTriggerWarn(String value);

public  void setOpId(long value);

public  void setCreateDate(String value);

public  void setRecordId(long value);

public  void setValidDate(Timestamp value);

public  void setSubsystemDomain(String value);

public  void setMonType(String value);

public  void setWarnLevel(String value);

public  void setGroupId(String value);

public  void setInfoCode(String value);

public  void setLayer(String value);

public  void setDoneCode(long value);

public  void setInfoName(String value);

public  void setInfoValue(String value);

public  void setIp(String value);

public  void setAppServerName(String value);

public  void setBatchId(long value);

public  void setOrgId(long value);

public  void setCreateTime(Timestamp value);

public  void setFromType(String value);

public  void setSystemDomain(String value);
}
