package com.asiainfo.index.base.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOBsIndexDefValue extends DataStructInterface{

  public final static  String S_IndexDesc = "INDEX_DESC";
  public final static  String S_IndexFormat = "INDEX_FORMAT";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_IndexId = "INDEX_ID";
  public final static  String S_IndexTypeId = "INDEX_TYPE_ID";
  public final static  String S_IndexUnit = "INDEX_UNIT";
  public final static  String S_IndexCode = "INDEX_CODE";
  public final static  String S_State = "STATE";
  public final static  String S_IndexName = "INDEX_NAME";
  public final static  String S_IndexExpr = "INDEX_EXPR";


public String getIndexDesc();

public String getIndexFormat();

public Timestamp getCreateDate();

public Timestamp getDoneDate();

public int getIndexId();

public int getIndexTypeId();

public String getIndexUnit();

public String getIndexCode();

public String getState();

public String getIndexName();

public String getIndexExpr();


public  void setIndexDesc(String value);

public  void setIndexFormat(String value);

public  void setCreateDate(Timestamp value);

public  void setDoneDate(Timestamp value);

public  void setIndexId(int value);

public  void setIndexTypeId(int value);

public  void setIndexUnit(String value);

public  void setIndexCode(String value);

public  void setState(String value);

public  void setIndexName(String value);

public  void setIndexExpr(String value);
}
