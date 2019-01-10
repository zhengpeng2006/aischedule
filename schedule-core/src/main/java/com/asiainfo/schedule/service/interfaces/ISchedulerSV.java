package com.asiainfo.schedule.service.interfaces;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.asiainfo.schedule.core.client.tf.config.CfgTf;
import com.asiainfo.schedule.core.client.tf.config.CfgTfMapping;
import com.asiainfo.schedule.core.po.BackupInfoBean;
import com.asiainfo.schedule.core.po.CfgTfBean;
import com.asiainfo.schedule.core.po.CfgTfDtlBean;
import com.asiainfo.schedule.core.po.FaultBean;
import com.asiainfo.schedule.core.po.ServerBean;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskGroupBean;
import com.asiainfo.schedule.core.po.TaskItemJobBean;
import com.asiainfo.schedule.core.po.TaskJobBean;
import com.asiainfo.schedule.core.po.TaskRunView;
import com.asiainfo.schedule.core.po.TaskView;
import com.asiainfo.schedule.core.utils.Constants.ServerType;

public interface ISchedulerSV
{
    
    /**
     * 获取任务组树
     * 
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public Element getTaskGroupTree(Boolean isLog)
        throws Exception, RemoteException;
    
    /**
     * 根据任务组编码获取任务
     * 
     * @param groupCode
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public TaskGroupBean[] getAllTaskGroup()
        throws Exception, RemoteException;
    
    /**
     * 获取所有状态正常的任务配置
     * 
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public TaskBean[] getAllValidTasks()
        throws Exception, RemoteException;
    
    public TaskBean[] getAllTasks(String groupCode, String taskCode, String taskName, String taskType, String taskState)
        throws Exception, RemoteException;
    
    /**
     * 根据任务编码获取配置信息
     * 
     * @param taskCode
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public TaskBean getTaskInfoByTaskCode(String taskCode)
        throws Exception, RemoteException;
    
    /**
     * 根据任务组编码、任务编码、任务名称查询任务信息视图。支持参数(taskCode,taskName)的模糊查询。
     * 
     * @param groupCode 任务组编码
     * @param taskCode 任务编码
     * @param taskName 任务名称
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public TaskView[] getTaskView(String groupCode, String taskCode, String taskName)
        throws Exception, RemoteException;
    
    /**
     * 根据服务进程编码列表查询对应的任务信息视图
     * 
     * @param appCodes
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public TaskView[] getTaskViewByAppCode(List<String> appCodes)
        throws Exception, RemoteException;
    
    /**
     * 根据任务组编码获取任务
     * 
     * @param groupCode
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public TaskBean[] getTasksByGroupCode(String groupCode)
        throws Exception, RemoteException;
    
    /**
     * 根据任务获取拆分的任务项
     * 
     * @param taskCode
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public String[] getTaskItemsByTaskCode(String taskCode)
        throws Exception, RemoteException;
    
    /**
     * 根据任务编码、任务项编码获取对应的服务进程编码
     * 
     * @param taskCode 任务编码
     * @param itemCode 任务项编码
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public String getServerCode(String taskCode, String itemCode)
        throws Exception, RemoteException;
    
    /**
     * 根据任务编码获取所有任务项与进程编码的映射关系
     * 
     * @param taskCode
     * @return key:任务项编码，value：进程服务编码
     * @throws Exception
     * @throws RemoteException
     */
    public Map<String, String> getAllServersByTaskCode(String taskCode)
        throws Exception, RemoteException;
    
    /**
     * 创建任务
     * 
     * @param task
     * @throws Exception
     * @throws RemoteException
     */
    public void createTask(TaskBean task)
        throws Exception, RemoteException;
    
    /**
     * 更新task
     * 
     * @param task
     * @throws Exception
     * @throws RemoteException
     */
    public void updateTask(TaskBean task)
        throws Exception, RemoteException;
    
    public void createTaskParam(String taskCode, String paramKey, String paramValue)
        throws Exception, RemoteException;
    
    public void deleteTaskParam(String taskCode, String paramKey)
        throws Exception, RemoteException;
    
    public Map<String, String> getTaskParam(String taskCode)
        throws Exception, RemoteException;
    
    /**
     * 为指定的任务项分派处理服务进程
     * 
     * @param taskCode
     * @param taskItem
     * @param serverCode
     * @throws Exception
     * @throws RemoteException
     */
    public void assignServer2TaskItem(String taskCode, String taskItem, String serverCode)
        throws Exception, RemoteException;
    
    /**
     * 根据任务编码删除任务
     * 
     * @param taskCode
     * @throws Exception
     * @throws RemoteException
     */
    public void deleteTask(String taskCode)
        throws Exception, RemoteException;
    
    /**
     * 创建任务组
     * 
     * @param task
     * @throws Exception
     * @throws RemoteException
     */
    public void createTaskGroup(TaskGroupBean taskGroup)
        throws Exception, RemoteException;
    
    /**
     * 删除任务组
     * 
     * @param groupCode
     * @throws Exception
     * @throws RemoteException
     */
    public void deleteTaskGroup(String groupCode)
        throws Exception, RemoteException;
    
    /**
     * 停止任务
     * 
     * @param taskCode
     * @return 任务作业流水号
     * @throws Exception
     * @throws RemoteException
     */
    public String stopTask(String taskCode, String opId)
        throws Exception, RemoteException;
    
    /**
     * 停止单个任务项
     * 
     * @param taskCode
     * @param taskItem
     * @param opId
     * @return 任务作业流水号
     * @throws Exception
     * @throws RemoteException
     */
    public String stopTaskItem(String taskCode, String taskItem, String opId)
        throws Exception, RemoteException;
    
    /**
     * 立即执行任务
     * 
     * @param taskCode
     * @param opId
     * @throws Exception
     * @throws RemoteException
     */
    public String startTaskNow(String taskCode, HashMap<String, String> param, String opId)
        throws Exception, RemoteException;
    
    /**
     * @param taskCode
     * @param taskItemId
     * @param param
     * @param opId
     * @throws Exception
     * @throws RemoteException
     */
    public String startTaskItemNow(String taskCode, String taskItemId, HashMap<String, String> param, String opId)
        throws Exception, RemoteException;
    
    /**
     * 
     * 停止应用服务处理任务
     * 
     * @param serverId
     * @throws Exception
     * @throws RemoteException
     */
    public void stopServer(String serverId, String opId)
        throws Exception, RemoteException;
    
    /**
     * 恢复应用服务
     * 
     * @param serverId
     * @param opId
     * @throws Exception
     * @throws RemoteException
     */
    public void resumServer(String serverId, String opId)
        throws Exception, RemoteException;
    
    /**
     * 新增或更新tf配置
     * 
     * @param bean
     * @throws Exception
     * @throws RemoteException
     */
    public void createOrUpdCfgTf(CfgTfBean bean)
        throws Exception, RemoteException;
    
    /**
     * 删除tf配置
     * 
     * @param tfCode
     * @throws Exception
     * @throws RemoteException
     */
    public void deleteCfgTf(String tfCode)
        throws Exception, RemoteException;
    
    /**
     * 创建新增tf配置的转移详情
     * 
     * @param cfgTfDtlBeans
     * @throws Exception
     * @throws RemoteException
     */
    public void createCfgTfDtl(CfgTfDtlBean[] cfgTfDtlBeans)
        throws Exception, RemoteException;
    
    /**
     * 删除tf转移详情
     * 
     * @param tfDtlId
     * @throws Exception
     * @throws RemoteException
     */
    public void deleteCfgTfDtl(String tfCode, String tfDtlId)
        throws Exception, RemoteException;
    
    /**
     * 新增tf对应的字段映射
     * 
     * @param tfCode
     * @param mapping
     * @throws Exception
     * @throws RemoteException
     */
    public void createCfgTfMapping(String tfCode, CfgTfMapping[] mapping)
        throws Exception, RemoteException;
    
    /**
     * 批量删除tf字段映射
     * 
     * @param tfCode
     * @param mapping
     * @throws Exception
     * @throws RemoteException
     */
    public void deleteCfgTfMapping(String taskCode, String tfDtlId, List<String> mappingIds)
        throws Exception, RemoteException;
    
    public CfgTfBean getCfgTfBean(String taskCode)
        throws Exception, RemoteException;
    
    public CfgTfDtlBean[] getCfgTfDtlBeans(String taskCode)
        throws Exception, RemoteException;
    
    public CfgTfMapping[] getCfgTfMappings(String taskCode, String tfDtlId)
        throws Exception, RemoteException;
    
    public CfgTf getCfgTf(String taskCode)
        throws Exception, RemoteException;
    
    public void taskEffect(String taskCode)
        throws Exception, RemoteException;
    
    public void taskHangOn(String taskCode)
            throws Exception, RemoteException;
    
    public ServerBean[] getRegistServersByServerType(ServerType sType)
        throws Exception, RemoteException;
    
    public ServerBean getRegistServerInfo(String serverId, ServerType sType)
        throws Exception, RemoteException;
    
    public ServerBean getScheduleServerByTaskCode(String taskCode)
        throws Exception, RemoteException;
    
    public String getTaskRunSts(String taskCode)
        throws Exception, RemoteException;
    
    public String getTaskServerRunSts(String serverId)
        throws Exception, RemoteException;
    
    public void enableAutoFault()
        throws Exception, RemoteException;
    
    public void diableAutoFault()
        throws Exception, RemoteException;
    
    public String getFaultFlag()
        throws Exception, RemoteException;
    
    /**
     * 故障恢复
     * 
     * @param taskCode
     * @param faultServerId
     * @throws Exception
     * @throws RemoteException
     */
    public void faultRecovery(String faultServerId)
        throws Exception, RemoteException;
    
    public FaultBean[] getAllFaultServer()
        throws Exception, RemoteException;
    
    public void setDeployNodeState(String nodeId, String stateInfo)
        throws Exception, RemoteException;
    
    public String getDeployNodeState(String nodeId)
        throws Exception, RemoteException;
    
    /**
     * 根据条件获取任务运行视图（支持模糊查询，参数传空返回所有）
     * 
     * @param taskGroupCode
     * @param taskCode
     * @param taskName
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public TaskRunView[] getTaskRunView(String taskGroupCode, String taskCode, String taskName)
        throws Exception, RemoteException;
    
    /**
     * 根据条件获取任务运行视图（支持模糊查询，参数传空返回所有）
     * 
     * @param taskGroupCode
     * @param taskCode
     * @param taskName
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public TaskRunView[] getTaskHangOnView(String taskGroupCode, String taskCode, String taskName)
        throws Exception, RemoteException;
    
    /**
     * 根据任务编码，任务流水号获取还未处理完成的任务项流水信息
     * 
     * @param taskCode
     * @param jobId
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public TaskItemJobBean[] getTaskItemJobs(String taskCode, String jobId)
        throws Exception, RemoteException;
    
    /**
     * 根据服务进程编码获取当前正在运行的任务流水号
     * 
     * @param serverId
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public TaskItemJobBean[] getAllTaskItemJobsByServerId(String serverId)
        throws Exception, RemoteException;
    
    /**
     * 获取当前配置的服务进程与任务的全景视图
     * 
     * @return Map key：任务执行进程编码；value：进程编码对应的任务信息（包括静态配置以及运行信息）
     * @throws Exception
     * @throws RemoteException
     */
    public Map<String, List<Map<String, String>>> getServerTaskPanoramicView()
        throws Exception, RemoteException;
    
    /**
     * 根据任务编码获取任务流水号
     * 
     * @param taskCode
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public String getTaskJobId(String taskCode)
        throws Exception, RemoteException;
    
    public TaskJobBean getTaskJobBean(String taskCode, String taskJobiD)
        throws Exception, RemoteException;
    
    /**
     * 创建任务参数描述
     * 
     * @param taskCode
     * @param paramDescMap
     * @throws Exception
     */
    public void createTaskParamDesc(String taskCode, Map<String, String> paramDescMap)
        throws Exception, RemoteException;
    
    /**
     * 获取任务参数描述
     * 
     * @param taskCode
     * @return
     * @throws Exception
     */
    public Map<String, String> getTaskParamDesc(String taskCode)
        throws Exception, RemoteException;
    
    /**
     * 创建备用进程
     * 
     * @param task
     * @throws Exception
     * @throws RemoteException
     */
    public void createBackupConfig(BackupInfoBean backupInfo)
        throws Exception, RemoteException;
    
    /**
     * 删除备用进程
     * 
     * @param task
     * @throws Exception
     * @throws RemoteException
     */
    public void deleteBackupConfig(BackupInfoBean backupInfo)
        throws Exception, RemoteException;
    
    /**
     * 查询备用进程
     * 
     * @param task
     * @throws Exception
     * @throws RemoteException
     */
    public String getBackupConfig(String serverCode)
        throws Exception, RemoteException;
    
    /**
     * 查询备用进程
     * 
     * @param task
     * @throws Exception
     * @throws RemoteException
     */
    public List<String> getBackupConfigByBackupServerCode(String backupServerCode)
        throws Exception, RemoteException;
    
}
