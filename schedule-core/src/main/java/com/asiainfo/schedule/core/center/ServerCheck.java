package com.asiainfo.schedule.core.center;

import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.po.FaultBean;
import com.asiainfo.schedule.core.po.ServerBean;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskItemJobBean;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.Constants.RunSts;
import com.asiainfo.schedule.core.utils.Constants.ServerType;
import com.asiainfo.schedule.core.utils.Constants.TaskAssignType;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.core.utils.OutsideService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 服务检查处理实现
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月27日
 * @modify
 */
public class ServerCheck implements Callable<Boolean> {
	private static final Logger logger = LoggerFactory.getLogger(ServerCheck.class);
	String serverId;
	String managerId;
	String faultProcessFlag;

	public ServerCheck(String serverId, String managerId, String faultProcessFlag) {
		this.serverId = serverId;
		this.managerId = managerId;
		this.faultProcessFlag = faultProcessFlag;
	}

	/**
	 * 检查任务执行服务进程心跳，心跳丢失则进行故障处理。
	 * 
	 * @return
	 */
	@Override
	public Boolean call() throws Exception {

		DataManagerFactory dmf = DataManagerFactory.getDataManager();

		// 获取执行进程心跳信息
		ServerBean serverHeart = dmf.getServerRegistry(serverId, ServerType.processor);
		// 没有心跳或者心跳超过死亡间隔，则进入故障处理
		if (serverHeart == null
				|| (System.currentTimeMillis() - serverHeart.getHeartbeatTime().getTime()) > dmf.getScheduleConfig().getDeadInterval()) {
			// 清除失效的老数据
			if (serverHeart != null) {
				dmf.serverUnRegist(serverId, ServerType.processor);
			}

			// 当前服务进程是否存在处理的任务
			List<TaskItemJobBean> tasks = dmf.getAllTaskJobsByServerId(serverId);
			if (tasks == null || tasks.size() == 0) {
				if (logger.isDebugEnabled()) {
					logger.debug("任务执行进程没有可处理的任务，结束进程状态检测！" + serverId);
				}

				return Boolean.TRUE;
			}

			String curTime = DateUtils.formatYYYYMMddHHmmss(DateUtils.getCurrentDate());
			FaultBean faultBean = dmf.getFaultProcessBean(serverId);
			if (faultBean == null) {
				faultBean = new FaultBean();
				faultBean.setFaultServerId(serverId);
				faultBean.setFaultTime(curTime);
				faultBean.setProcessCount(1);
				faultBean.setProcessType("A");
				faultBean.setMsg("故障处理：心跳出现异常！");
				dmf.setFaultProcessBean(faultBean);
				return Boolean.TRUE;
			}

			// 第一次心跳出现故障时间间隔，要大于配置的死亡间隔时间才开始故障处理！！！以防网络闪断情况下心跳恢复。
			long firstFaultTime = DateUtils.parseYYYYMMddHHmmss(faultBean.getFaultTime()).getTime();
			long inteval = DateUtils.getCurrentDate().getTime() - firstFaultTime;
			if (inteval < dmf.getScheduleConfig().getDeadInterval()) {
				logger.error("任务执行进程服务【" + serverId + "】心跳出现异常，但未达到配置的进程死亡间隔时间。此次不处理！" + inteval);
				return Boolean.FALSE;
			}

			logger.error("达到配置的进程死亡间隔时间,开始故障处理。" + inteval);

			int size = tasks.size();
			for (Iterator<TaskItemJobBean> it = tasks.iterator(); it.hasNext();) {
				TaskItemJobBean itemJob = it.next();
				String runSts = dmf.getTaskRunSts(itemJob.getTaskCode());
				if (RunSts.stop.name().equals(runSts)) {
					logger.error("任务执行进程服务【" + serverId + "】出现故障,运行的任务【" + itemJob.getItemJobId() + "】状态已停止，移除。");
					dmf.finishTaskItemJob(serverId, itemJob.getTaskCode(), itemJob.getTaskItemId(), itemJob.getTaskJobId(),
							itemJob.getItemJobId());
					continue;
				}
				String cur = dmf.getTaskItemServerId(itemJob.getTaskCode(), itemJob.getTaskItemId());
				if (!serverId.equals(cur)) {
					logger.error("任务执行进程服务【" + serverId + "】心跳出现异常,关联的任务项【" + itemJob.getItemJobId() + "】,被重新分派到其他server，转移任务。");

					dmf.finishTaskItemJob(serverId, itemJob.getTaskCode(), itemJob.getTaskItemId(), itemJob.getTaskJobId(),
							itemJob.getItemJobId());
					if (!CommonUtils.isBlank(cur)) {
						TaskItemJobBean newJob = dmf.transformTaskItemJob(itemJob, cur);
						logger.error("成功转移任务项【" + newJob.getItemJobId() + "】至：" + cur);
					}
					it.remove();
					size--;
				}
			}
			if (logger.isInfoEnabled() && size > 0) {
				for (TaskItemJobBean j : tasks) {
					logger.info("故障server：" + serverId + ",正运行的任务：" + j.getItemJobId());
				}
			}

			// 故障自动切换开关关闭，直接返回。
			if (!"on".equals(faultProcessFlag)) {
				return Boolean.TRUE;
			}

			// 如果已经转人工了不再处理
			if (faultBean.getProcessType().equals("M") || faultBean.getProcessType().equals("F")) {
				if (logger.isDebugEnabled()) {
					logger.debug("故障任务执行进程已经处理成功或转人工处理，结束处理！" + serverId);
				}
				return Boolean.TRUE;
			} else if (faultBean.getProcessCount() >= dmf.getScheduleConfig().getFaultProcessCount()) {
				// 当故障处理次数超过配置阈值转人工处理，防止异常情况下一直重复处理
				logger.error("任务执行进程服务[" + serverId + "]故障处理次数超过配置阈值，转人工处理。");
				faultBean.setMsg("故障自动处理失败转人工处理，失败原因：" + faultBean.getMsg());
				faultBean.setProcessType("M");
				dmf.setFaultProcessBean(faultBean);
				return Boolean.TRUE;
			}
			int p = faultBean.getProcessCount();
			faultBean.setProcessCount(++p);
			faultBean.setLastProcessTime(curTime);

			boolean startFail = false;
			try {
				if (logger.isInfoEnabled()) {
					logger.info("任务执行进程服务【" + serverId + "】心跳出现异常，重新启动处理...");
				}
				OutsideService.startServer(serverId);
				faultBean.setMsg("故障处理：重新启动进程成功！");
				faultBean.setProcessType("F");
			} catch (Exception ex) {
				logger.error("重启进程异常", ex);
				faultBean.setMsg("故障处理：重新启动进程出现异常：" + CommonUtils.getExceptionMesssage(ex, 1000));
				startFail = true;
			}

			if (!startFail) {
				dmf.setFaultProcessBean(faultBean);
				return Boolean.FALSE;
			}

			if (serverId.endsWith("_bak")) {
				// TODO
				logger.error("备用任务服务进程心跳出现异常，不再切换。开始告警！！" + serverId);
				return Boolean.TRUE;
			}

			// 重启故障进程失败
			try {
				String bakServerId = "";
				if (TaskAssignType.Manual.name().equals(dmf.getScheduleConfig().getTaskAssignType())) {
					if (logger.isInfoEnabled()) {
						logger.info("任务人工分派模式，故障处理方式默认为备份模式。开始把任务切换到备机。。。");
					}
					bakServerId = createBakServerCode(serverId);
					OutsideService.startServer(bakServerId);

					dmf.setTaskServerRunSts(bakServerId, RunSts.start);
					faultBean.setBakServerId(bakServerId);

					// 启用备份server成功，开始转移任务。对配置到故障server的任务项迁移到备用server
					Map<String, List<String>> r = dmf.getAllTaskItemsByServerId(serverId);
					if (r.size() > 0) {
						for (String code : r.keySet()) {
							TaskBean task = dmf.getTaskInfoByTaskCode(code);
							// 自动转移
							if ("A".equals(task.getFaultProcessMethod())) {
								for (String item : r.get(code)) {
									dmf.assignServer2TaskItem(code, item, bakServerId);
									if (logger.isInfoEnabled()) {
										logger.info("成功迁移任务【" + code + "," + item + "】到备用server:" + bakServerId);
									}
								}
							}
						}
					}
					// 当前正在运行的任务迁移到备用server
					if (tasks != null && tasks.size() > 0) {
						for (TaskItemJobBean itemJob : tasks) {
							TaskBean task = dmf.getTaskInfoByTaskCode(itemJob.getTaskCode());
							// 自动转移
							if ("A".equals(task.getFaultProcessMethod())) {

								dmf.finishTaskItemJob(serverId, itemJob.getTaskCode(), itemJob.getTaskItemId(), itemJob.getTaskJobId(),
										itemJob.getItemJobId());
								TaskItemJobBean newJob = dmf.transformTaskItemJob(itemJob, bakServerId);

								if (logger.isInfoEnabled()) {
									logger.info("成功迁移运行中任务流水【" + newJob.getItemJobId() + "】到备用server:" + bakServerId);
								}
							}
						}
					}
				} else {
					throw new Exception("暂不支持的故障处理方式！" + dmf.getScheduleConfig().getTaskAssignType());
				}

				faultBean.setProcessType("F");
				faultBean.setMsg("故障处理成功。");
			} catch (Exception ex) {
				logger.error("故障处理失败。" + serverId, ex);
				faultBean.setMsg("故障处理失败。" + CommonUtils.getExceptionMesssage(ex, 1000));
			}
			dmf.setFaultProcessBean(faultBean);

		} else {

			FaultBean faultBean = dmf.getFaultProcessBean(serverId);
			if (faultBean != null && CommonUtils.isBlank(faultBean.getBakServerId())) {
				if (logger.isInfoEnabled()) {
					logger.info("任务执行进程【" + serverId + "】心跳恢复正常,故障时未执行备机切换操作，直接执行故障恢复操作！！");
				}
				dmf.faultRecovery(serverId);
			}
		}

		return Boolean.TRUE;
	}

	/**
	 * 在备机上创建备用,调用监控提供的接口
	 * 
	 * @return
	 */
	String createBakServerCode(String serverId) throws Exception {
		OutsideService.createServerBak(serverId);
		return serverId + "_bak";
	}

}
