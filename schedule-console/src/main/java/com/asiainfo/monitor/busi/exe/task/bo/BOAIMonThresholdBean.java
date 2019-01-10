package com.asiainfo.monitor.busi.exe.task.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonThresholdValue;

public class BOAIMonThresholdBean extends DataContainer implements DataContainerInterface,IBOAIMonThresholdValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonThreshold";



  public final static  String S_State = "STATE";
  public final static  String S_Expr5 = "EXPR5";
  public final static  String S_Expr1 = "EXPR1";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ThresholdName = "THRESHOLD_NAME";
  public final static  String S_Expr2 = "EXPR2";
  public final static  String S_ExpiryDays = "EXPIRY_DAYS";
  public final static  String S_Expr3 = "EXPR3";
  public final static  String S_Expr4 = "EXPR4";
  public final static  String S_ThresholdId = "THRESHOLD_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonThresholdBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 @Override
public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initState(String value){
     this.initProperty(S_State,value);
  }
  @Override
public  void setState(String value){
     this.set(S_State,value);
  }
  public  void setStateNull(){
     this.set(S_State,null);
  }

  @Override
public String getState(){
       return DataType.getAsString(this.get(S_State));
  
  }
  public String getStateInitialValue(){
        return DataType.getAsString(this.getOldObj(S_State));
      }

  public void initExpr5(String value){
     this.initProperty(S_Expr5,value);
  }
  @Override
public  void setExpr5(String value){
     this.set(S_Expr5,value);
  }
  public  void setExpr5Null(){
     this.set(S_Expr5,null);
  }

  @Override
public String getExpr5(){
       return DataType.getAsString(this.get(S_Expr5));
  
  }
  public String getExpr5InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr5));
      }

  public void initExpr1(String value){
     this.initProperty(S_Expr1,value);
  }
  @Override
public  void setExpr1(String value){
     this.set(S_Expr1,value);
  }
  public  void setExpr1Null(){
     this.set(S_Expr1,null);
  }

  @Override
public String getExpr1(){
       return DataType.getAsString(this.get(S_Expr1));
  
  }
  public String getExpr1InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr1));
      }

  public void initRemarks(String value){
     this.initProperty(S_Remarks,value);
  }
  @Override
public  void setRemarks(String value){
     this.set(S_Remarks,value);
  }
  public  void setRemarksNull(){
     this.set(S_Remarks,null);
  }

  @Override
public String getRemarks(){
       return DataType.getAsString(this.get(S_Remarks));
  
  }
  public String getRemarksInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Remarks));
      }

  public void initThresholdName(String value){
     this.initProperty(S_ThresholdName,value);
  }
  @Override
public  void setThresholdName(String value){
     this.set(S_ThresholdName,value);
  }
  public  void setThresholdNameNull(){
     this.set(S_ThresholdName,null);
  }

  @Override
public String getThresholdName(){
       return DataType.getAsString(this.get(S_ThresholdName));
  
  }
  public String getThresholdNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ThresholdName));
      }

  public void initExpr2(String value){
     this.initProperty(S_Expr2,value);
  }
  @Override
public  void setExpr2(String value){
     this.set(S_Expr2,value);
  }
  public  void setExpr2Null(){
     this.set(S_Expr2,null);
  }

  @Override
public String getExpr2(){
       return DataType.getAsString(this.get(S_Expr2));
  
  }
  public String getExpr2InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr2));
      }

  public void initExpiryDays(int value){
     this.initProperty(S_ExpiryDays,new Integer(value));
  }
  @Override
public  void setExpiryDays(int value){
     this.set(S_ExpiryDays,new Integer(value));
  }
  public  void setExpiryDaysNull(){
     this.set(S_ExpiryDays,null);
  }

  @Override
public int getExpiryDays(){
        return DataType.getAsInt(this.get(S_ExpiryDays));
  
  }
  public int getExpiryDaysInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_ExpiryDays));
      }

  public void initExpr3(String value){
     this.initProperty(S_Expr3,value);
  }
  @Override
public  void setExpr3(String value){
     this.set(S_Expr3,value);
  }
  public  void setExpr3Null(){
     this.set(S_Expr3,null);
  }

  @Override
public String getExpr3(){
       return DataType.getAsString(this.get(S_Expr3));
  
  }
  public String getExpr3InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr3));
      }

  public void initExpr4(String value){
     this.initProperty(S_Expr4,value);
  }
  @Override
public  void setExpr4(String value){
     this.set(S_Expr4,value);
  }
  public  void setExpr4Null(){
     this.set(S_Expr4,null);
  }

  @Override
public String getExpr4(){
       return DataType.getAsString(this.get(S_Expr4));
  
  }
  public String getExpr4InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr4));
      }

  public void initThresholdId(long value){
     this.initProperty(S_ThresholdId,new Long(value));
  }
  @Override
public  void setThresholdId(long value){
     this.set(S_ThresholdId,new Long(value));
  }
  public  void setThresholdIdNull(){
     this.set(S_ThresholdId,null);
  }

  @Override
public long getThresholdId(){
        return DataType.getAsLong(this.get(S_ThresholdId));
  
  }
  public long getThresholdIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ThresholdId));
      }


 
 }

