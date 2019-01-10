/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201703                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201703
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

alter table ABG_MON_BUSI_ERR_LOG_201703 comment '处理错单量';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201704                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201704
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

alter table ABG_MON_BUSI_ERR_LOG_201704 comment '处理错单量';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201705                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201705
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

alter table ABG_MON_BUSI_ERR_LOG_201705 comment '处理错单量';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201706                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201706
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

alter table ABG_MON_BUSI_ERR_LOG_201706 comment '处理错单量';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201707                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201707
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

alter table ABG_MON_BUSI_ERR_LOG_201707 comment '处理错单量';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201708                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201708
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

alter table ABG_MON_BUSI_ERR_LOG_201708 comment '处理错单量';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201709                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201709
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

alter table ABG_MON_BUSI_ERR_LOG_201709 comment '处理错单量';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201710                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201710
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

alter table ABG_MON_BUSI_ERR_LOG_201710 comment '处理错单量';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201711                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201711
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

alter table ABG_MON_BUSI_ERR_LOG_201711 comment '处理错单量';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201712                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201712
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

alter table ABG_MON_BUSI_ERR_LOG_201712 comment '处理错单量';

