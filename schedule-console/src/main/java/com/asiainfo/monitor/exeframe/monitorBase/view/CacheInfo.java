package com.asiainfo.monitor.exeframe.monitorBase.view;

import org.apache.tapestry.IRequestCycle;

import com.ailk.appengine.web.view.AppPage;

/**
 * Created by SMT 
 * Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class CacheInfo extends AppPage {

	private void initExtend(IRequestCycle cycle) {
	
	}

	private void initExtend() {
	
	}

	protected void initPageAttrs() {
		initExtend();
	}

	public void initPage(IRequestCycle cycle) {
	
		initExtend(cycle);
	}
	

}
