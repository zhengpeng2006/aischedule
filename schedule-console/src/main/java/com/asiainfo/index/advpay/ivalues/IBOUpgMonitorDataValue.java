package com.asiainfo.index.advpay.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOUpgMonitorDataValue extends DataStructInterface{

  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_ScaValue2 = "SCA_VALUE2";
  public final static  String S_ScaValue1 = "SCA_VALUE1";
  public final static  String S_State = "STATE";
  public final static  String S_MonitorId = "MONITOR_ID";
  public final static  String S_SeqId = "SEQ_ID";
  public final static  String S_DimValue2 = "DIM_VALUE2";
  public final static  String S_DimValue3 = "DIM_VALUE3";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_DimValue4 = "DIM_VALUE4";
  public final static  String S_DimValue5 = "DIM_VALUE5";
  public final static  String S_DimValue6 = "DIM_VALUE6";
  public final static  String S_DimValue7 = "DIM_VALUE7";
  public final static  String S_DimValue8 = "DIM_VALUE8";
  public final static  String S_ScaValue8 = "SCA_VALUE8";
  public final static  String S_ScaValue7 = "SCA_VALUE7";
  public final static  String S_ScaValue4 = "SCA_VALUE4";
  public final static  String S_ScaValue3 = "SCA_VALUE3";
  public final static  String S_ScaValue6 = "SCA_VALUE6";
  public final static  String S_DimValue1 = "DIM_VALUE1";
  public final static  String S_ScaValue5 = "SCA_VALUE5";


public Timestamp getDoneDate();

public String getScaValue2();

public String getScaValue1();

public String getState();

public int getMonitorId();

public long getSeqId();

public String getDimValue2();

public String getDimValue3();

public Timestamp getCreateDate();

public String getDimValue4();

public String getDimValue5();

public String getDimValue6();

public String getDimValue7();

public String getDimValue8();

public String getScaValue8();

public String getScaValue7();

public String getScaValue4();

public String getScaValue3();

public String getScaValue6();

public String getDimValue1();

public String getScaValue5();


public  void setDoneDate(Timestamp value);

public  void setScaValue2(String value);

public  void setScaValue1(String value);

public  void setState(String value);

public  void setMonitorId(int value);

public  void setSeqId(long value);

public  void setDimValue2(String value);

public  void setDimValue3(String value);

public  void setCreateDate(Timestamp value);

public  void setDimValue4(String value);

public  void setDimValue5(String value);

public  void setDimValue6(String value);

public  void setDimValue7(String value);

public  void setDimValue8(String value);

public  void setScaValue8(String value);

public  void setScaValue7(String value);

public  void setScaValue4(String value);

public  void setScaValue3(String value);

public  void setScaValue6(String value);

public  void setDimValue1(String value);

public  void setScaValue5(String value);
}
