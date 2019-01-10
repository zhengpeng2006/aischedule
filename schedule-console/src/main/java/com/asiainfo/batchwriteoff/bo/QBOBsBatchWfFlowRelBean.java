package com.asiainfo.batchwriteoff.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.*;
import com.asiainfo.batchwriteoff.ivalues.IQBOBsBatchWfFlowRelValue;

import java.sql.Timestamp;

public class QBOBsBatchWfFlowRelBean extends DataContainer implements DataContainerInterface,IQBOBsBatchWfFlowRelValue {

  private static String  m_boName = "com.asiainfo.crm.common.bo.QBOBsBatchWfFlowRel";



  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_RelRegionId = "REL_REGION_ID";
  public final static  String S_WfFlowDesc = "WF_FLOW_DESC";
  public final static  String S_IsDtl = "IS_DTL";
  public final static  String S_StateDate = "STATE_DATE";
  public final static  String S_State = "STATE";
  public final static  String S_PreWfFlowId = "PRE_WF_FLOW_ID";
  public final static  String S_WfFlowName = "WF_FLOW_NAME";
  public final static  String S_DtlUrl = "DTL_URL";
  public final static  String S_TimeGroupId = "TIME_GROUP_ID";
  public final static  String S_PreWfFlowState = "PRE_WF_FLOW_STATE";
  public final static  String S_BillDay = "BILL_DAY";
  public final static  String S_IsDiscontinue = "IS_DISCONTINUE";
  public final static  String S_RunMode = "RUN_MODE";
  public final static  String S_BusinessClass = "BUSINESS_CLASS";
  public final static  String S_WfFlowId = "WF_FLOW_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public QBOBsBatchWfFlowRelBean() throws AIException {
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException {
   return S_TYPE;
 }

 public void setObjectType(ObjectType value) throws AIException {
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initRegionId(String value){
     this.initProperty(S_RegionId,value);
  }
  public  void setRegionId(String value){
     this.set(S_RegionId,value);
  }
  public  void setRegionIdNull(){
     this.set(S_RegionId,null);
  }

  public String getRegionId(){
       return DataType.getAsString(this.get(S_RegionId));
  
  }
  public String getRegionIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RegionId));
      }

  public void initRelRegionId(String value){
     this.initProperty(S_RelRegionId,value);
  }
  public  void setRelRegionId(String value){
     this.set(S_RelRegionId,value);
  }
  public  void setRelRegionIdNull(){
     this.set(S_RelRegionId,null);
  }

  public String getRelRegionId(){
       return DataType.getAsString(this.get(S_RelRegionId));
  
  }
  public String getRelRegionIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RelRegionId));
      }

  public void initWfFlowDesc(String value){
     this.initProperty(S_WfFlowDesc,value);
  }
  public  void setWfFlowDesc(String value){
     this.set(S_WfFlowDesc,value);
  }
  public  void setWfFlowDescNull(){
     this.set(S_WfFlowDesc,null);
  }

  public String getWfFlowDesc(){
       return DataType.getAsString(this.get(S_WfFlowDesc));
  
  }
  public String getWfFlowDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_WfFlowDesc));
      }

  public void initIsDtl(String value){
     this.initProperty(S_IsDtl,value);
  }
  public  void setIsDtl(String value){
     this.set(S_IsDtl,value);
  }
  public  void setIsDtlNull(){
     this.set(S_IsDtl,null);
  }

  public String getIsDtl(){
       return DataType.getAsString(this.get(S_IsDtl));
  
  }
  public String getIsDtlInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IsDtl));
      }

  public void initStateDate(Timestamp value){
     this.initProperty(S_StateDate,value);
  }
  public  void setStateDate(Timestamp value){
     this.set(S_StateDate,value);
  }
  public  void setStateDateNull(){
     this.set(S_StateDate,null);
  }

  public Timestamp getStateDate(){
        return DataType.getAsDateTime(this.get(S_StateDate));
  
  }
  public Timestamp getStateDateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_StateDate));
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

  public void initPreWfFlowId(long value){
     this.initProperty(S_PreWfFlowId,new Long(value));
  }
  public  void setPreWfFlowId(long value){
     this.set(S_PreWfFlowId,new Long(value));
  }
  public  void setPreWfFlowIdNull(){
     this.set(S_PreWfFlowId,null);
  }

  public long getPreWfFlowId(){
        return DataType.getAsLong(this.get(S_PreWfFlowId));
  
  }
  public long getPreWfFlowIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_PreWfFlowId));
      }

  public void initWfFlowName(String value){
     this.initProperty(S_WfFlowName,value);
  }
  public  void setWfFlowName(String value){
     this.set(S_WfFlowName,value);
  }
  public  void setWfFlowNameNull(){
     this.set(S_WfFlowName,null);
  }

  public String getWfFlowName(){
       return DataType.getAsString(this.get(S_WfFlowName));
  
  }
  public String getWfFlowNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_WfFlowName));
      }

  public void initDtlUrl(String value){
     this.initProperty(S_DtlUrl,value);
  }
  public  void setDtlUrl(String value){
     this.set(S_DtlUrl,value);
  }
  public  void setDtlUrlNull(){
     this.set(S_DtlUrl,null);
  }

  public String getDtlUrl(){
       return DataType.getAsString(this.get(S_DtlUrl));
  
  }
  public String getDtlUrlInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DtlUrl));
      }

  public void initTimeGroupId(long value){
     this.initProperty(S_TimeGroupId,new Long(value));
  }
  public  void setTimeGroupId(long value){
     this.set(S_TimeGroupId,new Long(value));
  }
  public  void setTimeGroupIdNull(){
     this.set(S_TimeGroupId,null);
  }

  public long getTimeGroupId(){
        return DataType.getAsLong(this.get(S_TimeGroupId));
  
  }
  public long getTimeGroupIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TimeGroupId));
      }

  public void initPreWfFlowState(String value){
     this.initProperty(S_PreWfFlowState,value);
  }
  public  void setPreWfFlowState(String value){
     this.set(S_PreWfFlowState,value);
  }
  public  void setPreWfFlowStateNull(){
     this.set(S_PreWfFlowState,null);
  }

  public String getPreWfFlowState(){
       return DataType.getAsString(this.get(S_PreWfFlowState));
  
  }
  public String getPreWfFlowStateInitialValue(){
        return DataType.getAsString(this.getOldObj(S_PreWfFlowState));
      }

  public void initBillDay(long value){
     this.initProperty(S_BillDay,new Long(value));
  }
  public  void setBillDay(long value){
     this.set(S_BillDay,new Long(value));
  }
  public  void setBillDayNull(){
     this.set(S_BillDay,null);
  }

  public long getBillDay(){
        return DataType.getAsLong(this.get(S_BillDay));
  
  }
  public long getBillDayInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_BillDay));
      }

  public void initIsDiscontinue(String value){
     this.initProperty(S_IsDiscontinue,value);
  }
  public  void setIsDiscontinue(String value){
     this.set(S_IsDiscontinue,value);
  }
  public  void setIsDiscontinueNull(){
     this.set(S_IsDiscontinue,null);
  }

  public String getIsDiscontinue(){
       return DataType.getAsString(this.get(S_IsDiscontinue));
  
  }
  public String getIsDiscontinueInitialValue(){
        return DataType.getAsString(this.getOldObj(S_IsDiscontinue));
      }

  public void initRunMode(String value){
     this.initProperty(S_RunMode,value);
  }
  public  void setRunMode(String value){
     this.set(S_RunMode,value);
  }
  public  void setRunModeNull(){
     this.set(S_RunMode,null);
  }

  public String getRunMode(){
       return DataType.getAsString(this.get(S_RunMode));
  
  }
  public String getRunModeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RunMode));
      }

  public void initBusinessClass(String value){
     this.initProperty(S_BusinessClass,value);
  }
  public  void setBusinessClass(String value){
     this.set(S_BusinessClass,value);
  }
  public  void setBusinessClassNull(){
     this.set(S_BusinessClass,null);
  }

  public String getBusinessClass(){
       return DataType.getAsString(this.get(S_BusinessClass));
  
  }
  public String getBusinessClassInitialValue(){
        return DataType.getAsString(this.getOldObj(S_BusinessClass));
      }

  public void initWfFlowId(long value){
     this.initProperty(S_WfFlowId,new Long(value));
  }
  public  void setWfFlowId(long value){
     this.set(S_WfFlowId,new Long(value));
  }
  public  void setWfFlowIdNull(){
     this.set(S_WfFlowId,null);
  }

  public long getWfFlowId(){
        return DataType.getAsLong(this.get(S_WfFlowId));
  
  }
  public long getWfFlowIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_WfFlowId));
      }


 
 }

