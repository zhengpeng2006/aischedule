package com.asiainfo.monitor.exeframe.monitorItem.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IBOAIMonBatchValue extends DataStructInterface{

  public final static  String S_TaskCode = "TASK_CODE";
  public final static  String S_BatchId = "BATCH_ID";
  public final static  String S_Sn = "SN";


public String getTaskCode();

public long getBatchId();

public long getSn();


public  void setTaskCode(String value);

public  void setBatchId(long value);

public  void setSn(long value);
}
