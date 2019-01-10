package com.asiainfo.monitor.tools.common;

import com.asiainfo.monitor.tools.common.interfaces.IWorker;

public class TaskWorker implements Runnable {
	
	protected IWorker to;

	public TaskWorker(IWorker to) {
		this.to = to;
	}

	public void run() {
		try {
			to.action();
		} catch (Exception e) {
			
		} finally {

		}
	}

}
