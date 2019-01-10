package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainRelatValue;

public class BOAIMonDomainRelatBean extends DataContainer implements DataContainerInterface,IBOAIMonDomainRelatValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomainRelat";



  public final static  String S_State = "STATE";
  public final static  String S_DomainId = "DOMAIN_ID";
  public final static  String S_RelatDesc = "RELAT_DESC";
  public final static  String S_RelatId = "RELAT_ID";
  public final static  String S_RelatdomainType = "RELATDOMAIN_TYPE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_RelatdomainId = "RELATDOMAIN_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonDomainRelatBean() throws AIException{
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

  public void initRelatDesc(String value){
     this.initProperty(S_RelatDesc,value);
  }
  public  void setRelatDesc(String value){
     this.set(S_RelatDesc,value);
  }
  public  void setRelatDescNull(){
     this.set(S_RelatDesc,null);
  }

  public String getRelatDesc(){
       return DataType.getAsString(this.get(S_RelatDesc));
  
  }
  public String getRelatDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RelatDesc));
      }

  public void initRelatId(long value){
     this.initProperty(S_RelatId,new Long(value));
  }
  public  void setRelatId(long value){
     this.set(S_RelatId,new Long(value));
  }
  public  void setRelatIdNull(){
     this.set(S_RelatId,null);
  }

  public long getRelatId(){
        return DataType.getAsLong(this.get(S_RelatId));
  
  }
  public long getRelatIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RelatId));
      }

  public void initRelatdomainType(String value){
     this.initProperty(S_RelatdomainType,value);
  }
  public  void setRelatdomainType(String value){
     this.set(S_RelatdomainType,value);
  }
  public  void setRelatdomainTypeNull(){
     this.set(S_RelatdomainType,null);
  }

  public String getRelatdomainType(){
       return DataType.getAsString(this.get(S_RelatdomainType));
  
  }
  public String getRelatdomainTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RelatdomainType));
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

  public void initRelatdomainId(long value){
     this.initProperty(S_RelatdomainId,new Long(value));
  }
  public  void setRelatdomainId(long value){
     this.set(S_RelatdomainId,new Long(value));
  }
  public  void setRelatdomainIdNull(){
     this.set(S_RelatdomainId,null);
  }

  public long getRelatdomainId(){
        return DataType.getAsLong(this.get(S_RelatdomainId));
  
  }
  public long getRelatdomainIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RelatdomainId));
      }


 
 }

