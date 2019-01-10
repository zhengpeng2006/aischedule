package com.asiainfo.monitor.exeframe.config.ivalues;
import java.sql.Timestamp;

import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonMemoValue extends DataStructInterface{

  public final static  String S_MemoId = "MEMO_ID";
  public final static  String S_State = "STATE";
  public final static  String S_MemoNote = "MEMO_NOTE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Creator = "CREATOR";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_MemoSubject = "MEMO_SUBJECT";


public long getMemoId();

public String getState();

public String getMemoNote();

public String getRemarks();

public String getCreator();

public Timestamp getCreateDate();

public String getMemoSubject();


public  void setMemoId(long value);

public  void setState(String value);

public  void setMemoNote(String value);

public  void setRemarks(String value);

public  void setCreator(String value);

public  void setCreateDate(Timestamp value);

public  void setMemoSubject(String value);
}
