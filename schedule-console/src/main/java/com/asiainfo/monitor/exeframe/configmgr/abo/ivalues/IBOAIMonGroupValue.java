package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonGroupValue extends DataStructInterface{

  public final static  String S_GroupDesc = "GROUP_DESC";
  public final static  String S_State = "STATE";
  public final static  String S_GroupId = "GROUP_ID";
  public final static  String S_GroupName = "GROUP_NAME";
  public final static  String S_Priv = "PRIV";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_GroupCode = "GROUP_CODE";


public String getGroupDesc();

public String getState();

public long getGroupId();

public String getGroupName();

public String getPriv();

public Timestamp getCreateDate();

public String getGroupCode();


public  void setGroupDesc(String value);

public  void setState(String value);

public  void setGroupId(long value);

public  void setGroupName(String value);

public  void setPriv(String value);

public  void setCreateDate(Timestamp value);

public  void setGroupCode(String value);
}
