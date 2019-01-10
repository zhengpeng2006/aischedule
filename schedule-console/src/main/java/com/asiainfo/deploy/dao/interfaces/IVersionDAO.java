package com.asiainfo.deploy.dao.interfaces;

import com.asiainfo.deploy.common.bo.BODeployVersionBean;

/**
 * 版本表
 * @author 孙德东(24204)
 */
public interface IVersionDAO {
	/**
	 * 获取下一个版本
	 * @return
	 * @throws Exception 
	 */
	long nextId() throws Exception;
	
	/**
	 * 新增一个版本
	 * @param version
	 * @throws Exception 
	 */
	void saveVersion(BODeployVersionBean version) throws Exception;
	
	/**
	 * 查询某个部署策略的当前版本
	 * @param strategyId
	 * @return
	 * @throws Exception
	 */
	BODeployVersionBean currentVersion(long strategyId) throws Exception;
	/**
	 * 某策略类型的可用历史版本数目
	 * @param strategyId
	 * @return
	 * @throws Exception
	 */
	int countValidVersion(long strategyId) throws Exception;
	
	/**
	 * find by id
	 * @param versionId
	 * @throws Exception
	 */
	BODeployVersionBean getVersionById(long versionId) throws Exception;
	
	/**
	 * 正序获取有效的历史版本
	 * @return
	 * @throws Exception
	 */
	BODeployVersionBean[] getValidHistoryVersionsAsc(long strategyId) throws Exception;
}
