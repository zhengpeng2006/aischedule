package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetValue;

public class BOAIMonCmdSetBean extends DataContainer implements DataContainerInterface,IBOAIMonCmdSetValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonCmdSet";



  public final static  String S_CmdsetCode = "CMDSET_CODE";
  public final static  String S_State = "STATE";
  public final static  String S_CmdsetDesc = "CMDSET_DESC";
  public final static  String S_CmdsetName = "CMDSET_NAME";
  public final static  String S_CmdsetId = "CMDSET_ID";
  public final static  String S_Remark = "REMARK";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonCmdSetBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initCmdsetCode(String value){
     this.initProperty(S_CmdsetCode,value);
  }
  public  void setCmdsetCode(String value){
     this.set(S_CmdsetCode,value);
  }
  public  void setCmdsetCodeNull(){
     this.set(S_CmdsetCode,null);
  }

  public String getCmdsetCode(){
       return DataType.getAsString(this.get(S_CmdsetCode));
  
  }
  public String getCmdsetCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CmdsetCode));
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

  public void initCmdsetDesc(String value){
     this.initProperty(S_CmdsetDesc,value);
  }
  public  void setCmdsetDesc(String value){
     this.set(S_CmdsetDesc,value);
  }
  public  void setCmdsetDescNull(){
     this.set(S_CmdsetDesc,null);
  }

  public String getCmdsetDesc(){
       return DataType.getAsString(this.get(S_CmdsetDesc));
  
  }
  public String getCmdsetDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CmdsetDesc));
      }

  public void initCmdsetName(String value){
     this.initProperty(S_CmdsetName,value);
  }
  public  void setCmdsetName(String value){
     this.set(S_CmdsetName,value);
  }
  public  void setCmdsetNameNull(){
     this.set(S_CmdsetName,null);
  }

  public String getCmdsetName(){
       return DataType.getAsString(this.get(S_CmdsetName));
  
  }
  public String getCmdsetNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CmdsetName));
      }

  public void initCmdsetId(long value){
     this.initProperty(S_CmdsetId,new Long(value));
  }
  public  void setCmdsetId(long value){
     this.set(S_CmdsetId,new Long(value));
  }
  public  void setCmdsetIdNull(){
     this.set(S_CmdsetId,null);
  }

  public long getCmdsetId(){
        return DataType.getAsLong(this.get(S_CmdsetId));
  
  }
  public long getCmdsetIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_CmdsetId));
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


 
 }

