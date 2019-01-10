package com.asiainfo.monitor.acquire.dto;

import java.util.Date;


/**
 * 
 * <p>Title:日志方法参数 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class LogParamBean
{
    private String serverCode;
    private String taskId;
    private String taskSplitId;
    private Date beginDate;
    private Date endDate;
    private String monFlag;
    private String modFlag = "F";//F:不修改，T：修改标识

    public String getServerCode()
    {
        return serverCode;
    }

    public void setServerCode(String serverCode)
    {
        this.serverCode = serverCode;
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

    public Date getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(Date beginDate)
    {
        this.beginDate = beginDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public String getMonFlag()
    {
        return monFlag;
    }

    public void setMonFlag(String monFlag)
    {
        this.monFlag = monFlag;
    }

    public String getModFlag()
    {
        return modFlag;
    }

    public void setModFlag(String modFlag)
    {
        this.modFlag = modFlag;
    }

}
