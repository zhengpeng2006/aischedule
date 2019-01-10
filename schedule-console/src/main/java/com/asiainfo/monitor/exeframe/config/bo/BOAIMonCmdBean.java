package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdValue;

public class BOAIMonCmdBean extends DataContainer implements DataContainerInterface,IBOAIMonCmdValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonCmd";



  public final static  String S_CmdName = "CMD_NAME";
  public final static  String S_State = "STATE";
  public final static  String S_CmdId = "CMD_ID";
  public final static  String S_CmdExpr = "CMD_EXPR";
  public final static  String S_CmdType = "CMD_TYPE";
  public final static  String S_CmdDesc = "CMD_DESC";
  public final static  String S_Remark = "REMARK";
  public final static  String S_ParamType = "PARAM_TYPE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonCmdBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initCmdName(String value){
     this.initProperty(S_CmdName,value);
  }
  public  void setCmdName(String value){
     this.set(S_CmdName,value);
  }
  public  void setCmdNameNull(){
     this.set(S_CmdName,null);
  }

  public String getCmdName(){
       return DataType.getAsString(this.get(S_CmdName));
  
  }
  public String getCmdNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CmdName));
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

  public void initCmdId(long value){
     this.initProperty(S_CmdId,new Long(value));
  }
  public  void setCmdId(long value){
     this.set(S_CmdId,new Long(value));
  }
  public  void setCmdIdNull(){
     this.set(S_CmdId,null);
  }

  public long getCmdId(){
        return DataType.getAsLong(this.get(S_CmdId));
  
  }
  public long getCmdIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_CmdId));
      }

  public void initCmdExpr(String value){
     this.initProperty(S_CmdExpr,value);
  }
  public  void setCmdExpr(String value){
     this.set(S_CmdExpr,value);
  }
  public  void setCmdExprNull(){
     this.set(S_CmdExpr,null);
  }

  public String getCmdExpr(){
       return DataType.getAsString(this.get(S_CmdExpr));
  
  }
  public String getCmdExprInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CmdExpr));
      }

  public void initCmdType(String value){
     this.initProperty(S_CmdType,value);
  }
  public  void setCmdType(String value){
     this.set(S_CmdType,value);
  }
  public  void setCmdTypeNull(){
     this.set(S_CmdType,null);
  }

  public String getCmdType(){
       return DataType.getAsString(this.get(S_CmdType));
  
  }
  public String getCmdTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CmdType));
      }

  public void initCmdDesc(String value){
     this.initProperty(S_CmdDesc,value);
  }
  public  void setCmdDesc(String value){
     this.set(S_CmdDesc,value);
  }
  public  void setCmdDescNull(){
     this.set(S_CmdDesc,null);
  }

  public String getCmdDesc(){
       return DataType.getAsString(this.get(S_CmdDesc));
  
  }
  public String getCmdDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CmdDesc));
      }

  public void initRemark(String value){
     this.initProperty(S_Remark,value);
  }
  public  void setRemark(String value){
     this.set(S_Remark,value);
  }
  public  void setRemarkNull(){
     this.set(S_Remark,null);
  }

  public String getRemark(){
       return DataType.getAsString(this.get(S_Remark));
  
  }
  public String getRemarkInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Remark));
      }

  public void initParamType(String value){
     this.initProperty(S_ParamType,value);
  }
  public  void setParamType(String value){
     this.set(S_ParamType,value);
  }
  public  void setParamTypeNull(){
     this.set(S_ParamType,null);
  }

  public String getParamType(){
       return DataType.getAsString(this.get(S_ParamType));
  
  }
  public String getParamTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ParamType));
      }


 
 }

