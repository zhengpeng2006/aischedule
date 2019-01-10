package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOCfgTfMappingValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_TfColumnName = "TF_COLUMN_NAME";
  public final static  String S_SrcColumnName = "SRC_COLUMN_NAME";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_MappingId = "MAPPING_ID";
  public final static  String S_CfgTfDtlId = "CFG_TF_DTL_ID";


public String getState();

public String getTfColumnName();

public String getSrcColumnName();

public String getRemarks();

public long getMappingId();

public long getCfgTfDtlId();


public  void setState(String value);

public  void setTfColumnName(String value);

public  void setSrcColumnName(String value);

public  void setRemarks(String value);

public  void setMappingId(long value);

public  void setCfgTfDtlId(long value);
}
