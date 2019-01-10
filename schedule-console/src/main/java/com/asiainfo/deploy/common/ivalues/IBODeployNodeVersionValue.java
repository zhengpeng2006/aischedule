package com.asiainfo.deploy.common.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBODeployNodeVersionValue extends DataStructInterface{

  public final static  String S_VersionId = "VERSION_ID";
  public final static  String S_CreateDate = "CREATE_DATE";
  public final static  String S_OperatorId = "OPERATOR_ID";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_NodeId = "NODE_ID";


public long getVersionId();

public Timestamp getCreateDate();

public long getOperatorId();

public String getRemarks();

public long getNodeId();


public  void setVersionId(long value);

public  void setCreateDate(Timestamp value);

public  void setOperatorId(long value);

public  void setRemarks(String value);

public  void setNodeId(long value);
}
