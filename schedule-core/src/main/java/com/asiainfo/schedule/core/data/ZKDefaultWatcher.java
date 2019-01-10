package com.asiainfo.schedule.core.data;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月29日
 * @modify
 */
public class ZKDefaultWatcher implements Watcher {

	@Override
	public void process(WatchedEvent event) {
		System.out.println("event.getType():" + event.getType().name());
		System.out.println("event.getState():" + event.getState().name());
	}

}
