package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;
import com.asiainfo.monitor.busi.cache.interfaces.IPanel;

public class Panel extends DefaultCheckCache implements Serializable,IPanel{

	private String panel_Id;
	
	private String panel_Name;
	
	private String panel_Desc;
	
	private String layer;
	
	private String obj_Id;
	
	private String obj_Type;
	
	private String threshold_Id;
	
	private String time_Id;
	
	private String exec_Method;
	
	private String temp_Type;
	
	private String view_Type;
	
	//显示策略
	private String view_strategy;
	//显示转换实现
	private String view_transform;
	
	private String contrail;
	
	private String state;
	
	private String remarks;
	
	
	public String getKey(){
		return this.panel_Id;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Panel() {
		super();
	}

	public String getExec_Method() {
		return exec_Method;
	}

	public void setExec_Method(String exec_Method) {
		this.exec_Method = exec_Method;
	}

	public String getTemp_Type() {
		return temp_Type;
	}

	public void setTemp_Type(String temp_Type) {
		this.temp_Type = temp_Type;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public String getPanel_Desc() {
		return panel_Desc;
	}

	public void setPanel_Desc(String panel_Desc) {
		this.panel_Desc = panel_Desc;
	}

	public String getThreshold_Id() {
		return threshold_Id;
	}

	public void setThreshold_Id(String threshold_Id) {
		this.threshold_Id = threshold_Id;
	}

	

	public String getObj_Id() {
		return obj_Id;
	}

	public void setObj_Id(String obj_Id) {
		this.obj_Id = obj_Id;
	}


	public String getObj_Type() {
		return obj_Type;
	}


	public void setObj_Type(String obj_Type) {
		this.obj_Type = obj_Type;
	}


	public String getPanel_Id() {
		return panel_Id;
	}


	public void setPanel_Id(String panel_Id) {
		this.panel_Id = panel_Id;
	}


	public String getPanel_Name() {
		return panel_Name;
	}


	public void setPanel_Name(String panel_Name) {
		this.panel_Name = panel_Name;
	}
	

	public String getView_strategy() {
		return view_strategy;
	}

	public void setView_strategy(String viewStrategy) {
		view_strategy = viewStrategy;
	}

	public String getView_transform() {
		return view_transform;
	}

	public void setView_transform(String viewTransform) {
		view_transform = viewTransform;
	}

	public String getView_Type() {
		return view_Type;
	}


	public void setView_Type(String view_Type) {
		this.view_Type = view_Type;
	}

	public String getTime_Id() {
		return time_Id;
	}

	public void setTime_Id(String time_Id) {
		this.time_Id = time_Id;
	}

	public String getContrail() {
		return contrail;
	}

	public void setContrail(String contrail) {
		this.contrail = contrail;
	}

}
