package com.asiainfo.schedule.view;

import java.io.StringWriter;

import com.ailk.appengine.web.view.AppPage;

import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.data.ZKDataManagerFactory;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

import net.sf.json.JSONObject;


/**
 * Created by SMT 
 * Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class TaskExport extends AppPage {
	
	private static final Logger LOGGER = Logger.getLogger(TaskExport.class);
	
	/**
	 * 展示任务树
	 * @param cycle
	 * @throws Exception
	 */
	public void showTree(IRequestCycle cycle) throws Exception{
		JSONObject result = new JSONObject();
		try{
			ZKDataManagerFactory zkdm = (ZKDataManagerFactory) DataManagerFactory.getDataManager();
			StringWriter sw = new StringWriter();
			zkdm.printTree("/ai-schedule-center-ams", sw, "<br/>");
			result.put("msg", sw.toString());
		}catch(Exception e){
			LOGGER.error("get task tree failed",e);
			result.put("msg", "get task tree failed:"+e.getMessage());
		}
		this.setAjax(result);
	}

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
