package com.asiainfo.index.base.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.asiainfo.index.base.ivalues.*;

public class BOBsStaticDataBean extends DataContainer implements DataContainerInterface,IBOBsStaticDataValue{

  private static String  m_boName = "com.asiainfo.index.base.bo.BOBsStaticData";



  public final static  String S_CodeType = "CODE_TYPE";
  public final static  String S_CodeTypeAlias = "CODE_TYPE_ALIAS";
  public final static  String S_SortId = "SORT_ID";
  public final static  String S_CodeValue = "CODE_VALUE";
  public final static  String S_CodeDesc = "CODE_DESC";
  public final static  String S_ExternCodeType = "EXTERN_CODE_TYPE";
  public final static  String S_CodeName = "CODE_NAME";
  public final static  String S_State = "STATE";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOBsStaticDataBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initCodeType(String value){
     this.initProperty(S_CodeType,value);
  }
  public  void setCodeType(String value){
     this.set(S_CodeType,value);
  }
  public  void setCodeTypeNull(){
     this.set(S_CodeType,null);
  }

  public String getCodeType(){
       return DataType.getAsString(this.get(S_CodeType));
  
  }
  public String getCodeTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CodeType));
      }

  public void initCodeTypeAlias(String value){
     this.initProperty(S_CodeTypeAlias,value);
  }
  public  void setCodeTypeAlias(String value){
     this.set(S_CodeTypeAlias,value);
  }
  public  void setCodeTypeAliasNull(){
     this.set(S_CodeTypeAlias,null);
  }

  public String getCodeTypeAlias(){
       return DataType.getAsString(this.get(S_CodeTypeAlias));
  
  }
  public String getCodeTypeAliasInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CodeTypeAlias));
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

  public void initCodeValue(String value){
     this.initProperty(S_CodeValue,value);
  }
  public  void setCodeValue(String value){
     this.set(S_CodeValue,value);
  }
  public  void setCodeValueNull(){
     this.set(S_CodeValue,null);
  }

  public String getCodeValue(){
       return DataType.getAsString(this.get(S_CodeValue));
  
  }
  public String getCodeValueInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CodeValue));
      }

  public void initCodeDesc(String value){
     this.initProperty(S_CodeDesc,value);
  }
  public  void setCodeDesc(String value){
     this.set(S_CodeDesc,value);
  }
  public  void setCodeDescNull(){
     this.set(S_CodeDesc,null);
  }

  public String getCodeDesc(){
       return DataType.getAsString(this.get(S_CodeDesc));
  
  }
  public String getCodeDescInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CodeDesc));
      }

  public void initExternCodeType(String value){
     this.initProperty(S_ExternCodeType,value);
  }
  public  void setExternCodeType(String value){
     this.set(S_ExternCodeType,value);
  }
  public  void setExternCodeTypeNull(){
     this.set(S_ExternCodeType,null);
  }

  public String getExternCodeType(){
       return DataType.getAsString(this.get(S_ExternCodeType));
  
  }
  public String getExternCodeTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_ExternCodeType));
      }

  public void initCodeName(String value){
     this.initProperty(S_CodeName,value);
  }
  public  void setCodeName(String value){
     this.set(S_CodeName,value);
  }
  public  void setCodeNameNull(){
     this.set(S_CodeName,null);
  }

  public String getCodeName(){
       return DataType.getAsString(this.get(S_CodeName));
  
  }
  public String getCodeNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CodeName));
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


 
 }

