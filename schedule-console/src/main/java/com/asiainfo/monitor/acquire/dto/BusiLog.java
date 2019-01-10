package com.asiainfo.monitor.acquire.dto;

import java.sql.Date;

public class BusiLog
{

    private long serialNo; //批次号
    private String taskId; //任务标识
    private String taskSplitId; //任务分片标识
    private String serverCode; //应用编码
    private Date acqDate; //采集时间
    private long seq; //采集序列

    public long getSerialNo()
    {
        return serialNo;
    }

    public void setSerialNo(long serialNo)
    {
        this.serialNo = serialNo;
    }

    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public String getTaskSplitId()
    {
        return taskSplitId;
    }

    public void setTaskSplitId(String taskSplitId)
    {
        this.taskSplitId = taskSplitId;
    }

    public Date getAcqDate()
    {
        return acqDate;
    }

    public void setAcqDate(Date acqDate)
    {
        this.acqDate = acqDate;
    }

    public long getSeq()
    {
        return seq;
    }

    public void setSeq(long seq)
    {
        this.seq = seq;
    }

    public String getServerCode()
    {
        return serverCode;
    }

    public void setServerCode(String serverCode)
    {
        this.serverCode = serverCode;
    }
}
