package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.asiainfo.monitor.busi.cache.interfaces.ICustomPanel;
import com.asiainfo.monitor.busi.model.event.CustomPanelEvent;
import com.asiainfo.monitor.busi.model.interfaces.IDomainEvent;
import com.asiainfo.monitor.tools.model.interfaces.ICustomIterator;
import com.asiainfo.monitor.tools.util.TypeConst;

public class CustomPanel extends Panel implements Serializable,ICustomPanel {

	//公共面板标识列表
	private List childPanels=new ArrayList();
	
	
	//自定义类型，默认CUSTOM_CHART;
	private String cust_Type;
	
	public CustomPanel(){
		super();
	}


	public List getChildPanles() {
		return childPanels;
	}

	public void addChildPanels(String panelId){
		this.childPanels.add(panelId);
	}
	
	public void setChildPanles(List childPanels) {
		this.childPanels = childPanels;
	}

	public String getCust_Type() {
		return cust_Type;
	}

	public void setCust_Type(String custType) {
		cust_Type = custType;
	}
	
	public ICustomIterator createIterator(){
		return new ConcreteIterator();
	}
	
	public Object getChildPanel(String id) throws Exception{
		String cid = getPanel_Id();
		IDomainEvent event=new CustomPanelEvent();
		return event.getEventResult(cid + TypeConst._INTERPRET_CHAR+id);
	}
	
	private class ConcreteIterator implements ICustomIterator{
		
		private int currentIndex=0;
		
		/**
		 * 移动到第一个元素
		 */
		public void first(){
			currentIndex=0;
		}
		
		public void next(){
			if (currentIndex<childPanels.size()){
				currentIndex++;
			}
		}
		
		public boolean isDone(){
			return currentIndex==childPanels.size();
		}
		
		public Object currentItem(){
			//获取当前面板信息
			String id=String.valueOf(childPanels.get(currentIndex));
			String cid = getPanel_Id();
			IDomainEvent event=new CustomPanelEvent();
			return event.getEventResult(cid + TypeConst._INTERPRET_CHAR+id);
		}
	}
	
	
}
