package com.asiainfo.batchwriteoff.util;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.common.util.ExceptionUtil;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBillingCycleValue;
import com.asiainfo.batchwriteoff.service.interfaces.IBWFCommonBillingSV;

/**
 * <p> @Description </p>
 * <p> @company Asiainfo-linkage (NJ) </p>
 * <p> @author 闫瑞国 </p>
 * <p> @Email yanrg@asiainfo-linkage.com </p>
 * <p> @createDate 2012-7-7 下午3:33:10 </p>
 * <p> @modifyDate </p>
 * <p> @version 1.0 </p>
 */
public class BWFBillingCycleUtil {

	public static IBOBsBillingCycleValue getCurBillingCycle(String regionId,long billDay)throws Exception{
		IBWFCommonBillingSV commonSV = (IBWFCommonBillingSV) ServiceFactory.getService(IBWFCommonBillingSV.class);

		IBOBsBillingCycleValue[] billingCycles = commonSV.getBillingCycle(regionId, billDay, "2", 0);

		if(billingCycles==null || billingCycles.length<1){
			ExceptionUtil.throwBusinessException("AMS0700478", regionId, "2", Long.toString(billDay));
		}else if(billingCycles.length>1){
			ExceptionUtil.throwBusinessException("AMS0700479", regionId, "2", Long.toString(billDay));
		}

		return billingCycles[0];
	}












}
