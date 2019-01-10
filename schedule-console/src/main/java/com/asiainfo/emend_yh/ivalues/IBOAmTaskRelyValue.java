package com.asiainfo.emend_yh.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAmTaskRelyValue extends DataStructInterface{

  public final static  String S_CurTaskCode = "CUR_TASK_CODE";
  public final static  String S_IsHandle = "IS_HANDLE";
  public final static  String S_PreTaskCode = "PRE_TASK_CODE";
  public final static  String S_IsAllregionRely = "IS_ALLREGION_RELY";
  public final static  String S_Ext1 = "EXT1";
  public final static  String S_Ext2 = "EXT2";
  public final static  String S_TaskGroup = "TASK_GROUP";
  public final static  String S_Ext3 = "EXT3";


public String getCurTaskCode();

public String getIsHandle();

public String getPreTaskCode();

public String getIsAllregionRely();

public String getExt1();

public String getExt2();

public String getTaskGroup();

public String getExt3();


public  void setCurTaskCode(String value);

public  void setIsHandle(String value);

public  void setPreTaskCode(String value);

public  void setIsAllregionRely(String value);

public  void setExt1(String value);

public  void setExt2(String value);

public  void setTaskGroup(String value);

public  void setExt3(String value);
}
