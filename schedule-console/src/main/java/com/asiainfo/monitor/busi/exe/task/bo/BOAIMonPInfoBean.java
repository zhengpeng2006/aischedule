package com.asiainfo.monitor.busi.exe.task.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;

public class BOAIMonPInfoBean extends DataContainer implements DataContainerInterface,IBOAIMonPInfoValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfo";



  public final static  String S_Layer = "LAYER";
  public final static  String S_MType = "M_TYPE";
  public final static  String S_GroupId = "GROUP_ID";
  public final static  String S_InfoCode = "INFO_CODE";
  public final static  String S_State = "STATE";
  public final static  String S_Name = "NAME";
  public final static  String S_FilterId = "FILTER_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_SplitRuleId = "SPLIT_RULE_ID";
  public final static  String S_ResolveId = "RESOLVE_ID";
  public final static  String S_InfoId = "INFO_ID";
  public final static  String S_HostId = "HOST_ID";
  public final static  String S_TimeId = "TIME_ID";
  public final static  String S_TypeId = "TYPE_ID";
  public final static  String S_ThresholdId = "THRESHOLD_ID";
  public final static  String S_TaskMethod = "TASK_METHOD";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonPInfoBean() throws AIException{
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

  public void initMType(String value){
     this.initProperty(S_MType,value);
  }
  public  void setMType(String value){
     this.set(S_MType,value);
  }
  public  void setMTypeNull(){
     this.set(S_MType,null);
  }

  public String getMType(){
       return DataType.getAsString(this.get(S_MType));
  
  }
  public String getMTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MType));
      }

  public void initGroupId(long value){
     this.initProperty(S_GroupId,new Long(value));
  }
  public  void setGroupId(long value){
     this.set(S_GroupId,new Long(value));
  }
  public  void setGroupIdNull(){
     this.set(S_GroupId,null);
  }

  public long getGroupId(){
        return DataType.getAsLong(this.get(S_GroupId));
  
  }
  public long getGroupIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_GroupId));
      }

  public void initInfoCode(String value){
     this.initProperty(S_InfoCode,value);
  }
  public  void setInfoCode(String value){
     this.set(S_InfoCode,value);
  }
  public  void setInfoCodeNull(){
     this.set(S_InfoCode,null);
  }

  public String getInfoCode(){
       return DataType.getAsString(this.get(S_InfoCode));
  
  }
  public String getInfoCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_InfoCode));
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

  public void initName(String value){
     this.initProperty(S_Name,value);
  }
  public  void setName(String value){
     this.set(S_Name,value);
  }
  public  void setNameNull(){
     this.set(S_Name,null);
  }

  public String getName(){
       return DataType.getAsString(this.get(S_Name));
  
  }
  public String getNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Name));
      }

  public void initFilterId(long value){
     this.initProperty(S_FilterId,new Long(value));
  }
  public  void setFilterId(long value){
     this.set(S_FilterId,new Long(value));
  }
  public  void setFilterIdNull(){
     this.set(S_FilterId,null);
  }

  public long getFilterId(){
        return DataType.getAsLong(this.get(S_FilterId));
  
  }
  public long getFilterIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_FilterId));
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

  public void initSplitRuleId(long value){
     this.initProperty(S_SplitRuleId,new Long(value));
  }
  public  void setSplitRuleId(long value){
     this.set(S_SplitRuleId,new Long(value));
  }
  public  void setSplitRuleIdNull(){
     this.set(S_SplitRuleId,null);
  }

  public long getSplitRuleId(){
        return DataType.getAsLong(this.get(S_SplitRuleId));
  
  }
  public long getSplitRuleIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_SplitRuleId));
      }

  public void initResolveId(long value){
     this.initProperty(S_ResolveId,new Long(value));
  }
  public  void setResolveId(long value){
     this.set(S_ResolveId,new Long(value));
  }
  public  void setResolveIdNull(){
     this.set(S_ResolveId,null);
  }

  public long getResolveId(){
        return DataType.getAsLong(this.get(S_ResolveId));
  
  }
  public long getResolveIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ResolveId));
      }

  public void initInfoId(long value){
     this.initProperty(S_InfoId,new Long(value));
  }
  public  void setInfoId(long value){
     this.set(S_InfoId,new Long(value));
  }
  public  void setInfoIdNull(){
     this.set(S_InfoId,null);
  }

  public long getInfoId(){
        return DataType.getAsLong(this.get(S_InfoId));
  
  }
  public long getInfoIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_InfoId));
      }

  public void initHostId(long value){
     this.initProperty(S_HostId,new Long(value));
  }
  public  void setHostId(long value){
     this.set(S_HostId,new Long(value));
  }
  public  void setHostIdNull(){
     this.set(S_HostId,null);
  }

  public long getHostId(){
        return DataType.getAsLong(this.get(S_HostId));
  
  }
  public long getHostIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_HostId));
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

  public void initTypeId(long value){
     this.initProperty(S_TypeId,new Long(value));
  }
  public  void setTypeId(long value){
     this.set(S_TypeId,new Long(value));
  }
  public  void setTypeIdNull(){
     this.set(S_TypeId,null);
  }

  public long getTypeId(){
        return DataType.getAsLong(this.get(S_TypeId));
  
  }
  public long getTypeIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TypeId));
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

  public void initTaskMethod(String value){
     this.initProperty(S_TaskMethod,value);
  }
  public  void setTaskMethod(String value){
     this.set(S_TaskMethod,value);
  }
  public  void setTaskMethodNull(){
     this.set(S_TaskMethod,null);
  }

  public String getTaskMethod(){
       return DataType.getAsString(this.get(S_TaskMethod));
  
  }
  public String getTaskMethodInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskMethod));
      }


 
 }

