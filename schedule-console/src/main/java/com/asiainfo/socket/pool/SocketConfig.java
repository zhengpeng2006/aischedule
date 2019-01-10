package com.asiainfo.socket.pool;

/**
 * socket配置
 * 
 * @author 孙德东(24204)
 */
public class SocketConfig {
	
	private SocketConfig(){}
	
	/**
	 * 心跳间隔
	 * @return
	 */
	public static int getHeartbeatInternal() {
		String internal = System.getProperty(SocketConstants.HEART_BEAT_INTERNAL_PROP);
		if (internal != null) {
			return Integer.valueOf(internal);
		}
		
		return SocketConstants.DEFAULT_HEART_BEAT_INTERNAL;
	}
	
	/**
	 * 监控端口
	 * @return
	 */
	public static int getMonitorPort() {
		String port = System.getProperty(SocketConstants.MONITOR_PORT_PROP);
		if (port != null) {
			return Integer.valueOf(port);
		}
		
		return SocketConstants.DEFAULT_MONITOR_PROT;
	}
	
	/**
	 * 连接超时时间
	 * @return
	 */
	public static int getConnectTimeout() {
		String timeout = System.getProperty(SocketConstants.CONNECT_TIMEOUT_PROP);
		if (timeout != null) {
			return Integer.valueOf(timeout);
		}
		
		return SocketConstants.DEFAULT_CONNNECT_TIMEOUT;
	}
	
	/**
	 * 读取超时时间设置--该时间周期内没有收到服务器的响应则抛出异常
	 * @return
	 */
	public static int getReadTimeout() {
		String timeout = System.getProperty(SocketConstants.READ_TIMEOUT_PROP);
		if (timeout != null) {
			return Integer.valueOf(timeout);
		}
		
		return SocketConstants.DEFAULT_READ_TIMEOUT;
	}
	
	/**
	 * 获取响应的超时时间设置
	 * @return
	 */
	public static int getResponseTimeout() {
		String timeout = System.getProperty(SocketConstants.RESPONSE_TIMEOUT_PROP);
		if (timeout != null) {
			return Integer.valueOf(timeout);
		}
		
		return SocketConstants.DEFAULT_RESPONSE_TIMEOUT;
	}
}
