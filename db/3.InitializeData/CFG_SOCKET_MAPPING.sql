insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (2, 'DEFAULT', 'max-connection', '500', 'U', '最大连接数(本配置为500个最大连接)');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (4, 'DEFAULT', 'timeout', '-1', 'U', '超时时间(本配置为永不超时)');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (6, 'DEFAULT', 'console-logging-formatter', 'org.quickserver.util.logging.SimpleConsoleFormatter', 'U', '日志格式化的实现类');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (8, 'DEFAULT', 'default-data-mode.data-type-in', 'STRING', 'U', '数据传入的类型(BINARY,STRING,OBJECT,BYTE  本配置为二进制)');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (10, 'DEFAULT', 'object-pool.max-active', '-1', 'U', '??');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (12, 'DEFAULT', 'object-pool.thread-object-pool.init-size', '30', 'U', '工作线程池的初始值');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (14, 'DEFAULT', 'object-pool.thread-object-pool.max-idle', '50', 'U', '工作线程池的最大剩余值');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (16, 'DEFAULT', 'object-pool.client-handler-object-pool.max-idle', '50', 'U', '??');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (18, 'DEFAULT', 'object-pool.byte-buffer-object-pool.max-idle', '50', 'U', '??');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (20, 'DEFAULT', 'object-pool.client-data-object-pool.max-idle', '50', 'U', '??');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (22, 'DEFAULT', 'advanced-settings.use-direct-byte-buffer', 'true', 'U', '是否使用直接ByteBuffer模式');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (24, 'DEFAULT', 'advanced-settings.socket-linger', '-1', 'U', 'socket拖延时间');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (26, 'DEFAULT', 'client-command-handler', 'com.asiainfo.appframe.ext.exeframe.socket.handler.DefaultCommandHandler', 'U', 'com.asiainfo.boss.common.exe.socket.handler.DefaultCommandHandler');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (1, 'DEFAULT', 'server-mode.blocking', 'true', 'U', '阻塞和非阻塞方式');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (3, 'DEFAULT', 'max-connection-msg', 'MAX Connection', 'U', '达到最大连接数后的错误信息');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (5, 'DEFAULT', 'console-logging-level', 'INFO', 'U', '控制台日志级别');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (7, 'DEFAULT', 'communication-logging.enable', 'false', 'U', '通讯日志是否打开(本配置为关闭)');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (9, 'DEFAULT', 'default-data-mode.data-type-out', 'STRING', 'U', '数据传入的类型(BINARY,STRING,OBJECT,BYTE  本配置为二进制)');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (11, 'DEFAULT', 'object-pool.max-idle', '50', 'U', '??');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (13, 'DEFAULT', 'object-pool.thread-object-pool.max-active', '50', 'U', '工作线程池的最大活动值');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (15, 'DEFAULT', 'object-pool.client-handler-object-pool.max-active', '-1', 'U', '??');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (17, 'DEFAULT', 'object-pool.byte-buffer-object-pool.max-active', '-1', 'U', '??');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (19, 'DEFAULT', 'object-pool.client-data-object-pool.max-active', '-1', 'U', '??');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (21, 'DEFAULT', 'advanced-settings.charset', 'UTF-8', 'U', '编码模式');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (23, 'DEFAULT', 'advanced-settings.backlog', '1024', 'U', 'backlog指定在请求队列中允许的最大请求数，也就是设置操作系统中此socket服务器允许最大连接数');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (25, 'DEFAULT', 'client-event-handler', 'com.asiainfo.appframe.ext.exeframe.socket.handler.DefaultCommandHandler', 'U', 'com.asiainfo.boss.common.exe.socket.handler.DefaultCommandHandler');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (20001, 'MSG_DISPATCH_SERVER', 'port', '2045', 'U', '监听端口');
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (20003, 'MSG_DISPATCH_SERVER', 'client-binary-handler', 'com.asiainfo.appframe.ext.exeframe.socket.handler.AdvanceBinaryHandler ', 'U', null);
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (20004, 'MSG_DISPATCH_SERVER', 'client-data', 'com.asiainfo.appframe.ext.exeframe.socket.data.SecurityData', 'U', null);
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (20005, 'MSG_DISPATCH_SERVER', 'client-event-handler', 'com.asiainfo.appframe.ext.exeframe.socket.handler.AdvanceBinaryHandler ', 'U', null);
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (20006, 'MSG_DISPATCH_SERVER', 'default-data-mode.data-type-in', 'BIN_C_MONITOR', 'U', null);
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (20007, 'MSG_DISPATCH_SERVER', 'default-data-mode.data-type-out', 'BINARY', 'U', null);
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (20008, 'MSG_DISPATCH_SERVER', 'timeout', '300000', 'U', null);
insert into CFG_SOCKET_MAPPING (mapping_id, cfg_socket_code, mapping_name, mapping_value, state, remarks)
values (20002, 'MSG_DISPATCH_SERVER', 'bind-address', '0.0.0.0', 'U', null);
commit;