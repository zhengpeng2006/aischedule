package com.asiainfo.monitor.busi.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.AbstractEnable;

public abstract class DefaultCheckCache extends AbstractEnable implements ICheckCache {

	private static transient Log log=LogFactory.getLog(DefaultCheckCache.class);
	
	protected ICacheListener event;
	

	public void setCacheListener(ICacheListener event){
		this.event=event;
	}
	
	public void validityCheck() throws Exception{
		try{
			if (this.event!=null && !this.getEnable())
				this.event.validityCheck(this);
		}catch(Exception e){
			// 缓存校验发生异常:
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000234")+e.getMessage());
		}
	}
	
	/**
	 * 设置可能状态
	 * @param enable,如果设置为不可用,则触发监听事件
	 */
	public void setEnable(boolean enable){
		if (enable!=super.getEnable()){
			synchronized(this){
				if (enable==super.getEnable())
					return;
				super.setEnable(enable);
				try{
					if (!enable)
						this.validityCheck();
				}catch(Exception e){
					// "调用缓存监听异常"
					log.error(AIMonLocaleFactory.getResource("MOS0000235"));
				}
			}
		}
				
	}
}
