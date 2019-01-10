package com.asiainfo.emend_yh.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.emend_yh.ivalues.*;

public class BOAmTaskRelyBean extends DataContainer implements DataContainerInterface,IBOAmTaskRelyValue{

  private static String  m_boName = "com.asiainfo.emend_yh.bo.BOAmTaskRely";



  public final static  String S_Ext3 = "EXT3";
  public final static  String S_TaskGroup = "TASK_GROUP";
  public final static  String S_Ext2 = "EXT2";
  public final static  String S_Ext1 = "EXT1";
  public final static  String S_IsAllregionRely = "IS_ALLREGION_RELY";
  public final static  String S_PreTaskCode = "PRE_TASK_CODE";
  public final static  String S_IsHandle = "IS_HANDLE";
  public final static  String S_CurTaskCode = "CUR_TASK_CODE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAmTaskRelyBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
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

  public void initTaskGroup(String value){
     this.initProperty(S_TaskGroup,value);
  }
  public  void setTaskGroup(String value){
     this.set(S_TaskGroup,value);
  }
  public  void setTaskGroupNull(){
     this.set(S_TaskGroup,null);
  }

  public String getTaskGroup(){
       return DataType.getAsString(this.get(S_TaskGroup));
  
  }
  public String getTaskGroupInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskGroup));
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

  public void initIsAllregionRely(String value){
     this.initProperty(S_IsAllregionRely,value);
  }
  public  void setIsAllregionRely(String value){
     this.set(S_IsAllregionRely,value);
  }
  public  void setIsAllregionRelyNull(){
     this.set(S_IsAllregionRely,null);
  }

  public String getIsAllregionRely(){
       return DataType.getAsString(this.get(S_IsAllregionRely));
  
  }
  public String getIsAllregionRelyInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IsAllregionRely));
      }

  public void initPreTaskCode(String value){
     this.initProperty(S_PreTaskCode,value);
  }
  public  void setPreTaskCode(String value){
     this.set(S_PreTaskCode,value);
  }
  public  void setPreTaskCodeNull(){
     this.set(S_PreTaskCode,null);
  }

  public String getPreTaskCode(){
       return DataType.getAsString(this.get(S_PreTaskCode));
  
  }
  public String getPreTaskCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_PreTaskCode));
      }

  public void initIsHandle(String value){
     this.initProperty(S_IsHandle,value);
  }
  public  void setIsHandle(String value){
     this.set(S_IsHandle,value);
  }
  public  void setIsHandleNull(){
     this.set(S_IsHandle,null);
  }

  public String getIsHandle(){
       return DataType.getAsString(this.get(S_IsHandle));
  
  }
  public String getIsHandleInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IsHandle));
      }

  public void initCurTaskCode(String value){
     this.initProperty(S_CurTaskCode,value);
  }
  public  void setCurTaskCode(String value){
     this.set(S_CurTaskCode,value);
  }
  public  void setCurTaskCodeNull(){
     this.set(S_CurTaskCode,null);
  }

  public String getCurTaskCode(){
       return DataType.getAsString(this.get(S_CurTaskCode));
  
  }
  public String getCurTaskCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CurTaskCode));
      }


 
 }

