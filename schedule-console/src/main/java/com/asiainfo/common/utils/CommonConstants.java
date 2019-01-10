package com.asiainfo.common.utils;

/**
 * 公共常量类
 * @author lj
 *
 */
public interface CommonConstants {
	
	/**操作模块-调度 */
	String OPERATE_MODULE_SCHED = "调度";
	
	/**操作模块-监控 */
	String OPERATE_MODULE_MONITOR = "监控";
	
	/**操作模块-部署 */
	String OPERATE_MODULE_DEPLOY = "部署";
	
	/**操作模块-配置 */
	String OPERATE_MODULE_CONFIG = "配置";
	
	/**操作模块-启停 */
	String OPERATE_MODULE_START_STOP = "启停";
	
	/**操作对象-节点 */
	String OPERTATE_OBJECT_NODE = "节点";
	
	/**操作对象-主机 */
	String OPERTATE_OBJECT_HOST = "主机";
	
	/**操作对象-部署策略 */
	String OPERTATE_OBJECT_STRATEGY = "部署策略";
	
	/**操作对象-任务 */
	String OPERTATE_OBJECT_TASK ="任务";
	
	/**操作对象-任务分组 */
	String OPERTATE_OBJECT_TASKGROUP ="任务分组";
	
	/**操作对象-告警分组 */
	String OPERTATE_OBJECT_MONITOR_GROUP ="告警分组";
	
	/**操作对象-告警任务 */
	String OPERTATE_OBJECT_MONITOR_TASK ="告警任务";
	
	/**操作对象-告警任务相关配置数据 */
	String OPERTATE_OBJECT_MONITOR_TASK_PARAM ="告警任务相关配置数据";
	
	/**操作对象-主机集群 */
	String OPERTATE_OBJECT_HOSTGROUP ="主机集群";
	
	/**操作对象-应用 */
	String OPERTATE_OBJECT_APP = "应用";
	
	/**操作对象-用户 */
	String OPERTATE_OBJECT_UESR = "用户";
	
	/**操作类型-新增 */
	String OPERATE_TYPE_ADD = "新增";
	
	/**操作类型-删除 */
	String OPERATE_TYPE_DELETE ="删除";
	
	/**操作类型-修改 */
	String OPERATE_TYPE_MODIFY = "修改";
	
	/**操作类型-挂起 */
	String OPERATE_TYPE_HANGON = "挂起";
	
	/**操作类型-恢复 */
	String OPERATE_TYPE_RECOVER = "恢复";
	
	/**操作类型-启动 */
	String OPERATE_TYPE_START = "启动";
	
	/**操作类型-停止 */
	String OPERATE_TYPE_STOP = "停止";
	
	/**操作类型-主机初始化 */
	String OPERATE_TYPE_HOST_INIT = "主机初始化";
	
	/**操作类型-发布部署 */
	String OPERATE_TYPE_DEPLOY = "发布部署";
	
	/**操作类型-回滚 */
	String OPERATE_TYPE_ROLLBACK = "回滚";
	
	/**操作类型-同步至策略当前版本 */
	String OPERATE_TYPE_SYCHRONIZE = "同步至策略当前版本";
	
	/**操作日志查询条件-开始时间 */
	String OPERATION_QRY_START = "startTime";
	
	/**操作日志查询条件-结束时间 */
	String OPERATION_QRY_END = "endTime";
}
