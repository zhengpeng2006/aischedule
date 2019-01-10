/*==============================================================*/
/* Table: ABG_MON_HOST_LOG_201703                               */
/*==============================================================*/
create table ABG_MON_HOST_LOG_201703
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

alter table ABG_MON_HOST_LOG_201703 comment '记录主机cpu，mem，file的kpi信息';

/*==============================================================*/
/* Table: ABG_MON_HOST_LOG_201704                               */
/*==============================================================*/
create table ABG_MON_HOST_LOG_201704
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

alter table ABG_MON_HOST_LOG_201704 comment '记录主机cpu，mem，file的kpi信息';

/*==============================================================*/
/* Table: ABG_MON_HOST_LOG_201705                               */
/*==============================================================*/
create table ABG_MON_HOST_LOG_201705
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

alter table ABG_MON_HOST_LOG_201705 comment '记录主机cpu，mem，file的kpi信息';

/*==============================================================*/
/* Table: ABG_MON_HOST_LOG_201706                               */
/*==============================================================*/
create table ABG_MON_HOST_LOG_201706
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

alter table ABG_MON_HOST_LOG_201706 comment '记录主机cpu，mem，file的kpi信息';

/*==============================================================*/
/* Table: ABG_MON_HOST_LOG_201707                               */
/*==============================================================*/
create table ABG_MON_HOST_LOG_201707
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

alter table ABG_MON_HOST_LOG_201707 comment '记录主机cpu，mem，file的kpi信息';

/*==============================================================*/
/* Table: ABG_MON_HOST_LOG_201708                               */
/*==============================================================*/
create table ABG_MON_HOST_LOG_201708
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

alter table ABG_MON_HOST_LOG_201708 comment '记录主机cpu，mem，file的kpi信息';

/*==============================================================*/
/* Table: ABG_MON_HOST_LOG_201709                               */
/*==============================================================*/
create table ABG_MON_HOST_LOG_201709
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

alter table ABG_MON_HOST_LOG_201709 comment '记录主机cpu，mem，file的kpi信息';

/*==============================================================*/
/* Table: ABG_MON_HOST_LOG_201710                               */
/*==============================================================*/
create table ABG_MON_HOST_LOG_201710
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

alter table ABG_MON_HOST_LOG_201710 comment '记录主机cpu，mem，file的kpi信息';

/*==============================================================*/
/* Table: ABG_MON_HOST_LOG_201711                               */
/*==============================================================*/
create table ABG_MON_HOST_LOG_201711
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

alter table ABG_MON_HOST_LOG_201711 comment '记录主机cpu，mem，file的kpi信息';

/*==============================================================*/
/* Table: ABG_MON_HOST_LOG_201712                               */
/*==============================================================*/
create table ABG_MON_HOST_LOG_201712
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

alter table ABG_MON_HOST_LOG_201712 comment '记录主机cpu，mem，file的kpi信息';