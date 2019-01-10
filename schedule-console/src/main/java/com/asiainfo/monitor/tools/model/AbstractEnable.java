package com.asiainfo.monitor.tools.model;

import com.asiainfo.monitor.tools.common.interfaces.IEnable;

public abstract class AbstractEnable implements IEnable {

	private boolean enbale;
	
	public AbstractEnable() {
		super();
		setEnable(true);
	}

	public void setEnable(boolean enable) {
		this.enbale=enable;
	}

	public boolean getEnable() {
		return this.enbale;
	}

}
