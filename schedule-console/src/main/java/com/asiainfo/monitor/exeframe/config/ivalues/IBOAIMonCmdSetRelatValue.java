package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonCmdSetRelatValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_CmdId = "CMD_ID";
  public final static  String S_CmdSeq = "CMD_SEQ";
  public final static  String S_CmdsetId = "CMDSET_ID";
  public final static  String S_Remark = "REMARK";


public String getState();

public long getCmdId();

public int getCmdSeq();

public long getCmdsetId();

public String getRemark();


public  void setState(String value);

public  void setCmdId(long value);

public  void setCmdSeq(int value);

public  void setCmdsetId(long value);

public  void setRemark(String value);
}
