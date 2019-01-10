package com.asiainfo.monitor.exeframe.configmgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.*;

public class BOAIMonGroupBean extends DataContainer implements DataContainerInterface,IBOAIMonGroupValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroup";



  public final static  String S_GroupId = "GROUP_ID";
  public final static  String S_GroupCode = "GROUP_CODE";
  public final static  String S_GroupName = "GROUP_NAME";
  public final static  String S_GroupDesc = "GROUP_DESC";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_Priv = "PRIV";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonGroupBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initGroupId(long value){
     this.initProperty(S_GroupId,new Long(value));
  }
  public  void setGroupId(long value){
     this.set(S_GroupId,new Long(value));
  }
  public  void setGroupIdNull(){
     this.set(S_GroupId,null);
  }

  public long getGroupId(){
        return DataType.getAsLong(this.get(S_GroupId));
  
  }
  public long getGroupIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_GroupId));
      }

  public void initGroupCode(String value){
     this.initProperty(S_GroupCode,value);
  }
  public  void setGroupCode(String value){
     this.set(S_GroupCode,value);
  }
  public  void setGroupCodeNull(){
     this.set(S_GroupCode,null);
  }

  public String getGroupCode(){
       return DataType.getAsString(this.get(S_GroupCode));
  
  }
  public String getGroupCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_GroupCode));
      }

  public void initGroupName(String value){
     this.initProperty(S_GroupName,value);
  }
  public  void setGroupName(String value){
     this.set(S_GroupName,value);
  }
  public  void setGroupNameNull(){
     this.set(S_GroupName,null);
  }

  public String getGroupName(){
       return DataType.getAsString(this.get(S_GroupName));
  
  }
  public String getGroupNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_GroupName));
      }

  public void initGroupDesc(String value){
     this.initProperty(S_GroupDesc,value);
  }
  public  void setGroupDesc(String value){
     this.set(S_GroupDesc,value);
  }
  public  void setGroupDescNull(){
     this.set(S_GroupDesc,null);
  }

  public String getGroupDesc(){
       return DataType.getAsString(this.get(S_GroupDesc));
  
  }
  public String getGroupDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_GroupDesc));
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

  public void initPriv(String value){
     this.initProperty(S_Priv,value);
  }
  public  void setPriv(String value){
     this.set(S_Priv,value);
  }
  public  void setPrivNull(){
     this.set(S_Priv,null);
  }

  public String getPriv(){
       return DataType.getAsString(this.get(S_Priv));
  
  }
  public String getPrivInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Priv));
      }


 
 }

