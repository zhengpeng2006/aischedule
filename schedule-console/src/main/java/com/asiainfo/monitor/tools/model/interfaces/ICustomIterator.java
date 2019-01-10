package com.asiainfo.monitor.tools.model.interfaces;

public interface ICustomIterator {

	public void first();
	
	public void next();
	
	public boolean isDone();
	
	public Object currentItem();
	
}
