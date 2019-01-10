package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonAttentionPanelValue extends DataStructInterface{

  public final static  String S_Layer = "LAYER";
  public final static  String S_State = "STATE";
  public final static  String S_ObjId = "OBJ_ID";
  public final static  String S_TempType = "TEMP_TYPE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ViewStrategy = "VIEW_STRATEGY";
  public final static  String S_Contrail = "CONTRAIL";
  public final static  String S_PanelId = "PANEL_ID";
  public final static  String S_PanelName = "PANEL_NAME";
  public final static  String S_ViewType = "VIEW_TYPE";
  public final static  String S_TimeId = "TIME_ID";
  public final static  String S_ObjType = "OBJ_TYPE";
  public final static  String S_ThresholdId = "THRESHOLD_ID";
  public final static  String S_ViewTransform = "VIEW_TRANSFORM";
  public final static  String S_ExecMethod = "EXEC_METHOD";
  public final static  String S_PanelDesc = "PANEL_DESC";


public String getLayer();

public String getState();

public long getObjId();

public String getTempType();

public String getRemarks();

public String getViewStrategy();

public String getContrail();

public long getPanelId();

public String getPanelName();

public String getViewType();

public long getTimeId();

public String getObjType();

public long getThresholdId();

public String getViewTransform();

public String getExecMethod();

public String getPanelDesc();


public  void setLayer(String value);

public  void setState(String value);

public  void setObjId(long value);

public  void setTempType(String value);

public  void setRemarks(String value);

public  void setViewStrategy(String value);

public  void setContrail(String value);

public  void setPanelId(long value);

public  void setPanelName(String value);

public  void setViewType(String value);

public  void setTimeId(long value);

public  void setObjType(String value);

public  void setThresholdId(long value);

public  void setViewTransform(String value);

public  void setExecMethod(String value);

public  void setPanelDesc(String value);
}
