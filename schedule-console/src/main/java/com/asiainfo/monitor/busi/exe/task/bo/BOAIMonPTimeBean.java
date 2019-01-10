package com.asiainfo.monitor.busi.exe.task.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTimeValue;

public class BOAIMonPTimeBean extends DataContainer implements DataContainerInterface,IBOAIMonPTimeValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPTime";



  public final static  String S_TType = "T_TYPE";
  public final static  String S_State = "STATE";
  public final static  String S_TimeId = "TIME_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Expr = "EXPR";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonPTimeBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initTType(String value){
     this.initProperty(S_TType,value);
  }
  public  void setTType(String value){
     this.set(S_TType,value);
  }
  public  void setTTypeNull(){
     this.set(S_TType,null);
  }

  public String getTType(){
       return DataType.getAsString(this.get(S_TType));
  
  }
  public String getTTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TType));
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

  public void initTimeId(long value){
     this.initProperty(S_TimeId,new Long(value));
  }
  public  void setTimeId(long value){
     this.set(S_TimeId,new Long(value));
  }
  public  void setTimeIdNull(){
     this.set(S_TimeId,null);
  }

  public long getTimeId(){
        return DataType.getAsLong(this.get(S_TimeId));
  
  }
  public long getTimeIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TimeId));
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

  public void initExpr(String value){
     this.initProperty(S_Expr,value);
  }
  public  void setExpr(String value){
     this.set(S_Expr,value);
  }
  public  void setExprNull(){
     this.set(S_Expr,null);
  }

  public String getExpr(){
       return DataType.getAsString(this.get(S_Expr));
  
  }
  public String getExprInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr));
      }


 
 }

