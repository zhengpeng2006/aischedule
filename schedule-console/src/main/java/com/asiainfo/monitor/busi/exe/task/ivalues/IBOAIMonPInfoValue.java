package com.asiainfo.monitor.busi.exe.task.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonPInfoValue extends DataStructInterface{

  public final static  String S_Layer = "LAYER";
  public final static  String S_MType = "M_TYPE";
  public final static  String S_GroupId = "GROUP_ID";
  public final static  String S_InfoCode = "INFO_CODE";
  public final static  String S_State = "STATE";
  public final static  String S_Name = "NAME";
  public final static  String S_FilterId = "FILTER_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_SplitRuleId = "SPLIT_RULE_ID";
  public final static  String S_ResolveId = "RESOLVE_ID";
  public final static  String S_InfoId = "INFO_ID";
  public final static  String S_HostId = "HOST_ID";
  public final static  String S_TimeId = "TIME_ID";
  public final static  String S_TypeId = "TYPE_ID";
  public final static  String S_ThresholdId = "THRESHOLD_ID";
  public final static  String S_TaskMethod = "TASK_METHOD";


public String getLayer();

public String getMType();

public long getGroupId();

public String getInfoCode();

public String getState();

public String getName();

public long getFilterId();

public String getRemarks();

public long getSplitRuleId();

public long getResolveId();

public long getInfoId();

public long getHostId();

public long getTimeId();

public long getTypeId();

public long getThresholdId();

public String getTaskMethod();


public  void setLayer(String value);

public  void setMType(String value);

public  void setGroupId(long value);

public  void setInfoCode(String value);

public  void setState(String value);

public  void setName(String value);

public  void setFilterId(long value);

public  void setRemarks(String value);

public  void setSplitRuleId(long value);

public  void setResolveId(long value);

public  void setInfoId(long value);

public  void setHostId(long value);

public  void setTimeId(long value);

public  void setTypeId(long value);

public  void setThresholdId(long value);

public  void setTaskMethod(String value);
}
