/*==============================================================*/
/* Table: ABG_AI_SCHED_TASK_LOG                                 */
/*==============================================================*/
create table ABG_AI_SCHED_TASK_LOG
(
   SYSTEM_DOMAIN        varchar(255) not null,
   SUBSYSTEM_DOMAIN     varchar(255) not null,
   APP_SERVER_NAME      varchar(255) not null,
   TASK_CODE            varchar(255) not null,
   TASK_NAME            varchar(255),
   TASK_VERSION         varchar(12) not null,
   JOB_ID               varchar(255) not null,
   START_TIME           datetime,
   FINISH_TIME          datetime,
   STATE                varchar(2) not null,
   CREATE_DATE          datetime not null,
   LOG_DATE             datetime not null,
   primary key (JOB_ID)
);

/*==============================================================*/
/* Table: ABG_AI_SCHED_TASK_LOG_DTL                             */
/*==============================================================*/
create table ABG_AI_SCHED_TASK_LOG_DTL
(
   SYSTEM_DOMAIN        varchar(255) not null,
   SUBSYSTEM_DOMAIN     varchar(255) not null,
   APP_SERVER_NAME      varchar(255) not null,
   TASK_CODE            varchar(255) not null,
   TASK_VERSION         varchar(12) not null,
   JOB_ID               varchar(255) not null,
   LOG_TYPE             varchar(12) not null,
   TASK_ITEM            varchar(255),
   CREATE_DATE          datetime not null,
   OPERATOR             varchar(255),
   OP_INFO              varchar(2000),
   LOG_DATE             datetime not null,
   EX_MSG               varchar(4000)
);

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG                                  */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG
(
   ERR_ORDER_ID         numeric(12,0) not null,
   SERVER_CODE          varchar(40),
   SERIAL_NO            numeric(12,0),
   TASK_ID              varchar(12) not null comment '任务标识',
   TASK_SPLIT_ID        varchar(255) comment '格式暂定为：570^分片标识三种分片方式：账户标识、区域、账户+区域',
   REGION_CODE          char(3),
   ERR_CNT              numeric(9,0),
   HANDLE_TIME          numeric(12,0) comment '秒',
   SYSTEM_DOMAIN        varchar(255) not null comment '默认：CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '日志所属子系统',
   APP_SERVER_NAME      varchar(255) not null comment '业务进程侧，填写APPCODE',
   CREATE_DATE          varchar(255) not null,
   ACQ_DATE             datetime not null,
   MON_FLAG             char(1) comment '标识监控是否读取过',
   primary key (ERR_ORDER_ID)
);

alter table ABG_MON_BUSI_ERR_LOG comment '处理错单量';

/*==============================================================*/
/* Table: ABG_MON_BUSI_LOG                                      */
/*==============================================================*/
create table ABG_MON_BUSI_LOG
(
   THROUGHPUT_ID        numeric(12,0) not null comment '主键ID',
   SERIAL_NO            numeric(12,0) comment '流水号',
   TASK_ID              varchar(255) not null comment '任务编号',
   TASK_SPLIT_ID        varchar(255) comment '任务分片标识',
   SERVER_CODE          varchar(255) comment '应用服务编码',
   TOTAL_CNT            numeric(12,0) comment '业务处理总量',
   PER_HANDLE_CNT       numeric(12,0) comment '当次处理量',
   HANDLE_CNT           numeric(12,0) comment '当前总处理量',
   PER_ERR_CNT          numeric(12,0) comment '当次处理错单量',
   ERR_CNT              numeric(12,0) comment '当前错误总量',
   REGION_CODE          char(3) comment '地市编码',
   CONSUME_TIME         numeric(12,0) comment '当次处理消耗时间',
   SYSTEM_DOMAIN        varchar(255) not null comment '默认：CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '日志所属子系统',
   APP_SERVER_NAME      varchar(255) not null comment '业务进程侧，填写APPCODE',
   CREATE_DATE          datetime not null comment '日志生成时间',
   SEQ                  numeric(12,0) comment '序号',
   MON_FLAG             char(1) comment '默认：N N：未监控展示Y：已监控展示',
   EXT1                 varchar(40),
   EXT2                 varchar(40),
   primary key (THROUGHPUT_ID)
);

alter table ABG_MON_BUSI_LOG comment '业务监控日志表';


/*==============================================================*/
/* Table: ABG_MON_HOST_LOG                                      */
/*==============================================================*/
create table ABG_MON_HOST_LOG
(
   ACQ_LOG_ID           numeric(12,0) not null,
   HOST_IP              varchar(255) not null,
   KPI_CPU              varchar(10) comment '主机CPU使用信息',
   KPI_MEM              varchar(10) comment '内存占比信息',
   KPI_FS               varchar(1000) comment '文件系统占比信息',
   EXT_KPI_1            varchar(10) comment '保留扩展字段，以便后续需要新增主机kpi信息的保存',
   EXT_KPI_2            varchar(10),
   EXT_KPI_3            varchar(10),
   EXT_KPI_4            varchar(10),
   SYSTEM_DOMAIN        varchar(255) not null comment '默认：CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '日志所属子系统',
   APP_SERVER_NAME      varchar(255) not null comment '业务进程侧，填写APPCODE',
   CREATE_DATE          varchar(255) not null,
   MON_FLAG             char(1) comment '默认：NN：未监控展示Y：已监控展示',
   ACQ_DATE             datetime not null,
   STATE                char(1)
);

alter table ABG_MON_HOST_LOG comment '记录主机cpu，mem，file的kpi信息';



/*==============================================================*/
/* Table: ABG_MON_HOST_LOG_BAK                                  */
/*==============================================================*/
create table ABG_MON_HOST_LOG_BAK
(
   ACQ_LOG_ID           numeric(12,0) not null,
   HOST_ID              numeric(12,0) not null,
   KPI_CPU              varchar(10) comment '主机CPU使用信息',
   KPI_MEM              varchar(10) comment '内存占比信息',
   KPI_FS               varchar(10) comment '文件系统占比信息',
   EXT_KPI_1            varchar(10) comment '保留扩展字段，以便后续需要新增主机kpi信息的保存',
   EXT_KPI_2            varchar(10),
   EXT_KPI_3            varchar(10),
   EXT_KPI_4            varchar(10),
   SYSTEM_DOMAIN        varchar(255) not null comment '默认：CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '日志所属子系统',
   APP_SERVER_NAME      varchar(255) not null comment '业务进程侧，填写APPCODE',
   CREATE_DATE          varchar(255) not null,
   MON_FLAG             char(1) comment '默认：N N：未监控展示Y：已监控展示',
   ACQ_DATE             datetime not null,
   STATE                char(1),
   primary key (ACQ_LOG_ID)
);

alter table ABG_MON_HOST_LOG_BAK comment '记录主机cpu，mem，file的kpi信息';

/*==============================================================*/
/* Table: ABG_MON_L_RECORD                                      */
/*==============================================================*/
create table ABG_MON_L_RECORD
(
   RECORD_ID            numeric(12,0) not null,
   INFO_ID              numeric(12,0),
   INFO_CODE            varchar(40),
   HOST_NAME            varchar(255),
   IP                   varchar(255),
   MON_TYPE             varchar(255),
   INFO_NAME            varchar(255),
   LAYER                varchar(255),
   FROM_TYPE            varchar(40),
   GROUP_ID             varchar(255),
   IS_TRIGGER_WARN      char(1),
   WARN_LEVEL           varchar(255),
   INFO_VALUE           varchar(4000),
   DONE_CODE            numeric(12,0),
   DONE_DATE            datetime,
   BATCH_ID             numeric(12,0),
   VALID_DATE           datetime,
   EXPIRE_DATE          datetime,
   OP_ID                numeric(12,0),
   ORG_ID               numeric(12,0),
   SYSTEM_DOMAIN        varchar(255) not null,
   SUBSYSTEM_DOMAIN     varchar(255) not null,
   APP_SERVER_NAME      varchar(255) not null,
   CREATE_DATE          varchar(255) not null,
   CREATE_TIME          datetime,
   REMARKS              varchar(255)
);



/*==============================================================*/
/* Table: ABG_MON_W_TRIGGER                                     */
/*==============================================================*/
create table ABG_MON_W_TRIGGER
(
   TRIGGER_ID           numeric(12,0) not null,
   RECORD_ID            numeric(12,0),
   IP                   varchar(255),
   INFO_ID              numeric(12,0),
   INFO_NAME            varchar(255),
   LAYER                varchar(20),
   PHONENUM             varchar(4000),
   CONTENT              varchar(4000),
   WARN_LEVEL           varchar(255),
   EXPIRY_DATE          datetime,
   SYSTEM_DOMAIN        varchar(255) not null,
   SUBSYSTEM_DOMAIN     varchar(255) not null,
   APP_SERVER_NAME      varchar(255) not null,
   CREATE_DATE          varchar(255) not null,
   CREATE_TIME          datetime,
   DONE_DATE            datetime,
   STATE                char(1),
   REMARKS              varchar(255),
   primary key (TRIGGER_ID)
);

/*==============================================================*/
/* Table: ABG_PROCESS_LOG                                       */
/*==============================================================*/
create table ABG_PROCESS_LOG
(
   PROCESS_CODE         varchar(128),
   EXPIRE_STEP_ID       numeric(12,0) not null,
   EXPIRE_STEP_NAME     varchar(256),
   REGION_ID            varchar(128),
   TOTAL_DEAL_COUNT     numeric(12,0),
   SUCCESS_DEAL_COUNT   numeric(12,0),
   FAILED_DEAL_COUNT    numeric(12,0),
   BEGIN_DEAL_DATE      varchar(255),
   END_DEAL_DATE        varchar(255),
   DEAL_TIME_DURATION   varchar(24),
   SYSTEM_DOMAIN        varchar(255) not null,
   SUBSYSTEM_DOMAIN     varchar(255) not null,
   APP_SERVER_NAME      varchar(255) not null,
   CREATE_DATE          varchar(255) not null
);

/*==============================================================*/
/* Table: AI_SCHEDULER_OPERATIONS                               */
/*==============================================================*/
create table AI_SCHEDULER_OPERATIONS
(
   OPERATION_ID         numeric(12,0) not null,
   OPERATION_SOURCE     varchar(128),
   OPERATION_MODEL      varchar(1024),
   OPERATION_TYPE       varchar(1024),
   OPERATION_OBJECT_TYPE varchar(1024),
   OPERATION_OBJECT_CONTENT varchar(4000),
   OPERATION_CLIENT_IP  varchar(255),
   SYSTEM_DOMAIN        varchar(255) not null,
   SUBSYSTEM_DOMAIN     varchar(255) not null,
   APP_SERVER_NAME      varchar(255) not null,
   CREATE_DATE          datetime not null,
   OPERATOR             varchar(128),
   REMARKS              varchar(4000),
   primary key (OPERATION_ID)
);