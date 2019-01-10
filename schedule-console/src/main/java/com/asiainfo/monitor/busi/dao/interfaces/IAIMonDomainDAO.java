package com.asiainfo.monitor.busi.dao.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainRelatValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainValue;

public interface IAIMonDomainDAO {
    /**
     * 获取域信息
     * @param domainCode
     * @param domainType
     * @param domainDesc
     * @param state
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainValue[] getDomain(String domainCode, String domainType, String domainDesc, String state) throws Exception;
    
    /**
     * 根据主键获取域信息
     * @param domainId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public IBOAIMonDomainValue getDomainById(long domainId)throws RemoteException,Exception;
    
    /**
     * 读取所有域信息
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainValue[] getAllDomainBean() throws Exception;
    
    /**
     * 根据关联类型、与域关联对象标识读取关联信息
     * @param type
     * @param relatDomainId
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainRelatValue[] getDomainRelatByTypeAndRelatId(String type, String relatDomainId) throws Exception;
    
    
    /**
     * 根据关联类型、与域标识读取关联信息
     * @param type
     * @param relatDomainId
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainRelatValue[] getDomainRelatByTypeAndId(String type, String domainId) throws Exception;
    
    /**
     * 新增或修改域与组、主机、应用的关系
     * @param value
     * @throws RemoteException
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonDomainRelatValue value)throws RemoteException,Exception;
    
    /**
     * 根据组、主机、应用获取隶属于的域信息
     * @param relateDomainType
     * @param relateDomainId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public IBOAIMonDomainValue[] getRelateDomain(String relateDomainType, String relateDomainId) throws RemoteException,Exception;
    
    /**
     * 删除域关系
     * @param type
     * @param id
     * @throws RemoteException
     * @throws Exception
     */
    public void delDomainRelatByPk(String relatId) throws RemoteException,Exception;
}
