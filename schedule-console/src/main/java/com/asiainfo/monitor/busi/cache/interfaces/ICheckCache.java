package com.asiainfo.monitor.busi.cache.interfaces;

public interface ICheckCache {
	
	public boolean getEnable();
	
	public String getKey();
	
	public void setCacheListener(ICacheListener event);
	
	public void validityCheck() throws Exception;
}
