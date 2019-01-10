/*==============================================================*/
/* Table: AI_DEPLOY_APP_PARAM                                   */
/*==============================================================*/
create table AI_DEPLOY_APP_PARAM
(
   APPLICATION_PARAM_ID numeric(12,0) not null,
   APPLICATION_ID       numeric(12,0) not null comment 'Ӧ��id',
   _KEY                  varchar(64) not null comment '���� ',
   _VALUE                varchar(64) comment 'ֵ',
   PARAM_TYPE           char(1) default 'T',
   primary key (APPLICATION_PARAM_ID)
);

alter table AI_DEPLOY_APP_PARAM comment '����Ӧ�ò��ǲ������ñ�';

/*==============================================================*/
/* Table: AI_DEPLOY_APP_TEMPLATE                                */
/*==============================================================*/
create table AI_DEPLOY_APP_TEMPLATE
(
   TEMPLATE_ID          numeric(12,0) not null comment 'ģ��id',
   TEMPLATE_NAME        varchar(128) comment 'ģ������',
   CONTENTS             varchar(4000),
   CREATE_TIME          datetime comment '����ʱ��',
   MODIFY_TIME          datetime comment '�޸�ʱ��',
   OPERATOR_ID          numeric(12,0) comment '������id',
   REMARKS              varchar(512) comment '��ע',
   primary key (TEMPLATE_ID)
);

alter table AI_DEPLOY_APP_TEMPLATE comment '��ͣģ�����ñ�';

/*==============================================================*/
/* Table: AI_DEPLOY_NODE_STRATEGY_RELA                          */
/*==============================================================*/
create table AI_DEPLOY_NODE_STRATEGY_RELA
(
   NODE_ID              numeric(12,0) not null comment '�ڵ�ID',
   DEPLOY_STRATEGY_ID   numeric(12,0) comment '�������ID',
   CREATE_TIME          datetime,
   MODIFY_TIME          datetime,
   primary key (NODE_ID)
);

alter table AI_DEPLOY_NODE_STRATEGY_RELA comment '�ڵ��벿����Թ�ϵ��';

/*==============================================================*/
/* Table: AI_DEPLOY_NODE_VERSION                                */
/*==============================================================*/
create table AI_DEPLOY_NODE_VERSION
(
   NODE_ID              numeric(12,0) not null comment '�ڵ�ID',
   REMARKS              varchar(512),
   VERSION_ID           numeric(12,0) comment '�汾id',
   CREATE_DATE          timestamp comment '����ʱ��',
   OPERATOR_ID          numeric(12,0) comment '������id',
   primary key (NODE_ID)
);

alter table AI_DEPLOY_NODE_VERSION comment '�����ڵ�汾��';

/*==============================================================*/
/* Table: AI_DEPLOY_STRATEGY                                    */
/*==============================================================*/
create table AI_DEPLOY_STRATEGY
(
   DEPLOY_STRATEGY_ID   numeric(12,0) not null comment '�������id',
   DEPLOY_STRATEGY_NAME varchar(128) comment '�����������',
   CLIENT_HOME_PATH     varchar(256) comment '��Ŀ¼',
   CLIENT_BIN_PATH      varchar(256),
   CLIENT_SBIN_PATH     varchar(256),
   SERVER_PACKAGE_PATH  varchar(256) comment '��������·��',
   HISTORY_PACKAGE_NUM  numeric(1,0) comment '��ʷ�汾��',
   FTP_HOST_IP          varchar(128) comment 'ftp����ip',
   FTP_HOST_PORT        numeric(5,0) comment 'ftp�˿�',
   FTP_PROTOCOL         char(1) comment 'ftpЭ��',
   FTP_PACKAGE_PATH     varchar(512) comment 'ftp��·��',
   FTP_USER_NAME        varchar(256) comment 'ftp�û���',
   FTP_PASSWORD         varchar(256) comment 'ftp����',
   INSTALL_RULE         varchar(1024) comment '��װ����',
   TEMPLATE_ID          numeric(12,0),
   STOP_TEMPLATE_ID     numeric(12,0),
   CREATE_TIME          datetime comment '����ʱ��',
   MODIFY_TIME          datetime comment '�޸�ʱ��',
   OPERATOR_ID          numeric(12,0) comment '����Ա',
   REMARKS              varchar(512) comment '��ע',
   CLIENT_LOG_PATH      varchar(256),
   primary key (DEPLOY_STRATEGY_ID)
);

alter table AI_DEPLOY_STRATEGY comment '������Ա�';

/*==============================================================*/
/* Table: AI_DEPLOY_VERSION                                     */
/*==============================================================*/
create table AI_DEPLOY_VERSION
(
   VERSION_ID           numeric(12,0) not null comment '�汾id',
   EXTERNAL_VERSION_ID  numeric(12,0) comment '�ⲿ�汾id',
   DEPLOY_STRATEGY_ID   numeric(12,0) comment '�������id',
   PARENT_VERSION_ID    numeric(12,0) comment '���汾id',
   STATE                char(1) comment '״̬',
   PACKAGE_PATH         varchar(512) comment '���·��',
   OPERATE_TYPE         char(1) not null comment '��������',
   FILE_LIST            varchar(4000) comment '�ļ���',
   RESOLVED_PROBLEMS    varchar(1024) comment '���������',
   CONTACTS             varchar(512) comment '�޸���',
   REMARKS              varchar(512) comment '��ע',
   CREATE_TIME          datetime not null comment '����ʱ��',
   OPERATOR_ID          numeric(12,0) comment '����Ա',
   primary key (VERSION_ID)
);

alter table AI_DEPLOY_VERSION comment '��װ�汾��¼��';

/*==============================================================*/
/* Table: AI_MON_BATCH                                          */
/*==============================================================*/
create table AI_MON_BATCH
(
   BATCH_ID             numeric(12,0) not null comment '���Σ�1:������ĩ�³�����',
   TASK_CODE            varchar(64) not null comment '�������',
   SN                   numeric(12,0) not null comment '˳��',
   primary key (BATCH_ID, TASK_CODE)
);

alter table AI_MON_BATCH comment '������ĩ�³�����';

/*==============================================================*/
/* Table: AI_MON_CMD                                            */
/*==============================================================*/
create table AI_MON_CMD
(
   CMD_ID               numeric(12,0) not null comment '��ʶ',
   CMD_NAME             varchar(40) comment '����',
   CMD_DESC             varchar(255) comment '����',
   CMD_TYPE             varchar(12) comment '����',
   CMD_EXPR             varchar(4000) comment '����',
   PARAM_TYPE           varchar(20) comment '������������',
   STATE                char(1) comment '״̬',
   REMARK               varchar(255) comment '��ע',
   primary key (CMD_ID)
);

alter table AI_MON_CMD comment '�����';

/*==============================================================*/
/* Table: AI_MON_CMDSET                                         */
/*==============================================================*/
create table AI_MON_CMDSET
(
   CMDSET_ID            numeric(12,0) not null comment '�����ʶ',
   CMDSET_CODE          varchar(40) comment '��ʶ��',
   CMDSET_NAME          varchar(40) comment '����',
   CMDSET_DESC          varchar(255) comment '����',
   STATE                char(1) comment '״̬',
   REMARK               varchar(255) comment '��ע',
   primary key (CMDSET_ID)
);

alter table AI_MON_CMDSET comment '��������';

/*==============================================================*/
/* Table: AI_MON_CMDSET_RELAT                                   */
/*==============================================================*/
create table AI_MON_CMDSET_RELAT
(
   CMDSET_ID            numeric(12,0) comment '�����ʶ',
   CMD_ID               numeric(12,0) comment '�����ʶ',
   CMD_SEQ              numeric(3,0) comment '˳��',
   STATE                char(1) comment '״̬',
   REMARK               varchar(255) comment '��ע'
);

alter table AI_MON_CMDSET_RELAT comment '������������';

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
   DOMAIN_ID            numeric(12,0) not null comment '��ʶ',
   DOMAIN_CODE          varchar(40) comment '��ʶ��',
   DOMAIN_TYPE          varchar(255) comment '����',
   DOMAIN_DESC          varchar(255) comment '����',
   STATE                char(1) comment '״̬',
   REMARKS              varchar(255) comment '��ע',
   primary key (DOMAIN_ID)
);

alter table AI_MON_DOMAIN comment '�����Ͷ����';

/*==============================================================*/
/* Table: AI_MON_DOMAIN_RELAT                                   */
/*==============================================================*/
create table AI_MON_DOMAIN_RELAT
(
   RELAT_ID             numeric(12,0) not null comment '��ʶ',
   DOMAIN_ID            numeric(12,0) comment '���ʶ',
   RELATDOMAIN_ID       numeric(12,0) comment '�����ʶ',
   RELATDOMAIN_TYPE     varchar(2) comment '��������:1,��;2,����;3,Ӧ��',
   RELAT_DESC           varchar(255) comment '��ϵ����',
   STATE                char(1) comment '״̬',
   REMARKS              varchar(255) comment '��ע',
   primary key (RELAT_ID)
);

alter table AI_MON_DOMAIN_RELAT comment '�����ۺϹ�ϵ�����';

/*==============================================================*/
/* Table: AI_MON_GROUP                                          */
/*==============================================================*/
create table AI_MON_GROUP
(
   GROUP_ID             numeric(12,0) not null comment '��ʶ',
   GROUP_NAME           varchar(40) comment '����',
   GROUP_DESC           varchar(255) comment '������',
   CREATE_DATE          datetime comment '����ʱ��',
   STATE                char(1) comment '״̬:U-����,E-ɾ��',
   GROUP_CODE           varchar(50) not null,
   PRIV                 varchar(4000),
   primary key (GROUP_ID)
);

alter table AI_MON_GROUP comment '����Ϣ';

/*==============================================================*/
/* Table: AI_MON_GROUP_HOST_REL                                 */
/*==============================================================*/
create table AI_MON_GROUP_HOST_REL
(
   GROUP_ID             numeric(12,0) not null comment '��ID',
   HOST_ID              numeric(12,0) not null comment '����ID',
   CREATE_DATE          datetime comment '����ʱ��',
   RELATION_ID          numeric(12,0) not null,
   primary key (RELATION_ID)
);

alter table AI_MON_GROUP_HOST_REL comment '��������������ϵ��';

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
   LOG_ID               numeric(12,0) not null comment '��ʶ',
   OPERATE_ID           numeric(12,0) comment '����Ա��ID',
   OPERATE_NAME         varchar(50) comment '����Ա��',
   CLKURL               varchar(255) comment '����URL',
   CLKCODE              varchar(255) comment '����CODE',
   OPERATOR_EXPR        varchar(4000) comment '��־����',
   CREATE_TIME          datetime comment '��־��¼ʱ��',
   REMARKS              varchar(200) comment '��ע',
   primary key (LOG_ID)
);

alter table AI_MON_LOG comment 'Monitor��־';

/*==============================================================*/
/* Table: AI_MON_LOGIN                                          */
/*==============================================================*/
create table AI_MON_LOGIN
(
   IP                   varchar(50) not null comment '��¼������ip',
   LOGIN_NAME           varchar(50) not null comment '��¼�û���',
   LAST_FAIL_TIME       datetime comment '�ϴε�¼ʧ��ʱ��',
   FAIL_COUNT           numeric(10,0) comment 'ʧ�ܴ���',
   EXT_01               varchar(255),
   EXT_02               varchar(255),
   primary key (IP, LOGIN_NAME)
);

alter table AI_MON_LOGIN comment 'ͳ�Ƶ�¼ʧ����Ϣ';

/*==============================================================*/
/* Table: AI_MON_L_RECORD                                       */
/*==============================================================*/
create table AI_MON_L_RECORD
(
   RECORD_ID            numeric(12,0) not null comment '��ʶ',
   INFO_ID              numeric(12,0) comment '�����ʶ',
   INFO_CODE            varchar(40),
   HOST_NAME            varchar(255) comment '������',
   IP                   varchar(255) comment 'IP',
   MON_TYPE             varchar(255) comment '�������',
   INFO_NAME            varchar(255) comment '������',
   LAYER                varchar(255) comment '��',
   FROM_TYPE            varchar(40) comment '��Դ����',
   GROUP_ID             varchar(255) comment '����',
   IS_TRIGGER_WARN      char(1) comment '�Ƿ񾯸�',
   WARN_LEVEL           varchar(255) comment '���漶��',
   INFO_VALUE           varchar(4000) comment '����ֵ',
   DONE_CODE            numeric(12,0) comment '����',
   DONE_DATE            datetime comment '���ʱ��',
   BATCH_ID             numeric(12,0) comment '������',
   VALID_DATE           datetime comment '��Ч����',
   EXPIRE_DATE          datetime comment 'ʧЧ����',
   OP_ID                numeric(12,0) comment '������',
   ORG_ID               numeric(12,0) comment '��֯',
   CREATE_DATE          datetime comment '����ʱ��',
   REMARKS              varchar(255) comment '��ע',
   primary key (RECORD_ID),
   key AK_KEY_2_AI_MON_L (INFO_CODE)
);

alter table AI_MON_L_RECORD comment '��ؽ����(�ñ�ֲ㡢����)';

/*==============================================================*/
/* Table: AI_MON_MASTER_SLAVE_REL                               */
/*==============================================================*/
create table AI_MON_MASTER_SLAVE_REL
(
   RELATION_ID          numeric(12,0) not null comment '��ϵ��ID',
   MASTER_HOST_ID       numeric(12,0) not null comment '������ID',
   SLAVE_HOST_ID        numeric(12,0) not null comment '������ID',
   STATE                char(1) comment '״̬:U-����,E-ɾ��',
   CREATE_DATE          datetime comment '����ʱ��',
   REMARK               varchar(255) comment '��ע'
);

alter table AI_MON_MASTER_SLAVE_REL comment '����������ϵ��';

/*==============================================================*/
/* Table: AI_MON_NODE                                           */
/*==============================================================*/
create table AI_MON_NODE
(
   NODE_ID              numeric(12,0) not null comment '�ڵ��ʶ',
   HOST_ID              numeric(12,0) not null comment '�ڵ����������ʶ',
   NODE_CODE            varchar(40) comment '�߼���',
   NODE_NAME            varchar(100) comment '�ڵ���',
   STATE                char(1) comment '״̬:U-����,E-ɾ��',
   CREATE_DATE          datetime comment '����ʱ��',
   REMARK               varchar(255) comment '��ע',
   IS_MONITOR_NODE      char(1),
   primary key (NODE_ID)
);

alter table AI_MON_NODE comment '�ڵ㶨���';

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
   PARAM_ID             numeric(12,0) not null comment '������ʶ',
   REGISTE_TYPE         numeric(2,0) comment '��������;1�����̣�2����3������',
   REGISTE_ID           numeric(12,0) comment '������ʶ',
   PARAM_CODE           varchar(40) comment '������',
   PARAM_DESC           varchar(255) comment '��������',
   IS_MUST              char(1) comment '�Ƿ����,1����;0����',
   DATA_TYPE            varchar(20) comment '��������',
   SORT_ID              numeric(2,0) comment '˳��',
   STATE                char(1) comment '״̬',
   REMARKS              varchar(255) comment '��ע',
   primary key (PARAM_ID)
);

alter table AI_MON_PARAM_DEFINE comment '���������';

/*==============================================================*/
/* Table: AI_MON_PARAM_VALUES                                   */
/*==============================================================*/
create table AI_MON_PARAM_VALUES
(
   V_ID                 numeric(12,0) not null comment '��ʶ',
   REGISTE_TYPE         numeric(2,0) comment '��������;10������20����壬30������',
   REGISTE_ID           numeric(12,0) comment '������ʶ',
   PARAM_ID             numeric(12,0) comment '������ʶ',
   PARAM_CODE           varchar(40) comment '������',
   PARAM_VALUE          varchar(255) comment '����ֵ',
   SORT_ID              numeric(2,0) comment '˳��',
   PARAM_DESC           varchar(255) comment '����',
   REMARKS              varchar(255) comment '��ע',
   primary key (V_ID),
   key AK_AI_MON_PV (REGISTE_TYPE, REGISTE_ID, PARAM_ID)
);

alter table AI_MON_PARAM_VALUES comment '����ֵ����';

/*==============================================================*/
/* Table: AI_MON_PHYSIC_HOST                                    */
/*==============================================================*/
create table AI_MON_PHYSIC_HOST
(
   HOST_ID              numeric(12,0) not null comment '��ʶ',
   HOST_CODE            varchar(40) comment '����',
   HOST_NAME            varchar(40) comment '����',
   HOST_DESC            varchar(255) comment '����',
   HOST_IP              varchar(100) comment 'IP',
   STATE                char(1) comment '״̬:U-����,E-ɾ��',
   CREATE_DATE          datetime comment '����ʱ��',
   REMARKS              varchar(255) comment '��ע',
   primary key (HOST_ID)
);

alter table AI_MON_PHYSIC_HOST comment '�������������';

/*==============================================================*/
/* Table: AI_MON_P_EXEC                                         */
/*==============================================================*/
create table AI_MON_P_EXEC
(
   EXEC_ID              numeric(12,0) not null comment '��ʶ',
   NAME                 varchar(255) comment '����',
   E_TYPE               varchar(255) comment 'SHELL��shell�ű�����SSH��¼����������ϣ�������������������ʱSHELL�ű���ִ�У�ִ����ɺ�ɾ��
            JAVACOMMAND�����ֱ�������ʽִ�к��ȡ��ؽ��',
   EXPR                 varchar(4000) comment '���ʽ',
   STATE                char(1) comment '״̬',
   REMARKS              varchar(255) comment '��ע',
   primary key (EXEC_ID)
);

alter table AI_MON_P_EXEC comment '���̼�����ñ�';

/*==============================================================*/
/* Table: AI_MON_P_IMGDATA_RESOLVE                              */
/*==============================================================*/
create table AI_MON_P_IMGDATA_RESOLVE
(
   RESOLVE_ID           numeric(12,0) not null comment '��ʶ',
   NAME                 varchar(255) comment '����',
   PARENT_ID            numeric(12,0) comment '�ϼ���ʶ',
   TRANSFORM_CLASS      varchar(255) comment '������',
   SHOW_NAME_POS        numeric(3,0) comment '����ȡֵλ��',
   SHOW_VALUE_POS       numeric(3,0) comment 'ֵȡֵλ��',
   SORT_BY              varchar(255) comment '����',
   SHOW_TYPE            varchar(255) comment 'չ������:AREACHART,����ͼ;BARCHART,������״ͼ;COLUMNCHART,��״ͼ;LINECHART,��ͼ;PIECHART,��ͼ;3DCOLUMNCHART;3DPIECHART;3DBARCHART;RINGCHART,��״ͼ;3DRINGCHART;SCALER,�Ǳ���',
   STATE                char(1) comment '״̬',
   REMARKS              varchar(255) comment '��ע',
   primary key (RESOLVE_ID)
);

alter table AI_MON_P_IMGDATA_RESOLVE comment 'չʾ���ݽ�������';

/*==============================================================*/
/* Table: AI_MON_P_INFO                                         */
/*==============================================================*/
create table AI_MON_P_INFO
(
   INFO_ID              numeric(12,0) not null comment '�����ʶ',
   INFO_CODE            varchar(40),
   NAME                 varchar(255) comment '������',
   HOST_ID              numeric(12,0) comment '������ʶ',
   TASK_METHOD          char(1) comment '��������:F,Ϊ�̶�ʱ��;C,Ϊcron����ִ��;I,Ϊ����ִ��;P:���ִ��',
   TYPE_ID              numeric(12,0) comment '������ñ�ʶ(����,������)',
   THRESHOLD_ID         numeric(12,0) comment '��ط�ֵ��ʶ',
   RESOLVE_ID           numeric(12,0) comment '���������ʶ',
   TIME_ID              numeric(12,0) comment 'ʱ������',
   SPLIT_RULE_ID        numeric(12,0) comment '�ֲ�����ʶ',
   FILTER_ID            numeric(12,0),
   M_TYPE               varchar(255) comment '�����Ϣ����,EXEC�����̷�ʽ;TABLE����ʽ',
   LAYER                varchar(20),
   GROUP_ID             numeric(12,0) comment '����ҵ�������ٷ���',
   STATE                char(1) comment '״̬U:������E:ע��',
   REMARKS              varchar(255),
   primary key (INFO_ID),
   key AK_KEY_AI_MON_P (INFO_CODE)
);

alter table AI_MON_P_INFO comment '�������';

/*==============================================================*/
/* Table: AI_MON_P_INFO_FILTER                                  */
/*==============================================================*/
create table AI_MON_P_INFO_FILTER
(
   FILTER_ID            numeric(12,0) not null comment '��ʶ',
   FILTER_NAME          varchar(255) comment '������',
   FILTER_DESC          varchar(255) comment '��������',
   EXPR1                varchar(4000) comment '������ʽ1',
   EXPR2                varchar(4000) comment '������ʽ2',
   EXPR3                varchar(4000) comment '������ʽ3',
   EXPR4                varchar(4000) comment '������ʽ4',
   EXPR5                varchar(4000) comment '������ʽ5',
   STATE                char(1) comment '״̬',
   REMARKS              varchar(255) comment '��ע',
   primary key (FILTER_ID)
);

alter table AI_MON_P_INFO_FILTER comment '������˹������';

/*==============================================================*/
/* Table: AI_MON_P_INFO_GROUP                                   */
/*==============================================================*/
create table AI_MON_P_INFO_GROUP
(
   GROUP_ID             numeric(12,0) not null comment '��ʶ',
   GROUP_CODE           varchar(40) comment '����',
   GROUP_NAME           varchar(40) comment '����',
   GROUP_DESC           varchar(255) comment '����',
   PARENT_ID            numeric(12,0),
   SORT_ID              numeric(3,0) comment 'λ��',
   LAYER                varchar(40) comment '��',
   GROUP_STYLE          varchar(200),
   STATE                char(1) comment '״̬',
   REMARK               varchar(255) comment '��ע',
   primary key (GROUP_ID),
   key AK_KEY_2_AI_MON_P (GROUP_CODE)
);

alter table AI_MON_P_INFO_GROUP comment '��������';

/*==============================================================*/
/* Table: AI_MON_P_TABLE                                        */
/*==============================================================*/
create table AI_MON_P_TABLE
(
   TABLE_ID             numeric(12,0) not null comment '��ʶ',
   TABLE_NAME           varchar(255),
   TABLE_SQL            varchar(4000) comment 'SQL',
   DB_ACCT_CODE         varchar(255) comment '����Դ',
   DB_URL_NAME          varchar(255) comment '���ݿ�ڵ�URL',
   STATE                char(1) comment '״̬',
   REMARKS              varchar(255) comment '��ע',
   primary key (TABLE_ID)
);

alter table AI_MON_P_TABLE comment '�����ݼ�����ñ�';

/*==============================================================*/
/* Table: AI_MON_P_THRESHOLD                                    */
/*==============================================================*/
create table AI_MON_P_THRESHOLD
(
   THRESHOLD_ID         numeric(12,0) not null comment '��ʶ',
   THRESHOLD_NAME       varchar(255) comment '����',
   EXPR1                varchar(4000) comment '���ʽ1',
   EXPR2                varchar(4000) comment '���ʽ2',
   EXPR3                varchar(4000) comment '���ʽ3',
   EXPR4                varchar(4000) comment '���ʽ4',
   EXPR5                varchar(4000) comment '���ʽ5',
   EXPIRY_DAYS          numeric(3,0) comment '��Ч����',
   STATE                char(1) comment '״̬',
   REMARKS              varchar(255) comment '��ע',
   primary key (THRESHOLD_ID)
);

alter table AI_MON_P_THRESHOLD comment '���̷�ֵ���ñ�';

/*==============================================================*/
/* Table: AI_MON_P_TIME                                         */
/*==============================================================*/
create table AI_MON_P_TIME
(
   TIME_ID              numeric(12,0) not null comment '��ʶ',
   T_TYPE               varchar(255) comment '����',
   EXPR                 varchar(255) comment '���ʽ',
   STATE                char(1) comment '״̬',
   REMARKS              varchar(255) comment '��ע',
   primary key (TIME_ID)
);

alter table AI_MON_P_TIME comment '���ʱ�������';

/*==============================================================*/
/* Table: AI_MON_SERVER                                         */
/*==============================================================*/
create table AI_MON_SERVER
(
   SERVER_ID            numeric(12,0) not null comment '��ʶ',
   SERVER_CODE          varchar(40) not null comment 'Ӧ�ô���,��ֵ����Ӧ�ö�JVM��������appframe.client.app.name��appframe.server.name��ֵһ��',
   NODE_ID              numeric(12,0) not null comment '�ڵ�ID',
   SERVER_NAME          varchar(255) comment 'Ӧ������',
   LOCATOR_TYPE         varchar(40) comment '��λ���ͣ�remote ��jmx��',
   LOCATOR              varchar(255) comment '��λ����',
   SERVER_IP            varchar(40) comment 'Ӧ��IP',
   SERVER_PORT          varchar(40) comment 'Ӧ�ö˿�',
   SVERSION             varchar(40) comment '�汾',
   CHECK_URL            varchar(255) comment '�汾URL',
   MIDWARE_TYPE         varchar(40) comment '�м�����ͣ�WAS��BEA��',
   TEMP_TYPE            varchar(20) comment 'ģ�����ͣ�APP��WEB��OTHER��EXEC��',
   START_CMD_ID         numeric(12,0) comment '��������',
   STOP_CMD_ID          numeric(12,0) comment 'ֹͣ����',
   STATE                char(1) comment '״̬:U-����,E-ɾ��',
   CREATE_DATE          datetime comment '����ʱ��',
   REMARK               varchar(255) comment '��ע',
   BUSINESS_TYPE        varchar(255),
   primary key (SERVER_ID)
);

alter table AI_MON_SERVER comment 'Ӧ�ö����';

/*==============================================================*/
/* Table: AI_MON_SET                                            */
/*==============================================================*/
create table AI_MON_SET
(
   SET_ID               numeric(12,0) not null comment '��ʶ',
   SET_CODE             varchar(40) comment '��ʶ��',
   HOST_ID              numeric(12,0) comment '������ʶ',
   SERVER_ID            numeric(12,0) comment 'Ӧ�ñ�ʶ',
   APP_NAME             varchar(255) comment 'Ӧ����',
   SET_VCODE            varchar(40) comment '������',
   SET_VALUE            varchar(255) comment 'ֵ',
   SET_DESC             varchar(255) comment '����',
   CREATE_DATE          datetime comment '����ʱ��',
   OPERATER             varchar(40) comment '������',
   STATE                char(1) comment '״̬',
   REMARKS              varchar(255) comment '��ע',
   primary key (SET_ID)
);

alter table AI_MON_SET comment '���ñ�';

/*==============================================================*/
/* Table: AI_MON_STATIC_DATA                                    */
/*==============================================================*/
create table AI_MON_STATIC_DATA
(
   CODE_TYPE            varchar(255) comment '����',
   CODE_VALUE           varchar(255) comment 'ֵ',
   CODE_NAME            varchar(255) comment '����',
   CODE_DESC            varchar(255) comment '����',
   CODE_TYPE_ALIAS      varchar(255) comment '����',
   SORT_ID              numeric(3,0) comment '����',
   STATE                char(1) comment '״̬(U:����,E:ɾ��)',
   EXTERN_CODE_TYPE     varchar(255) comment '��չ����'
);

alter table AI_MON_STATIC_DATA comment '��̬���ݱ�';

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
   VERIFY_TYPE          varchar(50) not null comment 'У������',
   RULE                 varchar(1000) not null comment 'У�����',
   primary key (VERIFY_TYPE)
);

alter table AI_MON_VERIFY comment 'У����Ϣ��';

/*==============================================================*/
/* Table: AI_MON_W_DTL                                          */
/*==============================================================*/
create table AI_MON_W_DTL
(
   DTL_ID               numeric(12,0) not null comment '��ʶ',
   INFO_ID              numeric(12,0) comment '�����ʶ',
   PERSON_ID            numeric(12,0) comment '��Ա��ʶ',
   WARN_LEVEL           varchar(3) comment '���漶��',
   STATE                char(1) comment '״̬',
   REMARKS              varchar(255) comment '��ע',
   primary key (DTL_ID)
);

alter table AI_MON_W_DTL comment '�����Ϣ������Ա�������ñ�';

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
   PERSON_ID            numeric(12,0) not null comment '��ʶ',
   NAME                 varchar(255) comment '����',
   PHONENUM             varchar(255) comment '����',
   REGION_ID            numeric(12,0) comment '��֯��ʶ',
   REGION               varchar(255) comment '��֯',
   STATE                char(1) comment '״̬',
   REMARKS              varchar(255) comment '��ע',
   primary key (PERSON_ID)
);

alter table AI_MON_W_PERSON comment '�澯���ŷ���Ա����';

/*==============================================================*/
/* Table: AI_MON_W_SMS                                          */
/*==============================================================*/
create table AI_MON_W_SMS
(
   SMS_ID               numeric(12,0) not null comment '��ʶ',
   INFO_ID              numeric(12,0) comment '�����ʶ',
   RECORD_ID            numeric(12,0) comment '��¼��ʶ',
   TRIGGER_ID           numeric(12,0) comment '�����ʶ',
   PHONE_NUM            varchar(1000) comment '�ֻ���',
   SMS_CONTENT          varchar(4000) comment '��������',
   WARN_LEVEL           varchar(255) comment '���漶��',
   CREATE_DATE          datetime comment '����ʱ��',
   SEND_DATE            datetime comment '����ʱ��,��¼���һ�η���ʱ��',
   SEND_COUNT           numeric(3,0),
   STATE                char(1) comment '����״̬,E:�����쳣,�ѷ��͵ļ�¼��ɾ��',
   EXCEPTIONS           varchar(4000) comment '�쳣��Ϣ,�������쳣ʱ�����쳣��Ϣ',
   REMARKS              varchar(255) comment '��ע',
   primary key (SMS_ID)
);

alter table AI_MON_W_SMS comment '���ŷ��ͱ�';

/*==============================================================*/
/* Table: AI_SCHED_TASK_INTER                                   */
/*==============================================================*/
create table AI_SCHED_TASK_INTER
(
   INTER_ID             numeric(12,0) not null comment '���к�sequence',
   TASK_CODE            varchar(255) not null comment '�������',
   TASK_DESC            varchar(255) comment '��������',
   TASK_ITEM_ID         varchar(255) not null comment '�������-1 ָ���������������',
   OP_ID                varchar(255) not null comment '����Ա',
   ACTION               varchar(255) not null comment '����ָ�start��������stop��ֹͣ��',
   TASK_PARAM           varchar(4000) comment '����������������ʽ:key=@@key=value',
   CREATE_DATE          datetime not null comment '���񴴽�ʱ��',
   STATE_DATE           datetime comment '�������״̬',
   STATE                varchar(2) not null comment 'C��δִ�С�R��ִ���С�F��ִ�гɹ���E:ִ���쳣',
   TASK_JOB_ID          varchar(255) comment '����ִ����ҵ��ˮ��',
   EXEC_RESULT          varchar(2000) comment '������',
   REMARKS              varchar(255) comment '��ע',
   primary key (INTER_ID)
);

alter table AI_SCHED_TASK_INTER comment '���ȹ�����������ִ������ӿڱ�';

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
   DEPLOY_STRATEGY_HISTORY_ID numeric(12,0) not null comment '���������ʷid',
   HISTORY_CREATE_TIME  datetime,
   DEPLOY_STRATEGY_ID   numeric(12,0) not null comment '�������id',
   DEPLOY_STRATEGY_NAME varchar(128) comment '���������',
   CLIENT_HOME_PATH     varchar(256) comment '��Ŀ¼',
   CLIENT_BIN_PATH      varchar(256),
   CLIENT_SBIN_PATH     varchar(256),
   SERVER_PACKAGE_PATH  varchar(256) comment '��������·��',
   HISTORY_PACKAGE_NUM  numeric(1,0) comment '�����汾��',
   FTP_HOST_IP          varchar(128) comment 'ftp����ip',
   FTP_HOST_PORT        numeric(5,0) comment 'ftp�����˿�',
   FTP_PROTOCOL         char(1) comment 'ftpЭ��',
   FTP_PACKAGE_PATH     varchar(512) comment 'ftp��·��',
   FTP_USER_NAME        varchar(256) comment 'ftp�û���',
   FTP_PASSWORD         varchar(256) comment 'ftp����',
   INSTALL_RULE         varchar(1024) comment '��װ����',
   TEMPLATE_ID          numeric(12,0) comment 'ģ��id',
   STOP_TEMPLATE_ID     numeric(12,0) comment 'ֹͣģ��id',
   CREATE_TIME          datetime comment '����ʱ��',
   MODIFY_TIME          datetime comment '�޸�ʱ��',
   OPERATOR_ID          numeric(12,0) comment '����Ա',
   REMARKS              varchar(512) comment '��ע',
   CLIENT_LOG_PATH      varchar(256),
   primary key (DEPLOY_STRATEGY_HISTORY_ID)
);

alter table H_AI_DEPLOY_STRATEGY comment '���������ʷ��';

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

