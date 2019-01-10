package com.asiainfo.deploy.common.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBODeployNodeStrategyRelationValue extends DataStructInterface{

  public final static  String S_DeployStrategyId = "DEPLOY_STRATEGY_ID";
  public final static  String S_CreateTime = "CREATE_TIME";
  public final static  String S_NodeId = "NODE_ID";
  public final static  String S_ModifyTime = "MODIFY_TIME";


public long getDeployStrategyId();

public Timestamp getCreateTime();

public long getNodeId();

public Timestamp getModifyTime();


public  void setDeployStrategyId(long value);

public  void setCreateTime(Timestamp value);

public  void setNodeId(long value);

public  void setModifyTime(Timestamp value);
}
