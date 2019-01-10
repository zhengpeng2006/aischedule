package com.asiainfo.monitor.busi.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public abstract class MultitaskProcess {

	private static transient Log log = LogFactory.getLog(MultitaskProcess.class);
	
	private String[] ids=null;
	
	private int threadCount;
	
	private int timeOut;
	
	public MultitaskProcess(int threadCount,int timeOut,String[] ids){
		this.threadCount=threadCount;
		this.timeOut=timeOut;
		this.ids=ids;
	}
	/**
	 * 执行多个任务,例如启动多台应用视为多个任务
	 * @param threadCount
	 * @param timeOut
	 * @param servers
	 * @return
	 * @throws Exception
	 */
	public List operate() throws Exception {
		if (ids == null || ids.length == 0) {
			return null;
		}
		int n = ids.length;
		List result=new ArrayList(n);
		if (threadCount < 1) {
			threadCount = 1;
		}
		ExecutorService exe = Executors.newFixedThreadPool(threadCount);
		try {
			List<Future> list = new ArrayList<Future>();
			for (int i = 0; i < n; i++) {
				Future submit = exe.submit(new SummaryCallable(ids[i]));
				list.add(submit);
			}
			if (timeOut < 1) {
				/** no timeout */
				for (int i = 0; i < n; i++) {
					result.addAll((List)list.get(i).get());
				}
			} else {
				/** with timeout */
				for (int i = 0; i < n; i++) {
					try {
						result.addAll((List)list.get(i).get(timeOut, TimeUnit.SECONDS));
					} catch (Exception e) {
						// "应用["+ids[i]+"]取SQL统计信息超时"
						log.error(AIMonLocaleFactory.getResource("MOS0000090", ids[i]));
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
		return result;
	}
	
	
	/**
	 * 执行单个任务,例如启动一台应用视为单个任务
	 * @param server
	 * @return
	 * @throws Exception
	 */
	public abstract List getSummary(String id) throws Exception;
	
	/**
	 * 服务器控制,执行SH（Shell）脚本
	 * @author Guocx
	 *
	 */
	private  class SummaryCallable implements Callable{
		
		private String id;
		
		public SummaryCallable(String id) {
			this.id=id;
		}

		public List call() throws Exception {
			List result = null;
			try {
				result=getSummary(id);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return result;
		}
	}
}
