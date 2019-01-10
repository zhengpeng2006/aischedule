package com.asiainfo.batchwriteoff.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.*;
import com.ai.common.ivalues.IBOBsDistrictValue;

public class BOBsDistrictBean extends DataContainer implements DataContainerInterface,IBOBsDistrictValue {

  private static String  m_boName = "com.asiainfo.crm.so.common.bo.BOBsDistrict";



  public final static  String S_ParentDistrictId = "PARENT_DISTRICT_ID";
  public final static  String S_CourseFlag = "COURSE_FLAG";
  public final static  String S_SortId = "SORT_ID";
  public final static  String S_AreaCode = "AREA_CODE";
  public final static  String S_DistrictEnglishName = "DISTRICT_ENGLISH_NAME";
  public final static  String S_RegionId = "REGION_ID";
  public final static  String S_DistrictTypeId = "DISTRICT_TYPE_ID";
  public final static  String S_DistrictId = "DISTRICT_ID";
  public final static  String S_CenterInfoCode = "CENTER_INFO_CODE";
  public final static  String S_PostCode = "POST_CODE";
  public final static  String S_DistrictName = "DISTRICT_NAME";
  public final static  String S_DistrictTwoNumber = "DISTRICT_TWO_NUMBER";
  public final static  String S_Notes = "NOTES";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOBsDistrictBean() throws AIException {
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException {
   return S_TYPE;
 }

 public void setObjectType(ObjectType value) throws AIException {
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initParentDistrictId(long value){
     this.initProperty(S_ParentDistrictId,new Long(value));
  }
  public  void setParentDistrictId(long value){
     this.set(S_ParentDistrictId,new Long(value));
  }
  public  void setParentDistrictIdNull(){
     this.set(S_ParentDistrictId,null);
  }

  public long getParentDistrictId(){
        return DataType.getAsLong(this.get(S_ParentDistrictId));
  
  }
  public long getParentDistrictIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_ParentDistrictId));
      }

  public void initCourseFlag(int value){
     this.initProperty(S_CourseFlag,new Integer(value));
  }
  public  void setCourseFlag(int value){
     this.set(S_CourseFlag,new Integer(value));
  }
  public  void setCourseFlagNull(){
     this.set(S_CourseFlag,null);
  }

  public int getCourseFlag(){
        return DataType.getAsInt(this.get(S_CourseFlag));
  
  }
  public int getCourseFlagInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_CourseFlag));
      }

  public void initSortId(long value){
     this.initProperty(S_SortId,new Long(value));
  }
  public  void setSortId(long value){
     this.set(S_SortId,new Long(value));
  }
  public  void setSortIdNull(){
     this.set(S_SortId,null);
  }

  public long getSortId(){
        return DataType.getAsLong(this.get(S_SortId));
  
  }
  public long getSortIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_SortId));
      }

  public void initAreaCode(String value){
     this.initProperty(S_AreaCode,value);
  }
  public  void setAreaCode(String value){
     this.set(S_AreaCode,value);
  }
  public  void setAreaCodeNull(){
     this.set(S_AreaCode,null);
  }

  public String getAreaCode(){
       return DataType.getAsString(this.get(S_AreaCode));
  
  }
  public String getAreaCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_AreaCode));
      }

  public void initDistrictEnglishName(String value){
     this.initProperty(S_DistrictEnglishName,value);
  }
  public  void setDistrictEnglishName(String value){
     this.set(S_DistrictEnglishName,value);
  }
  public  void setDistrictEnglishNameNull(){
     this.set(S_DistrictEnglishName,null);
  }

  public String getDistrictEnglishName(){
       return DataType.getAsString(this.get(S_DistrictEnglishName));
  
  }
  public String getDistrictEnglishNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DistrictEnglishName));
      }

  public void initRegionId(String value){
     this.initProperty(S_RegionId,value);
  }
  public  void setRegionId(String value){
     this.set(S_RegionId,value);
  }
  public  void setRegionIdNull(){
     this.set(S_RegionId,null);
  }

  public String getRegionId(){
       return DataType.getAsString(this.get(S_RegionId));
  
  }
  public String getRegionIdInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RegionId));
      }

  public void initDistrictTypeId(int value){
     this.initProperty(S_DistrictTypeId,new Integer(value));
  }
  public  void setDistrictTypeId(int value){
     this.set(S_DistrictTypeId,new Integer(value));
  }
  public  void setDistrictTypeIdNull(){
     this.set(S_DistrictTypeId,null);
  }

  public int getDistrictTypeId(){
        return DataType.getAsInt(this.get(S_DistrictTypeId));
  
  }
  public int getDistrictTypeIdInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_DistrictTypeId));
      }

  public void initDistrictId(long value){
     this.initProperty(S_DistrictId,new Long(value));
  }
  public  void setDistrictId(long value){
     this.set(S_DistrictId,new Long(value));
  }
  public  void setDistrictIdNull(){
     this.set(S_DistrictId,null);
  }

  public long getDistrictId(){
        return DataType.getAsLong(this.get(S_DistrictId));
  
  }
  public long getDistrictIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_DistrictId));
      }

  public void initCenterInfoCode(String value){
     this.initProperty(S_CenterInfoCode,value);
  }
  public  void setCenterInfoCode(String value){
     this.set(S_CenterInfoCode,value);
  }
  public  void setCenterInfoCodeNull(){
     this.set(S_CenterInfoCode,null);
  }

  public String getCenterInfoCode(){
       return DataType.getAsString(this.get(S_CenterInfoCode));
  
  }
  public String getCenterInfoCodeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_CenterInfoCode));
      }

  public void initPostCode(int value){
     this.initProperty(S_PostCode,new Integer(value));
  }
  public  void setPostCode(int value){
     this.set(S_PostCode,new Integer(value));
  }
  public  void setPostCodeNull(){
     this.set(S_PostCode,null);
  }

  public int getPostCode(){
        return DataType.getAsInt(this.get(S_PostCode));
  
  }
  public int getPostCodeInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_PostCode));
      }

  public void initDistrictName(String value){
     this.initProperty(S_DistrictName,value);
  }
  public  void setDistrictName(String value){
     this.set(S_DistrictName,value);
  }
  public  void setDistrictNameNull(){
     this.set(S_DistrictName,null);
  }

  public String getDistrictName(){
       return DataType.getAsString(this.get(S_DistrictName));
  
  }
  public String getDistrictNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_DistrictName));
      }

  public void initDistrictTwoNumber(int value){
     this.initProperty(S_DistrictTwoNumber,new Integer(value));
  }
  public  void setDistrictTwoNumber(int value){
     this.set(S_DistrictTwoNumber,new Integer(value));
  }
  public  void setDistrictTwoNumberNull(){
     this.set(S_DistrictTwoNumber,null);
  }

  public int getDistrictTwoNumber(){
        return DataType.getAsInt(this.get(S_DistrictTwoNumber));
  
  }
  public int getDistrictTwoNumberInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_DistrictTwoNumber));
      }

  public void initNotes(String value){
     this.initProperty(S_Notes,value);
  }
  public  void setNotes(String value){
     this.set(S_Notes,value);
  }
  public  void setNotesNull(){
     this.set(S_Notes,null);
  }

  public String getNotes(){
       return DataType.getAsString(this.get(S_Notes));
  
  }
  public String getNotesInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Notes));
      }


 
 }

