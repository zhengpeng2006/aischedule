package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgAnalyseDefineValue;

public class BOAIMonColgAnalyseDefineBean extends DataContainer implements DataContainerInterface,IBOAIMonColgAnalyseDefineValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonColgAnalyseDefine";



  public final static  String S_DefineDesc = "DEFINE_DESC";
  public final static  String S_State = "STATE";
  public final static  String S_AnalyseMethod = "ANALYSE_METHOD";
  public final static  String S_AnalyseType = "ANALYSE_TYPE";
  public final static  String S_DefineId = "DEFINE_ID";
  public final static  String S_AnalyseId = "ANALYSE_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_AnalyseClass = "ANALYSE_CLASS";
  public final static  String S_AnalyseCode = "ANALYSE_CODE";
  public final static  String S_DefineName = "DEFINE_NAME";
  public final static  String S_DefineCode = "DEFINE_CODE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonColgAnalyseDefineBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initDefineDesc(String value){
     this.initProperty(S_DefineDesc,value);
  }
  public  void setDefineDesc(String value){
     this.set(S_DefineDesc,value);
  }
  public  void setDefineDescNull(){
     this.set(S_DefineDesc,null);
  }

  public String getDefineDesc(){
       return DataType.getAsString(this.get(S_DefineDesc));
  
  }
  public String getDefineDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DefineDesc));
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

  public void initAnalyseMethod(String value){
     this.initProperty(S_AnalyseMethod,value);
  }
  public  void setAnalyseMethod(String value){
     this.set(S_AnalyseMethod,value);
  }
  public  void setAnalyseMethodNull(){
     this.set(S_AnalyseMethod,null);
  }

  public String getAnalyseMethod(){
       return DataType.getAsString(this.get(S_AnalyseMethod));
  
  }
  public String getAnalyseMethodInitialValue(){
        return DataType.getAsString(this.getOldObj(S_AnalyseMethod));
      }

  public void initAnalyseType(int value){
     this.initProperty(S_AnalyseType,new Integer(value));
  }
  public  void setAnalyseType(int value){
     this.set(S_AnalyseType,new Integer(value));
  }
  public  void setAnalyseTypeNull(){
     this.set(S_AnalyseType,null);
  }

  public int getAnalyseType(){
        return DataType.getAsInt(this.get(S_AnalyseType));
  
  }
  public int getAnalyseTypeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_AnalyseType));
      }

  public void initDefineId(long value){
     this.initProperty(S_DefineId,new Long(value));
  }
  public  void setDefineId(long value){
     this.set(S_DefineId,new Long(value));
  }
  public  void setDefineIdNull(){
     this.set(S_DefineId,null);
  }

  public long getDefineId(){
        return DataType.getAsLong(this.get(S_DefineId));
  
  }
  public long getDefineIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_DefineId));
      }

  public void initAnalyseId(long value){
     this.initProperty(S_AnalyseId,new Long(value));
  }
  public  void setAnalyseId(long value){
     this.set(S_AnalyseId,new Long(value));
  }
  public  void setAnalyseIdNull(){
     this.set(S_AnalyseId,null);
  }

  public long getAnalyseId(){
        return DataType.getAsLong(this.get(S_AnalyseId));
  
  }
  public long getAnalyseIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_AnalyseId));
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

  public void initAnalyseClass(String value){
     this.initProperty(S_AnalyseClass,value);
  }
  public  void setAnalyseClass(String value){
     this.set(S_AnalyseClass,value);
  }
  public  void setAnalyseClassNull(){
     this.set(S_AnalyseClass,null);
  }

  public String getAnalyseClass(){
       return DataType.getAsString(this.get(S_AnalyseClass));
  
  }
  public String getAnalyseClassInitialValue(){
        return DataType.getAsString(this.getOldObj(S_AnalyseClass));
      }

  public void initAnalyseCode(String value){
     this.initProperty(S_AnalyseCode,value);
  }
  public  void setAnalyseCode(String value){
     this.set(S_AnalyseCode,value);
  }
  public  void setAnalyseCodeNull(){
     this.set(S_AnalyseCode,null);
  }

  public String getAnalyseCode(){
       return DataType.getAsString(this.get(S_AnalyseCode));
  
  }
  public String getAnalyseCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_AnalyseCode));
      }

  public void initDefineName(String value){
     this.initProperty(S_DefineName,value);
  }
  public  void setDefineName(String value){
     this.set(S_DefineName,value);
  }
  public  void setDefineNameNull(){
     this.set(S_DefineName,null);
  }

  public String getDefineName(){
       return DataType.getAsString(this.get(S_DefineName));
  
  }
  public String getDefineNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DefineName));
      }

  public void initDefineCode(String value){
     this.initProperty(S_DefineCode,value);
  }
  public  void setDefineCode(String value){
     this.set(S_DefineCode,value);
  }
  public  void setDefineCodeNull(){
     this.set(S_DefineCode,null);
  }

  public String getDefineCode(){
       return DataType.getAsString(this.get(S_DefineCode));
  
  }
  public String getDefineCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DefineCode));
      }


 
 }

