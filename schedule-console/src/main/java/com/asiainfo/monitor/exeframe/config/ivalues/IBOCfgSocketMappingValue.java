package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgSocketMappingValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_MappingId = "MAPPING_ID";
  public final static  String S_CfgSocketCode = "CFG_SOCKET_CODE";
  public final static  String S_MappingValue = "MAPPING_VALUE";
  public final static  String S_MappingName = "MAPPING_NAME";


public String getState();

public String getRemarks();

public long getMappingId();

public String getCfgSocketCode();

public String getMappingValue();

public String getMappingName();


public  void setState(String value);

public  void setRemarks(String value);

public  void setMappingId(long value);

public  void setCfgSocketCode(String value);

public  void setMappingValue(String value);

public  void setMappingName(String value);
}
