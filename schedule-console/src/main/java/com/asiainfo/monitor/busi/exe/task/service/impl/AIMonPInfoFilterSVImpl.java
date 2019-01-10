package com.asiainfo.monitor.busi.exe.task.service.impl;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPInfoFilterDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoFilterValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoFilterSV;

public class AIMonPInfoFilterSVImpl implements IAIMonPInfoFilterSV {

	/**
	 * 根据主键取得任务过滤信息
	 * @param filterId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoFilterValue getFilterById(long filterId) throws Exception {
		IAIMonPInfoFilterDAO filterDao = (IAIMonPInfoFilterDAO) ServiceFactory.getService(IAIMonPInfoFilterDAO.class);
		return filterDao.getFilterById(filterId);
	}

	/**
	 * 根据条件取得任务过滤信息
	 * 
	 * @param expr
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoFilterValue[] getFilterByCond(String filterName, Integer startNum, Integer endNum) throws Exception {
		IAIMonPInfoFilterDAO filterDao = (IAIMonPInfoFilterDAO) ServiceFactory.getService(IAIMonPInfoFilterDAO.class);
		return filterDao.getFilterByCond(filterName, startNum, endNum);
	}
	
	/**
	 * 取得总件数
	 * 
	 * @param expr
	 * @return
	 * @throws Exception
	 */
	public int getFilterCount(String filterName) throws Exception {
		IAIMonPInfoFilterDAO filterDao = (IAIMonPInfoFilterDAO) ServiceFactory.getService(IAIMonPInfoFilterDAO.class);
		return filterDao.getFilterCount(filterName);
	}

	/**
	 * 保存或修改任务过滤信息
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoFilterValue value) throws Exception {
		IAIMonPInfoFilterDAO filterDao = (IAIMonPInfoFilterDAO) ServiceFactory.getService(IAIMonPInfoFilterDAO.class);
		filterDao.saveOrUpdate(value);
	}
	
	/**
	 * 批量保存或修改任务过滤信息
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoFilterValue[] values) throws Exception {
		IAIMonPInfoFilterDAO filterDao = (IAIMonPInfoFilterDAO) ServiceFactory.getService(IAIMonPInfoFilterDAO.class);
		filterDao.saveOrUpdate(values);
	}
	
	/**
	 * 删除任务过滤信息
	 * @param filterId
	 * @throws Exception
	 */
	public void delete(long filterId) throws Exception {
		IAIMonPInfoFilterDAO filterDao = (IAIMonPInfoFilterDAO) ServiceFactory.getService(IAIMonPInfoFilterDAO.class);
		filterDao.delete(filterId);
	}
	
}
