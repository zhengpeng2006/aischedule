package com.asiainfo.monitor.exeframe.config.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserPriEntityValue;

public class BOAIMonUserPriEntityBean extends DataContainer implements DataContainerInterface,IBOAIMonUserPriEntityValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserPriEntity";



  public final static  String S_EntityId = "ENTITY_ID";
  public final static  String S_State = "STATE";
  public final static  String S_DoneDate = "DONE_DATE";
  public final static  String S_Notes = "NOTES";
  public final static  String S_EntityCode = "ENTITY_CODE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_DeployType = "DEPLOY_TYPE";
  public final static  String S_EntitySeq = "ENTITY_SEQ";
  public final static  String S_ParentId = "PARENT_ID";
  public final static  String S_EntityAttr = "ENTITY_ATTR";
  public final static  String S_OpId = "OP_ID";
  public final static  String S_EntityName = "ENTITY_NAME";
  public final static  String S_LoadType = "LOAD_TYPE";
  public final static  String S_EntityType = "ENTITY_TYPE";
  public final static  String S_EntityStyle = "ENTITY_STYLE";
  public final static  String S_SelfType = "SELF_TYPE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonUserPriEntityBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initEntityId(long value){
     this.initProperty(S_EntityId,new Long(value));
  }
  public  void setEntityId(long value){
     this.set(S_EntityId,new Long(value));
  }
  public  void setEntityIdNull(){
     this.set(S_EntityId,null);
  }

  public long getEntityId(){
        return DataType.getAsLong(this.get(S_EntityId));
  
  }
  public long getEntityIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_EntityId));
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

  public void initDoneDate(Timestamp value){
     this.initProperty(S_DoneDate,value);
  }
  public  void setDoneDate(Timestamp value){
     this.set(S_DoneDate,value);
  }
  public  void setDoneDateNull(){
     this.set(S_DoneDate,null);
  }

  public Timestamp getDoneDate(){
        return DataType.getAsDateTime(this.get(S_DoneDate));
  
  }
  public Timestamp getDoneDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_DoneDate));
      }

  public void initNotes(String value){
     this.initProperty(S_Notes,value);
  }
  public  void setNotes(String value){
     this.set(S_Notes,value);
  }
  public  void setNotesNull(){
     this.set(S_Notes,null);
  }

  public String getNotes(){
       return DataType.getAsString(this.get(S_Notes));
  
  }
  public String getNotesInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Notes));
      }

  public void initEntityCode(String value){
     this.initProperty(S_EntityCode,value);
  }
  public  void setEntityCode(String value){
     this.set(S_EntityCode,value);
  }
  public  void setEntityCodeNull(){
     this.set(S_EntityCode,null);
  }

  public String getEntityCode(){
       return DataType.getAsString(this.get(S_EntityCode));
  
  }
  public String getEntityCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_EntityCode));
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

  public void initDeployType(String value){
     this.initProperty(S_DeployType,value);
  }
  public  void setDeployType(String value){
     this.set(S_DeployType,value);
  }
  public  void setDeployTypeNull(){
     this.set(S_DeployType,null);
  }

  public String getDeployType(){
       return DataType.getAsString(this.get(S_DeployType));
  
  }
  public String getDeployTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DeployType));
      }

  public void initEntitySeq(int value){
     this.initProperty(S_EntitySeq,new Integer(value));
  }
  public  void setEntitySeq(int value){
     this.set(S_EntitySeq,new Integer(value));
  }
  public  void setEntitySeqNull(){
     this.set(S_EntitySeq,null);
  }

  public int getEntitySeq(){
        return DataType.getAsInt(this.get(S_EntitySeq));
  
  }
  public int getEntitySeqInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_EntitySeq));
      }

  public void initParentId(long value){
     this.initProperty(S_ParentId,new Long(value));
  }
  public  void setParentId(long value){
     this.set(S_ParentId,new Long(value));
  }
  public  void setParentIdNull(){
     this.set(S_ParentId,null);
  }

  public long getParentId(){
        return DataType.getAsLong(this.get(S_ParentId));
  
  }
  public long getParentIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ParentId));
      }

  public void initEntityAttr(String value){
     this.initProperty(S_EntityAttr,value);
  }
  public  void setEntityAttr(String value){
     this.set(S_EntityAttr,value);
  }
  public  void setEntityAttrNull(){
     this.set(S_EntityAttr,null);
  }

  public String getEntityAttr(){
       return DataType.getAsString(this.get(S_EntityAttr));
  
  }
  public String getEntityAttrInitialValue(){
        return DataType.getAsString(this.getOldObj(S_EntityAttr));
      }

  public void initOpId(long value){
     this.initProperty(S_OpId,new Long(value));
  }
  public  void setOpId(long value){
     this.set(S_OpId,new Long(value));
  }
  public  void setOpIdNull(){
     this.set(S_OpId,null);
  }

  public long getOpId(){
        return DataType.getAsLong(this.get(S_OpId));
  
  }
  public long getOpIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_OpId));
      }

  public void initEntityName(String value){
     this.initProperty(S_EntityName,value);
  }
  public  void setEntityName(String value){
     this.set(S_EntityName,value);
  }
  public  void setEntityNameNull(){
     this.set(S_EntityName,null);
  }

  public String getEntityName(){
       return DataType.getAsString(this.get(S_EntityName));
  
  }
  public String getEntityNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_EntityName));
      }

  public void initLoadType(String value){
     this.initProperty(S_LoadType,value);
  }
  public  void setLoadType(String value){
     this.set(S_LoadType,value);
  }
  public  void setLoadTypeNull(){
     this.set(S_LoadType,null);
  }

  public String getLoadType(){
       return DataType.getAsString(this.get(S_LoadType));
  
  }
  public String getLoadTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_LoadType));
      }

  public void initEntityType(int value){
     this.initProperty(S_EntityType,new Integer(value));
  }
  public  void setEntityType(int value){
     this.set(S_EntityType,new Integer(value));
  }
  public  void setEntityTypeNull(){
     this.set(S_EntityType,null);
  }

  public int getEntityType(){
        return DataType.getAsInt(this.get(S_EntityType));
  
  }
  public int getEntityTypeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_EntityType));
      }

  public void initEntityStyle(String value){
     this.initProperty(S_EntityStyle,value);
  }
  public  void setEntityStyle(String value){
     this.set(S_EntityStyle,value);
  }
  public  void setEntityStyleNull(){
     this.set(S_EntityStyle,null);
  }

  public String getEntityStyle(){
       return DataType.getAsString(this.get(S_EntityStyle));
  
  }
  public String getEntityStyleInitialValue(){
        return DataType.getAsString(this.getOldObj(S_EntityStyle));
      }

  public void initSelfType(int value){
     this.initProperty(S_SelfType,new Integer(value));
  }
  public  void setSelfType(int value){
     this.set(S_SelfType,new Integer(value));
  }
  public  void setSelfTypeNull(){
     this.set(S_SelfType,null);
  }

  public int getSelfType(){
        return DataType.getAsInt(this.get(S_SelfType));
  
  }
  public int getSelfTypeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_SelfType));
      }


 
 }

