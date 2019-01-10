package com.asiainfo.monitor.exeframe.monitorItem.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.monitorItem.ivalues.*;

public class BOAIMonBatchBean extends DataContainer implements DataContainerInterface,IBOAIMonBatchValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.monitorItem.bo.BOAIMonBatch";



  public final static  String S_BatchId = "BATCH_ID";
  public final static  String S_TaskCode = "TASK_CODE";
  public final static  String S_Sn = "SN";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonBatchBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initBatchId(long value){
     this.initProperty(S_BatchId,new Long(value));
  }
  public  void setBatchId(long value){
     this.set(S_BatchId,new Long(value));
  }
  public  void setBatchIdNull(){
     this.set(S_BatchId,null);
  }

  public long getBatchId(){
        return DataType.getAsLong(this.get(S_BatchId));
  
  }
  public long getBatchIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_BatchId));
      }

  public void initTaskCode(String value){
     this.initProperty(S_TaskCode,value);
  }
  public  void setTaskCode(String value){
     this.set(S_TaskCode,value);
  }
  public  void setTaskCodeNull(){
     this.set(S_TaskCode,null);
  }

  public String getTaskCode(){
       return DataType.getAsString(this.get(S_TaskCode));
  
  }
  public String getTaskCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskCode));
      }

  public void initSn(long value){
     this.initProperty(S_Sn,new Long(value));
  }
  public  void setSn(long value){
     this.set(S_Sn,new Long(value));
  }
  public  void setSnNull(){
     this.set(S_Sn,null);
  }

  public long getSn(){
        return DataType.getAsLong(this.get(S_Sn));
  
  }
  public long getSnInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Sn));
      }


 
 }

