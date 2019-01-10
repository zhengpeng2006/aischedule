package com.asiainfo.socket.pool;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.Channel;

/**
 * 已经连接的Socket的线程池
 * 
 * @author 孙德东(24204)
 */
public class ConnectedSocketPool {
    /**
     * 维持全部连接
     */
	private static final Map<String, Channel> channels = new ConcurrentHashMap<String, Channel>();
	private static transient Log LOG = LogFactory.getLog(ConnectedSocketPool.class);
	private ConnectedSocketPool(){}
	/**
	 * 添加一个连接
	 * @param host
	 * @param port
	 * @param one
	 */
	public static void addChannel(String host, String port, Channel one) {
		String key = HostManager.combine(host, port);
		LOG.debug("add channel of " + key);
		Channel old = channels.get(key);
		if (old != null) {
			old.close().awaitUninterruptibly(); //等待关闭后才能加入新的连接
			LOG.debug("wait old channle of " + key + " to close.");
		}
		channels.put(key, one);
	}
	
	/**
	 * 心跳不可用的时候移除一个连接
	 * @param one
	 */
	public static void removeChannel(String host, String port) {
		String key =HostManager.combine(host, port);
		LOG.debug("remove channel of " + key);
		channels.remove(key);
	}
	
	/**
	 * 获取维持的连接
	 * @param host
	 * @param port
	 * @return
	 */
	public static Channel getChannel(String host, String port) {
		return channels.get(HostManager.combine(host, port));
	}
	
	/**
	 * 是否存在已经维护的连接
	 * @param host
	 * @param port
	 * @return
	 */
	public static boolean contains(String host, String port) {
		return channels.containsKey(HostManager.combine(host, port));
	}
	
	/**
	 * 获取所有连接
	 * @return
	 */
	public static Collection<Channel> getAllChannels() {
		return channels.values();
	}
}
