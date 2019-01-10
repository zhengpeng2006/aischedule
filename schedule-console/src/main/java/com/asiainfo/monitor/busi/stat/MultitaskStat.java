package com.asiainfo.monitor.busi.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.AIMonRootConfig;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public abstract class MultitaskStat {

	private static transient Log log = LogFactory.getLog(MultitaskStat.class);
	
	protected List<Future> futureList=new ArrayList<Future>();
	
	protected ExecutorService executor=null;
	
	protected AtomicBoolean scanover=new AtomicBoolean(false);
	
	protected Stack summaryStack=new Stack();
	
	private Object[] ids=null;
	
	private static String DEFAULT_OUT=AIMonRootConfig.getInstance().getValueByName("JmxSummayTime");
	
	private int timeOut=15;//超时时间:秒
	
	private long startTime=System.currentTimeMillis();
	
	protected String condition=null;
	
	public MultitaskStat(int threadCount,int timeOut,Object[] ids,String condition) {
		executor=Executors.newFixedThreadPool(threadCount);
		
		if (StringUtils.isNotBlank(DEFAULT_OUT))
			this.timeOut=Integer.parseInt(DEFAULT_OUT);
		if (timeOut>0)
			this.timeOut=timeOut;
		this.ids=ids;
		this.condition=condition;
		try{
			init();
		}catch(Exception e){
			// "初始化异常:"
			log.error(AIMonLocaleFactory.getResource("MOS0000091")+e.getMessage());
		}
	}
	
	
	
	/**
	 * 初始化
	 * @throws Exception
	 */
	public void init() throws Exception {
		if (ids == null || ids.length == 0) {
			return;
		}
		int n = ids.length;		
		try {
			for (int i = 0; i < n; i++) {
				Future submit = executor.submit(new SummaryCallable(String.valueOf(ids[i])));
				futureList.add(submit);
			}
			scanFutureList();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		return;
	}
	
	/**
	 * 另起线程扫描FutureList
	 *
	 */
	private void scanFutureList(){
		FutureTask futureTask = new FutureTask(new Callable() {
			public Boolean call() throws Exception {
				try {
					while(futureList.size()>0){
						long difftime=System.currentTimeMillis()-startTime;
						for (int i=0;i<futureList.size();i++){
							if (futureList.get(i).isDone()){
								Future itemFuture=futureList.remove(i);
								summaryStack.push(itemFuture.get());
							}else if (timeOut>0){
								if (difftime>timeOut*1000){
									futureList.remove(i);
								}
							}
						}
					}
					scanover.compareAndSet(false,true);
				} catch (Exception e) {
					return false;
				}finally{
					if (executor!=null){
						executor.shutdownNow();
					}
				}
				return true;
			}
		});
		futureTask.run();
	}
	
	/**
	 * 统计结果
	 * @return
	 * @throws Exception
	 */
	public Map getSummary() throws Exception{		
		FutureTask futureTask = new FutureTask(new Callable() {
			public Map call() throws Exception{
				Map result = new HashMap();
				while (summaryStack.size()>0 || !scanover.get()){
					List request = (List)summaryStack.pop();
					summary(request,result);
				}
				
				return result;
			}
		});
		futureTask.run();
		return (Map)futureTask.get();
	}
	
	/**
	 * 统计方法
	 * @param reqVector
	 * @param summaryMap
	 * @throws Exception
	 */
	public abstract void summary(List reqVector,Map summaryMap) throws Exception;
	
	public abstract List getSummary(String id) throws Exception;
	
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
