package com.asiainfo.monitor.busi.exe.task.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWPersonValue;

public class BOAIMonWPersonBean extends DataContainer implements DataContainerInterface,IBOAIMonWPersonValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWPerson";



  public final static  String S_State = "STATE";
  public final static  String S_PersonId = "PERSON_ID";
  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Name = "NAME";
  public final static  String S_Region = "REGION";
  public final static  String S_Phonenum = "PHONENUM";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonWPersonBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
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

  public void initPersonId(long value){
     this.initProperty(S_PersonId,new Long(value));
  }
  public  void setPersonId(long value){
     this.set(S_PersonId,new Long(value));
  }
  public  void setPersonIdNull(){
     this.set(S_PersonId,null);
  }

  public long getPersonId(){
        return DataType.getAsLong(this.get(S_PersonId));
  
  }
  public long getPersonIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_PersonId));
      }

  public void initRegionId(long value){
     this.initProperty(S_RegionId,new Long(value));
  }
  public  void setRegionId(long value){
     this.set(S_RegionId,new Long(value));
  }
  public  void setRegionIdNull(){
     this.set(S_RegionId,null);
  }

  public long getRegionId(){
        return DataType.getAsLong(this.get(S_RegionId));
  
  }
  public long getRegionIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RegionId));
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

  public void initName(String value){
     this.initProperty(S_Name,value);
  }
  public  void setName(String value){
     this.set(S_Name,value);
  }
  public  void setNameNull(){
     this.set(S_Name,null);
  }

  public String getName(){
       return DataType.getAsString(this.get(S_Name));
  
  }
  public String getNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Name));
      }

  public void initRegion(String value){
     this.initProperty(S_Region,value);
  }
  public  void setRegion(String value){
     this.set(S_Region,value);
  }
  public  void setRegionNull(){
     this.set(S_Region,null);
  }

  public String getRegion(){
       return DataType.getAsString(this.get(S_Region));
  
  }
  public String getRegionInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Region));
      }

  public void initPhonenum(String value){
     this.initProperty(S_Phonenum,value);
  }
  public  void setPhonenum(String value){
     this.set(S_Phonenum,value);
  }
  public  void setPhonenumNull(){
     this.set(S_Phonenum,null);
  }

  public String getPhonenum(){
       return DataType.getAsString(this.get(S_Phonenum));
  
  }
  public String getPhonenumInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Phonenum));
      }


 
 }

