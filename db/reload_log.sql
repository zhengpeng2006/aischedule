/*==============================================================*/
/* Table: RELOAD_LOG                                            */
/*==============================================================*/
create table RELOAD_LOG
(
   LOG_ID               numeric(12,0) not null comment 'id',
   SRC_TAB              varchar(255) comment 'ԭ����',
   SRC_INSTANCE         varchar(1024),
   DEST_TAB             varchar(255) comment 'Ŀ�ı�',
   MOD_COUNT            numeric(10,0) comment '�޸ļ�¼��',
   DEL_COUNT            numeric(10,0) comment 'ɾ����¼��',
   DONE_DATE            datetime comment '����ʱ��',
   primary key (LOG_ID)
);

/*==============================================================*/
/* Index: IDX_RELOAD_LOG_1                                      */
/*==============================================================*/
create index IDX_RELOAD_LOG_1 on RELOAD_LOG
(
   DONE_DATE
);

