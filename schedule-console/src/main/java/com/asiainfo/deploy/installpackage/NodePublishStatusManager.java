package com.asiainfo.deploy.installpackage;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.deploy.common.constants.Category.NodeStatus;

/**
 * 节点状态管理
 * 
 * @author 孙德东(24204)
 */
public class NodePublishStatusManager {

	private static Map<Long, NodePublishStatus> container = new HashMap<Long, NodePublishStatus>();

	private NodePublishStatusManager() {
	}

	/**
	 * 设置节点状态
	 * @param nodeId
	 * @param result
	 */
	public static void setNodeStatus(Long nodeId, NodePublishStatus result) {
		container.put(nodeId, result);
	}

	/**
	 * 返回节点的发布状态
	 * @param nodeId
	 * @return
	 */
	public static NodePublishStatus getNodeStatus(Long nodeId) {
		return container.get(nodeId);
	}

	/**
	 * 节点状态的抽象
	 * @author 孙德东(24204)
	 */
	public static class NodePublishStatus {
		private NodeStatus status;
		private String message;

		public NodePublishStatus(NodeStatus status, String message) {
			this.status = status;
			this.message = message;
		}

		public NodePublishStatus(NodeStatus status) {
			this.status = status;
			this.message = StringUtils.EMPTY;
		}

		public NodeStatus status() {
			return this.status;
		}

		public String message() {
			return this.message;
		}
	}
}
