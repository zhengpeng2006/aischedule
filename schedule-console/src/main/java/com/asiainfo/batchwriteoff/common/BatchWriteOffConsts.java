package com.asiainfo.batchwriteoff.common;


public interface BatchWriteOffConsts {
   
	public interface Common{
		/**调度进程 脚本 前缀*/
		public final static String  SCHEDULE_PREFIX="BWF_PROCESS_MANAGE_SCHEDULE_";
		
		/**
		   U 可以执行
		   C  执行中
		   F  正常完成
		   X  异常结束
		   H  人工干预模式下，稽核完成后置状态   等待人工前台修改为 Y
		   Y  确认完成 自动模式下稽核完成后，置Y状态
		   N  确实失败
		 **/
		
		public final static String FLOW_MON_STATE_U="U";
		
		public final static String FLOW_MON_STATE_C="C";
		
		public final static String FLOW_MON_STATE_F="F";
		
		public final static String FLOW_MON_STATE_X="X";
		
		public final static String FLOW_MON_STATE_H="H";
		
		public final static String FLOW_MON_STATE_Y="Y";
		
		public final static String FLOW_MON_STATE_N="N";
		
		/**全省代码*/
		public final static String ALL_PROVINCE_CODE="X";
		/**批销流程配置参数信息*/
		public final static String PARA_TYPE_BATCH_WIRTE_OFF_CFG="AM_BATCH_WIRTE_OFF_CFG";
		/**批销流程配置参数信息 开始结点*/
		public final static String PARA_CODE_START="START";
		/**批销流程配置参数信息 结束结点*/
		public final static String PARA_CODE_END="END";
		/**批销流程配置参数信息  每一行的长度*/
		public final static String PARA_CODE_LENGTH="LENGTH";
		/**流程结点  有明细  Y*/
		public final static String FLOW_NODE_IS_DTL_Y="Y";
		/**流程结点  没有明细  N*/
		public final static String FLOW_NODE_IS_DTL_N="N";

        //默认地市
		public final static String BWF_DEFAULT_REGION_ID="571";
		
		public final static String BWF_PASSWORD_REGION_ID = "571";
	}
}
