package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainValue;

public class BOAIMonDomainBean extends DataContainer implements DataContainerInterface,IBOAIMonDomainValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomain";



  public final static  String S_State = "STATE";
  public final static  String S_DomainId = "DOMAIN_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_DomainType = "DOMAIN_TYPE";
  public final static  String S_DomainCode = "DOMAIN_CODE";
  public final static  String S_DomainDesc = "DOMAIN_DESC";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonDomainBean() throws AIException{
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

  public void initDomainId(long value){
     this.initProperty(S_DomainId,new Long(value));
  }
  public  void setDomainId(long value){
     this.set(S_DomainId,new Long(value));
  }
  public  void setDomainIdNull(){
     this.set(S_DomainId,null);
  }

  public long getDomainId(){
        return DataType.getAsLong(this.get(S_DomainId));
  
  }
  public long getDomainIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_DomainId));
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

  public void initDomainType(String value){
     this.initProperty(S_DomainType,value);
  }
  public  void setDomainType(String value){
     this.set(S_DomainType,value);
  }
  public  void setDomainTypeNull(){
     this.set(S_DomainType,null);
  }

  public String getDomainType(){
       return DataType.getAsString(this.get(S_DomainType));
  
  }
  public String getDomainTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DomainType));
      }

  public void initDomainCode(String value){
     this.initProperty(S_DomainCode,value);
  }
  public  void setDomainCode(String value){
     this.set(S_DomainCode,value);
  }
  public  void setDomainCodeNull(){
     this.set(S_DomainCode,null);
  }

  public String getDomainCode(){
       return DataType.getAsString(this.get(S_DomainCode));
  
  }
  public String getDomainCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DomainCode));
      }

  public void initDomainDesc(String value){
     this.initProperty(S_DomainDesc,value);
  }
  public  void setDomainDesc(String value){
     this.set(S_DomainDesc,value);
  }
  public  void setDomainDescNull(){
     this.set(S_DomainDesc,null);
  }

  public String getDomainDesc(){
       return DataType.getAsString(this.get(S_DomainDesc));
  
  }
  public String getDomainDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DomainDesc));
      }


 
 }

