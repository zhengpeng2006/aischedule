package com.asiainfo.schedule.util;

/**
 * @author lj 任务调度常量类
 */
public interface ScheduleConstants {

	/** 参数：任务分组(业务维度) */
	String PARAM_TASK_GROUP = "taskGroup";

	/** 参数：任务编码(主机查询时此数据为应用编码) */
	String PARAM_TASK_CODE = "taskCode";

	/** 参数：任务名称(业务，主机维度共用) */
	String PARAM_TASK_NAME = "taskName";
	
	/** 参数：任务类型(业务维度)  */
	String PARAM_TASK_TYPE = "taskType";
	
	String PARAM_TASK_STATE = "taskState";

	/** 参数：主机集群(主机维度) */
	String PARAM_HOST_GROUP = "hostGroup";

	/** 参数：主机名(主机维度) */
	String PARAM_HOST_NAME = "hostName";

	/** 参数：主机IP(主机维度) */
	String PARAM_HOST_IP = "hostIp";

	/** 参数：主机Id(业务，主机维度共用) */
	String PARAM_HOST_ID = "hostId";
	
	/** 参数：节点Id(主机维度) */
	String PARAM_NODE_ID = "nodeId";

	/** 参数：节点名称(主机维度) */
	String PARAM_NODE = "nodeNameStr";

	/** 参数：应用编码(业务，主机维度共用) */
	String PARAM_SERVER_CODE = "serverCode";
	
	/** 参数：进程状态 */
	String PARAM_PROCESS_STATE = "processState";
	
	/** 参数：进程状态运行中 */
	String PARAM_STATE_RUNNING = "T";
	
	/** 进程启停操作类型：启动进程 */
	String PROCESS_OPERATION_START = "1";

	/** 进程启停操作类型：关闭进程 */
	String PROCESS_OPERATION_STOP = "2";

	/** 进程启停操作类型：检查进程 暂时没用 */
	String PROCESS_OPERATION_CHECK = "3";
	
	/** 任务日志状态：执行中 */
	String TASK_LOG_STATE_C = "C";
	
	/** 任务日志状态：结束 */
	String TASK_LOG_STATE_F = "F";
	
	/** 任务日志状态：异常 */	
	String TASK_LOG_STATE_E = "E";
	
	/** 登录用户名*/	
	String LOGIN_USER_NAME = "system";
	
	/** 登录密码 */	
	String LOGIN_PASS_WORD = "loginPassword";
	
	/** 获取菜单参数：FUNC_TYPE */
    String FUNC_TYPE = "20";
    
	/**设置domainID */
	long DOMAIN_ID= 85L;
	
}
