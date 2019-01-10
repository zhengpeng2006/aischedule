package com.asiainfo.monitor.busi.exe.task.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.common.abo.ivalues.IBOABGMonLRecordValue;
import com.asiainfo.common.abo.ivalues.IBOABGMonWTriggerValue;
import com.asiainfo.monitor.tools.model.interfaces.ISmsState;



public class TaskResultQueueManager {

	private transient final static Log log = LogFactory.getLog(TaskResultQueueManager.class);
	
	private static String RECORD="RECORD";
	
	private static String TRIGER="TRIGER";
	
	private static String SMS="SMS";
	
	private int maxnumRecordQueueSize=5000;//任务结果队列最大值
	
	private int maxnumTrigerQueueSize=5000;//预警结果队列最大值
	
	private int maxnumSmsQueueSize=5000;//短信结果队列最大值
	
	private ReentrantLock recordLock;//任务结果锁
	
	private ReentrantLock trigerLock;//预警结果锁
	
	private ReentrantLock smsLock;//短信结果锁
	
	private Condition hasRecordResult;//有任务结果
	
	private Condition hasTrigerResult;//有预警结果
	
	private Condition hasSmsResult;//有短信结果
	
	private QueueChecker recordChecker;//任务结果队列检查
	
	private QueueChecker trigerChecker;//预警记录检查
	
	private QueueChecker smsChecker;//短信结果记录检查
	
	private BlockingQueue recordQueue;//结果记录队列
	
	private BlockingQueue trigerQueue;//预警队列
	
	private BlockingQueue smsQueue;//短信队列
	
	private AtomicInteger recordCounter;
	
	private boolean trace=false;
	
	
	
	public TaskResultQueueManager(int maxnumRecordQueueSize,int maxnumTrigerQueueSize,int maxnumSmsQueueSize){
		this.maxnumRecordQueueSize=maxnumRecordQueueSize;
		this.maxnumTrigerQueueSize=maxnumTrigerQueueSize;
		this.maxnumSmsQueueSize=maxnumSmsQueueSize;
		if (System.getProperty("TRACE")!=null && System.getProperty("TRACE").equals("Y"))
			trace=true;
		init();
	}
	
	public void init()
	{
		recordCounter=new AtomicInteger(0);
		
		recordLock = new ReentrantLock();		
		hasRecordResult = recordLock.newCondition();
		
		trigerLock=new ReentrantLock();
		hasTrigerResult=trigerLock.newCondition();
		
		smsLock=new ReentrantLock();
		hasSmsResult=smsLock.newCondition();
		
		recordQueue = new LinkedBlockingQueue(maxnumRecordQueueSize);
		
		trigerQueue=new LinkedBlockingQueue(maxnumTrigerQueueSize);
		
		smsQueue=new LinkedBlockingQueue(maxnumSmsQueueSize);
		
		recordChecker = new RecordQueueChecker(RECORD,100);
		recordChecker.setDaemon(true);
		recordChecker.start();
		
		trigerChecker = new TrigerQueueChecker(TRIGER,100);
		trigerChecker.setDaemon(true);
		trigerChecker.start();
		
		smsChecker = new SmsQueueChecker(SMS,100);
		smsChecker.setDaemon(true);
		smsChecker.start();
		
	}
	
	/**
	 * 重置,清零
	 */
	public void clean()
	{
		recordCounter.set(0);
		
		recordQueue.clear();
		
		trigerQueue.clear();
		
		smsQueue.clear();
		
		recordChecker.stopThread();
		recordChecker = null;
		
		trigerChecker.stopThread();
		trigerChecker=null;
		
		smsChecker.stopThread();
		smsChecker=null;		
	}
	
	
	
	/**
	 * 放置任务结果记录
	 * @param job
	 * @return
	 */
    public boolean offerRecord(IBOABGMonLRecordValue record)
	{
		boolean result = recordQueue.offer(record);		
		if (result)
		{
			notifyHasRecordResult();
		}
		
		return result;
	}
	
	/**
	 * 放置预警记录
	 * @param triger
	 * @return
	 */
    public boolean offerTriger(IBOABGMonWTriggerValue triger)
	{
		boolean result = trigerQueue.offer(triger);		
		if (result)
		{
			notifyHasTrigerResult();
		}
		
		return result;
	}
	
	/**
	 * 放置任务预警
	 * @param sms
	 * @return
	 */
	public boolean offerSms(ISmsState sms){
		boolean result=smsQueue.offer(sms);
		
		if (result){
			notifyHasSmsResult();
		}
		return result;
	}
	
	/**
	 * 通知有任务结果
	 */
	public void notifyHasRecordResult()
	{
		boolean flag = false;
		
		try
		{
			flag = recordLock.tryLock(50,TimeUnit.MILLISECONDS);
			
			if (flag)
				hasRecordResult.signalAll();
		}
		catch(InterruptedException ie)
		{
			//do nothing;
		}
		catch(Exception ex)
		{
			log.error(ex);
		}
		finally
		{
			if (flag)
				recordLock.unlock();
		}
	}
	
	/**
	 * 通知有预警结果
	 */
	public void notifyHasTrigerResult()
	{
		boolean flag = false;
		
		try
		{
			flag = trigerLock.tryLock(50,TimeUnit.MILLISECONDS);
			
			if (flag)
				hasTrigerResult.signalAll();
		}
		catch(InterruptedException ie)
		{
			//do nothing;
		}
		catch(Exception ex)
		{
			log.error(ex);
		}
		finally
		{
			if (flag)
				trigerLock.unlock();
		}
	}
	
	
	/**
	 * 通知有短信结果
	 */
	public void notifyHasSmsResult()
	{
		boolean flag = false;
		
		try
		{
			flag = smsLock.tryLock(50,TimeUnit.MILLISECONDS);
			
			if (flag)
				hasSmsResult.signalAll();
		}
		catch(InterruptedException ie)
		{
			//do nothing;
		}
		catch(Exception ex)
		{
			log.error(ex);
		}
		finally
		{
			if (flag)
				smsLock.unlock();
		}
	}
	
	/**
	 * 没有任务结果时，锁定等待
	 * @param waittime
	 */
	public void blockNoRecordResult(long waittime)
	{
		boolean flag = recordLock.tryLock();
		
		if (flag)
		{
			try
			{
				hasRecordResult.await(waittime, TimeUnit.MILLISECONDS);
			}
			catch(InterruptedException ie)
			{
				//do nothing;
			}
			catch(Exception ex)
			{
				log.error("block Record Queue error.",ex);
			}
			finally
			{
				recordLock.unlock();
			}
		}
	}
	
	/**
	 * 没有预警结果时，锁定等待
	 * @param waittime
	 */
	public void blockNoTrigerResult(long waittime)
	{
		boolean flag = trigerLock.tryLock();
		
		if (flag)
		{
			try
			{
				hasTrigerResult.await(waittime, TimeUnit.MILLISECONDS);
			}
			catch(InterruptedException ie)
			{
				//do nothing;
			}
			catch(Exception ex)
			{
				log.error("block Triger Queue error.",ex);
			}
			finally
			{
				trigerLock.unlock();
			}
		}
	}
	
	/**
	 * 没有短信结果记录，锁定
	 * @param waittime
	 */
	public void blockNoSmsResult(long waittime)
	{
		boolean flag = smsLock.tryLock();
		
		if (flag)
		{
			try
			{
				hasSmsResult.await(waittime, TimeUnit.MILLISECONDS);
			}
			catch(InterruptedException ie)
			{
				//do nothing;
			}
			catch(Exception ex)
			{
				log.error("block Sms Queue error.",ex);
			}
			finally
			{
				smsLock.unlock();
			}
		}
	}
	
	
	/**
	 * 抽象队列检查
	 * @author guocx
	 *
	 */
	abstract class QueueChecker extends Thread
	{
		private boolean isRunning = true;
		
		//一次保存记录数
		private int count=100;
		
		private String name;
		
		private List batchDatas=null;
		
		public QueueChecker(String name,int count)
		{
			super(name+" Queue Checker");
			this.name=name;			
			this.count=count;
			batchDatas=new ArrayList();
		}
		
		/**
		 * 尽量避免空轮询，设置没有任务记录时等待一分钟
		 */
		public void run()
		{
			while (isRunning) 
			{				
				try
				{
					//检阅是否有数据					
					Object data=peekObject();
					
					if (data != null)
					{
						//没有超时设置,暂时不做超时放弃操作						
						long startTime=0;
						if (trace){
							if (name.equals(TaskResultQueueManager.RECORD))
								recordCounter.set(0);
							startTime=System.currentTimeMillis();
						}
						List records=new ArrayList();
						int i=0;
						do{	
							i++;
							if (trace){
								if (name.equals(TaskResultQueueManager.RECORD))
									recordCounter.incrementAndGet();
							}
							records.add(data);
							//移除数据
							removeObject(data);
							
							if (i==count){
								process(records);
								i=0;
								records.clear();
							}
							data =peekObject();
							if (data==null && records.size()>0 && i>0){
								process(records);
								i=0;
								records.clear();
							}
							
						}while (data!=null);
						
						if (trace){
							if (name.equals(TaskResultQueueManager.RECORD)){
								long endTime=System.currentTimeMillis();
								long coreTime=endTime-startTime;
								int count=recordCounter.get();
								log.error("非错误信息,"+name+" Queue本次处理开始时间:["+startTime+"],结束时间:["+endTime+"],耗时:["+coreTime+"]，共处理记录["+count+"]");
								recordCounter.set(0);
							}
						}
					}
					else
					{
						if (log.isDebugEnabled()){
							log.debug("队列["+name+"]中暂无数据等待1分钟");
						}
						if (trace){
							if (name.equals(TaskResultQueueManager.RECORD)){
								log.error("非错误信息，队列中暂无数据,最长等待1分钟");
							}							
						}
						blockQueue(60*1000);//等待一分钟
					}					
				}
				catch(Exception ex)
				{
					log.error("QueueChecker error!",ex);
				}
				
			}

			
		}
		
		/**
		 * 移除对象
		 */
		public abstract void removeObject(Object data);
		
		/**
		 * 检阅对象
		 * @return
		 */
		public abstract Object peekObject();
		
		/**
		 * 处理数据(持久化数据)
		 * @param list
		 * @throws Exception
		 */
		public abstract void process(List list) throws Exception;
		
		/**
		 * 锁定队列
		 * @param wait
		 */
		public abstract void blockQueue(int wait);
		
		public void stopThread()
		{
			try
			{
				isRunning = false;
				this.interrupt();
			}
			catch(Exception e)
			{
				//do nothing
			}
		}
	}
	
	/**
	 * 监控记录队列
	 * @author guocx
	 *
	 */
	class RecordQueueChecker extends QueueChecker{
		
		public RecordQueueChecker(String name,int count){
			super(name,count);
		}
		/**
		 * 移除对象
		 */
		public void removeObject(Object data){
			recordQueue.remove(data);
		}
		
		/**
		 * 检阅对象
		 * @return
		 */
		public Object peekObject(){
			return recordQueue.peek();
		}
		
		/**
		 * 处理数据(持久化数据)
		 * @param list
		 * @throws Exception
		 */
		public void process(List list) throws Exception{
            TaskPostionStategyFactory.persistentRecord((IBOABGMonLRecordValue[]) list.toArray(new IBOABGMonLRecordValue[0]));
		}
		
		/**
		 * 锁定队列
		 * @param wait
		 */
		public void blockQueue(int wait){
			blockNoRecordResult(wait);
		}
	}
	
	/**
	 * 预警队列检查
	 * @author guocx
	 *
	 */
	class TrigerQueueChecker extends QueueChecker{
		
		public TrigerQueueChecker(String name,int count){
			super(name,count);
		}
		/**
		 * 移除对象
		 */
		public void removeObject(Object data){
			trigerQueue.remove(data);
		}
		
		/**
		 * 检阅对象
		 * @return
		 */
		public Object peekObject(){
			return trigerQueue.peek();
		}
		
		/**
		 * 处理数据(持久化数据)
		 * @param list
		 * @throws Exception
		 */
		public void process(List list) throws Exception{
            TaskPostionStategyFactory.persistentTriger((IBOABGMonWTriggerValue[]) list.toArray(new IBOABGMonWTriggerValue[0]));
		}
		
		/**
		 * 锁定队列
		 * @param wait
		 */
		public void blockQueue(int wait){
			blockNoTrigerResult(wait);
		}
	}
	
	/**
	 * 短信队列检查
	 * @author guocx
	 *
	 */
	class SmsQueueChecker extends QueueChecker{
		
		public SmsQueueChecker(String name,int count){
			super(name,count);
		}
		/**
		 * 移除对象
		 */
		public void removeObject(Object data){
			smsQueue.remove(data);
		}
		
		/**
		 * 检阅对象
		 * @return
		 */
		public Object peekObject(){
			return smsQueue.peek();
		}
		
		/**
		 * 处理数据(持久化数据)
		 * @param list
		 * @throws Exception
		 */
		public void process(List list) throws Exception{
			TaskPostionStategyFactory.persistentSms((ISmsState[])list.toArray(new ISmsState[0]));
		}
		
		/**
		 * 锁定队列
		 * @param wait
		 */
		public void blockQueue(int wait){
			blockNoSmsResult(wait);
		}
	}
}
