package com.asiainfo.monitor.exeframe.config.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;

public class BOAIMonColgRuleBean extends DataContainer implements DataContainerInterface,IBOAIMonColgRuleValue{

  private static String  m_boName = "com.asiainfo.monitor.exeframe.config.bo.BOAIMonColgRule";



  public final static  String S_State = "STATE";
  public final static  String S_RuleId = "RULE_ID";
  public final static  String S_Expr5 = "EXPR5";
  public final static  String S_Expr1 = "EXPR1";
  public final static  String S_Expr7 = "EXPR7";
  public final static  String S_HostId = "HOST_ID";
  public final static  String S_Expr2 = "EXPR2";
  public final static  String S_Expr6 = "EXPR6";
  public final static  String S_AppId = "APP_ID";
  public final static  String S_RuleName = "RULE_NAME";
  public final static  String S_RuleKind = "RULE_KIND";
  public final static  String S_Expr3 = "EXPR3";
  public final static  String S_RuleType = "RULE_TYPE";
  public final static  String S_Expr9 = "EXPR9";
  public final static  String S_Expr4 = "EXPR4";
  public final static  String S_Expr8 = "EXPR8";
  public final static  String S_Expr0 = "EXPR0";
  public final static  String S_Remark = "REMARK";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public BOAIMonColgRuleBean() throws AIException{
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

  public void initRuleId(long value){
     this.initProperty(S_RuleId,new Long(value));
  }
  public  void setRuleId(long value){
     this.set(S_RuleId,new Long(value));
  }
  public  void setRuleIdNull(){
     this.set(S_RuleId,null);
  }

  public long getRuleId(){
        return DataType.getAsLong(this.get(S_RuleId));
  
  }
  public long getRuleIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_RuleId));
      }

  public void initExpr5(String value){
     this.initProperty(S_Expr5,value);
  }
  public  void setExpr5(String value){
     this.set(S_Expr5,value);
  }
  public  void setExpr5Null(){
     this.set(S_Expr5,null);
  }

  public String getExpr5(){
       return DataType.getAsString(this.get(S_Expr5));
  
  }
  public String getExpr5InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr5));
      }

  public void initExpr1(String value){
     this.initProperty(S_Expr1,value);
  }
  public  void setExpr1(String value){
     this.set(S_Expr1,value);
  }
  public  void setExpr1Null(){
     this.set(S_Expr1,null);
  }

  public String getExpr1(){
       return DataType.getAsString(this.get(S_Expr1));
  
  }
  public String getExpr1InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr1));
      }

  public void initExpr7(String value){
     this.initProperty(S_Expr7,value);
  }
  public  void setExpr7(String value){
     this.set(S_Expr7,value);
  }
  public  void setExpr7Null(){
     this.set(S_Expr7,null);
  }

  public String getExpr7(){
       return DataType.getAsString(this.get(S_Expr7));
  
  }
  public String getExpr7InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr7));
      }

  public void initHostId(long value){
     this.initProperty(S_HostId,new Long(value));
  }
  public  void setHostId(long value){
     this.set(S_HostId,new Long(value));
  }
  public  void setHostIdNull(){
     this.set(S_HostId,null);
  }

  public long getHostId(){
        return DataType.getAsLong(this.get(S_HostId));
  
  }
  public long getHostIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_HostId));
      }

  public void initExpr2(String value){
     this.initProperty(S_Expr2,value);
  }
  public  void setExpr2(String value){
     this.set(S_Expr2,value);
  }
  public  void setExpr2Null(){
     this.set(S_Expr2,null);
  }

  public String getExpr2(){
       return DataType.getAsString(this.get(S_Expr2));
  
  }
  public String getExpr2InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr2));
      }

  public void initExpr6(String value){
     this.initProperty(S_Expr6,value);
  }
  public  void setExpr6(String value){
     this.set(S_Expr6,value);
  }
  public  void setExpr6Null(){
     this.set(S_Expr6,null);
  }

  public String getExpr6(){
       return DataType.getAsString(this.get(S_Expr6));
  
  }
  public String getExpr6InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr6));
      }

  public void initAppId(long value){
     this.initProperty(S_AppId,new Long(value));
  }
  public  void setAppId(long value){
     this.set(S_AppId,new Long(value));
  }
  public  void setAppIdNull(){
     this.set(S_AppId,null);
  }

  public long getAppId(){
        return DataType.getAsLong(this.get(S_AppId));
  
  }
  public long getAppIdInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_AppId));
      }

  public void initRuleName(String value){
     this.initProperty(S_RuleName,value);
  }
  public  void setRuleName(String value){
     this.set(S_RuleName,value);
  }
  public  void setRuleNameNull(){
     this.set(S_RuleName,null);
  }

  public String getRuleName(){
       return DataType.getAsString(this.get(S_RuleName));
  
  }
  public String getRuleNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RuleName));
      }

  public void initRuleKind(String value){
     this.initProperty(S_RuleKind,value);
  }
  public  void setRuleKind(String value){
     this.set(S_RuleKind,value);
  }
  public  void setRuleKindNull(){
     this.set(S_RuleKind,null);
  }

  public String getRuleKind(){
       return DataType.getAsString(this.get(S_RuleKind));
  
  }
  public String getRuleKindInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RuleKind));
      }

  public void initExpr3(String value){
     this.initProperty(S_Expr3,value);
  }
  public  void setExpr3(String value){
     this.set(S_Expr3,value);
  }
  public  void setExpr3Null(){
     this.set(S_Expr3,null);
  }

  public String getExpr3(){
       return DataType.getAsString(this.get(S_Expr3));
  
  }
  public String getExpr3InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr3));
      }

  public void initRuleType(String value){
     this.initProperty(S_RuleType,value);
  }
  public  void setRuleType(String value){
     this.set(S_RuleType,value);
  }
  public  void setRuleTypeNull(){
     this.set(S_RuleType,null);
  }

  public String getRuleType(){
       return DataType.getAsString(this.get(S_RuleType));
  
  }
  public String getRuleTypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_RuleType));
      }

  public void initExpr9(String value){
     this.initProperty(S_Expr9,value);
  }
  public  void setExpr9(String value){
     this.set(S_Expr9,value);
  }
  public  void setExpr9Null(){
     this.set(S_Expr9,null);
  }

  public String getExpr9(){
       return DataType.getAsString(this.get(S_Expr9));
  
  }
  public String getExpr9InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr9));
      }

  public void initExpr4(String value){
     this.initProperty(S_Expr4,value);
  }
  public  void setExpr4(String value){
     this.set(S_Expr4,value);
  }
  public  void setExpr4Null(){
     this.set(S_Expr4,null);
  }

  public String getExpr4(){
       return DataType.getAsString(this.get(S_Expr4));
  
  }
  public String getExpr4InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr4));
      }

  public void initExpr8(String value){
     this.initProperty(S_Expr8,value);
  }
  public  void setExpr8(String value){
     this.set(S_Expr8,value);
  }
  public  void setExpr8Null(){
     this.set(S_Expr8,null);
  }

  public String getExpr8(){
       return DataType.getAsString(this.get(S_Expr8));
  
  }
  public String getExpr8InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr8));
      }

  public void initExpr0(String value){
     this.initProperty(S_Expr0,value);
  }
  public  void setExpr0(String value){
     this.set(S_Expr0,value);
  }
  public  void setExpr0Null(){
     this.set(S_Expr0,null);
  }

  public String getExpr0(){
       return DataType.getAsString(this.get(S_Expr0));
  
  }
  public String getExpr0InitialValue(){
        return DataType.getAsString(this.getOldObj(S_Expr0));
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

