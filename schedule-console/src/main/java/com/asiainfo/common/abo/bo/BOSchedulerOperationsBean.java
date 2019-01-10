package com.asiainfo.common.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.common.abo.ivalues.*;

public class BOSchedulerOperationsBean extends DataContainer implements DataContainerInterface,IBOSchedulerOperationsValue{

  private static String  m_boName = "com.asiainfo.common.abo.bo.BOSchedulerOperations";



  public final static  String S_OperationClientIp = "OPERATION_CLIENT_IP";
  public final static  String S_SubsystemDomain = "SUBSYSTEM_DOMAIN";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_OperationSource = "OPERATION_SOURCE";
  public final static  String S_SystemDomain = "SYSTEM_DOMAIN";
  public final static  String S_OperationObjectContent = "OPERATION_OBJECT_CONTENT";
  public final static  String S_OperationType = "OPERATION_TYPE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_Operator = "OPERATOR";
  public final static  String S_AppServerName = "APP_SERVER_NAME";
  public final static  String S_OperationModel = "OPERATION_MODEL";
  public final static  String S_OperationId = "OPERATION_ID";
  public final static  String S_OperationObjectType = "OPERATION_OBJECT_TYPE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOSchedulerOperationsBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initOperationClientIp(String value){
     this.initProperty(S_OperationClientIp,value);
  }
  public  void setOperationClientIp(String value){
     this.set(S_OperationClientIp,value);
  }
  public  void setOperationClientIpNull(){
     this.set(S_OperationClientIp,null);
  }

  public String getOperationClientIp(){
       return DataType.getAsString(this.get(S_OperationClientIp));
  
  }
  public String getOperationClientIpInitialValue(){
        return DataType.getAsString(this.getOldObj(S_OperationClientIp));
      }

  public void initSubsystemDomain(String value){
     this.initProperty(S_SubsystemDomain,value);
  }
  public  void setSubsystemDomain(String value){
     this.set(S_SubsystemDomain,value);
  }
  public  void setSubsystemDomainNull(){
     this.set(S_SubsystemDomain,null);
  }

  public String getSubsystemDomain(){
       return DataType.getAsString(this.get(S_SubsystemDomain));
  
  }
  public String getSubsystemDomainInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SubsystemDomain));
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

  public void initOperationSource(String value){
     this.initProperty(S_OperationSource,value);
  }
  public  void setOperationSource(String value){
     this.set(S_OperationSource,value);
  }
  public  void setOperationSourceNull(){
     this.set(S_OperationSource,null);
  }

  public String getOperationSource(){
       return DataType.getAsString(this.get(S_OperationSource));
  
  }
  public String getOperationSourceInitialValue(){
        return DataType.getAsString(this.getOldObj(S_OperationSource));
      }

  public void initSystemDomain(String value){
     this.initProperty(S_SystemDomain,value);
  }
  public  void setSystemDomain(String value){
     this.set(S_SystemDomain,value);
  }
  public  void setSystemDomainNull(){
     this.set(S_SystemDomain,null);
  }

  public String getSystemDomain(){
       return DataType.getAsString(this.get(S_SystemDomain));
  
  }
  public String getSystemDomainInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SystemDomain));
      }

  public void initOperationObjectContent(String value){
     this.initProperty(S_OperationObjectContent,value);
  }
  public  void setOperationObjectContent(String value){
     this.set(S_OperationObjectContent,value);
  }
  public  void setOperationObjectContentNull(){
     this.set(S_OperationObjectContent,null);
  }

  public String getOperationObjectContent(){
       return DataType.getAsString(this.get(S_OperationObjectContent));
  
  }
  public String getOperationObjectContentInitialValue(){
        return DataType.getAsString(this.getOldObj(S_OperationObjectContent));
      }

  public void initOperationType(String value){
     this.initProperty(S_OperationType,value);
  }
  public  void setOperationType(String value){
     this.set(S_OperationType,value);
  }
  public  void setOperationTypeNull(){
     this.set(S_OperationType,null);
  }

  public String getOperationType(){
       return DataType.getAsString(this.get(S_OperationType));
  
  }
  public String getOperationTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_OperationType));
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

  public void initOperator(String value){
     this.initProperty(S_Operator,value);
  }
  public  void setOperator(String value){
     this.set(S_Operator,value);
  }
  public  void setOperatorNull(){
     this.set(S_Operator,null);
  }

  public String getOperator(){
       return DataType.getAsString(this.get(S_Operator));
  
  }
  public String getOperatorInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Operator));
      }

  public void initAppServerName(String value){
     this.initProperty(S_AppServerName,value);
  }
  public  void setAppServerName(String value){
     this.set(S_AppServerName,value);
  }
  public  void setAppServerNameNull(){
     this.set(S_AppServerName,null);
  }

  public String getAppServerName(){
       return DataType.getAsString(this.get(S_AppServerName));
  
  }
  public String getAppServerNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_AppServerName));
      }

  public void initOperationModel(String value){
     this.initProperty(S_OperationModel,value);
  }
  public  void setOperationModel(String value){
     this.set(S_OperationModel,value);
  }
  public  void setOperationModelNull(){
     this.set(S_OperationModel,null);
  }

  public String getOperationModel(){
       return DataType.getAsString(this.get(S_OperationModel));
  
  }
  public String getOperationModelInitialValue(){
        return DataType.getAsString(this.getOldObj(S_OperationModel));
      }

  public void initOperationId(long value){
     this.initProperty(S_OperationId,new Long(value));
  }
  public  void setOperationId(long value){
     this.set(S_OperationId,new Long(value));
  }
  public  void setOperationIdNull(){
     this.set(S_OperationId,null);
  }

  public long getOperationId(){
        return DataType.getAsLong(this.get(S_OperationId));
  
  }
  public long getOperationIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_OperationId));
      }

  public void initOperationObjectType(String value){
     this.initProperty(S_OperationObjectType,value);
  }
  public  void setOperationObjectType(String value){
     this.set(S_OperationObjectType,value);
  }
  public  void setOperationObjectTypeNull(){
     this.set(S_OperationObjectType,null);
  }

  public String getOperationObjectType(){
       return DataType.getAsString(this.get(S_OperationObjectType));
  
  }
  public String getOperationObjectTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_OperationObjectType));
      }


 
 }

