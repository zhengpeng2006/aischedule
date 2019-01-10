package com.asiainfo.schedule.core.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.schedule.core.client.tf.config.CfgTf;
import com.asiainfo.schedule.core.client.tf.config.CfgTfMapping;
import com.asiainfo.schedule.core.po.BackupInfoBean;
import com.asiainfo.schedule.core.po.CfgTfBean;
import com.asiainfo.schedule.core.po.CfgTfDtlBean;
import com.asiainfo.schedule.core.po.FaultBean;
import com.asiainfo.schedule.core.po.ServerBean;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskGroupBean;
import com.asiainfo.schedule.core.po.TaskItemBackupInfo;
import com.asiainfo.schedule.core.po.TaskItemJobBean;
import com.asiainfo.schedule.core.po.TaskJobBean;
import com.asiainfo.schedule.core.po.TaskView;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.Constants.RunSts;
import com.asiainfo.schedule.core.utils.Constants.ServerType;

/**
 * 调度数据管理工厂类
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月29日
 * @modify
 */
public abstract class DataManagerFactory
{
    private static DataManagerFactory dmFactory = null;
    
    private static ScheduleConfig scheduleConfig;
    
    private static Boolean INIT = Boolean.FALSE;
    
    public ScheduleConfig getScheduleConfig()
    {
        return scheduleConfig;
    }
    
    public static DataManagerFactory getDataManager()
        throws Exception
    {
        if (!INIT.booleanValue())
        {
            synchronized (INIT)
            {
                if (!INIT.booleanValue())
                {
                    // 创建数据管理工厂并初始化
                    scheduleConfig = new ScheduleConfig();
                    scheduleConfig.parse();
                    String dsType = scheduleConfig.getDsType();
                    if (CommonUtils.isBlank(dsType) || dsType.equalsIgnoreCase("zookeeper"))
                    {
                        dmFactory = new ZKDataManagerFactory();
                    }
                    else if (dsType.equalsIgnoreCase("database"))
                    {
                        // dmFactory = new DBDataManagerFactory();
                    }
                    else
                    {
                        throw new Exception("不支持的数据源类型！" + dsType);
                    }
                    dmFactory.initial();
                    INIT = Boolean.TRUE;
                }
            }
        }
        return dmFactory;
    }
    
    protected void initial()
        throws Exception
    {
    }
    
    public abstract String serverRegist(String serverId, ServerType type)
        throws Exception;
    
    public abstract void serverUnRegist(String serverId, ServerType type)
        throws Exception;
    
    // public abstract void heartbeat(String serverId, ServerType type, String
    // msg) throws Exception;
    
    public abstract void heartbeat(ServerBean server)
        throws Exception;
    
    public abstract ServerBean getServerRegistry(String serverId, ServerType type)
        throws Exception;
    
    public abstract List<String> getAllServerIdByType(ServerType type)
        throws Exception;
    
    public abstract List<ServerBean> getAllServerRegistry(ServerType type)
        throws Exception;
    
    public abstract List<String> getAllTaskCodes()
        throws Exception;
    
    public abstract List<TaskBean> getAllValidTasks()
        throws Exception;
    
    public abstract TaskBean getTaskInfoByTaskCode(String taskCode)
        throws Exception;
    
    public abstract String getCurScheduleServer(String taskCode)
        throws Exception;
    
    public abstract void setCurScheduleServer(String taskCode, String serverId)
        throws Exception;
    
    public abstract String getReqScheduleServer(String taskCode)
        throws Exception;
    
    public abstract void setReqScheduleServer(String taskCode, String serverId)
        throws Exception;
    
    /**
     * 根据当前管理服务标识获取所有可管理的任务列表
     * 
     * @param serverId
     * @return
     * @throws Exception
     */
    public abstract List<String> getAllManageTasksByCurServer(String serverId)
        throws Exception;
    
    /**
     * 创建任务组
     * 
     * @param group
     * @throws Exception
     */
    public abstract void createTaskGroup(TaskGroupBean group)
        throws Exception;
    
    /**
     * 删除任务组
     * 
     * @param groupCode
     * @throws Exception
     */
    public abstract void deleteTaskGroup(String groupCode)
        throws Exception;
    
    /**
     * 创建调度任务
     * 
     * @param schedTask
     * @throws Exception
     */
    public abstract void createTask(TaskBean schedTask)
        throws Exception;
    
    /**
     * 更新调度任务
     * 
     * @param schedTask
     * @throws Exception
     */
    public abstract void updateTask(TaskBean schedTask)
        throws Exception;
    
    /**
     * 获取任务对应的所有任务项
     * 
     * @param taskCode
     * @return
     * @throws Exception
     */
    public abstract List<String> getAllTaskItem(String taskCode)
        throws Exception;
    
    /**
     * 为指定的任务项分派任务处理服务标识
     * 
     * @param taskCode
     * @param item
     * @param server
     * @throws Exception
     */
    public abstract void assignServer2TaskItem(String taskCode, String item, String serverId)
        throws Exception;
    
    public abstract List<String> getTaskItemsByServerId(String taskCode, String serverId)
        throws Exception;
    
    public abstract TaskJobBean createTaskJob(String taskCode, HashMap<String, String> param, String opId)
        throws Exception;
    
    public abstract boolean deleteTaskJob(String taskCode, String jobId)
        throws Exception;
    
    public abstract void deleteTaskItemJobFromServer(String serverId, String itemJobId)
        throws Exception;
    
    public abstract void deleteTaskItemJobFromTask(String taskCode, String taskJobId, String itemJobId)
        throws Exception;
    
    public abstract String getTaskRunSts(String taskCode)
        throws Exception;
    
    public abstract void setTaskRunSts(String taskCode, String sts)
        throws Exception;
    
    public abstract String getTaskJobId(String taskCode)
        throws Exception;
    
    public abstract TaskJobBean getTaskJobByJobId(String taskCode, String jobId)
        throws Exception;
    
    /**
     * 任务下的任务项是否还存在可处理的作业流水
     * 
     * @param taskCode
     * @param jobId
     * @return
     * @throws Exception
     */
    public abstract List<String> getAllTaskItemJobId(String taskCode, String jobId)
        throws Exception;
    
    public abstract List<TaskItemJobBean> getAllTaskItemJobBean(String taskCode, String jobId)
        throws Exception;
    
    /**
     * 根据任务编码获取所有任务处理的服务器编码
     * 
     * @param taskCode
     * @return
     * @throws Exception
     */
    public abstract List<String> getAllServersByTaskCode(String taskCode)
        throws Exception;
    
    public abstract String getTaskItemServerId(String taskCode, String itemCode)
        throws Exception;
    
    /**
     * 获取已定义的所有任务组
     * 
     * @return
     * @throws Exception
     */
    public abstract TaskGroupBean[] getTaskGroup()
        throws Exception;
    
    /**
     * 获取指定任务组下的所有任务
     * 
     * @param groupCode
     * @return
     * @throws Exception
     */
    public abstract TaskBean[] getTasksByGroupCode(String groupCode)
        throws Exception;
    
    /**
     * 对指定的任务处理服务器进行故障恢复处理
     * 
     * @param faultServerId 故障处理进程
     * @throws Exception
     */
    public abstract void faultRecovery(String faultServerId)
        throws Exception;
    
    /**
     * 根据任务处理服务器标识获取可处理的任务列表
     * 
     * @param serverId
     * @return
     * @throws Exception
     */
    public abstract List<TaskItemJobBean> getAllTaskJobsByServerId(String serverId)
        throws Exception;
    
    public abstract void setTaskServerRunSts(String serverId, RunSts sts)
        throws Exception;
    
    public abstract String getTaskServerRunSts(String serverId)
        throws Exception;
    
    public abstract void deleteTask(String taskCode)
        throws Exception;
    
    public abstract String getTaskItemCurProcessorId(String taskCode, String item)
        throws Exception;
    
    public abstract void applyTaskItem(String taskCode, String item, String processorId)
        throws Exception;
    
    public abstract void releaseTaskItem(String taskCode, String item, String pid)
        throws Exception;
    
    public abstract void finishTaskItemJob(String serverId, String taskCode, String taskItemId, String taskJobId,
        String itemJobId)
        throws Exception;
    
    public abstract Map<String, String> getTaskParam(String taskCode)
        throws Exception;
    
    public abstract String getFaultProcessFlag()
        throws Exception;
    
    public abstract void setFaultProcessFlag(String value)
        throws Exception;
    
    public abstract TaskView[] getTaskViewByAppCode(List<String> appCodes)
        throws Exception;
    
    public abstract TaskView[] getTaskView(String groupCode, String taskCode, String taskName)
        throws Exception;
    
    /**
     * 模糊查询符合条件的任务配置（参数传入为空则认为条件不生效）
     * 
     * @param groupCode 任务组编码
     * @param qryTaskCode 任务编码
     * @param qryTaskName 任务名称
     * @param taskType 任务类型
     * @param state 配置状态
     * @return
     * @throws Exception
     */
    public abstract List<TaskBean> getTaskBeanFuzzy(String groupCode, String qryTaskCode, String qryTaskName,
        String taskType, String state)
        throws Exception;
    
    /**
     * 根据任务编码获取tf配置信息
     * 
     * @param taskCode
     * @return
     * @throws Exception
     */
    public abstract CfgTf getCfgTfByCode(String taskCode)
        throws Exception;
    
    public abstract void createOrUpdCfgTf(CfgTfBean tfBean)
        throws Exception;
    
    public abstract void deleteCfgTf(String taskCode)
        throws Exception;
    
    public abstract void createCfgTfDtl(CfgTfDtlBean[] tfDtls)
        throws Exception;
    
    public abstract void createCfgTfMapping(String cfgTfCode, CfgTfMapping[] mappings)
        throws Exception;
    
    public abstract void createTaskParam(String taskCode, String paramKey, String paramValue)
        throws Exception;
    
    public abstract void createTaskParamDesc(String taskCode, Map<String, String> paramDescMap)
        throws Exception;
    
    public abstract Map<String, String> getTaskParamDesc(String taskCode)
        throws Exception;
    
    public abstract void deleteTaskParam(String taskCode, String paramKey)
        throws Exception;
    
    public abstract void deleteCfgTfDtl(String taskCode, String tfDtlId)
        throws Exception;
    
    public abstract void deleteCfgTfMapping(String taskCode, String tfDtlId, List<String> mappingIds)
        throws Exception;
    
    public abstract CfgTfBean getCfgTfBean(String taskCode)
        throws Exception;
    
    public abstract CfgTfDtlBean[] getCfgTfDtlBeans(String taskCode)
        throws Exception;
    
    public abstract CfgTfMapping[] getCfgTfMappings(String taskCode, String tfDtlId)
        throws Exception;
    
    public abstract void taskEffect(String taskCode)
        throws Exception;
    
    /**
     * 任务改为挂起状态，挂起状态：暂停调度
     * 
     * @param taskCode
     * @throws Exception
     */
    public abstract void taskHangOn(String taskCode)
            throws Exception;
    
    public abstract void setDeployNodeState(String nodeId, String stateInfo)
        throws Exception;
    
    public abstract String getDeployNodeState(String nodeId)
        throws Exception;
    
    public abstract String getStaticData(String code)
        throws Exception;
    
    public abstract void setStaticData(String code, String value)
        throws Exception;
    
    /**
     * 任务执行过程中，转移任务流水到新指派的处理进程
     * 
     * @param job 重新分配前的任务项作业流水
     * @param serverId 新指派的serverid
     * @return 新生成的任务项流水
     * @throws Exception
     */
    public abstract TaskItemJobBean transformTaskItemJob(TaskItemJobBean job, String serverId)
        throws Exception;
    
    /**
     * 获取所有任务执行的服务进程通道
     * 
     * @return
     * @throws Exception
     */
    public abstract List<String> getAllServerChannles()
        throws Exception;
    
    /**
     * 根据服务进程编码获取故障处理信息
     * 
     * @param serverId
     * @return
     * @throws Exception
     */
    public abstract FaultBean getFaultProcessBean(String serverId)
        throws Exception;
    
    /**
     * 设置服务故障处理信息
     * 
     * @param faultBean
     * @throws Exception
     */
    public abstract void setFaultProcessBean(FaultBean faultBean)
        throws Exception;
    
    /**
     * 服务心跳正常，删除服务故障处理信息
     * 
     * @param faultBean
     * @throws Exception
     */
    public abstract void deleteFaultProcessBean(String serverId)
        throws Exception;
    
    /**
     * 获取所有故障列表
     * 
     * @return
     * @throws Exception
     */
    public abstract FaultBean[] getAllFault()
        throws Exception;
    
    /**
     * 根据serverid获取处理的任务项
     * 
     * @param serverId
     * @return
     * @throws Exception
     */
    public abstract Map<String, List<String>> getAllTaskItemsByServerId(String serverId)
        throws Exception;
    
    /**
     * 单个任务拆分项启动，如果任务作业已经创建则合并。
     * 
     * @param taskCode
     * @param taskItemId
     * @param param
     * @param opId
     * @return
     * @throws Exception
     */
    public abstract String startTaskItemNow(String taskCode, String taskItemId, HashMap<String, String> param,
        String opId)
        throws Exception;
    
    public abstract void stopTaskItemJob(TaskItemJobBean itemJobBean)
        throws Exception;
    
    public abstract String getBackupProcessor(String serverName)
        throws Exception;
    
    public abstract List<String> getMasterProcessor(String backupServerName)
        throws Exception;
    
    public abstract List<String> getAllBackupProcessor()
        throws Exception;
    
    /**
     * 创建备用进程
     * 
     * @param backupInfo
     * @throws Exception
     */
    public abstract void createBackupConfig(BackupInfoBean backupInfo)
        throws Exception;
    
    /**
     * 删除备用进程
     * 
     * @param backupInfo
     * @throws Exception
     */
    public abstract void deleteBackupConfig(BackupInfoBean backupInfo)
        throws Exception;
    
    public abstract void saveBackupRunningTaskItem(String serverName, List<TaskItemBackupInfo> backupInfoList)
        throws Exception;
    
    public abstract void deleteBackupRunningTaskItem(String serverName)
        throws Exception;
    
    public abstract List<TaskItemBackupInfo> qryBackupRunningTaskItem(String serverName)
        throws Exception;
    
    public abstract List<String> qryHandledBackupRunningServer()
        throws Exception;
    
}
