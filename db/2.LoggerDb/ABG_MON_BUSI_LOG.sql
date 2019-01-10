/*==============================================================*/
/* Table: ABG_MON_BUSI_LOG_201703                               */
/*==============================================================*/
create table ABG_MON_BUSI_LOG_201703
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

alter table ABG_MON_BUSI_LOG_201703 comment '业务监控日志表';

/*==============================================================*/
/* Table: ABG_MON_BUSI_LOG_201704                               */
/*==============================================================*/
create table ABG_MON_BUSI_LOG_201704
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

alter table ABG_MON_BUSI_LOG_201704 comment '业务监控日志表';

/*==============================================================*/
/* Table: ABG_MON_BUSI_LOG_201705                               */
/*==============================================================*/
create table ABG_MON_BUSI_LOG_201705
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

alter table ABG_MON_BUSI_LOG_201705 comment '业务监控日志表';

/*==============================================================*/
/* Table: ABG_MON_BUSI_LOG_201706                               */
/*==============================================================*/
create table ABG_MON_BUSI_LOG_201706
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

alter table ABG_MON_BUSI_LOG_201706 comment '业务监控日志表';

/*==============================================================*/
/* Table: ABG_MON_BUSI_LOG_201707                               */
/*==============================================================*/
create table ABG_MON_BUSI_LOG_201707
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

alter table ABG_MON_BUSI_LOG_201707 comment '业务监控日志表';

/*==============================================================*/
/* Table: ABG_MON_BUSI_LOG_201708                               */
/*==============================================================*/
create table ABG_MON_BUSI_LOG_201708
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

alter table ABG_MON_BUSI_LOG_201708 comment '业务监控日志表';

/*==============================================================*/
/* Table: ABG_MON_BUSI_LOG_201709                               */
/*==============================================================*/
create table ABG_MON_BUSI_LOG_201709
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

alter table ABG_MON_BUSI_LOG_201709 comment '业务监控日志表';

/*==============================================================*/
/* Table: ABG_MON_BUSI_LOG_201710                               */
/*==============================================================*/
create table ABG_MON_BUSI_LOG_201710
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

alter table ABG_MON_BUSI_LOG_201710 comment '业务监控日志表';

/*==============================================================*/
/* Table: ABG_MON_BUSI_LOG_201711                               */
/*==============================================================*/
create table ABG_MON_BUSI_LOG_201711
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

alter table ABG_MON_BUSI_LOG_201711 comment '业务监控日志表';

/*==============================================================*/
/* Table: ABG_MON_BUSI_LOG_201712                               */
/*==============================================================*/
create table ABG_MON_BUSI_LOG_201712
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

alter table ABG_MON_BUSI_LOG_201712 comment '业务监控日志表';
