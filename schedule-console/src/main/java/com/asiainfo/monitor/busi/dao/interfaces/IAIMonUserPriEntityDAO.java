package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserPriEntityBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserPriEntityValue;

public interface IAIMonUserPriEntityDAO {

	/**
	 * 根据条件获取查询结果
	 * 
	 * @param cond
	 * @param param
	 * @return IBOAIMonUserPriEntityValue[] 
	 * @throws Exception
	 */
	public IBOAIMonUserPriEntityValue[] query(String cond, Map param) throws Exception;
	
	/**
	 * 根据EntityId查询实体信息
	 * 
	 * @param entityId
	 * @return IBOAIMonUserPriEntityValue
	 * @throws Exception
	 */
	public IBOAIMonUserPriEntityValue queryEntityByEntityId(String entityId) throws Exception;

	
	/**
     * 根据条件检索用户实体信息
     * 
     * @param entityName
     * @param entityType
     * @return
     * @throws Exception
     */
    public IBOAIMonUserPriEntityValue[] getUserEntityByCond(String entityName, String entityType) throws Exception;

    /**
     * 批量保存或修改实体信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserPriEntityValue[] values) throws Exception;

    /**
     * 删除实体信息
     * 
     * @param entityId
     * @throws Exception
     */
    public void delete(long entityId) throws Exception;

    /**
     * 保存或修改实体信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserPriEntityValue value) throws Exception;

    public BOAIMonUserPriEntityBean[] getPrivEntityPrior() throws Exception;

    /**
     * 根据实体名称和代码校验记录是否存在
     * 
     * @param entityCode
     * @param entityName
     * @return
     * @throws Exception
     */
    public boolean checkByCodeAndName(String entityCode, String entityName) throws Exception;
	
}
