package com.asiainfo.monitor.busi.exe.task.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonPInfoGroupValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_SortId = "SORT_ID";
  public final static  String S_GroupStyle = "GROUP_STYLE";
  public final static  String S_GroupName = "GROUP_NAME";
  public final static  String S_GroupCode = "GROUP_CODE";
  public final static  String S_GroupId = "GROUP_ID";
  public final static  String S_GroupDesc = "GROUP_DESC";
  public final static  String S_Remark = "REMARK";
  public final static  String S_Layer = "LAYER";
  public final static  String S_ParentId = "PARENT_ID";


public String getState();

public int getSortId();

public String getGroupStyle();

public String getGroupName();

public String getGroupCode();

public long getGroupId();

public String getGroupDesc();

public String getRemark();

public String getLayer();

public long getParentId();


public  void setState(String value);

public  void setSortId(int value);

public  void setGroupStyle(String value);

public  void setGroupName(String value);

public  void setGroupCode(String value);

public  void setGroupId(long value);

public  void setGroupDesc(String value);

public  void setRemark(String value);

public  void setLayer(String value);

public  void setParentId(long value);
}
