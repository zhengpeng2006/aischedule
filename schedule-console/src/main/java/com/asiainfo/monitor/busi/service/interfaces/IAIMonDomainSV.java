package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.cache.impl.Domain;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainRelatValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainValue;


public interface IAIMonDomainSV {
    /**
     * 获取域信息列表
     * @param domainCode
     * @param domainType
     * @param domainDesc
     * @param state
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public IBOAIMonDomainValue[] getDomains(String domainCode, String domainType, String domainDesc, String state) throws RemoteException,Exception;
    
    /**
     * 根据主键获取域信息
     * @param domainId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public IBOAIMonDomainValue getDomainBeanById(long domainId)throws RemoteException,Exception;

    /**
     * 根据标识读取域信息
     * @param id
     * @return
     * @throws Exception
     */
    public Domain getDomainById(String id) throws RemoteException,Exception;
    
    /**
     * 获取所有域信息
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainValue[] getAllDomainBean() throws RemoteException,Exception;
    
    /**
     * 根据标识从数据读取域信息
     * @param id
     * @return
     * @throws Exception
     */
    public Domain getDomainByIdFromDb(String id) throws RemoteException,Exception;
    
    /**
     * 简单封装域信息
     * @param value
     * @return
     * @throws Exception
     */
    public Domain wrapperDomainByBean(IBOAIMonDomainValue value) throws RemoteException,Exception;
    
    /**
     * 根据关联类型、与域关联对象标识读取关联信息
     * @param type
     * @param relatDomainId
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainRelatValue getDomainRelatByTypeAndRelatId(String type, String relatDomainId) throws RemoteException,Exception;
    
    
    /**
     * 根据关联类型、与域标识读取关联信息
     * @param type
     * @param relatDomainId
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainRelatValue[] getDomainRelatByTypeAndId(String type, String domainId) throws RemoteException,Exception;
    
	/**
	 * 获取组、主机、应用对应的域
	 * @param relateDomainType
	 * @param relateDomainId
	 * @param domainState
	 * @param relateState
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDomainValue[] getRelateDomains(String relateDomainType, String relateDomainId) throws RemoteException,Exception;

	/**
	 * 保存域的关系数据
	 * @param domainId 域标识
	 * @param relatDomainId 与域的关系标识
	 * @param relatType 与域关系类型
	 * @throws Exception
	 */
	public void saveRelat(String domainId, String relatDomainId, String relatType) throws RemoteException,Exception;

	/**
	 * 删除域的关系数据
	 * @param relatDomainId
	 * @param string
	 * @throws Exception
	 */
	public void deleteRelat(String relatDomainId, String relatType) throws RemoteException,Exception;

}
