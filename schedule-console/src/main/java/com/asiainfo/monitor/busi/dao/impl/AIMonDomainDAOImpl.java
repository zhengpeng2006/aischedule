package com.asiainfo.monitor.busi.dao.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonDomainDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomainEngine;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomainRelatBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomainRelatEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainRelatValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainValue;

public class AIMonDomainDAOImpl implements IAIMonDomainDAO {

    /**
     * 获取域信息
     */
    public IBOAIMonDomainValue[] getDomain(String domainCode,
            String domainType, String domainDesc, String state)
            throws Exception {
        StringBuilder sql = new StringBuilder(" 1=1");
        Map param = new HashMap();
        if(StringUtils.isNotBlank(domainCode)){
            sql.append(" AND ").append(IBOAIMonDomainValue.S_DomainCode).append(" like :").append(IBOAIMonDomainValue.S_DomainCode);
            param.put(IBOAIMonDomainValue.S_DomainCode, "%"  + domainCode + "%");
        }
        if(StringUtils.isNotBlank(domainType)){
            sql.append(" AND ").append(IBOAIMonDomainValue.S_DomainType).append(" like :").append(IBOAIMonDomainValue.S_DomainType);
            param.put(IBOAIMonDomainValue.S_DomainType, "%"  + domainType + "%");
        }
        if(StringUtils.isNotBlank(domainDesc)){
            sql.append(" AND ").append(IBOAIMonDomainValue.S_DomainDesc).append(" like :").append(IBOAIMonDomainValue.S_DomainDesc);
            param.put(IBOAIMonDomainValue.S_DomainDesc, "%"  + domainDesc + "%");
        }
        if(StringUtils.isNotBlank(state)){
            sql.append(" AND ").append(IBOAIMonDomainValue.S_State).append("= :").append(IBOAIMonDomainValue.S_State);
            param.put(IBOAIMonDomainValue.S_State, state);
        }
        return BOAIMonDomainEngine.getBeans(sql.toString(), param);
    }

    /**
     * 根据主键获取域信息
     */
    public IBOAIMonDomainValue getDomainById(long domainId) throws RemoteException, Exception {
        return BOAIMonDomainEngine.getBean(domainId);
    }

    /**
     * 读取所有域信息
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainValue[] getAllDomainBean() throws Exception{
    	String condition=IBOAIMonDomainValue.S_State+" ='U'";
    	return BOAIMonDomainEngine.getBeans(condition,null);
    }
    
    /**
     * 根据关联类型、与域关联对象标识读取关联信息
     * @param type
     * @param relatDomainId
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainRelatValue[] getDomainRelatByTypeAndRelatId(String type,String relatDomainId) throws Exception{
    	StringBuilder sb=new StringBuilder("");
    	sb.append(IBOAIMonDomainRelatValue.S_RelatdomainType+" = :type ");
    	sb.append(" AND ");
    	sb.append(IBOAIMonDomainRelatValue.S_RelatdomainId+" = :relatDomainId");
    	sb.append(" AND "+IBOAIMonDomainRelatValue.S_State+" ='U' ");
    	HashMap parameter=new HashMap();
    	parameter.put("type",type);
    	parameter.put("relatDomainId",relatDomainId);
    	return BOAIMonDomainRelatEngine.getBeans(sb.toString(),parameter); 
    }
    
    /**
     * 根据关联类型、与域标识读取关联信息
     * @param type
     * @param relatDomainId
     * @return
     * @throws Exception
     */
    public IBOAIMonDomainRelatValue[] getDomainRelatByTypeAndId(String type,String domainId) throws Exception{
    	StringBuilder sb=new StringBuilder("");
    	sb.append(IBOAIMonDomainRelatValue.S_RelatdomainType+" = :type ");
    	sb.append(" AND ");
    	sb.append(IBOAIMonDomainRelatValue.S_DomainId+" = :domainId");
    	sb.append(" AND "+IBOAIMonDomainRelatValue.S_State+" ='U' ");
    	HashMap parameter=new HashMap();
    	parameter.put("type",type);
    	parameter.put("domainId",domainId);
    	return BOAIMonDomainRelatEngine.getBeans(sb.toString(),parameter); 
    }
    
    /**
     * 新增或修改域与组、主机、应用的关系
     */
    public void saveOrUpdate(IBOAIMonDomainRelatValue value)
            throws RemoteException, Exception {
        if(value.isNew()){
         value.setRelatId(BOAIMonDomainRelatEngine.getNewId().longValue());   
        }
        BOAIMonDomainRelatEngine.save(value);
    }

    /**
     * 根据组、主机、应用获取隶属于的域信息
     */
	public IBOAIMonDomainValue[] getRelateDomain(String relateDomainType, String relateDomainId) throws Exception {
		
		IBOAIMonDomainValue[] domainValues = null;
		
		// 获取域关系，通过域关系获取域主键（DOMAIN_ID）
		IBOAIMonDomainRelatValue[] rltValue = getDomainRelatByTypeAndRelatId(relateDomainType, relateDomainId);
		if(rltValue.length<1){
			return new IBOAIMonDomainValue[0];
		}
		
		// 根据主键（DOMAIN_ID）获取域信息（AI_MON_DOMAIN）
		domainValues = new IBOAIMonDomainValue[rltValue.length];
		for(int i=0;i<rltValue.length;i++){
			domainValues[i] = BOAIMonDomainEngine.getBean(rltValue[i].getDomainId());
		}
		
		return domainValues;
	}

	/**
	 * 逻辑删除域关系
	 */
	public void delDomainRelatByPk(String relatId) throws RemoteException, Exception {
		BOAIMonDomainRelatBean bean = BOAIMonDomainRelatEngine.getBean(Long.parseLong(relatId));
		bean.setState("E");
		BOAIMonDomainRelatEngine.save(bean);
	}
}
