package com.asiainfo.batchwriteoff.util;


/**
 * <p> @Description </p>
 * <p> @company Asiainfo-linkage (NJ) </p>
 * <p> @author 闫瑞国 </p>
 * <p> @Email yanrg@asiainfo-linkage.com </p>
 * <p> @createDate Dec 9, 2010 3:48:08 PM </p>
 * <p> @modifyDate </p>
 * <p> @version 1.0 </p>
 */
public interface AmsConst {

    //地区
    public final static String REGION_ID = "RegionId";
    /**
     * 帐管静态常量配置分模块
     * 资金管理相关
     *
     * @author yanrg
     */
    interface BALANCE {

        /**
         * 1=有效期变化、2=资金变化、3=信用等级变化、4=预存抵扣、5=销帐
         */
        public static final int UP_NOTIFY_UP_FLAG_1 = 1;
        public static final int UP_NOTIFY_UP_FLAG_2 = 2;
        public static final int UP_NOTIFY_UP_FLAG_3 = 3;
        public static final int UP_NOTIFY_UP_FLAG_4 = 4;
        public static final int UP_NOTIFY_UP_FLAG_5 = 5;
        public static final int UP_NOTIFY_UP_FLAG_7 = 7;


        /**
         * 1=单笔、2=批量、3=预存抵扣
         */
        public static final int UP_NOTIFY_BUSI_TYPE_1 = 1;
        public static final int UP_NOTIFY_BUSI_TYPE_2 = 2;
        public static final int UP_NOTIFY_BUSI_TYPE_3 = 3;


        /**
         * 2001: 普通帐本
         * 2101: 馈赠金帐本
         * 2401: 历史欠费
         * 1001：帐目级专款
         * 1002：用户级专款
         */
        public static final String UP_NOTIFY_UP_TYPE_COMMON_PRINCIPAL_BAL = "2001";    // 普通帐本
        public static final String UP_NOTIFY_UP_TYPE_COMMON_PRESENT_BAL = "2101";        // 馈赠金帐本
        public static final String UP_NOTIFY_UP_TYPE_OWE_AMOUNT = "2401";                // 历史欠费
        public static final String UP_NOTIFY_UP_TYPE_SPE_PAY_ACCT_ITEM = "1001";        // 帐目级专款
        public static final String UP_NOTIFY_UP_TYPE_SPE_PAY_USER = "1002";            // 用户级专款


        /**
         * 账本类型定义中 KIND 字段 表明本金，馈赠金
         */
        public static final String BAL_TYPE_KIND_PRINCIPAL = "1";// 本金
        public static final String BAL_TYPE_KIND_PRESENT = "0";// 馈赠金


        /**
         * 缴费工单表中 AM_PS_PAYMENT 中字段 PROC_PARAM 中定义参数时，KEY 值
         */
        public static final String PROC_PARAM_HIS_OWE_AMOUNT = "HIS_OWE_AMOUNT";//历史欠费总额
        public static final String PROC_PARAM_SITH_PRINCIPAL_BAL = "SITH_PRINCIPAL_BAL";//缴费后本金额总额
        public static final String PROC_PARAM_SITH_PRESENT_BAL = "SITH_PRESENT_BAL";//缴费后馈赠金额总额
        public static final String PROC_PARAM_IS_HAS_SPEBAL = "IS_HAS_SPEBAL";//是否包含专款
        public static final String PROC_PARAM_SITH_BALANCE = "SITH_BALANCE";//缴费后余额
        public static final String PROC_PARAM_NEED_COUNT = "NEED_COUNT";//是否需要计算有效期
        public static final String PROC_PARAM_BALANCE_EXPDATE = "BALANCE_EXPDATE";//有效期
        public static final String PROC_PARAM_PROD_CATALOG_ID = "PROD_CATALOG_ID";//用户规格 1：GSM规格
        public static final String PROC_PARAM_USER_OFFER_ID = "USER_OFFER_ID";//用户主套餐
        public static final String PROC_PARAM_AGENT_BILL_ID = "AGENT_BILL_ID";//代理商号码
        public static final String PROC_PARAM_AGENT_REGION_ID = "AGENT_REGION_ID";//代理商归属地市
        public static final String PROC_PARAM_AGENT_COUNTY_CODE = "AGENT_COUNTY_CODE";//代理商县区标识
        public static final String PROC_PARAM_IS_AIR_PAY = "IS_AIR_PAY";//代理商县区标识
        public static final String PROC_PARAM_IS_BANK_PAY = "IS_BANK_PAY";//代理商县区标识
        public static final String PROC_PARAM_IS_INVOICE = "IS_INVOICE";//代理商县区标识
        public static final String PROC_PARAM_NOTIFY_DONE = "NOTIFY_DONE";//上发是否已经处理过
        public static final String PROC_PARAM_VALID_DONE = "VALID_DONE";//计算有效期是否已经处理过
        public static final String PROC_PARAM_IS_PRE_PAY = "IS_PRE_PAY";//判断是否是预缴充值
        public static final String PROC_REAL_END_LATE_FEE_DATE = "REAL_END_LATE_FEE_DATE";//真正结束计算滞纳金的时间
        public static final String PROC_PARAM_UNPAY_FEE_AFTER_ENTRUST = "ENTRUST_FEE"; //非联网托收总额标识 added by weism on 2013/5/21
        public static final String PROC_PARAM_NEW_ACCT_ID = "NEW_ACCT_ID"; //统一支付户主账户编号 added by weism on 2013/5/21
        public static final String PROC_PARAM_OLD_ACCT_ID = "OLD_ACCT_ID"; //统一支付老账户编号 added by weism on 2013/5/21
        public static final String PROC_PARAM_ENTRUST_BATCHNUM = "ENTRUST_BATCHNUM"; //非联网托收批次号 add by zhaimm 充值流程优化
        public static final String PROC_PARAM_ENTRUST_BATCHNUM_L = "ENTRUST_BATCHNUM_L"; //联网托收批次号 add by zhaimm
        public static final String PROC_PARAM_ENTRUST_CONTRACT_NO = "CONTRACT_NO"; //联网托收协议号 add by zhaimm
        public static final String PROC_PARAM_ENTRUST_MONTH = "ENTRUST_MONTH"; //联网托收月份（填写托收明细表AM_ENTRUST_BUSI_REC_DTL中BILLING_CYCLE_ID字段的值） add by zhaimm


        public static final String PAY_PARA_IS_PRE_PAY_Y = "Y";// 预缴充值
        public static final String PAY_PARA_IS_PRE_PAY_N = "N";// 非预缴充值

        public static final String BALANCE_AVAILDAY_STATE_Y = "Y";//计算有效期
        public static final String BALANCE_AVAILDAY_STATE_N = "N";//不计算有效期

        public static final String AM_PS_PAYMENT_IS_SMS_Y = "Y";// 充值完后 发送短信
        public static final String AM_PS_PAYMENT_IS_SMS_N = "N";// 充值完后 不发送短信    只有当这个字段不为空并且为 N 时，才不发送短信，其他情况都发送短信

        public static final String AM_PS_PAYMENT_IS_AVAILDAY_Y = "Y";// 充值完后 计算有效期
        public static final String AM_PS_PAYMENT_IS_AVAILDAY_N = "N";// 充值完后 不计算有效期  只有当这个字段不为空并且为 N 时，才不计算有效期，其他情况都计算有效期

        /**
         *
         */
        public static final String BALANCE_VALID_MODE_ADD = "ADD";
        public static final String BALANCE_VALID_MODE_RED = "RED";

        public static final String BUSI_BALANCE_ITEM_TYPE_1 = "1"; // 1 帐本科目
        public static final String BUSI_BALANCE_ITEM_TYPE_2 = "2";//  2 帐单科目

        public static final String PAYMENT_TYPE_COMMON = "1"; //普通缴费
        public static final String PAYMENT_TYPE_SETTLE_USER = "2"; //结清账户+用户
        public static final String PAYMENT_TYPE_USER_SPE_BALANCE = "3"; //缴用户专款


        //2 整票冲正
        //C 呆帐用户退费
        public static final String AM_BALANCE_PAYOUT_TYPE_1 = "1";  //退预存
        public static final String AM_BALANCE_PAYOUT_TYPE_2 = "2";  //返销
        public static final String AM_BALANCE_PAYOUT_TYPE_E = "E";    //E 实时销账
        public static final String AM_BALANCE_PAYOUT_TYPE_Y = "Y";  //Y 被返销
        public static final String AM_BALANCE_PAYOUT_TYPE_H = "H";    //H 坏帐用户退费
        public static final String AM_BALANCE_PAYOUT_TYPE_X = "X";    //X 批量销帐
        //Y 被返销

        public static final String AM_BALANCE_SOURCE_TYPE_F = "F";  //返销


        /**
         * 允许体现标识 1：可退 0：不可退
         */
        public static final String BALANCE_TYPE_ALLOW_CASH_ABLE = "1";
        public static final String BALANCE_TYPE_ALLOW_CASH_UNABLE = "0";

        /**
         * 允许销账标识 1：可销帐 0：不可销帐
         */
        public static final String BALANCE_TYPE_ALLOW_WRITEOFF_ABLE = "1";
        public static final String BALANCE_TYPE_ALLOW_WRITEOFF_UNABLE = "0";

        /**
         * SPE_PAY_TYPE
         * 0:非专款，1:帐目级专款，2:用户级专款
         */
        public static final String SPE_PAY_TYPE_COMMON = "0";//非专款
        public static final String SPE_PAY_TYPE_ACCT_ITEM = "1";//账目级专款
        public static final String SPE_PAY_TYPE_USER = "2";//用户级专款

        /**
         * AM_PAYMENT.PAYED_TYPE
         * 销帐方式 0: 按帐户销帐；1: 按用户销帐；2:按用户结清;（按用户进行信控时需要用到，可以使用用户基本帐户的资金去核销合户帐户的欠费）
         * 3:按帐期销帐（会导致欠费不连续，暂不支持） 默认值为0
         */
        public static final String PAYED_TYPE_ACCT = "0";
        public static final String PAYED_TYPE_ACCT_USER = "1";//按账户+用户方式销账

        /**
         * 工单处理状态，0 正常；F 错单；
         */
        public static final String PROCESS_STATUS_COMMON = "0";
        public static final String PROCESS_STATUS_FAIL = "F";


        public static final long LATE_FEE_TARIFF_RATE_RADIX = 1000000;

        /**
         * 滞纳金减免 减免方式
         */
        public static final String LATE_FEE_DERATE_TYPE_AMOUNT = "AMOUNT";//按金额减免
        public static final String LATE_FEE_DERATE_TYPE_RATE = "RATE";//按比例减免

        public static final String LATE_FEE_USED_STATE_USEFUL = "1";//滞纳金减免记录有效
        public static final String LATE_FEE_USED_STATE_USED = "2";//已使用
        public static final String LATE_FEE_USED_STATE_USELESS = "3";//无效

        public static final long DEFAULT_OPT_SEQ = 1;        //默认OPT_SEQ
        public static final long DEFAULT_OPT_SEQ_NULL = -1;        //OPT_SEQ无效时的赋值


        public static final String OWE_QRY_MDB_Y = "Y";
        public static final String OWE_QRY_MDB_N = "N";


        public static final int INDEP_FEE_OPERATION_TYPE_ONE_GET = 1;    // 1: 一次性费用收取
        public static final int INDEP_FEE_OPERATION_TYPE_ONE_BACK = 2;    // 2: 一次性费用收取返销
        public static final int INDEP_FEE_OPERATION_TYPE_GET = 6;        // 6:押金收取
        public static final int INDEP_FEE_OPERATION_TYPE_CANCEL = 7;    // 7:押金收取返销
        public static final int INDEP_FEE_OPERATION_TYPE_BACK = 8;        // 8:押金退还

        public static final int RECEIVE_FEE_TYPE_1 = 1;        //一次性费用: 1 一次性费用
        public static final int RECEIVE_FEE_TYPE_3 = 3;        //一次性费用: 3 预缴
        public static final int INDEP_FEE_TYPE_1 = 1;        //押金类型: 1 一次性费用
        public static final int INDEP_FEE_TYPE_2 = 2;        //押金类型: 2 普通个人押金
        public static final int INDEP_FEE_TYPE_3 = 3;        //押金类型: 3 预缴
        public static final int INDEP_FEE_TYPE_4 = 4;        //押金类型: 4 固网产品押金
        public static final int INDEP_FEE_TYPE_5 = 5;        //押金类型: 5 特殊产品押金
        public static final int INDEP_FEE_TYPE_6 = 6;        //押金类型: 6 固网客户押金
        public static final int INDEP_FEE_TYPE_7 = 7;        //押金类型: 7 积分扣减
        public static final int INDEP_FEE_TYPE_8 = 8;        //押金类型: 8 现金充值 2001
        public static final int INDEP_FEE_TYPE_9 = 9;        //押金类型: 9 现金充值 2101
        public static final int INDEP_FEE_TYPE_10 = 10;        //押金类型: 10 租机押金

        public static final int INDEP_FEE_DEFAULT_FREEZE_MONTHS = 24;

        public static final String INDEP_FEE_BALANCE_TYPE_CASH = "1";        //押金方式: 1 现金
        public static final String INDEP_FEE_BALANCE_TYPE_BANKFREEZE = "2";        //押金方式: 2 银行资金冻结

        public static final String BALANCE_TRANS_TYPE_ORDER = "ORDER";         //预约转移
        public static final String BALANCE_TRANS_TYPE_IMMEDIATE = "IMMEDIATE"; //立即转移

        public static final String BALANCE_QUERY_TYPE_WRITEOFF = "1";      //查询虚拟销帐后的帐本
        public static final String BALANCE_QUERY_TYPE_PLAIN = "0";         //查询未经虚拟销帐的帐本

        public static final String BALANCE_TRANS_STATE_U = "U"; //预约转移正常未转移
        public static final String BALANCE_TRANS_STATE_C = "C"; //预约转移取消
        public static final String BALANCE_TRANS_STATE_F = "F"; //预约转移成功
        public static final String BALANCE_TRANS_STATE_E = "E"; //预约转移失败

        public static final int BALANCE_TRANS_MODE_DEFAULT_ALL = -1; //资金转移默认转移帐本金额

        //报损帐单是否结清状态
        public static final int LOSS_BILL_PAY_STATUS_UNPAID = 0; //0  未结清  1  结清
        public static final int LOSS_BILL_PAY_STATUS_PAID = 1;

        public static final String LOSS_BILL_OPERATE_TYPE_Q = "Q"; //Q 坏帐缴费 B 呆账缴费
        public static final String LOSS_BILL_OPERATE_TYPE_R = "R"; //R 坏帐冲正 C 呆账冲正

        public static final long BALANCE_TYPE_COMMON_PRESENT_BAL = 2101; //馈赠金帐本
        public static final long BALANCE_TYPE_COMMON_PRINCIPAL_BAL = 2001; //本金帐本
        public static final long BALANCE_TYPE_PAY_BAL = 2201; //支付平台账本
        //public static final long BALANCE_TYPE_AIR_PAY_BAL = 2301; //空中充值账本
        public static final long BALANCE_TYPE_HIS_OWE = 2401; //历史欠费
        public static final long BALANCE_TYPE_ID_COMMON = BALANCE_TYPE_COMMON_PRINCIPAL_BAL; //通用账本（报损缴费用到此值，报损只允许现金缴费）

        //资金转移状态
        public static final String BALANCE_MOVE_STATE_U = "U"; //正常
        public static final String BALANCE_MOVE_STATE_C = "C"; //取消
        public static final String BALANCE_MOVE_STATE_F = "F"; //转移成功
        public static final String BALANCE_MOVE_STATE_E = "E"; //转移失败

        public static final String AM_BALANCE_TRANS_REPORT_FLAG_Y = "Y"; //记录日报
        public static final String AM_BALANCE_TRANS_REPORT_FLAG_N = "N"; //不记录日报

        //调用boss socket销账接口，销账类型  add by zhaimm 充值流程优化
        public static final short WRITE_OFF_TYPE_1 = 1; //实际销帐
        public static final short WRITE_OFF_TYPE_2 = 2; //模拟销帐
        public static final short WRITE_OFF_TYPE_3 = 3; //模拟销帐无专款直接加减模式  目前不会调用，帐管侧直接加减
    }

    interface COMMON {



        public static final String DEFAULT_REGION_ID = "571";

        public static final String STRING_DAY = "day";

        public static final String STRING_NULL = "null";

        public static final String BUSSNESS_ID_REPORT_FLAG_YES = "Y";// 标识一个业务编码是否记录报表--记录
        public static final String BUSSNESS_ID_REPORT_FLAG_NO = "N";// 标识一个业务编码是否记录报表--不记录

        public static final int REGION_CUT_OVER_FLAG = 1;//地市已割接标识

        public static final String AM_QRY_CONDITON_TYPE_USER_ID = "userId";
        public static final String AM_QRY_CONDITON_TYPE_ACCT_ID = "acctId";
        public static final String AM_QRY_CONDITON_TYPE_BANK_ACCT = "bankAcct";

        public static final String AM_PARA_SEPARATOR = ";";

        /**
         * 判断一个 business_id 对应的业务编码信息 span_flag 是否支持异地业务
         */
        public static final String BS_OPERATION_SPAN_FLAG_ABLE = "Y";    //    支持异地业务
        public static final String BS_OPERATION_SPAN_FLAG_UNABLE = "N"; // 不支持异地业务

        /**
         * 判断地市当前账期是否处于 月末出账期
         * 0 ： 正常     1：出账期
         */
        public static final String BILLING_CYCLE_CTRL_FLAG_0 = "0";
        public static final String BILLING_CYCLE_CTRL_FLAG_1 = "1";

        /**
         * 1：未来帐期 2：计费帐期 3：缴费帐期 4：历史帐期
         */
        public static final String BILLING_CYCLE_STATE_FUTURE = "1";
        public static final String BILLING_CYCLE_STATE_CUR = "2";
        public static final String BILLING_CYCLE_STATE_LAST = "3";
        public static final String BILLING_CYCLE_STATE_HIS = "4";




        public static final String CENTER_CODE_1 = "1";
        public static final String CENTER_CODE_2 = "2";
        public static final String CENTER_CODE_3 = "3";
        public static final String CENTER_CODE_4 = "4";


        public static final String BO_MAPING_ENTY_TYPE_TABLE = "table";
        public static final String BO_MAPING_ENTY_TYPE_QUERY = "query";

        /**
         * 省级工号地市标识
         */
        //public static final String REGION_ID_PROVINCE = "X";

        public static final String PS_PAYMENT_HIS_FLAG_Y = "Y";
        public static final String PS_PAYMENT_HIS_FLAG_N = "N";

        /**
         * 销账时，是否限制账目所在账期大于等于账本生效时间所在账期
         */
        public static final String EFFECTIVE_DATE_LIMIT_FLAG_Y = "Y";
        public static final String EFFECTIVE_DATE_LIMIT_FLAG_N = "N";


        public static final long BATCH_SIZE = 5000;

        /**
         * 0:作废  1：正常 2：返销 3：被返销
         * U 在用   E 逻辑删除
         */
        public static final String STATE_0 = "0";
        public static final String STATE_1 = "1";
        public static final String STATE_2 = "2";
        public static final String STATE_3 = "3";
        public static final String STATE_U = "U";
        public static final String STATE_E = "E";

        /**
         * 0：未处理；1：成功处理；2：处理异常
         */
        public static final String BATCH_OPERATE_STATE_0 = "0";
        public static final String BATCH_OPERATE_STATE_1 = "1";
        public static final String BATCH_OPERATE_STATE_2 = "2";

        public static final String PS_PAYMENT_START_PROCESS_ID = "0";
        public static final String PS_PAYMENT_PROCESS_ID_BAL_INCOME = "1";//入账本
        public static final String PS_PAYMENT_PROCESS_ID_WRITF_OFF = "2";//销账
        public static final String PS_PAYMENT_PROCESS_ID_VALID_COUNT = "3";//计算有效期
        public static final String PS_PAYMENT_PROCESS_ID_UP_NOTIFY = "4";//余额上发
        public static final String PS_PAYMENT_PROCESS_ID_SWITCH_ACCT = "5";//月控开机
        public static final String PS_PAYMENT_PROCESS_ID_BENEFIT4_PREPAY = "6";//带4号码优惠充值


        public static final String PS_PAYMENT_ACTION_1 = "1"; // 个人缴费
        public static final String PS_PAYMENT_ACTION_2 = "2"; // 个人缴费--出账期间，驻留的第2步销账工单,后续流程
        public static final String PS_PAYMENT_ACTION_4 = "4"; // 优惠充值
        public static final String PS_PAYMENT_ACTION_A = "A"; // 退费
        public static final String PS_PAYMENT_ACTION_E = "E"; // 返销

        public static final String PS_PAYMENT_ACTION_C = "C";//工单中销账方式为按账户+用户销账时，补充一条按账户销账工单
        public static final String PS_PAYMENT_ACTION_F = "F"; // 强制发起销账
        public static final String PS_PAYMENT_ACTION_V = "V";//触发有效期计算
        public static final String PS_PAYMENT_ACTION_T = "T";//异地统付账户充值，需要将充值工单从用户归属地市转移到账户归属地市
        //added by weism on 2013/05/31 -- 增加统一支付异地成员充值标记
        public static final String PS_PAYMENT_ACTION_Z = "Z";//统一支付异地成员充值，在充值入账本时，将充值工单从用户归属地市转移到账户归属地市
        public static final String PS_PAYMENT_ACTION_Y = "Y"; //异地统一支付返销 add by zhiamm 充值流程优化

		
		 
	     /*public static final String V_6_WK_PAYMENT_ACTION_2 = "2"; // 自有代理商缴费
	     public static final String V_6_WK_PAYMENT_ACTION_3 = "3"; // 社会代理商一级帐户缴费
	     public static final String V_6_WK_PAYMENT_ACTION_4 = "4"; // 社会代理商拨入
	     public static final String V_6_WK_PAYMENT_ACTION_5 = "5"; // 个人缴费
	     public static final String V_6_WK_PAYMENT_ACTION_6 = "6"; // 呆坏帐缴费
	     public static final String V_6_WK_PAYMENT_ACTION_D = "D"; // 社会代理商二级到移动客户拨出
	     public static final String V_6_WK_PAYMENT_ACTION_B = "B"; // 自有代理商退费
*/

        public static final String PAYMENT_OPERATION_TYPE_1 = "1"; // 退预存
        public static final String PAYMENT_OPERATION_TYPE_2 = "2"; // 冲正
        public static final String PAYMENT_OPERATION_TYPE_3 = "3"; // 缴费
        public static final String PAYMENT_OPERATION_TYPE_4 = "4"; // 转帐
	     
	     
	     /*public static final String V_6_PAYMENT_OPERATION_TYPE_C = "C"; // 呆账冲正
	       public static final String V_6_PAYMENT_OPERATION_TYPE_B = "B"; // 呆账缴费
	       
	       public static final String V_6_PAYMENT_OPERATION_TYPE_Q = "Q"; // 坏帐缴费
	       public static final String V_6_PAYMENT_OPERATION_TYPE_R = "R"; // 坏帐冲正
*/

        public static final String PAYMENT_CERTIFICATE_TYPE_CASH = "0";//	现金
        //public static final String PAYMENT_CERTIFICATE_TYPE_CHECK ="8" ;//	支票
        public static final String PAYMENT_CERTIFICATE_TYPE_EASYOWN_CARD = "E";        //	神州行充值卡
        public static final String PAYMENT_CERTIFICATE_TYPE_505CARD = "5";            //	505充值卡
	       
	       /*public static final String PAYMENT_CERTIFICATE_TYPE_1 ="1";//	代收
	       public static final String PAYMENT_CERTIFICATE_TYPE_2 ="2";//	代金券
	       public static final String PAYMENT_CERTIFICATE_TYPE_3 ="3";//	记帐凭证
	       public static final String PAYMENT_CERTIFICATE_TYPE_4 ="4";//	托收
	       public static final String PAYMENT_CERTIFICATE_TYPE_5 ="5";//	充值卡
	       public static final String PAYMENT_CERTIFICATE_TYPE_6 ="6";//	缴费卡
	       public static final String PAYMENT_CERTIFICATE_TYPE_7 ="7";//	邮政汇票
	       public static final String PAYMENT_CERTIFICATE_TYPE_8 ="8";//	支票
	       public static final String PAYMENT_CERTIFICATE_TYPE_9 ="9";//	银联刷卡
*/

        public static final String BATCH_TASK_METHOD_IMMEDIATE = "I";
        public static final String BATCH_TASK_METHOD_FIXED = "F";

        public static final String BATCH_EXE_TASK = "com.asiainfo.crm.common.batch.SimpleBatchTask";


        /**
         * 二卡合一充值平台 渠道标识
         */
        public static final String CHANNEL_ID_VC = "VC";
        public static final String CHANNEL_ID_505 = "505";
        public static final String CHANNEL_ID_CBOSS = "CBOSS";

        /**
         * 退费类型
         * 0 ： 所有     1：SP退费		2：非SP退费
         */
        public static final int REV_FEE_MODE_ALL = 0; //所有
        public static final int REV_FEE_MODE_SP = 1; //SP退费
        public static final int REV_FEE_MODE_GENERAL = 2; //非SP退费

        //public static final String REGION_ID_CODE_PROVINCE = "0"; //地区为全省的编号

        public static final int COMMON_JDBC_RESULTSET_FETCH_SIZE = 1000; //查询取数

        public static final int AMS_PARAM_ISMODIFY_0 = 0;//标识当前记录可修改
        public static final int AMS_PARAM_ISMODIFY_1 = 1;//标识当前记录不可修改

        public static final String INVOICE_TYPE_NULL = "0";   //请选择
        public static final String INVOICE_TYPE_MOBILE = "1"; //移动发票
        public static final String INVOICE_TYPE_PBOSS = "2"; //铁通发票

        public static final int RETURN_CODE_SUCCESS = 0;   //成功
        public static final int RETURN_CODE_ERROR = 1;   //失败

        //add by zhaimm 充值流程优化  线程数
        public static final int PROCESS_THREAD_NUM = 10;

    }

    interface BILL {
        public static final String OWE_FEE_TYPE_HIS = "HIS";//历史未结清账单
        public static final String OWE_FEE_TYPE_CUR = "CUR";//当月MDB未结清账单

        /**
         * 查询未销账账单时，根据制定的模式，是否进行归并
         */
        public static final String GROUP_MODE_NULL = "0";                //不归并
        public static final String GROUP_MODE_ACCT_BILLING = "1";        // 账户 + 账期 归并
        public static final String GROUP_MODE_ACCT_USER_BILLING = "2";  // 账户 + 用户 + 账期 归并

        //地市托收文件处理
        public static final String VIEW_FILE_TYPE_PARAM_NAME = "viewFileType";   //查看文件类型参数名
        public static final String VIEW_FILE_NAME_PARAM_NAME = "viewFileName";   //查看文件名参数名
        public static final String VIEW_FILE_FTPPATHCODE_PARAM_NAME = "viewFtpPathCode"; //查看FTP路径参数
        public static final String VIEW_FILE_REGION_PARAM_NAME = "regionId";    //地区编号
        public static final String VIEW_FILE_BANKCODE_PARAM_NAME = "bankCode";  //银行代码
        public static final String VIEW_FILE_BANKCODECARRY_PARAM_NAME = "bankCodeCarry";  //银行代码取值
        public static final String VIEW_FILE_BANKCODE_PARAM_NAME_CARRYCODE = "1";  //bankCode传银行代码
        public static final String VIEW_FILE_BANKCODE_PARAM_NAME_CARRYTYPE = "2";  //传银行类型代码
        public static final String VIEW_FILE_TYPE_FTPFILE_LOC = "FTP_FILE_LOC";  //查看文件类型_FTP文件LOC目录
        public static final String VIEW_FILE_TYPE_FTPFILE_HIS = "FTP_FILE_HIS";  //查看文件类型_FTP文件HIS目录
        public static final String VIEW_FILE_TYPE_FTPFILE_TMP = "FTP_FILE_TMP";  //查看文件类型_FTP文件TMP目录
        public static final String VIEW_FILE_TYPE_UPTMPFILE = "UPLOAD_TMP_FILE";  //查看文件类型_FTP文件

        //am_entrust_busi_rec 查询　　BANK_ID 时候的 取值
        public static final String QUERY_AM_ENTRUST_BUSI_REC_BANKCODESELF = "1";  //传银行编号按银行编号查询
        public static final String QUERY_AM_ENTRUST_BUSI_REC_BANKCODETYPE = "2";  //传银行类型按银行类型查询
        public static final String QUERY_AM_ENTRUST_BUSI_REC_BANKCODEBYTYPE = "3";  //传银行编号按银行类型查询

        public static final int FUND_ADJUST_RESULT_SUCCESS = 1; //划帐成功
        public static final int FUND_ADJUST_RESULT_FAIL = 2;    //划帐失败
        public static final int FUND_ADJUST_RESULT_FAIL_YE = 3;    //划帐因划出账户余额不足失败
        //集团资金划帐的三种划帐类型
        public static final int FUND_ADJUST_TYPE_FIX = 1;    //固定金额
        public static final int FUND_ADJUST_TYPE_BILL = 2;    //按成同帐单
        public static final int FUND_ADJUST_TYPE_MIX = 3;    //混合


        // 销帐类型
        public static final String HEAD_CHAR_ITEM_TYPE_SP = "8"; //SP销帐类型--帐目类型标识以8开头的为SP销帐类型

        public static final String GROUP_BILL_FTP_CODE = "AM_GROUP_BILL_DOWNLOAD"; // 集团帐单文件FTP路径

        public static final long RTA_ALL_SUM_ITEM_CODE = 6063161; // 获取通话次数的参数值


    }

    interface INVOICE {
        public static final long MONTH_INVOICE_TEMPLET = 1;//账单、报损发票模板编号
        public static final long MONTH_INVOICE_TYPE = 21;//账单、报损发票项类型编号
        public static final long ENTRUST_INVOICE_BUSINESS_ID = 954;//托收发票业务类型编码(给资源侧)
        public static final long INVOICE_BUSINESS_ID = 574;//月结发票业务类型编码(给资源侧)
        public static final long BILL_INVOICE_ITEM_ID = 2;//月结发票ITEM编码(给资源侧)
        public static final long PAY_INVOICE_ITEM_ID = 4;//充值发票ITEM编码(给资源侧)
        public static final long LOSS_INVOICE_ITEM_ID = 3;//报损帐单缴费发票ITEM编码(给资源侧)
        public static final long INDEP_INVOICE_ITEM_ID = 5;//押金发票ITEM编码(给资源侧)
        public static final long CUSTOM_INVOICE_ITEM_ID = 7;//个人自定发票ITEM编码(给资源侧)
        public static final long POLITE_INVOICE_ITEM_ID = 8;//政企自定义ITEM编码(给资源侧)
        public static final long ENTRUST_INVOICE_ITEM_ID = 1;//托收发票ITEM编码(给资源侧)

        public static final String BILL_INVOICE_DOC_TYPE = "2";//月结发票类型
        public static final String PAY_INVOICE_DOC_TYPE = "1";//充值发票
        public static final String LOSS_INVOICE_DOC_TYPE = "5";//报损帐单缴费发票发票
        public static final String INDEP_INVOICE_DOC_TYPE = "4";//押金发票
        public static final String RECEIVE_INVOICE_DOC_TYPE = "7";//一次性发票

        public static final long GEJIE_MONTH = 201109;//充值发票打印时，老数据不兼容，割接时间之前的按一套逻辑，以后一套
        public static final String INVOICE_ADDRESS = "INVOICE_ADDRESS";//发票地址static_data中code_type
    }

    interface STATIC_DATA {
        public static final String FUND_ADJUST_EFF_MONTH = "FUND_ADJUST_EFF_MONTH";//集团划帐自定义划帐开始月份最大月份数配置，2到？个月

        public static final String AM_PRESENT4BANK_MUTEX_OFFER = "AM_PRESENT4BANK_MUTEX_OFFER";//快乐充值3重奏，互斥套餐

        public static final String AM_BALANCE_QRY_TYPE = "AM_BALANCE_QRY_TYPE";//资金信息查询 类型
        public static final String AM_BALANCE_QRY_CONDITON_TYPE = "AM_BALANCE_QRY_CONDITON_TYPE";//资金变化明细页面，查询条件类型


        public static final String AM_RES_505CARD_STATE = "AM_RES_505CARD_STATE";//505充值卡 卡状态
        public static final String AM_RES_ECARD_STATE = "AM_RES_ECARD_STATE";//神州行充值卡 卡状态

        public static final String AM_QRY_CONDITON_TYPE = "AM_QRY_CONDITON_TYPE";//前台缴费时，查询条件类型

        //发票
        public static final String OWE_MONTH = "OWE_MONTH";//设置查询几个月前欠费
        public static final String AM_PAYMENT_TYPE = "AM_PAYMENT_TYPE";//缴费类型-前台选择
        public static final String AM_PAYMENT_CERTIFICATE_TYPE = "AM_PAYMENT_CERTIFICATE_TYPE";//前台缴费时凭证类型

        public static final String AM_SPE_PAY_TYPE = "AM_SPE_PAY_TYPE";//专款类型定义

        public static final String CODE_TYPE_CFG_TASK_METHOD = "CFG_TASK_METHOD";//标记任务执行方式
        //BANKLOG
        public static final String CODE_TYPE_BANKLOGIMPORT_HOLD_REGION = "BANKLOGIMPORT_HOLD_REGION";//前置机日志入库 CODE_VALUE 填写地区值,通过该地区值获取中心
        // --Begin----hongjf--
        //银行指令批量查询相关
        public static final String CODE_TYPE_BANK_ACCOUNT_PAY_METHOD_STR = "CM_ACCOUNT_PAY_METHOD"; // 银行帐户类型在静态数据中的分组标识
        public static final String CODE_TYPE_BANK_BUSI_DIRECT_STR = "40035"; // 交易方向
        public static final String CODE_TYPE_BANK_DONE_RESULT_STR = "40036"; // 处理结果
        public static final String CODE_TYPE_BANK_DONE_RESULT4REV_DEDUCT = "AM_BANK_BUSI_REC_DONE_RESULT4REV";//托收批扣反撤处理结果枚举值
        // 银行托收返回错误码
        public static final String BANK_COLLECTION_BACK_RESULT = "AM_BANK_COLLECTION_BACK_RESULT"; // 银行托收返回错误码
        //退费录入
        public static final String CODE_TYPE_REV_FEE_MODE_SP_STR = "REV_FEE_MODE_SP"; //SP退费类型
        public static final String CODE_TYPE_REV_FEE_MODE_NON_SP_STR = "REV_FEE_MODE_NON_SP"; //非SP退费类型

        public static final String CODE_TYPE_REV_FEE_MON_TYPE_STR = "REV_FEE_MON_TYPE"; //梦网业务类型
        public static final String CODE_TYPE_REV_FEE_REV_TYPE_STR = "REV_FEE_REV_TYPE"; //退费金额退费方式

        public static final String CODE_TYPE_REV_FEE_REASON_TYPE_STR = "REV_FEE_REASON_SP"; //退费原因类型
        public static final String CODE_TYPE_REV_FEE_REASON_TYPE_NON_STR = "REV_FEE_REASON_NON_SP"; //非SP退费原因类型

        public static final String CODE_TYPE_REV_APPLY_CHANNEL_TYPE_STR = "REV_FEE_APPLY_CHANNEL"; //退费受理渠道
        public static final String CODE_TYPE_REV_AWARD_TYPE_STR = "REV_FEE_AWARD_TYPE"; //奖励金额退费方式

        //充值记录查询
        public static final String CODE_TYPE_PAYMENT_OPERATION_TYPE_STR = "AM_PAYMENT_OPERATION_TYPE"; //操作类别
        public static final String CODE_TYPE_PAYMENT_CERTIFICATE_TYPE_STR = "AM_PAYMENT_CERTIFICATE_TYPE"; //证件类别
        public static final String CODE_TYPE_PAYMENT_STATE_TYPE_STR = "AM_PAYMENT_STATE"; //记录状态
        public static final String CODE_TYPE_PAYMENT_TYPE_STR = "AM_PAYMENT_TYPE"; //缴费方式
        public static final String CODE_TYPE_PAYMENT_PAYED_TYPE_STR = "AM_PAYMENT_PAYED_TYPE"; //销帐方式
        //帐本支出
        public static final String CODE_TYPE_BALANCE_PAYOUT_TYPE_STR = "AM_BALANCE_PAYOUT_TYPE"; //支出类型
        public static final String CODE_TYPE_BALANCE_PAYOUT_STATE_STR = "AM_BA_PAYOUT_STATE"; //支出记录状态
        //冲销记录查询
        public static final String CODE_TYPE_ACCOUNT_PAY_METHOD_STR = "CM_ACCOUNT_PAY_METHOD"; //银行账户类型（客户管理侧定义）
        //欠费号码查询
        public static final String CODE_TYPE_OWE_QUERY_USER_STATUS = "SO_USER_STATE"; //欠费用户查询code_type--用户状态
        public static final String CODE_TYPE_OWE_QUERY_USER_BRAND = "SO_BRAND_TYPE"; //欠费用户查询code_type--用户品牌
        public static final String CODE_TYPE_CM_CUST_CERT_TYPE = "CM_CUST_CERT_TYPE"; //欠费号码查询--证件类型
        //空中充值调帐记录查询
        public static final String CODE_TYPE_BANK_PARTNER_ADJUST = "BANK_PARTNER_ADJUST_TYPE"; //空中充值调帐类型
        public static final String CODE_TYPE_BANK_PARTNER_ADJUST_STATUS = "AM_BANK_PARTNER_ADJUST_STATUS"; //空中充值调帐记录状态
        //催缴日志查询
        public static final String CODE_TYPE_SMS_LOG_QUERY_EXT_URL = "EXTERNAL_URL"; //静态数据表中的code_type--外部URL
        public static final String CODE_VALUE_SMS_LOG_QUERY_EXT_URL = "SMS_LOG_URL"; //静态数据表中的code_value--外部URL华为提供的催缴日志请求地址
        //欠费业务查询
        public static final String CODE_TYPE_OWE_BUSI_QUERY_URGE_STATUS = "CM_ACCOUNT_URGE_STOP_FLAG"; //欠费业务查询结果code_type--催停状态
        public static final String CODE_TYPE_OWE_BUSI_QUERY_FINISH_STATUS = "SO_BATCH_PROCESS_STATE"; //欠费业务查询结果code_type--处理状态
        //对帐调帐结果查询
        public static final String CODE_TYPE_BANK_CHECK_ADJUST_EXCEPTION_TYPE = "37000";//对帐调帐结果类型
        //销账记录
        public static final String CODE_TYPE_BILL_STATE_TYPE_TYPE = "AM_BILL_STATE_TYPE";//销账记录状态
        // --End------hongjf--

        public static final String CODE_TYPE_CHECK_LOGSTAT_PROVINCE_REGION = "CHECK_LOGSTAT_PROVINCE_REGION";//CODE_VALUE 填写地区值,通过该地区值获取中心
        public static final String CODE_TYPE_PROVINCE_DISTRICT_ID = "AMS_PROVINCE_DISTRICT_ID"; //填写省中心对应记录的 DISTRICT_ID 值 ,CODE_VALUE 填写值
        public static final String CODE_TYPE_CHECK_LOGSTAT_ALLOW_REGIONAL = "CHECK_LOGSTAT_ALLOW_REGIONAL"; //CODE_VALUE 填写 1 表示允许单独地市对帐 非 0 表示不允许


        public static final String CODE_TYPE_REVFEE_LIMIT_YEARS = "REVFEE_LIMIT_YEARS"; //退费限制年数

        /**
         * 空中充值3大代理商账本存放在一个固定中心
         * 这里配置一个此中心的地市标识
         */
        public static final String AIR_PAY_DEFAULT_REGION_ID = "AIR_PAY_DEFAULT_REGION_ID";//
        //空中充值代理商 修改时同时修改com.asiainfo.crm.ams.airpay.bo.BOAmAirPaymentSelf中的BANK_CODE_TYPE属性的ListDataSource的参数值
        public static final String AIR_PAY_AGENT = "AIR_PAY_AGENT";
        //空中充值余额调整请求默认的处理方式。只能配置一条，取code_value
        public static final String AIR_PAY_DEFAULT_DEAL_TYPE = "AIR_PAY_DEFAULT_DEAL_TYPE";
        //空中充值余额调整时用于判断的特殊套餐,37045。 code_name是套餐标识
        public static final String AIR_PAY_ADJUST_SPECIAL_BRAND = "AIR_PAY_ADJUST_SPECIAL_BRAND";
        //空中充值余额调整每个网点每月调整申请限制次数。code_value是合作商，code_name是处理类型
        public static final String AIR_PAY_ADJUST_REQUEST_COUNT_LIMIT = "AIR_PAY_ADJUST_REQUEST_COUNT_LIMIT";
        //空中充值余额调整限制几天内有充值记录的才允许调整。只能配置一条，取code_value
        public static final String AIR_PAY_ADJUST_MAX_DAYS = "AIR_PAY_ADJUST_MAX_DAYS";
        //全省催缴时间设置，在bs_static_data配置的银行信息的code_type值
        public final static String CODE_TYPE_ACC_OWE_TIME_DEF_BANKS = "11023";

        //适用全省托收银行类型 ,适用各地市个性化的托收类型为  ENTRUST_BANK_TYPE_地区编号 如 ENTRUST_BANK_TYPE_571代表杭州
        //public static final String CODE_TYPE_ENTRUST_BANK_TYPE = "ENTRUST_BANK_TYPE";  //适用全省各个地市托收银行类型
        public static final String CODE_TYPE_ENTRUST_BANK_TYPE = "ENTRUST_BANK_TYPE"; //用同一份数据

        //集团资金划帐帐本类型在bs_static_data中配置的code_type 。只能配置一条，取code_value
        public static final String FUND_ADJUST_BALANCE_TYPE_KEY = "CRM_FUND_ADJUST_BALANCE_TYPE";
        //固网押金的押金明细类型数据
        public static final String INDEP_FEE_DTL_TYPE = "INDEP_FEE_DTL_TYPE";
        //
        public static final String BANK_ACCOUNT_TYPE = "BANK_ACCOUNT_TYPE";


        //BANK_FLOW_DEF表默认中心
        public static final String BANK_FLOW_DEF_DEFAULT_REGION_ID = "BANK_FLOW_DEF_DEFAULT_REGION_ID";

        //SMS_NOTICE表默认地市
        public static final String SMS_NOTICE_DEFAULT_REGION_ID = "SMS_NOTICE_DEFAULT_REGION_ID";

        //帐本是否可退可转
        public final static String AM_BALANCE_ALLOW_CASH = "AM_BALANCE_ALLOW_CASH";

        //关于对账单的统一税率
        public final static String DEFAULT_UNIFIED_RATE = "DEFAULT_UNIFIED_RATE";

        //关于对帐单统一税率开关配置
        public final static String UNIFIED_RATE_SWITCH_TYPE = "UNIFIED_RATE_SWITCH_TYPE";

        //对帐单功能上线前月份
        public final static String FUNCTION_YEARMONTH_ON_LINE = "FUNCTION_YEARMONTH_ON_LINE";

        //对账调账状态
        public final static String AM_CHECK_LOG_DTL_STATE = "AM_CHECK_LOG_DTL_STATE";
        //对帐统计表（汇总表）状态
        public final static String AM_CHECK_LOG_STAT_STATE = "AM_CHECK_LOG_STAT_STATE";
        //用户类别
        public final static String AM_SO_USER_TYPE = "SO_USER_TYPE";

        /**
         * 托收相关
         */
        public final static String ENTRUST_NEW_FLOW_REGION = "ENTRUST_NEW_FLOW_REGION";
        public final static String WZ_ENTRUST_HEAD = "WZ_ENTRUST_HEAD";
        public final static String ENTRUST_HZ_FLW_BANK_ID = "ENTRUST_HZ_FLW_BANK_ID";


        //银行批扣冲正
        public final static String CODE_TYPE_QUERY_TYPE4REV_DEDUCT_SINGLE = "AM_QUERY_TYPE4REV_DEDUCT_SINGLE";//银行批扣冲正查询方式
        public final static String CODE_TYPE_ACCOUNT_PAY_METHOD4REV_DEDUCT = "CM_ACCOUNT_PAY_METHOD4REV_DEDUCT"; //银行批扣支付方式
        public final static String CODE_TYPE_BARGIN_TYPE4REV_DEDUCT = "AM_BARGIN_TYPE4REV_DEDUCT";  //批量扣款返回

        //托收批扣业务记录状态
        public final static String CODE_TYPE_ENTRUST_BUSI_REC_TRANS_STATUS = "AM_ENTRUST_BUSI_REC_TRANS_STATUS";

        /**
         * 文件名前后缀
         * _571_000.004         业务量文件
         * _571_000.005         收入文件
         * BBOSS_Billing_LIST_  前缀
         */
        public final static String FILE_BILL_LIST_INFO_NAME = "FILE_BILL_LIST_INFO_NAME";

        /**
         * 手机邮箱功能费科目
         */
        public final static String PUSH_MAIL = "10314";

        /**
         * 黑莓功能费科目
         */
        public final static String BLACK_BERRY_ITEM = "BLACK_BERRY_ITEM";

        /**
         * 商信通功能费科目
         */
        public final static String BUSINESS_ICT_ITEM = "55053";

        /**
         * 财信通业务代码
         */
        public final static String OPERATOR_CODE_ITEM = "88991";

        /**
         * 预约资金转移状态
         */
        public final static String CODE_TYPE_AM_BAL_ORDER_TRANS = "AM_BAL_ORDER_TRANS";

        /**
         * 全国卡状态
         */
        public final static String RES_PAY_CARD_STATE = "RES_PAY_CARD_STATE";

        /**
         * 全国卡所属地区
         */
        public final static String RES_PAY_CARD_BELONGED_REGION = "990500";

        /**
         * 充值类型选择时用户专款项是否可见
         */
        public final static String IS_SPACIAL_USERS = "IS_SPACIAL_USERS";

        /**
         * 村邮站网点编号是否需要验证配置
         */
        public final static String IS_COUNTRY_POST_VALIDATE = "IS_COUNTRY_POST_VALIDATE";

        /**
         * 刷卡记录状态
         */
        public final static String SLOT_CARD_REC_STATUS = "SLOT_CARD_REC_STATUS";

        /**
         * 刷卡交易类型
         */
        public final static String SLOT_CARD_PAY_TYPE = "SLOT_CARD_PAY_TYPE";

        
        public static final String CODE_TYPE_BANK_CODE = "CODE_TYPE_BANK_CODE"; //银行编号
        
        public static final String PLATFORM_CODE_PATH_FTP_CODE = "PLATFORM_CODE_PATH_FTP_CODE"; //根据平台编码查询
        public static final String AM_CFG_TASK_BUSINESS_CLASS = "AM_CFG_TASK_BUSINESS_CLASS"; //进程类
        public static final String AM_CFG_TASK_TYPE_CODE = "AM_CFG_TASK_TYPE_CODE"; //组编号
        public static final String AM_FILE_PATH = "AM_FILE_PATH"; //服务器路径配置
        
        public static final String AM_CFG_URL_CODE = "AM_CFG_URL_CODE"; //服务器地址
        public static final String AM_CFG_URL_PORT_CODE = "AM_CFG_URL_PORT_CODE"; //服务器端口
    }

    interface PARA_DETAIL {

        public static final String PARA_DETAIL_REGION_X = "X"; //省级地市配置,  只用于标识全省统一配置，不代表省级工号地市标识

        public static final String PARA_TYPE_BILLING_CYCLE = "BILLING_CYCLE";
        public static final String PARA_CODE_START_HOUR = "START_HOUR";
        public static final String PARA_CODE_END_HOUR = "END_HOUR";
        public static final String PARA_CODE_START_DAY = "START_DAY";

        public static final String PARA_CODE_PS_COUNT_SLEEP = "PS_COUNT_SLEEP";
        public static final String PARA_CODE_PS_SLEEP_TIME = "PS_SLEEP_TIME";

        /**
         * 签约后扣款生效时间
         */
        public static final String PARA_TYPE_AGENT_ACCT_CHARGE = "AM_AGENT_ACCT_CHARGE";
        public static final String PARA_CODE_CHARGE_AFTER_DATE = "CHARGE_AFTER_DATE";

        /**
         * 押金最小值限制
         */
        public static final String PARA_TYPE_AGENT_DEPOSIT_LIMIT = "AM_AGENT_ACCOUNT_DEPOSIT_LIMIT";
        public static final String PARA_CODE_DEPOSIT_LIMIT_CHARGE = "DEPOSIT_LIMIT_CHARGE";

        /**
         * 批扣门限默认值
         */
        public static final String PARA_TYPE_AGENT_ACCOUNT_AUTO_LIMIT = "AM_AGENT_ACCOUNT_AUTO_LIMIT";
        public static final String PARA_CODE_AUTO_LIMIT_CHARGE = "AUTO_LIMIT_CHARGE";

        /**
         * 业务退费
         */
        public static final String AM_AGENT_BUSINESS_ID_EPAY_REV = "AM_AGENT_BUSINESS_ID_EPAY_REV";
        public static final String BUSINESS_ID_EPAY_REV = "BUSINESS_ID_EPAY_REV";

        /**
         * 业务扣款
         */
        public static final String AM_AGENT_BUSINESS_ID_EPAY = "AM_AGENT_BUSINESS_ID_EPAY";
        public static final String BUSINESS_ID_EPAY = "BUSINESS_ID_EPAY";

        /**
         * 充值额度申请
         */
        public static final String AM_AGENT_BUSINESS_ID_REQUIRE_EPAY = "AM_AGENT_BUSINESS_ID_REQUIRE_EPAY";
        public static final String BUSINESS_ID_REQUIRE_EPAY = "BUSINESS_ID_REQUIRE_EPAY";

        /**
         * 充值额度回收
         */
        public static final String AM_AGENT_BUSINESS_ID_EPAY_CALLBACK = "AM_AGENT_BUSINESS_ID_EPAY_CALLBACK";
        public static final String BUSINESS_ID_EPAY_CALLBACK = "BUSINESS_ID_EPAY_CALLBACK";


        /**
         * 快乐充值3重奏
         */
        public static final String PARA_TYPE_AM_PRESENT_PAY_4BANKPAY = "AM_PRESENT_PAY_4BANKPAY";
        public static final String PARA_CODE_SINGLE_AMOUNT = "SINGLE_AMOUNT";//单笔充值金额，大于此金额才赠送
        public static final String PARA_CODE_SINGLE_MONTH_AMOUNT = "SINGLE_MONTH_AMOUNT";//当月赠送金额上限，超过此金额，不予赠送
        public static final String PARA_CODE_CHECK_BUSINESS_ID = "CHECK_BUSINESS_ID";//快乐充值3重奏优惠业务编码
        public static final String PARA_CODE_CHECK_BANK_CODE = "CHECK_BANK_CODE";//快乐充值3重奏优惠银行编码


        /**
         * 带4号码优惠送相关配置
         */
        public static final String PARA_TYPE_AM_BENEFIT_4_PREPAY = "AM_BENEFIT_4_PREPAY";
        public static final String PARA_CODE_OPERATOR_ID = "OPERATOR_ID";//带4号码优惠送-操作员编号
        public static final String PARA_CODE_SMS_TEMPLATE_ID = "SMS_TEMPLATE_ID";//充值短信模板

        /**
         * 批量本金充值相关配置
         */
        public static final String PARA_TYPE_AM_BATCH_COMMON_PAY = "AM_BATCH_COMMON_PAY";

        /**
         * 批量余额调整相关配置
         */
        public static final String PARA_TYPE_AM_BATCH_ADJUST = "AM_BATCH_ADJUST";

        /**
         * 银行业务批量余额调整相关配置
         */
        public static final String PARA_TYPE_AM_BATCH_BANK_ADJ = "AM_BATCH_BANK_ADJ";

        /**
         * 批量退费录入
         */
        public static final String PARA_TYPE_AM_BATCH_REVFEE = "AM_BATCH_REVFEE";

        /**
         * 账本有效期计算相关配置
         */
        public static final String PARA_TYPE_AM_BA_AVAILDAY_DEF = "AM_BA_AVAILDAY_DEF";
        public static final String PARA_CODE_MAX_YEARS = "MAX_YEARS";


        public static final String PARA_TYPE_AM_PAYMENT = "AM_PAYMENT"; //
        public static final String PARA_CODE_CTRL_FLAG_1_LIMIT = "CTRL_FLAG_1_LIMIT"; //出账期间不可以做的业务编码
        public static final String PARA_CODE_IS_COUNT_LATE_FEE = "IS_COUNT_LATE_FEE";// 不需要计算滞纳金的业务编码
        public static final String PARA_CODE_SPE_PAY_LATE_FEE = "SPE_PAY_LATE_FEE"; // 判断 缴纳专款时，是否计算滞纳金
        public static final String PARA_CODE_PAYMENT_ID_LENGTH = "PAYMENT_ID_LENGTH";//缴费记录PAYMENT_ID长度 25
        public static final String PARA_CODE_SHOW_SPE_PAY_BAL = "SHOW_SPE_PAY_BAL";//前台缴费页面中需要弹出页面展示专款信息
        public static final String PARA_CODE_505PAY_CARD_L = "505PAY_CARD_L";//浙江缴费卡卡号长度
        public static final String PARA_CODE_EPAY_CARD_L = "EPAY_CARD_L";//神州行卡卡号长度
        public static final String PARA_CODE_SEC_MONITOR_ROLE_ID = "SEC_MONITOR_ROLE_ID";//营业班长角色标识
        public static final String PARA_CODE_OWE_QRY_MDB = "OWE_QRY_MDB";
        public static final String PARA_CODE_DELAY_PS_TIME = "DELAY_PS_TIME";//对某些工单延迟处理时间 单位:秒
        //add by xiajs 2011/11/21
        public static final String PARA_CODE_PRI_LOW_BUSI_ID = "PRI_LOW_BUSI_ID"; //充值优先级低，不需要在充值第一个步骤中执行有效计算、TT上发步骤的业务


        public static final String PARA_CODE_REGION_ID_PROVINCE = "REGION_ID_PROVINCE";//省级工号地市标识配置
        public static final String PARA_CODE_PAY_REMIND_AMOUNT = "PAY_REMIND_AMOUNT";//充值时，超过此金额(单位：分)时，多一次提醒确认
        public static final String PARA_CODE_CHECK_PEER_BUSI = "CHECK_PEER_BUSI";//需要校验外部流水是否重复的业务编码


        public static final String PARA_TYPE_AM_WRITE_OFF = "AM_WRITE_OFF";
        public static final String PARA_CODE_EFF_DATE_LIMIT_FLAG = "EFF_DATE_LIMIT_FLAG";


        public static final String PARA_TYPE_AM_OPEN_NEW_TAB = "AM_OPEN_NEW_TAB"; //
        public static final String PARA_CODE_MONTH_INVOICE = "MONTH_INVOICE";
        public static final String PARA_CODE_ENTRUST_INVOICE = "ENTRUST_INVOICE";

        public static final String PARA_CODE_AIR_PAY_REGIONS = "AIR_PAY_REGIONS";//空充账户与账本的代表地市


        /**
         * 滞纳金相关参数
         */
        public static final String PARA_TYPE_AM_LATE_FEE = "AM_LATE_FEE";

        public static final String PARA_CODE_CALCULATE_FLAG = "CALCULATE_FLAG";//滞纳金计算标识 Y 计算  N  不计算
        public static final String PARA_CODE_CALCULATE_MONTHS = "CALCULATE_MONTHS";//滞纳金计算月数

        //crm.ams.banklog  ----begin----
        public static final String PARAM_TYPE_BANKLOG_TRADECODE = "AM_BANKLOG_TRADECODE";//日志交易码参数类型
        public static final String PARA_CODE_BANKLOG_TCSERVER = "TRADECODE_SERVER"; //参数代码_前置机服务端操作类型
        public static final String PARA_CODE_BANKLOG_TCCLIENT = "TRADECODE_CLIENT";//参数代码_前置机客户端操作类型
        public static final String PARA_CODE_BANKLOG_TCMANUAL = "TRADECODE_MANUAL";//参数代码_手工发起需要配置的操作类型
        public static final String PARA_CODE_BANKLOG_TCAGENTSERV = "TRADECODE_AGENTSERV";//参数代码_前置机代理服务
        //crm.ams.banklog  ----end----

        //// --Begin-----hongjf--
        // 银行指令批量查询相关
        public static final String PARA_TYPE_BANK_BUSI_PARA_TYPE_STR = "AM_BANK_BUSI_TYPE"; // 银行指令批量查询--表数据区分参数
        public static final String PARA_CODE_BANK_BUSI_COMM_STR = "COMM_BANK_BUSI_TYPE"; // 普通银行指令批量查询--交易类型参数名
        public static final String PARA_CODE_BANK_BUSI_OTHER_STR = "OTHER_BANK_BUSI_TYPE"; // 其它银行指令批量查询--交易类型参数名
        public static final String PARA_CODE_BANK_BUSI_REV = "BANK_REV_BUSI_TYPE"; // 银行单笔交易返销交易类型
        public static final String PARA_CODE_BANK_BUSI_QUERY_TYPE = "BANK_BUSI_QUERY_TYPE"; //银行交易记录查询限定交易类型

        // 505充值接口--按手机号查询充值记录相关
        public static final String PARA_TYPE_PAYMENT_BUSI_STR = "PAYMENT_BUSI_TYPE"; //按手机号查询充值记录----表数据区分参数
        public static final String PARA_CODE_505CARD_PAYMENT_BUSI_STR = "AM_PAYMENT_505_CODE"; // 按手机号查询充值记录--BUSI类型参数名
        // ATMI接口 － 充值记录查询
        public static final String PARA_CODE_PAYMENT_BUSI_PARACODE_STR = "ATMI_PAYMENT_BUSI"; //ATMI接口充值记录查询--业务类型参数名
        // ATMI接口 － 银行交易记录
        public static final String PARA_TYPE_ATMI_BANK_BUSI_REC_BUSI_ID_STR = "AMTI_BANK_BUSI_TYP"; //ATMI接口银行交易记录查询--业务类型参数名
        public static final String PARA_CODE_ATMI_BANK_BUSI_REC_BUSI_ID_STR = "AMTI_BANK_BUSI_CODE"; //ATMI接口银行交易记录查询--业务类型参数名
        // 综合查询接口 － 充值记录查询
        public static final String PARA_CODE_COMPLEX_PAYMENT_BUSI_STR = "COMPLEX_PAYMENT_BUSI"; // 综合查询按手机号查询充值记录--BUSI类型参数名
        // 资金变更 -- 支出类型（批量销账 和 实时销账）
        public static final String PARA_TYPE_BALANCE_CHANGE_PARA_TYPE_STR = "AM_BALANCE_CHANGE"; // 资金变更支出类型--表数据区分参数
        public static final String PARA_CODE_BALANCE_CHANGE_BATCH_STR = "BATCH_BILL"; // 批量销帐
        public static final String PARA_CODE_BALANCE_CHANGE_REAL_STR = "REAL_TIEM_BILL"; // 实时销帐
        public static final String PARA_TYPE_PAYMENT_OPER_TYPE_TYPE_STR = "AM_PAYMENT_OPER_TYPE";  // 资金变更中充值缴费记录操作类型--区分出资金是出还是入账本的操作
        public static final String PARA_CODE_PAY_AND_PREPAID_STR = "PAY_AND_PREPAID"; // 充值缴费中资金是入账本的操作类型
        //// --End-------hongjf--

        //--check--
        //		银行对帐本端限制 PARAM_CODE 填BANK_CODE_TYPE , PARA1,PARAM2 填SQL限制
        //      PARAM3 填时间清算开始日期,清算结束日期浮动天数,格式为-DD1,DD2(列如配置 -3,31  则查找缴费表开始月为 清算开始日期-3,清算结束日期+31)
        public static final String PARA_TYPE_AM_BANKCHK_SELFLIMIT = "AM_BANKCHK_SELFLIMIT";
        //      银行对帐结果通知号码列表
        public static final String PARA_TYPE_AM_CHECK_RESULT_NOTIFY = "AM_CHECK_RESULT_NOTIFY";
        public static final String PARA_CODE_REMIND_BILL_ID_LIST = "REMIND_BILL_ID_LIST"; //提醒号码列表
        //      银行文件入库控制参数
        public static final String PARA_TYPE_AM_BANKCHK_IMPSIDECTRL = "AM_BANKCHK_IMPSIDECTRL";

        //--check--

        //银行对账调帐
        public static final String PARA_TYPE_AM_BANK_ADJUST = "AM_BANK_ADJUST"; //银行对账调帐
        public static final String PARA_CODE_COUNT_LIMIT = "COUNT_LIMIT"; //调帐笔数限制
        public static final String PARA_CODE_ENFORCE_COUNT_LIMIT = "ENFORCE_COUNT_LIMIT"; //强制调帐笔数限制
        public static final String PARA_CODE_FILE_NAME_PROFIX = "FILE_NAME_PROFIX"; //银行对帐调帐文件名前缀
		//实扣对账调账
		public static final String PARA_TYPE_AM_REALPAY_ADJUST = "AM_REALPAY_ADJUST"; //实扣对账调账
		
        //返销业务配置
        public static final String PARA_TYPE_WRITEOFF_BACK = "WRITEOFF_BACK"; //前台返销
        public static final String PARA_CODE_STAGE_COMMON = "STAGE_COMMON"; //普通充值业务
        public static final String PARA_CODE_STAGE_AGENT = "STAGE_AGENT"; //代理商充值业务
        public static final String PARA_CODE_AIR_AGENT = "AIR_AGENT"; //空中充值代理商
        public static final String PARA_CODE_BAL_HIS_MONTHS = "BAL_HIS_MONTHS"; //历史帐本查询限制月
        public static final String PARA_CODE_TIME_LIMIT_FREE = "TIME_LIMIT_FREE"; //返销不限制时间的业务编码
        public static final String PARA_TYPE_WRITEOFF_BACK_TIME = "WRITEOFF_BACK_TIME"; //前台返销时间配置

        //代理商资金返还
        public static final String PARA_TYPE_AM_PARTNER = "AM_PARTNER"; //代理商
        public static final String PARA_CODE_BALANCE_LIMIT = "BALANCE_LIMIT"; //代理商资金返还帐户余额阀值
        public static final String PARA_CODE_FILE_NAME_EXT = "FILE_NAME_EXT"; //二次投诉反撤调整文件名
        public static final String PARA_CODE_AM_PARTNER_DEFAULT_PID = "AM_PARTNER_DEFAULT_PID"; //代理商调帐指定代理网点

        //转账、退费相关配置
        public static final String PARA_TYPE_AM_BALANCE_RETURN = "AM_BALANCE_RETURN"; //退预存
        public static final String PARA_CODE_ALLOW_DIFF_REGION = "ALLOW_DIFF_REGION"; //是否允许异地
        public static final String PARA_CODE_ALLOW_SAME_ACCT = "ALLOW_SAME_ACCT"; //是否允许同一帐户转移
        public static final String PARA_TYPE_AM_BALANCE_TRANS = "AM_BALANCE_TRANS"; //资金转移
        public static final String PARA_CODE_GROUP_TRANS_LIMIT = "GROUP_TRANS_LIMIT"; //集团预付费帐户转移金额配置
        public static final String PARA_CODE_SPE_ITEM = "SPE_ITEM"; //帐目级专款
        public static final String PARA_CODE_SPE_USER = "SPE_USER"; //用户级专款
        public static final String PARA_CODE_REPORT_FLAG = "REPORT_FLAG"; //转帐是否需要记录日报
        public static final String PARA_DETAIL_USER_SPE_TRANS_FLAG_Y = "Y"; //允许用户级专款转帐
        public static final String PARA_DETAIL_USER_SPE_TRANS_FLAG_N = "N"; //不允许用户级专款转帐
        public static final String PARA_DETAIL_ITEM_SPE_TRANS_FLAG_Y = "Y"; //允许帐目级专款转帐
        public static final String PARA_DETAIL_ITEM_SPE_TRANS_FLAG_N = "N"; //不允许帐目级专款转帐

        //余额调整相关配置
        public static final String PARA_TYPE_AM_BALANCE_ADJUST = "AM_BALANCE_ADJUST"; //余额调整
        public static final String PARA_CODE_ADJUST_LIMIT = "LIMIT"; //阀值

        //有效期催停相关配置
        public static final String PARA_TYPE_GENCARD_URGESTOP = "GENCARD_URGESTOP"; //有效期催停
        public static final String PARA_CODE_URGESTOP_DAY = "URGESTOP_DAY"; //催停日期
        public static final String PARA_TYPE_URGENCY = "URGENCY"; //有效期催停
        public static final String PARA_CODE_CJCS_SMS = "CJCS_SMS"; //短信催缴次数
        public static final String PARA_CODE_CJCS_VOICE = "CJCS_VOICE"; //语音催缴次数
        public static final String PARA_CODE_SBZCG_SMS = "SBZCG_SMS"; //短信失败转成功次数
        public static final String PARA_CODE_SBZCG_VOICE = "SBZCG_VOICE"; //语音失败转成功次数
        public static final String PARA_CODE_VALID_DAYS_SMS = "VALID_DAYS_SMS"; //短信催缴有效天数
        public static final String PARA_CODE_VALID_DAYS_VOICE = "VALID_DAYS_VOICE"; //语音催缴有效天数
        public static final String PARA_CODE_SMS_TONE = "SMS_TONE"; //有效期短信催缴语气
        public static final String PARA_CODE_ZJHM = "ZJHM"; //有效期催停主叫号码

        //停复机相关配置
        public static final String PARA_TYPE_AM_SPEC_OPEN = "AM_SPEC_OPEN"; //特权报开
        public static final String PARA_CODE_MGR_LIMIT = "MGR_LIMIT"; //电话经理阀值
        public static final String PARA_CODE_VIP_LIMIT = "VIP_LIMIT"; //全球通VIP客户阀值
        public static final String PARA_TYPE_AM_AVAILDAY = "AM_AVAILDAY"; //有效期更新
        public static final String PARA_CODE_ADJUST_MONTH = "ADJUST_MONTH"; //限制月配置

        //准备TF进程数据配置
        public static final String PARA_TYPE_AM_MAKE_TF_SRC_DATA = "AM_MAKE_TF_SRC_DATA"; //生产TF源表数据
        public static final String PARA_CODE_AM2WEB_BANK_CARD = "AM2WEB_BANK_CARD";//帐务到网厅银行卡业务数据

        //算黑名单欠费月数
        public static final String PARA_TYPE_AM_OWEFEE_MONTH = "AM_OWEFEE_MONTH";
        public static final String PARA_CODE_AM_DESTROY_OWEFF = "AM_DESTROY_OWEFF"; //历史欠费
        public static final String PARA_CODE_AM_LOSS_OWEFF = "AM_LOSS_OWEFF"; //报损欠费

        //帐管 任务执行的虚拟操作员
        public static final String PARA_TYPE_AM_TASK_OPERATORS = "AM_TASK_OPERATORS";//
        public static final String PARA_CODE_OPER_COMMON = "OPER_COMMON";
        public static final String PARA_CODE_OPER_UNFREEZE = "OPER_UNFREEZE";
        public static final String PARA_CODE_OPER_GROUP_FUND_ADJUST = "OPER_GROUP_FUND_ADJUST";
        public static final String PARA_CODE_OPER_SUPER = "OPER_SUPER";

        //空中充值余额调用-给调出用户发信息 的各个参数
        public static final String PARA_TYPE_AIR_ADJUST_SMS_PARAM = "AIR_ADJUST_SMS_PARAM";//
        public static final String PARA_CODE_SMS_VALID_DAYS = "SMS_VALID_DAYS";    //催缴有效天数
        public static final String PARA_CODE_SMS_SMS_CODE = "SMS_SMS_CODE";        //短信编码
        public static final String PARA_CODE_SMS_PRIORITY = "SMS_PRIORITY";        //优先级
        public static final String PARA_CODE_SMS_CALL_NO = "SMS_CALL_NO";        //主叫号码
        public static final String PARA_CODE_SMS_FAIL_COUNT = "SMS_FAIL_COUNT";    //发送次数
        public static final String PARA_CODE_SMS_SMS_COUNT = "SMS_SMS_COUNT";    //催缴次数

        //提醒业务
        public static final String PARA_TYPE_AM_FEE_EARLY_ALERT_PARAM = "AM_FEE_EARLY_ALERT_PARAM";
        //余额天天提醒忽略帐户支付方式 para1填忽略的ACCT_PAY_METHOD列表,类型之间用英文字母';'分隔  忽略 0,2,3,4,5,6,8,9,10
        public static final String PARA_CODE_DAILY_BALANCE_IGNOR_PAY_METHOD = "DAILY_IGNOR_ACCT_PAY_METHOD";
        //话费到达提醒忽略帐户支付方式 para1填忽略的ACCT_PAY_METHOD列表,类型之间用英文字母';'分隔  忽略 7
        public static final String PARA_CODE_UPPER_LIMIT_IGNOR_PAY_METHOD = "UPPER_IGNORE_ACCT_PAY_METHOD";
        //余额不足提醒忽略帐户支付方式 para1填忽略的ACCT_PAY_METHOD列表,类型之间用英文字母','分隔
        //public static final String PARA_CODE_NOENOUGH_BALANCE_IGNORE_PAY_METHOD = "NOENOUGH_IGNORE_ACCT_PAY_METHOD";

        public static final String PARA_TYPE_ATMI_PAY_CASH_REV = "ATMI_PAY_CASH_REV"; //ATMI现金充值冲正
        public static final String PARA_CODE_EB_USERS = "EB_USERS"; //电商平台操作员工号配置

        public static final String PARA_CODE_PS_DELAY_TIME = "PS_DELAY_TIME"; //工单延迟执行秒数

        //渠道资金归集业务配置
        public static final String PARA_TYPE_AM_AGENT_BALANCE_BUSINESS = "AM_AGENT_BALANCE_BUSINESS";

    }


    interface AGENT_ACCT {

        public static final String ACCT_TYPE___AGENT_EPAY = "1";            //电子充值账户
        public static final String ACCT_TYPE___AGENT_CONTRACT_PAY = "2";    //合约支付账户
        public static final String ACCT_TYPE_AIR_PAY_ = "3";                // 空中充值
        public static final String ACCT_TYPE_AIR_PAY_AGENT = "4";                // 4：空中充值二级网点账户

        public static final String AGENT_IS_AUTO_CHARGE = "Y";            //支持自动扣款
        public static final String AGENT_IS_NOT_AUTO_CHARGE = "N";        //不支持支持自动扣款
    }

    /**
     * 代理商-电子充值
     */
    interface AGENT_EPAY {
        public static final String PAY_OPERATION_TYPE_SELF = "S";  // 代理商给自己充值(人工)
        public static final String PAY_OPERATION_TYPE_OTHER = "O"; // 为其他人充值
        public static final String PAY_OPERATION_TYPE_CALLBACK = "C"; // 回收充值额度
        
        public static final String PAY_OPERATION_TYPE_EDHB_P = "H"; // 划拨充值额度(总代)
        public static final String PAY_OPERATION_TYPE_EDHB_S= "I"; // 划拨充值额度（二级网点)
        public static final String PAY_OPERATION_TYPE_EDHS_P = "R"; // 回收充值额度(总代)
        public static final String PAY_OPERATION_TYPE_EDHS_S = "D"; // 回收充值额度（二级网点)

        public static final String PAYMENT_CERT_TYPE_0 = COMMON.PAYMENT_CERTIFICATE_TYPE_CASH;//	现金
        public static final String PAYMENT_CERT_TYPE_2 = COMMON.PAYMENT_CERTIFICATE_TYPE_505CARD;//	缴费卡

        public static final String PAYMENT_CHARGE_TYPE_0 = COMMON.PAYMENT_CERTIFICATE_TYPE_CASH; //	现金
        public static final String PAYMENT_CHARGE_TYPE_CALLBACK = "1";                              //    回收充值额度
        public static final String PAYMENT_CHARGE_TYPE_2 = COMMON.PAYMENT_CERTIFICATE_TYPE_505CARD; //	缴费卡
        public static final String PAYMENT_CHARGE_TYPE_3 = "3";                                       //	省外异地充值

        public static final String STATE_UNCERTAIN = "9";    // 不确定状态
        public static final String STATE_FAIL = "0";         // 失败状态
        public static final String STATE_SUCCESS = "1";      // 成功状态

        public static final String STATE_CALLBACK = "4"; // 充值额度被回收
        public static final long CALLBACK_OPT_SEQ = 2;        //回收OPT_SEQ
        public static final String PAYOUT_TYPE_CALLBACK = "C";  // 支出类型（充值额度回收）

        public static final long ORG_USER_ID = 0; // 组织对应的用户标识（充值门限管理中使用）

        public static final int OP_BILLID_LENGTH = 11; // 省外异地充值位数限制

        public static final String AGENT_CHARGED_AMOUNT = "AGENT_CHARGED_AMOUNT"; // 代理商已充值金额
        // 代理商充值门限配置类型：D为日门限，S为单笔门限，O为组织日门限
        public static String[] AGENT_LIMIT_TYPE = new String[]{"D", "S", "O"};

        public static final String ACCT_STATE_CANCEL = "0";//代理商帐户状态，0：作废
        public static final String ACCT_STATE_NORMAL = "1";//代理商帐户状态，1：正常
        public static final String ACCT_STATE_NOT_TRANSFER = "4";//代理商帐户状态，4：已注销，余额未转移；
        public static final String ACCT_STATE_TRANSFERED = "5";//代理商帐户状态，5：已注销，余额已结清；

        public static final int EXPIRE_DATE = 2099;//代理商帐户失效年份

        public static final String AM_AGENT_USER_STATE_1 = "1";//1:标识代理商用户表中记录有效
        public static final String AM_AGENT_USER_STATE_0 = "0";//0:标识代理商用户表中记录作废

        public static final String CH_PAY_TYPE_TRANSFER_OUTLINE = "1";//线下支付
        public static final String CH_PAY_TYPE_CONTACT_ONLINE = "2";//	签约支付

        public static final String SOURCE_SYSTEM_CRM = "CRM";
        public static final String SOURCE_SYSTEM_CHNL = "CHNL";
        public static final String SOURCE_SYSTEM_TERM = "TERM";

    }

    /**
     * 空中充值
     */
    interface AIRPAY {
        //
        public static final String AIR_DEF_FOR_ALL_BANK_CODE_TYPE = "X";

        /**
         * 空中充值3大代理商账本存放在一个固定中心
         * 这里配置一个此中心的地市标识
         */
        //public static final String AIR_PAY_DEFAULT_REGION_ID ="571" ;//
        //用于配置代理商的专用调出号码
        public static final String AIR_PAY_ADJUST_OUT_BILLID = "SPE_ADJ_OUT_BILL_ID";
        //用于配置代理网点每月可申请调账的最大限制
        //public static final String AIR_PAY_ADJUST_MONTH_REQUEST_COUNT_LIMIT = "MONTH_REQUEST_COUNT_LIMIT";
        //用于配置代理商接收专用调出账户不足的手机号码
        public static final String AIR_PAY_SMS_BILLID = "SMS_BILL_ID";
        //用于配置几天内的充值记录才可以申请调帐
        //public static final String AIR_PAY_ADJUST_ALLOW_DAYS_LIMIT = "ALLOW_DAYS_LIMIT";
        //空中充值余额调整申请的处理状态
        public static final int AIR_PAY_ADJUST_REQUEST_STATUS_UNHANDLED = 0;//未处理
        public static final int AIR_PAY_ADJUST_REQUEST_STATUS_HANDLED = 1;//已处理
        public static final int AIR_PAY_ADJUST_REQUEST_STATUS_EXCLUDED = 2;//不处理

        public static final String AIR_PAY_OPERATION_TYPE_OUT_PAY = "O";//代理商给其他用户充值
        public static final String AIR_PAY_OPERATION_TYPE_REV_PAY = "R";//代理商给其他用户充值 后返销

        public static final String SINGLE_PAY_LOW_LIMIT = "SINGLE_PAY_LOW_LIMIT";//代理商单笔充值最小金额限制
        public static final String SINGLE_PAY_LOW_MAX = "SINGLE_PAY_LOW_MAX";//代理商单笔充值最大金额限制

        public static final String AIR_PAY_HAS_BANK_WEB_INFO = "HAS_BANK_WEB_INFO"; //是否有代理充值网点，有则是真正的空中充值，无则表示只是银行充值，但需要进行额度限制
        public static final String AIR_PAY_HAS_BANK_WEB_INFO_Y = "Y";
        public static final String AIR_PAY_HAS_BANK_WEB_INFO_N = "N";
    }

    /**
     * 帐单发票打印
     *
     * @author taojy
     *         <p/>
     *         Feb 23, 2011
     */
    interface INVOICE_FINAL {
        public static final String QRYTYPE_BILLID = "0"; // 查询条件服务接入号
        public static final String QRYTYPE_ACCTID = "1"; // 查询条件帐户编号
        public static final String QRYTYPE_CONTRACT_NO = "2"; // 托收发票专票查询条件协议编号

        public static final String PRINTTYPE_ACCT = "0"; // 查询条件选择打印类为帐户发票
        public static final String PRINTTYPE_USER = "1"; // 查询条件选择打印类型为用户发票
        public static final String PRINTTYPE_CONTRACT_NO = "1"; // 托收发票专票打印类型为协议号
    }

    /**
     * 查询相关
     *
     * @author hongjf
     */
    interface QUERY {
        //modify by hexx 20120110 帐户资金变化明细从12个月改成36个月
        public static final int AM_BALANCE_QRY_MONTH_LENGTH = 36;                      //资金变化明细查询，最多查询6个月
        public static final String BANK_BUSI_REGION_STR = "X"; // 银行指令批量查询--银行指令地区参数
        public static final String PAYMENT505_BUSI_REGION_STR = "X"; // 银行指令批量查询--银行指令地区参数

        public static final String BANK_TYPE_COUNTY = "0"; //银行类型--县级
        public static final String BANK_TYPE_CITY = "1"; //银行类型--市级
        public static final String BANK_TYPE_PROVINCE = "2"; //银行类型--省级

        public static final String BANK_BUSI_QUERY_TYPE_BANKSEQ = "0"; //查询方式--银行流水号
        public static final String BANK_BUSI_QUERY_TYPE_BILLCODE = "1"; //查询方式--用户手机号码
        public static final String BANK_BUSI_QUERY_TYPE_BANKACCTCODE = "2"; //查询方式--银行帐号

        public static final String BANK_BUSI_QUERY_IS_BILL_REL_N = "0"; //不需要查询关联用户手机号
        public static final String BANK_BUSI_QUERY_IS_BILL_REL_Y = "1"; //需要查询关联用户手机号

        public static final String BANK_BARGINTYPE_BAT_DEDUCT = "9998"; //页面协定--"银行批量扣款业务状态"
        public static final String BANK_BARGINTYPE_BAT_COLLECT = "9999"; //页面协定--"银行批量托收业务状态"

        public static final String OWE_BUSI_QUERY_MODE_BILLID = "1"; //欠费业务查询查询方式--服务接入号;
        public static final String OWE_BUSI_QUERY_MODE_USERID = "2"; //欠费业务查询查询方式--用户编号;
        public static final String OWE_BUSI_QUERY_MODE_ACCTID = "3"; //欠费业务查询查询方式--帐户编号;
        public static final String OWE_BUSI_QUERY_MODE_CUSTID = "4"; //欠费业务查询查询方式--客户编号;

        public static final String SMS_LOG_QUERY_MODE_BILLID = "1"; //欠费业务记录查询方式--手机号;

        public static final int OWE_NUM_QUERY_CUSTOMER_TYPE_INDIVI = 1; // 欠费号码查询--个人客户

        public static final String PAYMENT_REC_QUERY_MODE_BILLID = "1"; //资金变化记录查询方式--以手机号方式查询
        public static final String PAYMENT_REC_QUERY_MODE_ACCTID = "2"; //资金变化记录查询方式--以账户号方式查询

        public static final String PAYMENT_REC_QUERY_BILL_TYPE_IN = "1"; //充值记录查询方式--号码在网
        public static final String PAYMENT_REC_QUERY_BILL_TYPE_UN_RECYCLED = "2"; //充值记录查询方式--号码销户未回收
        public static final String PAYMENT_REC_QUERY_BILL_TYPE_RECYCLED = "3"; //充值记录查询方式--号码销户已回收

        public static final int BANK_BUSI_DONE_RESULT_SUCCESS = 0; // 银行交易记录--成功状态

        /**
         * 对账调账页面个别字段显示名称
         */
        //public static final String REGION_DIPLAY_NAME = "REGION_DIPLAY_NAME";         // 地区名称
        //public static final String EXCEPTION_DIPLAY_NAME = "EXCEPTION_DIPLAY_NAME";   // 对账结果
        //public static final String BUSINESS_DIPLAY_NAME = "BUSINESS_DIPLAY_NAME";     // 业务类型STATE_DISPLAY_NAME
        //public static final String STATE_DISPLAY_NAME = "STATE_DISPLAY_NAME";         // 状态名称

        public static final int AM_PAYMENT_HALF_YEAR_SEARCH = 6;                      // 半年账期
    }

    interface SO {

        public static final long PROD_CATALOG_ID_GSM = 1;// GSM规格

        public static final String STATIC_DATE_SO_BRAND_TYPE = "SO_BRAND_TYPE";


        /**
         * 品牌定义
         */


        //账户类型定义
        //默认基本账户
        //全部
        public static final int ACCT_PAY_TYPE_ALL = -1;

        // 用户类型


        /**
         * 生效类型
         */

        /*
         * 预缴受理费用支付方式
         */

        /*
         * 渠道编号 guowx 130520
         */
        public static final String SO_CHANNEL_TYPE_0 = "0";// 联网银行
        public static final String SO_CHANNEL_TYPE_1 = "1";// 营业厅
        //		public static final String SO_CHANNEL_TYPE_10 = "10";//BBOSS系统
//		public static final String SO_CHANNEL_TYPE_11 = "11";//业务平台WEB
//		public static final String SO_CHANNEL_TYPE_12 = "12";//业务平台IVR
//		public static final String SO_CHANNEL_TYPE_13 = "13";//业务平台SMS
//		public static final String SO_CHANNEL_TYPE_14 = "14";//业务平台接口机
//		public static final String SO_CHANNEL_TYPE_15 = "15";//业务平台批量
//		public static final String SO_CHANNEL_TYPE_16 = "16";//业务平台功能复制开通
//		public static final String SO_CHANNEL_TYPE_17 = "17";//业务平台文件开通
//		public static final String SO_CHANNEL_TYPE_18 = "18";//业务平台WAP
//		public static final String SO_CHANNEL_TYPE_19 = "19";//业务平台MMS
//		public static final String SO_CHANNEL_TYPE_2 = "2";//10086客服
//		public static final String SO_CHANNEL_TYPE_20 = "20";//业务平台JAVA
//		public static final String SO_CHANNEL_TYPE_23 = "23";//短信营业厅
//		public static final String SO_CHANNEL_TYPE_25 = "25";//营业批量受理
//		public static final String SO_CHANNEL_TYPE_26 = "26";//PushEmail客户端
        public static final String SO_CHANNEL_TYPE_3 = "3";//代理商
        //		public static final String SO_CHANNEL_TYPE_4 = "4";//网上营业厅
//		public static final String SO_CHANNEL_TYPE_5 = "5";//合作营业厅
//		public static final String SO_CHANNEL_TYPE_6 = "6";//内部管理
        public static final String SO_CHANNEL_TYPE_7 = "7";//后台自动处理
        //		public static final String SO_CHANNEL_TYPE_8 = "8"; //小额支付
//		public static final String SO_CHANNEL_TYPE_9 = "9"; //外部接口
        public static final String SO_CHANNEL_TYPE_99 = "99"; //CBOSS
        //		public static final String SO_CHANNEL_TYPE_71 = "71";//托收
        public static final String SO_CHANNEL_TYPE_29 = "29";//空中充值
        public static final String SO_CHANNEL_TYPE_39 = "39";//ATMI
        public static final String SO_CHANNEL_TYPE_49 = "49";//VC充值
        public static final String SO_CHANNEL_TYPE_59 = "59";//505充值
        public static final String SO_CHANNEL_TYPE_69 = "69";//全国卡
        public static final String SO_CHANNEL_TYPE_89 = "89";//自助终端
    }

    interface BankZDZ {
        public static final String BANK_PAYMENT_STATE_PAY = "1";//正常
        public static final String BANK_PAYMENT_STATE_FOR_RB = "6";//发起记录，为了冲正
        public static final String BANK_PAYMENT_STATE_FAIL = "7";//发起失败记录
        public static final String BANK_PAYMENT_STATE_ROLLBACK_1 = "2";//冲正返销
        public static final String BANK_PAYMENT_STATE_ROLLBACK_2 = "4";//退费返销
        public static final String BANK_PAYMENT_STATE_ROLLBACKED_1 = "3";//被冲正返销
        public static final String BANK_PAYMENT_STATE_ROLLBACKED_2 = "5";//被退费返销
        public static final String BANK_ZDZ_PAY_BUSINESS_ID = "577001";
    }

    interface CRM {



        /**
         * 支付方式:移动充值
         */

        /**
         * 支付方式:托收
         */

        /**
         * 支付方式:一户通
         */

        /**
         * 支付方式:专用卡（预付）
         */

        /**
         * 支付方式:储蓄卡
         */

        /**
         * 支付方式:信用卡（日控）
         */

        /**
         * 预付费
         */


        /**
         * 从接口inter引入--begin--*
         */
        public static final int ACCT_FTP_TYPE_BILL = 0;//IBsInterfaceSV.FTP_TYPE_BILL;// 0 帐单发票类
        public static final int ACCT_FTP_TYPE_CHECK = 1;//IBsInterfaceSV.FTP_TYPE_CHECK;//1  对帐类
        public static final int ACCT_FTP_TYPE_BATCH = 2;//IBsInterfaceSV.FTP_TYPE_BATCH;// 2 批量业务类
        public static final int ACCT_FTP_TYPE_AGENT = 3;//IBsInterfaceSV.FTP_TYPE_AGENT; //3 合作商类
        public static final int ACCT_FTP_TYPE_SPECIAL = 9;//IBsInterfaceSV.FTP_TYPE_SPECIAL; //9 其它类

        // ftp传输方向
        public static final int ACCT_FTP_PATH_UPLOAD = 0;//IBsInterfaceSV.FTP_PATH_UPLOAD; //0 上传目录
        public static final int ACCT_FTP_PATH_DOWNLOAD = 1;//IBsInterfaceSV.FTP_PATH_DOWNLOAD; //1 下载目录
        /**从接口inter引入--end--**/
    }

    interface CHECK {
        public static final int BANK_CHECK_EXCEPTION_SUCCESS = 0;     //对帐成功
        public static final int BANK_CHECK_EXCEPTION_SIDE_ONLY = 100;   //银行独有
        public static final int BANK_CHECK_EXCEPTION_SELF_ONLY = 101;   //移动独有
        public static final int BANK_CHECK_EXCEPTION_OTHER = 102;   //其他异常
        public static final int BANK_CHECK_EXCEPTION_SELF_MORE = 103;   //移动多
        public static final int BANK_CHECK_EXCEPTION_SELF_LESS = 104;   //移动少

        public static final String BANK_CHECK_STATE_AUTO_ADJ_FAULT = "F"; //调帐前后多次发对帐文件产生的无效数据
        public static final String BANK_CHECK_STATE_AUTO_ADJ_INIT = "0"; //正常未调帐记录
        public static final String BANK_CHECK_STATE_AUTO_ADJ_SUCCESS = "1"; //自动调帐后成功记录
        public static final String BANK_CHECK_STATE_AUTO_ADJ_FAILED = "2"; //自动调帐后失败记录
        public static final String BANK_CHECK_STATE_BAL_ADJ_SUCCESS = "3"; //余额调整成功记录
        public static final String BANK_CHECK_STATE_BAL_ADJ_FAILED = "4"; //余额调整失败记录
        public static final String BANK_CHECK_STATE_BAL_ADJ_UNSORTED = "X"; //余额调整获取多条记录时，只更新第一条，其它设为X
    }

    interface ADJUST {
        public final static int BANK_ADJUST_MODE_DEFAULT = 0; //银行调帐模式-默认；
        public final static int BANK_ADJUST_MODE_ENFORCE = 1; //银行调帐模式-强制；

        public static final String BANK_ADJUST_FNAME_YDTZ = "YDTZ"; //移动调帐
        public static final String BANK_ADJUST_FNAME_YHTZ = "YHTZ"; //银行调帐
        public static final String BANK_ADJUST_FNAME_QZ = "QZ";   //强制调帐

        public final static String BANK_ADJUST_STATE_SUCCESS = "0"; //银行对帐调帐成功
        public final static String BANK_ADJUST_STATE_FAILED = "1"; //银行对帐调帐失败

        public final static String BALANCE_ADJUST_STATE_SUCCESS = "0"; //余额调整成功
        public final static String BALANCE_ADJUST_STATE_FAILED = "1"; //余额调整失败

        public final static int BANK_PARTNER_ADJUST_DIRECTION_SINGLE = 0; //银行合作商调帐模式-单边调整
        public final static int BANK_PARTNER_ADJUST_DIRECTION_DOUBLE = 1; //银行合作商调帐模式-双边调整

        public final static int BANK_PARTNER_ADJUST_TYPE_0 = 0; //单边
        public final static int BANK_PARTNER_ADJUST_TYPE_1 = 1; //双边
        public final static int BANK_PARTNER_ADJUST_TYPE_2 = 2; //误充值调整
        public final static int BANK_PARTNER_ADJUST_TYPE_3 = 3; //投诉反撤调整

        public final static int BANK_PARTNER_ADJUST_STATUS_0 = 0; //人工取消
        public final static int BANK_PARTNER_ADJUST_STATUS_1 = 1; //筛选成功
        public final static int BANK_PARTNER_ADJUST_STATUS_2 = 2; //筛选失败
        public final static int BANK_PARTNER_ADJUST_STATUS_3 = 3; //短信发送成功
        public final static int BANK_PARTNER_ADJUST_STATUS_4 = 4; //短信发送失败
        public final static int BANK_PARTNER_ADJUST_STATUS_5 = 5; //调整成功
        public final static int BANK_PARTNER_ADJUST_STATUS_6 = 6; //调整失败
        public final static int BANK_PARTNER_ADJUST_STATUS_7 = 7; //预开户、预销户、特殊套餐

        public final static int ADJUST_DEAL_METHOD_0 = 0; //发短信、调有效期
        public final static int ADJUST_DEAL_METHOD_1 = 1; //发短信、不调有效期
        public final static int ADJUST_DEAL_METHOD_2 = 2; //不发短信、调有效期
        public final static int ADJUST_DEAL_METHOD_3 = 3; //不发短信、不调有效期

        public final static String BANK_PARTNER_ADJUST_OPERATION_CANCEL = "cancel"; //调帐取消
        public final static String BANK_PARTNER_ADJUST_OPERATION_ADJUST = "adjust"; //人工调帐

        // 银行指令方向
        public static final int BUSINESS_ID_DIRECT_COMPANY_TO_BANK = 0; //银行指令方向-- 移动-银行
        public static final int BUSINESS_ID_DIRECT_BANK_TO_COMPANY = 1; //银行指令方向-- 银行-移动

        public static final String BANK_ADJUST_TYPE_L1 = "L1"; //对账调账
        public static final String BANK_ADJUST_TYPE_L2 = "L2"; //余额调整

        public static final String BANK_CHECK_STAT_STATE_0 = "0"; //对帐正常
        public static final String BANK_CHECK_STAT_STATE_1 = "1"; //对帐发生异常
        public static final String BANK_CHECK_STAT_STATE_2 = "2"; //调帐中
        public static final String BANK_CHECK_STAT_STATE_3 = "3"; //已调帐
        public static final String BANK_CHECK_STAT_STATE_F = "F"; //调帐前后多次发对帐文件产生的无效数据

        public static final String BANK_CHECK_ADJ_TASK_TYPE_CODE = "AM_BANK_INTERFACE"; //银行对帐调帐指令上发任务名称

    }

    interface COMMON_REV {

        public static final String VC_CHARGE_REV_STATUS_0 = "0"; //成功
        public static final String VC_CHARGE_REV_STATUS_3002 = "3002"; //未开户
        public static final String VC_CHARGE_REV_STATUS_3008 = "3008"; //被返销交易不存在
        public static final String VC_CHARGE_REV_STATUS_3009 = "3009"; //被返销交易已返销
        public static final String VC_CHARGE_REV_STATUS_3010 = "3010"; //被返销交易已对账

    }

    interface BANK_REV {

        public final static int BANK_BUSI_DIR_CM2BANK = 0; //交易方向：移动-银行
        public final static int BANK_BUSI_DIR_BANK2CM = 1; //交易方向：银行-移动

        public final static int WRITEOFFREV_DEALSTATUS_SUCCESS = 0; //冲正成功状态
        public final static int WRITEOFFREV_DEALSTATUS_FAIL = 1; //冲正失败状态

        public final static String MAIN_PARTNERS_90 = "90"; //空中充值合作商-连连科技
        public final static String MAIN_PARTNERS_T0 = "T0"; //空中充值合作商-杭州正和
        public final static String MAIN_PARTNERS_T1 = "T1"; //空中充值合作商-杭州纵横

        public final static int BANK_FLOW_DEF_REV_FLAG_0 = 0; //对银行开放返销接口
        public final static int BANK_FLOW_DEF_REV_FLAG_1 = 1; //不对银行开放返销接口

        public static final int BANK_BUSI_DONE_RESULT_REVOFF = -1; //撤销
        public static final int BANK_BUSI_DONE_RESULT_SUCCESS = 0; //成功
        public static final int BANK_BUSI_DONE_RESULT_FAIL = 1;    //失败
        public static final int BANK_BUSI_DONE_RESULT_RETURN = 10; //退款

    }

    interface BANKINTER {

        public static final String INDEP_FEE_BANK_OPT_CODE_FREEZE = "YYDJ";    // 银行冻结，在调用银行冻结接口时使用
        public static final String INDEP_FEE_BANK_OPT_CODE_UNFREEZE = "YYJD";//	银行解冻，在调用银行解冻接口时使用
        public static final String INDEP_FEE_BANK_UNFREEZE_TRAN_TYPE_SINGLE = "T2"; //单个解冻，在调用银行解冻接口时使用
        public static final String INDEP_FEE_BANK_UNFREEZE_TRAN_TYPE_BATCH = "L2"; //批量解冻，在调用银行解冻接口时使用

        public static final String INDEP_FEE_BANK_UNFREEZE_SUCCEED = "INS0"; //批量解冻，在调用银行解冻接口时使用. 返回本值为成功，其它为失败

        ////////////////////////////////////////
        //   银行接口参数或者返回的结果使用的key值   //
        ////////////////////////////////////////

        public static final String PUBINFO_OPID = "opId";
        public static final String PUBINFO_KEYID = "keyId";
        public static final String PUBINFO_OPORGID = "opOrgId";
        public static final String PUBINFO_OPREGIONCODE = "opRegionCode";
        public static final String PUBINFO_OPCOUNTYCODE = "opCountyCode";
        public static final String PUBINFO_ORGID = "orgId";
        public static final String PUBINFO_BUSIORIGIN = "busiOrigin";
        public static final String PUBINFO_NOTES = "notes";
        public static final String PUBINFO_SERVCODE = "servCode";

        public static final String IDINFO_IDTYPE = "idType";
        public static final String IDINFO_IDNUM = "idNum";
        public static final String IDINFO_CUSTNAME = "custName";

        public static final String DEPOSITINFO_MAP_IDINFO = "idInfo";
        public static final String DEPOSITINFO_MAP_BILLID = "billId";
        public static final String DEPOSITINFO_MAP_AMOUNT = "amount";
        public static final String DEPOSITINFO_MAP_PUNISHAMOUNT = "punishAmount";
        public static final String DEPOSITINFO_MAP_BANKACCID = "bankAccId";
        public static final String DEPOSITINFO_MAP_REASON = "reason";
        public static final String DEPOSITINFO_MAP_AUTHCODE = "authCode";


        public static final String RESULTMAP_AUTHCODE = "authCode";//冻结用
        public static final String RESULTMAP_RETURNCODE = "returnCode";
        public static final String RESULTMAP_RETURNMSG = "returnMsg";
        public static final String RESULTMAP_BANKSEQ = "bankSeq";
        public static final String RESULTMAP_RESULTLIST = "resultList";//解冻用
        public static final String RESULTMAP_DEPOSITINFO_IDINFO = "idInfo";
        public static final String RESULTMAP_DEPOSITINFO_BILLID = "billId";
        public static final String RESULTMAP_DEPOSITINFO_AMOUNT = "amount";
        public static final String RESULTMAP_DEPOSITINFO_PUNISHAMOUNT = "punishAmount";
        public static final String RESULTMAP_DEPOSITINFO_BANKACCID = "bankAccId";
        public static final String RESULTMAP_DEPOSITINFO_REASON = "reason";
        public static final String RESULTMAP_DEPOSITINFO_AUTHCODE = "authCode";
        public static final String RESULTMAP_DEPOSITINFO_RESULT = "result";

        /////////////////////////////////////////////////////////////////////////
        public static final int ADJUST_RECORD_QUERY_DAY_SCOPE = 35;  //(银行接口)合作商调帐纪录的时间范围暂考虑限定为35天

    }

    interface CREDIT {

        public final static String GENCARD_IS_VOICE_Y = "Y"; //有效期催停语音催缴
        public final static String GENCARD_IS_VOICE_N = "N"; //有效期催停不进行语音催缴

        public final static int GENCARD_EXPIRE_URGE = 71; //有效期到期提醒
        public final static int GENCARD_EXPIRE_STOP = 78; //有效期到期停机

        public final static int GENCARD_CTRL_TYPE_ACCT = 0; //帐户
        public final static int GENCARD_CTRL_TYPE_USER = 1; //用户

        public final static String GENCARD_DEAL_INFO_FAIL = "FAIL"; //失败
        public final static String GENCARD_DEAL_INFO_SUCCESS = "SUCCESS"; //成功

        //特权报开大客户信息
        public final static String SPEC_OPEN_CUST_TYPE_MGR = "MGR"; //大客户-电话经理
        public final static String SPEC_OPEN_CUST_TYPE_VIP = "VIP"; //大客户-全球通VIP客户

        public final static int SPEC_OPEN_BUSI_TYPE_1 = 1; //特权报开-帐务复机
        public final static int SPEC_OPEN_BUSI_TYPE_2 = 2; //特权报开-呼出限制复机

        //信控处理方式
        public final static int PROCE_TYPE_URGE = 91; //催缴
        public final static int PROCE_TYPE_CALL_LIMIT = 6; //呼限
        public final static int PROCE_TYPE_STOP = 8; //停机

        //缴费停开机ACTION
        public static final int REALTIME_ACCT_SWITCH_WRITEOFF = 0; //销帐
        public static final int REALTIME_ACCT_SWITCH_CANCEL = 1; //返销帐
        public static final int REALTIME_ACCT_SWITCH_FREEZE = 2; //冻结
        public static final int REALTIME_ACCT_SWITCH_NORMAL = 3; //普通
        public static final int REALTIME_ACCT_SWITCH_BATCH = 4; //批量处理

        public final static String USER_OS_STATUS_OPEN = "0"; //用户停开位-开
        public final static String USER_OS_STATUS_STOP = "1"; //用户停开位-停
		
		/*
		 * 用户停开机字段配置
		 */

    }

    interface REV_FEE {

        public static final int REV_FEE_SOURCE_1 = 1; //BOSS前台
        public static final int REV_FEE_SOURCE_2 = 2; //客服

        public static final int REV_FEE_IS_DOUBLE_TRUE = 1; //双倍退费
        public static final int REV_FEE_IS_DOUBLE_FALSE = 0; //非双倍退费

        public static final int REV_FEE_MODULE_OTHER = 0; //退费模式-其它
        public static final int REV_FEE_MODULE_SP = 1; //退费模式-SP退费
        public static final int REV_FEE_MODULE_NONE_SP = 2; //退费模式-非SP退费

        public static final int REV_FEE_TYPE_DEFAULT = -1; //默认退费类型

        public static final int REV_FEE_REV_TYPE_CHARGE = 1; //退费金额退费方式-现金充值
        public static final int REV_FEE_REV_TYPE_CARD = 2; //退费金额退费方式-缴费卡赠送
        public static final int REV_FEE_REV_TYPE_CASH = 3; //退费金额退费方式-现金返还

        public static final int REV_FEE_AWARD_TYPE_CHARGE = 1; //奖励金额退费方式-现金充值
        public static final int REV_FEE_AWARD_TYPE_CARD = 2; //奖励金额退费方式-缴费卡赠送

        public static final int REV_FEE_REASON_ID_DEFAULT = -1; //默认退费原因类型

        public static final int REV_FEE_MONTYPE_DEFAULT = -1; //默认梦网业务类型

        public static final long REV_FEE_OTHER_ITEM_ID = 9999; //其它费用科目类型

    }

    /**
     * 营收稽核相关常量定义
     */
    interface AUDIT {
        public static final String AUDIT_BANKDATAFILE_PREFIX = "BQSWDZ";//银行下发对帐文件的前缀定义
        public static final String AUDIT_DATA_SEPARATOR = "|";//银行下发对帐文件中数据的分隔符号

        public static final String AUDIT_UPLOADFILE_PREFIX = "CQSWFF";//稽核之后，移动上传差异数据文件前缀
        public static final String AUDIT_UPLOADFILE_EXPAND = ".txt";//稽核之后，移动上传差异数据文件扩展名

        public static final String AUDIT_FILE_PATH_CODE = "ams_audit_file_path";//从静态表（bs_static_data）中获取值的key,用于获取银行下发文件路径。
        public static final long AUDIT_BUSINESSID = 715;//暂定值

        public static final int AUDIT_RECORD_NOTSUBMIT = 0;//标识缴款记录未提交
        public static final int AUDIT_RECORD_SUBMIT = 1;//标识缴款记录已提交


        public static final int AUDIT_POS_FLAG = 3;//缴款类别为POS显示时，对应的值

        public static final String AUDIT_DOWNLOAD_FTP_PATH_CODE = "DATA_AUDIT_DOWNLOAD";//用于从银行前置机下载缴款文件的FTP标识
        public static final String AUDIT_UPLOAD_FTP_PATH_CODE = "DATA_AUDIT_UPLOAD";//用于将稽核结果文件上传至银行前置机的FTP标识


        public static final String AUDIT_TASK_CENTERINFO = "AMBANKAUDITTASK_CENTERINFO";//后台任务的中心标识，通过该标识从静态数据表中获取中心的值


        /**
         * 中行对帐start
         */
        public static final String AUDIT_ZHDZ_DOWNLOAD_FTP_PATH_CODE = "ZHDZ_DOWNLOAD";//中行对帐文件下载的FTP标识
        public static final String AUDIT_ZHDZ_UPLOAD_FTP_PATH_CODE = "ZHDZ_UPLOAD";//中行对帐文件上传的FTP标识

        public static final String AUDIT_ZHDZ_FILEPREFIX = "TAS_BCDL_TRADE_DETAIL";//中行对帐文件前缀
        public static final String AUDIT_ZHDZ_FILETYPE = ".txt";//中行对帐文件文件类型


    }

    interface RES {
        public static final String RES_CHECK_BUSI_TYPE_PAY = "PAY";

        public static final String RES_ECARD_CHARGE_SUCCESS = "0";//神州行充值卡状态 0 正常


    }


    interface SEC {
        public static final long SEC_MONITOR_ROLE_ID = 2005;//营业班长角色标识
        public static final long SEC_WRITEOFF_PRIV_ID = 34; //预存款负调整权限点
        public static final long SEC_EPAY_WRITEOFF_PRIV_ID = 2557; //电子充值返销权限控制点
    }

    /**
     * 短信运营位常量
     */
    interface SMSEXPANSION {
        public static final String SMSINFO_DOWNLOAD_FTP_PATH_CODE = "SMSINFO_DOWNLOAD";//短信运营位数据文件FTP下载标识
        public static final String SMSINFO_UPLOAD_FTP_PATH_CODE = "SMSINFO_UPLOAD";//短信运营位数据文件FTP上传标识
        public static final String SMSINFO_FILE_ENCODING = "ISO-8859-15";//解析数据文件与校验文件的编码
        public static final String Iprefix = "i";//接口前缀
        public static final String I80101 = "VGOP2-SOP-80101";//用户群信息接口编码
        public static final String I80102 = "VGOP2-SOP-80102";//运营活动表接口编码
        public static final String I80103 = "VGOP2-SOP-80103";//资讯信息表接口编码
        public static final String I80104 = "SOP2-VGOP-80104";//短信下发清单同步接口编码
        public static final String dataExpand = ".dat";//数据文件扩展名
        public static final String verfExpand = ".verf";//验证文件扩展名
        public static final String separator = "_";//文件名之间的分隔符
        public static final String repeatNo = "00";//重传序号
        public static final String seqNo = "001";//序列号


        public static final String CHECKDATA_SEPARATOR = "\u0080";//实际值为\u0080,验证文件中，各项数据的分隔符 欧元符号 ISO-8859-15编码下欧元符号对应的unicode编码
        public static final String DATA_SEPARATOR = "\u0080";//验证文件中，各项数据的分隔符 欧元符号 ISO-8859-15编码下欧元符号对应的unicode编码

        public static final String AM_OP_USER_INFO_INDEX_NAME = "IDX_BILL_ID";//运营用户信息表索引名称
        public static final String SQL_LOADER_CTRL_LOCATION = "SQL_LOADER_CTRL_LOCATION";//从base.bs_static_date表中获取sql loader控制文件所在目录的关键字
        public static final String SQL_LOADER_LOG_LOCATION = "SQL_LOADER_LOG_LOCATION";//从base.bs_static_date表中获取sql loader日志文件所在目录的关键字
        public static final String SQL_LOADER_CTRL_EXPANDNAME = ".ctl";//sql loader控制文件扩展名称
        public static final String SQL_LOADER_LOG_EXPANDNAME = ".log";//sql loader控制文件扩展名称

        public static final String SMSEXPANSION_SQL_LOADER_DBCONFIG = "SMSEXPANSION_SQL_LOADER_DBCONFIG";//从base.bs_static_date表中获取sql loader数据库配置的KEY

        public static final String ERROR_FILE_EXPAND = ".err";//数据文件检验不通过的文件追加的扩展名
        public static final String CENTERINFO_KEY = "SMSEXPANSION_CENTERINFO1";//从静态数据表中获取中心信息的KEY
        public static final String CENTERINFO1_KEY = "SMSEXPANSION_CENTERINFO1";//从静态数据表中获取中心信息的KEY
        public static final String CENTERINFO2_KEY = "SMSEXPANSION_CENTERINFO2";//从静态数据表中获取中心信息的KEY
        public static final String CENTERINFO3_KEY = "SMSEXPANSION_CENTERINFO3";//从静态数据表中获取中心信息的KEY
        public static final String CENTERINFO4_KEY = "SMSEXPANSION_CENTERINFO4";//从静态数据表中获取中心信息的KEY
        public static final String USER_INFO_READNUM = "USER_INFO_READNUM";//从静态数据表中获取一次性从用户信息文件中读取记录条数的KEY
        public static final String EXPANSION_ACCESS = "1";//可访问短信运营位信息
        public static final String EXPANSION_NO_ACCESS = "0";//不可访问短信运营位信息
        public static final String SMS_PARAM_CODE = "SMS_EXPANSION";//用于标识运营位能否访问的ams_param表的主键
        public static final String SMS_PARAM_VALUE_ACCESS = "1";//标识当前能访问运营位信息
        public static final String SMS_PARAM_VALUE_NO_ACCESS = "0";//标识当前不能访问运营位信息

        /**
         * 短信参数名称和值分隔符
         */
        public static final String SMSPARAM_KEY_VALULE_SPLIT = "~";
        /**
         * 短信参数分隔符
         */
        public static final String SMSPARAM_SPLIT = "|";

    }

    /**
     * 短信业务编号businessId定义
     */
    interface SMS_BUSINESS_ID {
        public static final long BENEFIT4_BUSI_ID = 406300;//带4号码优惠
        public static final long SP_MONTH_FEE_BUSI_ID = 899;//SP短信帐单有sp消费
        public static final long SP_MONTH_NO_FEE_BUSI_ID = 999;//SP短信帐单无sp消费
        public static final long SMS_MONTH_FEE_BUSI_ID = 406100;//短信账单
    }

    /**
     * 短信模板编号定义
     */
    interface SMS_TEMPLETE {
        public static final long BENEFIT4 = 2010210000014L;//带4号码优惠短信
        public static final long SP_MONTH_FEE_1 = 2010210000012L;//SP短信帐单模板(有SP消费)
        public static final long SP_MONTH_FEE_2 = 2010210000013L;//SP短信帐单模板(无SP消费)
        public static final long SMS_MONTH_FEE = 2010210000011L;//短信账单模板

        public static final long AIR_BALANCE_PREPAY = 2010210000019L;//充值额度购买
        public static final long AIR_SPE_BALANCE_NOTIFY = 2010210000009L;//代理商网点通知其专用账户余额不足
        public static final long GROUP_FUND_ADJUST_NOTIFY = 2010210000010L;//集团资金划帐入帐提示
        public static final long BATCH_SMS_NOTIFY = 2010210000025L;//批量管控提醒短信模板

        public static final long AM_REVFEE_INPUT = 2010210000004L; //退费录入短信模板
        public static final long AM_BAL_TRANS_OUT = 2010210000005L; //帐户资金转移（转帐出）
        public static final long AM_BAL_TRANS_IN = 2010210000006L; //帐户资金转移（转帐入）
        public static final long AM_PARTNER_ADJ_OUT = 2010210000007L; //空充误充值调出短信
        public static final long AM_PARTNER_ADJ_IN = 2010210000008L; //空充误充值调入短信
        public static final long AM_BAL_VALIDATE_TEMPLATE_ID = 1345014280864L; //余额有效期短信模板
        public static final long AM_PARTER_ADJ_TEMPLATE_ID = 1345014308478L; // 充错调出短信模板
    }

    /**
     * 短信模板宏变量定义
     *
     * @author yanrg
     */
    interface SMS_PARAM {
        public static final String CHANNEL_TYPE = "CHANNEL_TYPE";//受理来源名称，如营业厅、10086、网上营业厅等
        public static final String TRADE_MARK = "TRADE_MARK";//品牌名称
        public static final String YEAR = "YEAR";//年
        public static final String MONTH = "MONTH";//月
        public static final String DAY = "DAY";//日
        public static final String MONEY_AMOUNT = "MONEY_AMOUNT";//金额：单位（元）
        public static final String BILL_ID = "BILL_ID";//服务号码
        public static final String BILLING_CYCLE_ID = "BILLING_CYCLE_ID";//账期
        public static final String BILL_CONTENT = "BILL_CONTENT";//短信账单话费内容
        public static final String INTEGRAL_CONTENT = "INTEGRAL_CONTENT";//短信账单积分内容
        public static final String BILL_TOTAL = "BILL_TOTAL";//账单总金额
        public static final String FEE_BILL = "FEE_BILL";//实际金额
        public static final String FEE_BENEFIT = "FEE_BENEFIT";//实际优惠金额
        public static final String CUR_BILL_TOTAL = "CUR_BILL_TOTAL";//当月话费
        public static final String BALANCE = "BALANCE";//余额
        public static final String HOUR = "HOUR";//小时
        public static final String MINUTES = "MINUTES"; // 分钟
        public static final String OWE_AMOUNT = "OWE_AMOUNT";//欠费总额
        public static final String EXPIRE_YEAR = "EXPIRE_YEAR";//失效年份
        public static final String EXPIRE_MONTH = "EXPIRE_MONTH";//失效月份
        public static final String EXPIRE_DAY = "EXPIRE_DAY";//失效日
        public static final String BANK_CODE = "BANK_CODE";//银行编码
        public static final String BANK_TRADE_START_DATE = "BANK_TRADE_START_DATE";//银行对账开始日期
        public static final String BANK_TRADE_END_DATE = "BANK_TRADE_END_DATE";//银行对账结束日期
        public static final String CHECK_RESULT = "CHECK_RESULT";//对账结果
        public static final String HINT_MESSAGE = "HINT_MESSAGE";//异常提示信息
        public static final String AWARD_POINTS = "AWARD_POINTS";//奖励积分
        public static final String TOTAL_POINTS = "TOTAL_POINTS";//总积分
        public static final String REGION_ID = "REGION_ID";//地市标识
        public static final String PREPAY_CONTENT = "PREPAY_CONTENT";//预缴后台统一短信
        public static final String PRE_BALANCE = "PRE_BALANCE";//代理商购买充值额度前余额
        public static final String SITH_BALANCE = "SITH_BALANCE";//代理商购买充值额度后新余额
        public static final String CHARGE_FEE = "CHARGE_FEE";//充值金额
        public static final String PRESENT_FEE = "PRESENT_FEE";//优惠赠送金额
        public static final String EXSMS = "EXSMS";//短信运营位
        public static final String ORDER_ID = "ORDER_ID";// 订单号

        /*-------------实扣对账----------------*/

        public static final String CURRENT_DATETIME = "CURRENT_DATETIME";//当前时间
        public static final String ACCOUNT_DATE = "ACCOUNT_DATE";//清算日期
        public static final String SIDECOUNT = "SIDECOUINT";//对端有，本端无
        public static final String SIDEAMOUNT = "SIDEAMOUNT";//对端有本端无金额
        public static final String SUCCCOUNT = "SUCCCOUNT";//成功笔数
        public static final String SUCCAMOUNT = "SUCCAMOUNT";//成功金额
        public static final String SELFCOUNT = "SELFCOUINT";//对端无，本端有
        public static final String SELFAMOUNT = "SELFAMOUNT";//对端无，本端有金额



    }

    /**
     * HTTP 框架配置
     *
     * @author yanrg
     */
    interface CFG_HTTP {
        public static final String CFG_HTTP_CLIENT_CODE_EPAY_QRY = "EPAY_QRY_CLIENT";//电子充值查询老系统三户资料
    }


    interface TASK_INFO {
        public static final String TASK_TYPE_CODE_AM_BANK_CHECK = "AM_BANK_CHECK"; //银行对帐
        public static final long TASK_PARAM_ID_1001 = 1001;//地市
        public static final long TASK_PARAM_ID_1002 = 1002;//银行代码
        public static final long TASK_PARAM_ID_1003 = 1003;//filePath文件存放目录
        public static final long TASK_PARAM_ID_1006 = 1006;//调帐模式
        public static final long TASK_PARAM_ID_1007 = 1007;//合作商代码序列
        public static final long TASK_PARAM_ID_1008 = 1008;//模数
        public static final long TASK_PARAM_ID_1009 = 1009;//模值
        public static final long TASK_PARAM_ID_1010 = 1010;//每月开始出帐日期，取值1-31
        public static final long TASK_PARAM_ID_1011 = 1011;//每月结束出帐日期，取值1-31
        public static final long TASK_PARAM_ID_1013 = 1013;//FTP编码,多个交易码之间用英文字母','分隔　
        public static final long TASK_PARAM_ID_1014 = 1014;//是否语音催缴

        //task.AmBankLogImportTask	special begin ,need 1013FTP代码
        public static final long TASK_PARAM_ID_1015 = 1015;//上次处理时间
        public static final long TASK_PARAM_ID_1016 = 1016;//日志文件分类
        public static final long TASK_PARAM_ID_1017 = 1017;//文件名模板
        //task.AmBankLogImportTask	special end
        //task.AmFeeEarlyAlertTask special begin,need 1001提醒地区
        public static final long TASK_PARAM_ID_1018 = 1018;//提醒分类
        //task.AmFeeEarlyAlertTask special end

        //task.AmFeeEarlyAlertTask special begin,need 1007	合作商代码序列,1013FTP编码
        //task.AmFeeEarlyAlertTask special end
        public static final long TASK_PARAM_ID_1037 = 1037;//提醒分类
        //task.AmBankCheckDetailTask special begin ,need 1001对帐地市
        public static final long TASK_PARAM_ID_1048 = 1048; //对帐业务ID
        public static final long TASK_PARAM_ID_1049 = 1049; //对帐服务类型
        public static final long TASK_PARAM_ID_1050 = 1050;    //对帐交易日期
        public static final long TASK_PARAM_ID_1051 = 1051;    //比对开始日期
        public static final long TASK_PARAM_ID_1052 = 1052; //比对结束日期
        public static final long TASK_PARAM_ID_1053 = 1053; //渠道编号(填银行编号)
        public static final long TASK_PARAM_ID_1054 = 1054; //对帐文件名
        public static final long TASK_PARAM_ID_1055 = 1055; //对帐总金额
        public static final long TASK_PARAM_ID_1057 = 1057; //对帐操作员登录工号
        public static final long TASK_PARAM_ID_1058 = 1058; //对帐操作员登录组织
        //task.AmBankCheckDetailTask special end

        //task.AmGenBankChargeRecTask special begin, need 1001地市
        public static final long TASK_PARAM_ID_1019 = 1019; //生成tf数据源
        public static final long TASK_PARAM_ID_1020 = 1020; //帐务到网厅银行卡交易数据同步
        public static final long TASK_PARAM_ID_1021 = 1021; //帐期
        public static final long TASK_PARAM_ID_1022 = 1022; //开始时间
        public static final long TASK_PARAM_ID_1023 = 1023; //结束时间
        public static final long TASK_PARAM_ID_1024 = 1024; //银行代码类型列表
        //task.AmGenBankChargeRecTask special end

        public static final long TASK_PARAM_ID_1059 = 1059; //充值记录标识 payment_id

        public static final long TASK_PARAM_ID_1025 = 1025; //业务类型
        public static final long TASK_PARAM_ID_1026 = 1026; //银行代码类型
        public static final long TASK_PARAM_ID_1027 = 1027; //支付类型
        public static final long TASK_PARAM_ID_1028 = 1028; //账单月
        public static final long TASK_PARAM_ID_1029 = 1029; //完成后手机号码
        public static final long TASK_PARAM_ID_1030 = 1030; //指定托收的账户列表
        public static final long TASK_PARAM_ID_1031 = 1031; //类型
        public static final long TASK_PARAM_ID_1032 = 1032; // 账户类型
        public static final long TASK_PARAM_ID_1060 = 1060; //文件名称
        public static final long TASK_PARAM_ID_1061 = 1061; //总金额
        public static final long TASK_PARAM_ID_1062 = 1062; //总笔数
        public static final long TASK_PARAM_ID_1063 = 1063; //流水号

        public static final long TASK_PARAM_ID_1065 = 1065; //异常提醒号码，多个号码之间用逗号间隔

        public static final long TASK_PARAM_ID_1066 = 1066; // 集团ftp
        public static final long TASK_PARAM_ID_1067 = 1067; // 本地ftp

        public static final long TASK_PARAM_ID_2008 = 2008; //银行交易类型列表,多个交易码之间用英文字母','分隔　

    }

    /**
     * 集团预缴帐户充值
     */
    interface GROUP_PREPAY {
        public static final int BOOK_TYPE = 2001;//预缴帐本类型
        public static final long BUSI_CODE = 514;//预缴业务代码
        public static final String BUSI_NAME = "营业预缴";//预缴业务代码对应的名称
        public static final String SEQ_NAME = "AM_GROUP_BUY_FILLDATA_REC$SEQ";//充值流水号使用的序列名称

        public static final long TRANSFER_BUSI_CODE = 516;//转充业务代码
        public static final String TRANSFER_BUSI_NAME = "帐户资金转移";//转充业务代码对应的名称


        public final String PRINCIPAL_ACCTYPE = "PRINCIPAL_ACCTYPE";//本金充值帐户类型
        public final String GIFT_ACCTYPE = "GIFT_ACCTYPE";//馈赠金充值帐户类型

        public final long PRINCIPAL_BUSI_CODE = 514;//本金充值业务类型
        public final long GIFT_BUSI_CODE = 515;//馈赠金充值业务类型
    }

    interface INTER {
        public static final String AM_SELF_TERMINAL_LIMITE_REGION = "AM_SELF_TERMINAL_LIMITE_REGION"; // 自助终端充值 银行地区限制
        public static final String BANK_WS = "WS"; // 温州银行
        public static final String BANK_ZX = "ZX"; // 杭州中信银行
        public static final String BANK_HG = "HG"; // 杭州光大银行
        public static final String BANK_N6 = "N6"; // 宁波建设银行
        public static final String BANK_CY = "CY"; // 村油站
        public static final String AM_BANK_60_REGION = "AM_BANK_60_REGION"; // 建行刷卡地区号与地市对应关系
        public static final String AM_BANK_50_REGION = "AM_BANK_50_REGION"; // 建行刷卡地区号与地市对应关系
        public static final String AM_BANK_40_REGION = "AM_BANK_40_REGION"; // 建行刷卡地区号与地市对应关系
        public static final String AM_BANK_30_REGION = "AM_BANK_30_REGION"; // 工行刷卡地区号与地市对应关系
    }

    /**
     * 银行POS刷卡银行编号
     */
    interface POS_BANK_TYPE {
        public static final String BANK_GH_30 = "102";
        public static final String BANK_NH_40 = "103";
        public static final String BANK_ZH_50 = "104";
        public static final String BANK_JH_60 = "105";

    }

    /**
     * 银行POS刷卡银行对账
     */
    interface POS_BANK_CHECK {
        public static final String CHECK_RESULT_11 = "11"; //平账
        public static final String CHECK_RESULT_22 = "22";  //银有移无
        public static final String CHECK_RESULT_33 = "33";  //银无移有
        public static final String CHECK_RESULT_14 = "14";  //刷卡金额一致，手续费银多移少
        public static final String CHECK_RESULT_15 = "15";  //刷卡金额一致，手续费移多银少
        public static final String CHECK_RESULT_41 = "41";  //手续费金额一致，刷卡金额银多移少
        public static final String CHECK_RESULT_51 = "51";  //手续费金额一致，刷卡金额移多银少
        public static final String CHECK_RESULT_45 = "45";  //刷卡金额银多移少，手续费移多银少
        public static final String CHECK_RESULT_54 = "54";  //刷卡金额移多银少，手续费银多移少
        public static final String CHECK_RESULT_44 = "44";  //刷卡金额银多移少，手续费银多移少
        public static final String CHECK_RESULT_55 = "55";  //刷卡金额移多银少，手续费移多银少
    }

    /**
     * 银行POS刷卡银行交易类型
     */
    interface POS_TRADE_TYPE {
        public static final String POS_CASH = "1";
        public static final String POS_PERIODS = "2";

    }

    /**
     * 业务处理返回值常量
     */
    interface BUSI_RETURN_TYPE {
        public static final String JTHZ_REASON = "帐本余额已不足";
        public static final String JTHZ_REASON2 = "金实际需要划出金额";

    }

    /**
     * 全网改号业务编码常量
     */
    interface NET_TRANSFER_TYPE {
        public static final String TRANSFER_IN = "20120001";
        public static final String TRANSFER_OUT = "20120002";
    }

    interface ENTRUST_BUSI_REC_DTL {
        /**
         * AM_ENTRUST_BUSI_REC_DTL 的记录为发起失败的记录
         */
        public static final int STATE_0 = 0;
        /**
         * AM_ENTRUST_BUSI_REC_DTL 的记录为发起成功的记录
         */
        public static final int STATE_1 = 1;
        /**
         * 非地市银行  AM_ENTRUST_BUSI_REC_DTL 的记录为发起成功后且银行返回扣款成功的记录
         */
        public static final int STATE_2 = 2;
        /**
         * AM_ENTRUST_BUSI_REC_DTL 的记录为发起成功后但银行返回扣款失败等原因需要返销的记录
         */
        public static final int STATE_3 = 3;
        /**
         * 地市银行  AM_ENTRUST_BUSI_REC_DTL 的记录为发起成功后且银行返回扣款成功的记录
         * 经分统计这个状态的数据,包括非联网托收、批扣、一卡通
         */
        public static final int STATE_4 = 4;
    }

    interface PAY {
        /**
         * 支付平台
         */
        public static final long PAY_OP_SEQ = 1;
        public static final String PAY_CHARGE_TYPE = "8";
        public static final String PAY_PREPAY = "P";
        public static final String PAY_TRUEPAY = "T";
        public static final String PAY_BACKPAY = "B";
        public static final String PAY_OUT = "O";
        public static final String PAY_GET = "G";
        public static final String PREPAY_STATE = "1";
        public static final String TRUEPAY_STATE = "2";
        public static final String BACKPAY_STATE = "3";
        public static final String NOMAL_STATE = "1";
    }


    interface PAYMENTID {
        /**
         * 流水号相关
         */
        public static final int PYAMENT_SOURCE_CASH = 1;//资金
        public static final int PYAMENT_SOURCE_COST = 2;//消费
        public static final int PYAMENT_STAGE_FRONT = 1;//前台
        public static final int PYAMENT_STAGE_BACK = 2;//后台
    }

    interface LOGSTAGE {
        /**
         * 日志阶段
         */
        public static final int STAGE_1 = 1;
        public static final int STAGE_2 = 2;
        public static final int STAGE_3 = 3;
        public static final int STAGE_4 = 4;
        public static final int STAGE_5 = 5;
        public static final int STAGE_6 = 6;
        public static final int STAGE_7 = 7;
        public static final int STAGE_8 = 8;
        public static final int STAGE_9 = 9;
        public static final int STAGE_10 = 10;
        public static final int STAGE_11 = 11;
    }

    interface SOCKETTYPE {//edit by guowx 20130705
        /**
         * 调用SOCKET服务类型
         */
        public static final String PAY_SEARCH = "PAY_SEARCH";//
        public static final String PAY_BALANCE = "PAY_BALANCE";
        public static final String WRITE_OFF = "WRITE_OFF";


        public static final String PAY_SEARCH_BIG = "PAY_SEARCH_BIG";
        public static final String PAY_BALANCE_BIG = "PAY_BALANCE_BIG";
        public static final String WRITE_OFF_BIG = "WRITE_OFF_BIG";//1

        public static final String PAY_SEARCH_SMALL = "PAY_SEARCH_SMALL";
        public static final String PAY_BALANCE_SMALL = "PAY_BALANCE_SMALL";
        public static final String WRITE_OFF_SMALL = "WRITE_OFF_SMALL";//2
    }

    //----------------------营改增------------------------------------start
    public static final String VAT_BUSI_TYPE = "VAT_INVOICE_SERVICE";//业务类型code
    public static final String VAT_BUSI_PARAM_KEY_ORD_ID = "ordId";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_ORD_CODE = "orderCode";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_ORD_CUST_ID = "ordCustId";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_BUSI_TYPE = "BUSI_TYPE";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_RES_TYPE = "resType";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_TAX_FEE = "txtFee";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_ITEM_NAME = "printItem";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_ITEM_PRICE = "feeSum";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_USER_ID = "userId";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_CUST_ID = "custId";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_ACCT_ID = "acctId";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_ACCT_NAME = "acctName";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_BILL_ID = "billId";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_CUST_NAME = "custName";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_QUERY_TYPE = "qryType";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_QUERY_VALUE = "qryValue";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_RECEIVE_FEE_ID = "amIndepFeeId";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_INDEP_FEE_ID = "RECEIVE_FEE_ID";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_BILLING_CYCLE = "billingCycleId";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_PRINT_TYPE = "printType";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_SELF_PRINT_TYPE = "selPrintType";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_CONTRACT_NO = "contractNo";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_DISCOUNT_FEE = "discountFee";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_PRESENT_FEE = "presentFee";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_CARED_PAY = "cardPayedFee";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_BILL_TOTAL = "billTotal";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_TYPE_FLAG = "typeFlag";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_ENTRUST_FEE = "entrustFee";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_GROUP_NAME = "groupName";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_REDUCE_AMOUNT = "reduceAmount";
    public static final String VAT_BUSI_PARAM_KEY_LIMIT_AMOUNT = "limitPrintAmount";
    public static final String VAT_BUSI_PARAM_KEY_INVOICE_XML = "INVOICE_XML";//发票格式
    public static final String VAT_BUSI_PARAM_KEY_INVOICE_TEMPLATE = "INVOICE_TEMPLATE";//发票模板
    public static final String VAT_BUSI_PARAM_KEY_NOTES = "notes";//发票模板
    public static final String VAT_BUSI_PARAM_KEY_OLD_BOOK_ID = "OLD_BOOK_ID";//发票模板
    public static final String VAT_BUSI_PARAM_KEY_OLD_RES_ID = "OLD_RES_ID";//发票模板
    public static final String VAT_BUSI_PARAM_KEY_PAY_AMOUNT = "payAmountCent";
    public static final String VAT_BUSI_PARAM_KEY_PAY_MENNTID = "paymentId";
    public static final String VAT_BUSI_PARAM_KEY_IS_PREVIEW = "IS_FOR_PREVIEW";
    public static final String VAT_BUSI_PARAM_KEY_PRINT_ITEM = "printId";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_INVOICE_NUM = "invoiceNum";//业务参数key
    public static final String VAT_BUSI_PARAM_KEY_TER_IMEI = "imei";//业务参数key

    public static final String VAT_BUSI_FEE_TYPE = "feeType";
    public static final String LOCAL_INVOICE_RES_ID = "4000";//地税发票资源编号
    public static final String LOCAL_PBOSS_INVOICE_RES_ID = "8000";
    public static final String INVOICE_RES_ID = "4002";//国税发票资源编号
    public static final String PBOSS_INVOICE_RES_ID = "8004";
    public static final String RECEIPT_RES_ID = "6000";//收据资源编号
    public static final String INVOICE_SELECT_TYPE_ALL = "1";//全部
    public static final String INVOICE_SELECT_TYPE_MOBILE = "2";//移动
    public static final String INVOICE_SELECT_TYPE_PBOSS = "3";//铁通
    public static final String VAT_SO_INDEP_FEE_NAME = "押金收据";
    public static final String VAT_SO_RECEIVE_FEE_NAME = "业务发票";
    public static final String VAT_SO_PREPAY_FEE_NAME = "预缴收据";
    public static final String VAT_SPECIAL_INVOICE_RES_ID = "4003";//专票
    public static final String VAT_OLD_Receipt_RES_ID = "2896536";//老模板资源类型
    /**
     * 发票业务类型 ：
     * 1、充值发票 2、月结发票 3、自定义发票 4、押金发票 5: 报损帐单缴费发票 6: 托收发票 7. 一次性费用 8. 分摊发票
     * 9, 代扣发票
     */
    public static final String VAT_BUSI_TYPE_PAY = "1";


    public static final String VAT_BUSI_TYPE_INDEP_FEE = "4";
    public static final String VAT_BUSI_TYPE_SO = "1";//个人营业受理：一次性费用，押金收据，预存收据

    public static final String VAT_BUSI_TYPE_SO_ALL = "0";//预缴和一次性费用立即打印
    public static final String VAT_BUSI_TYPE_SO_RECEIVE = "1";
    public static final String VAT_BUSI_TYPE_SO_INDEP = "2";
    public static final String VAT_BUSI_TYPE_SO_PREPAY = "3";

    public static final String VAT_BUSI_TYPE_BILL = "2";//账单
    public static final String VAT_BUSI_TYPE_BILL_FREE = "1";//账单发票费用
    public static final String VAT_BUSI_TYPE_BILL_SP = "3";//0税率专票账单需要打通票

    public static final String VAT_BUSI_TYPE_GREEN = "3";//专线产品：初装费，一次性，扩容，移机
    public static final String VAT_BUSI_TYPE_GREEN_INSTALL = "1";
    public static final String VAT_BUSI_TYPE_GREEN_RECEIVE = "2";
    public static final String VAT_BUSI_TYPE_GREEN_EXPAND = "3";
    public static final String VAT_BUSI_TYPE_GREEN_REMOVE = "4";


    public static final String VAT_BUSI_TYPE_MERGE = "4";//融合类：包时长，互联网服务，一次性费用，押金
    public static final String VAT_BUSI_TYPE_MERGE_TIME_PERSISTENCE = "1";
    public static final String VAT_BUSI_TYPE_MERGE_TIME_HLW = "2";
    public static final String VAT_BUSI_TYPE_MERGE_TIME_RECEIVE = "3";
    public static final String VAT_BUSI_TYPE_MERGE_TIME_INDEP = "4";
    public static final String VAT_BUSI_TYPE_MERGE_TIME_PREPAY = "5";


    public static final String VAT_BUSI_TYPE_MOBILE_PBOSS = "5";//移动宽带:包时长，互联网服务，一次性费用
    public static final String VAT_BUSI_TYPE_MOBILE_PBOSS_TIME_PERSISTENCE = "1";
    public static final String VAT_BUSI_TYPE_MOBILE_PBOSS_HLW = "2";
    public static final String VAT_BUSI_TYPE_MOBILE_PBOSS_RECEIVE = "3";
    public static final String VAT_BUSI_TYPE_MOBILE_PBOSS_INDEP = "4";
    public static final String VAT_BUSI_TYPE_MOBILE_PBOSS_PREPAY = "5";

    public static final String VAT_BUSI_TYPE_HLW = "6";//互联网 ：互联网服务，一次性费用
    public static final String VAT_BUSI_TYPE_HLW_SERVICE = "1";
    public static final String VAT_BUSI_TYPE_HLW_RECEIVE = "2";

    public static final String VAT_BUSI_TYPE_MOBILE = "7";//MOBILE ：一次性费用,押金
    public static final String VAT_BUSI_TYPE_MOBILE_RECEIVE = "1";
    public static final String VAT_BUSI_TYPE_MOBILE_INDEP = "2";

    public static final String VAT_BUSI_TYPE_GW = "8";//固网 ：一次性费用
    public static final String VAT_BUSI_TYPE_GW_RECEIVE = "1";
    public static final String VAT_BUSI_TYPE_GW_INDEP = "2";

    public static final String VAT_BUSI_TYPE_BBS = "9";//宽带加速：一次性费用
    public static final String VAT_BUSI_TYPE_BBS_RECEIVE = "1";

    //	public static final String VAT_BUSI_TYPE_SELF = "10";//自定义：个人自定义，政企自定义通票，专票
    public static final String VAT_BUSI_TYPE_SELF = "10";//自定义：个人自定义 MODIFY BY AMOL 20140716 个人自定义/政企自定义发票打印区分
    public static final String VAT_BUSI_TYPE_SELF_NEW = "45";//自定义：个人自定义通票
    public static final String VAT_BUSI_TYPE_POLITY = "46";//自定义：政企自定义通票
    public static final String VAT_BUSI_TYPE_SELF_CUSTOMER = "1";
    public static final String VAT_BUSI_TYPE_SELF_POLITY = "2";
    public static final String VAT_BUSI_TYPE_SELF_POLITY_SP = "3";


    public static final String VAT_BUSI_TYPE_LOSS = "11";//报损
    public static final String VAT_BUSI_TYPE_LOSS_FEE = "1";//报损费用

    public static final String VAT_BUSI_TYPE_CHARGE = "12";//充值
    public static final String VAT_BUSI_TYPE_CHARGE_FEE = "1";//充值
    public static final String VAT_BUSI_TYPE_CHARGE_CBOSS_FEE = "3";//充值

    public static final String VAT_BUSI_TYPE_ENTRUST = "13";//托收
    public static final String VAT_BUSI_TYPE_ENTRUST_FEE = "1";//托收


    public static final String VAT_BUSI_TYPE_SO_REPRINT = "14";//一次性补打
    public static final String VAT_BUSI_TYPE_SO_RECEIVE_REPRINT = "1";//一次性补打
    public static final String VAT_BUSI_TYPE_SO_PREPAY_REPRINT = "3";

    public static final String VAT_BUSI_TYPE_ROAM = "15";//日美韩漫游
    public static final String VAT_BUSI_TYPE_ROAM_INDEP = "1";//日美韩漫游押金
    public static final String VAT_BUSI_TYPE_ROAM_RECEIVE = "2";//日美韩漫游一次性

    public static final String VAT_BUSI_TYPE_FIXED = "16";//固网类
    public static final String VAT_BUSI_TYPE_FIXED_INDEP = "1";//固网类押金


    public static final String VAT_BUSI_TYPE_GROUP_ONCE = "17";//集团一次性
    public static final String VAT_BUSI_TYPE_GROUP_ONCE_VAT = "1";//集团一次性通票
    public static final String VAT_BUSI_TYPE_GROUP_ONCE_SP = "2";//集团一次性专票
    public static final String CM_ONCEINCOME_PLAN_TYPE = "CM_ONCEINCOME_PLAN_TYPE";
    public static final String CM_ONCEINCOME_PAY_TYPE = "CM_ONCEINCOME_PAY_TYPE";

    public static final String VAT_BUSI_TYPE_TERMINAL = "18";//终端
    public static final String VAT_BUSI_TYPE_TERMINAL_VAT = "1";//终端

    public static final String VAT_BUSI_TYPE_RESBUSICARD = "19";//新业务卡销售

    public static final String VAT_BUSI_INVOICE = "INVOICE";//日美韩漫游一次性

    //public static final String VAT_BUSI_TYPE_ENTRUCT = "6";
    public static final String VAT_BUSI_TYPE_RECEIVE = "7";
    public static final String SEPARATOR = "_";
    /**
     * 发票资源类型 ：
     * 0、通用机打发票 1、专票 2、收据
     */
    public static final int VAT_RES_TYPE_GENERAL = 0;
    public static final int VAT_RES_TYPE_SPECIAL = 1;
    public static final int VAT_RES_TYPE_RECEIPT = 2;

    public static final String VAT_INVOICE_BUSI_CANCEL = "0";//专票作废状态
    public static final String VAT_INVOICE_BUSI_RED = "1";//专票冲红状态

    /**
     * 发票状态 ：
     * 0 未开具1已开具 2已作废3已冲红4专票申请未处理5专票申请处理中6专票申请取消7专票申请处理失败
     */
    public static final String VAT_STATE_NOPEN = "0";//未开具
    public static final String VAT_STATE_ISSUED = "1";//已开具
    public static final String VAT_STATE_INVALID = "2";//已作废
    public static final String VAT_STATE_RED = "3";//已冲红
    public static final String VAT_STATE_APPLY_NOPROCESS = "4";//专票申请未处理
    public static final String VAT_STATE_APPLY_PROCESS = "5";//专票申请处理中
    public static final String VAT_STATE_APPLY_CANCER = "6";//专票申请取消
    public static final String VAT_STATE_APPLY_FAILED_PROCESS = "7";//专票申请失败
    public static final String VAT_STATE_APPLY_NOT_APPROVED = "8";//专票审核不通过
    public static final String VAT_CAPITALFEE_NAME = "（除本金）";
    public static final String VAT_PROMOTION_FEE_NAME = "活动金额";
    public static final String VAT_FLAG = "FLAG";
    public static final String VAT_NOTE = "NOTE";
    public static final String VAT_ERR_MESSAGE = "MESSAGE";
    public static final Long VAT_TERMINAL_FEE = 6000101L;
    public static final Long VAT_PROMOTION_FEE = 6000102L;
    public static final Long VAT_CAPITAL_FEE = 6000103L;
    public static final Long VAT_TOTAL_FEE = 6000104L;

    /**
     * 专票申请类型：
     * 1、专票开具申请 2、专票作废申请 3、专票冲红申请
     */
    public static final String VAT_TYPE_ISSUED_APPLICATION = "1";//专票开具申请
    public static final String VAT_TYPE_INVALID_APPLICATION = "2";//专票作废申请
    public static final String VAT_TYPE_RED_APPLICATION = "3";//专票冲红申请


    /**
     * 对账单科目
     */
    public static final long VAT_ACC_ITEM_ID = 303;//个人对账单实际消费金额
    public static final long VAT_ACC_JT_ITEM_ID = 635;
    public static final String VAT_ACC_ITEM_NAME = "语音通信费";

    /**
     * 发票审批状态 ：
     * 0未进入流程1待上级主管审核2待开票专员审核3待发送金税4待金税系统反馈结果99审核通过98金税反馈失败-99审核不通过
     */
    public static final String VAT_APPROVE_NONE = "0";
    public static final String VAT_APPROVE_DIRECTOR_APPROVE = "1";
    public static final String VAT_APPROVE_SPEC_APPROVE = "2";
    public static final String VAT_APPROVE_WAIT_SEND = "3";
    public static final String VAT_APPROVE_WAIT_FEDBACK = "4";
    public static final String VAT_APPROVE_APPROVE = "99";
    public static final String VAT_APPROVE_FEDBACK_FAIL = "98";
    public static final String VAT_APPROVE_REJECT = "-99";

    /**
     * 专票开具审核结果 1 -- 待审核 2 -- 审核通过 3 -- 审核不通过  4 -- 开票
     */
    public static final int VAT_PENDING_AUDIT = 1;
    public static final int VAT_APPROVED_AUDIT = 2;
    public static final int VAT_DISAPPROVED_AUDIT = 3;
    public static final int VAT_INVOICE = 4;

    /**
     * 一般纳税客户类型 1 个人 2 企业
     */
    public static final int VAT_TAXPAYER_CUST_TYPE_INDEV = 1;
    public static final int VAT_TAXPAYER_CUST_TYPE_GROUP = 2;

    //-----------------------营改增----------------------------------------end
    /**
     * 常用的常量定义
     */
    public static final String ZERO_STR = "0";

    /**
     * 账单专票打印 电信服务费税率
     */
    public static final int VAT_SP_INVOICE_BASIC_TAX_RATE = 11;//基础电信服务费税率
    public static final int VAT_SP_INVOICE_ACCRUED_TAX_RATE = 6;//增值电信服务费税率
    public static final int VAT_SP_INVOICE_OTHER_TAX_RATE = 0;//电信服务费0税率
    public static final int VAT_SP_INVOICE_EQUIPMENT_SALES_TAX_RATE = 17;//设备销售税率


    //-----------------------营改增----------------------------------------start

    /**
     * 发票资金池常量
     *
     * @author guowx
     * @version v1.0.0
     * @Copyright Copyright (c) 2014 Asiainfo-Linkage
     * <br>
     * Modification History:<br>
     * Date         Author          Version            Description<br>
     * ---------------------------------------------------------*<br>
     * 2014-1-10     guowx           v1.0.0               修改原因<br>
     */
    interface INVOICE_POOL {//add by guowx 20140110
        /**
         * 更改资金池的常量类型
         */
        public static final String RETURN_BALANCE = "RETURN_BALANCE";//退预存 ---减
        public static final String PRINT_INVOICE = "PRINT_INVOICE";//打印发票 ---减
        public static final String INVOICE_CANCEL = "INVOICE_CANCEL";//发票作废 ---增
        public static final String WRITE_OFF_BACK = "WRITE_OFF_BACK";//充值返销 ---减
        public static final String PAY_BALANCE = "PAY_BALANCE";//充值 ---增
        public static final String TRANS_BALANCE = "TRANS_BALANCE";//资金转移 ---增减
        public static final String PREPAY = "PREPAY";//预缴 ---增
        public static final String PREPAY_CANCEL = "PREPAY_CANCEL";//预缴返撤终止 ---减
        public static final String ADJUST_ADD = "ADJUST_ADD";//手工调增 ---增
        public static final String ADJUST_REDUCE = "ADJUST_REDUCE";//手工调减 ---减

        /**
         * 更改资金池的数据库存储常量值
         */
        public static final long RETURN_BALANCE_L = 3;//退预存 ---减
        public static final long PRINT_INVOICE_L = 2;//打印发票 ---减
        public static final long INVOICE_CANCEL_L = 2;//发票作废 ---增
        public static final long WRITE_OFF_BACK_L = 1;//充值返销 ---减
        public static final long PAY_BALANCE_L = 1;//充值 ---增
        public static final long TRANS_BALANCE_L = 4;//资金转移 ---增减
        public static final long PREPAY_L = 5;//预缴 ---增
        public static final long PREPAY_CANCEL_L = 5;//预缴返撤终止 ---减
        public static final long ADJUST_ADD_L = 6;//手工调增 ---增
        public static final long ADJUST_REDUCE_L = 6;//手工调减 ---减

        public static final String IS_EFFECTIVED = "IS_EFFECTIVED";//是否有打印资格
        public static final String LIMIT_PRINT_AMOUNT = "PRINT_AMOUNT";//可打印金额
        public static final String USED_PRINT_AMOUNT = "USED_PRINT_AMOUNT";//已打印金额

    }

    /**
     * 专票使用常量
     *
     * @author guowx
     */
    interface SPECIAL_INVOICE {//add by guowx 20140520
        /**
         * 组合常量
         */
        public static final String NO_PRINT = "1";//未开具
        public static final String PRINT_NO_TRANS = "2";//已开具未传递
        public static final String PRINT_TRANSING = "3";//已开具传递中
        public static final String PRINT_TRANS = "4";//已开具已传递
        public static final String RED_NOT_DO = "5";//冲红申请未处理
        public static final String RED_MODIFY_YES = "6";//冲红审核通过
        public static final String RED_MODIFY_NO = "7";//冲红审核未通过
        public static final String PRINT_MODIFY_NO = "8";//专票审核不通过
        public static final String PRINT_CANCEL = "9"; //专票申请取消

        /**
         * 专票状态
         */
        public static final String WAIT_PRINT = "0";//未开具
        public static final String PRINT = "1";//已开具
        public static final String CANCEL = "2";//已作废
        public static final String RED = "3";//已冲红
        public static final String APPLY_WAIT = "4";//专票申请未处理
        public static final String APPLY_DEALING = "5";//专票申请处理中
        public static final String APPLY_CANCELING = "6";//专票申请取消
        public static final String APPLY_DEAL_ERROR = "7";//专票申请处理失败
        public static final String APPLY_NOT_APPROVED = "8";//专票审核不通过
        /**
         * 申请类型
         */
        public static final String APPLY_PRINT = "1";//开具
        public static final String APPLY_CANCEL = "2";//作废
        public static final String APPLY_RED = "3";//冲红
        /**
         * 传递状态
         */
        public static final String NO_TRANS = "0";//未传递
        public static final String TRANSING = "1";//传递中
        public static final String TRANS = "2";//已传递
    }

    // 通票资源状态
    interface RES_COMMON_INVOICE {

        // 发票资源作废
        public static final String RES_INVOICE_ZF = "2";

        // 发票资源遗失
        public static final String RES_INVOICE_YS = "3";


    }
    //-----------------------营改增----------------------------------------end

    //实扣类常量
    interface REAL_PAY {
        public static final String COMM_ACCT_PAY_TYPE = "1";   //通信账户支付
        public static final String THIRD_PARTY_PAY_TYPE = "2"; //第三方支付

        public static final int BALANCE_CODE_TYPE = 0; //本金

        public static final String IS_PRINTINVOICE = "1";   //已经打印发票.
        public static final String NOT_PRINTINVOICE = "0";   //没有打印发票.

    }

    interface REAL_PAY_BUSINESSID {
        public static final long DRIBLET_BUSINESSID = 11L;   //小额支付
        public static final long WlAN_CARD_BUSINESSID = 22; //WLAN电子卡

        public static final long REALPAY_ADDFLOW = 651;   //流量加油包购买
        public static final long REALPAY_REVERT_ADDFLOW = 652; //流量加油包购买撤单

        public static final long REALPAY_LITTLEPAY = 101;   //小额支付
        public static final long REALPAY_REVERT_LITTLEPAY = 901; //小额支付撤单

        public static final long REALPAY_WLANCARD = 102;   //购买WLAN电子卡
        public static final long REALPAY_REVERT_WLANCARD = 902; //购买WLAN电子卡撤单

        public static final long REALPAY_BOARDLAND = 103;   //宽带续包
        public static final long REALPAY_REVERT_BOARDLAND = 903; //宽带续包撤单

        public static final long REALPAY_JINHUA = 104;   //金华营销活动办理
        public static final long REALPAY_REVERT_JINHUA = 904; //金华营销活动办理撤单
        
        public static final String  AIR_REALPAY_BIZCODE ="T1,T0,90";//空中充值的三个商家编号

        /**
         * 实扣对账结果，手机短信通知
         */
        public static final String PARA_TYPE_AM_REALPAY_CHECK_RESULT_NOTIFY = "AM_REALPAY_CHECK_RESULT_NOTIFY";

    }
    
    interface TRANS_STATUS
	{
		public static final int BUSINESS_SEND_1 = 1; //发送中
		public static final int BUSINESS_SEND_2 = 2; //送扣中
		public static final int BUSINESS_SEND_4 = 4; //返回成功
	}

    interface PREPAY{
        //进程互斥类型(-1:默认,0:分摊,1:充值,2:送促销)  兼容老系统  默认-1  所有-1 必须加上
        public static final String PREPAY_STEPS_DEFAULT = "-1";
        public static final String PREPAY_STEPS_APPORTION = "0";
        public static final String PREPAY_STEPS_NOTPROMOTION = "1";
        public static final String PREPAY_STEPS_PROMOTION = "2";
    }
}
