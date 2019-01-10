package com.asiainfo.monitor.busi.exe.task.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonPExecValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_ExecId = "EXEC_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Name = "NAME";
  public final static  String S_EType = "E_TYPE";
  public final static  String S_Expr = "EXPR";


public String getState();

public long getExecId();

public String getRemarks();

public String getName();

public String getEType();

public String getExpr();


public  void setState(String value);

public  void setExecId(long value);

public  void setRemarks(String value);

public  void setName(String value);

public  void setEType(String value);

public  void setExpr(String value);
}
