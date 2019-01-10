package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSVStatisticsTmpValue;

public class BOAIMonSVStatisticsTmpBean extends DataContainer implements DataContainerInterface,IBOAIMonSVStatisticsTmpValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonSVStatisticsTmp";



  public final static  String S_Issuccess = "ISSUCCESS";
  public final static  String S_Classname = "CLASSNAME";
  public final static  String S_Flag = "FLAG";
  public final static  String S_Methodname = "METHODNAME";
  public final static  String S_Logdate = "LOGDATE";
  public final static  String S_TmpId = "TMP_ID";
  public final static  String S_Usetime = "USETIME";
  public final static  String S_Usercode = "USERCODE";
  public final static  String S_Cputime = "CPUTIME";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonSVStatisticsTmpBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initIssuccess(int value){
     this.initProperty(S_Issuccess,new Integer(value));
  }
  public  void setIssuccess(int value){
     this.set(S_Issuccess,new Integer(value));
  }
  public  void setIssuccessNull(){
     this.set(S_Issuccess,null);
  }

  public int getIssuccess(){
        return DataType.getAsInt(this.get(S_Issuccess));
  
  }
  public int getIssuccessInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_Issuccess));
      }

  public void initClassname(String value){
     this.initProperty(S_Classname,value);
  }
  public  void setClassname(String value){
     this.set(S_Classname,value);
  }
  public  void setClassnameNull(){
     this.set(S_Classname,null);
  }

  public String getClassname(){
       return DataType.getAsString(this.get(S_Classname));
  
  }
  public String getClassnameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Classname));
      }

  public void initFlag(int value){
     this.initProperty(S_Flag,new Integer(value));
  }
  public  void setFlag(int value){
     this.set(S_Flag,new Integer(value));
  }
  public  void setFlagNull(){
     this.set(S_Flag,null);
  }

  public int getFlag(){
        return DataType.getAsInt(this.get(S_Flag));
  
  }
  public int getFlagInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_Flag));
      }

  public void initMethodname(String value){
     this.initProperty(S_Methodname,value);
  }
  public  void setMethodname(String value){
     this.set(S_Methodname,value);
  }
  public  void setMethodnameNull(){
     this.set(S_Methodname,null);
  }

  public String getMethodname(){
       return DataType.getAsString(this.get(S_Methodname));
  
  }
  public String getMethodnameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Methodname));
      }

  public void initLogdate(long value){
     this.initProperty(S_Logdate,new Long(value));
  }
  public  void setLogdate(long value){
     this.set(S_Logdate,new Long(value));
  }
  public  void setLogdateNull(){
     this.set(S_Logdate,null);
  }

  public long getLogdate(){
        return DataType.getAsLong(this.get(S_Logdate));
  
  }
  public long getLogdateInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Logdate));
      }

  public void initTmpId(long value){
     this.initProperty(S_TmpId,new Long(value));
  }
  public  void setTmpId(long value){
     this.set(S_TmpId,new Long(value));
  }
  public  void setTmpIdNull(){
     this.set(S_TmpId,null);
  }

  public long getTmpId(){
        return DataType.getAsLong(this.get(S_TmpId));
  
  }
  public long getTmpIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_TmpId));
      }

  public void initUsetime(long value){
     this.initProperty(S_Usetime,new Long(value));
  }
  public  void setUsetime(long value){
     this.set(S_Usetime,new Long(value));
  }
  public  void setUsetimeNull(){
     this.set(S_Usetime,null);
  }

  public long getUsetime(){
        return DataType.getAsLong(this.get(S_Usetime));
  
  }
  public long getUsetimeInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Usetime));
      }

  public void initUsercode(long value){
     this.initProperty(S_Usercode,new Long(value));
  }
  public  void setUsercode(long value){
     this.set(S_Usercode,new Long(value));
  }
  public  void setUsercodeNull(){
     this.set(S_Usercode,null);
  }

  public long getUsercode(){
        return DataType.getAsLong(this.get(S_Usercode));
  
  }
  public long getUsercodeInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Usercode));
      }

  public void initCputime(long value){
     this.initProperty(S_Cputime,new Long(value));
  }
  public  void setCputime(long value){
     this.set(S_Cputime,new Long(value));
  }
  public  void setCputimeNull(){
     this.set(S_Cputime,null);
  }

  public long getCputime(){
        return DataType.getAsLong(this.get(S_Cputime));
  
  }
  public long getCputimeInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Cputime));
      }


 
 }

