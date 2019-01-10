package com.asiainfo.monitor.busi.exe.task.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonPTableValue extends DataStructInterface{

  public final static  String S_Sql = "SQL";
  public final static  String S_State = "STATE";
  public final static  String S_DbAcctCode = "DB_ACCT_CODE";
  public final static  String S_DbUrlName = "DB_URL_NAME";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Name = "NAME";
  public final static  String S_TableId = "TABLE_ID";


public String getSql();

public String getState();

public String getDbAcctCode();

public String getDbUrlName();

public String getRemarks();

public String getName();

public long getTableId();


public  void setSql(String value);

public  void setState(String value);

public  void setDbAcctCode(String value);

public  void setDbUrlName(String value);

public  void setRemarks(String value);

public  void setName(String value);

public  void setTableId(long value);
}
