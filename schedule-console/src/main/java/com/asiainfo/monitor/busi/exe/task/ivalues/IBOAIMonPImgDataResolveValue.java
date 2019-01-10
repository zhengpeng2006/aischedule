package com.asiainfo.monitor.busi.exe.task.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonPImgDataResolveValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_ShowValuePos = "SHOW_VALUE_POS";
  public final static  String S_SortBy = "SORT_BY";
  public final static  String S_ShowType = "SHOW_TYPE";
  public final static  String S_ShowNamePos = "SHOW_NAME_POS";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_TransformClass = "TRANSFORM_CLASS";
  public final static  String S_Name = "NAME";
  public final static  String S_ResolveId = "RESOLVE_ID";
  public final static  String S_ParentId = "PARENT_ID";


public String getState();

public int getShowValuePos();

public String getSortBy();

public String getShowType();

public int getShowNamePos();

public String getRemarks();

public String getTransformClass();

public String getName();

public long getResolveId();

public long getParentId();


public  void setState(String value);

public  void setShowValuePos(int value);

public  void setSortBy(String value);

public  void setShowType(String value);

public  void setShowNamePos(int value);

public  void setRemarks(String value);

public  void setTransformClass(String value);

public  void setName(String value);

public  void setResolveId(long value);

public  void setParentId(long value);
}
