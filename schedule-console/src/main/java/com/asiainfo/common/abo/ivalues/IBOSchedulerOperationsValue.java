package com.asiainfo.common.abo.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOSchedulerOperationsValue extends DataStructInterface{

  public final static  String S_OperationClientIp = "OPERATION_CLIENT_IP";
  public final static  String S_SubsystemDomain = "SUBSYSTEM_DOMAIN";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_OperationSource = "OPERATION_SOURCE";
  public final static  String S_SystemDomain = "SYSTEM_DOMAIN";
  public final static  String S_OperationObjectContent = "OPERATION_OBJECT_CONTENT";
  public final static  String S_OperationType = "OPERATION_TYPE";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_Operator = "OPERATOR";
  public final static  String S_AppServerName = "APP_SERVER_NAME";
  public final static  String S_OperationModel = "OPERATION_MODEL";
  public final static  String S_OperationId = "OPERATION_ID";
  public final static  String S_OperationObjectType = "OPERATION_OBJECT_TYPE";


public String getOperationClientIp();

public String getSubsystemDomain();

public String getRemarks();

public String getOperationSource();

public String getSystemDomain();

public String getOperationObjectContent();

public String getOperationType();

public Timestamp getCreateDate();

public String getOperator();

public String getAppServerName();

public String getOperationModel();

public long getOperationId();

public String getOperationObjectType();


public  void setOperationClientIp(String value);

public  void setSubsystemDomain(String value);

public  void setRemarks(String value);

public  void setOperationSource(String value);

public  void setSystemDomain(String value);

public  void setOperationObjectContent(String value);

public  void setOperationType(String value);

public  void setCreateDate(Timestamp value);

public  void setOperator(String value);

public  void setAppServerName(String value);

public  void setOperationModel(String value);

public  void setOperationId(long value);

public  void setOperationObjectType(String value);
}
