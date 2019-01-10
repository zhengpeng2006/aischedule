package com.asiainfo.deploy.dao.interfaces;

import java.util.List;

import com.asiainfo.deploy.common.bo.BODeployNodeVersionBean;

/**
 * 节点版本表
 * @author 孙德东(24204)
 */
public interface INodeRefVersionDAO {

	/**
	 * 根据节点的id获取节点的版本信息
	 * @param nodes
	 * @return
	 * @throws Exception 
	 */
	BODeployNodeVersionBean[] qryNodeVersionsByNodes(List<Long> nodes) throws Exception;
	/**
	 * 新增或修改节点引用的版本
	 * @param nodeId
	 * @param versionId
	 * @throws Exception 
	 */
    void saveNodeVersion(long nodeId, long versionId) throws Exception;
}
