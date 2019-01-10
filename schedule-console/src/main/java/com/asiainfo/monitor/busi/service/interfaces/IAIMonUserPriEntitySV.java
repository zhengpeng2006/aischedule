package com.asiainfo.monitor.busi.service.interfaces;

import java.util.List;

import com.asiainfo.monitor.busi.cache.impl.PriEntity;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserPriEntityBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserPriEntityValue;

public interface IAIMonUserPriEntitySV {

	/**
     * 将实体简单封装
     * @param value
     * @return
     * @throws Exception
     */
    public PriEntity wrapperPriEntityByBean(IBOAIMonUserPriEntityValue value) throws Exception;


    /**
     * 根据EntityId查询实体信息
     * 
     * @param entityId
     * @return IBOAIMonUserPriEntityValue
     * @throws Exception
     */
    public PriEntity getEntityByEntityId(String entityId) throws Exception;


    /**
     * 根据parentId查询实体信息
     * 
     * @param parentId
     * @return IBOAIMonUserPriEntityValue[] 
     * @throws Exception
     */
    public List getEntityByParentId(String parentId) throws Exception;


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
     * 根据parentId查询实体信息
     * 
     * @param parentId
     * @return IBOAIMonUserPriEntityValue[] 
     * @throws Exception
     */
    public List getEntityByParentIdNocache(String parentId) throws Exception;


    /**
     * 从数据库读取实体信息并简单封装
     * @param id
     * @return
     * @throws Exception
     */
    public PriEntity getPriEntiryByIdFromDb(String id) throws Exception;


    /**
     * 根据用户权限获取实体信息
     * 
     * @param userId
     * @param parentId
     * @param type
     * @return IBOAIMonUserPriEntityValue[] 
     * @throws Exception
     */
    public List queryEntityByPermission(String userId, String parentId, String type) throws Exception;


    /**
     * 获取所有实体信息
     * @return
     * @throws Exception
     */
    public IBOAIMonUserPriEntityValue[] getAllEntityBean() throws Exception;


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
     * 根据区域获取扩展实体
     * @param busiAreaId
     * @return
     * @throws Exception
     */
    public List getExtendEntitysByEntityId(String entityId) throws Exception;


    /**
     * 根据加载类型获取实体信息
     * @param loadType
     * @return
     * @throws Exception
     */
    public List getEntityByLoadType(String loadType) throws Exception;


    /**
     * 保存或修改实体信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserPriEntityValue value) throws Exception;


    /**
     * 返回缓存中所有的实体信息
     * @return
     * @throws Exception
     */
    public List getAllEntity() throws Exception;


    /**
     * 根据EntityId查询实体信息
     * 
     * @param entityId
     * @return IBOAIMonUserPriEntityValue
     * @throws Exception
     */
    public PriEntity getEntityByEntityIdNocache(String entityId) throws Exception;


    public BOAIMonUserPriEntityBean[] getPrivEntityPrior(String parentId) throws Exception;
	
//	public Map getAll() throws Exception;

    public IBOAIMonUserPriEntityValue getPriEntityByEntityId(String entityId) throws Exception;

    /**
     * 根据j角色查询实体信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserPriEntityValue[] getUserEntityListByRoleId(String userRoleId) throws Exception;

    /**
     * 根据角色查询要关联的实体信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserPriEntityValue[] getRelUserEntityListByRoleId(String userRoleId) throws Exception;

    /**
     * 根据实体编码和名字校验实体是否存在
     * 
     * @param entityCode
     * @param entityName
     * @return
     * @throws Exception
     */
    public boolean checkByCodeAndName(String entityCode, String entityName) throws Exception;

}
