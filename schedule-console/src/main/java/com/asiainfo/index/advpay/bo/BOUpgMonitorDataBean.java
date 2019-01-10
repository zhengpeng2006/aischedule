package com.asiainfo.index.advpay.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.index.advpay.ivalues.*;

public class BOUpgMonitorDataBean extends DataContainer implements DataContainerInterface,IBOUpgMonitorDataValue{

  private static String  m_boName = "com.asiainfo.index.advpay.bo.BOUpgMonitorData";



  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_ScaValue2 = "SCA_VALUE2";
  public final static  String S_ScaValue1 = "SCA_VALUE1";
  public final static  String S_State = "STATE";
  public final static  String S_MonitorId = "MONITOR_ID";
  public final static  String S_SeqId = "SEQ_ID";
  public final static  String S_DimValue2 = "DIM_VALUE2";
  public final static  String S_DimValue3 = "DIM_VALUE3";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_DimValue4 = "DIM_VALUE4";
  public final static  String S_DimValue5 = "DIM_VALUE5";
  public final static  String S_DimValue6 = "DIM_VALUE6";
  public final static  String S_DimValue7 = "DIM_VALUE7";
  public final static  String S_DimValue8 = "DIM_VALUE8";
  public final static  String S_ScaValue8 = "SCA_VALUE8";
  public final static  String S_ScaValue7 = "SCA_VALUE7";
  public final static  String S_ScaValue4 = "SCA_VALUE4";
  public final static  String S_ScaValue3 = "SCA_VALUE3";
  public final static  String S_ScaValue6 = "SCA_VALUE6";
  public final static  String S_DimValue1 = "DIM_VALUE1";
  public final static  String S_ScaValue5 = "SCA_VALUE5";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOUpgMonitorDataBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
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

  public void initScaValue2(String value){
     this.initProperty(S_ScaValue2,value);
  }
  public  void setScaValue2(String value){
     this.set(S_ScaValue2,value);
  }
  public  void setScaValue2Null(){
     this.set(S_ScaValue2,null);
  }

  public String getScaValue2(){
       return DataType.getAsString(this.get(S_ScaValue2));
  
  }
  public String getScaValue2InitialValue(){
        return DataType.getAsString(this.getOldObj(S_ScaValue2));
      }

  public void initScaValue1(String value){
     this.initProperty(S_ScaValue1,value);
  }
  public  void setScaValue1(String value){
     this.set(S_ScaValue1,value);
  }
  public  void setScaValue1Null(){
     this.set(S_ScaValue1,null);
  }

  public String getScaValue1(){
       return DataType.getAsString(this.get(S_ScaValue1));
  
  }
  public String getScaValue1InitialValue(){
        return DataType.getAsString(this.getOldObj(S_ScaValue1));
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

  public void initMonitorId(int value){
     this.initProperty(S_MonitorId,new Integer(value));
  }
  public  void setMonitorId(int value){
     this.set(S_MonitorId,new Integer(value));
  }
  public  void setMonitorIdNull(){
     this.set(S_MonitorId,null);
  }

  public int getMonitorId(){
        return DataType.getAsInt(this.get(S_MonitorId));
  
  }
  public int getMonitorIdInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_MonitorId));
      }

  public void initSeqId(long value){
     this.initProperty(S_SeqId,new Long(value));
  }
  public  void setSeqId(long value){
     this.set(S_SeqId,new Long(value));
  }
  public  void setSeqIdNull(){
     this.set(S_SeqId,null);
  }

  public long getSeqId(){
        return DataType.getAsLong(this.get(S_SeqId));
  
  }
  public long getSeqIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_SeqId));
      }

  public void initDimValue2(String value){
     this.initProperty(S_DimValue2,value);
  }
  public  void setDimValue2(String value){
     this.set(S_DimValue2,value);
  }
  public  void setDimValue2Null(){
     this.set(S_DimValue2,null);
  }

  public String getDimValue2(){
       return DataType.getAsString(this.get(S_DimValue2));
  
  }
  public String getDimValue2InitialValue(){
        return DataType.getAsString(this.getOldObj(S_DimValue2));
      }

  public void initDimValue3(String value){
     this.initProperty(S_DimValue3,value);
  }
  public  void setDimValue3(String value){
     this.set(S_DimValue3,value);
  }
  public  void setDimValue3Null(){
     this.set(S_DimValue3,null);
  }

  public String getDimValue3(){
       return DataType.getAsString(this.get(S_DimValue3));
  
  }
  public String getDimValue3InitialValue(){
        return DataType.getAsString(this.getOldObj(S_DimValue3));
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

  public void initDimValue4(String value){
     this.initProperty(S_DimValue4,value);
  }
  public  void setDimValue4(String value){
     this.set(S_DimValue4,value);
  }
  public  void setDimValue4Null(){
     this.set(S_DimValue4,null);
  }

  public String getDimValue4(){
       return DataType.getAsString(this.get(S_DimValue4));
  
  }
  public String getDimValue4InitialValue(){
        return DataType.getAsString(this.getOldObj(S_DimValue4));
      }

  public void initDimValue5(String value){
     this.initProperty(S_DimValue5,value);
  }
  public  void setDimValue5(String value){
     this.set(S_DimValue5,value);
  }
  public  void setDimValue5Null(){
     this.set(S_DimValue5,null);
  }

  public String getDimValue5(){
       return DataType.getAsString(this.get(S_DimValue5));
  
  }
  public String getDimValue5InitialValue(){
        return DataType.getAsString(this.getOldObj(S_DimValue5));
      }

  public void initDimValue6(String value){
     this.initProperty(S_DimValue6,value);
  }
  public  void setDimValue6(String value){
     this.set(S_DimValue6,value);
  }
  public  void setDimValue6Null(){
     this.set(S_DimValue6,null);
  }

  public String getDimValue6(){
       return DataType.getAsString(this.get(S_DimValue6));
  
  }
  public String getDimValue6InitialValue(){
        return DataType.getAsString(this.getOldObj(S_DimValue6));
      }

  public void initDimValue7(String value){
     this.initProperty(S_DimValue7,value);
  }
  public  void setDimValue7(String value){
     this.set(S_DimValue7,value);
  }
  public  void setDimValue7Null(){
     this.set(S_DimValue7,null);
  }

  public String getDimValue7(){
       return DataType.getAsString(this.get(S_DimValue7));
  
  }
  public String getDimValue7InitialValue(){
        return DataType.getAsString(this.getOldObj(S_DimValue7));
      }

  public void initDimValue8(String value){
     this.initProperty(S_DimValue8,value);
  }
  public  void setDimValue8(String value){
     this.set(S_DimValue8,value);
  }
  public  void setDimValue8Null(){
     this.set(S_DimValue8,null);
  }

  public String getDimValue8(){
       return DataType.getAsString(this.get(S_DimValue8));
  
  }
  public String getDimValue8InitialValue(){
        return DataType.getAsString(this.getOldObj(S_DimValue8));
      }

  public void initScaValue8(String value){
     this.initProperty(S_ScaValue8,value);
  }
  public  void setScaValue8(String value){
     this.set(S_ScaValue8,value);
  }
  public  void setScaValue8Null(){
     this.set(S_ScaValue8,null);
  }

  public String getScaValue8(){
       return DataType.getAsString(this.get(S_ScaValue8));
  
  }
  public String getScaValue8InitialValue(){
        return DataType.getAsString(this.getOldObj(S_ScaValue8));
      }

  public void initScaValue7(String value){
     this.initProperty(S_ScaValue7,value);
  }
  public  void setScaValue7(String value){
     this.set(S_ScaValue7,value);
  }
  public  void setScaValue7Null(){
     this.set(S_ScaValue7,null);
  }

  public String getScaValue7(){
       return DataType.getAsString(this.get(S_ScaValue7));
  
  }
  public String getScaValue7InitialValue(){
        return DataType.getAsString(this.getOldObj(S_ScaValue7));
      }

  public void initScaValue4(String value){
     this.initProperty(S_ScaValue4,value);
  }
  public  void setScaValue4(String value){
     this.set(S_ScaValue4,value);
  }
  public  void setScaValue4Null(){
     this.set(S_ScaValue4,null);
  }

  public String getScaValue4(){
       return DataType.getAsString(this.get(S_ScaValue4));
  
  }
  public String getScaValue4InitialValue(){
        return DataType.getAsString(this.getOldObj(S_ScaValue4));
      }

  public void initScaValue3(String value){
     this.initProperty(S_ScaValue3,value);
  }
  public  void setScaValue3(String value){
     this.set(S_ScaValue3,value);
  }
  public  void setScaValue3Null(){
     this.set(S_ScaValue3,null);
  }

  public String getScaValue3(){
       return DataType.getAsString(this.get(S_ScaValue3));
  
  }
  public String getScaValue3InitialValue(){
        return DataType.getAsString(this.getOldObj(S_ScaValue3));
      }

  public void initScaValue6(String value){
     this.initProperty(S_ScaValue6,value);
  }
  public  void setScaValue6(String value){
     this.set(S_ScaValue6,value);
  }
  public  void setScaValue6Null(){
     this.set(S_ScaValue6,null);
  }

  public String getScaValue6(){
       return DataType.getAsString(this.get(S_ScaValue6));
  
  }
  public String getScaValue6InitialValue(){
        return DataType.getAsString(this.getOldObj(S_ScaValue6));
      }

  public void initDimValue1(String value){
     this.initProperty(S_DimValue1,value);
  }
  public  void setDimValue1(String value){
     this.set(S_DimValue1,value);
  }
  public  void setDimValue1Null(){
     this.set(S_DimValue1,null);
  }

  public String getDimValue1(){
       return DataType.getAsString(this.get(S_DimValue1));
  
  }
  public String getDimValue1InitialValue(){
        return DataType.getAsString(this.getOldObj(S_DimValue1));
      }

  public void initScaValue5(String value){
     this.initProperty(S_ScaValue5,value);
  }
  public  void setScaValue5(String value){
     this.set(S_ScaValue5,value);
  }
  public  void setScaValue5Null(){
     this.set(S_ScaValue5,null);
  }

  public String getScaValue5(){
       return DataType.getAsString(this.get(S_ScaValue5));
  
  }
  public String getScaValue5InitialValue(){
        return DataType.getAsString(this.getOldObj(S_ScaValue5));
      }


 
 }

