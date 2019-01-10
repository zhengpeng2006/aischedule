package com.asiainfo.monitor.exeframe.configmgr.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonVerifyValue extends DataStructInterface{

  public final static  String S_Rule = "RULE";
  public final static  String S_VerifyType = "VERIFY_TYPE";


public String getRule();

public String getVerifyType();


public  void setRule(String value);

public  void setVerifyType(String value);
}
