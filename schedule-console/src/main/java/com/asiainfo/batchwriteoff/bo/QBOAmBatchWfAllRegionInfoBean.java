package com.asiainfo.batchwriteoff.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.*;
import com.asiainfo.batchwriteoff.ivalues.IQBOAmBatchWfAllRegionInfoValue;

public class QBOAmBatchWfAllRegionInfoBean extends DataContainer implements DataContainerInterface,IQBOAmBatchWfAllRegionInfoValue {

  private static String  m_boName = "com.asiainfo.crm.common.bo.QBOAmBatchWfAllRegionInfo";



  public final static  String S_InsertData = "INSERT_DATA";
  public final static  String S_SetCtrlflagBegin = "SET_CTRLFLAG_BEGIN";
  public final static  String S_KillRealWf = "KILL_REAL_WF";
  public final static  String S_RenameTable = "RENAME_TABLE";
  public final static  String S_SetCtrlflagFinish = "SET_CTRLFLAG_FINISH";
  public final static  String S_Audit = "AUDIT";
  public final static  String S_BillingCycleId = "BILLING_CYCLE_ID";
  public final static  String S_BatchWriteoffFinish = "BATCH_WRITEOFF_FINISH";
  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_Start = "START";
  public final static  String S_RunRealWf = "RUN_REAL_WF";
  public final static  String S_End = "END";
  public final static  String S_TableAnalyse = "TABLE_ANALYSE";
  public final static  String S_Init = "INIT";
  public final static  String S_BatchWriteoff = "BATCH_WRITEOFF";
  public final static  String S_SqlloadData = "SQLLOAD_DATA";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public QBOAmBatchWfAllRegionInfoBean() throws AIException {
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException {
   return S_TYPE;
 }

 public void setObjectType(ObjectType value) throws AIException {
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initInsertData(String value){
     this.initProperty(S_InsertData,value);
  }
  public  void setInsertData(String value){
     this.set(S_InsertData,value);
  }
  public  void setInsertDataNull(){
     this.set(S_InsertData,null);
  }

  public String getInsertData(){
       return DataType.getAsString(this.get(S_InsertData));
  
  }
  public String getInsertDataInitialValue(){
        return DataType.getAsString(this.getOldObj(S_InsertData));
      }

  public void initSetCtrlflagBegin(String value){
     this.initProperty(S_SetCtrlflagBegin,value);
  }
  public  void setSetCtrlflagBegin(String value){
     this.set(S_SetCtrlflagBegin,value);
  }
  public  void setSetCtrlflagBeginNull(){
     this.set(S_SetCtrlflagBegin,null);
  }

  public String getSetCtrlflagBegin(){
       return DataType.getAsString(this.get(S_SetCtrlflagBegin));
  
  }
  public String getSetCtrlflagBeginInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SetCtrlflagBegin));
      }

  public void initKillRealWf(String value){
     this.initProperty(S_KillRealWf,value);
  }
  public  void setKillRealWf(String value){
     this.set(S_KillRealWf,value);
  }
  public  void setKillRealWfNull(){
     this.set(S_KillRealWf,null);
  }

  public String getKillRealWf(){
       return DataType.getAsString(this.get(S_KillRealWf));
  
  }
  public String getKillRealWfInitialValue(){
        return DataType.getAsString(this.getOldObj(S_KillRealWf));
      }

  public void initRenameTable(String value){
     this.initProperty(S_RenameTable,value);
  }
  public  void setRenameTable(String value){
     this.set(S_RenameTable,value);
  }
  public  void setRenameTableNull(){
     this.set(S_RenameTable,null);
  }

  public String getRenameTable(){
       return DataType.getAsString(this.get(S_RenameTable));
  
  }
  public String getRenameTableInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RenameTable));
      }

  public void initSetCtrlflagFinish(String value){
     this.initProperty(S_SetCtrlflagFinish,value);
  }
  public  void setSetCtrlflagFinish(String value){
     this.set(S_SetCtrlflagFinish,value);
  }
  public  void setSetCtrlflagFinishNull(){
     this.set(S_SetCtrlflagFinish,null);
  }

  public String getSetCtrlflagFinish(){
       return DataType.getAsString(this.get(S_SetCtrlflagFinish));
  
  }
  public String getSetCtrlflagFinishInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SetCtrlflagFinish));
      }

  public void initAudit(String value){
     this.initProperty(S_Audit,value);
  }
  public  void setAudit(String value){
     this.set(S_Audit,value);
  }
  public  void setAuditNull(){
     this.set(S_Audit,null);
  }

  public String getAudit(){
       return DataType.getAsString(this.get(S_Audit));
  
  }
  public String getAuditInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Audit));
      }

  public void initBillingCycleId(long value){
     this.initProperty(S_BillingCycleId,new Long(value));
  }
  public  void setBillingCycleId(long value){
     this.set(S_BillingCycleId,new Long(value));
  }
  public  void setBillingCycleIdNull(){
     this.set(S_BillingCycleId,null);
  }

  public long getBillingCycleId(){
        return DataType.getAsLong(this.get(S_BillingCycleId));
  
  }
  public long getBillingCycleIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_BillingCycleId));
      }

  public void initBatchWriteoffFinish(String value){
     this.initProperty(S_BatchWriteoffFinish,value);
  }
  public  void setBatchWriteoffFinish(String value){
     this.set(S_BatchWriteoffFinish,value);
  }
  public  void setBatchWriteoffFinishNull(){
     this.set(S_BatchWriteoffFinish,null);
  }

  public String getBatchWriteoffFinish(){
       return DataType.getAsString(this.get(S_BatchWriteoffFinish));
  
  }
  public String getBatchWriteoffFinishInitialValue(){
        return DataType.getAsString(this.getOldObj(S_BatchWriteoffFinish));
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

  public void initStart(String value){
     this.initProperty(S_Start,value);
  }
  public  void setStart(String value){
     this.set(S_Start,value);
  }
  public  void setStartNull(){
     this.set(S_Start,null);
  }

  public String getStart(){
       return DataType.getAsString(this.get(S_Start));
  
  }
  public String getStartInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Start));
      }

  public void initRunRealWf(String value){
     this.initProperty(S_RunRealWf,value);
  }
  public  void setRunRealWf(String value){
     this.set(S_RunRealWf,value);
  }
  public  void setRunRealWfNull(){
     this.set(S_RunRealWf,null);
  }

  public String getRunRealWf(){
       return DataType.getAsString(this.get(S_RunRealWf));
  
  }
  public String getRunRealWfInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RunRealWf));
      }

  public void initEnd(String value){
     this.initProperty(S_End,value);
  }
  public  void setEnd(String value){
     this.set(S_End,value);
  }
  public  void setEndNull(){
     this.set(S_End,null);
  }

  public String getEnd(){
       return DataType.getAsString(this.get(S_End));
  
  }
  public String getEndInitialValue(){
        return DataType.getAsString(this.getOldObj(S_End));
      }

  public void initTableAnalyse(String value){
     this.initProperty(S_TableAnalyse,value);
  }
  public  void setTableAnalyse(String value){
     this.set(S_TableAnalyse,value);
  }
  public  void setTableAnalyseNull(){
     this.set(S_TableAnalyse,null);
  }

  public String getTableAnalyse(){
       return DataType.getAsString(this.get(S_TableAnalyse));
  
  }
  public String getTableAnalyseInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TableAnalyse));
      }

  public void initInit(String value){
     this.initProperty(S_Init,value);
  }
  public  void setInit(String value){
     this.set(S_Init,value);
  }
  public  void setInitNull(){
     this.set(S_Init,null);
  }

  public String getInit(){
       return DataType.getAsString(this.get(S_Init));
  
  }
  public String getInitInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Init));
      }

  public void initBatchWriteoff(String value){
     this.initProperty(S_BatchWriteoff,value);
  }
  public  void setBatchWriteoff(String value){
     this.set(S_BatchWriteoff,value);
  }
  public  void setBatchWriteoffNull(){
     this.set(S_BatchWriteoff,null);
  }

  public String getBatchWriteoff(){
       return DataType.getAsString(this.get(S_BatchWriteoff));
  
  }
  public String getBatchWriteoffInitialValue(){
        return DataType.getAsString(this.getOldObj(S_BatchWriteoff));
      }

  public void initSqlloadData(String value){
     this.initProperty(S_SqlloadData,value);
  }
  public  void setSqlloadData(String value){
     this.set(S_SqlloadData,value);
  }
  public  void setSqlloadDataNull(){
     this.set(S_SqlloadData,null);
  }

  public String getSqlloadData(){
       return DataType.getAsString(this.get(S_SqlloadData));
  
  }
  public String getSqlloadDataInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SqlloadData));
      }


 
 }

