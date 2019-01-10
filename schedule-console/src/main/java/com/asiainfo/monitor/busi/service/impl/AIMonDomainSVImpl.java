package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.AIMonDomainCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonDomainCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.Domain;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonDomainDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonDomainSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomainRelatBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainRelatValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonDomainSVImpl implements IAIMonDomainSV {

    /**
     * 获取域信息实现
     */
    public IBOAIMonDomainValue[] getDomains(String domainCode, String domainType, String domainDesc, String state)throws RemoteException, Exception {
        IAIMonDomainDAO dao = (IAIMonDomainDAO)ServiceFactory.getService(IAIMonDomainDAO.class);
        return dao.getDomain(domainCode, domainType, domainDesc, state);
    }

    /**
     * 根据标识读取域信息
     * @param domainId
     */
    public IBOAIMonDomainValue getDomainBeanById(long domainId) throws RemoteException, Exception {
        IAIMonDomainDAO dao = (IAIMonDomainDAO)ServiceFactory.getService(IAIMonDomainDAO.class);
        return dao.getDomainById(domainId);
    }
    
    /**
     * 根据标识读取域信息
     * @param id
     * @return
     * @throws Exception
     */
    public Domain getDomainById(String id) throws RemoteException,Exception{
    	if (StringUtils.isBlank(id))
    		return null;
    	Domain result=(Domain)MonCacheManager.get(AIMonDomainCacheImpl.class,id);
    	if (result==null ){			
			result=this.getDomainByIdFromDb(id);
			if (!MonCacheManager.getCacheReadOnlySet() && result!=null){
				MonCacheManager.put(AIMonDomainCacheImpl.class,result.getId(),result);
			}
		}
		return result;
    }
    
    /**
     * 获取所有域信息
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainValue[] getAllDomainBean() throws RemoteException,Exception{    	
    	IAIMonDomainDAO dao = (IAIMonDomainDAO)ServiceFactory.getService(IAIMonDomainDAO.class);
    	return dao.getAllDomainBean();
    }

    /**
     * 根据标识从数据读取域信息
     * @param id
     * @return
     * @throws Exception
     */
    public Domain getDomainByIdFromDb(String id) throws RemoteException,Exception{
    	if (StringUtils.isBlank(id))
    		return null;
    	IBOAIMonDomainValue value=this.getDomainBeanById(Long.valueOf(id));
    	return this.wrapperDomainByBean(value);
    }
    /**
     * 简单封装域信息
     * @param value
     * @return
     * @throws Exception
     */
    public Domain wrapperDomainByBean(IBOAIMonDomainValue value) throws RemoteException,Exception{
    	if (value==null || StringUtils.isBlank(value.getState())  || value.getState().equals("E"))
    		return null;
    	Domain result=new Domain();
    	result.setId(value.getDomainId()+"");
    	result.setCode(value.getDomainCode());
    	result.setType(value.getDomainType());
    	result.setDesc(value.getDomainDesc());
    	result.setCacheListener(new AIMonDomainCheckListener());
		return result;
    }
    /**
     * 根据关联类型、与域关联对象标识读取关联信息
     * @param type
     * @param relatDomainId
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainRelatValue getDomainRelatByTypeAndRelatId(String type,String relatDomainId) throws RemoteException,Exception{
    	IAIMonDomainDAO dao = (IAIMonDomainDAO)ServiceFactory.getService(IAIMonDomainDAO.class);
    	IBOAIMonDomainRelatValue[] values=dao.getDomainRelatByTypeAndRelatId(type,relatDomainId);
    	if (values==null || values.length<1)
    		return null;
    	else
    		return values[0];
    }
    

    /**
     * 根据关联类型、与域标识读取关联信息
     * @param type
     * @param relatDomainId
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainRelatValue[] getDomainRelatByTypeAndId(String type,String domainId) throws RemoteException,Exception{
    	IAIMonDomainDAO dao = (IAIMonDomainDAO)ServiceFactory.getService(IAIMonDomainDAO.class);
    	IBOAIMonDomainRelatValue[] values=dao.getDomainRelatByTypeAndId(type,domainId);
    	return values;
    }
    
    /**
	 * 获取组、主机、应用对应的域
	 */
	public IBOAIMonDomainValue[] getRelateDomains(String relateDomainType,String relateDomainId) throws RemoteException,Exception {
		IAIMonDomainDAO dao = (IAIMonDomainDAO) ServiceFactory.getService(IAIMonDomainDAO.class);
		return dao.getRelateDomain(relateDomainType, relateDomainId);
	}

	public void saveRelat(String domainId, String relatDomainId,String relatType) throws RemoteException,Exception {
		if (StringUtils.isBlank(domainId))
			// "域标识不能为空！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000099"));
		if (StringUtils.isBlank(relatDomainId))
			// 域关联标识不能为空！
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000100"));
		if (StringUtils.isBlank(relatType))
			// 关联类型不能为空！
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000101"));
		IAIMonDomainDAO domainDao = (IAIMonDomainDAO) ServiceFactory.getService(IAIMonDomainDAO.class);
		IBOAIMonDomainRelatValue relatValue = this.getDomainRelatByTypeAndRelatId(relatType, relatDomainId);
		if (relatValue == null)
			relatValue = new BOAIMonDomainRelatBean();
		relatValue.setDomainId(Long.parseLong(domainId));
		relatValue.setRelatdomainId(Long.parseLong(relatDomainId));
		relatValue.setRelatdomainType(relatType);
		relatValue.setState("U");
		domainDao.saveOrUpdate(relatValue);
		
	}

	public void deleteRelat(String relatDomainId, String relatType) throws RemoteException,Exception {
		if (StringUtils.isBlank(relatType))
			// 关联类型为空,无法删除！
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000102"));
		if (StringUtils.isBlank(relatDomainId))
			// 域关联标识不能为空！
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000100"));
		IAIMonDomainDAO domainDao = (IAIMonDomainDAO) ServiceFactory.getService(IAIMonDomainDAO.class);
		IBOAIMonDomainRelatValue relatValue = this.getDomainRelatByTypeAndRelatId(relatType, relatDomainId);
		if (relatValue == null)
			return;

		domainDao.delDomainRelatByPk(String.valueOf(relatValue.getRelatId()));
	}
	
}
