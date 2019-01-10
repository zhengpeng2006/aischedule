package com.asiainfo.monitor.interapi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.interapi.ServerControlInfo;
import com.asiainfo.monitor.interapi.config.CallResult;
import com.asiainfo.monitor.tools.common.interfaces.IKeyName;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.interfaces.ITaskContext;
import com.asiainfo.monitor.tools.util.TypeConst;

public class ConcurrentServerControl {
	public static final String SUCCESS = "	OPERATING SUCCESS ";
	public static final String FAIL = " OPERATING FAIL : Exception-";
	public static final String TIMEOUT= " OPERATING TIMEOUT ";
	
	private static final int	DEFAULT_THREAD_COUNT = 2;
	
	private static transient Log log = LogFactory.getLog(ConcurrentServerControl.class);

	/**
	 * 执行多个任务,例如启动多台应用视为多个任务
	 * @param threadCount
	 * @param timeOut
	 * @param servers
	 * @return
	 * @throws Exception
	 */
	public static CallResult[] operateServer(int threadCount, int timeOut,ITaskContext[] tasks) throws Exception {
		if (tasks == null || tasks.length == 0) {
			return null;
		}
		int n = tasks.length;
		CallResult[] ret = new CallResult[n];
		if (threadCount < 1) {
			threadCount = DEFAULT_THREAD_COUNT;
		}
		ExecutorService exe = Executors.newFixedThreadPool(threadCount);
		try {
			List<Future<CallResult>> list = new ArrayList<Future<CallResult>>();
			for (int i = 0; i < n; i++) {
				Future<CallResult> submit = exe.submit(new ServerControl(tasks[i]));
				list.add(submit);
			}
			if (timeOut < 1) {
				/** no timeout */
				for (int i = 0; i < n; i++) {
					ret[i] = list.get(i).get();					
				}
			} else {
				/** with timeout */
				for (int i = 0; i < n; i++) {
					try {
						ret[i]= list.get(i).get(timeOut, TimeUnit.SECONDS);						
					} catch (Exception e) {
						ret[i]=new CallResult();
						if (tasks[i].getParameter()!=null && tasks[i].getParameter().size()>0){
							IKeyName hostParam=tasks[i].findParameter(TypeConst.HOST+TypeConst._NAME);
							if (hostParam!=null){
								ret[i].setRemark(hostParam.getKey() + TIMEOUT);
							}
						}
						ret[i].setResult(CallResult.FAIL);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {			
			if (exe != null) {
				exe.shutdownNow();
			}
		}
		return ret;
	}
	

	/**
	 * 执行单个任务,例如启动一台应用视为单个任务
	 * @param server
	 * @return
	 * @throws Exception
	 */
	public static CallResult operateServer(ITaskContext task) throws Exception {
		if (task == null) {
			return null;
		}
		CallResult ret=null;
		StringBuilder descMsg=new StringBuilder("");
		try {
			ret=new CallResult();
			
			if (task.getParameter()!=null && task.getParameter().size()>0){
				IKeyName hostParam=task.findParameter(TypeConst.HOST+TypeConst._NAME);
				if (hostParam!=null){
					// " 主机:"
					descMsg.append(AIMonLocaleFactory.getResource("MOS0000276")+hostParam.getKey());
				}
				IKeyName serverParam=task.findParameter(TypeConst.SERVER+TypeConst._NAME);
				if (serverParam!=null){
					// " 应用:"
					descMsg.append(AIMonLocaleFactory.getResource("MOS0000277")+serverParam.getKey());
				}
			}
			
			task.action();
			// " 操作成功"
			descMsg.append(AIMonLocaleFactory.getResource("MOS0000275"));
			ret.setResult(CallResult.SUCCESS);
			ret.setRemark(descMsg.toString());			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ret.setRemark(descMsg.toString() + FAIL + e.getMessage());
			ret.setResult(ret.FAIL);
		} finally {			
		}
		return ret;
	}
	
	/**
	 * 服务器控制,执行SH（Shell）脚本
	 * @author Guocx
	 *
	 */
	private static class ServerControl implements Callable<CallResult> {
		private ITaskContext task;
		
		public ServerControl(ITaskContext task) {
			this.task = task;
		}

		public CallResult call() throws Exception {
			CallResult result = null;
			try {
				result=operateServer(task);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				result.setResult(result.FAIL);
				if (task.getParameter()!=null && task.getParameter().size()>0){
					IKeyName hostParam=task.findParameter(TypeConst.HOST+TypeConst._NAME);
					if (hostParam!=null){
						result.setRemark(hostParam.getKey() + FAIL + e.getMessage());
					}
				}
				
			}
			return result;
		}
	}

	/**
	 * 检查版本
	 * @param threadCount
	 * @param timeout
	 * @param runnableTask
	 * @return
	 * @throws Exception
	 */
	public static ServerControlInfo[] parallelCheck(int threadCount,int timeout, ServerControlInfo[] runnableTask) throws Exception {
		List rtn = new ArrayList();
		ExecutorService exec = Executors.newFixedThreadPool(threadCount);
		try {
			CompletionService<String> serv = new ExecutorCompletionService<String>(exec);
			for (int i = 0; i < runnableTask.length; i++) {
				serv.submit(new UrlCallable(timeout, runnableTask[i]));
			}

			for (int index = 0; index < runnableTask.length; index++) {
				Future task = serv.poll(timeout + 5, TimeUnit.SECONDS);
				if (task == null) {
					runnableTask[index].setStatus("TIMEOUT");
					rtn.add(runnableTask[index]);
				} else {
					ServerControlInfo item = (ServerControlInfo) task.get();
					rtn.add(item);
				}
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			// 关闭线程池
			if (exec != null) {
				exec.shutdownNow();
			}
		}
		return (ServerControlInfo[]) rtn.toArray(new ServerControlInfo[0]);
	}

	/**
	 * 检查版本Url
	 * @author Guocx
	 *
	 */
	public static class UrlCallable implements Callable {
		private int timeout = 3;

		private ServerControlInfo item = null;

		public UrlCallable(int timeout, ServerControlInfo item) {
			this.timeout = timeout;
			this.item = item;
		}

		public Object call() throws Exception {

			try {
				String info = HttpClientUtil.curl(item.getUrl(), this.timeout);
				item.setStatus("OK");
				item.setInfo(info);
			} catch (Exception ex) {
				item.setStatus("EXCEPTION");
			}
			return item;
		}

	}
}
