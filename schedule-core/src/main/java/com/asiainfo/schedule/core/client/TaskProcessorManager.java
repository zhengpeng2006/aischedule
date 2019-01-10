package com.asiainfo.schedule.core.client;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.po.ServerBean;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskItemJobBean;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.Constants;
import com.asiainfo.schedule.core.utils.Constants.RunSts;
import com.asiainfo.schedule.core.utils.Constants.ServerType;
import com.asiainfo.schedule.core.utils.Constants.TaskType;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.core.utils.SchedulerLogger;
import com.asiainfo.schedule.core.utils.SwitchUtils;

/**
 * 任务处理管理主线程. 0.服务启动时注册，定时反馈心跳 1.为当前服务运行的每个任务创建一个任务处理器。创建管理列表：维护任务项与处理器的关系 2.定时检查当前服务的状态，如果状态为停止则终止、移除所有管理的任务处理器；
 * 3.若为启动则检查任务是否被重新分配，若任务被重新分配则从管理列表中移除，并终止对应的处理器。 若新增任务则创建处理器，并添加到管理列表中。 4.检查任务状态
 * <p>
 * 任务处理器： 1.根据任务编码、任务项编码获取任务流水号 2.没有可处理的任务流水号则直接退出 3.若存在：对于single、reload任务，获取线程资源，直接执行任务配置的业务方法，执行完成后归还线程资源。 对于complex任务，先获取需要的线程资源，然后执行一次取总数的方法，再循环取数执行业务方法。
 * 若取数的数量为空调用是否需要退出的接口，若需要退出则归还线程资源结束当此处理；若不需要退出则睡眠等待下次取数。
 * <p>
 * 任务执行器按优先级执行任务，当运行的线程数达到指定阈值，任务进入等待执行队列。
 *
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月30日
 * @modify
 */
public class TaskProcessorManager extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(TaskProcessorManager.class);

	private final DataManagerFactory dmFactory = DataManagerFactory.getDataManager();

	private final ServerType serverType = ServerType.processor;

	// 启动时注册生成的唯一编码
	private String myid;

	private volatile boolean running;

	private AtomicLong version = new AtomicLong(0);

	private Map<String, HashSet<TaskProcessor>> taskProcessorMap = new ConcurrentHashMap<String, HashSet<TaskProcessor>>();

	// 线程阈值（并发处理最大线程数【主要取决于数据库连接数】）
	private int threadThreshold = 25;

	// 等待告警次数
	private int taskMaxWaitTimes;

	// 流水号进入等待队列连续次数
	private Map<String, Integer> waitTimesMap = new ConcurrentHashMap<String, Integer>();

	private int refreshExceptionCount = 0;
	
	private long overDateFreshTime;
	
	//一天毫秒数
	private long dayMillis = 24 * 60 * 60 * 1000;

	private Comparator<TaskItemJobBean> taskPriorComparator = new Comparator<TaskItemJobBean>() {
		@Override
		public int compare(TaskItemJobBean o1, TaskItemJobBean o2) {
			if (o1.getPriority() < o2.getPriority()) {
				return 1;
			} else if (o1.getPriority() > o2.getPriority()) {
				return -1;
			} else {
				return 0;
			}
		}
	};

	public TaskProcessorManager() throws Exception {
		super("TaskProcessor-Main");
		this.setDaemon(true);
		myid = CommonUtils.getServerName();
		try {
			String t = dmFactory.getScheduleConfig().getThreadThreshold();
			String w = dmFactory.getScheduleConfig().getTaskMaxAlarmTimes();
			if (CommonUtils.isBlank(t)) {
				logger.warn("default set threadThreshold 25");
				threadThreshold = 25;
			} else {
				threadThreshold = Integer.parseInt(t);
			}
			if (CommonUtils.isBlank(w)) {
				logger.warn("default set taskMaxWaitTimes 3");
				taskMaxWaitTimes = 3;
			} else {
				taskMaxWaitTimes = Integer.parseInt(w);
			}
		} catch (Exception ex) {
			logger.error("default set threadThreshold 25," + ex.getMessage());
			threadThreshold = 25;
			taskMaxWaitTimes = 3;
		}
		running = true;

	}

	private void suspendServer() {
		if (taskProcessorMap.isEmpty()) {
			return;
		}
		Collection<HashSet<TaskProcessor>> taskSets = taskProcessorMap.values();
		for (HashSet<TaskProcessor> set : taskSets) {
			for (TaskProcessor tp : set) {
				tp.stopProcess();
			}
		}
	}

	private void stopTaskProcessor(TaskItemJobBean job) {
		TaskProcessor tp = getTaskProcessor(job.getTaskCode(), job.getTaskItemId(), job.getTaskJobId(), job.getItemJobId());
		if (tp != null && tp.isRunning()) {
			tp.stopProcess();
			SchedulerLogger.addTaskLogDtl(job.getTaskCode(), job.getTaskVersion() + "", job.getTaskJobId(), Constants.ServerType.processor.name(),
					job.getTaskItemId(), myid, "终止执行", "");
		}
	}

	@Override
	public void run() {
		this.setPriority(Thread.MAX_PRIORITY);

		// 先注册
		try {
			dmFactory.serverRegist(myid, this.serverType);
		} catch (Exception ex) {
			throw new RuntimeException("执行进程注册失败，直接退出！" + myid, ex);
		}

		// 主循环
		int heartbeat = this.dmFactory.getScheduleConfig().getHeartbeatInterval();

		while (running) {
			try {
				logger.info("refresh start[" + version.incrementAndGet() + "]>>>>>>>>>>>>>>>>>>>>>>>>>");
				logger.info("stats:" + stats());

				long start = System.currentTimeMillis();
				this.refresh();
				logger.info("refresh finish[" + version.get() + "],cost(ms)" + (System.currentTimeMillis() - start) + "<<<<<<<<<<<<<<<<<<<<<<<<<<");
			} catch (Throwable t) {
				logger.error("", t);
			}
			try {
				sleep(heartbeat);
			} catch (InterruptedException e) {
				e.printStackTrace();
				running = false;
			}
		}

	}

	private void refresh() {

		try {
			ServerBean server = dmFactory.getServerRegistry(myid, this.serverType);
			if (server == null) {
				dmFactory.serverRegist(myid, this.serverType);
				logger.info("任务处理服务器未注册或已经失效，注册成功。" + myid);
			} else {
				server.setHeartbeatTime(DateUtils.getCurrentDate());
				server.setHeartbeatInfo("version:" + version.get() + ",exeTaskCount:" + this.taskProcessorMap.size() + ",runningThreadCount:"
						+ this.getTaskProcessorThreadCount());
				// 先发送心跳信息
				dmFactory.heartbeat(server);
			}

			releaseTaskProcessor();

			if (RunSts.stop.name().equals(this.dmFactory.getTaskServerRunSts(myid))) {
				logger.info("服务进程状态挂起，停止处理任务！");
				this.suspendServer();
				return;
			}

			// 获取当前服务可处理的任务列表
			List<TaskItemJobBean> newTasks = this.dmFactory.getAllTaskJobsByServerId(myid);

			if (newTasks == null || newTasks.size() == 0) {
				logger.info("服务进程没有可处理的任务！");
				this.suspendServer();
				return;
			}
			// 按任务的优先级从大到小排序
			Collections.sort(newTasks, taskPriorComparator);

			for (TaskItemJobBean task : newTasks) {
				processTask(task);
			}
			// 重设计数器
			if (refreshExceptionCount > 0) {
				refreshExceptionCount = 0;
			}
		} catch (Throwable t) {
			refreshExceptionCount++;
			logger.error("refresh exception:" + refreshExceptionCount, t);
			SchedulerLogger.addTaskErrorLogDtl("-1", "-1", "-1", Constants.ServerType.processor.name(), "-1", myid, "任务执行器刷新异常！",
					Constants.TaskErrLevel.LEVEL_7, CommonUtils.getExceptionMesssage(t, 1000));
			if (refreshExceptionCount >= 120) {
				// 告警
				logger.error("管理主线程维护状态连续异常次数达到心跳的12倍，结束当前容器中的任务！！");
				this.suspendServer();
			}
		}
	}

	private void processTask(TaskItemJobBean job) {
		try {

			TaskBean taskBean = this.dmFactory.getTaskInfoByTaskCode(job.getTaskCode());

			TaskProcessor tp = getTaskProcessor(job.getTaskCode(), job.getTaskItemId(), job.getTaskJobId(), job.getItemJobId());
			if (taskBean == null) {
				logger.error("任务配置已经被删除,移除任务流水号！！" + job.getItemJobId());

				if (tp != null) {
					// 先停止任务，当任务执行器状态结束后再移除
					tp.stopProcess();
				} else {
					// 当前调度器未执行，直接移除。
					try {
						this.dmFactory.deleteTaskItemJobFromServer(myid, job.getItemJobId());
					} catch (Exception e) {
						logger.error("", e);
					}
				}
				return;
			}

			if (RunSts.stop.name().equals(job.getSts())) {
				logger.error("任务拆分项状态为终止！！！" + job.getItemJobId());
				if (tp == null) {
					this.dmFactory.finishTaskItemJob(myid, job.getTaskCode(), job.getTaskItemId(), job.getTaskJobId(), job.getItemJobId());
				} else {
					this.stopTaskProcessor(job);
				}
				return;
			}
			String sts = this.dmFactory.getTaskRunSts(taskBean.getTaskCode());
			if (RunSts.start.name().equals(sts)) {
				if (tp == null) {

					// 执行前校验任务是否被重新分配
					String serverId = this.dmFactory.getTaskItemServerId(job.getTaskCode(), job.getTaskItemId());
					if (!myid.equals(serverId)) {
						logger.error("当前任务【" + job.getItemJobId() + "】重新分配，从调度队列中移除。");
						this.dmFactory.finishTaskItemJob(myid, job.getTaskCode(), job.getTaskItemId(), job.getTaskJobId(), job.getItemJobId());
						if (!CommonUtils.isBlank(serverId)) {
							this.dmFactory.transformTaskItemJob(job, serverId);
						}
						return;
					}

					int runThreadCnt = getTaskProcessorThreadCount();
					// 执行线程数是否达到阈值
					if (runThreadCnt >= this.threadThreshold) {
						logger.error("任务执行容器当前执行的任务线程数【" + runThreadCnt + "】已经达到配置的阈值。任务流水【" + job.getItemJobId() + "】进入等待队列。");
						Integer itemJobId_waitTimes = waitTimesMap.get(job.getItemJobId());
						if (itemJobId_waitTimes == null) {
							waitTimesMap.put(job.getItemJobId(), 0);
						} else if (itemJobId_waitTimes < taskMaxWaitTimes) {
							waitTimesMap.put(job.getItemJobId(), ++itemJobId_waitTimes);
						} else {
							SchedulerLogger.addTaskErrorLogDtl(job.getTaskCode(), "-1", job.getTaskJobId(), Constants.ServerType.processor.name(),
									job.getTaskItemId(), myid, "任务堵塞，且连续次数达到最大尝试次数" + taskMaxWaitTimes + "！", Constants.TaskErrLevel.LEVEL_8, "任务执行容器当前执行的任务线程数【"
											+ runThreadCnt + "】已经达到配置的阈值。任务流水【" + job.getItemJobId() + "】进入等待队列。");
						}
						
						clearOverDate();
						return;
					}
					
					removeWaitTimesMap(job.getItemJobId());

					if (this.applyTaskItem(job.getTaskCode(), job.getTaskItemId())) {
						try {
							tp = createProcessor(taskBean, job.getTaskItemId(), job.getTaskJobId(), job.getItemJobId(), job.getTaskItemParam());
						} catch (Exception ex) {
							logger.error("初始化任务执行类异常！" + taskBean.getTaskCode() + ":" + job.getTaskItemId() + ",任务流水:" + job.getTaskJobId(), ex);

							SchedulerLogger.addTaskLogDtl(taskBean.getTaskCode(), taskBean.getVersion() + "", job.getTaskJobId(),
									Constants.ServerType.processor.name(), job.getTaskItemId(), myid, "任务执行异常", CommonUtils.getExceptionMesssage(ex, 1000));
							this.dmFactory.finishTaskItemJob(myid, job.getTaskCode(), job.getTaskItemId(), job.getTaskJobId(), job.getItemJobId());
							throw ex;
						}

						// 启动任务执行
						tp.startProcess();

						// add manager map
						HashSet<TaskProcessor> set = this.taskProcessorMap.get(taskBean.getTaskCode());
						if (set == null) {
							set = new HashSet<TaskProcessor>();
						}
						set.add(tp);
						this.taskProcessorMap.put(taskBean.getTaskCode(), set);

						SchedulerLogger.addTaskLogDtl(taskBean.getTaskCode(), taskBean.getVersion() + "", job.getTaskJobId(),
								Constants.ServerType.processor.name(), job.getTaskItemId(), myid, "任务开始执行", "");

					}

				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("当前任务流水号【" + job.getItemJobId() + "】执行中。。。");
					}
					// 校验任务是否被重新分配
					String serverId = this.dmFactory.getTaskItemServerId(job.getTaskCode(), job.getTaskItemId());
					if (!myid.equals(serverId)) {
						if (tp.isFinish()) {
							logger.info("当前任务【" + job.getItemJobId() + "】重新分配，但已执行结束等待释放。");
						} else {
							logger.info("当前任务【" + job.getItemJobId() + "】重新分配，从调度队列中移除。");
							this.stopTaskProcessor(job);
							// 任务还未执行完成，重新分派任务单至新的server中，并结束自己的任务。
							if (!CommonUtils.isBlank(serverId)) {
								TaskItemJobBean newItemJob = this.dmFactory.transformTaskItemJob(job, serverId);
								logger.info("当前任务【" + job.getItemJobId() + "】重新分配，转移任务成功。" + newItemJob.getItemJobId());
							}
						}
					}
					
					//移除single实现的常驻任务
					if(!"U".equals(taskBean.getState())){
						if (tp.isFinish()) {
							logger.info("当前任务【" + job.getItemJobId() + "】状态未生效，但已执行结束等待释放。");
						} else {
							logger.info("当前任务【" + job.getItemJobId() + "】状态未生效，从调度队列中移除。");
							this.stopTaskProcessor(job);
						}
					}
				}
			} else {
				// 状态如果已经终止
				if (logger.isInfoEnabled()) {
					logger.info("任务状态已终止，任务停止执行【" + job.getItemJobId() + "】");
				}
				if (tp == null) {
					this.dmFactory.finishTaskItemJob(myid, job.getTaskCode(), job.getTaskItemId(), job.getTaskJobId(), job.getItemJobId());
				} else {
					this.stopTaskProcessor(job);
				}
			}
		} catch (Throwable ex) {
			logger.error("应用进程处理任务作业流水出现异常！", ex);
			SchedulerLogger.addTaskErrorLogDtl(job.getTaskCode(), "-1", job.getTaskJobId(), Constants.ServerType.processor.name(), job.getTaskItemId(), myid,
					"应用进程处理任务作业流水出现异常！", SwitchUtils.getTaskErrorLevel(job.getServerId(), job.getTaskCode(), job.getTaskItemId()),
					CommonUtils.getExceptionMesssage(ex, 1000));
		}
	}

	private void removeWaitTimesMap(String itemJobId) {
		waitTimesMap.remove(itemJobId);
	}

	/**
	 * 至多一天清理一次map，防止脏数据一直占内存
	 */
	private void clearOverDate() {
		try {
			if (overDateFreshTime == 0) {
				overDateFreshTime = System.currentTimeMillis();
				return;
			}

			if (System.currentTimeMillis() > overDateFreshTime + dayMillis) {
				waitTimesMap.clear();
				overDateFreshTime = System.currentTimeMillis();
			}
		} catch (Exception e) {
			logger.error("clearOverDate err ,e = " + e);
		}
		
	}

	private TaskProcessor getTaskProcessor(String taskCode, String taskItemId, String taskJobId, String itemJobId) {
		TaskProcessor result = null;
		if (!taskProcessorMap.containsKey(taskCode)) {
			return result;
		}
		for (TaskProcessor p : taskProcessorMap.get(taskCode)) {
			// if (p.getTaskBean().getTaskCode().equals(taskCode) &&
			// p.getTaskItem().equals(taskItemId)) {
			if (p.getTaskBean().getTaskCode().equals(taskCode) && p.getItemJobId().equals(itemJobId)) {
				return p;
			}
		}
		return result;
	}

	private int getTaskProcessorThreadCount() {
		if (taskProcessorMap.size() == 0) {
			return 0;
		}
		Collection<HashSet<TaskProcessor>> values = taskProcessorMap.values();
		int count = 0;
		for (HashSet<TaskProcessor> set : values) {
			for (TaskProcessor p : set) {
				count += p.getThreadNum();
			}
		}
		return count;
	}

	/**
	 * 释放已经处理结束的任务处理器
	 *
	 * @throws Exception
	 */
	private void releaseTaskProcessor() {
		if (taskProcessorMap.isEmpty()) {
			return;
		}
		Set<String> taskCodes = taskProcessorMap.keySet();
		for (String taskCode : taskCodes) {
			HashSet<TaskProcessor> set = taskProcessorMap.get(taskCode);
			for (Iterator<TaskProcessor> it = set.iterator(); it.hasNext();) {
				TaskProcessor tp = it.next();
				if (tp.isFinish()) {
					try {
						this.dmFactory.finishTaskItemJob(myid, taskCode, tp.getTaskItem(), tp.getJobId(), tp.getItemJobId());
					} catch (Exception ex) {
						logger.error("任务项执行结束释放出现异常", ex);
					}
					it.remove();
				}
			}

			if (set.size() == 0) {
				taskProcessorMap.remove(taskCode);
			}

		}
	}

	private boolean applyTaskItem(String task, String item) throws Exception {

		String processorId = this.dmFactory.getTaskItemCurProcessorId(task, item);
		if (CommonUtils.isBlank(processorId)) {
			this.dmFactory.applyTaskItem(task, item, myid);
		} else if (processorId.equals(myid)) {

		} else {
			ServerBean server = this.dmFactory.getServerRegistry(processorId, ServerType.processor);
			// 校验此server是否已经失效，如果已经失效则释放任务项
			if (server == null || (System.currentTimeMillis() - server.getHeartbeatTime().getTime()) > dmFactory.getScheduleConfig().getDeadInterval()) {
				logger.error("当前任务项被其他服务锁定,但锁定的服务已经失效，释放任务项。！taskCode:" + task + ",itemCode：" + item + ",锁定的服务进程名:" + processorId);
				this.dmFactory.applyTaskItem(task, item, myid);
				return true;
			} else {
				// 被其他进程锁定，反向校验此服务id是否正在处理此任务项
				List<TaskItemJobBean> list = this.dmFactory.getAllTaskJobsByServerId(processorId);
				if (list != null && list.size() > 0) {
					for (TaskItemJobBean job : list) {
						if (job.getTaskItemId().equals(item)) {
							logger.error("当前任务项正被其他服务进程处理中，申请失败！taskCode:" + task + ",itemCode：" + item + ",锁定的服务进程名:" + processorId);
							return false;
						}
					}
				} else {
					logger.warn("当前任务项被其他服务锁定，更改成功！taskCode:" + task + ",itemCode：" + item + ",锁定的服务进程名:" + processorId);
					this.dmFactory.applyTaskItem(task, item, myid);
					return true;
				}
			}
		}
		return true;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private TaskProcessor createProcessor(TaskBean taskBean, String taskItem, String jobid, String itemJobId, Map<String, String> param) throws Exception {
		TaskProcessor p = null;

		if (TaskType.single.name().equals(taskBean.getTaskType())) {
			p = new SingleTaskWrapper(taskBean, taskItem, jobid, itemJobId, param, myid);
		} else if (TaskType.complex.name().equals(taskBean.getTaskType()) || TaskType.tf.name().equals(taskBean.getTaskType())) {
			p = new ComplexTaskWrapper(taskBean, taskItem, jobid, itemJobId, param, myid);
		} else if (TaskType.reload.name().equals(taskBean.getTaskType())) {
			p = new ReloadTaskWrapper(taskBean, taskItem, jobid, itemJobId, param, myid);
		} else {
			throw new Exception("不支持的任务类型！" + taskBean.getTaskType());
		}
		return p;
	}

	public String stats() {
		return taskProcessorMap.toString();
	}

}
