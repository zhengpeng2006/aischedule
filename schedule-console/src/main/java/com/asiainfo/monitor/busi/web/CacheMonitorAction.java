package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowCacheSV;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class CacheMonitorAction extends BaseAction {

	private static transient Log log=LogFactory.getLog(CacheMonitorAction.class);
	
	
	
	/**
	 * 读取框架缓存类型
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getFrameCacheType(Object[] ids) throws Exception{
		if (ids == null || ids.length<1){
			return null;
		}
		List result = new ArrayList();
		// "全部"
		result.add(AIMonLocaleFactory.getResource("MOS0000007"));		
		try{
			IAPIShowCacheSV showCacheSV = (IAPIShowCacheSV)ServiceFactory.getService(IAPIShowCacheSV.class);
			result.addAll(showCacheSV.getFrameCacheType(ids));
		}catch(Exception e){
			log.error("Call CacheMonitorAction's Method getFrameCacheType has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 读取业务缓存类型
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getBusiCacheType(String appId) throws Exception {
		if (StringUtils.isBlank(appId))
			return null;
		List result= new ArrayList();		
		result.add(AIMonLocaleFactory.getResource("MOS0000007"));
		try{
			IAPIShowCacheSV showCacheSV=(IAPIShowCacheSV)ServiceFactory.getService(IAPIShowCacheSV.class);
			result.addAll(showCacheSV.getBusiCacheType(appId));
		}catch(Exception e){
			log.error("Call CacheMonitorAction's Method getFrameCacheType has Exception :"+e.getMessage());
		}
		return result;
	}
	
	  /**
     * 获取一组应用的缓存类型（就是CLASS_NAME）
     * @param appIds
     * @return
     * @throws Exception
     */
    public List getAppsBusiCacheType(Object[] appIds) throws Exception {
        if (appIds==null || appIds.length<1) {
            return null;
        }
        List result= new ArrayList();		
		result.add(AIMonLocaleFactory.getResource("MOS0000007"));
        try {
            IAPIShowCacheSV showCacheSV = (IAPIShowCacheSV) ServiceFactory.getService(IAPIShowCacheSV.class);
            result.addAll(showCacheSV.getBusiCacheType(appIds));
        } catch (Exception e) {
            log.error("Call CacheMonitorAction's Method getFrameCacheType has Exception :" + e.getMessage());
        }
        return result;
    }
	
	/**
	 * 读取多应用框架指定类型的平台缓存
	 * @param appId
	 * @param type
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List getFrameCache(Object[] appIds,String type,String key,Integer start,Integer end) throws Exception{
		if (appIds==null || appIds.length<1)
			return null;
		List result=new ArrayList();
		try{
			IAPIShowCacheSV showCacheSV=(IAPIShowCacheSV)ServiceFactory.getService(IAPIShowCacheSV.class);
			result=showCacheSV.getFrameCache(appIds,type,key,start,end);
		}catch(Exception e){
			log.error("Call CacheMonitorAction's Method getFrameCache has Exception :"+e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 读取单应用框架指定类型的平台缓存
	 * @param appId
	 * @param type
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getFrameCache(String appId,String type,String key,Integer start,Integer end) throws Exception{
		if (StringUtils.isNotBlank(appId))
			// "没有设置应用参数"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000006"));
		List result=new ArrayList();
		try{
			String[] ids=new String[]{appId};
			result=this.getFrameCache(ids,type,key,start,end);
		}catch(Exception e){
			log.error("Call CacheMonitorAction's Method getFrameCache has Exception :"+e.getMessage());
		}
		return result;
	}
	/**
	 * 读取业务所有类型的缓存
	 * @param appId
	 * @param type
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List getBusiCache(String appId,String type,Integer start,Integer end) throws Exception{
		if (StringUtils.isBlank(appId))
			return null;
		List result= new ArrayList();
		
		try{
			IAPIShowCacheSV showCacheSV=(IAPIShowCacheSV)ServiceFactory.getService(IAPIShowCacheSV.class);
			result=showCacheSV.getBusiCache(appId,type,start,end);
		}catch(Exception e){
			log.error("Call CacheMonitorAction's Method getFrameCache has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取多个应用的业务缓存统计信息
	 * @param appIds
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List getBusiCacheFromMultiApp(Object[] appIds,String type) throws Exception{
	    if(null==appIds||0==appIds.length){
	        return null;
	    }
        List result= new ArrayList();
        
        try{
            IAPIShowCacheSV showCacheSV=(IAPIShowCacheSV)ServiceFactory.getService(IAPIShowCacheSV.class);
            result=showCacheSV.getBusiCacheFromMultiApp(appIds, type);
        }catch(Exception e){
            log.error("Call CacheMonitorAction's Method getFrameCache has Exception :"+e.getMessage());
            throw new Exception(e.getMessage());
        }
        return result;	    
	}
	
	
	/**
	 * 获取多个应用的业务缓存统计信息(AppFrame)
	 * @param appIds
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getBusiCacheFromMultiAppForAppFrame(Object[] appIds,String type) throws Exception{
	    if(null == appIds || 0 == appIds.length){
	        return null;
	    }
	    DataContainerInterface[] retDBInfo = null;
        
        try{
            IAPIShowCacheSV showCacheSV = (IAPIShowCacheSV)ServiceFactory.getService(IAPIShowCacheSV.class);
            retDBInfo = showCacheSV.getBusiCacheFromMultiAppForAppFrame(appIds, type);
        }catch(Exception e){
            log.error("Call CacheMonitorAction's Method getFrameCache has Exception :"+e.getMessage());
            throw new Exception(e.getMessage());
        }
        return retDBInfo;	    
	}
	
	
	
}
