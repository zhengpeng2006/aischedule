package com.asiainfo.monitor.exeframe.monitorBase.view;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;


/**
 * Created by SMT 
 * Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class ServiceStatistics extends AppPage {

	private void initExtend(IRequestCycle cycle) {
	
	}

	private void initExtend() {
	
	}

	public abstract void setServiceListCount(long serviceListCount);

	public abstract void setServiceListItem(JSONObject serviceListItem);

	public abstract void setServiceListItems(JSONArray serviceListItems);

	public abstract void setQuery(JSONObject query);

	public abstract void setServiceListRowIndex(int serviceListRowIndex);

	protected void initPageAttrs() {
	
		initExtend();
	}

	public void initPage(IRequestCycle cycle) {
		IDataBus serviceListItem = createData("serviceListItem");
		if (serviceListItem != null && !serviceListItem.getDataObject().isEmpty()) {
			setServiceListItem(serviceListItem.getDataObject());
		}
		IDataBus query = createData("query");
		if (query != null && !query.getDataObject().isEmpty()) {
			setQuery(query.getDataObject());
		}
		initExtend(cycle);
	}
	

}
