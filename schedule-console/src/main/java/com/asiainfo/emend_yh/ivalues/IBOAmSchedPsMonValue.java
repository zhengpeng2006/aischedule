package com.asiainfo.emend_yh.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAmSchedPsMonValue extends DataStructInterface{

  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_TaskCode = "TASK_CODE";
  public final static  String S_State = "STATE";
  public final static  String S_Condition = "CONDITION";
  public final static  String S_TaskName = "TASK_NAME";
  public final static  String S_Ext5 = "EXT5";
  public final static  String S_Mod = "MOD";
  public final static  String S_BsBillingCycle = "BS_BILLING_CYCLE";
  public final static  String S_Ext1 = "EXT1";
  public final static  String S_Ext2 = "EXT2";
  public final static  String S_ModValue = "MOD_VALUE";
  public final static  String S_Ext3 = "EXT3";
  public final static  String S_Ext4 = "EXT4";


public String getRegionId();

public String getTaskCode();

public String getState();

public String getCondition();

public String getTaskName();

public String getExt5();

public long getMod();

public long getBsBillingCycle();

public String getExt1();

public String getExt2();

public long getModValue();

public String getExt3();

public String getExt4();


public  void setRegionId(String value);

public  void setTaskCode(String value);

public  void setState(String value);

public  void setCondition(String value);

public  void setTaskName(String value);

public  void setExt5(String value);

public  void setMod(long value);

public  void setBsBillingCycle(long value);

public  void setExt1(String value);

public  void setExt2(String value);

public  void setModValue(long value);

public  void setExt3(String value);

public  void setExt4(String value);
}
