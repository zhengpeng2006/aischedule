package com.asiainfo.monitor.busi.exe.task.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPExecValue;

public class BOAIMonPExecBean extends DataContainer implements DataContainerInterface,IBOAIMonPExecValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPExec";



  public final static  String S_State = "STATE";
  public final static  String S_ExecId = "EXEC_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Name = "NAME";
  public final static  String S_EType = "E_TYPE";
  public final static  String S_Expr = "EXPR";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonPExecBean() throws AIException{
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

  public void initExecId(long value){
     this.initProperty(S_ExecId,new Long(value));
  }
  public  void setExecId(long value){
     this.set(S_ExecId,new Long(value));
  }
  public  void setExecIdNull(){
     this.set(S_ExecId,null);
  }

  public long getExecId(){
        return DataType.getAsLong(this.get(S_ExecId));
  
  }
  public long getExecIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ExecId));
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

  public void initEType(String value){
     this.initProperty(S_EType,value);
  }
  public  void setEType(String value){
     this.set(S_EType,value);
  }
  public  void setETypeNull(){
     this.set(S_EType,null);
  }

  public String getEType(){
       return DataType.getAsString(this.get(S_EType));
  
  }
  public String getETypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_EType));
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

