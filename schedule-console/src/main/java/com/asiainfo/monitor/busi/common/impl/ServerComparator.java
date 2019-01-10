package com.asiainfo.monitor.busi.common.impl;

import java.util.Comparator;

import com.asiainfo.monitor.busi.cache.interfaces.IServer;

public class ServerComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		int ret = 0;
		if (o1 == null || o2 == null)
			return ret;
		IServer server1 = (IServer) o1;
		IServer server2 = (IServer) o2;

		return server1.getApp_Name().compareTo(server2.getApp_Name());		
	}
}
