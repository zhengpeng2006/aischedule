package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonLoginValue extends DataStructInterface{

  public final static  String S_Ext02 = "EXT_02";
  public final static  String S_LastFailTime = "LAST_FAIL_TIME";
  public final static  String S_Ext01 = "EXT_01";
  public final static  String S_Ip = "IP";
  public final static  String S_FailCount = "FAIL_COUNT";
  public final static  String S_LoginName = "LOGIN_NAME";


public String getExt02();

public Timestamp getLastFailTime();

public String getExt01();

public String getIp();

public long getFailCount();

public String getLoginName();


public  void setExt02(String value);

public  void setLastFailTime(Timestamp value);

public  void setExt01(String value);

public  void setIp(String value);

public  void setFailCount(long value);

public  void setLoginName(String value);
}
