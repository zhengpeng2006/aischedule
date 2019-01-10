package com.asiainfo.schedule.core.data;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZKSessionWatcher implements Watcher {

	private static final transient Logger logger = LoggerFactory.getLogger(ZKSessionWatcher.class);

	@Override
	public void process(WatchedEvent event) {
		try {
			logger.info("event.getState():" + event.getState());
			if (KeeperState.SyncConnected.equals(event.getState()) || KeeperState.Expired.equals(event.getState())) {
				logger.info("校验zookeeper连接是否失效，若连接失效重新创建会话！");
				ZKDataManagerFactory dmf = (ZKDataManagerFactory) DataManagerFactory.getDataManager();
				if (dmf.checkZookeeperState() == false) {
					dmf.createZKSession();
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
