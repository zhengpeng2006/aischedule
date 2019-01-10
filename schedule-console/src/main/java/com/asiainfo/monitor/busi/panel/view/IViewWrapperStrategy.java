package com.asiainfo.monitor.busi.panel.view;

import java.util.List;

import com.asiainfo.monitor.busi.cache.interfaces.IPanel;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;

public interface IViewWrapperStrategy {

	/**
	 * 包装显示XML头信息
	 * @param rtnList
	 * @param panel
	 * @return
	 * @throws Exception
	 */
	public String wrapperHeader(List taskList, IPanel panel) throws Exception;
	
	/**
	 * 包装Xml内容信息
	 * @param rtnList
	 * @param panel
	 * @return
	 * @throws Exception
	 */
	public String wrapperBody(TaskRtnModel rtnList, IPanel panel) throws Exception;
	
	/**
	 * 包装Xml尾信息
	 * @param rtnList
	 * @param panel
	 * @return
	 * @throws Exception
	 */
	public String wrapperTail(List taskList, IPanel panel) throws Exception;
	
}
