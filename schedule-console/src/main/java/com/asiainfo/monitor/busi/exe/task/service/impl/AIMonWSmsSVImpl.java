package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWSmsBean;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonWSmsDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWSmsValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonWSmsSV;
import com.asiainfo.monitor.tools.model.interfaces.ISmsPersion;
import com.asiainfo.monitor.tools.model.interfaces.ISmsState;
import com.asiainfo.monitor.tools.util.TypeConst;

public class AIMonWSmsSVImpl implements IAIMonWSmsSV {

	/**
	 * 保存短信信息
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long saveOrUpdate(IBOAIMonWSmsValue value) throws RemoteException,Exception{
		IAIMonWSmsDAO smsDao=(IAIMonWSmsDAO)ServiceFactory.getService(IAIMonWSmsDAO.class);
		return smsDao.saveOrUpdate(value);
	}
	
	/**
	 * 批量保存短信信息
	 * @param values
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWSmsValue[] values) throws RemoteException,Exception{
		IAIMonWSmsDAO smsDao=(IAIMonWSmsDAO)ServiceFactory.getService(IAIMonWSmsDAO.class);
		smsDao.saveOrUpdate(values);
	}
	
	/**
	 * 批量保存短信信息，参数对象需要进行转化成值对象
	 * @param sms
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveOrUpdate(ISmsState[] sms) throws RemoteException,Exception{
		IAIMonWSmsDAO smsDao=(IAIMonWSmsDAO)ServiceFactory.getService(IAIMonWSmsDAO.class);
		List result=new ArrayList();
		for (int i=0;i<sms.length;i++){
			BOAIMonWSmsBean smsBean=new BOAIMonWSmsBean();
			smsBean.setRecordId(sms[i].getId());
			smsBean.setInfoId(sms[i].getInfoId());
			smsBean.setCreateDate(sms[i].getCreateDate());
			StringBuffer nums=new StringBuffer("");
			for (int j=0;j<sms[i].getPersion().length;j++){
				ISmsPersion persion=sms[i].getPersion()[j];
				if (persion==null)
					continue;
				int warnLevel=persion.getWarn_Level();
				//短信警告内容级别大于人员设置的发送短信级别时，则记录短信发送记录表
				if (sms[i].getLevel()>=warnLevel && StringUtils.isNotBlank(persion.getPhone_Num())){
					if (!StringUtils.isBlank(nums.toString()))
						nums.append(TypeConst._SPLIT_CHAR);
					nums.append(persion.getPhone_Num());
				}
			}
			
			if (StringUtils.isBlank(nums.toString()))
				continue;
			smsBean.setPhoneNum(nums.toString());
			smsBean.setWarnLevel(sms[i].getLevel()+"");
			smsBean.setSmsContent(sms[i].getMsg());
			smsBean.setState("U");//未发送
			smsBean.setStsToNew();
			result.add(smsBean);
		}
		smsDao.saveOrUpdate((IBOAIMonWSmsValue[])result.toArray(new IBOAIMonWSmsValue[0]));
	}
}
