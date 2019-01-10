package com.asiainfo.monitor.busi.cache.interfaces;

import java.util.List;

import com.asiainfo.monitor.tools.model.interfaces.ICustomIterator;

public interface ICustomPanel extends IPanel{

	public List getChildPanles();

	public void addChildPanels(String panelId);
	
	public void setChildPanles(List childPanels);

	public String getCust_Type();

	public void setCust_Type(String custType);
	
	public Object getChildPanel(String id) throws Exception;
	
	public ICustomIterator createIterator();
	
}
