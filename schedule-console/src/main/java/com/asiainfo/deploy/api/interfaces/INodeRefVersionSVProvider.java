package com.asiainfo.deploy.api.interfaces;


/**
 * 节点版本
 * 
 * @author 孙德东(24204)
 */
public interface INodeRefVersionSVProvider {

	public void saveNodeVersion(long nodeId, long versionId) throws Exception;
}
