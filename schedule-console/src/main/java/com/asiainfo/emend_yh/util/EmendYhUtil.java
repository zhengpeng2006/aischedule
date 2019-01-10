package com.asiainfo.emend_yh.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBillingCycleValue;
import com.asiainfo.batchwriteoff.util.BWFBillingCycleUtil;
import com.asiainfo.emend_yh.ivalues.IBOAmSchedPsMonValue;
import com.asiainfo.emend_yh.ivalues.IBOAmTaskRelyValue;
import com.asiainfo.emend_yh.service.interfaces.IEmendYhCommonSV;

public class EmendYhUtil {

	public static void main(String[] args) throws Exception {
		IBOBsBillingCycleValue billingCycleValue=BWFBillingCycleUtil.getCurBillingCycle(null,-1);
		System.out.println(billingCycleValue.getBillingCycleId());
	}

	/**
	 * 获取所有任务信息，并放到一个有序的Map下
	 * @return
	 * @throws Exception  
	 * @Description:
	 */
	public static LinkedHashMap<String, IBOAmTaskRelyValue> qryTaskInfo() throws Exception {
		IEmendYhCommonSV sv = (IEmendYhCommonSV) ServiceFactory.getService(IEmendYhCommonSV.class);
		List<String> list=new ArrayList<String>();
		list=sv.qryGroupInfo();
		LinkedHashMap<String, IBOAmTaskRelyValue> map = new LinkedHashMap<String, IBOAmTaskRelyValue>();
		if(list!=null&&list.size()>0){
			for(int m=0;m<list.size();m++){
				//获取所有的任务依赖信息
				IBOAmTaskRelyValue[] values = sv.qryRely((String)list.get(m));
				//创建一个有序的map
				String preCode = "0";
				if (values != null && values.length > 0) {
					int j = 0;
					while (true) {
						//每取出一个value放到map里，退出for循环
						for (int i = 0; i < values.length; i++) {
							IBOAmTaskRelyValue value = values[i];
							if (value.getPreTaskCode().equals(preCode)) {
								map.put(value.getCurTaskCode(), value);
								preCode = value.getCurTaskCode();
								j++;
								break;
							}
						}
						//当把从数据库取出的值遍历完之后，退出while循环
						if (j == values.length) {
							break;
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 根据任务编码，地市编码,账期 查询任务状态
	 * @return  
	 * @throws Exception 
	 * @Description:
	 */
	public static String qryTaskStateByCondition(String taskCode, String regionId, long bsBillingCycle) throws Exception {
		IEmendYhCommonSV sv = (IEmendYhCommonSV) ServiceFactory.getService(IEmendYhCommonSV.class);
		//根据任务编码，地市编码,账期 查询任务状态信息
		IBOAmSchedPsMonValue[] values = sv.qryPsMonByCondition(taskCode, regionId, bsBillingCycle);
		StringBuffer strBuff = new StringBuffer();
		//将同一个地市下的所有分片状态放在同一个字符串中
		if (values != null && values.length > 0) {
			for (IBOAmSchedPsMonValue value : values) {
				strBuff.append(value.getState());
			}
		}
		//若这个字符串中含有X，则状态为出错；若没有X，有C，则状态为执行中；若全为F，则状态为正确结束
		if(StringUtils.isNotBlank(strBuff.toString())){
			if (strBuff.toString().contains("X")) {
				return "\u5f02\u5e38";
			} else if (strBuff.toString().contains("C")) {
				return "\u6267\u884c\u4e2d";
			} else if(strBuff.toString().contains("F")){
				return "\u6b63\u786e\u7ed3\u675f";
			}
		} 
		return null;
	}
}
