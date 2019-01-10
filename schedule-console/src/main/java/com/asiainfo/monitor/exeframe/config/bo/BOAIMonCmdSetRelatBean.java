package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetRelatValue;

public class BOAIMonCmdSetRelatBean extends DataContainer implements DataContainerInterface,IBOAIMonCmdSetRelatValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonCmdSetRelat";



  public final static  String S_State = "STATE";
  public final static  String S_CmdId = "CMD_ID";
  public final static  String S_CmdSeq = "CMD_SEQ";
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
  public BOAIMonCmdSetRelatBean() throws AIException{
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

  public void initCmdSeq(int value){
     this.initProperty(S_CmdSeq,new Integer(value));
  }
  public  void setCmdSeq(int value){
     this.set(S_CmdSeq,new Integer(value));
  }
  public  void setCmdSeqNull(){
     this.set(S_CmdSeq,null);
  }

  public int getCmdSeq(){
        return DataType.getAsInt(this.get(S_CmdSeq));
  
  }
  public int getCmdSeqInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_CmdSeq));
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

