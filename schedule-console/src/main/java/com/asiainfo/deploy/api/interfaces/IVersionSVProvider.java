package com.asiainfo.deploy.api.interfaces;

import java.util.List;

import com.asiainfo.deploy.common.bo.BODeployVersionBean;

/**
 * 提供版本相关的接口
 * @author 孙德东(24204)
 */
public interface IVersionSVProvider {
	
	/**
	 * 验证某个部署策略下的节点（同一个系统的节点）的版本是否一致
	 * @param deployStrategyId
	 * @return
	 * @throws Exception 
	 */
	boolean isAllNodesHaveSameVersion(long deployStrategyId) throws Exception;
	/**
	 * 验证节点列表（同一个系统的节点）的版本是否一致
	 * @param deployStrategyId
	 * @return
	 * @throws Exception 
	 */
	boolean isAllNodesHaveSameVersion(List<Long> nodeList) throws Exception;
	
	/**
	 * 获取下个ID
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

}
