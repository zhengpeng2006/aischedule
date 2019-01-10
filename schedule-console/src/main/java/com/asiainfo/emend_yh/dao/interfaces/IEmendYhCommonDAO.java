package com.asiainfo.emend_yh.dao.interfaces;  

import java.util.List;
import java.util.Map;

import com.asiainfo.emend_yh.ivalues.IBOAmSchedPsMonValue;
import com.asiainfo.emend_yh.ivalues.IBOAmTaskRelyValue;
  /**
   * 
   *@Title:  月对账dao层接口
   *@Description:  
   *@Author:szh  
   *@Since:2015年8月27日  
   *@Version:1.1.0
   */
public interface IEmendYhCommonDAO {
	/**
	 * 获取所有任务依赖关系
	 * @return
	 * @throws Exception  
	 * @Description:
	 */
	public IBOAmTaskRelyValue[] qryAllRely() throws Exception;

	/**
	 * 根据任务分组标识查询出任务信息
	 * @return
	 * @throws Exception  
	 * @Description:
	 */
	public IBOAmTaskRelyValue[] qryRely(String taskGroup) throws Exception;
	
	/**
	 * 通过查询条件查询到进程状态数据
	 * @param taskCode
	 * @param regionId
	 * @param bsBillingCycle
	 * @return  
	 * @Description:
	 */
	public IBOAmSchedPsMonValue[] qryPsMonByCondition(String taskCode, String regionId, long bsBillingCycle) throws Exception;
	
	/**
	 * 查询分组信息
	 * @return
	 * @throws Exception  
	 * @Description:
	 */
	public List<String> qryGroupInfo() throws Exception;

}
