package com.asiainfo.monitor.busi.service.impl;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonStaticDataCacheImpl;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonStaticDataDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;

public class AIMonStaticDataSVImpl implements IAIMonStaticDataSV {

	/**
	 * 通过自定义查询条件查询
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] getAllStaticDataBean() throws RemoteException,Exception {
		IAIMonStaticDataDAO dao = (IAIMonStaticDataDAO) ServiceFactory.getService(IAIMonStaticDataDAO.class);
		return dao.query(IBOAIMonStaticDataValue.S_State+"='U'",null);
	}

	/**
	 * 通过codeType查询静态数据集
	 * @param codeType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] queryByCodeType(String codeType) throws RemoteException,Exception {
		IBOAIMonStaticDataValue[] result=(IBOAIMonStaticDataValue[])CacheFactory.get(AIMonStaticDataCacheImpl.class,AIMonStaticDataCacheImpl.CODE_TYPE_PREFIX_KEY+codeType);
		return result;
	}
	
	/**
	 * 通过ExternCodeType查询静态数据集
	 * @param externCodeType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] queryByEct(String externCodeType) throws RemoteException,Exception {
		IAIMonStaticDataDAO dao = (IAIMonStaticDataDAO) ServiceFactory.getService(IAIMonStaticDataDAO.class);
		return dao.queryByECT(externCodeType);
	}

	/**
	 * 通过CodeType和CodeValue查询静态数据
	 * @param codeType
	 * @param codeValue
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue queryByCodeTypeAndValue(String codeType,String codeValue) throws RemoteException,Exception {
		IAIMonStaticDataDAO dao = (IAIMonStaticDataDAO) ServiceFactory.getService(IAIMonStaticDataDAO.class);
		return dao.queryByCodeTypeAndValue(codeType, codeValue);
	}

	public IBOAIMonStaticDataValue[] queryByCodeType(String[] codeTypes) throws RemoteException,Exception {
		if(codeTypes == null || codeTypes.length ==0 )
			return null;
		List result = new ArrayList();
		IBOAIMonStaticDataValue[] values = null;
		for(int i=0;i<codeTypes.length;i++){
			values = this.queryByCodeType(codeTypes[i]);
			if(values != null && values.length > 0)
				java.util.Collections.addAll(result,values);
		}
		return (IBOAIMonStaticDataValue[])result.toArray(new IBOAIMonStaticDataValue[0]);
	}

	/**
	 * 保存静态数据
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonStaticDataValue value) throws RemoteException,Exception{
		IAIMonStaticDataDAO dao = (IAIMonStaticDataDAO) ServiceFactory.getService(IAIMonStaticDataDAO.class);
		dao.saveOrUpdate(value);
	}
	
	/**
	 * 批量保存静态数据
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonStaticDataValue[] values) throws RemoteException,Exception{
		IAIMonStaticDataDAO dao = (IAIMonStaticDataDAO) ServiceFactory.getService(IAIMonStaticDataDAO.class);
		dao.saveOrUpdate(values);
	}
	
	/**
     * 通过静态表查询配置的任务信息
     * @param codeType（taskType）
     * @param codeValue（taskCode）
     * @return
     */
    @Override
    public IBOAIMonStaticDataValue[] qryTaskInfos(String codeType, String codeValue,String taskCode1) throws RemoteException, Exception
    {
        IAIMonStaticDataDAO dao = (IAIMonStaticDataDAO) ServiceFactory.getService(IAIMonStaticDataDAO.class);
        return dao.qryTaskInfos(codeType,codeValue,taskCode1);
    }
}
