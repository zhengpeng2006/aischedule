package com.asiainfo.monitor.tools.bo;


import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.tools.ivalues.IBOAIMonI18nResourceValue;

public class BOAIMonI18nResourceBean extends DataContainer implements DataContainerInterface,IBOAIMonI18nResourceValue{

  private static String  m_boName = "com.asiainfo.monitor.tools.bo.BOAIMonI18nResource";



  public final static  String S_State = "STATE";
  public final static  String S_ResKey = "RES_KEY";
  public final static  String S_EnUs = "EN_US";
  public final static  String S_Remark = "REMARK";
  public final static  String S_ZhCn = "ZH_CN";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonI18nResourceBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
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

  public void initResKey(String value){
     this.initProperty(S_ResKey,value);
  }
  public  void setResKey(String value){
     this.set(S_ResKey,value);
  }
  public  void setResKeyNull(){
     this.set(S_ResKey,null);
  }

  public String getResKey(){
       return DataType.getAsString(this.get(S_ResKey));
  
  }
  public String getResKeyInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ResKey));
      }

  public void initEnUs(String value){
     this.initProperty(S_EnUs,value);
  }
  public  void setEnUs(String value){
     this.set(S_EnUs,value);
  }
  public  void setEnUsNull(){
     this.set(S_EnUs,null);
  }

  public String getEnUs(){
       return DataType.getAsString(this.get(S_EnUs));
  
  }
  public String getEnUsInitialValue(){
        return DataType.getAsString(this.getOldObj(S_EnUs));
      }

  public void initRemark(String value){
     this.initProperty(S_Remark,value);
  }
  public  void setRemark(String value){
     this.set(S_Remark,value);
  }
  public  void setRemarkNull(){
     this.set(S_Remark,null);
  }

  public String getRemark(){
       return DataType.getAsString(this.get(S_Remark));
  
  }
  public String getRemarkInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Remark));
      }

  public void initZhCn(String value){
     this.initProperty(S_ZhCn,value);
  }
  public  void setZhCn(String value){
     this.set(S_ZhCn,value);
  }
  public  void setZhCnNull(){
     this.set(S_ZhCn,null);
  }

  public String getZhCn(){
       return DataType.getAsString(this.get(S_ZhCn));
  
  }
  public String getZhCnInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ZhCn));
      }


 
 }

