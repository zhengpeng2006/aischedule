package com.asiainfo.monitor.exeframe.configmgr.view;

import org.apache.tapestry.IRequestCycle;

import com.ailk.appengine.web.view.AppPage;


/**
 * Created by SMT 
 * Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class HostPerformanceConfig extends AppPage {

	protected void initPageAttrs() {
		initExtend();
	}

	public void initPage(IRequestCycle cycle) {
	
		initExtend(cycle);
	}

	private void initExtend(IRequestCycle cycle) {
	
	}

	private void initExtend() {
	
	}
	

}
