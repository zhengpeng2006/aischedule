package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonPanelRelatEntityValue;

public class BOAIMonPanelRelatEntityBean extends DataContainer implements DataContainerInterface,IBOAIMonPanelRelatEntityValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonPanelRelatEntity";



  public final static  String S_EntityId = "ENTITY_ID";
  public final static  String S_State = "STATE";
  public final static  String S_PanelType = "PANEL_TYPE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_RelatId = "RELAT_ID";
  public final static  String S_PanelId = "PANEL_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonPanelRelatEntityBean() throws AIException{
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

  public void initPanelType(String value){
     this.initProperty(S_PanelType,value);
  }
  public  void setPanelType(String value){
     this.set(S_PanelType,value);
  }
  public  void setPanelTypeNull(){
     this.set(S_PanelType,null);
  }

  public String getPanelType(){
       return DataType.getAsString(this.get(S_PanelType));
  
  }
  public String getPanelTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_PanelType));
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

  public void initPanelId(long value){
     this.initProperty(S_PanelId,new Long(value));
  }
  public  void setPanelId(long value){
     this.set(S_PanelId,new Long(value));
  }
  public  void setPanelIdNull(){
     this.set(S_PanelId,null);
  }

  public long getPanelId(){
        return DataType.getAsLong(this.get(S_PanelId));
  
  }
  public long getPanelIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_PanelId));
      }


 
 }

