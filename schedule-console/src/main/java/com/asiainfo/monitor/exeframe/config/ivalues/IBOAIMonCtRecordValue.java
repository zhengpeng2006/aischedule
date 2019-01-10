package com.asiainfo.monitor.exeframe.config.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonCtRecordValue extends DataStructInterface{

  public final static  String S_CtType = "CT_TYPE";
  public final static  String S_InvalidDuti = "INVALID_DUTI";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CtObjtype = "CT_OBJTYPE";
  public final static  String S_CtObjname = "CT_OBJNAME";
  public final static  String S_InvalidDate = "INVALID_DATE";
  public final static  String S_CtDate = "CT_DATE";
  public final static  String S_CtActor = "CT_ACTOR";
  public final static  String S_EffectDate = "EFFECT_DATE";
  public final static  String S_CtFlag = "CT_FLAG";
  public final static  String S_CtId = "CT_ID";
  public final static  String S_CtObjid = "CT_OBJID";


public int getCtType();

public long getInvalidDuti();

public String getRemarks();

public int getCtObjtype();

public String getCtObjname();

public Timestamp getInvalidDate();

public Timestamp getCtDate();

public String getCtActor();

public Timestamp getEffectDate();

public int getCtFlag();

public long getCtId();

public long getCtObjid();


public  void setCtType(int value);

public  void setInvalidDuti(long value);

public  void setRemarks(String value);

public  void setCtObjtype(int value);

public  void setCtObjname(String value);

public  void setInvalidDate(Timestamp value);

public  void setCtDate(Timestamp value);

public  void setCtActor(String value);

public  void setEffectDate(Timestamp value);

public  void setCtFlag(int value);

public  void setCtId(long value);

public  void setCtObjid(long value);
}
