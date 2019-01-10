package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelRelateValue;

public class BOAIMonCustomizePanelRelateBean extends DataContainer implements DataContainerInterface,IBOAIMonCustomizePanelRelateValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonCustomizePanelRelate";



  public final static  String S_State = "STATE";
  public final static  String S_ViewType = "VIEW_TYPE";
  public final static  String S_TimeId = "TIME_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CpanelId = "CPANEL_ID";
  public final static  String S_RelateId = "RELATE_ID";
  public final static  String S_ThresholdId = "THRESHOLD_ID";
  public final static  String S_RunType = "RUN_TYPE";
  public final static  String S_ViewStrategy = "VIEW_STRATEGY";
  public final static  String S_ViewTransform = "VIEW_TRANSFORM";
  public final static  String S_PanelId = "PANEL_ID";
  public final static  String S_RelateName = "RELATE_NAME";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonCustomizePanelRelateBean() throws AIException{
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

  public void initViewType(String value){
     this.initProperty(S_ViewType,value);
  }
  public  void setViewType(String value){
     this.set(S_ViewType,value);
  }
  public  void setViewTypeNull(){
     this.set(S_ViewType,null);
  }

  public String getViewType(){
       return DataType.getAsString(this.get(S_ViewType));
  
  }
  public String getViewTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ViewType));
      }

  public void initTimeId(long value){
     this.initProperty(S_TimeId,new Long(value));
  }
  public  void setTimeId(long value){
     this.set(S_TimeId,new Long(value));
  }
  public  void setTimeIdNull(){
     this.set(S_TimeId,null);
  }

  public long getTimeId(){
        return DataType.getAsLong(this.get(S_TimeId));
  
  }
  public long getTimeIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TimeId));
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

  public void initCpanelId(long value){
     this.initProperty(S_CpanelId,new Long(value));
  }
  public  void setCpanelId(long value){
     this.set(S_CpanelId,new Long(value));
  }
  public  void setCpanelIdNull(){
     this.set(S_CpanelId,null);
  }

  public long getCpanelId(){
        return DataType.getAsLong(this.get(S_CpanelId));
  
  }
  public long getCpanelIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_CpanelId));
      }

  public void initRelateId(long value){
     this.initProperty(S_RelateId,new Long(value));
  }
  public  void setRelateId(long value){
     this.set(S_RelateId,new Long(value));
  }
  public  void setRelateIdNull(){
     this.set(S_RelateId,null);
  }

  public long getRelateId(){
        return DataType.getAsLong(this.get(S_RelateId));
  
  }
  public long getRelateIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RelateId));
      }

  public void initThresholdId(long value){
     this.initProperty(S_ThresholdId,new Long(value));
  }
  public  void setThresholdId(long value){
     this.set(S_ThresholdId,new Long(value));
  }
  public  void setThresholdIdNull(){
     this.set(S_ThresholdId,null);
  }

  public long getThresholdId(){
        return DataType.getAsLong(this.get(S_ThresholdId));
  
  }
  public long getThresholdIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ThresholdId));
      }

  public void initRunType(String value){
     this.initProperty(S_RunType,value);
  }
  public  void setRunType(String value){
     this.set(S_RunType,value);
  }
  public  void setRunTypeNull(){
     this.set(S_RunType,null);
  }

  public String getRunType(){
       return DataType.getAsString(this.get(S_RunType));
  
  }
  public String getRunTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RunType));
      }

  public void initViewStrategy(String value){
     this.initProperty(S_ViewStrategy,value);
  }
  public  void setViewStrategy(String value){
     this.set(S_ViewStrategy,value);
  }
  public  void setViewStrategyNull(){
     this.set(S_ViewStrategy,null);
  }

  public String getViewStrategy(){
       return DataType.getAsString(this.get(S_ViewStrategy));
  
  }
  public String getViewStrategyInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ViewStrategy));
      }

  public void initViewTransform(String value){
     this.initProperty(S_ViewTransform,value);
  }
  public  void setViewTransform(String value){
     this.set(S_ViewTransform,value);
  }
  public  void setViewTransformNull(){
     this.set(S_ViewTransform,null);
  }

  public String getViewTransform(){
       return DataType.getAsString(this.get(S_ViewTransform));
  
  }
  public String getViewTransformInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ViewTransform));
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

  public void initRelateName(String value){
     this.initProperty(S_RelateName,value);
  }
  public  void setRelateName(String value){
     this.set(S_RelateName,value);
  }
  public  void setRelateNameNull(){
     this.set(S_RelateName,null);
  }

  public String getRelateName(){
       return DataType.getAsString(this.get(S_RelateName));
  
  }
  public String getRelateNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RelateName));
      }


 
 }

