package com.asiainfo.schedule.core.client;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.schedule.core.client.interfaces.ISingleTaskDeal;
import com.asiainfo.schedule.core.client.interfaces.ISingleTaskStop;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.utils.CommonUtils;

/**
 * 单次任务运行处理实现
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年9月2日
 * @modify
 */
public class SingleTaskWrapper extends TaskProcessor {

	private static final transient Logger logger = LoggerFactory.getLogger(SingleTaskWrapper.class);

	ISingleTaskDeal taskDeal;

	SingleTaskWrapper(TaskBean taskBean, String taskItem, String jobId, String itemJobId,Map<String,String> _param, String serverId) throws Exception {
		super(taskBean, taskItem, jobId, itemJobId, _param,serverId);
		if (CommonUtils.isBlank(taskBean.getProcessClass())) {
			throw new Exception("业务实现类不能为空，必须实现接口ISingleTaskDeal！");
		}
		Object obj = Class.forName(taskBean.getProcessClass()).newInstance();
		if (!(obj instanceof ISingleTaskDeal)) {
			throw new Exception("任务类型为single，业务实现类必须实现接口ISingleTaskDeal！" + taskBean.getProcessClass());
		}
		taskDeal = (ISingleTaskDeal) obj;
	}

	@Override
	protected void excute() throws Exception {
		taskDeal.init(this.getTaskDealParam());
		this.taskDeal.execute();
	}

	@Override
	public void stopProcess() {
		super.stopProcess();
		
		if(taskDeal instanceof ISingleTaskStop){
			logger.error("简单任务类型实现了ISingleTaskStop接口，执行业务的stopTask方法！");
			ISingleTaskStop ss = (ISingleTaskStop)taskDeal;
			ss.stopTask();
			logger.error("执行业务的stopTask方法结束！");
			return;
		}
		
		logger.warn("简单任务类型执行线程终止操作！");
		for (Thread t : threadList) {
			try {
				// singletask，业务单线程执行。当出现故障任务转移时，必须保证线程终止防止重复处理的情况。不得已而为
				t.stop();
			} catch (Exception ex) {
				logger.error("线程终止异常", ex);
			}
		}

	}

	@Override
	public String getDealDescription() {
		return "processing";
	}

	public static void main(String[] args) {
		try {
			Object obj = Class.forName("com.ai.ac.core.bill.task.basedata.DealBaseDataTask").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
