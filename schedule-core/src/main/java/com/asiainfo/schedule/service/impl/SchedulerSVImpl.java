package com.asiainfo.schedule.service.impl;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.schedule.core.client.tf.config.CfgTf;
import com.asiainfo.schedule.core.client.tf.config.CfgTfMapping;
import com.asiainfo.schedule.core.data.DataManagerFactory;
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
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.Constants;
import com.asiainfo.schedule.core.utils.Constants.KeyCodes;
import com.asiainfo.schedule.core.utils.Constants.RunSts;
import com.asiainfo.schedule.core.utils.Constants.ServerType;
import com.asiainfo.schedule.core.utils.CronExpression;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.core.utils.SchedulerLogger;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

public class SchedulerSVImpl implements ISchedulerSV
{
    private static final Logger logger = LoggerFactory.getLogger(SchedulerSVImpl.class);
    
    @Override
    public Element getTaskGroupTree(Boolean isLog)
        throws Exception, RemoteException
    {
        TaskGroupBean[] groups = this.getAllTaskGroup();
        if (groups == null || groups.length == 0)
        {
            return null;
        }
        List<String> taskCodes = DataManagerFactory.getDataManager().getAllTaskCodes();
        if (taskCodes == null || taskCodes.size() == 0)
        {
            return null;
        }
        List<TaskBean> tasks = new ArrayList<TaskBean>(taskCodes.size());
        for (String taskCode : taskCodes)
        {
            TaskBean bean = DataManagerFactory.getDataManager().getTaskInfoByTaskCode(taskCode);
            if (bean != null)
            {
                if (isLog == null)
                {
                    tasks.add(bean);
                }
                else if (isLog.booleanValue() == bean.isLog())
                {
                    tasks.add(bean);
                }
            }
        }
        Element root = DocumentHelper.createElement("root");
        for (TaskGroupBean group : groups)
        {
            Element e = DocumentHelper.createElement("group");
            e.addAttribute("code", group.getGroupCode());
            e.addAttribute("name", group.getGroupName());
            e.addAttribute("desc", group.getGroupDesc());
            for (TaskBean t : tasks)
            {
                if (!group.getGroupCode().equals(t.getTaskGroupCode()))
                {
                    continue;
                }
                Element e1 = DocumentHelper.createElement("task");
                e1.addAttribute("code", t.getTaskCode());
                e1.addAttribute("name", t.getTaskName());
                e1.addAttribute("desc", t.getTaskDesc());
                
                String[] items = this.getTaskItemsByTaskCode(t.getTaskCode());
                if (items == null)
                {
                    continue;
                }
                for (String s : items)
                {
                    Element e2 = DocumentHelper.createElement("item");
                    e2.addAttribute("code", s);
                    e2.addAttribute("name", s);
                    e1.add(e2);
                }
                
                e.add(e1);
            }
            
            root.add(e);
        }
        return root;
    }
    
    @Override
    public TaskGroupBean[] getAllTaskGroup()
        throws Exception, RemoteException
    {
        
        return DataManagerFactory.getDataManager().getTaskGroup();
    }
    
    @Override
    public TaskBean[] getAllValidTasks()
        throws Exception, RemoteException
    {
        List<TaskBean> r = DataManagerFactory.getDataManager().getAllValidTasks();
        if (r == null)
        {
            return null;
        }
        return r.toArray(new TaskBean[0]);
    }
    
    @Override
    public TaskBean[] getAllTasks(String groupCode, String taskCode, String taskName, String taskType, String taskState)
        throws Exception, RemoteException
    {
        List<TaskBean> r =
            DataManagerFactory.getDataManager().getTaskBeanFuzzy(groupCode, taskCode, taskName, taskType, taskState);
        if (r == null)
        {
            return null;
        }
        return r.toArray(new TaskBean[0]);
    }
    
    @Override
    public TaskView[] getTaskViewByAppCode(List<String> appCodes)
        throws Exception, RemoteException
    {
        if (appCodes == null || appCodes.size() == 0)
        {
            logger.error("传入的AppCode列表为空！");
            return null;
        }
        
        return DataManagerFactory.getDataManager().getTaskViewByAppCode(appCodes);
    }
    
    @Override
    public TaskBean getTaskInfoByTaskCode(String taskCode)
        throws Exception, RemoteException
    {
        if (CommonUtils.isBlank(taskCode))
        {
            throw new Exception("传入的查询条件不能都为空！");
        }
        return DataManagerFactory.getDataManager().getTaskInfoByTaskCode(taskCode);
    }
    
    @Override
    public TaskView[] getTaskView(String groupCode, String taskCode, String taskName)
        throws Exception, RemoteException
    {
        if (CommonUtils.isBlank(groupCode) && CommonUtils.isBlank(taskCode) && CommonUtils.isBlank(taskName))
        {
            throw new Exception("传入的查询条件不能都为空！");
        }
        return DataManagerFactory.getDataManager().getTaskView(groupCode, taskCode, taskName);
    }
    
    @Override
    public String[] getTaskItemsByTaskCode(String taskCode)
        throws Exception, RemoteException
    {
        List<String> l = DataManagerFactory.getDataManager().getAllTaskItem(taskCode);
        if (l == null || l.size() == 0)
        {
            return null;
        }
        return l.toArray(new String[0]);
    }
    
    @Override
    public TaskBean[] getTasksByGroupCode(String groupCode)
        throws Exception, RemoteException
    {
        return DataManagerFactory.getDataManager().getTasksByGroupCode(groupCode);
    }
    
    @Override
    public String getServerCode(String taskCode, String itemCode)
        throws Exception, RemoteException
    {
        
        return DataManagerFactory.getDataManager().getTaskItemServerId(taskCode, itemCode);
    }
    
    @Override
    public Map<String, String> getAllServersByTaskCode(String taskCode)
        throws Exception, RemoteException
    {
        
        TaskBean task = this.getTaskInfoByTaskCode(taskCode);
        if (task == null)
        {
            throw new Exception("任务已经被删除！" + taskCode);
        }
        Map<String, String> map = new HashMap<String, String>();
        String[] items = getTaskItemsByTaskCode(taskCode);
        if (items == null || items.length == 0)
            return map;
        for (String item : items)
        {
            if (task.isSplitRegion() && item.indexOf("^") < 0 && task.getItems().length == 1)
            {
                // 任务仅根据地市拆分，没有子分片。serverCode后增加特殊标识，用于前台监控数据展现时特殊处理。
                map.put(item, getServerCode(taskCode, item) + Constants.ONLY_SPLIT_REGION_FLAG);
            }
            else
            {
                map.put(item, getServerCode(taskCode, item));
            }
        }
        return map;
    }
    
    @Override
    public void assignServer2TaskItem(String taskCode, String taskItem, String serverCode)
        throws Exception, RemoteException
    {
        
        DataManagerFactory.getDataManager().assignServer2TaskItem(taskCode, taskItem, serverCode);
    }
    
    @Override
    public void createTask(TaskBean task)
        throws Exception, RemoteException
    {
        
        DataManagerFactory.getDataManager().createTask(task);
    }
    
    @Override
    public void updateTask(TaskBean task)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().updateTask(task);
    }
    
    @Override
    public void createTaskParam(String taskCode, String paramKey, String paramValue)
        throws Exception, RemoteException
    {
        
        DataManagerFactory.getDataManager().createTaskParam(taskCode, paramKey, paramValue);
    }
    
    @Override
    public void deleteTaskParam(String taskCode, String paramKey)
        throws Exception, RemoteException
    {
        
        DataManagerFactory.getDataManager().deleteTaskParam(taskCode, paramKey);
    }
    
    @Override
    public Map<String, String> getTaskParam(String taskCode)
        throws Exception, RemoteException
    {
        return DataManagerFactory.getDataManager().getTaskParam(taskCode);
    }
    
    @Override
    public void createTaskGroup(TaskGroupBean taskGroup)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().createTaskGroup(taskGroup);
        
    }
    
    @Override
    public void deleteTaskGroup(String groupCode)
        throws Exception, RemoteException
    {
        TaskBean[] tasks = this.getTasksByGroupCode(groupCode);
        if (tasks != null && tasks.length > 0)
        {
            throw new Exception("任务组下存在配置任务，无法删除！");
        }
        DataManagerFactory.getDataManager().deleteTaskGroup(groupCode);
        
    }
    
    @Override
    public void deleteTask(String taskCode)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().deleteTask(taskCode);
    }
    
    @Override
    public void stopServer(String serverId, String opId)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().setTaskServerRunSts(serverId, RunSts.stop);
    }
    
    @Override
    public void resumServer(String serverId, String opId)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().setTaskServerRunSts(serverId, RunSts.start);
    }
    
    @Override
    public String stopTask(String taskCode, String opId)
        throws Exception, RemoteException
    {
        String state = DataManagerFactory.getDataManager().getTaskRunSts(taskCode);
        if (RunSts.stop.name().equals(state))
        {
            logger.error("任务处理状态已经终止!");
            return "";
        }
        
        String jobId = DataManagerFactory.getDataManager().getTaskJobId(taskCode);
        
        // 停止任务
        DataManagerFactory.getDataManager().setTaskRunSts(taskCode, RunSts.stop.name());
        logger.info("任务【" + taskCode + "】控制台操作任务停止." + opId);
        // 记录日志
        SchedulerLogger.addTaskLogDtl(taskCode, "-1", jobId, "outside", "", opId, "外部操作任务停止", "");
        return jobId;
    }
    
    @Override
    public String stopTaskItem(String taskCode, String taskItem, String opId)
        throws Exception, RemoteException
    {
        String state = DataManagerFactory.getDataManager().getTaskRunSts(taskCode);
        if (RunSts.stop.name().equals(state))
        {
            logger.error("任务处理状态已经终止!");
            return "";
        }
        logger.info("任务【" + taskCode + "】控制台操作任务停止任务项." + taskItem);
        String jobId = DataManagerFactory.getDataManager().getTaskJobId(taskCode);
        if (CommonUtils.isBlank(jobId))
        {
            
            List<TaskItemJobBean> items = DataManagerFactory.getDataManager().getAllTaskItemJobBean(taskCode, jobId);
            if (items == null || items.size() == 0)
            {
                return "";
            }
            
            for (TaskItemJobBean item : items)
            {
                if (item.getTaskItemId().equals(taskItem))
                {
                    DataManagerFactory.getDataManager().stopTaskItemJob(item);
                    // 记录日志
                    SchedulerLogger.addTaskLogDtl(taskCode,
                        "-1",
                        jobId,
                        "outside",
                        taskItem,
                        opId,
                        "外部操作任务停止。" + item.getItemJobId(),
                        "");
                }
            }
        }
        
        return jobId;
    }
    
    @Override
    public String startTaskNow(String taskCode, HashMap<String, String> param, String opId)
        throws Exception, RemoteException
    {
        TaskBean task = this.getTaskInfoByTaskCode(taskCode);
        TaskJobBean job = null;
        String exMsg = "";
        String state = "C";
        StringBuilder opInfo = new StringBuilder("外部启动任务");
        
        try
        {
            if (task == null)
            {
                throw new Exception("任务配置已被删除，无法创建作业！！！" + taskCode);
            }
            job = DataManagerFactory.getDataManager().createTaskJob(taskCode, param, opId);
            if (job == null)
            {
                throw new Exception("创建作业流水失败，启动任务失败！");
            }
            opInfo.append("成功");
        }
        catch (Exception ex)
        {
            opInfo.append("异常");
            state = "E";
            job = null;
            logger.error("开始任务出现异常！！！", ex);
            exMsg = CommonUtils.getExceptionMesssage(ex, 1000);
        }
        
        // add log
        String jobId = "";
        String createTime = "";
        if (job != null)
        {
            jobId = job.getJobId();
            createTime = job.getCreateTime();
        }
        else
        {
            long cur = System.currentTimeMillis();
            jobId = "ERROR$" + cur;
            createTime = DateUtils.formatYYYYMMddHHmmss(new Timestamp(cur));
        }
        SchedulerLogger.createTaskLog(taskCode,
            task == null ? "" : task.getTaskName(),
            task == null ? "-1" : task.getVersion() + "",
            jobId,
            createTime,
            state);
        SchedulerLogger.addTaskLogDtl(taskCode,
            task == null ? "-1" : task.getVersion() + "",
            jobId,
            "outside",
            "",
            opId,
            opInfo.toString(),
            exMsg);
        
		if (StringUtils.isNotBlank(exMsg)) {
			SchedulerLogger.addTaskErrorLogDtl(taskCode, task == null ? "-1" : task.getVersion() + "", jobId, "outside", "", opId, opInfo.toString(),
					Constants.TaskErrLevel.LEVEL_10, exMsg);
		}
        
        return jobId;
    }
    
    @Override
    public String startTaskItemNow(String taskCode, String taskItemId, HashMap<String, String> param, String opId)
        throws Exception, RemoteException
    {
        
        return DataManagerFactory.getDataManager().startTaskItemNow(taskCode, taskItemId, param, opId);
        
    }
    
    @Override
    public void createBackupConfig(BackupInfoBean backupInfo)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().createBackupConfig(backupInfo);
        
    }
    
    @Override
    public void deleteBackupConfig(BackupInfoBean backupInfo)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().deleteBackupConfig(backupInfo);
        
    }
    
    @Override
    public String getBackupConfig(String serverCode)
        throws Exception, RemoteException
    {
        
        return DataManagerFactory.getDataManager().getBackupProcessor(serverCode);
        
    }
    
    @Override
    public void createCfgTfDtl(CfgTfDtlBean[] cfgTfDtlBeans)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().createCfgTfDtl(cfgTfDtlBeans);
    }
    
    @Override
    public void createCfgTfMapping(String tfCode, CfgTfMapping[] mapping)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().createCfgTfMapping(tfCode, mapping);
        
    }
    
    @Override
    public void createOrUpdCfgTf(CfgTfBean bean)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().createOrUpdCfgTf(bean);
    }
    
    @Override
    public void deleteCfgTf(String taskCode)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().deleteCfgTf(taskCode);
    }
    
    @Override
    public void deleteCfgTfDtl(String taskCode, String tfDtlId)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().deleteCfgTfDtl(taskCode, tfDtlId);
    }
    
    @Override
    public void deleteCfgTfMapping(String taskCode, String tfDtlId, List<String> mappingIds)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().deleteCfgTfMapping(taskCode, tfDtlId, mappingIds);
        
    }
    
    @Override
    public CfgTfBean getCfgTfBean(String taskCode)
        throws Exception, RemoteException
    {
        return DataManagerFactory.getDataManager().getCfgTfBean(taskCode);
    }
    
    @Override
    public CfgTfDtlBean[] getCfgTfDtlBeans(String taskCode)
        throws Exception, RemoteException
    {
        return DataManagerFactory.getDataManager().getCfgTfDtlBeans(taskCode);
    }
    
    @Override
    public CfgTfMapping[] getCfgTfMappings(String taskCode, String tfDtlId)
        throws Exception, RemoteException
    {
        return DataManagerFactory.getDataManager().getCfgTfMappings(taskCode, tfDtlId);
    }
    
    @Override
    public CfgTf getCfgTf(String taskCode)
        throws Exception, RemoteException
    {
        return DataManagerFactory.getDataManager().getCfgTfByCode(taskCode);
    }
    
    @Override
    public void taskEffect(String taskCode)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().taskEffect(taskCode);
        
    }
    
    @Override
    public ServerBean[] getRegistServersByServerType(ServerType sType)
        throws Exception, RemoteException
    {
        List<ServerBean> servers = DataManagerFactory.getDataManager().getAllServerRegistry(sType);
        if (servers == null || servers.size() == 0)
        {
            return null;
        }
        return servers.toArray(new ServerBean[0]);
    }
    
    @Override
    public ServerBean getRegistServerInfo(String serverId, ServerType sType)
        throws Exception, RemoteException
    {
        
        return DataManagerFactory.getDataManager().getServerRegistry(serverId, sType);
    }
    
    @Override
    public ServerBean getScheduleServerByTaskCode(String taskCode)
        throws Exception, RemoteException
    {
        String managerId = DataManagerFactory.getDataManager().getCurScheduleServer(taskCode);
        if (CommonUtils.isBlank(managerId))
        {
            return null;
        }
        return this.getRegistServerInfo(managerId, ServerType.manager);
    }
    
    @Override
    public String getTaskRunSts(String taskCode)
        throws Exception, RemoteException
    {
        return DataManagerFactory.getDataManager().getTaskRunSts(taskCode);
    }
    
    @Override
    public String getTaskServerRunSts(String serverId)
        throws Exception, RemoteException
    {
        
        return DataManagerFactory.getDataManager().getTaskServerRunSts(serverId);
    }
    
    @Override
    public void diableAutoFault()
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().setFaultProcessFlag("off");
    }
    
    @Override
    public void enableAutoFault()
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().setFaultProcessFlag("on");
    }
    
    @Override
    public String getFaultFlag()
        throws Exception, RemoteException
    {
        
        return DataManagerFactory.getDataManager().getFaultProcessFlag();
    }
    
    @Override
    public void faultRecovery(String faultServerId)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().faultRecovery(faultServerId);
        
    }
    
    @Override
    public FaultBean[] getAllFaultServer()
        throws Exception, RemoteException
    {
        
        return DataManagerFactory.getDataManager().getAllFault();
    }
    
    @Override
    public String getDeployNodeState(String nodeId)
        throws Exception, RemoteException
    {
        return DataManagerFactory.getDataManager().getDeployNodeState(nodeId);
    }
    
    @Override
    public void setDeployNodeState(String nodeId, String stateInfo)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().setDeployNodeState(nodeId, stateInfo);
    }
    
    @Override
    public TaskRunView[] getTaskRunView(String taskGroupCode, String taskCode, String taskName)
        throws Exception, RemoteException
    {
        return getTaskStateView(taskGroupCode, taskCode, taskName, "U");
    }
    
    @Override
	public TaskRunView[] getTaskHangOnView(String taskGroupCode, String taskCode, String taskName) throws Exception, RemoteException {
    	return getTaskStateView(taskGroupCode, taskCode, taskName, "H");
	}
    
    private TaskRunView[] getTaskStateView(String taskGroupCode, String taskCode, String taskName, String taskState)
            throws Exception, RemoteException
        {
            DataManagerFactory dm = DataManagerFactory.getDataManager();
            List<TaskBean> beans = dm.getTaskBeanFuzzy(taskGroupCode, taskCode, taskName, null, taskState);
            
            if (beans == null || beans.size() == 0)
            {
                return null;
            }
            TaskRunView[] views = new TaskRunView[beans.size()];
            int i = 0;
            for (TaskBean bean : beans)
            {
                views[i] = new TaskRunView();
                views[i].setTaskBean(bean);
                String jobId = dm.getTaskJobId(bean.getTaskCode());
                if (!CommonUtils.isBlank(jobId))
                {
                    views[i].setRunningTaskJob(dm.getTaskJobByJobId(bean.getTaskCode(), jobId));
                }
                String startTime = bean.getStartTime();
                String endTime = bean.getEndTime();
                Date curDate = DateUtils.getCurrentDate();
                
                // 根据云南帐管中心要求修改
                // if (!CommonUtils.isBlank(startTime) && startTime.indexOf("-1") >= 0) {
                if (!CommonUtils.isBlank(startTime))
                {
                    try
                    {
                        CronExpression ce = new CronExpression(CommonUtils.translateCron(startTime));
                        views[i].setNextSchedStartTime(DateUtils.formatYYYYMMddHHmmss(ce.getNextValidTimeAfter(curDate)));
                    }
                    catch (Exception ex)
                    {
                    }
                }
                
                // if (!CommonUtils.isBlank(endTime) && endTime.indexOf("-1") >= 0) {
                if (!CommonUtils.isBlank(endTime))
                {
                    try
                    {
                        CronExpression ce = new CronExpression(CommonUtils.translateCron(endTime));
                        views[i].setNextSchedEndTime(DateUtils.formatYYYYMMddHHmmss(ce.getNextValidTimeAfter(curDate)));
                    }
                    catch (Exception ex)
                    {
                    }
                }
                
                views[i].setCurManagerId(dm.getCurScheduleServer(bean.getTaskCode()));
                
                views[i].setTaskRunSts(dm.getTaskRunSts(bean.getTaskCode()));
                
                i++;
            }
            return views;
        }
    
    @Override
    public TaskItemJobBean[] getTaskItemJobs(String taskCode, String jobId)
        throws Exception, RemoteException
    {
        if (CommonUtils.isBlank(taskCode) || CommonUtils.isBlank(jobId))
        {
            throw new Exception("查询条件不能为空！");
        }
        List<TaskItemJobBean> itemJobs = DataManagerFactory.getDataManager().getAllTaskItemJobBean(taskCode, jobId);
        if (itemJobs != null)
        {
            return itemJobs.toArray(new TaskItemJobBean[0]);
        }
        
        return null;
        
    }
    
    @Override
    public TaskItemJobBean[] getAllTaskItemJobsByServerId(String serverId)
        throws Exception, RemoteException
    {
        List<TaskItemJobBean> l = DataManagerFactory.getDataManager().getAllTaskJobsByServerId(serverId);
        return (l == null ? null : l.toArray(new TaskItemJobBean[0]));
    }
    
    @Override
    public Map<String, List<Map<String, String>>> getServerTaskPanoramicView()
        throws Exception, RemoteException
    {
        Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();
        List<TaskBean> tasks = DataManagerFactory.getDataManager().getAllValidTasks();
        if (tasks == null || tasks.size() == 0)
        {
            return result;
        }
        
        ExecutorService service = Executors.newFixedThreadPool(16);
        List<FutureTask<List<Map<String, String>>>> futureList =
            new ArrayList<FutureTask<List<Map<String, String>>>>(tasks.size());
        
        for (TaskBean task : tasks)
        {
            FutureTask<List<Map<String, String>>> f =
                new FutureTask<List<Map<String, String>>>(new GetServerTaskViewCallable(task));
            service.execute(f);
            futureList.add(f);
        }
        
        for (FutureTask<List<Map<String, String>>> f : futureList)
        {
            List<Map<String, String>> mm = f.get();
            for (Map<String, String> jo : mm)
            {
                String serverId = jo.get(KeyCodes.ServerId.name());
                List<Map<String, String>> l = result.get(serverId);
                if (l == null)
                {
                    l = new ArrayList<Map<String, String>>();
                }
                
                l.add(jo);
                result.put(serverId, l);
            }
        }
        service.shutdown();
        return result;
    }
    
    @Override
    public String getTaskJobId(String taskCode)
        throws Exception, RemoteException
    {
        return DataManagerFactory.getDataManager().getTaskJobId(taskCode);
    }
    
    @Override
    public TaskJobBean getTaskJobBean(String taskCode, String taskJobiD)
        throws Exception, RemoteException
    {
        return DataManagerFactory.getDataManager().getTaskJobByJobId(taskCode, taskJobiD);
    }
    
    @Override
    public void createTaskParamDesc(String taskCode, Map<String, String> paramDescMap)
        throws Exception, RemoteException
    {
        DataManagerFactory.getDataManager().createTaskParamDesc(taskCode, paramDescMap);
    }
    
    @Override
    public Map<String, String> getTaskParamDesc(String taskCode)
        throws Exception, RemoteException
    {
        
        return DataManagerFactory.getDataManager().getTaskParamDesc(taskCode);
    }
    
    public static void main(String[] args)
        throws Exception
    {
        SchedulerSVImpl sv = new SchedulerSVImpl();
        
        // HashMap<String,String> paramDescMap = new HashMap<String,String>();
        // paramDescMap.put("regionId", "地市编码（570-580）");
        // paramDescMap.put("billId", "计费号码");
        // sv.createTaskParamDesc("demo_tf_01", paramDescMap);
        System.out.println(sv.getTaskParamDesc("demo_tf_01"));
        
        // sv.startTaskItemNow("demo_single_01", "0", aaa, "9999");
        // Thread.currentThread().sleep(40000);
    }
    
    @Override
    public List<String> getBackupConfigByBackupServerCode(String backupServerCode)
        throws Exception, RemoteException
    {
        return DataManagerFactory.getDataManager().getMasterProcessor(backupServerCode);
    }

	@Override
	public void taskHangOn(String taskCode) throws Exception, RemoteException {
		DataManagerFactory.getDataManager().taskHangOn(taskCode);
	}

	
    
}
