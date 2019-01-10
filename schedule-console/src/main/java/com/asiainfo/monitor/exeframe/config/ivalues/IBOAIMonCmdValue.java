package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonCmdValue extends DataStructInterface{

  public final static  String S_CmdName = "CMD_NAME";
  public final static  String S_State = "STATE";
  public final static  String S_CmdId = "CMD_ID";
  public final static  String S_CmdExpr = "CMD_EXPR";
  public final static  String S_CmdType = "CMD_TYPE";
  public final static  String S_CmdDesc = "CMD_DESC";
  public final static  String S_Remark = "REMARK";
  public final static  String S_ParamType = "PARAM_TYPE";


public String getCmdName();

public String getState();

public long getCmdId();

public String getCmdExpr();

public String getCmdType();

public String getCmdDesc();

public String getRemark();

public String getParamType();


public  void setCmdName(String value);

public  void setState(String value);

public  void setCmdId(long value);

public  void setCmdExpr(String value);

public  void setCmdType(String value);

public  void setCmdDesc(String value);

public  void setRemark(String value);

public  void setParamType(String value);
}
