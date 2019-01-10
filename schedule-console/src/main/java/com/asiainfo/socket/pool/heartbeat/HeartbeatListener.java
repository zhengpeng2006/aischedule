package com.asiainfo.socket.pool.heartbeat;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 监控心跳程序的启动
 * 
 * @author 孙德东(24204)
 */
public class HeartbeatListener implements ServletContextListener {

	private HeartbeatThread t = new HeartbeatThread();
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		t.releaseChannels();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		t.start();
	}

}
