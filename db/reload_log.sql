/*==============================================================*/
/* Table: RELOAD_LOG                                            */
/*==============================================================*/
create table RELOAD_LOG
(
   LOG_ID               numeric(12,0) not null comment 'id',
   SRC_TAB              varchar(255) comment '原表名',
   SRC_INSTANCE         varchar(1024),
   DEST_TAB             varchar(255) comment '目的表',
   MOD_COUNT            numeric(10,0) comment '修改记录数',
   DEL_COUNT            numeric(10,0) comment '删除记录数',
   DONE_DATE            datetime comment '处理时间',
   primary key (LOG_ID)
);

/*==============================================================*/
/* Index: IDX_RELOAD_LOG_1                                      */
/*==============================================================*/
create index IDX_RELOAD_LOG_1 on RELOAD_LOG
(
   DONE_DATE
);

