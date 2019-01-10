package com.asiainfo.monitor.busi.exe.task.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPImgDataResolveValue;

public class BOAIMonPImgDataResolveBean extends DataContainer implements DataContainerInterface,IBOAIMonPImgDataResolveValue{

    private static String m_boName = "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPImgDataResolve";



  public final static  String S_State = "STATE";
  public final static  String S_ShowValuePos = "SHOW_VALUE_POS";
  public final static  String S_SortBy = "SORT_BY";
  public final static  String S_ShowType = "SHOW_TYPE";
  public final static  String S_ShowNamePos = "SHOW_NAME_POS";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_TransformClass = "TRANSFORM_CLASS";
  public final static  String S_Name = "NAME";
  public final static  String S_ResolveId = "RESOLVE_ID";
  public final static  String S_ParentId = "PARENT_ID";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonPImgDataResolveBean() throws AIException{
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

  public void initShowValuePos(int value){
     this.initProperty(S_ShowValuePos,new Integer(value));
  }
  public  void setShowValuePos(int value){
     this.set(S_ShowValuePos,new Integer(value));
  }
  public  void setShowValuePosNull(){
     this.set(S_ShowValuePos,null);
  }

  public int getShowValuePos(){
        return DataType.getAsInt(this.get(S_ShowValuePos));
  
  }
  public int getShowValuePosInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_ShowValuePos));
      }

  public void initSortBy(String value){
     this.initProperty(S_SortBy,value);
  }
  public  void setSortBy(String value){
     this.set(S_SortBy,value);
  }
  public  void setSortByNull(){
     this.set(S_SortBy,null);
  }

  public String getSortBy(){
       return DataType.getAsString(this.get(S_SortBy));
  
  }
  public String getSortByInitialValue(){
        return DataType.getAsString(this.getOldObj(S_SortBy));
      }

  public void initShowType(String value){
     this.initProperty(S_ShowType,value);
  }
  public  void setShowType(String value){
     this.set(S_ShowType,value);
  }
  public  void setShowTypeNull(){
     this.set(S_ShowType,null);
  }

  public String getShowType(){
       return DataType.getAsString(this.get(S_ShowType));
  
  }
  public String getShowTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ShowType));
      }

  public void initShowNamePos(int value){
     this.initProperty(S_ShowNamePos,new Integer(value));
  }
  public  void setShowNamePos(int value){
     this.set(S_ShowNamePos,new Integer(value));
  }
  public  void setShowNamePosNull(){
     this.set(S_ShowNamePos,null);
  }

  public int getShowNamePos(){
        return DataType.getAsInt(this.get(S_ShowNamePos));
  
  }
  public int getShowNamePosInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_ShowNamePos));
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

  public void initTransformClass(String value){
     this.initProperty(S_TransformClass,value);
  }
  public  void setTransformClass(String value){
     this.set(S_TransformClass,value);
  }
  public  void setTransformClassNull(){
     this.set(S_TransformClass,null);
  }

  public String getTransformClass(){
       return DataType.getAsString(this.get(S_TransformClass));
  
  }
  public String getTransformClassInitialValue(){
        return DataType.getAsString(this.getOldObj(S_TransformClass));
      }

  public void initName(String value){
     this.initProperty(S_Name,value);
  }
  public  void setName(String value){
     this.set(S_Name,value);
  }
  public  void setNameNull(){
     this.set(S_Name,null);
  }

  public String getName(){
       return DataType.getAsString(this.get(S_Name));
  
  }
  public String getNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Name));
      }

  public void initResolveId(long value){
     this.initProperty(S_ResolveId,new Long(value));
  }
  public  void setResolveId(long value){
     this.set(S_ResolveId,new Long(value));
  }
  public  void setResolveIdNull(){
     this.set(S_ResolveId,null);
  }

  public long getResolveId(){
        return DataType.getAsLong(this.get(S_ResolveId));
  
  }
  public long getResolveIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ResolveId));
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

