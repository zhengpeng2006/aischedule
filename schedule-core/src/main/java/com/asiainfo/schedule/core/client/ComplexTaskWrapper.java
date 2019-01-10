package com.asiainfo.schedule.core.client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.appframe2.bo.dialect.DialectFactory;
import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.logger.utils.LoggerUtils;
import com.asiainfo.schedule.core.client.interfaces.IComplexTaskDeal;
import com.asiainfo.schedule.core.client.interfaces.ISleepTimeController;
import com.asiainfo.schedule.core.client.tf.AbstractTfTaskDeal;
import com.asiainfo.schedule.core.client.tf.DefaultTfTaskDeal;
import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.Constants;
import com.asiainfo.schedule.core.utils.Constants.TaskType;
import com.asiainfo.schedule.core.utils.DateUtils;

/**
 * 复杂任务多线程实现，为任务项对应的作业流水根据任务配置的线程数、扫描间隔时间、每次处理数量等配置 启动线程轮询处理任务数据
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年9月22日
 * @modify
 * @param <T>
 */
public class ComplexTaskWrapper<T> extends TaskProcessor {

	private static final transient Logger logger = LoggerFactory.getLogger(ComplexTaskWrapper.class);

	protected IComplexTaskDeal<T> taskDealBean;

	Comparator<T> taskComparator;

	StatisticsInfo statisticsInfo;

	Timestamp lastFetchDataTime;

	protected List<T> taskList = Collections.synchronizedList(new ArrayList<T>());

	protected List<T> runningTaskList = Collections.synchronizedList(new ArrayList<T>());

	protected List<T> maybeRepeatTaskList = Collections.synchronizedList(new ArrayList<T>());

	Lock lockFetchID = new ReentrantLock();
	Lock lockFetchMutilID = new ReentrantLock();
	Lock lockLoadData = new ReentrantLock();

	boolean isMutilTask = true;
	boolean isSleeping = false;

	Boolean isFirstLoad = Boolean.TRUE;

	boolean logOutputFlag = false;
	long lastCostTime = 0;
	long lastDealSus = 0;
	long lastDealFail = 0;

	int scanInTime = 1000;// 默认的间隔扫描时间
	int idleTime = 10000;// 默认的空闲扫描间隔时间

	long lastSelectTaskCount = 0;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ComplexTaskWrapper(TaskBean taskBean, String taskItem, String jobId, String itemJobId, Map<String, String> _param,
			String serverId) throws Exception {
		super(taskBean, taskItem, jobId, itemJobId, _param, serverId);
		statisticsInfo = new StatisticsInfo();

		if (taskBean.getTaskType().equalsIgnoreCase("tf") && CommonUtils.isBlank(taskBean.getProcessClass())) {
			taskDealBean = (IComplexTaskDeal) new DefaultTfTaskDeal();
		} else {

			if (CommonUtils.isBlank(taskBean.getProcessClass())) {
				throw new Exception("业务实现类不能为空，必须实现接口IComplexTaskDeal！");
			}
			Object obj = Class.forName(taskBean.getProcessClass()).newInstance();
			if (!(obj instanceof IComplexTaskDeal)) {
				throw new Exception("任务类型为complex，业务实现类必须实现接口IComplexTaskDeal！" + taskBean.getProcessClass());
			}

			if (taskBean.getTaskType().equalsIgnoreCase("tf") && !(obj instanceof AbstractTfTaskDeal)) {
				throw new Exception("任务类型为tf，业务实现类必须实现AbstractTfTaskDeal！" + taskBean.getProcessClass());
			}

			taskDealBean = (IComplexTaskDeal) obj;
		}

		this.taskComparator = new MYComparator(this.taskDealBean.getComparator());

		if (taskBean.getScanNum() < taskBean.getThreadNum() * 10) {
			logger.error("参数设置不合理，系统性能不佳。【每次从数据库获取的数量scanNum】 >= 【线程数量threadnum】 *【最少循环次数10】 ");
		}

		this.statisticsInfo.setThreadCount(taskBean.getThreadNum());
		try {
			String flagstr = DataManagerFactory.getDataManager().getScheduleConfig().getLogOutputFlag();
			if ("true".equals(flagstr)) {
				this.logOutputFlag = true;
			}
		} catch (Exception ex) {
			logger.error("", ex);
			this.logOutputFlag = false;
		}

		if (this.taskBean.getScanIntervalTime() > 0) {
			scanInTime = this.taskBean.getScanIntervalTime();
		}

		if (this.taskBean.getIdleSleepTime() > 0) {
			idleTime = this.taskBean.getIdleSleepTime();
		}

		if (logger.isInfoEnabled()) {
			logger.info("scanInTime:" + scanInTime + ",idleTime:" + idleTime);
		}

	}

	@Override
	public void stopProcess() {
		super.stopProcess();

		// 最后输出一次日志
		logOutput();

		this.taskList.clear();
		this.runningTaskList.clear();
		this.maybeRepeatTaskList.clear();

	}

	/**
	 * 主要防止最后一批数据执行耗时过长，另外的线程重新加载数据时导致数据重复加载进来。
	 * 
	 * @param aTask
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected boolean isDealing(T aTask) {
		if (this.maybeRepeatTaskList.size() == 0) {
			return false;
		}
		T[] tmpList = (T[]) this.maybeRepeatTaskList.toArray();
		for (int i = 0; i < tmpList.length; i++) {
			if (this.taskComparator.compare(aTask, tmpList[i]) == 0) {
				this.maybeRepeatTaskList.remove(tmpList[i]);
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取单个任务，注意lock是必须， 否则在maybeRepeatTaskList的数据处理上会出现冲突
	 * 
	 * @return
	 */
	public T getScheduleTaskId() {
		lockFetchID.lock();
		try {
			T result = null;
			while (true) {
				if (this.taskList.size() > 0) {
					result = this.taskList.remove(0); // 按正序处理
				} else {
					return null;
				}
				if (this.isDealing(result) == false) {
					return result;
				} else {
					return null;
				}
			}
		} finally {
			lockFetchID.unlock();
		}
	}

	/**
	 * 获取多个任务
	 * 
	 * @return
	 */
	public List<T> getScheduleTaskIdMulti() {
		//TODO  修改多线程处理任务重复问题
		lockFetchMutilID.lock();
		try {
			if (this.taskList.size() == 0) {
				return null;
			}
			int size = taskList.size() > taskBean.getExecuteNum() ? taskBean.getExecuteNum() : taskList.size();

			List<T> result = new ArrayList<T>();
			int point = 0;
			T tmpObject = null;
			while (point < size && ((tmpObject = this.getScheduleTaskId()) != null)) {
				result.add(tmpObject);
				point = point + 1;
			}
			//TODO 修复多线程数据重复处理问题
			if (result.size() == 0) {
				return null;
			} else {
				this.runningTaskList.addAll(result);
				return result;
			}
		} finally {
			lockFetchMutilID.unlock();
		}
	}

	public void clearAllHasFetchData() {
		this.taskList.clear();
	}

	public boolean isDealFinishAllData() {
		return this.taskList.size() == 0 && this.runningTaskList.size() == 0;
	}

	public boolean isSleeping() {
		return this.isSleeping;
	}

	/**
	 * 装载数据
	 * 
	 * @return
	 */
	protected int loadScheduleData() {
		lockLoadData.lock();
		try {
			if (this.taskList.size() > 0 || this.sts != RUNNING) { // 判断是否有别的线程已经装载过了。
				return this.taskList.size();
			}
			// 在每次数据处理完毕后休眠固定的时间
			try {

				// 休眠时间是否由业务实现类自己计算
				if (this.taskDealBean instanceof ISleepTimeController) {

					if (logger.isInfoEnabled()) {
						logger.info("处理完一批数据后,,自定义计算休眠时间。");
					}
					ISleepTimeController contorller = (ISleepTimeController) this.taskDealBean;
					long invTime = 0;
					if (lastFetchDataTime != null) {
						invTime = System.currentTimeMillis() - lastFetchDataTime.getTime();
					}
					int sleepTime = contorller.getSleepTime(lastSelectTaskCount, invTime);

					if (logger.isInfoEnabled()) {
						logger.info("处理完一批数据,,任务数：" + lastSelectTaskCount + ",处理时长：" + invTime + ",自定义计算获得的休眠时间：" + sleepTime);
					}

					if (sleepTime > this.taskBean.getIdleSleepTime()) {
						sleepTime = this.taskBean.getIdleSleepTime();
					}
					//TODO 验证休眠时间规则,TF要求按照 配置休眠时间-实际运算时间=休眠时间 小于0则不休眠
					if (sleepTime > 0) {
						this.isSleeping = true;
						//TODO 设置休眠时间休眠时间为业务自行计算的时间
						// 修改休眠时间BUG 将idleTime 替换为sleeptime
						Thread.sleep(sleepTime);
						this.isSleeping = false;

						if (logger.isInfoEnabled()) {
							logger.info("处理完一批数据后休眠后恢复");
						}
					}

					if (this.sts != RUNNING) {
						logger.info("调度器状态已终止，停止取数！");
						return taskList.size();
					}

				} else if (scanInTime > 0) {
					if (logger.isInfoEnabled()) {
						logger.info("处理完一批数据后休眠：" + scanInTime);
					}
					this.isSleeping = true;
					Thread.sleep(scanInTime);
					this.isSleeping = false;

					if (logger.isInfoEnabled()) {
						logger.info("处理完一批数据后休眠后恢复");
					}

					if (this.sts != RUNNING) {
						logger.info("调度器状态已终止，停止取数！");
						return taskList.size();
					}
				}
			} catch (Throwable ex) {
				logger.error("休眠时错误", ex);
			}

			putLastRunningTaskList();// 将running队列的数据拷贝到可能重复的队列中

			try {
				//调用业务的方法装载数据
				List<T> tmpList = this.taskDealBean.selectTasks();
				if (tmpList != null) {
					this.taskList.addAll(tmpList);
				}
				lastFetchDataTime = DateUtils.getCurrentDate();
				lastSelectTaskCount = this.taskList.size();
				addFetchNum(taskList.size(), "com.asiainfo.schedule.core.client.ComplexTaskProcessor.loadScheduleData");
				if (taskList.size() <= 0) {
					// 判断当没有数据是否需要退出调度
					if (this.taskDealBean.isFinishNoData() == false) {
						if (idleTime > 0) {
							if (logger.isInfoEnabled()) {
								logger.info("没有读取到需要处理的数据,sleep " + idleTime);
							}
							this.isSleeping = true;
							Thread.sleep(idleTime);
							this.isSleeping = false;
						}
					} else {
						logger.info("没有获取到数据，停止任务调度器！");
						this.stopProcess();
					}
				}
				return this.taskList.size();
			} catch (Throwable ex) {
				logger.error("获取任务数据错误", ex);
			}
			return 0;
		} finally {
			lockLoadData.unlock();
		}
	}

	/**
	 * 将running队列的数据拷贝到可能重复的队列中 防止多线程处理时，最后一批数据处理重复
	 */
	@SuppressWarnings("unchecked")
	public void putLastRunningTaskList() {
		lockFetchID.lock();
		try {
			this.maybeRepeatTaskList.clear();
			if (this.runningTaskList.size() == 0) {
				return;
			}
			T[] tmpList = (T[]) this.runningTaskList.toArray();
			for (int i = 0; i < tmpList.length; i++) {
				this.maybeRepeatTaskList.add(tmpList[i]);
			}
		} finally {
			lockFetchID.unlock();
		}
	}

	public void addFetchNum(long num, String addr) {
		// 取数时输出日志
		long fetchCount = this.statisticsInfo.getFetchDataCount();
		// 第一次取数，或者处理业务超过1分钟输出日志
		if (fetchCount == 0 || (fetchCount % 30) == 0 || (this.statisticsInfo.getDealSpendTime() - lastCostTime) >= 60000) {
			logOutput();
		}

		this.statisticsInfo.addFetchDataCount(1);
		this.statisticsInfo.addFetchDataNum(num);
	}

	private void logOutput() {
		if (!logOutputFlag) {
			return;
		}
		long curDealSus = this.statisticsInfo.getDealDataSucess();// 当前处理的成功工单数量
		long curDealFail = this.statisticsInfo.getDealDataFail();// 当前处理的失败工单数量
		long curCostTime = this.statisticsInfo.getDealSpendTime(); // 总耗时（ms）
		long curDealCount = curDealSus + curDealFail;// 当前处理的业务总量
		if (curDealCount == 0) {
			return;
		}
		long totalcnt = -1;
		try {
			totalcnt = this.taskDealBean.getTaskDataTotal();
		} catch (Exception e) {
			logger.error("", e);
		}
		if (totalcnt < 0) {
			totalcnt = this.statisticsInfo.getFetchDataNum();
		} else if (totalcnt >= 0 && this.getTaskBean().getTaskType().equalsIgnoreCase(TaskType.tf.name())) {
			totalcnt = totalcnt + curDealCount;
		}
		StringBuilder logMessage = new StringBuilder("");
		logMessage.append(this.param.getTaskCode()).append(LoggerUtils.getColumnSeperator());
		logMessage.append(this.param.getTaskItem()).append(LoggerUtils.getColumnSeperator());
		logMessage.append(totalcnt).append(LoggerUtils.getColumnSeperator());
		logMessage.append(curDealCount - (lastDealFail + lastDealSus)).append(LoggerUtils.getColumnSeperator());
		logMessage.append(curDealCount).append(LoggerUtils.getColumnSeperator());
		logMessage.append(curDealFail - lastDealFail).append(LoggerUtils.getColumnSeperator());
		logMessage.append(this.statisticsInfo.getDealDataFail()).append(LoggerUtils.getColumnSeperator());
		logMessage.append(StringUtils.isNotBlank(this.param.getRegionCode()) && null != param.getRegionCode() ? param.getRegionCode() : "").append(
				LoggerUtils.getColumnSeperator());
		logMessage.append(curCostTime - lastCostTime).append(LoggerUtils.getColumnSeperator());
		logMessage.append(LoggerUtils.formatCreateDate(new Date()));
		if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
			LoggerUtils.addLog(Constants.ABG_MON_BUSI_LOG, logMessage.toString());
		} else {
			LoggerUtils.addLog("MYSQL.ABG_MON_BUSI_LOG", logMessage.toString());
		}
		lastCostTime = curCostTime;
		lastDealFail = curDealFail;
		lastDealSus = curDealSus;
	}

	public void addSuccessNum(long num, long spendTime, String addr) {
		this.statisticsInfo.addDealDataSucess(num);
		this.statisticsInfo.addDealSpendTime(spendTime);
	}

	public void addFailNum(long num, long spendTime, String addr) {
		this.statisticsInfo.addDealDataFail(num);
		this.statisticsInfo.addDealSpendTime(spendTime);
	}

	class MYComparator implements Comparator<T> {
		Comparator<T> comparator;

		public MYComparator(Comparator<T> aComparator) {
			this.comparator = aComparator;
		}

		@Override
		public int compare(T o1, T o2) {
			statisticsInfo.addOtherCompareCount(1);
			return this.comparator.compare(o1, o2);
		}

	}

	@Override
	protected void excute() throws Exception {
		long startTime = 0;
		long sequence = 0;
		List<T> executeTask = null;
		while (true) {
			if (this.sts != RUNNING) { // 停止队列调度
				logger.info("任务执行状态已终止，停止队列调度！");

				return;
			}

			// 加载调度任务
			executeTask = this.getScheduleTaskIdMulti();

			if (executeTask == null) {
				// 第一次加载数据时获取任务处理数据总数
				if (isFirstLoad.booleanValue() == true) {
					synchronized (this) {
						if (isFirstLoad.booleanValue() == true) {
							if (this.sts != RUNNING) {

								if (logger.isInfoEnabled()) {
									logger.info("执行状态已终止，停止执行！");
								}

								return;
							}
							// 调用初始化方法
							try {
								if (logger.isInfoEnabled()) {
									logger.info("开始执行初始化方法！！");
								}

								taskDealBean.init(this.getTaskDealParam());

								if (logger.isInfoEnabled()) {
									logger.info("初始化方法执行完成！！");
								}
								long taskTotal = this.taskDealBean.getTaskDataTotal();
								this.statisticsInfo.setTaskDataTotal(taskTotal);
							} catch (Exception ex) {
								logger.error("初始化失败！！", ex);
								this.sts = STOPPING;
								throw ex;
							}
							isFirstLoad = Boolean.FALSE;
						}
					}
				}
				this.loadScheduleData();
				continue;
			}
			try {
				// 运行相关的程序

				startTime = System.currentTimeMillis();
				sequence = sequence + 1;

				this.taskDealBean.execute(executeTask);
				addSuccessNum(executeTask.size(), System.currentTimeMillis() - startTime,
						"com.asiainfo.schedule.core.client.ComplexTaskProcessor.run");

			} catch (Throwable ex) {
				logger.error("Task 任务数量:" + executeTask.size() + " 处理失败", ex);
				addFailNum(executeTask.size(), System.currentTimeMillis() - startTime,
						"com.asiainfo.schedule.core.client.ComplexTaskProcessor.run");

			} finally {
				this.runningTaskList.removeAll(executeTask);
			}
		}
	}

	@Override
	public String getDealDescription() {
		return this.statisticsInfo.getDealDescription();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(super.toString());
		s.append(",").append(this.getDealDescription());
		return s.toString();
	}
}

class StatisticsInfo {
	private long taskDataTotal = -1; // 处理任务总数
	private int threadCount = 0;
	private AtomicLong fetchDataNum = new AtomicLong(0);// 读取的总数据量
	private AtomicLong fetchDataCount = new AtomicLong(0);// 读取次数
	private AtomicLong dealDataSucess = new AtomicLong(0);// 处理成功的数据量
	private AtomicLong dealDataFail = new AtomicLong(0);// 处理失败的数据量
	private AtomicLong dealSpendTime = new AtomicLong(0);// 处理总耗时,没有做同步，可能存在一定的误差
	private AtomicLong otherCompareCount = new AtomicLong(0);// 特殊比较的次数

	public int getThreadCount() {
		return threadCount;
	}

	public long getTaskDataTotal() {
		return taskDataTotal;
	}

	public long getFetchDataNum() {
		return fetchDataNum.get();
	}

	public long getFetchDataCount() {
		return fetchDataCount.get();
	}

	public long getDealDataSucess() {
		return dealDataSucess.get();
	}

	public long getDealDataFail() {
		return dealDataFail.get();
	}

	public long getDealSpendTime() {
		return dealSpendTime.get();
	}

	public long getOtherCompareCount() {
		return otherCompareCount.get();
	}

	public void addFetchDataNum(long value) {
		this.fetchDataNum.addAndGet(value);
	}

	public void addFetchDataCount(long value) {
		this.fetchDataCount.addAndGet(value);
	}

	public void addDealDataSucess(long value) {
		this.dealDataSucess.addAndGet(value);
	}

	public void addDealDataFail(long value) {
		this.dealDataFail.addAndGet(value);
	}

	public void addDealSpendTime(long value) {
		this.dealSpendTime.addAndGet(value);
	}

	public void addOtherCompareCount(long value) {
		this.otherCompareCount.addAndGet(value);
	}

	public void setTaskDataTotal(long value) {
		this.taskDataTotal = value;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public String getDealDescription() {
		return "TaskDataTotal:" + this.taskDataTotal + ",FetchDataCount:" + this.fetchDataCount + ",FetchDataNum:"
				+ this.fetchDataNum + ",DealDataSucess:" + this.dealDataSucess + ",DealDataFail:" + this.dealDataFail + ",DealSpendTime:"
				+ this.dealSpendTime + ",otherCompareCount:" + this.otherCompareCount + ",ThreadCount:" + this.threadCount;
	}
}
