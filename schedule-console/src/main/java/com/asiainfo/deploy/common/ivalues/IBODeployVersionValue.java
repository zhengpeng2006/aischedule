package com.asiainfo.deploy.common.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBODeployVersionValue extends DataStructInterface{

  public final static  String S_Contacts = "CONTACTS";
  public final static  String S_PackagePath = "PACKAGE_PATH";
  public final static  String S_ParentVersionId = "PARENT_VERSION_ID";
  public final static  String S_FileList = "FILE_LIST";
  public final static  String S_ResolvedProblems = "RESOLVED_PROBLEMS";
  public final static  String S_State = "STATE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_OperateType = "OPERATE_TYPE";
  public final static  String S_DeployStrategyId = "DEPLOY_STRATEGY_ID";
  public final static  String S_VersionId = "VERSION_ID";
  public final static  String S_OperatorId = "OPERATOR_ID";
  public final static  String S_ExternalVersionId = "EXTERNAL_VERSION_ID";
  public final static  String S_CreateTime = "CREATE_TIME";


public String getContacts();

public String getPackagePath();

public long getParentVersionId();

public String getFileList();

public String getResolvedProblems();

public String getState();

public String getRemarks();

public String getOperateType();

public long getDeployStrategyId();

public long getVersionId();

public long getOperatorId();

public long getExternalVersionId();

public Timestamp getCreateTime();


public  void setContacts(String value);

public  void setPackagePath(String value);

public  void setParentVersionId(long value);

public  void setFileList(String value);

public  void setResolvedProblems(String value);

public  void setState(String value);

public  void setRemarks(String value);

public  void setOperateType(String value);

public  void setDeployStrategyId(long value);

public  void setVersionId(long value);

public  void setOperatorId(long value);

public  void setExternalVersionId(long value);

public  void setCreateTime(Timestamp value);
}
