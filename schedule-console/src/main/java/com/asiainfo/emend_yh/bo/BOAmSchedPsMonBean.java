package com.asiainfo.emend_yh.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.emend_yh.ivalues.*;

public class BOAmSchedPsMonBean extends DataContainer implements DataContainerInterface,IBOAmSchedPsMonValue{

  private static String  m_boName = "com.asiainfo.emend_yh.bo.BOAmSchedPsMon";



  public final static  String S_Ext4 = "EXT4";
  public final static  String S_Ext3 = "EXT3";
  public final static  String S_ModValue = "MOD_VALUE";
  public final static  String S_Ext2 = "EXT2";
  public final static  String S_Ext1 = "EXT1";
  public final static  String S_BsBillingCycle = "BS_BILLING_CYCLE";
  public final static  String S_Mod = "MOD";
  public final static  String S_Ext5 = "EXT5";
  public final static  String S_TaskName = "TASK_NAME";
  public final static  String S_Condition = "CONDITION";
  public final static  String S_State = "STATE";
  public final static  String S_TaskCode = "TASK_CODE";
  public final static  String S_RegionId = "REGION_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAmSchedPsMonBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initExt4(String value){
     this.initProperty(S_Ext4,value);
  }
  public  void setExt4(String value){
     this.set(S_Ext4,value);
  }
  public  void setExt4Null(){
     this.set(S_Ext4,null);
  }

  public String getExt4(){
       return DataType.getAsString(this.get(S_Ext4));
  
  }
  public String getExt4InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Ext4));
      }

  public void initExt3(String value){
     this.initProperty(S_Ext3,value);
  }
  public  void setExt3(String value){
     this.set(S_Ext3,value);
  }
  public  void setExt3Null(){
     this.set(S_Ext3,null);
  }

  public String getExt3(){
       return DataType.getAsString(this.get(S_Ext3));
  
  }
  public String getExt3InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Ext3));
      }

  public void initModValue(long value){
     this.initProperty(S_ModValue,new Long(value));
  }
  public  void setModValue(long value){
     this.set(S_ModValue,new Long(value));
  }
  public  void setModValueNull(){
     this.set(S_ModValue,null);
  }

  public long getModValue(){
        return DataType.getAsLong(this.get(S_ModValue));
  
  }
  public long getModValueInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ModValue));
      }

  public void initExt2(String value){
     this.initProperty(S_Ext2,value);
  }
  public  void setExt2(String value){
     this.set(S_Ext2,value);
  }
  public  void setExt2Null(){
     this.set(S_Ext2,null);
  }

  public String getExt2(){
       return DataType.getAsString(this.get(S_Ext2));
  
  }
  public String getExt2InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Ext2));
      }

  public void initExt1(String value){
     this.initProperty(S_Ext1,value);
  }
  public  void setExt1(String value){
     this.set(S_Ext1,value);
  }
  public  void setExt1Null(){
     this.set(S_Ext1,null);
  }

  public String getExt1(){
       return DataType.getAsString(this.get(S_Ext1));
  
  }
  public String getExt1InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Ext1));
      }

  public void initBsBillingCycle(long value){
     this.initProperty(S_BsBillingCycle,new Long(value));
  }
  public  void setBsBillingCycle(long value){
     this.set(S_BsBillingCycle,new Long(value));
  }
  public  void setBsBillingCycleNull(){
     this.set(S_BsBillingCycle,null);
  }

  public long getBsBillingCycle(){
        return DataType.getAsLong(this.get(S_BsBillingCycle));
  
  }
  public long getBsBillingCycleInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_BsBillingCycle));
      }

  public void initMod(long value){
     this.initProperty(S_Mod,new Long(value));
  }
  public  void setMod(long value){
     this.set(S_Mod,new Long(value));
  }
  public  void setModNull(){
     this.set(S_Mod,null);
  }

  public long getMod(){
        return DataType.getAsLong(this.get(S_Mod));
  
  }
  public long getModInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Mod));
      }

  public void initExt5(String value){
     this.initProperty(S_Ext5,value);
  }
  public  void setExt5(String value){
     this.set(S_Ext5,value);
  }
  public  void setExt5Null(){
     this.set(S_Ext5,null);
  }

  public String getExt5(){
       return DataType.getAsString(this.get(S_Ext5));
  
  }
  public String getExt5InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Ext5));
      }

  public void initTaskName(String value){
     this.initProperty(S_TaskName,value);
  }
  public  void setTaskName(String value){
     this.set(S_TaskName,value);
  }
  public  void setTaskNameNull(){
     this.set(S_TaskName,null);
  }

  public String getTaskName(){
       return DataType.getAsString(this.get(S_TaskName));
  
  }
  public String getTaskNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskName));
      }

  public void initCondition(String value){
     this.initProperty(S_Condition,value);
  }
  public  void setCondition(String value){
     this.set(S_Condition,value);
  }
  public  void setConditionNull(){
     this.set(S_Condition,null);
  }

  public String getCondition(){
       return DataType.getAsString(this.get(S_Condition));
  
  }
  public String getConditionInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Condition));
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


 
 }

