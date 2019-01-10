/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201703                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201703
(
   ERR_ORDER_ID         numeric(12,0) not null,
   SERVER_CODE          varchar(40),
   SERIAL_NO            numeric(12,0),
   TASK_ID              varchar(12) not null comment '�����ʶ',
   TASK_SPLIT_ID        varchar(255) comment '��ʽ�ݶ�Ϊ��570^��Ƭ��ʶ���ַ�Ƭ��ʽ���˻���ʶ�������˻�+����',
   REGION_CODE          char(3),
   ERR_CNT              numeric(9,0),
   HANDLE_TIME          numeric(12,0) comment '��',
   SYSTEM_DOMAIN        varchar(255) not null comment 'Ĭ�ϣ�CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '��־������ϵͳ',
   APP_SERVER_NAME      varchar(255) not null comment 'ҵ����̲࣬��дAPPCODE',
   CREATE_DATE          varchar(255) not null,
   ACQ_DATE             datetime not null,
   MON_FLAG             char(1) comment '��ʶ����Ƿ��ȡ��',
   primary key (ERR_ORDER_ID)
);

alter table ABG_MON_BUSI_ERR_LOG_201703 comment '�������';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201704                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201704
(
   ERR_ORDER_ID         numeric(12,0) not null,
   SERVER_CODE          varchar(40),
   SERIAL_NO            numeric(12,0),
   TASK_ID              varchar(12) not null comment '�����ʶ',
   TASK_SPLIT_ID        varchar(255) comment '��ʽ�ݶ�Ϊ��570^��Ƭ��ʶ���ַ�Ƭ��ʽ���˻���ʶ�������˻�+����',
   REGION_CODE          char(3),
   ERR_CNT              numeric(9,0),
   HANDLE_TIME          numeric(12,0) comment '��',
   SYSTEM_DOMAIN        varchar(255) not null comment 'Ĭ�ϣ�CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '��־������ϵͳ',
   APP_SERVER_NAME      varchar(255) not null comment 'ҵ����̲࣬��дAPPCODE',
   CREATE_DATE          varchar(255) not null,
   ACQ_DATE             datetime not null,
   MON_FLAG             char(1) comment '��ʶ����Ƿ��ȡ��',
   primary key (ERR_ORDER_ID)
);

alter table ABG_MON_BUSI_ERR_LOG_201704 comment '�������';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201705                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201705
(
   ERR_ORDER_ID         numeric(12,0) not null,
   SERVER_CODE          varchar(40),
   SERIAL_NO            numeric(12,0),
   TASK_ID              varchar(12) not null comment '�����ʶ',
   TASK_SPLIT_ID        varchar(255) comment '��ʽ�ݶ�Ϊ��570^��Ƭ��ʶ���ַ�Ƭ��ʽ���˻���ʶ�������˻�+����',
   REGION_CODE          char(3),
   ERR_CNT              numeric(9,0),
   HANDLE_TIME          numeric(12,0) comment '��',
   SYSTEM_DOMAIN        varchar(255) not null comment 'Ĭ�ϣ�CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '��־������ϵͳ',
   APP_SERVER_NAME      varchar(255) not null comment 'ҵ����̲࣬��дAPPCODE',
   CREATE_DATE          varchar(255) not null,
   ACQ_DATE             datetime not null,
   MON_FLAG             char(1) comment '��ʶ����Ƿ��ȡ��',
   primary key (ERR_ORDER_ID)
);

alter table ABG_MON_BUSI_ERR_LOG_201705 comment '�������';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201706                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201706
(
   ERR_ORDER_ID         numeric(12,0) not null,
   SERVER_CODE          varchar(40),
   SERIAL_NO            numeric(12,0),
   TASK_ID              varchar(12) not null comment '�����ʶ',
   TASK_SPLIT_ID        varchar(255) comment '��ʽ�ݶ�Ϊ��570^��Ƭ��ʶ���ַ�Ƭ��ʽ���˻���ʶ�������˻�+����',
   REGION_CODE          char(3),
   ERR_CNT              numeric(9,0),
   HANDLE_TIME          numeric(12,0) comment '��',
   SYSTEM_DOMAIN        varchar(255) not null comment 'Ĭ�ϣ�CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '��־������ϵͳ',
   APP_SERVER_NAME      varchar(255) not null comment 'ҵ����̲࣬��дAPPCODE',
   CREATE_DATE          varchar(255) not null,
   ACQ_DATE             datetime not null,
   MON_FLAG             char(1) comment '��ʶ����Ƿ��ȡ��',
   primary key (ERR_ORDER_ID)
);

alter table ABG_MON_BUSI_ERR_LOG_201706 comment '�������';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201707                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201707
(
   ERR_ORDER_ID         numeric(12,0) not null,
   SERVER_CODE          varchar(40),
   SERIAL_NO            numeric(12,0),
   TASK_ID              varchar(12) not null comment '�����ʶ',
   TASK_SPLIT_ID        varchar(255) comment '��ʽ�ݶ�Ϊ��570^��Ƭ��ʶ���ַ�Ƭ��ʽ���˻���ʶ�������˻�+����',
   REGION_CODE          char(3),
   ERR_CNT              numeric(9,0),
   HANDLE_TIME          numeric(12,0) comment '��',
   SYSTEM_DOMAIN        varchar(255) not null comment 'Ĭ�ϣ�CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '��־������ϵͳ',
   APP_SERVER_NAME      varchar(255) not null comment 'ҵ����̲࣬��дAPPCODE',
   CREATE_DATE          varchar(255) not null,
   ACQ_DATE             datetime not null,
   MON_FLAG             char(1) comment '��ʶ����Ƿ��ȡ��',
   primary key (ERR_ORDER_ID)
);

alter table ABG_MON_BUSI_ERR_LOG_201707 comment '�������';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201708                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201708
(
   ERR_ORDER_ID         numeric(12,0) not null,
   SERVER_CODE          varchar(40),
   SERIAL_NO            numeric(12,0),
   TASK_ID              varchar(12) not null comment '�����ʶ',
   TASK_SPLIT_ID        varchar(255) comment '��ʽ�ݶ�Ϊ��570^��Ƭ��ʶ���ַ�Ƭ��ʽ���˻���ʶ�������˻�+����',
   REGION_CODE          char(3),
   ERR_CNT              numeric(9,0),
   HANDLE_TIME          numeric(12,0) comment '��',
   SYSTEM_DOMAIN        varchar(255) not null comment 'Ĭ�ϣ�CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '��־������ϵͳ',
   APP_SERVER_NAME      varchar(255) not null comment 'ҵ����̲࣬��дAPPCODE',
   CREATE_DATE          varchar(255) not null,
   ACQ_DATE             datetime not null,
   MON_FLAG             char(1) comment '��ʶ����Ƿ��ȡ��',
   primary key (ERR_ORDER_ID)
);

alter table ABG_MON_BUSI_ERR_LOG_201708 comment '�������';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201709                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201709
(
   ERR_ORDER_ID         numeric(12,0) not null,
   SERVER_CODE          varchar(40),
   SERIAL_NO            numeric(12,0),
   TASK_ID              varchar(12) not null comment '�����ʶ',
   TASK_SPLIT_ID        varchar(255) comment '��ʽ�ݶ�Ϊ��570^��Ƭ��ʶ���ַ�Ƭ��ʽ���˻���ʶ�������˻�+����',
   REGION_CODE          char(3),
   ERR_CNT              numeric(9,0),
   HANDLE_TIME          numeric(12,0) comment '��',
   SYSTEM_DOMAIN        varchar(255) not null comment 'Ĭ�ϣ�CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '��־������ϵͳ',
   APP_SERVER_NAME      varchar(255) not null comment 'ҵ����̲࣬��дAPPCODE',
   CREATE_DATE          varchar(255) not null,
   ACQ_DATE             datetime not null,
   MON_FLAG             char(1) comment '��ʶ����Ƿ��ȡ��',
   primary key (ERR_ORDER_ID)
);

alter table ABG_MON_BUSI_ERR_LOG_201709 comment '�������';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201710                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201710
(
   ERR_ORDER_ID         numeric(12,0) not null,
   SERVER_CODE          varchar(40),
   SERIAL_NO            numeric(12,0),
   TASK_ID              varchar(12) not null comment '�����ʶ',
   TASK_SPLIT_ID        varchar(255) comment '��ʽ�ݶ�Ϊ��570^��Ƭ��ʶ���ַ�Ƭ��ʽ���˻���ʶ�������˻�+����',
   REGION_CODE          char(3),
   ERR_CNT              numeric(9,0),
   HANDLE_TIME          numeric(12,0) comment '��',
   SYSTEM_DOMAIN        varchar(255) not null comment 'Ĭ�ϣ�CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '��־������ϵͳ',
   APP_SERVER_NAME      varchar(255) not null comment 'ҵ����̲࣬��дAPPCODE',
   CREATE_DATE          varchar(255) not null,
   ACQ_DATE             datetime not null,
   MON_FLAG             char(1) comment '��ʶ����Ƿ��ȡ��',
   primary key (ERR_ORDER_ID)
);

alter table ABG_MON_BUSI_ERR_LOG_201710 comment '�������';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201711                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201711
(
   ERR_ORDER_ID         numeric(12,0) not null,
   SERVER_CODE          varchar(40),
   SERIAL_NO            numeric(12,0),
   TASK_ID              varchar(12) not null comment '�����ʶ',
   TASK_SPLIT_ID        varchar(255) comment '��ʽ�ݶ�Ϊ��570^��Ƭ��ʶ���ַ�Ƭ��ʽ���˻���ʶ�������˻�+����',
   REGION_CODE          char(3),
   ERR_CNT              numeric(9,0),
   HANDLE_TIME          numeric(12,0) comment '��',
   SYSTEM_DOMAIN        varchar(255) not null comment 'Ĭ�ϣ�CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '��־������ϵͳ',
   APP_SERVER_NAME      varchar(255) not null comment 'ҵ����̲࣬��дAPPCODE',
   CREATE_DATE          varchar(255) not null,
   ACQ_DATE             datetime not null,
   MON_FLAG             char(1) comment '��ʶ����Ƿ��ȡ��',
   primary key (ERR_ORDER_ID)
);

alter table ABG_MON_BUSI_ERR_LOG_201711 comment '�������';

/*==============================================================*/
/* Table: ABG_MON_BUSI_ERR_LOG_201712                           */
/*==============================================================*/
create table ABG_MON_BUSI_ERR_LOG_201712
(
   ERR_ORDER_ID         numeric(12,0) not null,
   SERVER_CODE          varchar(40),
   SERIAL_NO            numeric(12,0),
   TASK_ID              varchar(12) not null comment '�����ʶ',
   TASK_SPLIT_ID        varchar(255) comment '��ʽ�ݶ�Ϊ��570^��Ƭ��ʶ���ַ�Ƭ��ʽ���˻���ʶ�������˻�+����',
   REGION_CODE          char(3),
   ERR_CNT              numeric(9,0),
   HANDLE_TIME          numeric(12,0) comment '��',
   SYSTEM_DOMAIN        varchar(255) not null comment 'Ĭ�ϣ�CRM',
   SUBSYSTEM_DOMAIN     varchar(255) not null comment '��־������ϵͳ',
   APP_SERVER_NAME      varchar(255) not null comment 'ҵ����̲࣬��дAPPCODE',
   CREATE_DATE          varchar(255) not null,
   ACQ_DATE             datetime not null,
   MON_FLAG             char(1) comment '��ʶ����Ƿ��ȡ��',
   primary key (ERR_ORDER_ID)
);

alter table ABG_MON_BUSI_ERR_LOG_201712 comment '�������';

