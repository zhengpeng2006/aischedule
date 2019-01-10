package com.asiainfo.schedule.core.center;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.po.ServerBean;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskItemBackupInfo;
import com.asiainfo.schedule.core.utils.Constants;
import com.asiainfo.schedule.core.utils.Constants.ServerType;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.core.utils.JsonUtils;

/**
 * 调度管理主线程
 *
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月11日
 * @modify
 */
public class ScheduleManager extends Thread
{

    private static final Logger logger = LoggerFactory.getLogger(ScheduleManager.class);

    private final DataManagerFactory dmFactory = DataManagerFactory.getDataManager();

    private final ServerType serverType = ServerType.manager;;

    // 调度服务器列表
    private List<ServerBean> serverList = new ArrayList<ServerBean>(5);

    // 启动时注册生成的唯一编码
    private String myid;

    private ITaskScheduler taskScheduler;

    private boolean isLeader = false;

    private volatile boolean running;

    private AtomicLong version = new AtomicLong(0);

    private int taskCount;

    private int manageTaskCount;

    private TaskChecker taskChecker;


    private List<TaskBean> hangOnTaskList = new ArrayList<TaskBean>();

    private static final Comparator<ServerBean> serverComparator = new Comparator<ServerBean>()
    {
        @Override
        public int compare(ServerBean u1, ServerBean u2)
        {
            int result = 0;
            String s1 = u1.getServerId();
            String s2 = u2.getServerId();
            result = s1.substring(s1.lastIndexOf("$") + 1).compareTo(s2.substring(s2.lastIndexOf("$") + 1));

            return result;
        }
    };

    public ScheduleManager()
            throws Exception
    {
        super("schedule-main");
        this.setDaemon(true);
        // 初始化任务调度执行的实现类
        if (this.dmFactory.getScheduleConfig().getScheduleImplementClass() != null)
        {
            Class<?> c = Class.forName(this.dmFactory.getScheduleConfig().getScheduleImplementClass());
            taskScheduler = (ITaskScheduler)c.getConstructor(DataManagerFactory.class).newInstance();
        }
        else
        {
            taskScheduler = new TaskSchedulerForQuartz();
        }
        taskChecker = new TaskChecker(this.dmFactory.getScheduleConfig().getScheduleCheckThreadPoolSize());
        running = true;
    }

    public void shutdown()
    {
        try
        {
            if (serverList != null)
            {
                serverList.clear();
            }
            if (taskScheduler != null)
            {
                taskScheduler.shutdown();
            }
            dmFactory.serverUnRegist(myid, this.serverType);
        }
        catch (Throwable t)
        {
            logger.error("停止服务出现异常！", t);
        }
        finally
        {
            running = false;
        }
    }

    @Override
    public void run()
    {

        this.setPriority(Thread.MAX_PRIORITY);

        // 主循环
        int heartbeat = this.dmFactory.getScheduleConfig().getHeartbeatInterval();
        while (running)
        {
            version.incrementAndGet();
            if (logger.isInfoEnabled())
            {
                logger.info("refresh start[" + version.get() + "]");
            }
            long start = System.currentTimeMillis();
            this.refresh();
            if (logger.isInfoEnabled())
            {
                logger.info("refresh finish[" + version.get() + "]" + ":cost(ms)"
                        + (System.currentTimeMillis() - start) + "," + stats());
            }
            // 错误接管监测方法
            this.serverCheck();
            try
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("sleep:" + heartbeat);
                }
                sleep(heartbeat);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                running = false;
            }
        }

    }

    public String stats()
    {
        StringBuffer sb = new StringBuffer("");
        sb.append("version:").append(version.get()).append(",");
        sb.append("isLeader:").append(isLeader).append(",");
        sb.append("serverCount:").append(serverList.size()).append(",");
        sb.append("taskCount:").append(taskCount).append(",");
        sb.append("manageTaskCount:").append(this.manageTaskCount);
        return sb.toString();
    }

    private void refresh()
    {

        try
        {
            ServerBean server = dmFactory.getServerRegistry(myid, this.serverType);
            if (server == null)
            {
                myid = dmFactory.serverRegist(null, this.serverType);
                taskScheduler.setManager(myid);
                logger.info("调度中心服务器未注册或已经失效，注册成功。" + myid);
            }
            else
            {
                server.setHeartbeatTime(DateUtils.getCurrentDate());
                server.setHeartbeatInfo("version:" + version.get() + ",isLeader:" + this.isLeader);
                // 先发送心跳信息
                dmFactory.heartbeat(server);
            }

            if (isLeader)
            {
                // 清除已过期的调度管理器
                clearExpireServer();
                // 任务执行服务进程状态检查，对异常进程故障处理
                TaskExecutorChecker.INSTANCE.check();

            }
            // 获取当前所有有效的调度管理服务器
            serverList = dmFactory.getAllServerRegistry(serverType);
            // 如果是 leader 对已定义的任务重新分配调度服务器
            redistribution();

            // 刷新任务
            refreshTask();

        }
        catch (Throwable t)
        {
            logger.error("refresh exception", t);
            serverList.clear();
            taskCount = 0;
            manageTaskCount = 0;
            if (taskScheduler != null)
            {
                taskScheduler.removeAllTask();
            }
        }

    }

    private void refreshTask()
            throws Exception
    {

        // 在做任务调度前需要先获取服务当前管理的任务列表，释放自己占有但已被别人申请的任务.返回清理后的任务列表
        List<TaskBean> tasks = this.releaseTask();
        if (tasks != null)
        {
            taskChecker.check(tasks);
        }

        if (!hangOnTaskList.isEmpty()) {
            taskChecker.check(hangOnTaskList);
        }

        manageTaskCount = (tasks == null ? 0 : tasks.size());

        // 任务执行时间调度
        if (manageTaskCount == 0)
        {
            logger.warn("调度器没有可管理的任务，清除任务列表。");
            taskScheduler.removeAllTask();
        }
        else
        {
            taskScheduler.refreshScheduleTask(tasks);
        }
        if (logger.isInfoEnabled())
        {
            logger.info("调度执行器状态信息:" + JsonUtils.toJson(taskScheduler.stats()));
        }

    }

    protected int clearExpireServer()
            throws Exception
    {

        int result = 0;

        for (ServerBean server : dmFactory.getAllServerRegistry(this.serverType))
        {
            try
            {

                if ((System.currentTimeMillis() - server.getHeartbeatTime().getTime()) > dmFactory.getScheduleConfig()
                        .getDeadInterval())
                {

                    dmFactory.serverUnRegist(server.getServerId(), this.serverType);
                    result++;
                }
            }
            catch (Exception e)
            {
                // 多个server有可能同时清理
                result++;
            }
        }
        return result;

    }

    private List<TaskBean> releaseTask()
            throws Exception
    {
        List<TaskBean> tasks = new ArrayList<TaskBean>();
        int release = 0;

        hangOnTaskList.clear();
        for (String taskCode : dmFactory.getAllManageTasksByCurServer(myid))
        {
            String reqServer = this.dmFactory.getReqScheduleServer(taskCode);
            if (reqServer != null)
            {
                logger.debug("任务：" + taskCode + " 被重新分配.当前serverId:" + myid + ",现serverId:" + reqServer);
                // 先终止调度器中的任务
                taskScheduler.removeTask(taskCode);
                try
                {
                    // relase
                    dmFactory.setCurScheduleServer(taskCode, reqServer);
                    dmFactory.setReqScheduleServer(taskCode, null);
                    release++;
                }
                catch (Exception ex)
                {
                    logger.error("忽略释放任务出现异常，下次刷新再处理!" + taskCode, ex);
                }
                continue;
            }

            TaskBean schedTask = dmFactory.getTaskInfoByTaskCode(taskCode);
            if (schedTask == null || !"U".equals(schedTask.getState()))
            {
                if ("H".equals(schedTask.getState())) {
                    hangOnTaskList.add(schedTask);
                }
                logger.warn("当前任务服务端配置已经被删除或者状态已失效，从当前调度器中移除！" + taskCode);
                taskScheduler.removeTask(taskCode);
                continue;
            }
            tasks.add(schedTask);
        }

        logger.debug("当前服务器管理的任务列表大小为:" + tasks.size() + ",释放任务数:" + release);
        return tasks;

    }

    protected boolean isLeader()
    {

        long no = Long.parseLong(myid.substring(myid.lastIndexOf("$") + 1));
        for (ServerBean server : serverList)
        {
            if (no > Long.parseLong(server.getServerId().substring(server.getServerId().lastIndexOf("$") + 1)))
            {
                return (isLeader = false);
            }
        }
        return (isLeader = true);
    }

    protected void redistribution()
            throws Exception
    {
        if (serverList == null || serverList.size() == 0)
        {
            return;
        }

        if (!isLeader())
        {
            return;
        }

        List<String> allTask = dmFactory.getAllTaskCodes();
        if (allTask == null || allTask.size() == 0)
        {
            logger.error("没有配置任何Task,直接返回!");
            taskCount = 0;
            return;
        }
        taskCount = allTask.size();

        // 每次重新分派前进行排序，减少分配次数。保证只在任务或者服务器之间发生改变时重新分配。
        Collections.sort(serverList, serverComparator);
        Collections.sort(allTask);

        // Round-Robin
        int pos = 0;
        for (String taskCode : allTask)
        {
            // 获取任务的当前调度管理器
            String curServerId = dmFactory.getCurScheduleServer(taskCode);
            String reqServerId = dmFactory.getReqScheduleServer(taskCode);
            if (curServerId == null || !contains(curServerId))
            {
                dmFactory.setCurScheduleServer(taskCode, serverList.get(pos).getServerId());
                dmFactory.setReqScheduleServer(taskCode, null);
            }
            else if (curServerId.equals(serverList.get(pos).getServerId()) == true && reqServerId == null)
            {
                // 没有改变，保持现状不动
            }
            else
            {
                dmFactory.setReqScheduleServer(taskCode, serverList.get(pos).getServerId());
            }
            pos = (pos + 1) % serverList.size();
        }
        logger.debug("leader重新分配任务成功！");
    }

    private boolean contains(String curServerId)
    {
        if (serverList == null || serverList.size() == 0)
        {
            return false;
        }
        for (ServerBean server : serverList)
        {
            if (server.getServerId().equals(curServerId))
            {
                return true;
            }
        }
        return false;
    }

    // 添加错误接管机制

    // 错误进程记录
    private List<String> errorServerList = new ArrayList<String>();

    // 错误进程记录zk获取标识
    private boolean errorRefreshFlag = false;

    // 重上线记录
    private List<String> reOnlineServerList = new ArrayList<String>();

    /**
     * 进程监测方法 检查所有进程状态,返回异常进程集合
     *
     * @throws Exception
     */
    private void processorCheck()
            throws Exception
    {
        logger.debug("监测进程状态开始....");
        List<String> servers = DataManagerFactory.getDataManager().getAllServerChannles();
        if (servers == null || servers.isEmpty())
            return;
        StringBuilder stringBuilder = new StringBuilder();
        String separator = "";
        for (String serverName : servers)
        {
            stringBuilder.append(separator);
            stringBuilder.append(serverName);
            separator = ",";
        }
        logger.debug("取得进程: " + stringBuilder.toString() + " 开始监测状态...");
        for (String serverName : servers)
        {
            ServerBean serverHeart = dmFactory.getServerRegistry(serverName, Constants.ServerType.processor);
            if (serverHeart == null && !errorServerList.contains(serverName))
            {
                logger.debug("未能取到进程: " + serverName + "状态,该进程已离线!");
                errorServerList.add(serverName);
            }
        }
        // manager重启后第一次加载检查zk数据
        if (!errorRefreshFlag)
        {
            List<String> serverList = dmFactory.qryHandledBackupRunningServer();
            for (String serverName : serverList)
            {
                if (!errorServerList.contains(serverName))
                {
                    logger.debug("进程: " + serverName + "已恢复正常，但仍使用备机配置，需主进程恢复");
                    errorServerList.add(serverName);
                }
            }
            errorRefreshFlag = true;
        }
        if (errorServerList.size() == 0 || errorServerList.isEmpty())
        {
            logger.debug("未监测到错误进程,系统正在正常运行...");
        }
    }

    /**
     * 异常进程监测方法 监测所有异常进程,返回已恢复进程集合
     *
     * @throws Exception
     */
    private void errorProcessorChecker()
            throws Exception
    {
        if (errorServerList.isEmpty())
            return;
        for (String serverName : errorServerList)
        {
            logger.debug("监测进程: " + serverName + "是否重上线...");
            ServerBean serverHeart = dmFactory.getServerRegistry(serverName, Constants.ServerType.processor);
            if (serverHeart == null)
            {
                logger.debug("进程: " + serverName + " 仍未上线...");
            }
            else
            {
                if (!reOnlineServerList.contains(serverName))
                {
                    reOnlineServerList.add(serverName);
                    logger.debug("进程: " + serverName + "已重上线...");
                }
            }
        }
        logger.debug("目前有" + reOnlineServerList.size() + "个进程重上线,等待恢复初始配置...");
    }

    /**
     * 后备进程切换方法
     */
    private void changeToBackup()
    {
        logger.debug("后备切换开始...");
        if (errorServerList.isEmpty())
            return;
        for (String serverName : errorServerList)
        {
            String backupServerName = "";
            try
            {
                if (dmFactory.qryBackupRunningTaskItem(serverName) != null)
                {
                    logger.debug("错误进程: " + serverName + " 已被处理...");
                    continue;
                }

                logger.debug("后备切换开始,处理进程: " + serverName);
                backupServerName = dmFactory.getBackupProcessor(serverName);
                if (StringUtils.isBlank(backupServerName))
                {
                    logger.debug("主进程: " + serverName + " 不存在备用进程。");
                    continue;
                }
                logger.debug("后备切换取得进程: " + serverName + " 备用进程: " + backupServerName);
                Map<String, List<String>> taskItemMap = dmFactory.getAllTaskItemsByServerId(serverName);
                if (taskItemMap == null || taskItemMap.isEmpty())
                    continue;
                Set<String> keySet = taskItemMap.keySet();
                List<TaskItemBackupInfo> taskItemBackupInfoList = new ArrayList<TaskItemBackupInfo>();
                for (String taskCode : keySet)
                {
                    List<String> itemList = taskItemMap.get(taskCode);
                    if (itemList == null || itemList.isEmpty())
                        continue;
                    for (String item : itemList)
                    {
                        TaskItemBackupInfo taskItemBackupInfo = new TaskItemBackupInfo();
                        taskItemBackupInfo.setTaskCode(taskCode);
                        taskItemBackupInfo.setItemCode(item);
                        taskItemBackupInfo.setNormalServerName(serverName);
                        taskItemBackupInfo.setBackupServerName(backupServerName);
                        taskItemBackupInfoList.add(taskItemBackupInfo);
                    }
                }
                logger.debug("获取进程: " + serverName + " 中运行的任务分片数: " + taskItemBackupInfoList.size());
                // 后备运行记录
                List<TaskItemBackupInfo> backupRunningTaskItemList = new ArrayList<TaskItemBackupInfo>();
                for (TaskItemBackupInfo taskItemBackupInfo : taskItemBackupInfoList)
                {
                    String taskCode = taskItemBackupInfo.getTaskCode();
                    String item = taskItemBackupInfo.getItemCode();
                    TaskBean task = dmFactory.getTaskInfoByTaskCode(taskItemBackupInfo.getTaskCode());
                    if ("A".equals(task.getFaultProcessMethod()))
                    {
                        logger.error("任务: " + task.getTaskCode() + " 错误接管方式为自动,开始切换分片...");
                        logger.debug("错误接管切换任务: " + taskCode + " 分片: " + item + " 至进程: " + backupServerName);
                        dmFactory.assignServer2TaskItem(task.getTaskCode(), item, backupServerName);
                        backupRunningTaskItemList.add(taskItemBackupInfo);
                        logger.debug("进程 " + serverName + "异常,切换分片 " + item + "至" + backupServerName + "运行");
                    }
                    if ("M".equals(task.getFaultProcessMethod()))
                    {
                        logger.debug("进程 " + serverName + "为人工处理模式,系统已忽略!");
                    }
                }
                dmFactory.saveBackupRunningTaskItem(serverName, backupRunningTaskItemList);

            }
            catch (Exception e)
            {
                logger.error("进程: " + serverName + " 切换至备用进程: " + backupServerName + "异常!", e);
            }
        }
    }

    /**
     * 进程恢复方法 将相关任务分片切换至已恢复进程中执行
     */
    private void processorRecovery()
            throws Exception
    {
        if (reOnlineServerList.isEmpty())
            return;
        List<String> needRemoveServerList = new ArrayList<String>();

        for (Iterator<String> it = reOnlineServerList.iterator(); it.hasNext();)
        {
            String reOnlineServerName = it.next();
            logger.debug("进程: " + reOnlineServerName + "重上线,开始恢复其任务...");
            List<TaskItemBackupInfo> backupRunningTaskItemList = dmFactory.qryBackupRunningTaskItem(reOnlineServerName);
            if (backupRunningTaskItemList == null)
            {
                // 主进程不存在备用进程场景
                it.remove();
                logger.debug("重上线记录中移除进程: " + reOnlineServerName);
                errorServerList.remove(reOnlineServerName);
                logger.debug("异常进程记录中移除: " + reOnlineServerName);
                continue;
            }
            for (TaskItemBackupInfo taskItemBackupInfo : backupRunningTaskItemList)
            {
                // 处理切换后，已切换任务分片被删除情况
                try
                {
                    dmFactory.assignServer2TaskItem(taskItemBackupInfo.getTaskCode(),
                            taskItemBackupInfo.getItemCode(),
                            reOnlineServerName);
                    logger.debug(reOnlineServerName + " 重新上线,恢复 " + taskItemBackupInfo.getTaskCode() + " 原始配置");
                }
                catch (Exception e)
                {
                    if (e.getMessage().indexOf("不存在的任务项") > -1)
                    {
                        logger.debug("任务 " + taskItemBackupInfo.getTaskCode() + "分片" + taskItemBackupInfo.getItemCode()
                                + "已被删除，跳过主进程恢复。");
                    }
                    else
                        // 不应影响其他进程恢复
                        logger.error("任务 " + taskItemBackupInfo.getTaskCode() + "分片" + taskItemBackupInfo.getItemCode()
                                + "主进程恢复失败！错误原因" + e.getMessage());
                }

            }
            dmFactory.deleteBackupRunningTaskItem(reOnlineServerName);
            needRemoveServerList.add(reOnlineServerName);
        }
        if (!needRemoveServerList.isEmpty())
        {
            for (String serverName : needRemoveServerList)
            {
                reOnlineServerList.remove(serverName);
                logger.debug("重上线记录中移除进程: " + serverName);
                errorServerList.remove(serverName);
                logger.debug("异常进程记录中移除: " + serverName);

            }
        }
    }

    private void serverCheck()
    {
        try
        {
            this.processorCheck();
            if (!errorServerList.isEmpty())
            {
                this.changeToBackup();
            }
            this.errorProcessorChecker();
            if (!reOnlineServerList.isEmpty())
            {
                this.processorRecovery();
            }
        }
        catch (Exception e)
        {
            logger.debug("后备切换线程异常!", e);
        }
    }
}
