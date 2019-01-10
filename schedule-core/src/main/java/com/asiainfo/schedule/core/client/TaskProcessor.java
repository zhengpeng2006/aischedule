package com.asiainfo.schedule.core.client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.po.ServerBean;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskJobBean;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.Constants;
import com.asiainfo.schedule.core.utils.Constants.ServerType;
import com.asiainfo.schedule.core.utils.Constants.TaskType;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.core.utils.SchedulerLogger;
import com.asiainfo.schedule.core.utils.SwitchUtils;

public abstract class TaskProcessor implements Runnable {

    private static transient final Logger logger = LoggerFactory.getLogger(TaskProcessor.class);

    protected String itemJobId;

    protected TaskBean taskBean;

    protected String taskItem;

    protected String jobId;

    protected TaskDealParam param;

    protected String serverId;

    protected Timestamp createTime;

    volatile int sts = VIRGIN;

    private int threadNum;

    // 初始执行
    static final int VIRGIN = 0;

    // 执行中
    static final int RUNNING = 1;

    static final int STOPPING = 2;

    // 执行结束
    static final int FINISH = 3;

    AtomicInteger COUNTER = new AtomicInteger(0);

    List<Thread> threadList = Collections.synchronizedList(new ArrayList<Thread>());

    TaskProcessor(TaskBean taskBean, String taskItem, String jobId, String itemJobId,
                  Map<String, String> taskItemParam, String serverId)
            throws Exception {
        if (taskBean == null || CommonUtils.isBlank(taskItem) || CommonUtils.isBlank(jobId)) {
            throw new NullPointerException();
        }

        this.createTime = DateUtils.getCurrentDate();
        this.taskBean = taskBean;
        this.taskItem = taskItem;
        this.itemJobId = itemJobId;
        this.jobId = jobId;
        this.serverId = serverId;

        param = new TaskDealParam();
        param.setTaskCode(this.taskBean.getTaskCode());
        param.setTaskGroup(this.taskBean.getTaskGroupCode());
        param.setTaskType(this.taskBean.getTaskType());
        param.setEachFetchDataNum(this.taskBean.getScanNum());
        param.setJobId(jobId);
        param.setSplitRegion(this.taskBean.isSplitRegion());
        String[] items = taskBean.getItems();
        if (items == null || items.length == 0) {
            throw new Exception("任务配置的拆分项数据为空，请修正数据后执行！");
        }
        param.setTaskItemCount(items.length);
        // 按地市拆分
        if (this.taskBean.isSplitRegion()) {
            if (taskItem.indexOf("^") > 0) {
                String[] tmp = taskItem.split("\\^");
                param.setRegionCode(tmp[0]);
                param.setTaskItem(tmp[1]);
            } else {
                param.setRegionCode(taskItem);
                param.setTaskItem(items[0]);// 默认为0
            }
        } else {
            // 不按地市拆分
            param.setTaskItem(taskItem);
        }

        // 优先取任务项上的启动参数，如果为空则取任务上的（兼容老的已经启动的tf任务）。
        if (taskItemParam == null) {
            TaskJobBean jobBean = DataManagerFactory.getDataManager().getTaskJobByJobId(taskBean.getTaskCode(), jobId);
            if (jobBean == null) {
                throw new RuntimeException("当前作业流水已删除！");
            }
            param.setTaskParam(jobBean.getParam());
        } else {
            param.setTaskParam(taskItemParam);
        }

        threadNum = this.taskBean.getThreadNum();
        if (threadNum <= 0) {
            // 最小为1
            threadNum = 1;
        }
        TaskType tt = TaskType.valueOf(this.getTaskBean().getTaskType());
        if (!tt.isComplex()) {
            // 单线程执行任务
            threadNum = 1;
        }

    }

    public void startProcess() {

        sts = RUNNING;

        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(this);
            thread.setName(this.getProcessorName() + "-" + i);
            thread.setDaemon(true);
            thread.start();

            threadList.add(thread);
        }
        if (logger.isInfoEnabled()) {
            logger.info("当前任务启动线程数量为:" + threadNum);
        }

    }

    @Override
    public void run() {
        try {
            this.excute();

        } catch (Throwable ex) {
            logger.error("任务处理错误！" + this.taskBean.getTaskCode() + ":" + taskItem + ",任务流水:" + jobId, ex);

            String processErrMsg = CommonUtils.getExceptionMesssage(ex, 1000);
            
            SchedulerLogger.addTaskLogDtl(taskBean.getTaskCode(),
                    taskBean.getVersion() + "",
                    jobId,
                    Constants.ServerType.processor.name(),
                    taskItem,
                    serverId,
                    "执行异常",
                    processErrMsg);
            
			if (!processErrMsg.contains("TaskProcessorManager.stopTaskProcessor")) {
				SchedulerLogger.addTaskErrorLogDtl(taskBean.getTaskCode(), taskBean.getVersion() + "", jobId, Constants.ServerType.processor.name(), taskItem,
						serverId, "执行异常", SwitchUtils.getTaskErrorLevel(serverId, taskBean.getTaskCode(), taskItem), processErrMsg);
			}
        } finally {
            int c = COUNTER.incrementAndGet();
            if (c == this.threadNum) {
                this.sts = FINISH;
                if (logger.isInfoEnabled()) {
                    logger.info("所有任务执行线程已经结束。。。");
                }
                SchedulerLogger.addTaskLogDtl(taskBean.getTaskCode(),
                        taskBean.getVersion() + "",
                        jobId,
                        Constants.ServerType.processor.name(),
                        taskItem,
                        serverId,
                        "任务执行结束",
                        this.getTaskDealParam().getExecuteResult());
                loggerAlarmLog();
            }
        }
    }
    
    /**
     * 告警日志记录 
     */
	private void loggerAlarmLog() {
		Map<String, Object> result = SwitchUtils.getBackupSwitchInfo(serverId, taskBean.getTaskCode(), taskItem);
		if (null != result && null != result.get("masterServerId")) {
			logger.error("任务在备进程执行结束，但主进程[" + result.get("masterServerId") + "]异常！");
			SchedulerLogger.addTaskErrorLogDtl(taskBean.getTaskCode(), taskBean.getVersion() + "", jobId, Constants.ServerType.processor.name(), taskItem,
					serverId, "任务执行结束", Constants.TaskErrLevel.LEVEL_2, "任务在备进程执行结束，但主进程[" + result.get("masterServerId") + "]异常！");
		} else {
			try {
				DataManagerFactory df = DataManagerFactory.getDataManager();
				String backupServerId = df.getBackupProcessor(serverId);
				if (StringUtils.isNotBlank(backupServerId)) {
					// 获取执行进程心跳信息
					ServerBean serverHeart = df.getServerRegistry(backupServerId, ServerType.processor);
					// 没有心跳或者心跳超过死亡间隔
					if (serverHeart == null
							|| (System.currentTimeMillis() - serverHeart.getHeartbeatTime().getTime()) > df.getScheduleConfig().getDeadInterval()) {
						logger.error("任务在主进程执行结束，但备进程[" + backupServerId + "]异常！");
						SchedulerLogger.addTaskErrorLogDtl(taskBean.getTaskCode(), taskBean.getVersion() + "", jobId, Constants.ServerType.processor.name(),
								taskItem, serverId, "任务执行结束", Constants.TaskErrLevel.LEVEL_1, "任务在主进程执行结束，但备进程[" + backupServerId + "]异常！");
					}
				
				}
			} catch (Exception e) {
				logger.error("查询备进程状态异常!", e);
			}
		}
	}

    public String getProcessorName() {
        return this.getTaskBean().getTaskType() + "[" + this.getItemJobId() + "]";
    }

    public TaskDealParam getTaskDealParam() {
        return param;
    }

    public void stopProcess() {
        this.sts = STOPPING;
    }

    public String getItemJobId() {
        return itemJobId;
    }

    public TaskBean getTaskBean() {
        return taskBean;
    }

    public void setTaskBean(TaskBean taskBean) {
        this.taskBean = taskBean;
    }

    public String getTaskItem() {
        return taskItem;
    }

    public void setTaskItem(String taskItem) {
        this.taskItem = taskItem;
    }

    public int getSts() {
        return sts;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getThreadNum() {
        return this.threadNum;
    }

    public boolean isFinish() {
        return this.sts == FINISH;
    }

    public boolean isRunning() {
        return this.sts == RUNNING;
    }

    protected abstract void excute()
            throws Exception;

    public abstract String getDealDescription();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("task_deal_param:").append(param.toString());
        sb.append(",sts:").append(this.getSts());
        return sb.toString();
    }

}
