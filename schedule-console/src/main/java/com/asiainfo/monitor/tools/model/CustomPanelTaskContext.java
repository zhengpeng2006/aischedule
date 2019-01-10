package com.asiainfo.monitor.tools.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IKeyName;
import com.asiainfo.monitor.tools.model.interfaces.IWebPanelTaskContext;

public class CustomPanelTaskContext extends DefaultTaskContext implements IWebPanelTaskContext, Serializable {

	private static Log log=LogFactory.getLog(CustomPanelTaskContext.class);
	
	private String layer;
	
	private Map viewMap=new HashMap();
	
	private Map cmdRelatePanelMap=new HashMap();
	
	public String getLayer() {
		return layer;
	}

	public ThresholdModel getThreshold() {
		return null;
	}

	/**
	 * 返回面板对应的显示策略、显示转换类
	 * @param key
	 * @return IKeyName ,key:显示策略;name:显示转换
	 */
	public IKeyName getViewWrapperClass(String key){
		return (IKeyName)viewMap.get(key);
	}
	
	public void putViewWrapperClass(String key,String viewStrategy,String viewTranform){
		viewMap.put(key,new KeyName(viewStrategy,viewTranform));
	}
	
	public String getViewWrapperClass() {
		return null;
	}

	/**
	 * 保存具体命令(Table/Exec)和自定义面板关联的公共面板的对应关系
	 * @param key
	 * @param panelId
	 */
	public void putCmdRelatePanel(String key,String panelId){
		cmdRelatePanelMap.put(key, panelId);
	}
	
	public String getCmdRelatePanel(String key){
		return (String)cmdRelatePanelMap.get(key);
	}
	
	public void setLayer(String layer) {
		this.layer=layer;
	}

	public void setThreshold(ThresholdModel threshold) {
	}

	public void setViewWrapperClass(String viewWrapperClass) {
	}

}
