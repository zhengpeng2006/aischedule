package com.asiainfo.monitor.exeframe.config.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonMemoValue;

public class BOAIMonMemoBean extends DataContainer implements DataContainerInterface,IBOAIMonMemoValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonMemo";



  public final static  String S_MemoId = "MEMO_ID";
  public final static  String S_State = "STATE";
  public final static  String S_MemoNote = "MEMO_NOTE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Creator = "CREATOR";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_MemoSubject = "MEMO_SUBJECT";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonMemoBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initMemoId(long value){
     this.initProperty(S_MemoId,new Long(value));
  }
  public  void setMemoId(long value){
     this.set(S_MemoId,new Long(value));
  }
  public  void setMemoIdNull(){
     this.set(S_MemoId,null);
  }

  public long getMemoId(){
        return DataType.getAsLong(this.get(S_MemoId));
  
  }
  public long getMemoIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_MemoId));
      }

  public void initState(String value){
     this.initProperty(S_State,value);
  }
  public  void setState(String value){
     this.set(S_State,value);
  }
  public  void setStateNull(){
     this.set(S_State,null);
  }

  public String getState(){
       return DataType.getAsString(this.get(S_State));
  
  }
  public String getStateInitialValue(){
        return DataType.getAsString(this.getOldObj(S_State));
      }

  public void initMemoNote(String value){
     this.initProperty(S_MemoNote,value);
  }
  public  void setMemoNote(String value){
     this.set(S_MemoNote,value);
  }
  public  void setMemoNoteNull(){
     this.set(S_MemoNote,null);
  }

  public String getMemoNote(){
       return DataType.getAsString(this.get(S_MemoNote));
  
  }
  public String getMemoNoteInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MemoNote));
      }

  public void initRemarks(String value){
     this.initProperty(S_Remarks,value);
  }
  public  void setRemarks(String value){
     this.set(S_Remarks,value);
  }
  public  void setRemarksNull(){
     this.set(S_Remarks,null);
  }

  public String getRemarks(){
       return DataType.getAsString(this.get(S_Remarks));
  
  }
  public String getRemarksInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Remarks));
      }

  public void initCreator(String value){
     this.initProperty(S_Creator,value);
  }
  public  void setCreator(String value){
     this.set(S_Creator,value);
  }
  public  void setCreatorNull(){
     this.set(S_Creator,null);
  }

  public String getCreator(){
       return DataType.getAsString(this.get(S_Creator));
  
  }
  public String getCreatorInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Creator));
      }

  public void initCreateDate(Timestamp value){
     this.initProperty(S_CreateDate,value);
  }
  public  void setCreateDate(Timestamp value){
     this.set(S_CreateDate,value);
  }
  public  void setCreateDateNull(){
     this.set(S_CreateDate,null);
  }

  public Timestamp getCreateDate(){
        return DataType.getAsDateTime(this.get(S_CreateDate));
  
  }
  public Timestamp getCreateDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_CreateDate));
      }

  public void initMemoSubject(String value){
     this.initProperty(S_MemoSubject,value);
  }
  public  void setMemoSubject(String value){
     this.set(S_MemoSubject,value);
  }
  public  void setMemoSubjectNull(){
     this.set(S_MemoSubject,null);
  }

  public String getMemoSubject(){
       return DataType.getAsString(this.get(S_MemoSubject));
  
  }
  public String getMemoSubjectInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MemoSubject));
      }


 
 }

