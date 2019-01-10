package com.asiainfo.monitor.busi.common.impl;


import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.common.IState;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
;

public class ServerStateEnv {

	private final transient Log log=LogFactory.getLog(ServerStateEnv.class);
	
	private static ServerStateEnv instance=new ServerStateEnv();
	
	private Map stateMap=new ConcurrentHashMap();
	
	private Timer timer =null;
	
	private ServerStateEnv(){
	}
	
	
	public static ServerStateEnv getInstance(){
		return instance;
	}
	
	/**
	 * 存储应用状态
	 * @param id:应用标识
	 * @param state:状态
	 * @return
	 */
	public boolean putState(String id,Object state){
		if (stateMap.containsKey(id))
			return false;
		stateMap.put(id,state);
		synchronized(this){
			if (timer==null){
				timer=new Timer(true);
				int start = 30 * 60 * 1000;//30分钟后检查
				int interval = 5*60 * 1000;//间隔5分钟
				timer.schedule(new CheckStateThread(), start, interval);				
			}
		}
		return true;
	}
	
	/**
	 * 移除应用状态信息
	 * @param id
	 */
	public void removeState(String id){
		stateMap.remove(id);
		synchronized(this){
			if (stateMap.size()<1 && timer!=null){
				timer.cancel();
				timer=null;
			}
		}
	}
	

	public class CheckStateThread extends TimerTask{
		
		public CheckStateThread(){
			
		}
		
		public void run(){			
			Thread.currentThread().setName("COMEBACK_CHECK");
			// 运行检查恢复线程
			System.out.println(AIMonLocaleFactory.getResource("MOS0000228")+Thread.currentThread().getName());
			try{
				if (stateMap.size()>0){
					Iterator it=stateMap.entrySet().iterator();
					long curtime=System.currentTimeMillis();
					while(it.hasNext()){
						Entry entry=(Entry)it.next();
						IState state=(IState)entry.getValue();
						if (state!=null){
							if (curtime>=state.getOverTime()){
								it.remove();
							}else{
								if (log.isDebugEnabled())
									// "暂未超时........."
									log.debug(AIMonLocaleFactory.getResource("MOS0000229"));
							}
						}
					}
				}else{
					
				}
			}catch(Exception e){
				log.error("Call CheckStateThread's method run has Exception :"+e.getMessage());
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*IState state=new CommState();
		state.setStartTime(System.currentTimeMillis());
		state.setOverTime(TimeUtil.addOrMinusHours(System.currentTimeMillis(),1).getTime());
		state.setState("E");
		ServerStateEnv.getInstance().putState("1",state);
		
		ServerStateEnv.getInstance().removeState("1");
		
		IState state2=new CommState();
		state2.setStartTime(System.currentTimeMillis());
		state2.setOverTime(TimeUtil.addOrMinusHours(System.currentTimeMillis(),2).getTime());
		state2.setState("E");
		ServerStateEnv.getInstance().putState("3",state2);
		
		
		try{
			Thread.currentThread().sleep(3*60*1000);
		}catch(Exception e){
			
		}
		*/
		
	}

}
