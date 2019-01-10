package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonDomainRelatValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_DomainId = "DOMAIN_ID";
  public final static  String S_RelatDesc = "RELAT_DESC";
  public final static  String S_RelatId = "RELAT_ID";
  public final static  String S_RelatdomainType = "RELATDOMAIN_TYPE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_RelatdomainId = "RELATDOMAIN_ID";


public String getState();

public long getDomainId();

public String getRelatDesc();

public long getRelatId();

public String getRelatdomainType();

public String getRemarks();

public long getRelatdomainId();


public  void setState(String value);

public  void setDomainId(long value);

public  void setRelatDesc(String value);

public  void setRelatId(long value);

public  void setRelatdomainType(String value);

public  void setRemarks(String value);

public  void setRelatdomainId(long value);
}
