package com.asiainfo.deploy.api.interfaces;

import java.util.List;

/**
 * 提供版本包分发的接口
 * @author 孙德东(24204)
 */
public interface IPackageDispatcherSVProvider {
	
	/**
	 * 
	 * @param nodeList
	 * @param deployStrategy
	 * @throws Exception
	 */
	void hostInitialization(List<Long> nodeList, long deployStrategy) throws Exception;
	/**
	 * 安装应用
	 * @param nodes
	 * @param versionId
	 * @throws Exception
	 */
	void install(List<Long> nodeList, long versionId) throws Exception;
	
	/**
	 * 统一同一部署策略下的主机所有应用到当前版本
	 * @param deployStrategy
	 * @return
	 * @throws Exception
	 */
	void unifyToCurrentVersion(long deployStrategy) throws Exception;
	
	/**
	 * 回滚应用
	 * @param nodes
	 * @param versionId
	 * @throws Exception
	 */
	void rollback(List<Long> nodes, long versionId)throws Exception;
}
