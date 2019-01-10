package com.asiainfo.monitor.exeframe.configmgr.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.*;

public class BOAIMonVerifyBean extends DataContainer implements DataContainerInterface,IBOAIMonVerifyValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonVerify";



  public final static  String S_VerifyType = "VERIFY_TYPE";
  public final static  String S_Rule = "RULE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonVerifyBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   throw new AIException("Cannot reset ObjectType");
 }


  public void initVerifyType(String value){
     this.initProperty(S_VerifyType,value);
  }
  public  void setVerifyType(String value){
     this.set(S_VerifyType,value);
  }
  public  void setVerifyTypeNull(){
     this.set(S_VerifyType,null);
  }

  public String getVerifyType(){
       return DataType.getAsString(this.get(S_VerifyType));
  
  }
  public String getVerifyTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_VerifyType));
      }

  public void initRule(String value){
     this.initProperty(S_Rule,value);
  }
  public  void setRule(String value){
     this.set(S_Rule,value);
  }
  public  void setRuleNull(){
     this.set(S_Rule,null);
  }

  public String getRule(){
       return DataType.getAsString(this.get(S_Rule));
  
  }
  public String getRuleInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Rule));
      }


 
 }

