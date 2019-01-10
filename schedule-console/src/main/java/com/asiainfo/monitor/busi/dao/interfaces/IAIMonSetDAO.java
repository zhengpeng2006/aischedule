package com.asiainfo.monitor.busi.dao.interfaces;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSetValue;

public interface IAIMonSetDAO {

	/**
	 * 根据标识获取设置
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue getMonSetBeanById(long id) throws Exception;
	
	
	/**
	 * 根据应用标识、设置码查询设置
	 * @param appId
	 * @param vcode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue[] getMonSetBeanByAppIdAndVCode(long appId, String vcode) throws Exception;
	
	/**
	 * 读取所有设置
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue[] getAllMonSetBean() throws Exception;
	
	/**
	 * 保存设置
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long saveOrUpdate(IBOAIMonSetValue value) throws Exception;
	
	/**
	 * 批量保存设置
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonSetValue[] values) throws Exception;
	
	/**
	 * 删除设置
	 * @param id
	 * @throws Exception
	 */
	public void deleteSet(long id) throws Exception;
}
