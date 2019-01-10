/*==============================================================*/
/* Table: AI_DEPLOY_APP_PARAM                                   */
/*==============================================================*/
create table AI_DEPLOY_APP_PARAM
(
   APPLICATION_PARAM_ID numeric(12,0) not null,
   APPLICATION_ID       numeric(12,0) not null comment '应用id',
   _KEY                  varchar(64) not null comment '主键 ',
   _VALUE                varchar(64) comment '值',
   PARAM_TYPE           char(1) default 'T',
   primary key (APPLICATION_PARAM_ID)
);

alter table AI_DEPLOY_APP_PARAM comment '主机应用不是参数配置表';

/*==============================================================*/
/* Table: AI_DEPLOY_APP_TEMPLATE                                */
/*==============================================================*/
create table AI_DEPLOY_APP_TEMPLATE
(
   TEMPLATE_ID          numeric(12,0) not null comment '模板id',
   TEMPLATE_NAME        varchar(128) comment '模板名称',
   CONTENTS             varchar(4000),
   CREATE_TIME          datetime comment '创建时间',
   MODIFY_TIME          datetime comment '修改时间',
   OPERATOR_ID          numeric(12,0) comment '操作者id',
   REMARKS              varchar(512) comment '备注',
   primary key (TEMPLATE_ID)
);

alter table AI_DEPLOY_APP_TEMPLATE comment '启停模板配置表';

/*==============================================================*/
/* Table: AI_DEPLOY_NODE_STRATEGY_RELA                          */
/*==============================================================*/
create table AI_DEPLOY_NODE_STRATEGY_RELA
(
   NODE_ID              numeric(12,0) not null comment '节点ID',
   DEPLOY_STRATEGY_ID   numeric(12,0) comment '部署策略ID',
   CREATE_TIME          datetime,
   MODIFY_TIME          datetime,
   primary key (NODE_ID)
);

alter table AI_DEPLOY_NODE_STRATEGY_RELA comment '节点与部署策略关系表';

/*==============================================================*/
/* Table: AI_DEPLOY_NODE_VERSION                                */
/*==============================================================*/
create table AI_DEPLOY_NODE_VERSION
(
   NODE_ID              numeric(12,0) not null comment '节点ID',
   REMARKS              varchar(512),
   VERSION_ID           numeric(12,0) comment '版本id',
   CREATE_DATE          timestamp comment '创建时间',
   OPERATOR_ID          numeric(12,0) comment '操作者id',
   primary key (NODE_ID)
);

alter table AI_DEPLOY_NODE_VERSION comment '主机节点版本表';

/*==============================================================*/
/* Table: AI_DEPLOY_STRATEGY                                    */
/*==============================================================*/
create table AI_DEPLOY_STRATEGY
(
   DEPLOY_STRATEGY_ID   numeric(12,0) not null comment '部署策略id',
   DEPLOY_STRATEGY_NAME varchar(128) comment '部署策略名称',
   CLIENT_HOME_PATH     varchar(256) comment '家目录',
   CLIENT_BIN_PATH      varchar(256),
   CLIENT_SBIN_PATH     varchar(256),
   SERVER_PACKAGE_PATH  varchar(256) comment '服务器包路径',
   HISTORY_PACKAGE_NUM  numeric(1,0) comment '历史版本数',
   FTP_HOST_IP          varchar(128) comment 'ftp主机ip',
   FTP_HOST_PORT        numeric(5,0) comment 'ftp端口',
   FTP_PROTOCOL         char(1) comment 'ftp协议',
   FTP_PACKAGE_PATH     varchar(512) comment 'ftp包路径',
   FTP_USER_NAME        varchar(256) comment 'ftp用户名',
   FTP_PASSWORD         varchar(256) comment 'ftp密码',
   INSTALL_RULE         varchar(1024) comment '安装规则',
   TEMPLATE_ID          numeric(12,0),
   STOP_TEMPLATE_ID     numeric(12,0),
   CREATE_TIME          datetime comment '创建时间',
   MODIFY_TIME          datetime comment '修改时间',
   OPERATOR_ID          numeric(12,0) comment '操作员',
   REMARKS              varchar(512) comment '备注',
   CLIENT_LOG_PATH      varchar(256),
   primary key (DEPLOY_STRATEGY_ID)
);

alter table AI_DEPLOY_STRATEGY comment '部署策略表';

/*==============================================================*/
/* Table: AI_DEPLOY_VERSION                                     */
/*==============================================================*/
create table AI_DEPLOY_VERSION
(
   VERSION_ID           numeric(12,0) not null comment '版本id',
   EXTERNAL_VERSION_ID  numeric(12,0) comment '外部版本id',
   DEPLOY_STRATEGY_ID   numeric(12,0) comment '部署策略id',
   PARENT_VERSION_ID    numeric(12,0) comment '父版本id',
   STATE                char(1) comment '状态',
   PACKAGE_PATH         varchar(512) comment '打包路径',
   OPERATE_TYPE         char(1) not null comment '操作类型',
   FILE_LIST            varchar(4000) comment '文件集',
   RESOLVED_PROBLEMS    varchar(1024) comment '解决的问题',
   CONTACTS             varchar(512) comment '修改人',
   REMARKS              varchar(512) comment '备注',
   CREATE_TIME          datetime not null comment '创建时间',
   OPERATOR_ID          numeric(12,0) comment '操作员',
   primary key (VERSION_ID)
);

alter table AI_DEPLOY_VERSION comment '安装版本记录表';

/*==============================================================*/
/* Table: AI_MON_BATCH                                          */
/*==============================================================*/
create table AI_MON_BATCH
(
   BATCH_ID             numeric(12,0) not null comment '批次，1:代表月末月初保障',
   TASK_CODE            varchar(64) not null comment '任务编码',
   SN                   numeric(12,0) not null comment '顺序',
   primary key (BATCH_ID, TASK_CODE)
);

alter table AI_MON_BATCH comment '用于月末月初保障';

/*==============================================================*/
/* Table: AI_MON_CMD                                            */
/*==============================================================*/
create table AI_MON_CMD
(
   CMD_ID               numeric(12,0) not null comment '标识',
   CMD_NAME             varchar(40) comment '名称',
   CMD_DESC             varchar(255) comment '描述',
   CMD_TYPE             varchar(12) comment '类型',
   CMD_EXPR             varchar(4000) comment '内容',
   PARAM_TYPE           varchar(20) comment '参数规则类型',
   STATE                char(1) comment '状态',
   REMARK               varchar(255) comment '备注',
   primary key (CMD_ID)
);

alter table AI_MON_CMD comment '命令定义';

/*==============================================================*/
/* Table: AI_MON_CMDSET                                         */
/*==============================================================*/
create table AI_MON_CMDSET
(
   CMDSET_ID            numeric(12,0) not null comment '命令集标识',
   CMDSET_CODE          varchar(40) comment '标识码',
   CMDSET_NAME          varchar(40) comment '名称',
   CMDSET_DESC          varchar(255) comment '描述',
   STATE                char(1) comment '状态',
   REMARK               varchar(255) comment '备注',
   primary key (CMDSET_ID)
);

alter table AI_MON_CMDSET comment '命令集定义表';

/*==============================================================*/
/* Table: AI_MON_CMDSET_RELAT                                   */
/*==============================================================*/
create table AI_MON_CMDSET_RELAT
(
   CMDSET_ID            numeric(12,0) comment '命令集标识',
   CMD_ID               numeric(12,0) comment '命令标识',
   CMD_SEQ              numeric(3,0) comment '顺序',
   STATE                char(1) comment '状态',
   REMARK               varchar(255) comment '备注'
);

alter table AI_MON_CMDSET_RELAT comment '命令集关联命令定义';

/*==============================================================*/
/* Table: AI_MON_CON_MODE                                       */
/*==============================================================*/
create table AI_MON_CON_MODE
(
   CON_ID               numeric(12,0) not null,
   CON_TYPE             varchar(20),
   CON_PORT             int,
   STATE                char(1),
   CREATE_DATE          datetime,
   REMARK               varchar(255),
   primary key (CON_ID)
);

/*==============================================================*/
/* Table: AI_MON_DB_ACCT                                        */
/*==============================================================*/
create table AI_MON_DB_ACCT
(
   DB_ACCT_CODE         varchar(255) not null,
   USERNAME             varchar(255),
   PASSWORD             varchar(255),
   HOST                 varchar(255),
   PORT                 numeric(5,0),
   SID                  varchar(255),
   CONN_MIN             numeric(3,0),
   CONN_MAX             numeric(3,0),
   STATE                char(1),
   REMARKS              varchar(1000)
);

/*==============================================================*/
/* Table: AI_MON_DB_URL                                         */
/*==============================================================*/
create table AI_MON_DB_URL
(
   NAME                 varchar(255) not null,
   URL                  varchar(4000),
   STATE                char(1),
   REMARKS              varchar(255)
);

/*==============================================================*/
/* Table: AI_MON_DOMAIN                                         */
/*==============================================================*/
create table AI_MON_DOMAIN
(
   DOMAIN_ID            numeric(12,0) not null comment '标识',
   DOMAIN_CODE          varchar(40) comment '标识码',
   DOMAIN_TYPE          varchar(255) comment '名称',
   DOMAIN_DESC          varchar(255) comment '描述',
   STATE                char(1) comment '状态',
   REMARKS              varchar(255) comment '备注',
   primary key (DOMAIN_ID)
);

alter table AI_MON_DOMAIN comment '域类型定义表';

/*==============================================================*/
/* Table: AI_MON_DOMAIN_RELAT                                   */
/*==============================================================*/
create table AI_MON_DOMAIN_RELAT
(
   RELAT_ID             numeric(12,0) not null comment '标识',
   DOMAIN_ID            numeric(12,0) comment '域标识',
   RELATDOMAIN_ID       numeric(12,0) comment '与域标识',
   RELATDOMAIN_TYPE     varchar(2) comment '与域类型:1,组;2,主机;3,应用',
   RELAT_DESC           varchar(255) comment '关系描述',
   STATE                char(1) comment '状态',
   REMARKS              varchar(255) comment '备注',
   primary key (RELAT_ID)
);

alter table AI_MON_DOMAIN_RELAT comment '与域综合关系定义表';

/*==============================================================*/
/* Table: AI_MON_GROUP                                          */
/*==============================================================*/
create table AI_MON_GROUP
(
   GROUP_ID             numeric(12,0) not null comment '标识',
   GROUP_NAME           varchar(40) comment '组名',
   GROUP_DESC           varchar(255) comment '组描述',
   CREATE_DATE          datetime comment '创建时间',
   STATE                char(1) comment '状态:U-正常,E-删除',
   GROUP_CODE           varchar(50) not null,
   PRIV                 varchar(4000),
   primary key (GROUP_ID)
);

alter table AI_MON_GROUP comment '组信息';

/*==============================================================*/
/* Table: AI_MON_GROUP_HOST_REL                                 */
/*==============================================================*/
create table AI_MON_GROUP_HOST_REL
(
   GROUP_ID             numeric(12,0) not null comment '组ID',
   HOST_ID              numeric(12,0) not null comment '主机ID',
   CREATE_DATE          datetime comment '创建时间',
   RELATION_ID          numeric(12,0) not null,
   primary key (RELATION_ID)
);

alter table AI_MON_GROUP_HOST_REL comment '组与主机关联关系表';

/*==============================================================*/
/* Table: AI_MON_HOST_CON_MODE                                  */
/*==============================================================*/
create table AI_MON_HOST_CON_MODE
(
   RELATION_ID          numeric(12,0) not null,
   CON_ID               numeric(12,0) not null,
   HOST_ID              numeric(12,0) not null
);

/*==============================================================*/
/* Table: AI_MON_HOST_USER                                      */
/*==============================================================*/
create table AI_MON_HOST_USER
(
   HOST_USER_ID         numeric(12,0) not null,
   HOST_ID              numeric(12,0),
   USER_NAME            varchar(50),
   USER_PWD             varchar(50),
   USER_TYPE            varchar(20),
   STATE                char(1),
   CREATE_DATE          datetime,
   REMARK               varchar(255),
   primary key (HOST_USER_ID)
);

/*==============================================================*/
/* Table: AI_MON_I18N_RESOURCE                                  */
/*==============================================================*/
create table AI_MON_I18N_RESOURCE
(
   RES_KEY              varchar(15) not null,
   ZH_CN                varchar(1000) not null,
   EN_US                varchar(1000),
   STATE                char(1) not null,
   REMARK               varchar(255),
   primary key (RES_KEY)
);

/*==============================================================*/
/* Table: AI_MON_LOG                                            */
/*==============================================================*/
create table AI_MON_LOG
(
   LOG_ID               numeric(12,0) not null comment '标识',
   OPERATE_ID           numeric(12,0) comment '操作员工ID',
   OPERATE_NAME         varchar(50) comment '操作员工',
   CLKURL               varchar(255) comment '触发URL',
   CLKCODE              varchar(255) comment '触发CODE',
   OPERATOR_EXPR        varchar(4000) comment '日志内容',
   CREATE_TIME          datetime comment '日志记录时间',
   REMARKS              varchar(200) comment '备注',
   primary key (LOG_ID)
);

alter table AI_MON_LOG comment 'Monitor日志';

/*==============================================================*/
/* Table: AI_MON_LOGIN                                          */
/*==============================================================*/
create table AI_MON_LOGIN
(
   IP                   varchar(50) not null comment '登录主机的ip',
   LOGIN_NAME           varchar(50) not null comment '登录用户名',
   LAST_FAIL_TIME       datetime comment '上次登录失败时间',
   FAIL_COUNT           numeric(10,0) comment '失败次数',
   EXT_01               varchar(255),
   EXT_02               varchar(255),
   primary key (IP, LOGIN_NAME)
);

alter table AI_MON_LOGIN comment '统计登录失败信息';

/*==============================================================*/
/* Table: AI_MON_L_RECORD                                       */
/*==============================================================*/
create table AI_MON_L_RECORD
(
   RECORD_ID            numeric(12,0) not null comment '标识',
   INFO_ID              numeric(12,0) comment '任务标识',
   INFO_CODE            varchar(40),
   HOST_NAME            varchar(255) comment '主机名',
   IP                   varchar(255) comment 'IP',
   MON_TYPE             varchar(255) comment '监控类型',
   INFO_NAME            varchar(255) comment '任务名',
   LAYER                varchar(255) comment '层',
   FROM_TYPE            varchar(40) comment '来源类型',
   GROUP_ID             varchar(255) comment '分组',
   IS_TRIGGER_WARN      char(1) comment '是否警告',
   WARN_LEVEL           varchar(255) comment '警告级别',
   INFO_VALUE           varchar(4000) comment '任务值',
   DONE_CODE            numeric(12,0) comment '批次',
   DONE_DATE            datetime comment '完成时间',
   BATCH_ID             numeric(12,0) comment '子批次',
   VALID_DATE           datetime comment '有效日期',
   EXPIRE_DATE          datetime comment '失效日期',
   OP_ID                numeric(12,0) comment '操作人',
   ORG_ID               numeric(12,0) comment '组织',
   CREATE_DATE          datetime comment '创建时间',
   REMARKS              varchar(255) comment '备注',
   primary key (RECORD_ID),
   key AK_KEY_2_AI_MON_L (INFO_CODE)
);

alter table AI_MON_L_RECORD comment '监控结果表(该表分层、日期)';

/*==============================================================*/
/* Table: AI_MON_MASTER_SLAVE_REL                               */
/*==============================================================*/
create table AI_MON_MASTER_SLAVE_REL
(
   RELATION_ID          numeric(12,0) not null comment '关系表ID',
   MASTER_HOST_ID       numeric(12,0) not null comment '主主机ID',
   SLAVE_HOST_ID        numeric(12,0) not null comment '备主机ID',
   STATE                char(1) comment '状态:U-正常,E-删除',
   CREATE_DATE          datetime comment '创建时间',
   REMARK               varchar(255) comment '备注'
);

alter table AI_MON_MASTER_SLAVE_REL comment '主机主备关系表';

/*==============================================================*/
/* Table: AI_MON_NODE                                           */
/*==============================================================*/
create table AI_MON_NODE
(
   NODE_ID              numeric(12,0) not null comment '节点标识',
   HOST_ID              numeric(12,0) not null comment '节点归属主机标识',
   NODE_CODE            varchar(40) comment '逻辑码',
   NODE_NAME            varchar(100) comment '节点名',
   STATE                char(1) comment '状态:U-正常,E-删除',
   CREATE_DATE          datetime comment '创建时间',
   REMARK               varchar(255) comment '备注',
   IS_MONITOR_NODE      char(1),
   primary key (NODE_ID)
);

alter table AI_MON_NODE comment '节点定义表';

/*==============================================================*/
/* Table: AI_MON_NODE_USER                                      */
/*==============================================================*/
create table AI_MON_NODE_USER
(
   NODE_USER_ID         numeric(12,0) not null,
   NODE_ID              numeric(12,0),
   USER_ID              numeric(12,0),
   STATE                char(1),
   CREATE_DATE          datetime,
   MODIFY_DATE          datetime,
   REMARK               varchar(255),
   primary key (NODE_USER_ID)
);

/*==============================================================*/
/* Table: AI_MON_PARAM_DEFINE                                   */
/*==============================================================*/
create table AI_MON_PARAM_DEFINE
(
   PARAM_ID             numeric(12,0) not null comment '参数标识',
   REGISTE_TYPE         numeric(2,0) comment '寄主类型;1、进程，2、表，3、命令',
   REGISTE_ID           numeric(12,0) comment '寄主标识',
   PARAM_CODE           varchar(40) comment '参数码',
   PARAM_DESC           varchar(255) comment '参数描述',
   IS_MUST              char(1) comment '是否必填,1必填;0不必',
   DATA_TYPE            varchar(20) comment '参数类型',
   SORT_ID              numeric(2,0) comment '顺序',
   STATE                char(1) comment '状态',
   REMARKS              varchar(255) comment '备注',
   primary key (PARAM_ID)
);

alter table AI_MON_PARAM_DEFINE comment '参数定义表';

/*==============================================================*/
/* Table: AI_MON_PARAM_VALUES                                   */
/*==============================================================*/
create table AI_MON_PARAM_VALUES
(
   V_ID                 numeric(12,0) not null comment '标识',
   REGISTE_TYPE         numeric(2,0) comment '寄主类型;10、任务，20、面板，30、命令',
   REGISTE_ID           numeric(12,0) comment '寄主标识',
   PARAM_ID             numeric(12,0) comment '参数标识',
   PARAM_CODE           varchar(40) comment '参数码',
   PARAM_VALUE          varchar(255) comment '参数值',
   SORT_ID              numeric(2,0) comment '顺序',
   PARAM_DESC           varchar(255) comment '描述',
   REMARKS              varchar(255) comment '备注',
   primary key (V_ID),
   key AK_AI_MON_PV (REGISTE_TYPE, REGISTE_ID, PARAM_ID)
);

alter table AI_MON_PARAM_VALUES comment '参数值配置';

/*==============================================================*/
/* Table: AI_MON_PHYSIC_HOST                                    */
/*==============================================================*/
create table AI_MON_PHYSIC_HOST
(
   HOST_ID              numeric(12,0) not null comment '标识',
   HOST_CODE            varchar(40) comment '标码',
   HOST_NAME            varchar(40) comment '名称',
   HOST_DESC            varchar(255) comment '描述',
   HOST_IP              varchar(100) comment 'IP',
   STATE                char(1) comment '状态:U-正常,E-删除',
   CREATE_DATE          datetime comment '创建时间',
   REMARKS              varchar(255) comment '备注',
   primary key (HOST_ID)
);

alter table AI_MON_PHYSIC_HOST comment '物理主机定义表';

/*==============================================================*/
/* Table: AI_MON_P_EXEC                                         */
/*==============================================================*/
create table AI_MON_P_EXEC
(
   EXEC_ID              numeric(12,0) not null comment '标识',
   NAME                 varchar(255) comment '名称',
   E_TYPE               varchar(255) comment 'SHELL：shell脚本，以SSH登录到相关主机上，根据配置数据生成临时SHELL脚本并执行，执行完成后删除
            JAVACOMMAND：命令，直接以命令方式执行后获取监控结果',
   EXPR                 varchar(4000) comment '表达式',
   STATE                char(1) comment '状态',
   REMARKS              varchar(255) comment '备注',
   primary key (EXEC_ID)
);

alter table AI_MON_P_EXEC comment '进程监控配置表';

/*==============================================================*/
/* Table: AI_MON_P_IMGDATA_RESOLVE                              */
/*==============================================================*/
create table AI_MON_P_IMGDATA_RESOLVE
(
   RESOLVE_ID           numeric(12,0) not null comment '标识',
   NAME                 varchar(255) comment '名称',
   PARENT_ID            numeric(12,0) comment '上级标识',
   TRANSFORM_CLASS      varchar(255) comment '处理类',
   SHOW_NAME_POS        numeric(3,0) comment '名称取值位置',
   SHOW_VALUE_POS       numeric(3,0) comment '值取值位置',
   SORT_BY              varchar(255) comment '排序',
   SHOW_TYPE            varchar(255) comment '展现类型:AREACHART,区域图;BARCHART,横向柱状图;COLUMNCHART,柱状图;LINECHART,线图;PIECHART,饼图;3DCOLUMNCHART;3DPIECHART;3DBARCHART;RINGCHART,环状图;3DRINGCHART;SCALER,仪表盘',
   STATE                char(1) comment '状态',
   REMARKS              varchar(255) comment '备注',
   primary key (RESOLVE_ID)
);

alter table AI_MON_P_IMGDATA_RESOLVE comment '展示数据解析定义';

/*==============================================================*/
/* Table: AI_MON_P_INFO                                         */
/*==============================================================*/
create table AI_MON_P_INFO
(
   INFO_ID              numeric(12,0) not null comment '任务标识',
   INFO_CODE            varchar(40),
   NAME                 varchar(255) comment '任务名',
   HOST_ID              numeric(12,0) comment '主机标识',
   TASK_METHOD          char(1) comment '任务类型:F,为固定时间;C,为cron周期执行;I,为立即执行;P:面板执行',
   TYPE_ID              numeric(12,0) comment '监控配置标识(进程,表数据)',
   THRESHOLD_ID         numeric(12,0) comment '监控阀值标识',
   RESOLVE_ID           numeric(12,0) comment '解析分组标识',
   TIME_ID              numeric(12,0) comment '时间配置',
   SPLIT_RULE_ID        numeric(12,0) comment '分拆规则标识',
   FILTER_ID            numeric(12,0),
   M_TYPE               varchar(255) comment '监控信息类型,EXEC：进程方式;TABLE：表方式',
   LAYER                varchar(20),
   GROUP_ID             numeric(12,0) comment '归属业务区域再分组',
   STATE                char(1) comment '状态U:正常　E:注销',
   REMARKS              varchar(255),
   primary key (INFO_ID),
   key AK_KEY_AI_MON_P (INFO_CODE)
);

alter table AI_MON_P_INFO comment '任务定义表';

/*==============================================================*/
/* Table: AI_MON_P_INFO_FILTER                                  */
/*==============================================================*/
create table AI_MON_P_INFO_FILTER
(
   FILTER_ID            numeric(12,0) not null comment '标识',
   FILTER_NAME          varchar(255) comment '过滤名',
   FILTER_DESC          varchar(255) comment '过滤描述',
   EXPR1                varchar(4000) comment '规则表达式1',
   EXPR2                varchar(4000) comment '规则表达式2',
   EXPR3                varchar(4000) comment '规则表达式3',
   EXPR4                varchar(4000) comment '规则表达式4',
   EXPR5                varchar(4000) comment '规则表达式5',
   STATE                char(1) comment '状态',
   REMARKS              varchar(255) comment '备注',
   primary key (FILTER_ID)
);

alter table AI_MON_P_INFO_FILTER comment '任务过滤规则定义表';

/*==============================================================*/
/* Table: AI_MON_P_INFO_GROUP                                   */
/*==============================================================*/
create table AI_MON_P_INFO_GROUP
(
   GROUP_ID             numeric(12,0) not null comment '标识',
   GROUP_CODE           varchar(40) comment '编码',
   GROUP_NAME           varchar(40) comment '名称',
   GROUP_DESC           varchar(255) comment '描述',
   PARENT_ID            numeric(12,0),
   SORT_ID              numeric(3,0) comment '位次',
   LAYER                varchar(40) comment '层',
   GROUP_STYLE          varchar(200),
   STATE                char(1) comment '状态',
   REMARK               varchar(255) comment '备注',
   primary key (GROUP_ID),
   key AK_KEY_2_AI_MON_P (GROUP_CODE)
);

alter table AI_MON_P_INFO_GROUP comment '任务分组表';

/*==============================================================*/
/* Table: AI_MON_P_TABLE                                        */
/*==============================================================*/
create table AI_MON_P_TABLE
(
   TABLE_ID             numeric(12,0) not null comment '标识',
   TABLE_NAME           varchar(255),
   TABLE_SQL            varchar(4000) comment 'SQL',
   DB_ACCT_CODE         varchar(255) comment '数据源',
   DB_URL_NAME          varchar(255) comment '数据库节点URL',
   STATE                char(1) comment '状态',
   REMARKS              varchar(255) comment '备注',
   primary key (TABLE_ID)
);

alter table AI_MON_P_TABLE comment '表数据监控配置表';

/*==============================================================*/
/* Table: AI_MON_P_THRESHOLD                                    */
/*==============================================================*/
create table AI_MON_P_THRESHOLD
(
   THRESHOLD_ID         numeric(12,0) not null comment '标识',
   THRESHOLD_NAME       varchar(255) comment '名称',
   EXPR1                varchar(4000) comment '表达式1',
   EXPR2                varchar(4000) comment '表达式2',
   EXPR3                varchar(4000) comment '表达式3',
   EXPR4                varchar(4000) comment '表达式4',
   EXPR5                varchar(4000) comment '表达式5',
   EXPIRY_DAYS          numeric(3,0) comment '有效期限',
   STATE                char(1) comment '状态',
   REMARKS              varchar(255) comment '备注',
   primary key (THRESHOLD_ID)
);

alter table AI_MON_P_THRESHOLD comment '进程阀值配置表';

/*==============================================================*/
/* Table: AI_MON_P_TIME                                         */
/*==============================================================*/
create table AI_MON_P_TIME
(
   TIME_ID              numeric(12,0) not null comment '标识',
   T_TYPE               varchar(255) comment '类型',
   EXPR                 varchar(255) comment '表达式',
   STATE                char(1) comment '状态',
   REMARKS              varchar(255) comment '备注',
   primary key (TIME_ID)
);

alter table AI_MON_P_TIME comment '监控时间段配置';

/*==============================================================*/
/* Table: AI_MON_SERVER                                         */
/*==============================================================*/
create table AI_MON_SERVER
(
   SERVER_ID            numeric(12,0) not null comment '标识',
   SERVER_CODE          varchar(40) not null comment '应用代码,该值保持应用端JVM环境变量appframe.client.app.name或appframe.server.name的值一致',
   NODE_ID              numeric(12,0) not null comment '节点ID',
   SERVER_NAME          varchar(255) comment '应用名称',
   LOCATOR_TYPE         varchar(40) comment '定位类型（remote 和jmx）',
   LOCATOR              varchar(255) comment '定位内容',
   SERVER_IP            varchar(40) comment '应用IP',
   SERVER_PORT          varchar(40) comment '应用端口',
   SVERSION             varchar(40) comment '版本',
   CHECK_URL            varchar(255) comment '版本URL',
   MIDWARE_TYPE         varchar(40) comment '中间件类型（WAS和BEA）',
   TEMP_TYPE            varchar(20) comment '模板类型（APP、WEB、OTHER、EXEC）',
   START_CMD_ID         numeric(12,0) comment '启动命令',
   STOP_CMD_ID          numeric(12,0) comment '停止命令',
   STATE                char(1) comment '状态:U-正常,E-删除',
   CREATE_DATE          datetime comment '创建时间',
   REMARK               varchar(255) comment '备注',
   BUSINESS_TYPE        varchar(255),
   primary key (SERVER_ID)
);

alter table AI_MON_SERVER comment '应用定义表';

/*==============================================================*/
/* Table: AI_MON_SET                                            */
/*==============================================================*/
create table AI_MON_SET
(
   SET_ID               numeric(12,0) not null comment '标识',
   SET_CODE             varchar(40) comment '标识码',
   HOST_ID              numeric(12,0) comment '主机标识',
   SERVER_ID            numeric(12,0) comment '应用标识',
   APP_NAME             varchar(255) comment '应用名',
   SET_VCODE            varchar(40) comment '设置码',
   SET_VALUE            varchar(255) comment '值',
   SET_DESC             varchar(255) comment '描述',
   CREATE_DATE          datetime comment '创建时间',
   OPERATER             varchar(40) comment '操作人',
   STATE                char(1) comment '状态',
   REMARKS              varchar(255) comment '备注',
   primary key (SET_ID)
);

alter table AI_MON_SET comment '设置表';

/*==============================================================*/
/* Table: AI_MON_STATIC_DATA                                    */
/*==============================================================*/
create table AI_MON_STATIC_DATA
(
   CODE_TYPE            varchar(255) comment '类型',
   CODE_VALUE           varchar(255) comment '值',
   CODE_NAME            varchar(255) comment '名称',
   CODE_DESC            varchar(255) comment '描述',
   CODE_TYPE_ALIAS      varchar(255) comment '别名',
   SORT_ID              numeric(3,0) comment '排序',
   STATE                char(1) comment '状态(U:启用,E:删除)',
   EXTERN_CODE_TYPE     varchar(255) comment '扩展类型'
);

alter table AI_MON_STATIC_DATA comment '静态数据表';

/*==============================================================*/
/* Table: AI_MON_USERS                                          */
/*==============================================================*/
create table AI_MON_USERS
(
   USER_ID              numeric(12,0) not null,
   USER_CODE            varchar(50),
   USER_NAME            varchar(50),
   USER_PWD             varchar(50),
   STATE                char(1),
   CREATE_DATE          datetime,
   MODIFY_DATE          datetime,
   REMARK               varchar(255),
   primary key (USER_ID)
);

/*==============================================================*/
/* Table: AI_MON_VERIFY                                         */
/*==============================================================*/
create table AI_MON_VERIFY
(
   VERIFY_TYPE          varchar(50) not null comment '校验类型',
   RULE                 varchar(1000) not null comment '校验规则',
   primary key (VERIFY_TYPE)
);

alter table AI_MON_VERIFY comment '校验信息表';

/*==============================================================*/
/* Table: AI_MON_W_DTL                                          */
/*==============================================================*/
create table AI_MON_W_DTL
(
   DTL_ID               numeric(12,0) not null comment '标识',
   INFO_ID              numeric(12,0) comment '任务标识',
   PERSON_ID            numeric(12,0) comment '人员标识',
   WARN_LEVEL           varchar(3) comment '警告级别',
   STATE                char(1) comment '状态',
   REMARKS              varchar(255) comment '备注',
   primary key (DTL_ID)
);

alter table AI_MON_W_DTL comment '监控信息发送人员关联配置表';

/*==============================================================*/
/* Index: M_W_DTL1                                              */
/*==============================================================*/
create index M_W_DTL1 on AI_MON_W_DTL
(
   INFO_ID
);

/*==============================================================*/
/* Table: AI_MON_W_PERSON                                       */
/*==============================================================*/
create table AI_MON_W_PERSON
(
   PERSON_ID            numeric(12,0) not null comment '标识',
   NAME                 varchar(255) comment '姓名',
   PHONENUM             varchar(255) comment '号码',
   REGION_ID            numeric(12,0) comment '组织标识',
   REGION               varchar(255) comment '组织',
   STATE                char(1) comment '状态',
   REMARKS              varchar(255) comment '备注',
   primary key (PERSON_ID)
);

alter table AI_MON_W_PERSON comment '告警短信发人员配置';

/*==============================================================*/
/* Table: AI_MON_W_SMS                                          */
/*==============================================================*/
create table AI_MON_W_SMS
(
   SMS_ID               numeric(12,0) not null comment '标识',
   INFO_ID              numeric(12,0) comment '任务标识',
   RECORD_ID            numeric(12,0) comment '记录标识',
   TRIGGER_ID           numeric(12,0) comment '警告标识',
   PHONE_NUM            varchar(1000) comment '手机号',
   SMS_CONTENT          varchar(4000) comment '短信内容',
   WARN_LEVEL           varchar(255) comment '警告级别',
   CREATE_DATE          datetime comment '创建时间',
   SEND_DATE            datetime comment '发送时间,记录最后一次发送时间',
   SEND_COUNT           numeric(3,0),
   STATE                char(1) comment '发送状态,E:发送异常,已发送的记录将删除',
   EXCEPTIONS           varchar(4000) comment '异常信息,但发送异常时存入异常信息',
   REMARKS              varchar(255) comment '备注',
   primary key (SMS_ID)
);

alter table AI_MON_W_SMS comment '短信发送表';

/*==============================================================*/
/* Table: AI_SCHED_TASK_INTER                                   */
/*==============================================================*/
create table AI_SCHED_TASK_INTER
(
   INTER_ID             numeric(12,0) not null comment '序列号sequence',
   TASK_CODE            varchar(255) not null comment '任务编码',
   TASK_DESC            varchar(255) comment '任务描述',
   TASK_ITEM_ID         varchar(255) not null comment '任务拆分项（-1 指启动所有任务拆分项）',
   OP_ID                varchar(255) not null comment '操作员',
   ACTION               varchar(255) not null comment '操作指令（start：启动，stop：停止）',
   TASK_PARAM           varchar(4000) comment '任务启动参数，格式:key=@@key=value',
   CREATE_DATE          datetime not null comment '任务创建时间',
   STATE_DATE           datetime comment '任务更新状态',
   STATE                varchar(2) not null comment 'C：未执行、R：执行中、F：执行成功、E:执行异常',
   TASK_JOB_ID          varchar(255) comment '任务执行作业流水号',
   EXEC_RESULT          varchar(2000) comment '处理结果',
   REMARKS              varchar(255) comment '备注',
   primary key (INTER_ID)
);

alter table AI_SCHED_TASK_INTER comment '调度管理中心立即执行任务接口表';

/*==============================================================*/
/* Index: IDX_AI_SCHTASK_INTER_CODE                             */
/*==============================================================*/
create index IDX_AI_SCHTASK_INTER_CODE on AI_SCHED_TASK_INTER
(
   TASK_CODE
);

/*==============================================================*/
/* Table: CFG_BO_MASK                                           */
/*==============================================================*/
create table CFG_BO_MASK
(
   BO_NAME              varchar(255) not null,
   ATTR_NAME            varchar(255) not null,
   MASK_FUNCTION_CLASS  varchar(255) not null,
   STATE                char(1) not null,
   REMARKS              varchar(255),
   primary key (BO_NAME, ATTR_NAME)
);

/*==============================================================*/
/* Table: CFG_CLIENT_TIMEOUT                                    */
/*==============================================================*/
create table CFG_CLIENT_TIMEOUT
(
   SERVER_NAME          varchar(100) not null,
   INTERFACE_CLASSNAME  varchar(200) not null,
   METHOD_NAME          varchar(100) not null,
   PARAMETER_COUNT      numeric(6,0) not null,
   TIMEOUT_SECOND       numeric(6,0) not null,
   STATE                char(1) not null,
   REMARKS              varchar(255),
   primary key (INTERFACE_CLASSNAME, METHOD_NAME, PARAMETER_COUNT)
);

/*==============================================================*/
/* Table: CFG_DB_ACCT                                           */
/*==============================================================*/
create table CFG_DB_ACCT
(
   DB_ACCT_CODE         varchar(255) not null,
   USERNAME             varchar(255),
   PASSWORD             varchar(255),
   HOST                 varchar(255),
   PORT                 numeric(5,0),
   SID                  varchar(255),
   DEFAULT_CONN_MIN     numeric(3,0),
   DEFAULT_CONN_MAX     numeric(3,0),
   STATE                char(1),
   REMARKS              varchar(1000)
);

/*==============================================================*/
/* Table: CFG_DB_JDBC_PARAMETER                                 */
/*==============================================================*/
create table CFG_DB_JDBC_PARAMETER
(
   PARAMETER_ID         numeric(12,0) not null,
   DB_ACCT_CODE         varchar(255) not null,
   SERVER_NAME          varchar(255) not null,
   NAME                 varchar(255) not null,
   VALUE                varchar(255) not null,
   STATE                char(1) not null,
   REMARKS              varchar(255),
   primary key (PARAMETER_ID)
);

/*==============================================================*/
/* Index: IDX_CFG_DB_JDBC_PARAMETER_1                           */
/*==============================================================*/
create index IDX_CFG_DB_JDBC_PARAMETER_1 on CFG_DB_JDBC_PARAMETER
(
   SERVER_NAME
);

/*==============================================================*/
/* Index: IDX_CFG_DB_JDBC_PARAMETER_2                           */
/*==============================================================*/
create index IDX_CFG_DB_JDBC_PARAMETER_2 on CFG_DB_JDBC_PARAMETER
(
   DB_ACCT_CODE
);

/*==============================================================*/
/* Table: CFG_DB_RELAT                                          */
/*==============================================================*/
create table CFG_DB_RELAT
(
   DB_ACCT_CODE         varchar(255) not null,
   URL_NAME             varchar(255),
   SERVER_NAME          varchar(255) not null,
   STATE                char(1),
   REMARKS              varchar(255),
   primary key (DB_ACCT_CODE, SERVER_NAME)
);

/*==============================================================*/
/* Index: IDX_CFG_DB_RELAT_1                                    */
/*==============================================================*/
create index IDX_CFG_DB_RELAT_1 on CFG_DB_RELAT
(
   SERVER_NAME
);

/*==============================================================*/
/* Table: CFG_DB_RELAT_TMP                                      */
/*==============================================================*/
create table CFG_DB_RELAT_TMP
(
   DB_ACCT_CODE         varchar(255) not null,
   URL_NAME             varchar(255),
   SERVER_NAME          varchar(255) not null,
   STATE                char(1),
   REMARKS              varchar(255)
);

/*==============================================================*/
/* Table: CFG_DB_URL                                            */
/*==============================================================*/
create table CFG_DB_URL
(
   NAME                 varchar(255) not null,
   URL                  varchar(4000),
   STATE                char(1),
   REMARKS              varchar(255),
   primary key (NAME)
);

/*==============================================================*/
/* Table: CFG_DYNC_TABLE_SPLIT                                  */
/*==============================================================*/
create table CFG_DYNC_TABLE_SPLIT
(
   GROUP_NAME           varchar(255) not null,
   TABLE_NAME           varchar(255) not null,
   TABLE_NAME_EXPR      varchar(255) not null,
   CONVERT_CLASS        varchar(255) not null,
   STATE                char(1) not null,
   REMARKS              varchar(255)
);

/*==============================================================*/
/* Table: CFG_I18N_RESOURCE                                     */
/*==============================================================*/
create table CFG_I18N_RESOURCE
(
   RES_KEY              varchar(15) not null,
   ZH_CN                varchar(1000) not null,
   EN_US                varchar(1000),
   STATE                char(1) not null,
   REMARK               varchar(255)
);

/*==============================================================*/
/* Table: CFG_ID_GENERATOR                                      */
/*==============================================================*/
create table CFG_ID_GENERATOR
(
   TABLE_NAME           varchar(100) not null,
   DOMAIN_ID            numeric(6,0) not null,
   GENERATOR_TYPE       char(1) not null,
   SEQUENCE_NAME        varchar(60),
   MAX_ID               numeric(12,0),
   START_VALUE          numeric(12,0),
   MIN_VALUE            numeric(12,0),
   MAX_VALUE            numeric(12,0),
   INCREMENT_VALUE      numeric(6,0),
   CYCLE_FLAG           char(1),
   CACHE_SIZE           numeric(6,0),
   SEQUENCE_CREATE_SCRIPT varchar(1000),
   HIS_TABLE_NAME       varchar(100),
   HIS_SEQUENCE_NAME    varchar(60),
   HIS_DATA_FLAG        char(1),
   HIS_MAX_ID           numeric(12,0),
   HIS_DONECODE_FLAG    char(1),
   STEP_BY              numeric(6,0),
   HIS_STEP_BY          numeric(6,0)
);

/*==============================================================*/
/* Table: CFG_ID_GENERATOR_WRAPPER                              */
/*==============================================================*/
create table CFG_ID_GENERATOR_WRAPPER
(
   TABLE_NAME           varchar(100) not null,
   TABLE_SEQ_WRAPPER_IMPL varchar(1000),
   HIS_TABLE_SEQ_WRAPPER_IMPL varchar(1000)
);

/*==============================================================*/
/* Table: CFG_METHOD_CENTER                                     */
/*==============================================================*/
create table CFG_METHOD_CENTER
(
   SERVICE_IMPL_CLASSNAME varchar(255) not null,
   METHOD_NAME          varchar(255) not null,
   PARAMETER_COUNT      numeric(6,0) not null,
   PARAMETER_INDEX      numeric(6,0) not null,
   PARAMETER_FUNCTION   varchar(255) not null,
   CENTER_TYPE          varchar(255) not null,
   STATE                char(1) not null,
   REMARKS              varchar(255),
   primary key (SERVICE_IMPL_CLASSNAME, METHOD_NAME, PARAMETER_COUNT)
);

/*==============================================================*/
/* Table: CFG_SERVICE_CONTROL                                   */
/*==============================================================*/
create table CFG_SERVICE_CONTROL
(
   SERVER_NAME          varchar(1000) not null,
   SERVICE_NAME         varchar(1000) not null,
   METHOD_NAME          varchar(1000),
   LIMIT_COUNT          numeric(6,0) not null,
   STATE                char(1) not null,
   REMARKS              varchar(255)
);

/*==============================================================*/
/* Table: CFG_SOCKET                                            */
/*==============================================================*/
create table CFG_SOCKET
(
   CFG_SOCKET_CODE      varchar(255) not null,
   SOCKET_GRP           varchar(255) not null,
   SOCKET_DESC          varchar(1000),
   SOCKET_BUSINESS_CLASS varchar(255) not null,
   STATE                char(1) not null,
   REMARKS              varchar(255)
);

/*==============================================================*/
/* Table: CFG_SOCKET_ASYN                                       */
/*==============================================================*/
create table CFG_SOCKET_ASYN
(
   CFG_SOCKET_CODE      varchar(255) not null,
   IS_ASYN              char(1) not null,
   WORK_THREAD_MIN      numeric(3,0) not null,
   WORK_THREAD_MAX      numeric(3,0) not null,
   WORK_BUFFER_SIZE     numeric(6,0) not null,
   WORK_KEEP_ALIVE_TIME numeric(6,0) not null,
   SEND_THREAD_MIN      numeric(3,0) not null,
   SEND_THREAD_MAX      numeric(3,0) not null,
   SEND_BUFFER_SIZE     numeric(6,0) not null,
   SEND_KEEP_ALIVE_TIME numeric(6,0) not null,
   OVERLOAD_PROTECT_PERCENT numeric(6,0),
   STATE                char(1) not null,
   REMARKS              varchar(255),
   primary key (CFG_SOCKET_CODE)
);

/*==============================================================*/
/* Table: CFG_SOCKET_FILTER                                     */
/*==============================================================*/
create table CFG_SOCKET_FILTER
(
   FILTER_ID            numeric(12,0) not null,
   CFG_SOCKET_CODE      varchar(255) not null,
   FILTER_CLASS         varchar(255) not null,
   SORT_ID              numeric(6,0) not null,
   STATE                char(1) not null,
   REMARKS              varchar(255),
   primary key (FILTER_ID)
);

/*==============================================================*/
/* Table: CFG_SOCKET_MAPPING                                    */
/*==============================================================*/
create table CFG_SOCKET_MAPPING
(
   MAPPING_ID           numeric(12,0) not null,
   CFG_SOCKET_CODE      varchar(255) not null,
   MAPPING_NAME         varchar(1000) not null,
   MAPPING_VALUE        varchar(1000) not null,
   STATE                char(1) not null,
   REMARKS              varchar(255),
   primary key (MAPPING_ID)
);

/*==============================================================*/
/* Table: CFG_TABLE_SPLIT                                       */
/*==============================================================*/
create table CFG_TABLE_SPLIT
(
   TABLE_NAME           varchar(255) not null,
   TABLE_NAME_EXPR      varchar(255) not null,
   STATE                char(1) not null,
   REMARKS              varchar(255)
);

/*==============================================================*/
/* Table: CFG_TABLE_SPLIT_MAPPING                               */
/*==============================================================*/
create table CFG_TABLE_SPLIT_MAPPING
(
   MAPPING_ID           numeric(12,0) not null,
   TABLE_NAME           varchar(255) not null,
   COLUMN_NAME          varchar(255) not null,
   COLUMN_CONVERT_CLASS varchar(255) not null,
   STATE                char(1) not null,
   REMARKS              varchar(255)
);

/*==============================================================*/
/* Table: CFG_WS                                                */
/*==============================================================*/
create table CFG_WS
(
   CFG_WS_CODE          varchar(255) not null,
   WS_GRP               varchar(255) not null,
   WS_DESC              varchar(1000),
   STATE                char(1) not null,
   REMARKS              varchar(255),
   primary key (CFG_WS_CODE)
);

/*==============================================================*/
/* Table: CFG_WS_CLIENT                                         */
/*==============================================================*/
create table CFG_WS_CLIENT
(
   CFG_WS_CLIENT_CODE   varchar(255) not null,
   URL_ADDRESS          varchar(4000) not null,
   STATE                char(1) not null,
   REMARKS              varchar(255),
   primary key (CFG_WS_CLIENT_CODE)
);

/*==============================================================*/
/* Table: CFG_WS_CLIENT_CONTROL                                 */
/*==============================================================*/
create table CFG_WS_CLIENT_CONTROL
(
   METHOD_NAME          varchar(255) not null,
   WS_CLIENT_CODE       varchar(255) not null,
   SERVER_NAME          varchar(255) not null,
   TIMEOUT_SECONDS      numeric(6,0) not null,
   CONCURRENT_LIMIT     numeric(3,0) not null,
   CONTROL_RULE         varchar(1000),
   STATE                char(1) not null,
   REMARKS              varchar(255),
   primary key (WS_CLIENT_CODE, METHOD_NAME, SERVER_NAME)
);

/*==============================================================*/
/* Index: IDX_CFG_WS_CLIENT_CONTROL_AMS                         */
/*==============================================================*/
create index IDX_CFG_WS_CLIENT_CONTROL_AMS on CFG_WS_CLIENT_CONTROL
(
   SERVER_NAME
);

/*==============================================================*/
/* Table: CFG_WS_CLIENT_METHOD                                  */
/*==============================================================*/
create table CFG_WS_CLIENT_METHOD
(
   METHOD_NAME          varchar(255) not null,
   CFG_WS_CLIENT_CODE   varchar(255) not null,
   METHOD_PARAMETER     varchar(4000) not null,
   METHOD_RETURN_TYPE   varchar(4000) not null,
   REGISTER_TYPE_MAPPING varchar(4000),
   TIMEOUT_SECONDS      numeric(12,0) not null,
   OPERATION_STYLE      varchar(255),
   OPERATION_USE        varchar(255),
   SOAP_VERSION         varchar(255),
   STATE                char(1) not null,
   REMARKS              varchar(255),
   primary key (CFG_WS_CLIENT_CODE, METHOD_NAME)
);

/*==============================================================*/
/* Table: CFG_WS_MAPPING                                        */
/*==============================================================*/
create table CFG_WS_MAPPING
(
   MAPPING_ID           numeric(12,0) not null,
   CFG_WS_CODE          varchar(255) not null,
   MAPPING_NAME         varchar(4000) not null,
   MAPPING_VALUE1       varchar(4000) not null,
   MAPPING_VALUE2       varchar(4000),
   MAPPING_VALUE3       varchar(4000),
   MAPPING_VALUE4       varchar(4000),
   STATE                char(1) not null,
   REMARKS              varchar(255),
   primary key (MAPPING_ID)
);

/*==============================================================*/
/* Index: IDX_CFG_WS_MAPPING_AMS                                */
/*==============================================================*/
create index IDX_CFG_WS_MAPPING_AMS on CFG_WS_MAPPING
(
   CFG_WS_CODE
);

/*==============================================================*/
/* Table: H_AI_DEPLOY_STRATEGY                                  */
/*==============================================================*/
create table H_AI_DEPLOY_STRATEGY
(
   DEPLOY_STRATEGY_HISTORY_ID numeric(12,0) not null comment '部署策略历史id',
   HISTORY_CREATE_TIME  datetime,
   DEPLOY_STRATEGY_ID   numeric(12,0) not null comment '部署策略id',
   DEPLOY_STRATEGY_NAME varchar(128) comment '部署策略名',
   CLIENT_HOME_PATH     varchar(256) comment '家目录',
   CLIENT_BIN_PATH      varchar(256),
   CLIENT_SBIN_PATH     varchar(256),
   SERVER_PACKAGE_PATH  varchar(256) comment '服务器包路径',
   HISTORY_PACKAGE_NUM  numeric(1,0) comment '保留版本数',
   FTP_HOST_IP          varchar(128) comment 'ftp主机ip',
   FTP_HOST_PORT        numeric(5,0) comment 'ftp主机端口',
   FTP_PROTOCOL         char(1) comment 'ftp协议',
   FTP_PACKAGE_PATH     varchar(512) comment 'ftp包路径',
   FTP_USER_NAME        varchar(256) comment 'ftp用户名',
   FTP_PASSWORD         varchar(256) comment 'ftp密码',
   INSTALL_RULE         varchar(1024) comment '安装规则',
   TEMPLATE_ID          numeric(12,0) comment '模板id',
   STOP_TEMPLATE_ID     numeric(12,0) comment '停止模板id',
   CREATE_TIME          datetime comment '创建时间',
   MODIFY_TIME          datetime comment '修改时间',
   OPERATOR_ID          numeric(12,0) comment '操作员',
   REMARKS              varchar(512) comment '备注',
   CLIENT_LOG_PATH      varchar(256),
   primary key (DEPLOY_STRATEGY_HISTORY_ID)
);

alter table H_AI_DEPLOY_STRATEGY comment '部署策略历史表';

/*==============================================================*/
/* Table: SYS_SEQUENCES                                         */
/*==============================================================*/
create table SYS_SEQUENCES
(
   SEQUENCE_NAME        varchar(60) not null,
   START_BY             numeric(20,0),
   INCREMENT_BY         numeric(10,0),
   LAST_NUMBER          numeric(20,0),
   primary key (SEQUENCE_NAME)
);

