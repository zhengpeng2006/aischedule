package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.util.XmlUtil;
import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserPriEntityCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserRoleCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.PriEntity;
import com.asiainfo.monitor.busi.cache.impl.RoleDomain;
import com.asiainfo.monitor.busi.config.PriEntityTreeConfig;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserRoleEntityRelDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserPriEntitySV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleEntityRelSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserRoleEntityRelBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserRoleEntityRelEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleEntityRelValue;

public class AIMonUserRoleEntityRelSVImpl implements IAIMonUserRoleEntityRelSV{

	/**
	 * 根据主键取得角色与实体关系信息
	 * 
	 * @param relateId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserRoleEntityRelValue getBeanById(long relateId) throws RemoteException,Exception {
		IAIMonUserRoleEntityRelDAO roleEntityRelDao = (IAIMonUserRoleEntityRelDAO) ServiceFactory
				.getService(IAIMonUserRoleEntityRelDAO.class);
		return roleEntityRelDao.getBeanById(relateId);
	}
	
	/**
	 * 根据角色ID取得对应的实体权限ID
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserRoleEntityRelValue[] getEntityIdByRoleId(String roleId) throws RemoteException,Exception {
		IAIMonUserRoleEntityRelDAO roleEntityRelDao = (IAIMonUserRoleEntityRelDAO) ServiceFactory
				.getService(IAIMonUserRoleEntityRelDAO.class);
		return roleEntityRelDao.getEntityIdByRoleId(roleId);
	}
	
	/**
	 * 保存或修改角色与实体关系信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserRoleEntityRelValue value) throws RemoteException,Exception {
		IAIMonUserRoleEntityRelDAO roleEntityRelDao = (IAIMonUserRoleEntityRelDAO)ServiceFactory.getService(IAIMonUserRoleEntityRelDAO.class);
		roleEntityRelDao.saveOrUpdate(value);
		RoleDomain item=(RoleDomain)MonCacheManager.get(AIMonUserRoleCacheImpl.class,value.getUserRoleId()+"");
		if (item!=null)
			item.setEnable(false);
	}
	
	/**
	 * 批量保存或修改角色与实体关系信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserRoleEntityRelValue[] values) throws RemoteException,Exception {
		IAIMonUserRoleEntityRelDAO roleEntityRelDao = (IAIMonUserRoleEntityRelDAO)ServiceFactory.getService(IAIMonUserRoleEntityRelDAO.class);
		roleEntityRelDao.saveOrUpdate(values);
		for (int i=0;values!=null && values.length>0;i++){
			//角色始终是存在的,将角色实体缓存对象设置为不可能,使其自动重新加载
			RoleDomain item=(RoleDomain)MonCacheManager.get(AIMonUserRoleCacheImpl.class,values[i].getUserRoleId()+"");
			if (item!=null)
				item.setEnable(false);
		}
	}
	
	/**
	 * 保存角色与实体关系信息
	 * 
	 * @param oldValues
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserRoleEntityRelValue[] oldValues,
			IBOAIMonUserRoleEntityRelValue[] values) throws RemoteException,Exception {
		IAIMonUserRoleEntityRelDAO roleEntityRelDao = (IAIMonUserRoleEntityRelDAO)ServiceFactory.getService(IAIMonUserRoleEntityRelDAO.class);
		List roles=new ArrayList();
		if (oldValues != null && oldValues.length >0) {
			roleEntityRelDao.saveOrUpdate(oldValues);
			for (int i=0;i<oldValues.length;i++){
				roles.add(oldValues[i].getUserRoleId()+"");
			}
		}
		if (values != null && values.length > 0) {
			roleEntityRelDao.saveOrUpdate(values);
			for (int i=0;i<values.length;i++){
				if (!roles.contains(values[i].getUserRoleId()+"")){
					roles.add(values[i].getUserRoleId()+"");
				}
			}
		}
		for (int i=0;i<roles.size();i++){
			String roleId=roles.get(i).toString();
			RoleDomain item=(RoleDomain)MonCacheManager.get(AIMonUserRoleCacheImpl.class,roleId);
			if (item!=null)
				item.setEnable(false);
		}
	}
	
	
	
	/**
	 * 获取角色关联实体的状态树
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public String getRoleEntityTreeXml(String roleId) throws RemoteException,Exception{
		// 创建树的根节点
		Element root = XmlUtil.createElement("nodes", "");
		
		// 缓存中获取所有实体
		HashMap allEntityCache = CacheFactory.getAll(AIMonUserPriEntityCacheImpl.class);		
		if(allEntityCache == null || allEntityCache.size() == 0)
			return XmlUtil.formatElement(root);
		
		// 获取当前角色拥有的实体ID
		IAIMonUserRoleEntityRelSV relSV = (IAIMonUserRoleEntityRelSV) ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
		IBOAIMonUserRoleEntityRelValue[] relValues = relSV.getEntityIdByRoleId(roleId);
		HashMap entityIds = new HashMap(); 
		List allEntityList = new ArrayList(allEntityCache.size());
		if (relValues != null && relValues.length > 0) {
			for (int i = 0; i < relValues.length; i++)
				entityIds.put("" + relValues[i].getEntityId(), relValues[i].getEntityId());
		}
		
		// 设置实体的选中状态
		PriEntityTreeConfig entityTreeConfig = null;
		for(Iterator it = allEntityCache.keySet().iterator();it.hasNext();)
		{
			entityTreeConfig = new PriEntityTreeConfig();
			entityTreeConfig.setPriEntity((PriEntity)allEntityCache.get(it.next()));
			entityTreeConfig.setChecked(entityIds.containsKey("" + entityTreeConfig.getPriEntity().getId()));
			allEntityList.add(entityTreeConfig);
		}
		
		// 构建实体的状态树
		PriEntityTreeConfig topEntity = buildRoleEntityTree(allEntityList);
		root.add(topEntity.toXml());
		return XmlUtil.formatElement(root);
		
	}
	/**
	 * 获取角色关联实体的状态树
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public String getRoleEntityTreeXmlNocache(String roleId) throws RemoteException,Exception{
		// 创建树的根节点
		Element root = XmlUtil.createElement("nodes", "");
		
		// 缓存中获取所有实体
		IAIMonUserPriEntitySV sv = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
		List allEntitys = sv.getAllEntity();		
		if(allEntitys == null || allEntitys.size() == 0)
			return XmlUtil.formatElement(root);
		
		// 获取当前角色拥有的实体ID
		IAIMonUserRoleEntityRelSV relSV = (IAIMonUserRoleEntityRelSV) ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
		IBOAIMonUserRoleEntityRelValue[] relValues = relSV.getEntityIdByRoleId(roleId);
		HashMap entityIds = new HashMap(); 
		List allEntityList = new ArrayList(allEntitys.size());
		if (relValues != null && relValues.length > 0) {
			for (int i = 0; i < relValues.length; i++)
				entityIds.put("" + relValues[i].getEntityId(), relValues[i].getEntityId());
		}
		
		// 设置实体的选中状态
		PriEntityTreeConfig entityTreeConfig = null;
		for(int i=0;i<allEntitys.size();i++)
		{
			entityTreeConfig = new PriEntityTreeConfig();
			entityTreeConfig.setPriEntity((PriEntity)allEntitys.get(i));
			entityTreeConfig.setChecked(entityIds.containsKey("" + entityTreeConfig.getPriEntity().getId()));
			allEntityList.add(entityTreeConfig);
		}
		
		// 构建实体的状态树
		PriEntityTreeConfig topEntity = buildRoleEntityTree(allEntityList);
		root.add(topEntity.toXml());
		return XmlUtil.formatElement(root);
		
	}

	private PriEntityTreeConfig buildRoleEntityTree(List allEntityList) {
		PriEntityTreeConfig rootEntity = null;
		for (int i=0;i<allEntityList.size();i++) {
			PriEntityTreeConfig entity = (PriEntityTreeConfig) allEntityList.get(i);
				if ("0".equals(entity.getPriEntity().getParentId())) {
					rootEntity = entity;
					rootEntity.setChilds(new ArrayList());
					allEntityList.remove(i);
					buildRoleEntityTree(rootEntity, allEntityList);
					break;
				}

		}

		return rootEntity;
	}
	
	private void buildRoleEntityTree(PriEntityTreeConfig parent, List allEntityList) {
		for (Iterator it = allEntityList.iterator();it.hasNext();){
			PriEntityTreeConfig entity = (PriEntityTreeConfig) it.next();
			if (parent.getPriEntity().getId().equals(entity.getPriEntity().getParentId())) {
				if (null == parent.getChilds())
					parent.setChilds(new ArrayList());
				parent.getChilds().add(entity);
				it.remove();
			}
		}
		if (allEntityList.size() == 0)
			return;
		if (null != parent.getChilds()) {

			for (int i = 0; i < parent.getChilds().size(); i++) {
				PriEntityTreeConfig entity2 = (PriEntityTreeConfig) parent.getChilds().get(i);
				buildRoleEntityTree(entity2, allEntityList);
			}
		}

	}

    /**
     * 检查该实体是否已经加入角色
     * 
     * @param userId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public boolean checkUserRoleEntityRel(String entityId, String userRoleId) throws RemoteException, Exception
    {

        IAIMonUserRoleEntityRelDAO groupRoleRelDao = (IAIMonUserRoleEntityRelDAO) ServiceFactory.getService(IAIMonUserRoleEntityRelDAO.class);
        return groupRoleRelDao.checkUserRoleEntityRel(entityId, userRoleId);
    }

    /**
     * 根据角色和对应实体除信息
     * 
     * @param userGroupId
     * @param userId
     */
    public boolean delete(long userRoleId, long entityId) throws Exception
    {
        Criteria sql = new Criteria();
        sql.addEqual("user_role_id", String.valueOf(userRoleId));
        sql.addEqual("entity_id", String.valueOf(entityId));

        IAIMonUserRoleEntityRelDAO groupRelDao = (IAIMonUserRoleEntityRelDAO) ServiceFactory.getService(IAIMonUserRoleEntityRelDAO.class);
        BOAIMonUserRoleEntityRelBean[] beans = BOAIMonUserRoleEntityRelEngine.getBeans(sql);

        for(BOAIMonUserRoleEntityRelBean bean : beans) {
            groupRelDao.delete(bean.getRelateId());
        }

        return true;
    }

    /**
     * 根据角色和对应实体除信息
     * 
     * @param userGroupId
     * @param userId
     */
    public boolean delete(long userRoleId) throws Exception
    {
        Criteria sql = new Criteria();
        sql.addEqual("user_role_id", String.valueOf(userRoleId));

        IAIMonUserRoleEntityRelDAO groupRelDao = (IAIMonUserRoleEntityRelDAO) ServiceFactory.getService(IAIMonUserRoleEntityRelDAO.class);
        BOAIMonUserRoleEntityRelBean[] beans = BOAIMonUserRoleEntityRelEngine.getBeans(sql);

        for(BOAIMonUserRoleEntityRelBean bean : beans) {
            groupRelDao.delete(bean.getRelateId());
        }

        return true;
    }

}
