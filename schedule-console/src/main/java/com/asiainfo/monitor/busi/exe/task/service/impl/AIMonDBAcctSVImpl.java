package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonDBAcctDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBAcctValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonDBAcctSV;

public class AIMonDBAcctSVImpl implements IAIMonDBAcctSV{

	/**
	 * 根据条件查询数据源配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBAcctValue[] getDBAcctByCode(String acctCode) throws RemoteException,Exception {
		IAIMonDBAcctDAO acctDao = (IAIMonDBAcctDAO)ServiceFactory.getService(IAIMonDBAcctDAO.class);
		IBOAIMonDBAcctValue[] values=acctDao.getDBAcctByCode(acctCode);
		if (values!=null && values.length>0 && values[0]!=null){
			for (int i=0;i<values.length;i++){
				values[i].setPassword(K.k(values[i].getPassword()));
			}
		}
		return acctDao.getDBAcctByCode(acctCode);
	}

	/**
	 * 根据主键取得数据源配置信息
	 * 
	 * @param acctCode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBAcctValue getBeanByCode(String acctCode) throws RemoteException,Exception {
		IAIMonDBAcctDAO acctDao = (IAIMonDBAcctDAO)ServiceFactory.getService(IAIMonDBAcctDAO.class);
		IBOAIMonDBAcctValue value=acctDao.getBeanByCode(acctCode);
		value.setPassword(K.k(value.getPassword()));
		return value;
	}
	
	/**
	 * 批量保存或修改数据源配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBAcctValue[] values) throws RemoteException,Exception {
		IAIMonDBAcctDAO acctDao = (IAIMonDBAcctDAO)ServiceFactory.getService(IAIMonDBAcctDAO.class);
		acctDao.saveOrUpdate(values);
	}

	/**
	 * 保存或修改数据源配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBAcctValue value) throws RemoteException,Exception {
		IAIMonDBAcctDAO acctDao = (IAIMonDBAcctDAO)ServiceFactory.getService(IAIMonDBAcctDAO.class);
		acctDao.saveOrUpdate(value);
	}

	/**
	 * 删除数据源配置
	 * 
	 * @param acctCode
	 * @throws Exception
	 */
	public void delete(String acctCode) throws RemoteException,Exception {
		IAIMonDBAcctDAO acctDao = (IAIMonDBAcctDAO)ServiceFactory.getService(IAIMonDBAcctDAO.class);
		acctDao.delete(acctCode);
	}
	
	/**
	 * 检查数据源代码是否存在
	 * 
	 * @param acctCode
	 * @return
	 * @throws Exception
	 */
	public boolean checkCodeExists(String acctCode) throws RemoteException,Exception {
		IAIMonDBAcctDAO acctDao = (IAIMonDBAcctDAO)ServiceFactory.getService(IAIMonDBAcctDAO.class);
		return acctDao.checkCodeExists(acctCode);
	}
	
}
