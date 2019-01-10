package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonCustomizePanelRelateValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_ViewType = "VIEW_TYPE";
  public final static  String S_TimeId = "TIME_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CpanelId = "CPANEL_ID";
  public final static  String S_RelateId = "RELATE_ID";
  public final static  String S_ThresholdId = "THRESHOLD_ID";
  public final static  String S_RunType = "RUN_TYPE";
  public final static  String S_ViewStrategy = "VIEW_STRATEGY";
  public final static  String S_ViewTransform = "VIEW_TRANSFORM";
  public final static  String S_PanelId = "PANEL_ID";
  public final static  String S_RelateName = "RELATE_NAME";


public String getState();

public String getViewType();

public long getTimeId();

public String getRemarks();

public long getCpanelId();

public long getRelateId();

public long getThresholdId();

public String getRunType();

public String getViewStrategy();

public String getViewTransform();

public long getPanelId();

public String getRelateName();


public  void setState(String value);

public  void setViewType(String value);

public  void setTimeId(long value);

public  void setRemarks(String value);

public  void setCpanelId(long value);

public  void setRelateId(long value);

public  void setThresholdId(long value);

public  void setRunType(String value);

public  void setViewStrategy(String value);

public  void setViewTransform(String value);

public  void setPanelId(long value);

public  void setRelateName(String value);
}
