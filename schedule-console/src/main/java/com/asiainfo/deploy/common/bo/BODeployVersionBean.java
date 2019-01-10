package com.asiainfo.deploy.common.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.deploy.common.ivalues.*;

public class BODeployVersionBean extends DataContainer implements DataContainerInterface,IBODeployVersionValue{

  private static String  m_boName = "com.asiainfo.deploy.common.bo.BODeployVersion";



  public final static  String S_Contacts = "CONTACTS";
  public final static  String S_PackagePath = "PACKAGE_PATH";
  public final static  String S_ParentVersionId = "PARENT_VERSION_ID";
  public final static  String S_FileList = "FILE_LIST";
  public final static  String S_ResolvedProblems = "RESOLVED_PROBLEMS";
  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_OperateType = "OPERATE_TYPE";
  public final static  String S_DeployStrategyId = "DEPLOY_STRATEGY_ID";
  public final static  String S_VersionId = "VERSION_ID";
  public final static  String S_OperatorId = "OPERATOR_ID";
  public final static  String S_ExternalVersionId = "EXTERNAL_VERSION_ID";
  public final static  String S_CreateTime = "CREATE_TIME";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BODeployVersionBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initContacts(String value){
     this.initProperty(S_Contacts,value);
  }
  public  void setContacts(String value){
     this.set(S_Contacts,value);
  }
  public  void setContactsNull(){
     this.set(S_Contacts,null);
  }

  public String getContacts(){
       return DataType.getAsString(this.get(S_Contacts));
  
  }
  public String getContactsInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Contacts));
      }

  public void initPackagePath(String value){
     this.initProperty(S_PackagePath,value);
  }
  public  void setPackagePath(String value){
     this.set(S_PackagePath,value);
  }
  public  void setPackagePathNull(){
     this.set(S_PackagePath,null);
  }

  public String getPackagePath(){
       return DataType.getAsString(this.get(S_PackagePath));
  
  }
  public String getPackagePathInitialValue(){
        return DataType.getAsString(this.getOldObj(S_PackagePath));
      }

  public void initParentVersionId(long value){
     this.initProperty(S_ParentVersionId,new Long(value));
  }
  public  void setParentVersionId(long value){
     this.set(S_ParentVersionId,new Long(value));
  }
  public  void setParentVersionIdNull(){
     this.set(S_ParentVersionId,null);
  }

  public long getParentVersionId(){
        return DataType.getAsLong(this.get(S_ParentVersionId));
  
  }
  public long getParentVersionIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ParentVersionId));
      }

  public void initFileList(String value){
     this.initProperty(S_FileList,value);
  }
  public  void setFileList(String value){
     this.set(S_FileList,value);
  }
  public  void setFileListNull(){
     this.set(S_FileList,null);
  }

  public String getFileList(){
       return DataType.getAsString(this.get(S_FileList));
  
  }
  public String getFileListInitialValue(){
        return DataType.getAsString(this.getOldObj(S_FileList));
      }

  public void initResolvedProblems(String value){
     this.initProperty(S_ResolvedProblems,value);
  }
  public  void setResolvedProblems(String value){
     this.set(S_ResolvedProblems,value);
  }
  public  void setResolvedProblemsNull(){
     this.set(S_ResolvedProblems,null);
  }

  public String getResolvedProblems(){
       return DataType.getAsString(this.get(S_ResolvedProblems));
  
  }
  public String getResolvedProblemsInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ResolvedProblems));
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

  public void initOperateType(String value){
     this.initProperty(S_OperateType,value);
  }
  public  void setOperateType(String value){
     this.set(S_OperateType,value);
  }
  public  void setOperateTypeNull(){
     this.set(S_OperateType,null);
  }

  public String getOperateType(){
       return DataType.getAsString(this.get(S_OperateType));
  
  }
  public String getOperateTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_OperateType));
      }

  public void initDeployStrategyId(long value){
     this.initProperty(S_DeployStrategyId,new Long(value));
  }
  public  void setDeployStrategyId(long value){
     this.set(S_DeployStrategyId,new Long(value));
  }
  public  void setDeployStrategyIdNull(){
     this.set(S_DeployStrategyId,null);
  }

  public long getDeployStrategyId(){
        return DataType.getAsLong(this.get(S_DeployStrategyId));
  
  }
  public long getDeployStrategyIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_DeployStrategyId));
      }

  public void initVersionId(long value){
     this.initProperty(S_VersionId,new Long(value));
  }
  public  void setVersionId(long value){
     this.set(S_VersionId,new Long(value));
  }
  public  void setVersionIdNull(){
     this.set(S_VersionId,null);
  }

  public long getVersionId(){
        return DataType.getAsLong(this.get(S_VersionId));
  
  }
  public long getVersionIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_VersionId));
      }

  public void initOperatorId(long value){
     this.initProperty(S_OperatorId,new Long(value));
  }
  public  void setOperatorId(long value){
     this.set(S_OperatorId,new Long(value));
  }
  public  void setOperatorIdNull(){
     this.set(S_OperatorId,null);
  }

  public long getOperatorId(){
        return DataType.getAsLong(this.get(S_OperatorId));
  
  }
  public long getOperatorIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_OperatorId));
      }

  public void initExternalVersionId(long value){
     this.initProperty(S_ExternalVersionId,new Long(value));
  }
  public  void setExternalVersionId(long value){
     this.set(S_ExternalVersionId,new Long(value));
  }
  public  void setExternalVersionIdNull(){
     this.set(S_ExternalVersionId,null);
  }

  public long getExternalVersionId(){
        return DataType.getAsLong(this.get(S_ExternalVersionId));
  
  }
  public long getExternalVersionIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ExternalVersionId));
      }

  public void initCreateTime(Timestamp value){
     this.initProperty(S_CreateTime,value);
  }
  public  void setCreateTime(Timestamp value){
     this.set(S_CreateTime,value);
  }
  public  void setCreateTimeNull(){
     this.set(S_CreateTime,null);
  }

  public Timestamp getCreateTime(){
        return DataType.getAsDateTime(this.get(S_CreateTime));
  
  }
  public Timestamp getCreateTimeInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_CreateTime));
      }


 
 }

