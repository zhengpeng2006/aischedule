package com.asiainfo.index.base.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.index.base.ivalues.*;

public class BOBsIndexDefBean extends DataContainer implements DataContainerInterface,IBOBsIndexDefValue{

  private static String  m_boName = "com.asiainfo.index.base.bo.BOBsIndexDef";



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

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOBsIndexDefBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initIndexDesc(String value){
     this.initProperty(S_IndexDesc,value);
  }
  public  void setIndexDesc(String value){
     this.set(S_IndexDesc,value);
  }
  public  void setIndexDescNull(){
     this.set(S_IndexDesc,null);
  }

  public String getIndexDesc(){
       return DataType.getAsString(this.get(S_IndexDesc));
  
  }
  public String getIndexDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IndexDesc));
      }

  public void initIndexFormat(String value){
     this.initProperty(S_IndexFormat,value);
  }
  public  void setIndexFormat(String value){
     this.set(S_IndexFormat,value);
  }
  public  void setIndexFormatNull(){
     this.set(S_IndexFormat,null);
  }

  public String getIndexFormat(){
       return DataType.getAsString(this.get(S_IndexFormat));
  
  }
  public String getIndexFormatInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IndexFormat));
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

  public void initDoneDate(Timestamp value){
     this.initProperty(S_DoneDate,value);
  }
  public  void setDoneDate(Timestamp value){
     this.set(S_DoneDate,value);
  }
  public  void setDoneDateNull(){
     this.set(S_DoneDate,null);
  }

  public Timestamp getDoneDate(){
        return DataType.getAsDateTime(this.get(S_DoneDate));
  
  }
  public Timestamp getDoneDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_DoneDate));
      }

  public void initIndexId(int value){
     this.initProperty(S_IndexId,new Integer(value));
  }
  public  void setIndexId(int value){
     this.set(S_IndexId,new Integer(value));
  }
  public  void setIndexIdNull(){
     this.set(S_IndexId,null);
  }

  public int getIndexId(){
        return DataType.getAsInt(this.get(S_IndexId));
  
  }
  public int getIndexIdInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_IndexId));
      }

  public void initIndexTypeId(int value){
     this.initProperty(S_IndexTypeId,new Integer(value));
  }
  public  void setIndexTypeId(int value){
     this.set(S_IndexTypeId,new Integer(value));
  }
  public  void setIndexTypeIdNull(){
     this.set(S_IndexTypeId,null);
  }

  public int getIndexTypeId(){
        return DataType.getAsInt(this.get(S_IndexTypeId));
  
  }
  public int getIndexTypeIdInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_IndexTypeId));
      }

  public void initIndexUnit(String value){
     this.initProperty(S_IndexUnit,value);
  }
  public  void setIndexUnit(String value){
     this.set(S_IndexUnit,value);
  }
  public  void setIndexUnitNull(){
     this.set(S_IndexUnit,null);
  }

  public String getIndexUnit(){
       return DataType.getAsString(this.get(S_IndexUnit));
  
  }
  public String getIndexUnitInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IndexUnit));
      }

  public void initIndexCode(String value){
     this.initProperty(S_IndexCode,value);
  }
  public  void setIndexCode(String value){
     this.set(S_IndexCode,value);
  }
  public  void setIndexCodeNull(){
     this.set(S_IndexCode,null);
  }

  public String getIndexCode(){
       return DataType.getAsString(this.get(S_IndexCode));
  
  }
  public String getIndexCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IndexCode));
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

  public void initIndexName(String value){
     this.initProperty(S_IndexName,value);
  }
  public  void setIndexName(String value){
     this.set(S_IndexName,value);
  }
  public  void setIndexNameNull(){
     this.set(S_IndexName,null);
  }

  public String getIndexName(){
       return DataType.getAsString(this.get(S_IndexName));
  
  }
  public String getIndexNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IndexName));
      }

  public void initIndexExpr(String value){
     this.initProperty(S_IndexExpr,value);
  }
  public  void setIndexExpr(String value){
     this.set(S_IndexExpr,value);
  }
  public  void setIndexExprNull(){
     this.set(S_IndexExpr,null);
  }

  public String getIndexExpr(){
       return DataType.getAsString(this.get(S_IndexExpr));
  
  }
  public String getIndexExprInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IndexExpr));
      }


 
 }

