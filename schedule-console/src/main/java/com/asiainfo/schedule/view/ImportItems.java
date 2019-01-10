package com.asiainfo.schedule.view;

import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ailk.appengine.web.view.AppPage;

/**
 * Created by SMT Copyright: Copyright(c) 2015 Asiainfo-Linkage
 */
public abstract class ImportItems extends AppPage {
	private static final Logger LOGGER = Logger.getLogger(TaskExport.class);

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
