/*
 * 所有的请求都在这里咯
 */
define(["request"], function() {
	
	/*
	 * 
	 */
	function init() {
		
		/*
		 * 一级目录
		 */
		Request.set("index/loadTreeData", "index/loadTreeData.json", "");
		
		/*
		 * 用户配置-查询
		 */
		Request.set("config/user/loadUsersInfo", "config/user/loadUsersInfo.json", "service=ajax&page=module.monitor.configmgr.UsersInfo&listener=loadUsersInfo");
		
		/*
		 * 用户配置-新增，修改
		 */
		Request.set("config/user/saveOrUpdate", "common/success.json", "service=ajax&page=module.monitor.configmgr.UsersInfo&listener=saveOrUpdate");
		
		/*
		 * 用户配置-删除
		 */
		Request.set("config/user/delUser", "common/success.json", "service=ajax&page=module.monitor.configmgr.UsersInfo&listener=delUser");
		
		/*
		 * 部署策略配置-查询
		 */
		Request.set("config/strategy/qryStrategies", "config/strategy/qryStrategies.json", "service=ajax&page=module.deploy.StrategyCfg&listener=qryStrategies");
		
		/*
		 * 部署策略配置-新增，修改
		 */
		Request.set("config/strategy/saveStrategy", "common/success.json", "service=ajax&page=module.deploy.StrategyCfg&listener=saveStrategy");
		
		/*
		 * 部署策略配置-删除
		 */
		Request.set("config/strategy/deleteStrategy", "common/success.json", "service=ajax&page=module.deploy.StrategyCfg&listener=deleteStrategy");

		/*
		 * 部署策略配置-保存安装规则
		 */
		Request.set("config/strategy/saveRules", "common/success.json", "service=ajax&page=module.deploy.StrategyCfg&listener=saveRules");
		
		/*
		 * 启停模板配置-查询
		 */
		Request.set("config/strategy/qryTemplate", "config/strategy/qryTemplate.json", "service=ajax&page=module.deploy.TemplateCfg&listener=qryTemplate");
		
		/*
		 * 启停模板配置-新增，修改
		 */
		Request.set("config/strategy/saveTemplate", "common/success.json", "service=ajax&page=module.deploy.TemplateCfg&listener=saveTemplate");
		
		/*
		 * 启停模板配置-删除
		 */
		Request.set("config/strategy/deleteTemplate", "common/success.json", "service=ajax&page=module.deploy.TemplateCfg&listener=deleteTemplate");
		
		/*
		 * 调度任务配置-查询
		 */
		Request.set("config/task/qryTask", "config/task/qryTask.json", "service=ajax&page=module.schedule.ScheduleTaskCfg&listener=qryTask");
		
		/*
		 * 调度任务配置-查询
		 */
		Request.set("config/task/qryTaskName", "config/task/qryTaskName.json", "service=ajax&page=module.schedule.ScheduleTaskCfg&listener=qryTaskName");
		
		/*
		 * 调度任务配置-新增，修改
		 */
		Request.set("config/task/saveTask", "common/success.json", "service=ajax&page=module.schedule.ScheduleTaskCfg&listener=saveTask");
		
		/*
		 * 调度任务配置-删除
		 */
		Request.set("config/task/deleteTask", "common/success.json", "service=ajax&page=module.schedule.ScheduleTaskCfg&listener=deleteTask");
		
		/*
		 * 调度任务配置-查询任务分组
		 */
		Request.set("config/task/qryTaskGroup", "config/task/qryTaskGroup.json", "service=ajax&page=module.schedule.TaskGroupCfg&listener=qryGroup");
		
		/*
		 * 调度任务配置-新增，修改任务分组
		 */
		Request.set("config/task/saveTaskGroup", "common/success.json", "service=ajax&page=module.schedule.TaskGroupCfg&listener=saveGroup");
		
		/*
		 * 调度任务配置-删除任务分组
		 */
		Request.set("config/task/delTaskGroup", "common/success.json", "service=ajax&page=module.schedule.TaskGroupCfg&listener=deleteGroup");
		
		/*
		 * 调度任务配置-查询参数
		 */
		Request.set("config/task/qryTaskParam", "config/task/qryTaskParam.json", "service=ajax&page=module.schedule.TaskParamCfg&listener=qryParam");
		
		/*
		 * 调度任务配置-新增，修改参数
		 */
		Request.set("config/task/saveTaskParam", "common/success.json", "service=ajax&page=module.schedule.TaskParamCfg&listener=saveParam");
		
		/*
		 * 调度任务配置-删除参数
		 */
		Request.set("config/task/delTaskParam", "common/success.json", "service=ajax&page=module.schedule.TaskParamCfg&listener=deleteParam");
		
		/*
		 * 调度任务配置-查询分片
		 */
		Request.set("config/task/qryTaskSplit", "config/task/qryTaskSplit.json", "service=ajax&page=module.schedule.SplitCfg&listener=qrySplit");
		
		/*
		 * 调度任务配置-保存分片
		 */
		Request.set("config/task/saveTaskSplit", "common/success.json", "service=ajax&page=module.schedule.SplitCfg&listener=saveSplits");
		
		/*
		 * 调度任务配置-根据查询主机查询应用编码
		 */
		Request.set("config/task/qryAppCode", "config/task/qryAppCode.json", "service=ajax&page=module.schedule.SplitCfg&listener=getAppcodeList");
		
		/*
		 * 调度任务配置-查询所有主机
		 */
		Request.set("config/task/qryAllHostInfo", "config/task/qryAllHostInfo.json", "service=ajax&page=module.monitor.configmgr.HostInfo&listener=qryAllHostInfo");
		
		/*
		 * 调度任务配置-启用任务
		 */
		Request.set("config/task/enable", "common/success.json", "service=ajax&page=module.schedule.ScheduleTaskCfg&listener=enable");
		
		/*
		 * 调度任务配置-tf/reload配置查询目标表
		 */
		Request.set("config/task/qryDestTable", "config/task/qryDestTable.json", "service=ajax&page=module.schedule.TfCfg&listener=qryData");
		
		/*
		 * 调度任务配置-tf/reload配置保存目标表
		 */
		Request.set("config/task/saveDestTable", "common/success.json", "service=ajax&page=module.schedule.TfCfg&listener=saveTfDtl");
		
		/*
		 * 调度任务配置-tf/reload配置删除目标表
		 */
		Request.set("config/task/delDestTable", "common/success.json", "service=ajax&page=module.schedule.TfCfg&listener=deleteDtlCfg");
		
		/*
		 * 调度任务配置-tf/reload配置保存源表
		 */
		Request.set("config/task/saveTfCfg", "common/success.json", "service=ajax&page=module.schedule.TfCfg&listener=saveTfCfg");
		
		/*
		 * 调度任务配置-tf/reload配置查询映射关系
		 */
		Request.set("config/task/qryCols", "config/task/qryCols.json", "service=ajax&page=module.schedule.ColRel&listener=qryCols");
		
		/*
		 * 调度任务配置-tf/reload配置保存映射关系
		 */
		Request.set("config/task/addCol", "common/success.json", "service=ajax&page=module.schedule.ColRel&listener=addCol");
		
		/*
		 * 调度任务配置-tf/reload配置删除映射关系
		 */
		Request.set("config/task/deleteCols", "common/success.json", "service=ajax&page=module.schedule.ColRel&listener=deleteCols");
		
		/*
		 * 主机配置-查询主机分组
		 */
		Request.set("config/host/qryHostGroup", "config/host/qryHostGroup.json", "service=ajax&page=module.monitor.configmgr.BaseConfigPortal&listener=loadTableData");
		
		/*
		 * 主机配置-新增，修改主机分组
		 */
		Request.set("config/host/saveHostGroup", "common/success.json", "service=ajax&page=module.monitor.configmgr.GroupInfo&listener=saveOrUpdate");
		
		/*
		 * 主机配置-删除主机分组
		 */
		Request.set("config/host/delHostGroup", "common/success.json", "service=ajax&page=module.monitor.configmgr.BaseConfigPortal&listener=delTableRec");
		
		/*
		 * 主机配置-导出
		 */
		Request.set("config/host/export", "", "export?opertion=hostExport");
		
		/*
		 * 主机配置-查询
		 */
		Request.set("config/host/qryHost", "config/host/qryHost.json", "service=ajax&page=module.monitor.configmgr.BaseConfigPortal&listener=loadTableData");
		
		/*
		 * 主机配置-新增，修改
		 */
		Request.set("config/host/saveOrUpdate", "common/success.json", "service=ajax&page=module.monitor.configmgr.HostInfo&listener=saveOrUpdate");
		
		/*
		 * 主机配置-删除
		 */
		Request.set("config/host/delHost", "common/success.json", "service=ajax&page=module.monitor.configmgr.BaseConfigPortal&listener=delTableRec");
		
		/*
		 * 主机配置-查询连接方式
		 */
		Request.set("config/host/qryConModeInfo", "config/host/qryConModeInfo.json", "service=ajax&page=module.monitor.configmgr.ConModeInfo&listener=qryConModeInfo");
		
		/*
		 * 主机配置-新增,修改连接方式
		 */
		Request.set("config/host/saveConModeInfo", "common/success.json", "service=ajax&page=module.monitor.configmgr.ConModeInfo&listener=saveOrUpdate");
		
		/*
		 * 主机配置-删除连接方式
		 */
		Request.set("config/host/delConMode", "common/success.json", "service=ajax&page=module.monitor.configmgr.ConModeInfo&listener=delConMode");
		
		/*
		 * 主机配置-查询主备机
		 */
		Request.set("config/host/qryMasterSlaveInfo", "config/host/qryMasterSlaveInfo.json", "service=ajax&page=module.monitor.configmgr.MasterSlaveInfo&listener=qryMasterSlaveInfo");
		
		/*
		 * 主机配置-新增,修改主备机
		 */
		Request.set("config/host/saveMasterSlaveInfo", "common/success.json", "service=ajax&page=module.monitor.configmgr.MasterSlaveInfo&listener=saveOrUpdate");
		
		/*
		 * 主机配置-删除主备机
		 */
		Request.set("config/host/delSlave", "common/success.json", "service=ajax&page=module.monitor.configmgr.MasterSlaveInfo&listener=delSlave");
		
		/*
		 * 主机节点配置-查询
		 */
		Request.set("config/host/qryNode", "config/host/qryNode.json", "service=ajax&page=module.monitor.configmgr.BaseConfigPortal&listener=loadTableData");
		
		/*
		 * 主机节点配置-新增，修改
		 */
		Request.set("config/host/saveNode", "common/success.json", "service=ajax&page=module.monitor.configmgr.NodeInfo&listener=saveOrUpdate");
		
		/*
		 * 主机节点配置-删除
		 */
		Request.set("config/host/delNode", "common/success.json", "service=ajax&page=module.monitor.configmgr.BaseConfigPortal&listener=delTableRec");
		
		/*
		 * 主机节点配置-查询节点用户
		 */
		Request.set("config/host/qryNodeUserInfo", "config/host/qryNodeUserInfo.json", "service=ajax&page=module.monitor.configmgr.NodeUserInfo&listener=qryNodeUserInfo");
		
		/*
		 * 主机节点配置-保存节点用户
		 */
		Request.set("config/host/saveNodeUser", "common/success.json", "service=ajax&page=module.monitor.configmgr.NodeUserInfo&listener=saveOrUpdate");
		
		/*
		 * 主机节点配置-删除节点用户
		 */
		Request.set("config/host/delNodeUser", "common/success.json", "service=ajax&page=module.monitor.configmgr.NodeUserInfo&listener=delNodeUser");
		
		/*
		 * 主机应用配置-查询
		 */
		Request.set("config/host/qryApp", "config/host/qryApp.json", "service=ajax&page=module.monitor.configmgr.BaseConfigPortal&listener=loadTableData");
		
		/*
		 * 主机应用配置-新增，修改
		 */
		Request.set("config/host/saveApp", "common/success.json", "service=ajax&page=module.monitor.configmgr.ServerInfo&listener=saveOrUpdate");
		
		/*
		 * 主机应用配置-删除
		 */
		Request.set("config/host/delApp", "common/success.json", "service=ajax&page=module.monitor.configmgr.BaseConfigPortal&listener=delTableRec");
		
		/*
		 * 主机应用配置-查询部署参数
		 */
		Request.set("config/host/qryParamInfo", "config/host/qryParamInfo.json", "service=ajax&page=module.monitor.configmgr.DeployAppParamInfo&listener=qryParamInfo");
		
		/*
		 * 主机应用配置-新增，修改部署参数
		 */
		Request.set("config/host/saveParam", "common/success.json", "service=ajax&page=module.monitor.configmgr.DeployAppParamInfo&listener=saveOrUpdate");
		
		/*
		 * 主机应用配置-删除部署参数
		 */
		Request.set("config/host/delParam", "common/success.json", "service=ajax&page=module.monitor.configmgr.DeployAppParamInfo&listener=delParam");
		
		/*
		 * 主机应用配置-查询备用进程
		 */
		Request.set("config/host/qryBackupServerCode", "config/host/qryBackupServerCode.json", "service=ajax&page=module.monitor.configmgr.MasterSlaveInfo&listener=qryBackupServerCode");
		
		/*
		 * 主机应用配置-新增备用进程
		 */
		Request.set("config/host/saveBackupServerCode", "common/success.json", "service=ajax&page=module.monitor.configmgr.MasterSlaveInfo&listener=saveBackupServerCode");
		
		/*
		 * 主机应用配置-删除备用进程
		 */
		Request.set("config/host/deleteBackupServerCode", "common/success.json", "service=ajax&page=module.monitor.configmgr.MasterSlaveInfo&listener=deleteBackupServerCode");
		
		/*
		 * 主机应用配置-根据主机id查询应用列表
		 */
		Request.set("config/host/qryServerInfosByHostId", "config/host/qryServerInfosByHostId.json", "service=ajax&page=module.monitor.configmgr.ServerInfo&listener=qryServerInfosByHostId");
		
		/*
		 * 主机应用配置-复制应用
		 */
		Request.set("config/host/copyServer", "common/success.json", "service=ajax&page=module.monitor.configmgr.NodeTreeInfo&listener=copyServer");
		
		/*
		 * 监控任务配置-查询监控任务分组
		 */
		Request.set("config/monitor/qryMonitorGroup", "config/monitor/qryMonitorGroup.json", "service=ajax&page=module.monitor.configmgr.PInfoConfig&listener=loadTableData");
		
		/*
		 * 监控任务配置-新增，修改监控任务分组
		 */
		Request.set("config/monitor/saveMonitorGroup", "common/success.json", "service=ajax&page=module.monitor.configmgr.PInfoConfig&listener=auto_saveOrUpdate_groupDataSaveOnclick");
		
		/*
		 * 监控任务配置-删除监控任务分组
		 */
		Request.set("config/monitor/delMonitorGroup", "common/success.json", "service=ajax&page=module.monitor.configmgr.PInfoConfig&listener=auto_delete_groupDeleteOnclick");
		
		/*
		 * 监控任务配置-查询
		 */
		Request.set("config/monitor/qryMonitor", "config/monitor/qryMonitor.json", "service=ajax&page=module.monitor.configmgr.PInfoConfig&listener=loadTableData");
		
		/*
		 * 监控任务配置-新增，修改
		 */
		Request.set("config/monitor/saveMonitor", "common/success.json", "service=ajax&page=module.monitor.configmgr.PInfoConfig&listener=auto_saveOrUpdate_infoDataSaveOnclick");
		
		/*
		 * 监控任务配置-删除
		 */
		Request.set("config/monitor/delMonitor", "common/success.json", "service=ajax&page=module.monitor.configmgr.PInfoConfig&listener=auto_delete_infoDeleteOnclick");
		
		/*
		 * 监控任务配置-查询参数
		 */
		Request.set("config/monitor/qryMonitorParam", "config/monitor/qryMonitorParam.json", "service=ajax&page=module.monitor.configmgr.ParamValueCfg&listener=auto_getParamValuesByType_queryButtonOnclick");
		
		/*
		 * 监控任务配置-新增，修改参数
		 */
		Request.set("config/monitor/saveMonitorParam", "common/success.json", "service=ajax&page=module.monitor.configmgr.ParamValueCfg&listener=auto_saveOrUpdate_paramSaveOnclick");
		
		/*
		 * 监控任务配置-删除参数
		 */
		Request.set("config/monitor/delMonitorParam", "common/success.json", "service=ajax&page=module.monitor.configmgr.ParamValueCfg&listener=auto_deleteParamValues_deleteOnclick");
		
		/*
		 * 监控任务配置-查询监控脚本
		 */
		Request.set("config/monitor/qryMonitorScript", "config/monitor/qryMonitorScript.json", "service=ajax&page=module.monitor.configmgr.PExecConfig&listener=auto_getExecByNameAndExpr_queryOnclick");
		
		/*
		 * 监控任务配置-新增，修改监控脚本
		 */
		Request.set("config/monitor/saveMonitorScript", "common/success.json", "service=ajax&page=module.monitor.configmgr.PExecConfig&listener=auto_saveOrUpdate_saveOnclick");
		
		/*
		 * 监控任务配置-删除监控脚本
		 */
		Request.set("config/monitor/delMonitorScript", "common/success.json", "service=ajax&page=module.monitor.configmgr.PExecConfig&listener=auto_delete_deleteOnclick");
	    
		/*
		 * 监控任务配置-选择监控脚本
		 */
		Request.set("config/monitor/selectMonitorScript", "common/success.json", "service=ajax&page=module.monitor.configmgr.PExecConfig&listener=auto_saveOrUpdate_dataSaveOnclick");
		
		/*
		 * 监控任务配置-查询监控阀值
		 */
		Request.set("config/monitor/qryMonitorThreshold", "config/monitor/qryMonitorThreshold.json", "service=ajax&page=module.monitor.controlmgr.PThresholdConfig&listener=auto_getThresholdByExprAndName_queryOnclick");
		
		/*
		 * 监控任务配置-新增，修改监控阀值
		 */
		Request.set("config/monitor/saveMonitorThreshold", "common/success.json", "service=ajax&page=module.monitor.controlmgr.PThresholdConfig&listener=auto_saveOrUpdate_saveOnclick");
		
		/*
		 * 监控任务配置-删除监控阀值
		 */
		Request.set("config/monitor/delMonitorThreshold", "common/success.json", "service=ajax&page=module.monitor.controlmgr.PThresholdConfig&listener=auto_delete_deleteOnclick");
		
		/*
		 * 监控任务配置-选择监控阀值
		 */
		Request.set("config/monitor/selectMonitorThreshold", "common/success.json", "service=ajax&page=module.monitor.controlmgr.PThresholdConfig&listener=auto_saveOrUpdate_dataSelectOnclick");
		
		/*
		 * 监控任务配置-查询监控时间
		 */
		Request.set("config/monitor/qryMonitorTime", "config/monitor/qryMonitorTime.json", "service=ajax&page=module.monitor.configmgr.PTimeConfig&listener=auto_getTimeInfoByExpr_queryOnclick");
		
		/*
		 * 监控任务配置-新增，修改时间
		 */
		Request.set("config/monitor/saveMonitorTime", "common/success.json", "service=ajax&page=module.monitor.configmgr.PTimeConfig&listener=auto_saveOrUpdate_saveOnclick");
		
		/*
		 * 监控任务配置-删除监控时间
		 */
		Request.set("config/monitor/delMonitorTime", "common/success.json", "service=ajax&page=module.monitor.configmgr.PTimeConfig&listener=auto_delete_deleteOnclick");
		
		/*
		 * 监控任务配置-选择监控时间
		 */
		Request.set("config/monitor/selectMonitorTime", "common/success.json", "service=ajax&page=module.monitor.configmgr.PTimeConfig&listener=auto_saveOrUpdate_dataSelectOnclick");
		
		/*
		 * 任务执行日志监控-根据任务分组查询任务编码
		 */
		Request.set("manage/showTaskCodes", "manage/showTaskCodes.json", "service=ajax&page=module.deploy.QryTaskLog&listener=showTaskCodes");
		
		/*
		 * 任务执行日志监控-查询
		 */
		Request.set("manage/qryLogs", "manage/qryLogs.json", "service=ajax&page=module.deploy.QryTaskLog&listener=qryLogs");
		
		/*
		 * 任务执行日志监控-查询详情
		 */
		Request.set("manage/qryLogDetail", "manage/qryLogDetail.json", "service=ajax&page=module.deploy.QryTaskLog&listener=qryDetail");
		
		/*
		 * 任务当天执行报表-查询
		 */
		Request.set("manage/qryTaskInfos", "manage/qryTaskInfos.json", "service=ajax&page=module.deploy.TaskStatus&listener=qryTaskInfos");
		
		/*
		 * 任务当天执行报表-查询任务编码
		 */
		Request.set("manage/qryTaskCode", "manage/qryTaskCode.json", "service=ajax&page=module.deploy.TaskStatus&listener=qryTaskCode");
		
		/*
		 * 任务执行状态监控-业务纬度查询
		 */
		Request.set("manage/qryTaskStateByBusi", "manage/qryTaskStateByBusi.json", "service=ajax&page=module.deploy.TaskOperation&listener=qryTaskInfos");
		
		/*
		 * 任务执行状态监控-启动
		 */
		Request.set("manage/startTaskNow", "common/success.json", "service=ajax&page=module.deploy.TaskOperation&listener=startTaskNow");
		
		/*
		 * 任务执行状态监控-挂起
		 */
		Request.set("manage/hangOn", "common/success.json", "service=ajax&page=module.deploy.TaskOperation&listener=hangOn");
		
		/*
		 * 任务执行状态监控-查询任务参数
		 */
		Request.set("manage/showParam", "manage/showParam.json", "service=ajax&page=module.deploy.TaskOperation&listener=showParam");
		
		/*
		 * 任务执行状态监控-业务纬度查询详情
		 */
		Request.set("manage/showDetails", "manage/showDetails.json", "service=ajax&page=module.deploy.TaskOperation&listener=showDetails");
		
		/*
		 * 任务执行状态监控-主机纬度查询
		 */
		Request.set("manage/qryTaskStateByHost", "manage/qryTaskStateByHost.json", "service=ajax&page=module.deploy.TaskOperation&listener=qryPro");
		
		/*
		 * 任务执行状态监控-主机纬度查询详情
		 */
		Request.set("manage/showTaskPros", "manage/showTaskPros.json", "service=ajax&page=module.deploy.TaskOperation&listener=showTaskPros");
		
		/*
		 * 应用发布-查询
		 */
		Request.set("publish/qryDeploy", "publish/qryDeploy.json", "service=ajax&page=module.deploy.Deploy&listener=loadTableData");
		
		/*
		 * 应用发布-查询版本号详情
		 */
		Request.set("publish/showVersionDetail", "publish/showVersionDetail.json", "service=ajax&page=module.deploy.Deploy&listener=showVersionDetail");
		
		/*
		 * 应用发布-查询历史版本
		 */
		Request.set("publish/showHisVersion", "publish/showHisVersion.json", "service=ajax&page=module.deploy.Deploy&listener=showHisVersion");
		
		/*
		 * 应用发布-初始化
		 */
		Request.set("publish/initHost", "common/success.json", "service=ajax&page=module.deploy.Deploy&listener=initHost");
		
		/*
		 * 应用发布-发布
		 */
		Request.set("publish/saveVersion", "common/success.json", "service=ajax&page=module.deploy.Deploy&listener=saveVersion");
		
		/*
		 * 应用发布-回滚
		 */
		Request.set("publish/rollback", "common/success.json", "service=ajax&page=module.deploy.Deploy&listener=rollback");
		
		/*
		 * 应用发布-同步
		 */
		Request.set("publish/updateToCurVersion", "common/success.json", "service=ajax&page=module.deploy.Deploy&listener=updateToCurVersion");
		
		/*
		 * 应用启停-查询
		 */
		Request.set("publish/qryProcess", "publish/qryProcess.json", "service=ajax&page=module.schedule.ProcessControl&listener=qryProcess");
		
		/*
		 * 应用启停-启停
		 */
		Request.set("publish/operateProcess", "publish/operateProcess.json", "service=ajax&page=module.schedule.ProcessControl&listener=operateProcess");
		
		/*
		 * 应用启停-详情
		 */
		Request.set("publish/showTaskDetail", "publish/showTaskDetail.json", "service=ajax&page=module.schedule.ProcessControl&listener=showTaskDetail");
		
		/*
		 * 监控-主机监控-选择主机组,查询该组下的所有主机
		 */
		Request.set("monitor/host/qryHostList", "monitor/host/qryHostList.json", "service=ajax&page=module.monitor.portal.MonProcInfoPortal&listener=loadTableData");
		
		/*
		 * 监控-主机监控-主机分组下的kpi
		 */
		Request.set("monitor/host/qryKPI", "monitor/host/qryKPI.json", "service=ajax&page=module.monitor.portal.MonProcInfoPortal&listener=loadKpiInitData");
		
		/*
		 * 监控-主机监控-主机信息展示
		 */
		Request.set("monitor/host/qryHostInfo", "monitor/host/qryHostInfo.json", "service=ajax&page=module.monitor.portal.MonProcInfoPortal&listener=loadTableData");
		
		/*
		 * 
		 */
		Request.set("monitor/host/fileSysRatio", "monitor/host/fileSysRatio.json", "service=ajax&page=module.monitor.portal.MonProcInfoPortal&listener=loadFsInfo");
		
		/*
		 * 监控-主机监控-主机下的kpi(暂时没用到)
		 */
		Request.set("monitor/host/qryHostKPI", "monitor/host/qryKPI.json", "service=ajax&page=module.monitor.portal.MonProcInfoPortal&listener=qryHostKpiHisInfo");
		
		/*
		 * 监控-主机监控-应用进程信息展示
		 */
		Request.set("monitor/host/qryProcess", "monitor/host/qryProcess.json", "service=ajax&page=module.monitor.portal.MonProcInfoPortal&listener=loadProcessData");

		/*
		 * 监控-主机监控-非应用进程信息展示
		 */
		Request.set("monitor/host/qryProcKpi", "monitor/host/qryProcKpi.json", "service=ajax&page=module.monitor.portal.MonProcInfoPortal&listener=loadProcKpiData");
		
		/*
		 * 监控-主机监控-任务信息
		 */
		Request.set("monitor/host/taskInfo", "monitor/host/taskInfo.json", "service=ajax&page=module.monitor.portal.MonProcInfoPortal&listener=loadTaskInfo");
		
		/*
		 * 监控-操作日志-列表查询
		 */
		Request.set("monitor/log/logList", "monitor/log/logList.json", "service=ajax&page=module.monitor.configmgr.OperationLogQry&listener=qryLog");
		
		/*
		 * 监控-告警监控-列表查询
		 */
		Request.set("monitor/warning/warningList", "monitor/warning/warningList.json", "service=ajax&page=module.monitor.monitorItem.TriggerInfo&listener=loadWTriggerInfo");
		
		/*
		 * 监控-进程状态监控
		 */
		Request.set("monitor/status/WarningInfo", "monitor/status/WarningInfo.json", "service=ajax&page=module.monitor.monitorItem.WarningInfo&listener=loadTableData");
		
		/*
		 * 监控-进程状态监控-版本检查
		 */
		Request.set("monitor/status/version", "monitor/status/version.json", "service=ajax&page=module.monitor.monitorItem.WarningInfo&listener=checkVersion");
		
		/*
		 * 监控-进程状态监控-任务详情
		 */
		Request.set("monitor/status/detail", "monitor/status/detail.json", "service=ajax&page=module.monitor.monitorItem.WarningInfo&listener=loadTaskInfo");
		
		/*
		 * 监控-业务监控
		 */
		Request.set("monitor/bus/busTree", "monitor/bus/busTree.json", "service=ajax&page=module.monitor.portal.MonBusiInfoPortal&listener=loadTreeData");
		
		/*
		 * 监控-业务监控-任务及分片信息
		 */
		Request.set("monitor/bus/busTask", "monitor/bus/busTask.json", "service=ajax&page=module.monitor.portal.MonBusiInfoPortal&listener=getFirstList");
		
		/*
		 * 监控-业务监控-进程信息
		 */
		Request.set("monitor/bus/busProc", "monitor/bus/busProc.json", "service=ajax&page=module.monitor.portal.MonBusiInfoPortal&listener=getServerCode");
		
		/*
		 * 监控-业务监控-业务信息
		 */
		Request.set("monitor/bus/busChart", "monitor/bus/busChart.json", "service=ajax&page=module.monitor.portal.MonBusiInfoPortal&listener=queryHisDataQry");
		
	}
	
	return {
		init: init
	};
	
});