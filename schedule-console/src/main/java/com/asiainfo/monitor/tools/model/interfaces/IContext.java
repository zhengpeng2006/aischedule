package com.asiainfo.monitor.tools.model.interfaces;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.model.BaseContainer;

public interface IContext {

	public String getHostId();


	public void setHostId(String hostId);


	public String getServerId();


	public void setServerId(String serverId);


	public String getName();

	public void setName(String name);
	
	
	public BaseContainer getParent();


	public void setParent(BaseContainer parent);


	public List getParameter();
	
	
	public KeyName findParameter(String name);
	
	public Object getAttach();

	public void setAttach(Object attach);
	
	
	public CountDownLatch getSignal();

	public void setSignal(CountDownLatch signal);
	
	/**
	 * 设置参数
	 * @param item
	 * @throws Exception
	 */
	public void addParameter(KeyName item) throws Exception;
	
	/**
	 * 增加容器监听
	 * @param listener
	 */
	public void addContainerListener(IContainerListener listener);
	
	/**
	 * 移除容器监听
	 * @param listener
	 */
	public void removeContainerListener(IContainerListener listener);
	
	/**
	 * 触发容器监听事件
	 * @param type
	 * @param data
	 */
	public void fireContainerEvent(String type, Object data) throws Exception;
	
	/**
	 * 增加属性监听
	 * @param listener
	 */
    public void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * 移除属性监听
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener);
    
}
