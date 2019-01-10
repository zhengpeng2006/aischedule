package com.asiainfo.monitor.busi.exe.task.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoFilterValue;

public class BOAIMonPInfoFilterBean extends DataContainer implements DataContainerInterface,IBOAIMonPInfoFilterValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoFilter";



  public final static  String S_State = "STATE";
  public final static  String S_FilterId = "FILTER_ID";
  public final static  String S_Expr4 = "EXPR4";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_Expr3 = "EXPR3";
  public final static  String S_Expr2 = "EXPR2";
  public final static  String S_Expr1 = "EXPR1";
  public final static  String S_FilterName = "FILTER_NAME";
  public final static  String S_FilterDesc = "FILTER_DESC";
  public final static  String S_Expr5 = "EXPR5";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonPInfoFilterBean() throws AIException{
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

  public void initFilterId(long value){
     this.initProperty(S_FilterId,new Long(value));
  }
  public  void setFilterId(long value){
     this.set(S_FilterId,new Long(value));
  }
  public  void setFilterIdNull(){
     this.set(S_FilterId,null);
  }

  public long getFilterId(){
        return DataType.getAsLong(this.get(S_FilterId));
  
  }
  public long getFilterIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_FilterId));
      }

  public void initExpr4(String value){
     this.initProperty(S_Expr4,value);
  }
  public  void setExpr4(String value){
     this.set(S_Expr4,value);
  }
  public  void setExpr4Null(){
     this.set(S_Expr4,null);
  }

  public String getExpr4(){
       return DataType.getAsString(this.get(S_Expr4));
  
  }
  public String getExpr4InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr4));
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

  public void initExpr3(String value){
     this.initProperty(S_Expr3,value);
  }
  public  void setExpr3(String value){
     this.set(S_Expr3,value);
  }
  public  void setExpr3Null(){
     this.set(S_Expr3,null);
  }

  public String getExpr3(){
       return DataType.getAsString(this.get(S_Expr3));
  
  }
  public String getExpr3InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr3));
      }

  public void initExpr2(String value){
     this.initProperty(S_Expr2,value);
  }
  public  void setExpr2(String value){
     this.set(S_Expr2,value);
  }
  public  void setExpr2Null(){
     this.set(S_Expr2,null);
  }

  public String getExpr2(){
       return DataType.getAsString(this.get(S_Expr2));
  
  }
  public String getExpr2InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr2));
      }

  public void initExpr1(String value){
     this.initProperty(S_Expr1,value);
  }
  public  void setExpr1(String value){
     this.set(S_Expr1,value);
  }
  public  void setExpr1Null(){
     this.set(S_Expr1,null);
  }

  public String getExpr1(){
       return DataType.getAsString(this.get(S_Expr1));
  
  }
  public String getExpr1InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr1));
      }

  public void initFilterName(String value){
     this.initProperty(S_FilterName,value);
  }
  public  void setFilterName(String value){
     this.set(S_FilterName,value);
  }
  public  void setFilterNameNull(){
     this.set(S_FilterName,null);
  }

  public String getFilterName(){
       return DataType.getAsString(this.get(S_FilterName));
  
  }
  public String getFilterNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_FilterName));
      }

  public void initFilterDesc(String value){
     this.initProperty(S_FilterDesc,value);
  }
  public  void setFilterDesc(String value){
     this.set(S_FilterDesc,value);
  }
  public  void setFilterDescNull(){
     this.set(S_FilterDesc,null);
  }

  public String getFilterDesc(){
       return DataType.getAsString(this.get(S_FilterDesc));
  
  }
  public String getFilterDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_FilterDesc));
      }

  public void initExpr5(String value){
     this.initProperty(S_Expr5,value);
  }
  public  void setExpr5(String value){
     this.set(S_Expr5,value);
  }
  public  void setExpr5Null(){
     this.set(S_Expr5,null);
  }

  public String getExpr5(){
       return DataType.getAsString(this.get(S_Expr5));
  
  }
  public String getExpr5InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr5));
      }


 
 }

