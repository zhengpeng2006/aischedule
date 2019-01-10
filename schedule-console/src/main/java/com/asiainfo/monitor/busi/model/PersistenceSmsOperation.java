package com.asiainfo.monitor.busi.model;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWSmsBean;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonWSmsSV;
import com.asiainfo.monitor.tools.model.interfaces.ISmsOperation;
import com.asiainfo.monitor.tools.model.interfaces.ISmsPersion;
import com.asiainfo.monitor.tools.model.interfaces.ISmsState;
import com.asiainfo.monitor.tools.util.TypeConst;

public class PersistenceSmsOperation implements ISmsOperation {

	/**
	 * 将警告信息持久化
	 */
	public boolean sendSms(ISmsState smsState) throws Exception {
		if (smsState==null)
			return false;
		if (smsState.getPersion()==null || smsState.getPersion().length<1)
			return false;
		BOAIMonWSmsBean smsBean=new BOAIMonWSmsBean();
		smsBean.setRecordId(smsState.getId());
		smsBean.setInfoId(smsState.getInfoId());
		smsBean.setCreateDate(smsState.getCreateDate());
		StringBuffer nums=new StringBuffer("");
		for (int i=0;i<smsState.getPersion().length;i++){
			ISmsPersion persion=smsState.getPersion()[i];
			if (persion==null)
				continue;
			int warnLevel=persion.getWarn_Level();
			//短信警告内容级别大于人员设置的发送短信级别时，则记录短信发送记录表
			if (smsState.getLevel()>=warnLevel && StringUtils.isNotBlank(persion.getPhone_Num())){
				if (!StringUtils.isBlank(nums.toString()))
					nums.append(TypeConst._SPLIT_CHAR);
				nums.append(persion.getPhone_Num());
			}
		}
		
		if (StringUtils.isBlank(nums.toString()))
			return false;
		smsBean.setPhoneNum(nums.toString());
		smsBean.setWarnLevel(smsState.getLevel()+"");
		smsBean.setSmsContent(smsState.getMsg());
		smsBean.setState("U");//未发送
		IAIMonWSmsSV smsSV=(IAIMonWSmsSV)ServiceFactory.getService(IAIMonWSmsSV.class);
		smsSV.saveOrUpdate(smsBean);
		return true;
	}

}
