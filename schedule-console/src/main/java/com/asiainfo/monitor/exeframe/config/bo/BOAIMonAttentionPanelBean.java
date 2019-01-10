package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAttentionPanelValue;

public class BOAIMonAttentionPanelBean extends DataContainer implements DataContainerInterface,IBOAIMonAttentionPanelValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonAttentionPanel";



  public final static  String S_Layer = "LAYER";
  public final static  String S_State = "STATE";
  public final static  String S_ObjId = "OBJ_ID";
  public final static  String S_TempType = "TEMP_TYPE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ViewStrategy = "VIEW_STRATEGY";
  public final static  String S_Contrail = "CONTRAIL";
  public final static  String S_PanelId = "PANEL_ID";
  public final static  String S_PanelName = "PANEL_NAME";
  public final static  String S_ViewType = "VIEW_TYPE";
  public final static  String S_TimeId = "TIME_ID";
  public final static  String S_ObjType = "OBJ_TYPE";
  public final static  String S_ThresholdId = "THRESHOLD_ID";
  public final static  String S_ViewTransform = "VIEW_TRANSFORM";
  public final static  String S_ExecMethod = "EXEC_METHOD";
  public final static  String S_PanelDesc = "PANEL_DESC";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonAttentionPanelBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initLayer(String value){
     this.initProperty(S_Layer,value);
  }
  public  void setLayer(String value){
     this.set(S_Layer,value);
  }
  public  void setLayerNull(){
     this.set(S_Layer,null);
  }

  public String getLayer(){
       return DataType.getAsString(this.get(S_Layer));
  
  }
  public String getLayerInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Layer));
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

  public void initObjId(long value){
     this.initProperty(S_ObjId,new Long(value));
  }
  public  void setObjId(long value){
     this.set(S_ObjId,new Long(value));
  }
  public  void setObjIdNull(){
     this.set(S_ObjId,null);
  }

  public long getObjId(){
        return DataType.getAsLong(this.get(S_ObjId));
  
  }
  public long getObjIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ObjId));
      }

  public void initTempType(String value){
     this.initProperty(S_TempType,value);
  }
  public  void setTempType(String value){
     this.set(S_TempType,value);
  }
  public  void setTempTypeNull(){
     this.set(S_TempType,null);
  }

  public String getTempType(){
       return DataType.getAsString(this.get(S_TempType));
  
  }
  public String getTempTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TempType));
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

  public void initContrail(String value){
     this.initProperty(S_Contrail,value);
  }
  public  void setContrail(String value){
     this.set(S_Contrail,value);
  }
  public  void setContrailNull(){
     this.set(S_Contrail,null);
  }

  public String getContrail(){
       return DataType.getAsString(this.get(S_Contrail));
  
  }
  public String getContrailInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Contrail));
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

  public void initPanelName(String value){
     this.initProperty(S_PanelName,value);
  }
  public  void setPanelName(String value){
     this.set(S_PanelName,value);
  }
  public  void setPanelNameNull(){
     this.set(S_PanelName,null);
  }

  public String getPanelName(){
       return DataType.getAsString(this.get(S_PanelName));
  
  }
  public String getPanelNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_PanelName));
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

  public void initObjType(String value){
     this.initProperty(S_ObjType,value);
  }
  public  void setObjType(String value){
     this.set(S_ObjType,value);
  }
  public  void setObjTypeNull(){
     this.set(S_ObjType,null);
  }

  public String getObjType(){
       return DataType.getAsString(this.get(S_ObjType));
  
  }
  public String getObjTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ObjType));
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

  public void initExecMethod(String value){
     this.initProperty(S_ExecMethod,value);
  }
  public  void setExecMethod(String value){
     this.set(S_ExecMethod,value);
  }
  public  void setExecMethodNull(){
     this.set(S_ExecMethod,null);
  }

  public String getExecMethod(){
       return DataType.getAsString(this.get(S_ExecMethod));
  
  }
  public String getExecMethodInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ExecMethod));
      }

  public void initPanelDesc(String value){
     this.initProperty(S_PanelDesc,value);
  }
  public  void setPanelDesc(String value){
     this.set(S_PanelDesc,value);
  }
  public  void setPanelDescNull(){
     this.set(S_PanelDesc,null);
  }

  public String getPanelDesc(){
       return DataType.getAsString(this.get(S_PanelDesc));
  
  }
  public String getPanelDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_PanelDesc));
      }


 
 }

