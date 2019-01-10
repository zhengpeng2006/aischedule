package com.asiainfo.batchwriteoff.util;


/**
 * <p> @Description </p>
 * <p> @company Asiainfo-linkage (NJ) </p>
 * <p> @author 闫瑞国 </p>
 * <p> @Email yanrg@asiainfo-linkage.com </p>
 * <p> @createDate Dec 10, 2010 3:08:58 PM </p>
 * <p> @modifyDate </p>
 * <p> @version 1.0 </p>
 */
public interface AmBatchConst {
	
	

	interface COMMON {

		/* 帐管优化项目20140902孙奇start */
		/** 换表时批销进程通知入账本TF */
		public static final String PS_PAYMENT_ACTION_R = "R";
		/** 批销时通知第二步工单停止实时销账  */
		public static final String PS_PAYMENT_ACTION_W = "W";
		/* 帐管优化项目20140902孙奇start */

	}
	
	interface returnValue{
		
		// 返回MAP中账户账本资金KEY
		final static String rtnAcctBookKey = "RTNACCTBOOKKEY";

		// 返回MAP中账户账单KEY
		final static String rtnAcctBillKey = "RTNACCTBILLKEY";

		// 返回MAP中销账记录KEY
		final static String rtnAcctRelKey = "RTNACCTRELKEY";

		// 返回MAP中账户实际信控可用余额KEY
		 final static String rtnRealBalKey = "RTNREALBALKEY";

		// 返回MAP中有效期失效标识KEY
		 final static String rtnBFlagKey = "RTNBFLAGKEY";

		//
		 final static String rtnSpeUserKey = "RTNSPEUSERKEY";

		//
		 final static String rtnBookIdKey = "RTNBOOKIDKEY";

		//
		 final static String rtnItemIdKey = "RTNITEMIDKEY";
		
		// ERROR信息KEY
		 final static String rtnErrorKey = "RTNERRORKEY";
	}

	interface SMS_PARAM {

		/* 帐管优化项目20140902孙奇start */
		/** 通用 */
		public static final String AMS_SMS = "AMS_SMS";
		/* 帐管优化项目20140902孙奇start */
	}
	interface SMS_TEMPLETE
	{
		/*帐管优化项目20140902孙奇start*/
		
		/** 账管公用短信 */
		public static final long AM_COMMON = 19000001L;
		
		/*帐管优化项目20140902孙奇start*/
	}
}
