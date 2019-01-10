package com.asiainfo.deploy.test;

import com.ai.appframe2.common.AIException;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.api.interfaces.IDeployStrategySVProvider;

import junit.framework.TestCase;

/**
 * 节点部署策略的测试类
 * @author 孙德东(24204)
 */
public class DeployStrategySVProviderImplTest extends TestCase {

	private long addedNodeId = 2L;
	private IDeployStrategySVProvider strategy = (IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class);
	
	public void testAddNodeDeployStrategy() {
		
		try {
			strategy.addNodeDeployStrategy(addedNodeId, 1L);
		} catch (AIException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	public void testModifyNodeDeployStrategy() {
		try {
			strategy.modifyNodeDeployStrategy(addedNodeId, 2L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testDeleteDeployStrategyByNodeId() {
		try {
			strategy.deleteNodeDeployStrategy(addedNodeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
