package com.asiainfo.monitor.exeframe.config.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonLogValue extends DataStructInterface{

  public final static  String S_OperatorExpr = "OPERATOR_EXPR";
  public final static  String S_LogId = "LOG_ID";
  public final static  String S_OperateId = "OPERATE_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CreateTime = "CREATE_TIME";
  public final static  String S_Clkcode = "CLKCODE";
  public final static  String S_Clkurl = "CLKURL";
  public final static  String S_OperateName = "OPERATE_NAME";


public String getOperatorExpr();

public long getLogId();

public long getOperateId();

public String getRemarks();

public Timestamp getCreateTime();

public String getClkcode();

public String getClkurl();

public String getOperateName();


public  void setOperatorExpr(String value);

public  void setLogId(long value);

public  void setOperateId(long value);

public  void setRemarks(String value);

public  void setCreateTime(Timestamp value);

public  void setClkcode(String value);

public  void setClkurl(String value);

public  void setOperateName(String value);
}
