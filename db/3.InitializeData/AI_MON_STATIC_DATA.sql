insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('JMX_STATE_CACHE', 'SERVER_ID', '应用标识', '设置表中VCODE是JMX_STATE的值则缓存主键为SERVER_ID', null, null, null, null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('LOCATOR_TYPE', 'Remote', '远程', '远程', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('LOCATOR_TYPE', 'Local', '本地', '本地', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('LOCK_FLAG', '0', '正常', '正常', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('LOCK_FLAG', '1', '锁定', '锁定', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('MONITOR_LAYER', 'BASE', '基础层', '基础层', 'LAYER', 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('MONITOR_LAYER', 'APPC', '应用层', '应用层', 'LAYER', 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('MONITOR_LAYER', 'BUSI', '业务层', '业务层', 'LAYER', 3, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('OBJ_TYPE', 'EXEC', '进程', '进程', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('OBJ_TYPE', 'TABLE', '表', '表', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('PHOST_SELECT_LIMIT', '10', '关注面板选择物理主机个数上限值', '关注面板选择物理主机个数上限值', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('RELATDOMAIN_TYPE', '1', '组与域', '组与域的关联', 'AI_MON_DOMAIN_RELAT', 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('RELATDOMAIN_TYPE', '2', '主机与域', '主机与域的关联', 'AI_MON_DOMAIN_RELAT', 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('RELATDOMAIN_TYPE', '3', '应用与域', '应用与域的关联', 'AI_MON_DOMAIN_RELAT', 3, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('RULE_TYPE', 'CTRACE', '采集TRACE', '采集TRACE的参数设置规则', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('RULE_TYPE', 'START', '启动', '应用启动的参数设置规则', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('RULE_TYPE', 'CJMXPRO', '采集Jmx配置', '采集Jmx配置的参数设置规则', null, 3, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('SELF_TYPE', '1', '系统所属', '系统所属', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('SELF_TYPE', '2', '扩展', '扩展', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('SERVER_TYPE', 'WAS', 'WAS服务器', 'WAS服务器', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('SERVER_TYPE', 'BEA', 'BEA服务器', 'BEA服务器', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('SERVER_TYPE', 'TOMCAT', 'TOMCAT服务器', 'TOMCAT服务器', null, 3, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TASK_METHOD', 'F', '固定时间', '固定时间', null, 1, 'E', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TASK_METHOD', 'C', '周期执行', '周期执行', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TASK_METHOD', 'I', '立即执行', '立即执行', null, 3, 'E', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TASK_METHOD', 'P', '面板执行', '面板执行', null, 4, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TEMP_TYPE', 'WEB', 'WEB应用', 'WEB', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TEMP_TYPE', 'OTHER', '其他', '其他', null, 5, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TEMP_TYPE', 'APP', 'APP应用', 'APP', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TEMP_TYPE', 'MEM', 'memcached', 'memcached', null, 3, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TEMP_TYPE', 'ITF', '接口应用', '接口应用', null, 4, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('WARN_SHOW_PERIOD', '1', '告警显示周期', '告警显示周期（以天为单位）', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('YES_NO', 'Y', '是', '是', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('YES_NO', 'N', '不是', '不是', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('LAYER', 'BASE', 'BASE', 'BASE', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('LAYER', 'BUSI', 'BUSI', 'BUSI', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('USER_TYPE', 'M', '监控用户类型', '给监控模块使用的用户类型', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('USER_TYPE', 'P', '发布用户类型', '给调度侧，应用发布部署的用户类型', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('DEPLOY_TYPE', 'EXEC', '进程', '进程', null, 6, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TIME_TYPE', 'CRON', 'CRON', 'CRON', 'ai_mon_p_time', 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('SSH_CHARENCODING', 'ISO_8859_1', 'SSH字符集', '执行远程SSH命令时,如果参数是中文的需要设置字符集', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('INTERVEL_TIME', '30', '地区闪烁停留时间', '地区闪烁停留时间', null, 15, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('BLINK_TIME', '1', '地区闪烁间隔时间', '地区闪烁间隔时间', null, 16, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('WARN_LEVEL', '1', '轻微警告', '轻微警告', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('WARN_LEVEL', '2', '普通警告', '普通警告', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('WARN_LEVEL', '3', '严重警告', '严重警告', null, 3, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TASK_STATE', 'U', '正常', '正常', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TASK_STATE', 'A', '全部', '全部', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TASK_STATE', 'S', '屏蔽', '屏蔽', null, 3, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TASK_STATE', 'E', '无效', '无效', null, 4, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('REGION_MAP', '0', '全部', null, null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('RUNTYPE', 'RUN_ONE', '执行一次', '运行类型', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('RUNTYPE', 'HT', '后台执行', '运行类型', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TRANSFORM_CLASS', 'com.asiainfo.appframe.ext.monitor.util.transform.HostChartTransformImpl', '主机CPUMEM资源', '主机CPUMEM资源', null, 9, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TRANSFORM_CLASS', 'com.asiainfo.appframe.ext.monitor.util.transform.DefaultDataTransformImpl', 'DefaultDataTransformImpl', 'DefaultDataTransformImpl', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TRANSFORM_CLASS', 'com.asiainfo.appframe.ext.monitor.util.transform.HostTransformImpl', 'HostTransformImpl', 'HostTransformImpl', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TRANSFORM_CLASS', 'com.asiainfo.appframe.ext.monitor.util.transform.CreditCtlTransformImpl', 'CreditCtlTransformImpl', 'CreditCtlTransformImpl', null, 3, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TRANSFORM_CLASS', 'com.asiainfo.appframe.ext.monitor.util.transform.OrdBatchTransformImpl', 'OrdBatchTransformImpl', 'OrdBatchTransformImpl', null, 4, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TRANSFORM_CLASS', 'com.asiainfo.appframe.ext.monitor.util.transform.ProvisionTransformImpl', 'ProvisionTransformImpl', 'ProvisionTransformImpl', null, 5, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TRANSFORM_CLASS', 'com.asiainfo.appframe.ext.monitor.util.transform.StopOpenTransformImpl', 'StopOpenTransformImpl', 'StopOpenTransformImpl', null, 6, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TRANSFORM_CLASS', 'com.asiainfo.appframe.ext.monitor.util.transform.VMTransformImpl', 'VMTransformImpl', 'VMTransformImpl', null, 7, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('TRANSFORM_CLASS', 'com.asiainfo.appframe.ext.monitor.util.transform.WKPaymentTransformImpl', 'WKPaymentTransformImpl', 'WKPaymentTransformImpl', null, 8, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'STATECOLUMNCHART', '状态柱图', '状态柱图', null, 8, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'DATAGRID', '表格', '表格', null, 12, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'DECLINECHART', '线图（可显示小数）', '线图（可显示小数）', null, 13, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'DECCOLUMNCHART', '柱状图（可显示小数）', '柱状图（可显示小数）', null, 14, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'DECBARCHART', '横向柱状图（可显示小数）', '横向柱状图（可显示小数）', null, 15, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'DECSTACKEDCOLUMNCHART', '切片柱图（可显示小数）', '切片柱图（可显示小数）', null, 16, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'DEC3DCOLUMNCHART', '3D柱状图（可显示小数）', '3D柱状图（可显示小数）', null, 17, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'DEC3DBARCHART', '3D横向柱状图（可显示小数）', '3D横向柱状图（可显示小数）', null, 18, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'DEC3DSTACKEDCOLUMNCHART', '3D切片柱图（可显示小数）', '3D切片柱图（可显示小数）', null, 19, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'AM3DCOLUMNCHART', '3D柱状图', '3D柱状图', null, 9, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'AM3DSTACKEDCOLUMNCHART', '3D切片柱图', '3D切片柱图', null, 11, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'AM3DBARCHART', '3D横向柱状图', '3D横向柱状图', null, 10, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'SCALER', '仪表盘', '仪表盘', null, 7, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'COLUMNCHART', '柱状图', '柱状图', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'BARCHART', '横向柱状图', '横向柱状图', null, 3, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'AREACHART', '区域图', '区域图', null, 5, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'STACKEDCOLUMNCHART', '切片柱图', '切片柱图', null, 4, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'PIECHART', '饼图', '饼图', null, 6, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'LINECHART', '线图', '线图', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'DATAGRIDLINECHART', '表格和线图综合', '表格和线图综合', null, 20, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'DATAGRIDCOLUMNCHART', '表格和柱图综合', '表格和柱图综合', null, 21, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('VIEW_TYPE', 'DATAGRIDPIECHART', '表格和饼图综合', '表格和饼图综合', null, 22, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.AIMonI18nResourceCacheImpl', '国际化缓存', '国际化缓存', null, 13, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.AIMonGroupCacheImpl', '组缓存', '组缓存', null, 1, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.AIMonNodeCacheImpl', '节点缓存', '节点缓存', null, 2, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.AIMonServerCacheImpl', '应用缓存', '应用缓存', null, 3, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.AIMonPInfoGroupCacheImpl', '任务分组缓存', '任务分组缓存', null, 4, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.AIMonUserPriEntityCacheImpl', '权限实体(菜单)缓存', '权限实体(菜单)缓存', null, 5, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.AIMonTaskCacheImpl', '任务缓存', '任务缓存', null, 6, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.AIMonAttentionPanelCacheImpl', '面板缓存', '面板缓存', null, 7, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.AIMonStaticDataCacheImpl', '数据字典缓存', '数据字典缓存', null, 8, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.AIMonSetCacheImpl', '基础设置缓存', '基础设置缓存', null, 9, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.TopuXmlCacheImpl', '组主机应用拓扑缓存', '组主机应用拓扑缓存', null, 10, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.TreeXmlCacheImpl', '组主机应用树缓存', '组主机应用树缓存', null, 11, 'U', null);
insert into AI_MON_STATIC_DATA (code_type, code_value, code_name, code_desc, code_type_alias, sort_id, state, extern_code_type)
values ('CACHE_TYPE', 'com.asiainfo.appframe.ext.monitor.cache.impl.AIMonUserRoleDomainCacheImpl', '用户角色权限缓存', '用户角色权限缓存', null, 12, 'U', null);
commit;