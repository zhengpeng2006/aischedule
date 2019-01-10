package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonServerRouteValue;

public class BOAIMonServerRouteBean extends DataContainer implements DataContainerInterface,IBOAIMonServerRouteValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonServerRoute";



  public final static  String S_RouteId = "ROUTE_ID";
  public final static  String S_DNodeCode = "D_NODE_CODE";
  public final static  String S_SServId = "S_SERV_ID";
  public final static  String S_State = "STATE";
  public final static  String S_DNodeId = "D_NODE_ID";
  public final static  String S_SNodeId = "S_NODE_ID";
  public final static  String S_SNodeCode = "S_NODE_CODE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_DServCode = "D_SERV_CODE";
  public final static  String S_SServCode = "S_SERV_CODE";
  public final static  String S_DServId = "D_SERV_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonServerRouteBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initRouteId(long value){
     this.initProperty(S_RouteId,new Long(value));
  }
  public  void setRouteId(long value){
     this.set(S_RouteId,new Long(value));
  }
  public  void setRouteIdNull(){
     this.set(S_RouteId,null);
  }

  public long getRouteId(){
        return DataType.getAsLong(this.get(S_RouteId));
  
  }
  public long getRouteIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RouteId));
      }

  public void initDNodeCode(String value){
     this.initProperty(S_DNodeCode,value);
  }
  public  void setDNodeCode(String value){
     this.set(S_DNodeCode,value);
  }
  public  void setDNodeCodeNull(){
     this.set(S_DNodeCode,null);
  }

  public String getDNodeCode(){
       return DataType.getAsString(this.get(S_DNodeCode));
  
  }
  public String getDNodeCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DNodeCode));
      }

  public void initSServId(long value){
     this.initProperty(S_SServId,new Long(value));
  }
  public  void setSServId(long value){
     this.set(S_SServId,new Long(value));
  }
  public  void setSServIdNull(){
     this.set(S_SServId,null);
  }

  public long getSServId(){
        return DataType.getAsLong(this.get(S_SServId));
  
  }
  public long getSServIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_SServId));
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

  public void initDNodeId(long value){
     this.initProperty(S_DNodeId,new Long(value));
  }
  public  void setDNodeId(long value){
     this.set(S_DNodeId,new Long(value));
  }
  public  void setDNodeIdNull(){
     this.set(S_DNodeId,null);
  }

  public long getDNodeId(){
        return DataType.getAsLong(this.get(S_DNodeId));
  
  }
  public long getDNodeIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_DNodeId));
      }

  public void initSNodeId(long value){
     this.initProperty(S_SNodeId,new Long(value));
  }
  public  void setSNodeId(long value){
     this.set(S_SNodeId,new Long(value));
  }
  public  void setSNodeIdNull(){
     this.set(S_SNodeId,null);
  }

  public long getSNodeId(){
        return DataType.getAsLong(this.get(S_SNodeId));
  
  }
  public long getSNodeIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_SNodeId));
      }

  public void initSNodeCode(String value){
     this.initProperty(S_SNodeCode,value);
  }
  public  void setSNodeCode(String value){
     this.set(S_SNodeCode,value);
  }
  public  void setSNodeCodeNull(){
     this.set(S_SNodeCode,null);
  }

  public String getSNodeCode(){
       return DataType.getAsString(this.get(S_SNodeCode));
  
  }
  public String getSNodeCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SNodeCode));
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

  public void initDServCode(String value){
     this.initProperty(S_DServCode,value);
  }
  public  void setDServCode(String value){
     this.set(S_DServCode,value);
  }
  public  void setDServCodeNull(){
     this.set(S_DServCode,null);
  }

  public String getDServCode(){
       return DataType.getAsString(this.get(S_DServCode));
  
  }
  public String getDServCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DServCode));
      }

  public void initSServCode(String value){
     this.initProperty(S_SServCode,value);
  }
  public  void setSServCode(String value){
     this.set(S_SServCode,value);
  }
  public  void setSServCodeNull(){
     this.set(S_SServCode,null);
  }

  public String getSServCode(){
       return DataType.getAsString(this.get(S_SServCode));
  
  }
  public String getSServCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SServCode));
      }

  public void initDServId(long value){
     this.initProperty(S_DServId,new Long(value));
  }
  public  void setDServId(long value){
     this.set(S_DServId,new Long(value));
  }
  public  void setDServIdNull(){
     this.set(S_DServId,null);
  }

  public long getDServId(){
        return DataType.getAsLong(this.get(S_DServId));
  
  }
  public long getDServIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_DServId));
      }


 
 }

