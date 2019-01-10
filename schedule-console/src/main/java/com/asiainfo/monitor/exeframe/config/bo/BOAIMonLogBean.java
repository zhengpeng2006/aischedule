package com.asiainfo.monitor.exeframe.config.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonLogValue;

public class BOAIMonLogBean extends DataContainer implements DataContainerInterface,IBOAIMonLogValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonLog";



  public final static  String S_OperatorExpr = "OPERATOR_EXPR";
  public final static  String S_LogId = "LOG_ID";
  public final static  String S_OperateId = "OPERATE_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CreateTime = "CREATE_TIME";
  public final static  String S_Clkcode = "CLKCODE";
  public final static  String S_Clkurl = "CLKURL";
  public final static  String S_OperateName = "OPERATE_NAME";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonLogBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initOperatorExpr(String value){
     this.initProperty(S_OperatorExpr,value);
  }
  public  void setOperatorExpr(String value){
     this.set(S_OperatorExpr,value);
  }
  public  void setOperatorExprNull(){
     this.set(S_OperatorExpr,null);
  }

  public String getOperatorExpr(){
       return DataType.getAsString(this.get(S_OperatorExpr));
  
  }
  public String getOperatorExprInitialValue(){
        return DataType.getAsString(this.getOldObj(S_OperatorExpr));
      }

  public void initLogId(long value){
     this.initProperty(S_LogId,new Long(value));
  }
  public  void setLogId(long value){
     this.set(S_LogId,new Long(value));
  }
  public  void setLogIdNull(){
     this.set(S_LogId,null);
  }

  public long getLogId(){
        return DataType.getAsLong(this.get(S_LogId));
  
  }
  public long getLogIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_LogId));
      }

  public void initOperateId(long value){
     this.initProperty(S_OperateId,new Long(value));
  }
  public  void setOperateId(long value){
     this.set(S_OperateId,new Long(value));
  }
  public  void setOperateIdNull(){
     this.set(S_OperateId,null);
  }

  public long getOperateId(){
        return DataType.getAsLong(this.get(S_OperateId));
  
  }
  public long getOperateIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_OperateId));
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

  public void initClkcode(String value){
     this.initProperty(S_Clkcode,value);
  }
  public  void setClkcode(String value){
     this.set(S_Clkcode,value);
  }
  public  void setClkcodeNull(){
     this.set(S_Clkcode,null);
  }

  public String getClkcode(){
       return DataType.getAsString(this.get(S_Clkcode));
  
  }
  public String getClkcodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Clkcode));
      }

  public void initClkurl(String value){
     this.initProperty(S_Clkurl,value);
  }
  public  void setClkurl(String value){
     this.set(S_Clkurl,value);
  }
  public  void setClkurlNull(){
     this.set(S_Clkurl,null);
  }

  public String getClkurl(){
       return DataType.getAsString(this.get(S_Clkurl));
  
  }
  public String getClkurlInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Clkurl));
      }

  public void initOperateName(String value){
     this.initProperty(S_OperateName,value);
  }
  public  void setOperateName(String value){
     this.set(S_OperateName,value);
  }
  public  void setOperateNameNull(){
     this.set(S_OperateName,null);
  }

  public String getOperateName(){
       return DataType.getAsString(this.get(S_OperateName));
  
  }
  public String getOperateNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_OperateName));
      }


 
 }

