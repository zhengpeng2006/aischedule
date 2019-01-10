package com.asiainfo.monitor.busi.common.impl;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.exe.task.interfaces.AbstractTaskProcess;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.TaskWorker;
import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.common.interfaces.IWorker;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtn;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.interfaces.ITaskContext;

public class ChannelExecutor {

	private final static transient Log log=LogFactory.getLog(ChannelExecutor.class);
	
	
	private static ChannelExecutor instance=null;
	
	private ThreadPoolExecutor executor;
	
	private ChannelExecutor() {
		super();
		executor=new ThreadPoolExecutor(1, 30, 60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(5),new ThreadPoolExecutor.CallerRunsPolicy());
		// TODO 自动生成构造函数存根
	}
	
	public static ChannelExecutor getInstance() {
		if (instance == null) {
			synchronized (ChannelExecutor.class) {
				if (instance == null) {
					instance = new ChannelExecutor();
				}
			}
		}
		return instance;
	}
	
	public void work(IWorker worker) {
		
		try{
			executor.execute(new TaskWorker(worker));
		}catch(Exception e){
			log.error(AIMonLocaleFactory.getResource("MOS0000049")+e.getMessage());
		}
		
	}
	
	public void runTask(ITaskContext[] tasks){
		try {
			int n = tasks.length;
			List<Future> list = new ArrayList<Future>();
			for (int i = 0; i < n; i++) {
				Future submit = executor.submit(new TaskRunnable(tasks[i]));
				list.add(submit);
			}
			/** no timeout */
			for (int i = 0; i < n; i++) {
				list.get(i).get();					
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
//			throw e;
		} finally {			
//			executor.shutdown();
		}
	}
	
	class TaskRunnable extends AbstractTaskProcess implements Runnable {
		
		private ITaskContext task;

		public TaskRunnable(ITaskContext task) {
			this.task = task;
		}

		/**
		 * 执行任务
		 */
		public void run()  {
			try {			
				this.execute(task);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				
			}
		}
		
		/**
		 * 任务后继处理方法
		 * @param task
		 * @param rtn
		 * @return
		 * @throws Exception
		 */
		public void process(ITaskContext task) throws Exception{
			ITaskRtn[] objMutilRtn =(ITaskRtn[])task.getRtnModel().getRtns().toArray(new TaskRtn[0]);
			if (objMutilRtn != null && objMutilRtn.length != 0) {
				for (int i = 0; i < objMutilRtn.length; i++) {
					String infoValue=objMutilRtn[i].getMsg();
					String[] vars=com.ai.appframe2.util.StringUtils.getParamFromString(infoValue, "{", "}");
					if (vars!=null && vars.length>0){
						for (int pCount=0;pCount<vars.length;pCount++){
							KeyName itemVar=task.findParameter(vars[pCount].toUpperCase());
							if (itemVar!=null){
								infoValue=StringUtils.replace(infoValue,"{"+vars[pCount]+"}",itemVar.getKey());
							}
						}
					}
					objMutilRtn[i].setMsg(infoValue);
				}
			}
		}
	}

}
