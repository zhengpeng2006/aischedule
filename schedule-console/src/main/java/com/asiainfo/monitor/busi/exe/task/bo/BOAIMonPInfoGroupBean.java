package com.asiainfo.monitor.busi.exe.task.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;

public class BOAIMonPInfoGroupBean extends DataContainer implements DataContainerInterface,IBOAIMonPInfoGroupValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoGroup";



  public final static  String S_State = "STATE";
  public final static  String S_SortId = "SORT_ID";
  public final static  String S_GroupStyle = "GROUP_STYLE";
  public final static  String S_GroupName = "GROUP_NAME";
  public final static  String S_GroupCode = "GROUP_CODE";
  public final static  String S_GroupId = "GROUP_ID";
  public final static  String S_GroupDesc = "GROUP_DESC";
  public final static  String S_Remark = "REMARK";
  public final static  String S_Layer = "LAYER";
  public final static  String S_ParentId = "PARENT_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonPInfoGroupBean() throws AIException{
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

  public void initSortId(int value){
     this.initProperty(S_SortId,new Integer(value));
  }
  public  void setSortId(int value){
     this.set(S_SortId,new Integer(value));
  }
  public  void setSortIdNull(){
     this.set(S_SortId,null);
  }

  public int getSortId(){
        return DataType.getAsInt(this.get(S_SortId));
  
  }
  public int getSortIdInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_SortId));
      }

  public void initGroupStyle(String value){
     this.initProperty(S_GroupStyle,value);
  }
  public  void setGroupStyle(String value){
     this.set(S_GroupStyle,value);
  }
  public  void setGroupStyleNull(){
     this.set(S_GroupStyle,null);
  }

  public String getGroupStyle(){
       return DataType.getAsString(this.get(S_GroupStyle));
  
  }
  public String getGroupStyleInitialValue(){
        return DataType.getAsString(this.getOldObj(S_GroupStyle));
      }

  public void initGroupName(String value){
     this.initProperty(S_GroupName,value);
  }
  public  void setGroupName(String value){
     this.set(S_GroupName,value);
  }
  public  void setGroupNameNull(){
     this.set(S_GroupName,null);
  }

  public String getGroupName(){
       return DataType.getAsString(this.get(S_GroupName));
  
  }
  public String getGroupNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_GroupName));
      }

  public void initGroupCode(String value){
     this.initProperty(S_GroupCode,value);
  }
  public  void setGroupCode(String value){
     this.set(S_GroupCode,value);
  }
  public  void setGroupCodeNull(){
     this.set(S_GroupCode,null);
  }

  public String getGroupCode(){
       return DataType.getAsString(this.get(S_GroupCode));
  
  }
  public String getGroupCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_GroupCode));
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

  public void initGroupDesc(String value){
     this.initProperty(S_GroupDesc,value);
  }
  public  void setGroupDesc(String value){
     this.set(S_GroupDesc,value);
  }
  public  void setGroupDescNull(){
     this.set(S_GroupDesc,null);
  }

  public String getGroupDesc(){
       return DataType.getAsString(this.get(S_GroupDesc));
  
  }
  public String getGroupDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_GroupDesc));
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

  public void initParentId(long value){
     this.initProperty(S_ParentId,new Long(value));
  }
  public  void setParentId(long value){
     this.set(S_ParentId,new Long(value));
  }
  public  void setParentIdNull(){
     this.set(S_ParentId,null);
  }

  public long getParentId(){
        return DataType.getAsLong(this.get(S_ParentId));
  
  }
  public long getParentIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ParentId));
      }


 
 }

