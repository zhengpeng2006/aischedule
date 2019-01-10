package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWSmsValue;

public interface IAIMonWSmsDAO {

	/**
	 * 保存短信信息
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long saveOrUpdate(IBOAIMonWSmsValue value) throws Exception;
	
	/**
	 * 批量保存短信信息
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWSmsValue[] values) throws Exception;
}
