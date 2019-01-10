package com.asiainfo.monitor.busi.asyn.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;


public abstract class AsynOperation {

//	private static int DEFAULT_TIMEOUT=5;
	protected static transient Log log=LogFactory.getLog(AsynOperation.class);
	
	public AsynOperation(){
//		DEFAULT_TIMEOUT=Integer.parseInt(AIMonRootConfig.getInstance().getValueByName("JmxInvokeTime"));
	}
	
	public List asynOperation(int threadCount,int timeout,Object[] datas,long seconds) throws Exception {
		List result = new ArrayList();
		
		//创建线程池, threadCount为线程数
		ExecutorService exec = Executors.newFixedThreadPool(threadCount);
		try {
			CompletionService<String> serv = new ExecutorCompletionService<String>(exec);
			for (int i = 0; i < datas.length; i++) {
				OperateCallable callable = takeOperateCallable(datas[i]);
				serv.submit(callable);
			}
			
			Future task = null;
			
			for (int index = 0; index < datas.length; index++) {
				if (timeout<0){
					//获取并移除表示下一个已完成任务的Future, 如果目前不存在这样的任务则等待
					task = serv.take();
				}else{
					//获取并移除表示下一个已完成任务的Future, 如果目前不存在这样的任务则等待指定时间
					task = serv.poll(timeout, TimeUnit.SECONDS);
				}
				if (task == null) {
					// 操作任务为空
					log.error(AIMonLocaleFactory.getResource("MOS0000247") + "[app:"+datas[index]+"]");
				} else {
					SimpleResult item = (SimpleResult) task.get();
					result.add(item);
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
		return result;
	}
	
	public abstract OperateCallable takeOperateCallable(Object data); 
	
	
}
