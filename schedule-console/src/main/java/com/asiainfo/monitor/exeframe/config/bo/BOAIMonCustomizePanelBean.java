package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelValue;

public class BOAIMonCustomizePanelBean extends DataContainer implements DataContainerInterface,IBOAIMonCustomizePanelValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonCustomizePanel";



  public final static  String S_Layer = "LAYER";
  public final static  String S_State = "STATE";
  public final static  String S_TempType = "TEMP_TYPE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_CpanelId = "CPANEL_ID";
  public final static  String S_CustType = "CUST_TYPE";
  public final static  String S_CpanelName = "CPANEL_NAME";
  public final static  String S_CpanelDesc = "CPANEL_DESC";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonCustomizePanelBean() throws AIException{
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

  public void initCpanelId(long value){
     this.initProperty(S_CpanelId,new Long(value));
  }
  public  void setCpanelId(long value){
     this.set(S_CpanelId,new Long(value));
  }
  public  void setCpanelIdNull(){
     this.set(S_CpanelId,null);
  }

  public long getCpanelId(){
        return DataType.getAsLong(this.get(S_CpanelId));
  
  }
  public long getCpanelIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_CpanelId));
      }

  public void initCustType(String value){
     this.initProperty(S_CustType,value);
  }
  public  void setCustType(String value){
     this.set(S_CustType,value);
  }
  public  void setCustTypeNull(){
     this.set(S_CustType,null);
  }

  public String getCustType(){
       return DataType.getAsString(this.get(S_CustType));
  
  }
  public String getCustTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CustType));
      }

  public void initCpanelName(String value){
     this.initProperty(S_CpanelName,value);
  }
  public  void setCpanelName(String value){
     this.set(S_CpanelName,value);
  }
  public  void setCpanelNameNull(){
     this.set(S_CpanelName,null);
  }

  public String getCpanelName(){
       return DataType.getAsString(this.get(S_CpanelName));
  
  }
  public String getCpanelNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CpanelName));
      }

  public void initCpanelDesc(String value){
     this.initProperty(S_CpanelDesc,value);
  }
  public  void setCpanelDesc(String value){
     this.set(S_CpanelDesc,value);
  }
  public  void setCpanelDescNull(){
     this.set(S_CpanelDesc,null);
  }

  public String getCpanelDesc(){
       return DataType.getAsString(this.get(S_CpanelDesc));
  
  }
  public String getCpanelDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CpanelDesc));
      }


 
 }

