package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgSocketFilterValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_FilterId = "FILTER_ID";
  public final static  String S_FilterClass = "FILTER_CLASS";
  public final static  String S_SortId = "SORT_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CfgSocketCode = "CFG_SOCKET_CODE";


public String getState();

public long getFilterId();

public String getFilterClass();

public int getSortId();

public String getRemarks();

public String getCfgSocketCode();


public  void setState(String value);

public  void setFilterId(long value);

public  void setFilterClass(String value);

public  void setSortId(int value);

public  void setRemarks(String value);

public  void setCfgSocketCode(String value);
}
