package com.asiainfo.monitor.busi.exe.task.dao.impl;

import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWSmsEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonWSmsDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWSmsValue;


public class AIMonWSmsDAOImpl implements IAIMonWSmsDAO {

	/**
	 * 保存短信信息
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long saveOrUpdate(IBOAIMonWSmsValue value) throws Exception {
		if (value.isNew() && value.getSmsId()<=0) {
			value.setSmsId(BOAIMonWSmsEngine.getNewId().longValue());
		}
		BOAIMonWSmsEngine.save(value);
		return value.getSmsId();
	}
	
	/**
	 * 批量保存短信信息
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWSmsValue[] values) throws Exception {
		for (int i=0;i<values.length;i++){
			if (values[i].isNew() && values[i].getSmsId()<=0) {
				values[i].setSmsId(BOAIMonWSmsEngine.getNewId().longValue());
			}
		}
		BOAIMonWSmsEngine.saveBatch(values);
	}
}
