package com.asiainfo.common.abo.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.common.abo.ivalues.*;

public class BOABGMonBusiLogBean extends DataContainer implements DataContainerInterface,IBOABGMonBusiLogValue{

  private static String  m_boName = "com.asiainfo.common.abo.bo.BOABGMonBusiLog";



  public final static  String S_MonFlag = "MON_FLAG";
  public final static  String S_TaskSplitId = "TASK_SPLIT_ID";
  public final static  String S_ErrCnt = "ERR_CNT";
  public final static  String S_PerErrCnt = "PER_ERR_CNT";
  public final static  String S_RegionCode = "REGION_CODE";
  public final static  String S_SerialNo = "SERIAL_NO";
  public final static  String S_HandleCnt = "HANDLE_CNT";
  public final static  String S_SubsystemDomain = "SUBSYSTEM_DOMAIN";
  public final static  String S_SystemDomain = "SYSTEM_DOMAIN";
  public final static  String S_TotalCnt = "TOTAL_CNT";
  public final static  String S_PerHandleCnt = "PER_HANDLE_CNT";
  public final static  String S_Ext1 = "EXT1";
  public final static  String S_Ext2 = "EXT2";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_ThroughputId = "THROUGHPUT_ID";
  public final static  String S_AppServerName = "APP_SERVER_NAME";
  public final static  String S_ConsumeTime = "CONSUME_TIME";
  public final static  String S_Seq = "SEQ";
  public final static  String S_ServerCode = "SERVER_CODE";
  public final static  String S_TaskId = "TASK_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOABGMonBusiLogBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initMonFlag(String value){
     this.initProperty(S_MonFlag,value);
  }
  public  void setMonFlag(String value){
     this.set(S_MonFlag,value);
  }
  public  void setMonFlagNull(){
     this.set(S_MonFlag,null);
  }

  public String getMonFlag(){
       return DataType.getAsString(this.get(S_MonFlag));
  
  }
  public String getMonFlagInitialValue(){
        return DataType.getAsString(this.getOldObj(S_MonFlag));
      }

  public void initTaskSplitId(String value){
     this.initProperty(S_TaskSplitId,value);
  }
  public  void setTaskSplitId(String value){
     this.set(S_TaskSplitId,value);
  }
  public  void setTaskSplitIdNull(){
     this.set(S_TaskSplitId,null);
  }

  public String getTaskSplitId(){
       return DataType.getAsString(this.get(S_TaskSplitId));
  
  }
  public String getTaskSplitIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskSplitId));
      }

  public void initErrCnt(long value){
     this.initProperty(S_ErrCnt,new Long(value));
  }
  public  void setErrCnt(long value){
     this.set(S_ErrCnt,new Long(value));
  }
  public  void setErrCntNull(){
     this.set(S_ErrCnt,null);
  }

  public long getErrCnt(){
        return DataType.getAsLong(this.get(S_ErrCnt));
  
  }
  public long getErrCntInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ErrCnt));
      }

  public void initPerErrCnt(long value){
     this.initProperty(S_PerErrCnt,new Long(value));
  }
  public  void setPerErrCnt(long value){
     this.set(S_PerErrCnt,new Long(value));
  }
  public  void setPerErrCntNull(){
     this.set(S_PerErrCnt,null);
  }

  public long getPerErrCnt(){
        return DataType.getAsLong(this.get(S_PerErrCnt));
  
  }
  public long getPerErrCntInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_PerErrCnt));
      }

  public void initRegionCode(String value){
     this.initProperty(S_RegionCode,value);
  }
  public  void setRegionCode(String value){
     this.set(S_RegionCode,value);
  }
  public  void setRegionCodeNull(){
     this.set(S_RegionCode,null);
  }

  public String getRegionCode(){
       return DataType.getAsString(this.get(S_RegionCode));
  
  }
  public String getRegionCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RegionCode));
      }

  public void initSerialNo(long value){
     this.initProperty(S_SerialNo,new Long(value));
  }
  public  void setSerialNo(long value){
     this.set(S_SerialNo,new Long(value));
  }
  public  void setSerialNoNull(){
     this.set(S_SerialNo,null);
  }

  public long getSerialNo(){
        return DataType.getAsLong(this.get(S_SerialNo));
  
  }
  public long getSerialNoInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_SerialNo));
      }

  public void initHandleCnt(long value){
     this.initProperty(S_HandleCnt,new Long(value));
  }
  public  void setHandleCnt(long value){
     this.set(S_HandleCnt,new Long(value));
  }
  public  void setHandleCntNull(){
     this.set(S_HandleCnt,null);
  }

  public long getHandleCnt(){
        return DataType.getAsLong(this.get(S_HandleCnt));
  
  }
  public long getHandleCntInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_HandleCnt));
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

  public void initTotalCnt(long value){
     this.initProperty(S_TotalCnt,new Long(value));
  }
  public  void setTotalCnt(long value){
     this.set(S_TotalCnt,new Long(value));
  }
  public  void setTotalCntNull(){
     this.set(S_TotalCnt,null);
  }

  public long getTotalCnt(){
        return DataType.getAsLong(this.get(S_TotalCnt));
  
  }
  public long getTotalCntInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TotalCnt));
      }

  public void initPerHandleCnt(long value){
     this.initProperty(S_PerHandleCnt,new Long(value));
  }
  public  void setPerHandleCnt(long value){
     this.set(S_PerHandleCnt,new Long(value));
  }
  public  void setPerHandleCntNull(){
     this.set(S_PerHandleCnt,null);
  }

  public long getPerHandleCnt(){
        return DataType.getAsLong(this.get(S_PerHandleCnt));
  
  }
  public long getPerHandleCntInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_PerHandleCnt));
      }

  public void initExt1(String value){
     this.initProperty(S_Ext1,value);
  }
  public  void setExt1(String value){
     this.set(S_Ext1,value);
  }
  public  void setExt1Null(){
     this.set(S_Ext1,null);
  }

  public String getExt1(){
       return DataType.getAsString(this.get(S_Ext1));
  
  }
  public String getExt1InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Ext1));
      }

  public void initExt2(String value){
     this.initProperty(S_Ext2,value);
  }
  public  void setExt2(String value){
     this.set(S_Ext2,value);
  }
  public  void setExt2Null(){
     this.set(S_Ext2,null);
  }

  public String getExt2(){
       return DataType.getAsString(this.get(S_Ext2));
  
  }
  public String getExt2InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Ext2));
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

  public void initThroughputId(long value){
     this.initProperty(S_ThroughputId,new Long(value));
  }
  public  void setThroughputId(long value){
     this.set(S_ThroughputId,new Long(value));
  }
  public  void setThroughputIdNull(){
     this.set(S_ThroughputId,null);
  }

  public long getThroughputId(){
        return DataType.getAsLong(this.get(S_ThroughputId));
  
  }
  public long getThroughputIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ThroughputId));
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

  public void initConsumeTime(long value){
     this.initProperty(S_ConsumeTime,new Long(value));
  }
  public  void setConsumeTime(long value){
     this.set(S_ConsumeTime,new Long(value));
  }
  public  void setConsumeTimeNull(){
     this.set(S_ConsumeTime,null);
  }

  public long getConsumeTime(){
        return DataType.getAsLong(this.get(S_ConsumeTime));
  
  }
  public long getConsumeTimeInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ConsumeTime));
      }

  public void initSeq(long value){
     this.initProperty(S_Seq,new Long(value));
  }
  public  void setSeq(long value){
     this.set(S_Seq,new Long(value));
  }
  public  void setSeqNull(){
     this.set(S_Seq,null);
  }

  public long getSeq(){
        return DataType.getAsLong(this.get(S_Seq));
  
  }
  public long getSeqInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Seq));
      }

  public void initServerCode(String value){
     this.initProperty(S_ServerCode,value);
  }
  public  void setServerCode(String value){
     this.set(S_ServerCode,value);
  }
  public  void setServerCodeNull(){
     this.set(S_ServerCode,null);
  }

  public String getServerCode(){
       return DataType.getAsString(this.get(S_ServerCode));
  
  }
  public String getServerCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ServerCode));
      }

  public void initTaskId(String value){
     this.initProperty(S_TaskId,value);
  }
  public  void setTaskId(String value){
     this.set(S_TaskId,value);
  }
  public  void setTaskIdNull(){
     this.set(S_TaskId,null);
  }

  public String getTaskId(){
       return DataType.getAsString(this.get(S_TaskId));
  
  }
  public String getTaskIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TaskId));
      }


 
 }

