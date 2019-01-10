package com.asiainfo.monitor.busi.cache.impl;

import com.asiainfo.monitor.tools.util.TypeConst;

public class ConsanPanel extends Panel {
	private String c_Panel_Id;

	public String getC_Panel_Id() {
		return c_Panel_Id;
	}

	public void setC_Panel_Id(String cPanelId) {
		c_Panel_Id = cPanelId;
	}
	
	public String getKey(){
		return this.c_Panel_Id + TypeConst._INTERPRET_CHAR + super.getKey();
	}
	
}
