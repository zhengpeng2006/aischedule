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
public abstract class SQLStatistics extends AppPage {

	private void initExtend(IRequestCycle cycle) {
	
	}

	private void initExtend() {
	
	}

	public abstract void setSQLStatisticsListCount(long SQLStatisticsListCount);

	public abstract void setSQLStatisticsListItem(JSONObject SQLStatisticsListItem);

	public abstract void setSQLStatisticsListItems(JSONArray SQLStatisticsListItems);

	public abstract void setSQLStatisticsListRowIndex(int SQLStatisticsListRowIndex);

	public abstract void setQueryForm(JSONObject queryForm);

	protected void initPageAttrs() {
	
		initExtend();
	}

	public void initPage(IRequestCycle cycle) {
		IDataBus SQLStatisticsListItem = createData("SQLStatisticsListItem");
		if (SQLStatisticsListItem != null
				&& !SQLStatisticsListItem.getDataObject().isEmpty()) {
			setSQLStatisticsListItem(SQLStatisticsListItem.getDataObject());
		}
		IDataBus queryForm = createData("queryForm");
		if (queryForm != null && !queryForm.getDataObject().isEmpty()) {
			setQueryForm(queryForm.getDataObject());
		}
		initExtend(cycle);
	}
	

}
