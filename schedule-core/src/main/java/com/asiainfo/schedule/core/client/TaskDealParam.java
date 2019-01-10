package com.asiainfo.schedule.core.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 任务处理时与业务交互的通用接口对象
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年9月11日
 * @modify
 */
public class TaskDealParam implements java.io.Serializable
{
    private static final long serialVersionUID = -6901825350432491996L;
    
    /**
     * 任务编码
     */
    private String taskCode;
    
    /**
     * 任务类型
     */
    private String taskType;
    
    /**
     * 任务组
     */
    private String taskGroup;
    
    /**
     * 任务拆分项编码
     */
    private String taskItem;
    
    /**
     * 任务拆分项总数量
     */
    private int taskItemCount;
    
    /**
     * 任务是否按区域地市拆分
     */
    private boolean splitRegion;
    
    /**
     * 区域编码
     */
    private String regionCode;
    
    /**
     * 每次获取数据量
     */
    private int eachFetchDataNum;
    
    /**
     * 当次处理的作业流水号
     */
    private String jobId;
    
    /**
     * 任务参数
     */
    private Map<String, String> taskParam = new HashMap<String, String>();
    
    private String executeResult;
    
    /**
     * 埋点参数
     */
    private Map<String, String> traceLogParam = new HashMap<String, String>();
    
    public Map<String, String> getTraceLogParam()
    {
        return traceLogParam;
    }
    
    public void setTraceLogParam(Map<String, String> traceLogParam)
    {
        this.traceLogParam = traceLogParam;
    }
    
    /**
     * 获取任务编码
     * 
     * @return
     */
    public String getTaskCode()
    {
        return taskCode;
    }
    
    void setTaskCode(String taskCode)
    {
        this.taskCode = taskCode;
    }
    
    /**
     * 获取任务类型
     * 
     * @return
     */
    public String getTaskType()
    {
        return taskType;
    }
    
    void setTaskType(String taskType)
    {
        this.taskType = taskType;
    }
    
    /**
     * 获取任务组
     * 
     * @return
     */
    public String getTaskGroup()
    {
        return taskGroup;
    }
    
    void setTaskGroup(String taskGroup)
    {
        this.taskGroup = taskGroup;
    }
    
    /**
     * 获取任务处理项
     * 
     * @return
     */
    public String getTaskItem()
    {
        return taskItem;
    }
    
    void setTaskItem(String taskItem)
    {
        this.taskItem = taskItem;
    }
    
    /**
     * 获取任务拆分项的总数
     * 
     * @return
     */
    public int getTaskItemCount()
    {
        return taskItemCount;
    }
    
    void setTaskItemCount(int taskItemCount)
    {
        this.taskItemCount = taskItemCount;
    }
    
    /**
     * 获取任务每次获取的数据量
     * 
     * @return
     */
    public int getEachFetchDataNum()
    {
        return eachFetchDataNum;
    }
    
    public void setEachFetchDataNum(int eachFetchDataNum)
    {
        this.eachFetchDataNum = eachFetchDataNum;
    }
    
    /**
     * 获取任务处理作业流水号
     * 
     * @return
     */
    public String getJobId()
    {
        return jobId;
    }
    
    public void setJobId(String jobId)
    {
        this.jobId = jobId;
    }
    
    /**
     * 获取任务的所有参数
     * 
     * @return
     */
    public Map<String, String> getTaskParam()
    {
        return taskParam;
    }
    
    /**
     * 根据参数名获取参数值
     * 
     * @param key
     * @return
     */
    public String getTaskParam(String key)
    {
        return taskParam == null ? null : taskParam.get(key);
    }
    
    public void setTaskParam(Map<String, String> taskParam)
    {
        this.taskParam = taskParam;
    }
    
    public void addTaskParam(String key, String value)
    {
        this.taskParam.put(key, value);
    }
    
    public boolean isSplitRegion()
    {
        return splitRegion;
    }
    
    public void setSplitRegion(boolean splitRegion)
    {
        this.splitRegion = splitRegion;
    }
    
    public String getRegionCode()
    {
        return regionCode;
    }
    
    public void setRegionCode(String regionCode)
    {
        this.regionCode = regionCode;
    }
    
    public String getExecuteResult()
    {
        return executeResult;
    }
    
    public void setExecuteResult(String executeResult)
    {
        this.executeResult = executeResult;
    }
    
    @Override
    public String toString()
    {
        
        StringBuffer sb = new StringBuffer("{");
        sb.append("taskCode:").append(this.getTaskCode()).append(",");
        sb.append("taskGroup:").append(this.getTaskGroup()).append(",");
        sb.append("taskType:").append(this.getTaskType()).append(",");
        sb.append("taskItem:").append(this.getTaskItem()).append(",");
        sb.append("taskItemCount:").append(this.getTaskItemCount()).append(",");
        sb.append("splitRegion:").append(this.isSplitRegion()).append(",");
        sb.append("regionCode:").append(this.getRegionCode()).append(",");
        sb.append("eachFetchDataNum:").append(this.getEachFetchDataNum()).append(",");
        sb.append("jobId:").append(this.getJobId()).append(",");
        sb.append("taskParam:{");
        if (taskParam != null && !taskParam.isEmpty())
        {
            int i = 0;
            for (Entry<String, String> e : taskParam.entrySet())
            {
                sb.append(e.getKey()).append(":").append(e.getValue());
                if (++i < taskParam.size())
                {
                    sb.append(",");
                }
            }
        }
        sb.append("}}");
        return sb.toString();
    }
}
