package com.asiainfo.monitor.exeframe.configmgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.*;

public class BOAIMonConModeBean extends DataContainer implements DataContainerInterface,IBOAIMonConModeValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonConMode";



  public final static  String S_ConId = "CON_ID";
  public final static  String S_ConType = "CON_TYPE";
  public final static  String S_ConPort = "CON_PORT";
  public final static  String S_State = "STATE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_Remark = "REMARK";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonConModeBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initConId(long value){
     this.initProperty(S_ConId,new Long(value));
  }
  public  void setConId(long value){
     this.set(S_ConId,new Long(value));
  }
  public  void setConIdNull(){
     this.set(S_ConId,null);
  }

  public long getConId(){
        return DataType.getAsLong(this.get(S_ConId));
  
  }
  public long getConIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ConId));
      }

  public void initConType(String value){
     this.initProperty(S_ConType,value);
  }
  public  void setConType(String value){
     this.set(S_ConType,value);
  }
  public  void setConTypeNull(){
     this.set(S_ConType,null);
  }

  public String getConType(){
       return DataType.getAsString(this.get(S_ConType));
  
  }
  public String getConTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ConType));
      }

  public void initConPort(short value){
     this.initProperty(S_ConPort,new Short(value));
  }
  public  void setConPort(short value){
     this.set(S_ConPort,new Short(value));
  }
  public  void setConPortNull(){
     this.set(S_ConPort,null);
  }

  public short getConPort(){
        return DataType.getAsShort(this.get(S_ConPort));
  
  }
  public short getConPortInitialValue(){
        return DataType.getAsShort(this.getOldObj(S_ConPort));
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

  public void initRemark(String value){
     this.initProperty(S_Remark,value);
  }
  public  void setRemark(String value){
     this.set(S_Remark,value);
  }
  public  void setRemarkNull(){
     this.set(S_Remark,null);
  }

  public String getRemark(){
       return DataType.getAsString(this.get(S_Remark));
  
  }
  public String getRemarkInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Remark));
      }


 
 }

