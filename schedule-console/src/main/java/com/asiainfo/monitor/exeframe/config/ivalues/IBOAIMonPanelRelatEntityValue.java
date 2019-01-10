package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonPanelRelatEntityValue extends DataStructInterface{

  public final static  String S_EntityId = "ENTITY_ID";
  public final static  String S_State = "STATE";
  public final static  String S_PanelType = "PANEL_TYPE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_RelatId = "RELAT_ID";
  public final static  String S_PanelId = "PANEL_ID";


public long getEntityId();

public String getState();

public String getPanelType();

public String getRemarks();

public long getRelatId();

public long getPanelId();


public  void setEntityId(long value);

public  void setState(String value);

public  void setPanelType(String value);

public  void setRemarks(String value);

public  void setRelatId(long value);

public  void setPanelId(long value);
}
