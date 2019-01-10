package com.asiainfo.monitor.tools.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonI18nResourceValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_ResKey = "RES_KEY";
  public final static  String S_EnUs = "EN_US";
  public final static  String S_Remark = "REMARK";
  public final static  String S_ZhCn = "ZH_CN";


public String getState();

public String getResKey();

public String getEnUs();

public String getRemark();

public String getZhCn();


public  void setState(String value);

public  void setResKey(String value);

public  void setEnUs(String value);

public  void setRemark(String value);

public  void setZhCn(String value);
}
