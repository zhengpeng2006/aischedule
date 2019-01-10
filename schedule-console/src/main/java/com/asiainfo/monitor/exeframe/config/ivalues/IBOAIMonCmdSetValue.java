package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonCmdSetValue extends DataStructInterface{

  public final static  String S_CmdsetCode = "CMDSET_CODE";
  public final static  String S_State = "STATE";
  public final static  String S_CmdsetDesc = "CMDSET_DESC";
  public final static  String S_CmdsetName = "CMDSET_NAME";
  public final static  String S_CmdsetId = "CMDSET_ID";
  public final static  String S_Remark = "REMARK";


public String getCmdsetCode();

public String getState();

public String getCmdsetDesc();

public String getCmdsetName();

public long getCmdsetId();

public String getRemark();


public  void setCmdsetCode(String value);

public  void setState(String value);

public  void setCmdsetDesc(String value);

public  void setCmdsetName(String value);

public  void setCmdsetId(long value);

public  void setRemark(String value);
}
