package com.asiainfo.batchwriteoff.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.*;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBillingCycleValue;

import java.sql.Timestamp;

public class BOBsBillingCycleBean extends DataContainer implements DataContainerInterface,IBOBsBillingCycleValue {

  private static String  m_boName = "com.asiainfo.crm.common.bo.BOBsBillingCycle";



  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_CtrlFlag = "CTRL_FLAG";
  public final static  String S_CtrlDate = "CTRL_DATE";
  public final static  String S_StateDate = "STATE_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_BillingCycleId = "BILLING_CYCLE_ID";
  public final static  String S_BillingCycleTypeId = "BILLING_CYCLE_TYPE_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOBsBillingCycleBean() throws AIException {
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException {
   return S_TYPE;
 }

 public void setObjectType(ObjectType value) throws AIException {
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initRegionId(String value){
     this.initProperty(S_RegionId,value);
  }
  public  void setRegionId(String value){
     this.set(S_RegionId,value);
  }
  public  void setRegionIdNull(){
     this.set(S_RegionId,null);
  }

  public String getRegionId(){
       return DataType.getAsString(this.get(S_RegionId));
  
  }
  public String getRegionIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RegionId));
      }

  public void initCtrlFlag(String value){
     this.initProperty(S_CtrlFlag,value);
  }
  public  void setCtrlFlag(String value){
     this.set(S_CtrlFlag,value);
  }
  public  void setCtrlFlagNull(){
     this.set(S_CtrlFlag,null);
  }

  public String getCtrlFlag(){
       return DataType.getAsString(this.get(S_CtrlFlag));
  
  }
  public String getCtrlFlagInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CtrlFlag));
      }

  public void initCtrlDate(Timestamp value){
     this.initProperty(S_CtrlDate,value);
  }
  public  void setCtrlDate(Timestamp value){
     this.set(S_CtrlDate,value);
  }
  public  void setCtrlDateNull(){
     this.set(S_CtrlDate,null);
  }

  public Timestamp getCtrlDate(){
        return DataType.getAsDateTime(this.get(S_CtrlDate));
  
  }
  public Timestamp getCtrlDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_CtrlDate));
      }

  public void initStateDate(Timestamp value){
     this.initProperty(S_StateDate,value);
  }
  public  void setStateDate(Timestamp value){
     this.set(S_StateDate,value);
  }
  public  void setStateDateNull(){
     this.set(S_StateDate,null);
  }

  public Timestamp getStateDate(){
        return DataType.getAsDateTime(this.get(S_StateDate));
  
  }
  public Timestamp getStateDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_StateDate));
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

  public void initBillingCycleId(long value){
     this.initProperty(S_BillingCycleId,new Long(value));
  }
  public  void setBillingCycleId(long value){
     this.set(S_BillingCycleId,new Long(value));
  }
  public  void setBillingCycleIdNull(){
     this.set(S_BillingCycleId,null);
  }

  public long getBillingCycleId(){
        return DataType.getAsLong(this.get(S_BillingCycleId));
  
  }
  public long getBillingCycleIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_BillingCycleId));
      }

  public void initBillingCycleTypeId(int value){
     this.initProperty(S_BillingCycleTypeId,new Integer(value));
  }
  public  void setBillingCycleTypeId(int value){
     this.set(S_BillingCycleTypeId,new Integer(value));
  }
  public  void setBillingCycleTypeIdNull(){
     this.set(S_BillingCycleTypeId,null);
  }

  public int getBillingCycleTypeId(){
        return DataType.getAsInt(this.get(S_BillingCycleTypeId));
  
  }
  public int getBillingCycleTypeIdInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_BillingCycleTypeId));
      }


 
 }

