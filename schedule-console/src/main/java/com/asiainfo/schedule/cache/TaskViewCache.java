package com.asiainfo.schedule.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

/**
 * 任务信息用单例缓存
 * @author lj
 *
 */
public class TaskViewCache{
	
	private static final Logger LOGGER = Logger.getLogger(TaskViewCache.class);
	
	/**单例对象 */
	private static TaskViewCache cache;
	
	/** 任务信息	 */
	private static Map<String,List<Map<String,String>>> TASK_VIEWS = new HashMap<String, List<Map<String,String>>>();
	
	/** 当前版本号	 */
	private static long VERSION_CUR = 0;
	
	/** 最大版本号（到此数字以后从0开始）	 */
	private static long VERSION_MAX = 9999999;
	
	/** 刷新时间（毫秒） */
	private static final long REFRESH_TIME = 3*60*1000;
	
	/** 定时器 */
	private static final Timer timer = new Timer();
	
	/** 私有化构造方法 */
	private TaskViewCache(){
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					loadData();
				} catch (Exception e) {
					LOGGER.error("loadData failed",e);
				}
			}
		}, 0,REFRESH_TIME);
	}
	
	/**
	 * 获取单例缓存对象
	 * @return
	 * @throws Exception
	 */
	public synchronized static TaskViewCache getInstance() throws Exception {
		if (cache == null){
			cache = new TaskViewCache();
		}
		return cache;
	}
	
	/**
	 * 加载对象
	 * @throws Exception
	 */
	public synchronized void loadData() throws Exception {
		//首先清理缓存
		TASK_VIEWS.clear();
		//调用接口取得数据
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("load task info cache start");
		}
		ISchedulerSV sv = (ISchedulerSV) ServiceFactory.getService(ISchedulerSV.class);
		TASK_VIEWS  = sv.getServerTaskPanoramicView();
		//超过最大值时重置
		if (++VERSION_CUR > VERSION_MAX){
			VERSION_CUR = 0;
		}
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("load task info cache end,total count is "+ TASK_VIEWS.size()
					+", current version is "+VERSION_CUR);
		}
	}

	/**
	 * 获取数据
	 * @param serverCode
	 * @return
	 * @throws Exception
	 */
	public Map<String,List<Map<String,String>>> getData()
			throws Exception {
		
		if (TASK_VIEWS.isEmpty()){
			loadData();
		}
		return TASK_VIEWS;
	}

	/**
	 * 获得当前版本
	 * @return
	 * @throws Exception
	 */
	public long getCurVersion() throws Exception {
		return VERSION_CUR;
	}
}
