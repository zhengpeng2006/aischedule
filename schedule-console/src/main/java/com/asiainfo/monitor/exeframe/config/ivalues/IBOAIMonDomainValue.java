package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonDomainValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_DomainId = "DOMAIN_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_DomainType = "DOMAIN_TYPE";
  public final static  String S_DomainCode = "DOMAIN_CODE";
  public final static  String S_DomainDesc = "DOMAIN_DESC";


public String getState();

public long getDomainId();

public String getRemarks();

public String getDomainType();

public String getDomainCode();

public String getDomainDesc();


public  void setState(String value);

public  void setDomainId(long value);

public  void setRemarks(String value);

public  void setDomainType(String value);

public  void setDomainCode(String value);

public  void setDomainDesc(String value);
}
