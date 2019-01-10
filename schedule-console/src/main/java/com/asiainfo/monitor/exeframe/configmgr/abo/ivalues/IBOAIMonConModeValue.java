package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonConModeValue extends DataStructInterface{

  public final static  String S_ConType = "CON_TYPE";
  public final static  String S_State = "STATE";
  public final static  String S_ConId = "CON_ID";
  public final static  String S_Remark = "REMARK";
  public final static  String S_ConPort = "CON_PORT";
  public final static  String S_CreateDate = "CREATE_DATE";


public String getConType();

public String getState();

public long getConId();

public String getRemark();

public short getConPort();

public Timestamp getCreateDate();


public  void setConType(String value);

public  void setState(String value);

public  void setConId(long value);

public  void setRemark(String value);

public  void setConPort(short value);

public  void setCreateDate(Timestamp value);
}
